package com.bestbuy.bbym.ise.drp.promotion;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.PromotionService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link RegistrationPage}.
 */
public class RegistrationPageTest extends BaseNavPageTest {

    private PromotionService promotionService;

    @Override
    public void setUp() {
	super.setUp();
	promotionService = EasyMock.createMock(PromotionService.class);
	mockApplicationContext.putBean("promotionService", promotionService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(RegistrationPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(RegistrationPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// start and render the test page
	RegistrationPage page = new RegistrationPage(null, null);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(RegistrationPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();
    }

}
