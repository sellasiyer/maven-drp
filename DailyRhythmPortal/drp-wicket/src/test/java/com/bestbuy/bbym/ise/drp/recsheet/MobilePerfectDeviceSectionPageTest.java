package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

public class MobilePerfectDeviceSectionPageTest extends BaseNavPageTest {

    private CustomerService customerService;

    @Override
    public void setUp() {
	super.setUp();
	MobileSalesWorkspaceService mobileSalesWorkspaceService = EasyMock
		.createMock(MobileSalesWorkspaceService.class);
	mockApplicationContext.putBean("mobileSalesWorkspaceService", mobileSalesWorkspaceService);

	customerService = EasyMock.createMock(CustomerService.class);
	mockApplicationContext.putBean("customerService", customerService);

    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(MobilePerfectDeviceSectionPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(MobilePerfectDeviceSectionPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// start and render the test page
	tester.startPage(MobilePerfectDeviceSectionPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(MobilePerfectDeviceSectionPage.class);
	tester.assertNoErrorMessage();

    }

}
