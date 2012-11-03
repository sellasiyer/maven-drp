package com.bestbuy.bbym.ise.drp.triage;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link TriagePage}.
 */
public class TriagePageTest extends BaseNavPageTest {

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} {@link DrpConstants#SHP_MANAGER} and {@link DrpConstants#SHP_USER} have access
     * to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(TriagePage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
		DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(TriagePage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_MANAGER, DrpConstants.SHP_USER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// TODO IMplement me!
    }
}
