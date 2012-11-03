package com.bestbuy.bbym.ise.drp.dashboard;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.LoadingModalAjaxButton;
import com.bestbuy.bbym.ise.drp.common.Validation;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.PageError;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.exception.IseBaseException;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class SearchCustomerPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SearchCustomerPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    @SpringBean(name = "customerService")
    private CustomerService customerService;

    @SpringBean(name = "rewardZoneService")
    private RewardZoneService rewardZoneService;

    private CustomerSearchCriteria searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
    private Customer searchCustomer = new Customer();

    // SPLUNK LOG FORMAT
    private static final String BBY_ONLY_LOG_FORMAT = "SPLUNK FILTER BBY_CUSTOMER data : SESSION ID={0}, USER_ID={2}, BBY_CUSTOMER_FOUND={1}";
    private static final String BBY_ONLY_EXCEPTION_LOG_FORMAT = "SPLUNK FILTER BBY_CUSTOMER data : SESSION ID={0}, USER_ID={2}, BBY_CUSTOMER_FOUND={1}, ERROR={3}";

    public SearchCustomerPanel(String id) {
	super(id);

	populateSearchCriteria();

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	searchCustomer.setAddress(new Address());
	if (session.getSearchCustomer().getAddress() != null){
	    searchCustomer.setAddress(new Address());
	    searchCustomer.getAddress().setZipcode(session.getSearchCustomer().getAddress().getZipcode());
	}
	searchCustomer.setEmail(session.getSearchCustomer().getEmail());
	searchCustomer.setContactPhone(session.getSearchCustomer().getContactPhone());
	searchCustomer.setFirstName(session.getSearchCustomer().getFirstName());
	searchCustomer.setLastName(session.getSearchCustomer().getLastName());
	searchCustomer.setRewardZoneId(session.getSearchCustomer().getRewardZoneId());
	logger.debug("searchCustomer=" + searchCustomer);

	final SearchCustomerDataProvider searchCustomerDataProvider = new SearchCustomerDataProvider();
	searchCustomerDataProvider.setCustomerList(session.getSearchCustomers());
	if (session.getSearchCustomers() == null){
	    logger.debug("searchCustomers=null");
	}else{
	    logger.debug("searchCustomers.size=" + session.getSearchCustomers().size());
	}

	final List<IColumn<Customer>> columns = new ArrayList<IColumn<Customer>>();
	columns.add(new PropertyColumn<Customer>(new ResourceModel("searchCustomerPanel.lastName.column"), "lastName",
		"lastName"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("searchCustomerPanel.firstName.column"),
		"firstName", "firstName"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("searchCustomerPanel.address.column"),
		"address.addressLine1", "address.addressLine1"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("searchCustomerPanel.city.column"), "address.city",
		"address.city"));
	columns.add(new PropertyColumn<Customer>(new ResourceModel("searchCustomerPanel.state.column"),
		"address.state", "address.state"));
	columns.add(new FormatPropertyColumn<Customer, String>(new ResourceModel("searchCustomerPanel.phone.column"),
		"contactPhone", "contactPhone", new PhoneFormatter<String>()));
	columns.add(new AbstractColumn<Customer>(null, "lastName") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Customer row = (Customer) rowModel.getObject();
		cellItem.add(new SearchCustomerPanel.ViewLinkPanel(componentId, rowModel, row,
			getString("searchCustomerPanel.viewLink.label")));
	    }
	});

	final WebMarkupContainer searchCustomersContainer = new WebMarkupContainer("searchCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return searchCustomerDataProvider.size() > 0;
	    }
	};
	searchCustomersContainer.setMarkupId("searchCustomersContainer");
	searchCustomersContainer.setOutputMarkupId(true);
	searchCustomersContainer.setOutputMarkupPlaceholderTag(true);
	add(searchCustomersContainer);

	final AjaxFallbackDefaultDataTable<Customer> searchCustomersTable = new AjaxFallbackDefaultDataTable<Customer>(
		"searchCustomersTable", columns, searchCustomerDataProvider, 200);
	searchCustomersTable.setMarkupId("searchCustomersTable");
	searchCustomersTable.setOutputMarkupId(true);
	searchCustomersTable.setOutputMarkupPlaceholderTag(true);
	searchCustomersContainer.add(searchCustomersTable);

	final Form<Object> searchCustomerForm = new Form<Object>("searchCustomerForm");
	searchCustomerForm.setOutputMarkupPlaceholderTag(true);
	add(searchCustomerForm);

	FeedbackMessageFilter filter = new FeedbackMessageFilter("SearchCustomer");
	filter.setAcceptAllUnspecifiedComponents(false);
	filter.addFilterInComponent(searchCustomerForm);

	final FeedbackPanel searchCustomerFeedbackPanel = new FeedbackPanel("searchCustomerFeedback", filter);
	searchCustomerFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(searchCustomerFeedbackPanel);

	// no search customers
	final WebMarkupContainer noSearchCustomersContainer = new WebMarkupContainer("noSearchCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (searchCustomerFeedbackPanel.anyErrorMessage()){
		    return false;
		}
		if (searchCustomerDataProvider.size() > 0){
		    return false;
		}
		if (searchCustomer == null){
		    return false;
		}
		if (searchCriteria == CustomerSearchCriteria.PHONE_NUMBER && searchCustomer.getContactPhone() != null){
		    return true;
		}else if (searchCriteria == CustomerSearchCriteria.EMAIL && searchCustomer.getEmail() != null){
		    return true;
		}else if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP && searchCustomer.getFirstName() != null
			&& searchCustomer.getLastName() != null && searchCustomer.getAddress() != null
			&& searchCustomer.getAddress().getZipcode() != null){
		    return true;
		}
		return false;
	    }
	};
	noSearchCustomersContainer.setMarkupId("noSearchCustomersContainer");
	noSearchCustomersContainer.setOutputMarkupId(true);
	noSearchCustomersContainer.setOutputMarkupPlaceholderTag(true);
	add(noSearchCustomersContainer);

	final Label noSearchCustomersLabel = new Label("noSearchCustomersLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (session.getAutomatedSearchCustomerCriteria() == CustomerSearchCriteria.PHONE_EMAIL){
		    logger.debug("no customers for search criteria of phone/email ");
		    return getString("searchCustomerPanel.noPhoneEmailSearchCustomersLabel.label");

		}else if (searchCriteria == CustomerSearchCriteria.EMAIL){
		    logger.debug("no customers for search criteria of email");
		    return getString("searchCustomerPanel.noEmailSearchCustomersLabel.label");

		}else if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
		    logger.debug("no customers for search criteria of fn ln zip");
		    return getString("searchCustomerPanel.noFnLnZipSearchCustomersLabel.label");
		}
		return getString("searchCustomerPanel.noPhoneSearchCustomersLabel.label");
	    }
	});
	noSearchCustomersLabel.setOutputMarkupPlaceholderTag(true);
	noSearchCustomersContainer.add(noSearchCustomersLabel);

	// Phone Filter

	// phone search field
	final TextField<String> phoneSearchField = new RequiredTextField<String>("phoneSearch",
		new PropertyModel<String>(searchCustomer, "contactPhone")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchCriteria == CustomerSearchCriteria.PHONE_NUMBER){
		    logger.debug("checking validity of phone");
		    super.validate();
		}
	    }
	};
	phoneSearchField.setOutputMarkupPlaceholderTag(true);
	phoneSearchField.setRequired(true);
	phoneSearchField.add(new PatternValidator("\\d{" + Validation.PHONE_NUMBER_SIZE + "}"));
	searchCustomerForm.add(phoneSearchField);

	AjaxLink<Object> phoneSearchLink = new AjaxLink<Object>("phoneSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
		logger.debug("phoneSearchLink onClick");
	    }
	};
	phoneSearchLink.setOutputMarkupPlaceholderTag(true);
	searchCustomerForm.add(phoneSearchLink);

	// phone search button
	SearchCustomerButton phoneSearchButton = new SearchCustomerButton("phoneSearchButton", new ResourceModel(
		"searchCustomerForm.search.button"), searchCustomerForm, searchCustomerDataProvider,
		searchCustomerFeedbackPanel, searchCustomersTable, searchCustomersContainer, noSearchCustomersContainer);
	searchCustomerForm.add(phoneSearchButton);

	// Name & ZIP Filter

	// first name search field
	final TextField<String> firstNameSearchField = new RequiredTextField<String>("firstNameSearch",
		new PropertyModel<String>(searchCustomer, "firstName")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
		    logger.debug("checking validity of firstName");
		    super.validate();
		}
	    }
	};
	firstNameSearchField.setOutputMarkupPlaceholderTag(true);
	firstNameSearchField.setRequired(true);
	firstNameSearchField.add(new PatternValidator("[A-Za-z -]{1,30}"));
	searchCustomerForm.add(firstNameSearchField);

	// last name search field
	final TextField<String> lastNameSearchField = new RequiredTextField<String>("lastNameSearch",
		new PropertyModel<String>(searchCustomer, "lastName")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
		    logger.debug("checking validity of lastName");
		    super.validate();
		}
	    }
	};
	lastNameSearchField.setOutputMarkupPlaceholderTag(true);
	lastNameSearchField.setRequired(true);
	lastNameSearchField.add(new PatternValidator("[A-Za-z -]{1,30}"));
	searchCustomerForm.add(lastNameSearchField);

	// zip code search field
	final TextField<String> zipCodeSearchField = new RequiredTextField<String>("zipCodeSearch",
		new PropertyModel<String>(searchCustomer, "address.zipcode")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
		    logger.debug("checking validity of zipCode");
		    super.validate();
		}
	    }
	};
	zipCodeSearchField.setOutputMarkupPlaceholderTag(true);
	zipCodeSearchField.setRequired(true);
	zipCodeSearchField.add(new PatternValidator("\\d{" + Validation.ZIP_CODE_SIZE + "}"));
	searchCustomerForm.add(zipCodeSearchField);

	AjaxLink<Object> nameZipSearchLink = new AjaxLink<Object>("nameZipSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.FN_LN_ZIP;
		logger.debug("nameZipSearchLink onClick");
	    }
	};
	nameZipSearchLink.setOutputMarkupPlaceholderTag(true);
	searchCustomerForm.add(nameZipSearchLink);

	// name/zip search button
	SearchCustomerButton nameZipSearchButton = new SearchCustomerButton("nameZipSearchButton", new ResourceModel(
		"searchCustomerForm.search.button"), searchCustomerForm, searchCustomerDataProvider,
		searchCustomerFeedbackPanel, searchCustomersTable, searchCustomersContainer, noSearchCustomersContainer);
	searchCustomerForm.add(nameZipSearchButton);

	// Email Filter

	// email search field

	final TextField<String> emailSearchField = new RequiredTextField<String>("emailSearch",
		new PropertyModel<String>(searchCustomer, "email")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchCriteria == CustomerSearchCriteria.EMAIL){
		    logger.debug("checking validity of email");
		    super.validate();
		}
	    }
	};
	emailSearchField.setOutputMarkupPlaceholderTag(true);
	emailSearchField.setRequired(true);
	emailSearchField.add(EmailAddressValidator.getInstance());
	searchCustomerForm.add(emailSearchField);

	AjaxLink<Object> emailSearchLink = new AjaxLink<Object>("emailSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.EMAIL;
		logger.debug("emailSearchLink onClick");
	    }
	};
	emailSearchLink.setOutputMarkupPlaceholderTag(true);
	searchCustomerForm.add(emailSearchLink);

	// email search button
	SearchCustomerButton emailSearchButton = new SearchCustomerButton("emailSearchButton", new ResourceModel(
		"searchCustomerForm.search.button"), searchCustomerForm, searchCustomerDataProvider,
		searchCustomerFeedbackPanel, searchCustomersTable, searchCustomersContainer, noSearchCustomersContainer);
	searchCustomerForm.add(emailSearchButton);

	filter.addFilterInComponent(emailSearchField);
	filter.addFilterInComponent(phoneSearchField);
	filter.addFilterInComponent(firstNameSearchField);
	filter.addFilterInComponent(lastNameSearchField);
	filter.addFilterInComponent(zipCodeSearchField);

	if (session.getPageError() != null && session.getPageError().doesApply(SearchCustomerPanel.class)){
	    searchCustomerForm.error(session.getPageError().getMessage());
	    session.setPageError(null);
	}

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		if (searchCustomerDataProvider.size() > 0){
		    onDomReadyJS.append("tablePrep(searchCustomersTable);");
		    onDomReadyJS.append("setupSelectBestBuyCustomerLoading();");
		}
		onDomReadyJS.append("selectSearchCustomersNavFilter('");
		onDomReadyJS.append(searchCriteria.name());
		onDomReadyJS.append("');");
		onDomReadyJS.append("setupSearchNav();");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    /**
     * Hides the panel if we don't have BestBuy Customer details.
     */
    @Override
    public boolean isVisible() {
	return getDailyRhythmPortalSession().getBestBuyCustomer() == null;
    }

    /**
     * Populates the search criteria from the session
     */
    private void populateSearchCriteria() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (session.getSearchCustomerCriteria() != null){
	    switch (session.getSearchCustomerCriteria()) {
		case FN_LN_ZIP:
		    searchCriteria = CustomerSearchCriteria.FN_LN_ZIP;
		    break;
		case EMAIL:
		    searchCriteria = CustomerSearchCriteria.EMAIL;
		    break;
		case PHONE_NUMBER:
		    searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
		    break;
	    }
	}
	if (session.getAutomatedSearchCustomerCriteria() != null){
	    switch (session.getAutomatedSearchCustomerCriteria()) {
		case FN_LN_ZIP:
		    searchCriteria = CustomerSearchCriteria.FN_LN_ZIP;
		    session.setSearchCustomerCriteria(CustomerSearchCriteria.FN_LN_ZIP);
		    break;
		case PHONE_EMAIL:
		case EMAIL:
		    searchCriteria = CustomerSearchCriteria.EMAIL;
		    session.setSearchCustomerCriteria(CustomerSearchCriteria.EMAIL);
		    break;
		case PHONE_NUMBER:
		    searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
		    session.setSearchCustomerCriteria(CustomerSearchCriteria.PHONE_NUMBER);
		    break;
	    }
	}
	logger.debug("searchCriteria=" + searchCriteria);
    }

    class ViewLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ViewLinkPanel(String id, final IModel<?> model, final Customer row, final String label) {
	    super(id, model);

	    AjaxLink<Object> link = new AjaxLink<Object>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    try{
			Customer bestBuyCustomer = new Customer();
			BeanUtils.copyProperties(row, bestBuyCustomer);
			session.setBestBuyCustomer(bestBuyCustomer);
			session.getBestBuyCustomer().setDataSharingKey(session.getSearchCustomer().getDataSharingKey());
			session.getBestBuyCustomer().setAcctNumber(null);
			try{
			    logger.debug("saving BestBuy customer DSK="
				    + session.getBestBuyCustomer().getDataSharingKey());
			    customerService.createCustomer(session.getBestBuyCustomer(), session.getDrpUser());
			    session.getSearchCustomer().setDataSharingKey(
				    session.getBestBuyCustomer().getDataSharingKey());
			    logger.debug("saved BestBuy customer DSK="
				    + session.getBestBuyCustomer().getDataSharingKey());

			    // Service call to populate customer reward zone details...
			    populateCustomerRewardZone();

			}catch(ServiceException se){
			    logger.warn("Exception while saving BestBuy customer to BEAST", se);
			}
			logger.debug("bestBuyCustomer=" + session.getBestBuyCustomer());
			session
				.setServicePlanList(customerDataService
					.getAllServicePlans(session.getBestBuyCustomer()));
			if (session.getServicePlanList() == null){
			    session.setServicePlanList(new ArrayList<ServicePlan>());
			}
			logger.debug("# servicePlan items=" + session.getServicePlanList().size());
		    }catch(IseBusinessException be){
			logger.error("IseBusinessException is " + be.getFullMessage());
			session.setPageError(new PageError(be.getFullMessage(), GspTabPanel.class));
			session.setServicePlanList(null);
		    }catch(ServiceException se){
			logger.error("ServiceException is " + se.getFullMessage());
			session.setServicePlanList(null);
			session.setAllPurchaseHistory(null);
			session.setMobilePurchaseHistory(null);
			session.setPlanDetails(null);
			session.setPageError(new PageError(se.getFullMessage(), GspTabPanel.class));
			session.setServicePlanList(null);
		    }catch(Exception e){
			session.clearBestBuyCustomer();
			String message = "An unexpected exception occured while attempting to select Best Buy customer";
			logger.error(message, e);
			if (e instanceof RuntimeException){
			    throw (RuntimeException) e;
			}
			processException(message, new IseBaseException(IseExceptionCodeEnum.DataConversionError, e
				.getMessage()), new PageParameters());
			return;
		    }
		    session.setPurchaseHistoryStartDate(null);
		    session.setPurchaseHistoryEndDate(null);
		    session.setMobilePurchaseHistoryStartDate(null);
		    session.setMobilePurchaseHistoryEndDate(null);
		    setResponsePage(CustomerDashboardPage.class);
		}
	    };
	    link.setOutputMarkupPlaceholderTag(true);
	    link.add(new Label("label", label));
	    add(link);
	}
    }

    private class SearchCustomerButton extends LoadingModalAjaxButton {

	private static final long serialVersionUID = 1L;

	private SearchCustomerDataProvider searchCustomerDataProvider;
	private FeedbackPanel searchCustomerFeedbackPanel;
	private AjaxFallbackDefaultDataTable<Customer> searchCustomersTable;
	private WebMarkupContainer searchCustomersContainer, noSearchCustomersContainer;

	public SearchCustomerButton(String id, IModel<String> model, final Form<?> form,
		SearchCustomerDataProvider searchCustomerDataProvider, FeedbackPanel searchCustomerFeedbackPanel,
		AjaxFallbackDefaultDataTable<Customer> searchCustomersTable,
		WebMarkupContainer searchCustomersContainer, WebMarkupContainer noSearchCustomersContainer) {
	    super(id, form, model, new ResourceModel("loadingModalMessage.bbyCustomerSearch"));
	    setOutputMarkupPlaceholderTag(true);
	    setMarkupId(id);
	    setOutputMarkupId(true);
	    this.searchCustomerDataProvider = searchCustomerDataProvider;
	    this.searchCustomerFeedbackPanel = searchCustomerFeedbackPanel;
	    this.searchCustomersTable = searchCustomersTable;
	    this.searchCustomersContainer = searchCustomersContainer;
	    this.noSearchCustomersContainer = noSearchCustomersContainer;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onSubmit searchCriteria=" + searchCriteria);

	    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	    StringBuilder loadJS = new StringBuilder();
	    session.setAutomatedSearchCustomerCriteria(null);
	    if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
		session.setSearchCustomerCriteria(CustomerSearchCriteria.FN_LN_ZIP);
		session.getSearchCustomer().setFirstName(searchCustomer.getFirstName());
		session.getSearchCustomer().setLastName(searchCustomer.getLastName());
		if (session.getSearchCustomer().getAddress() == null){
		    session.getSearchCustomer().setAddress(new Address());
		}
		session.getSearchCustomer().getAddress().setZipcode(searchCustomer.getAddress().getZipcode());

	    }else if (searchCriteria == CustomerSearchCriteria.EMAIL){
		session.setSearchCustomerCriteria(CustomerSearchCriteria.EMAIL);
		session.getSearchCustomer().setEmail(searchCustomer.getEmail());

	    }else if (searchCriteria == CustomerSearchCriteria.PHONE_NUMBER){
		session.setSearchCustomerCriteria(CustomerSearchCriteria.PHONE_NUMBER);
		session.getSearchCustomer().setContactPhone(searchCustomer.getContactPhone());
	    }
	    logger.debug("searchCustomer=" + session.getSearchCustomer());
	    try{
		session.setSearchCustomers(customerDataService.getBBYCustomerProfile(searchCustomer, searchCriteria,
			session.getDrpUser()));
		// if (session.getSearchCustomers() == null){
		// session.setSearchCustomers(new
		// ArrayList<Customer>());
		// } FIXME do i need this

		if (session.getSearchCustomers() != null && !session.getSearchCustomers().isEmpty()){
		    //SPLUNK FILTER BBY_CUSTOMER data : SESSION ID={0}, USER_ID={2}, BBY_CUSTOMER_FOUND={1} 
		    logger.info(MessageFormat.format(BBY_ONLY_LOG_FORMAT, session.getId(), true, session.getDrpUser()
			    .getUserId()));
		}else{
		    logger.info(MessageFormat.format(BBY_ONLY_LOG_FORMAT, session.getId(), false, session.getDrpUser()
			    .getUserId()));
		}

		searchCustomerDataProvider.setCustomerList(session.getSearchCustomers());
		logger.debug("# search customers=" + searchCustomerDataProvider.size());
	    }catch(ServiceException se){
		logger.error("ServiceException is " + se.getFullMessage());
		session.setSearchCustomers(null);
		searchCustomerDataProvider.setCustomerList(null);
		logger.info(MessageFormat.format(BBY_ONLY_EXCEPTION_LOG_FORMAT, session.getId(), false, session
			.getDrpUser().getUserId(), "SERVICE_EXCEPTION"));
		getForm().error(se.getFullMessage());
		target.add(searchCustomerFeedbackPanel);
		target.add(searchCustomersContainer);
		target.add(noSearchCustomersContainer);
		return;
	    }catch(IseBusinessException be){
		logger.error("IseBusinessException is " + be.getFullMessage());
		session.setSearchCustomers(null);
		logger.info(MessageFormat.format(BBY_ONLY_EXCEPTION_LOG_FORMAT, session.getId(), false, session
			.getDrpUser().getUserId(), "ISE_BUSINESS_EXCEPTION"));
		searchCustomerDataProvider.setCustomerList(null);
		getForm().error(be.getFullMessage());
		target.add(searchCustomerFeedbackPanel);
		target.add(searchCustomersContainer);
		target.add(noSearchCustomersContainer);
		return;
	    }
	    searchCustomersTable.modelChanged();
	    target.add(searchCustomersContainer);
	    target.add(noSearchCustomersContainer);
	    if (searchCustomerDataProvider.size() > 0){
		loadJS.append("tablePrep(searchCustomersTable);");
		loadJS.append("setupSelectBestBuyCustomerLoading();");
	    }
	    logger.debug("loadJS=" + loadJS.toString());
	    target.appendJavaScript(loadJS.toString());
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onError");
	    target.add(searchCustomerFeedbackPanel);
	    searchCustomerDataProvider.setCustomerList(null);
	    getDailyRhythmPortalSession().setSearchCustomers(null);
	    searchCustomersTable.modelChanged();
	    target.add(searchCustomersContainer);
	    target.add(noSearchCustomersContainer);
	}
    }

    /**
     * Method call to make RZ Service calls and populate the customer
     */
    private void populateCustomerRewardZone() {

	Customer customer = getDailyRhythmPortalSession().getBestBuyCustomer();

	if (customer != null){
	    String rewardZoneId = customer.getRewardZoneId();
	    logger.debug("rewardZoneId :: " + rewardZoneId);
	    try{
		if (StringUtils.isNotBlank(rewardZoneId)){
		    customer.setRewardZone(rewardZoneService.validateRewardZoneMember(rewardZoneId));
		}
	    }catch(ServiceException se){
		logger.debug("Service Exception .... " + se.getFullMessage());
	    }catch(IseBusinessException be){
		logger.debug("ISE Bussiness Exception .... " + be.getFullMessage());
	    }

	}
    }
}
