/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;

/**
 * Implementation of triage DAO operations.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 22
 */
@Repository("triageDao")
public class TriageDaoImpl extends AbstractDao implements TriageDao {

    private static final String SQL_ACTION_SELECT_BY_RCMD = "SELECT action.* FROM BST_ISE_SCH01.TRIAGE_ACTN action, "
	    + "BST_ISE_SCH01.TRIAGE_RCMD_ACTN arcmd "
	    + "WHERE action.act_flg = 1 and action.actn_id = arcmd.actn_id and arcmd.rcmd_id =? "
	    + "ORDER BY arcmd.displ_ord ASC";
    private static final String SQL_ACTION_INSERT = "INSERT INTO BST_ISE_SCH01.TRIAGE_ACTN "
	    + "(actn_id,actn,act_flg,created_by,created_date,amended_by,amended_on)"
	    + "VALUES (BST_ISE_SCH01.TRIAGE_ACTN_SEQ.nextval,:actn,:act_flg,:created_by,sysdate,NULL,NULL)";
    private static final String SQL_ACTION_UPDATE = "UPDATE BST_ISE_SCH01.TRIAGE_ACTN SET actn=:actn,act_flg=:act_flg,"
	    + "amended_by=:amended_by,amended_on=sysdate " + "WHERE actn_id=:actn_id";
    private static final String SQL_ACTION_DELETE = "DELETE FROM BST_ISE_SCH01.TRIAGE_ACTN WHERE actn_id=:actn_id";

    private static final String SQL_EVENT_SELECT_BY_DEVICE_SERIAL_NO = "SELECT th.*,"
	    + "rcmd.rcmd_id rcmd_rcmd_id, rcmd.rcmd rcmd_rcmd, rcmd.issu_id rcmd_issu_id, issue.issu_desc rcmd_issu_desc, rcmd.created_by rcmd_created_by,"
	    + "rcmd.created_date rcmd_created_date, rcmd.amended_by rcmd_amended_by, rcmd.amended_on rcmd_amended_on,"
	    + "rsln.rsnl_id rsln_rsnl_id, rsln.rsln_desc rsln_rsln_desc,rsln.displ_ord rsln_displ_ord,"
	    + "rsln.act_flg rsln_act_flg, rsln.created_by rsln_created_by,rsln.created_date rsln_created_date,"
	    + "rsln.amended_by rsln_amended_by,rsln.amended_on rsln_amended_on FROM BST_ISE_SCH01.TRIAGE_HIST th, "
	    + "BST_ISE_SCH01.TRIAGE_RCMD rcmd, BST_ISE_SCH01.TRIAGE_RSLN_LKP rsln ,BST_ISE_SCH01.TRIAGE_ISSU_LKP issue "
	    + "WHERE th.rcmd_id = rcmd.rcmd_id and th.rsnl_id = rsln.rsnl_id and issue.issu_id = rcmd.issu_id and th.device_sn=? "
	    + "ORDER BY th.created_date DESC";
    private static final String SQL_EVENT_INSERT = "INSERT INTO BST_ISE_SCH01.TRIAGE_HIST "
	    + "(hist_id,device_sn,issu_cmnt,rsln_cmnt,protection_plan_id,tech_chkr_issues,rcmd_id,rsnl_id,"
	    + "created_by,created_date,amended_by,amended_on,cust_beni)"
	    + "VALUES (BST_ISE_SCH01.TRIAGE_HIST_SEQ.nextval,:device_sn,:issu_cmnt,:rsln_cmnt,:protection_plan_id,:tech_chkr_issues,"
	    + ":rcmd_id,:rsnl_id,:created_by,sysdate,NULL,NULL,:cust_beni)";
    private static final String SQL_EVENT_UPDATE = "UPDATE BST_ISE_SCH01.TRIAGE_HIST SET device_sn=:device_sn, issu_cmnt=:issu_cmnt,"
	    + "rsln_cmnt=:rsln_cmnt, protection_plan_id=:protection_plan_id,tech_chkr_issues=:tech_chkr_issues,rcmd_id=:rcmd_id,"
	    + "rsnl_id=:rsnl_id,amended_by=:amended_by,amended_on=sysdate, cust_beni=:cust_beni "
	    + "WHERE hist_id=:hist_id";
    private static final String SQL_EVENT_DELETE = "DELETE FROM BST_ISE_SCH01.TRIAGE_HIST WHERE hist_id=:hist_id";

