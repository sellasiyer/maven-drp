package com.bestbuy.bbym.ise.drp.tools;

import javax.sql.DataSource;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link DRPReportsPage}.
 */
public class DRPReportsPageTest extends BaseNavPageTest {

    private DataSource datasource;

    @Override
    public void setUp() {
	super.setUp();
	datasource = EasyMock.createMock(DataSource.class);
	mockApplicationContext.putBean("datasource", datasource);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} {@link DrpConstants#SHP_MANAGER} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(DRPReportsPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(DRPReportsPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_MANAGER,
		DrpConstants.SHP_USER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	// start and render the test page
	tester.startPage(DRPReportsPage.class, new PageParameters());

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(DRPReportsPage.class);
	tester.assertNoErrorMessage();

    }

    @Test
    public void testonSubmit() {

	tester.startPage(DRPReportsPage.class, new PageParameters());

    }

}
