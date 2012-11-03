package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.TradeInService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link TradeInTradabilityCheck}.
 */
public class TradeInTradabilityCheckTest extends BaseWebPageTest {

    private TradeInService tradeInService;

    @Override
    public void setUp() {
	super.setUp();
	tradeInService = EasyMock.createMock(TradeInService.class);
	mockApplicationContext.putBean("tradeInService", tradeInService);
	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(TradeInTradabilityCheck.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(TradeInTradabilityCheck.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() {

    }

}
