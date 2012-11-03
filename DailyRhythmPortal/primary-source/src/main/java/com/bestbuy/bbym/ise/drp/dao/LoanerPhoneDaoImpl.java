package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInExtra;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneDeletedReason;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneFulFillmentType;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneModelSearchCriteria;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.util.jdbc.DateUtil;

/**
 * Contains all methods that operate directly on the LoanerPhone
 */
@Repository("loanerPhoneDao")
public class LoanerPhoneDaoImpl extends AbstractDao implements LoanerPhoneDao {

    private static Logger logger = LoggerFactory.getLogger(LoanerPhoneDaoImpl.class);

    private static final String getPhonesByStoreIdSQL = "SELECT p.*,c.*,os.*,coci.* "
	    + "FROM BST_ISE_SCH01.LOANR_PH p "
	    // SQL for getting CHKOUT_CHKIN_HIST record with latest CHKOUT_TM
	    + "LEFT JOIN (SELECT cch.* FROM BST_ISE_SCH01.CHKOUT_CHKIN_HIST cch INNER JOIN "
	    + "(SELECT MAX(cch1.CHKOUT_TM) max_co, cch1.LOANR_PH_ID FROM BST_ISE_SCH01.CHKOUT_CHKIN_HIST cch1 GROUP BY cch1.LOANR_PH_ID) max_rec "
	    + " ON max_rec.LOANR_PH_ID = cch.LOANR_PH_ID AND cch.CHKOUT_TM = max_rec.max_co) coci ON p.LOANR_PH_ID=coci.LOANR_PH_ID "
	    // SQL for joining with CARR_LKUP
	    + "LEFT JOIN BST_ISE_SCH01.CARR_LKUP c ON p.CARR_LKUP_ID = c.CARR_LKUP_ID  "
	    // SQL for joining with OS_LKUP
	    + "LEFT JOIN BST_ISE_SCH01.OS_LKUP os ON p.OS_LKUP_ID = os.OS_LKUP_ID "
	    + "WHERE p.STOR_ID = :STOR_ID and p.ACTV_FLG = 1  ";

    private static final String getPhoneModelsByStoreIdSQL = "SELECT c.CARR,os.OS_NM,p.MAKE,p.MDL,count(*) as \""
	    + "NoOfPhones" + "\""
	    + " FROM BST_ISE_SCH01.LOANR_PH p LEFT JOIN BST_ISE_SCH01.CARR_LKUP c on p.CARR_LKUP_ID = c.CARR_LKUP_ID  "
	    + "LEFT JOIN BST_ISE_SCH01.OS_LKUP os on p.OS_LKUP_ID = os.OS_LKUP_ID "
	    + "WHERE p.STOR_ID = :STOR_ID and p.ACTV_FLG = 1  ";

    private static final String getCheckOutCheckInHistorySQL = "SELECT * FROM BST_ISE_SCH01.CHKOUT_CHKIN_HIST ";

    private static final String getCarriersSQL = "SELECT * FROM BST_ISE_SCH01.CARR_LKUP ";

    private static final String getOperatingSystemsSQL = "SELECT * FROM BST_ISE_SCH01.OS_LKUP ";

    private static final String getPhoneExtrasSQL = "SELECT * FROM BST_ISE_SCH01.PHONE_EXTRAS where  CHKOUT_CHKIN_HIST_ID=:CHKOUT_CHKIN_HIST_ID";

    private static final String createPhoneSQL = "insert into bst_ise_sch01.loanr_ph (loanr_ph_id,stor_id,carr_lkup_id,os_lkup_id,imei_esn,make,mdl,ph_cond,ph_cond_cmnt,last_actn_usr_id,bby_sku,del_reas,actv_flg,created_by,created_on,usr_frst_nm,usr_last_nm)"
	    + " values (bst_ise_sch01.phone_seq.nextval,:stor_id,:carr_lkup_id,:os_lkup_id,:imei_esn,:make,:mdl,:ph_cond,:ph_cond_cmnt,:last_actn_usr_id,:bby_sku,:del_reas,:actv_flg,:created_by,:created_on,:usr_frst_nm,:usr_last_nm)";

    private static final String updatePhoneSQL = "update bst_ise_sch01.loanr_ph"
	    + " set stor_id=:stor_id,carr_lkup_id=:carr_lkup_id,os_lkup_id=:os_lkup_id,imei_esn=:imei_esn,make=:make,mdl=:mdl,ph_cond=:ph_cond,ph_cond_cmnt=:ph_cond_cmnt,last_actn_usr_id=:last_actn_usr_id,bby_sku=:bby_sku,del_reas=:del_reas,actv_flg=:actv_flg,created_by=:created_by,created_on=:created_on,amended_by=:amended_by,amended_on=:amended_on,usr_frst_nm=:usr_frst_nm,usr_last_nm=:usr_last_nm"
	    + " where loanr_ph_id=:loanr_ph_id";

    private static final String deletePhoneSQL = "delete from bst_ise_sch01.loanr_ph where loanr_ph_id=:loanr_ph_id";

