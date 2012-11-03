package com.bestbuy.bbym.ise.drp.rest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.beast.portal.cancelgsp.response.GspPlanCancelResponse;
import com.bestbuy.bbym.beast.portal.gspcancel.write.request.CustomerRequest;
import com.bestbuy.bbym.beast.portal.gspcancel.write.response.DskResponse;

/**
 * JUnit test for {@link GspRestServiceImpl}.
 * 
 * @author a904002
 */
@Ignore
public class GspRestServiceImplTest {

    /**
     * Test of addCustomer method, of class GspRestServiceImpl.
     */
    @Test
    public void testAddCustomer() throws Exception {
	System.out.println("addCustomer");
	CustomerRequest account = null;
	String storeId = "";
	String userId = "";
	GspRestServiceImpl instance = new GspRestServiceImpl();
	DskResponse expResult = null;
	DskResponse result = instance.addCustomer(account, storeId, userId);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }

    /**
     * Test of cancelGSPPlans method, of class GspRestServiceImpl.
     */
    @Test
    public void testCancelGSPPlans() throws Exception {
	System.out.println("cancelGSPPlans");
	String dataSharingKey = "";
	GspRestServiceImpl instance = new GspRestServiceImpl();
	GspPlanCancelResponse expResult = null;
	GspPlanCancelResponse result = instance.cancelGSPPlans(dataSharingKey);
	assertEquals(expResult, result);
	// TODO review the generated test code and remove the default call to fail.
	fail("The test case is a prototype.");
    }
}
