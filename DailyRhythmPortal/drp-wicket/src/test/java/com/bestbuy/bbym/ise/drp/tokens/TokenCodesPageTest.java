package com.bestbuy.bbym.ise.drp.tokens;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.CarrierDataService;
import com.bestbuy.bbym.ise.drp.tokencode.TokenCodesPage;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link TokenCodesPage}.
 */
public class TokenCodesPageTest extends BaseNavPageTest {

    private CarrierDataService mockCarrierDataService;

    @Override
    public void setUp() {
	super.setUp();
	mockCarrierDataService = EasyMock.createMock(CarrierDataService.class);
	mockApplicationContext.putBean("tokenCodesService", mockCarrierDataService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST},
     * {@link DrpConstants#SHP_USER} and {@link DrpConstants#SHP_MANAGER} have access to the page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(TokenCodesPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(TokenCodesPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {
	//TO-DO
	// start and render the test page
	/*PageParameters pageParameters = new PageParameters();
	TokenCodesPage tokenCodesPage = new TokenCodesPage(pageParameters, TokenCodesPage.Carrier.SPRINT);

	tester.startPage(TokenCodesPage.class, pageParameters);

	tester.assertRenderedPage(TokenCodesPage.class);
	tester.assertNoErrorMessage();*/
    }

}