    private static final String createCheckOutCheckInHistorySql = "Insert into BST_ISE_SCH01.CHKOUT_CHKIN_HIST ( CHKOUT_CHKIN_HIST_ID,LOANR_PH_ID,CUST_FRST_NM,CUST_LAST_NM,CUST_CNTCT_PH,CUST_EMAIL,CUST_SVC_ORD_ID,FLFLMNT_TYP,GSP_ID,CHKOUT_TM,CHKIN_TM,CHKOUT_COND,CHKIN_COND,CHKOUT_COND_CMNT,CHKIN_COND_CMNT,CHKOUT_USR_NM,CHKIN_USR_NM,CHKOUT_DEP,CHKIN_DEP,CHKOUT_STAT,CREATED_BY,CREATED_ON,AMENDED_BY,AMENDED_ON, REGISTER_ID, TRANSACTION_NUMBER, TRANSACTION_DATE, NOTES, POS_STORE_ID)"
	    + " values (BST_ISE_SCH01.CHKOUT_CHKIN_HIST_SEQ.nextval,:LOANR_PH_ID,:CUST_FRST_NM,:CUST_LAST_NM,:CUST_CNTCT_PH,:CUST_EMAIL,:CUST_SVC_ORD_ID,:FLFLMNT_TYP,:GSP_ID,:CHKOUT_TM,:CHKIN_TM,:CHKOUT_COND,:CHKIN_COND,:CHKOUT_COND_CMNT,:CHKIN_COND_CMNT,:CHKOUT_USR_NM,:CHKIN_USR_NM,:CHKOUT_DEP,:CHKIN_DEP,:CHKOUT_STAT,:CREATED_BY,:CREATED_ON,:AMENDED_BY,:AMENDED_ON,:REGISTER_ID, :TRANSACTION_NUMBER, :TRANSACTION_DATE, :NOTES, :POS_STORE_ID)";

    private static final String updateCheckOutCheckInHistorySql = "UPDATE BST_ISE_SCH01.CHKOUT_CHKIN_HIST"
	    + " set CHKIN_TM=:CHKIN_TM,CHKIN_COND=:CHKIN_COND,CHKIN_COND_CMNT=:CHKIN_COND_CMNT,CHKIN_USR_NM=:CHKIN_USR_NM,CHKIN_DEP=:CHKIN_DEP,AMENDED_BY=:AMENDED_BY,AMENDED_ON=:AMENDED_ON,CHKOUT_STAT=:CHKOUT_STAT,REGISTER_ID=:REGISTER_ID, TRANSACTION_NUMBER=:TRANSACTION_NUMBER, TRANSACTION_DATE=:TRANSACTION_DATE, NOTES=:NOTES, POS_STORE_ID=:POS_STORE_ID "
	    + " where CHKOUT_CHKIN_HIST_ID=:CHKOUT_CHKIN_HIST_ID";

    private static final String createPhoneExtraSQL = "INSERT INTO BST_ISE_SCH01.PHONE_EXTRAS (PHONE_EXTRAS_ID,CHKOUT_CHKIN_HIST_ID,PH_XTRA_TYP,CHKOUT_STAT,CREATED_BY,CREATED_ON) values(BST_ISE_SCH01.PH_EXTRAS_SEQ.nextval,:CHKOUT_CHKIN_HIST_ID,:PH_XTRA_TYP,:CHKOUT_STAT,:CREATED_BY,:CREATED_ON)";

    private static final String updatePhoneExtraSQL = "UPDATE BST_ISE_SCH01.PHONE_EXTRAS  set CHKIN_STAT=:CHKIN_STAT,AMENDED_BY=:AMENDED_BY,AMENDED_ON=:AMENDED_ON"
	    + " where PHONE_EXTRAS_ID=:PHONE_EXTRAS_ID";

    private static final String checkForCheckedOutPhoneSQL = "SELECT * FROM BST_ISE_SCH01.CHKOUT_CHKIN_HIST WHERE LOANR_PH_ID=:loaner_phone_id AND CHKOUT_STAT=1";

