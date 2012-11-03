package com.bestbuy.bbym.ise.drp.admin;

import java.util.ArrayList;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.drp.utils.LoggerUtilServiceImpl;

/**
 * JUnit test for {@link ManagePropertiesPage}.
 */
public class ManagePropertiesPageTest extends BaseNavPageTest {

    @Override
    public void setUp() {
	super.setUp();
	LoggerUtilServiceImpl loggerUtilService = EasyMock.createMock(LoggerUtilServiceImpl.class);
	mockApplicationContext.putBean("loggerUtilService", loggerUtilService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_ADMIN} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(ManagePropertiesPage.class, DrpConstants.DRP_ADMIN);
	assertAccessDenied(ManagePropertiesPage.class, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
		DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM, DrpConstants.DRP_BEAST,
		DrpConstants.SHP_MANAGER);
    }

    /**
     * Tests rendering of page and presence of components.
     */
    @Override
    public void testRenderMyPage() throws Exception {

	setDrpUser(DrpUserFactory.getDrpUser());

	DrpPropertyService mockDrpPropertyService = getMockDrpPropertyService();
	EasyMock.expect(mockDrpPropertyService.getAllProperties()).andReturn(new ArrayList<DrpProperty>());
	EasyMock.replay(mockDrpPropertyService);

	tester.startPage(ManagePropertiesPage.class, new PageParameters());
	tester.assertNoErrorMessage();
	tester.assertRenderedPage(ManagePropertiesPage.class);
	tester.assertNoErrorMessage();
	assertNavigation();

    }

}
