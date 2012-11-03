package com.bestbuy.bbym.ise.drp.rest.util;

import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.bestbuy.bbym.beast.portal.cancelgsp.response.GspPlanType;
import com.bestbuy.bbym.beast.portal.customerinfo.response.BbyAccountType;
import com.bestbuy.bbym.beast.portal.customerinfo.response.CarrierAccountType;
import com.bestbuy.bbym.beast.portal.customerinfo.response.Customer;
import com.bestbuy.bbym.beast.portal.datasummary.response.DataTransferSummaryType;
import com.bestbuy.bbym.beast.portal.gspcancel.write.request.CustomerRequest;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.response.EntitlementBenefitType;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.response.GetMobileDeviceResponse;
import com.bestbuy.bbym.ise.common.AddressType;
import com.bestbuy.bbym.ise.common.CarrierType;
import com.bestbuy.bbym.ise.common.RecommendationType;
import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.drp.domain.RetExchDevcEntitlsModel;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

/**
 * 
 * @author a904002
 */
public class JAXBDomainMapper {

    /**
     * Map "DataTransferSummary" domain object to "DataTransferSummaryType" jaxb
     * Object
     */
    public static DataTransferSummaryType getDataTransferType(DataTransferSummary dataTransferSummary) {
	DataTransferSummaryType dtType = new DataTransferSummaryType();

	dtType.setDataSharingKey(dataTransferSummary.getDataSharingKey());
	dtType.setDetailsUrl(dataTransferSummary.getDetailsUrl());
	dtType.setFirstName(dataTransferSummary.getFirstName());
	dtType.setLastName(dataTransferSummary.getLastName());
	dtType.setPhoneNumber(dataTransferSummary.getPhoneNumber());
	dtType.setTransferFlag(dataTransferSummary.isTransferFlag());

	return dtType;
    }

    /**
     * Map "Customer" domain object to "Customer" jaxb Object
     */
    public static Customer getCustomer(com.bestbuy.bbym.ise.drp.domain.Customer dCustomer) throws ServiceException {
	Customer restCustomer = new Customer();
	if (dCustomer.getBbyAccount() != null){
	    restCustomer.setBbyAccount(getRestBbyAccount(dCustomer.getBbyAccount()));
	}
	if (dCustomer.getCarrierAccount() != null){
	    restCustomer.setCarrierAccount(getRestCarrierAccount(dCustomer.getCarrierAccount()));
	}
	if (dCustomer.getRecSheetSummary() != null){
	    restCustomer.setRecommendation(getRestRecSheetSummary(dCustomer.getRecSheetSummary()));
	}
	if (dCustomer.getDataSharingKey() != null){
	    restCustomer.setDataSharingKey(dCustomer.getDataSharingKey());
	}
	if (dCustomer.getCapTransactionId() != null){
	    restCustomer.setCapTransactionId(dCustomer.getCapTransactionId());
	}
	if (dCustomer.getSource() != null){
	    restCustomer.setSource(dCustomer.getSource());
	}
	if (dCustomer.getStoreId() != null){
	    restCustomer.setStoreId(dCustomer.getStoreId());
	}

	if (dCustomer.getModifiedBy() != null){
	    restCustomer.setLastModifiedBy(dCustomer.getModifiedBy());
	}
	if (dCustomer.getModifiedOn() != null){
	    restCustomer.setLastModifiedDate(Util.toXmlGregorianCalendar(dCustomer.getModifiedOn()));
	}
	if (dCustomer.getCreatedBy() != null){
	    restCustomer.setCreatedBy(dCustomer.getCreatedBy());
	}
	if (dCustomer.getCreatedOn() != null){
	    restCustomer.setCreatedDate(Util.toXmlGregorianCalendar(dCustomer.getCreatedOn()).toString());
	}

	restCustomer.setTransferFlag(dCustomer.getTransferFlag());

	return restCustomer;
    }

    /**
     * Map "BbyAccount" domain object to "BbyAccountType" jaxb Object
     */
    public static BbyAccountType getRestBbyAccount(BbyAccount bbyAccout) {
	BbyAccountType bbType = new BbyAccountType();
	boolean isObjectNull = true;
	if (getRestAddressType(bbyAccout.getAddress()) != null){
	    bbType.setAddress(getRestAddressType(bbyAccout.getAddress()));
	    isObjectNull = false;
	}
	if (bbyAccout.getEccaId() != null){
	    bbType.setBbyCustomerId(bbyAccout.getEccaId());
	    isObjectNull = false;
	}
	if (bbyAccout.getEmail() != null){
	    bbType.setEmailId(bbyAccout.getEmail());
	    isObjectNull = false;
	}
	if (bbyAccout.getFirstName() != null){
	    bbType.setFirstName(bbyAccout.getFirstName());
	    isObjectNull = false;
	}
	if (bbyAccout.getLastName() != null){
	    bbType.setLastName(bbyAccout.getLastName());
	    isObjectNull = false;
	}
	if (bbyAccout.getPhoneNumber() != null){
	    bbType.setPhoneNumber(bbyAccout.getPhoneNumber());
	    isObjectNull = false;
	}
	if (bbyAccout.getRewardZoneNo() != null){
	    bbType.setRewardZoneNbr(bbyAccout.getRewardZoneNo());
	    isObjectNull = false;
	}

	return isObjectNull?null:bbType;

    }

