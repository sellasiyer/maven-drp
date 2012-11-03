package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.BuybackPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

@Service("customerDataService2")
public class DummyCustomerDataService implements CustomerDataService {

    @Override
    public List<Customer> getBBYCustomerProfile(Customer bbyCustomer, CustomerSearchCriteria searchCriteria,
	    DrpUser drpUser) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	if (bbyCustomer == null || searchCriteria == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	List<Customer> list = new ArrayList<Customer>();
	int listSize = DummyData.getRandomIndex(6);

	switch (searchCriteria) {
	    case EMAIL:
		if (bbyCustomer.getEmail() == null){
		    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
		}
		break;
	    case PHONE_NUMBER:
		if (bbyCustomer.getContactPhone() == null){
		    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
		}
		break;
	    case FN_LN_ZIP:
		if (bbyCustomer.getFirstName() == null || bbyCustomer.getLastName() == null
			|| bbyCustomer.getAddress() == null || bbyCustomer.getAddress().getZipcode() == null){
		    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
		}
		break;
	}

	for(int i = 0; i < listSize; i++){
	    Customer cust = new Customer();
	    cust.setFirstName(DummyData.getFirstName());
	    cust.setLastName(DummyData.getLastName());
	    cust.setContactPhone(DummyData.getPhone());
	    cust.setEmail(DummyData.getEmail());
	    cust.setAddress(new Address());
	    cust.getAddress().setAddressLine1(DummyData.getAddress());
	    cust.getAddress().setCity(DummyData.getCity());
	    cust.getAddress().setState(DummyData.getState());
	    cust.getAddress().setZipcode(DummyData.getZipCode());
	    cust.setRewardZoneId(DummyData.getSku());

	    switch (searchCriteria) {
		case EMAIL:
		    cust.setEmail(bbyCustomer.getEmail());
		    break;
		case PHONE_NUMBER:
		    cust.setContactPhone(bbyCustomer.getContactPhone());
		    break;
		case FN_LN_ZIP:
		    cust.setFirstName(bbyCustomer.getFirstName());
		    cust.setLastName(bbyCustomer.getLastName());
		    cust.getAddress().setZipcode(bbyCustomer.getAddress().getZipcode());
		    break;
	    }
	    list.add(cust);
	}
	return list;
    }

    // private static int getAllServicePlansCount = 0;

    @Override
    public List<ServicePlan> getAllServicePlans(Customer bbyCustomer) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	// DummyData.throwServiceException(0, 3, getAllServicePlansCount++);
	DummyData.throwServiceException(40);
	DummyData.throwIseBusinessException(40);
	if (bbyCustomer == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	List<ServicePlan> servicePlanList = new ArrayList<ServicePlan>();
	int listSize = DummyData.getRandomIndex(20);

	for(int i = 0; i < listSize; i++){
	    int planType = DummyData.getRandomIndex(10);
	    // ProtectionPlan
	    if (planType < 6){
		ProtectionPlan protectionPlan = new ProtectionPlan();
		protectionPlan.setPlanOwnerName(bbyCustomer.getFirstName() + " " + bbyCustomer.getLastName());
		protectionPlan.setPlanType("GSBTP");
		protectionPlan.setSku(DummyData.getSku());
		protectionPlan.setPlanStatus(DummyData.getPlanStatus());
		protectionPlan.setDescription(DummyData.getPlanDescription());
		protectionPlan.setPlanNumber(DummyData.getPlanNumber());
		protectionPlan.setPlanExpiryDate(DummyData.getDate());
		protectionPlan.setPlanEffectiveDate(DummyData.getDate());
		int productListSize = DummyData.getRandomIndex(3) + 1;
		List<Product> productList = new ArrayList<Product>();
		for(int j = 0; j < productListSize; j++){
		    Product product = new Product();
		    product.setSerialNumber(DummyData.getSerialNumber());
		    product.setSku(DummyData.getSku());
		    product.setDescription(DummyData.getPhoneOrNonPhoneDescription());
		    product.setModelNumber(DummyData.getModelNumber());
		    product.setPurchaseDate(DummyData.getDate());
		    productList.add(product);
		}
		protectionPlan.setCoveredProductList(productList);
		servicePlanList.add(protectionPlan);
		// BuybackPlan
	    }else{
		BuybackPlan buybackPlan = new BuybackPlan();
		buybackPlan.setPlanOwnerName(bbyCustomer.getFirstName() + " " + bbyCustomer.getLastName());
		buybackPlan.setSku(DummyData.getSku());
		buybackPlan.setPlanStatus(DummyData.getPlanStatus());
		buybackPlan.setDescription(DummyData.getPlanDescription());
		buybackPlan.setPlanNumber(DummyData.getPlanNumber());
		buybackPlan.setPlanExpiryDate(DummyData.getDate());
		buybackPlan.setBuyBackPrice(null);
		Product product = new Product();
		product.setSerialNumber(DummyData.getSerialNumber());
		product.setSku(DummyData.getSku());
		product.setDescription(DummyData.getPhoneOrNonPhoneDescription());
		product.setModelNumber(DummyData.getModelNumber());
		product.setPurchaseDate(DummyData.getDate());
		buybackPlan.setProduct(product);
		servicePlanList.add(buybackPlan);
	    }
	}
	return servicePlanList;
    }

