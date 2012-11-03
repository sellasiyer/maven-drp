package com.bestbuy.bbym.ise.drp.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * Interface for CarrierAccount DB Object
 * 
 * @author Sridhar Savaram
 * @version 1.0
 * @since
 */
public interface CarrierAccountDao {

    /**
     * 
     * @param carrierAccount
     */
    public void persistCarrierAccount(CarrierAccount carrierAccount) throws DataAccessException, RuntimeException;

    public void persistTrdInCarrierAccount(CarrierAccount carrierAccount) throws DataAccessException, RuntimeException;

    public void updateCarrierAccount(CarrierAccount carrierAccount) throws DataAccessException, RuntimeException;

    public CarrierAccount getCarrierAccount(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public void deleteCarrierAccount(String dataSharingKey) throws DataAccessException, RuntimeException;
}
