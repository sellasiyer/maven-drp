package com.bestbuy.bbym.ise.drp.sao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.BuybackPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclienthub.CodeDescriptionType;
import com.bestbuy.bbym.ise.iseclienthub.ContractProductType;
import com.bestbuy.bbym.ise.iseclienthub.ContractStatusEnum;
import com.bestbuy.bbym.ise.iseclienthub.ContractType;
import com.bestbuy.bbym.ise.iseclienthub.CustomerIDType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanDetailRequestType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanDetailResponseType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanDetailType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanIDType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanSearchRequestType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanSearchResponseType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanTypeEnum;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanUpdateRequestType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanUpdateResponseType;
import com.bestbuy.bbym.ise.iseclienthub.RequestMetadataType;
import com.bestbuy.bbym.ise.iseclienthub.TransactionType;
import com.bestbuy.bbym.ise.util.Util;

/**
 * @author a218635
 */
@Service("hubProtectionPlanSao")
public class HubProtectionPlanSaoImpl extends AbstractSao implements HubProtectionPlanSao {

    private static Logger logger = LoggerFactory.getLogger(HubProtectionPlanSaoImpl.class);
    private static final String SERVICE = "HUB ";

    /**
     * Searches all the GSPs associated with the Customer based on his
     * customerId from the Protection Plan Foundation System.
     */
    @Override
    public List<ServicePlan> searchServicePlan(Customer bbyCustomer) throws ServiceException, IseBusinessException {
	return searchServicePlan(bbyCustomer, null, null);
    }

    public List<ServicePlan> searchServicePlanByPlanId(String protectionPlanId) throws ServiceException,
	    IseBusinessException {
	return searchServicePlan(null, protectionPlanId, null);
    }

