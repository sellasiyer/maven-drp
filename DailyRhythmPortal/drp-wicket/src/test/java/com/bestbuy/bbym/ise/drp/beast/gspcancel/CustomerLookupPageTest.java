package com.bestbuy.bbym.ise.drp.beast.gspcancel;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.dao.BbyAccountDao;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.BbyAccountFactory;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.GSPPlanService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link CustomerLookupPage}.
 */
public class CustomerLookupPageTest extends BaseWebPageTest {

    private CustomerService customerService;
    private BbyAccountDao mockBbyAccountDao;

    @Override
    public void setUp() {
	super.setUp();

	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
	customerService = EasyMock.createMock(CustomerService.class);
	mockApplicationContext.putBean("customerService", customerService);
	GSPPlanService gspPlanService = EasyMock.createMock(GSPPlanService.class);
	mockApplicationContext.putBean("gspPlanService", gspPlanService);
	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);
	mockBbyAccountDao = EasyMock.createMock(BbyAccountDao.class);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(CustomerLookupPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(CustomerLookupPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() {
	// start and render the test page
	tester.startPage(CustomerLookupPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(CustomerLookupPage.class);
	tester.assertNoErrorMessage();

    }

    @Test
    public void testCustomerLookupPage() throws Exception {

	BbyAccount bbyAccount = BbyAccountFactory.getBbyAccount();
	try{
	    expect(mockBbyAccountDao.getBbyAccount("dataSharingKey")).andReturn(bbyAccount);
	}catch(EmptyResultDataAccessException e){
	    EasyMock.replay(mockBbyAccountDao);
	    BbyAccount expectedResult = customerService.getBbyCustomer("dataSharingKey");
	    assertEquals(bbyAccount, expectedResult);
	}
    }

}
