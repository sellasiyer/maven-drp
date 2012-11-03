package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.sao.CustomerProfileSao;
import com.bestbuy.bbym.ise.drp.sao.EcTransactionLookupSao;
import com.bestbuy.bbym.ise.drp.sao.EjSao;
import com.bestbuy.bbym.ise.drp.sao.HubProtectionPlanSao;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a218635
 */
@Service("customerDataService")
public class CustomerDataServiceImpl implements CustomerDataService {

    private static Logger logger = LoggerFactory.getLogger(CustomerDataServiceImpl.class);
    @Autowired
    private HubProtectionPlanSao hubProtectionPlanSao;
    @Autowired
    private CustomerProfileSao customerProfileSao;
    @Autowired
    private EcTransactionLookupSao ecTransactionLookupSao;
    @Autowired
    private EjSao ejSao;
    @Autowired
    private DrpPropertyServiceImpl drpPropertyService;

    public void setHubProtectionPlanSao(HubProtectionPlanSao hubProtectionPlanSao) {
	this.hubProtectionPlanSao = hubProtectionPlanSao;
    }

    public void setCustomerProfileSao(CustomerProfileSao customerProfileSao) {
	this.customerProfileSao = customerProfileSao;
    }

    public void setEcTransactionLookupSao(EcTransactionLookupSao ecTransactionLookupSao) {
	this.ecTransactionLookupSao = ecTransactionLookupSao;
    }

    public void setDrpPropertyServiceImpl(DrpPropertyServiceImpl drpPropertyService) {
	this.drpPropertyService = drpPropertyService;
    }

    @Override
    public List<Customer> getBBYCustomerProfile(Customer bbyCustomer, CustomerSearchCriteria searchCriteria,
	    DrpUser drpUser) throws ServiceException, IseBusinessException {

	return customerProfileSao.searchBbyCustomer(bbyCustomer, searchCriteria, drpUser);
    }

    /**
     * Searches all the GSPs associated with the Customer based on his
     * customerId from the Protection Plan Foundation System.
     */
    @Override
    public List<ServicePlan> getAllServicePlans(Customer bbyCustomer) throws ServiceException, IseBusinessException {

	return hubProtectionPlanSao.searchServicePlan(bbyCustomer);
    }

    /**
     * Gets the GSP details based on the GSP PlanID from the Protection Plan
     * Foundation System.
     */
    @Override
    public ServicePlan getServicePlanDetails(ServicePlan servicePlanInfo) throws ServiceException, IseBusinessException {

	ServicePlan servicePlansDetails = servicePlanInfo;
	try{
	    logger.debug("Before getServicePlanDetails - servicePlanInfo:" + servicePlanInfo);
	    servicePlanInfo = hubProtectionPlanSao.getServicePlanDetails(servicePlanInfo);
	    logger.debug("After getServicePlanDetails - servicePlanInfo:" + servicePlanInfo);
	    if (servicePlanInfo.getServicePlanTransactions() != null)
		servicePlansDetails.setServicePlanTransactions(servicePlanInfo.getServicePlanTransactions());
	}catch(IseBusinessException ibe){
	    logger.error("Business Exception - CustomerDataServiceImpl: getServicePlanDetails : " + ibe.getMessage());
	}catch(ServiceException se){
	    logger.error("Service Exception - CustomerDataServiceImpl: getServicePlanDetails : " + se.getMessage());
	}
	return servicePlansDetails;
    }

    /**
     * Gets all the Mobile purchases from EC system.
     */
    @Override
    public List<Product> getMobilePurchaseHistory(Customer bbyCustomer, DrpUser drpUser, Date startDate, Date endDate)
	    throws ServiceException, IseBusinessException {
	return ecTransactionLookupSao.getAllTransactions(bbyCustomer, drpUser, startDate, endDate, true);
    }

    /**
     * Gets all the Mobile and Non-Mobile purchases from EC system.
     */
    @Override
    public List<Product> getAllPurchaseHistory(Customer bbyCustomer, DrpUser drpUser, Date startDate, Date endDate)
	    throws ServiceException, IseBusinessException {
	return ecTransactionLookupSao.getAllTransactions(bbyCustomer, drpUser, startDate, endDate, false);
    }

