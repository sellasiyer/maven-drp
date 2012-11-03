package com.bestbuy.bbym.ise.drp.service;

import com.bestbuy.bbym.ise.drp.domain.*;

import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * CustomerService.java Responsible for creating, retrieving and updating the
 * Customer object(DATA_XFER - table)
 * 
 * @author a904002
 */
public interface CustomerService {

    /**
     * Add new customer This will add, create and assign new datasharingkey
     */
    public String addCustomer(Customer customer) throws ServiceException;

    /**
     * Update bbymCustomer
     */
    public void updateBbyCustomer(BbyAccount bbyCustomerAccount) throws ServiceException;

    /**
     * Add new customer This will add, * create and assign new datasharingkey
     */
    public String addBbyCustomer(BbyAccount bbyCustomerAccount) throws ServiceException;

    /**
     * Get the customer - including(if available) 1. bbym_account, 2.
     * carrier_account & 3. recSheetSummary Sets transferFlag as true
     */
    public Customer getCustomer(String storeId, String dataSharingKey) throws ServiceException, IseBusinessException;

    /**
     * Update Customer record. It will update BBYM_ACCT record if already
     * present otherwise inserts new. It will update CARR_ACCT record if already
     * present otherwise inserts new It will update REC_SHEET_SUMM record if
     * already present otherwise inserts new
     */
    public void updateCustomer(Customer customer) throws ServiceException;

    /**
     * Get the BbyAccount
     */
    public BbyAccount getBbyCustomer(String dataSharingKey) throws ServiceException;

    public void createCustomer(com.bestbuy.bbym.ise.domain.Customer customer, DrpUser user) throws ServiceException;

    public void addRecSheetSummary(com.bestbuy.bbym.ise.domain.Customer customer, Recommendation recommendation,
	    DrpUser user) throws ServiceException;
}
