package com.bestbuy.bbym.ise.drp.loanerphone;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link LoanerPhoneCheckInPage}.
 */
public class LoanerPhoneCheckInPageTest extends BaseNavPageTest {

    @Override
    public void setUp() {
	super.setUp();
	LoanerPhoneService loanerPhoneService = EasyMock.createMock(LoanerPhoneService.class);
	mockApplicationContext.putBean("loanerPhoneService", loanerPhoneService);
	CustomerDataService customerDataService = EasyMock.createMock(CustomerDataService.class);
	mockApplicationContext.putBean("customerDataService", customerDataService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(LoanerPhoneCheckInPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(LoanerPhoneCheckInPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

	// start and render the test page
	LoanerPhoneCheckInPage page = new LoanerPhoneCheckInPage(null, null, null);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(LoanerPhoneCheckInPage.class);
	tester.assertNoErrorMessage();
    }

}
