package com.bestbuy.bbym.ise.drp.dao;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.WorkFlowTypeEnum;

/**
 * JUnit test for {@link DataTransferDaoImpl}.
 * 
 * @author Sridhar Savaram
 */
public class DataSummaryDaoImplTest extends BaseDaoTest {

    private DataTransferDaoImpl dataTransferDao = new DataTransferDaoImpl();

    @Before
    public void setUp() {
	dataTransferDao.setUseNextSequenceOnCreate(true);
	dataTransferDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	dataTransferDao.setJdbcTemplate(new JdbcTemplate(db));
    }

    @Test
    public void testPersistDataTransferDao() throws DataAccessException {
	String dataSharingKey = UUID.randomUUID().toString();
	dataTransferDao.persistDataTransfer(dataSharingKey, "3807", "a175157", false, "BEAST", "390712340900111",
		WorkFlowTypeEnum.BEAST_TRANSFER.name());
    }

}
