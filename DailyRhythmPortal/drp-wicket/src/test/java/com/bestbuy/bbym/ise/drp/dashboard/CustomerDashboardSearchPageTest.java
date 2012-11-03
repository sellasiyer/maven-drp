package com.bestbuy.bbym.ise.drp.dashboard;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.DailyRhythmPortalService;
import com.bestbuy.bbym.ise.drp.service.DashboardDataService;
import com.bestbuy.bbym.ise.drp.service.RSSFeedService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link CustomerDashboardSearchPage}.
 */
public class CustomerDashboardSearchPageTest extends BaseNavPageTest {

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
	CustomerService customerService = EasyMock.createMock(CustomerService.class);
	mockApplicationContext.putBean("customerService", customerService);
	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
	dailyRhythmPortalService = EasyMock.createMock(DailyRhythmPortalService.class);
	mockApplicationContext.putBean("dailyRhythmPortalService", dailyRhythmPortalService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(CustomerDashboardSearchPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(CustomerDashboardSearchPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	setMockSupportedCarriers(dailyRhythmPortalService);

	// start and render the test page
	tester.startPage(CustomerDashboardSearchPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(CustomerDashboardSearchPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();
    }

}
