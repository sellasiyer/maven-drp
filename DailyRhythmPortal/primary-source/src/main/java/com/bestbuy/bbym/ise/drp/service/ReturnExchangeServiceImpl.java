package com.bestbuy.bbym.ise.drp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.dao.EntitlementRulesDao;
import com.bestbuy.bbym.ise.drp.dao.RetExchDevcEntitlsDaoImpl;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Entitlement;
import com.bestbuy.bbym.ise.drp.domain.EntitlementRulesModel;
import com.bestbuy.bbym.ise.drp.domain.EntitlementRulesSingleton;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlementRequest;
import com.bestbuy.bbym.ise.drp.domain.RetExchDevcEntitlsModel;
import com.bestbuy.bbym.ise.drp.sao.EcTransactionLookupSao;
import com.bestbuy.bbym.ise.drp.sao.MobileEntitlementSao;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("returnExchangeService")
public class ReturnExchangeServiceImpl implements ReturnExchangeService {

    private static Logger logger = LoggerFactory.getLogger(ReturnExchangeServiceImpl.class);

    @Autowired
    private EntitlementRulesDao entitlementRulesDao;

    @Autowired
    private RetExchDevcEntitlsDaoImpl retExchDevcEntitlsDaoImpl;

    @Autowired
    private MobileEntitlementSao mobileEntitlementSao;

    @Autowired
    private EcTransactionLookupSao ecTransactionLookupSao;

    private EntitlementRulesSingleton entitlementRulesSingleton = new EntitlementRulesSingleton();

    private static final String CAP_MESSAGE_PLACEHOLDER = "<CAPMESSAGE>";

    private EntitlementRulesSingleton getEntitlementRulesSingleton() {
	if (entitlementRulesSingleton.getEntitlementRulesMap().isEmpty()){
	    refreshEntitlementRulesSingleton();
	}

	return entitlementRulesSingleton;
    }

    public void setEntitlementRulesSingleton(EntitlementRulesSingleton entitlementRulesSingleton) {
	this.entitlementRulesSingleton = entitlementRulesSingleton;
    }

    private List<EntitlementRulesModel> getAllEntitlementRules() throws ServiceException {
	return entitlementRulesDao.getAllEntitlementRules();
    }

    @Transactional
    @Override
    public String createDeviceEntitlement(RetExchDevcEntitlsModel retExchDevcEntitlsModel,
	    DataTransferSummary dataTransferSummary, String existingDataSharingKey) {
	return retExchDevcEntitlsDaoImpl.createDeviceEntitlement(retExchDevcEntitlsModel, dataTransferSummary,
		existingDataSharingKey);
    }

    private synchronized void refreshEntitlementRulesSingleton() {
	try{
	    List<EntitlementRulesModel> EntitlementRulesList = getAllEntitlementRules();
	    Map<String, String> entitlementKeyMap = new HashMap<String, String>();
	    Map<Map<String, String>, EntitlementRulesModel> entitlementRulesMap = new HashMap<Map<String, String>, EntitlementRulesModel>();
	    for(EntitlementRulesModel entitlementRuleModel: EntitlementRulesList){
		entitlementKeyMap.put(entitlementRuleModel.getCoverageBenefit().toUpperCase(), entitlementRuleModel
			.getCoverageVehicle().toUpperCase());
		entitlementRulesMap.put(new HashMap<String, String>(entitlementKeyMap), entitlementRuleModel);
		entitlementKeyMap.clear();
	    }
	    entitlementRulesSingleton.setEntitlementRulesMap(entitlementRulesMap);
	}catch(ServiceException se){
	    logger.error("refreshEntitlementRulesSingleton : error in refreshing entitlement singleton");
	    //TODO : throw the exception to the caller
	}
    }