    @Override
    public ServicePlan getServicePlanDetails(ServicePlan servicePlan) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	if (servicePlan == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	List<ServicePlanTransaction> transList = new ArrayList<ServicePlanTransaction>();
	int listSize = DummyData.getRandomIndex(5);
	for(int i = 0; i < listSize; i++){
	    ServicePlanTransaction trans = new ServicePlanTransaction();
	    trans.setStoreNum(DummyData.getStoreNumber());
	    trans.setRegisterNum(DummyData.getRegisterNumber());
	    trans.setTransactionNum(DummyData.getZipCode());
	    trans.setPurchaseDate(DummyData.getDate());
	    trans.setPurchasePrice(DummyData.getPrice());
	    if (i == 0){
		trans.setGspTransType("S");
	    }else{
		trans.setGspTransType(getNonSaleGspTransType());
	    }
	    transList.add(trans);
	}
	servicePlan.setServicePlanTransactions(transList);
	return servicePlan;
    }

    private String getNonSaleGspTransType() {
	String transType;
	for(;;){
	    transType = DummyData.getGspTransType();
	    if (!"S".equals(transType)){
		return transType;
	    }
	}
    }

    @Override
    public List<Product> getMobilePurchaseHistory(Customer bbyCustomer, DrpUser drpUser, Date startDate, Date endDate)
	    throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(50);
	DummyData.throwIseBusinessException(50);
	if (bbyCustomer == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	List<Product> productList = new ArrayList<Product>();
	int listSize = DummyData.getRandomIndex(6);

	for(int i = 0; i < listSize; i++){
	    Product product = new Product();
	    product.setTransactionType(DummyData.getTransactionType());
	    product.setTransactionId(DummyData.getFourPartKey());
	    product.setSku(DummyData.getSku());
	    product.setDescription(DummyData.getHardware());
	    product.setSerialNumber(DummyData.getSerialNumber());
	    product.setRetailPrice(DummyData.getPrice());
	    product.setPurchasePrice(DummyData.getPrice());
	    product.setPurchaseDate(DummyData.getDate());
	    productList.add(product);
	}
	return productList;
    }

    // private static int getAllPurchaseHistoryCount = 0;

    @Override
    public List<Product> getAllPurchaseHistory(Customer bbyCustomer, DrpUser drpUser, Date startDate, Date endDate)
	    throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	// DummyData.throwServiceException(0, 3, getAllPurchaseHistoryCount++);
	DummyData.throwServiceException(40);
	DummyData.throwIseBusinessException(40);
	if (bbyCustomer == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}

	List<Product> productList = new ArrayList<Product>();
	int listSize = DummyData.getRandomIndex(10);

	for(int i = 0; i < listSize; i++){
	    Product product = new Product();
	    product.setTransactionType(DummyData.getTransactionType());
	    product.setTransactionId(DummyData.getFourPartKey());
	    product.setSku(DummyData.getSku());
	    product.setSerialNumber(DummyData.getSerialNumber());
	    product.setDescription(DummyData.getPhoneOrNonPhoneDescription());
	    product.setRetailPrice(DummyData.getPrice());
	    product.setPurchasePrice(DummyData.getPrice());
	    product.setPurchaseDate(DummyData.getDate());
	    productList.add(product);
	}
	return productList;
    }

    @Override
    public BigDecimal getBuyBackValue(ServicePlan servicePlan, String productSku, Date productPurchaseDate,
	    DrpUser drpUser) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(20);
	DummyData.throwIseBusinessException(20);
	return DummyData.getBuyBackPrice();
    }

    @Override
    public ProtectionPlan getProtectionPlan(String planNumber) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	if (planNumber == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	if (DummyData.getRandomIndex(10) < 6){
	    ProtectionPlan protectionPlan = new ProtectionPlan();
	    protectionPlan.setPlanType("GSBTP");
	    protectionPlan.setSku(DummyData.getSku());
	    protectionPlan.setPlanStatus(DummyData.getPlanStatus());
	    protectionPlan.setDescription(DummyData.getPlanDescription());
	    protectionPlan.setPlanNumber(planNumber);
	    protectionPlan.setPlanExpiryDate(DummyData.getDate());
	    protectionPlan.setPlanEffectiveDate(DummyData.getDate());
	    return protectionPlan;
	}
	return null;
    }

    @Override
    public ProtectionPlan findProtectionPlan(String deviceSerialNum) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(30);
	DummyData.throwIseBusinessException(30);
	if (deviceSerialNum == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	if (DummyData.getRandomIndex(10) < 6){
	    ProtectionPlan protectionPlan = new ProtectionPlan();
	    protectionPlan.setPlanType("GSBTP");
	    protectionPlan.setSku(DummyData.getSku());
	    protectionPlan.setPlanStatus(DummyData.getPlanStatus());
	    protectionPlan.setDescription(DummyData.getPlanDescription());
	    protectionPlan.setPlanNumber(DummyData.getPlanNumber());
	    protectionPlan.setPlanExpiryDate(DummyData.getDate());
	    protectionPlan.setPlanEffectiveDate(DummyData.getDate());
	    return protectionPlan;
	}
	return null;
    }

    @Override
    public String getElectronicJournal(String storeId, String registerId, Date transactionDate,
	    String transactionNumber, DrpUser drpUser) throws ServiceException, IseBusinessException {
	DummyData.sleep(2);
	DummyData.throwServiceException(20);
	DummyData.throwIseBusinessException(20);
	if (storeId == null || registerId == null || transactionDate == null || transactionNumber == null
		|| drpUser == null){
	    throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull, "Unexpected null");
	}
	if (DummyData.getRandomIndex(10) < 2){
	    return "";
	}
	StringBuilder sb = new StringBuilder();
	sb.append("Keep your receipt!\n");
	sb.append("storeId=" + storeId + "\n");
	sb.append("registerId=" + registerId + "\n");
	sb.append("transactionDate=" + Util.toString(transactionDate, null) + "\n");
	sb.append("transactionNumber=" + transactionNumber + "\n");
	return sb.toString();
    }

}
