package com.bestbuy.bbym.ise.drp.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * Interface for Customer DAO
 * 
 * @author Sridhar Savaram
 */
public interface CustomerDao {

    public Customer getCustomer(String storeId, String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public void persistCustomer(Customer customer) throws DataAccessException, RuntimeException;

    public void updateCustomer(Customer customer) throws DataAccessException, RuntimeException;
}
