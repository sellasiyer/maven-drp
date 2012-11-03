package com.bestbuy.bbym.ise.drp.dao;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDay;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DateUtil;

@Repository("recommendationsDao")
public class RecommendationsDaoImpl extends AbstractDao implements RecommendationsDao {

    private static Logger logger = LoggerFactory.getLogger(RecommendationsDaoImpl.class);

    @Autowired
    private EssentialsDao essentialsDao;

    private final String SQL_SELECT_REC_FIELDS = "r.recommendation_id, r.device_capabilities, r.wow_requirements, r.recommended_subscription, r.recommended_device, r.net_use_info, "
	    + "r.notes, r.created_by, r.created_on, r.amended_by, r.amended_on, r.store_id, r.emp_contact, r.first_name, r.last_name, r.mobile_no, "
	    + "r.best_contact_tm_info, r.bby_customer_id, r.upgrade_reminder_text, r.upgrade_reminder_call, r.upgrade_eligibility_date, r.subscription_info, r.trade_in_val, r.emp_crt_frst_nm, r.emp_crt_last_nm, r.emp_alt_frst_nm, r.emp_alt_last_nm, "
	    + "r.rec_sht_typ, r.addr, r.city, r.state, r.zipcode, r.bby_cns_frst_nm, r.bby_cns_last_nm, r.bby_cns_ph_nbr, r.bby_cns_ph_ext, r.lang ";

    private final String SQL_SELECT_FROM = " FROM BST_ISE_SCH01.recommendation r ";

    private final String SQL_BASIC_SELECT = " SELECT " + SQL_SELECT_REC_FIELDS + SQL_SELECT_FROM;

    @Override
    public void persistRecommendation(Recommendation rec, User employee) throws DataAccessException {

	Map<Long, String> preferences = rec.getPreferences();
	essentialsDao.saveEssentials(preferences, rec.getId(), employee);

	String sql = "UPDATE BST_ISE_SCH01.recommendation SET device_capabilities = :device_capabilities, wow_requirements = :wow_requirements, recommended_subscription = :recommended_subscription, recommended_device = :recommended_device, "
		+ " net_use_info = :net_use_info, notes = :notes, amended_by = :amended_by, amended_on = CURRENT_TIMESTAMP, emp_contact = :emp_contact, "
		+ " first_name = :first_name, last_name = :last_name, mobile_no = :mobile_no, best_contact_tm_info = :best_contact_tm_info, bby_customer_id = :bby_customer_id, "
		+ " upgrade_reminder_text = :upgrade_reminder_text, upgrade_reminder_call = :upgrade_reminder_call, upgrade_eligibility_date = :upgrade_eligibility_date, subscription_info = :subscription_info, trade_in_val = :trade_in_val, "
		+ " emp_alt_frst_nm = :emp_alt_frst_nm, emp_alt_last_nm = :emp_alt_last_nm, "
		+ " rec_sht_typ = :rec_sht_typ, addr = :addr, city = :city, state = :state, zipcode = :zipcode, "
		+ " bby_cns_frst_nm = :bby_cns_frst_nm, bby_cns_last_nm = :bby_cns_last_nm, bby_cns_ph_nbr = :bby_cns_ph_nbr, bby_cns_ph_ext = :bby_cns_ph_ext"
		+ " WHERE recommendation_id = :id_to_update";

	NamedParameterJdbcTemplate tplUpdate = this.getNamedJdbcTemplate();
	Map<String, Object> params = new HashMap<String, Object>();

	params.put("device_capabilities", rec.getDeviceCapabilities());
	params.put("wow_requirements", rec.getWowRequirements());
	params.put("recommended_subscription", rec.getRecommendedSubscription());
	params.put("recommended_device", rec.getRecommendedDevice());
	params.put("net_use_info", rec.getNetUseInfo());
	params.put("notes", rec.getNotes());
	params.put("amended_by", employee.getUserId());
	// store id left out intentionally. i don't see a reason to have it be
	// updated.
	params.put("emp_contact", rec.getSpecialistContactInfo());
	params.put("first_name", rec.getFirstName());
	params.put("last_name", rec.getLastName());
	params.put("mobile_no", rec.getMobileNo());
	params.put("best_contact_tm_info", rec.getBestTimeToContact());
	params.put("bby_customer_id", rec.getBbyCustomerId());
	params.put("upgrade_reminder_text", rec.getUpgradeReminderText());
	params.put("upgrade_reminder_call", rec.getUpgradeReminderCall());
	params.put("upgrade_eligibility_date", DateUtil.utilDateToSqlDate(rec.getUpgradeEligibilityDate()));
	params.put("subscription_info", rec.getSubscriptionInfo());
	params.put("trade_in_val", rec.getTradeInValue());
	params.put("emp_alt_last_nm", employee.getLastName());
	params.put("emp_alt_frst_nm", employee.getFirstName());
	params.put("rec_sht_typ", rec.getRecShtTyp());
	params.put("addr", rec.getAddr());
	params.put("city", rec.getCity());
	params.put("state", rec.getState());
	params.put("zipcode", rec.getZipcode());
	params.put("bby_cns_frst_nm", rec.getBbyCnsFrstNm());
	params.put("bby_cns_last_nm", rec.getBbyCnsLastNm());
	params.put("bby_cns_ph_nbr", rec.getBbyCnsPhNbr());
	params.put("bby_cns_ph_ext", rec.getBbyCnsPhExt());

	params.put("id_to_update", rec.getId());

	int updated = tplUpdate.update(sql, params);
	logger.info("recommendation rows updated= " + updated);

    }

