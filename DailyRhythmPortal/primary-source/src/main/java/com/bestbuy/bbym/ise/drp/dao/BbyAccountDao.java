package com.bestbuy.bbym.ise.drp.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * Interface for the BbyAccount DB Object
 * 
 * @author Sridhar Savaram
 * @version 1.0
 * @since
 */
public interface BbyAccountDao {

    /**
     * 
     * @param bbyAccount
     */
    public void persistBbyAccount(BbyAccount bbyAccount) throws DataAccessException, RuntimeException;

    public void updateBbyAccount(BbyAccount bbyAccount) throws DataAccessException, RuntimeException;

    public BbyAccount getBbyAccount(String dataSharingKey) throws DataAccessException, EmptyResultDataAccessException,
	    RuntimeException;

    /**
     * get bby account only if DSK, StoreId, and employeeId match,
     * 
     * @param dataSharingKey
     * @param storeId
     * @param employeeId
     * @return
     * @throws DataAccessException
     * @throws EmptyResultDataAccessException
     * @throws RuntimeException
     */
    public BbyAccount getBbyAccount(String dataSharingKey, String storeId, String employeeId)
	    throws DataAccessException, EmptyResultDataAccessException, RuntimeException;

    public void deleteBbyAccount(String dataSharingKey) throws DataAccessException, RuntimeException;

}
