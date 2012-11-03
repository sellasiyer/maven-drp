package com.bestbuy.bbym.ise.drp.helpers;

import java.math.BigDecimal;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;

public class BeastObjectMapper {

    private static Logger logger = LoggerFactory.getLogger(BeastObjectMapper.class);

    private BeastObjectMapper() {
    }

    public static RecSheetSummary convertToRecSheetSummary(Recommendation rec) {
	RecSheetSummary recSheetSummary = new RecSheetSummary();
	recSheetSummary.setBuyBackPlanInfo(rec.getPreference(7L));

	recSheetSummary.setDeviceInfo(rec.getRecommendedDevice());
	recSheetSummary.setGspPlanInfo(rec.getPreference(6L));
	recSheetSummary.setPlanInfo(rec.getRecommendedSubscription());

	return recSheetSummary;
    }

    public static Customer convertToCustomer(BbyAccount account) {
	if (account == null){
	    return null;
	}
	Customer customer = new Customer();
	customer.setFirstName(account.getFirstName());
	customer.setLastName(account.getLastName());
	customer.setContactPhone(account.getPhoneNumber());
	customer.setEmail(account.getEmail());
	customer.setBbyCustomerId(account.getEccaId());
	customer.setCreatedBy(account.getCreatedBy());
	customer.setModifiedBy(account.getModifiedBy());
	customer.setAddress(convertToAddress(account.getAddress()));
	return customer;
    }

    public static BbyAccount convertToBbyAccount(Customer customer) {
	if (customer == null){
	    return null;
	}
	BbyAccount account = new BbyAccount();
	account.setFirstName(customer.getFirstName());
	account.setLastName(customer.getLastName());
	account.setPhoneNumber(customer.getContactPhone());
	account.setEmail(customer.getEmail());
	account.setEccaId(customer.getBbyCustomerId());
	account.setRewardZoneNo(customer.getRewardZoneId());
	account.setCreatedBy(customer.getCreatedBy());
	account.setModifiedBy(customer.getModifiedBy());

	account.setAddress(convertToBeastAddress(customer.getAddress()));
	return account;
    }

    public static Address convertToAddress(com.bestbuy.bbym.ise.drp.domain.Address beastAddress) {
	if (beastAddress == null){
	    return null;
	}
	Address address = new Address();
	address.setAddressId(beastAddress.getAddressId());
	address.setAddressLine1(beastAddress.getAddressline1());
	address.setAddressLine2(beastAddress.getAddressline2());
	address.setCity(beastAddress.getCity());
	address.setState(beastAddress.getState());
	address.setZipcode(beastAddress.getZip());
	address.setCreatedBy(beastAddress.getCreatedBy());
	address.setModifiedBy(beastAddress.getModifiedBy());
	return address;
    }

    public static CarrierAccount convertToCarrierAccount(Customer customer) {
	if (customer == null || customer.getFirstName() == null || customer.getLastName() == null){
	    return null;
	}
	CarrierAccount account = new CarrierAccount();
	account.setFirstName(customer.getFirstName());
	account.setLastName(customer.getLastName());
	account.setPhoneNumber(customer.getContactPhone());
	account.setEmail(customer.getEmail());
	account.setCarrierAccountNumber(customer.getAcctNumber());
	account.setCarrier(customer.getCarrier());
	account.setCreatedBy(customer.getCreatedBy());
	account.setModifiedBy(customer.getModifiedBy());
	account.setAddress(convertToBeastAddress(customer.getAddress()));
	if (customer.getAddress() != null && StringUtils.isNotBlank(customer.getAddress().getZipcode())){
	    account.setCoverageZip(customer.getAddress().getZipcode());
	}
	return account;
    }

    public static com.bestbuy.bbym.ise.drp.domain.Address convertToBeastAddress(Address address) {
	if (address == null){
	    return null;
	}
	com.bestbuy.bbym.ise.drp.domain.Address beastAddress = new com.bestbuy.bbym.ise.drp.domain.Address();
	beastAddress.setAddressId(address.getAddressId());
	beastAddress.setAddressline1(address.getAddressLine1());
	beastAddress.setAddressline2(address.getAddressLine2());
	beastAddress.setCity(address.getCity());
	beastAddress.setState(address.getState());
	beastAddress.setZip(address.getZipcode());
	beastAddress.setCreatedBy(address.getCreatedBy());
	beastAddress.setModifiedBy(address.getModifiedBy());
	return beastAddress;
    }

