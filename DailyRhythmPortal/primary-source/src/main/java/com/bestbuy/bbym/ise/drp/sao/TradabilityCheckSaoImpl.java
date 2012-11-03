package com.bestbuy.bbym.ise.drp.sao;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.HeaderType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.LineItemListType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.LineItemType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.RuleSetType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionBaseType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckRequestType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckResponseType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckResultType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransationSourceDetailType;
import com.bestbuy.bbym.sales.transactioncheck.fields.v1.CarrierBaseType;
import com.bestbuy.bbym.sales.transactioncheck.fields.v1.DeviceType;
import com.bestbuy.tsh.businessservices.proxy.tradeincheck.TransactionCheckFault;
import com.bestbuy.tsh.common.datatype.v1.CodeType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.NameType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;
import com.bestbuy.tsh.common.metadata.components.v1.InternationalBusinessHierarchyType;
import com.bestbuy.tsh.sales.common.components.v1.RequestMetaDataExtensionType;

/**
 * TradabilityCheckSaoImpl
 * 
 * @author a904002
 */
@Service("tradabilityCheckSao")
public class TradabilityCheckSaoImpl extends AbstractSao implements TradabilityCheckSao {

    private static Logger logger = LoggerFactory.getLogger(TradabilityCheckSaoImpl.class);
    private static final String SERVICE = "TRADABILITY CHECK ";
    private static final String DEFAULT_FAULT_STRING = "The tradability check is currently down. Please try again. If the problem persists, call the Help Desk to report the issue.";

