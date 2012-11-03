package com.bestbuy.bbym.ise.drp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.CarrierDataService;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.DailyRhythmPortalService;
import com.bestbuy.bbym.ise.drp.service.DashboardDataService;
import com.bestbuy.bbym.ise.drp.service.RSSFeedService;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link CustomerDashboardPage}.
 */
public class CustomerDashboardPageTest extends BaseNavPageTest {

    private RSSFeedService rssFeedService;
    private DashboardDataService dashboardDataService;
    private DailyRhythmPortalService dailyRhythmPortalService;

    @Override
    public void setUp() {
	super.setUp();
	rssFeedService = EasyMock.createMock(RSSFeedService.class);
	mockApplicationContext.putBean("rssFeedService", rssFeedService);
	dashboardDataService = EasyMock.createMock(DashboardDataService.class);
	mockApplicationContext.putBean("dashboardDataService", dashboardDataService);
	dailyRhythmPortalService = EasyMock.createMock(DailyRhythmPortalService.class);
	mockApplicationContext.putBean("dailyRhythmPortalService", dailyRhythmPortalService);
	CustomerService customerService = EasyMock.createMock(CustomerService.class);
	mockApplicationContext.putBean("customerService", customerService);
	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
	CarrierDataService carrierDataService = EasyMock.createMock(CarrierDataService.class);
	mockApplicationContext.putBean("carrierDataService", carrierDataService);
	RewardZoneService rewardZoneService = EasyMock.createMock(RewardZoneService.class);
	mockApplicationContext.putBean("rewardZoneService", rewardZoneService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(CustomerDashboardPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(CustomerDashboardPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() throws Exception {
	getCurrentDailyRhythmPortalSession().setSessionPropertyBoolean("needCustomer", false);

	List<Carrier> carriers = new ArrayList<Carrier>();
	carriers.add(Carrier.SPRINT);
	EasyMock.expect(dailyRhythmPortalService.getSupportedCarriers()).andReturn(carriers);
	EasyMock.replay(dailyRhythmPortalService);

	EasyMock.expect(getMockDrpPropertyService().getProperty("triage.enabled")).andReturn("true");
	EasyMock.expect(getMockDrpPropertyService().getProperty("triage.enabled")).andReturn("true");
	EasyMock.expect(getMockDrpPropertyService().getProperty("returnsExchanges.enabled")).andReturn("true");
	EasyMock.replay(getMockDrpPropertyService());

	// start and render the test page
	tester.startPage(CustomerDashboardPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(CustomerDashboardPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();

	tester.assertComponent("quickActionsPanel", DashboardQuickActionsPanel.class);
    }

}
