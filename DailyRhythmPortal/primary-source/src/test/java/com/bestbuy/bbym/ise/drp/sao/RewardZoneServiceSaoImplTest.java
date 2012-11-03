package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientrwzmember.InquireRZMemberV1;
import com.bestbuy.bbym.ise.iseclientrwzmember.RZMemberInquiryRequestType;
import com.bestbuy.bbym.ise.iseclientrwzmember.RZMemberInquiryResponseType;
import com.bestbuy.bbym.ise.iseclientrwzmember.Security;

/**
 * JUnit test for {@link RewardZoneServiceSaoImpl}.
 */
public class RewardZoneServiceSaoImplTest extends BaseSaoTest {

    private RewardZoneServiceSaoImpl rewardZoneServiceSaoImpl;
    private InquireRZMemberV1 mockInquireRZMemberV1;

    @Override
    public void setUp() {
	super.setUp();

	mockInquireRZMemberV1 = EasyMock.createMock(InquireRZMemberV1.class);
	rewardZoneServiceSaoImpl = new RewardZoneServiceSaoImpl() {

	    @Override
	    protected InquireRZMemberV1 getRewardZoneMemberLookupService() throws ServiceException {
		return mockInquireRZMemberV1;
	    }
	};

	expectIbhPropertiesCalls(true);

	rewardZoneServiceSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH?
    }

    /**
     * Test
     * {@link RewardZoneServiceSaoImpl#getRewardZoneMemberNumber(String, String, String, String)}
     * .
     */
    @Ignore
    @Test
    public void testGetRewardZoneMemberNumber() throws Exception {

	// Prepare to record request sent in service call
	Capture<RZMemberInquiryRequestType> captureInquiryRequest = new Capture<RZMemberInquiryRequestType>();
	Capture<Security> captureSecurity = new Capture<Security>();

	// Load a canned service response
	RZMemberInquiryResponseType paramResponseType = loadResponse("RZ_RZMemberInquiryResponse.xml",
		RZMemberInquiryResponseType.class);

	EasyMock.expect(mockInquireRZMemberV1.inquireMember(capture(captureInquiryRequest), capture(captureSecurity)))
		.andReturn(paramResponseType);
	EasyMock.replay(mockInquireRZMemberV1);

	final String customerFirstName = "someCustomerFirstName";
	final String customerLastName = "someCustomerLastName";
	final String phoneNumber = "somePhoneNumber";
	final String email = "someEmail";

	String rewardZoneNumber = rewardZoneServiceSaoImpl.getRewardZoneMemberNumber(customerFirstName,
		customerLastName, phoneNumber, email);

	// Check that correct values were sent in service call
	assertEquals("Incorrect customerFirstName passed in service call", customerFirstName, captureInquiryRequest
		.getValue().getCustomerFirstName());
	assertEquals("Incorrect customerLastName passed in service call", customerLastName, captureInquiryRequest
		.getValue().getCustomerLastName());
	assertEquals("Incorrect phoneNumber passed in service call", phoneNumber, captureInquiryRequest.getValue()
		.getPhone());
	assertEquals("Incorrect email passed in service call", email, captureInquiryRequest.getValue().getEmail());
	assertFalse("No security token passed in service call", captureSecurity.getValue().getAny().isEmpty());

	// Check that correct values were returned from SAO
	assertNotNull("No rewardZoneNumber returned from service call", rewardZoneNumber);
	assertEquals("Incorrect rewardZoneNumber returned from service call", "", rewardZoneNumber);
    }
}
