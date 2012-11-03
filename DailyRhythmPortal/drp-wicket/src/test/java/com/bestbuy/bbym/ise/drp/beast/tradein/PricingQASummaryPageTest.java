package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.PriceQAQuestionsFactory;
import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.HotlinkService;
import com.bestbuy.bbym.ise.drp.service.TradeInService;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link PricingQASummaryPage}.
 */
public class PricingQASummaryPageTest extends BaseWebPageTest {

    @Override
    public void setUp() {
	super.setUp();

	HotlinkService hotLinkService = EasyMock.createMock(HotlinkService.class);
	mockApplicationContext.putBean("hotLinkService", hotLinkService);

	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);

	UIService uiService = EasyMock.createMock(UIService.class);
	mockApplicationContext.putBean("uiService", uiService);

	TradeInService tradeInService = EasyMock.createMock(TradeInService.class);
	mockApplicationContext.putBean("tradeInService", tradeInService);

	setPriceQAQuestions(PriceQAQuestionsFactory.getDummyPriceQAQuestions());

	UIDataItem titanDataItem = new UIDataItem();
	setTitanDataItem(titanDataItem);

	TitanDevice titanDevice = new TitanDevice();
	setTitanDevice(titanDevice);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(PricingQASummaryPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(PricingQASummaryPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {
	PageParameters pageParameters = new PageParameters();

	PricingQASummaryPage page = new PricingQASummaryPage(pageParameters);
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(PricingQASummaryPage.class);
	tester.assertNoErrorMessage();

    }

}
