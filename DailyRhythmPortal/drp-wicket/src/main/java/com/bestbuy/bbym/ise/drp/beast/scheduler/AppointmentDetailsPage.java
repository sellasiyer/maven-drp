package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.AjaxSelfUpdatingTimerBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.Styles;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.domain.TimeSlot;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.SchedulerService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class AppointmentDetailsPage extends BaseBeastPage2 {

    private static final long serialVersionUID = 1L;

    private SchedulerRequest schedulerReq;

    private String appointmentDate;

    private String appointmentTime;

    private String reasonLabel;

    private int minCounter;

    private int secCounter;

    private int counter;

    private String clientTime;

    private static Logger logger = LoggerFactory.getLogger(AppointmentDetailsPage.class);

    @SpringBean(name = "schedulerService")
    private SchedulerService schedulerService;

    @SpringBean(name = "drpPropertyService")
    private DrpPropertyService drpPropertyService;

    public AppointmentDetailsPage(final PageParameters parameters) {
	super(parameters);

	String schedulerTimer = null;
	try{
	    schedulerTimer = drpPropertyService.getProperty("SCHEDULER_TIMER");
	}catch(ServiceException e){
	    logger.error("ServiceException getting property value for scheduler timer " + e);
	}
	if (StringUtils.isNotBlank(schedulerTimer)){
	    minCounter = Integer.parseInt(schedulerTimer);
	    counter = minCounter * 60;
	}else{
	    minCounter = 5;
	    counter = 300;
	}

	schedulerReq = (SchedulerRequest) Util.clone(this.getDailyRhythmPortalSession().getSchedulerReq());

	logger.debug("schedulerReq in appointment details screen...." + schedulerReq);

	final Label timerLabel = new Label("timerLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (secCounter < 10){
		    return minCounter + getString("schedulerAppointmentDetailsForm.colonzero.label") + secCounter + " "
			    + getString("schedulerAppointmentDetailsForm.timer.label");
		}
		return minCounter + getString("schedulerAppointmentDetailsForm.colon.label") + secCounter + " "
			+ getString("schedulerAppointmentDetailsForm.timer.label");
	    }

	});
	timerLabel.setOutputMarkupPlaceholderTag(true);
	form.add(timerLabel);

	timerLabel.add(new AjaxSelfUpdatingTimerBehavior(Duration.seconds(1)) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onPostProcessTarget(AjaxRequestTarget target) {
		super.onPostProcessTarget(target);
		counter--;
		setTimer();

		if (counter == 60){
		    logger.debug("60 secs left");
		    timerLabel.add(new AttributeAppender("class", new Model<String>(Styles.SCHEDULER_TIMER), " "));
		}
		target.add(timerLabel);
		if (counter == 0){
		    setResponsePage(ChooseAppointmentPage.class);
		}
	    }

	});

	if (schedulerReq != null && schedulerReq.getAppointmentTime() != null){
	    appointmentDate = getDateFromSlot(schedulerReq.getAppointmentTime());
	    appointmentTime = getTimeFromSlot(schedulerReq.getAppointmentTime());
	    reasonLabel = getReason(schedulerReq.getReason());
	}

	Label dateLabel = new Label("dateLabel", new PropertyModel<String>(this, "appointmentDate"));
	form.add(dateLabel);

	Label timeLabel = new Label("timeLabel", new PropertyModel<String>(this, "appointmentTime"));
	form.add(timeLabel);

	Label reasonLabel = new Label("reasonLabel", new PropertyModel<String>(this, "reasonLabel"));
	form.add(reasonLabel);

	serviceErrorMsgModalPanel.setMessageLabel(getString("scheduler.serviceErrorMessageModalPanel.message.label"));
    }

    private void setTimer() {
	minCounter = counter / 60;
	secCounter = counter % 60;
    }

    private String getDateFromSlot(Map<Date, TimeSlot> appointmentTime) {
	String returnDate = "";
	if (appointmentTime != null){
	    Set<Date> st = appointmentTime.keySet();
	    Iterator<Date> itr = st.iterator();
	    while(itr.hasNext()){
		returnDate = Util.toEmptyString((Date) itr.next(), "EEEE, MMMM dd,yyyy");
	    }
	}

	return returnDate;
    }

    private String getTimeFromSlot(Map<Date, TimeSlot> appointmentTime) {
	TimeSlot slot = null;

	if (appointmentTime != null){
	    Collection<TimeSlot> c = appointmentTime.values();
	    Iterator<TimeSlot> itr = c.iterator();

	    while(itr.hasNext()){
		slot = itr.next();
	    }
	}

	return slot != null?slot.getTime():null;
    }

    private String getReason(Map<String, String> reasonMap) {
	Map.Entry<String, String> reason = null;

	if (reasonMap != null){
	    Iterator<Map.Entry<String, String>> itr = reasonMap.entrySet().iterator();
	    while(itr.hasNext()){
		reason = itr.next();
	    }
	}

	return reason != null?reason.getValue():null;
    }

    @Override
    protected String getButtonFunctionKey(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "F3";
	    case 2:
		return "F2";
	    case 3:
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
		return "continueButton";
	    case 3:
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
		return "button right";
	    case 3:
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
		logger.debug("continueButton onSubmit");
		doContinue(target);
		break;
	    case 3:
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
		return new ResourceModel("schedulerAppointmentDetailsForm.submit.button");
	    case 3:
		return new ResourceModel("schedulerCustomerInfoForm.back.button");
	}
	return null;
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
	logger.debug("in wicketBehavior id=" + id);
	if ("back".equals(id)){
	    doBack(target);
	}else if ("continue".equals(id)){
	    doContinue(target);
	}

	if (id != null && id.startsWith("clientTime")){
	    clientTime = id.replace("clientTime", "");
	    logger.debug("client Time in appt details...." + clientTime);
	}
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("getClientTime(wicketBehavior);");
	return onDomReadyJS.toString();
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

    private void doBack(AjaxRequestTarget target) {
	logger.debug("back onSubmit");
	PageParameters pp = new PageParameters();
	pp.add(PageParameterKeys.CLIENT_TIME.getUrlParameterKey(), clientTime);
	setResponsePage(new ChooseAppointmentPage(pp));
    }

    private void doContinue(AjaxRequestTarget target) {
	logger.debug("continue onSubmit");
	if (schedulerReq != null && schedulerReq.getAppointmentId() != null){
	    try{
		schedulerService.modifyAppointment(schedulerReq);
	    }catch(ServiceException se){
		String message = "An unexpected exception occured while modifying appointment slots";
		logger.error(message, se);
		if (!serviceErrorMsgModalPanel.isOpen()){
		    serviceErrorMsgModalPanel.setErrorMessage(se.getMessage());
		    serviceErrorMsgModalPanel.open(target);
		    return;
		}
	    }catch(IseBusinessException be){
		String message = "An unexpected exception occured while modifying the appointment slots";
		logger.error(message, be);
		if (!serviceErrorMsgModalPanel.isOpen()){
		    serviceErrorMsgModalPanel.setErrorMessage(be.getMessage());
		    serviceErrorMsgModalPanel.open(target);
		    return;
		}
	    }
	}else{
	    String appointmentId = "";
	    try{
		appointmentId = schedulerService.createAppointment(schedulerReq);
	    }catch(ServiceException se){
		String message = "An unexpected exception occured while creating appointment slots";
		logger.error(message, se);
		if (!serviceErrorMsgModalPanel.isOpen()){
		    serviceErrorMsgModalPanel.setErrorMessage(se.getMessage());
		    serviceErrorMsgModalPanel.open(target);
		    return;
		}
	    }catch(IseBusinessException be){
		String message = "An unexpected exception occured while creating the appointment slots";
		logger.error(message, be);
		if (!serviceErrorMsgModalPanel.isOpen()){
		    serviceErrorMsgModalPanel.setErrorMessage(be.getMessage());
		    serviceErrorMsgModalPanel.open(target);
		    return;
		}
	    }
	    schedulerReq.setAppointmentId(appointmentId);
	    this.getDailyRhythmPortalSession().setSchedulerReq(schedulerReq);
	}

	setResponsePage(AppointmentConfirmationPage.class);
    }

}