    public static com.bestbuy.bbym.ise.drp.domain.Customer convertToCustomer(Customer iseDomainCustomer) {
	if (iseDomainCustomer == null){
	    return null;
	}
	com.bestbuy.bbym.ise.drp.domain.Customer customer = new com.bestbuy.bbym.ise.drp.domain.Customer();

	if (!StringUtils.isBlank(iseDomainCustomer.getCapTransactionId())){
	    customer.setCapTransactionId(iseDomainCustomer.getCapTransactionId());
	}

	if (!StringUtils.isBlank(iseDomainCustomer.getAcctNumber())){

	    customer.setCarrierAccount(convertToCarrierAccount(iseDomainCustomer));

	}
	if (!StringUtils.isBlank(iseDomainCustomer.getBbyCustomerId())){

	    customer.setBbyAccount(convertToBbyAccount(iseDomainCustomer));

	}

	if (customer.getBbyAccount() == null && customer.getCarrierAccount() == null){

	    customer.setCarrierAccount(convertToCarrierAccount(iseDomainCustomer));
	}

	return customer;
    }

    public static GSPPlan convertToGSPPlan(ProtectionPlan protectionPlan) {
	if (protectionPlan == null){
	    return null;
	}
	GSPPlan gspPlan = new GSPPlan();
	BigDecimal monthlyPayment = null;
	for(ServicePlanTransaction servicePlanTransaction: protectionPlan.getServicePlanTransactions()){
	    if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(servicePlanTransaction.getGspTransType())){
		monthlyPayment = servicePlanTransaction.getMonthlyPayment();
	    }
	}
	logger.debug("MonthlyPayment:" + monthlyPayment);
	if (ServicePlan.PLAN_STATUS_ON_HOLD.equalsIgnoreCase(protectionPlan.getPlanStatus())){
	    gspPlan.setBillingType(GSPPlan.BILLING_TYPE_MONTHLY);
	    gspPlan.setMonthlyPayment("0");
	    logger.debug("GSP Plan Status:ONHOLD");
	}else if (monthlyPayment != null && monthlyPayment.compareTo(BigDecimal.ZERO) > 0){
	    gspPlan.setBillingType(GSPPlan.BILLING_TYPE_MONTHLY);
	    gspPlan.setMonthlyPayment(monthlyPayment.toEngineeringString());
	    logger.debug("GSP Plan Status:MONTHLY");
	}else{
	    gspPlan.setBillingType(GSPPlan.BILLING_TYPE_ONE_TIME);
	    gspPlan.setMonthlyPayment("0");
	    logger.debug("GSP Plan Status:ONE-TIME");
	}
	gspPlan.setContractSKU(protectionPlan.getSku());
	gspPlan.setContractSKUDescription(protectionPlan.getDescription());
	gspPlan.setExpirationDate(protectionPlan.getPlanExpiryDate());
	gspPlan.setPlanType(protectionPlan.getPlanType());

	ServicePlanTransaction saleTrans = null;
	if (protectionPlan.getServicePlanTransactions() != null
		&& !protectionPlan.getServicePlanTransactions().isEmpty()
		&& protectionPlan.getServicePlanTransactions().get(0) != null){
	    saleTrans = protectionPlan.getServicePlanTransactions().get(0);
	}
	if (saleTrans == null){
	    saleTrans = new ServicePlanTransaction();
	}
	gspPlan.setRegisterTransactionNumber(saleTrans.getTransactionNum());
	gspPlan.setStoreId(saleTrans.getStoreNum());
	gspPlan.setWorkstationId(saleTrans.getRegisterNum());
	gspPlan.setProtectionPlanId(protectionPlan.getPlanNumber());
	gspPlan.setBusinessDate(saleTrans.getPurchaseDate());
	gspPlan.setCreatedBy(protectionPlan.getCreatedBy());
	gspPlan.setModifiedBy(protectionPlan.getModifiedBy());
	return gspPlan;
    }
}