    private List<ServicePlan> searchServicePlan(Customer bbyCustomer, String protectionPlanId, String deviceSerialNumber)
	    throws ServiceException, IseBusinessException {

	logger.info("In the searchServicePlan method");
	List<ServicePlan> servicePlanInfoList = new ArrayList<ServicePlan>();
	ProtectionPlanSearchRequestType protectionPlanSearchReq = new ProtectionPlanSearchRequestType();

	ProtectionPlanSearchResponseType protectionPlanSearchResponse = null;

	CustomerIDType custIdType = new CustomerIDType();

	if (bbyCustomer != null){
	    custIdType.setValue(bbyCustomer.getBbyCustomerId());
	    protectionPlanSearchReq.setCustomerID(custIdType);
	    logger.info("Invoking the Search protection Plan Service with custId : " + custIdType.getValue());
	}

	if (protectionPlanId != null){
	    ProtectionPlanIDType protectionPlanIDType = new ProtectionPlanIDType();
	    protectionPlanIDType.setValue(protectionPlanId);
	    protectionPlanSearchReq.setProtectionPlanID(protectionPlanIDType);
	    logger.info("Invoking the Search protection Plan Service with ProtectionPlanId : " + protectionPlanId);
	}

	if (deviceSerialNumber != null){
	    protectionPlanSearchReq.setCoveredSerialNumber(deviceSerialNumber);
	    logger.info("Invoking the Search protection Plan Service with deviceSerialNumber : " + deviceSerialNumber);
	}

	try{
	    protectionPlanSearchResponse = getHubService().searchProtectionPlan(protectionPlanSearchReq);
	    if (protectionPlanSearchResponse != null && protectionPlanSearchResponse.getSystemStatus() != null){
		logger.info("Service Plan Response : System Status Code : "
			+ protectionPlanSearchResponse.getSystemStatus().getStatusCode()
			+ " and System Status Message : "
			+ protectionPlanSearchResponse.getSystemStatus().getStatusMessage());
	    }

	    List<ProtectionPlanType> allServicePlans = new ArrayList<ProtectionPlanType>();

	    if (protectionPlanSearchResponse != null
		    && protectionPlanSearchResponse.getSystemStatus().getStatusCode() == 0){
		allServicePlans = protectionPlanSearchResponse.getProtectionPlan();
		logger.info("Number of Service Plans Retrieved : " + allServicePlans.size());
		servicePlanInfoList = interpretAllServicePlans(allServicePlans);
	    }else{
		// TODO Set the correct error code and the sourceSystem Name
		throw new IseBusinessException(protectionPlanSearchResponse.getSystemStatus().getStatusMessage());
	    }

	}catch(SOAPFaultException sfe){
	    String errorMessage = sfe.getFault().getFaultCode() + " : " + sfe.getFault().getFaultString();
	    logger.info(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "In the SearchProtectionPlan SOAPFaultException : " + errorMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errorMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " ServiceException - SearchProtectionPlan : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + " Exception - SearchProtectionPlan", e);
	    if (protectionPlanSearchResponse != null && protectionPlanSearchResponse.getSystemStatus() != null){
		if (protectionPlanSearchResponse.getSystemStatus().getStatusCode() == 1001
			|| protectionPlanSearchResponse.getSystemStatus().getStatusCode() == 2001){
		    return servicePlanInfoList;
		}
	    }
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}

	return servicePlanInfoList;

    }

    /**
     * Gets the GSP details based on the GSP PlanID from the Protection Plan
     * Foundation System.
     */
    @Override
    public ServicePlan getServicePlanDetails(ServicePlan servicePlanInfo) throws ServiceException, IseBusinessException {

	logger.info("In the getServicePlanDetails method");

	ProtectionPlanDetailRequestType planDetailsRequest = new ProtectionPlanDetailRequestType();
	ProtectionPlanDetailResponseType planDetailsResponse = null;

	ProtectionPlanIDType planIdType = new ProtectionPlanIDType();
	planIdType.setValue(servicePlanInfo.getPlanNumber());
	planDetailsRequest.setProtectionPlanID(planIdType);
	planDetailsRequest.setActivityLogLimit(0);

	try{
	    logger
		    .info("Invoking the Protection Plan Details Service with protectionPlanId : "
			    + planIdType.getValue());
	    planDetailsResponse = getHubService().getProtectionPlanDetail(planDetailsRequest);

	    if (planDetailsResponse.getSystemStatus() != null){
		logger.info("Plans Details Response : System Status Code : "
			+ planDetailsResponse.getSystemStatus().getStatusCode() + " and System Status Message : "
			+ planDetailsResponse.getSystemStatus().getStatusMessage());
	    }

	    List<ProtectionPlanDetailType> protectionPlansDetailsList = new ArrayList<ProtectionPlanDetailType>();

	    if (planDetailsResponse.getSystemStatus().getStatusCode() == 0){
		protectionPlansDetailsList = planDetailsResponse.getProtectionPlanDetail();
		logger.info("Number of Plans Details Retrieved : " + protectionPlansDetailsList.size());
		StringBuffer fourPartKey = new StringBuffer("");
		List<ServicePlanTransaction> gspTransactionsList = interpretServicePlanDetails(fourPartKey,
			protectionPlansDetailsList);
		servicePlanInfo.setServicePlanTransactions(gspTransactionsList);
		servicePlanInfo.setTransactionId(fourPartKey.toString());
	    }else{
		logger.error("getServicePlanDetails FAILED: "
			+ planDetailsResponse.getSystemStatus().getStatusMessage());
		throw new IseBusinessException(planDetailsResponse.getSystemStatus().getStatusMessage());
	    }

	}catch(SOAPFaultException sfe){
	    String errorMessage = sfe.getFault().getFaultCode() + " : " + sfe.getFault().getFaultString();
	    logger.info(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "In the getServicePlanDetails SOAPFaultException : " + errorMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errorMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " ServiceException - getServicePlanDetails : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + " Exception - getServicePlanDetails", e);
	    if (planDetailsResponse.getSystemStatus() != null){
		if (planDetailsResponse.getSystemStatus().getStatusCode() == 1001
			|| planDetailsResponse.getSystemStatus().getStatusCode() == 2001){
		    throw new IseBusinessException(planDetailsResponse.getSystemStatus().getStatusMessage());
		}
	    }
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}

	return servicePlanInfo;

    }

    /**
     * Interprets the Response obtained from the SearchProtectionPlans and map
     * it to ISE's List<ProtectionPlanInfo>
     */
    private List<ServicePlan> interpretAllServicePlans(List<ProtectionPlanType> allProtectionPlans)
	    throws ServiceException {

	List<ServicePlan> interpretedPlanInfoList = new ArrayList<ServicePlan>();

	for(ProtectionPlanType eachProtectionPlan: allProtectionPlans){

	    try{
		// If the Protection plan is of BuyBack
		if (ProtectionPlanTypeEnum.BB == eachProtectionPlan.getType()){
		    BuybackPlan buyBackPlanInfo = new BuybackPlan();

		    if (eachProtectionPlan.getID() != null
			    && StringUtils.isNotBlank(eachProtectionPlan.getID().getValue())){
			buyBackPlanInfo.setPlanNumber(eachProtectionPlan.getID().getValue());
		    }

		    if (eachProtectionPlan.getContract() != null){
			ContractType planContract = eachProtectionPlan.getContract();

			if (StringUtils.isNotBlank(planContract.getSKU())){
			    buyBackPlanInfo.setSku(planContract.getSKU());
			}
			if (StringUtils.isNotBlank(planContract.getSKUDescription())){
			    buyBackPlanInfo.setDescription(planContract.getSKUDescription());
			}
			if (planContract.getStatus() != null){
			    buyBackPlanInfo.setPlanStatus(planContract.getStatus().toString());
			}
			buyBackPlanInfo.setPlanEffectiveDate(Util.toUtilDate(planContract.getEffectiveDate()));
			buyBackPlanInfo.setPlanExpiryDate(Util.toUtilDate(planContract.getExpirationDate()));
		    }

		    if (eachProtectionPlan.getProduct() != null && !eachProtectionPlan.getProduct().isEmpty()){
			List<Product> coveredProductList = new ArrayList<Product>();
			for(ContractProductType product: eachProtectionPlan.getProduct()){
			    Product coveredProduct = new Product();
			    if (StringUtils.isNotBlank(product.getSKU())){
				coveredProduct.setSku(product.getSKU());
			    }
			    if (StringUtils.isNotBlank(product.getSKUDescription())){
				coveredProduct.setDescription(product.getSKUDescription());
			    }
			    if (StringUtils.isNotBlank(product.getModel())){
				coveredProduct.setModelNumber(product.getModel());
			    }
			    if (StringUtils.isNotBlank(product.getSerialNumber())){
				coveredProduct.setSerialNumber(product.getSerialNumber());
			    }
			    coveredProduct.setPurchaseDate(Util.toUtilDate(product.getPurchaseDate()));

			    coveredProductList.add(coveredProduct);
			}
			// As buyback applies to only 1 device.
			buyBackPlanInfo.setProduct(coveredProductList.get(0));
		    }

		    if (eachProtectionPlan.getOwner() != null){
			String ownerName = "";
			if (StringUtils.isNotBlank(eachProtectionPlan.getOwner().getName().getFirstName())){
			    ownerName = eachProtectionPlan.getOwner().getName().getFirstName() + " ";
			}
			if (StringUtils.isNotBlank(eachProtectionPlan.getOwner().getName().getLastName())){
			    ownerName = ownerName + eachProtectionPlan.getOwner().getName().getLastName();
			}
			buyBackPlanInfo.setPlanOwnerName(ownerName);
		    }

		    // buyBackPlanInfo.setBuyBackPrice(new BigDecimal(20));

		    interpretedPlanInfoList.add(buyBackPlanInfo);

		}else // If the Protection plan is of BuyBack
		{
		    ProtectionPlan protectionPlanInfo = new ProtectionPlan();

		    if (eachProtectionPlan.getType() != null){
			protectionPlanInfo.setPlanType(eachProtectionPlan.getType().toString());
		    }else{
			protectionPlanInfo.setPlanType("");
		    }

		    if (eachProtectionPlan.getID() != null
			    && StringUtils.isNotBlank(eachProtectionPlan.getID().getValue())){
			protectionPlanInfo.setPlanNumber(eachProtectionPlan.getID().getValue());
		    }

		    if (eachProtectionPlan.getContract() != null){
			ContractType planContract = eachProtectionPlan.getContract();

			if (StringUtils.isNotBlank(planContract.getSKU())){
			    protectionPlanInfo.setSku(planContract.getSKU());
			}
			if (StringUtils.isNotBlank(planContract.getSKUDescription())){
			    protectionPlanInfo.setDescription(planContract.getSKUDescription());
			}
			if (planContract.getStatus() != null){
			    protectionPlanInfo.setPlanStatus(planContract.getStatus().toString());
			}
			protectionPlanInfo.setPlanEffectiveDate(Util.toUtilDate(planContract.getEffectiveDate()));
			protectionPlanInfo.setPlanExpiryDate(Util.toUtilDate(planContract.getExpirationDate()));
		    }

		    if (eachProtectionPlan.getProduct() != null && !eachProtectionPlan.getProduct().isEmpty()){
			List<Product> coveredProductList = new ArrayList<Product>();
			for(ContractProductType product: eachProtectionPlan.getProduct()){
			    Device coveredProduct = new Device();
			    if (StringUtils.isNotBlank(product.getSKU())){
				coveredProduct.setSku(product.getSKU());
			    }
			    if (StringUtils.isNotBlank(product.getSKUDescription())){
				coveredProduct.setDescription(product.getSKUDescription());
			    }
			    if (StringUtils.isNotBlank(product.getModel())){
				coveredProduct.setModelNumber(product.getModel());
			    }
			    if (StringUtils.isNotBlank(product.getSerialNumber())){
				coveredProduct.setSerialNumber(product.getSerialNumber());
			    }
			    coveredProduct.setPurchaseDate(Util.toUtilDate(product.getPurchaseDate()));

			    coveredProductList.add(coveredProduct);
			}
			protectionPlanInfo.setCoveredProductList(coveredProductList);
		    }

		    if (eachProtectionPlan.getOwner() != null){
			String ownerName = "";
			if (StringUtils.isNotBlank(eachProtectionPlan.getOwner().getName().getFirstName())){
			    ownerName = eachProtectionPlan.getOwner().getName().getFirstName() + " ";
			}
			if (StringUtils.isNotBlank(eachProtectionPlan.getOwner().getName().getLastName())){
			    ownerName = ownerName + eachProtectionPlan.getOwner().getName().getLastName();
			}
			protectionPlanInfo.setPlanOwnerName(ownerName);
		    }

		    interpretedPlanInfoList.add(protectionPlanInfo);
		}

	    }catch(Exception e){
		throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	    }
	}

	return interpretedPlanInfoList;
    }

    /**
     * Interprets the Response obtained from the ProtectionPlanDetails and map
     * it to ISE ProtectionPlanDetail Object.
     */
    private List<ServicePlanTransaction> interpretServicePlanDetails(StringBuffer fourPartKey,
	    List<ProtectionPlanDetailType> protectionPlansDetailsList) {

	List<ServicePlanTransaction> gspTransactionsList = new ArrayList<ServicePlanTransaction>();

	for(ProtectionPlanDetailType eachPlanDetails: protectionPlansDetailsList){
	    if (eachPlanDetails.getTransaction() != null && !eachPlanDetails.getTransaction().isEmpty()){

		for(TransactionType gspTransFromHub: eachPlanDetails.getTransaction()){

		    ServicePlanTransaction gspTransactionInfo = new ServicePlanTransaction();
		    if (gspTransFromHub.getLineCode() != null){
			gspTransactionInfo.setGspTransType(gspTransFromHub.getLineCode().toString());
		    }

		    if (gspTransFromHub.getTransactionKey() != null){

			if (StringUtils.isNotBlank(gspTransFromHub.getTransactionKey().getRetailStoreID())){
			    gspTransactionInfo.setStoreNum(gspTransFromHub.getTransactionKey().getRetailStoreID());
			    if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(gspTransactionInfo
				    .getGspTransType())){
				fourPartKey.append(gspTransactionInfo.getStoreNum() + "^");
			    }
			}
			if (gspTransFromHub.getTransactionKey().getBusinessDayDate() != null){
			    gspTransactionInfo.setPurchaseDate(Util.toUtilDate(gspTransFromHub.getTransactionKey()
				    .getBusinessDayDate()));
			    if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(gspTransactionInfo
				    .getGspTransType())){
				fourPartKey.append(gspTransFromHub.getTransactionKey().getBusinessDayDate().toString()
					.substring(0, 4));
				fourPartKey.append(gspTransFromHub.getTransactionKey().getBusinessDayDate().toString()
					.substring(5, 7));
				fourPartKey.append(gspTransFromHub.getTransactionKey().getBusinessDayDate().toString()
					.substring(8, 10));
				fourPartKey.append("^");
			    }
			}
			if (StringUtils.isNotBlank(gspTransFromHub.getTransactionKey().getWorkstationID())){
			    gspTransactionInfo.setRegisterNum(gspTransFromHub.getTransactionKey().getWorkstationID());
			    if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(gspTransactionInfo
				    .getGspTransType())){
				fourPartKey.append(gspTransactionInfo.getRegisterNum() + "^");
			    }
			}
			if (StringUtils.isNotBlank(gspTransFromHub.getTransactionKey().getRegisterTransactionNumber()
				.toString())){
			    gspTransactionInfo.setTransactionNum(gspTransFromHub.getTransactionKey()
				    .getRegisterTransactionNumber().toString());
			    if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(gspTransactionInfo
				    .getGspTransType())){
				fourPartKey.append(gspTransactionInfo.getTransactionNum());
			    }
			}
			if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(gspTransactionInfo
				.getGspTransType())){
			    gspTransactionInfo.setMonthlyPayment(eachPlanDetails.getMonthlyPayment());
			}

		    }
		    if (gspTransFromHub.getActualPricePaid() != null){
			gspTransactionInfo.setPurchasePrice(gspTransFromHub.getActualPricePaid());
		    }

		    gspTransactionsList.add(gspTransactionInfo);
		}
	    }
	    break;

	}
	return gspTransactionsList;
    }

    @Override
    public boolean updateProtectionPlan(GSPPlan gspPlan) throws ServiceException, IseBusinessException {
	logger.debug(">> Update protection plan with protectionPlanID : " + gspPlan.getProtectionPlanId());
	boolean successFlag = false;
	ProtectionPlanUpdateResponseType protectionPlanUpdateResponse = new ProtectionPlanUpdateResponseType();
	ProtectionPlanUpdateRequestType protectionPlanUpdateRequest = getProtectionPlanUpdateRequestFromGSPPlan(gspPlan);

	try{
	    protectionPlanUpdateResponse = getHubService().updateProtectionPlan(protectionPlanUpdateRequest);

	    if (protectionPlanUpdateResponse.getSystemStatus() != null){
		if (protectionPlanUpdateResponse.getSystemStatus().getStatusCode() == 0){
		    successFlag = true;
		}else if (protectionPlanUpdateResponse.getSystemStatus().getStatusCode() > 0){
		    logger.debug(">> Update protection plan - Response Code : "
			    + protectionPlanUpdateResponse.getSystemStatus().getStatusCode());
		    logger.debug(">> Update protection plan - Response Message : "
			    + protectionPlanUpdateResponse.getSystemStatus().getStatusMessage());
		    throw new IseBusinessException(protectionPlanUpdateResponse.getSystemStatus().getStatusMessage());
		}
	    }

	}catch(SOAPFaultException sfe){
	    String errorMessage = sfe.getFault().getFaultCode() + " : " + sfe.getFault().getFaultString();
	    logger.info("In the updateProtectionPlan SOAPFaultException : " + errorMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errorMessage);
	}catch(IseBusinessException ex){
	    throw ex;
	}catch(ServiceException se){
	    logger.error(" ServiceException - updateProtectionPlan : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Exception e){
	    logger.error(" Exception - updateProtectionPlan", e);
	    if (protectionPlanUpdateResponse.getSystemStatus() != null){
		if (protectionPlanUpdateResponse.getSystemStatus().getStatusCode() > 0){
		    logger.debug(">> Update protection plan - Response Code : "
			    + protectionPlanUpdateResponse.getSystemStatus().getStatusCode());
		    logger.debug(">> Update protection plan - Response Message : "
			    + protectionPlanUpdateResponse.getSystemStatus().getStatusMessage());
		    throw new IseBusinessException(protectionPlanUpdateResponse.getSystemStatus().getStatusMessage());
		}
	    }
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}

	return successFlag;

    }

    private ProtectionPlanUpdateRequestType getProtectionPlanUpdateRequestFromGSPPlan(GSPPlan gspPlan)
	    throws ServiceException {

	ProtectionPlanUpdateRequestType protectionPlanUpdateRequest = new ProtectionPlanUpdateRequestType();

	ProtectionPlanDetailType planDetailType = new ProtectionPlanDetailType();
	planDetailType.setType(ProtectionPlanTypeEnum.PSP);

	ContractType cType = new ContractType();
	ProtectionPlanIDType idType = new ProtectionPlanIDType();
	CodeDescriptionType cdType = new CodeDescriptionType();
	RequestMetadataType metadata = new RequestMetadataType();

	idType.setValue(gspPlan.getProtectionPlanId());
	planDetailType.setID(idType);

	cType.setStatus(ContractStatusEnum.INACTIVE);

	cType.setExpirationDate(Util.toXmlGregorianCalendar(gspPlan.getExpirationDate()));
	planDetailType.setContract(cType);

	cdType.setValue("BEAST Mobile Hardware Change/Upgrade");
	planDetailType.setStatusReasonCode(cdType);

	metadata.setSourceID(getDrpPropertiesService().getProperty("APPLICATION_NAME_EC"));
	metadata.setProgramID(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	metadata.setUserID(gspPlan.getModifiedBy());
	metadata.setRequestTimeStamp(Util.toXmlGregorianCalendar(new Date()));

	protectionPlanUpdateRequest.setMetaData(metadata);
	protectionPlanUpdateRequest.setProtectionPlan(planDetailType);

	return protectionPlanUpdateRequest;
    }

    @Override
    public List<ServicePlan> searchServicePlanByIMEI(String deviceSerialNum) throws ServiceException,
	    IseBusinessException {
	return searchServicePlan(null, null, deviceSerialNum);
    }
}
