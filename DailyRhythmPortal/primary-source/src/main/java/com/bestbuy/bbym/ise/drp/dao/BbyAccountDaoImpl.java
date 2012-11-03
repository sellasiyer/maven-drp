package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * DB operations implementation class for BBYM_ACCT table
 * 
 * @author Sridhar Savaram
 * @version
 * @since
 */
@Repository("bbyAccountDao")
public class BbyAccountDaoImpl extends AbstractDao implements BbyAccountDao {

    private final String BBYM_ACCT_SELECT_QUERY = "SELECT ba.dta_sharing_key, ba.cust_frst_nm ba_cust_frst_nm, ba.cust_last_nm ba_cust_last_nm, "
	    + "ba.cust_ph_nbr ba_cust_ph_nbr, ba.cust_email_id ba_cust_email_id, ba.bby_cust_id, ba.created_by ba_created_by, "
	    + "ba.created_on ba_created_on, ba.amended_by ba_amended_by, ba.amended_on ba_amended_on, baa.addr_ln_1 baa_address_line1, "
	    + "baa.addr_ln_2 baa_address_line2, baa.city baa_city, baa.state baa_state, baa.zipcode baa_zipcode, baa.created_by baa_created_by, "
	    + "baa.created_on baa_created_on, baa.amended_by baa_amended_by, baa.amended_on baa_amended_on FROM bst_ise_sch01.bbym_acct ba, "
	    + "bst_ise_sch01.addr baa WHERE ba.addr_id = baa.addr_id(+) AND ba.dta_sharing_key = ?";

    private final String BBYM_ACCT_SELECT_QUERY_EMPLOYEE_AND_STORE = BBYM_ACCT_SELECT_QUERY + " AND ba.created_by = ?";

    private final String BBYM_ACCT_INSERT_QUERY = "insert into BST_ISE_SCH01.bbym_acct (dta_sharing_key, cust_frst_nm, cust_last_nm, cust_ph_nbr, cust_email_id, addr_id, bby_cust_id,"
	    + "created_by, created_on,rwz_nbr) values (:dataSharingKey, :custFirstName, :custLastName, :custPhoneNumber, :custEmailId, :addressId, :bbyCustId,"
	    + ":createdBy, sysdate,:rwdZoneNbr)";

    private final String BBYM_ACCT_UPDATE_QUERY = "update BST_ISE_SCH01.bbym_acct set cust_frst_nm = :custFirstName, cust_last_nm = :custLastName, "
	    + "cust_ph_nbr = :custPhoneNumber, cust_email_id = :custEmailId, addr_id = :addressId, bby_cust_id = :bbyCustId, "
	    + " amended_by = :modifiedBy, amended_on = sysdate, rwz_nbr = :rwdZoneNbr where dta_sharing_key =:dataSharingKey ";

    private final String BBYM_ACCT_SELECT_ADDRESSID_QUERY = "SELECT addr_id FROM BST_ISE_SCH01.bbym_acct WHERE dta_sharing_key=?";

    private final String BBYM_ACCT_DELETE_QUERY = "DELETE FROM BST_ISE_SCH01.bbym_acct WHERE dta_sharing_key=:dataSharingKey";

    @Autowired
    private AddressDao addressDao;

    public void setAddressDao(AddressDao addressDao) {
	this.addressDao = addressDao;
    }

    /**
     * Persists BbyAccount into database table: BBYM_ACCT.
     */
    @Override
    public void persistBbyAccount(BbyAccount bbyAccount) throws DataAccessException, RuntimeException {

	if (bbyAccount.getAddress() != null)
	    addressDao.persistAddress(bbyAccount.getAddress());

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", bbyAccount.getDataSharingKey());
	params.put("custFirstName", bbyAccount.getFirstName());
	params.put("custLastName", bbyAccount.getLastName());
	params.put("custPhoneNumber", bbyAccount.getPhoneNumber());
	params.put("custEmailId", bbyAccount.getEmail());
	params.put("addressId", (bbyAccount.getAddress() != null)?bbyAccount.getAddress().getAddressId():null);
	params.put("bbyCustId", bbyAccount.getEccaId());
	params.put("createdBy", bbyAccount.getCreatedBy());
	params.put("rwdZoneNbr", bbyAccount.getRewardZoneNo());

	getNamedJdbcTemplate().update(BBYM_ACCT_INSERT_QUERY, params);
    }

