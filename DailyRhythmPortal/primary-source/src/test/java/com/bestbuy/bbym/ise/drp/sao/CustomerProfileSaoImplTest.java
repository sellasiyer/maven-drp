package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientca.CustomerAccountServicesPortType;
import com.bestbuy.bbym.ise.iseclientca.SearchCustomerRequestType;
import com.bestbuy.bbym.ise.iseclientca.SearchCustomerRequestType.SearchParameters;
import com.bestbuy.bbym.ise.iseclientca.SearchCustomerResponseType;
import com.bestbuy.bbym.ise.iseclientca.Security;

/**
 * JUnit test for {@link CustomerProfileSaoImpl}.
 * 
 * @author a218635
 */
public class CustomerProfileSaoImplTest extends BaseSaoTest {

    private CustomerProfileSaoImpl customerProfileSaoImpl;
    private CustomerAccountServicesPortType mockCustomerAccountServicesPortType;

    @Override
    public void setUp() {
	super.setUp();

	mockCustomerAccountServicesPortType = EasyMock.createMock(CustomerAccountServicesPortType.class);
	customerProfileSaoImpl = new CustomerProfileSaoImpl() {

	    @Override
	    protected CustomerAccountServicesPortType getCustomerAccountServices() throws ServiceException {
		return mockCustomerAccountServicesPortType;
	    }
	};

	customerProfileSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH for CA?
    }

    /**
     * Test {@link CustomerProfileSaoImpl#searchBbyCustomer(Customer, CustomerSearchCriteria, DrpUser)}.
     */
    @Test
    public void testSearchBbyCustomer() throws Exception {

	// Prepare to record request sent in service call
	Capture<SearchCustomerRequestType> capture = new Capture<SearchCustomerRequestType>();

	// Load a canned service response
	SearchCustomerResponseType searchCustomerResponseType = loadResponse("CA_SearchCustomerResponseType.xml",
		SearchCustomerResponseType.class);

	EasyMock.expect(mockCustomerAccountServicesPortType.searchCustomer(capture(capture), isA(Security.class)))
		.andReturn(searchCustomerResponseType);
	EasyMock.replay(mockCustomerAccountServicesPortType);

	final DrpUser drpUser = DrpUserFactory.getDrpUser();
	final Customer bbyCustomer = new Customer();
	bbyCustomer.setEmail("CINGINDTAX@CINGULAR.COM");

	expect(drpPropertyService.getProperty("APPLICATION_NAME")).andReturn("someApplicationName");
	expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("someSourceSystem");
	EasyMock.replay(drpPropertyService);

	List<Customer> customers = customerProfileSaoImpl.searchBbyCustomer(bbyCustomer, CustomerSearchCriteria.EMAIL,
		drpUser);

	// Check that correct values were sent in service call
	SearchParameters sentSearchParameters = capture.getValue().getSearchParameters();
	assertEquals("Incorrect email passed in service call", bbyCustomer.getEmail(), sentSearchParameters
		.getCustomer().getEmailAddress().getEmailAddress());

	// Check that correct values were returned from SAO
	assertNotNull("No customers", customers);
	assertEquals("Incorrect number of customers returned from service call", 1, customers.size());
	Customer customer = customers.get(0);
	assertEquals("Incorrect bbyCustomerId returned from service call", "19207676", customer.getBbyCustomerId());
	assertEquals("Incorrect firstName returned from service call", "RONALD", customer.getFirstName());
	assertEquals("Incorrect lastName returned from service call", "SPURGEON", customer.getLastName());
	// TODO Add more assertions
    }
}
