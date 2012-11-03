package com.bestbuy.bbym.ise.drp.msw;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.handler.resource.ResourceStreamRequestHandler;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.resource.StringResourceStream;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.PatternValidator;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.MessageModalPanel;
import com.bestbuy.bbym.ise.drp.beast.common.OkCancelCenterModalPanel;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.dashboard.TableColumnRepeater;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportList;
import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import com.bestbuy.bbym.ise.drp.service.MobileSalesWorkspaceService;
import com.bestbuy.bbym.ise.drp.validator.DatesValidator;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class DigitalRecSheetCountPanel extends BasePanel {

    private static Logger logger = LoggerFactory.getLogger(DigitalRecSheetCountPanel.class);
    private static final long serialVersionUID = 1L;

    private static final int REC_SHEET_REPORT_SIZE_LIMIT = 10;

    final SimpleDateFormat headerDateFormat = new SimpleDateFormat("MM/dd/yy");
    final SimpleDateFormat keyDateFormat = new SimpleDateFormat("yyyyMMdd");

    @SpringBean(name = "mobileSalesWorkspaceService")
    private MobileSalesWorkspaceService mobileSalesWorkspaceService;

    private Date fromSDate;
    private Date toSDate;
    private String store;
    private String storeId;
    private List<RecSheetReportList> recSheetReportList;
    private RecSheetReportingSearchResults recSheetReportingSearchResults = new RecSheetReportingSearchResults();

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public Date getFromDate() {
	return fromSDate;
    }

    public void setFromDate(Date fromDate) {
	this.fromSDate = fromDate;
    }

    public Date getToDate() {
	return toSDate;
    }

    public void setToDate(Date toDate) {
	this.toSDate = toDate;
    }

    public String getStore() {
	return store;
    }

    public void setStore(String store) {
	this.store = store;
    }

    public List<RecSheetReportList> getRecSheetReportList() {
	return recSheetReportList;
    }

    public void setRecSheetReportList(List<RecSheetReportList> recSheetReportList) {
	this.recSheetReportList = recSheetReportList;
    }

    public RecSheetReportingSearchResults getRecSheetReportingSearchResults() {
	return recSheetReportingSearchResults;
    }

    public void setRecSheetReportingSearchResults(RecSheetReportingSearchResults recSheetReportingSearchResults) {
	this.recSheetReportingSearchResults = recSheetReportingSearchResults;
    }

    final Button exportButton;
    final MessageModalPanel printWarningPanel;
    final OkCancelCenterModalPanel printLimitationWarningPanel;

    public DigitalRecSheetCountPanel(final String id) {
	super(id);
	Calendar gregCal = new GregorianCalendar();
	gregCal.add(Calendar.DAY_OF_YEAR, -30);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupId(true);
	add(feedbackPanel);

	final Form<ValueMap> recSheetCountForm = new Form<ValueMap>("recSheetCountForm",
		new CompoundPropertyModel<ValueMap>(new ValueMap()));
	recSheetCountForm.setMarkupId("recSheetCountForm");
	add(recSheetCountForm);

	final DrpUser drpUser = getDailyRhythmPortalSession().getDrpUser();
	recSheetCountForm.add(new Label("dateLabel", "Date Range (ex : mm/dd/yy)"));
	recSheetCountForm.add(new Label("storeLabel", "Store Number"));

	fromSDate = gregCal.getTime();

	final DateTextField fromDate = new DateTextField("fromDate", new PropertyModel<Date>(this, "fromSDate"),
		"MM/dd/yy");
	fromDate.add(new DatePicker());
	recSheetCountForm.add(fromDate);

	gregCal = new GregorianCalendar();
	Date today = gregCal.getTime();
	String sToday = headerDateFormat.format(today);
	try{
	    toSDate = headerDateFormat.parse(sToday);
	}catch(ParseException e){
	    logger.error("Error parsing date", e);
	}

	final DateTextField toDate = new DateTextField("toDate", new PropertyModel<Date>(this, "toSDate"), "MM/dd/yy");
	toDate.add(new DatePicker());
	recSheetCountForm.add(toDate);

	setStoreId(drpUser.getStoreId());

	final TextField<String> store = new TextField<String>("store", new PropertyModel<String>(this, "storeId"));
	store.add(new PatternValidator("[0-9]{1,4}"));
	store.setMarkupId("store");
	store.setOutputMarkupId(true);
	recSheetCountForm.add(store);

	recSheetCountForm.add(new DatesValidator(fromDate, toDate));

	final RepeatingView headerRepeater = new RepeatingView("headerRepeater");
	add(headerRepeater);
	final RepeatingView rowRepeater = new RepeatingView("rowRepeater");
	add(rowRepeater);

	// Search Button
	final Button searchButton = new Button("searchButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {

		headerRepeater.removeAll();
		rowRepeater.removeAll();

		if (fromSDate == null && toSDate == null){
		    error(new StringResourceModel("invalid.date.empty", this, null).getString());
		    return;
		}

		if (fromSDate == null){
		    error(new StringResourceModel("invalid.date.from", this, null).getString());
		    return;
		}

		if (toSDate == null){
		    error(new StringResourceModel("invalid.date.to", this, null).getString());
		    return;
		}

		if (fromSDate.compareTo(toSDate) > 0){
		    error(new StringResourceModel("invalid.date.range", this, null).getString());
		    return;
		}

		if (StringUtils.isEmpty(storeId)){
		    error(new StringResourceModel("invalid.storeId", this, null).getString());
		    return;
		}
		// FIXME: why is store # a string in the database?
		storeId = String.format("%04d", Integer.parseInt(storeId));

		List<RecSheetReportList> list = null;
		try{
		    list = mobileSalesWorkspaceService.findRecommendationsFromToDate(fromSDate, toSDate, storeId);
		}catch(ServiceException e){
		    error(e.getMessage());
		    return;
		}

		if (list == null || list.size() == 0){
		    info(new StringResourceModel("noDataFound", this, null).getString());
		    //actionPanel.setVisible(false);
		    return;
		}else{
		    recSheetReportList = list;
		    exportButton.setVisible(true);
		}

		int days = Days.daysBetween(new DateTime(fromSDate), new DateTime(toSDate)).getDays() + 1;
		Calendar cal = Calendar.getInstance();

		headerRepeater.add(new Label(headerRepeater.newChildId(), "Last Name"));
		headerRepeater.add(new Label(headerRepeater.newChildId(), "First Name"));

		// The "Employee ID" column is mislabeled. It actually contains
		// user ID.
		headerRepeater.add(new Label(headerRepeater.newChildId(), "Employee ID"));
		cal.setTimeInMillis(fromSDate.getTime());
		for(int i = 0; i < days; i++){
		    Label label = new Label(headerRepeater.newChildId(), headerDateFormat.format(cal.getTime()));
		    if (i == days - 1){
			label.add(new AttributeAppender("class", new Model<String>("last"), " "));
		    }
		    headerRepeater.add(label);
		    cal.add(Calendar.DAY_OF_YEAR, 1);
		}

		for(int i = 0; i < list.size(); ++i){
		    RecSheetReportList row = list.get(i);
		    ArrayList<Object> data = new ArrayList<Object>();
		    data.add(row.getLastName());
		    data.add(row.getFirstName());
		    data.add(row.getAid());
		    cal.setTimeInMillis(fromSDate.getTime());
		    for(int j = 0; j < days; j++){
			// FIXME: change the service to return the data in an
			// ordered list instead of an associative array.
			String date = keyDateFormat.format(cal.getTime());
			String count = row.getDateMap().get(date);
			if (count == null){
			    count = "0";
			}
			data.add(count);
			cal.add(Calendar.DAY_OF_YEAR, 1);
		    }
		    TableColumnRepeater columnRepeater = new TableColumnRepeater(rowRepeater.newChildId(),
			    new MyColumnFactory(data, 0, data.size()));
		    applyClass(columnRepeater, i, list.size());
		    rowRepeater.add(columnRepeater);
		}

	    }
	};

	recSheetCountForm.add(searchButton);

	exportButton = new Button("exportButton", new Model<String>("export")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {

		List<RecSheetReportList> list = null;
		try{
		    list = mobileSalesWorkspaceService.findRecommendationsFromToDate(fromSDate, toSDate, storeId);
		}catch(ServiceException e){
		    logger.error("Error finding recommendations", e);
		    error(e.getMessage());
		    return;
		}

		if (list == null || list.size() == 0){
		    info(new StringResourceModel("noDataFound", this, null).getString());
		    return;
		}

		StringBuilder builder = new StringBuilder();
		// The "Employee ID" column is mislabeled. It actually contains
		// user ID.
		builder.append("\"Last Name\",").append("\"First Name\",").append("\"Employee ID\",");
		int days = Days.daysBetween(new DateTime(fromSDate), new DateTime(toSDate)).getDays() + 1;

		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(fromSDate.getTime());
		cal.add(Calendar.DAY_OF_YEAR, -1);

		for(int i = 0; i < days; i++){
		    cal.add(Calendar.DAY_OF_YEAR, 1);
		    builder.append("\"" + headerDateFormat.format(cal.getTime()) + "\",");
		}
		builder.append("\n");

		for(RecSheetReportList current: list){
		    builder.append("\"" + current.getLastName() + "\",");
		    builder.append("\"" + current.getFirstName() + "\",");
		    builder.append("\"" + current.getAid() + "\",");

		    days = Days.daysBetween(new DateTime(fromSDate), new DateTime(toSDate)).getDays() + 1;
		    cal = Calendar.getInstance();
		    cal.setTimeInMillis(fromSDate.getTime());
		    cal.add(Calendar.DAY_OF_YEAR, -1);
		    for(int i = 0; i < days; i++){
			cal.add(Calendar.DAY_OF_YEAR, 1);
			String formattedDate = keyDateFormat.format(cal.getTime());

			if (current.getDateMap().containsKey(formattedDate)){
			    String numbers = current.getDateMap().get(formattedDate);
			    builder.append("\"" + numbers + "\",");
			}else{
			    builder.append("\"0\",");
			}

		    }

		    builder.append("\n");
		}

		CharSequence export = builder.toString();
		ResourceStreamRequestHandler target = new ResourceStreamRequestHandler(new StringResourceStream(export,
			"application/vnd.ms-excel"));
		target.setFileName("recsheetcounts.csv");
		RequestCycle.get().scheduleRequestHandlerAfterCurrent(target);
	    }
	};

	exportButton.setVisible(false);
	recSheetCountForm.add(exportButton);

	AjaxLink<Object> link = new AjaxLink<Object>("printButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {

		int recCount = getRecDataCount();
		if (recCount > 0){

		    if (recCount > REC_SHEET_REPORT_SIZE_LIMIT){
			displayPrintWarning(getString("close.cancel.question.label1") + "\n \n"
				+ getString("close.cancel.question.label2"), target);
		    }else{
			retrieveRecData();
			target.appendJavaScript("window.open('" + getPDFURL() + "')");
		    }
		}
	    }

	};

	recSheetCountForm.add(link);

	printWarningPanel = new MessageModalPanel("printWarningPanel", "OK") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
	    }
	};
	printWarningPanel.setOutputMarkupPlaceholderTag(true);
	add(printWarningPanel);

	printLimitationWarningPanel = new OkCancelCenterModalPanel("printLimitationWarningPanel", "Print", "Back") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (this.isOk()){
		    retrieveRecData();
		    target.appendJavaScript("window.open('" + getPDFURL() + "')");
		}
	    }
	};
	printLimitationWarningPanel.setOutputMarkupPlaceholderTag(true);
	add(printLimitationWarningPanel);

    }

    private String getPDFURL() {

	ResourceReference ref = getResourceReference();
	if (ref.canBeRegistered()){
	    logger.debug("Generated reference key:" + ref.getKey());
	    getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
	}

	CharSequence strUrl = RequestCycle.get().urlFor(ref, null);

	return strUrl.toString();
    }

    private ResourceReference getResourceReference() {

	final PdfByteArrayResource pdfResource = new PdfByteArrayResource() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String fetchData() {
		setPdfByteArray(null);
		try{
		    byte[] byteArray = mobileSalesWorkspaceService
			    .getRecSheetReportAsPDF(recSheetReportingSearchResults.getRecs());
		    if (byteArray != null)
			setPdfByteArray(byteArray);
		}catch(ServiceException e){
		    logger.error("Error getting rec sheet report as PDF", e);
		}
		return null;
	    }

	};

	pdfResource.fetchData();

	String uidString = UUID.randomUUID().toString().replaceAll("-", "");
	ResourceReference ref = new ResourceReference(uidString) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public IResource getResource() {
		return pdfResource;

	    }
	};
	return ref;
    }

    private int getRecDataCount() {
	List<RecSheetReportList> list = null;
	try{
	    list = mobileSalesWorkspaceService.findRecommendationsFromToDate(fromSDate, toSDate, storeId);
	}catch(ServiceException e){
	    logger.error("Error finding recommendations", e);
	    error(e.getMessage());
	    return 0;
	}

	int recSheetReportSize = 0;

	if (list != null){
	    for(RecSheetReportList recSheetReport: list){
		logger.debug("each:" + recSheetReport.getRecommendationIdList().size());
		recSheetReportSize += recSheetReport.getRecommendationIdList().size();
	    }
	}
	logger.debug("total size:" + recSheetReportSize);
	if (recSheetReportSize == 0 || recSheetReportList == null || recSheetReportList.size() == 0){
	    info("You must have search results before you can print.");
	    return 0;
	}else{
	    return recSheetReportSize;
	}

    }

    private boolean retrieveRecData() {
	List<RecSheetReportList> list = null;
	try{
	    list = mobileSalesWorkspaceService.findRecommendationsFromToDate(fromSDate, toSDate, storeId);
	}catch(ServiceException e){
	    logger.error("Error finding recommendations", e);
	    error(e.getMessage());
	    return false;
	}

	int recSheetReportSize = 0;

	if (list != null){
	    for(RecSheetReportList recSheetReport: list){
		logger.debug("each:" + recSheetReport.getRecommendationIdList().size());
		recSheetReportSize += recSheetReport.getRecommendationIdList().size();
	    }
	}

	recSheetReportingSearchResults = new RecSheetReportingSearchResults();
	List<Recommendation> recommendationList = recSheetReportingSearchResults.getRecs();

	for(RecSheetReportList recSheetReport: list){
	    for(Long recommendationId: recSheetReport.getRecommendationIdList()){
		try{
		    Recommendation rec = mobileSalesWorkspaceService.getRecommendation(recommendationId);
		    recommendationList.add(rec);
		}catch(ServiceException e){
		    logger.error("Error finding recommendation.", e);
		    error(e.getMessage());
		    return false;
		}
	    }
	}
	return true;
    }

    private void applyClass(Component component, int index, int length) {
	if (index == length - 1){
	    component.add(new AttributeAppender("class", new Model<String>("last"), " "));
	}
	if (index % 2 == 1){
	    component.add(new AttributeAppender("class", new Model<String>("odd"), " "));
	}
    }

    private void displayPrintWarning(String message, final AjaxRequestTarget target) {
	printLimitationWarningPanel.setMultiLineQuestion(message);

	if (!printLimitationWarningPanel.isOpen()){
	    if (target != null){
		printLimitationWarningPanel.open(target);
		return;
	    }
	}
    }

    class MyColumnFactory implements TableColumnRepeater.ColumnFactory {

	List<Object> data;
	int offset;
	int length;

	public MyColumnFactory(List<Object> data) {
	    this.data = data;
	    this.offset = 0;
	    this.length = data.size();
	}

	public MyColumnFactory(List<Object> data, int offset, int length) {
	    this.data = data;
	    this.offset = offset;
	    this.length = length;
	}

	@Override
	public int size() {
	    return length;
	}

	@Override
	public Component newColumn(int index, String id) {
	    Object value = data.get(offset + index);
	    Label label = new Label(id, value != null?value.toString():"");
	    if (index == length - 1){
		label.add(new AttributeAppender("class", new Model<String>("last"), " "));
	    }
	    return label;
	}
    }

}
