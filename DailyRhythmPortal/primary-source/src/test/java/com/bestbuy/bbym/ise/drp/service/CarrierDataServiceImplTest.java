package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerFactory;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.OptInResponse;
import com.bestbuy.bbym.ise.drp.sao.CapSao;
import com.bestbuy.bbym.ise.drp.sao.UpgradeCheckerSao;

/**
 * JUnit test for {@link CarrierDataServiceImpl}.
 * 
 * @author a915726
 */
public class CarrierDataServiceImplTest {

    private CarrierDataServiceImpl service = new CarrierDataServiceImpl();
    private CapSao mockCapSao;
    private UpgradeCheckerSao mockUpgradeCheckerSao;

    @Before
    public void setUp() {
	mockCapSao = createStrictMock(CapSao.class);
	mockUpgradeCheckerSao = createStrictMock(UpgradeCheckerSao.class);
	service.setCapSao(mockCapSao);
	service.setUpgradeCheckerSao(mockUpgradeCheckerSao);
    }

    /**
     * Testing for the method getSubscribersCarrierInfo (invokeUcs=true)
     */
    @Test
    public void testGetSubscribersCarrierInfo_invokeUcs_true() throws Exception {
	Customer customer = CustomerFactory.getMockCustomer();
	DrpUser drpUser = DrpUserFactory.getDrpUser();
	boolean invokeUcs = true;
	CustomersDashboardCarrierData customersDashboardCarrierData = new CustomersDashboardCarrierData();

	expect(mockUpgradeCheckerSao.getSubcribersUpgradeInfo(customer, drpUser)).andReturn(
		customersDashboardCarrierData);

	// Unexpected method calls after this point with throw exceptions
	EasyMock.replay(mockUpgradeCheckerSao);

	// Testing
	CustomersDashboardCarrierData expectedResult = service.getSubscribersCarrierInfo(customer, drpUser, invokeUcs);
	assertEquals(customersDashboardCarrierData, expectedResult);
    }

    /**
     * Testing for the method setSubscribersOptIn
     */
    @Test
    public void testSetSubscribersOptIn() throws Exception {
	Customer customer = CustomerFactory.getMockCustomer();
	DrpUser drpUser = DrpUserFactory.getDrpUser();
	List<Line> optedSubsLineData = new ArrayList<Line>();
	HashMap<String, String> phoneOptInStatPair = new HashMap<String, String>();
	OptInResponse response = null;

	expect(mockUpgradeCheckerSao.setOptInStatus(phoneOptInStatPair, customer.getAddress().getZipcode(), drpUser))
		.andReturn(response);

	// Unexpected method calls after this point with throw exceptions
	EasyMock.replay(mockCapSao);

	// Testing
	OptInResponse expectedResult = service.setSubscribersOptIn(optedSubsLineData, customer, drpUser);
	assertEquals(response, expectedResult);
    }

}