    @Override
    public long addRecommendation(final Recommendation rec, final User employee) throws DataAccessException {

	NamedParameterJdbcTemplate tplInsert2 = this.getNamedJdbcTemplate();

	final String sql2 = "INSERT INTO BST_ISE_SCH01.recommendation ( recommendation_id, device_capabilities, wow_requirements, recommended_subscription, recommended_device, net_use_info, notes, created_by, created_on, store_id, "
		+ "emp_contact, first_name, last_name, mobile_no, best_contact_tm_info, bby_customer_id, upgrade_reminder_text, upgrade_reminder_call, upgrade_eligibility_date, subscription_info, trade_in_val, emp_crt_last_nm, emp_crt_frst_nm, "
		+ " rec_sht_typ, addr, city, state, zipcode, bby_cns_frst_nm, bby_cns_last_nm, bby_cns_ph_nbr, bby_cns_ph_ext, lang )"
		+ " VALUES ( BST_ISE_SCH01.seq_recommendation.nextval, :device_capabilities, :wow_requirements, :recommended_subscription, :recommended_device, :net_use_info, :notes, :created_by, CURRENT_TIMESTAMP, :store_id, "
		+ " :emp_contact, :first_name, :last_name, :mobile_no, :best_contact_tm_info, :bby_customer_id, :upgrade_reminder_text, :upgrade_reminder_call, :upgrade_eligibility_date, :subscription_info, :trade_in_val, :emp_crt_last_nm, :emp_crt_frst_nm, "
		+ " :rec_sht_typ, :addr, :city, :state, :zipcode, :bby_cns_frst_nm, :bby_cns_last_nm, :bby_cns_ph_nbr, :bby_cns_ph_ext, :lang "
		+ ")";

	KeyHolder keyHolder = new GeneratedKeyHolder();
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("device_capabilities", rec.getDeviceCapabilities());

	params.addValue("wow_requirements", rec.getWowRequirements());
	params.addValue("recommended_subscription", rec.getRecommendedSubscription());
	params.addValue("recommended_device", rec.getRecommendedDevice());
	params.addValue("net_use_info", rec.getNetUseInfo()); // 5
	params.addValue("notes", rec.getNotes());
	params.addValue("created_by", employee.getUserId()); // created_by
	params.addValue("store_id", employee.getStoreId());
	params.addValue("emp_contact", rec.getSpecialistContactInfo()); // 10
	params.addValue("first_name", rec.getFirstName());
	params.addValue("last_name", rec.getLastName());
	params.addValue("mobile_no", rec.getMobileNo());
	params.addValue("best_contact_tm_info", rec.getBestTimeToContact());
	params.addValue("bby_customer_id", rec.getBbyCustomerId()); // 15
	params.addValue("upgrade_reminder_text", rec.getUpgradeReminderText());
	params.addValue("upgrade_reminder_call", rec.getUpgradeReminderCall());
	params.addValue("upgrade_eligibility_date", DateUtil.utilDateToSqlDate(rec.getUpgradeEligibilityDate()));
	params.addValue("subscription_info", rec.getSubscriptionInfo());
	params.addValue("trade_in_val", rec.getTradeInValue()); // 20
	params.addValue("emp_crt_last_nm", employee.getLastName());
	params.addValue("emp_crt_frst_nm", employee.getFirstName());
	params.addValue("rec_sht_typ", rec.getRecShtTyp());
	params.addValue("addr", rec.getAddr());
	params.addValue("city", rec.getCity());
	params.addValue("state", rec.getState());
	params.addValue("zipcode", rec.getZipcode());
	params.addValue("bby_cns_frst_nm", rec.getBbyCnsFrstNm());
	params.addValue("bby_cns_last_nm", rec.getBbyCnsLastNm());
	params.addValue("bby_cns_ph_nbr", rec.getBbyCnsPhNbr());
	params.addValue("bby_cns_ph_ext", rec.getBbyCnsPhExt());
	params.addValue("lang", rec.getLanguage());

	tplInsert2.update(sql2, params, keyHolder, new String[] {"recommendation_id" });

	long createdId = keyHolder.getKey().longValue();
	rec.setId(createdId);

	// need recId before saving essentials.
	Map<Long, String> preferences = rec.getPreferences();
	essentialsDao.saveEssentials(preferences, rec.getId(), employee);
	return createdId;

    }

