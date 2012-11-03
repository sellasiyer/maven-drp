package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;

public class DrpPropertyRowMapper implements RowMapper<DrpProperty> {

    @Override
    public DrpProperty mapRow(ResultSet rs, int rowNum) throws SQLException {

	DrpProperty drpProperty = new DrpProperty();
	drpProperty.setName(rs.getString("prop_name"));
	drpProperty.setValue(rs.getString("prop_value"));
	drpProperty.setDescription(rs.getString("prop_desc"));
	drpProperty.setModifiedBy(rs.getString("modified_by"));
	drpProperty.setModifiedDate(rs.getTimestamp("modified_date"));
	drpProperty.setCreatedBy(rs.getString("created_by"));
	drpProperty.setCreatedDate(rs.getTimestamp("created_date"));
	drpProperty.setId(rs.getLong("prop_id"));
	drpProperty.setWicketProperty(rs.getInt("is_drp_prop_flg") == 1);

	return drpProperty;
    };
}
