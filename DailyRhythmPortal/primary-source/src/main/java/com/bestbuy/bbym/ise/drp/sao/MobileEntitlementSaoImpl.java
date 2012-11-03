package com.bestbuy.bbym.ise.drp.sao;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Entitlement;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlementRequest;
import com.bestbuy.bbym.ise.drp.domain.VendorWarranty;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientmec.ContractInfoType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementBenefitType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementChecFault;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckRequestType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckResponseType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckResultType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementlistDetailsType;
import com.bestbuy.bbym.ise.iseclientmec.EtfInfoDetailsType;
import com.bestbuy.bbym.ise.iseclientmec.InternationalBusinessHierarchyType;
import com.bestbuy.bbym.ise.iseclientmec.RequestMetaDataType;
import com.bestbuy.bbym.ise.iseclientmec.TransactionKeyInfoDetailsType;
import com.bestbuy.bbym.ise.iseclientmec.VendorWarrantyDetailsType;
import com.bestbuy.bbym.ise.util.Util;

/**
 * @author a175157
 */
@Service("mobileEntitlementSao")
public class MobileEntitlementSaoImpl extends AbstractSao implements MobileEntitlementSao {

    private static Logger logger = LoggerFactory.getLogger(MobileEntitlementSaoImpl.class);
    private static final String SERVICE = "MEC ";