    @Override
    public boolean checkForTradability(BeastDevice tradeInDevice, DrpUser drpUser, String storeId)
	    throws ServiceException, IseBusinessException {
	logger.info("TRADE IN : In the checkForTradability method");
	if (drpPropertiesService.getProperty("TRADABILITY_FAKE", "false").equalsIgnoreCase("true")){
	    return(drpPropertiesService.getProperty("TRADABILITY_FAKE_RESULT", "0").equals("1"));
	}
	try{
	    TransactionCheckRequestType tradabilityCheckRequest;
	    tradabilityCheckRequest = prepareTradabilityCheckRequest(tradeInDevice, drpUser, storeId);
	    TransactionCheckResponseType tradabilityCheckResponse = null;

	    tradabilityCheckResponse = getTradabilityCheckService().transactionCheck(tradabilityCheckRequest);
	    if (tradabilityCheckResponse != null && tradabilityCheckResponse.getStatus() != null){

		String StatusCode = tradabilityCheckResponse.getStatus().getCode().getValue();
		String statusMessage = tradabilityCheckResponse.getStatus().getDescription();
		logger.info("Tradability Check Response  : Status Code : " + StatusCode + " and Status Description : "
			+ statusMessage);

		if (StatusCode.equalsIgnoreCase("TSH-SRV-000000")){
		    TransactionCheckResultType result = tradabilityCheckResponse.getTransactionCheckResult();
		    logger.info("Tradability check Result is " + result.getTransactionCheckResultHeader().getStatus());
		    if (result.getTransactionCheckResultHeader().getStatus().equalsIgnoreCase(
			    DrpConstants.TRADABILITY_APPROVED)){
			logger.info("TRADE IN : Tradability check succeeded");
			return true;
		    }

		}else{
		    logger.info("TRADE IN : Tradability check Failed");
		    return false;
		}
	    }
	}catch(EmptyResultDataAccessException era){
	    String faultString = drpPropertiesService.getProperty("TRADABILITY_FAULT_MSG", DEFAULT_FAULT_STRING);
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + "TRADE IN : TradabilityCheck Service Exception",
		    era);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, faultString);
	}catch(DataAccessException da){
	    String faultString = drpPropertiesService.getProperty("TRADABILITY_FAULT_MSG", DEFAULT_FAULT_STRING);
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + "TRADE IN : TradabilityCheck Service Exception : "
		    + da.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, faultString);
	}catch(TransactionCheckFault tcf){
	    String faultString = drpPropertiesService.getProperty("TRADABILITY_FAULT_MSG", DEFAULT_FAULT_STRING);
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + "TRADE IN : TradabilityCheck Service Exception : "
		    + tcf.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, faultString);
	}catch(Throwable t){
	    handleException(SERVICE + " - check for tradability", t);
	}

	return false;

    }

    private TransactionCheckRequestType prepareTradabilityCheckRequest(BeastDevice tradeInDevice, DrpUser drpUser,
	    String storeId) throws EmptyResultDataAccessException, DataAccessException, RuntimeException,
	    ServiceException {
	TransactionCheckRequestType returnTradabilityCheckRequest = new TransactionCheckRequestType();
	returnTradabilityCheckRequest.setRequestMetaData(getRequestMetaDataExtensionType(drpUser));
	returnTradabilityCheckRequest
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	returnTradabilityCheckRequest.setRuleSet(getRuleSet());
	returnTradabilityCheckRequest.setTransactionCheck(getTransactionCheck(tradeInDevice, drpUser, storeId));

	return returnTradabilityCheckRequest;
    }

    private RequestMetaDataExtensionType getRequestMetaDataExtensionType(DrpUser drpUser) {
	RequestMetaDataExtensionType tradabilityCheckMetaData = new RequestMetaDataExtensionType();
	try{
	    tradabilityCheckMetaData.setSourceID(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	    tradabilityCheckMetaData.setUserID(drpUser.getUserId());
	    tradabilityCheckMetaData.setProgramID(getDrpPropertiesService().getProperty("APPLICATION_NAME"));
	}catch(ServiceException e){
	    logger.error("Error setting metadata values", e);
	}
	tradabilityCheckMetaData.setRequestTimeStamp(Util.toXmlGregorianCalendar(new Date()));
	return tradabilityCheckMetaData;
    }

    private RuleSetType getRuleSet() {
	RuleSetType returnRuleSetType = new RuleSetType();
	NameType nameType = new NameType();
	nameType.setValue(DrpConstants.TSH_RESULTSET_NAME);
	returnRuleSetType.setName(nameType);
	return returnRuleSetType;
    }

    private TransactionCheckType getTransactionCheck(BeastDevice tradeInDevice, DrpUser drpUser, String storeId)
	    throws EmptyResultDataAccessException, DataAccessException, RuntimeException {
	TransactionCheckType returnTransactionCheckType = new TransactionCheckType();
	returnTransactionCheckType.setTransactionDetails(getTransactionBaseType(tradeInDevice, drpUser, storeId));
	return returnTransactionCheckType;
    }

    private TransactionBaseType getTransactionBaseType(BeastDevice tradeInDevice, DrpUser drpUser, String storeId)
	    throws EmptyResultDataAccessException, DataAccessException, RuntimeException {
	TransactionBaseType returnTransactionBaseType = new TransactionBaseType();
	returnTransactionBaseType.setHeader(getHeaderType(tradeInDevice, drpUser, storeId));
	returnTransactionBaseType.setLineItemList(getLineItemList(tradeInDevice));
	return returnTransactionBaseType;
    }

    private LineItemListType getLineItemList(BeastDevice tradeInDevice) {
	LineItemListType returnLineItemListType = new LineItemListType();
	LineItemType lineItem = new LineItemType();
	DeviceType device = new DeviceType();
	device.setTechnologyCode(tradeInDevice.getHandsetIdentifierType());
	IdentifierType identifier = new IdentifierType();
	identifier.setValue(tradeInDevice.getHandsetIdentifier());
	device.getID().add(identifier);
	lineItem.setDevice(device);
	returnLineItemListType.getLineItem().add(lineItem);
	return returnLineItemListType;
    }

    private HeaderType getHeaderType(BeastDevice tradeInDevice, DrpUser drpUser, String storeId)
	    throws EmptyResultDataAccessException, DataAccessException, RuntimeException {
	HeaderType returnHeaderType = new HeaderType();
	if (drpUser.getUserId() != null || storeId != null){
	    TransationSourceDetailType transationSourceDetailType = new TransationSourceDetailType();
	    if (drpUser.getUserId() != null){
		transationSourceDetailType.setRepID(drpUser.getUserId());
	    }
	    if (storeId != null){
		transationSourceDetailType.setLocationID(storeId);
	    }
	    returnHeaderType.setTransationSourceDetail(transationSourceDetailType);
	}
	if (tradeInDevice.getBeastTransactionId() != null){
	    IdentifierType id = new IdentifierType();
	    id.setValue(tradeInDevice.getBeastTransactionId());
	    id.setType(DrpConstants.TRANSACTION_ID);
	    returnHeaderType.getID().add(id);
	}

	if (StringUtils.isNotBlank(tradeInDevice.getCarrier())){
	    CarrierBaseType carrier = new CarrierBaseType();
	    CodeType code = new CodeType();
	    code.setName(tradeInDevice.getCarrier());
	    carrier.setCode(code);
	    returnHeaderType.setCarrier(carrier);
	}
	returnHeaderType.setUserArea(new UserAreaType());
	return returnHeaderType;
    }

}
