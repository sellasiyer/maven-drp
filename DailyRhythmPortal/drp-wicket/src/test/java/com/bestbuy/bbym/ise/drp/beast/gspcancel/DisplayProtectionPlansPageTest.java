package com.bestbuy.bbym.ise.drp.beast.gspcancel;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.Ignore;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.GSPPlanService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link DisplayProtectionPlansPage}.
 */
public class DisplayProtectionPlansPageTest extends BaseWebPageTest {

    @Override
    public void setUp() {
	super.setUp();
	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
	GSPPlanService gspPlanService = EasyMock.createMock(GSPPlanService.class);
	mockApplicationContext.putBean("gspPlanService", gspPlanService);
	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(DisplayProtectionPlansPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(DisplayProtectionPlansPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Ignore
    @Override
    public void testRenderMyPage() {
	// start and render the test page
	tester.startPage(DisplayProtectionPlansPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(DisplayProtectionPlansPage.class);
	tester.assertNoErrorMessage();

    }

}
