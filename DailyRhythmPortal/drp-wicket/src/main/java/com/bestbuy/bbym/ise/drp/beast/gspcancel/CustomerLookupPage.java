package com.bestbuy.bbym.ise.drp.beast.gspcancel;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.Validation;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.helpers.BeastObjectMapper;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.GSPPlanService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

/**
 * Entry point of the cancel GSP flow.
 * 
 * @see <a
 *      href="https://spr.bestbuy.com/ISE/Shared%20Documents/Requirements%20(User%20Stories)/Release%201.0/UserStory_B-07919-%20Cancel%20GSP.docx">User
 *      story B-07919 - Cancel GSP</a>
 */
public class CustomerLookupPage extends BaseBeastPage2 {

    private static Logger logger = LoggerFactory.getLogger(CustomerLookupPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    @SpringBean(name = "customerService")
    private CustomerService customerService;

    @SpringBean(name = "gspPlanService")
    private GSPPlanService gspPlanService;

    private SelectItem<String> phoneEmailSelected;
    private CustomerSearchCriteria radioSearchCriteria = CustomerSearchCriteria.PHONE_EMAIL;

    private AbstractDefaultAjaxBehavior customerRadioGroupOnClick;
    private long selectedCustomerId = 0L;
    private String dataSharingKey;

    private String noResultsMessage;

    // SPLUNK LOG FORMAT
    private static final String BBY_LOOKUP_LOG_FORMAT = "SPLUNK FILTER BBY_CUSTOMER_GSP data : SESSION ID={0}, BBY_CUSTOMER_FOUND={1}";

    private SearchCustomersDataProvider searchCustomersDataProvider;
    private AjaxFallbackDefaultDataTable<Customer> searchCustomersTable;
    private WebMarkupContainer searchCustomersContainer;
    private WebMarkupContainer noSearchCustomersContainer;

    public CustomerLookupPage(final PageParameters parameters) {
	super(parameters);

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();
	logger.debug("passed in DSK=" + dataSharingKey);

	try{
	    BbyAccount bestBuyAccount = customerService.getBbyCustomer(dataSharingKey);
	    Customer customer = BeastObjectMapper.convertToCustomer(bestBuyAccount);
	    getDailyRhythmPortalSession().setSearchCustomer(customer);
	    if (customer != null){
		if (customer.getBbyCustomerId() != null){
		    if (!WicketUtils.determineBooleanParameter(parameters, PageParameterKeys.NEW_CUSTOMER_SEARCH
			    .getUrlParameterKey())){
			getDailyRhythmPortalSession().setBestBuyCustomer(customer);
			PageParameters pp = new PageParameters();
			pp.add(PageParameterKeys.GET_GSP_PLANS.getUrlParameterKey(), "true");
			pp.add(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey(), dataSharingKey);
			throw new RestartResponseAtInterceptPageException(new DisplayProtectionPlansPage(pp));
		    }else{
			logger.debug("doing new customer search for BBY customer id=" + customer.getBbyCustomerId());
		    }
		}
	    }else{
		logger.info("no BBY customer for dataSharingKey=" + dataSharingKey);
		logger.debug("set search customer as empty");
		getDailyRhythmPortalSession().setSearchCustomer(new Customer());
	    }
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get BBY account";
	    logger.error(message, se);
	    logger.debug("set search customer as empty");
	    getDailyRhythmPortalSession().setSearchCustomer(new Customer());
	}
	// TODO if no DSK account found, popup error and then exit status

	List<SelectItem<String>> phoneEmailSelections = new ArrayList<SelectItem<String>>();
	phoneEmailSelections.add(new SelectItem<String>("P", getString("gspCancelCustomerLookupForm.phone.selection")));
	phoneEmailSelections.add(new SelectItem<String>("E", getString("gspCancelCustomerLookupForm.email.selection")));

	for(SelectItem<String> selected: phoneEmailSelections){
	    if ("P".equals(selected.getKey())){
		phoneEmailSelected = selected;
		break;
	    }
	}

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	form.add(feedbackPanel);

	final RadioGroup<CustomerSearchCriteria> searchByFieldRadioGroup = new RadioGroup<CustomerSearchCriteria>(
		"searchByFieldRadioGroup", new Model<CustomerSearchCriteria>(radioSearchCriteria));
	form.add(searchByFieldRadioGroup);

	final Radio<CustomerSearchCriteria> phoneEmailRadio = new Radio<CustomerSearchCriteria>("phoneEmailRadio",
		new Model<CustomerSearchCriteria>(CustomerSearchCriteria.PHONE_EMAIL));
	searchByFieldRadioGroup.add(phoneEmailRadio);

	final Radio<CustomerSearchCriteria> nameZipRadio = new Radio<CustomerSearchCriteria>("nameZipRadio",
		new Model<CustomerSearchCriteria>(CustomerSearchCriteria.FN_LN_ZIP));
	searchByFieldRadioGroup.add(nameZipRadio);

	ChoiceRenderer<SelectItem<String>> choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	DropDownChoice<SelectItem<String>> phoneEmailDropDown = new DropDownChoice<SelectItem<String>>(
		"phoneEmailSelect", new PropertyModel<SelectItem<String>>(this, "phoneEmailSelected"),
		phoneEmailSelections, choiceRenderer);
	phoneEmailDropDown.setMarkupId("phoneEmailSelect");
	phoneEmailDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		logger.debug("phoneEmailDropDown onUpdate");
		StringBuilder appendJS = new StringBuilder();
		appendJS.append(buildValidationJS());
		CustomerSearchCriteria criteria = determineSearchCriteria();
		if (criteria == CustomerSearchCriteria.EMAIL){
		    appendJS.append("handleCustLookupFieldMarkup('email');");
		}else{
		    appendJS.append("handleCustLookupFieldMarkup('phone');");
		}
		appendJS.append("doFormFieldValidation(custLookupValidation);");
		target.appendJavaScript(appendJS.toString());
	    }
	});
	phoneEmailDropDown.setOutputMarkupPlaceholderTag(true);
	searchByFieldRadioGroup.add(phoneEmailDropDown);

	final TextField<String> phoneSearch = new TextField<String>("phoneSearch", new PropertyModel<String>(
		getDailyRhythmPortalSession().getSearchCustomer(), "contactPhone"));
	phoneSearch.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		PhoneNumberConverter converter = new PhoneNumberConverter();
		phoneSearch.setDefaultModelObject(converter.convertToString(phoneSearch.getDefaultModelObject(), null));
		target.add(phoneSearch);
	    }
	});
	phoneSearch.setMarkupId("phoneSearch");
	phoneSearch.setOutputMarkupPlaceholderTag(true);
	searchByFieldRadioGroup.add(phoneSearch);

	final TextField<String> emailSearch = new TextField<String>("emailSearch", new PropertyModel<String>(
		getDailyRhythmPortalSession().getSearchCustomer(), "email"));
	emailSearch.setMarkupId("emailSearch");
	emailSearch.setOutputMarkupPlaceholderTag(true);
	searchByFieldRadioGroup.add(emailSearch);

	final TextField<String> firstNameSearch = new TextField<String>("firstNameSearch", new PropertyModel<String>(
		getDailyRhythmPortalSession().getSearchCustomer(), "firstName"));
	firstNameSearch.setMarkupId("firstNameSearch");
	firstNameSearch.setOutputMarkupPlaceholderTag(true);
	searchByFieldRadioGroup.add(firstNameSearch);

	final TextField<String> lastNameSearch = new TextField<String>("lastNameSearch", new PropertyModel<String>(
		getDailyRhythmPortalSession().getSearchCustomer(), "lastName"));
	lastNameSearch.setMarkupId("lastNameSearch");
	lastNameSearch.setOutputMarkupPlaceholderTag(true);
	searchByFieldRadioGroup.add(lastNameSearch);

	final TextField<String> zipSearch = new TextField<String>("zipSearch", new PropertyModel<String>(
		getDailyRhythmPortalSession().getSearchCustomer(), "address.zipcode"));
	zipSearch.setMarkupId("zipSearch");
	zipSearch.setOutputMarkupPlaceholderTag(true);
	searchByFieldRadioGroup.add(zipSearch);

	phoneEmailRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("phoneEmailRadio onEvent");
		radioSearchCriteria = CustomerSearchCriteria.PHONE_EMAIL;
		StringBuilder appendJS = new StringBuilder();
		CustomerSearchCriteria criteria = determineSearchCriteria();
		if (criteria == CustomerSearchCriteria.EMAIL){
		    appendJS.append("handleCustLookupFieldMarkup('email');");
		}else{
		    appendJS.append("handleCustLookupFieldMarkup('phone');");
		}
		appendJS.append(buildValidationJS());
		appendJS.append("doFormFieldValidation(custLookupValidation);");
		logger.debug("appendJS=" + appendJS.toString());
		target.appendJavaScript(appendJS.toString());
	    }
	});

	nameZipRadio.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		logger.debug("nameZipRadio onEvent");
		radioSearchCriteria = CustomerSearchCriteria.FN_LN_ZIP;
		StringBuilder appendJS = new StringBuilder();
		if (phoneEmailSelected != null && "E".equals(phoneEmailSelected.getKey())){
		    appendJS.append("handleCustLookupFieldMarkup('nameZip', 'email');");
		}else{
		    appendJS.append("handleCustLookupFieldMarkup('nameZip', 'phone');");
		}
		appendJS.append(buildValidationJS());
		appendJS.append("doFormFieldValidation(custLookupValidation);");
		logger.debug("appendJS=" + appendJS.toString());
		target.appendJavaScript(appendJS.toString());
	    }
	});

	searchCustomersDataProvider = new SearchCustomersDataProvider();

	final List<IColumn<Customer>> columns = new ArrayList<IColumn<Customer>>();
	columns.add(new AbstractColumn<Customer>(null, "lastName") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Customer row = (Customer) rowModel.getObject();
		cellItem.add(new CustomerLookupPage.SelectRowPanel(componentId, rowModel, row));
	    }
	});
	columns.add(new PropertyColumn<Customer>(new ResourceModel("gspCancelCustomerLookup.lastName.column"),
		"lastName", "lastName"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("gspCancelCustomerLookup.firstName.column"),
		"firstName", "firstName"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("gspCancelCustomerLookup.address.column"),
		"address.addressLine1", "address.addressLine1"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("gspCancelCustomerLookup.city.column"),
		"address.city", "address.city"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("gspCancelCustomerLookup.state.column"),
		"address.state", "address.state"));
	columns.add(new FormatPropertyColumn<Customer, String>(
		new ResourceModel("gspCancelCustomerLookup.phone.column"), "contactPhone", "contactPhone",
		new PhoneFormatter<String>()));

	searchCustomersContainer = new WebMarkupContainer("searchCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return searchCustomersDataProvider.size() > 0;
	    }
	};
	searchCustomersContainer.setMarkupId("searchCustomersContainer");
	searchCustomersContainer.setOutputMarkupPlaceholderTag(true);
	form.add(searchCustomersContainer);

	searchCustomersTable = new AjaxFallbackDefaultDataTable<Customer>("searchCustomersTable", columns,
		searchCustomersDataProvider, 200);
	searchCustomersTable.setOutputMarkupPlaceholderTag(true);
	searchCustomersContainer.add(searchCustomersTable);

	customerRadioGroupOnClick = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("onClick id=" + id);
		selectedCustomerId = Util.getLong(id, -1L);
		target.appendJavaScript("handleButtonState(true, '#nextButton');");
	    }
	};
	add(customerRadioGroupOnClick);

	noSearchCustomersContainer = new WebMarkupContainer("noSearchCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return noResultsMessage != null;
	    }
	};
	noSearchCustomersContainer.setOutputMarkupPlaceholderTag(true);
	form.add(noSearchCustomersContainer);

	final Label noSearchCustomersLabel = new Label("noSearchCustomersLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		return noResultsMessage;
	    }

	});
	noSearchCustomersLabel.setOutputMarkupPlaceholderTag(true);
	noSearchCustomersContainer.add(noSearchCustomersLabel);
    }

    @Override
    protected String getButtonFunctionKey(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "F3";
	    case 2:
		return "F2";
	    case 3:
		return "F5";
	}
	return null;
    }

    @Override
    protected String getButtonName(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "closeButton";
	    case 2:
		return "nextButton";
	    case 3:
		return "searchButton";
	}
	return null;
    }

    @Override
    protected String getButtonStyleClasses(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "button left";
	    case 2:
		return "button right";
	    case 3:
		return "button right";
	}
	return null;
    }

    @Override
    protected IModel<String> getButtonModel(int buttonId) {
	switch (buttonId) {
	    case 1:
		return new ResourceModel("gspCancelCustomerLookupForm.close.button");
	    case 2:
		return new ResourceModel("gspCancelCustomerLookupForm.next.button");
	    case 3:
		return new ResourceModel("gspCancelCustomerLookupForm.search.button");
	}
	return null;
    }

    @Override
    protected void onButtonSubmit(int buttonId, AjaxRequestTarget target) {
	switch (buttonId) {
	    case 1:
		logger.debug("closeButton onSubmit");
		if (!quitModalPanel.isOpen()){
		    quitModalPanel.setQuestion(getString("gspCancelCustomerLookup.quitModal.question.label"));
		    quitModalPanel.open(target);
		}
		break;
	    case 2:
		logger.debug("nextButton onSubmit");
		if (getDailyRhythmPortalSession().getProtectionPlanList() != null){
		    PageParameters pp = new PageParameters();
		    pp.add(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey(), dataSharingKey);
		    setResponsePage(new DisplayProtectionPlansPage(pp));
		}else{
		    doNext(target);
		}
		break;
	    case 3:
		logger.debug("searchButton onSubmit");
		doSearch(target);
		break;
	}
    }

    private String buildValidationJS() {
	StringBuilder validateJS = new StringBuilder();
	CustomerSearchCriteria criteria = determineSearchCriteria();
	// firstName, lastName, zip should be validated
	if (criteria == CustomerSearchCriteria.FN_LN_ZIP){
	    validateJS.append("custLookupValidation.firstName.ignore=false;");
	    validateJS.append("custLookupValidation.lastName.ignore=false;");
	    validateJS.append("custLookupValidation.zip.ignore=false;");
	    validateJS.append("custLookupValidation.phone.ignore=true;");
	    validateJS.append("custLookupValidation.email.ignore=true;");

	    // email should be validated
	}else if (criteria == CustomerSearchCriteria.EMAIL){
	    validateJS.append("custLookupValidation.firstName.ignore=true;");
	    validateJS.append("custLookupValidation.lastName.ignore=true;");
	    validateJS.append("custLookupValidation.zip.ignore=true;");
	    validateJS.append("custLookupValidation.phone.ignore=true;");
	    validateJS.append("custLookupValidation.email.ignore=false;");

	    // phone should be validated
	}else{
	    validateJS.append("custLookupValidation.firstName.ignore=true;");
	    validateJS.append("custLookupValidation.lastName.ignore=true;");
	    validateJS.append("custLookupValidation.zip.ignore=true;");
	    validateJS.append("custLookupValidation.phone.ignore=false;");
	    validateJS.append("custLookupValidation.email.ignore=true;");
	}
	return validateJS.toString();
    }

    private CustomerSearchCriteria determineSearchCriteria() {
	if (radioSearchCriteria != null && radioSearchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
	    return CustomerSearchCriteria.FN_LN_ZIP;
	}else if ((radioSearchCriteria == null || radioSearchCriteria == CustomerSearchCriteria.PHONE_EMAIL)
		&& phoneEmailSelected != null && "E".equals(phoneEmailSelected.getKey())){
	    return CustomerSearchCriteria.EMAIL;
	}
	return CustomerSearchCriteria.PHONE_NUMBER;
    }

    class SelectRowPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectRowPanel(String id, final IModel<?> model, final Customer row) {
	    super(id, model);

	    final Radio<Object> customerRadio = new Radio<Object>("customerRadio") {

		private static final long serialVersionUID = 1L;

		@Override
		protected void onComponentTag(final ComponentTag tag) {
		    tag.put("onClick", "wicketAjaxGet('" + customerRadioGroupOnClick.getCallbackUrl() + "&id="
			    + row.getId() + "');");
		}
	    };
	    add(customerRadio);
	}
    }

    @Override
    protected void quitModalPanelOnClose(AjaxRequestTarget target) {
	if (quitModalPanel.isOk()){
	    getDailyRhythmPortalSession().setBestBuyCustomer(null);
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
	    PageParameters pp = new PageParameters();
	    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
	    setResponsePage(new StatusPage(pp));
	}
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
	logger.debug("in wicketBehavior id=" + id);
	if ("search".equals(id)){
	    doSearch(target);
	}else if ("next".equals(id)){
	    doNext(target);
	}else if ("message".equals(id)){
	    doMessage(target);
	}
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append(buildStaticValidationJS());
	onDomReadyJS.append(buildValidationJS());
	onDomReadyJS.append("handleButtonState(false, '#nextButton');");
	onDomReadyJS.append("handleCustLookupFieldMarkup('phone');");
	onDomReadyJS.append("doFormFieldValidation(custLookupValidation);getClientTime(wicketBehavior);");
	return onDomReadyJS.toString();
    }

    private void doSearch(AjaxRequestTarget target) {
	getDailyRhythmPortalSession().setProtectionPlanList(null);
	noResultsMessage = null;
	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("gspCancelCustomerLookup.customerSearch.loading.label"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("handleButtonState(false, '#nextButton');clickButton('#searchButton');");
	    return;
	}
	logger.debug("doing customer search");
	CustomerSearchCriteria criteria = determineSearchCriteria();
	Customer searchCust = new Customer();
	// firstName, lastName, zip
	if (criteria == CustomerSearchCriteria.FN_LN_ZIP){
	    searchCust.setFirstName(getDailyRhythmPortalSession().getSearchCustomer().getFirstName());
	    searchCust.setLastName(getDailyRhythmPortalSession().getSearchCustomer().getLastName());
	    searchCust.setAddress(new Address());
	    if (getDailyRhythmPortalSession().getSearchCustomer().getAddress() != null){
		searchCust.getAddress().setZipcode(
			getDailyRhythmPortalSession().getSearchCustomer().getAddress().getZipcode());
	    }
	    // email
	}else if (criteria == CustomerSearchCriteria.EMAIL){
	    searchCust.setEmail(getDailyRhythmPortalSession().getSearchCustomer().getEmail());
	    // phone
	}else{
	    String phone = getDailyRhythmPortalSession().getSearchCustomer().getContactPhone().replaceAll("[^0-9]", "");
	    getDailyRhythmPortalSession().getSearchCustomer().setContactPhone(phone);
	    searchCust.setContactPhone(phone);
	}
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	logger.debug("searchCriteria=" + criteria);
	logger.debug("searchCustomer=" + searchCust);
	logger.debug("session.drpUser=" + session.getDrpUser());
	try{
	    List<Customer> customerList = customerDataService.getBBYCustomerProfile(searchCust, criteria, session
		    .getDrpUser());
	    if (customerList != null){
		long count = 0L;
		for(Customer c: customerList){
		    count++;
		    c.setId(new Long(count));
		}
	    }

	    if (customerList != null && !customerList.isEmpty()){
		//SPLUNK FILTER BBY_CUSTOMER data : SESSION ID={0}, BBY_CUSTOMER_FOUND={1} 
		logger.info(MessageFormat.format(BBY_LOOKUP_LOG_FORMAT, session.getId(), true));
	    }else{
		logger.info(MessageFormat.format(BBY_LOOKUP_LOG_FORMAT, session.getId(), false));
	    }

	    searchCustomersDataProvider.setCustomerList(customerList);
	    logger.debug("# customers found=" + searchCustomersDataProvider.size());
	}catch(ServiceException se){
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get customer list";
	    logger.info(MessageFormat.format(BBY_LOOKUP_LOG_FORMAT, session.getId(), false));
	    logger.error(message, se);
	    searchCustomersDataProvider.setCustomerList(null);
	    searchCustomersTable.modelChanged();
	    target.add(searchCustomersContainer);
	    target.add(noSearchCustomersContainer);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}catch(IseBusinessException be){
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get customer list";
	    logger.info(MessageFormat.format(BBY_LOOKUP_LOG_FORMAT, session.getId(), false));
	    logger.error(message);
	    searchCustomersDataProvider.setCustomerList(null);
	    searchCustomersTable.modelChanged();
	    target.add(searchCustomersContainer);
	    target.add(noSearchCustomersContainer);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}
	loadingModalPanel.close(target);

	if (searchCustomersDataProvider.size() == 0){
	    if (criteria == CustomerSearchCriteria.FN_LN_ZIP){
		noResultsMessage = getString("gspCancelCustomerLookup.noFnLnZipSearchCustomersLabel.label");
	    }else if (criteria == CustomerSearchCriteria.EMAIL){
		noResultsMessage = getString("gspCancelCustomerLookup.noEmailSearchCustomersLabel.label");
		// phone
	    }else{
		noResultsMessage = getString("gspCancelCustomerLookup.noPhoneSearchCustomersLabel.label");
	    }
	}else{
	    target.appendJavaScript("setupSearchCustomersTable();");
	}
	logger.debug("noResultsMessage=" + noResultsMessage);

	searchCustomersTable.modelChanged();
	target.add(searchCustomersContainer);
	target.add(noSearchCustomersContainer);
    }

    private void doMessage(AjaxRequestTarget target) {
	if (!messageModalPanel.isOpen()){
	    messageModalPanel.open(target);
	}
    }

    private void doNext(AjaxRequestTarget target) {
	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("gspCancelCustomerLookup.gspPlansRetrieve.loading.label"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("wicketBehavior('next');");
	    return;
	}

	getDailyRhythmPortalSession().setBestBuyCustomer(null);
	for(Customer c: searchCustomersDataProvider.getCustomerList()){
	    if (c.getId() != null && c.getId().longValue() == selectedCustomerId){
		getDailyRhythmPortalSession().setBestBuyCustomer(c);
		break;
	    }
	}
	if (getDailyRhythmPortalSession().getBestBuyCustomer() == null){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    return;
	}
	logger.debug("selectedCustomer=" + getDailyRhythmPortalSession().getBestBuyCustomer());

	logger.debug("updating BBY customer for dataSharingKey=" + dataSharingKey);
	try{
	    BbyAccount acct = BeastObjectMapper.convertToBbyAccount(getDailyRhythmPortalSession().getBestBuyCustomer());
	    acct.setDataSharingKey(dataSharingKey);
	    customerService.updateBbyCustomer(acct);
	}catch(ServiceException se){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to update BBY customer for dataSharingKey="
		    + dataSharingKey;
	    logger.error(message, se);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}

	// set search customer to selected customer
	getDailyRhythmPortalSession().setSearchCustomer(getDailyRhythmPortalSession().getBestBuyCustomer());

	Set<String> cancelGspPlanIdSet = new HashSet<String>();
	logger.debug("getting cancel GSP plans for DSK=" + dataSharingKey);
	try{
	    List<GSPPlan> list = gspPlanService.getGSPPlansMarkedForCancel(dataSharingKey);
	    if (list != null){
		for(GSPPlan gp: list){
		    cancelGspPlanIdSet.add(gp.getProtectionPlanId());
		}
	    }
	}catch(ServiceException se){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get cancel GSP plans for DSK="
		    + dataSharingKey;
	    logger.error(message, se);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}

	logger.debug("getting GSP plans for customer");
	getDailyRhythmPortalSession().setProtectionPlanList(new ArrayList<ProtectionPlan>());
	try{
	    List<ServicePlan> list = customerDataService.getAllServicePlans(getDailyRhythmPortalSession()
		    .getBestBuyCustomer());
	    if (list != null){
		for(ServicePlan sp: list){
		    if (sp instanceof ProtectionPlan){
			if (servicePlanDateGood(sp)){
			    if (cancelGspPlanIdSet.contains(sp.getPlanNumber())){
				sp.setPlanStatus(ServicePlan.PLAN_STATUS_MARKEDFORCANCEL);
			    }
			    getDailyRhythmPortalSession().getProtectionPlanList().add((ProtectionPlan) sp);
			}
		    }
		}
	    }
	    logger.debug("# GSP plans=" + getDailyRhythmPortalSession().getProtectionPlanList().size());
	}catch(ServiceException se){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get service plans";
	    logger.error(message, se);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}catch(IseBusinessException be){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get service plans";
	    logger.error(message);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}
	loadingModalPanel.close(target);
	target.appendJavaScript("clickButton('#nextButton');");
    }

    public static boolean servicePlanDateGood(ServicePlan servicePlan) {
	if (servicePlan == null || servicePlan.getPlanEffectiveDate() == null){
	    return false;
	}
	Calendar numberDaysAgo = Calendar.getInstance();
	numberDaysAgo.add(Calendar.DAY_OF_YEAR, -30);
	Calendar planStartDate = Calendar.getInstance();
	planStartDate.setTime(servicePlan.getPlanEffectiveDate());
	return planStartDate.before(numberDaysAgo);
    }

    private String buildStaticValidationJS() {
	final StringBuilder validationJS = new StringBuilder();
	//
	// phone settings
	validationJS.append("custLookupValidation.phone.exactLength=");
	validationJS.append(Validation.PHONE_NUMBER_SIZE);
	validationJS.append(";");
	//
	// first name settings
	validationJS.append("custLookupValidation.firstName.maxLength=");
	validationJS.append(Validation.FIRST_NAME_MAX_SIZE);
	validationJS.append(";");
	//
	// last name settings
	validationJS.append("custLookupValidation.lastName.maxLength=");
	validationJS.append(Validation.LAST_NAME_MAX_SIZE);
	validationJS.append(";");
	//
	// zip settings
	validationJS.append("custLookupValidation.zip.exactLength=");
	validationJS.append(Validation.ZIP_CODE_SIZE);
	validationJS.append(";");
	return validationJS.toString();
    }

}
