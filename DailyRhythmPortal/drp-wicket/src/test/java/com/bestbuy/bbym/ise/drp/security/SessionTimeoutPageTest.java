package com.bestbuy.bbym.ise.drp.security;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link SessionTimeoutPage}.
 */
public class SessionTimeoutPageTest extends BaseWebPageTest {

    /**
     * Checks that all roles have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(SessionTimeoutPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.DRP_BEAST, DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	tester.startPage(SessionTimeoutPage.class);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(SessionTimeoutPage.class);
	tester.assertNoErrorMessage();
    }

}
