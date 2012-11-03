package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;
import com.bestbuy.tsh.sales.common.components.v1.SearchParameterType;
import com.bestbuy.tsh.sales.common.components.v1.SearchTransactionsRequestType;
import com.bestbuy.tsh.sales.common.components.v1.SearchTransactionsResponseType;
import com.example.xmlns._1299800422009.PortType;

/**
 * JUnit test for {@link EcTransactionLookupSaoImpl}.
 * 
 * @author a218635
 */
public class EcTransactionLookupSaoImplTest extends BaseSaoTest {

    private EcTransactionLookupSaoImpl ecTransactionLookupSaoImpl;
    private PortType mockPortType;

    @Override
    public void setUp() {
	super.setUp();

	mockPortType = EasyMock.createMock(PortType.class);
	ecTransactionLookupSaoImpl = new EcTransactionLookupSaoImpl() {

	    @Override
	    protected PortType getTshEcService() throws ServiceException {
		return mockPortType;
	    }

	};

	expectIbhPropertiesCalls(false);

	ecTransactionLookupSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.tsh.common.metadata.components.v1.InternationalBusinessHierarchyType.class);
    }

    /**
     * Test for {@link EcTransactionLookupSaoImpl#getAllTransactions(Customer, DrpUser, java.util.Date, java.util.Date, boolean)}.
     */
    @Test
    public void testGetAllTransactions() throws Exception {

	// Prepare to record request sent in service call
	Capture<SearchTransactionsRequestType> capture = new Capture<SearchTransactionsRequestType>();

	// Load a canned service response
	SearchTransactionsResponseType searchTransactionsResponseType = loadResponse(
		"ECTSH_SearchTransactionsResponse.xml", SearchTransactionsResponseType.class);

	EasyMock.expect(mockPortType.searchTransaction(capture(capture))).andReturn(searchTransactionsResponseType);
	EasyMock.replay(mockPortType);

	EasyMock.expect(drpPropertyService.getProperty("APPLICATION_NAME")).andReturn("SomeApplicationName");
	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("SomeSourceSystem");
	EasyMock.replay(drpPropertyService);

	final Customer bbyCustomer = new Customer();
	bbyCustomer.setBbyCustomerId("1095156210");
	final DrpUser drpUser = DrpUserFactory.getDrpUser();
	final Calendar now = Calendar.getInstance();
	final Calendar startDate = (Calendar) now.clone();
	startDate.add(Calendar.YEAR, -5);

	List<Product> products = ecTransactionLookupSaoImpl.getAllTransactions(bbyCustomer, drpUser, startDate
		.getTime(), now.getTime(), false);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarcy());
	SearchParameterType sentSearchParameterType = capture.getValue().getSearchParameter();
	assertEquals("Incorrect customerId passed in service call", bbyCustomer.getBbyCustomerId(),
		sentSearchParameterType.getCustomer().getCustomerID().getValue());
	assertEquals("Incorrect startDate passed in service call", Util.toXmlGregorianCalendarNoTimePart(startDate
		.getTime()), sentSearchParameterType.getStartDate());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No products", products);
	// TODO Add more assertions
    }

    /**
     * Test for {@link EcTransactionLookupSaoImpl#getAllTransactionsByFourPartKey(String, DrpUser, boolean)}.
     */
    @Test
    public void testGetAllTransactionsByFourPartKey() throws Exception {

	// Prepare to record request sent in service call
	Capture<SearchTransactionsRequestType> capture = new Capture<SearchTransactionsRequestType>();

	// Load a canned service response
	SearchTransactionsResponseType searchTransactionsResponseType = loadResponse(
		"ECTSH_SearchTransactionsResponse.xml", SearchTransactionsResponseType.class);

	EasyMock.expect(mockPortType.searchTransaction(capture(capture))).andReturn(searchTransactionsResponseType);
	EasyMock.replay(mockPortType);

	EasyMock.expect(drpPropertyService.getProperty("APPLICATION_NAME")).andReturn("SomeApplicationName");
	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("SomeSourceSystem");
	EasyMock.replay(drpPropertyService);

	final String transactionKey = "someTransactionKey";
	final DrpUser drpUser = DrpUserFactory.getDrpUser();
	final boolean mobileOnly = true;

	List<Product> products = ecTransactionLookupSaoImpl.getAllTransactionsByFourPartKey(transactionKey, drpUser,
		mobileOnly);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarcy());
	SearchParameterType sentSearchParameterType = capture.getValue().getSearchParameter();
	assertEquals("Incorrect transactionKey passed in service call", transactionKey, sentSearchParameterType
		.getTransactionKey().getValue());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No products", products);
	// TODO Assert that the products match what was in the canned XML
	// response file
    }

    /**
     * Test for {@link EcTransactionLookupSaoImpl#getRetailPriceByFourPartKey(String, String, DrpUser)}.
     */
    @Test
    public void testGetRetailPriceByFourPartKey() throws Exception {

	// Prepare to record request sent in service call
	Capture<SearchTransactionsRequestType> capture = new Capture<SearchTransactionsRequestType>();

	// Load a canned service response
	SearchTransactionsResponseType searchTransactionsResponseType = loadResponse(
		"ECTSH_SearchTransactionsResponse.xml", SearchTransactionsResponseType.class);

	EasyMock.expect(mockPortType.searchTransaction(capture(capture))).andReturn(searchTransactionsResponseType);
	EasyMock.replay(mockPortType);

	EasyMock.expect(drpPropertyService.getProperty("APPLICATION_NAME")).andReturn("SomeApplicationName");
	EasyMock.expect(drpPropertyService.getProperty("SOURCE_SYSTEM")).andReturn("SomeSourceSystem");
	EasyMock.replay(drpPropertyService);

	final String transactionKey = "someTransactionKey";
	final String sku = "someSku";
	final DrpUser drpUser = DrpUserFactory.getDrpUser();
	final Calendar now = Calendar.getInstance();
	final Calendar startDate = (Calendar) now.clone();
	startDate.add(Calendar.YEAR, -5);

	BigDecimal price = ecTransactionLookupSaoImpl.getRetailPriceByFourPartKey(transactionKey, sku, drpUser);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarcy());
	SearchParameterType sentSearchParameterType = capture.getValue().getSearchParameter();
	assertEquals("Incorrect transactionKey passed in service call", transactionKey, sentSearchParameterType
		.getTransactionKey().getValue());
	assertEquals("Incorrect sku passed in service call", sku, sentSearchParameterType.getItem().getSKU().getValue());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	// TODO Add more assertions
	//	assertNotNull("No price", price);
	//	assertEquals("Incorrect price", price, BigDecimal.valueOf(123));
    }
}
