package com.bestbuy.bbym.ise.drp.error;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link DRPWorkflowErrorPage}.
 */
public class DRPWorkflowErrorPageTest extends BaseNavPageTest {

    /**
     * Checks that all roles except have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(DRPWorkflowErrorPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_MANAGER, DrpConstants.SHP_USER,
		DrpConstants.DRP_TEAM, DrpConstants.DRP_BEAST);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// TODO IMplement me!
    }

}
