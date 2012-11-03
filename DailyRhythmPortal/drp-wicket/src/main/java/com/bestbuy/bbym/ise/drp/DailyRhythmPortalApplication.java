package com.bestbuy.bbym.ise.drp;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.jar.Manifest;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.DefaultPageManagerProvider;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.RuntimeConfigurationType;
import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authentication.AuthenticatedWebApplication;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.behavior.IBehaviorListener;
import org.apache.wicket.event.IEvent;
import org.apache.wicket.feedback.IFeedback;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.page.DefaultPageManagerContext;
import org.apache.wicket.pageStore.IDataStore;
import org.apache.wicket.pageStore.memory.HttpSessionDataStore;
import org.apache.wicket.pageStore.memory.PageNumberEvictionStrategy;
import org.apache.wicket.protocol.http.IRequestLogger;
import org.apache.wicket.protocol.http.PageExpiredException;
import org.apache.wicket.protocol.http.RequestLogger;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.protocol.http.servlet.ServletWebResponse;
import org.apache.wicket.request.IRequestHandler;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;
import org.apache.wicket.request.cycle.AbstractRequestCycleListener;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.PageProvider;
import org.apache.wicket.request.handler.RenderPageRequestHandler;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.resource.SharedResourceReference;
import org.apache.wicket.session.ISessionStore.UnboundListener;
import org.apache.wicket.spring.injection.annot.SpringComponentInjector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bestbuy.bbym.ise.drp.admin.ManagePropertiesPage;
import com.bestbuy.bbym.ise.drp.admin.hotlinks.ManageHotlinksPage;
import com.bestbuy.bbym.ise.drp.beast.accessories.DisplayAccessories;
import com.bestbuy.bbym.ise.drp.beast.gspcancel.CustomerLookupPage;
import com.bestbuy.bbym.ise.drp.beast.tradein.DoNotBuyListPage;
import com.bestbuy.bbym.ise.drp.beast.tradein.EditCustomerBasicInfoPage;
import com.bestbuy.bbym.ise.drp.beast.tradein.EditProductInfoPage;
import com.bestbuy.bbym.ise.drp.beast.tradein.TradeInTradabilityCheck;
import com.bestbuy.bbym.ise.drp.common.HomePage;
import com.bestbuy.bbym.ise.drp.common.TimeoutInfo;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardSearchPage;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.error.IseRuntimeException;
import com.bestbuy.bbym.ise.drp.loanerphone.LoanerPhoneLandingPage;
import com.bestbuy.bbym.ise.drp.msw.CTRecSheetPage;
import com.bestbuy.bbym.ise.drp.msw.DigitalRecSheetCountPage;
import com.bestbuy.bbym.ise.drp.msw.MobileSalesWorkspacePage;
import com.bestbuy.bbym.ise.drp.msw.RecSheetByEmployeePage;
import com.bestbuy.bbym.ise.drp.promotion.PromotionsLookupPage;
import com.bestbuy.bbym.ise.drp.rev.BuildManifestPage;
import com.bestbuy.bbym.ise.drp.rev.ManifestSummaryPage;
import com.bestbuy.bbym.ise.drp.rev.ShippingPage;
import com.bestbuy.bbym.ise.drp.sao.OpenSSOSao;
import com.bestbuy.bbym.ise.drp.scoreboard.ScoreboardPage;
import com.bestbuy.bbym.ise.drp.security.AccessDeniedPage;
import com.bestbuy.bbym.ise.drp.security.RuntimeErrorPage;
import com.bestbuy.bbym.ise.drp.security.SessionTimeoutPage;
import com.bestbuy.bbym.ise.drp.security.SignInPage;
import com.bestbuy.bbym.ise.drp.service.AuthenticationService;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.tokencode.TokenCodesPage;
import com.bestbuy.bbym.ise.drp.tools.DataUsageCalculatorPage;
import com.bestbuy.bbym.ise.drp.triage2.DashboardPage;
import com.bestbuy.bbym.ise.drp.triage2.IssuesPage;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.drp.utils.LoggerUtilSerivce;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

/**
 * Application object for your web application. If you want to run this
 * application without deploying, run the Start class.
 * 
 * @see com.bestbuy.bbym.ise.drp.common.Start#main(String[])
 */
public class DailyRhythmPortalApplication extends AuthenticatedWebApplication {

    private static Logger logger = LoggerFactory.getLogger(DailyRhythmPortalApplication.class);

    private String buildVersion = "";

    @Autowired
    private DrpPropertyService drpPropertyService;
    @Resource(name = "authenticationService")
    private AuthenticationService authenticator;
    @Autowired
    private LoggerUtilSerivce loggerUtilService;

    private final static String HOME_PAGE = "_HOME_PAGE";

