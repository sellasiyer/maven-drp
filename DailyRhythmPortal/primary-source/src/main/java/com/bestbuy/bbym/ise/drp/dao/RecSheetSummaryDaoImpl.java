package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * DB operations implementation class for REC_SHT_SUMM table
 * 
 * @author Sridhar Savaram
 */
@Repository("recSheetSummaryDao")
public class RecSheetSummaryDaoImpl extends AbstractDao implements RecSheetSummaryDao {

    private final String REC_SHT_SUMM_INSERT_QUERY = "insert into BST_ISE_SCH01.rec_sht_summ (dta_sharing_key, plan_info, device_info, gsp_plan_info, "
	    + "buyback_plan_info, created_by, created_on)  "
	    + "values (:dataSharingKey, :planInfo, :deviceInfo, :gspPlanInfo, :buyBackPlanInfo, :createdBy, sysdate)";

    private final String REC_SHT_SUMM_SELECT_QUERY = "SELECT dta_sharing_key, plan_info, device_info, gsp_plan_info, buyback_plan_info, created_by, "
	    + "created_on, amended_by, amended_on from BST_ISE_SCH01.rec_sht_summ WHERE dta_sharing_key = ?";

    private final String REC_SHT_SUMM_DELETE_QUERY = "DELETE FROM BST_ISE_SCH01.rec_sht_summ WHERE dta_sharing_key = :dataSharingKey";

    private final String REC_SHT_SUMM_UPDATE_QUERY = "update BST_ISE_SCH01.rec_sht_summ set plan_info = :planInfo, device_info = :deviceInfo, "
	    + "gsp_plan_info = :gspPlanInfo, buyback_plan_info = :buyBackPlanInfo,  "
	    + " amended_by = :modifiedBy, amended_on = sysdate where dta_sharing_key =:dataSharingKey ";

    /**
     * Persists RecSheetSummary into database table: REC_SHT_SUMM
     */
    @Override
    public void persistRecSheetSummary(RecSheetSummary recSheetSummary) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", recSheetSummary.getDataSharingKey());
	params.put("planInfo", recSheetSummary.getPlanInfo());
	params.put("deviceInfo", recSheetSummary.getDeviceInfo());
	params.put("gspPlanInfo", recSheetSummary.getGspPlanInfo());
	params.put("buyBackPlanInfo", recSheetSummary.getBuyBackPlanInfo());
	params.put("createdBy", recSheetSummary.getCreatedBy());

	getNamedJdbcTemplate().update(REC_SHT_SUMM_INSERT_QUERY, params);
    }

    @Override
    public RecSheetSummary getRecSheetSummary(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().queryForObject(REC_SHT_SUMM_SELECT_QUERY, new Object[] {dataSharingKey },
		new RecSheetSummaryRowMapper());
    }

    @Override
    public void updateRecSheetSummary(RecSheetSummary recSheetSummary) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();

	params.put("dataSharingKey", recSheetSummary.getDataSharingKey());
	params.put("planInfo", recSheetSummary.getPlanInfo());
	params.put("deviceInfo", recSheetSummary.getDeviceInfo());
	params.put("gspPlanInfo", recSheetSummary.getGspPlanInfo());
	params.put("buyBackPlanInfo", recSheetSummary.getBuyBackPlanInfo());

	params.put("modifiedBy", recSheetSummary.getModifiedBy());

	getNamedJdbcTemplate().update(REC_SHT_SUMM_UPDATE_QUERY, params);
    }

    private class RecSheetSummaryRowMapper implements RowMapper<RecSheetSummary> {

	@Override
	public RecSheetSummary mapRow(ResultSet rs, int rowNum) throws SQLException {
	    RecSheetSummary recSheetSummary = new RecSheetSummary();
	    recSheetSummary.setDataSharingKey(rs.getString("dta_sharing_key"));
	    recSheetSummary.setPlanInfo(rs.getString("plan_info"));
	    recSheetSummary.setDeviceInfo(rs.getString("device_info"));
	    recSheetSummary.setGspPlanInfo(rs.getString("gsp_plan_info"));
	    recSheetSummary.setBuyBackPlanInfo(rs.getString("buyback_plan_info"));
	    recSheetSummary.setCreatedBy(rs.getString("created_by"));
	    recSheetSummary.setCreatedOn(rs.getDate("created_on"));
	    recSheetSummary.setModifiedBy(rs.getString("amended_by"));
	    recSheetSummary.setModifiedOn(rs.getDate("amended_on"));
	    return recSheetSummary;
	}

    }

    @Override
    public void deleteRecSheetSummary(String dataSharingKey) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("dataSharingKey", dataSharingKey);
	getNamedJdbcTemplate().update(REC_SHT_SUMM_DELETE_QUERY, params);

    }

}
