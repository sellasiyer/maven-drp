package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.BuybackPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerFactory;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.sao.CustomerProfileSao;
import com.bestbuy.bbym.ise.drp.sao.EcTransactionLookupSao;
import com.bestbuy.bbym.ise.drp.sao.HubProtectionPlanSao;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link CustomerDataServiceImpl}.
 */
public class CustomerDataServiceImplTest {

    private CustomerDataServiceImpl service;
    private HubProtectionPlanSao mockHubProtectionPlanSao;
    private CustomerProfileSao mockCustomerProfileSao;
    private EcTransactionLookupSao mockEcTransactionLookupSao;
    private DrpPropertyServiceImpl drpPropertyService;

    @Before
    public void setUp() {
	service = new CustomerDataServiceImpl();
	mockHubProtectionPlanSao = createStrictMock(HubProtectionPlanSao.class);
	mockCustomerProfileSao = createStrictMock(CustomerProfileSao.class);
	mockEcTransactionLookupSao = createStrictMock(EcTransactionLookupSao.class);
	drpPropertyService = createStrictMock(DrpPropertyServiceImpl.class);
	service.setHubProtectionPlanSao(mockHubProtectionPlanSao);
	service.setCustomerProfileSao(mockCustomerProfileSao);
	service.setEcTransactionLookupSao(mockEcTransactionLookupSao);
	service.setDrpPropertyServiceImpl(drpPropertyService);
    }

    /**
     * Test {@link CustomerDataService#getBBYCustomerProfile(Customer, CustomerSearchCriteria, DrpUser)}.
     */
    @Test
    public void testGetBBYCustomerProfile() throws Exception {

	final Customer customer = CustomerFactory.getMockCustomer();
	DrpUser drpUser = DrpUserFactory.getDrpUser();

	final List<Customer> list = new ArrayList<Customer>();
	final Customer returnedCustomer = CustomerFactory.getAnotherMockCustomer();
	list.add(returnedCustomer);

	expect(mockCustomerProfileSao.searchBbyCustomer(customer, CustomerSearchCriteria.PHONE_EMAIL, drpUser))
		.andReturn(list);
	replay(mockCustomerProfileSao);

	final String rewardZoneNumber = "someRewardZoneNumber";

	List<Customer> expectedResult = service.getBBYCustomerProfile(customer, CustomerSearchCriteria.PHONE_EMAIL,
		drpUser);
	assertSame("Incorrect customers", list, expectedResult);
	//	assertEquals("Customer rewardZone not populated", rewardZoneNumber, expectedResult.get(0).getRewardZoneId());
    }

    /**
     * Test {@link CustomerDataService#getAllServicePlans(Customer)}.
     */
    @Test
    public void testGetAllServicePlans() throws Exception {
	Customer customer = CustomerFactory.getMockCustomer();
	List<ServicePlan> list = new ArrayList<ServicePlan>();

	expect(mockHubProtectionPlanSao.searchServicePlan(customer)).andReturn(list);
	replay(mockHubProtectionPlanSao);

	List<ServicePlan> expectedResult = service.getAllServicePlans(customer);
	assertEquals(list, expectedResult);
    }

    /**
     * Test {@link CustomerDataService#getServicePlanDetails(ServicePlan)}.
     */
    @Test
    public void testGetServicePlanDetails() throws Exception {
	ServicePlan servicePlanInfo = new BuybackPlan();

	expect(mockHubProtectionPlanSao.getServicePlanDetails(servicePlanInfo)).andReturn(servicePlanInfo);
	replay(mockHubProtectionPlanSao);

	ServicePlan expectedResult = service.getServicePlanDetails(servicePlanInfo);
	assertEquals(servicePlanInfo, expectedResult);
    }

