package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerFactory;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.helpers.UpgCheckerResponseData;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientucs.CachedUpgradeEligibilityRequest;
import com.bestbuy.bbym.ise.iseclientucs.GetCachedUpgradeEligibilityResponse;
import com.bestbuy.bbym.ise.iseclientucs.GetNotificationStatusPlusResponse;
import com.bestbuy.bbym.ise.iseclientucs.NotificationStatusGetRequest;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeChecker;

/**
 * JUnit test for {@link UpgradeCheckerSaoImpl}.
 */
public class UpgradeCheckerSaoImplTest extends BaseSaoTest {

    private UpgradeCheckerSaoImpl upgradeCheckerSaoImpl;
    private UpgradeChecker mockUpgradeChecker;

    @Override
    public void setUp() {
	super.setUp();

	mockUpgradeChecker = EasyMock.createMock(UpgradeChecker.class);
	upgradeCheckerSaoImpl = new UpgradeCheckerSaoImpl() {

	    @Override
	    protected UpgradeChecker getUpgradeCheckerService() throws ServiceException {
		return mockUpgradeChecker;
	    }
	};

	expectIbhPropertiesCalls(false);

	upgradeCheckerSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientucs.InternationalBusinessHierarchy.class);
    }

    /**
     * Test for {@link UpgradeCheckerSaoImpl#getCachedSubcribersUpgradeInfo(com.bestbuy.bbym.ise.domain.Customer, String, com.bestbuy.bbym.ise.drp.domain.DrpUser)}.
     */
    @Test
    public void testGetCachedSubcribersUpgradeInfo() throws Exception {

	// Prepare to record request sent in service call
	Capture<CachedUpgradeEligibilityRequest> capture = new Capture<CachedUpgradeEligibilityRequest>();

	// Load a canned service response
	GetCachedUpgradeEligibilityResponse getCachedUpgradeEligibilityResponse = loadResponse(
		"UCS_getCachedUpgradeEligibilityResponse.xml", GetCachedUpgradeEligibilityResponse.class);

	EasyMock.expect(mockUpgradeChecker.getCachedUpgradeEligibility(capture(capture))).andReturn(
		getCachedUpgradeEligibilityResponse.getUpgradeEligibilityResponse());
	EasyMock.replay(mockUpgradeChecker);

	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("SomeSourceSystem");
	EasyMock.expect(drpPropertyService.getProperty("DUMMY_STORE_ENABLED")).andReturn("false");
	EasyMock.replay(drpPropertyService);

	final Customer customer = CustomerFactory.getMockCustomer();
	final String transactionId = "someTransactionId";
	final DrpUser drpUser = DrpUserFactory.getDrpUser();

	UpgCheckerResponseData upgCheckerResponseData = upgradeCheckerSaoImpl.getCachedSubcribersUpgradeInfo(customer,
		transactionId, drpUser);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationalBusinessHierarchy());
	assertEquals("Incorrect mobilePhoneNumber passed in service call", customer.getContactPhone(), capture
		.getValue().getMobilePhoneNumber());
	assertEquals("Incorrect zip passed in service call", customer.getAddress().getZipcode(), capture.getValue()
		.getZip());
	assertEquals("Incorrect capTransactionId passed in service call", transactionId, capture.getValue()
		.getCapTransactionId());

	// Check that correct values were returned from SAO
	assertNotNull("No UCS response data", upgCheckerResponseData);
	// TODO Add more assertions
    }

    /**
     * Test for {@link UpgradeCheckerSaoImpl#getOptInStatus(java.util.List, String, DrpUser)}.
     */
    public void testGetOptInStatus() throws Exception {
	// Prepare to record request sent in service call
	Capture<NotificationStatusGetRequest> capture = new Capture<NotificationStatusGetRequest>();

	// Load a canned service response
	GetNotificationStatusPlusResponse getNotificationStatusPlusResponse = loadResponse(
		"UCS_getNotificationStatusPlusResponse.xml", GetNotificationStatusPlusResponse.class);

	EasyMock.expect(mockUpgradeChecker.getNotificationStatusPlus(capture(capture))).andReturn(
		getNotificationStatusPlusResponse.getNotificationStatusGetResponse());
	EasyMock.replay(mockUpgradeChecker);

	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("SomeSourceSystem");
	EasyMock.expect(drpPropertyService.getProperty("DUMMY_STORE_ENABLED")).andReturn("false");
	EasyMock.replay(drpPropertyService);

	final List<String> phoneNumbers = new ArrayList<String>();
	final String phoneNumber1 = "somePhoneNumber";
	final String phoneNumber2 = "anotherPhoneNumber";
	phoneNumbers.add(phoneNumber1);
	phoneNumbers.add(phoneNumber2);
	final String zipCode = "someZipCode";
	final DrpUser drpUser = DrpUserFactory.getDrpUser();

	Map<String, String> optInStatusResult = upgradeCheckerSaoImpl.getOptInStatus(phoneNumbers, zipCode, drpUser);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationalBusinessHierarchy());
	assertEquals("Incorrect phone numbers passed in service call", phoneNumbers, capture.getValue()
		.getMobilePhoneNumbers());
	assertEquals("Incorrect zip passed in service call", zipCode, capture.getValue().getZip());
	assertEquals("Incorrect userId passed in service call", drpUser.getUserId(), capture.getValue().getUserId());

	// Check that correct values were returned from SAO
	assertNotNull("No opt in result", optInStatusResult);
	// TODO Add more assertions
    }
}
