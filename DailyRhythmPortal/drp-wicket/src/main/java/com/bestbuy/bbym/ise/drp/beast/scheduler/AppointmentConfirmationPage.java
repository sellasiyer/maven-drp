package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.domain.TimeSlot;
import com.bestbuy.bbym.ise.util.Util;

public class AppointmentConfirmationPage extends BaseBeastPage2 {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(AppointmentConfirmationPage.class);

    private SchedulerRequest schedulerReq;

    private String appointmentDate;

    private String appointmentTime;

    private String reasonLabel;

    private String appointmentId;

    private String orderNo;

    public AppointmentConfirmationPage(final PageParameters parameters) {
	super(parameters);

	schedulerReq = (SchedulerRequest) Util.clone(this.getDailyRhythmPortalSession().getSchedulerReq());

	logger.debug("schedulerReq in appointment confirmation screen...." + schedulerReq);

	if (schedulerReq != null){
	    appointmentDate = getDateFromSlot(schedulerReq.getAppointmentTime());
	    appointmentTime = getTimeFromSlot(schedulerReq.getAppointmentTime());
	    reasonLabel = getReason(schedulerReq.getReason());
	    appointmentId = schedulerReq.getAppointmentId();
	    orderNo = schedulerReq.getOrderNo();
	}

	Label appointmentIdLabel = new Label("appointmentIdLabel", new PropertyModel<String>(this, "appointmentId"));
	form.add(appointmentIdLabel);

	Label orderNoLabel = new Label("orderNoLabel", new PropertyModel<String>(this, "orderNo"));
	form.add(orderNoLabel);

	Label dateLabel = new Label("dateLabel", new PropertyModel<String>(this, "appointmentDate"));
	form.add(dateLabel);

	Label timeLabel = new Label("timeLabel", new PropertyModel<String>(this, "appointmentTime"));
	form.add(timeLabel);

	Label reasonLabel = new Label("reasonLabel", new PropertyModel<String>(this, "reasonLabel"));
	form.add(reasonLabel);
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
		return "F2";
	    case 2:
		return "F4";

	}
	return null;
    }

    @Override
    protected String getButtonName(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "doneButton";
	    case 2:
		return "printButton";

	}
	return null;
    }

    @Override
    protected String getButtonStyleClasses(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "button right";
	    case 2:
		return "button right";

	}
	return null;
    }

    @Override
    protected void onButtonSubmit(int buttonId, AjaxRequestTarget target) {
	switch (buttonId) {
	    case 1:
		logger.debug("doneButton onSubmit");
		doComplete(target);
		break;
	    case 2:
		logger.debug("printButton onSubmit");
		doPrint(target);
		break;
	}
    }

    @Override
    protected IModel<String> getButtonModel(int buttonId) {
	switch (buttonId) {
	    case 1:
		return new ResourceModel("schedulerAppointmentConfirmationForm.done.button");
	    case 2:
		return new ResourceModel("schedulerAppointmentConfirmationForm.print.button");
	}
	return null;
    }

    private void doPrint(AjaxRequestTarget target) {
	logger.debug("doPrint onSubmit");
	target.appendJavaScript("window.print();");

    }

    private void doComplete(AjaxRequestTarget target) {
	logger.debug("doComplete onSubmit");
	getDailyRhythmPortalSession().clearSchedulerRequest();
	PageParameters pp = new PageParameters();
	pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "success");
	logger.info("setting the status to success....");
	setResponsePage(new StatusPage(pp));
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	// TODO Auto-generated method stub

    }

    @Override
    protected String getOnDomReadyJS() {
	// TODO Auto-generated method stub
	return null;
    }

}