    private static final String SQL_ISSUE_INSERT = "INSERT INTO BST_ISE_SCH01.TRIAGE_ISSU_LKP "
	    + "(issu_id,issu_desc,tooltip,displ_ord,act_flg,created_by,created_date,amended_by,amended_on) "
	    + "VALUES (BST_ISE_SCH01.TRIAGE_ISSU_LKP_SEQ.nextval,:issu_desc,:tooltip,:displ_ord,:act_flg,:created_by,sysdate,NULL,NULL)";
    private static final String SQL_ISSUE_UPDATE = "UPDATE BST_ISE_SCH01.TRIAGE_ISSU_LKP "
	    + "SET issu_desc=:issu_desc, tooltip=:tooltip, displ_ord=:displ_ord, act_flg=:act_flg, "
	    + "amended_by=:amended_by, amended_on=sysdate " + "WHERE issu_id=:issu_id";
    private static final String SQL_ISSUE_DELETE = "DELETE FROM BST_ISE_SCH01.TRIAGE_ISSU_LKP WHERE issu_id=:issu_id";
    private static final String SQL_ISSUE_SELECT = "SELECT * FROM BST_ISE_SCH01.TRIAGE_ISSU_LKP t WHERE act_flg=1 ORDER BY displ_ord ASC";

    private static final String SQL_RECOMMENDATION_SELECT = "SELECT * FROM "
	    + "(SELECT 0 AS is_default, r.* FROM BST_ISE_SCH01.TRIAGE_RCMD r, BST_ISE_SCH01.TRIAGE_RCMD_SKU rs "
	    + "WHERE r.issu_id = ? AND r.rcmd_id = rs.rcmd_id AND rs.sku = ? "
	    + "UNION SELECT 1 AS is_default, r.* FROM BST_ISE_SCH01.TRIAGE_RCMD r, BST_ISE_SCH01.TRIAGE_RCMD_SKU rs "
	    + "WHERE r.issu_id = ? AND r.rcmd_id = rs.rcmd_id AND (rs.sku = NULL OR rs.sku = ?))" + "ORDER BY 1 ASC";
    private static final String DEFAULT_RCMD_SKU_VALUE = "0";

    private static final String SQL_RSLN_SELECT = "SELECT rsln.* FROM BST_ISE_SCH01.TRIAGE_RSLN_LKP rsln "
	    + "WHERE rsln.act_flg = 1 ORDER BY rsln.displ_ord ASC";
    private static final String SQL_RSLN_INSERT = "INSERT INTO BST_ISE_SCH01.TRIAGE_RSLN_LKP "
	    + "(rsnl_id,rsln_desc,displ_ord,act_flg,created_by,created_date,amended_by,amended_on)"
	    + "VALUES (BST_ISE_SCH01.TRIAGE_RSLN_LKP_SEQ.nextval,:rsln_desc,:displ_ord,:act_flg,:created_by,sysdate,NULL,NULL)";
    private static final String SQL_RSLN_UPDATE = "UPDATE BST_ISE_SCH01.TRIAGE_RSLN_LKP SET rsln_desc=:rsln_desc, displ_ord=:displ_ord,"
	    + "act_flg=:act_flg,amended_by=:amended_by,amended_on=sysdate " + "WHERE rsnl_id=:rsnl_id";
    private static final String SQL_RSLN_DELETE = "DELETE FROM BST_ISE_SCH01.TRIAGE_RSLN_LKP WHERE rsnl_id=:rsnl_id";

