package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.RetExchDevcEntitlsModel;
import com.bestbuy.bbym.ise.exception.DataAccessException;

@SuppressWarnings("unchecked")
@Repository("retExchDevcEntitlsDao")
public class RetExchDevcEntitlsDaoImpl extends AbstractDao implements RetExchDevcEntitlsDao {

    private static Logger logger = LoggerFactory.getLogger(RetExchDevcEntitlsDaoImpl.class);

    private static final String sqlgetDeviceDetails = "SELECT dta_sharing_key, handset_id, ph_nbr, trans_key, covg_beni, covg_beni_prmpt_cde, "
	    + "covg_vhcl, type, protection_plan_id, dfec_stat, created_by, created_on, amended_by, amended_on FROM bst_ise_sch01.ret_exch_devc_entitl "
	    + "WHERE created_on > TRUNC(sysdate)  AND(handset_id = ? OR ph_nbr = ?) ORDER BY nvl(amended_on,   created_on) DESC";

    private static final String sqlInsertDeviceEntitlement = "INSERT INTO bst_ise_sch01.ret_exch_devc_entitl "
	    + "(dta_sharing_key, handset_id,ph_nbr,trans_key,covg_beni,covg_beni_prmpt_cde,covg_vhcl,type, "
	    + "protection_plan_id,dfec_stat,created_by,created_on,amended_by,amended_on) VALUES "
	    + "(:data_sharing_key, :handset_id,:ph_nbr,:trans_key,:covg_beni,:covg_beni_prmpt_cde,:covg_vhcl,"
	    + ":type, :protection_plan_id,:dfec_stat,:created_by,:created_on,:amended_by,:amended_on)";

    private static final String sqlInsertDataXferSummary = "insert into BST_ISE_SCH01.dta_xfer (dta_sharing_key, stor_id, xfer_flg, src_sys, cap_trans_id,workflow_type, "
	    + "created_by, created_on) values (:dataSharingKey, :storeId, :transferFlag, :sourceSystem, :capTransId,:workFlowType, "
	    + ":createdBy, sysdate)";

    private static final String sqlDeleteDeviceEntitlement = "DELETE FROM bst_ise_sch01.ret_exch_devc_entitl re "
	    + "where re.dta_sharing_key = :existingDataSharingKey";//and created_on = SYSDATE";

    @Override
    public RetExchDevcEntitlsModel getDeviceEntitlement(String handsetId, String phoneNumber)
	    throws DataAccessException, RuntimeException {
	List<RetExchDevcEntitlsModel> retExchDevcEntitlsList = null;
	retExchDevcEntitlsList = getJdbcTemplate().query(sqlgetDeviceDetails,
		new Object[] {(handsetId == null?"":handsetId), (phoneNumber == null?"":phoneNumber) },
		new RetExchDevcEntitlsRowMapper());
	if (retExchDevcEntitlsList == null || retExchDevcEntitlsList.size() == 0)
	    return null;
	return retExchDevcEntitlsList.get(0);
    }

