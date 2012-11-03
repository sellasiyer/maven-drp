package com.bestbuy.bbym.ise.drp.common;

import static org.junit.Assert.fail;

import org.apache.wicket.Page;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.IRoleCheckingStrategy;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AnnotationsRoleAuthorizationStrategy;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.apache.wicket.spring.test.ApplicationContextMock;
import org.apache.wicket.util.tester.WicketTester;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalApplication;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.PriceQAQuestions;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.security.AccessDeniedPage;
import com.bestbuy.bbym.ise.drp.service.AuthenticationService;

/**
 * Base class for all web page tests.
 * <p>
 * Sets up a mock Spring application context.
 * </p>
 */
public abstract class BaseWebPageTest {

    protected WicketTester tester;
    protected ApplicationContextMock mockApplicationContext;

    /**
     * Sets up a mock Spring application context.
     */
    @Before
    public void setUp() {
	DailyRhythmPortalApplication webApp = new DailyRhythmPortalApplication() {

	    @Override
	    public void init() {
		mockApplicationContext = new ApplicationContextMock();
		getComponentInstantiationListeners().add(
			new SpringComponentInjector(this, mockApplicationContext, false));
	    }

	    @Override
	    public AuthenticationService getAuthenticator() {
		return EasyMock.createMock(AuthenticationService.class);
	    }

	};
	tester = new WicketTester(webApp);
    }

    /**
     * Checks page access authorization.
     * <p>
     * This has been made abstract to ensure that we don't forget to ensure that
     * the page access has been set correctly in the Java code. At the moment we
     * grant access to almost all roles in the base page and explicity restrict
     * access when needed. We may need to revisit this since this approach even
     * results in the {@link AccessDeniedPage} having restricted access.
     * </p>
     * 
     * @see #assertAccessAllowed(Class, String...)
     * @see #assertAccessDenied(Class, String...)
     */
    @Test
    public abstract void testPageAccess();

    /**
     * Tests rendering of page and presence of components.
     * <p>
     * At a bare minimum we should at least be testing that all pages can be
     * rendered (even if we're not checking the page contents).
     * </p>
     */
    @Test
    public abstract void testRenderMyPage() throws Exception;

    /**
     * Asserts that all the given roles have access to the page.
     * 
     * @param pageClass
     *            the class of the page being checked
     * @param roles
     *            all the roles that should be allowed access to the page
     */
    protected final void assertAccessAllowed(Class<? extends Page> pageClass, final String... roles) {
	for(final String role: roles){
	    IAuthorizationStrategy authorizationStrategy = new MockAnnotationsRoleAuthorizationStrategy(role);
	    if (!authorizationStrategy.isInstantiationAuthorized(pageClass)){
		fail("Page access not allowed for role " + role);
	    }
	}
    }

    /**
     * Asserts that all the given roles do not have access to the page.
     * 
     * @param pageClass
     *            the class of the page being checked
     * @param roles
     *            all the roles that should not be allowed access to the page
     */
    protected final void assertAccessDenied(Class<? extends Page> pageClass, final String... roles) {
	for(final String role: roles){
	    IAuthorizationStrategy authorizationStrategy = new MockAnnotationsRoleAuthorizationStrategy(role);
	    if (authorizationStrategy.isInstantiationAuthorized(pageClass)){
		fail("Page access should be denied for role " + role);
	    }
	}
    }

    private class MockAnnotationsRoleAuthorizationStrategy extends AnnotationsRoleAuthorizationStrategy {

	MockAnnotationsRoleAuthorizationStrategy(final String allowedRole) {
	    super(new IRoleCheckingStrategy() {

		public boolean hasAnyRole(Roles roles) {
		    return roles.hasRole(allowedRole);
		}
	    });
	}
    }

    /**
     * Gets the DRP session.
     */
    protected final DailyRhythmPortalSession getCurrentDailyRhythmPortalSession() {
	DailyRhythmPortalSession drpSession = (DailyRhythmPortalSession) tester.getSession();
	return drpSession;
    }

    /**
     * Sets the given user in the session.
     */
    protected final void setDrpUser(DrpUser drpUser) {
	getCurrentDailyRhythmPortalSession().setDrpUser(drpUser);
    }

    protected final void setProtectionPlanDetails(ProtectionPlan protectionPlanDetails) {
	getCurrentDailyRhythmPortalSession().setProtectionPlanDetails(protectionPlanDetails);
    }

    protected final void setPriceQAQuestions(PriceQAQuestions priceQAQuestions) {
	getCurrentDailyRhythmPortalSession().setPriceQAQuestions(priceQAQuestions);
    }

    protected final void setTitanDataItem(UIDataItem titanDataItem) {
	getCurrentDailyRhythmPortalSession().setTitanDataItem(titanDataItem);

    }

    protected final void setTitanDevice(TitanDevice titanDevice) {
	getCurrentDailyRhythmPortalSession().setTitanDevice(titanDevice);
    }

}
