package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.WorkFlowTypeEnum;

/**
 * DB operations implementation class for DTA_XFER table
 * 
 * @author Sridhar Savaram
 * @version 1.0
 * @since
 */
@Repository("dataTransferDao")
public class DataTransferDaoImpl extends AbstractDao implements DataTransferDao {

    private static Logger logger = LoggerFactory.getLogger(DataTransferDaoImpl.class);
    private static final String SCHEMA = "BST_ISE_SCH01.";

    private final boolean isJunit;

    @Autowired
    @Qualifier("drpPropertyService")
    private DrpPropertyService drpPropertyService;

    private final String DTA_XFER_INSERT_QUERY = "insert into BST_ISE_SCH01.dta_xfer (dta_sharing_key, stor_id, xfer_flg, src_sys, cap_trans_id,workflow_type, "
	    + "created_by, created_on) values (:dataSharingKey, :storeId, :transferFlag, :sourceSystem, :capTransId,:workFlowType, "
	    + ":createdBy, sysdate)";

    private final String DTA_XFER_SELECT_QUERY = "SELECT dta_sharing_key, stor_id, xfer_flg, src_sys, cap_trans_id,created_by,"
	    + " created_on FROM BST_ISE_SCH01.dta_xfer WHERE dta_sharing_key = ?";

    private final String DTA_XFER_SELECT_CREATED_DATE_QUERY = "select dt.created_on from bst_ise_sch01.dta_xfer dt where dt.dta_sharing_key   = ?"
	    + " and dt.stor_id=? and dt.created_by=?";

    private final String DTA_XFER_SUMMARY_LIST_QUERY = "SELECT dta_sharing_key,cust_frst_nm, cust_last_nm,cust_ph_nbr,created_by,created_on,amended_by,amended_on,xfer_flg FROM ("
	    + "SELECT dt.dta_sharing_key, ba.cust_frst_nm, ba.cust_last_nm, ba.cust_ph_nbr, "
	    + "dt.created_by, dt.created_on, dt.amended_by, dt.amended_on, dt.xfer_flg, 1 priority FROM BST_ISE_SCH01.dta_xfer dt, "
	    + "BST_ISE_SCH01.bbym_acct ba WHERE dt.dta_sharing_key = ba.dta_sharing_key AND dt.stor_id = ?  AND (dt.created_on  > sysdate - (?/24) OR "
	    + " (dt.amended_on is not null AND dt.amended_on > sysdate - (?/24))) AND dt.workflow_type = ? UNION "
	    + "SELECT dt.dta_sharing_key, ca.cust_frst_nm, ca.cust_last_nm, ca.cust_ph_nbr, "
	    + "dt.created_by, dt.created_on, dt.amended_by, dt.amended_on, dt.xfer_flg,0 priority FROM BST_ISE_SCH01.dta_xfer dt, "
	    + "BST_ISE_SCH01.carr_acct ca WHERE dt.dta_sharing_key = ca.dta_sharing_key AND dt.stor_id = ?  AND (dt.created_on  > sysdate - (?/24) OR "
	    + " (dt.amended_on is not null AND dt.amended_on > sysdate - (?/24))) AND dt.workflow_type = ? "
	    + ") order by priority";

    private final String DTA_XFER_SUMMARY_SELECT_QUERY = "SELECT dt.dta_sharing_key, ba.cust_frst_nm, ba.cust_last_nm, ba.cust_ph_nbr, "
	    + "dt.created_by, dt.created_on, dt.amended_by, dt.amended_on, dt.xfer_flg FROM BST_ISE_SCH01.dta_xfer dt, "
	    + "BST_ISE_SCH01.bbym_acct ba WHERE dt.dta_sharing_key = ba.dta_sharing_key AND dt.dta_sharing_key  = ?";

    private final String DTA_XFER_SUMMARY_UPDATE_TRANSFER_FLAG = "UPDATE BST_ISE_SCH01.dta_xfer SET xfer_flg = 1, amended_on = sysdate WHERE dta_sharing_key  = :dataSharingKey";

    private final String DTA_XFER_SUMMARY_UPDATE_AMMENDED_ON = "UPDATE BST_ISE_SCH01.dta_xfer SET  amended_on = sysdate WHERE dta_sharing_key  = :dataSharingKey";

    private final String DTA_XFER_SUMMARY_UPDATE = "UPDATE BST_ISE_SCH01.dta_xfer SET cap_trans_id = :capTransId, amended_by = :modifiedBy,"
	    + "amended_on = sysdate WHERE dta_sharing_key  = :dataSharingKey";

    public DataTransferDaoImpl() {
	isJunit = false;
    }

    public DataTransferDaoImpl(boolean junit) {
	this.isJunit = junit;
    }

    // remove SCHEMA=="" for testing
    private String removeSchema(String sql) {
	if (isJunit == false || StringUtils.isEmpty(sql) || StringUtils.isEmpty(SCHEMA)){
	    return sql;
	}

	return sql.replaceAll(SCHEMA, "");
    }

    // replace sql for testing
    private String replaceForJunitTesting(String sql) {
	if (isJunit == false || StringUtils.isEmpty(sql) || StringUtils.isEmpty(SCHEMA)){
	    return sql;
	}

	return sql.replaceAll("sysdate - \\(\\?/24\\)", "DATEADD('hh', ?, sysdate)");
    }

