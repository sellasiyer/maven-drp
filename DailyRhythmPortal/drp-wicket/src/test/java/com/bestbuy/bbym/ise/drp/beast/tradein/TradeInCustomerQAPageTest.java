package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link TradeInCustomerQAPage}.
 */
public class TradeInCustomerQAPageTest extends BaseWebPageTest {

    public void setUp() {
	super.setUp();

	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);

    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(TradeInCustomerQAPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(TradeInCustomerQAPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {
	TradeInCustomerQAPage page = new TradeInCustomerQAPage(new PageParameters());
	tester.startPage(page);

	tester.assertRenderedPage(TradeInCustomerQAPage.class);
	tester.assertNoErrorMessage();
    }

    @Test(expected = RuntimeException.class)
    public void testRenderMyPageNullPointer() {
	TradeInCustomerQAPage page = new TradeInCustomerQAPage(null);
	tester.startPage(page);

	tester.assertRenderedPage(TradeInCustomerQAPage.class);
	tester.assertNoErrorMessage();
    }

}
