package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import com.bestbuy.bbym.ise.drp.dao.BbyAccountDaoImpl;
import com.bestbuy.bbym.ise.drp.dao.CustomerDaoImpl;
import com.bestbuy.bbym.ise.drp.dao.RecSheetSummaryDaoImpl;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.BbyAccountFactory;
import com.bestbuy.bbym.ise.drp.domain.Customer;
import com.bestbuy.bbym.ise.drp.domain.CustomerFactory;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummary;
import com.bestbuy.bbym.ise.drp.domain.RecSheetSummaryFactory;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.domain.RecommendationFactory;

/**
 * JUnit test for {@link CustomerServiceImpl}.
 * 
 * @author a915726
 */
public class CustomerServiceImplTest {

    private CustomerServiceImpl service = new CustomerServiceImpl();
    private CustomerDaoImpl mockCustomerDao;
    private BbyAccountDaoImpl mockBbyAccountDao;
    private RecSheetSummaryDaoImpl mockRecSheetSummaryDao;

    @Before
    public void setUp() {
	mockCustomerDao = createStrictMock(CustomerDaoImpl.class);
	mockBbyAccountDao = createStrictMock(BbyAccountDaoImpl.class);
	ReflectionTestUtils.setField(service, "customerDao", mockCustomerDao);
	ReflectionTestUtils.setField(service, "bbyAccountDao", mockBbyAccountDao);
    }

    /**
     * Test for {@link CustomerServiceImpl#addCustomer(Customer)}.
     */
    @Test
    public void testAddCustomer() throws Exception {
	Customer customer = CustomerFactory.getCustomer();

	mockCustomerDao.persistCustomer(customer);
	EasyMock.expectLastCall();

	EasyMock.replay(mockCustomerDao);

	// Testing
	service.addCustomer(customer);
	EasyMock.verify(mockCustomerDao);
    }

    /**
     * Test for {@link CustomerServiceImpl#getCustomer(String, String)}.
     */
    @Test
    public void testGetCustomer() throws Exception {
	Customer customer = CustomerFactory.getCustomer();

	expect(mockCustomerDao.getCustomer("storeId", "dataSharingKey")).andReturn(customer);

	// Unexpected method calls after this point with throw exceptions
	EasyMock.replay(mockCustomerDao);

	// Testing
	Customer expectedResult = service.getCustomer("storeId", "dataSharingKey");
	assertEquals(customer, expectedResult);
    }

    /**
     * Test for {@link CustomerServiceImpl#addBbyCustomer(BbyAccount)}.
     */
    @Test
    public void testAddBbyCustomer() throws Exception {
	BbyAccount bbyAccount = BbyAccountFactory.getBbyAccount();

	mockBbyAccountDao.persistBbyAccount(bbyAccount);
	EasyMock.expectLastCall();

	EasyMock.replay(mockBbyAccountDao);

	// Testing
	service.addBbyCustomer(bbyAccount);
	EasyMock.verify(mockBbyAccountDao);
    }

    /**
     * Test for {@link CustomerServiceImpl#updateBbyCustomer(BbyAccount)}.
     */
    @Test
    public void testUpdateBbyCustomer() throws Exception {
	BbyAccount bbyAccount = BbyAccountFactory.getBbyAccount();

	mockBbyAccountDao.updateBbyAccount(bbyAccount);
	EasyMock.expectLastCall();

	EasyMock.replay(mockBbyAccountDao);

	// Testing
	service.updateBbyCustomer(bbyAccount);
	EasyMock.verify(mockBbyAccountDao);
    }

    /**
     * Test for {@link CustomerServiceImpl#updateCustomer(Customer)}.
     */
    @Test
    public void testUpdateCustomer() throws Exception {
	Customer customer = CustomerFactory.getCustomer();

	mockCustomerDao.updateCustomer(customer);
	EasyMock.expectLastCall();

	EasyMock.replay(mockCustomerDao);

	// Testing
	service.updateCustomer(customer);
	EasyMock.verify(mockCustomerDao);
    }

    /**
     * Test for {@link CustomerServiceImpl#getBbyCustomer(String).
     */
    @Test
    public void testGetBbyCustomer() throws Exception {
	BbyAccount bbyAccount = BbyAccountFactory.getBbyAccount();

	expect(mockBbyAccountDao.getBbyAccount("dataSharingKey")).andReturn(bbyAccount);

	// Unexpected method calls after this point with throw exceptions
	EasyMock.replay(mockBbyAccountDao);

	// Testing
	BbyAccount expectedResult = service.getBbyCustomer("dataSharingKey");
	assertEquals(bbyAccount, expectedResult);

    }

    /**
     * Test for
     * {@link CustomerServiceImpl#addRecSheetSummary(com.bestbuy.bbym.ise.domain.Customer, Recommendation, DrpUser)}
     * .
     */
    @Test
    @Ignore
    public void testAddRecSheetSummary() throws Exception {
	Customer customer = CustomerFactory.getCustomer();
	BbyAccount bbyAccount = customer.getBbyAccount();

	com.bestbuy.bbym.ise.domain.Customer cust = new com.bestbuy.bbym.ise.domain.Customer();
	// cust.setDataSharingKey(customer.getDataSharingKey());
	cust.setDataSharingKey("d2c53ec8-e59d-40cb-aa08-c5c4c7840f64");
	cust.setFirstName(bbyAccount.getFirstName());
	cust.setLastName(bbyAccount.getLastName());
	cust.setContactPhone(bbyAccount.getPhoneNumber());

	// RecSheetSummary recSheetSummary =
	// this.getRecSheetSummary(customer.getDataSharingKey());
	RecSheetSummary recSheetSummary = RecSheetSummaryFactory.getRecSheetSummary(cust.getDataSharingKey());
	Recommendation recommendation = RecommendationFactory.getRecommendation();
	DrpUser drpUser = DrpUserFactory.getDrpUser();

	mockRecSheetSummaryDao.persistRecSheetSummary(recSheetSummary);
	EasyMock.expectLastCall();

	EasyMock.replay(mockRecSheetSummaryDao);

	// Testing
	service.addRecSheetSummary(cust, recommendation, drpUser);
	EasyMock.verify(mockRecSheetSummaryDao);
    }

}
