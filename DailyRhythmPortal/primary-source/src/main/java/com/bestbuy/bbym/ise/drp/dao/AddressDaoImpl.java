package com.bestbuy.bbym.ise.drp.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * DB operations implementation class to store and retrieve from ADDR DB table.
 * 
 * @author Sridhar Savaram
 * @version
 * @since
 */
@Repository("addressDao")
public class AddressDaoImpl extends AbstractDao implements AddressDao {

    private final String ADDR_INSERT_QUERY = "insert into BST_ISE_SCH01.addr (addr_id, addr_ln_1, addr_ln_2, city, state, zipcode,"
	    + "created_by, created_on) values (:addressId, :addressLine1, :addressLine2, :city, :state, :zipCode,  "
	    + ":createdBy, sysdate)";

    private final String ADDR_UPDATE_QUERY = "update BST_ISE_SCH01.addr set addr_ln_1 = :addressLine1, addr_ln_2 = :addressLine2, city = :city,"
	    + "state = :state, zipcode = :zipCode, amended_by = :modifiedBy, amended_on = sysdate "
	    + "where addr_id = :addressId";

    private final String ADDR_DELETE_QUERY = "DELETE FROM BST_ISE_SCH01.addr WHERE addr_id=:addressId";

    /**
     * Persists Address into database table: ADDR.
     */
    @Override
    public void persistAddress(Address address) throws DataAccessException, RuntimeException {
	getNamedJdbcTemplate().update(ADDR_INSERT_QUERY, getParamsMap(address));
    }

    @Override
    public void updateAddress(Address address) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("addressId", address.getAddressId());
	params.put("addressLine1", address.getAddressline1());
	params.put("addressLine2", address.getAddressline2());
	params.put("city", address.getCity());
	params.put("state", address.getState());
	params.put("zipCode", address.getZip());
	params.put("modifiedBy", address.getModifiedBy());
	getNamedJdbcTemplate().update(ADDR_UPDATE_QUERY, params);
    }

    private Map<String, Object> getParamsMap(Address address) {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("addressId", address.getAddressId());
	params.put("addressLine1", address.getAddressline1());
	params.put("addressLine2", address.getAddressline2());
	params.put("city", address.getCity());
	params.put("state", address.getState());
	params.put("zipCode", address.getZip());
	params.put("createdBy", address.getCreatedBy());

	return params;
    }

    @Override
    public void deleteAddress(String addressId) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("addressId", addressId);
	getNamedJdbcTemplate().update(ADDR_DELETE_QUERY, params);
    }
}
