/**
 * 
 */
package com.bestbuy.bbym.ise.drp.dao;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * @author a194869
 *
 */
public class TradeInDaoImplTest extends BaseDaoTest {

    private TradeInDaoImpl tradeInDao = new TradeInDaoImpl();

    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
	tradeInDao.setUseNextSequenceOnCreate(true);
	tradeInDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	tradeInDao.setJdbcTemplate(new JdbcTemplate(db));
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetTradeInCarrierRecord() throws DataAccessException {
    }

}
