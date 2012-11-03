package com.bestbuy.bbym.ise.drp.security;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link SignInPage}.
 */
public class SignInPageTest extends BaseWebPageTest {

    @Override
    public void setUp() {
	super.setUp();
	DrpPropertyService propertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", propertyService);
    }

    /**
     * Checks that all roles have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(SignInPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
		DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM, DrpConstants.DRP_BEAST,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	// start and render the test page
	tester.startPage(SignInPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(SignInPage.class);
	tester.assertNoErrorMessage();

    }

}