    @Override
    public List<Phone> getPhones(String storeId, PhoneSearchCriteria criteria) throws DataAccessException {
	List<Phone> phonelist = null;
	if (storeId != null){
	    NamedParameterJdbcTemplate phoneSelect = getNamedJdbcTemplate();
	    StringBuffer sql = new StringBuffer(getPhonesByStoreIdSQL);
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("STOR_ID", storeId);
	    if (criteria != null){
		if (criteria.getLast4SerialNo() != null){
		    if (criteria.getLast4SerialNo().length() == 4){
			sql.append("and p.IMEI_ESN LIKE :IMEI_ESN ");
			params.put("IMEI_ESN", "%" + criteria.getLast4SerialNo());
		    }else{
			sql.append("and p.IMEI_ESN = :IMEI_ESN ");
			params.put("IMEI_ESN", criteria.getLast4SerialNo());
		    }
		}
		if (criteria.getCarrier() != null && criteria.getCarrier().getCarrier() != null){
		    sql
			    .append("and p.CARR_LKUP_ID=(SELECT CARR_LKUP_ID FROM BST_ISE_SCH01.CARR_LKUP WHERE CARR=:CARR) ");
		    params.put("CARR", criteria.getCarrier().getCarrier());
		}
		if (criteria.getOperatingSystem() != null && criteria.getOperatingSystem().getOs() != null){
		    sql.append("and p.OS_LKUP_ID=(SELECT OS_LKUP_ID FROM BST_ISE_SCH01.OS_LKUP WHERE OS_NM=:OS_NM) ");
		    params.put("OS_NM", criteria.getOperatingSystem().getOs());
		}
		if (criteria.getCheckedOut() != null){
		    if (criteria.getCheckedOut().booleanValue()){
			sql.append("and coci.CHKOUT_STAT=1 ");
		    }else{
			sql.append("and (coci.CHKOUT_STAT =0 or coci.CHKOUT_STAT is null) ");
		    }
		}
		if (criteria.getLast4ServiceOrderNo() != null){
		    logger.debug("serial no--->>" + criteria.getLast4ServiceOrderNo());
		    if (criteria.getLast4ServiceOrderNo().length() == 4){
			sql.append("and coci.CUST_SVC_ORD_ID LIKE :CUST_SVC_ORD_ID ");
			params.put("CUST_SVC_ORD_ID", "%" + criteria.getLast4ServiceOrderNo());
		    }else{
			sql.append("and coci.CUST_SVC_ORD_ID = :CUST_SVC_ORD_ID ");
			params.put("CUST_SVC_ORD_ID", criteria.getLast4ServiceOrderNo());
		    }

		}
		sql.append("order by c.CARR,upper(os.OS_NM),upper(p.MAKE),upper(p.MDL) ");
	    }
	    try{
		phonelist = phoneSelect.query(sql.toString(), params, new PhoneRowMapper());
	    }catch(EmptyResultDataAccessException ex){
		logger.error("Exception while getting the phones for the storeId " + storeId + " " + ex.getMessage());
		return new ArrayList<Phone>();
	    }
	}
	return phonelist;
    }

