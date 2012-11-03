package com.bestbuy.bbym.ise.drp.recsheet;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link MACServicesAddOptionsPage}.
 */
public class MACServicesAddOptionsPageTest extends BaseNavPageTest {

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
	assertAccessAllowed(MACServicesAddOptionsPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(MACServicesAddOptionsPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// start and render the test page
	tester.startPage(MACServicesAddOptionsPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(MACServicesAddOptionsPage.class);
	tester.assertNoErrorMessage();

    }

}
