package com.bestbuy.bbym.ise.drp.triage2;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isNull;
import static org.easymock.EasyMock.notNull;
import static org.easymock.EasyMock.replay;

import java.util.ArrayList;

import org.junit.Test;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.common.BaseNavPageTest;
import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageRecommendation;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.drp.service.YGIBService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

/**
 * JUnit test for {@link DashboardPage}.
 */
public class DashboardPageTest extends BaseNavPageTest {

    private TriageService triageService;
    private YGIBService ygibService;

    @Override
    public void setUp() {
	super.setUp();
	triageService = createMock(TriageService.class);
	mockApplicationContext.putBean("triageService", triageService);
	ygibService = createMock(YGIBService.class);
	mockApplicationContext.putBean("ygibService", ygibService);
	mockApplicationContext.putBean("ygibService1", ygibService);
    }

    /**
     * Checks that all roles except {@link DrpConstants#DRP_BEAST},
     * {@link DrpConstants#SHP_USER} and {@link DrpConstants#SHP_MANAGER} have access to the page.
     */
    @Override
    @Test
    public void testPageAccess() {
	assertAccessAllowed(DashboardPage.class, DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER,
		DrpConstants.DRP_LEAD, DrpConstants.DRP_USER, DrpConstants.DRP_TEAM);
	assertAccessDenied(DashboardPage.class, DrpConstants.DRP_BEAST, DrpConstants.SHP_USER, DrpConstants.SHP_MANAGER);
    }

    @Override
    @Test
    public void testRenderMyPage() throws Exception {
	TriageRecommendation rcmd = new TriageRecommendation();
	expect(triageService.getRecommendation(notNull(TriageIssue.class))).andReturn(rcmd);
	expect(triageService.getActions(eq(rcmd))).andReturn(new ArrayList<TriageAction>());
	expect(triageService.getResolutionList()).andReturn(new ArrayList<TriageResolution>());
	expect(triageService.getArticles(isNull(String.class), isNull(String.class), isNull(Carrier.class))).andReturn(
		new ArrayList());
	expect(triageService.getTriageHistoryBySerialNumber(isNull(String.class))).andReturn(new ArrayList());
	replay(triageService);

	expect(getMockDrpPropertyService().getProperty(eq("TRIAGE_CHECK_OPTIONS_BUTTON_ENABLED"), eq("TRUE")))
		.andReturn("true").anyTimes();
	replay(getMockDrpPropertyService());

	expect(ygibService.processRequest(notNull(YGIBDevice.class))).andReturn(new YGIBResponse());
	replay(ygibService);

	tester.startPage(DashboardPage.class);
	tester.assertRenderedPage(DashboardPage.class);
    }

}
