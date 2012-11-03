package com.bestbuy.bbym.ise.drp.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.UUID;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.domain.GSPPlanFactory;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * JUnit test for {@link GSPPlanDaoImpl}.
 * 
 * @author Sridhar Savaram
 */
public class GSPPlanDaoImplTest extends BaseDaoTest {

    private GSPPlanDaoImpl gspPlanDao = new GSPPlanDaoImpl();

    @Before
    public void setUp() {
	gspPlanDao.setUseNextSequenceOnCreate(true);
	gspPlanDao.setNamedJdbcTemplate(new NamedParameterJdbcTemplate(db));
	gspPlanDao.setJdbcTemplate(new JdbcTemplate(db));
    }

    @Test
    public void testPersistGSPPlanDao() throws DataAccessException {
	String id = UUID.randomUUID().toString();
	GSPPlan gspPlan1 = GSPPlanFactory.getGspPlan(id, "36d70c7d-fc98-4c97-af32-314370eeafff", "Monthly", new Date(),
		true, "101010", "Service Plan Desc", new Date(), "100.00", "GSP", "101010", "3807", "100", "100",
		"a175157", "a175157");
	gspPlanDao.persistGSPPlan(gspPlan1);
	GSPPlan gspPlan2 = gspPlanDao.getGSPPlansMarkedForCancel("36d70c7d-fc98-4c97-af32-314370eeafff").get(0);
	assertEquals(gspPlan1.getDataSharingKey(), gspPlan2.getDataSharingKey());
	assertEquals(gspPlan1.getBillingType(), gspPlan2.getBillingType());
	// assertEquals(gspPlan1.getBusinessDate(), gspPlan2.getBusinessDate());
	assertEquals(gspPlan1.isCancel(), gspPlan2.isCancel());
	assertEquals(gspPlan1.getProtectionPlanId(), gspPlan2.getProtectionPlanId());
	assertEquals(gspPlan1.getContractSKU(), gspPlan2.getContractSKU());
	assertEquals(gspPlan1.getContractSKUDescription(), gspPlan2.getContractSKUDescription());
	// assertEquals(gspPlan1.getExpirationDate(),
	// gspPlan2.getExpirationDate());
	assertEquals(gspPlan1.getStoreId(), gspPlan2.getStoreId());
	assertEquals(gspPlan1.getWorkstationId(), gspPlan2.getWorkstationId());
	assertEquals(gspPlan1.getRegisterTransactionNumber(), gspPlan2.getRegisterTransactionNumber());
    }

    @Test
    @Ignore
    public void testAddGSPPlan() throws DataAccessException {
	String id = UUID.randomUUID().toString();
	GSPPlan gspPlan1 = GSPPlanFactory.getGspPlan(id, "a5f99d1f-a016-4c0f-a217-dd8d84afc242", "On-Time", new Date(),
		true, "11111", "Service Plan Desc", new Date(), "", "GSP", "101010", "3907", "100", "100", "a175157",
		"a175157");
	gspPlanDao.persistGSPPlan(gspPlan1);
	GSPPlan gspPlan2 = gspPlanDao.getGSPPlan(id);
	assertEquals(gspPlan1.getDataSharingKey(), gspPlan2.getDataSharingKey());
	assertEquals(gspPlan1.getBillingType(), gspPlan2.getBillingType());
	// assertEquals(gspPlan1.getBusinessDate(), gspPlan2.getBusinessDate());
	assertEquals(gspPlan1.isCancel(), gspPlan2.isCancel());
	assertEquals(gspPlan1.getProtectionPlanId(), gspPlan2.getProtectionPlanId());
	assertEquals(gspPlan1.getContractSKU(), gspPlan2.getContractSKU());
	assertEquals(gspPlan1.getContractSKUDescription(), gspPlan2.getContractSKUDescription());
	assertEquals(gspPlan1.getStoreId(), gspPlan2.getStoreId());
	assertEquals(gspPlan1.getWorkstationId(), gspPlan2.getWorkstationId());
	assertEquals(gspPlan1.getRegisterTransactionNumber(), gspPlan2.getRegisterTransactionNumber());
    }
}