    //SPLUNK MSG FORMATS
    //START
    private static final String SESSION_LOG_FORMAT = "SPLUNK FILTER Session data : Session ID={4},  Session Start Time={0},"
	    + " No. of Requests={1}, Session Total Time={2}, Session Size={3}, Session Info={5} ";
    private static final String REQUEST_LOG_FORMAT = "SPLUNK FILTER Request data : Request Start Time={0}, Request Total Time={1}, "
	    + "Request URL={2}, Origin URL={6}, Session Id={3}, Session Size={4}, Active Requests ={5} ";

    //END

    @Override
    public void init() {
	super.init();

	getResourceSettings().getStringResourceLoaders().add(0, new DatabaseStringResourceLoader(drpPropertyService));

	if (RuntimeConfigurationType.DEPLOYMENT != getConfigurationType()){
	    getRequestLoggerSettings().setRequestLoggerEnabled(true);
	}

	// add the spring component injector to enable annotation based
	// injection of Spring components on the web page components.
	getComponentInstantiationListeners().add(new SpringComponentInjector(this));
	getApplicationSettings().setPageExpiredErrorPage(SessionTimeoutPage.class);
	getApplicationSettings().setAccessDeniedPage(AccessDeniedPage.class);
	initBuildVersion();

	IBehaviorListener.INTERFACE.setIncludeRenderCount(false);

	loggerUtilService.setLog4jPath(getServletContext().getRealPath("WEB-INF/classes/log4j.xml"));
	loggerUtilService.createLog4jFromDB();

	getRequestCycleListeners().add(new AbstractRequestCycleListener() {

	    @Override
	    public void onBeginRequest(RequestCycle cycle) {
		try{
		    setSessionTimeOut(cycle);
		}catch(IseRuntimeException e){
		    logger.error("Could not load database session timeout overrides", e);
		}

		clearSessionInfoWhenComingFromEtk(cycle);
	    }

	    @Override
	    public IRequestHandler onException(RequestCycle cycle, Exception e) {
		if (e instanceof PageExpiredException){
		    return new RenderPageRequestHandler(new PageProvider(new SessionTimeoutPage()));
		}
		logger.error("RuntimeException " + e.getClass().getName(), e);
		return new RenderPageRequestHandler(new PageProvider(new RuntimeErrorPage()));
	    }

	    private void setSessionTimeOut(RequestCycle cycle) {

		if (drpPropertyService != null){
		    String sessionTimeOutStr = drpPropertyService.getProperty(DrpConstants.DFLT_SESSION_TIMEOUT, null);
		    int sessionTimeout = Util.getInt(sessionTimeOutStr, 0);
		    if (sessionTimeout > 0){
			logger.trace("Overriding web.xml session timeout with database property value: "
				+ sessionTimeout);
			ServletWebRequest servletWebRequest = (ServletWebRequest) cycle.getRequest();
			HttpServletRequest httpServletRequest = servletWebRequest.getContainerRequest();
			httpServletRequest.getSession().setMaxInactiveInterval(sessionTimeout);
		    }

		    String sessionTimeoutWarningStr = drpPropertyService.getProperty(
			    DrpConstants.DFLT_SESSION_TIMEOUT_WARNING, "600");
		    int sessionTimeoutWarning = Util.getInt(sessionTimeoutWarningStr, 0);
		    TimeoutInfo.getInstance().setWarningTime(sessionTimeoutWarning);
		}

	    }

	    /**
	     * When we're entering BEAST Portal via ETK widget and SSO we need
	     * to clear the session information. This is needed because IE
	     * shares the same HTTP session between all open browser instances.
	     */
	    private void clearSessionInfoWhenComingFromEtk(RequestCycle cycle) {
		ServletWebRequest servletWebRequest = (ServletWebRequest) cycle.getRequest();
		HttpServletRequest httpRequest = servletWebRequest.getContainerRequest();
		String referrer = httpRequest.getHeader("referer");
		logger.debug("referrer=" + referrer);
		if (referrer == null || referrer.trim().length() == 0){
		    logger.warn("Could not determine where we came from. Referrer is empty");
		}else{
		    if (referrer.toLowerCase().contains("bbyportal")){
			logger.info("Entering portal via ETK, clearing session");
			Session session = Session.get();
			if (session != null && (session instanceof DailyRhythmPortalSession)){
			    DailyRhythmPortalSession drpSession = (DailyRhythmPortalSession) session;
			    drpSession.clearCarrierCustomer();
			    drpSession.clearBestBuyCustomer();
			    drpSession.clearSearchCustomer();
			    drpSession.setSessionPropertyBoolean("needCustomer", false);
			}
		    }
		}
	    }
	});

	setPageManagerProvider(new DefaultPageManagerProvider(this) {

	    @Override
	    protected IDataStore newDataStore() {
		return new HttpSessionDataStore(new DefaultPageManagerContext(), new PageNumberEvictionStrategy(20));
	    }

	});
	getSessionStore().registerUnboundListener(new UnboundListener() {

	    @Override
	    public void sessionUnbound(String sessionId) {
		logger.trace("Time out for sessionId " + sessionId);

	    }
	});

	mountResource("/images", new SharedResourceReference("images"));

	mountPage("/accessDenied", AccessDeniedPage.class);
	mountPage("/login", SignInPage.class);
	mountPage("/customerDashboard", CustomerDashboardPage.class);
	mountPage("/customerDashboardSearch", CustomerDashboardSearchPage.class);
	mountPage("/dataUsageCalculator", DataUsageCalculatorPage.class);
	mountPage("/home", HomePage.class);
	mountPage("/loanerPhone", LoanerPhoneLandingPage.class);
	mountPage("/manageHotlinks", ManageHotlinksPage.class);
	mountPage("/manageProperties", ManagePropertiesPage.class);
	mountPage("/promotionsLookup", PromotionsLookupPage.class);
	mountPage("/recSheet", MobileSalesWorkspacePage.class);
	mountPage("/ctRecSheet", CTRecSheetPage.class);
	mountPage("/recSheetByEmployee", RecSheetByEmployeePage.class);
	mountPage("/recSheetCount", DigitalRecSheetCountPage.class);
	mountPage("/shipping", ShippingPage.class);
	mountPage("/shippingManifestSummary", ManifestSummaryPage.class);
	mountPage("/shippingBuildManifest", BuildManifestPage.class);
	mountPage("/tradeincheck", TradeInTradabilityCheck.class);
	mountPage("/triageIssues", IssuesPage.class);
	mountPage("/triageDashboard", DashboardPage.class);
	mountPage("/editProductAnswersPage", EditProductInfoPage.class);
	mountPage("/editCustomerBasicInfo", EditCustomerBasicInfoPage.class);
	mountPage("/mobileAccessories", DisplayAccessories.class);
	mountPage("/tokenCodes", TokenCodesPage.class);
	mountPage("/doNotBuyList", DoNotBuyListPage.class);
	mountPage("/scoreBoard", ScoreboardPage.class);
	// Use a constant for GSP since we reference this somewhere else in the app
	mountPage("/" + DrpConstants.GSP_CANCEL_BOOKMARK_VALUE, CustomerLookupPage.class);
    }

