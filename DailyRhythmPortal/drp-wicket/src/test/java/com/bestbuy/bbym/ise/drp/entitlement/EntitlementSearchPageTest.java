package com.bestbuy.bbym.ise.drp.entitlement;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.ReturnExchangeService;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.service.SKUService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link EntitlementSearchPage}.
 */
public class EntitlementSearchPageTest extends BaseNavPageTest {

    @Override
    public void setUp() {
	super.setUp();
	ReturnExchangeService returnExchangeService = EasyMock.createMock(ReturnExchangeService.class);
	mockApplicationContext.putBean("returnExchangeService", returnExchangeService);
	SKUService skuService = EasyMock.createMock(SKUService.class);
	mockApplicationContext.putBean("skuService", skuService);
	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
	RewardZoneService rewardZoneService = EasyMock.createMock(RewardZoneService.class);
	mockApplicationContext.putBean("rewardZoneService", rewardZoneService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(EntitlementSearchPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(EntitlementSearchPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	// start and render the test page
	EntitlementSearchPage page = new EntitlementSearchPage(new PageParameters());
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(EntitlementSearchPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();
    }

}
