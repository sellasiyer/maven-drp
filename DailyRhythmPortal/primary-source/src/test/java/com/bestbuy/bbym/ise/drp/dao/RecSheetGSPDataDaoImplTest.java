package com.bestbuy.bbym.ise.drp.dao;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;
import static org.junit.Assert.assertEquals;

/**
 * JUnit test for {@link RecSheetGSPDataDaoImpl}.
 * 
 * @author a175157
 */
public class RecSheetGSPDataDaoImplTest extends BaseDaoTest {

    private RecSheetGSPDataDaoImpl recSheetGSPDataDaoImpl = new RecSheetGSPDataDaoImpl();

    @Before
    public void setUp() {
	recSheetGSPDataDaoImpl.setUseNextSequenceOnCreate(true);
	recSheetGSPDataDaoImpl.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	recSheetGSPDataDaoImpl.setJdbcTemplate(new JdbcTemplate(db));
    }

    /**
     *  Test for (@link RecSheetGSPDataDaoImpl#getGSPData(String, int, float)
     */
    @Test
    public void testGetGSPData() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_recsheet_gsp_data.sql", db);
	Map<String, String> result = recSheetGSPDataDaoImpl.getGSPData("LAPTOP", 1, 100.00F);
	Map.Entry<String, String> record = result.entrySet().iterator().next();
	assertEquals("50", record.getKey());
	assertEquals("123456789", record.getValue());
    }

    /**
     *  Test for (@link RecSheetGSPDataDaoImpl#getGSPPrice(String, int, float)
     */
    @Test
    public void testGetGSPPrice() throws DataAccessException {
	//DatabaseScript.execute("ise_db_scripts/add_recsheet_gsp_data.sql", db);
	Map<String, String> result = recSheetGSPDataDaoImpl.getGSPPrice(100.00F);
	Map.Entry<String, String> record = result.entrySet().iterator().next();
	assertEquals("LAPTOP1", record.getKey());
	assertEquals("50", record.getValue());
    }

    /**
     *  Test for (@link RecSheetGSPDataDaoImpl#getGSPSKU(String, int, float)
     */
    @Test
    public void testGetGSPSKU() throws DataAccessException {
	//DatabaseScript.execute("ise_db_scripts/add_recsheet_gsp_data.sql", db);
	String sku = recSheetGSPDataDaoImpl.getGSPSKU("LAPTOP", 1, 100.00F);
	assertEquals("123456789", sku);
    }
}
