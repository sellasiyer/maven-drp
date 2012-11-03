/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.beanutils.BeanComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDailyGoal;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeDistribution;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget.Type;

/**
 * Implementation of score board DAO operations.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 25
 */
@Repository("scoreboardDao")
public class ScoreboardDaoImpl extends AbstractDao implements ScoreboardDao {

    private static Logger logger = LoggerFactory.getLogger(ScoreboardDaoImpl.class);

    @Autowired
    private ConfigDaoImpl configDao;

    private static final String UNIT_PRICE_REQUIRED = "UNIT_PRICE_REQUIRED";
    private static final Map<String, String> mapSQL = loadSQLFromXmlFile(ScoreboardDaoImpl.class.getResource(
	    "Scoreboard-sql.xml").toString());
    private static final String SQL_SELECT_SALES_AND_RETURN_ITEMS = getSQLByName("SQL_SELECT_SALES_AND_RETURN_ITEMS");
    private static final String SQL_SELECT_EMPLOYEE_SHIFT_NOTIONAL_MARGIN = getSQLByName("SQL_SELECT_EMPLOYEE_SHIFT_NOTIONAL_MARGIN");
    private static final String SQL_SELECT_STORE_NOTIONAL_MARGIN = getSQLByName("SQL_SELECT_STORE_NOTIONAL_MARGIN");
    private static final String SQL_SELECT_INITIALIZED_EMPLOYEE_COUNT = getSQLByName("SQL_SELECT_INITIALIZED_EMPLOYEE_COUNT");
    private static final String SQL_DELETE_STORE_DAILY_TRANS = getSQLByName("SQL_DELETE_STORE_DAILY_TRANS");
    private static final String SQL_UPDATE_STORE_EMP_SHIFT = getSQLByName("SQL_UPDATE_STORE_EMP_SHIFT");
    private static final String SQL_INSERT_TRANSACTION = getSQLByName("SQL_INSERT_TRANSACTION");
    private static final String SQL_SELECT_GOAL = getSQLByName("SQL_SELECT_GOAL");
    private static final String SQL_GOAL_INSERT = getSQLByName("SQL_GOAL_INSERT");
    private static final String SQL_GOAL_UPDATE = getSQLByName("SQL_GOAL_UPDATE");
    private static final String SQL_DELETE_TRANS_BY_EMP = getSQLByName("SQL_DELETE_TRANS_BY_EMP");
    private static final String SQL_DELETE_DLY_EMP = getSQLByName("SQL_DELETE_DLY_EMP");
    private static final String SQL_SELECT_EMP_SHIFT = getSQLByName("SQL_SELECT_EMP_SHIFT");
    private static final String SQL_INSERT_EMPLOYEE = getSQLByName("SQL_INSERT_EMPLOYEE");
    private static final String SQL_INSERT_EMPLOYEE_SHIFT = getSQLByName("SQL_INSERT_EMPLOYEE_SHIFT");
    private static final String SQL_SELECT_DAILY_EMP_SHIFT_LIST = getSQLByName("SQL_SELECT_DAILY_EMP_SHIFT_LIST");
    private static final String SQL_UPDATE_EMP_SHIFT = getSQLByName("SQL_UPDATE_EMP_SHIFT");
    private static final String SQL_SELECT_STORE_DETAILS = getSQLByName("SQL_SELECT_STORE_DETAILS");
    private static final String SQL_SELECT_EMP_DETAILS = getSQLByName("SQL_SELECT_EMP_DETAILS");
    private static final String SQL_DELETE_TRANSACTION = getSQLByName("SQL_DELETE_TRANSACTION");
    private static final String SQL_EDIT_EMPLOYEE_SALES = getSQLByName("SQL_EDIT_EMPLOYEE_SALES");
    private static final String SQL_EDIT_STORE_RETURNS = getSQLByName("SQL_EDIT_STORE_RETURNS");

    private static final SimpleDateFormat sdfDateTime = new SimpleDateFormat("MM/dd/yyyy HH:mm");
    private static final SimpleDateFormat sdfDate = new SimpleDateFormat("MM/dd/yyyy");

    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_TOTAL_CONNECTIONS = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_TOTAL_CONNECTIONS");
    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_POST_PAID_GSP = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_POST_PAID_GSP");
    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_POST_PAID_ACCESSORIES = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_POST_PAID_ACCESSORIES");
    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_NO_CONTRACT_ACTIVATIONS = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_NO_CONTRACT_ACTIVATIONS");
    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_NOTIONAL_MARGIN = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_NOTIONAL_MARGIN");
    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_GSP = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_GSP");
    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_ACCESSORIES = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_ACCESSORIES");
    private static final String SQL_SELECT_WIDGET_SWAS_MOBILE_UPGRADE_CHECKS = getSQLByName("SQL_SELECT_WIDGET_SWAS_MOBILE_UPGRADE_CHECKS");

    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_LAPTOPS = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_LAPTOPS");
    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_DESKTOPS_ALL_IN_ONES = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_DESKTOPS_ALL_IN_ONES");
    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_TABLETS = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_TABLETS");
    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_EREADERS = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_EREADERS");
    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_GEEK_SQUAD = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_GEEK_SQUAD");
    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_ACCESSORIES = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_ACCESSORIES");
    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_CONTENT = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_CONTENT");
    private static final String SQL_SELECT_WIDGET_SWAS_COMPUTING_CONNECTIONS = getSQLByName("SQL_SELECT_WIDGET_SWAS_COMPUTING_CONNECTIONS");

    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_CONNECTIONS = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_CONNECTIONS");
    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_IPHONE_CONNECTIONS = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_IPHONE_CONNECTIONS");
    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_NO_CONTRACT_CONNECTIONS = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_NO_CONTRACT_CONNECTIONS");
    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_BROADBAND_CONNECTIONS = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_BROADBAND_CONNECTIONS");
    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_GSP = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_GSP");
    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_ACCESSORIES = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_ACCESSORIES");
    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_NO_CONTRACT_ACTIVATIONS = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_NO_CONTRACT_ACTIVATIONS");
    private static final String SQL_SELECT_WIDGET_SAS_MOBILE_UPGRADE_CHECKS = getSQLByName("SQL_SELECT_WIDGET_SAS_MOBILE_UPGRADE_CHECKS");
    private static final String SQL_SELECT_STORE_EMPLOYEE_SCHEDULE = getSQLByName("SQL_SELECT_STORE_EMPLOYEE_SCHEDULE");

    private static final String SCOREBOARD_MOBILE_TC_WIDGET_LIST = "SCOREBOARD_MOBILE_TC_WIDGET_LIST";
    private static final String SCOREBOARD_MOBILE_MP3NM_WIDGET_LIST = "SCOREBOARD_MOBILE_MP3NM_WIDGET_LIST";
    private static final String SCOREBOARD_COMPUTING_TL_WIDGET_LIST = "SCOREBOARD_COMPUTING_TL_WIDGET_LIST";
    private static final String SCOREBOARD_COMPUTING_DSKTP_WIDGET_LIST = "SCOREBOARD_COMPUTING_DSKTP_WIDGET_LIST";
    private static final String SCOREBOARD_PENTAGON_STORE_TYP = "SCOREBOARD_PENTAGON_STORE_TYP";
    private static final String PENTAGON_MOBILE = "PENTAGON_MOBILE";
    private static final String SCOREBOARD_COMPUTING_GS_WIDGET_LIST = "SCOREBOARD_COMPUTING_GS_WIDGET_LIST";
    private static final String NOTIONAL_MARGIN = "Notional Margin";

