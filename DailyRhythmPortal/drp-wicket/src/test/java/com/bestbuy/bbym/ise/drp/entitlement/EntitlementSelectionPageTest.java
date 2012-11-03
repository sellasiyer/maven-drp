package com.bestbuy.bbym.ise.drp.entitlement;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookupFactory;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.DailyRhythmPortalService;
import com.bestbuy.bbym.ise.drp.service.ReturnExchangeService;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link EntitlementSelectionPage}.
 */
public class EntitlementSelectionPageTest extends BaseNavPageTest {

    @Override
    public void setUp() {
	super.setUp();
	ReturnExchangeService returnExchangeService = EasyMock.createMock(ReturnExchangeService.class);
	mockApplicationContext.putBean("returnExchangeService", returnExchangeService);
	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
	RewardZoneService rewardZoneService = EasyMock.createMock(RewardZoneService.class);
	mockApplicationContext.putBean("rewardZoneService", rewardZoneService);
	DailyRhythmPortalService dailyRhythmPortalService = EasyMock.createMock(DailyRhythmPortalService.class);
	mockApplicationContext.putBean("dailyRhythmPortalService", dailyRhythmPortalService);
	TriageService triageService = EasyMock.createMock(TriageService.class);
	mockApplicationContext.putBean("triageService", triageService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(EntitlementSelectionPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(EntitlementSelectionPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	EntitlementLookup entitlementLookup = EntitlementLookupFactory.getEntitlementLookup();

	MobileEntitlement mobileEntitlement = new MobileEntitlement();

	// start and render the test page
	EntitlementSelectionPage page = new EntitlementSelectionPage(entitlementLookup, mobileEntitlement, null, null);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(EntitlementSelectionPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();
    }

}