    @Override
    public MobileEntitlement getMobileEntitlement(MobileEntitlementRequest mobileEntitlementRequest, DrpUser drpUser,
	    String iseTransactionId) throws ServiceException, IseBusinessException {
	MobileEntitlement mobileEntitlement = null;
	try{
	    mobileEntitlement = mobileEntitlementSao.getMobileEntitlements(mobileEntitlementRequest, drpUser,
		    iseTransactionId);

	    logger.info("\nEntitlement list returned from Service :"
		    + mobileEntitlement.getEntitlementList().toString());
	    mobileEntitlement.setEntitlementList(getEntitlementRulesSingleton().supplyEntitlementDetailsAndActions(
		    mobileEntitlement.getEntitlementList(), mobileEntitlement.isTradeable(),
		    mobileEntitlementRequest.isUpgradeEligible()));

	    if (mobileEntitlement.getEtfDescription() != null && !mobileEntitlement.getEtfDescription().isEmpty()){
		for(Entitlement entitlement: mobileEntitlement.getEntitlementList()){
		    if (entitlement.getDetails() != null){
			String etfMessage = mobileEntitlement.getEtfDescription();
			if (!StringUtils.isEmpty(mobileEntitlement.getDeviceETF()))
			    etfMessage = etfMessage + " - Current ETF is $" + mobileEntitlement.getEstimatedETF();
			entitlement.setDetails(entitlement.getDetails().replace(CAP_MESSAGE_PLACEHOLDER, etfMessage));
		    }
		}
	    }
	}catch(IseBusinessException e){
	    throw e;
	}catch(ServiceException e){
	    logger.error("Throwing ServiceException with Custom Message");
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException,
		    "We are not able to recommend Customer Service Options at this time.");
	}
	return mobileEntitlement;
    }

    @Override
    public Product validateTransactionKeySkuCombo(String transactionKey, DrpUser drpUser, String sku)
	    throws ServiceException {
	try{
	    List<Product> productList = ecTransactionLookupSao.getAllTransactionsByFourPartKey(transactionKey, drpUser,
		    false);
	    if (productList == null || productList.size() == 0)
		throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, "Transaction not found. Please try again.");
	    for(Product product: productList){
		if (product != null){
		    if (sku.equalsIgnoreCase(product.getSku())){
			return product;
		    }
		}
	    }
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND,
		    "Transaction and SKU not found. Please try again.");
	}catch(ServiceException se){
	    logger.error("Error calling getAllTransactionsByFourPartKey", se);
	    if (IseExceptionCodeEnum.NOTFOUND.equals(se.getErrorCode()))
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	    throw new ServiceException(
		    IseExceptionCodeEnum.ExternalServiceException,
		    "Purchase Transaction cannot be verified at this time.  We are not able to recommend Customer Service Options at this time.");
	}catch(Exception e){
	    logger.error("Error calling getAllTransactionsByFourPartKey", e);
	    throw new ServiceException(
		    IseExceptionCodeEnum.ExternalServiceException,
		    "Purchase Transaction cannot be verified at this time.  We are not able to recommend Customer Service Options at this time.");
	}
    }

    @Override
    public RetExchDevcEntitlsModel getMobileDeviceDetails(String handsetId, String phoneNumber) throws ServiceException {
	RetExchDevcEntitlsModel retExchDevcEntitlsModel = null;
	try{
	    retExchDevcEntitlsModel = retExchDevcEntitlsDaoImpl.getDeviceEntitlement(handsetId, phoneNumber);
	}catch(DataAccessException ex){
	    logger.error("RET & EXCH: Get Mobile Details - Unexpected Error. HandsetId:" + handsetId + " Phone Number:"
		    + phoneNumber, ex);
	    throw new ServiceException(IseExceptionCodeEnum.CONFLICT, ex.getMessage());
	}catch(RuntimeException e){
	    logger.error("RET & EXCH: Get Mobile Details - Unexpected Error. HandsetId:" + handsetId + " Phone Number:"
		    + phoneNumber, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return retExchDevcEntitlsModel;
    }
}