    /**
     * Map "CarrierAccount" domain object to "CarrierAccountType" jaxb Object
     */
    public static CarrierAccountType getRestCarrierAccount(CarrierAccount carrierAccount) {
	CarrierAccountType cType = new CarrierAccountType();
	boolean isObjectNull = true;
	if (getRestAddressType(carrierAccount.getAddress()) != null){
	    cType.setAddress(getRestAddressType(carrierAccount.getAddress()));
	    isObjectNull = false;
	}
	if (carrierAccount.getCarrierAccountNumber() != null){
	    cType.setCarrierAccountNumber(carrierAccount.getCarrierAccountNumber());
	    isObjectNull = false;
	}
	if (carrierAccount.getEmail() != null){
	    cType.setEmailId(carrierAccount.getEmail());
	    isObjectNull = false;
	}
	if (carrierAccount.getFirstName() != null){
	    cType.setFirstName(carrierAccount.getFirstName());
	    isObjectNull = false;
	}
	if (carrierAccount.getLastName() != null){
	    cType.setLastName(carrierAccount.getLastName());
	    isObjectNull = false;
	}
	if (carrierAccount.getPhoneNumber() != null){
	    cType.setPhoneNumber(carrierAccount.getPhoneNumber());
	    isObjectNull = false;
	}
	if (carrierAccount.getCoverageZip() != null){
	    cType.setCoverageZip(carrierAccount.getCoverageZip());
	    isObjectNull = false;
	}
	if (carrierAccount.getCarrier() != null){
	    cType.setCarrier(getCarrier(carrierAccount.getCarrier()));
	    isObjectNull = false;
	}

	return isObjectNull?null:cType;
    }

    private static CarrierType getCarrier(Carrier carrier) {
	switch (carrier) {
	    case ATT:
		return CarrierType.ATT;
	    case SPRINT:
		return CarrierType.SPR;
	    case TMOBILE:
		return CarrierType.TMO;
	    case VERIZON:
		return CarrierType.VEZ;
	}
	return null;
    }

    /**
     * Map "Address" domain object to "AddressType" jaxb Object
     */
    public static AddressType getRestAddressType(Address address) {
	AddressType aType = new AddressType();
	boolean isObjectNull = true;
	if (address.getCity() != null){
	    aType.setCity(address.getCity());
	    isObjectNull = false;
	}
	if (address.getCountryCode() != null){
	    aType.setCountryCode(address.getCountryCode());
	    isObjectNull = false;
	}
	if (address.getState() != null){
	    aType.setStateCode(address.getState());
	    isObjectNull = false;
	}
	if (address.getAddressline1() != null){
	    aType.setStreetAddress1(address.getAddressline1());
	    isObjectNull = false;
	}
	if (address.getAddressline2() != null){
	    aType.setStreetAddress2(address.getAddressline2());
	    isObjectNull = false;
	}
	if (address.getUrbanizationCode() != null){
	    aType.setUrbanizationCode(address.getUrbanizationCode());
	    isObjectNull = false;
	}
	if (address.getZip() != null){
	    aType.setZipCode(address.getZip());
	    isObjectNull = false;
	}

	return isObjectNull?null:aType;
    }

    /**
     * Map "RecSheetSummary" domain object to "RecommendationType" jaxb Object
     */
    public static RecommendationType getRestRecSheetSummary(RecSheetSummary recSheetSummary) {
	RecommendationType recType = new RecommendationType();
	boolean isObjectNull = true;
	if (recSheetSummary.getBuyBackPlanInfo() != null){
	    recType.setBuybackInfo(recSheetSummary.getBuyBackPlanInfo());
	    isObjectNull = false;
	}
	if (recSheetSummary.getDeviceInfo() != null){
	    recType.setDeviceInfo(recSheetSummary.getDeviceInfo());
	    isObjectNull = false;
	}
	if (recSheetSummary.getGspPlanInfo() != null){
	    recType.setGspInfo(recSheetSummary.getGspPlanInfo());
	    isObjectNull = false;
	}
	if (recSheetSummary.getPlanInfo() != null){
	    recType.setPlanInfo(recSheetSummary.getPlanInfo());
	    isObjectNull = false;
	}

	return isObjectNull?null:recType;
    }

