package com.bestbuy.bbym.ise.drp.entitlement;

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
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.RetExchRZLookupCustomer;
import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.loanerphone.CustomModalWindow;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

class RewardZoneLookupModalPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(RewardZoneLookupModalPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    @SpringBean(name = "rewardZoneService")
    private RewardZoneService rewardZoneService;

    private CustomerSearchCriteria searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
    private Customer searchRzCustomer = new Customer();
    private List<RetExchRZLookupCustomer> searchRzCustomerList = new ArrayList<RetExchRZLookupCustomer>();
    private FeedbackPanel searchRzCustomerFeedbackPanel;

    public RewardZoneLookupModalPanel(String id, final CustomModalWindow rewardZoneLookupModalWindow,
	    final RetExchRZLookupCustomer selectedRzCustomer) {
	super(id);
	logger.info("inside RewardZoneLookupModalPanel constructor");

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	searchRzCustomer.setAddress(new Address());
	if (session.getSearchCustomer().getAddress() != null){
	    searchRzCustomer.setAddress(new Address());
	    searchRzCustomer.getAddress().setZipcode(session.getSearchCustomer().getAddress().getZipcode());
	}
	searchRzCustomer.setEmail(session.getSearchCustomer().getEmail());
	searchRzCustomer.setContactPhone(session.getSearchCustomer().getContactPhone());
	searchRzCustomer.setFirstName(session.getSearchCustomer().getFirstName());
	searchRzCustomer.setLastName(session.getSearchCustomer().getLastName());
	logger.debug("searchRzCustomer=" + searchRzCustomer);

	//RetExchRZLookupCustomer
	final RewardZoneLookupDataProvider rewardZoneLookupDataProvider = new RewardZoneLookupDataProvider();

	//TODOK -commenting for now
	//rewardZoneLookupDataProvider.setCustomerList(session.getSearchCustomers());
	//end TODOK
	if (session.getSearchCustomers() == null){
	    logger.debug("searchCustomers=null");
	}else{
	    logger.debug("searchCustomers.size=" + session.getSearchCustomers().size());
	}

	final List<IColumn<RetExchRZLookupCustomer>> columns = new ArrayList<IColumn<RetExchRZLookupCustomer>>();
	columns.add(new PropertyColumn<RetExchRZLookupCustomer>(new ResourceModel(
		"rewardZoneLookupModalPanel.rzMemberDetails.column"), "rzMemberDetails", "rzMemberDetails"));
	columns.add(new PropertyColumn<RetExchRZLookupCustomer>(new ResourceModel(
		"rewardZoneLookupModalPanel.phone.column"), "customer.contactPhone", "customer.contactPhone"));
	columns.add(new PropertyColumn<RetExchRZLookupCustomer>(new ResourceModel(
		"rewardZoneLookupModalPanel.rzNumber.column"), "rzNumber", "rzNumber"));
	columns.add(new PropertyColumn<RetExchRZLookupCustomer>(new ResourceModel(
		"rewardZoneLookupModalPanel.rzTier.column"), "rzTier", "rzTier"));
	columns.add(new PropertyColumn<RetExchRZLookupCustomer>(new ResourceModel(
		"rewardZoneLookupModalPanel.rzPoints.column"), "rzPoints", "rzPoints"));

	columns.add(new AbstractColumn<RetExchRZLookupCustomer>(null, "rzMemberDetails") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		RetExchRZLookupCustomer row = (RetExchRZLookupCustomer) rowModel.getObject();
		cellItem.add(new RewardZoneLookupModalPanel.SelectRZPanel(componentId, rowModel, row,
			getString("rewardZoneLookupModalPanel.selectLink.label"), rewardZoneLookupModalWindow,
			searchRzCustomerFeedbackPanel, selectedRzCustomer));
	    }

	});

	final WebMarkupContainer searchRzCustomersContainer = new WebMarkupContainer("searchRzCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return rewardZoneLookupDataProvider.size() > 0;
	    }
	};
	searchRzCustomersContainer.setMarkupId("searchRzCustomersContainer");
	searchRzCustomersContainer.setOutputMarkupId(true);
	searchRzCustomersContainer.setOutputMarkupPlaceholderTag(true);
	add(searchRzCustomersContainer);

	final AjaxFallbackDefaultDataTable<RetExchRZLookupCustomer> searchRzCustomersTable = new AjaxFallbackDefaultDataTable<RetExchRZLookupCustomer>(
		"searchRzCustomersTable", columns, rewardZoneLookupDataProvider, 200);

	searchRzCustomersTable.setOutputMarkupPlaceholderTag(true);
	searchRzCustomersTable.setOutputMarkupId(true);
	searchRzCustomersContainer.add(searchRzCustomersTable);

	final Form<Object> searchRzCustomerForm = new Form<Object>("searchRzCustomerForm");
	searchRzCustomerForm.setOutputMarkupPlaceholderTag(true);
	add(searchRzCustomerForm);

	FeedbackMessageFilter filter = new FeedbackMessageFilter("SearchRzCustomer");
	filter.setAcceptAllUnspecifiedComponents(false);
	filter.addFilterInComponent(searchRzCustomerForm);

	searchRzCustomerFeedbackPanel = new FeedbackPanel("searchCustomerFeedback", filter);
	searchRzCustomerFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	searchRzCustomerFeedbackPanel.setOutputMarkupId(true);
	searchRzCustomerFeedbackPanel.setVisible(true);
	add(searchRzCustomerFeedbackPanel);

	// no search RZ customers
	final WebMarkupContainer noSearchRzCustomersContainer = new WebMarkupContainer("noSearchRzCustomersContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (searchRzCustomerFeedbackPanel.anyErrorMessage()){
		    return false;
		}
		if (rewardZoneLookupDataProvider.size() > 0){
		    return false;
		}
		if (searchRzCustomer == null){
		    return false;
		}
		if (searchCriteria == CustomerSearchCriteria.PHONE_NUMBER && searchRzCustomer.getContactPhone() != null){
		    return true;
		}else if (searchCriteria == CustomerSearchCriteria.EMAIL && searchRzCustomer.getEmail() != null){
		    return true;
		}else if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP && searchRzCustomer.getFirstName() != null
			&& searchRzCustomer.getLastName() != null && searchRzCustomer.getAddress() != null
			&& searchRzCustomer.getAddress().getZipcode() != null){
		    return true;
		}
		return false;
	    }
	};
	noSearchRzCustomersContainer.setMarkupId("noSearchRzCustomersContainer");
	noSearchRzCustomersContainer.setOutputMarkupId(true);
	noSearchRzCustomersContainer.setOutputMarkupPlaceholderTag(true);
	add(noSearchRzCustomersContainer);

	final Label noSearchRzCustomersLabel = new Label("noSearchRzCustomersLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		//if (session.getAutomatedSearchCustomerCriteria() == CustomerSearchCriteria.PHONE_EMAIL){searchCriteria
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
	noSearchRzCustomersLabel.setOutputMarkupPlaceholderTag(true);
	noSearchRzCustomersLabel.setVisible(false);
	//noSearchRzCustomersLabel.setVisible(false); for now 09/04
	noSearchRzCustomersContainer.add(noSearchRzCustomersLabel);

	// Phone Filter

	// phone search field
	final TextField<String> phoneSearchField = new RequiredTextField<String>("phoneSearch",
		new PropertyModel<String>(searchRzCustomer, "contactPhone")) {

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
	searchRzCustomerForm.add(phoneSearchField);

	AjaxLink<Object> phoneSearchLink = new AjaxLink<Object>("phoneSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
		logger.debug("phoneSearchLink onClick");
	    }
	};
	phoneSearchLink.setOutputMarkupPlaceholderTag(true);
	searchRzCustomerForm.add(phoneSearchLink);

	// phone search button
	SearchRzCustomerButton phoneSearchButton = new SearchRzCustomerButton("phoneSearchButton", new ResourceModel(
		"searchRzCustomerForm.search.button"), searchRzCustomerForm, rewardZoneLookupDataProvider,
		searchRzCustomerFeedbackPanel, searchRzCustomersTable, searchRzCustomersContainer,
		noSearchRzCustomersContainer);
	searchRzCustomerForm.add(phoneSearchButton);

	// Name & ZIP Filter

	// first name search field
	final TextField<String> firstNameSearchField = new RequiredTextField<String>("firstNameSearch",
		new PropertyModel<String>(searchRzCustomer, "firstName")) {

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
	searchRzCustomerForm.add(firstNameSearchField);

	// last name search field
	final TextField<String> lastNameSearchField = new RequiredTextField<String>("lastNameSearch",
		new PropertyModel<String>(searchRzCustomer, "lastName")) {

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
	searchRzCustomerForm.add(lastNameSearchField);

	// zip code search field
	final TextField<String> zipCodeSearchField = new RequiredTextField<String>("zipCodeSearch",
		new PropertyModel<String>(searchRzCustomer, "address.zipcode")) {

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
	searchRzCustomerForm.add(zipCodeSearchField);

	AjaxLink<Object> nameZipSearchLink = new AjaxLink<Object>("nameZipSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.FN_LN_ZIP;
		logger.debug("nameZipSearchLink onClick");
	    }
	};
	nameZipSearchLink.setOutputMarkupPlaceholderTag(true);
	searchRzCustomerForm.add(nameZipSearchLink);

	// name/zip search button
	SearchRzCustomerButton nameZipSearchButton = new SearchRzCustomerButton("nameZipSearchButton",
		new ResourceModel("searchRzCustomerForm.search.button"), searchRzCustomerForm,
		rewardZoneLookupDataProvider, searchRzCustomerFeedbackPanel, searchRzCustomersTable,
		searchRzCustomersContainer, noSearchRzCustomersContainer);
	searchRzCustomerForm.add(nameZipSearchButton);

	// Email Filter

	// email search field

	final TextField<String> emailSearchField = new RequiredTextField<String>("emailSearch",
		new PropertyModel<String>(searchRzCustomer, "email")) {

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
	searchRzCustomerForm.add(emailSearchField);

	AjaxLink<Object> emailSearchLink = new AjaxLink<Object>("emailSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.EMAIL;
		logger.debug("emailSearchLink onClick");
	    }
	};
	emailSearchLink.setOutputMarkupPlaceholderTag(true);
	searchRzCustomerForm.add(emailSearchLink);

	// email search button
	SearchRzCustomerButton emailSearchButton = new SearchRzCustomerButton("emailSearchButton", new ResourceModel(
		"searchRzCustomerForm.search.button"), searchRzCustomerForm, rewardZoneLookupDataProvider,
		searchRzCustomerFeedbackPanel, searchRzCustomersTable, searchRzCustomersContainer,
		noSearchRzCustomersContainer);
	searchRzCustomerForm.add(emailSearchButton);

	filter.addFilterInComponent(emailSearchField);
	filter.addFilterInComponent(phoneSearchField);
	filter.addFilterInComponent(firstNameSearchField);
	filter.addFilterInComponent(lastNameSearchField);
	filter.addFilterInComponent(zipCodeSearchField);

	/*if (session.getPageError() != null && session.getPageError().doesApply(SearchCustomerPanel.class)){
	    searchRzCustomerForm.error(session.getPageError().getMessage());
	    session.setPageError(null);
	}*/

	AjaxLink<Object> cancelButton = new AjaxLink<Object>("cancelButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.debug("RewardZoneLookupModalPanel : Cancel clicked .....");
		    //entitlement.setRzNumber(null);
		    //entitlement.setRzPoints(0);//DEFAULT?
		    //entitlement.setRzTier(null);
		    selectedRzCustomer.setRzNumber(null);
		    selectedRzCustomer.setRzPoints(0);
		    selectedRzCustomer.setRzTier(null);

		    rewardZoneLookupModalWindow.close(target);
		}
	    }

	};
	cancelButton.setOutputMarkupPlaceholderTag(true);
	cancelButton.setOutputMarkupId(true);
	cancelButton.setVisible(true);
	add(cancelButton);

	final AjaxLink<Object> closeLink = new AjaxLink<Object>("closeLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		if (target != null){
		    logger.debug("RewardZoneLookupModalPanel : Cancel clicked .....");
		    /*entitlement.setRzNumber(null);
		    entitlement.setRzPoints(0);//DEFAULT?
		    entitlement.setRzTier(null);*/
		    selectedRzCustomer.setRzNumber(null);
		    selectedRzCustomer.setRzPoints(0);
		    selectedRzCustomer.setRzTier(null);
		    rewardZoneLookupModalWindow.close(target);
		}
	    }
	};
	closeLink.setMarkupId("closeLink");
	closeLink.setOutputMarkupId(true);
	closeLink.setOutputMarkupPlaceholderTag(true);
	add(closeLink);

	// means by which JS can call back into Java
	final AbstractDefaultAjaxBehavior wicketBehaviorRZP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorRZP id=" + id);
		if ("load".equals(id)){
		    session.setAutomatedSearchCustomerCriteria(null);
		    if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
			session.setSearchCustomerCriteria(CustomerSearchCriteria.FN_LN_ZIP);
			session.getSearchCustomer().setFirstName(searchRzCustomer.getFirstName());
			session.getSearchCustomer().setLastName(searchRzCustomer.getLastName());
			if (session.getSearchCustomer().getAddress() == null){
			    session.getSearchCustomer().setAddress(new Address());
			}
			session.getSearchCustomer().getAddress().setZipcode(searchRzCustomer.getAddress().getZipcode());
		    }else if (searchCriteria == CustomerSearchCriteria.EMAIL){
			session.setSearchCustomerCriteria(CustomerSearchCriteria.EMAIL);
			session.getSearchCustomer().setEmail(searchRzCustomer.getEmail());
		    }else if (searchCriteria == CustomerSearchCriteria.PHONE_NUMBER){
			session.setSearchCustomerCriteria(CustomerSearchCriteria.PHONE_NUMBER);
			session.getSearchCustomer().setContactPhone(searchRzCustomer.getContactPhone());
		    }
		    logger.debug("searchRzCustomer=" + session.getSearchCustomer());
		    try{
			rewardZoneLookupDataProvider.setCustomerList(getRzCustomerDetails(searchRzCustomer,
				searchCriteria, session.getDrpUser(), searchRzCustomerFeedbackPanel));

			/*session.setSearchCustomers(customerDataService.getCustomer().getBBYCustomerProfile(searchRzCustomer,
				searchCriteria, session.getDrpUser()));
			if (session.getSearchCustomers() == null){
			    session.getCustomer().setSearchCustomers(new ArrayList<RetExchRZLookupCustomer>());*/
			logger.debug("# search customers=" + rewardZoneLookupDataProvider.size());
		    }catch(ServiceException se){
			logger.error("ServiceException is " + se.getFullMessage());
			searchRzCustomerForm.error(se.getFullMessage());
			searchRzCustomerFeedbackPanel.error(getString("rewardZoneLookupModalPanel.dataError.label"));
			target.add(searchRzCustomerFeedbackPanel);
			target.add(searchRzCustomersContainer);
			target.add(noSearchRzCustomersContainer);
			target.appendJavaScript("showBestBuyCustomerSearchLoading(false);");
			return;
		    }catch(IseBusinessException be){
			logger.error("IseBusinessException is " + be.getFullMessage());
			searchRzCustomerForm.error(be.getFullMessage());
			searchRzCustomerFeedbackPanel.error(getString("rewardZoneLookupModalPanel.dataError.label"));
			target.add(searchRzCustomerFeedbackPanel);
			target.add(searchRzCustomersContainer);
			target.add(noSearchRzCustomersContainer);
			target.appendJavaScript("showBestBuyCustomerSearchLoading(false);");
			return;
		    }
		    searchRzCustomersTable.modelChanged();
		    target.add(searchRzCustomersContainer);
		    noSearchRzCustomersContainer.get(0).setVisible(true); //LABEL
		    target.add(noSearchRzCustomersContainer);
		    /* target
		        .appendJavaScript("showBestBuyCustomerSearchLoading(false);setupSearchCustomersTable();setupSelectBestBuyCustomerLoading();");
		     */
		    target.appendJavaScript("showBestBuyCustomerSearchLoading(false);setupSearchCustomersTable();");

		}
	    }
	};
	add(wicketBehaviorRZP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("showBestBuyCustomerSearchLoading(false);");
		onDomReadyJS.append("setupSearchCustomersTable();");
		onDomReadyJS.append("selectSearchCustomersNavFilter('");
		onDomReadyJS.append(searchCriteria.name());
		onDomReadyJS.append("');");
		onDomReadyJS.append("setupSearchNav();");
		//onDomReadyJS.append("setupSelectBestBuyCustomerLoading();");
		onDomReadyJS.append("wicketBehaviorRZP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorRZP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    private class SelectRZPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectRZPanel(String id, final IModel<?> model, final RetExchRZLookupCustomer row, final String label,
		final CustomModalWindow rewardZoneLookupModalWindow, final FeedbackPanel rZCustomerFeedbackPanel,
		final RetExchRZLookupCustomer selectedRzCustomer) {
	    super(id, model);

	    AjaxLink<Object> link = new AjaxLink<Object>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {

		    logger.info("in select RZ panel on click...." + row);
		    if (row == null || row.getRzNumber() == null){
			logger.error("Error while selecting the customer from Reward Zone Panel");
			rZCustomerFeedbackPanel.error("RZ Panel selection error");
			return;
		    }
		    selectedRzCustomer.setRzValues(row);
		    Customer customer = new Customer();
		    customer.setFirstName(row.getCustomer().getFirstName());
		    customer.setLastName(row.getCustomer().getLastName());
		    selectedRzCustomer.setCustomer(customer);
		    selectedRzCustomer.setRzMemberTierCode(row.getRzMemberTierCode());

		    logger.info("selected Row in modal window...." + row.toString());
		    rewardZoneLookupModalWindow.close(target);

		}
	    };
	    link.setOutputMarkupPlaceholderTag(true);
	    link.add(new Label("label", label));
	    add(link);
	}
    }

    private class SearchRzCustomerButton extends AjaxButton {

	private static final long serialVersionUID = 1L;

	private RewardZoneLookupDataProvider rewardZoneLookupDataProvider;
	private FeedbackPanel searchRzCustomerFeedbackPanel;
	private AjaxFallbackDefaultDataTable<RetExchRZLookupCustomer> searchRzCustomersTable;
	private WebMarkupContainer searchRzCustomersContainer, noSearchRzCustomersContainer;

	public SearchRzCustomerButton(String id, IModel<String> model, final Form<?> form,
		RewardZoneLookupDataProvider rewardZoneLookupDataProvider, FeedbackPanel searchRzCustomerFeedbackPanel,
		AjaxFallbackDefaultDataTable<RetExchRZLookupCustomer> searchRzCustomersTable,
		WebMarkupContainer searchRzCustomersContainer, WebMarkupContainer noSearchRzCustomersContainer) {
	    super(id, model, form);
	    setOutputMarkupPlaceholderTag(true);
	    setMarkupId(id);
	    setOutputMarkupId(true);
	    searchRzCustomerFeedbackPanel.setVisible(true);
	    searchRzCustomerFeedbackPanel.setOutputMarkupId(true);
	    searchRzCustomerFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	    this.rewardZoneLookupDataProvider = rewardZoneLookupDataProvider;
	    this.searchRzCustomerFeedbackPanel = searchRzCustomerFeedbackPanel;
	    this.searchRzCustomersTable = searchRzCustomersTable;
	    this.searchRzCustomersContainer = searchRzCustomersContainer;
	    this.noSearchRzCustomersContainer = noSearchRzCustomersContainer;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onSubmit searchCriteria=" + searchCriteria);
	    target
		    .appendJavaScript("showBestBuyCustomerSearchLoading(true);doWicketBehavior('wicketBehaviorRZP(\"load\")');");
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onError");
	    target.add(searchRzCustomerFeedbackPanel);
	    rewardZoneLookupDataProvider.setCustomerList(null);
	    getDailyRhythmPortalSession().setSearchCustomers(null);
	    searchRzCustomersTable.modelChanged();
	    target.add(searchRzCustomersContainer);
	    target.add(noSearchRzCustomersContainer);
	}

    }

    private List<RetExchRZLookupCustomer> getRzCustomerDetails(Customer searchRzCustomer,
	    CustomerSearchCriteria searchCriteria, DrpUser drpUser, final FeedbackPanel rZCustomerFeedbackPanel)
	    throws IseBusinessException, ServiceException {
	try{
	    rZCustomerFeedbackPanel.setVisible(true);
	    rZCustomerFeedbackPanel.setOutputMarkupId(true);
	    rZCustomerFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	    List<Customer> customerList = customerDataService.getBBYCustomerProfile(searchRzCustomer, searchCriteria,
		    drpUser);
	    RetExchRZLookupCustomer retExchRZLookupCustomer = new RetExchRZLookupCustomer();

	    for(Customer customer: customerList){
		RewardZone rewardZone = rewardZoneService.getRewardZonePointsAndCerts(customer.getRewardZoneId());
		if (rewardZone != null){
		    retExchRZLookupCustomer.setCustomer(customer);
		    retExchRZLookupCustomer.setRzNumber(rewardZone.getMemberNumber());
		    retExchRZLookupCustomer.setRzPoints(rewardZone.getPointsBalance());
		    retExchRZLookupCustomer.setRzTier(rewardZone.getMemberTier());
		    retExchRZLookupCustomer.setRzMemberTierCode(rewardZone.getMemberTierCode());
		}
		searchRzCustomerList.add(new RetExchRZLookupCustomer(retExchRZLookupCustomer));
		retExchRZLookupCustomer = null;
	    }
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get Reward Zone details for the customer";
	    logger.error(message, se);
	    //error(se.getFullMessage());
	    rZCustomerFeedbackPanel.error(getString("rewardZoneLookupModalPanel.dataError.label"));
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException while getting the Reward Zone details for the customer");
	    rZCustomerFeedbackPanel.error(getString("rewardZoneLookupModalPanel.dataError.label"));
	}catch(Exception ee){
	    logger.error("IseBusinessException while getting the Reward Zone details for the customer", ee);
	    rZCustomerFeedbackPanel.error(getString("rewardZoneLookupModalPanel.dataError.label"));
	}
	return searchRzCustomerList;
    }

}
