package com.bestbuy.bbym.ise.drp.entitlement;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookupFactory;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.DailyRhythmPortalService;
import com.bestbuy.bbym.ise.drp.service.ReturnExchangeService;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link EntitlementLookupPage}.
 */
public class EntitlementLookupPageTest extends BaseNavPageTest {

    private DailyRhythmPortalService dailyRhythmPortalService;

    @Override
    public void setUp() {
	super.setUp();
	ReturnExchangeService returnExchangeService = EasyMock.createMock(ReturnExchangeService.class);
	mockApplicationContext.putBean("returnExchangeService", returnExchangeService);
	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
	RewardZoneService rewardZoneService = EasyMock.createMock(RewardZoneService.class);
	mockApplicationContext.putBean("rewardZoneService", rewardZoneService);
	dailyRhythmPortalService = EasyMock.createMock(DailyRhythmPortalService.class);
	mockApplicationContext.putBean("dailyRhythmPortalService", dailyRhythmPortalService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(EntitlementLookupPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(EntitlementLookupPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	setMockSupportedCarriers(dailyRhythmPortalService);

	EntitlementLookup entitlementLookup = EntitlementLookupFactory.getEntitlementLookup();

	// start and render the test page
	EntitlementLookupPage page = new EntitlementLookupPage(entitlementLookup, null, null);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(EntitlementLookupPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();
    }

}
