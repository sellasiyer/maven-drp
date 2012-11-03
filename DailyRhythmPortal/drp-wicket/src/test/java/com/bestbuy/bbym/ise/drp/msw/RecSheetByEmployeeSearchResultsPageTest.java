package com.bestbuy.bbym.ise.drp.msw;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link RecSheetByEmployeeSearchResultsPage}.
 */
public class RecSheetByEmployeeSearchResultsPageTest extends BaseNavPageTest {

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(RecSheetByEmployeeSearchResultsPage.class, DrpConstants.DRP_ADMIN,
		DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(RecSheetByEmployeeSearchResultsPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// TODO Implement me!!
    }
}