    /**
     * Map "RecSheetSummary" domain object to "RecommendationType" jaxb Object
     */
    public static GspPlanType getGspPlanType(GSPPlan gspPlan) throws ServiceException {

	GspPlanType gspType = new GspPlanType();
	if (gspPlan.getBillingType() != null){
	    gspType.setBillingType(gspPlan.getBillingType());
	}

	if (gspPlan.getBusinessDate() != null){
	    gspType.setBusinessDate(Util.toXmlGregorianCalendar(gspPlan.getBusinessDate()));
	}

	gspType.setCancel(gspPlan.isCancel());
	if (gspPlan.getContractSKU() != null){
	    gspType.setContractSku(gspPlan.getContractSKU());
	}
	if (gspPlan.getContractSKUDescription() != null){
	    gspType.setContractSkuDescription(gspPlan.getContractSKUDescription());
	}
	if (gspPlan.getProtectionPlanId() != null){
	    gspType.setProtectionPlanId(gspPlan.getProtectionPlanId());
	}
	if (gspPlan.getRegisterTransactionNumber() != null){
	    gspType.setRegisterTransactionNumber(gspPlan.getRegisterTransactionNumber());
	}
	if (gspPlan.getStoreId() != null){
	    gspType.setRetailStoreId(gspPlan.getStoreId());
	}
	if (gspPlan.getWorkstationId() != null){
	    gspType.setWorkstationId(gspPlan.getWorkstationId());
	}

	return gspType;
    }

    /**
     * Map "AddressType" JAXB object to "Address" Domain Object
     */
    public static Address getAddressFromRestAddressType(AddressType adType, String userId) {
	Address address = new Address();
	address.setAddressId(UUID.randomUUID().toString());
	if (adType.getStreetAddress1() != null){
	    address.setAddressline1(adType.getStreetAddress1());
	}
	if (adType.getStreetAddress2() != null){
	    address.setAddressline2(adType.getStreetAddress2());
	}
	if (adType.getCity() != null){
	    address.setCity(adType.getCity());
	}
	if (adType.getCountryCode() != null){
	    address.setCountryCode(adType.getCountryCode());
	}
	if (adType.getStateCode() != null){
	    address.setState(adType.getStateCode());
	}
	if (adType.getUrbanizationCode() != null){
	    address.setUrbanizationCode(adType.getUrbanizationCode());
	}
	if (adType.getZipCode() != null){
	    address.setZip(adType.getZipCode());
	}

	if (!StringUtils.isBlank(userId)){
	    address.setCreatedBy(userId);
	    address.setModifiedBy(userId);
	}

	return address;

    }

    /**
     * Map "CustomerRequest" (GspCancel) JAXB Object to "BbyAccount" Domain
     * object
     */
    public static BbyAccount getBbyAccount(CustomerRequest account, String storeId, String userId) {
	BbyAccount bbyAccount = new BbyAccount(UUID.randomUUID().toString());

	if (account.getAddress() != null){
	    Address address = JAXBDomainMapper.getAddressFromRestAddressType(account.getAddress(), userId);
	    bbyAccount.setAddress(address);
	}
	if (account.getBbyCustomerId() != null){
	    bbyAccount.setEccaId(account.getBbyCustomerId());
	}
	if (account.getEmailId() != null){
	    bbyAccount.setEmail(account.getEmailId());
	}
	if (account.getFirstName() != null){
	    bbyAccount.setFirstName(account.getFirstName());
	}
	if (account.getLastName() != null){
	    bbyAccount.setLastName(account.getLastName());
	}
	if (account.getPhoneNumber() != null){
	    bbyAccount.setPhoneNumber(account.getPhoneNumber());
	}

	bbyAccount.setCreatedBy(userId);
	bbyAccount.setModifiedBy(userId);

	return bbyAccount;
    }

    public static GetMobileDeviceResponse getGetMobileDeviceResponse(RetExchDevcEntitlsModel retExchDevcEntitlsModel) {
	GetMobileDeviceResponse getMobileDeviceResponse = new GetMobileDeviceResponse();
	getMobileDeviceResponse.setContractId(retExchDevcEntitlsModel.getProtectionPlanId());
	getMobileDeviceResponse.setCreatedBy(retExchDevcEntitlsModel.getCreated_by());
	getMobileDeviceResponse.setCreatedDate(Util.toXmlGregorianCalendar(retExchDevcEntitlsModel.getCreated_on()));
	getMobileDeviceResponse.setDefectiveStatus(retExchDevcEntitlsModel.isDefectiveStatus());
	getMobileDeviceResponse.setTransactionKey(retExchDevcEntitlsModel.getTransaction_key());
	EntitlementBenefitType entitlementBenefitType = new EntitlementBenefitType();
	entitlementBenefitType.setCoverageBenefit(retExchDevcEntitlsModel.getCoverageBenefit());
	entitlementBenefitType.setCoverageBenefitPromptCode(retExchDevcEntitlsModel.getCoverageBenefitPromptCode());
	entitlementBenefitType.setCoverageVehicle(retExchDevcEntitlsModel.getCoverageVehicle());
	entitlementBenefitType.setType(retExchDevcEntitlsModel.getType());
	getMobileDeviceResponse.setEntitlementBenefit(entitlementBenefitType);
	return getMobileDeviceResponse;
    }
}
