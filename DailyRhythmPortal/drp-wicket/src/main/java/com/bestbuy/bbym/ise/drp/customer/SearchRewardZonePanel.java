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
import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class SearchRewardZonePanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SearchRewardZonePanel.class);

    @SpringBean(name = "rewardZoneService")
    private RewardZoneService rewardZoneService;

    private CustInfoModalPanel parentModalPanel;
    private CustomerSearchCriteria searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
    private Customer searchCustomer = new Customer(), searchCustCopy = new Customer();
    private SearchRewardZoneDataProvider searchRewardZoneDataProvider = new SearchRewardZoneDataProvider();

    final String na = getString("notApplicable.label");
    PhoneFormatter<String> formatter = new PhoneFormatter<String>();

    public SearchRewardZonePanel(final String id, final CustInfoModalPanel parentModalPanel) {
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

	final List<IColumn<RewardZone>> columns = new ArrayList<IColumn<RewardZone>>();

	columns.add(new PropertyColumn<RewardZone>(new ResourceModel("custInfo.rewardZone.memberDetails.column"),
		"memberDetails") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		RewardZone row = (RewardZone) rowModel.getObject();
		cellItem.add(new SearchRewardZonePanel.MemberNumberDisplayPanel(componentId, rowModel, row, na));
	    }
	});
	columns.add(new FormatPropertyColumn<RewardZone, String>(new ResourceModel(
		"custInfo.rewardZone.memberPhone.column"), "contactPhone", "contactPhone", formatter, na));
	columns.add(new PropertyColumn<RewardZone>(new ResourceModel("custInfo.rewardZone.memberNumber.column"),
		"memberNumber", "memberNumber"));
	columns.add(new PropertyColumn<RewardZone>(new ResourceModel("custInfo.rewardZone.memberTier.column"),
		"memberTier", "memberTier"));
	columns.add(new PropertyColumn<RewardZone>(new ResourceModel("custInfo.rewardZone.memberPoints.column"),
		"pointsBalance", "pointsBalance"));
	columns.add(new PropertyColumn<RewardZone>(new ResourceModel("custInfo.rewardZone.accountStatus.column"),
		"accountStatus", "accountStatus"));
	columns.add(new AbstractColumn<RewardZone>(null, "memberNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		RewardZone row = (RewardZone) rowModel.getObject();
		cellItem.add(new SearchRewardZonePanel.SelectLinkPanel(componentId, rowModel, row,
			getString("custInfo.selectLink.label")));
	    }
	});

	final WebMarkupContainer searchRewardZonesContainer = new WebMarkupContainer("searchRewardZonesContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return searchRewardZoneDataProvider.size() > 0;
	    }
	};
	searchRewardZonesContainer.setMarkupId("searchRewardZonesContainer");
	searchRewardZonesContainer.setOutputMarkupId(true);
	searchRewardZonesContainer.setOutputMarkupPlaceholderTag(true);
	add(searchRewardZonesContainer);

	final AjaxFallbackDefaultDataTable<RewardZone> searchRewardZonesTable = new AjaxFallbackDefaultDataTable<RewardZone>(
		"searchRewardZonesTable", columns, searchRewardZoneDataProvider, 200);
	searchRewardZonesTable.setMarkupId("searchRewardZonesTable");
	searchRewardZonesTable.setOutputMarkupId(true);
	searchRewardZonesTable.setOutputMarkupPlaceholderTag(true);
	searchRewardZonesContainer.add(searchRewardZonesTable);

	final Form<Object> searchRewardZoneForm = new Form<Object>("searchRewardZoneForm");
	searchRewardZoneForm.setOutputMarkupPlaceholderTag(true);
	add(searchRewardZoneForm);

	// no search customers
	final WebMarkupContainer noSearchRewardZonesContainer = new WebMarkupContainer("noSearchRewardZonesContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (parentModalPanel.getFeedbackPanel().anyErrorMessage()){
		    return false;
		}
		if (searchRewardZoneDataProvider.size() > 0){
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
	noSearchRewardZonesContainer.setMarkupId("noSearchRewardZonesContainer");
	noSearchRewardZonesContainer.setOutputMarkupId(true);
	noSearchRewardZonesContainer.setOutputMarkupPlaceholderTag(true);
	add(noSearchRewardZonesContainer);

	final Label noSearchRewardZonesLabel = new Label("noSearchRewardZonesLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (searchCriteria == CustomerSearchCriteria.PHONE_EMAIL){
		    logger.debug("no reward zones for search criteria of phone/email ");
		    return getString("custInfo.rewardZone.noPhoneSearchDataLabel.label");

		}else if (searchCriteria == CustomerSearchCriteria.EMAIL){
		    logger.debug("no reward zones for search criteria of email");
		    return getString("custInfo.rewardZone.noEmailSearchDataLabel.label");

		}else if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
		    logger.debug("no reward zones for search criteria of fn ln zip");
		    return getString("custInfo.rewardZone.noFnLnZipSearchDataLabel.label");
		}
		return getString("custInfo.rewardZone.noPhoneSearchDataLabel.label");
	    }
	});
	noSearchRewardZonesLabel.setOutputMarkupPlaceholderTag(true);
	noSearchRewardZonesContainer.add(noSearchRewardZonesLabel);

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
	searchRewardZoneForm.add(phoneSearchField);

	AjaxLink<Object> phoneSearchLink = new AjaxLink<Object>("phoneSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.PHONE_NUMBER;
		logger.debug("phoneSearchLink onClick");
	    }
	};
	phoneSearchLink.setOutputMarkupPlaceholderTag(true);
	searchRewardZoneForm.add(phoneSearchLink);

	// phone search button
	SearchCustomerButton phoneSearchButton = new SearchCustomerButton("phoneSearchButton", new ResourceModel(
		"custInfo.searchButton.label"), searchRewardZoneForm, parentModalPanel.getFeedbackPanel(),
		searchRewardZonesTable, searchRewardZonesContainer, noSearchRewardZonesContainer);
	searchRewardZoneForm.add(phoneSearchButton);

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
	searchRewardZoneForm.add(firstNameSearchField);

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
	searchRewardZoneForm.add(lastNameSearchField);

	// zip code search field
	// final TextField<String> zipCodeSearchField = new
	// RequiredTextField<String>("zipCodeSearch",
	// new PropertyModel<String>(searchCustomer, "address.zipcode")) {
	//
	// private static final long serialVersionUID = 1L;
	//
	// @Override
	// public void validate() {
	// if (searchCriteria == CustomerSearchCriteria.FN_LN_ZIP){
	// logger.debug("checking validity of zipCode");
	// super.validate();
	// }
	// }
	// };
	// zipCodeSearchField.setOutputMarkupPlaceholderTag(true);
	// zipCodeSearchField.setRequired(true);
	// zipCodeSearchField.add(new PatternValidator("\\d{" +
	// Validation.ZIP_CODE_SIZE + "}"));
	// searchRewardZoneForm.add(zipCodeSearchField);

	AjaxLink<Object> nameZipSearchLink = new AjaxLink<Object>("nameZipSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.FN_LN_ZIP;
		logger.debug("nameZipSearchLink onClick");
	    }
	};
	nameZipSearchLink.setOutputMarkupPlaceholderTag(true);
	searchRewardZoneForm.add(nameZipSearchLink);

	// name/zip search button
	SearchCustomerButton nameZipSearchButton = new SearchCustomerButton("nameZipSearchButton", new ResourceModel(
		"custInfo.searchButton.label"), searchRewardZoneForm, parentModalPanel.getFeedbackPanel(),
		searchRewardZonesTable, searchRewardZonesContainer, noSearchRewardZonesContainer);
	searchRewardZoneForm.add(nameZipSearchButton);

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
	searchRewardZoneForm.add(emailSearchField);

	AjaxLink<Object> emailSearchLink = new AjaxLink<Object>("emailSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchCriteria = CustomerSearchCriteria.EMAIL;
		logger.debug("emailSearchLink onClick");
	    }
	};
	emailSearchLink.setOutputMarkupPlaceholderTag(true);
	searchRewardZoneForm.add(emailSearchLink);

	// email search button
	SearchCustomerButton emailSearchButton = new SearchCustomerButton("emailSearchButton", new ResourceModel(
		"custInfo.searchButton.label"), searchRewardZoneForm, parentModalPanel.getFeedbackPanel(),
		searchRewardZonesTable, searchRewardZonesContainer, noSearchRewardZonesContainer);
	searchRewardZoneForm.add(emailSearchButton);

	// means by which JS can callback into Java
	final AbstractDefaultAjaxBehavior wicketBehaviorRZP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorRZP id=" + id);
		if ("load".equals(id)){
		    restoreSearchCustomer();
		    logger.debug("searchCustomer=" + searchCustomer);
		    logger.debug("searchCriteria=" + searchCriteria);
		    try{
			switch (searchCriteria) {
			    case FN_LN_ZIP:
				searchRewardZoneDataProvider.setRewardZoneList(rewardZoneService
					.retrieveCustomerRewardZone(searchCustomer.getFirstName(), searchCustomer
						.getLastName(), null, null));
				break;
			    case EMAIL:
				searchRewardZoneDataProvider.setRewardZoneList(rewardZoneService
					.retrieveCustomerRewardZone(null, null, null, searchCustomer.getEmail()));
				break;
			    case PHONE_NUMBER:
				searchRewardZoneDataProvider
					.setRewardZoneList(rewardZoneService.retrieveCustomerRewardZone(null, null,
						searchCustomer.getContactPhone(), null));
				break;
			}
			logger.debug("# search reward zones=" + searchRewardZoneDataProvider.size());
		    }catch(ServiceException se){
			searchRewardZoneDataProvider.setRewardZoneList(null);
			logger.error("ServiceException is " + se.getFullMessage());
			parentModalPanel.getFeedbackPanel().error(se.getFullMessage());
			target.add(parentModalPanel.getFeedbackPanel());
		    }catch(IseBusinessException be){
			searchRewardZoneDataProvider.setRewardZoneList(null);
			logger.error("IseBusinessException is " + be.getFullMessage());
			parentModalPanel.getFeedbackPanel().error(be.getFullMessage());
			target.add(parentModalPanel.getFeedbackPanel());
		    }
		    searchRewardZonesTable.modelChanged();
		    target.add(searchRewardZonesContainer);
		    target.add(noSearchRewardZonesContainer);
		    parentModalPanel.refresh(target);
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
		onDomReadyJS.append("wicketBehaviorRZP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorRZP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    private class SearchCustomerButton extends AjaxButton {

	private static final long serialVersionUID = 1L;

	private final FeedbackPanel searchCustomerFeedbackPanel;
	private final AjaxFallbackDefaultDataTable<RewardZone> searchRewardZonesTable;
	private final WebMarkupContainer searchRewardZonesContainer, noSearchRewardZonesContainer;

	public SearchCustomerButton(String id, IModel<String> model, final Form<?> form,
		final FeedbackPanel searchCustomerFeedbackPanel,
		final AjaxFallbackDefaultDataTable<RewardZone> searchRewardZonesTable,
		final WebMarkupContainer searchRewardZonesContainer,
		final WebMarkupContainer noSearchRewardZonesContainer) {
	    super(id, model, form);
	    setOutputMarkupPlaceholderTag(true);
	    setMarkupId(id);
	    setOutputMarkupId(true);
	    this.searchCustomerFeedbackPanel = searchCustomerFeedbackPanel;
	    this.searchRewardZonesTable = searchRewardZonesTable;
	    this.searchRewardZonesContainer = searchRewardZonesContainer;
	    this.noSearchRewardZonesContainer = noSearchRewardZonesContainer;
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onSubmit searchCriteria=" + searchCriteria);
	    target
		    .appendJavaScript("showRewardZoneSearchLoading(true);doWicketBehavior('wicketBehaviorRZP(\"load\")');");
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("searchButton onError");
	    target.add(searchCustomerFeedbackPanel);
	    searchRewardZoneDataProvider.setRewardZoneList(null);
	    searchRewardZonesTable.modelChanged();
	    target.add(searchRewardZonesContainer);
	    target.add(noSearchRewardZonesContainer);
	    parentModalPanel.refresh(target);
	}
    }

    public String getOpenPanelJS() {
	if (!isVisible()){
	    return "";
	}
	StringBuilder openPanelJS = new StringBuilder();
	openPanelJS.append("selectSearchRewardZonesNavFilter('");
	openPanelJS.append(searchCriteria.name());
	openPanelJS.append("');");
	openPanelJS.append("setupSearchNav();");
	openPanelJS.append("showRewardZoneSearchLoading(true);doWicketBehavior('wicketBehaviorRZP(\"load\")');");
	return openPanelJS.toString();
    }

    public String getRefreshPanelJS() {
	if (!isVisible()){
	    return "";
	}
	StringBuilder openPanelJS = new StringBuilder();
	openPanelJS.append("showRewardZoneSearchLoading(false);");
	openPanelJS.append("selectSearchRewardZonesNavFilter('");
	openPanelJS.append(searchCriteria.name());
	openPanelJS.append("');");
	openPanelJS.append("setupSearchNav();");
	if (searchRewardZoneDataProvider.size() > 0){
	    openPanelJS.append("tablePrep(custInfoModalSearchRewardZonesTable);");
	}
	return openPanelJS.toString();
    }

    @Override
    public boolean isVisible() {
	return parentModalPanel.getModalState() == CustInfoModalPanel.ModalState.SELECT_REWARD_ZONE;
    }

    class SelectLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectLinkPanel(String id, final IModel<?> model, final RewardZone row, final String label) {
	    super(id, model);

	    AjaxLink<Object> link = new AjaxLink<Object>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("selectLinkPanel onClick");
		    String error = parentModalPanel.checkRewardZone((RewardZone) row);
		    if (error != null){
			error(error);
			target.add(parentModalPanel.getFeedbackPanel());
			return;
		    }
		    parentModalPanel.setSelectedRewardZone((RewardZone) Util.clone(row));
		    parentModalPanel.close(target);
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

    class MemberNumberDisplayPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public MemberNumberDisplayPanel(String id, final IModel<?> model, final RewardZone row,
		final String notAvailableLabel) {
	    super(id, model);
	    StringBuffer sb = new StringBuffer();
	    if (row != null && row.getLastName() != null && row.getFirstName() != null){
		sb.append(row.getLastName() + ", " + row.getFirstName());
	    }else{
		sb.append(notAvailableLabel);
	    }
	    sb.append("<br/>");
	    if (row != null && row.getAddressString() != null){
		sb.append(row.getAddressString());
	    }else{
		sb.append(notAvailableLabel);
	    }
	    Label memberDetailsLabel = new Label("memberDetailsLabel", new Model<String>(sb.toString()));
	    memberDetailsLabel.setEscapeModelStrings(false);
	    add(memberDetailsLabel);
	}
    }

}