    @Override
    public List<PhoneModel> getPhoneModels(String storeId, PhoneModelSearchCriteria criteria)
	    throws DataAccessException {
	List<PhoneModel> phoneModellist = null;
	if (storeId != null){
	    NamedParameterJdbcTemplate phoneModelSelect = getNamedJdbcTemplate();
	    StringBuffer sql = new StringBuffer(getPhoneModelsByStoreIdSQL);
	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("STOR_ID", storeId);
	    if (criteria != null){
		if (criteria.getCarrier() != null && criteria.getCarrier().getCarrier() != null){
		    sql
			    .append("and p.CARR_LKUP_ID=(SELECT CARR_LKUP_ID FROM BST_ISE_SCH01.CARR_LKUP WHERE CARR=:CARR) ");
		    params.put("CARR", criteria.getCarrier().getCarrier());
		}
		if (criteria.getOperatingSystem() != null && criteria.getOperatingSystem().getOs() != null){
		    sql.append("and p.OS_LKUP_ID=(SELECT OS_LKUP_ID FROM BST_ISE_SCH01.OS_LKUP WHERE OS_NM=:OS_NM) ");
		    params.put("OS_NM", criteria.getOperatingSystem().getOs());
		}
	    }

	    sql.append("group by c.CARR,os.OS_NM,p.MAKE,p.MDL ");
	    sql.append("order by c.CARR,upper(os.OS_NM),upper(p.MAKE),upper(p.MDL) ");
	    try{
		phoneModellist = phoneModelSelect.query(sql.toString(), params, new PhoneModelRowMapper());
	    }catch(EmptyResultDataAccessException ex){
		logger.error("Exception while getting the checkOutCheckInHistory phoneModels for the storeId "
			+ storeId + " " + ex.getMessage());
		return new ArrayList<PhoneModel>();
	    }
	}
	return phoneModellist;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public long createPhone(Phone phone) throws DataAccessException {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("stor_id", phone.getStoreId());
	params.addValue("carr_lkup_id", phone.getCarrier().getId());
	params.addValue("os_lkup_id", phone.getOperatingSystem().getId());
	params.addValue("created_by", phone.getCreatedBy());
	params.addValue("created_on", phone.getCreatedOn());
	params.addValue("imei_esn", phone.getSerialNumber());
	params.addValue("make", phone.getMake());
	params.addValue("mdl", phone.getModelNumber());
	params.addValue("ph_cond", phone.getCondition() != null?phone.getCondition().name():null);
	params.addValue("ph_cond_cmnt", phone.getConditionComment());
	params.addValue("last_actn_usr_id", phone.getLastActionUserId());
	params.addValue("bby_sku", phone.getSku());
	params.addValue("del_reas", phone.getDeletedReason() != null?phone.getDeletedReason().name():null);
	params.addValue("actv_flg", phone.isActive());
	params.addValue("usr_frst_nm", phone.getFirstName());
	params.addValue("usr_last_nm", phone.getLastName());
	KeyHolder keyHolder = new GeneratedKeyHolder();
	try{
	    getNamedJdbcTemplate().update(createPhoneSQL, params, keyHolder, new String[] {"loanr_ph_id" });
	    long id = keyHolder.getKey().longValue();
	    phone.setId(id);
	    return id;
	}catch(org.springframework.dao.DuplicateKeyException ex){
	    // If a phone already exists in the same store with the same serial number, check if it is active or not.
	    // If is active, throw an exception. If it is inactive, change the insert into an update.
	    Phone existingPhone = getPhoneWithStorIdAndSerialNumberForUpdate(phone.getStoreId(), phone
		    .getSerialNumber());
	    if (existingPhone.isActive()){
		throw new DataAccessException(IseExceptionCodeEnum.DuplicateLoanerPhone, "Duplicate loaner phone");
	    }else{
		phone.setId(existingPhone.getId());
		updatePhone(phone);
		return phone.getId();
	    }
	}
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void updatePhone(Phone phone) throws DataAccessException {
	if (!phone.isActive() && phone.getLatestCheckOutCheckInHistory() != null
		&& phone.getLatestCheckOutCheckInHistory().isCheckedOut()){
	    throw new DataAccessException(IseExceptionCodeEnum.DeactivatedCheckedOutLoanerPhone,
		    "Checked out phones cannot be deactivated");
	}
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("loanr_ph_id", phone.getId());
	params.addValue("stor_id", phone.getStoreId());
	params.addValue("carr_lkup_id", phone.getCarrier().getId());
	params.addValue("os_lkup_id", phone.getOperatingSystem().getId());
	params.addValue("created_by", phone.getCreatedBy());
	params.addValue("created_on", phone.getCreatedOn());
	params.addValue("imei_esn", phone.getSerialNumber());
	params.addValue("make", phone.getMake());
	params.addValue("mdl", phone.getModelNumber());
	params.addValue("ph_cond", phone.getCondition() != null?phone.getCondition().name():null);
	params.addValue("ph_cond_cmnt", phone.getConditionComment());
	params.addValue("last_actn_usr_id", phone.getLastActionUserId());
	params.addValue("bby_sku", phone.getSku());
	params.addValue("del_reas", phone.getDeletedReason() != null?phone.getDeletedReason().name():null);
	params.addValue("actv_flg", phone.isActive());
	params.addValue("amended_by", phone.getModifiedBy());
	params.addValue("amended_on", phone.getModifiedOn());
	params.addValue("usr_frst_nm", phone.getFirstName());
	params.addValue("usr_last_nm", phone.getLastName());
	try{
	    getNamedJdbcTemplate().update(updatePhoneSQL, params);
	}catch(org.springframework.dao.DuplicateKeyException ex){
	    throw new DataAccessException(IseExceptionCodeEnum.DuplicateLoanerPhone, "Duplicate loaner phone");
	}
    }

    @Override
    public void deletePhone(long loanr_ph_id) throws DataAccessException {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("loanr_ph_id", loanr_ph_id);
	this.getNamedJdbcTemplate().update(deletePhoneSQL, params);
    }

    @Override
    public CheckOutCheckInHistory getCheckOutHistory(Phone phone) throws DataAccessException {
	CheckOutCheckInHistory checkOut = null;
	String sql = getCheckOutCheckInHistorySQL + " WHERE LOANR_PH_ID= ? and CHKOUT_STAT = 1";

	try{
	    checkOut = getJdbcTemplate().queryForObject(sql, new Object[] {phone.getId() },
		    new CheckOutCheckInHistoryRowMapper());
	}catch(EmptyResultDataAccessException ex){
	    logger.debug("Exception while getting the check out history for this phone id " + ex.getMessage());
	    return null;
	}

	return checkOut;
    }

    @Override
    public List<Carrier> getCarriers() throws DataAccessException {
	List<Carrier> carrierList = null;
	try{
	    carrierList = getJdbcTemplate().query(getCarriersSQL, new CarrierRowMapper());
	}catch(EmptyResultDataAccessException ex){
	    logger.error("Exception while getting the Carriers for the look up " + ex.getMessage());
	    return new ArrayList<Carrier>();
	}
	return carrierList;
    }

    @Override
    public List<OperatingSystem> getOperatingSystems() throws DataAccessException {
	List<OperatingSystem> osList = null;
	try{
	    osList = getJdbcTemplate().query(getOperatingSystemsSQL, new OperatingSystemRowMapper());
	}catch(EmptyResultDataAccessException ex){
	    logger.error("Exception while getting the Carriers for the look up " + ex.getMessage());
	    return new ArrayList<OperatingSystem>();
	}
	return osList;
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public long checkOutPhone(CheckOutCheckInHistory history) throws DataAccessException {

	if (isPhoneCheckedOut(history.getPhone().getId())){
	    throw new DataAccessException(IseExceptionCodeEnum.LoanerPhoneAlreadyCheckedOut);
	}
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("LOANR_PH_ID", history.getPhone().getId());
	params.addValue("CUST_FRST_NM", history.getFirstName());
	params.addValue("CUST_LAST_NM", history.getLastName());
	params.addValue("CUST_CNTCT_PH", history.getContactPhone());
	params.addValue("CUST_EMAIL", history.getContactEmail());
	params.addValue("CUST_SVC_ORD_ID", history.getServiceOrderNo());
	params.addValue("FLFLMNT_TYP", history.getFulfillmentType() != null?history.getFulfillmentType().name():null);
	params.addValue("GSP_ID", history.getGspNo());
	params.addValue("CHKOUT_TM", history.getCheckOutTime());
	params.addValue("CHKIN_TM", history.getCheckInTime());
	params.addValue("CHKOUT_COND", history.getCheckOutCondition() != null?history.getCheckOutCondition().name()
		:null);
	params.addValue("CHKOUT_COND_CMNT", history.getCheckOutConditionComment());
	params.addValue("CHKIN_COND", history.getCheckInCondition() != null?history.getCheckInCondition().name():null);
	params.addValue("CHKIN_COND_CMNT", history.getCheckInConditionComment());
	params.addValue("CHKOUT_USR_NM", history.getCheckOutEmployee());
	params.addValue("CHKIN_USR_NM", history.getCheckInEmployee());
	params.addValue("CHKOUT_DEP", history.getCheckOutDeposit());
	params.addValue("CHKIN_DEP", history.getCheckInDeposit());
	params.addValue("CHKOUT_STAT", history.isCheckedOut());
	params.addValue("CREATED_BY", history.getCreatedBy());
	params.addValue("CREATED_ON", history.getCreatedOn());
	params.addValue("AMENDED_BY", history.getModifiedBy());
	params.addValue("AMENDED_ON", history.getModifiedOn());
	params.addValue("REGISTER_ID", history.getRegisterId());
	params.addValue("TRANSACTION_NUMBER", history.getTransactionNumber());
	params.addValue("TRANSACTION_DATE", DateUtil.utilDateToSqlDate(history.getTransactionDate()));
	params.addValue("NOTES", history.getNotes());
	params.addValue("POS_STORE_ID", history.getPosStoreId());
	KeyHolder keyHolder = new GeneratedKeyHolder();
	this.getNamedJdbcTemplate().update(createCheckOutCheckInHistorySql, params, keyHolder,
		new String[] {"CHKOUT_CHKIN_HIST_ID" });
	long id = keyHolder.getKey().longValue();
	history.setId(id);
	logger.debug("check out history id...." + id);
	// update phone with checkout condition and condition comments
	updatePhone(history.getPhone());
	// insert phone extras records for this check out phone record
	List<CheckOutCheckInExtra> phoneExtrasList = history.getPhoneExtraList();
	if (phoneExtrasList != null){
	    for(CheckOutCheckInExtra phoneExtra: phoneExtrasList){
		// insert phone extra record corresponding to this history id
		createPhoneExtras(phoneExtra, id);
	    }

	}
	return id;
    }

    public String[] getDistinctMakes(String stor_id) throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("stor_id", stor_id);
	List<Map<String, Object>> list = getNamedJdbcTemplate().queryForList(
		"select distinct make from bst_ise_sch01.loanr_ph where stor_id = :stor_id", params);
	String result[] = new String[list.size()];
	for(int i = 0; i < list.size(); ++i){
	    result[i] = (String) list.get(i).get("MAKE");
	}
	return result;
    }

    public String[] getDistinctModels(String stor_id) throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("stor_id", stor_id);
	List<Map<String, Object>> list = getNamedJdbcTemplate().queryForList(
		"select distinct mdl from bst_ise_sch01.loanr_ph where stor_id = :stor_id", params);
	String result[] = new String[list.size()];
	for(int i = 0; i < list.size(); ++i){
	    result[i] = (String) list.get(i).get("MDL");
	}
	return result;
    }

    private void createPhoneExtras(CheckOutCheckInExtra phoneExtra, long historyId) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("CHKOUT_CHKIN_HIST_ID", historyId);
	params.addValue("PH_XTRA_TYP", phoneExtra.getName());
	params.addValue("CHKOUT_STAT", phoneExtra.isCheckOutStatus());
	params.addValue("CREATED_BY", phoneExtra.getCreatedBy());
	params.addValue("CREATED_ON", phoneExtra.getCreatedOn());

	this.getNamedJdbcTemplate().update(createPhoneExtraSQL, params);
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public void checkInPhone(CheckOutCheckInHistory history) throws DataAccessException {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("CHKIN_TM", history.getCheckInTime());
	params.addValue("CHKIN_COND", history.getCheckInCondition() != null?history.getCheckInCondition().name():null);
	params.addValue("CHKIN_COND_CMNT", history.getCheckInConditionComment());
	params.addValue("CHKIN_USR_NM", history.getCheckInEmployee());
	params.addValue("CHKIN_DEP", history.getCheckInDeposit());
	params.addValue("CHKOUT_STAT", history.isCheckedOut());
	params.addValue("AMENDED_BY", history.getModifiedBy());
	params.addValue("AMENDED_ON", history.getModifiedOn());
	params.addValue("CHKOUT_CHKIN_HIST_ID", history.getId());
	params.addValue("REGISTER_ID", history.getRegisterId());
	params.addValue("TRANSACTION_NUMBER", history.getTransactionNumber());
	params.addValue("TRANSACTION_DATE", DateUtil.utilDateToSqlDate(history.getTransactionDate()));
	params.addValue("NOTES", history.getNotes());
	params.addValue("POS_STORE_ID", history.getPosStoreId());
	this.getNamedJdbcTemplate().update(updateCheckOutCheckInHistorySql, params);
	logger.debug("check out/check in history id for check in phone...." + history.getId());
	// update phone with check in condition and condition comments
	updatePhone(history.getPhone());
	// update phone extras records for this check out phone record
	List<CheckOutCheckInExtra> phoneExtrasList = history.getPhoneExtraList();
	if (phoneExtrasList != null){
	    for(CheckOutCheckInExtra phoneExtra: phoneExtrasList){
		// update phone extra record corresponding to this history id
		updatePhoneExtras(phoneExtra);
	    }

	}

    }

    private void updatePhoneExtras(CheckOutCheckInExtra phoneExtra) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("CHKIN_STAT", phoneExtra.isCheckInStatus());
	params.addValue("AMENDED_BY", phoneExtra.getModifiedBy());
	params.addValue("AMENDED_ON", phoneExtra.getModifiedOn());
	params.addValue("PHONE_EXTRAS_ID", phoneExtra.getId());

	this.getNamedJdbcTemplate().update(updatePhoneExtraSQL, params);
    }

