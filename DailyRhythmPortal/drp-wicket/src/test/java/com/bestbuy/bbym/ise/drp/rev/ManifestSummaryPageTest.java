package com.bestbuy.bbym.ise.drp.rev;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestFactory;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link ManifestSummaryPage}.
 */
public class ManifestSummaryPageTest extends BaseNavPageTest {

    private ShippingService shippingService;

    @Override
    public void setUp() {
	super.setUp();
	shippingService = EasyMock.createMock(ShippingService.class);
	mockApplicationContext.putBean("shippingService", shippingService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_ADMIN},
     * {@link DrpConstants#SHP_USER} and  {@link DrpConstants#SHP_MANAGER} roles have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(ManifestSummaryPage.class, DrpConstants.DRP_ADMIN, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
	assertAccessDenied(ManifestSummaryPage.class, DrpConstants.DRP_BEAST, DrpConstants.DRP_LEAD,
		DrpConstants.DRP_MANAGER, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() throws Exception {

	final Manifest manifest = ManifestFactory.getManifest();

	EasyMock.expect(shippingService.generateManifestDoc(manifest.getManifestID(), null)).andReturn(null);
	EasyMock.expect(shippingService.generateManifestDoc(manifest.getManifestID(), null)).andReturn(null);
	EasyMock.replay(shippingService);

	// start and render the test page
	ManifestSummaryPage page = new ManifestSummaryPage(manifest);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(ManifestSummaryPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();
    }

}
