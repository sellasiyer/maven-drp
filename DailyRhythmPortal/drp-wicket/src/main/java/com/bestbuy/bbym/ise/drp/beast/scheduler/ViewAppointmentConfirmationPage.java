package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.domain.AppointmentCloseStatus;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.service.SchedulerService;
import com.bestbuy.bbym.ise.drp.util.SelectItem;

public class ViewAppointmentConfirmationPage extends BaseBeastPage2 {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ViewAppointmentConfirmationPage.class);

    @SpringBean(name = "schedulerService")
    private SchedulerService schedulerService;

    private SelectItem<String> selectedCancelAppointmentStatus, selectedCompletedAppointmentStatus,
	    selectedNoShowAppointmentStatus;

    private DrpUser drpUser;
    private List<Appointment> cancelGroup;
    private List<Appointment> comepleteGroup;
    private List<Appointment> noShowGroup;
    Page returnPage;

    public ViewAppointmentConfirmationPage(
	    LinkedHashMap<AppointmentCloseStatus, List<Appointment>> appointmentSelectionMap,
	    final Map<String, String> subStatusCollection) {

	drpUser = getDailyRhythmPortalSession().getDrpUser();

	Iterator<Map.Entry<AppointmentCloseStatus, List<Appointment>>> iter = appointmentSelectionMap.entrySet()
		.iterator();
	final Map.Entry<AppointmentCloseStatus, List<Appointment>> cancelStatusType = iter.next();

	final AppointmentCloseStatus appointmentCloseStatus = cancelStatusType.getKey();
	cancelGroup = cancelStatusType.getValue();

	final Map.Entry<AppointmentCloseStatus, List<Appointment>> completeStatusType = iter.next();
	final AppointmentCloseStatus appointmentCompleteStatus = completeStatusType.getKey();
	comepleteGroup = completeStatusType.getValue();

	final Map.Entry<AppointmentCloseStatus, List<Appointment>> noShowStatusType = iter.next();
	final AppointmentCloseStatus appointmentNoShowStatus = noShowStatusType.getKey();
	noShowGroup = noShowStatusType.getValue();

	ListView<Appointment> appointmentCancelListView = new ListView<Appointment>("appointmentCancelListView",
		cancelGroup) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<Appointment> item) {
		Appointment appointment = item.getModelObject();
		item.add(new Label("customerName", new Model<String>(appointment.getLastName())));
		item.add(new Label("changeStatusTo", new Model<String>(appointmentCloseStatus.name())));

		final List<SelectItem<String>> subStatusList = populateSubStatusDisplayList(appointmentCloseStatus,
			appointment.getAllowedStatus(), subStatusCollection);

		ChoiceRenderer<SelectItem<String>> dropDownRenderer = new ChoiceRenderer<SelectItem<String>>("value",
			"key");
		DropDownChoice<SelectItem<String>> statusDropDown = new DropDownChoice<SelectItem<String>>(
			"appointmentSelect", new PropertyModel<SelectItem<String>>(
				ViewAppointmentConfirmationPage.this, "selectedCancelAppointmentStatus"),
			subStatusList, dropDownRenderer) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public boolean isVisible() {
			return subStatusList.size() > 1;
		    }
		};
		item.add(statusDropDown);

	    }

	};

	form.add(appointmentCancelListView);

	ListView<Appointment> appointmentCompleteListView = new ListView<Appointment>("appointmentCompleteListView",
		comepleteGroup) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<Appointment> item) {
		Appointment appointment = item.getModelObject();
		item.add(new Label("customerName", new Model<String>(appointment.getLastName())));
		item.add(new Label("changeStatusTo", new Model<String>(appointmentCompleteStatus.name())));

		final List<SelectItem<String>> subStatusList = populateSubStatusDisplayList(appointmentCompleteStatus,
			appointment.getAllowedStatus(), subStatusCollection);

		ChoiceRenderer<SelectItem<String>> dropDownRenderer = new ChoiceRenderer<SelectItem<String>>("value",
			"key");
		DropDownChoice<SelectItem<String>> statusDropDown = new DropDownChoice<SelectItem<String>>(
			"appointmentSelect", new PropertyModel<SelectItem<String>>(
				ViewAppointmentConfirmationPage.this, "selectedCompletedAppointmentStatus"),
			subStatusList, dropDownRenderer) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public boolean isVisible() {
			return subStatusList.size() > 1;
		    }

		};
		item.add(statusDropDown);

	    }

	};
	form.add(appointmentCompleteListView);

	ListView<Appointment> appointmentNoShowListView = new ListView<Appointment>("appointmentNoShowListView",
		noShowGroup) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<Appointment> item) {
		Appointment appointment = item.getModelObject();
		item.add(new Label("customerName", new Model<String>(appointment.getLastName())));
		item.add(new Label("changeStatusTo", new Model<String>(appointmentNoShowStatus.name())));

		final List<SelectItem<String>> subStatusList = populateSubStatusDisplayList(appointmentNoShowStatus,
			appointment.getAllowedStatus(), subStatusCollection);

		ChoiceRenderer<SelectItem<String>> dropDownRenderer = new ChoiceRenderer<SelectItem<String>>("value",
			"key");
		DropDownChoice<SelectItem<String>> statusDropDown = new DropDownChoice<SelectItem<String>>(
			"appointmentSelect", new PropertyModel<SelectItem<String>>(
				ViewAppointmentConfirmationPage.this, "selectedNoShowAppointmentStatus"),
			subStatusList, dropDownRenderer) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public boolean isVisible() {
			return subStatusList.size() > 1;
		    }
		};
		item.add(statusDropDown);
	    }

	};
	form.add(appointmentNoShowListView);

    }

    public List<SelectItem<String>> populateSubStatusDisplayList(AppointmentCloseStatus status,
	    Map<AppointmentCloseStatus, List<String>> allowedStatusMap, Map<String, String> subStatusCollection) {
	List<SelectItem<String>> convertedSubStatusList = new ArrayList<SelectItem<String>>();

	List<String> subStatusList = allowedStatusMap.get(status);
	logger.debug("subStatusList.........." + subStatusList);

	for(String name: subStatusList){
	    convertedSubStatusList.add(new SelectItem<String>(name, subStatusCollection.get(name)));
	}

	return convertedSubStatusList;
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
		doSubmit(target);
		break;
	    case 3:
		logger.debug("backButton onSubmit");
		doPageBack(target);
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
    protected String getOnDomReadyJS() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
	logger.debug("in wicketBehavior id=" + id);
	if ("back".equals(id)){
	    doPageBack(target);

	}else if ("continue".equals(id)){
	    doSubmit(target);
	}
    }

    public void doPageBack(AjaxRequestTarget target) {
	setResponsePage(new ViewManageAppointmentPage(null));
    }

    public void doSubmit(AjaxRequestTarget target) {

	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("schedulerViewManagePage.updatingStatus.message"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("clickButton('#continueButton');");
	    return;
	}

	Map<String, String> appointmentStatusMap = new HashMap<String, String>();

	for(Appointment appointment: cancelGroup){
	    List<String> valLst = appointment.getAllowedStatus().get(AppointmentCloseStatus.CANCEL);
	    if (valLst.size() > 1){
		if (selectedCancelAppointmentStatus != null){
		    appointmentStatusMap.put(appointment.getAppointmentId(), selectedCancelAppointmentStatus.getKey());
		}
	    }else if (valLst.size() == 1){
		appointmentStatusMap.put(appointment.getAppointmentId(), valLst.get(0));
	    }
	}

	for(Appointment appointment: comepleteGroup){
	    List<String> valLst = appointment.getAllowedStatus().get(AppointmentCloseStatus.COMPLETE);
	    if (valLst.size() > 1){
		if (selectedCompletedAppointmentStatus != null){
		    appointmentStatusMap.put(appointment.getAppointmentId(), selectedCompletedAppointmentStatus
			    .getKey());
		}
	    }else if (valLst.size() == 1){
		appointmentStatusMap.put(appointment.getAppointmentId(), valLst.get(0));
	    }
	}
	for(Appointment appointment: noShowGroup){

	    List<String> valLst = appointment.getAllowedStatus().get(AppointmentCloseStatus.NO_SHOW);

	    if (valLst.size() > 1){
		if (selectedNoShowAppointmentStatus != null){
		    appointmentStatusMap.put(appointment.getAppointmentId(), selectedNoShowAppointmentStatus.getKey());
		}
	    }else if (valLst.size() == 1){
		appointmentStatusMap.put(appointment.getAppointmentId(), valLst.get(0));
	    }
	}

	try{
	    schedulerService.updateStatus(drpUser.getUserId(), appointmentStatusMap);

	}catch(Exception ee){
	    logger.debug("exception........." + ee);
	}

	loadingModalPanel.close(target);
	setResponsePage(new ViewManageAppointmentPage(null));

    }

    public SelectItem<String> getSelectedCancelAppointmentStatus() {
	return selectedCancelAppointmentStatus;
    }

    public void setSelectedCancelAppointmentStatus(SelectItem<String> selectedCancelAppointmentStatus) {
	this.selectedCancelAppointmentStatus = selectedCancelAppointmentStatus;
    }

    public SelectItem<String> getSelectedCompletedAppointmentStatus() {
	return selectedCompletedAppointmentStatus;
    }

    public void setSelectedCompletedAppointmentStatus(SelectItem<String> selectedCompletedAppointmentStatus) {
	this.selectedCompletedAppointmentStatus = selectedCompletedAppointmentStatus;
    }

    public SelectItem<String> getSelectedNoShowAppointmentStatus() {
	return selectedNoShowAppointmentStatus;
    }

    public void setSelectedNoShowAppointmentStatus(SelectItem<String> selectedNoShowAppointmentStatus) {
	this.selectedNoShowAppointmentStatus = selectedNoShowAppointmentStatus;
    }

}
