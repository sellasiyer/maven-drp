package com.bestbuy.bbym.ise.drp.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;

public class RecSheetReportingSearchResultsRowMapper implements RowMapper<RecSheetReportingSearchResults> {

    public RecSheetReportingSearchResultsRowMapper() {
	super();
    }

    @Override
    public RecSheetReportingSearchResults mapRow(ResultSet rs, int rowNum) throws SQLException {

	RecSheetReportingSearchResults r = new RecSheetReportingSearchResults();
	r.setAid(rs.getString("created_by"));
	r.setFirstName(rs.getString("emp_crt_frst_nm"));
	r.setLastName(rs.getString("emp_crt_last_nm"));

	return r;
    }

}