    @Override
    public Recommendation getRecommendation(long recommendationId) throws DataAccessException {
	JdbcTemplate tplSelect = this.getJdbcTemplate();
	String sql = SQL_BASIC_SELECT + " WHERE r.recommendation_id = ?";
	Recommendation out = (Recommendation) tplSelect.queryForObject(sql, new Object[] {recommendationId },
		new RecommendationsRowMapper());

	Map<Long, String> preferences = essentialsDao.getEssentials(out.getId());
	out.setPreferences(preferences);

	return out;

    }

    public List<Recommendation> selectAll() throws DataAccessException {

	String sql = SQL_BASIC_SELECT;

	JdbcTemplate tplSelect = this.getJdbcTemplate();
	List<Recommendation> list = tplSelect.query(sql, new RecommendationsRowMapper());
	this.mapEssentials(list);

	return list;
    }

    @Override
    public List<Recommendation> getRecommendationList(String storeId, String lastName) throws DataAccessException {
	List<Recommendation> list = null;

	if (lastName != null && !lastName.isEmpty()){
	    NamedParameterJdbcTemplate tplSelect = getNamedJdbcTemplate();

	    StringBuilder sql = new StringBuilder(SQL_BASIC_SELECT + " WHERE upper( r.last_name) LIKE :last_name "
		    + " AND r.rec_sht_typ = 1 ");
	    if (storeId != null){
		sql.append(" AND r.store_id = :store_id");;
	    }

	    sql.append(" order by upper(r.last_name), upper(r.first_name), r.created_on desc, r.mobile_no");

	    Map<String, Object> params = new HashMap<String, Object>();

	    params.put("last_name", "%" + lastName.toUpperCase() + "%");
	    params.put("store_id", storeId);

	    list = tplSelect.query(sql.toString(), params, new RecommendationsRowMapper());
	    this.mapEssentials(list);
	}
	return list;

    }

    @Override
    public List<Recommendation> getRecommendationList(String storeId) throws DataAccessException {
	NamedParameterJdbcTemplate tplSelect = this.getNamedJdbcTemplate();

	String sql = SQL_BASIC_SELECT + " WHERE r.store_id = :store_id";
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("store_id", storeId);

	List<Recommendation> list = tplSelect.query(sql, params, new RecommendationsRowMapper());
	this.mapEssentials(list);

	return list;
    }

    @Override
    public List<Recommendation> getRecommendationListByMobile(String storeId, String mobileNumber)
	    throws DataAccessException {
	List<Recommendation> list = null;

	if (mobileNumber != null){

	    NamedParameterJdbcTemplate tplSelect = this.getNamedJdbcTemplate();

	    StringBuilder sql = new StringBuilder(SQL_BASIC_SELECT + " WHERE r.mobile_no = :mobile_no"
		    + " AND r.rec_sht_typ = 1 ");
	    if (storeId != null){
		sql.append(" AND r.store_id = :store_id");
	    }
	    sql.append(" order by  r.created_on desc, upper(r.last_name), upper(r.first_name)");

	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("mobile_no", mobileNumber);
	    params.put("store_id", storeId);

	    list = tplSelect.query(sql.toString(), params, new RecommendationsRowMapper());
	    this.mapEssentials(list);
	}
	return list;
    }