    class SimplePhoneRowMapper implements RowMapper<Phone> {

	@Override
	public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Phone phone = new Phone();
	    mapSimplePhoneFields(phone, rs, rowNum);
	    Carrier carrier = new Carrier();
	    carrier.setId(rs.getLong("CARR_LKUP_ID"));
	    phone.setCarrier(carrier);
	    OperatingSystem operatingSystem = new OperatingSystem();
	    operatingSystem.setId(rs.getLong("OS_LKUP_ID"));
	    phone.setOperatingSystem(operatingSystem);
	    return phone;
	}
    }

    class PhoneRowMapper implements RowMapper<Phone> {

	@Override
	public Phone mapRow(ResultSet rs, int rowNum) throws SQLException {
	    Phone phone = new Phone();
	    mapSimplePhoneFields(phone, rs, rowNum);
	    phone.setCarrier(mapCarrier(rs));
	    phone.setOperatingSystem(mapOperatingSystem(rs));
	    phone.setLatestCheckOutCheckInHistory(mapCheckOutCheckInHistory(rs));
	    return phone;
	}
    }

    private void mapSimplePhoneFields(Phone phone, ResultSet rs, int rowNum) throws SQLException {
	phone.setId(rs.getLong("LOANR_PH_ID"));
	phone.setStoreId(rs.getString("STOR_ID"));
	phone.setSerialNumber(rs.getString("IMEI_ESN"));
	phone.setMake(rs.getString("MAKE"));
	phone.setModelNumber(rs.getString("MDL"));
	if (rs.getString("PH_COND") != null){
	    phone.setCondition(LoanerPhoneCondition.valueOf(rs.getString("PH_COND")));
	}
	phone.setConditionComment(rs.getString("PH_COND_CMNT"));
	phone.setLastActionUserId(rs.getString("LAST_ACTN_USR_ID"));
	phone.setSku(rs.getString("BBY_SKU"));
	if (rs.getString("DEL_REAS") != null){
	    phone.setDeletedReason(LoanerPhoneDeletedReason.valueOf(rs.getString("DEL_REAS")));
	}
	phone.setActive(rs.getInt("ACTV_FLG") == 1);
	phone.setCreatedBy(rs.getString("CREATED_BY"));
	phone.setCreatedOn(rs.getDate("CREATED_ON"));
	phone.setModifiedBy(rs.getString("AMENDED_BY"));
	phone.setModifiedOn(rs.getDate("AMENDED_ON"));
	phone.setFirstName(rs.getString("USR_FRST_NM"));
	phone.setLastName(rs.getString("USR_LAST_NM"));
    }

    class CheckOutCheckInHistoryRowMapper implements RowMapper<CheckOutCheckInHistory> {

	@Override
	public CheckOutCheckInHistory mapRow(ResultSet rs, int rowNum) throws SQLException {
	    CheckOutCheckInHistory checkOutCheckIn = mapCheckOutCheckInHistory(rs);
	    if (checkOutCheckIn == null){
		return null;
	    }
	    Phone phone = new Phone();
	    phone.setId(rs.getLong("LOANR_PH_ID"));
	    checkOutCheckIn.setPhone(phone);
	    return checkOutCheckIn;
	}
    }

    class PhoneModelRowMapper implements RowMapper<PhoneModel> {

	@Override
	public PhoneModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	    PhoneModel phoneModel = new PhoneModel();
	    phoneModel.setMake(rs.getString("MAKE"));
	    phoneModel.setModel(rs.getString("MDL"));
	    phoneModel.setNoOfPhones(rs.getInt("NoOfPhones"));

	    Carrier carrier = new Carrier();
	    carrier.setCarrier(rs.getString("CARR"));
	    phoneModel.setCarrier(carrier);
	    OperatingSystem os = new OperatingSystem();
	    os.setOs(rs.getString("OS_NM"));
	    phoneModel.setOs(os);
	    return phoneModel;
	}
    }

    class CarrierRowMapper implements RowMapper<Carrier> {

	@Override
	public Carrier mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return mapCarrier(rs);
	}
    }

    class OperatingSystemRowMapper implements RowMapper<OperatingSystem> {

	@Override
	public OperatingSystem mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return mapOperatingSystem(rs);
	}
    }

    class CheckOutCheckInExtraRowMapper implements RowMapper<CheckOutCheckInExtra> {

	@Override
	public CheckOutCheckInExtra mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return mapCheckOutCheckInExtra(rs);
	}
    }

    private CheckOutCheckInHistory mapCheckOutCheckInHistory(ResultSet rs) throws SQLException {
	CheckOutCheckInHistory checkOutCheckIn = new CheckOutCheckInHistory();
	checkOutCheckIn.setId(rs.getLong("CHKOUT_CHKIN_HIST_ID"));
	checkOutCheckIn.setFirstName(rs.getString("CUST_FRST_NM"));
	checkOutCheckIn.setLastName(rs.getString("CUST_LAST_NM"));
	checkOutCheckIn.setContactPhone(rs.getString("CUST_CNTCT_PH"));
	checkOutCheckIn.setContactEmail(rs.getString("CUST_EMAIL"));
	checkOutCheckIn.setServiceOrderNo(rs.getString("CUST_SVC_ORD_ID"));
	if (rs.getString("FLFLMNT_TYP") != null){
	    checkOutCheckIn.setFulfillmentType(LoanerPhoneFulFillmentType.valueOf(rs.getString("FLFLMNT_TYP")));
	}
	checkOutCheckIn.setGspNo(rs.getString("GSP_ID"));
	checkOutCheckIn.setCheckInTime(rs.getTimestamp("CHKIN_TM"));
	checkOutCheckIn.setCheckOutTime(rs.getTimestamp("CHKOUT_TM"));
	if (rs.getString("CHKIN_COND") != null){
	    checkOutCheckIn.setCheckInCondition(LoanerPhoneCondition.valueOf(rs.getString("CHKIN_COND")));
	}
	if (rs.getString("CHKOUT_COND") != null){
	    checkOutCheckIn.setCheckOutCondition(LoanerPhoneCondition.valueOf(rs.getString("CHKOUT_COND")));
	}
	checkOutCheckIn.setCheckInConditionComment(rs.getString("CHKIN_COND_CMNT"));
	checkOutCheckIn.setCheckOutConditionComment(rs.getString("CHKOUT_COND_CMNT"));
	checkOutCheckIn.setCheckOutEmployee(rs.getString("CHKOUT_USR_NM"));
	checkOutCheckIn.setCheckInEmployee(rs.getString("CHKIN_USR_NM"));
	checkOutCheckIn.setCheckInDeposit(rs.getBigDecimal("CHKIN_DEP"));
	checkOutCheckIn.setCheckOutDeposit(rs.getBigDecimal("CHKOUT_DEP"));
	checkOutCheckIn.setCreatedBy(rs.getString("CREATED_BY"));
	checkOutCheckIn.setCreatedOn(rs.getTimestamp("CREATED_ON"));
	checkOutCheckIn.setModifiedBy(rs.getString("AMENDED_BY"));
	checkOutCheckIn.setModifiedOn(rs.getTimestamp("AMENDED_ON"));
	checkOutCheckIn.setCheckedOut(1 == rs.getInt("CHKOUT_STAT"));

	String registerId = rs.getString("REGISTER_ID");
	if (registerId != null){
	    checkOutCheckIn.setRegisterId(rs.getInt("REGISTER_ID"));
	}
	String transNum = rs.getString("TRANSACTION_NUMBER");
	if (transNum != null){
	    checkOutCheckIn.setTransactionNumber(rs.getInt("TRANSACTION_NUMBER"));
	}
	checkOutCheckIn.setTransactionDate(rs.getDate("TRANSACTION_DATE"));
	checkOutCheckIn.setNotes(rs.getString("NOTES"));
	checkOutCheckIn.setPosStoreId(rs.getString("POS_STORE_ID"));
	try{
	    checkOutCheckIn.setPhoneExtraList(getPhoneExtrasList(rs.getLong("CHKOUT_CHKIN_HIST_ID")));
	}catch(DataAccessException de){
	    logger.error("Failed to get phones extras for the check out/check-in history : " + de);
	    checkOutCheckIn.setPhoneExtraList(new ArrayList<CheckOutCheckInExtra>());
	}

	return checkOutCheckIn;
    }

    private List<CheckOutCheckInExtra> getPhoneExtrasList(long historyId) throws DataAccessException {
	List<CheckOutCheckInExtra> phoneExtrasList = null;
	try{
	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("CHKOUT_CHKIN_HIST_ID", historyId);
	    phoneExtrasList = this.getNamedJdbcTemplate().query(getPhoneExtrasSQL, params,
		    new CheckOutCheckInExtraRowMapper());
	}catch(EmptyResultDataAccessException ex){
	    logger.error("Exception while getting the phone extras list for the check out/check-in history id "
		    + ex.getMessage());
	    return new ArrayList<CheckOutCheckInExtra>();
	}
	return phoneExtrasList;
    }

    private Carrier mapCarrier(ResultSet rs) throws SQLException {
	Carrier carrier = new Carrier();
	carrier.setId(rs.getLong("CARR_LKUP_ID"));
	carrier.setCarrier(rs.getString("CARR"));
	carrier.setCarrierLoanerSku(rs.getString("CARR_LOANR_SKU"));
	return carrier;
    }

    private OperatingSystem mapOperatingSystem(ResultSet rs) throws SQLException {
	OperatingSystem os = new OperatingSystem();
	os.setId(rs.getLong("OS_LKUP_ID"));
	os.setOs(rs.getString("OS_NM"));
	os.setInstruction(rs.getString("INSTRCTN"));
	return os;
    }

    private CheckOutCheckInExtra mapCheckOutCheckInExtra(ResultSet rs) throws SQLException {
	CheckOutCheckInExtra phoneExtra = new CheckOutCheckInExtra();
	phoneExtra.setId(rs.getLong("PHONE_EXTRAS_ID"));
	phoneExtra.setName(rs.getString("PH_XTRA_TYP"));
	phoneExtra.setCheckOutStatus("1".equals(rs.getString("CHKOUT_STAT")));
	phoneExtra.setCheckInStatus("1".equals(rs.getString("CHKIN_STAT")));
	phoneExtra.setCreatedBy(rs.getString("CREATED_BY"));
	phoneExtra.setCreatedOn(rs.getDate("CREATED_ON"));
	phoneExtra.setModifiedBy(rs.getString("AMENDED_BY"));
	phoneExtra.setModifiedOn(rs.getDate("AMENDED_ON"));

	return phoneExtra;
    }

    @Override
    public Phone getPhone(long id) throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("loanr_ph_id", id);
	List<Phone> list = getNamedJdbcTemplate()
		.query(
			"select bst_ise_sch01.loanr_ph.* from bst_ise_sch01.loanr_ph loanr_ph where loanr_ph_id = :loanr_ph_id",
			params, new SimplePhoneRowMapper());
	if (list.size() > 0){
	    return list.get(0);
	}else{
	    return null;
	}
    }

    @Transactional(rollbackFor = Throwable.class)
    @Override
    public Phone getPhoneWithStorIdAndSerialNumberForUpdate(String storeId, String serialNumber)
	    throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("stor_id", storeId);
	params.put("imei_esn", serialNumber);
	List<Phone> list = getNamedJdbcTemplate().query(
		"select * from bst_ise_sch01.loanr_ph where stor_id = :stor_id and imei_esn = :imei_esn for update",
		params, new SimplePhoneRowMapper());
	if (list.size() > 0){
	    return list.get(0);
	}else{
	    return null;
	}
    }

    /**
     * This is for debugging H2 unit tests. It simply selects the contents of
     * one row in the chkout_chkin_hist table.
     */
    @Override
    public CheckOutCheckInHistory getCheckOutCheckInHistory(long id) throws DataAccessException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("CHKOUT_CHKIN_HIST_ID", id);
	List<CheckOutCheckInHistory> list = getNamedJdbcTemplate().query(
		"select * from bst_ise_sch01.chkout_chkin_hist where CHKOUT_CHKIN_HIST_ID = :CHKOUT_CHKIN_HIST_ID",
		params, new CheckOutCheckInHistoryRowMapper());
	if (list.size() > 0){
	    return list.get(0);
	}else{
	    return null;
	}
    }

    @Override
    public void updateHistory(CheckOutCheckInHistory history) throws DataAccessException {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("CHKIN_TM", history.getCheckInTime());
	params.addValue("CHKIN_COND", history.getCheckInCondition() != null?history.getCheckInCondition().name():null);
	params.addValue("CHKIN_COND_CMNT", history.getCheckInConditionComment());
	params.addValue("CHKIN_USR_NM", history.getCheckInEmployee());
	params.addValue("CHKIN_DEP", history.getCheckInDeposit());
	params.addValue("CHKOUT_STAT", history.isCheckedOut());
	params.addValue("AMENDED_BY", history.getModifiedBy());
	params.addValue("AMENDED_ON", history.getModifiedOn());
	params.addValue("CHKOUT_CHKIN_HIST_ID", history.getId());
	params.addValue("REGISTER_ID", history.getRegisterId());
	params.addValue("TRANSACTION_NUMBER", history.getTransactionNumber());
	params.addValue("TRANSACTION_DATE", DateUtil.utilDateToSqlDate(history.getTransactionDate()));
	params.addValue("NOTES", history.getNotes());
	params.addValue("POS_STORE_ID", history.getPosStoreId());
	this.getNamedJdbcTemplate().update(updateCheckOutCheckInHistorySql, params);
	logger.debug("check out/check in history id for updateHistory...." + history.getId());

    }

    public boolean isPhoneCheckedOut(long loanerPhoneId) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("loaner_phone_id", loanerPhoneId);

	SqlRowSet rowSet = this.getNamedJdbcTemplate().queryForRowSet(checkForCheckedOutPhoneSQL, params);
	return rowSet.first();
    }
}
