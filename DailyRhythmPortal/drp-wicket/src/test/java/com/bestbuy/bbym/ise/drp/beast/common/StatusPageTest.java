package com.bestbuy.bbym.ise.drp.beast.common;

import static org.junit.Assert.assertTrue;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link StatusPage}.
 */
public class StatusPageTest extends BaseWebPageTest {

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(StatusPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(StatusPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
		DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM, DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {

	final String exitStatus = "someExitStatus";

	// start and render the test page
	PageParameters pageParameters = new PageParameters();
	pageParameters.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), exitStatus);
	tester.startPage(StatusPage.class, pageParameters);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(StatusPage.class);

	tester.assertContains("window.status='" + exitStatus + "';");
    }

    /**
     * Checks that the page was rendered and the Wicket session is invalidated
     * afterwards.
     */
    @Ignore
    @Test
    public void testSessionInvalidated() throws Exception {
	PageParameters pageParameters = new PageParameters();
	pageParameters.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "success");
	tester.startPage(StatusPage.class, pageParameters);
	tester.assertRenderedPage(StatusPage.class);
	tester.assertContains("window.status='");
	assertTrue("Session should have been invalidated", tester.getSession().isSessionInvalidated());
    }

}