    /**
     * Persists data transfer into database table: DTA_XFER.
     */
    @Override
    public void persistDataTransfer(String dataSharingKey, String storeId, String userId, boolean transferFlag,
	    String sourceSystem, String capTransId, String workFlowType) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", dataSharingKey);
	params.put("storeId", storeId);
	params.put("transferFlag", transferFlag?1:0);
	params.put("sourceSystem", sourceSystem);
	params.put("capTransId", capTransId);
	params.put("createdBy", userId);
	params.put("workFlowType", workFlowType);
	getNamedJdbcTemplate().update(removeSchema(DTA_XFER_INSERT_QUERY), params);
    }

    @Override
    public List<DataTransferSummary> getDataTransferSummaryList(String storeId) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	int interval = 1;// One hour by default
	try{

	    // for testing
	    if (isJunit){
		interval *= (-1);
	    }

	    String intervalStr = drpPropertyService.getProperty("GSP_CANCEL_DATA_TRANSFER_INTERVAL");
	    interval = Integer.parseInt(intervalStr);
	}catch(ServiceException e){
	    logger.error("GSP_CANCEL_DATA_TRANSFER_INTERVAL is not defined in ISE_PROPERTIES table");
	}catch(NumberFormatException e){
	    logger.error("GSP_CANCEL_DATA_TRANSFER_INTERVAL is not a number in ISE_PROPERTIES table");
	}

	return getJdbcTemplate().query(removeSchema(replaceForJunitTesting(DTA_XFER_SUMMARY_LIST_QUERY)),// for
		// testing
		new Object[] {storeId, new Integer(interval), new Integer(interval),
			WorkFlowTypeEnum.BEAST_TRANSFER.name(), storeId, new Integer(interval), new Integer(interval),
			WorkFlowTypeEnum.BEAST_TRANSFER.name() }, new DataTransferRowMapper());

    }

    private class DataTransferRowMapper implements RowMapper<DataTransferSummary> {

	@Override
	public DataTransferSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
	    DataTransferSummary dataTransferSummary = new DataTransferSummary();
	    dataTransferSummary.setDataSharingKey(rs.getString("dta_sharing_key"));
	    dataTransferSummary.setFirstName(rs.getString("cust_frst_nm"));
	    dataTransferSummary.setLastName(rs.getString("cust_last_nm"));
	    dataTransferSummary.setPhoneNumber(rs.getString("cust_ph_nbr"));
	    dataTransferSummary.setTransferFlag(rs.getInt("xfer_flg") == 1?true:false);
	    // dataTransferSummary.setWorkFlowType(WorkFlowTypeEnum.valueOf(rs.getString("workflow_type")));
	    dataTransferSummary.setCreatedBy(rs.getString("created_by"));
	    dataTransferSummary.setCreatedOn(rs.getDate("created_on"));
	    dataTransferSummary.setModifiedBy(rs.getString("amended_by"));
	    dataTransferSummary.setModifiedOn(rs.getDate("amended_on"));
	    return dataTransferSummary;
	}
    }

    private class DataTransferMapper implements RowMapper<DataTransferSummary> {

	@Override
	public DataTransferSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
	    DataTransferSummary dataTransferSummary = new DataTransferSummary();
	    dataTransferSummary.setDataSharingKey(rs.getString("dta_sharing_key"));
	    dataTransferSummary.setStoreId(rs.getString("stor_id"));
	    dataTransferSummary.setCapTransId(rs.getString("cap_trans_id"));
	    dataTransferSummary.setSrcSys(rs.getString("src_sys"));
	    dataTransferSummary.setTransferFlag(rs.getInt("xfer_flg") == 1?true:false);
	    dataTransferSummary.setCreatedBy(rs.getString("created_by"));
	    dataTransferSummary.setCreatedOn(rs.getDate("created_on"));
	    return dataTransferSummary;
	}
    }

    private class CreatedOnRowMapper implements RowMapper<Date> {

	@Override
	public Date mapRow(ResultSet rs, int rowNum) throws SQLException {
	    return rs.getTimestamp("created_on");
	}
    }

    @Override
    public Date getDataTransferCreatedTime(String dataSharingKey, String storeId, String userId)
	    throws DataAccessException, EmptyResultDataAccessException, RuntimeException {

	return getJdbcTemplate().queryForObject(removeSchema(DTA_XFER_SELECT_CREATED_DATE_QUERY),
		new Object[] {dataSharingKey, storeId, userId }, new CreatedOnRowMapper());
    }

    @Override
    public DataTransferSummary getDataTransferRow(String dataSharingKey) throws DataAccessException {
	return getJdbcTemplate().queryForObject(removeSchema(DTA_XFER_SELECT_QUERY), new Object[] {dataSharingKey },
		new DataTransferMapper());

    }

    @Override
    public DataTransferSummary getDataTransferSummary(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {

	return getJdbcTemplate().queryForObject(removeSchema(DTA_XFER_SUMMARY_SELECT_QUERY),
		new Object[] {dataSharingKey }, new DataTransferRowMapper());
    }

    @Override
    public void updateTransferFlag(String dataSharingKey) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", dataSharingKey);
	getNamedJdbcTemplate().update(removeSchema(DTA_XFER_SUMMARY_UPDATE_TRANSFER_FLAG), params);
    }

    @Override
    public void updateAmmendedOn(String dataSharingKey) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", dataSharingKey);
	getNamedJdbcTemplate().update(removeSchema(DTA_XFER_SUMMARY_UPDATE_AMMENDED_ON), params);
    }

    @Override
    public void update(String dataSharingKey, String capTransId, String modifiedBy) throws DataAccessException,
	    RuntimeException {
	logger.debug(">>> in update datatransfer table : " + dataSharingKey);
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", dataSharingKey);
	params.put("capTransId", capTransId);
	params.put("modifiedBy", modifiedBy);
	getNamedJdbcTemplate().update(removeSchema(DTA_XFER_SUMMARY_UPDATE), params);
    }
}
