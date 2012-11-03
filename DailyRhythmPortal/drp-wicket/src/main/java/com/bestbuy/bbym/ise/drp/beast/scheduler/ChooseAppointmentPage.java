package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.list.AbstractItem;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.Styles;
import com.bestbuy.bbym.ise.drp.domain.AppointmentSlots;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.domain.TimeSlot;
import com.bestbuy.bbym.ise.drp.service.SchedulerService;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

/**
 * Scheduler choose day and time for appointment screen
 * 
 * @author a909782
 */
public class ChooseAppointmentPage extends BaseBeastPage2 {

    private static final long serialVersionUID = 1L;

    private DateTextField startDateField;
    private DateTextField endDateField;
    private DropDownChoice<SelectItem<String>> departmentDropDown;
    private DropDownChoice<SelectItem<String>> appointmentTypeDropDown;
    private DropDownChoice<SelectItem<String>> reasonDropDown;

    private Map<String, String> departmentMap = new HashMap<String, String>();
    private Map<String, String> appointmentTypeMap = new HashMap<String, String>();
    private Map<String, String> reasonMap = new HashMap<String, String>();

    private List<SelectItem<String>> departmentList = new ArrayList<SelectItem<String>>();
    private List<SelectItem<String>> appointmentTypeList = new ArrayList<SelectItem<String>>();
    private List<SelectItem<String>> reasonList = new ArrayList<SelectItem<String>>();

    private SelectItem<String> defaultSelection = new SelectItem<String>("", "");

    private SelectItem<String> selectedDepartment = defaultSelection;
    private SelectItem<String> selectedAppointmentType = defaultSelection;
    private SelectItem<String> selectedReason = defaultSelection;
    private Date startDate, endDate;

    private Date selectedDate;
    private TimeSlot selectedSlot;

    private SchedulerRequest schedulerReq;
    private WebMarkupContainer appointmentSlotsContainer;
    private RepeatingView rowRepeater;

    private DrpUser user;

    private static final String BEST_BUY_MOBILE_DEPT_ID = "1";

    private Date clientTime;

    @SpringBean(name = "schedulerService")
    private SchedulerService schedulerService;

    private static Logger logger = LoggerFactory.getLogger(ChooseAppointmentPage.class);

    FeedbackPanel feedbackPanel;