    /**
     * Gets the build version.
     */
    public String getBuildVersion() {
	return buildVersion;
    }

    /**
     * The build version is injected into the manifest file by Maven as supplied
     * by Jenkins at build time. Retrieve this value and remember it for display
     * purposes.
     */
    private void initBuildVersion() {
	try{
	    InputStream inputStream = getServletContext().getResourceAsStream("/META-INF/MANIFEST.MF");
	    Manifest manifest = new Manifest(inputStream);
	    if (manifest.getMainAttributes() != null){
		buildVersion = manifest.getMainAttributes().getValue("Implementation-Version");
	    }
	}catch(NullPointerException npe){
	    logger.warn("Failed to read build version from manifest", npe);
	    buildVersion = "Unknown";
	}catch(IOException e){
	    logger.warn("Failed to read build version from manifest", e);
	    buildVersion = "Unknown";
	}
    }

    @Override
    public Class<? extends Page> getHomePage() {
	return getRoleBasedLandingPage();
    }

    @Override
    public Session newSession(Request request, Response response) {
	DailyRhythmPortalSession session = new DailyRhythmPortalSession(request);
	if (session.getDrpUser() != null && session.getDrpUser().getOpenSsoTokenId() != null){
	    logger.info("Adding OpenSSO cookie");
	    Cookie cookie = new Cookie(OpenSSOSao.COOKIE_NAME_OPENSSO, session.getDrpUser().getOpenSsoTokenId());
	    WebResponse webResponse = (WebResponse) response;
	    HttpServletResponse servletResponse = (HttpServletResponse) webResponse.getContainerResponse();
	    servletResponse.addCookie(cookie);
	}
	return session;
    }

    @Override
    public Class<? extends WebPage> getSignInPageClass() {
	return SignInPage.class;
    }

    @Override
    protected Class<? extends AuthenticatedWebSession> getWebSessionClass() {
	return DailyRhythmPortalSession.class;
    }

    public static DailyRhythmPortalApplication get() {
	return (DailyRhythmPortalApplication) AuthenticatedWebApplication.get();
    }

    /**
     * Added to handle refreshing of feedback panels automatically.
     */
    @Override
    public void onEvent(IEvent<?> event) {
	if (event.getPayload() instanceof AjaxRequestTarget){
	    AjaxRequestTarget target = (AjaxRequestTarget) event.getPayload();
	    if (target.getPage() == null){
		return;
	    }
	    target.addChildren(target.getPage(), IFeedback.class);
	}
    }

