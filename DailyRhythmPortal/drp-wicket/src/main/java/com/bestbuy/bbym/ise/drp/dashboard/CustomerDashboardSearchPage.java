package com.bestbuy.bbym.ise.drp.dashboard;

import java.text.MessageFormat;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.IRequestParameters;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Subscription;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.LoadingModalAjaxButton;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.NewsTickerPanel;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.Validation;
import com.bestbuy.bbym.ise.drp.domain.DashboardData;
import com.bestbuy.bbym.ise.drp.service.DailyRhythmPortalService;
import com.bestbuy.bbym.ise.drp.service.DashboardDataService;
import com.bestbuy.bbym.ise.drp.util.PageError;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class CustomerDashboardSearchPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(CustomerDashboardSearchPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "dashboardDataService")
    private DashboardDataService dashboardDataService;

    @SpringBean
    private DailyRhythmPortalService dailyRhythmPortalService;

    private Carrier carrier;
    private String phoneNumber;
    private String zipCode;
    private String last4ssn;
    private String carrierPin;

    private AjaxCheckBox noSsnCbx;
    private WebMarkupContainer noSsn;
    private SearchButton searchButton;

    private boolean fromEtk;
    private boolean etkSearchPerformed;

    // SPLUNK LOG FORMAT
    private static final String DASHBOARD_LOG_FORMAT = "SPLUNK FILTER Dashboard data : SESSION ID={0}, USER_ID={5}, CARRIER={1}, BBY_CUSTOMER_FOUND={2}, CARRIER_PHONE={3}, ERROR={4}";

    // SPLUNK LOG FORMAT
    private static final String DASHBOARD_EXECEPTION_LOG_FORMAT = "SPLUNK FILTER Dashboard data : SESSION ID={0}, USER_ID={1}, CARRIER={2}, CARRIER_PHONE={3}, ERROR={4}";

    public CustomerDashboardSearchPage(final PageParameters parameters) {
	super(parameters);

	Customer customer = getDailyRhythmPortalSession().getCustomer();
	Model<Customer> customerModel = new Model<Customer>(customer);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	// form
	final Form<Customer> dashboardSearchForm = new Form<Customer>("dashboardSearchForm", customerModel);
	dashboardSearchForm.setMarkupId("dashboardSearchForm");
	dashboardSearchForm.setOutputMarkupId(true);
	dashboardSearchForm.setOutputMarkupPlaceholderTag(true);
	add(dashboardSearchForm);

	// search button
	searchButton = new SearchButton(feedbackPanel);
	dashboardSearchForm.add(searchButton);

	// best Buy Lookup Only button
	Button bbyLookupOnlyButton = new Button("cancel", new ResourceModel("dashboardSearchForm.cancel.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		logger.debug("bbyLookupOnly onSubmit");
		getDailyRhythmPortalSession().clearCarrierCustomer();
		getDailyRhythmPortalSession().setSessionPropertyBoolean("needCustomer", false);
		setResponsePage(CustomerDashboardPage.class);
	    }
	};
	// turn off validation for cancel functionality
	bbyLookupOnlyButton.setDefaultFormProcessing(false);
	dashboardSearchForm.add(bbyLookupOnlyButton);

	// carrier selection
	dashboardSearchForm.add(new Label("carrierLabel", new ResourceModel("dashboardSearchForm.carrier.field")));
	ChoiceRenderer<Carrier> renderer = new ChoiceRenderer<Carrier>();
	DropDownChoice<Carrier> carriers = new DropDownChoice<Carrier>("carrierSelect", new PropertyModel<Carrier>(
		this, "carrier"), dailyRhythmPortalService.getSupportedCarriers(), renderer);
	dashboardSearchForm.add(carriers);

	// phone number input
	final WebMarkupContainer phoneData = new WebMarkupContainer("phoneData");
	phoneData.setOutputMarkupPlaceholderTag(true);
	dashboardSearchForm.add(phoneData);
	phoneData.add(new Label("phoneLabel", new ResourceModel("dashboardSearchForm.phone.field")));
	final TextField<String> phoneInput = new TextField<String>("phoneInput", new PropertyModel<String>(this,
		"phoneNumber"));
	phoneInput.setMarkupId("phoneInput");
	phoneInput.setOutputMarkupId(true);
	phoneData.add(phoneInput);

	// zip code input
	final WebMarkupContainer zipData = new WebMarkupContainer("zipCodeData");
	zipData.setOutputMarkupPlaceholderTag(true);
	dashboardSearchForm.add(zipData);
	zipData.add(new Label("zipCodeLabel", new ResourceModel("dashboardSearchForm.zipCode.field")));
	final TextField<String> zipInput = new TextField<String>("zipCodeInput", new PropertyModel<String>(this,
		"zipCode"));
	zipInput.setMarkupId("zipInput");
	zipInput.setOutputMarkupId(true);
	zipData.add(zipInput);

	// optional ssn input
	final WebMarkupContainer ssnData = new WebMarkupContainer("ssnData") {

	    private static final long serialVersionUID = 1L;

	    /**
	     * Hides the last 4 SSN input if the carrier does not require it or
	     * the <em>No SSN<em> checkbox has been checked.
	     */
	    @Override
	    public boolean isVisible() {
		return isSsnNeeded() && !(noSsn.isVisible() && Boolean.TRUE.equals(noSsnCbx.getModelObject()));
	    }
	};
	ssnData.setOutputMarkupPlaceholderTag(true);
	dashboardSearchForm.add(ssnData);
	ssnData.add(new Label("ssnLabel", new ResourceModel("dashboardSearchForm.ssn.field")));
	final PasswordTextField ssnInput = new PasswordTextField("ssnInput",
		new PropertyModel<String>(this, "last4ssn"));
	ssnInput.setMarkupId("ssnInput");
	ssnInput.setOutputMarkupId(true);
	ssnInput.setRequired(false);
	ssnInput.setConvertEmptyInputStringToNull(true);
	ssnInput.setResetPassword(false);
	ssnData.add(ssnInput);

	// optional pin input
	final WebMarkupContainer pinData = new WebMarkupContainer("pinData") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return isPinNeeded();
	    }
	};
	pinData.setOutputMarkupPlaceholderTag(true);
	dashboardSearchForm.add(pinData);
	pinData.add(new Label("pinLabel", new ResourceModel("dashboardSearchForm.pin.field")));
	final PasswordTextField pinInput = new PasswordTextField("pinInput", new PropertyModel<String>(this,
		"carrierPin"));
	pinInput.setMarkupId("pinInput");
	pinInput.setOutputMarkupId(true);
	pinInput.setRequired(false);
	pinInput.setConvertEmptyInputStringToNull(true);
	pinInput.setResetPassword(false);
	pinData.add(pinInput);

	noSsn = new WebMarkupContainer("noSsn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return isUpgradeCheckBoxNeeded();
	    }
	};
	noSsn.setOutputMarkupPlaceholderTag(true);
	dashboardSearchForm.add(noSsn);

	noSsnCbx = new AjaxCheckBox("noSsnCheckbox", new Model<Boolean>(false)) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		logger.debug("noSsnCheckbox onUpdate value=" + noSsnCbx.getModelObject());
		handleFields(target, ssnData, pinData);
	    }
	};
	noSsnCbx.setOutputMarkupPlaceholderTag(true);
	noSsn.add(noSsnCbx);

	// behavior when carrier selected
	carriers.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		handleFields(target, ssnData, pinData);
	    }
	});

	NewsTickerPanel newsTickerPanel = null;
	if (getDailyRhythmPortalSession().getPageError() != null){
	    error(getDailyRhythmPortalSession().getPageError().getMessage());
	    getDailyRhythmPortalSession().setPageError(null);
	    newsTickerPanel = new NewsTickerPanel("beastTicker", false);
	}else{
	    newsTickerPanel = new NewsTickerPanel("beastTicker");
	}
	newsTickerPanel.setMarkupId("beastTicker");
	newsTickerPanel.setOutputMarkupId(true);
	add(newsTickerPanel);

	// If we are navigating to here from the ETK widget we'll populate the
	// form with the values posted to us and fire off the form submit
	final IRequestParameters requestParameters = getRequest().getRequestParameters();
	fromEtk = requestParameters.getParameterValue(PageParameterKeys.ETK.getUrlParameterKey()).toBoolean(false);
	if (fromEtk){
	    logger.info("Performing ETK Search Customer");
	    if (!requestParameters.getParameterNames().isEmpty()){
		logger.info("Populating form from parameters");
		etkSearchPerformed = false;
		String carrier = requestParameters.getParameterValue(PageParameterKeys.CARRIER.getUrlParameterKey())
			.toString();
		String phoneNumber = requestParameters.getParameterValue(
			PageParameterKeys.PHONE_NUMBER.getUrlParameterKey()).toString();
		String zip = requestParameters.getParameterValue(PageParameterKeys.ZIP_CODE.getUrlParameterKey())
			.toString();
		String lastFourSsn = requestParameters.getParameterValue(
			PageParameterKeys.LAST_FOUR_SSN.getUrlParameterKey()).toString();
		String pin = requestParameters.getParameterValue(PageParameterKeys.PIN.getUrlParameterKey()).toString();
		Boolean noSsn = requestParameters.getParameterValue(PageParameterKeys.NO_SSN.getUrlParameterKey())
			.toOptionalBoolean();

		CustomerDashboardSearchPage.this.carrier = Carrier.fromLabel(carrier);
		CustomerDashboardSearchPage.this.phoneNumber = phoneNumber;
		CustomerDashboardSearchPage.this.zipCode = zip;
		CustomerDashboardSearchPage.this.last4ssn = lastFourSsn;
		CustomerDashboardSearchPage.this.carrierPin = pin;
		noSsnCbx.setModelObject(noSsn);
	    }
	}
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);

	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append(buildValidationJS());
	onDomReadyJS.append(buildCarrierSelectJS());
	onDomReadyJS.append("installCustomerSearchPasteHandlers();");
	onDomReadyJS.append("doFormFieldValidation(custSearchValidation);");
	if (fromEtk && !etkSearchPerformed && !getRequest().getRequestParameters().getParameterNames().isEmpty()){
	    onDomReadyJS.append("$('#" + searchButton.getId() + "').removeAttr('disabled');");
	    onDomReadyJS.append("$('#" + searchButton.getId() + "').click();");
	    etkSearchPerformed = true;
	}
	logger.debug("onDomReadyJS=" + onDomReadyJS);
	response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
    }

    private class SearchButton extends LoadingModalAjaxButton {

	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedbackPanel;

	public SearchButton(FeedbackPanel feedbackPanel) {
	    super("search", new ResourceModel("dashboardSearchForm.search.button"), new ResourceModel(
		    "loadingModalMessage.customerSearch"));
	    this.feedbackPanel = feedbackPanel;
	    setOutputMarkupPlaceholderTag(true);
	    setMarkupId("search");
	    setOutputMarkupId(true);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    logger.info("searchButton onSubmit");
	    long t0 = System.currentTimeMillis();
	    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	    Customer customer = session.getCustomer();
	    DashboardData dashboardData = null;
	    customer.setSimpleUpgradeFlag(noSsn.isVisible() && Boolean.TRUE.equals(noSsnCbx.getModelObject()));
	    boolean invokeUCS = customer.isSimpleUpgradeFlag();
	    logger.debug("invokeUCS=" + invokeUCS);
	    try{
		if (!isSsnNeeded() || invokeUCS){
		    customer.setLast4ssn(null);
		}else{
		    customer.setLast4ssn(last4ssn);
		}
		if (customer.getSubscription() == null){
		    customer.setSubscription(new Subscription());
		}
		if (!isPinNeeded()){
		    customer.getSubscription().setCarrierPin(null);
		}else{
		    customer.getSubscription().setCarrierPin(carrierPin);
		}
		customer.getSubscription().setCarrier(carrier);
		customer.setContactPhone(phoneNumber);
		if (customer.getAddress() == null){
		    customer.setAddress(new Address());
		}
		customer.getAddress().setZipcode(zipCode);
		dashboardData = dashboardDataService.getDashboardData(customer, session.getDrpUser(), invokeUCS);
		if (dashboardData == null || dashboardData.getCarrierData() == null
			|| dashboardData.getCarrierData().getSubscriptionInfo() == null){
		    throw new IseBusinessException("Invalid carrier/customer data returned");
		}
		if (customer.getSubscription() != null){
		    dashboardData.getCarrierData().getSubscriptionInfo().setCarrier(
			    customer.getSubscription().getCarrier());
		}
		session.setAutomatedSearchCustomerCriteria(dashboardData.getCustomerSearchCriteria());
		session.setSearchCustomers(dashboardData.getSearchCustomers());
		if (session.getSearchCustomers() == null){
		    logger.debug("searchCustomers=null");
		    //SPLUNK FILTER Dashboard data : SESSION ID={0}, CARRIER={1}, BBY_CUSTOMER_FOUND={2}, CARRIER_PHONE={3}  
		    logger.info(MessageFormat.format(DASHBOARD_LOG_FORMAT, session.getId(), carrier.toString(), false,
			    phoneNumber, false, session.getDrpUser().getUserId()));

		}else{
		    logger.debug("searchCustomers.size=" + session.getSearchCustomers().size());
		    //SPLUNK FILTER Dashboard data : SESSION ID={0}, CARRIER={1}, BBY_CUSTOMER_FOUND={2}, CARRIER_PHONE={3} 
		    logger.info(MessageFormat.format(DASHBOARD_LOG_FORMAT, session.getId(), carrier.toString(), true,
			    phoneNumber, false, session.getDrpUser().getUserId()));
		}
	    }catch(IseBusinessException ex){
		logger.error("IseBusinessException is " + ex.getFullMessage());
		session.clearBestBuyCustomer();
		logger.info(MessageFormat.format(DASHBOARD_EXECEPTION_LOG_FORMAT, session.getId(), session.getDrpUser()
			.getUserId(), carrier.toString(), phoneNumber, ex.getFullMessage()));
		session.setPageError(new PageError(ex.getFullMessage(), CustomerDashboardSearchPage.this.getClass()));
		error(ex.getFullMessage());
		target.add(feedbackPanel);
		return;
	    }catch(ServiceException se){
		session.clearCarrierCustomer();
		session.clearBestBuyCustomer();
		String message = "An unexpected exception occured while attempting to get carrier/customer info";
		logger.info(MessageFormat.format(DASHBOARD_EXECEPTION_LOG_FORMAT, session.getId(), session.getDrpUser()
			.getUserId(), carrier.toString(), phoneNumber, "SERVICE_EXCEPTION"));
		logger.error(message, se);
		processException(message, se);
	    }
	    Subscription dashboardSubscription = dashboardData.getCarrierData().getSubscriptionInfo();
	    session.getCustomer().setSubscription(dashboardSubscription);
	    if (dashboardSubscription.getPrimAcctFirstName() != null){
		session.getCustomer().setFirstName(dashboardSubscription.getPrimAcctFirstName());
	    }
	    if (dashboardSubscription.getPrimAcctLastName() != null){
		session.getCustomer().setLastName(dashboardSubscription.getPrimAcctLastName());
	    }
	    if (dashboardSubscription.getPrimAcctEmailId() != null){
		session.getCustomer().setEmail(dashboardSubscription.getPrimAcctEmailId());
	    }
	    if (dashboardSubscription.getAccountNumber() != null){
		session.getCustomer().setAcctNumber(dashboardSubscription.getAccountNumber());
	    }
	    if (dashboardSubscription.getCarrier() != null){
		if (session.getCustomer().getSubscription() == null){
		    session.getCustomer().setSubscription(new Subscription());
		}
		session.getCustomer().getSubscription().setCarrier(dashboardSubscription.getCarrier());
	    }
	    session.setCarrierStore(dashboardData.getCarrierData().getStoreInfo());

	    // Set search customer data from carrier customer data
	    session.getSearchCustomer().setFirstName(session.getCustomer().getFirstName());
	    session.getSearchCustomer().setLastName(session.getCustomer().getLastName());
	    if (session.getSearchCustomer().getAddress() == null){
		session.getSearchCustomer().setAddress(new Address());
	    }
	    if (session.getCustomer().getAddress() != null){
		session.getSearchCustomer().getAddress().setZipcode(session.getCustomer().getAddress().getZipcode());
	    }
	    session.getSearchCustomer().setEmail(session.getCustomer().getEmail());
	    session.getSearchCustomer().setContactPhone(session.getCustomer().getContactPhone());
	    logger.debug("SearchCustomer=" + session.getSearchCustomer());
	    logger.debug("StoreInfo=" + dashboardData.getCarrierData().getStoreInfo());
	    setResponsePage(CustomerDashboardPage.class);
	    long t1 = System.currentTimeMillis();
	    logger.debug("search: " + (t1 - t0) + "ms");
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onError");
	    target.add(feedbackPanel);
	}
    };

    private boolean isSsnNeeded() {
	return carrier == Carrier.VERIZON || carrier == Carrier.ATT || carrier == Carrier.TMOBILE;
    }

    private boolean isPinNeeded() {
	return carrier == Carrier.VERIZON;
    }

    private boolean isUpgradeCheckBoxNeeded() {
	return carrier == Carrier.ATT || carrier == Carrier.TMOBILE;
    }

    private String buildValidationJS() {
	final StringBuilder validationJS = new StringBuilder();

	// phone settings
	validationJS.append("custSearchValidation.phone.exactLength=");
	validationJS.append(Validation.PHONE_NUMBER_SIZE);
	validationJS.append(";");

	// zip code settings
	validationJS.append("custSearchValidation.zipCode.exactLength=");
	validationJS.append(Validation.ZIP_CODE_SIZE);
	validationJS.append(";");

	// ssn settings
	validationJS.append("custSearchValidation.ssn.exactLength=");
	validationJS.append(Validation.SSN_SIZE);
	validationJS.append(";");

	// pin settings
	validationJS.append("custSearchValidation.pin.maxLength=");
	validationJS.append(Validation.CARRIER_PIN_SIZE);
	validationJS.append(";");

	return validationJS.toString();
    }

    private String buildCarrierSelectJS() {
	StringBuilder validateJS = new StringBuilder();
	if (carrier == null){
	    validateJS.append("custSearchValidation.carrier.valid=false;");
	}else{
	    validateJS.append("custSearchValidation.carrier.valid=true;");
	}
	if (isSsnNeeded() && !(noSsn.isVisible() && Boolean.TRUE.equals(noSsnCbx.getModelObject()))){
	    validateJS
		    .append("custSearchValidation.ssn.ignore=false;installCustomerSearchPasteHandler('ssn');fieldValueValidation('','ssn',custSearchValidation);");
	}else{
	    validateJS.append("custSearchValidation.ssn.ignore=true;");
	}
	if (isPinNeeded()){
	    validateJS
		    .append("custSearchValidation.pin.ignore=false;installCustomerSearchPasteHandler('pin');fieldValueValidation('','pin',custSearchValidation);");
	}else{
	    validateJS.append("custSearchValidation.pin.ignore=true;");
	}
	return validateJS.toString();
    }

    private void handleFields(AjaxRequestTarget target, WebMarkupContainer ssnData, WebMarkupContainer pinData) {
	target.add(ssnData);
	target.add(pinData);
	target.add(noSsn);
	String onCarrierChangeJS = buildCarrierSelectJS() + "doFormFieldValidation(custSearchValidation);";
	logger.debug("onCarrierChangeJS=" + onCarrierChangeJS);
	target.appendJavaScript(onCarrierChangeJS);
    }
}