    public ChooseAppointmentPage(final PageParameters parameters) {
	super(parameters);

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	form.add(feedbackPanel);

	DailyRhythmPortalSession session = this.getDailyRhythmPortalSession();

	user = session.getDrpUser();

	startDate = getTodayDate();

	endDate = getTodayDate();

	schedulerReq = (SchedulerRequest) Util.clone(session.getSchedulerReq());

	logger.debug("schedulerReq in choose appointment  screen...." + schedulerReq);

	if (parameters != null && parameters.get(PageParameterKeys.CLIENT_TIME.getUrlParameterKey()) != null){
	    String clientTimeString = parameters.get(PageParameterKeys.CLIENT_TIME.getUrlParameterKey()).toString();
	    clientTime = Util.toDate(clientTimeString, "HH:mm_yyyy-MM-dd", new Date());
	    logger.info("clientTime=" + Util.toStringTime(clientTime));
	}

	if (schedulerReq != null){
	    logger.debug("scheduler req is not null---->>>" + schedulerReq);
	    if (schedulerReq.getDepartment() != null && schedulerReq.getAppointmentType() != null
		    && schedulerReq.getReason() != null && schedulerReq.getAppointmentTime() != null){
		startDate = schedulerReq.getStartDate();
		endDate = schedulerReq.getEndDate();
		Map<String, String> departmentMap = schedulerReq.getDepartment();
		Iterator<Map.Entry<String, String>> departmentIterator = departmentMap.entrySet().iterator();
		while(departmentIterator.hasNext()){
		    Map.Entry<String, String> dept = departmentIterator.next();
		    selectedDepartment = new SelectItem<String>(dept.getKey(), dept.getValue());
		    break;
		}
		Map<String, String> apptTypeMap = schedulerReq.getAppointmentType();
		Iterator<Map.Entry<String, String>> apptTypeIterator = apptTypeMap.entrySet().iterator();
		while(apptTypeIterator.hasNext()){
		    Map.Entry<String, String> apptType = apptTypeIterator.next();
		    selectedAppointmentType = new SelectItem<String>(apptType.getKey(), apptType.getValue());
		    break;
		}
		Map<String, String> reasonMap = schedulerReq.getReason();
		Iterator<Map.Entry<String, String>> reasonIterator = reasonMap.entrySet().iterator();
		while(reasonIterator.hasNext()){
		    Map.Entry<String, String> reason = reasonIterator.next();
		    selectedReason = new SelectItem<String>(reason.getKey(), reason.getValue());
		    break;
		}
		Map<Date, TimeSlot> apptSlotMap = schedulerReq.getAppointmentTime();
		Iterator<Map.Entry<Date, TimeSlot>> apptSlotIterator = apptSlotMap.entrySet().iterator();
		while(apptSlotIterator.hasNext()){
		    Map.Entry<Date, TimeSlot> apptSlot = apptSlotIterator.next();
		    selectedDate = apptSlot.getKey();
		    selectedSlot = apptSlot.getValue();
		    break;
		}

	    }

	}

	startDateField = new DateTextField("startDate", new PropertyModel<Date>(this, "startDate"), "MM/dd/yy");
	startDateField.setOutputMarkupPlaceholderTag(true);
	startDateField.setMarkupId("startDate");
	form.add(startDateField);

	startDateField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		clearTimeSlot(target);
		getAppointmentSlots(target);
	    }

	});

	endDateField = new DateTextField("endDate", new PropertyModel<Date>(this, "endDate"), "MM/dd/yy");
	endDateField.setOutputMarkupPlaceholderTag(true);
	endDateField.setMarkupId("endDate");
	form.add(endDateField);

	endDateField.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {

		endDate = (Date) endDateField.getDefaultModelObject();
		logger.debug("endDate:: " + endDate);

		clearTimeSlot(target);
		getAppointmentSlots(target);
	    }

	});

	try{
	    departmentMap = schedulerService.getDepartments(user.getStoreId());
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get departments";
	    logger.error(message, se);
	}catch(IseBusinessException be){
	    String message = "An unexpected exception occured while attempting to get departments";
	    logger.error(message, be);
	}

	final Iterator<Map.Entry<String, String>> mapIterator = departmentMap.entrySet().iterator();

	while(mapIterator.hasNext()){
	    Map.Entry<String, String> department = (Map.Entry<String, String>) mapIterator.next();
	    departmentList.add(new SelectItem<String>(department.getKey(), department.getValue()));
	}

	for(SelectItem<String> selected: departmentList){
	    if (BEST_BUY_MOBILE_DEPT_ID.equals(selected.getKey())){
		selectedDepartment = selected;
		break;
	    }
	}

	if (selectedDepartment != null && !selectedDepartment.equals(defaultSelection)){
	    getAppointmentTypes(selectedDepartment.getKey(), null);
	}

	ChoiceRenderer<SelectItem<String>> dropDownRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");

	departmentDropDown = new DropDownChoice<SelectItem<String>>("departmentFilter",
		new PropertyModel<SelectItem<String>>(this, "selectedDepartment"), departmentList, dropDownRenderer);
	departmentDropDown.setMarkupId("departmentFilter");
	departmentDropDown.setOutputMarkupPlaceholderTag(true);
	form.add(departmentDropDown);

	departmentDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		SelectItem<String> selected = departmentDropDown.getModelObject();
		if (selected != null){
		    logger.debug("selected department key----" + selected.getKey());
		    getAppointmentTypes(selected.getKey(), target);
		}

		clearTimeSlot(target);
		getAppointmentSlots(target);

		target.add(appointmentTypeDropDown);

	    }
	});

	if (selectedAppointmentType != null && !selectedAppointmentType.equals(defaultSelection)){
	    getReasons(selectedAppointmentType.getKey(), null);
	}

	appointmentTypeDropDown = new DropDownChoice<SelectItem<String>>("appointmentTypeFilter",
		new PropertyModel<SelectItem<String>>(this, "selectedAppointmentType"), appointmentTypeList,
		dropDownRenderer);
	appointmentTypeDropDown.setMarkupId("appointmentTypeFilter");
	appointmentTypeDropDown.setOutputMarkupPlaceholderTag(true);
	form.add(appointmentTypeDropDown);

	appointmentTypeDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		SelectItem<String> selected = appointmentTypeDropDown.getModelObject();
		if (selected != null){
		    logger.debug("selected appointment key----" + selected.getKey());
		    getReasons(selected.getKey(), target);
		}

		reasonList.clear();

		final Iterator<Map.Entry<String, String>> mapIterator = reasonMap.entrySet().iterator();

		while(mapIterator.hasNext()){
		    Map.Entry<String, String> department = (Map.Entry<String, String>) mapIterator.next();
		    reasonList.add(new SelectItem<String>(department.getKey(), department.getValue()));
		}
		clearTimeSlot(target);
		getAppointmentSlots(target);
		target.add(reasonDropDown);

	    }
	});

	reasonDropDown = new DropDownChoice<SelectItem<String>>("reasonFilter", new PropertyModel<SelectItem<String>>(
		this, "selectedReason"), reasonList, dropDownRenderer);
	reasonDropDown.setMarkupId("reasonFilter");
	reasonDropDown.setOutputMarkupPlaceholderTag(true);
	form.add(reasonDropDown);

	reasonDropDown.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		clearTimeSlot(target);
		getAppointmentSlots(target);
	    }
	});

	appointmentSlotsContainer = new WebMarkupContainer("appointmentSlotsContainer");
	appointmentSlotsContainer.setVisible(false);
	appointmentSlotsContainer.setOutputMarkupPlaceholderTag(true);
	form.add(appointmentSlotsContainer);

	if (selectedSlot != null){
	    getAppointmentSlots(null);
	}

	serviceErrorMsgModalPanel.setMessageLabel(getString("scheduler.serviceErrorMessageModalPanel.message.label"));

    }

    private Date getTodayDate() {
	Date today = Calendar.getInstance().getTime();
	SimpleDateFormat df = new SimpleDateFormat("MM/dd/yy");
	String d = df.format(today);
	try{
	    today = df.parse(d);
	}catch(ParseException e){
	    logger.debug("parse error..", e);
	}
	return today;
    }

    private void validateDate(Date startDate, Date endDate) {
	Date today = getTodayDate();

	if (startDate != null && startDate.compareTo(today) == -1){
	    logger.debug("start Date is before today...");
	    error("Start Date must be today or a future date");
	    return;
	}

	if (endDate != null && endDate.compareTo(today) == -1){
	    logger.debug("end Date is before today...");
	    error("End Date must be today or a future date");
	    return;
	}

	if (startDate != null && endDate != null){
	    if (startDate.after(endDate)){
		logger.debug("start date is after end date...");
		error("Start Date must be before end Date");
		return;
	    }
	}
    }

    private void clearTimeSlot(AjaxRequestTarget target) {
	selectedDate = null;
	selectedSlot = null;
	target.appendJavaScript("handleButtonState(false, '#continueButton');");
    }

    private void getAppointmentTypes(String departmentId, AjaxRequestTarget target) {
	try{
	    appointmentTypeMap = schedulerService.getAppointmentTypes(user.getStoreId(), departmentId);
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get appointment Types";
	    logger.error(message, se);
	    if (!serviceErrorMsgModalPanel.isOpen() && target != null){
		serviceErrorMsgModalPanel.setErrorMessage(se.getMessage());
		serviceErrorMsgModalPanel.open(target);
		return;
	    }
	}catch(IseBusinessException be){
	    String message = "Business exception occured while attempting to get appointment Types";
	    logger.error(message, be);
	    if (!serviceErrorMsgModalPanel.isOpen() && target != null){
		serviceErrorMsgModalPanel.setErrorMessage(be.getMessage());
		serviceErrorMsgModalPanel.open(target);
		return;
	    }
	}

	appointmentTypeList.clear();

	final Iterator<Map.Entry<String, String>> mapIterator = appointmentTypeMap.entrySet().iterator();

	while(mapIterator.hasNext()){
	    Map.Entry<String, String> department = (Map.Entry<String, String>) mapIterator.next();
	    appointmentTypeList.add(new SelectItem<String>(department.getKey(), department.getValue()));
	}
    }

    private void getReasons(String appointmentTypeId, AjaxRequestTarget target) {
	try{
	    reasonMap = schedulerService.getReasons(appointmentTypeId);
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get reasons";
	    logger.error(message, se);
	    if (!serviceErrorMsgModalPanel.isOpen() && target != null){
		serviceErrorMsgModalPanel.setErrorMessage(se.getMessage());
		serviceErrorMsgModalPanel.open(target);
		return;
	    }
	}catch(IseBusinessException be){
	    String message = "Business exception occured while attempting to get reasons";
	    logger.error(message, be);
	    if (!serviceErrorMsgModalPanel.isOpen() && target != null){
		serviceErrorMsgModalPanel.setErrorMessage(be.getMessage());
		serviceErrorMsgModalPanel.open(target);
		return;
	    }
	}

	reasonList.clear();

	final Iterator<Map.Entry<String, String>> mapIterator = reasonMap.entrySet().iterator();

	while(mapIterator.hasNext()){
	    Map.Entry<String, String> reason = (Map.Entry<String, String>) mapIterator.next();
	    reasonList.add(new SelectItem<String>(reason.getKey(), reason.getValue()));
	}
    }

    private String formatValue(TimeSlot timeSlot) {
	String appointmentSlot = null;
	if (timeSlot != null){
	    appointmentSlot = timeSlot.getTime();
	}
	return appointmentSlot != null?appointmentSlot:"";
    }

    private void setAvailability(TimeSlot timeSlot, Date appointmentDate) {

	logger.debug("in the set availability...");
	if (timeSlot == null || timeSlot.getAvailability() == 0){
	    return;
	}

	String appointmentSlot = null;
	if (timeSlot != null && appointmentDate != null){

	    logger.debug("appointment date..." + appointmentDate);

	    Calendar apptCalendar = new GregorianCalendar();
	    Calendar todayCalendar = new GregorianCalendar();
	    apptCalendar.setTime(appointmentDate);
	    todayCalendar.setTime(Calendar.getInstance().getTime());

	    logger.debug("today's calendar..." + todayCalendar);
	    logger.debug("appointment slot  calendar..." + apptCalendar);
	    boolean sameDay = apptCalendar.get(Calendar.YEAR) == todayCalendar.get(Calendar.YEAR)
		    && apptCalendar.get(Calendar.DAY_OF_YEAR) == todayCalendar.get(Calendar.DAY_OF_YEAR);
	    if (!sameDay){
		return;
	    }
	    SimpleDateFormat dt = new SimpleDateFormat("hh:mma");
	    appointmentSlot = timeSlot.getTime();
	    Date startTime, now;
	    try{
		startTime = dt.parse(appointmentSlot);

		logger.debug("startTime=" + Util.toStringTime(startTime) + "now=" + Util.toStringTime(clientTime));

		if (startTime != null && clientTime != null && startTime.before(clientTime)){
		    logger.debug("set availability to 0..." + startTime);
		    timeSlot.setAvailability(0);
		}
	    }catch(ParseException e){
		logger.error("Could not parse", e);
	    }
	}

    }

    private Date convertTo12HourFormat(String timeIn24HourFormat) {
	if (timeIn24HourFormat == null){
	    return null;
	}
	Date dateIn12HrFormat = null;
	String date = null;
	SimpleDateFormat df1 = null;
	try{
	    df1 = new SimpleDateFormat("hh:mm");

	    Date dateIn24HrFormat = df1.parse(timeIn24HourFormat);

	    df1 = new SimpleDateFormat("h:mma");

	    date = df1.format(dateIn24HrFormat);

	    dateIn12HrFormat = df1.parse(date);

	}catch(ParseException e){
	    logger.error("Exception while parsing the date" + e);
	}

	return dateIn12HrFormat;
    }

    @Override
    protected String getButtonFunctionKey(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "F3";
	    case 2:
		return "F4";
	    case 3:
		return "F2";
	    case 4:
		return "F1";

	}
	return null;
    }

    @Override
    protected String getButtonName(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "closeButton";
	    case 2:
		return "clearButton";
	    case 3:
		return "continueButton";
	    case 4:
		return "backButton";

	}
	return null;
    }

    @Override
    protected String getButtonStyleClasses(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "button left";
	    case 2:
		return "button left";
	    case 3:
		return "button right";
	    case 4:
		return "button right";

	}
	return null;
    }

    @Override
    protected void onButtonSubmit(int buttonId, AjaxRequestTarget target) {
	switch (buttonId) {
	    case 1:
		logger.debug("closeButton onSubmit");
		if (!quitModalPanel.isOpen()){
		    quitModalPanel.setMultiLineQuestion(getString("schedulerCustomerInfo.quitModal.question.label"));
		    quitModalPanel.open(target);
		}
		break;
	    case 2:
		logger.debug("clearButton onSubmit");
		if (!clearModalPanel.isOpen()){
		    clearModalPanel.setQuestion(getString("schedulerCustomerInfo.clearModal.question.label"));
		    clearModalPanel.open(target);
		}
		break;
	    case 3:
		logger.debug("continueButton onSubmit");
		doContinue(target);
		break;
	    case 4:
		logger.debug("backButton onSubmit");
		doBack(target);
		break;
	}
    }

    @Override
    protected IModel<String> getButtonModel(int buttonId) {
	switch (buttonId) {
	    case 1:
		return new ResourceModel("schedulerCustomerInfoForm.close.button");
	    case 2:
		return new ResourceModel("schedulerCustomerInfoForm.clear.button");
	    case 3:
		return new ResourceModel("schedulerCustomerInfoForm.continue.button");
	    case 4:
		return new ResourceModel("schedulerCustomerInfoForm.back.button");
	}
	return null;
    }

    @Override
    protected boolean getDefaultFormProcessingProperty(int buttonId) {
	switch (buttonId) {
	    case 1:
		return false;
	    case 2:
		return true;
	    case 3:
		return true;
	    case 4:
		return false;
	}
	return true;
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
	logger.debug("in wicketBehavior id=" + id);
	if (id != null && id.startsWith("clientTime")){
	    clientTime = Util.toDate(id.substring("clientTime".length()), "HH:mm_yyyy-MM-dd", new Date());
	    logger.info("clientTime=" + Util.toStringTime(clientTime));
	}else if (id != null && id.equalsIgnoreCase("getAppointmentSlots")){
	    getAppointmentSlots(target);
	}
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append(buildValidationJS());
	onDomReadyJS.append("bindHotKeys();");
	if (selectedDate != null && selectedSlot != null){
	    onDomReadyJS.append("handleButtonState(true, '#continueButton');");
	}else{
	    onDomReadyJS.append("handleButtonState(false, '#continueButton');");
	}

	onDomReadyJS.append("doFormFieldValidation(chooseApptValidation);getClientTime(wicketBehavior);");
	return onDomReadyJS.toString();
    }

    private String buildValidationJS() {
	StringBuilder validateJS = new StringBuilder();

	return validateJS.toString();
    }

    @Override
    protected void quitModalPanelOnClose(AjaxRequestTarget target) {
	if (quitModalPanel.isOk()){
	    PageParameters pp = new PageParameters();
	    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
	    logger.info("setting the status to cancel...");
	    setResponsePage(new StatusPage(pp));
	}
    }

    @Override
    protected void clearModalPanelOnClose(AjaxRequestTarget target) {
	logger.debug("clear onSubmit");
	if (clearModalPanel.isOk()){
	    startDate = null;
	    endDate = null;
	    selectedDepartment = defaultSelection;
	    selectedAppointmentType = defaultSelection;
	    selectedReason = defaultSelection;
	    appointmentSlotsContainer.setVisible(false);
	    target.add(startDateField);
	    target.add(endDateField);
	    target.add(departmentDropDown);
	    target.add(appointmentTypeDropDown);
	    target.add(reasonDropDown);
	    target.add(appointmentSlotsContainer);

	}
    }

    private void doBack(AjaxRequestTarget target) {
	logger.debug("back onSubmit ");
	if (schedulerReq != null && schedulerReq.getAppointmentId() != null){
	    logger.debug("selected AppointmentId :: " + schedulerReq.getAppointmentId());
	    setResponsePage(new ViewManageAppointmentPage(null));
	}else{
	    setResponsePage(CustomerInfoPage.class);
	}

    }

    private void doContinue(AjaxRequestTarget target) {
	logger.debug("continue onSubmit");
	logger.debug("startdate ---" + startDate);
	logger.debug("enddate ---" + endDate);
	logger.debug("selected department ---" + selectedDepartment);
	logger.debug("selected appointment type ---" + selectedAppointmentType);
	logger.debug("selected reason ---" + selectedReason);
	logger.debug("selected day ---" + selectedDate);
	logger.debug("selected slot time ---" + selectedSlot);

	schedulerReq.setStartDate(startDate);
	schedulerReq.setEndDate(endDate);

	Map<String, String> departmentMap = new HashMap<String, String>();
	departmentMap.put(selectedDepartment.getKey(), selectedDepartment.getValue());
	Map<String, String> appointmentTypeMap = new HashMap<String, String>();
	appointmentTypeMap.put(selectedAppointmentType.getKey(), selectedAppointmentType.getValue());
	Map<String, String> reasonMap = new HashMap<String, String>();
	reasonMap.put(selectedReason.getKey(), selectedReason.getValue());

	schedulerReq.setDepartment(departmentMap);
	schedulerReq.setAppointmentType(appointmentTypeMap);
	schedulerReq.setReason(reasonMap);

	Map<Date, TimeSlot> appointmentTime = new HashMap<Date, TimeSlot>();
	appointmentTime.put(selectedDate, selectedSlot);
	schedulerReq.setAppointmentTime(appointmentTime);

	logger.debug("scheduler req in the session..." + schedulerReq);
	this.getDailyRhythmPortalSession().setSchedulerReq(schedulerReq);

	try{
	    schedulerService.reserveSlot(schedulerReq);
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while reserving appointment slots";
	    logger.error(message, se);
	    if (!serviceErrorMsgModalPanel.isOpen()){
		serviceErrorMsgModalPanel.setErrorMessage(se.getMessage());
		serviceErrorMsgModalPanel.open(target);
		return;
	    }
	}catch(IseBusinessException be){
	    String message = "An unexpected exception occured while reserving the appointment slots";
	    logger.error(message, be);
	    if (!serviceErrorMsgModalPanel.isOpen()){
		serviceErrorMsgModalPanel.setErrorMessage(be.getMessage());
		serviceErrorMsgModalPanel.open(target);
		return;
	    }
	}

	setResponsePage(AppointmentDetailsPage.class);
    }

    private void getAppointmentSlots(AjaxRequestTarget target) {
	logger.debug("in the get appointments slots method....");

	validateDate(startDate, endDate);
	if (hasErrorMessage()){
	    return;
	}

	if (startDate != null && !"".equals(startDate) && endDate != null && !"".equals(endDate)
		&& selectedDepartment != null && !selectedDepartment.equals(defaultSelection)
		&& selectedAppointmentType != null && !selectedAppointmentType.equals(defaultSelection)
		&& selectedReason != null && !selectedReason.equals(defaultSelection)){
	    if (!loadingModalPanel.isOpen() && target != null){
		loadingModalPanel
			.setMessage(getString("schedulerChooseAppointmentForm.retrieveAppointmentSlots.loading.label"));
		loadingModalPanel.open(target);
		target.appendJavaScript("getAppointmentSlots(wicketBehavior);");
		return;
	    }

	    logger.debug("All mandatory fields are entered, now invoke the get appointmentslots service");
	    schedulerReq.setUser(user);
	    schedulerReq.setStartDate(startDate);
	    schedulerReq.setEndDate(endDate);
	    Map<String, String> departmentMap = new HashMap<String, String>();
	    departmentMap.put(selectedDepartment.getKey(), selectedDepartment.getValue());
	    Map<String, String> appointmentTypeMap = new HashMap<String, String>();
	    appointmentTypeMap.put(selectedAppointmentType.getKey(), selectedAppointmentType.getValue());
	    schedulerReq.setDepartment(departmentMap);
	    schedulerReq.setAppointmentType(appointmentTypeMap);
	    try{
		AppointmentSlots slots = schedulerService.getAppointmentSlots(schedulerReq);
		logger.debug("slots from the service..." + slots);

		if (!slots.getAvailableSlots().isEmpty()){
		    appointmentSlotsContainer.setVisible(true);
		    buildRepeaterView(slots);
		}
		if (target != null){
		    target.add(appointmentSlotsContainer);
		    loadingModalPanel.close(target);
		}
	    }catch(ServiceException se){
		String message = "An unexpected exception occured while attempting to get appointment slots";
		logger.error(message, se);
		if (target != null){
		    loadingModalPanel.close(target);
		    if (!serviceErrorMsgModalPanel.isOpen()){
			serviceErrorMsgModalPanel.setErrorMessage(se.getMessage());
			serviceErrorMsgModalPanel.open(target);
			return;
		    }
		}

	    }catch(IseBusinessException be){
		String message = "Business exception occured while attempting to get appointment slots";
		logger.error(message, be);
		if (target != null){
		    loadingModalPanel.close(target);
		    if (!serviceErrorMsgModalPanel.isOpen()){
			serviceErrorMsgModalPanel.setErrorMessage(be.getMessage());
			serviceErrorMsgModalPanel.open(target);
			return;
		    }
		}

	    }
	}else{
	    if (appointmentSlotsContainer != null){
		appointmentSlotsContainer.setVisible(false);
		if (target != null){
		    target.add(appointmentSlotsContainer);
		    target.appendJavaScript("handleContinueButtonState(false);");
		}
	    }
	}
    }

    private void buildRepeaterView(AppointmentSlots slots) {
	rowRepeater = new RepeatingView("rowRepeater");
	rowRepeater.setOutputMarkupPlaceholderTag(true);
	appointmentSlotsContainer.addOrReplace(rowRepeater);

	List<Date> dates = new ArrayList<Date>(slots.getAvailableSlots().keySet());
	Collections.sort(dates);
	for(Date date: dates){
	    List<TimeSlot> timeSlots = slots.getAvailableSlots().get(date);
	    AbstractItem item = new AbstractItem(rowRepeater.newChildId());
	    rowRepeater.add(item);

	    logger.debug("timeslotlist...." + timeSlots);
	    logger.debug("slot date..." + date);

	    item.add(new Label("dayLabel", Util.toEmptyString(date, "EEEE MMM dd")));

	    final Date finalDate = date;
	    ListView<TimeSlot> timeSlotsListView = new ListView<TimeSlot>("timeSlotsListView", timeSlots) {

		private static final long serialVersionUID = 1L;

		@Override
		public void populateItem(final ListItem<TimeSlot> listItem) {
		    final TimeSlot timeSlot = listItem.getModelObject();

		    listItem.setOutputMarkupPlaceholderTag(true);

		    AjaxLink<Void> slotLink = new AjaxLink<Void>("slotLink") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
			    logger.debug("on click slotLink...");
			    if (timeSlot != null && timeSlot.getAvailability() == 1){
				selectedSlot = timeSlot;
				selectedDate = finalDate;
				logger.debug("selectedDate--->" + selectedDate + "selectedSlot--->>" + selectedSlot);
				target.appendJavaScript("bindTimeClick();");
				target.appendJavaScript("handleContinueButtonState(true);");

			    }
			}
		    };

		    listItem.add(slotLink);

		    Label slotlabel = new Label("slotlabel", formatValue(timeSlot));

		    logger.debug("timeslot time..." + timeSlot.getTime());

		    logger.debug("selected slot.." + selectedSlot);

		    logger.debug("selected date..." + selectedDate);

		    setAvailability(timeSlot, finalDate);

		    if (timeSlot != null && selectedSlot != null && selectedDate != null
			    && timeSlot.getTime().equals(selectedSlot.getTime()) && selectedDate.equals(finalDate)){
			logger.debug("selected date and slot...change the color");
			listItem.add(new AttributeAppender("class", new Model<String>(Styles.SCHEDULER_SLOT_SELECTED),
				" "));
		    }else if (timeSlot != null && timeSlot.getAvailability() == 0){
			listItem.add(new AttributeAppender("class",
				new Model<String>(Styles.SCHEDULER_SLOT_UNAVAILABLE), " "));
		    }else if (timeSlot != null && timeSlot.getAvailability() == 1){
			listItem.add(new AttributeAppender("class", new Model<String>(Styles.SCHEDULER_SLOT_AVAILABLE),
				" "));
		    }
		    slotLink.add(slotlabel);
		}
	    };

	    item.add(timeSlotsListView);
	}
    }
}
