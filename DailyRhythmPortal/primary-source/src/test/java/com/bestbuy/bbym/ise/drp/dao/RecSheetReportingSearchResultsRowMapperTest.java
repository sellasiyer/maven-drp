package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;

/**
 * JUnit test for {@link RecSheetReportingSearchResultsRowMapper}.
 * 
 * @author Udaya Duvvuri
 */
public class RecSheetReportingSearchResultsRowMapperTest {

    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
	resultSet = EasyMock.createMock(ResultSet.class);
    }

    @Test
    public void testRecSheetReportingSearchResults() throws Exception {

	RecSheetReportingSearchResultsRowMapper resultsRowMapper = new RecSheetReportingSearchResultsRowMapper();

	EasyMock.expect(resultSet.next()).andReturn(true);
	EasyMock.expect(resultSet.getString("created_by")).andReturn("A123");
	EasyMock.expect(resultSet.getString("emp_crt_frst_nm")).andReturn("Udaya");
	EasyMock.expect(resultSet.getString("emp_crt_last_nm")).andReturn("Duvvuri");

	EasyMock.expect(resultSet.next()).andReturn(false);

	EasyMock.replay(resultSet);

	RecSheetReportingSearchResults results = resultsRowMapper.mapRow(resultSet, 0);

	assertEquals(results.getFirstName(), "Udaya");
	assertEquals(results.getLastName(), "Duvvuri");

    }
}
