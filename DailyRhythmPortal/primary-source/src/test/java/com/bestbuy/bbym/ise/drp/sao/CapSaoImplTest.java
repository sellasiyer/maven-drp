package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.stream.StreamSource;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.dao.TestUtil;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.service.DashboardDataServiceImplTest;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientcap.CAPWsController;
import com.bestbuy.bbym.ise.iseclientforcaprequest.Body;
import com.bestbuy.bbym.ise.iseclientforcaprequest.Header;
import com.bestbuy.bbym.ise.iseclientforcaprequest.PostpaidAccountValidationRequest;

/**
 * JUnit test for {@link CapSaoImpl}.
 */
public class CapSaoImplTest extends BaseSaoTest {

    private CapSaoImpl capSaoImpl;
    private CAPWsController mockCAPWsController;

    @Override
    public void setUp() {
	super.setUp();

	mockCAPWsController = EasyMock.createMock(CAPWsController.class);
	capSaoImpl = new CapSaoImpl() {

	    @Override
	    protected CAPWsController getCapWsController() throws ServiceException {
		return mockCAPWsController;
	    }

	    @Override
	    public boolean isDummyStoreEnabled() throws ServiceException {
		return false;
	    }
	};

	expectIbhPropertiesCalls(false);

	capSaoImpl.setDrpPropertiesService(drpPropertyService);
	capSaoImpl.setRequestMarshaller(getRequestMarshaller());
	capSaoImpl.setResponseUnMarshaller(getResponseUnMarshaller());
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientforcapcommon.IntlBusinessHierarchyType.class);
    }

    /**
     * Test {@link CapSaoImpl#getSubsAccInfoFromCarrier(com.bestbuy.bbym.ise.domain.Customer, String, com.bestbuy.bbym.ise.drp.domain.DrpUser)}.
     */
    @Test
    public void testGetSubsAccInfoFromCarrier() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> capture = new Capture<String>();

	// Load a canned service response
	String responseMessage = TestUtil.readFileToString(getClass(), "CAP_PostpaidAccountValidationResponse.xml");

	EasyMock.expect(mockCAPWsController.processRequest(capture(capture))).andReturn(responseMessage);
	EasyMock.replay(mockCAPWsController);

	EasyMock.expect(drpPropertyService.getProperty("DISPLAY_SERVICE_XMLS")).andReturn("false");
	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("SomeSourceSystem");
	EasyMock.expect(drpPropertyService.getProperty("TRAINING_MODE")).andReturn("false");
	EasyMock.replay(drpPropertyService);

	final Customer customer = DashboardDataServiceImplTest.createCustomer();
	final String zipCode = customer.getAddress().getZipcode();
	final String iseTransactionId = "someIseTransactionId";
	final DrpUser user = DrpUserFactory.getDrpUser();

	CustomersDashboardCarrierData data = capSaoImpl.getSubsAccInfoFromCarrier(customer, iseTransactionId, user);

	// Check that correct values were sent in service call
	PostpaidAccountValidationRequest capRequest = (PostpaidAccountValidationRequest) getRequestUnMarshaller()
		.unmarshal(new StreamSource(new StringReader(capture.getValue())));
	Header requestHeader = capRequest.getHeader();
	assertNotNull("No IBH passed in service call", requestHeader.getInternationalBusinessHierarchy());
	assertEquals("Incorrect repId passed in service call", user.getUserId(), requestHeader.getRepId());
	assertEquals("Incorrect transactionId passed in service call", iseTransactionId, requestHeader
		.getTransactionId());
	Body requestBody = capRequest.getBody();
	assertEquals("Incorrect accountPhoneNumber passed in service call", customer.getContactPhone(), requestBody
		.getAccountPhoneNumber());
	assertEquals("Incorrect zipCode passed in service call", zipCode, requestBody.getZipCode());

	// Check that correct values were returned from SAO
	assertNotNull("No customer dashoard carrier data", data);
	assertEquals("Incorrect number of lines", 3, data.getSubscriptionInfo().getLines().size());
	// TODO Add more assertions
    }

    /**
     * Test for {@link CapSaoImpl#getRsaTokenByCarrier(com.bestbuy.bbym.ise.domain.Carrier, String, DrpUser)}.
     */
    @Test
    public void testGetRsaTokenByCarrier() throws Exception {

	// Load a canned service response
	String responseMessage = TestUtil.readFileToString(getClass(), "CAP_AgentCodeLookUpResponse.xml");

	// TODO Implement me!
    }

    private Marshaller getRequestMarshaller() {
	Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
	marshaller.setContextPath("com.bestbuy.bbym.ise.iseclientforcaprequest");
	Map<String, Object> marshallerProperties = new HashMap<String, Object>();
	marshallerProperties.put("com.sun.xml.bind.namespacePrefixMapper", new CapSaoImpl.MyNamespacePrefixMapper(
		"http://bestbuy.com/bbym/beast/cap/request/postpaid/accountValidation"));
	marshaller.setMarshallerProperties(marshallerProperties);
	return marshaller;
    }

    private Unmarshaller getRequestUnMarshaller() {
	Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
	unMarshaller.setContextPath("com.bestbuy.bbym.ise.iseclientforcaprequest");
	return unMarshaller;
    }

    private Unmarshaller getResponseUnMarshaller() {
	Jaxb2Marshaller unMarshaller = new Jaxb2Marshaller();
	unMarshaller.setContextPath("com.bestbuy.bbym.ise.iseclientforcapresponse");
	return unMarshaller;
    }

}
