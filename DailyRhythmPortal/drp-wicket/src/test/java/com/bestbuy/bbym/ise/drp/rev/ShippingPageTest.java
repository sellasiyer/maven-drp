package com.bestbuy.bbym.ise.drp.rev;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link ShippingPage}.
 */
public class ShippingPageTest extends BaseNavPageTest {

    private ShippingService shippingService;

    @Override
    public void setUp() {
	super.setUp();
	shippingService = EasyMock.createMock(ShippingService.class);
	mockApplicationContext.putBean("shippingService", shippingService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_ADMIN}, {@link DrpConstants#SHP_MANAGER} and
     * {@link DrpConstants#SHP_USER} roles have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(ShippingPage.class, DrpConstants.DRP_ADMIN, DrpConstants.SHP_MANAGER, DrpConstants.SHP_USER);
	assertAccessDenied(ShippingPage.class, DrpConstants.DRP_BEAST, DrpConstants.DRP_LEAD, DrpConstants.DRP_TEAM,
		DrpConstants.DRP_MANAGER, DrpConstants.DRP_USER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// TODO Implement me!!
    }

}
