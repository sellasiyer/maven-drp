package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.bestbuy.bbym.ise.drp.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.FourPartKey;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;

/**
 * 
 * @author a194869
 */
@Repository("tradeInDao")
public class TradeInDaoImpl extends AbstractDao implements TradeInDao {

    private final String DEVICE_INSERT_QUERY = "insert into bst_ise_sch01.device (devc_id,technlgy_typ,handset_id,mdl_nbr,ph_name,mfg_nm,"
	    + "sku,description,created_by,created_on) values (:devcId,:technologyType,:handsetId,:modelNbr,:phName,:mfgName,"
	    + ":sku,:description,:createdBy,sysdate)";
    private final String TRADEIN_INSERT_QUERY = "insert into bst_ise_sch01.trd_in (dta_sharing_key,next_url,titan_cart_id,"
	    + "trd_in_val,devc_id,created_by,created_on) values"
	    + " (:datasharingkey,:nexturl,:titancartid,:tradeinvalue,:deviceid," + ":createdBy,sysdate)";
    private final String DEVICE_DELETE_QUERY = "delete  from " + "bst_ise_sch01.device dev where dev.devc_id=:deviceId";
    private final String TRADEIN_DELETE_QUERY = "delete  from bst_ise_sch01.trd_in trd "
	    + "where trd.dta_sharing_key=:dataSharingKey";
    private final String TRADEIN_SELECT = "select trd.dta_sharing_key,trd.next_url,trd.titan_cart_id,trd.trd_in_val,"
	    + "trd.promo_code,trd.promo_val,trd.devc_id,trd.bsns_dt,trd.rgst_trans_nbr,trd.wrkstn_id,trd.stor_id,"
	    + "dev.technlgy_typ,dev.handset_id,dev.mdl_nbr,dev.ph_name,dev.mfg_nm,dev.sku,dev.description";
    private final String TRADEIN_WHERE = " from bst_ise_sch01.trd_in trd,"
	    + "bst_ise_sch01.device dev where ( trd.devc_id = dev.devc_id ) and trd.dta_sharing_key=:datasharingkey";
    private final String TRADEIN_DEVICE_SELECT_QUERY = TRADEIN_SELECT + TRADEIN_WHERE;
    private final String TRADEIN_UPDATE_QUERY = "update bst_ise_sch01.trd_in set promo_code = :promocode,"
	    + "promo_val = :promovalue,bsns_dt = :bsnsdate,rgst_trans_nbr = :rgsttransnbr,wrkstn_id = :workstationid,stor_id = :storeid,"
	    + "amended_by = :amendedby,amended_on = sysdate,receipt_req_flag = :receiptRequired where dta_sharing_key = :datasharingkey";

    private final String TRADEIN_DOCS_UPDATE_QUERY = "update bst_ise_sch01.trd_in set trd_in_doc_url=:tradeindocurl,"
	    + "item_tag=:itemtag,giftcard_number=:giftCardNo,amended_by=:amendedby,amended_on=sysdate WHERE "
	    + "dta_sharing_key=:datasharingkey";
    // Need to add columns in "" for tradeInDocURL,itemTag and giftCardNumber
    private final String TRADEIN_DOCS_SELECT_QUERY = TRADEIN_SELECT
	    + ",trd.trd_in_doc_url,trd.item_tag,trd.giftcard_number " + TRADEIN_WHERE;