    @Override
    public List<Recommendation> getRecommendationListForCAndT(String storeId, String lastName)
	    throws DataAccessException {
	List<Recommendation> list = null;

	if (lastName != null && !lastName.isEmpty()){
	    NamedParameterJdbcTemplate tplSelect = getNamedJdbcTemplate();

	    StringBuilder sql = new StringBuilder(SQL_BASIC_SELECT + " WHERE upper( r.last_name) LIKE :last_name "
		    + " AND (r.rec_sht_typ BETWEEN 2 AND 4) ");
	    if (storeId != null){
		sql.append(" AND r.store_id = :store_id");;
	    }

	    sql.append(" order by upper(r.last_name), upper(r.first_name), r.created_on desc, r.mobile_no");

	    Map<String, Object> params = new HashMap<String, Object>();

	    params.put("last_name", "%" + lastName.toUpperCase() + "%");
	    params.put("store_id", storeId);

	    list = tplSelect.query(sql.toString(), params, new RecommendationsRowMapper());
	    this.mapEssentials(list);
	}
	return list;
    }

    @Override
    public List<Recommendation> getRecommendationListForCAndTByPhone(String storeId, String phoneNumber)
	    throws DataAccessException {
	List<Recommendation> list = null;

	if (phoneNumber != null){

	    NamedParameterJdbcTemplate tplSelect = this.getNamedJdbcTemplate();

	    StringBuilder sql = new StringBuilder(SQL_BASIC_SELECT + " WHERE r.mobile_no = :mobile_no"
		    + " AND (r.rec_sht_typ BETWEEN 2 AND 4) ");
	    if (storeId != null){
		sql.append(" AND r.store_id = :store_id");
	    }
	    sql.append(" order by  r.created_on desc, upper(r.last_name), upper(r.first_name)");

	    Map<String, Object> params = new HashMap<String, Object>();
	    params.put("mobile_no", phoneNumber);
	    params.put("store_id", storeId);

	    list = tplSelect.query(sql.toString(), params, new RecommendationsRowMapper());
	    this.mapEssentials(list);
	}
	return list;
    }

    @Override
    public List<RecSheetReportingSearchResults> getRecommendationEmployeeNamesByAId(String aId)
	    throws DataAccessException {
	List<RecSheetReportingSearchResults> list = null;
	NamedParameterJdbcTemplate tplSelect = this.getNamedJdbcTemplate();

	String sql = "SELECT distinct r.created_by, r.emp_crt_frst_nm, r.emp_crt_last_nm " + SQL_SELECT_FROM
		+ " WHERE r.created_by = :created_by AND r.rec_sht_typ = 1 ";

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("created_by", aId.toUpperCase());

	list = tplSelect.query(sql, params, new RecSheetReportingSearchResultsRowMapper());

	return list;
    }

    @Override
    public List<Recommendation> getRecommendationReportsListByAId(String aId, Date startDate, Date endDate)
	    throws DataAccessException {
	return getRecommendationReportsListByAId(aId, startDate, endDate, null);
    }

    @Override
    public List<Recommendation> getRecommendationReportsListByAId(String aId, Date startDate, Date endDate,
	    String storeId) throws DataAccessException {

	if (StringUtils.isEmpty(aId) || startDate == null || endDate == null){
	    return Collections.emptyList();
	}

	List<Recommendation> list = null;

	if (aId != null){
	    NamedParameterJdbcTemplate tplSelect = this.getNamedJdbcTemplate();

	    String sql = SQL_BASIC_SELECT
		    + " WHERE (r.created_by = :created_by"
		    + " AND r.created_on >= trunc(:created_on_start) AND r.created_on < trunc(:created_on_end) + 1) AND r.rec_sht_typ = 1 ";

	    Map<String, Object> params = new HashMap<String, Object>();

	    if (storeId != null){
		sql = sql + " AND store_id = :store_id";
		params.put("store_id", storeId);
	    }

	    sql = sql + " ORDER BY created_on";

	    params.put("created_by", aId.toUpperCase());
	    params.put("created_on_start", startDate);
	    params.put("created_on_end", endDate);

	    list = tplSelect.query(sql, params, new RecommendationsRowMapper());
	    this.mapEssentials(list);
	}

	return list;
    }

