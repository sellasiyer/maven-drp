package com.bestbuy.bbym.ise.drp.beast.tradein;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link CatalogSearchPage}.
 */
public class CatalogSearchPageTest extends BaseWebPageTest {

    @Override
    public void setUp() {
	super.setUp();
	UIService uiService = EasyMock.createMock(UIService.class);
	mockApplicationContext.putBean("uiService", uiService);
	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(CatalogSearchPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(CatalogSearchPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {

	setDrpUser(DrpUserFactory.getDrpUser());

	CatalogSearchPage page = new CatalogSearchPage(new PageParameters());
	tester.startPage(page);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(CatalogSearchPage.class);
	tester.assertNoErrorMessage();
    }

}
