package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.sql.ResultSet;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.RecSheetCountByDay;

/**
 * JUnit test for {@link RecommendationsByDayMapper}.
 * 
 * @author Udaya Duvvuri
 */
public class RecommendationsByDayMapperTest {

    private ResultSet resultSet;

    @Before
    public void setUp() throws Exception {
	resultSet = EasyMock.createMock(ResultSet.class);
    }

    @Test
    public void testRecSheetCountByDay() throws Exception {

	RecommendationsByDayMapper recommendationsByDayMapper = new RecommendationsByDayMapper();

	EasyMock.expect(resultSet.next()).andReturn(true);
	EasyMock.expect(resultSet.getString("emp_frst_name")).andReturn("Udaya");
	EasyMock.expect(resultSet.getString("emp_last_name")).andReturn("Duvvuri");
	EasyMock.expect(resultSet.getString("changeDate")).andReturn("02-02-2012");
	EasyMock.expect(resultSet.getString("store_id")).andReturn("1234");
	EasyMock.expect(resultSet.getLong("recommendation_id")).andReturn(12L);
	EasyMock.expect(resultSet.getString("rec_sht_cnt")).andReturn("25");
	EasyMock.expect(resultSet.getString("changedBy")).andReturn("011");

	EasyMock.expect(resultSet.next()).andReturn(false);

	EasyMock.replay(resultSet);

	RecSheetCountByDay rec = recommendationsByDayMapper.mapRow(resultSet, 0);

	assertEquals(rec.getFirstName(), "Udaya");
	assertEquals(rec.getLastName(), "Duvvuri");
	assertEquals(rec.getChangeDate(), "02-02-2012");
	assertEquals(rec.getStoreId(), "1234");
	assertEquals(rec.getCountByDay(), "25");
	assertEquals(rec.getRecommendationId(), 12L);

    }
}