    public BigDecimal getBuyBackValue(ServicePlan servicePlan, String productSku, Date productPurchaseDate,
	    DrpUser drpUser) throws ServiceException, IseBusinessException {
	servicePlan = hubProtectionPlanSao.getServicePlanDetails(servicePlan);
	logger.info("About to orchestrate the hub Data with EC look up by fourPartKey ");
	BigDecimal retailPrice = ecTransactionLookupSao.getRetailPriceByFourPartKey(servicePlan.getTransactionId(),
		productSku, drpUser);
	if (retailPrice != null){
	    logger.info("About to invoke computeBuyBackValue with retailsPrice : " + retailPrice
		    + " and productPurchaseDate : " + productPurchaseDate);
	    if (productPurchaseDate != null){
		return computeBuyBackValue(retailPrice, productPurchaseDate);
	    }else{
		List<ServicePlanTransaction> gspTransList = servicePlan.getServicePlanTransactions();
		for(ServicePlanTransaction gspTransaction: gspTransList){
		    if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(gspTransaction.getGspTransType())){
			productPurchaseDate = gspTransaction.getPurchaseDate();
			break;
		    }
		}
		return computeBuyBackValue(retailPrice, productPurchaseDate);
	    }
	}
	return null;
    }

    /**
     * Computes the Buyback value of a device based on the duration and Actual
     * Purchase Price.
     */
    private BigDecimal computeBuyBackValue(BigDecimal retailPrice, Date productPurchaseDate) {

	BigDecimal buybackValue = null;

	if (retailPrice != null && productPurchaseDate != null){
	    Calendar currentDate = Calendar.getInstance();
	    Calendar effectiveDate = getEffectiveDate(productPurchaseDate, 31);
	    Calendar sixMonthLimit = getComputedDateLimits(productPurchaseDate, 6); // 6Months+31
	    Calendar twelveMonthLimit = getComputedDateLimits(productPurchaseDate, 12); // 12Months+31
	    Calendar eighteenMonthLimit = getComputedDateLimits(productPurchaseDate, 18); // 18Months+183+31
	    Calendar twentyFourMonthLimit = getComputedDateLimits(productPurchaseDate, 24); // 24Months+31

	    if (currentDate.after(twentyFourMonthLimit) || currentDate.before(effectiveDate)
		    || currentDate.equals(effectiveDate))
		buybackValue = new BigDecimal(0);
	    if ((currentDate.after(eighteenMonthLimit) && currentDate.before(twentyFourMonthLimit))
		    || (currentDate.equals(twentyFourMonthLimit)))
		buybackValue = getCurrentWorth(retailPrice, 20);
	    if ((currentDate.after(twelveMonthLimit) && currentDate.before(eighteenMonthLimit))
		    || (currentDate.equals(eighteenMonthLimit)))
		buybackValue = getCurrentWorth(retailPrice, 30);
	    if ((currentDate.after(sixMonthLimit) && currentDate.before(twelveMonthLimit))
		    || (currentDate.equals(twelveMonthLimit)))
		buybackValue = getCurrentWorth(retailPrice, 40);
	    if ((currentDate.after(effectiveDate) && currentDate.before(sixMonthLimit))
		    || (currentDate.equals(sixMonthLimit)))
		buybackValue = getCurrentWorth(retailPrice, 50);
	}

	return buybackValue;
    }

    private Calendar getEffectiveDate(Date inputDate, int numberOfDays) {
	Calendar computedDate = Calendar.getInstance();
	computedDate.setTime(inputDate);
	computedDate.add(Calendar.DATE, numberOfDays);
	return computedDate;
    }

    private Calendar getComputedDateLimits(Date inputDate, int numberOfMonths) {
	Calendar computedDate = Calendar.getInstance();
	computedDate.setTime(inputDate);
	computedDate.add(Calendar.DATE, 31);
	computedDate.add(Calendar.MONTH, numberOfMonths);
	return computedDate;
    }

    private BigDecimal getCurrentWorth(BigDecimal retailPrice, int percent) {
	float rtlPrice = retailPrice.floatValue();
	BigDecimal buybackValue = new BigDecimal((rtlPrice * percent) / 100);
	return buybackValue;
    }

    @Override
    public ProtectionPlan getProtectionPlan(String planNumber) throws ServiceException, IseBusinessException {
	List<ServicePlan> servicePlanList = hubProtectionPlanSao.searchServicePlanByPlanId(planNumber);
	if (servicePlanList == null || servicePlanList.size() < 1){
	    return null;
	}
	if ((servicePlanList.get(0) != null) && (servicePlanList.get(0) instanceof ProtectionPlan)){
	    return (ProtectionPlan) servicePlanList.get(0);
	}
	return null;
    }

    @Override
    public ProtectionPlan findProtectionPlan(String deviceSerialNum) throws ServiceException, IseBusinessException {
	logger.info("findProtectionPlan - deviceSerialNum:" + deviceSerialNum);

	if (StringUtils.isEmpty(deviceSerialNum)){
	    return null;
	}
	List<ServicePlan> servicePlanList = hubProtectionPlanSao.searchServicePlanByIMEI(deviceSerialNum);
	if (servicePlanList == null || servicePlanList.size() < 1 || servicePlanList.size() > 1){
	    return null;
	}
	if ((servicePlanList.get(0) != null) && (servicePlanList.get(0) instanceof ProtectionPlan)){
	    return (ProtectionPlan) servicePlanList.get(0);
	}
	return null;
    }

    @Override
    public String getElectronicJournal(String storeId, String registerId, Date transactionDate,
	    String transactionNumber, DrpUser drpUser) throws ServiceException, IseBusinessException {
	return ejSao.getElectronicJournal(storeId, registerId, transactionDate, transactionNumber, drpUser);
    }

}
