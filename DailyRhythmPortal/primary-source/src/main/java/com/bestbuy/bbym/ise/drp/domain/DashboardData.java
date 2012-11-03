package com.bestbuy.bbym.ise.drp.domain;

import java.util.List;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;

public class DashboardData {

    private CustomersDashboardCarrierData carrierData;
    private List<Customer> searchCustomers;
    private CustomerSearchCriteria customerSearchCriteria;

    public CustomersDashboardCarrierData getCarrierData() {
	return carrierData;
    }

    public void setCarrierData(CustomersDashboardCarrierData carrierData) {
	this.carrierData = carrierData;
    }

    public List<Customer> getSearchCustomers() {
	return searchCustomers;
    }

    public void setSearchCustomers(List<Customer> searchCustomers) {
	this.searchCustomers = searchCustomers;
    }

    public CustomerSearchCriteria getCustomerSearchCriteria() {
	return customerSearchCriteria;
    }

    public void setCustomerSearchCriteria(CustomerSearchCriteria customerSearchCriteria) {
	this.customerSearchCriteria = customerSearchCriteria;
    }

}
