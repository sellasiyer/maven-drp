package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.util.HashMap;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.easymock.EasyMock;

import com.bestbuy.bbym.ise.drp.common.BaseWebPageTest;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.SchedulerService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link ChooseAppointmentPage}.
 */
public class ChooseAppointmentPageTest extends BaseWebPageTest {

    private SchedulerService mockSchedulerService;

    @Override
    public void setUp() {
	super.setUp();

	mockSchedulerService = EasyMock.createMock(SchedulerService.class);
	mockApplicationContext.putBean("schedulerService", mockSchedulerService);
	DrpPropertyService drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
	mockApplicationContext.putBean("drpPropertyService", drpPropertyService);
    }

    /**
     * Checks that only {@link DrpConstants#DRP_BEAST} role has access to the
     * page.
     */
    @Override
    public void testPageAccess() {
	assertAccessAllowed(ChooseAppointmentPage.class, DrpConstants.DRP_BEAST);
	assertAccessDenied(ChooseAppointmentPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.SHP_USER, DrpConstants.DRP_TEAM,
		DrpConstants.SHP_MANAGER);
    }

    @Override
    public void testRenderMyPage() throws Exception {

	DrpUser user = DrpUserFactory.getDrpUser();
	setDrpUser(user);

	EasyMock.expect(mockSchedulerService.getDepartments(user.getStoreId()))
		.andReturn(new HashMap<String, String>());
	EasyMock.replay(mockSchedulerService);

	// start and render the test page
	PageParameters pageParameters = new PageParameters();
	tester.startPage(ChooseAppointmentPage.class, pageParameters);

	// Check that the right page was rendered (no unexpected redirect or
	// intercept)
	tester.assertRenderedPage(ChooseAppointmentPage.class);
	tester.assertNoErrorMessage();
    }

}
