package com.bestbuy.bbym.ise.drp.security;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link RuntimeErrorPage}.
 */
public class RuntimeErrorPageTest extends BaseNavPageTest {

    /**
     * Checks that all roles except have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(RuntimeErrorPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.DRP_BEAST, DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// start and render the test page
	tester.startPage(RuntimeErrorPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(RuntimeErrorPage.class);
	tester.assertNoErrorMessage();
    }

}
