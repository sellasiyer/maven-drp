package com.bestbuy.bbym.ise.drp.beast.scheduler;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link ViewAppointmentConfirmationPage}.
 */
public class ViewAppointmentConfirmationPageTest extends BaseWebPageTest {

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(ViewAppointmentConfirmationPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(ViewAppointmentConfirmationPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {
	// TODO Implement me!
    }
}
