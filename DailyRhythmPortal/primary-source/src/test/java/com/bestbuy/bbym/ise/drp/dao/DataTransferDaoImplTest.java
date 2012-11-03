package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.util.WorkFlowTypeEnum;

/**
 * JUnit test for {@link DataTransferDaoImpl}.
 * 
 * @author a915726
 */
@Ignore
public class DataTransferDaoImplTest extends BaseDaoTest {

    private DataTransferDaoImpl dataTransferDao = new DataTransferDaoImpl();

    @Before
    public void setUp() {
	dataTransferDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	dataTransferDao.setJdbcTemplate(new JdbcTemplate(db));
    }

    /**
     * Test for
     * {@link DataTransferDaoImpl#persistDataTransfer(String, String, String, boolean, String, String)}
     * .
     */
    @Test
    public void testPersistDataTransfer() throws Exception {
	DataTransferSummary newDataTransferSummary = null;
	try{
	    dataTransferDao.persistDataTransfer("55d70333-58e8-4159-8685-2a1519a5c300", "0699", "1136163272", true,
		    "BEAST-PORT", "ISE20120515153602916", WorkFlowTypeEnum.BEAST_TRANSFER.name());

	    newDataTransferSummary = dataTransferDao.getDataTransferSummary("55d70333-58e8-4159-8685-2a1519a5c300");
	}catch(IllegalArgumentException exc){
	    assertEquals("dataTransferSummary == null", exc.getMessage());
	}
	assertNotNull(newDataTransferSummary);
	assertEquals(true, newDataTransferSummary.isTransferFlag());
	assertEquals("55d70333-58e8-4159-8685-2a1519a5c300", newDataTransferSummary.getDataSharingKey());
    }

    /**
     * Test for {@link DataTransferDaoImpl#getDataTransferSummaryList(String)}.
     */
    @Test
    public void testGetDataTransferSummaryList() throws Exception, RuntimeException {
	List<DataTransferSummary> dataTransferSummaryList = null;
	try{
	    dataTransferSummaryList = dataTransferDao
		    .getDataTransferSummaryList("55d70333-58e8-4159-8685-2a1519a5c301");
	}catch(IllegalArgumentException exc){
	    assertEquals("dataTransferSummary == null", exc.getMessage());
	}
	assertNotNull(dataTransferSummaryList);
    }

    /**
     * Test for {@link DataTransferDaoImpl#getDataTransferSummary(String)}.
     */
    @Test
    public void testGetDataTransferSummary() throws Exception, RuntimeException {
	DataTransferSummary dataTransferSummary = null;
	try{
	    dataTransferSummary = dataTransferDao.getDataTransferSummary("55d70333-58e8-4159-8685-2a1519a5c301");
	}catch(IllegalArgumentException exc){
	    assertEquals("dataTransferSummary == null", exc.getMessage());
	}
	assertNotNull(dataTransferSummary);
	assertEquals("Tester", dataTransferSummary.getFirstName());
    }

    /**
     * Test for {@link DataTransferDaoImpl#updateTransferFlag(String)}.
     */
    @Test
    public void testUpdateTransferFlag() throws Exception {
	dataTransferDao.updateTransferFlag("55d70333-58e8-4159-8685-2a1519a5c301");

	DataTransferSummary dataTransferSummary = dataTransferDao
		.getDataTransferSummary("55d70333-58e8-4159-8685-2a1519a5c301");

	assertNotNull(dataTransferSummary);
	assertEquals(true, dataTransferSummary.isTransferFlag());
    }

    /**
     * Test for {@link DataTransferDaoImpl#updateAmmendedOn(String)}.
     */
    @Test
    public void testUpdateAmmendedOn() throws Exception {
	dataTransferDao.updateAmmendedOn("55d70333-58e8-4159-8685-2a1519a5c301");

	DataTransferSummary dataTransferSummary = dataTransferDao
		.getDataTransferSummary("55d70333-58e8-4159-8685-2a1519a5c301");

	SimpleDateFormat dateformatter = new SimpleDateFormat("yyyy-MM-dd");
	assertNotNull(dataTransferSummary);
	assertEquals(dateformatter.format(new GregorianCalendar().getTime()), dateformatter.format(dataTransferSummary
		.getModifiedOn()));

    }

    /**
     * Test for {@link DataTransferDaoImpl#update(String, String, String)}.
     */
    @Test
    public void testUpdate() throws Exception {
	dataTransferDao.update("55d70333-58e8-4159-8685-2a1519a5c301", "ISE2012", "Tester");

	DataTransferSummary dataTransferSummary = dataTransferDao
		.getDataTransferSummary("55d70333-58e8-4159-8685-2a1519a5c301");

	assertNotNull(dataTransferSummary);
	assertEquals("Tester", dataTransferSummary.getModifiedBy());
    }
}