    @Override
    public BbyAccount getBbyAccount(String dataSharingKey) throws DataAccessException, EmptyResultDataAccessException,
	    RuntimeException {

	return getJdbcTemplate().queryForObject(BBYM_ACCT_SELECT_QUERY, new Object[] {dataSharingKey },
		new BbyAccountRowMapper());
    }

    @Override
    public void updateBbyAccount(BbyAccount bbyAccount) throws DataAccessException, RuntimeException {
	String addressId = null;
	if (bbyAccount.getAddress() != null){
	    addressId = getAddressId(bbyAccount.getDataSharingKey());
	    bbyAccount.getAddress().setAddressId(addressId);
	    addressDao.updateAddress(bbyAccount.getAddress());
	}
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", bbyAccount.getDataSharingKey());
	params.put("custFirstName", bbyAccount.getFirstName());
	params.put("custLastName", bbyAccount.getLastName());
	params.put("custPhoneNumber", bbyAccount.getPhoneNumber());
	params.put("custEmailId", bbyAccount.getEmail());
	params.put("addressId", addressId);
	params.put("bbyCustId", bbyAccount.getEccaId());
	params.put("modifiedBy", bbyAccount.getModifiedBy());
	params.put("rwdZoneNbr", bbyAccount.getRewardZoneNo());
	getNamedJdbcTemplate().update(BBYM_ACCT_UPDATE_QUERY, params);
    }

    private String getAddressId(String dataSharingKey) throws DataAccessException {
	return getJdbcTemplate().queryForObject(BBYM_ACCT_SELECT_ADDRESSID_QUERY, new Object[] {dataSharingKey },
		new AddressIdRowMapper());
    }

    private class AddressIdRowMapper implements RowMapper<String> {

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return rs.getString("addr_id");
	}
    }

    private class BbyAccountRowMapper implements RowMapper<BbyAccount> {

	@Override
	public BbyAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
	    BbyAccount bbyAccount = new BbyAccount();
	    bbyAccount.setDataSharingKey(rs.getString("dta_sharing_key"));
	    bbyAccount.setFirstName(rs.getString("ba_cust_frst_nm"));
	    bbyAccount.setLastName(rs.getString("ba_cust_last_nm"));
	    bbyAccount.setPhoneNumber(rs.getString("ba_cust_ph_nbr"));
	    bbyAccount.setEmail(rs.getString("ba_cust_email_id"));
	    bbyAccount.setEccaId(rs.getString("bby_cust_id"));
	    bbyAccount.setCreatedBy(rs.getString("ba_created_by"));
	    bbyAccount.setCreatedOn(rs.getTimestamp("ba_created_on"));
	    bbyAccount.setModifiedBy(rs.getString("ba_amended_by"));
	    bbyAccount.setModifiedOn(rs.getTimestamp("ba_amended_on"));
	    Address bbyAccountAddress = new Address();
	    bbyAccountAddress.setAddressline1(rs.getString("baa_address_line1"));
	    bbyAccountAddress.setAddressline2(rs.getString("baa_address_line2"));
	    bbyAccountAddress.setCity(rs.getString("baa_city"));
	    bbyAccountAddress.setState(rs.getString("baa_state"));
	    bbyAccountAddress.setZip(rs.getString("baa_zipcode"));
	    bbyAccountAddress.setCreatedBy(rs.getString("baa_created_by"));
	    bbyAccountAddress.setCreatedOn(rs.getTimestamp("baa_created_on"));
	    bbyAccountAddress.setModifiedBy(rs.getString("baa_amended_by"));
	    bbyAccountAddress.setModifiedOn(rs.getTimestamp("baa_amended_on"));
	    bbyAccount.setAddress(bbyAccountAddress);
	    return bbyAccount;
	}
    }

    @Override
    public void deleteBbyAccount(String dataSharingKey) throws DataAccessException, RuntimeException {
	try{
	    addressDao.deleteAddress(getAddressId(dataSharingKey));
	}catch(EmptyResultDataAccessException e){
	    // Ignore as there may not address record
	}

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", dataSharingKey);
	getNamedJdbcTemplate().update(BBYM_ACCT_DELETE_QUERY, params);
    }

    @Override
    public BbyAccount getBbyAccount(String dataSharingKey, String storeId, String employeeId) {
	return getJdbcTemplate().queryForObject(BBYM_ACCT_SELECT_QUERY_EMPLOYEE_AND_STORE,
		new Object[] {dataSharingKey, employeeId }, new BbyAccountRowMapper());
    }

}
