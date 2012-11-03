package com.bestbuy.bbym.ise.drp.tools;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link BuybaculatorPage}.
 */
public class BuybaculatorPageTest extends BaseNavPageTest {

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST},
     * {@link DrpConstants#SHP_USER} and {@link DrpConstants#SHP_MANAGER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(BuybaculatorPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(BuybaculatorPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	// start and render the test page
	tester.startPage(BuybaculatorPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(BuybaculatorPage.class);
	tester.assertNoErrorMessage();

	tester.assertComponent("buybaculatorPanel", BuybaculatorPanel.class);
    }

}
