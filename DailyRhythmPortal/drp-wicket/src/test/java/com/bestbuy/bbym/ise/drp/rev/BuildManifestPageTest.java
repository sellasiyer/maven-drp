package com.bestbuy.bbym.ise.drp.rev;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link BuildManifestPage}.
 */
public class BuildManifestPageTest extends BaseNavPageTest {

    private ShippingService shippingService;

    @Override
    public void setUp() {
	super.setUp();
	shippingService = EasyMock.createMock(ShippingService.class);
	mockApplicationContext.putBean("shippingService", shippingService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_ADMIN},
     * {@link DrpConstants#SHP_USER} and {@link DrpConstants#SHP_MANAGER} roles
     * have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(BuildManifestPage.class, DrpConstants.DRP_ADMIN, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
	assertAccessDenied(BuildManifestPage.class, DrpConstants.DRP_BEAST, DrpConstants.DRP_LEAD,
		DrpConstants.DRP_TEAM, DrpConstants.DRP_USER, DrpConstants.DRP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// start and render the test page
	BuildManifestPage page = new BuildManifestPage(null, null);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(BuildManifestPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();
    }

}
