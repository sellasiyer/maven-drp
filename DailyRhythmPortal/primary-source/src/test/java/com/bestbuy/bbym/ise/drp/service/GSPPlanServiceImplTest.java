package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.bestbuy.bbym.ise.drp.dao.GSPPlanDao;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.domain.GSPPlanFactory;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * JUnit test for {@link GSPPlanServiceImpl}.
 * 
 * @author a911002
 */
public class GSPPlanServiceImplTest {

    private GSPPlanServiceImpl service;
    private GSPPlanDao mockDao;

    @Before
    public void setUp() {
	service = new GSPPlanServiceImpl();
	mockDao = createStrictMock(GSPPlanDao.class);
	ReflectionTestUtils.setField(service, "gspPlanDao", mockDao);
    }

    /**
     * Testing the happy path for the method cancelGSPPlans
     */
    @Test
    public void testCancelGSPPlans() throws Exception {
	GSPPlan mockPlan = GSPPlanFactory.getGSPPlan();
	List<GSPPlan> mockResults = new ArrayList<GSPPlan>();
	mockResults.add(0, mockPlan);

	// Testing the Happy Path
	expect(mockDao.getGSPPlansMarkedForCancel("id")).andReturn(mockResults);

	// Unexpected method calls after this point with throw exceptions
	EasyMock.replay(mockDao);

	List<GSPPlan> expectedResults = service.cancelGSPPlans("id");
	assertFalse(expectedResults.get(0).isCancel());
    }

    /**
     * Test for the null path with cancelGSPPlans
     */
    @Test(expected = ServiceException.class)
    public void testCancelGSPPlansNullPath() throws ServiceException {
	// Check for the null path
	service.cancelGSPPlans(null);
    }

    /**
     * Testing the happy path for the method getGSPPlansMarkedForCancel
     */
    @Test
    public void testGetGSPPlansMarkedForCancel() throws Exception {
	GSPPlan mockPlan = GSPPlanFactory.getGSPPlan();
	List<GSPPlan> mockResults = new ArrayList<GSPPlan>();
	mockResults.add(0, mockPlan);

	expect(mockDao.getGSPPlansMarkedForCancel("id")).andReturn(mockResults);

	// Unexpected method calls after this point with throw exceptions
	EasyMock.replay(mockDao);

	// Testing the Happy Path
	List<GSPPlan> expectedResults = service.getGSPPlansMarkedForCancel("id");
	assertEquals(mockResults, expectedResults);
    }

    /**
     * Test for the null path with getGSPPlanMarkedForCancel
     */
    @Test
    public void testGetGSPPlansMarkedForCancelNullPath() throws Exception {
	// Testing the null path
	List<GSPPlan> plans = service.getGSPPlansMarkedForCancel(null);
	assertNull(plans);
    }

    /**
     * Testing the happy path for the method addGSPPlan
     */
    @Test
    public void testAddGSPPlan() throws Exception {
	GSPPlan mockPlan = GSPPlanFactory.getGSPPlan();

	mockDao.persistGSPPlan(mockPlan);
	EasyMock.expectLastCall();

	EasyMock.replay(mockDao);

	// Testing the Happy Path
	service.addGSPPlan(mockPlan);
	EasyMock.verify(mockDao);
    }

    /**
     * Testing for the null path with addGSPPlan
     */
    @Test(expected = NullPointerException.class)
    public void testAddGSPPlanNullPath() throws Exception {
	// Testing the null path
	service.addGSPPlan(null);
    }

}
