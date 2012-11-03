package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.sao.OpenSSOSaoImpl.IseLdapRole;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientopensso.Attribute;
import com.bestbuy.bbym.ise.iseclientopensso.AttributesResponse;
import com.bestbuy.bbym.ise.iseclientopensso.AuthenticateResponse;
import com.bestbuy.bbym.ise.iseclientopensso.IdentityServicesImpl;
import com.bestbuy.bbym.ise.iseclientopensso.Token;

/**
 * JUnit test for {@link OpenSSOSaoImpl}.
 */
public class OpenSSOSaoImplTest extends BaseSaoTest {

    private OpenSSOSaoImpl openSSOSaoImpl;
    private IdentityServicesImpl mockIdentityServicesImpl;

    @Override
    public void setUp() {
	super.setUp();

	mockIdentityServicesImpl = EasyMock.createMock(IdentityServicesImpl.class);
	openSSOSaoImpl = new OpenSSOSaoImpl() {

	    @Override
	    protected IdentityServicesImpl getIdentityService() throws ServiceException {
		return mockIdentityServicesImpl;
	    }
	};

	openSSOSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// OpenSSO does not have an international business hierarchy in it's API
    }

    /**
     * Test for {@link OpenSSOSaoImpl#authenticate(String, String)}.
     */
    @Test
    public void testAuthenticate() throws Exception {

	// Prepare to record requests sent in service calls
	Capture<List<String>> captureAttributeNames = new Capture<List<String>>();
	Capture<Token> captureToken = new Capture<Token>();

	// Load canned service responses
	AuthenticateResponse authenticateResponse = loadResponse("OpenSSO_authenticateResponse.xml",
		AuthenticateResponse.class);
	AttributesResponse attributesResponse = loadResponse("OpenSSO_attributesResponse.xml", AttributesResponse.class);

	final String userName = "someUserName";
	final String password = "somePassword";

	// Expect a call to get the token
	EasyMock.expect(mockIdentityServicesImpl.authenticate(userName, password, null)).andReturn(
		authenticateResponse.getReturn());
	// Expect another call to get the user attributes
	EasyMock.expect(mockIdentityServicesImpl.attributes(capture(captureAttributeNames), capture(captureToken)))
		.andReturn(attributesResponse.getReturn());
	EasyMock.replay(mockIdentityServicesImpl);

	DrpUser drpUser = openSSOSaoImpl.authenticate(userName, password);

	// Check that correct values were sent in service calls
	assertEquals("Incorrect attributeNames passed in service call", Arrays.asList(new String[] {"uid", "sn",
		"givenName", "manager", "telephonenumber", "bboraclelocid", "bboraclehrtype" }), captureAttributeNames
		.getValue());
	assertEquals("Incorrect tokenId passed in service call",
		"AQIC5wM2LY4SfcxKYnAISJr76+ZdcgUrSOv1zi55QW2VPv4=@AAJTMQACMDIAAlNJAAIwMw==#", captureToken.getValue()
			.getId());

	// Check that correct values were returned from SAO
	assertNotNull("No user returned from service call", drpUser);
	assertEquals("Incorrect firstName returned from service call", getAttribute(attributesResponse, "givenName")
		.toUpperCase(), drpUser.getFirstName());
	assertEquals("Incorrect lastName returned from service call", getAttribute(attributesResponse, "sn")
		.toUpperCase(), drpUser.getLastName());
	assertEquals("Incorrect locationPhoneNum returned from service call", getAttribute(attributesResponse,
		"telephonenumber"), drpUser.getLocationPhoneNum());
	assertEquals("Incorrect locationType returned from service call", getAttribute(attributesResponse,
		"bboraclehrtype"), drpUser.getLocationType());
	assertEquals("Incorrect managerId returned from service call", getAttribute(attributesResponse, "manager"),
		drpUser.getManagerId());
	assertEquals("Incorrect openSsoTokenId returned from service call", authenticateResponse.getReturn().getId(),
		drpUser.getOpenSsoTokenId());
	assertEquals("Incorrect userId returned from service call", userName.toUpperCase(), drpUser.getUserId());
	assertFalse("Incorrect Beast flag returned from service call", drpUser.isBeast());

	assertNotNull("No roles returned from service call", drpUser.getApplicationRoles());
	List<String> expectedRoles = new ArrayList<String>();
	expectedRoles.add(IseLdapRole.ADMININSTRATOR.getIseRole());
	assertEquals("Incorrect roles returned from service call", expectedRoles, drpUser.getApplicationRoles());
    }

