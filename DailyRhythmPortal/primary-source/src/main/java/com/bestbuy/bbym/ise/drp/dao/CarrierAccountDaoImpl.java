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
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * DB operations implementation class for CARR_ACCT table
 * 
 * @author Sridhar Savaram
 * @version 1.0
 * @since
 */
@Repository("carrierAccountDao")
public class CarrierAccountDaoImpl extends AbstractDao implements CarrierAccountDao {

    private final String CARR_ACCT_INSERT_QUERY = "INSERT into BST_ISE_SCH01.carr_acct (dta_sharing_key, acct_nbr, covg_zip, cust_frst_nm, cust_last_nm, cust_ph_nbr, cust_email_id, addr_id,"
	    + "created_by, created_on,carr_id) values (:dataSharingKey, :accountNumber, :coverageZip, :custFirstName, :custLastName, :custPhoneNumber, :custEmailId, :addressId ,"
	    + ":createdBy, sysdate,:carrier) ";

    private final String CARR_ACCT_SELECT_QUERY = "SELECT ca.dta_sharing_key, ca.acct_nbr, ca.covg_zip, ca.cust_frst_nm ca_cust_frst_nm, ca.cust_last_nm ca_cust_last_nm, ca.cust_ph_nbr "
	    + "ca_cust_ph_nbr, ca.cust_email_id ca_cust_email_id, ca.created_by ca_created_by, ca.created_on ca_created_on, ca.amended_by ca_amended_by, ca.amended_on ca_amended_on, "
	    + "caa.addr_ln_1 caa_address_line1, caa.addr_ln_2 caa_address_line2, caa.city caa_city, caa.state caa_state, caa.zipcode caa_zipcode, caa.created_by caa_created_by, "
	    + "caa.created_on caa_created_on, caa.amended_by caa_amended_by, caa.amended_on caa_amended_on FROM bst_ise_sch01.carr_acct ca, bst_ise_sch01.addr caa "
	    + "WHERE ca.addr_id = caa.addr_id(+)  and ca.dta_sharing_key = ?";

    private final String CARR_ACCT_SELECT_ADDRESSID_QUERY = "SELECT addr_id FROM BST_ISE_SCH01.carr_acct WHERE dta_sharing_key=?";

    private final String CARR_ACCT_UPDATE_QUERY = "UPDATE BST_ISE_SCH01.carr_acct SET acct_nbr = :accountNumber, covg_zip = :coverageZip, "
	    + "cust_frst_nm = :custFirstName, cust_last_nm = :custLastName, cust_ph_nbr = :custPhoneNumber, cust_email_id = :custEmailId"
	    + ", addr_id = :addressId , amended_by = :modifiedBy, amended_on = sysdate WHERE dta_sharing_key = :dataSharingKey";

    private final String CARR_ACCT_DELETE_QUERY = "DELETE FROM BST_ISE_SCH01.carr_acct WHERE dta_sharing_key = :dataSharingKey";

    private final String TRD_IN_CARR_ACCT_INSERT_QUERY = "INSERT into BST_ISE_SCH01.carr_acct (dta_sharing_key, acct_nbr, covg_zip, cust_frst_nm, cust_last_nm, cust_ph_nbr,"
	    + "dob, id_type, id_issuer, id_code, id_exp_date, cust_email_id, addr_id,"
	    + "created_by, created_on) values (:dataSharingKey, :accountNumber, :coverageZip, :custFirstName, :custLastName, :custPhoneNumber, "
	    + ":dob, :idType, :idIssuer, :idCode, :idExpDate, :custEmailId, :addressId ," + ":createdBy, sysdate) ";

    @Autowired
    AddressDao addressDao;

    /**
     * Persists CarrierAccount into database table: CARR_ACCT.
     */
    @Override
    public void persistCarrierAccount(CarrierAccount carrierAccount) throws DataAccessException, RuntimeException {
	if (carrierAccount.getAddress() != null)
	    addressDao.persistAddress(carrierAccount.getAddress());

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", carrierAccount.getDataSharingKey());
	params.put("accountNumber", carrierAccount.getCarrierAccountNumber());
	params.put("coverageZip", carrierAccount.getCoverageZip());
	params.put("custFirstName", carrierAccount.getFirstName());
	params.put("custLastName", carrierAccount.getLastName());
	params.put("custPhoneNumber", carrierAccount.getPhoneNumber());
	params.put("custEmailId", carrierAccount.getEmail());
	params.put("addressId", (carrierAccount.getAddress() != null)?carrierAccount.getAddress().getAddressId():null);
	params.put("createdBy", carrierAccount.getCreatedBy());
	params.put("carrier", carrierAccount.getCarrier() != null?carrierAccount.getCarrier().getShortLabel():null);

	getNamedJdbcTemplate().update(CARR_ACCT_INSERT_QUERY, params);

    }

    @Override
    public CarrierAccount getCarrierAccount(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().queryForObject(CARR_ACCT_SELECT_QUERY, new Object[] {dataSharingKey },
		new CarrierAccountRowMapper());
    }