    private static final String SQL_EMPLOYEE_SHIFT_TRANSACTION_COUNT = getSQLByName("SQL_EMPLOYEE_SHIFT_TRANSACTION_COUNT");

    @Override
    public Long getInitializedEmployeeCount(String storeId, Date localDate) {
	return getJdbcTemplate().queryForLong(SQL_SELECT_INITIALIZED_EMPLOYEE_COUNT,
		new Object[] {sdfDateTime.format(localDate), storeId });
    }

    @Override
    public void initializeStoreData(String userId, String storeId) {
	logger.debug("initializeStoreData: " + storeId);
	int count = getJdbcTemplate().update(SQL_DELETE_STORE_DAILY_TRANS, new Object[] {storeId });
	logger.debug("Daily transaction deleted records. " + count);
	count = getJdbcTemplate().update(SQL_UPDATE_STORE_EMP_SHIFT, new Object[] {userId, storeId });
	logger.debug("Employee shift updated records. " + count);
    }

    @Override
    public List<ScoreboardEmployeeNotionalMargin> getEmployeeShiftNotionalMarginList(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate) {
	String dateTime = sdfDateTime.format(localDate);
	String date = sdfDate.format(localDate);
	String businessSource = storeType.toString() + "_" + operationType.toString();
	List<ScoreboardEmployeeNotionalMargin> list = getJdbcTemplate().query(
		SQL_SELECT_EMPLOYEE_SHIFT_NOTIONAL_MARGIN,
		new Object[] {dateTime, dateTime, dateTime, date, storeId, businessSource },
		new RowMapper<ScoreboardEmployeeNotionalMargin>() {

		    @Override
		    public ScoreboardEmployeeNotionalMargin mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScoreboardEmployeeNotionalMargin empNtlMrgn = new ScoreboardEmployeeNotionalMargin();
			empNtlMrgn.setCurrentValue(rs.getInt("CURRENT_VALUE"));
			float workedHours = rs.getFloat("TOTAL_HOURS_WORKED");
			if (0 == Float.compare(workedHours, 0f)){
			    empNtlMrgn.setProductivity(0);
			}else{
			    empNtlMrgn.setProductivity(Math.round(empNtlMrgn.getCurrentValue()
				    / rs.getFloat("TOTAL_HOURS_WORKED")));
			}
			ScoreboardEmployeeShift empShift = new ScoreboardEmployeeShift();
			empShift.setBusinessSourceId(rs.getLong("BSNS_SRC_ID"));
			empShift.setEmpBBYId(rs.getString("BBY_EMP_ID"));
			empShift.setEmpDailyNtlMrgnId(rs.getLong("NTL_MRGN_DLY_EMP_ID"));
			empShift.setEmpFullName(rs.getString("EMP_NM"));
			empShift.setEmpNtlMrgnId(rs.getLong("NTL_MRGN_EMP_ID"));
			empShift.setShiftEndTime(rs.getTimestamp("SHIFT_END_TM"));
			empShift.setShiftStartTime(rs.getTimestamp("SHIFT_BEG_TM"));
			empShift.setStoreId(rs.getString("STOR_ID"));
			//empShift.setPrimaryZone(configDao.getConfigItemById(rs.getString("PRIMARY_ZONE_ID")));
			//empShift.setSecondaryZone(configDao.getConfigItemById(rs.getString("SECONDARY_ZONE_ID")));
			empNtlMrgn.setEmployeeShift(empShift);
			return empNtlMrgn;
		    }
		});
	return list;
    }

    @Override
    public ScoreboardNotionalMargin getStoreNotionalMargin(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, Date localDate) {
	String dateTime = sdfDateTime.format(localDate);
	String businessSource = storeType.toString() + "_" + operationType.toString();
	String businessSourceWgt = storeType.toString() + "_" + operationType.toString();

	if (storeType == ScoreboardStoreType.SWAS && operationType == ScoreboardOperationType.MOBILE){
	    List<Config> list = configDao.getConfigItemsByType("SCOREBOARD_PENTAGON_STORE_TYP");
	    if (list != null){
		for(Config cfg: list){
		    if (cfg.getParamName().equalsIgnoreCase(storeId)){
			businessSourceWgt = "PENTAGON_MOBILE";
			break;
		    }
		}
	    }
	}

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("stor_id", storeId, java.sql.Types.VARCHAR);
	params.addValue("stor_typ_nm", storeType.toString(), java.sql.Types.VARCHAR);
	params.addValue("bsns_src_nm", businessSource, java.sql.Types.VARCHAR);
	params.addValue("bsns_src_nm_wgt", businessSourceWgt, java.sql.Types.VARCHAR);
	params.addValue("current_tm", dateTime, java.sql.Types.VARCHAR);

	SqlRowSet rowSet = getNamedJdbcTemplate().queryForRowSet(SQL_SELECT_STORE_NOTIONAL_MARGIN, params);
	Map<String, Double> mapValues = new HashMap<String, Double>();

	while(rowSet.next()){
	    mapValues.put(rowSet.getString("NTL_MRGN_TYPE"), rowSet.getDouble("NTL_MRGN_VALUE"));
	}

	ScoreboardNotionalMargin nm = new ScoreboardNotionalMargin();

	nm.setCurrentValue((int) Math.round(mapValues.get("CURRENT_VALUE") == null?0:mapValues.get("CURRENT_VALUE")));
	nm.setTargetValue((int) Math.round(mapValues.get("GOAL") == null?0:mapValues.get("GOAL")));
	nm.setNextHourPercentage((int) Math.round(mapValues.get("STORE_TRAFFIC_PERCENT") == null?0:100 * mapValues
		.get("STORE_TRAFFIC_PERCENT")));

	Double totalWorkedHours = mapValues.get("TOTAL_HOURS_WORKED_ALL_EMPS") == null?0:mapValues
		.get("TOTAL_HOURS_WORKED_ALL_EMPS");

	if (Double.compare(totalWorkedHours, 0) == 0){
	    nm.setProductivity(0);
	}else{
	    nm.setProductivity((int) Math.round(nm.getCurrentValue() / totalWorkedHours));
	}

	return nm;
    }

    @Override
    public List<ScoreboardDataItem> getEmployeeDetails(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, Long ntlMrgnDlyEmpId) {

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("stor_id", storeId, java.sql.Types.VARCHAR);
	params.addValue("bsns_src_nm", storeType.toString() + "_" + operationType.toString(), java.sql.Types.VARCHAR);
	params.addValue("ntl_mrgn_dly_emp_id", ntlMrgnDlyEmpId, java.sql.Types.NUMERIC);

	List<ScoreboardDataItem> list = getNamedJdbcTemplate().query(SQL_SELECT_EMP_DETAILS, params,
		new RowMapper<ScoreboardDataItem>() {

		    public ScoreboardDataItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScoreboardDataItem item = new ScoreboardDataItem();
			item.setName(rs.getString("DESCRIPTION"));
			item.setNtlMarginValueId(rs.getLong("NTL_MRGN_VAL_ID"));
			item.setSalesQuantity(rs.getInt("SALES_QTY"));
			item.setSalesTotal(rs.getBigDecimal("SALES_TOTAL"));

			String v = rs.getString("PARAM_VAL");
			if (UNIT_PRICE_REQUIRED.equals(v)){
			    item.setUnitPriceRequired(true);
			}else{
			    item.setUnitPriceRequired(false);
			}

			return item;
		    }
		});
	return list;
    }

    @Override
    public List<ScoreboardDataItem> getStoreDetails(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) {

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("stor_id", storeId, java.sql.Types.VARCHAR);
	params.addValue("bsns_src_nm", storeType.toString() + "_" + operationType.toString(), java.sql.Types.VARCHAR);

	List<ScoreboardDataItem> list = getNamedJdbcTemplate().query(SQL_SELECT_STORE_DETAILS, params,
		new RowMapper<ScoreboardDataItem>() {

		    public ScoreboardDataItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScoreboardDataItem item = new ScoreboardDataItem();
			item.setName(rs.getString("DESCRIPTION"));
			item.setNtlMarginValueId(rs.getLong("NTL_MRGN_VAL_ID"));
			item.setSalesQuantity(rs.getInt("SALES_QTY"));
			item.setSalesTotal(rs.getBigDecimal("SALES_TOTAL"));
			item.setReturnQuantity(rs.getInt("RETURNS_QTY"));
			item.setReturnTotal(rs.getBigDecimal("RETURNS_TOTAL"));

			String v = rs.getString("PARAM_VAL");
			if (UNIT_PRICE_REQUIRED.equals(v)){
			    item.setUnitPriceRequired(true);
			}else{
			    item.setUnitPriceRequired(false);
			}

			return item;
		    }
		});
	return list;
    }

    @Override
    public List<ScoreboardDataItem> getCategoryItems(String storeId, Long ntlMrgnDlyEmpId, String businessSource,
	    String categoryType) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("storeId", storeId, java.sql.Types.VARCHAR);
	params.addValue("businessSource", businessSource, java.sql.Types.VARCHAR);
	params.addValue("ntlMrgnDlyEmpId", ntlMrgnDlyEmpId, java.sql.Types.NUMERIC);
	params.addValue("categoryType", categoryType, java.sql.Types.VARCHAR);

	List<ScoreboardDataItem> list = getNamedJdbcTemplate().query(SQL_SELECT_SALES_AND_RETURN_ITEMS, params,
		new RowMapper<ScoreboardDataItem>() {

		    public ScoreboardDataItem mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScoreboardDataItem item = new ScoreboardDataItem();
			item.setName(rs.getString("DESCRIPTION"));
			item.setNtlMarginValueId(rs.getLong("NTL_MRGN_VAL_ID"));
			item.setNtlMarginValue(rs.getBigDecimal("NTL_MRGN_ITEM_VAL"));
			item.setSalesQuantity(rs.getInt("SALE_QTY"));
			item.setSalesTotal(rs.getBigDecimal("SALE_AMT"));
			item.setReturnQuantity(rs.getInt("RETURN_QTY"));
			item.setReturnTotal(rs.getBigDecimal("RETURN_AMT"));
			item.setId(rs.getLong("CONFIG_ID"));
			return item;
		    }
		});
	return list;
    }

    @Override
    public int[] insertTransaction(String storeId, Long ntlMrgnDlyEmpId, Long tranType, List<ScoreboardDataItem> items) {

	MapSqlParameterSource[] batchParams = new MapSqlParameterSource[items.size()];
	int idx = 0;

	for(ScoreboardDataItem item: items){

	    MapSqlParameterSource params = new MapSqlParameterSource();
	    params.addValue("TRAN_TYP_ID", tranType, java.sql.Types.NUMERIC);
	    params.addValue("STOR_ID", storeId, java.sql.Types.VARCHAR);
	    params.addValue("NTL_MRGN_DLY_EMP_ID", ntlMrgnDlyEmpId, java.sql.Types.NUMERIC);
	    params.addValue("NTL_MRGN_VAL_ID", item.getNtlMarginValueId(), java.sql.Types.NUMERIC);
	    params.addValue("QTY", item.getEditQuantity(), java.sql.Types.NUMERIC);
	    params.addValue("AMT", item.getEditUnitPrice(), java.sql.Types.NUMERIC);
	    params.addValue("TRAN_DT", item.getTransactionDate(), java.sql.Types.TIMESTAMP);
	    params.addValue("CREATED_BY", item.getCreatedBy(), java.sql.Types.VARCHAR);

	    batchParams[idx] = params;
	    idx++;
	}

	return getNamedJdbcTemplate().batchUpdate(SQL_INSERT_TRANSACTION, batchParams);

    }

    @Override
    public List<ScoreboardDailyGoal> getDailyGoalList(String storeId, String businessSource) {

	List<ScoreboardDailyGoal> list = getJdbcTemplate().query(SQL_SELECT_GOAL,
		new Object[] {storeId, businessSource, businessSource }, new RowMapper<ScoreboardDailyGoal>() {

		    @Override
		    public ScoreboardDailyGoal mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScoreboardDailyGoal sdg = new ScoreboardDailyGoal();
			String paramValue = rs.getString("PARAM_VAL");
			if (paramValue != null){
			    // PERCENTAGE,100,100,200
			    String[] attr = paramValue.split(",");
			    if (attr.length > 0){
				sdg.setGoalType(ScoreboardWidget.Type.valueOf(attr[0]));
			    }
			    if (attr.length > 1){
				sdg.setDefaultValue(Long.parseLong(attr[1]));
			    }
			    if (attr.length > 2){
				sdg.setMinValue(Long.parseLong(attr[2]));
			    }
			    if (attr.length > 3){
				sdg.setMaxValue(Long.parseLong(attr[3]));
			    }
			}

			sdg.setName(rs.getString("PARAM_NAME"));
			Double value = rs.getDouble("GOAL_VAL");
			if (sdg.getGoalType() == Type.PERCENTAGE){
			    value = 100 * value;
			}
			sdg.setGoalValue(Math.round(value));
			sdg.setId(rs.getLong("NTL_MRGN_STOR_GOAL_ID"));
			if (rs.wasNull()){
			    sdg.setId(null);
			}
			sdg.setGoalTypeId(rs.getLong("CONFIG_ID"));
			return sdg;
		    }

		});

	return list;
    }

    @Override
    public ScoreboardDailyGoal persistGoal(String storeId, Long bsnSourceId, ScoreboardDailyGoal goal) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	Double value = goal.getGoalValue().doubleValue();

	if (goal.getGoalType() == Type.PERCENTAGE){
	    value = value / 100;
	}
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("STOR_ID", storeId, java.sql.Types.VARCHAR);
	params.addValue("BSN_SRC_ID", bsnSourceId, java.sql.Types.NUMERIC);
	params.addValue("GOAL_TYP_ID", goal.getGoalTypeId(), java.sql.Types.NUMERIC);
	params.addValue("GOAL_VAL", value, java.sql.Types.DOUBLE);
	params.addValue("CREATED_BY", goal.getCreatedBy(), java.sql.Types.VARCHAR);

	getNamedJdbcTemplate().update(SQL_GOAL_INSERT, params, keyHolder, new String[] {"NTL_MRGN_STOR_GOAL_ID" });
	goal.setId(keyHolder.getKey().longValue());
	return goal;
    }

    @Override
    public ScoreboardDailyGoal updateGoal(ScoreboardDailyGoal goal) {

	Double value = goal.getGoalValue().doubleValue();

	if (goal.getGoalType() == Type.PERCENTAGE){
	    value = value / 100;
	}

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("GOAL_VAL", value, java.sql.Types.NUMERIC);
	params.addValue("AMENDED_BY", goal.getModifiedBy(), java.sql.Types.VARCHAR);
	params.addValue("NTL_MRGN_STOR_GOAL_ID", goal.getId(), java.sql.Types.NUMERIC);

	getNamedJdbcTemplate().update(SQL_GOAL_UPDATE, params);
	return goal;
    }

    @Override
    public void deleteEmployeeShift(ScoreboardEmployeeShift employee) {
	getJdbcTemplate().update(SQL_DELETE_TRANS_BY_EMP, new Object[] {employee.getEmpDailyNtlMrgnId() });
	getJdbcTemplate().update(SQL_DELETE_DLY_EMP, new Object[] {employee.getEmpDailyNtlMrgnId() });
    }

    @Override
    public ScoreboardEmployeeShift getEmployeeShift(String empBbyId, String storeId, String businessSource) {

	List<ScoreboardEmployeeShift> list = getJdbcTemplate().query(SQL_SELECT_EMP_SHIFT,
		new Object[] {storeId, businessSource, empBbyId }, new EmployeeRowMapper());
	if (list.size() > 0){
	    return list.get(0);
	}
	return null;
    }

    @Override
    public ScoreboardEmployeeShift persistEmployee(ScoreboardEmployeeShift empShift) {
	KeyHolder keyHolder = new GeneratedKeyHolder();

	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("BBY_EMP_ID", empShift.getEmpBBYId().toUpperCase(), java.sql.Types.VARCHAR);
	params.addValue("EMP_NM", empShift.getEmpFullName(), java.sql.Types.VARCHAR);
	params.addValue("CREATED_BY", empShift.getCreatedBy(), java.sql.Types.VARCHAR);

	getNamedJdbcTemplate().update(SQL_INSERT_EMPLOYEE, params, keyHolder, new String[] {"NTL_MRGN_EMP_ID" });
	empShift.setEmpNtlMrgnId(keyHolder.getKey().longValue());
	return empShift;
    }

    @Override
    public ScoreboardEmployeeShift persistEmployeeShift(ScoreboardEmployeeShift empShift) {
	KeyHolder keyHolder = new GeneratedKeyHolder();

	MapSqlParameterSource params = buildEmployeeShiftParams(empShift);

	getNamedJdbcTemplate().update(SQL_INSERT_EMPLOYEE_SHIFT, params, keyHolder,
		new String[] {"ntl_mrgn_dly_emp_id" });
	empShift.setEmpDailyNtlMrgnId(keyHolder.getKey().longValue());
	return empShift;
    }

    @Override
    public ScoreboardEmployeeShift updateEmployeeShift(ScoreboardEmployeeShift empShift) {
	MapSqlParameterSource params = buildEmployeeShiftParams(empShift);

	getNamedJdbcTemplate().update(SQL_UPDATE_EMP_SHIFT, params);
	return empShift;
    }

    private MapSqlParameterSource buildEmployeeShiftParams(ScoreboardEmployeeShift empShift) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("NTL_MRGN_DLY_EMP_ID", empShift.getEmpDailyNtlMrgnId());
	params.addValue("NTL_MRGN_EMP_ID", empShift.getEmpNtlMrgnId());
	params.addValue("STOR_ID", empShift.getStoreId());
	params.addValue("BSNS_SRC_ID", empShift.getBusinessSourceId());
	params.addValue("SHIFT_BEG_TM", empShift.getShiftStartTime());
	params.addValue("SHIFT_END_TM", empShift.getShiftEndTime());
	if (empShift.getPrimaryZone() != null){
	    params.addValue("PRIMARY_ZONE_ID", empShift.getPrimaryZone().getConfigId());
	}else{
	    params.addValue("PRIMARY_ZONE_ID", null);
	}
	if (empShift.getSecondaryZone() != null){
	    params.addValue("SECONDARY_ZONE_ID", empShift.getSecondaryZone().getConfigId());
	}else{
	    params.addValue("SECONDARY_ZONE_ID", null);
	}
	params.addValue("CREATED_BY", empShift.getCreatedBy(), java.sql.Types.VARCHAR);
	params.addValue("AMENDED_BY", empShift.getModifiedBy(), java.sql.Types.VARCHAR);
	return params;
    }

    @Override
    public List<ScoreboardEmployeeShift> getDailyEmployeeList(String storeId, String bsnSource) {
	return getJdbcTemplate().query(SQL_SELECT_DAILY_EMP_SHIFT_LIST, new Object[] {storeId, bsnSource },
		new EmployeeRowMapper());
    }

    private final class EmployeeRowMapper implements RowMapper<ScoreboardEmployeeShift> {

	public ScoreboardEmployeeShift mapRow(ResultSet rs, int rowNum) throws SQLException {
	    ScoreboardEmployeeShift empShift = new ScoreboardEmployeeShift();
	    empShift.setBusinessSourceId(rs.getLong("BSNS_SRC_ID"));
	    empShift.setEmpBBYId(rs.getString("BBY_EMP_ID"));
	    empShift.setEmpDailyNtlMrgnId(rs.getLong("NTL_MRGN_DLY_EMP_ID"));
	    empShift.setEmpFullName(rs.getString("EMP_NM"));
	    empShift.setEmpNtlMrgnId(rs.getLong("NTL_MRGN_EMP_ID"));
	    empShift.setShiftEndTime(rs.getTimestamp("SHIFT_END_TM"));
	    empShift.setShiftStartTime(rs.getTimestamp("SHIFT_BEG_TM"));
	    empShift.setStoreId(rs.getString("STOR_ID"));

	    Long zoneId = rs.getLong("PRIMARY_ZONE_ID");
	    if (!rs.wasNull()){
		empShift.setPrimaryZone(new Config());
		empShift.getPrimaryZone().setConfigId(zoneId);
	    }

	    zoneId = rs.getLong("SECONDARY_ZONE_ID");
	    if (!rs.wasNull()){
		empShift.setSecondaryZone(new Config());
		empShift.getSecondaryZone().setConfigId(zoneId);
	    }
	    return empShift;
	}
    }

    private static String getSQLByName(String name) {
	return mapSQL.get(name);
    }

    private static Map<String, String> loadSQLFromXmlFile(String fileName) {
	Map<String, String> map = new HashMap<String, String>();

	try{
	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    DocumentBuilder db;
	    db = dbf.newDocumentBuilder();
	    Document dom = db.parse(fileName);
	    Element docEle = dom.getDocumentElement();

	    NodeList nl = docEle.getElementsByTagName("sql");
	    if (nl != null && nl.getLength() > 0){
		for(int i = 0; i < nl.getLength(); i++){
		    Element el = (Element) nl.item(i);
		    map.put(el.getAttribute("name"), el.getTextContent());
		}
	    }

	}catch(Exception e){
	    // Ignoring
	}

	return map;
    }

    public ScoreboardWidget getScoreboardWidget(String storeId, String businessSourceId, Date localDate, int widgetOrder) {

	String operationType = businessSourceId.split("_")[1];
	String storeType = businessSourceId.split("_")[0];

	if (operationType.equalsIgnoreCase(ScoreboardOperationType.MOBILE.toString())){
	    if (storeType.equalsIgnoreCase(ScoreboardStoreType.SWAS.toString())){
		switch (widgetOrder) {
		    case 0:
			return this.getMobileTotalConnectionsWidget(storeId, localDate, ScoreboardOperationType.MOBILE,
				ScoreboardStoreType.SWAS);
		    case 1:
			return this.getMobilePostPaidGSPWidget(storeId, storeType, localDate, ScoreboardStoreType.SWAS);
		    case 2:
			return this.getMobilePostPaidAccessoriesWidget(storeId, storeType, localDate,
				ScoreboardStoreType.SWAS);
		    case 3:
			return this.getMobileNoContractActivationsWidget(storeId, storeType, localDate,
				ScoreboardStoreType.SWAS);
		    case 4:
			return this.getMobileMP3NotionalMarginWidget(storeId, storeType, localDate,
				ScoreboardOperationType.MOBILE, ScoreboardStoreType.SWAS);
		    case 5:
			return this.getMobileMp3GspWidget(storeId, storeType, localDate, ScoreboardStoreType.SWAS);
		    case 6:
			return this.getMobileMp3AccessoriesWidget(storeId, storeType, localDate,
				ScoreboardStoreType.SWAS);
		    case 7:
			return this.getMobileUpgradeChecksWidget(storeId, storeType, localDate,
				ScoreboardStoreType.SWAS);
		    default:
			return null;
		}
	    }else if (storeType.equalsIgnoreCase(ScoreboardStoreType.SAS.toString())){
		switch (widgetOrder) {
		    case 0:
			return this.getMobilePostPaidWidget(storeId, storeType, localDate,
				ScoreboardOperationType.MOBILE, ScoreboardStoreType.SAS);
		    case 1:
			return this.getMobileIphoneWidget(storeId, storeType, localDate,
				ScoreboardOperationType.MOBILE, ScoreboardStoreType.SAS);
		    case 2:
			return this.getMobileNoContractConnectionWidget(storeId, storeType, localDate,
				ScoreboardOperationType.MOBILE, ScoreboardStoreType.SAS);
		    case 3:
			return this.getMobileBroadbandWidget(storeId, storeType, localDate,
				ScoreboardOperationType.MOBILE, ScoreboardStoreType.SAS);
		    case 4:
			return this.getMobilePostPaidGSPWidget(storeId, storeType, localDate, ScoreboardStoreType.SAS);
		    case 5:
			return this.getMobilePostPaidAccessoriesWidget(storeId, storeType, localDate,
				ScoreboardStoreType.SAS);
		    case 6:
			return this.getMobileNoContractActivationsWidget(storeId, storeType, localDate,
				ScoreboardStoreType.SAS);
		    case 7:
			return this
				.getMobileUpgradeChecksWidget(storeId, storeType, localDate, ScoreboardStoreType.SAS);
		    default:
			return null;
		}
	    }
	}else if (operationType.equalsIgnoreCase(ScoreboardOperationType.COMPUTING.toString())){
	    switch (widgetOrder) {
		case 0:
		    return this.getComputingLaptopsWidget(storeId, storeType, localDate,
			    ScoreboardOperationType.COMPUTING);
		case 1:
		    return this.getComputingDesktopAllInOnesWidget(storeId, storeType, localDate,
			    ScoreboardOperationType.COMPUTING);
		case 2:
		    return this.getComputingTabletsWidget(storeId, storeType, localDate);
		case 3:
		    return this.getComputingEreadersWidget(storeId, storeType, localDate);
		case 4:
		    return this.getComputingGeekSquadWidget(storeId, storeType, localDate,
			    ScoreboardOperationType.COMPUTING);
		case 5:
		    return this.getComputingAccessoriesWidget(storeId, storeType, localDate);
		case 6:
		    return this.getComputingContentWidget(storeId, storeType, localDate);
		case 7:
		    return this.getComputingConnectionsWidget(storeId, storeType, localDate);
		default:
	    }
	    return null;
	}

	return null;
    }

    private ScoreboardWidget getMobileBroadbandWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType mobile, ScoreboardStoreType scoreboardStoreType) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SAS_MOBILE_BROADBAND_CONNECTIONS,
		ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobileNoContractConnectionWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType mobile, ScoreboardStoreType scoreboardStoreType) {
	return getWidgetCurrValGoal(storeId, storeType, localDate,
		SQL_SELECT_WIDGET_SAS_MOBILE_NO_CONTRACT_CONNECTIONS, ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobileIphoneWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType mobile, ScoreboardStoreType scoreboardStoreType) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SAS_MOBILE_IPHONE_CONNECTIONS,
		ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobilePostPaidWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType mobile, ScoreboardStoreType scoreboardStoreType) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_CONNECTIONS,
		ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getComputingConnectionsWidget(String storeId, String storeType, Date localDate) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_COMPUTING_CONNECTIONS,
		ScoreboardOperationType.COMPUTING);
    }

    private ScoreboardWidget getComputingContentWidget(String storeId, String storeType, Date localDate) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_COMPUTING_CONTENT,
		ScoreboardOperationType.COMPUTING);
    }

    private ScoreboardWidget getComputingAccessoriesWidget(String storeId, String storeType, Date localDate) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_COMPUTING_ACCESSORIES,
		ScoreboardOperationType.COMPUTING);
    }

    private ScoreboardWidget getComputingGeekSquadWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType operationType) {

	ScoreboardWidget geekSquadWidget = new ScoreboardWidget();
	List<ScoreboardWidget> geekSquadWidgetList = new ArrayList<ScoreboardWidget>();
	ScoreboardWidget gspWidget = new ScoreboardWidget();
	ScoreboardWidget gssWidget = new ScoreboardWidget();

	Map<String, String> widgetCurrValGoalMap = getScoreboardWidgetRows(SQL_SELECT_WIDGET_SWAS_COMPUTING_GEEK_SQUAD,
		storeId, storeType, localDate, operationType);

	if (widgetCurrValGoalMap.get("CURRENT_VALUE") != null)
	    geekSquadWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE")));
	if (widgetCurrValGoalMap.get("GOAL") != null)
	    geekSquadWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_GSP") != null)
	    gspWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE_GSP")));
	if (widgetCurrValGoalMap.get("GOAL_GSP") != null)
	    gspWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_GSP")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_GEEK_SQUAD_SERVICES") != null)
	    gssWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_GEEK_SQUAD_SERVICES")));
	if (widgetCurrValGoalMap.get("GOAL_GEEK_SQUAD_SERVICES") != null)
	    gssWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_GEEK_SQUAD_SERVICES")));

	geekSquadWidgetList.add(gspWidget);
	geekSquadWidgetList.add(gssWidget);

	populateWidgetCategories(geekSquadWidgetList, SCOREBOARD_COMPUTING_GS_WIDGET_LIST);
	geekSquadWidget.setWidgetAttributes(geekSquadWidgetList);

	return geekSquadWidget;

    }

    private ScoreboardWidget getComputingEreadersWidget(String storeId, String storeType, Date localDate) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_EREADERS,
		ScoreboardOperationType.COMPUTING);
    }

    private ScoreboardWidget getComputingTabletsWidget(String storeId, String storeType, Date localDate) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_TABLETS,
		ScoreboardOperationType.COMPUTING);
    }

    private ScoreboardWidget getComputingDesktopAllInOnesWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType operationType) {

	ScoreboardWidget desktopAllInOneWidget = new ScoreboardWidget();
	List<ScoreboardWidget> desktopAllInOneWidgetList = new ArrayList<ScoreboardWidget>();
	ScoreboardWidget desktopWidget = new ScoreboardWidget();
	ScoreboardWidget allInOneWidget = new ScoreboardWidget();

	Map<String, String> widgetCurrValGoalMap = getScoreboardWidgetRows(
		SQL_SELECT_WIDGET_SWAS_COMPUTING_DESKTOPS_ALL_IN_ONES, storeId, storeType, localDate, operationType);

	if (widgetCurrValGoalMap.get("CURRENT_VALUE") != null)
	    desktopAllInOneWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE")));
	if (widgetCurrValGoalMap.get("GOAL") != null)
	    desktopAllInOneWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_DESKTOPS") != null)
	    desktopWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE_DESKTOPS")));
	if (widgetCurrValGoalMap.get("GOAL_DESKTOPS") != null)
	    desktopWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_DESKTOPS")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_ALL_IN_ONES") != null)
	    allInOneWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE_ALL_IN_ONES")));
	if (widgetCurrValGoalMap.get("GOAL_ALL_IN_ONES") != null)
	    allInOneWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_ALL_IN_ONES")));

	desktopAllInOneWidgetList.add(desktopWidget);
	desktopAllInOneWidgetList.add(allInOneWidget);

	populateWidgetCategories(desktopAllInOneWidgetList, SCOREBOARD_COMPUTING_DSKTP_WIDGET_LIST);
	desktopAllInOneWidget.setWidgetAttributes(desktopAllInOneWidgetList);

	return desktopAllInOneWidget;

    }

    private ScoreboardWidget getComputingLaptopsWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType operationType) {

	ScoreboardWidget computingLaptopsWidget = new ScoreboardWidget();
	List<ScoreboardWidget> computingLaptopsWidgetList = new ArrayList<ScoreboardWidget>();
	ScoreboardWidget computingLaptopsLowEndWidget = new ScoreboardWidget();
	ScoreboardWidget computingLaptopsMediumEndWidget = new ScoreboardWidget();
	ScoreboardWidget computingLaptopsHighEndWidget = new ScoreboardWidget();

	Map<String, String> widgetCurrValGoalMap = getScoreboardWidgetRows(
		SQL_SELECT_WIDGET_SWAS_COMPUTING_TOTAL_LAPTOPS, storeId, storeType, localDate, operationType);

	if (widgetCurrValGoalMap.get("CURRENT_VALUE") != null)
	    computingLaptopsWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE")));
	if (widgetCurrValGoalMap.get("GOAL") != null)
	    computingLaptopsWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_LESS_THAN_500") != null)
	    computingLaptopsLowEndWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_LESS_THAN_500")));
	if (widgetCurrValGoalMap.get("GOAL_LESS_THAN_500") != null)
	    computingLaptopsLowEndWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("GOAL_LESS_THAN_500")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_500_TO_900") != null)
	    computingLaptopsMediumEndWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_500_TO_900")));
	if (widgetCurrValGoalMap.get("GOAL_LESS_500_TO_900") != null)
	    computingLaptopsMediumEndWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("GOAL_LESS_500_TO_900")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_GREATER_THAN_900") != null)
	    computingLaptopsHighEndWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_GREATER_THAN_900")));
	if (widgetCurrValGoalMap.get("GOAL_GREATER_THAN_900") != null)
	    computingLaptopsHighEndWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("GOAL_GREATER_THAN_900")));

	computingLaptopsWidgetList.add(computingLaptopsLowEndWidget);
	computingLaptopsWidgetList.add(computingLaptopsMediumEndWidget);
	computingLaptopsWidgetList.add(computingLaptopsHighEndWidget);

	populateWidgetCategories(computingLaptopsWidgetList, SCOREBOARD_COMPUTING_TL_WIDGET_LIST);
	computingLaptopsWidget.setWidgetAttributes(computingLaptopsWidgetList);

	return computingLaptopsWidget;

    }

    private ScoreboardWidget getMobileUpgradeChecksWidget(String storeId, String storeType, Date localDate,
	    ScoreboardStoreType scoreboardStoreType) {

	if (scoreboardStoreType == ScoreboardStoreType.SWAS)
	    return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_MOBILE_UPGRADE_CHECKS,
		    ScoreboardOperationType.MOBILE);
	else
	    return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SAS_MOBILE_UPGRADE_CHECKS,
		    ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobileMp3AccessoriesWidget(String storeId, String storeType, Date localDate,
	    ScoreboardStoreType scoreboardStoreType) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_ACCESSORIES,
		ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobileMp3GspWidget(String storeId, String storeType, Date localDate,
	    ScoreboardStoreType scoreboardStoreType) {
	return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_GSP,
		ScoreboardOperationType.MOBILE);
    }

    // has widgets inside
    private ScoreboardWidget getMobileMP3NotionalMarginWidget(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType operationType, ScoreboardStoreType scoreboardStoreType) {

	ScoreboardWidget mP3NotionalMarginWidget = new ScoreboardWidget();
	List<ScoreboardWidget> mP3NotionalMarginWidgetList = new ArrayList<ScoreboardWidget>();
	ScoreboardWidget mp3NmHwUnitsWidget = new ScoreboardWidget();
	ScoreboardWidget mp3NmAccsryMarginWidget = new ScoreboardWidget();

	Map<String, String> widgetCurrValGoalMap = getScoreboardWidgetRows(
		SQL_SELECT_WIDGET_SWAS_MOBILE_MP3_NOTIONAL_MARGIN, storeId, storeType, localDate, operationType);

	if (widgetCurrValGoalMap.get("CURRENT_VALUE") != null)
	    mP3NotionalMarginWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE")));
	if (widgetCurrValGoalMap.get("GOAL") != null)
	    mP3NotionalMarginWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_HARDWARE") != null)
	    mp3NmHwUnitsWidget
		    .setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE_HARDWARE")));
	if (widgetCurrValGoalMap.get("GOAL_HARDWARE") != null)
	    mp3NmHwUnitsWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_HARDWARE")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_ACCESSORY") != null)
	    mp3NmAccsryMarginWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_ACCESSORY")));
	if (widgetCurrValGoalMap.get("GOAL_ACCESSORY") != null)
	    mp3NmAccsryMarginWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_ACCESSORY")));

	mP3NotionalMarginWidgetList.add(mp3NmHwUnitsWidget);
	mP3NotionalMarginWidgetList.add(mp3NmAccsryMarginWidget);

	populateWidgetCategories(mP3NotionalMarginWidgetList, SCOREBOARD_MOBILE_MP3NM_WIDGET_LIST);
	mP3NotionalMarginWidget.setWidgetAttributes(mP3NotionalMarginWidgetList);

	return mP3NotionalMarginWidget;

    }

    private ScoreboardWidget getMobileNoContractActivationsWidget(String storeId, String storeType, Date localDate,
	    ScoreboardStoreType scoreboardStoreType) {

	if (scoreboardStoreType == ScoreboardStoreType.SWAS)
	    return getWidgetCurrValGoal(storeId, storeType, localDate,
		    SQL_SELECT_WIDGET_SWAS_MOBILE_NO_CONTRACT_ACTIVATIONS, ScoreboardOperationType.MOBILE);
	else
	    return getWidgetCurrValGoal(storeId, storeType, localDate,
		    SQL_SELECT_WIDGET_SAS_MOBILE_NO_CONTRACT_ACTIVATIONS, ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobilePostPaidAccessoriesWidget(String storeId, String storeType, Date localDate,
	    ScoreboardStoreType scoreboardStoreType) {
	if (scoreboardStoreType == ScoreboardStoreType.SWAS)
	    return getWidgetCurrValGoal(storeId, storeType, localDate,
		    SQL_SELECT_WIDGET_SWAS_MOBILE_POST_PAID_ACCESSORIES, ScoreboardOperationType.MOBILE);
	else
	    return getWidgetCurrValGoal(storeId, storeType, localDate,
		    SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_ACCESSORIES, ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobilePostPaidGSPWidget(String storeId, String storeType, Date localDate,
	    ScoreboardStoreType scoreboardStoreType) {

	if (scoreboardStoreType == ScoreboardStoreType.SWAS)
	    return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SWAS_MOBILE_POST_PAID_GSP,
		    ScoreboardOperationType.MOBILE);
	else
	    return getWidgetCurrValGoal(storeId, storeType, localDate, SQL_SELECT_WIDGET_SAS_MOBILE_POST_PAID_GSP,
		    ScoreboardOperationType.MOBILE);
    }

    private ScoreboardWidget getMobileTotalConnectionsWidget(String storeId, Date localDate,
	    ScoreboardOperationType operationType, ScoreboardStoreType scoreboardStoreType) {

	ScoreboardWidget totalConnectionWidget = new ScoreboardWidget();
	List<ScoreboardWidget> totalConnWidgetList = new ArrayList<ScoreboardWidget>();
	ScoreboardWidget totalConnIPhoneWidget = new ScoreboardWidget();
	ScoreboardWidget totalConnNoIphoneWidget = new ScoreboardWidget();
	ScoreboardWidget totalConnNoContractWidget = new ScoreboardWidget();
	ScoreboardWidget totalConnBroadbandWidget = new ScoreboardWidget();

	Map<String, String> widgetCurrValGoalMap = getScoreboardWidgetRows(
		SQL_SELECT_WIDGET_SWAS_MOBILE_TOTAL_CONNECTIONS, storeId, scoreboardStoreType.toString(), localDate,
		operationType);

	if (widgetCurrValGoalMap.get("CURRENT_VALUE") != null)
	    totalConnectionWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE")));
	if (widgetCurrValGoalMap.get("GOAL") != null)
	    totalConnectionWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_TC_IPHONE") != null)
	    totalConnIPhoneWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_TC_IPHONE")));
	if (widgetCurrValGoalMap.get("GOAL_TC_IPHONE") != null)
	    totalConnIPhoneWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_TC_IPHONE")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_TC_NO_IPHONE") != null)
	    totalConnNoIphoneWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_TC_NO_IPHONE")));
	if (widgetCurrValGoalMap.get("GOAL_TC_NO_IPHONE") != null)
	    totalConnNoIphoneWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_TC_NO_IPHONE")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_TC_NO_CONTRACT") != null)
	    totalConnNoContractWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_TC_NO_CONTRACT")));
	if (widgetCurrValGoalMap.get("GOAL_TC_NO_CONTRACT") != null)
	    totalConnNoContractWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("GOAL_TC_NO_CONTRACT")));

	if (widgetCurrValGoalMap.get("CURRENT_VALUE_TC_BROADBAND") != null)
	    totalConnBroadbandWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap
		    .get("CURRENT_VALUE_TC_BROADBAND")));
	if (widgetCurrValGoalMap.get("GOAL_TC_BROADBAND") != null)
	    totalConnBroadbandWidget
		    .setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL_TC_BROADBAND")));

	totalConnWidgetList.add(totalConnNoIphoneWidget);
	totalConnWidgetList.add(totalConnIPhoneWidget);
	totalConnWidgetList.add(totalConnNoContractWidget);
	totalConnWidgetList.add(totalConnBroadbandWidget);

	populateWidgetCategories(totalConnWidgetList, SCOREBOARD_MOBILE_TC_WIDGET_LIST);

	totalConnectionWidget.setWidgetAttributes(totalConnWidgetList);

	return totalConnectionWidget;
    }

    public ScoreboardWidget getWidgetCurrValGoal(String storeId, String storeType, Date localDate, String query,
	    ScoreboardOperationType operationType, Object... args) {

	List<Map<String, Object>> scoreboardWidgetList = new ArrayList<Map<String, Object>>();
	Map<String, String> widgetCurrValGoalMap = new HashMap<String, String>();
	ScoreboardWidget scoreboardWidget = new ScoreboardWidget();

	String date = new SimpleDateFormat("MM-dd-yyyy").format(localDate);

	scoreboardWidgetList = getNamedJdbcTemplate().queryForList(query,
		supplyQueryParams(storeId, storeType, localDate, operationType));

	for(Map<String, Object> scoreboardValues: scoreboardWidgetList){
	    widgetCurrValGoalMap.put(scoreboardValues.get("NTL_MRGN_TYPE").toString(), scoreboardValues.get(
		    "NTL_MRGN_VALUE").toString());
	}

	if (widgetCurrValGoalMap.get("CURRENT_VALUE") != null)
	    scoreboardWidget.setCurrentVal((int) Float.parseFloat(widgetCurrValGoalMap.get("CURRENT_VALUE")));
	if (widgetCurrValGoalMap.get("GOAL") != null)
	    scoreboardWidget.setTargetVal((int) Float.parseFloat(widgetCurrValGoalMap.get("GOAL")));

	return scoreboardWidget;
    }

    private void populateWidgetCategories(List<ScoreboardWidget> totalConnWidgetList, String widgetCategory) {
	List<Config> configList = new ArrayList<Config>();
	configList = configDao.getConfigItemsByType(widgetCategory);
	for(Config config: configList){
	    totalConnWidgetList.get(configList.indexOf(config)).setId(Long.parseLong(config.getParamName()));
	    totalConnWidgetList.get(configList.indexOf(config)).setWidgetName(config.getDescription());
	    totalConnWidgetList.get(configList.indexOf(config)).setWidgetType(
		    ScoreboardWidget.Type.valueOf(config.getParamValue().trim().toUpperCase()));
	}
    }

    private boolean isPentagonStore(String storeId) {
	List<Config> pentagonStoreList = null;
	if (pentagonStoreList == null){
	    pentagonStoreList = configDao.getConfigItemsByType(SCOREBOARD_PENTAGON_STORE_TYP);
	}
	for(Config config: pentagonStoreList){
	    if (storeId.equalsIgnoreCase(config.getParamName())){
		return true;
	    }
	}
	return false;
    }

    private Map<String, Object> supplyQueryParams(String storeId, String storeType, Date localDate,
	    ScoreboardOperationType operationType) {

	String bsnsSrcforWgt = new String();
	if (isPentagonStore(storeId)){
	    bsnsSrcforWgt = PENTAGON_MOBILE;
	}else{
	    bsnsSrcforWgt = storeType + "_" + operationType.toString().toUpperCase();
	}

	String date = new SimpleDateFormat("MM-dd-yyyy").format(localDate);

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("stor_id", storeId);
	params.put("bsns_src_nm", storeType + "_" + operationType.toString());
	params.put("current_tm", date);
	params.put("bsns_src_nm_wgt", bsnsSrcforWgt);

	return params;
    }

    private Map<String, String> getScoreboardWidgetRows(String sql, String storeId, String storeType, Date localDate,
	    ScoreboardOperationType operationType) {

	List<Map<String, Object>> scoreBoardValues = new ArrayList<Map<String, Object>>();
	Map<String, String> widgetCurrValGoalMap = new HashMap<String, String>();

	scoreBoardValues = getNamedJdbcTemplate().queryForList(sql,
		supplyQueryParams(storeId, storeType, localDate, operationType));

	for(Map<String, Object> scoreboardValues: scoreBoardValues){
	    widgetCurrValGoalMap.put(scoreboardValues.get("NTL_MRGN_TYPE").toString(), scoreboardValues.get(
		    "NTL_MRGN_VALUE").toString());
	}

	return widgetCurrValGoalMap;
    }

    public ConfigDaoImpl getConfigDao() {
	return configDao;
    }

    public void setConfigDao(ConfigDaoImpl configDao) {
	this.configDao = configDao;
    }

    @Override
    public ScoreboardEmployeeDistribution getScoreboardEmployeeDistribution(String storeId,
	    ScoreboardStoreType storeType, ScoreboardOperationType operationType, Date localDate) {

	ScoreboardEmployeeDistribution sbEmpDistribution = new ScoreboardEmployeeDistribution();
	ScoreboardDailyGoal sbDailyGoal = new ScoreboardDailyGoal();
	List<ScoreboardDailyGoal> scoreboardDailyGoalList = new ArrayList<ScoreboardDailyGoal>();

	// calculate notional margin
	sbDailyGoal.setGoalValue((long) getStoreNotionalMargin(storeId, storeType, operationType, localDate)
		.getTargetValue());
	sbDailyGoal.setName(NOTIONAL_MARGIN);
	scoreboardDailyGoalList.add(sbDailyGoal);

	List<Config> configList = configDao.getConfigItemsByType("EMPLOYEE_SCHEDULE_GOALS" + "_" + storeType.toString()
		+ "_" + operationType.toString());

	BeanComparator beanComparator = new BeanComparator(("paramValue"));
	Collections.sort(configList, beanComparator);

	for(Config config: configList){

	    ScoreboardWidget sbWidget = this.getWidgetCurrValGoal(storeId, storeType.toString(), localDate,
		    getSQLByName(config.getDescription()), operationType);
	    ScoreboardDailyGoal scoreboardDailyGoal = new ScoreboardDailyGoal();
	    scoreboardDailyGoal.setGoalValue((long) sbWidget.getTargetVal());
	    scoreboardDailyGoal.setName(config.getParamName());
	    scoreboardDailyGoalList.add(scoreboardDailyGoal);
	}

	sbEmpDistribution.setScoreboardDailyGoal(scoreboardDailyGoalList);
	// calculate employee notional margin.
	List<ScoreboardEmployeeNotionalMargin> scoreboardEmployeeScheduleList = getNamedJdbcTemplate().query(
		SQL_SELECT_STORE_EMPLOYEE_SCHEDULE,
		supplyQueryParams(storeId, storeType.toString(), localDate, operationType),
		new RowMapper<ScoreboardEmployeeNotionalMargin>() {

		    @Override
		    public ScoreboardEmployeeNotionalMargin mapRow(ResultSet rs, int rowNum) throws SQLException {
			ScoreboardEmployeeNotionalMargin empNtlMrgn = new ScoreboardEmployeeNotionalMargin();
			ScoreboardEmployeeShift empShift = new ScoreboardEmployeeShift();
			Config primaryZoneConfig = new Config();
			Config secondaryZoneConfig = new Config();
			empShift.setEmpDailyNtlMrgnId(rs.getLong("NTL_MRGN_DLY_EMP_ID"));
			empShift.setEmpFullName(rs.getString("EMP_NM"));
			empShift.setShiftEndTime(rs.getTimestamp("SHIFT_END_TM"));
			empShift.setShiftStartTime(rs.getTimestamp("SHIFT_BEG_TM"));
			primaryZoneConfig.setParamValue(rs.getString("PRIMARY_ZONE_NM"));
			empShift.setPrimaryZone(primaryZoneConfig);
			secondaryZoneConfig.setParamValue(rs.getString("SECONDARY_ZONE_NM"));
			empShift.setSecondaryZone(secondaryZoneConfig);
			empNtlMrgn.setEmployeeShift(empShift);
			return empNtlMrgn;
		    }
		});

	sbEmpDistribution.setScoreboardEmployeeScheduleList(scoreboardEmployeeScheduleList);
	return sbEmpDistribution;

    }

    private String getQuerybyName(String query) throws NoSuchFieldException {

	return (this.getClass().getDeclaredField(query)).toString();

    }

    @Override
    public void deleteTransaction(ScoreboardDataItem dataItem) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("NTL_MRGN_TRANS_ID", dataItem.getId(), java.sql.Types.NUMERIC);
	params.addValue("AMENDED_BY", dataItem.getModifiedBy(), java.sql.Types.VARCHAR);
	getNamedJdbcTemplate().update(SQL_DELETE_TRANSACTION, params);
    }

    @Override
    public List<ScoreboardDataItem> getEmployeeSaleTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType, Long empId) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("bsns_src_nm", storeType.toString() + "_" + operationType.toString(), java.sql.Types.VARCHAR);
	params.addValue("stor_id", storeId, java.sql.Types.VARCHAR);
	params.addValue("ntl_mrgn_dly_emp_id", empId, java.sql.Types.NUMERIC);

	return getNamedJdbcTemplate().query(SQL_EDIT_EMPLOYEE_SALES, params, new RowMapper<ScoreboardDataItem>() {

	    @Override
	    public ScoreboardDataItem mapRow(ResultSet rs, int idx) throws SQLException {
		ScoreboardDataItem item = new ScoreboardDataItem();
		item.setId(rs.getLong("NTL_MRGN_TRANS_ID"));
		item.setName(rs.getString("ITEM_DESCRIPTION"));
		item.setGroupName(rs.getString("PARENT_DESCRIPTION"));
		item.setEditQuantity(rs.getInt("QTY"));
		item.setEditUnitPrice(rs.getBigDecimal("AMT"));
		item.setTransactionDate(rs.getTime("TRAN_DT"));
		item.setDeleted(rs.getInt("DEL_FLG") == 0?false:true);
		return item;
	    }
	});
    }

    @Override
    public List<ScoreboardDataItem> getStoreReturnTransactions(String storeId, ScoreboardStoreType storeType,
	    ScoreboardOperationType operationType) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("bsns_src_nm", storeType.toString() + "_" + operationType.toString(), java.sql.Types.VARCHAR);
	params.addValue("stor_id", storeId, java.sql.Types.VARCHAR);

	return getNamedJdbcTemplate().query(SQL_EDIT_STORE_RETURNS, params, new RowMapper<ScoreboardDataItem>() {

	    @Override
	    public ScoreboardDataItem mapRow(ResultSet rs, int idx) throws SQLException {
		ScoreboardDataItem item = new ScoreboardDataItem();
		item.setId(rs.getLong("NTL_MRGN_TRANS_ID"));
		item.setName(rs.getString("ITEM_DESCRIPTION"));
		item.setGroupName(rs.getString("PARENT_DESCRIPTION"));
		item.setEditQuantity(rs.getInt("QTY"));
		item.setEditUnitPrice(rs.getBigDecimal("AMT"));
		item.setTransactionDate(rs.getTime("TRAN_DT"));
		item.setDeleted(rs.getInt("DEL_FLG") == 0?false:true);
		return item;
	    }
	});
    }

    @Override
    public int getEmployeeTransactionCount(ScoreboardEmployeeShift employee) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("NTL_MRGN_DLY_EMP_ID", employee.getEmpDailyNtlMrgnId(), java.sql.Types.NUMERIC);
	
	return getNamedJdbcTemplate().queryForInt(SQL_EMPLOYEE_SHIFT_TRANSACTION_COUNT, params);
    }

}
