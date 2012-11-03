package com.bestbuy.bbym.ise.drp.loanerphone;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link EditPage}.
 */
public class EditPageTest extends BaseNavPageTest {

    /**
     * Checks that only {@link DrpConstants#DRP_ADMIN},
     * {@link DrpConstants#DRP_MANAGER} and {@link DrpConstants#DRP_LEAD} roles
     * have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(EditPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD);
	assertAccessDenied(EditPage.class, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.DRP_BEAST, DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// TODO Implement me!!
    }

}
