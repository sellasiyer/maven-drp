package com.bestbuy.bbym.ise.drp.entitlement;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link EntitlementSummaryPrintPage}.
 */
public class EntitlementSummaryPrintPageTest extends BaseWebPageTest {

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(EntitlementSummaryPrintPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(EntitlementSummaryPrintPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {
	// TODO Implement me!
    }
}
