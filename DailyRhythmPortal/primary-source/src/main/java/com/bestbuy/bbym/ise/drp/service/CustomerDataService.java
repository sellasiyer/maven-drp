package com.bestbuy.bbym.ise.drp.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a218635
 */
public interface CustomerDataService {

    public List<Customer> getBBYCustomerProfile(Customer bbyCustomer, CustomerSearchCriteria searchCriteria,
	    DrpUser drpUser) throws ServiceException, IseBusinessException;

    public List<ServicePlan> getAllServicePlans(Customer bbyCustomer) throws ServiceException, IseBusinessException;

    public ServicePlan getServicePlanDetails(ServicePlan servicePlan) throws ServiceException, IseBusinessException;

    public List<Product> getMobilePurchaseHistory(Customer bbyCustomer, DrpUser drpUser, Date startDate, Date endDate)
	    throws ServiceException, IseBusinessException;

    public List<Product> getAllPurchaseHistory(Customer bbyCustomer, DrpUser drpUser, Date startDate, Date endDate)
	    throws ServiceException, IseBusinessException;

    public BigDecimal getBuyBackValue(ServicePlan servicePlan, String productSku, Date productPurchaseDate,
	    DrpUser drpUser) throws ServiceException, IseBusinessException;

    public ProtectionPlan getProtectionPlan(String planNumber) throws ServiceException, IseBusinessException;

    public ProtectionPlan findProtectionPlan(String deviceSerialNum) throws ServiceException, IseBusinessException;

    /**
     * Gets the electronic journal.
     * 
     * @param storeId
     *            the store that the transaction was processed in
     * @param registerId
     *            the register that the transaction was processed on
     * @param transactionDate
     *            the date that the transaction was performed on
     * @param transactionNumber
     *            the transaction number
     * @param the
     *            user requesting the electronic journal
     * @return the electronic journal receipt; {@code null} if no receipt was
     *         found
     */
    public String getElectronicJournal(String storeId, String registerId, Date transactionDate,
	    String transactionNumber, DrpUser drpUser) throws ServiceException, IseBusinessException;

}