    @Override
    public String createDeviceEntitlement(RetExchDevcEntitlsModel newRetExchDevcEntitlsModel,
	    DataTransferSummary dataTransferSummary, String existingDataSharingKey) {

	this.deleteDeviceEntitlement(existingDataSharingKey);

	Map devcEntitlsParams = new HashMap();

	//1. Update data_xfer table, get inserted key
	Map<String, Object> dataXferParams = new HashMap<String, Object>();
	dataXferParams.put("dataSharingKey", UUID.randomUUID().toString());
	dataXferParams.put("storeId", dataTransferSummary.getStoreId());
	dataXferParams.put("transferFlag", dataTransferSummary.isTransferFlag());
	dataXferParams.put("sourceSystem", dataTransferSummary.getSrcSys());
	dataXferParams.put("capTransId", dataTransferSummary.getCapTransId());
	dataXferParams.put("createdBy", dataTransferSummary.getCreatedBy());
	dataXferParams.put("workFlowType", "RET_EXCH");
	getNamedJdbcTemplate().update((sqlInsertDataXferSummary), dataXferParams);

	//2. use that key to insert record into RET_EXCH_DEVC_ENTITL. 
	devcEntitlsParams.put("data_sharing_key", dataXferParams.get("dataSharingKey"));
	devcEntitlsParams.put("handset_id", newRetExchDevcEntitlsModel.getHandsetId());
	devcEntitlsParams.put("ph_nbr", newRetExchDevcEntitlsModel.getPhoneNumber());
	devcEntitlsParams.put("trans_key", newRetExchDevcEntitlsModel.getTransaction_key());
	devcEntitlsParams.put("covg_beni", newRetExchDevcEntitlsModel.getCoverageBenefit());
	devcEntitlsParams.put("covg_beni_prmpt_cde", newRetExchDevcEntitlsModel.getCoverageBenefitPromptCode());
	devcEntitlsParams.put("covg_vhcl", newRetExchDevcEntitlsModel.getCoverageVehicle());
	devcEntitlsParams.put("type", newRetExchDevcEntitlsModel.getType());
	devcEntitlsParams.put("protection_plan_id", newRetExchDevcEntitlsModel.getProtectionPlanId());
	devcEntitlsParams.put("dfec_stat", newRetExchDevcEntitlsModel.isDefectiveStatus());
	devcEntitlsParams.put("created_by", newRetExchDevcEntitlsModel.getCreated_by());
	devcEntitlsParams.put("created_on", new Date());
	devcEntitlsParams.put("amended_by", null);
	devcEntitlsParams.put("amended_on", null);

	getNamedJdbcTemplate().update(sqlInsertDeviceEntitlement, devcEntitlsParams);

	logger.info("devcEntitlsParams inserted in db" + devcEntitlsParams);
	return (String) dataXferParams.get("dataSharingKey");

    }

    private class RetExchDevcEntitlsRowMapper implements RowMapper<RetExchDevcEntitlsModel> {

	@Override
	public RetExchDevcEntitlsModel mapRow(ResultSet rs, int rowNum) throws SQLException {
	    RetExchDevcEntitlsModel retExchDevcEntitlsModel = new RetExchDevcEntitlsModel();
	    retExchDevcEntitlsModel.setDataSharingKey(rs.getString("dta_sharing_key"));
	    retExchDevcEntitlsModel.setHandsetId(rs.getString("handset_id"));
	    retExchDevcEntitlsModel.setPhoneNumber(rs.getString("ph_nbr"));
	    retExchDevcEntitlsModel.setTransaction_key(rs.getString("trans_key"));
	    retExchDevcEntitlsModel.setCoverageBenefit(rs.getString("covg_beni"));
	    retExchDevcEntitlsModel.setCoverageBenefitPromptCode(rs.getString("covg_beni_prmpt_cde"));
	    retExchDevcEntitlsModel.setCoverageVehicle(rs.getString("covg_vhcl"));
	    retExchDevcEntitlsModel.setType(rs.getString("type"));
	    retExchDevcEntitlsModel.setProtectionPlanId(rs.getString("protection_plan_id"));
	    retExchDevcEntitlsModel.setDefectiveStatus(rs.getInt("dfec_stat") == 1?true:false);
	    retExchDevcEntitlsModel.setCreated_on(rs.getDate("created_on"));
	    retExchDevcEntitlsModel.setCreated_by(rs.getString("created_by"));
	    return retExchDevcEntitlsModel;
	}
    }

    @Override
    public void deleteDeviceEntitlement(String existingDataSharingKey) {

	Map<String, Object> deleteParamMap = new HashMap<String, Object>();
	deleteParamMap.put("existingDataSharingKey", existingDataSharingKey);

	getNamedJdbcTemplate().update(sqlDeleteDeviceEntitlement, deleteParamMap);

	logger.info("record deleted with dataSharingKey = " + existingDataSharingKey);

    }

}