    /**
     * Test for {@link OpenSSOSaoImpl#authenticate(String, String)}.
     * <p>
     * Checks:
     * <ol>
     * <li>populating user roles when user has multiple roles assigned in LDAP</li>
     * <li>that user with team role is set to dummy store</li>
     * </ol>
     * </p>
     */
    @Test
    public void testAuthenticateMultipleRoles() throws Exception {
	// Load canned service responses
	AuthenticateResponse authenticateResponse = loadResponse("OpenSSO_authenticateResponse.xml",
		AuthenticateResponse.class);
	AttributesResponse attributesResponse = loadResponse("OpenSSO_attributesResponse2.xml",
		AttributesResponse.class);

	final String userName = "someUserName";
	final String password = "somePassword";

	// Expect a call to get the token
	EasyMock.expect(mockIdentityServicesImpl.authenticate(userName, password, null)).andReturn(
		authenticateResponse.getReturn());
	// Expect another call to get the user attributes
	EasyMock.expect(mockIdentityServicesImpl.attributes(isA(List.class), isA(Token.class))).andReturn(
		attributesResponse.getReturn());
	EasyMock.replay(mockIdentityServicesImpl);

	final String someDummyStoreNumber = "9283";
	expect(drpPropertyService.getProperty("DUMMY_STORE_NUM")).andReturn(someDummyStoreNumber);
	EasyMock.replay(drpPropertyService);

	DrpUser drpUser = openSSOSaoImpl.authenticate(userName, password);

	// Check that correct values were returned from SAO
	assertEquals("User with team role should have dummy store", someDummyStoreNumber, drpUser.getStoreId());
	assertNotNull("No roles returned from service call", drpUser.getApplicationRoles());
	List<String> expectedRoles = new ArrayList<String>();
	expectedRoles.add(IseLdapRole.ADMININSTRATOR.getIseRole());
	expectedRoles.add(IseLdapRole.TEAM.getIseRole());
	assertEquals("Incorrect roles returned from service call", expectedRoles, drpUser.getApplicationRoles());
    }

    /**
     * Test for {@link OpenSSOSaoImpl#authenticate(String, String)}.
     * <p>
     * Checks:
     * <ol>
     * <li>populating user roles when user has both roles and groups</li>
     * </ol>
     * </p>
     */
    @Test
    public void testAuthenticateRolesAndGroups() throws Exception {
	// Load canned service responses
	AuthenticateResponse authenticateResponse = loadResponse("OpenSSO_authenticateResponse.xml",
		AuthenticateResponse.class);
	AttributesResponse attributesResponse = loadResponse("OpenSSO_attributesResponse3.xml",
		AttributesResponse.class);

	final String userName = "someUserName";
	final String password = "somePassword";

	// Expect a call to get the token
	EasyMock.expect(mockIdentityServicesImpl.authenticate(userName, password, null)).andReturn(
		authenticateResponse.getReturn());
	// Expect another call to get the user attributes
	EasyMock.expect(mockIdentityServicesImpl.attributes(isA(List.class), isA(Token.class))).andReturn(
		attributesResponse.getReturn());
	EasyMock.replay(mockIdentityServicesImpl);

	final String someDummyStoreNumber = "9283";
	expect(drpPropertyService.getProperty("DUMMY_STORE_NUM")).andReturn(someDummyStoreNumber);
	EasyMock.replay(drpPropertyService);

	DrpUser drpUser = openSSOSaoImpl.authenticate(userName, password);

	// Check that correct values were returned from SAO
	assertNotNull("No roles returned from service call", drpUser.getApplicationRoles());
	List<String> expectedRoles = new ArrayList<String>();
	expectedRoles.add(IseLdapRole.ADMININSTRATOR.getIseRole());
	expectedRoles.add(IseLdapRole.MANAGER.getIseRole());
	expectedRoles.add(IseLdapRole.TEAM.getIseRole());
	assertEquals("Incorrect roles returned from service call", expectedRoles, drpUser.getApplicationRoles());
    }

    private List<String> getAttributes(AttributesResponse attributesResponse, String attributeName) {
	for(Attribute attribute: attributesResponse.getReturn().getAttributes()){
	    if (attributeName.equals(attribute.getName())){
		return attribute.getValues();
	    }
	}
	fail("Failed to get value for attribute " + attributeName);
	// We'll never get here
	return null;
    }

    private String getAttribute(AttributesResponse attributesResponse, String attributeName) {
	return getAttributes(attributesResponse, attributeName).get(0);
    }
}
