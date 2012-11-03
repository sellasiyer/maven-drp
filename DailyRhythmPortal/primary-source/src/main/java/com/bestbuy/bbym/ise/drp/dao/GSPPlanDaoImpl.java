package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * DB operations implementation class for GSP_PLAN table
 * 
 * @author Sridhar Savaram
 * @version
 * @since
 */
@Repository("gspPlanDao")
public class GSPPlanDaoImpl extends AbstractDao implements GSPPlanDao {

    private final String GSP_PLAN_INSERT_QUERY = "INSERT INTO bst_ise_sch01.gsp_plan(id, dta_sharing_key,   bill_typ,   bsns_dt,   cncl_flg,   cntrct_sku, "
	    + "cntrct_sku_desc, exp_dt, mthly_pymt, plan_typ, protection_plan_id, rgst_trans_nbr, stor_id, wrkstn_id, created_by, created_on) "
	    + "VALUES(:id, :datasharingkey,   :billingType,   :businessDate,   :cancelFlag,   :contractSku, :contractSkuDesc, :expiryDate, "
	    + ":monthlyPayment, :planType, :protectionPlanId, :registerTransNum, :storeId, :workStationId, :createdBy,   sysdate)";

    private final String GSP_PLAN_CANCEL_SELECT_QUERY = "select id, dta_sharing_key,   bill_typ,   bsns_dt,   cncl_flg,   cntrct_sku, cntrct_sku_desc, "
	    + "exp_dt, mthly_pymt, plan_typ, protection_plan_id, rgst_trans_nbr, stor_id, wrkstn_id, created_by, created_on,   amended_by,   amended_on "
	    + "from bst_ise_sch01.gsp_plan ";

    private final String WHERE_CLAUSE_BY_DATA_SHARING_KEY = "where dta_sharing_key = ? and cncl_flg = 1";
    private final String WHERE_CLAUSE_BY_ID = "where id = ?";

    @Override
    public void persistGSPPlan(GSPPlan gspPlan) throws DataAccessException, RuntimeException {
	Map<String, Object> params = new HashMap<String, Object>();
	params.put("id", UUID.randomUUID().toString());
	params.put("datasharingkey", gspPlan.getDataSharingKey());
	params.put("billingType", gspPlan.getBillingType());
	params.put("businessDate", gspPlan.getBusinessDate());
	params.put("cancelFlag", (gspPlan.isCancel()?"1":"0"));
	params.put("contractSku", gspPlan.getContractSKU());
	params.put("contractSkuDesc", gspPlan.getContractSKUDescription());
	params.put("expiryDate", gspPlan.getExpirationDate());
	params.put("planType", gspPlan.getPlanType());
	params.put("monthlyPayment", gspPlan.getMonthlyPayment());
	params.put("protectionPlanId", gspPlan.getProtectionPlanId());
	params.put("registerTransNum", gspPlan.getRegisterTransactionNumber());
	params.put("storeId", gspPlan.getStoreId());
	params.put("workStationId", gspPlan.getWorkstationId());
	params.put("createdBy", gspPlan.getCreatedBy());

	getNamedJdbcTemplate().update(GSP_PLAN_INSERT_QUERY, params);
    }

    @Override
    public List<GSPPlan> getGSPPlansMarkedForCancel(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().query(GSP_PLAN_CANCEL_SELECT_QUERY + WHERE_CLAUSE_BY_DATA_SHARING_KEY,
		new Object[] {dataSharingKey }, new GSPPlanRowMapper());
    }

    private class GSPPlanRowMapper implements RowMapper<GSPPlan> {

	@Override
	public GSPPlan mapRow(ResultSet rs, int rowNum) throws SQLException {
	    GSPPlan gspPlan = new GSPPlan();
	    gspPlan.setId(rs.getString("id"));
	    gspPlan.setDataSharingKey(rs.getString("dta_sharing_key"));
	    gspPlan.setBillingType(rs.getString("bill_typ"));
	    gspPlan.setBusinessDate(rs.getDate("bsns_dt"));
	    gspPlan.setCancel(rs.getInt("cncl_flg") == 1?true:false);
	    gspPlan.setContractSKU(rs.getString("cntrct_sku"));
	    gspPlan.setContractSKUDescription(rs.getString("cntrct_sku_desc"));
	    gspPlan.setExpirationDate(rs.getDate("exp_dt"));
	    gspPlan.setMonthlyPayment(rs.getString("mthly_pymt"));
	    gspPlan.setPlanType(rs.getString("plan_typ"));
	    gspPlan.setProtectionPlanId(rs.getString("protection_plan_id"));
	    gspPlan.setRegisterTransactionNumber(rs.getString("rgst_trans_nbr"));
	    gspPlan.setStoreId(rs.getString("stor_id"));
	    gspPlan.setWorkstationId(rs.getString("wrkstn_id"));
	    gspPlan.setCreatedBy(rs.getString("created_by"));
	    gspPlan.setCreatedOn(rs.getDate("created_on"));
	    gspPlan.setModifiedBy(rs.getString("amended_by"));
	    gspPlan.setModifiedOn(rs.getDate("amended_on"));
	    return gspPlan;
	}
    }

    @Override
    public GSPPlan getGSPPlan(String id) throws DataAccessException, EmptyResultDataAccessException, RuntimeException {
	return getJdbcTemplate().queryForObject(GSP_PLAN_CANCEL_SELECT_QUERY + WHERE_CLAUSE_BY_ID, new Object[] {id },
		new GSPPlanRowMapper());
    }
}
