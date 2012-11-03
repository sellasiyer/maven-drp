package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDay;

class RecommendationsByDayMapper implements RowMapper<RecSheetCountByDay> {

    @Override
    public RecSheetCountByDay mapRow(ResultSet rs, int rowNum) throws SQLException {

	RecSheetCountByDay r = new RecSheetCountByDay();
	r.setFirstName(rs.getString("emp_frst_name"));
	r.setLastName(rs.getString("emp_last_name"));
	r.setChangeDate(rs.getString("changeDate"));
	r.setStoreId(rs.getString("store_id"));
	r.setRecommendationId(rs.getLong("recommendation_id"));
	r.setCountByDay(rs.getString("rec_sht_cnt"));
	r.setAid(rs.getString("changedBy"));

	return r;
    }

}