    /**
     * Calls TSH and TSH then calls 3 apps
     * <ul>
     * <li>ERA to get Entitlements,</li>
     * <li>CPC to get Carrier return Rules</li>
     * <li>CAP for tradeability check</li>
     * </ul>
     */
    @Override
    public MobileEntitlement getMobileEntitlements(MobileEntitlementRequest mobEntRequest, DrpUser drpUser,
	    String iseTransactionId) throws ServiceException, IseBusinessException {
	logger.info("EntitlementCheckService service - Begin");
	MobileEntitlement mobileEntitlement = null;
	try{
	    EntitlementCheckRequestType entitlementCheckRequest = prepareEntitlementCheckRequest(mobEntRequest,
		    drpUser, iseTransactionId);
	    EntitlementCheckResponseType entitlementCheckResponse = getMECService().getEntitlementDetails(
		    entitlementCheckRequest);
	    if (entitlementCheckResponse.getStatus() != null){
		String msg = entitlementCheckResponse.getStatus().getDescription();
		if (entitlementCheckResponse.getStatus().getCode() != null
			&& "101".equals(entitlementCheckResponse.getStatus().getCode().getValue())){
		    msg = "Unfortunately, an error has occurred.Please proceed to POS to check customer options. Please be aware the customer may be charge an early termination fee up to $350 if they cancel their contract.";
		}
		throw new IseBusinessException((StringUtils.isEmpty(msg)?"Entitlement Service Failed":msg));
	    }
	    mobileEntitlement = parseEntitlementCheckResponse(entitlementCheckResponse);
	}catch(EntitlementChecFault e){
	    throw new IseBusinessException(e.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - getMobileEntitlements", t);
	}
	logger.info("EntitlementCheckService service - End");
	return mobileEntitlement;
    }

    private MobileEntitlement parseEntitlementCheckResponse(EntitlementCheckResponseType entitlementCheckResponse) {
	logger.info("Parsing EntitlementCheckResponseType");
	MobileEntitlement mobEntitlement = new MobileEntitlement();
	if (entitlementCheckResponse != null && entitlementCheckResponse.getEntitlementCheckResult() != null){
	    EntitlementCheckResultType entitlementCheckResult = entitlementCheckResponse.getEntitlementCheckResult();
	    EntitlementlistDetailsType entitlementlistDetails = entitlementCheckResult.getEntitlementlistDetails();

	    if (entitlementlistDetails != null){
		logger.debug("Getting Entitlements  List");
		for(EntitlementBenefitType entitlementBenefit: entitlementlistDetails.getEntitlementBenefitTypes()){
		    Entitlement entitlement = new Entitlement();
		    entitlement.setCoverageBenefit(entitlementBenefit.getCoverageBenefit());
		    entitlement.setCoverageBenefitPromptCode(entitlementBenefit.getCoverageBenefitPromptCode());
		    entitlement.setCoverageVehicle(entitlementBenefit.getCoverageVehicle());
		    entitlement.setEntitlementType(entitlementBenefit.getEntitlementType());
		    mobEntitlement.getEntitlementList().add(entitlement);
		}
	    }
	    VendorWarrantyDetailsType vendorWarranyDetails = entitlementCheckResult.getVendorWarrantyDetails();
	    if (vendorWarranyDetails != null){
		logger.debug("Getting VendorWanrranty");
		VendorWarranty vendorWarranty = new VendorWarranty();
		vendorWarranty.setContractCoverageEndDate(Util.toUtilDate(vendorWarranyDetails
			.getContractCoverageEndDate()));
		vendorWarranty.setContractCoverageStartDate(Util.toUtilDate(vendorWarranyDetails
			.getContractCoverageStartDate()));
		vendorWarranty.setCoverageStartDate(Util.toUtilDate(vendorWarranyDetails.getCoverageStartDate()));
		vendorWarranty.setCoverageEndDate(Util.toUtilDate(vendorWarranyDetails.getCoverageEndDate()));
		vendorWarranty.setDuplicateOrder(vendorWarranyDetails.isDuplicateOrder());
		vendorWarranty.setDuplicateOrderNumber(vendorWarranyDetails.getDuplicateOrderNumber());
		vendorWarranty.setErrorCode(vendorWarranyDetails.getErrorCode());
		vendorWarranty.setErrorDescription(vendorWarranyDetails.getErrorDescription());
		vendorWarranty.setLaborCovered(vendorWarranyDetails.getLaborCovered());
		vendorWarranty.setManufacturer(vendorWarranyDetails.getManufacturer());
		vendorWarranty.setPartsCovered(vendorWarranyDetails.getPartsCovered());
		vendorWarranty.setProductDescription(vendorWarranyDetails.getProductDescription());
		vendorWarranty.setRepeatService(vendorWarranyDetails.isRepeatService());
		vendorWarranty.setRepeatServiceOrderNumber(vendorWarranyDetails.getRepeatServiceOrderNumber());
		vendorWarranty.setValidSerialNumber(vendorWarranyDetails.isIsValidSerialNumber());
		vendorWarranty.setWarrantyStatus(vendorWarranyDetails.getWarrantyStatus());
		mobEntitlement.setVendorWarranty(vendorWarranty);
	    }
	    EtfInfoDetailsType etfInfoDetails = entitlementCheckResult.getEtfInfoDetails();
	    if (etfInfoDetails != null){
		logger.debug("Getting ETF");
		mobEntitlement.setDeviceETF(etfInfoDetails.getDeviceETF());
		mobEntitlement.setEtfDescription(etfInfoDetails.getEtfDescription());
		mobEntitlement.setEstimatedETF(etfInfoDetails.getEstimatedETF());
	    }
	    mobEntitlement.setTradeable(entitlementCheckResult.isTradeability());
	}
	return mobEntitlement;
    }

    private EntitlementCheckRequestType prepareEntitlementCheckRequest(MobileEntitlementRequest mobEntRequest,
	    DrpUser drpUser, String iseTransactionId) throws ServiceException {
	logger.info("Preparing EntitlementCheckRequest");
	EntitlementCheckRequestType entitlementCheckRequest = new EntitlementCheckRequestType();
	entitlementCheckRequest.setRequestMetaData(getRequestMetaData(drpUser, iseTransactionId));
	entitlementCheckRequest
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchyType()));
	entitlementCheckRequest.setDeviceID(mobEntRequest.getHandsetId());
	entitlementCheckRequest.setSKU(mobEntRequest.getSku());
	entitlementCheckRequest.setCarrier(mobEntRequest.getCarrier().getShortLabel());
	entitlementCheckRequest.setPurchaseDate(Util.toXmlGregorianCalendarNoTimePart(mobEntRequest.getPurchaseDate()));
	if (mobEntRequest.isContinueWithoutSerial()){
	    entitlementCheckRequest.setSerialNumber("999999999999");
	}else{
	    entitlementCheckRequest.setSerialNumber(mobEntRequest.getManufacturerSerialNumber());
	}
	entitlementCheckRequest.setRewardZoneTier(mobEntRequest.getRewardZoneTier());
	if (mobEntRequest.getProtectionPlanId() != null){
	    ContractInfoType contract = new ContractInfoType();
	    contract.setContractId(mobEntRequest.getProtectionPlanId());
	    contract.setContractType(mobEntRequest.getPlanType());
	    entitlementCheckRequest.setContractInfo(contract);
	}
	TransactionKeyInfoDetailsType transaction = new TransactionKeyInfoDetailsType();
	transaction.setSourceTransactionKey(mobEntRequest.getSourceTransactionKey());
	transaction.setTransactionKeyType(mobEntRequest.getTransactionKeyType());
	transaction.setTransactionSourceSystem(mobEntRequest.getTransactionSourceSystem());
	entitlementCheckRequest.setTransactionKeyInfo(transaction);
	entitlementCheckRequest.setIsDefective(mobEntRequest.isDefective());
	return entitlementCheckRequest;
    }

    private RequestMetaDataType getRequestMetaData(DrpUser drpUser, String iseTransactionId) throws ServiceException {
	RequestMetaDataType requestMetaData = new RequestMetaDataType();
	requestMetaData.setVersion(1);
	requestMetaData.setSourceID(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	requestMetaData.setUserID(drpUser.getUserId());
	requestMetaData.setMessageID(iseTransactionId);
	requestMetaData.setRequestTimeStamp(Util.toXmlGregorianCalendar(new Date()));
	return requestMetaData;
    }

}