    @Override
    public void persistTrdInCarrierAccount(CarrierAccount carrierAccount) throws DataAccessException, RuntimeException {
	if (carrierAccount.getAddress() != null)
	    addressDao.persistAddress(carrierAccount.getAddress());

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", carrierAccount.getDataSharingKey());
	params.put("accountNumber", carrierAccount.getCarrierAccountNumber());
	params.put("coverageZip", carrierAccount.getCoverageZip());
	params.put("custFirstName", carrierAccount.getFirstName());
	params.put("custLastName", carrierAccount.getLastName());
	params.put("custPhoneNumber", carrierAccount.getPhoneNumber());
	params.put("dob", carrierAccount.getDob());
	params.put("idType", carrierAccount.getIdType());
	params.put("idIssuer", carrierAccount.getIdIssuer());
	params.put("idCode", carrierAccount.getIdCode());
	params.put("idExpDate", carrierAccount.getIdExpirationDate());
	params.put("custEmailId", carrierAccount.getEmail());
	params.put("addressId", (carrierAccount.getAddress() != null)?carrierAccount.getAddress().getAddressId():null);
	params.put("createdBy", carrierAccount.getCreatedBy());

	getNamedJdbcTemplate().update(TRD_IN_CARR_ACCT_INSERT_QUERY, params);
    }

    private class CarrierAccountRowMapper implements RowMapper<CarrierAccount> {

	@Override
	public CarrierAccount mapRow(ResultSet rs, int rowNum) throws SQLException {
	    CarrierAccount carrierAccount = new CarrierAccount();
	    carrierAccount.setDataSharingKey(rs.getString("dta_sharing_key"));
	    carrierAccount.setCarrierAccountNumber(rs.getString("acct_nbr"));
	    carrierAccount.setCoverageZip(rs.getString("covg_zip"));
	    carrierAccount.setFirstName(rs.getString("ca_cust_frst_nm"));
	    carrierAccount.setLastName(rs.getString("ca_cust_last_nm"));
	    carrierAccount.setPhoneNumber(rs.getString("ca_cust_ph_nbr"));
	    carrierAccount.setEmail(rs.getString("ca_cust_email_id"));
	    carrierAccount.setCreatedBy(rs.getString("ca_created_by"));
	    carrierAccount.setCreatedOn(rs.getDate("ca_created_on"));
	    carrierAccount.setModifiedBy(rs.getString("ca_amended_by"));
	    carrierAccount.setModifiedOn(rs.getDate("ca_amended_on"));
	    Address caAccountAddress = new Address();
	    caAccountAddress.setAddressline1(rs.getString("caa_address_line1"));
	    caAccountAddress.setAddressline2(rs.getString("caa_address_line2"));
	    caAccountAddress.setCity(rs.getString("caa_city"));
	    caAccountAddress.setState(rs.getString("caa_state"));
	    caAccountAddress.setZip(rs.getString("caa_zipcode"));
	    caAccountAddress.setCreatedBy(rs.getString("caa_created_by"));
	    caAccountAddress.setCreatedOn(rs.getDate("caa_created_on"));
	    caAccountAddress.setModifiedBy(rs.getString("caa_amended_by"));
	    caAccountAddress.setModifiedOn(rs.getDate("caa_amended_on"));
	    carrierAccount.setAddress(caAccountAddress);
	    return carrierAccount;
	}

    }

    public void setAddressDao(AddressDaoImpl addressDao) {
	this.addressDao = addressDao;
    }

    @Override
    public void updateCarrierAccount(CarrierAccount carrierAccount) throws DataAccessException, RuntimeException {
	String addressId = getAddressId(carrierAccount.getDataSharingKey());
	if (carrierAccount.getAddress() != null){
	    carrierAccount.getAddress().setAddressId(addressId);
	    addressDao.updateAddress(carrierAccount.getAddress());
	}

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", carrierAccount.getDataSharingKey());
	params.put("accountNumber", carrierAccount.getCarrierAccountNumber());
	params.put("coverageZip", carrierAccount.getCoverageZip());
	params.put("custFirstName", carrierAccount.getFirstName());
	params.put("custLastName", carrierAccount.getLastName());
	params.put("custPhoneNumber", carrierAccount.getPhoneNumber());
	params.put("custEmailId", carrierAccount.getEmail());
	params.put("addressId", addressId);

	params.put("modifiedBy", carrierAccount.getModifiedBy());
	getNamedJdbcTemplate().update(CARR_ACCT_UPDATE_QUERY, params);

    }

    private String getAddressId(String dataSharingKey) throws DataAccessException {
	return getJdbcTemplate().queryForObject(CARR_ACCT_SELECT_ADDRESSID_QUERY, new Object[] {dataSharingKey },
		new AddressIdRowMapper());
    }

    private class AddressIdRowMapper implements RowMapper<String> {

	@Override
	public String mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return rs.getString("addr_id");
	}
    }

    @Override
    public void deleteCarrierAccount(String dataSharingKey) throws DataAccessException, RuntimeException {
	try{
	    addressDao.deleteAddress(getAddressId(dataSharingKey));
	}catch(EmptyResultDataAccessException e){
	    // Ignore as there may not address record
	}

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", dataSharingKey);
	getNamedJdbcTemplate().update(CARR_ACCT_DELETE_QUERY, params);
    }
}
