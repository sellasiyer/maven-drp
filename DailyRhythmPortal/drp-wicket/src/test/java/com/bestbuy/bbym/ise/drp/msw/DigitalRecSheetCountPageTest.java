package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.Before;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link DigitalRecSheetCountPage}.
 */
public class DigitalRecSheetCountPageTest extends BaseNavPageTest {

    @Before
    public void setUp() {
	super.setUp();

	MobileSalesWorkspaceService mobileSalesWorkspaceService = EasyMock
		.createMock(MobileSalesWorkspaceService.class);
	mockApplicationContext.putBean("mobileSalesWorkspaceService", mobileSalesWorkspaceService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_ADMIN} and
     * {@link DrpConstants#DRP_MANAGER} roles have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(DigitalRecSheetCountPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER);
	assertAccessDenied(DigitalRecSheetCountPage.class, DrpConstants.DRP_LEAD, DrpConstants.DRP_USER,
		DrpConstants.SHP_USER, DrpConstants.DRP_TEAM, DrpConstants.DRP_BEAST, DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() throws Exception {

	setDrpUser(DrpUserFactory.getDrpUser());

	// start and render the test page
	tester.startPage(DigitalRecSheetCountPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(DigitalRecSheetCountPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();

	tester.assertComponent("digitalRecSheetCountPanel", DigitalRecSheetCountPanel.class);
    }

}
