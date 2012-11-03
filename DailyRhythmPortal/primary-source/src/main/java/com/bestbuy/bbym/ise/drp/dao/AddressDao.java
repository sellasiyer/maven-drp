package com.bestbuy.bbym.ise.drp.dao;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * Interface for the Address DB Object
 * 
 * @author Sridhar Savaram
 */
public interface AddressDao {

    /**
     * Create the address record
     */
    public void persistAddress(Address address) throws DataAccessException;

    /**
     * Updates address.
     */
    public void updateAddress(Address address) throws DataAccessException;

    /**
     * Deletes address.
     */
    public void deleteAddress(String addressId) throws DataAccessException;

}
