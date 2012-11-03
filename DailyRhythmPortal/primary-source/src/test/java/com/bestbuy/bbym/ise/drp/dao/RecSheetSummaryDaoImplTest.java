package com.bestbuy.bbym.ise.drp.dao;

import java.util.UUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;

/**
 * JUnit test for {@link RecSheetSummaryDaoImpl}.
 * 
 * @author Sridhar Savaram
 */
public class RecSheetSummaryDaoImplTest extends BaseDaoTest {

    private RecSheetSummaryDaoImpl recSheetSummaryDao = new RecSheetSummaryDaoImpl();

    @Before
    public void setUp() {
	recSheetSummaryDao.setUseNextSequenceOnCreate(true);
	recSheetSummaryDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	recSheetSummaryDao.setJdbcTemplate(new JdbcTemplate(db));
    }

    @After
    public void tearDown() throws Exception {
	DatabaseScript.execute("ise_db_scripts/clean_gsp_cancel.sql", db);
    }

    /**
     * Test
     * {@link RecSheetSummaryDaoImpl#persistRecSheetSummary(RecSheetSummary)}.
     */
    @Test
    public void testPersistRecSheetSummaryDao() throws Exception {
	String dataSharingKey = UUID.randomUUID().toString();
	RecSheetSummary recSheetSummary1 = new RecSheetSummary("Plan Info", "Device Info", "GSP Plan Info",
		"BuyBack Info", dataSharingKey, "a175157", "a175157");
	recSheetSummaryDao.persistRecSheetSummary(recSheetSummary1);
	RecSheetSummary recSheetSummary2 = recSheetSummaryDao.getRecSheetSummary(dataSharingKey);
	TestUtil.assertBeanEqual(recSheetSummary1, recSheetSummary2);
    }

}