    @Override
    public List<RecSheetReportingSearchResults> getRecommendationEmployeeNamesByLastName(String empLastName)
	    throws DataAccessException {
	if (StringUtils.isEmpty(empLastName)){
	    return Collections.emptyList();
	}

	List<RecSheetReportingSearchResults> list = null;
	NamedParameterJdbcTemplate tplSelect = this.getNamedJdbcTemplate();

	String sql = "SELECT distinct r.created_by, r.emp_crt_frst_nm, r.emp_crt_last_nm " + SQL_SELECT_FROM
		+ " WHERE r.emp_crt_last_nm = :emp_crt_last_nm AND r.rec_sht_typ = 1 ";

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("emp_crt_last_nm", empLastName.toUpperCase());

	list = tplSelect.query(sql, params, new RecSheetReportingSearchResultsRowMapper());

	return list;
    }

    @Override
    public List<RecSheetCountByDay> getRecommendationListFromToDate(Date startDate, Date endDate, String storeId)
	    throws DataAccessException {
	List<RecSheetCountByDay> list = null;
	if (startDate != null && endDate != null){

	    final String sql = "SELECT store_id,  recommendation_id,  emp_last_name,   emp_frst_name,   changedby,   changedate,   COUNT(*) rec_sht_cnt "
		    + "FROM   (SELECT store_id,      emp_crt_last_nm emp_last_name,      emp_crt_frst_nm emp_frst_name,      "
		    + "created_by changedby,      to_char(created_on,    'yyyymmdd') changedate,      recommendation_id    "
		    + "FROM bst_ise_sch01.recommendation    WHERE store_id = :store_id    AND created_on >=  trunc(:created_on_start)   "
		    + "AND created_on < trunc(:created_on_end) + 1    UNION    SELECT store_id,      emp_alt_last_nm emp_last_name,      emp_alt_frst_nm emp_frst_name,      "
		    + "amended_by changedby,      to_char(amended_on,    'yyyymmdd') changedate,      recommendation_id    "
		    + "FROM bst_ise_sch01.recommendation    WHERE store_id = :store_id    AND amended_on >= trunc(:amended_on_start)    AND amended_on < trunc(:amended_on_end) + 1) "
		    + "GROUP BY store_id,  recommendation_id,   emp_last_name,   emp_frst_name,   changedby,   changedate ORDER BY store_id,   emp_last_name,   "
		    + "emp_frst_name,   changedby,   changedate";

	    Map<String, Object> params = new HashMap<String, Object>();

	    params.put("store_id", storeId);
	    params.put("created_on_start", startDate);
	    params.put("created_on_end", endDate);
	    params.put("amended_on_start", startDate);
	    params.put("amended_on_end", endDate);

	    list = getNamedJdbcTemplate().query(sql, params, new RecommendationsByDayMapper());

	}
	return list;
    }

    public void setEssentialsDao(EssentialsDao essentialsDao) {
	this.essentialsDao = essentialsDao;
    }

    public EssentialsDao getEssentialsDao() {
	return essentialsDao;
    }

    private void mapEssentials(List<Recommendation> recommendations) throws DataAccessException {

	for(Recommendation rec: recommendations){

	    Map<Long, String> preferences = essentialsDao.getEssentials(rec.getId());
	    rec.setPreferences(preferences);
	}
    }

    @Override
    public List<Recommendation> getRecommendationList(Date fromDate, Date toDate) throws DataAccessException {
	if (fromDate == null || toDate == null){
	    return Collections.emptyList();
	}
	NamedParameterJdbcTemplate tplSelect = this.getNamedJdbcTemplate();

	String sql = SQL_BASIC_SELECT
		+ " WHERE r.rec_sht_typ = 1 AND r.created_on >= trunc(:created_on_start) AND r.created_on < trunc(:created_on_end) + 1";

	Map<String, Object> params = new HashMap<String, Object>();
	params.put("created_on_start", fromDate);
	params.put("created_on_end", toDate);
	List<Recommendation> list = tplSelect.query(sql, params, new RecommendationsRowMapper());
	this.mapEssentials(list);
	return list;
    }

}
