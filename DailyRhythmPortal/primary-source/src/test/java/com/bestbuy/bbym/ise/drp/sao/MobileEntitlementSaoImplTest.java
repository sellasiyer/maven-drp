package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlementRequest;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckPortType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckRequestType;
import com.bestbuy.bbym.ise.iseclientmec.EntitlementCheckResponseType;

/**
 * JUnit test for {@link MobileEntitlementSaoImpl}.
 */
public class MobileEntitlementSaoImplTest extends BaseSaoTest {

    private MobileEntitlementSaoImpl mobileEntitlementSaoImpl;
    private EntitlementCheckPortType mockEntitlementCheckPortType;

    @Override
    public void setUp() {
	super.setUp();

	mockEntitlementCheckPortType = EasyMock.createMock(EntitlementCheckPortType.class);
	mobileEntitlementSaoImpl = new MobileEntitlementSaoImpl() {

	    @Override
	    protected EntitlementCheckPortType getMECService() throws ServiceException {
		return mockEntitlementCheckPortType;
	    }
	};

	expectIbhPropertiesCalls(false);

	mobileEntitlementSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientmec.InternationalBusinessHierarchyType.class);
    }

    /**
     * Test {@link MobileEntitlementSaoImpl#getMobileEntitlements(com.bestbuy.bbym.ise.drp.domain.MobileEntitlementRequest, com.bestbuy.bbym.ise.drp.domain.DrpUser, String)}
     */
    @Test
    public void testGetMobileEntitlements() throws Exception {

	// Prepare to record request sent in service call
	Capture<EntitlementCheckRequestType> capture = new Capture<EntitlementCheckRequestType>();

	// Load a canned service response
	EntitlementCheckResponseType entitlementCheckResponseType = loadResponse("MEC_EntitlementCheckResponse.xml",
		EntitlementCheckResponseType.class);

	EasyMock.expect(mockEntitlementCheckPortType.getEntitlementDetails(capture(capture))).andReturn(
		entitlementCheckResponseType);
	EasyMock.replay(mockEntitlementCheckPortType);

	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("SomeSourceSystem");
	EasyMock.replay(drpPropertyService);

	final MobileEntitlementRequest mobileEntitlementRequest = new MobileEntitlementRequest();
	final Carrier carrier = Carrier.VERIZON;
	mobileEntitlementRequest.setCarrier(carrier);
	mobileEntitlementRequest.setHandsetId("someHandsetId");
	final DrpUser drpUser = DrpUserFactory.getDrpUser();
	final String iseTransactionId = "someIseTransactionId";

	MobileEntitlement mobileEntitlement = mobileEntitlementSaoImpl.getMobileEntitlements(mobileEntitlementRequest,
		drpUser, iseTransactionId);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationalBusinessHierarchy());
	assertEquals("Incorrect deviceId passed in service call", mobileEntitlementRequest.getHandsetId(), capture
		.getValue().getDeviceID());
	assertEquals("Incorrect carrier passed in service call", carrier.getShortLabel(), capture.getValue()
		.getCarrier());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No mobile entitlement returned from service call", mobileEntitlement);
	// TODO Add more assertions

    }
}
