package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.drp.domain.EntitlementRulesModel;

public class EntitlementAndActionRowMapper implements RowMapper<EntitlementRulesModel> {

    @Override
    public EntitlementRulesModel mapRow(ResultSet rs, int rowNum) throws SQLException {

	EntitlementRulesModel entitlementRulesModel = new EntitlementRulesModel();
	entitlementRulesModel.setCoverageBenefit((rs.getString("covg_beni")));
	entitlementRulesModel.setCoverageVehicle(rs.getString("covg_vhcl"));
	entitlementRulesModel.setDisplayText(rs.getString("displ_txt"));
	entitlementRulesModel.setEntitlementDetails(rs.getString("entitl_dtl"));
	entitlementRulesModel.setActions(rs.getString("actns"));
	entitlementRulesModel.setCreated_by((rs.getString("created_by")));
	entitlementRulesModel.setCreated_on(rs.getTimestamp("created_on"));
	entitlementRulesModel.setAmended_by((rs.getString("amended_by")));
	entitlementRulesModel.setAmended_on(rs.getTimestamp("amended_on"));
	entitlementRulesModel.setDisplayFlag(rs.getInt("displ_flg") == 1);

	return entitlementRulesModel;
    };
}
