package com.bestbuy.bbym.ise.drp.msw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.validator.DatesValidator;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class RecSheetByEmployeeSearchPanel extends BasePanel {

    enum SearchByField {
	LastName, AID
    };

    private SortableEmpRecSheetDataProvider searchResultsDataProvider = new SortableEmpRecSheetDataProvider(null);

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mobileSalesWorkspaceService;

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(RecSheetByEmployeeSearchPanel.class);

    private SearchByField searchByField = SearchByField.LastName;
    private String lastName;
    private String aid;
    private Date fromSDate;
    private Date toSDate;
    private String storeNumber;

    private AbstractDefaultAjaxBehavior wicketBehavior;

    public SearchByField getSearchByField() {
	return searchByField;
    }

    public void setSearchByField(SearchByField searchByField) {
	this.searchByField = searchByField;
    }

    public String getAid() {
	return aid;
    }

    public void setAid(String aid) {
	this.aid = aid;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    private final RecSheetByEmployeePage recSheetEmpPage;

    public Date getStartDateField() {
	return fromSDate;
    }

    public void setStartDateField(Date startDateField) {
	this.fromSDate = startDateField;
    }

    public Date getEndDateField() {
	return toSDate;
    }

    public void setEndDateField(Date endDateField) {
	this.toSDate = endDateField;
    }

    public String getStoreNumber() {
	return storeNumber;
    }

    public void setStoreNumber(String storeNumber) {
	this.storeNumber = storeNumber;
    }

    public RecSheetByEmployeeSearchPanel(String id, final RecSheetByEmployeePage mswPage) {
	super(id);

	this.recSheetEmpPage = mswPage;

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	@SuppressWarnings( {"unchecked", "rawtypes" })
	Form<ValueMap> recSheetByEmployeeSearchForm = new Form<ValueMap>("recSheetByEmployeeSearchForm",
		new CompoundPropertyModel(new ValueMap()));
	recSheetByEmployeeSearchForm.setMarkupId("recSheetByEmployeeSearchForm");
	recSheetByEmployeeSearchForm.setOutputMarkupPlaceholderTag(true);

	add(recSheetByEmployeeSearchForm);

	final TextField<String> lastNameField = new TextField<String>("lastName", new PropertyModel<String>(this,
		"lastName")) {

	    private static final long serialVersionUID = 1L;

	    public void validate() {

		if (searchByField != null && SearchByField.LastName.equals(searchByField)){
		    super.validate();
		    logger.debug("lastnameField is valid: " + this.isValid());
		}
	    }

	};
	lastNameField.add(StringValidator.lengthBetween(Integer.parseInt(getString("lastName.min.length")), Integer
		.parseInt(getString("lastName.max.length"))));
	lastNameField.setRequired(true);
	recSheetByEmployeeSearchForm.add(lastNameField);

	AjaxLink<Object> lastNameSearchLink = new AjaxLink<Object>("lastNameSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchByField = SearchByField.LastName;
		logger.info("last name search link onClick");
	    }
	};
	lastNameSearchLink.setOutputMarkupPlaceholderTag(true);
	recSheetByEmployeeSearchForm.add(lastNameSearchLink);

	final TextField<String> aidField = new TextField<String>("aid", new PropertyModel<String>(this, "aid")) {

	    private static final long serialVersionUID = 1L;

	    public void validate() {
		if (searchByField != null && SearchByField.AID.equals(searchByField)){
		    super.validate();
		    logger.debug("aid is valid: " + this.isValid());

		}
	    }
	};
	aidField.add(StringValidator.lengthBetween(Integer.parseInt(getString("aid.min.length")), Integer
		.parseInt(getString("aid.max.length"))));
	aidField.setRequired(true);
	recSheetByEmployeeSearchForm.add(aidField);

	AjaxLink<Object> aidSearchLink = new AjaxLink<Object>("aidSearchLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchByField = SearchByField.AID;
		logger.info("aid search link onClick");
	    }
	};
	aidSearchLink.setOutputMarkupPlaceholderTag(true);
	recSheetByEmployeeSearchForm.add(aidSearchLink);

	recSheetByEmployeeSearchForm.add(new Label("dateRangeLabel", "Date Range (ex:mm/dd/yy)"));
	final SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
	Calendar gregCal = new GregorianCalendar();
	gregCal.add(Calendar.DAY_OF_YEAR, -30);

	Date fromSDate = gregCal.getTime();
	String sLastmonth = sdf.format(fromSDate);
	try{
	    fromSDate = sdf.parse(sLastmonth);
	}catch(ParseException e1){
	    logger.debug(e1.getMessage(), e1);
	}
	setStartDateField(fromSDate);

	DateTextField startDateField = new DateTextField("startDate", new PropertyModel<Date>(this, "fromSDate"),
		"MM/dd/yy");

	startDateField.add(new DatePicker());
	recSheetByEmployeeSearchForm.add(startDateField);

	gregCal = new GregorianCalendar();

	Date toSDate = gregCal.getTime();
	String sToday = sdf.format(toSDate);
	// Date toSDate;
	try{
	    toSDate = sdf.parse(sToday);
	}catch(ParseException e1){
	    logger.debug(e1.getMessage(), e1);
	}
	setEndDateField(toSDate);

	DateTextField endDateField = new DateTextField("endDate", new PropertyModel<Date>(this, "toSDate"), "MM/dd/yy");
	endDateField.add(new DatePicker());
	recSheetByEmployeeSearchForm.add(endDateField);

	recSheetByEmployeeSearchForm.add(new DatesValidator(startDateField, endDateField));

	final TextField<String> storeNumberField = new TextField<String>("storeNumber", new PropertyModel<String>(this,
		"storeNumber"));
	storeNumberField.setMarkupId("storeNumber");
	storeNumberField.setOutputMarkupId(true);
	recSheetByEmployeeSearchForm.add(storeNumberField);

	recSheetByEmployeeSearchForm.add(new Label("storeNumberLabel", "Store Number"));

	AjaxButton submitSearchButton = new AjaxButton("submitSearchButton", recSheetByEmployeeSearchForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

		if (target != null){

		    target.appendJavaScript("showResultsSearchLoading(true);loadData();");

		}
	    };

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.trace("in search onError...");
		target.add(feedbackPanel);
		target.appendJavaScript("showResultsSearchLoading(false);");
	    }
	};
	submitSearchButton.setMarkupId("submitSearchButton");
	recSheetByEmployeeSearchForm.add(submitSearchButton);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehavior id=" + id);
		if ("load".equals(id)){
		    String lastNameInput = lastName;

		    String startDate = getStartDateField().toString();
		    String endDate = getEndDateField().toString();
		    String storeNumber = getStoreNumber();

		    if (StringUtils.isEmpty(aid) == false){
			aid = aid.toUpperCase();
		    }

		    if (StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)){
			error("TO and FROM date range must both be entered.");
			return;
		    }

		    if (StringUtils.isEmpty(startDate)){
			error("FROM date is required.");
			return;
		    }

		    if (StringUtils.isEmpty(endDate)){
			error("TO date is required.");
			return;
		    }
		    // Mon May 14 00:00:00 CDT 2012
		    SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd hh:mm:ss zzz yyyy");

		    Date startFormattedDate;
		    try{
			startFormattedDate = format.parse(startDate);
		    }catch(ParseException exc){
			logger.debug(exc.getMessage(), exc);
			error("FROM date is required.");
			return;
		    }

		    Date endFormattedDate;
		    try{
			endFormattedDate = format.parse(endDate);
		    }catch(ParseException exc){
			logger.debug(exc.getMessage(), exc);
			error("TO date is required.");
			return;
		    }

		    if (startFormattedDate.compareTo(endFormattedDate) > 0){
			error(new StringResourceModel("invalid.date.range", RecSheetByEmployeeSearchPanel.this, null)
				.getString());
			// error("Invalid Date Range: The FROM date must fall before the TO date.");
			return;
		    }

		    List<RecSheetReportingSearchResults> searchResults = null;
		    try{

			if (SearchByField.LastName.equals(searchByField) && lastNameField.isValid()){

			    if (StringUtils.isEmpty(storeNumber)){
				searchResults = mobileSalesWorkspaceService.findRecommendationReportsByLastName(
					lastNameInput, startFormattedDate, endFormattedDate);
			    }else{
				searchResults = mobileSalesWorkspaceService.findRecommendationReportsByLastName(
					lastNameInput, startFormattedDate, endFormattedDate, storeNumber);
			    }
			}else{

			    if (StringUtils.isEmpty(storeNumber)){
				searchResults = mobileSalesWorkspaceService.findRecommendationReportsByAId(aid,
					startFormattedDate, endFormattedDate);
			    }else{
				searchResults = mobileSalesWorkspaceService.findRecommendationReportsByAId(aid,
					startFormattedDate, endFormattedDate, storeNumber);
			    }
			}

			// update the search results on the data provider
			searchResultsDataProvider.setRecommendations(searchResults);
			target.appendJavaScript("showResultsSearchLoading(false);");

			if (searchResults == null || searchResults.isEmpty()){
			    info(new StringResourceModel("noDataFound", RecSheetByEmployeeSearchPanel.this, null)
				    .getString());
			}else{
			    logger.info("search results is not empty or null");
			    setResponsePage(new RecSheetByEmployeeSearchResultsPage(null, recSheetEmpPage,
				    searchResultsDataProvider, RecSheetByEmployeeSearchPanel.this));
			}

		    }catch(ServiceException e){

			logger.debug(e.getMessage(), e);
		    }

		}
	    }

	};
	add(wicketBehavior);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		onDomReadyJS.append("showResultsSearchLoading(false);setupSearchNav();");
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

    }

}