    private final String TRADEIN_DEVICE_CARR_SELECT = "select carr_acct.acct_nbr,carr_acct.covg_zip,"
	    + "carr_acct.cust_frst_nm,carr_acct.cust_last_nm,carr_acct.cust_email_id,carr_acct.addr_id,carr_acct.cust_ph_nbr,"
	    + "carr_acct.dob,carr_acct.id_type,carr_acct.id_issuer,carr_acct.id_code,carr_acct.id_exp_date,addr.addr_id,"
	    + "addr.addr_ln_1,addr.addr_ln_2,addr.city,addr.state,addr.zipcode,addr.urbanization_code,trd_in.dta_sharing_key,"
	    + "trd_in.next_url,trd_in.titan_cart_id,trd_in.trd_in_val,trd_in.promo_code,trd_in.promo_val,trd_in.devc_id,"
	    + "trd_in.bsns_dt,trd_in.rgst_trans_nbr,trd_in.wrkstn_id,trd_in.stor_id,trd_in.trd_in_doc_url,trd_in.item_tag,"
	    + "trd_in.giftcard_number, trd_in.receipt_req_flag, device.devc_id,device.technlgy_typ,device.handset_id,device.mdl_nbr,device.ph_name,"
	    + "device.mfg_nm,device.sku,device.description from bst_ise_sch01.carr_acct,bst_ise_sch01.addr,bst_ise_sch01.trd_in,"
	    + "bst_ise_sch01.device where ( carr_acct.addr_id = addr.addr_id ) and ( trd_in.devc_id = device.devc_id) and "
	    + "( carr_acct.dta_sharing_key = trd_in.dta_sharing_key ) and ( trd_in.dta_sharing_key = :dataSharingKey )";
    @Autowired
    private CarrierAccountDao carrierAccountDao;

    private static DataSourceTransactionManager transactionManager;
    private static Logger logger = LoggerFactory.getLogger(TradeInDaoImpl.class);

    @Override
    public void insertTradeInDevice(TitanDevice titanDevice) throws DataAccessException, RuntimeException {
	try{
	    transactionManager = new DataSourceTransactionManager(getDs());
	    setTransactionManager(transactionManager);
	    TransactionDefinition td = new DefaultTransactionDefinition();
	    TransactionStatus status = transactionManager.getTransaction(td);
	    TitanDevice tDev = getTradeInRecord(titanDevice.getDataSharingKey());
	    if (tDev != null){
		logger.info("TRD_IN DEVICE Record exists and deleting for dsk = " + titanDevice.getDataSharingKey());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dataSharingKey", tDev.getDataSharingKey());
		getNamedJdbcTemplate().update(TRADEIN_DELETE_QUERY, params);
		params = new HashMap<String, Object>();
		params.put("deviceId", tDev.getDeviceId());
		getNamedJdbcTemplate().update(DEVICE_DELETE_QUERY, params);
		transactionManager.commit(status);
		logger.info("TRD_IN DEVICE Record exists and Deleted for dsk = " + titanDevice.getDataSharingKey());
	    }
	}catch(DataAccessException ex){
	    logger.info(ex.getMessage());
	}catch(RuntimeException ex){
	    logger.info(ex.getMessage());
	}

	transactionManager = new DataSourceTransactionManager(getDs());
	setTransactionManager(transactionManager);
	TransactionDefinition td = new DefaultTransactionDefinition();

	TransactionStatus status = transactionManager.getTransaction(td);

	try{

	    persistDevice(titanDevice);
	    persistTradeIn(titanDevice);
	    transactionManager.commit(status);
	}catch(DuplicateKeyException e){
	    transactionManager.rollback(status);
	    logger.info("*** Transaction Rolled back status = " + status + " With exception message = "
		    + e.getMessage());
	    throw new DataAccessException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(DataAccessException e){
	    transactionManager.rollback(status);
	    logger.info("*** Transaction Rolled back status = " + status + " With exception message = "
		    + e.getMessage());
	    throw e;
	}catch(RuntimeException e){
	    transactionManager.rollback(status);
	    logger.info("*** Transaction Rolled back status = " + status + " With exception message = "
		    + e.getMessage());
	    throw e;
	}

    }

    @Override
    public void persistDevice(TitanDevice titanDevice) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	String description = "";
	params.put("devcId", titanDevice.getDeviceId());
	params.put("technologyType", titanDevice.getTechnologyType());
	params.put("handsetId", titanDevice.getSerialNumber());
	params.put("modelNbr", titanDevice.getModelNumber());
	params.put("phName", titanDevice.getDeviceName());
	params.put("mfgName", titanDevice.getManufacturer());
	params.put("sku", titanDevice.getSku());
	if (titanDevice.getDescription().length() > 200){
	    description = titanDevice.getDescription().substring(0, 199);
	}else{
	    description = titanDevice.getDescription();
	}
	titanDevice.setDescription(description);
	params.put("description", description);
	params.put("createdBy", titanDevice.getCreatedBy());
	getNamedJdbcTemplate().update(DEVICE_INSERT_QUERY, params);

    }

