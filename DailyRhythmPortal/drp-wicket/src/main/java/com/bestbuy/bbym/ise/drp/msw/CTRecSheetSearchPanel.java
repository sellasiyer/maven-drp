package com.bestbuy.bbym.ise.drp.msw;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxFallbackButton;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.PhoneNumber;
import com.bestbuy.bbym.ise.drp.dashboard.CarrierInfoDataProvider;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.recsheet.CTYouSectionPage;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class CTRecSheetSearchPanel extends BasePanel {

    enum SearchByField {

	LastName, PhoneNumber
    };

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CTRecSheetSearchPanel.class);

    private Recommendation recSheet;
    private SortableRecSheetDataProvider searchResultsDataProvider = new SortableRecSheetDataProvider(null);
    private final static PhoneNumberConverter phoneNumberConverter = new PhoneNumberConverter();
    private SearchByField searchByField = SearchByField.LastName;
    private String lastName;
    private boolean searchAllStores;

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mobileSalesWorkspaceService;

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public PhoneNumber getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(PhoneNumber phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    private PhoneNumber phoneNumber;

    public boolean isSearchAllStores() {
	return searchAllStores;
    }

    public void setSearchAllStores(boolean searchAllStores) {
	this.searchAllStores = searchAllStores;
    }

    public CTRecSheetSearchPanel(String id, final CTRecSheetPage ctRecSheetPage) {
	super(id);

	// pre-fill values for search using customer info we may have already
	// obtained on the dashboard as a result of a lookup from the carrier
	final Customer cust = getDailyRhythmPortalSession().getCustomer();
	setLastName(cust.getLastName());
	// Contact phone is the phone number looked up in a carrier search
	setPhoneNumber(new PhoneNumber(cust.getContactPhone()));

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	// %% New Recsheet Button %%
	AjaxLink<Object> createNewRecSheet = new AjaxLink<Object>("createNewRecSheetLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("launching new rec worksheet");
		recSheet = new Recommendation();
		// copy over values from the carrier customer searched on
		// the dashboard to the extent possible
		try{
		    if (cust.getSubscription() != null && cust.getSubscription().getLines() != null){

			recSheet.setFirstName(cust.getFirstName());
			recSheet.setLastName(cust.getLastName());
			// Contact phone is the phone number looked up in a
			// carrier search
			recSheet.setMobileNo(cust.getContactPhone());
			recSheet.setTransferFlag(false);
			if (!cust.getSubscription().getLines().isEmpty()
				&& cust.getSubscription().getLines().get(0) != null){

			    // Retrieving line... In order to get the same line
			    // as carrier panel need to use logic from
			    // CarrierInfoDataProvider, mostly for same sorting
			    // logic, etc.
			    CarrierInfoDataProvider provider = new CarrierInfoDataProvider();
			    provider.setLineDataList(cust.getSubscription().getLines());
			    provider.setSearchPhoneNum(cust.getContactPhone());
			    List<Line> allLineList = provider.getLineDataList();
			    Line line = allLineList.get(0);

			    List<Line> lineList = new LinkedList<Line>(allLineList.subList(1, allLineList.size() > 6?6
				    :allLineList.size()));

			    Device device = line.getDevice();
			    recSheet.setUpgradeEligibilityDate(line.getStdEligibilityDate());
			    recSheet.setUpgradeReminderText(line.getOptinText());
			    recSheet.setUpgradeReminderCall(line.getOptinVoice());
			    recSheet.setTradeInValue(device == null?null:device.getTradeInValue());
			    recSheet.setOtherLineList(lineList);

			    String planInfo = "";
			    try{
				CarrierPlan plan = line.getCarrierPlans().get(0);
				planInfo = StringUtils.trimToEmpty(plan.getPlanName());
				planInfo += " " + StringUtils.trimToEmpty(plan.getPlanType());
			    }catch(IndexOutOfBoundsException e){
				logger.error("carrier plans was empty");
			    }

			    String subscriptionInfo = "Carrier: "
				    + StringUtils.trimToEmpty(cust.getSubscription().getCarrier().toString())
				    + "\nPlan: " + planInfo + "\nDevice: "
				    + StringUtils.trimToEmpty(device.getDescription());
			    recSheet.setSubscriptionInfo(subscriptionInfo);
			    recSheet.setCreatedOn(new Date());
			} // end of if lines empty check.
		    } // end of nulls check.

		}catch(NullPointerException e){
		    logger.warn("missing data in customer object: " + e.getLocalizedMessage());
		}
		logger.info(">> Create new recsheet :" + recSheet.toString());
		recSheet.setRecShtTyp(2);
		getDailyRhythmPortalSession().setRecommendation(recSheet);
		setResponsePage(CTYouSectionPage.class);
	    }
	};
	createNewRecSheet.setOutputMarkupPlaceholderTag(true);
	createNewRecSheet.setMarkupId("createNewRecSheetLink");
	//add(createNewRecSheet);

	final Form<Recommendation> recSheetSearchForm = new Form<Recommendation>("recSheetSearchForm");
	add(recSheetSearchForm);

	recSheetSearchForm.add(createNewRecSheet);

	// last name search field
	final TextField<String> lastNameField = new RequiredTextField<String>("lastName", new PropertyModel<String>(
		this, "lastName")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		// check which search method was invoked and validate only its
		// input fields, ignoring the other
		if (SearchByField.LastName == searchByField){
		    super.validate();
		    logger.debug("lastnameField is valid: " + this.isValid());
		}
	    }
	};
	// validate last name is between 1 and 25 characters long
	lastNameField.add(StringValidator.lengthBetween(Integer.parseInt(getString("lastName.min.length")), Integer
		.parseInt(getString("lastName.max.length"))));
	recSheetSearchForm.add(lastNameField);

	AjaxLink<Object> lastNameSearchLink = new AjaxLink<Object>("lastNameSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchByField = SearchByField.LastName;
		logger.info("last name search link onClick");
	    }
	};
	lastNameSearchLink.setOutputMarkupPlaceholderTag(true);
	recSheetSearchForm.add(lastNameSearchLink);

	// phone number search field
	final TextField<PhoneNumber> phoneNumberField = new RequiredTextField<PhoneNumber>("phoneNum",
		new PropertyModel<PhoneNumber>(this, "phoneNumber")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		// check which search method was invoked and validate only its
		// input fields, ignoring the other
		if (SearchByField.PhoneNumber == searchByField){
		    super.validate();
		    logger.debug("phoneNumberField is valid: " + this.isValid());
		}
	    }

	    /**
	     * Validates cell phone is exactly 10 digits,
	     */
	    @Override
	    public <C> IConverter<C> getConverter(Class<C> type) {
		return phoneNumberConverter;
	    }
	};

	recSheetSearchForm.add(phoneNumberField);

	AjaxLink<Object> phoneNumSearchLink = new AjaxLink<Object>("phoneNumSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchByField = SearchByField.PhoneNumber;
		logger.info("phone num search link onClick");
	    }
	};
	phoneNumSearchLink.setOutputMarkupPlaceholderTag(true);
	recSheetSearchForm.add(phoneNumSearchLink);

	// search button
	AjaxFallbackButton searchButton = new AjaxFallbackButton("submitSearchButton", recSheetSearchForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit(AjaxRequestTarget target, Form<? extends Object> form) {
		List<Recommendation> searchResults = null;
		try{
		    String storeId = searchAllStores?null:getDailyRhythmPortalSession().getDrpUser().getStoreId();
		    if (SearchByField.LastName == searchByField && lastNameField.isValid()){
			searchResults = mobileSalesWorkspaceService.findRecommendationsForCAndT(getLastName(), storeId);
			logger.debug("looking up digital recommendation worksheet by lastname: " + getLastName());
		    }else if (SearchByField.PhoneNumber == searchByField && phoneNumberField.isValid()){
			searchResults = mobileSalesWorkspaceService.findRecommendationsForCAndTByPhone(getPhoneNumber()
				.toString(), storeId);
			logger.debug("looking up digital recommendation worksheet by mobile no: " + getPhoneNumber());
		    }else{// failed validation so return
			return;
		    }

		    searchResultsDataProvider.setRecommendations(searchResults);

		    if (searchResults == null || searchResults.isEmpty()){
			info(new StringResourceModel("noDataFound", this, null).getString());
			// searchResultsTable.setVisible(false);
		    }else{
			// searchResultsTable.setVisible(true);
			logger.info("search results is not empty or null");
			setResponsePage(new CTRecSheetSearchResultsPage(null, ctRecSheetPage,
				searchResultsDataProvider, CTRecSheetSearchPanel.this));

		    }

		    // update the search results on the data provider

		    // add component to refresh to target page
		    // target.add(searchResultsTable);
		    // target.appendJavascript("setupRecSheetTable();");
		}catch(ServiceException e){
		    logger.error("Error", e);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		target.add(feedbackPanel);
	    }
	};
	recSheetSearchForm.add(searchButton);

	CheckBox searchAllStoresToggle = new CheckBox("searchAllStoresToggle", new PropertyModel<Boolean>(this,
		"searchAllStores"));
	// radioSearchByField.add(searchAllStoresToggle);
	recSheetSearchForm.add(searchAllStoresToggle);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	response.renderOnDomReadyJavaScript("setupSearchNav();");
    }

}
