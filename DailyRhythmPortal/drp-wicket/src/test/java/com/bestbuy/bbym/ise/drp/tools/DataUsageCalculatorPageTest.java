package com.bestbuy.bbym.ise.drp.tools;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link DataUsageCalculatorPage}.
 */
public class DataUsageCalculatorPageTest extends BaseNavPageTest {

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST},
     * {@link DrpConstants#SHP_USER} and {@link DrpConstants#SHP_MANAGER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(DataUsageCalculatorPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(DataUsageCalculatorPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// TODO Implement me!
    }

}