    @Override
    public void persistTradeIn(TitanDevice titanDevice) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("datasharingkey", titanDevice.getDataSharingKey());
	params.put("nexturl", titanDevice.getNextUrl());
	params.put("titancartid", titanDevice.getCartId());
	params.put("tradeinvalue", titanDevice.getTradeInValue());
	params.put("deviceid", titanDevice.getDeviceId());
	params.put("createdBy", titanDevice.getCreatedBy());
	getNamedJdbcTemplate().update(TRADEIN_INSERT_QUERY, params);
    }

    @Override
    public TitanDevice getTradeInRecord(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().queryForObject(TRADEIN_DEVICE_SELECT_QUERY, new Object[] {dataSharingKey },
		new TradeInDeviceRowMapper());
    }

    @Override
    public TitanDevice getTradeInCarrierRecord(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().queryForObject(TRADEIN_DEVICE_CARR_SELECT, new Object[] {dataSharingKey },
		new TradeInCarrierRowMapper());
    }

    @Override
    public TitanDevice getTradeInDocs(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().queryForObject(TRADEIN_DOCS_SELECT_QUERY, new Object[] {dataSharingKey },
		new TradeInDocsRowMapper());
    }

    private class TradeInDocsRowMapper extends TradeInDeviceRowMapper {

	@Override
	public TitanDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
	    TitanDevice device = super.mapRow(rs, rowNum);
	    device.setDocURL(rs.getString("trd_in_doc_url"));
	    device.setItemTag(rs.getString("item_tag"));
	    device.setGiftCardNo(rs.getLong("giftcard_number"));
	    return device;
	}
    }

    private class TradeInCarrierRowMapper extends TradeInDeviceRowMapper {

	@Override
	public TitanDevice mapRow(ResultSet rs, int rowNum) throws SQLException {

	    TitanDevice device = super.mapRow(rs, rowNum);
	    device.setReceiptRequired(rs.getBoolean("receipt_req_flag"));
	    CarrierAccount carrierAccount = new CarrierAccount();
	    carrierAccount.setDataSharingKey(rs.getString("dta_sharing_key"));
	    carrierAccount.setCarrierAccountNumber(rs.getString("acct_nbr"));
	    carrierAccount.setCoverageZip(rs.getString("covg_zip"));
	    carrierAccount.setFirstName(rs.getString("cust_frst_nm"));
	    carrierAccount.setLastName(rs.getString("cust_last_nm"));
	    carrierAccount.setPhoneNumber(rs.getString("cust_ph_nbr"));
	    carrierAccount.setEmail(rs.getString("cust_email_id"));
	    carrierAccount.setIdType(rs.getString("id_type"));
	    carrierAccount.setIdIssuer(rs.getString("id_issuer"));
	    carrierAccount.setIdCode(rs.getString("id_code"));
	    carrierAccount.setIdExpirationDate(rs.getDate("id_exp_date"));
	    carrierAccount.setDob(rs.getDate("dob"));
	    Address caAccountAddress = new Address();
	    caAccountAddress.setAddressline1(rs.getString("addr_ln_1"));
	    caAccountAddress.setAddressline2(rs.getString("addr_ln_2"));
	    caAccountAddress.setCity(rs.getString("city"));
	    caAccountAddress.setState(rs.getString("state"));
	    caAccountAddress.setZip(rs.getString("zipcode"));
	    carrierAccount.setAddress(caAccountAddress);
	    device.setCarrierAccount(carrierAccount);
	    return device;
	}
    }

    private class TradeInDeviceRowMapper implements RowMapper<TitanDevice> {

	@Override
	public TitanDevice mapRow(ResultSet rs, int rowNum) throws SQLException {
	    TitanDevice device = new TitanDevice();
	    device.setDataSharingKey(rs.getString("dta_sharing_key"));
	    device.setNextUrl(rs.getString("next_url"));
	    device.setCartId(rs.getInt("titan_cart_id"));
	    device.setTradeInValue(rs.getBigDecimal("trd_in_val"));
	    device.setPromoCode(rs.getString("promo_code"));
	    device.setPromoValue(rs.getString("promo_val"));
	    device.setSerialNumber(rs.getString("devc_id"));

	    FourPartKey fpKey = new FourPartKey(rs.getString("stor_id"), rs.getString("wrkstn_id"), rs
		    .getString("rgst_trans_nbr"), rs.getDate("bsns_dt"));

	    if (device != null){
		device.setFourPartKey(fpKey);
	    }
	    device.setTechnologyType(rs.getString("technlgy_typ"));
	    device.setSerialNumber(rs.getString("handset_id"));
	    device.setModelNumber(rs.getString("mdl_nbr"));
	    device.setDeviceName(rs.getString("ph_name"));
	    device.setManufacturer(rs.getString("mfg_nm"));
	    device.setSku(rs.getString("sku"));
	    device.setDescription(rs.getString("description"));
	    return device;
	}
    }

    @Override
    public void updateTradeInDevice(TitanDevice titanDevice) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();

	params.put("promocode", titanDevice.getPromoCode());
	params.put("promovalue", titanDevice.getPromoValue());
	params.put("receiptRequired", titanDevice.isReceiptRequired()?1:0);
	if (titanDevice.getFourPartKey() != null){
	    params.put("bsnsdate", titanDevice.getFourPartKey().getBusinessDate());
	    params.put("rgsttransnbr", titanDevice.getFourPartKey().getRegisterTransactionNum());
	    params.put("workstationid", titanDevice.getFourPartKey().getWorkStationId());
	    params.put("storeid", titanDevice.getFourPartKey().getStoreId());
	}else{
	    params.put("bsnsdate", null);
	    params.put("rgsttransnbr", null);
	    params.put("workstationid", null);
	    params.put("storeid", null);
	}

	params.put("amendedby", titanDevice.getCreatedBy());
	params.put("datasharingkey", titanDevice.getDataSharingKey());
	transactionManager = new DataSourceTransactionManager(getDs());
	setTransactionManager(transactionManager);
	TransactionDefinition td = new DefaultTransactionDefinition();
	TransactionStatus status = transactionManager.getTransaction(td);
	try{
	    getNamedJdbcTemplate().update(TRADEIN_UPDATE_QUERY, params);
	    carrierAccountDao.persistTrdInCarrierAccount(titanDevice.getCarrierAccount());
	    transactionManager.commit(status);
	}catch(DataAccessException e){
	    transactionManager.rollback(status);
	    logger.info("*** Transaction Rolled back status = " + status + " With exception message = "
		    + e.getMessage());
	    throw e;
	}catch(RuntimeException e){
	    transactionManager.rollback(status);
	    logger.info("*** Transaction Rolled back status = " + status + " With exception message = "
		    + e.getMessage());
	    throw e;
	}

    }

    @Override
    public void updateTradeInDocs(TitanDevice titanDevice) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();

	params.put("tradeindocurl", titanDevice.getDocURL());
	params.put("itemtag", titanDevice.getItemTag());
	params.put("giftCardNo", titanDevice.getGiftCardNo());
	params.put("amendedby", titanDevice.getCreatedBy());
	params.put("datasharingkey", titanDevice.getDataSharingKey());
	getNamedJdbcTemplate().update(TRADEIN_DOCS_UPDATE_QUERY, params);

    }

}
