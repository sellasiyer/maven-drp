package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanDetailRequestType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanDetailResponseType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanSOAP;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanSearchRequestType;
import com.bestbuy.bbym.ise.iseclienthub.ProtectionPlanSearchResponseType;

/**
 * JUnit test for {@link HubProtectionPlanSaoImpl}.
 * 
 * @author a904002
 */
public class HubProtectionPlanSaoImplTest extends BaseSaoTest {

    private HubProtectionPlanSaoImpl hubProtectionPlanSaoImpl;
    private ProtectionPlanSOAP mockProtectionPlanSOAP;

    @Override
    public void setUp() {
	super.setUp();

	mockProtectionPlanSOAP = EasyMock.createMock(ProtectionPlanSOAP.class);
	hubProtectionPlanSaoImpl = new HubProtectionPlanSaoImpl() {

	    @Override
	    protected ProtectionPlanSOAP getHubService() throws ServiceException {
		return mockProtectionPlanSOAP;
	    }
	};

	expectIbhPropertiesCalls(true);

	hubProtectionPlanSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH for Hub?
    }

    /**
     * Test of searchProtectionPlan method, of class HubProtectionPlanSaoImpl.
     */
    @Test
    public void testSearchProtectionPlan() throws Exception {

	// Prepare to record request sent in service call
	Capture<ProtectionPlanSearchRequestType> capture = new Capture<ProtectionPlanSearchRequestType>();

	// Load a canned service response
	ProtectionPlanSearchResponseType protectionPlanSearchResponseType = loadResponse(
		"HUB_ProtectionPlanSearchResponse.xml", ProtectionPlanSearchResponseType.class);

	EasyMock.expect(mockProtectionPlanSOAP.searchProtectionPlan(capture(capture))).andReturn(
		protectionPlanSearchResponseType);
	EasyMock.replay(mockProtectionPlanSOAP);

	final Customer bbyCustomer = new Customer();
	bbyCustomer.setBbyCustomerId("1124341418");

	List<ServicePlan> servicePlans = hubProtectionPlanSaoImpl.searchServicePlan(bbyCustomer);

	// Check that correct values were sent in service call
	assertEquals("Incorrect customerId passed in service call", bbyCustomer.getBbyCustomerId(), capture.getValue()
		.getCustomerID().getValue());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No service plans returned from service call", servicePlans);
	// TODO Add more assertions
    }

    /**
     * Test of getProtectionPlanDetails method, of class
     * HubProtectionPlanSaoImpl.
     */
    @Ignore
    @Test
    public void testGetServicePlanDetails() throws Exception {

	// Prepare to record request sent in service call
	Capture<ProtectionPlanDetailRequestType> capture = new Capture<ProtectionPlanDetailRequestType>();

	// Load a canned service response
	ProtectionPlanDetailResponseType protectionPlanSearchResponseType = loadResponse(
		"HUB_ProtectionPlanDetailResponse.xml", ProtectionPlanDetailResponseType.class);

	EasyMock.expect(mockProtectionPlanSOAP.getProtectionPlanDetail(capture(capture))).andReturn(
		protectionPlanSearchResponseType);
	EasyMock.replay(mockProtectionPlanSOAP);

	final ServicePlan servicePlanInfo = new ProtectionPlan();
	servicePlanInfo.setPlanNumber("3926102759");

	ServicePlan servicePlan = hubProtectionPlanSaoImpl.getServicePlanDetails(servicePlanInfo);

	// Check that correct values were sent in service call
	assertEquals("Incorrect planId passed in service call", servicePlanInfo.getPlanNumber(), capture.getValue()
		.getProtectionPlanID().getValue());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No service plan returned from service call", servicePlan);
	// TODO Add more assertions
    }

    /**
     * Test of searchProtectionPlanByIMEI method, of class
     * HubProtectionPlanSaoImpl.
     */
    @Test
    public void testSearchProtectionPlanByIMEI() throws Exception {

	// Prepare to record request sent in service call
	Capture<ProtectionPlanSearchRequestType> capture = new Capture<ProtectionPlanSearchRequestType>();

	// Load a canned service response
	ProtectionPlanSearchResponseType protectionPlanSearchResponseType = loadResponse(
		"HUB_ProtectionPlanSearchResponse.xml", ProtectionPlanSearchResponseType.class);

	EasyMock.expect(mockProtectionPlanSOAP.searchProtectionPlan(capture(capture))).andReturn(
		protectionPlanSearchResponseType);
	EasyMock.replay(mockProtectionPlanSOAP);

	final String deviceSerialNum = "013059006032364";

	List<ServicePlan> servicePlans = hubProtectionPlanSaoImpl.searchServicePlanByIMEI(deviceSerialNum);

	// Check that correct values were sent in service call
	assertEquals("Incorrect coveredSerialNumber passed in service call", deviceSerialNum, capture.getValue()
		.getCoveredSerialNumber());

	// Check that correct values were returned from SAO
	assertNotNull("No service plans returned from service call", servicePlans);
	// TODO Add more assertions
    }

    /**
     * Test of searchProtectionPlanByPlanId method, of class
     * HubProtectionPlanSaoImpl.
     */
    @Test
    public void testSearchProtectionPlanByPlanId() throws Exception {

	// Prepare to record request sent in service call
	Capture<ProtectionPlanSearchRequestType> capture = new Capture<ProtectionPlanSearchRequestType>();

	// Load a canned service response
	ProtectionPlanSearchResponseType protectionPlanSearchResponseType = loadResponse(
		"HUB_ProtectionPlanSearchResponse.xml", ProtectionPlanSearchResponseType.class);

	EasyMock.expect(mockProtectionPlanSOAP.searchProtectionPlan(capture(capture))).andReturn(
		protectionPlanSearchResponseType);
	EasyMock.replay(mockProtectionPlanSOAP);

	final String protectionPlanId = "3063895738";

	List<ServicePlan> servicePlans = hubProtectionPlanSaoImpl.searchServicePlanByPlanId(protectionPlanId);

	// Check that correct values were sent in service call
	assertEquals("Incorrect protectionPlanId passed in service call", protectionPlanId, capture.getValue()
		.getProtectionPlanID().getValue());

	// Check that correct values were returned from SAO
	assertNotNull("No service plans returned from service call", servicePlans);
	// TODO Add more assertions
    }

}
