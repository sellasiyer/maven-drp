package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link LoadGiftCardPage}
 */
public class LoadGiftCardPageTest extends BaseWebPageTest {

    @Override
    public void setUp() {
	super.setUp();
	UIService uiService = EasyMock.createMock(UIService.class);
	mockApplicationContext.putBean("uiService", uiService);

	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);

	UIDataItem titanDataItem = new UIDataItem();
	setTitanDataItem(titanDataItem);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(LoadGiftCardPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(LoadGiftCardPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {
	// TODO When services are complete

    }

}
