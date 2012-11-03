package com.bestbuy.bbym.ise.drp.customer;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
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
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.Validation;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class SearchCustPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SearchCustPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private CustInfoModalPanel parentModalPanel;
    private CustomerSearchCriteria searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
    private Customer searchCustomer = new Customer(), searchCustCopy = new Customer();
    private SearchCustDataProvider searchCustomerDataProvider = new SearchCustDataProvider();

    public SearchCustPanel(final String id, final CustInfoModalPanel parentModalPanel) {
	super(id);
	this.parentModalPanel = parentModalPanel;

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
	logger.debug("searchCriteria=" + searchCriteria);

	searchCustomer.setAddress(new Address());
	if (session.getSearchCustomer().getAddress() != null){
	    searchCustomer.setAddress(new Address());
	    searchCustomer.getAddress().setZipcode(session.getSearchCustomer().getAddress().getZipcode());
	}
	searchCustomer.setEmail(session.getSearchCustomer().getEmail());
	searchCustomer.setContactPhone(session.getSearchCustomer().getContactPhone());
	searchCustomer.setFirstName(session.getSearchCustomer().getFirstName());
	searchCustomer.setLastName(session.getSearchCustomer().getLastName());
	logger.debug("searchCustomer=" + searchCustomer);

	searchCustCopy.setAddress(new Address());
	searchCustCopy.setEmail(searchCustomer.getEmail());
	searchCustCopy.setContactPhone(searchCustomer.getContactPhone());
	searchCustCopy.setFirstName(searchCustomer.getFirstName());
	searchCustCopy.setLastName(searchCustomer.getLastName());
	searchCustCopy.getAddress().setZipcode(searchCustomer.getAddress().getZipcode());

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
		cellItem.add(new SearchCustPanel.SelectLinkPanel(componentId, rowModel, row,
			getString("custInfo.selectLink.label")));
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

	// no search customers
	final WebMarkupContainer noSearchCustomersContainer = new WebMarkupContainer("noSearchCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (parentModalPanel.getFeedbackPanel().anyErrorMessage()){
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
		if (searchCriteria == CustomerSearchCriteria.PHONE_EMAIL){
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
		"custInfo.searchButton.label"), searchCustomerForm, parentModalPanel.getFeedbackPanel(),
		searchCustomersTable, searchCustomersContainer, noSearchCustomersContainer);
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
		"custInfo.searchButton.label"), searchCustomerForm, parentModalPanel.getFeedbackPanel(),
		searchCustomersTable, searchCustomersContainer, noSearchCustomersContainer);
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
		"custInfo.searchButton.label"), searchCustomerForm, parentModalPanel.getFeedbackPanel(),
		searchCustomersTable, searchCustomersContainer, noSearchCustomersContainer);
	searchCustomerForm.add(emailSearchButton);

	// means by which JS can callback into Java
	final AbstractDefaultAjaxBehavior wicketBehaviorSCP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorSCP id=" + id);
		if ("load".equals(id)){
		    restoreSearchCustomer();
		    logger.debug("searchCustomer=" + searchCustomer);
		    logger.debug("searchCriteria=" + searchCriteria);
		    try{
			searchCustomerDataProvider.setCustomerList(customerDataService.getBBYCustomerProfile(
				searchCustomer, searchCriteria, session.getDrpUser()));
			logger.debug("# search customers=" + searchCustomerDataProvider.size());
		    }catch(ServiceException se){
			searchCustomerDataProvider.setCustomerList(null);
			logger.error("ServiceException is " + se.getFullMessage());
			parentModalPanel.getFeedbackPanel().error(se.getFullMessage());
			target.add(parentModalPanel.getFeedbackPanel());
		    }catch(IseBusinessException be){
			searchCustomerDataProvider.setCustomerList(null);
			logger.error("IseBusinessException is " + be.getFullMessage());
			parentModalPanel.getFeedbackPanel().error(be.getFullMessage());
			target.add(parentModalPanel.getFeedbackPanel());
		    }
		    searchCustomersTable.modelChanged();
		    target.add(searchCustomersContainer);
		    target.add(noSearchCustomersContainer);
		    parentModalPanel.refresh(target);
		}
	    }
	};
	add(wicketBehaviorSCP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehaviorSCP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorSCP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    private class SearchCustomerButton extends AjaxButton {

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel searchCustomerFeedbackPanel;
	private final AjaxFallbackDefaultDataTable<Customer> searchCustomersTable;
	private final WebMarkupContainer searchCustomersContainer, noSearchCustomersContainer;

	public SearchCustomerButton(String id, IModel<String> model, final Form<?> form,
		final FeedbackPanel searchCustomerFeedbackPanel,
		final AjaxFallbackDefaultDataTable<Customer> searchCustomersTable,
		final WebMarkupContainer searchCustomersContainer, final WebMarkupContainer noSearchCustomersContainer) {
	    super(id, model, form);
	    setOutputMarkupPlaceholderTag(true);
	    setMarkupId(id);
	    setOutputMarkupId(true);
	    this.searchCustomerFeedbackPanel = searchCustomerFeedbackPanel;
	    this.searchCustomersTable = searchCustomersTable;
	    this.searchCustomersContainer = searchCustomersContainer;
	    this.noSearchCustomersContainer = noSearchCustomersContainer;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onSubmit searchCriteria=" + searchCriteria);
	    target
		    .appendJavaScript("showBestBuyCustomerSearchLoading(true);doWicketBehavior('wicketBehaviorSCP(\"load\")');");
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onError");
	    target.add(searchCustomerFeedbackPanel);
	    searchCustomerDataProvider.setCustomerList(null);
	    DailyRhythmPortalSession session = (DailyRhythmPortalSession) getSession();
	    session.setSearchCustomers(null);
	    searchCustomersTable.modelChanged();
	    target.add(searchCustomersContainer);
	    target.add(noSearchCustomersContainer);
	    parentModalPanel.refresh(target);
	}
    }

    public String getOpenPanelJS() {
	if (!isVisible()){
	    return "";
	}
	StringBuilder openPanelJS = new StringBuilder();
	openPanelJS.append("showBestBuyCustomerSearchLoading(false);");
	openPanelJS.append("selectSearchCustomersNavFilter('");
	openPanelJS.append(searchCriteria.name());
	openPanelJS.append("');");
	openPanelJS.append("setupSearchNav();");
	if (searchCustomerDataProvider.size() > 0){
	    openPanelJS.append("tablePrep(custInfoModalSearchCustomersTable);");
	    openPanelJS.append("setupSelectBestBuyCustomerLoading();");
	}
	return openPanelJS.toString();
    }

    public String getRefreshPanelJS() {
	return getOpenPanelJS();
    }

    @Override
    public boolean isVisible() {
	if (parentModalPanel.getModalState() == CustInfoModalPanel.ModalState.SELECT_REWARD_ZONE){
	    return false;
	}
	return parentModalPanel.getSelectedCustomer().getId() == null;
    }

    class SelectLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectLinkPanel(String id, final IModel<?> model, final Customer row, final String label) {
	    super(id, model);

	    AjaxLink<Object> link = new AjaxLink<Object>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("selectLinkPanel onClick");
		    parentModalPanel.setSelectedCustomer((Customer) Util.clone(row));
		    parentModalPanel.getSelectedCustomer().setId(new Long(0L));
		    logger.debug("selectedCustomer=" + parentModalPanel.getSelectedCustomer());
		    parentModalPanel.refresh(target);
		}
	    };
	    link.setOutputMarkupPlaceholderTag(true);
	    link.add(new Label("label", label));
	    add(link);
	}
    }

    private void restoreSearchCustomer() {
	switch (searchCriteria) {
	    case FN_LN_ZIP:
		searchCustCopy.setFirstName(searchCustomer.getFirstName());
		searchCustCopy.setLastName(searchCustomer.getLastName());
		if (searchCustomer.getAddress() == null){
		    searchCustCopy.getAddress().setZipcode(null);
		}else{
		    searchCustCopy.getAddress().setZipcode(searchCustomer.getAddress().getZipcode());
		}
		searchCustomer.setEmail(searchCustCopy.getEmail());
		searchCustomer.setContactPhone(searchCustCopy.getContactPhone());
		break;
	    case EMAIL:
		searchCustomer.setFirstName(searchCustCopy.getFirstName());
		searchCustomer.setLastName(searchCustCopy.getLastName());
		if (searchCustomer.getAddress() == null){
		    searchCustomer.setAddress(new Address());
		}
		searchCustomer.getAddress().setZipcode(searchCustCopy.getAddress().getZipcode());
		searchCustCopy.setEmail(searchCustomer.getEmail());
		searchCustomer.setContactPhone(searchCustCopy.getContactPhone());
		break;
	    case PHONE_NUMBER:
		searchCustomer.setFirstName(searchCustCopy.getFirstName());
		searchCustomer.setLastName(searchCustCopy.getLastName());
		if (searchCustomer.getAddress() == null){
		    searchCustomer.setAddress(new Address());
		}
		searchCustomer.getAddress().setZipcode(searchCustCopy.getAddress().getZipcode());
		searchCustomer.setEmail(searchCustCopy.getEmail());
		searchCustCopy.setContactPhone(searchCustomer.getContactPhone());
		break;
	}

    }

}
