package com.bestbuy.bbym.ise.drp;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.protocol.http.IRequestLogger.SessionData;
import org.apache.wicket.protocol.http.request.WebClientInfo;
import org.apache.wicket.request.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.CustomerQABean;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.PriceQAQuestions;
import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.ProductInformationBean;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Hotlink;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria.SearchType;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.navigation.MenuItem;
import com.bestbuy.bbym.ise.drp.recsheet.SectionMenuItemBean;
import com.bestbuy.bbym.ise.drp.service.AuthenticationService;
import com.bestbuy.bbym.ise.drp.util.PageError;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class DailyRhythmPortalSession extends AuthenticatedWebSession {

    private static final long serialVersionUID = -8662034956224396759L;
    private static Logger logger = LoggerFactory.getLogger(DailyRhythmPortalSession.class);

    public enum WorkflowAction {
	Triage, Return, OptIn
    }

    private static final String SESSION_LOG_FORMAT = "SPLUNK FILTER Session data : Session ID={4},  Session Start Time={0},"
	    + " No. of Requests={1}, Session Total Time={2}, Session Size={3}, Session Info={5} ";
    // Session properties
    private Map<String, String> sessionProperties = new HashMap<String, String>();

    // Carrier customer
    private Customer customer = new Customer();
    private Store carrierStore = new Store();
    private Line selectedLine;
    private WorkflowAction workflowAction;

    // Best Buy customer
    private List<ServicePlan> servicePlanList;
    private Customer bestBuyCustomer;
    private List<Product> allPurchaseHistory;
    private List<Product> mobilePurchaseHistory;
    private ServicePlan planDetails;
    private Date purchaseHistoryStartDate;
    private Date purchaseHistoryEndDate;
    private Date mobilePurchaseHistoryStartDate;
    private Date mobilePurchaseHistoryEndDate;
    private List<ProtectionPlan> protectionPlanList;
    private ProtectionPlan protectionPlanDetails;

    private HashMap<String, Object> entitlementDataMap;

    // Search customer
    private List<Customer> searchCustomers;
    private Customer searchCustomer = new Customer();
    private CustomerSearchCriteria searchCustomerCriteria = CustomerSearchCriteria.PHONE_NUMBER;
    private CustomerSearchCriteria automatedSearchCustomerCriteria;

    // Shipping portal
    private ManifestSearchCriteria manifestSearchCriteria = new ManifestSearchCriteria();
    private ManifestSearchCriteria inventorySearchCriteria = new ManifestSearchCriteria();

    // Triage
    private TriageIssue selectedTriageIssue;
    private TriageEvent triageEvent;

    private String variant;
    private DrpUser drpUser;
    private MenuItem menuBar;
    private String menuBarHtml;
    private PageError pageError;

    private DrpProperty addEditDrpProperty = new DrpProperty();
    private Hotlink addEditHotlink = new Hotlink();

    private volatile boolean loadTickerComplete;
    private volatile String tickerContent;

    private PriceQAQuestions priceQAQuestions;

    private TitanDevice titanDevice;
    private ProductInformationBean productInformationBean;
    private CustomerQABean customerQABean;
    private UIDataItem titanDataItem;

    private SchedulerRequest schedulerReq;
    private SchedulerRequest schedulerSearchCriteria;

    private Map<String, UIReply> cachingUIReplies = new HashMap<String, UIReply>();
    private Map<String, Map<String, String>> carrierTokenDataList = new HashMap<String, Map<String, String>>();
    private Map<String, Long> carrierTokenFetchTimeMap = new HashMap<String, Long>();

    private Recommendation recommendation;

    private List<List<SectionMenuItemBean>> sectionMenuItems;

    public List<List<SectionMenuItemBean>> getSectionMenuItems() {
	return sectionMenuItems;
    }

    public void setSectionMenuItems(List<List<SectionMenuItemBean>> sectionMenuItems) {
	this.sectionMenuItems = sectionMenuItems;
    }

    private byte[] shippingLabelImageBytes;

    public DailyRhythmPortalSession(Request request) {
	super(request);

	AuthenticationService authenticationService = ((DailyRhythmPortalApplication) getApplication())
		.getAuthenticator();

	// See whether user has already logged in via SSO
	Map<String, String> cookieNameValueMap = getCookieNameValueMap(request);
	try{
	    drpUser = authenticationService.getAuthenticatedUser(cookieNameValueMap);
	}catch(ServiceException se){
	    logger.warn("ServiceException when calling service to authenticate user", se);
	}catch(IseBusinessException be){
	    logger.warn("IseBusinessException when calling service to authenticate user", be);
	}

	// See whether this is BEAST logging in
	if (drpUser == null){
	    String userId = request.getRequestParameters().getParameterValue(DrpConstants.USER_ID).toOptionalString();
	    String dataSharingKey = request.getRequestParameters().getParameterValue(
		    PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toOptionalString();
	    String storeId = request.getRequestParameters().getParameterValue(
		    PageParameterKeys.STORE_ID.getUrlParameterKey()).toOptionalString();
	    HttpServletRequest httpServletRequest = (HttpServletRequest) request.getContainerRequest();
	    boolean isGspCancel = httpServletRequest.getRequestURI().endsWith(DrpConstants.GSP_CANCEL_BOOKMARK_VALUE);
	    String data = request.getRequestParameters().getParameterValue(DrpConstants.DATA).toString();

	    if (storeId != null && userId != null && dataSharingKey != null){
		logger.info("BEAST trying login: dataSharingKey=" + dataSharingKey + " userId=" + userId + " storeId="
			+ storeId + " isGspCancel=" + isGspCancel);
		if (authenticationService.authenticateSharingKey(dataSharingKey, userId, storeId, isGspCancel)){
		    drpUser = new DrpUser();
		    drpUser.setUserId(userId);
		    drpUser.setBeast(true);
		    drpUser.setStoreId(storeId);
		    drpUser.getApplicationRoles().add(DrpConstants.DRP_BEAST);
		    logger.info("Beast has logged in using dsk: " + dataSharingKey + ", userId: " + userId
			    + ", storeId: " + storeId);
		}
	    }else if (storeId != null && userId != null && data != null){
		// TODO Use an API key to authenticate
		logger.info("BEAST trying login into SSO: userId=" + userId + " storeId=" + storeId + " data=" + data);
		drpUser = new DrpUser();
		drpUser.setUserId(userId);
		drpUser.setBeast(true);
		drpUser.setStoreId(storeId);
		drpUser.getApplicationRoles().add(DrpConstants.DRP_BEAST);
		logger.info("User " + userId + " has logged IN with sessionId " + getId());
	    }else if (storeId != null && userId != null
		    && request.getUrl().toString().contains("ViewManageAppointmentPage")){
		// TODO Use an API key to authenticate
		logger.info("BEAST trying login into SSO: userId=" + userId + " storeId=" + storeId + " data=" + data);
		drpUser = new DrpUser();
		drpUser.setUserId(userId);
		drpUser.setBeast(true);
		drpUser.setStoreId(storeId);
		drpUser.getApplicationRoles().add(DrpConstants.DRP_BEAST);
		logger.info("User " + userId + " has logged IN with sessionId " + getId());
	    }
	}
	if (drpUser != null){
	    logger.info("User " + drpUser.getUserId() + " has logged IN with sessionId " + getId());
	    signIn(true);
	    bind();
	}

	WebClientInfo clientInfo = (WebClientInfo) getClientInfo();
	String userAgent = clientInfo.getUserAgent();
	logger.debug("UserAgent string: &1", userAgent);
	if (userAgent != null && (userAgent.contains("iPad") || userAgent.contains("Android"))){
	    variant = DrpConstants.TABLET;
	}

	inventorySearchCriteria.setSearchType(SearchType.STATUS);
    }

    @Override
    public boolean authenticate(String username, String password) {
	AuthenticationService authenticationService = ((DailyRhythmPortalApplication) getApplication())
		.getAuthenticator();
	boolean allRequiredInfoPresent = true;
	if (StringUtils.isBlank(username)){
	    logger.warn("User ID field is required");
	    allRequiredInfoPresent = false;
	}
	if (StringUtils.isBlank(password)){
	    logger.warn("Password field is required");
	    allRequiredInfoPresent = false;
	}

	if (!allRequiredInfoPresent){
	    return false;
	}

	try{
	    logger.info("User " + username + " is attemping to login");
	    drpUser = authenticationService.authenticateUser(username, password);
	    setMenuBar(null);
	    setMenuBarHtml(null);
	    logger.info("User " + username + " successfully logged IN with sessionId " + getId());

	    return true;
	}catch(Exception e){
	    String msg = "DRP User authentication failed with sessionId " + getId();
	    logger.error(msg, e);
	    return false;
	}
    }

    @Override
    public void signOut() {
	logger.info("signOut");
	super.signOut();

	if (drpUser != null){
	    String userId = drpUser.getUserId();
	    logger.info("User " + userId + " has logged OUT with sessionId " + getId());

	    // TODO: Revisit this strategy after the actual "invalidateNow()" or
	    // "invalidate()" method implementation.
	    // LOG total time spent on this session.
	    // Only useful for "BEAST" -> "BEAST-PORTAL" integration.
	    if (getApplication() != null && getApplication().getRequestLogger() != null
		    && getApplication().getRequestLogger().getLiveSessions() != null){
		for(SessionData sd: getApplication().getRequestLogger().getLiveSessions()){
		    if (sd != null && sd.getSessionId() != null && sd.getSessionId().equals(getId())){
			logger.info(logSessionInfo(sd));
			break;
		    }
		}
	    }

	}
	drpUser = null;
    }

    public String logSessionInfo(SessionData sd) {
	StringBuilder sb = new StringBuilder();

	if (sd != null){
	    SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");

	    sb.append(MessageFormat.format(SESSION_LOG_FORMAT, df.format(sd.getStartDate()), sd.getNumberOfRequests(),
		    sd.getTotalTimeTaken(), sd.getSessionSize(), sd.getSessionId(), sd.getSessionInfo()));

	}

	return sb.toString();

    }

    public boolean hasRole(String role) {
	if (isSignedIn() && drpUser != null){
	    return drpUser.hasRole(role);
	}
	return false;
    }

    @Override
    public Roles getRoles() {
	if (isSignedIn() && drpUser != null){
	    String[] applicationRoles = drpUser.getApplicationRoles().toArray(
		    new String[drpUser.getApplicationRoles().size()]);
	    return new Roles(applicationRoles);
	}
	return null;
    }

    public void clearCarrierCustomer() {
	if (customer != null){
	    customer.setEmail(null);
	    customer.setFirstName(null);
	    customer.setLastName(null);
	    customer.setLast4ssn(null);
	    customer.setAcctNumber(null);
	    customer.setBbyCustomerId(null);
	    customer.setRewardZoneId(null);
	    customer.setContactTime(null);
	    customer.setPreferredContactMethod(null);
	    customer.setContactPhone(null);
	    customer.setSubscription(null);
	    customer.setAddress(null);
	    customer.setAcctNumber(null);
	    customer.setTransferFlag(false);
	    customer.setSimpleUpgradeFlag(false);
	    customer.setCapTransactionId(null);
	    customer.setBbyCustomerId(null);
	    customer.setDataSharingKey(null);
	}
	setCarrierStore(null);
	setPurchaseHistoryStartDate(null);
	setPurchaseHistoryEndDate(null);
    }

    public void clearSearchCustomer() {
	if (searchCustomer != null){
	    searchCustomer.setEmail(null);
	    searchCustomer.setFirstName(null);
	    searchCustomer.setLastName(null);
	    searchCustomer.setAddress(null);
	    searchCustomer.setContactPhone(null);
	    searchCustomer.setDataSharingKey(null);
	    searchCustomer.setAcctNumber(null);
	    searchCustomer.setTransferFlag(false);
	    searchCustomer.setCapTransactionId(null);
	    searchCustomer.setBbyCustomerId(null);

	}
	searchCustomers = null;
	searchCustomerCriteria = CustomerSearchCriteria.PHONE_NUMBER;
	setPurchaseHistoryStartDate(null);
	setPurchaseHistoryEndDate(null);
    }

    public void clearBestBuyCustomer() {
	bestBuyCustomer = null;
	allPurchaseHistory = null;
	mobilePurchaseHistory = null;
	servicePlanList = null;
	planDetails = null;
	setPurchaseHistoryStartDate(null);
	setPurchaseHistoryEndDate(null);
    }

    public void clearSchedulerRequest() {
	schedulerReq = null;
    }

    public Customer getCustomer() {

	return customer;
    }

    public MenuItem getMenuBar() {
	return menuBar;
    }

    public void setMenuBar(MenuItem menuBar) {
	this.menuBar = menuBar;
    }

    public String getMenuBarHtml() {
	return menuBarHtml;
    }

    public void setMenuBarHtml(String menuBarHtml) {
	this.menuBarHtml = menuBarHtml;
    }

    public PageError getPageError() {
	return pageError;
    }

    public void setPageError(PageError pageError) {
	this.pageError = pageError;
    }

    public Store getCarrierStore() {
	return carrierStore;
    }

    public void setCarrierStore(Store carrierStore) {
	if (carrierStore == null){
	    this.carrierStore = new Store();
	    return;
	}
	this.carrierStore = carrierStore;
    }

    public Line getSelectedLine() {
	return selectedLine;
    }

    public void setSelectedLine(Line selectedLine) {
	this.selectedLine = selectedLine;
    }

    public WorkflowAction getWorkflowAction() {
	return workflowAction;
    }

    public void setWorkflowAction(WorkflowAction workflowAction) {
	this.workflowAction = workflowAction;
    }

    public DrpProperty getAddEditDrpProperty() {
	return addEditDrpProperty;
    }

    public Hotlink getAddEditHotlink() {
	return addEditHotlink;
    }

    public String getVariant() {
	return variant;
    }

    public DrpUser getDrpUser() {
	return drpUser;
    }

    public void setDrpUser(DrpUser drpUser) {
	this.drpUser = drpUser;
    }

    public List<Customer> getSearchCustomers() {
	return searchCustomers;
    }

    public void setSearchCustomers(List<Customer> searchCustomers) {
	this.searchCustomers = searchCustomers;
    }

    public Customer getSearchCustomer() {
	return searchCustomer;
    }

    public void setSearchCustomer(Customer searchCustomer) {
	if (searchCustomer != null){
	    this.searchCustomer = searchCustomer;
	}
    }

    public CustomerSearchCriteria getSearchCustomerCriteria() {
	return searchCustomerCriteria;
    }

    public void setSearchCustomerCriteria(CustomerSearchCriteria searchCustomerCriteria) {
	this.searchCustomerCriteria = searchCustomerCriteria;
    }

    public CustomerSearchCriteria getAutomatedSearchCustomerCriteria() {
	return automatedSearchCustomerCriteria;
    }

    public void setAutomatedSearchCustomerCriteria(CustomerSearchCriteria automatedSearchCustomerCriteria) {
	this.automatedSearchCustomerCriteria = automatedSearchCustomerCriteria;
    }

    public Customer getBestBuyCustomer() {
	return bestBuyCustomer;
    }

    public void setBestBuyCustomer(Customer bestBuyCustomer) {
	this.bestBuyCustomer = bestBuyCustomer;
    }

    public List<ServicePlan> getServicePlanList() {
	return servicePlanList;
    }

    public void setServicePlanList(List<ServicePlan> servicePlanList) {
	this.servicePlanList = servicePlanList;
    }

    public List<Product> getAllPurchaseHistory() {
	return allPurchaseHistory;
    }

    public void setAllPurchaseHistory(List<Product> allPurchaseHistory) {
	this.allPurchaseHistory = allPurchaseHistory;
    }

    public List<Product> getMobilePurchaseHistory() {
	return mobilePurchaseHistory;
    }

    public void setMobilePurchaseHistory(List<Product> mobilePurchaseHistory) {
	this.mobilePurchaseHistory = mobilePurchaseHistory;
    }

    public ServicePlan getPlanDetails() {
	return planDetails;
    }

    public void setPlanDetails(ServicePlan planDetails) {
	this.planDetails = planDetails;
    }

    public List<ProtectionPlan> getProtectionPlanList() {
	return protectionPlanList;
    }

    public void setProtectionPlanList(List<ProtectionPlan> protectionPlanList) {
	this.protectionPlanList = protectionPlanList;
    }

    public ProtectionPlan getProtectionPlanDetails() {
	return protectionPlanDetails;
    }

    public void setProtectionPlanDetails(ProtectionPlan protectionPlanDetails) {
	this.protectionPlanDetails = protectionPlanDetails;
    }

    public Date getPurchaseHistoryStartDate() {
	return purchaseHistoryStartDate;
    }

    public void setPurchaseHistoryStartDate(Date purchaseHistoryStartDate) {
	this.purchaseHistoryStartDate = purchaseHistoryStartDate;
    }

    public Date getPurchaseHistoryEndDate() {
	return purchaseHistoryEndDate;
    }

    public void setPurchaseHistoryEndDate(Date purchaseHistoryEndDate) {
	this.purchaseHistoryEndDate = purchaseHistoryEndDate;
    }

    public Date getMobilePurchaseHistoryStartDate() {
	return mobilePurchaseHistoryStartDate;
    }

    public void setMobilePurchaseHistoryStartDate(Date mobilePurchaseHistoryStartDate) {
	this.mobilePurchaseHistoryStartDate = mobilePurchaseHistoryStartDate;
    }

    public Date getMobilePurchaseHistoryEndDate() {
	return mobilePurchaseHistoryEndDate;
    }

    public void setMobilePurchaseHistoryEndDate(Date mobilePurchaseHistoryEndDate) {
	this.mobilePurchaseHistoryEndDate = mobilePurchaseHistoryEndDate;
    }

    public boolean isAuthorizedToInstantiate(Class<?> pageClass) {
	Roles roles = getRoles();
	AuthorizeInstantiation annotation = (AuthorizeInstantiation) pageClass
		.getAnnotation(AuthorizeInstantiation.class);
	for(String role: annotation.value()){
	    if (roles.hasRole(role)){
		return true;
	    }
	}
	return false;
    }

    public boolean isLoadTickerComplete() {
	return loadTickerComplete;
    }

    public void setLoadTickerComplete(boolean loadTickerComplete) {
	this.loadTickerComplete = loadTickerComplete;
    }

    public String getTickerContent() {
	return tickerContent;
    }

    public void setTickerContent(String tickerContent) {
	this.tickerContent = tickerContent;
    }

    public ManifestSearchCriteria getManifestSearchCriteria() {
	return manifestSearchCriteria;
    }

    public ManifestSearchCriteria getInventorySearchCriteria() {
	return inventorySearchCriteria;
    }

    public TriageIssue getSelectedTriageIssue() {
	return selectedTriageIssue;
    }

    public void setSelectedTriageIssue(TriageIssue selectedTriageIssue) {
	this.selectedTriageIssue = selectedTriageIssue;
    }

    public TriageEvent getTriageEvent() {
	return triageEvent;
    }

    public void setTriageEvent(TriageEvent triageEvent) {
	this.triageEvent = triageEvent;
    }

    public TitanDevice getTitanDevice() {
	return titanDevice;
    }

    public void setTitanDevice(TitanDevice titanDevice) {
	this.titanDevice = titanDevice;
    }

    // Trade-In
    public UIDataItem getTitanDataItem() {
	return titanDataItem;
    }

    public void setTitanDataItem(UIDataItem titanDataItem) {
	this.titanDataItem = titanDataItem;
    }

    public SchedulerRequest getSchedulerReq() {
	return schedulerReq;
    }

    public void setSchedulerReq(SchedulerRequest schedulerReq) {
	this.schedulerReq = schedulerReq;
    }

    public SchedulerRequest getSchedulerSearchCriteria() {
	return schedulerSearchCriteria;
    }

    public void setSchedulerSearchCriteria(SchedulerRequest schedulerSearchCriteria) {
	this.schedulerSearchCriteria = schedulerSearchCriteria;
    }

    public PriceQAQuestions getPriceQAQuestions() {
	return priceQAQuestions;
    }

    public void setPriceQAQuestions(PriceQAQuestions priceQAQuestions) {
	this.priceQAQuestions = priceQAQuestions;
    }

    public ProductInformationBean getProductInformationBean() {
	return productInformationBean;
    }

    public void setProductInformationBean(ProductInformationBean productInformationBean) {
	this.productInformationBean = productInformationBean;
    }

    public CustomerQABean getCustomerQABean() {
	return customerQABean;
    }

    public void setCustomerQABean(CustomerQABean customerQABean) {
	this.customerQABean = customerQABean;
    }

    public HashMap<String, Object> getEntitlementDataMap() {
	return entitlementDataMap;
    }

    public void setEntitlementDataMap(HashMap<String, Object> entitlementDataMap) {
	this.entitlementDataMap = entitlementDataMap;
    }

    public byte[] getShippingLabelImageBytes() {
	return shippingLabelImageBytes;
    }

    public void setShippingLabelImageBytes(byte[] shippingLabelImageBytes) {
	this.shippingLabelImageBytes = shippingLabelImageBytes;
    }

    public Map<String, Long> getCarrierTokenFetchTimeMap() {
	return carrierTokenFetchTimeMap;
    }

    public void setCarrierTokenFetchTimeMap(Map<String, Long> carrierTokenFetchTimeMap) {
	this.carrierTokenFetchTimeMap = carrierTokenFetchTimeMap;
    }

    public Map<String, Map<String, String>> getCarrierTokenDataList() {
	return carrierTokenDataList;
    }

    public void setCarrierTokenDataList(Map<String, Map<String, String>> tokenCodesMap) {
	this.carrierTokenDataList = tokenCodesMap;
    }

    /**
     * Caches UIReply object for Trade In flow.
     * 
     * @param className
     *            trade in UI class name.
     * @param uiReply
     *            UIReply
     */
    public void saveUIReply(String className, UIReply uiReply) {
	cachingUIReplies.put(className, uiReply);
    }

    /**
     * Returns UIReply for Trade In UI page.
     * 
     * @param className
     *            trade in UI class name.
     * @return UIReply
     */
    public UIReply getCachedUIReply(String className) {
	if (cachingUIReplies.containsKey(className)){
	    return cachingUIReplies.get(className);
	}
	return null;
    }

    /**
     * Gets the cookie name value pairs as a {@code Map}
     * 
     * @param request
     *            the request
     * @return a {@code Map} whose keys contain the cookie name and values
     *         contain the cookie value
     */
    private Map<String, String> getCookieNameValueMap(Request request) {
	HttpServletRequest httpRequest = (HttpServletRequest) request.getContainerRequest();
	Cookie[] cookies = httpRequest.getCookies();
	Map<String, String> cookieNameValueMap = new HashMap<String, String>();
	if (cookies != null){
	    for(Cookie cookie: cookies){
		cookieNameValueMap.put(cookie.getName(), cookie.getValue());
	    }
	}
	return cookieNameValueMap;
    }

    public void setSessionProperty(String key, String value) {
	if (key != null){
	    sessionProperties.put(key, value);
	}
    }

    public void setSessionPropertyBoolean(String key, boolean value) {
	if (key != null){
	    sessionProperties.put(key, value?"true":"false");
	}
    }

    public String getSessionProperty(String key) {
	if (key != null){
	    return sessionProperties.get(key);
	}
	return null;
    }

    public boolean getSessionPropertyBoolean(String key, boolean defaultValue) {
	if (key == null){
	    return defaultValue;
	}
	String value = getSessionProperty(key);
	if (value == null){
	    return defaultValue;
	}
	if (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("yes")){
	    return true;
	}
	return false;
    }

    /**
     * Returns Recommendation object for new rec sheet functionality.
     * 
     * @return Recommendation object for new rec sheet functionality.
     */
    public Recommendation getRecommendation() {
	return recommendation;
    }

    /**
     * Sets Recommendation object for new rec sheet functionality.
     * 
     * @param recommendation
     *            Recommendation object for new rec sheet functionality.
     */
    public void setRecommendation(Recommendation recommendation) {
	this.recommendation = recommendation;
    }

}