    @Override
    public TriageAction persist(TriageAction triageAction) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	getNamedJdbcTemplate().update(SQL_ACTION_INSERT, buildParams(triageAction), keyHolder,
		new String[] {"actn_id" });
	triageAction.setId(keyHolder.getKey().longValue());
	return triageAction;
    }

    private SqlParameterSource buildParams(TriageAction triageAction) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("actn_id", triageAction.getId());
	params.addValue("actn", triageAction.getAction());
	params.addValue("act_flg", triageAction.isActiveFlag()?1:0);
	params.addValue("created_by", triageAction.getCreatedBy());
	params.addValue("created_date", triageAction.getCreatedOn());
	params.addValue("amended_by", triageAction.getModifiedBy());
	params.addValue("amended_on", triageAction.getModifiedOn());
	return params;
    }

    @Override
    public TriageAction update(TriageAction triageAction) {
	getNamedJdbcTemplate().update(SQL_ACTION_UPDATE, buildParams(triageAction));
	return triageAction;
    }

    @Override
    public void delete(TriageAction triageAction) {
	getNamedJdbcTemplate().update(SQL_ACTION_DELETE, buildParams(triageAction));
    }

    @Override
    public List<TriageAction> getTriageActionActiveListByRcmd(TriageRecommendation rcmd) {
	return getJdbcTemplate().query(SQL_ACTION_SELECT_BY_RCMD, new Object[] {rcmd.getId() },
		new TriageActionRowMapper());
    }

    class TriageActionRowMapper implements RowMapper<TriageAction> {

	@Override
	public TriageAction mapRow(ResultSet rs, int rowNum) throws SQLException {
	    TriageAction triageAction = new TriageAction();
	    triageAction.setId(rs.getLong("actn_id"));
	    triageAction.setAction(rs.getString("actn"));
	    triageAction.setActiveFlag(rs.getLong("act_flg") == 1?true:false);
	    triageAction.setCreatedBy(rs.getString("created_by"));
	    triageAction.setCreatedOn(rs.getDate("created_date"));
	    triageAction.setModifiedBy(rs.getString("amended_by"));
	    triageAction.setModifiedOn(rs.getDate("amended_on"));
	    return triageAction;
	}
    }

    @Override
    public TriageEvent persist(TriageEvent triageEvent) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	getNamedJdbcTemplate().update(SQL_EVENT_INSERT, buildParams(triageEvent), keyHolder, new String[] {"hist_id" });
	triageEvent.setId(keyHolder.getKey().longValue());
	return triageEvent;
    }

    private SqlParameterSource buildParams(TriageEvent triageEvent) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("hist_id", triageEvent.getId());
	params.addValue("device_sn", triageEvent.getDeviceSerialNo());
	params.addValue("issu_cmnt", triageEvent.getIssueComment());
	params.addValue("rsln_cmnt", triageEvent.getResolutionComment());
	params.addValue("protection_plan_id", triageEvent.getProtectionPlanId());
	params.addValue("tech_chkr_issues", triageEvent.getTechCheckerIssues());
	params.addValue("rcmd_id", triageEvent.getTriageRecommendation().getId());
	params.addValue("rsnl_id", triageEvent.getTriageResolution().getId());
	params.addValue("created_by", triageEvent.getCreatedBy());
	params.addValue("created_date", triageEvent.getCreatedOn());
	params.addValue("amended_by", triageEvent.getModifiedBy());
	params.addValue("amended_on", triageEvent.getModifiedOn());
	params.addValue("cust_beni", triageEvent.getCustomerBenefits());
	return params;
    }

    @Override
    public TriageEvent update(TriageEvent triageEvent) {
	getNamedJdbcTemplate().update(SQL_EVENT_UPDATE, buildParams(triageEvent));
	return triageEvent;
    }

    @Override
    public void delete(TriageEvent triageEvent) {
	getNamedJdbcTemplate().update(SQL_EVENT_DELETE, buildParams(triageEvent));
    }

    @Override
    public List<TriageEvent> getTriageEventByDeviceSerialNo(String serialNo) {
	return getJdbcTemplate().query(SQL_EVENT_SELECT_BY_DEVICE_SERIAL_NO, new Object[] {serialNo },
		new TriageEventRowMapper());
    }

    class TriageEventRowMapper implements RowMapper<TriageEvent> {

	@Override
	public TriageEvent mapRow(ResultSet rs, int rowNum) throws SQLException {
	    TriageEvent triageEvent = new TriageEvent();
	    triageEvent.setId(rs.getLong("hist_id"));
	    triageEvent.setDeviceSerialNo(rs.getString("device_sn"));
	    triageEvent.setIssueComment(rs.getString("issu_cmnt"));
	    triageEvent.setResolutionComment(rs.getString("rsln_cmnt"));
	    triageEvent.setProtectionPlanId(rs.getString("protection_plan_id"));
	    triageEvent.setTechCheckerIssues(rs.getString("tech_chkr_issues"));
	    triageEvent.setCustomerBenefits(rs.getString("cust_beni"));

	    TriageRecommendation rcmd = new TriageRecommendation();
	    rcmd.setCreatedBy(rs.getString("rcmd_created_by"));
	    rcmd.setCreatedOn(rs.getDate("rcmd_created_date"));
	    rcmd.setId(rs.getLong("rcmd_rcmd_id"));
	    rcmd.setModifiedBy(rs.getString("rcmd_amended_by"));
	    rcmd.setModifiedOn(rs.getDate("rcmd_amended_on"));
	    rcmd.setRecommendation(rs.getString("rcmd_rcmd"));
	    rcmd.setTriageIssueId(rs.getLong("rcmd_issu_id"));
	    rcmd.setIssueDisplayText(rs.getString("rcmd_issu_desc"));
	    triageEvent.setTriageRecommendation(rcmd);

	    TriageResolution rsln = new TriageResolution();
	    rsln.setCreatedBy(rs.getString("rsln_created_by"));
	    rsln.setCreatedOn(rs.getDate("rsln_created_date"));
	    rsln.setId(rs.getLong("rsln_rsnl_id"));
	    rsln.setModifiedBy(rs.getString("rsln_amended_by"));
	    rsln.setModifiedOn(rs.getDate("rsln_amended_on"));
	    rsln.setActiveFlag(rs.getInt("rsln_act_flg") > 0?true:false);
	    rsln.setDisplayOrder(rs.getInt("rsln_displ_ord"));
	    rsln.setResolutionDesc(rs.getString("rsln_rsln_desc"));
	    triageEvent.setTriageResolution(rsln);

	    triageEvent.setCreatedBy(rs.getString("created_by"));
	    triageEvent.setCreatedOn(rs.getDate("created_date"));
	    triageEvent.setModifiedBy(rs.getString("amended_by"));
	    triageEvent.setModifiedOn(rs.getDate("amended_on"));
	    return triageEvent;
	}
    }

    @Override
    public TriageIssue persist(TriageIssue triageIssue) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	getNamedJdbcTemplate().update(SQL_ISSUE_INSERT, buildParams(triageIssue), keyHolder, new String[] {"issu_id" });
	triageIssue.setId(keyHolder.getKey().longValue());
	return triageIssue;
    }

    private SqlParameterSource buildParams(TriageIssue triageIssue) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("issu_id", triageIssue.getId());
	params.addValue("issu_desc", triageIssue.getIssueDesc());
	params.addValue("tooltip", triageIssue.getTooltip());
	params.addValue("displ_ord", triageIssue.getDisplayOrder());
	params.addValue("act_flg", triageIssue.isActiveFlag()?1:0);
	params.addValue("created_by", triageIssue.getCreatedBy());
	params.addValue("created_date", triageIssue.getCreatedOn());
	params.addValue("amended_by", triageIssue.getModifiedBy());
	params.addValue("amended_on", triageIssue.getModifiedOn());
	return params;
    }

    @Override
    public TriageIssue update(TriageIssue triageIssue) {
	getNamedJdbcTemplate().update(SQL_ISSUE_UPDATE, buildParams(triageIssue));
	return triageIssue;
    }

    @Override
    public void delete(TriageIssue triageIssue) {
	getNamedJdbcTemplate().update(SQL_ISSUE_DELETE, buildParams(triageIssue));

    }

    @Override
    public List<TriageIssue> getTriageIssueActiveList() {
	return getJdbcTemplate().query(SQL_ISSUE_SELECT, new TriageIssueRowMapper());
    }

    class TriageIssueRowMapper implements RowMapper<TriageIssue> {

	@Override
	public TriageIssue mapRow(ResultSet rs, int rowNum) throws SQLException {
	    TriageIssue triageIssue = new TriageIssue();
	    triageIssue.setId(rs.getLong("issu_id"));
	    triageIssue.setIssueDesc(rs.getString("issu_desc"));
	    triageIssue.setTooltip(rs.getString("tooltip"));
	    triageIssue.setDisplayOrder(rs.getInt("displ_ord"));
	    triageIssue.setActiveFlag(rs.getLong("act_flg") == 1?true:false);
	    triageIssue.setCreatedBy(rs.getString("created_by"));
	    triageIssue.setCreatedOn(rs.getDate("created_date"));
	    triageIssue.setModifiedBy(rs.getString("amended_by"));
	    triageIssue.setModifiedOn(rs.getDate("amended_on"));
	    return triageIssue;
	}
    }

    @Override
    public TriageRecommendation getTriageRecommendationBySku(TriageIssue triageIssue, String sku) {
	List<TriageRecommendation> list = getJdbcTemplate().query(SQL_RECOMMENDATION_SELECT,
		new Object[] {triageIssue.getId(), sku, triageIssue.getId(), DEFAULT_RCMD_SKU_VALUE },
		new TriageRecommendationRowMapper());
	if (list.isEmpty()){
	    return null;
	}else{
	    return list.get(0);
	}

    }

    class TriageRecommendationRowMapper implements RowMapper<TriageRecommendation> {

	@Override
	public TriageRecommendation mapRow(ResultSet rs, int rowNum) throws SQLException {
	    TriageRecommendation rcmd = new TriageRecommendation();
	    rcmd.setId(rs.getLong("rcmd_id"));
	    rcmd.setRecommendation(rs.getString("rcmd"));
	    rcmd.setTriageIssueId(rs.getLong("issu_id"));
	    rcmd.setCreatedBy(rs.getString("created_by"));
	    rcmd.setCreatedOn(rs.getDate("created_date"));
	    rcmd.setModifiedBy(rs.getString("amended_by"));
	    rcmd.setModifiedOn(rs.getDate("amended_on"));
	    return rcmd;
	}
    }

    @Override
    public TriageResolution persist(TriageResolution triageResolution) {
	KeyHolder keyHolder = new GeneratedKeyHolder();
	getNamedJdbcTemplate().update(SQL_RSLN_INSERT, buildParams(triageResolution), keyHolder,
		new String[] {"rsnl_id" });
	triageResolution.setId(keyHolder.getKey().longValue());
	return triageResolution;
    }

    private SqlParameterSource buildParams(TriageResolution triageResolution) {
	MapSqlParameterSource params = new MapSqlParameterSource();
	params.addValue("rsnl_id", triageResolution.getId());
	params.addValue("rsln_desc", triageResolution.getResolutionDesc());
	params.addValue("displ_ord", triageResolution.getDisplayOrder());
	params.addValue("act_flg", triageResolution.isActiveFlag()?1:0);
	params.addValue("created_by", triageResolution.getCreatedBy());
	params.addValue("created_date", triageResolution.getCreatedOn());
	params.addValue("amended_by", triageResolution.getModifiedBy());
	params.addValue("amended_on", triageResolution.getModifiedOn());
	return params;
    }

    @Override
    public TriageResolution update(TriageResolution TriageResolution) {
	getNamedJdbcTemplate().update(SQL_RSLN_UPDATE, buildParams(TriageResolution));
	return TriageResolution;
    }

    @Override
    public void delete(TriageResolution TriageResolution) {
	getNamedJdbcTemplate().update(SQL_RSLN_DELETE, buildParams(TriageResolution));

    }

    @Override
    public List<TriageResolution> getTriageResolutionActiveList() {
	return getJdbcTemplate().query(SQL_RSLN_SELECT, new TriageResolutionRowMapper());
    }

    class TriageResolutionRowMapper implements RowMapper<TriageResolution> {

	@Override
	public TriageResolution mapRow(ResultSet rs, int rowNum) throws SQLException {
	    TriageResolution triageResolution = new TriageResolution();
	    triageResolution.setId(rs.getLong("rsnl_id"));
	    triageResolution.setResolutionDesc(rs.getString("rsln_desc"));
	    triageResolution.setDisplayOrder(rs.getInt("displ_ord"));
	    triageResolution.setActiveFlag(rs.getLong("act_flg") == 1?true:false);
	    triageResolution.setCreatedBy(rs.getString("created_by"));
	    triageResolution.setCreatedOn(rs.getDate("created_date"));
	    triageResolution.setModifiedBy(rs.getString("amended_by"));
	    triageResolution.setModifiedOn(rs.getDate("amended_on"));
	    return triageResolution;
	}
    }
}
