package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.PriceQAQuestionsFactory;
import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link TradeInConditionPage}.
 */
public class TradeInConditionPageTest extends BaseWebPageTest {

    @Override
    public void setUp() {
	super.setUp();
	UIService uiService = EasyMock.createMock(UIService.class);
	mockApplicationContext.putBean("uiService", uiService);

	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);

	setPriceQAQuestions(PriceQAQuestionsFactory.getDummyPriceQAQuestions());
	UIDataItem titanDataItem = new UIDataItem();
	setTitanDataItem(titanDataItem);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(TradeInConditionPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(TradeInConditionPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * This method tests to make sure the page rendered
     */
    @Override
    public void testRenderMyPage() throws Exception {
	TradeInConditionPage page = new TradeInConditionPage(null, new UIReply());
	tester.startPage(page);

	tester.assertRenderedPage(TradeInConditionPage.class);
	tester.assertNoErrorMessage();
    }

    /**
     * This method tests for null UIReply
     */
    @Test
    public void testRenderMyPageNullPointer() {
	TradeInConditionPage page = new TradeInConditionPage(null, null);
	tester.startPage(page);

	tester.assertRenderedPage(TradeInConditionPage.class);
	tester.assertNoErrorMessage();
    }

}
