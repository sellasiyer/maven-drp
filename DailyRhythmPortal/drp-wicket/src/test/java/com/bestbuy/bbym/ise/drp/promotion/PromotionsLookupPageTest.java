package com.bestbuy.bbym.ise.drp.promotion;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.service.PromotionService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link PromotionsLookupPage}.
 */
public class PromotionsLookupPageTest extends BaseNavPageTest {

    private PromotionService promotionService;

    @Override
    public void setUp() {
	super.setUp();
	promotionService = EasyMock.createMock(PromotionService.class);
	mockApplicationContext.putBean("promotionService", promotionService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST} and
     * {@link DrpConstants#SHP_USER} have access to the page.
     */
    @Override
    public void testPageAccess() {

	assertAccessAllowed(PromotionsLookupPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(PromotionsLookupPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER,
		DrpConstants.SHP_MANAGER);

    }

    @Override
    public void testRenderMyPage() throws Exception {
	tester.startPage(PromotionsLookupPage.class, new PageParameters());
	tester.assertRenderedPage(PromotionsLookupPage.class);
	tester.assertNoErrorMessage();

	assertNavigation();

    }

}