    public void setAuthenticator(AuthenticationService authenticator) {
	this.authenticator = authenticator;
    }

    public AuthenticationService getAuthenticator() {
	return authenticator;
    }

    @Override
    protected WebResponse newWebResponse(WebRequest webRequest, HttpServletResponse httpServletResponse) {

	return new ServletWebResponse((ServletWebRequest) webRequest, httpServletResponse) {

	    @Override
	    public String encodeURL(CharSequence url) {
		return String.valueOf(url);
	    }
	};

    }

    @Override
    protected void onUnauthorizedPage(Page page) {
	logger.warn("Not authorized to view page " + page.getClass().getName());
	Class<? extends Page> accessDeniedPageClass = getApplicationSettings().getAccessDeniedPage();
	throw new RestartResponseAtInterceptPageException(accessDeniedPageClass);
    }

    @SuppressWarnings("unchecked")
    private Class<? extends Page> getRoleBasedLandingPage() {
	DailyRhythmPortalSession session = (DailyRhythmPortalSession) Session.get();
	DrpUser drpUser = session.getDrpUser();
	logger.info("Determining role based landing page for user with roles: "
		+ ((drpUser == null)?null:drpUser.getApplicationRoles()));
	String landingPage = null;
	try{
	    if (drpUser != null){
		if (drpUser.hasRole((DrpConstants.DRP_ADMIN))){
		    landingPage = drpPropertyService.getProperty(DrpConstants.DRP_ADMIN + HOME_PAGE);
		}else if (drpUser.hasRole(DrpConstants.DRP_MANAGER)){
		    landingPage = drpPropertyService.getProperty(DrpConstants.DRP_MANAGER + HOME_PAGE);
		}else if (drpUser.hasRole(DrpConstants.DRP_LEAD)){
		    landingPage = drpPropertyService.getProperty(DrpConstants.DRP_LEAD + HOME_PAGE);
		}else if (drpUser.hasRole(DrpConstants.DRP_USER)){
		    landingPage = drpPropertyService.getProperty(DrpConstants.DRP_USER + HOME_PAGE);
		}else if (drpUser.hasRole(DrpConstants.DRP_TEAM)){
		    landingPage = drpPropertyService.getProperty(DrpConstants.DRP_TEAM + HOME_PAGE);
		}else if (drpUser.hasRole(DrpConstants.DRP_BEAST)){
		    landingPage = drpPropertyService.getProperty(DrpConstants.DRP_BEAST + HOME_PAGE);
		}else if (drpUser.hasRole(DrpConstants.SHP_USER)){
		    landingPage = drpPropertyService.getProperty(DrpConstants.SHP_USER + HOME_PAGE);
		}else if (drpUser.hasRole(DrpConstants.SHP_MANAGER)){
		    landingPage = drpPropertyService.getProperty(DrpConstants.SHP_MANAGER + HOME_PAGE);
		}
	    }
	}catch(ServiceException se){
	    logger.error("ROLE Based HOME_PAGE is not defined in ISE_PROPERTIES table");
	    landingPage = drpPropertyService.getProperty("DEFAULT_HOME_PAGE", null);
	}catch(Exception e){
	    logger.error("Exception thrown: ", e);
	    landingPage = drpPropertyService.getProperty("DEFAULT_HOME_PAGE", null);
	}
	logger.debug("Landing Page = " + landingPage);
	Class<? extends Page> responsePage = CustomerDashboardSearchPage.class;
	try{
	    responsePage = (Class<? extends Page>) Class.forName(landingPage);
	}catch(Exception e){
	    logger.error("Home Page Class not defined properly in DB:" + landingPage);
	}
	logger.info("Found role based landing page: " + responsePage.getName());
	return responsePage;
    }

    @Override
    protected IRequestLogger newRequestLogger() {
	return new RequestLogger() {

	    protected void log(RequestData rd, SessionData sd) {

		logger.info(logRequestInfo(rd));
		//	logger.info(logSessionInfo(sd));

	    }

	    String logRequestInfo(RequestData rd) {

		RequestCycle cycle = RequestCycle.get();

		ServletWebRequest servletWebRequest = (ServletWebRequest) cycle.getRequest();
		HttpServletRequest httpRequest = servletWebRequest.getContainerRequest();
		String referrer = httpRequest.getHeader("referer");

		SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		return MessageFormat.format(REQUEST_LOG_FORMAT, df.format(rd.getStartDate()), rd.getTimeTaken(), rd
			.getRequestedUrl(), rd.getSessionId(), rd.getSessionSize(), rd.getActiveRequest(), referrer);

	    }

	};
    }
}