    /**
     * Test
     * {@link CustomerDataService#getMobilePurchaseHistory(Customer, DrpUser, Date, Date)}
     * .
     */
    @Test
    public void testGetMobilePurchaseHistory() throws Exception {
	Customer customer = CustomerFactory.getMockCustomer();
	DrpUser drpUser = DrpUserFactory.getDrpUser();
	Date startDate = new Date();
	Date endDate = new Date();
	boolean mobileOnly = true;
	List<Product> list = new ArrayList<Product>();

	expect(mockEcTransactionLookupSao.getAllTransactions(customer, drpUser, startDate, endDate, mobileOnly))
		.andReturn(list);
	replay(mockEcTransactionLookupSao);

	List<Product> expectedResult = service.getMobilePurchaseHistory(customer, drpUser, startDate, endDate);
	assertEquals(list, expectedResult);
    }

    /**
     * Test
     * {@link CustomerDataService#getAllPurchaseHistory(Customer, DrpUser, Date, Date)}
     * .
     */
    @Test
    public void testGetAllPurchaseHistory() throws Exception {
	Customer customer = CustomerFactory.getMockCustomer();
	DrpUser drpUser = DrpUserFactory.getDrpUser();

	Date startDate = new Date();
	Date endDate = new Date();
	boolean mobileOnly = false;
	List<Product> list = new ArrayList<Product>();

	expect(mockEcTransactionLookupSao.getAllTransactions(customer, drpUser, startDate, endDate, mobileOnly))
		.andReturn(list);
	replay(mockEcTransactionLookupSao);

	List<Product> expectedResult = service.getAllPurchaseHistory(customer, drpUser, startDate, endDate);
	assertEquals(list, expectedResult);

    }

    /**
     * Test
     * {@link CustomerDataService#getBuyBackValue(ServicePlan, String, Date, DrpUser)}
     * .
     */
    @Test
    public void testGetBuyBackValue() throws Exception {
	ServicePlan servicePlanInfo = new BuybackPlan();
	String productSku = "123";
	Date productPurchaseDate = new Date();
	DrpUser drpUser = DrpUserFactory.getDrpUser();
	BigDecimal retailPrice = BigDecimal.ZERO;

	expect(mockHubProtectionPlanSao.getServicePlanDetails(servicePlanInfo)).andReturn(servicePlanInfo);
	expect(
		mockEcTransactionLookupSao.getRetailPriceByFourPartKey(servicePlanInfo.getTransactionId(), productSku,
			drpUser)).andReturn(retailPrice);

	replay(mockHubProtectionPlanSao);
	replay(mockEcTransactionLookupSao);

	BigDecimal expectedResult = service.getBuyBackValue(servicePlanInfo, productSku, productPurchaseDate, drpUser);
	assertEquals(retailPrice, expectedResult);

    }

    /**
     * Test {@link CustomerDataService#getProtectionPlan(String)}.
     */
    @Test
    public void testGetProtectionPlan() throws Exception {
	String planId = "123456";
	ProtectionPlan plan = new ProtectionPlan();

	List<ServicePlan> list = new ArrayList<ServicePlan>();
	list.add(plan);
	expect(mockHubProtectionPlanSao.searchServicePlanByPlanId(planId)).andReturn(list);
	replay(mockHubProtectionPlanSao);

	ProtectionPlan expectedResult = service.getProtectionPlan(planId);
	assertEquals(plan, expectedResult);
    }

    /**
     * Test {@link CustomerDataService#findProtectionPlan(String)}.
     */
    @Test
    public void testFindProtectionPlan() throws Exception {
	String deviceSerialNum = "123456";
	ProtectionPlan plan = new ProtectionPlan();

	List<ServicePlan> list = new ArrayList<ServicePlan>();
	list.add(plan);
	replay(drpPropertyService);
	expect(mockHubProtectionPlanSao.searchServicePlanByIMEI(deviceSerialNum)).andReturn(list);
	replay(mockHubProtectionPlanSao);
	ProtectionPlan expectedResult = service.findProtectionPlan(deviceSerialNum);
	assertEquals(plan, expectedResult);
    }

}
