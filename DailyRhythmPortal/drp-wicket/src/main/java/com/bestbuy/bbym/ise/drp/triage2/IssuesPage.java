package com.bestbuy.bbym.ise.drp.triage2;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.OkDialogPanel;
import com.bestbuy.bbym.ise.drp.common.modal.MessageDialogPanel;
import com.bestbuy.bbym.ise.drp.customer.CustInfoModalPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.dashboard.GspStatus;
import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberField;
import com.bestbuy.bbym.ise.drp.validator.ValidatorPatterns;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class IssuesPage extends NewBaseNavPage {

    private static final long serialVersionUID = -3210986095763477956L;
    private static Logger logger = LoggerFactory.getLogger(IssuesPage.class);

    public enum PageState {
	GET_DEVICE, LOOKUP_GSP, GOT_DEV_AND_GSP, GET_ISSUE
    }

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    @SpringBean(name = "triageService")
    private TriageService triageService;

    // State Data
    private PageState pageState = PageState.GET_DEVICE;

    // Model Data
    private Device device = new Device();

    // Wicket Elements
    private Form<Object> form;
    private WebMarkupContainer enterDeviceContainer, viewDeviceContainer, findGspContainer, viewGspContainer,
	    noGspContainer;
    private AjaxButton backButton, submitButton, continueButton;
    private Panel issuesPanel;
    private CustInfoModalPanel custInfoModalPanel;
    private OkDialogPanel okDialog;
    private MessageDialogPanel issuePopupDialog;
    private DeviceModalPanel deviceModalPanel;

    public IssuesPage(final PageParameters parameters) {
	super(parameters);

	final MapFormatter<String> statusFmt = GspStatus.generateMapFormatter(this);
	final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
	final PhoneFormatter<String> phoneFmt = new PhoneFormatter<String>();
	final String na = getString("notApplicable.label");

	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	issuePopupDialog = new MessageDialogPanel("issuePopupDialog", getString("triageIssues.issuePopup.title.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("issuePopupDialog onClose");
		target.appendJavaScript("$('#submitButton').focus();");
	    }

	};
	issuePopupDialog.setOutputMarkupId(true);
	add(issuePopupDialog);

	form = new Form<Object>("triageIssuesForm");
	add(form);

	if (session.getSelectedLine() == null){
	    session.setSelectedLine(new Line());
	}else{
	    // Using OS field in device to hold phone number
	    device.setOs(session.getSelectedLine().getMobileNumber());
	}

	device.setProtectionPlan(new ProtectionPlan());
	if (session.getSelectedLine().getDevice() != null){
	    device.setSerialNumber(session.getSelectedLine().getDevice().getSerialNumber());
	    device.setDescription(session.getSelectedLine().getDevice().getDescription());
	    if (session.getSelectedLine().getDevice().getProtectionPlan() != null){
		device.getProtectionPlan().setPlanNumber(
			session.getSelectedLine().getDevice().getProtectionPlan().getPlanNumber());
		device.getProtectionPlan().setPlanStatus(
			session.getSelectedLine().getDevice().getProtectionPlan().getPlanStatus());
		device.getProtectionPlan().setPlanExpiryDate(
			session.getSelectedLine().getDevice().getProtectionPlan().getPlanExpiryDate());
	    }
	}

	// Have no device
	if (device.getSerialNumber() == null || device.getDescription() == null){
	    pageState = PageState.GET_DEVICE;

	    // Have device but no have GSP
	}else if (device.getProtectionPlan().getPlanNumber() == null){
	    pageState = PageState.LOOKUP_GSP;

	    // Have device and GSP
	}else{
	    pageState = PageState.GOT_DEV_AND_GSP;
	}

	enterDeviceContainer = new WebMarkupContainer("enterDeviceContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.GET_DEVICE;
	    }
	};
	enterDeviceContainer.setOutputMarkupPlaceholderTag(true);
	form.add(enterDeviceContainer);

	final PropertyModel<String> deviceNameModel = new PropertyModel<String>(device, "description");
	final TextField<String> deviceName = new TextField<String>("deviceName", deviceNameModel);
	deviceName.setOutputMarkupPlaceholderTag(true);
	deviceName.setRequired(true);
	enterDeviceContainer.add(deviceName);

	final PropertyModel<String> deviceSerialNumModel = new PropertyModel<String>(device, "serialNumber");
	final TextField<String> deviceSerialNum = new TextField<String>("deviceSerialNum", deviceSerialNumModel);
	deviceSerialNum.setOutputMarkupPlaceholderTag(true);
	deviceSerialNum.setRequired(true);
	deviceSerialNum.add(ValidatorPatterns.SERIAL_NUMBER);
	enterDeviceContainer.add(deviceSerialNum);

	final PropertyModel<String> devicePhoneNumModel = new PropertyModel<String>(device, "os");
	final PhoneNumberField devicePhoneNum = new PhoneNumberField("devicePhoneNum", devicePhoneNumModel);
	enterDeviceContainer.add(devicePhoneNum);

	viewDeviceContainer = new WebMarkupContainer("viewDeviceContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState != PageState.GET_DEVICE;
	    }
	};
	viewDeviceContainer.setOutputMarkupPlaceholderTag(true);
	form.add(viewDeviceContainer);

	viewDeviceContainer.add(new Label("deviceNameLabel", deviceNameModel));
	viewDeviceContainer.add(new Label("deviceSerialNumLabel", deviceSerialNumModel));
	viewDeviceContainer.add(new Label("devicePhoneNumLabel", new FormatPropertyModel<String, String>(
		devicePhoneNumModel, phoneFmt, na)));

	findGspContainer = new WebMarkupContainer("findGspContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.LOOKUP_GSP;
	    }
	};
	findGspContainer.setOutputMarkupPlaceholderTag(true);
	form.add(findGspContainer);

	final AjaxLink<Object> lookupGspLink = new AjaxLink<Object>("lookupGspLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("lookupGspLink onClick");
		logger.debug("custInfoModalPanel open");
		feedbackPanel.setVisible(false);
		// custInfoModalPanel.setModalState(CustInfoModalPanel.ModalState.SELECT_ANY_PURCHASE);
		// custInfoModalPanel.setModalState(CustInfoModalPanel.ModalState.SELECT_REWARD_ZONE);
		custInfoModalPanel.open(target);
	    }
	};
	lookupGspLink.setMarkupId("lookupGspLink");
	lookupGspLink.setOutputMarkupId(true);
	lookupGspLink.setOutputMarkupPlaceholderTag(true);
	findGspContainer.add(lookupGspLink);

	final PropertyModel<String> deviceGspNumModel = new PropertyModel<String>(device, "protectionPlan.planNumber");
	final TextField<String> deviceGspNum = new TextField<String>("deviceGspNum", deviceGspNumModel);
	deviceGspNum.setOutputMarkupPlaceholderTag(true);
	findGspContainer.add(deviceGspNum);

	viewGspContainer = new WebMarkupContainer("viewGspContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return (pageState == PageState.GOT_DEV_AND_GSP || pageState == PageState.GET_ISSUE)
			&& !StringUtils.isBlank(device.getProtectionPlan().getPlanNumber());
	    }
	};
	viewGspContainer.setOutputMarkupPlaceholderTag(true);
	form.add(viewGspContainer);

	viewGspContainer.add(new Label("deviceGspNumLabel", new PropertyModel<String>(device,
		"protectionPlan.planNumber")));

	final Label deviceGspStatusLabel = new Label("deviceGspStatusLabel", new FormatPropertyModel<Device, String>(
		new PropertyModel<Device>(device, "protectionPlan.planStatus"), statusFmt, "N/A"));
	viewGspContainer.add(deviceGspStatusLabel);

	final Label deviceGspExpirationDateLabel = new Label("deviceGspExpirationDateLabel",
		new FormatPropertyModel<Device, Date>(
			new PropertyModel<Device>(device, "protectionPlan.planExpiryDate"), dateFmt, "N/A"));
	viewGspContainer.add(deviceGspExpirationDateLabel);

	noGspContainer = new WebMarkupContainer("noGspContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return (pageState == PageState.GOT_DEV_AND_GSP || pageState == PageState.GET_ISSUE)
			&& StringUtils.isBlank(device.getProtectionPlan().getPlanNumber());
	    }
	};
	noGspContainer.setOutputMarkupPlaceholderTag(true);
	form.add(noGspContainer);

	issuesPanel = new EmptyPanel("issuesPanel");
	issuesPanel.setOutputMarkupPlaceholderTag(true);
	form.add(issuesPanel);

	final AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel(
		"triageIssues.cancelButton.label"), form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("cancelButton onSubmit");
		getDailyRhythmPortalSession().setTriageEvent(null);
		setResponsePage(CustomerDashboardPage.class);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("cancelButton onError");
		target.add(feedbackPanel);
	    }

	};
	cancelButton.setMarkupId("cancelButton");
	cancelButton.setOutputMarkupId(true);
	cancelButton.setOutputMarkupPlaceholderTag(true);
	cancelButton.setDefaultFormProcessing(false);
	form.add(cancelButton);

	backButton = new AjaxButton("backButton", new ResourceModel("triageIssues.backButton.label"), form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		// Must re-attach models
		deviceName.setModel(deviceNameModel);
		deviceSerialNum.setModel(deviceSerialNumModel);
		deviceGspNum.setModel(deviceGspNumModel);
		logger.debug("backButton onSubmit pageState=" + pageState);
		if (pageState == PageState.GET_ISSUE){
		    if (session.getSelectedLine() == null || session.getSelectedLine().getDevice() == null
			    || session.getSelectedLine().getDevice().getProtectionPlan() == null){
			pageState = PageState.LOOKUP_GSP;
		    }else{
			pageState = PageState.GOT_DEV_AND_GSP;
		    }
		}else if (pageState == PageState.GOT_DEV_AND_GSP){
		    pageState = PageState.LOOKUP_GSP;
		    session.getSelectedLine().getDevice().setProtectionPlan(null);
		}else if (pageState == PageState.LOOKUP_GSP){
		    pageState = PageState.GET_DEVICE;
		}
		form.setDefaultButton(continueButton);
		target.add(enterDeviceContainer);
		target.add(viewDeviceContainer);
		target.add(findGspContainer);
		target.add(viewGspContainer);
		target.add(noGspContainer);
		target.add(issuesPanel);
		targetButtons(target);
		target.appendJavaScript("$('#continueButton').focus();");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("backButton onError");
		target.add(feedbackPanel);
		target.appendJavaScript("$('#continueButton').focus();scrollToTop();");
	    }

	    @Override
	    public boolean isVisible() {
		return pageState != PageState.GET_DEVICE;
	    }
	};
	backButton.setMarkupId("backButton");
	backButton.setOutputMarkupId(true);
	backButton.setOutputMarkupPlaceholderTag(true);
	backButton.setDefaultFormProcessing(false);
	form.add(backButton);

	continueButton = new AjaxButton("continueButton", new ResourceModel("triageIssues.continueButton.label"), form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("continueButton onSubmit pageState=" + pageState);
		if (pageState == PageState.GET_DEVICE){
		    if (session.getSelectedLine() != null && session.getSelectedLine().getDevice() != null
			    && session.getSelectedLine().getDevice().getSerialNumber() != null
			    && session.getSelectedLine().getDevice().getSerialNumber().equals(device.getSerialNumber())){
			logger.debug("deviceSerialNum unchanged");
			launchAction("haveGspSerialNumUnchanged", target);
			return;
		    }else{
			logger.debug("deviceSerialNum changed");
		    }
		    openLoadingModal(getString("triageIssues.getDeviceInfo.loading.label"), target);
		    launchAction("getDevice", target);

		}else if (pageState == PageState.GOT_DEV_AND_GSP){
		    if (session.getSelectedLine() != null
			    && session.getSelectedLine().getDevice() != null
			    && session.getSelectedLine().getDevice().getProtectionPlan() != null
			    && session.getSelectedLine().getDevice().getProtectionPlan().getPlanStatus() != null
			    && session.getSelectedLine().getDevice().getProtectionPlan().getPlanStatus()
				    .equalsIgnoreCase(ServicePlan.PLAN_STATUS_ACTIVE)){
			openLoadingModal(getString("triageIssues.getTriageIssues.loading.label"), target);
			launchAction("getIssues", target);
		    }else{
			okDialog.setMessage(getString("triageIssues.gspPlanNotActive.message.label"));
			okDialog.open(target);
		    }

		}else if (pageState == PageState.LOOKUP_GSP){
		    if (StringUtils.isBlank(device.getProtectionPlan().getPlanNumber())){
			session.getSelectedLine().getDevice().setProtectionPlan(null);
			device.getProtectionPlan().setPlanNumber(null);
			device.getProtectionPlan().setPlanStatus(null);
			device.getProtectionPlan().setPlanExpiryDate(null);
			openLoadingModal(getString("triageIssues.getTriageIssues.loading.label"), target);
			launchAction("getIssues", target);
		    }else{
			openLoadingModal(getString("triageIssues.lookupGspPlan.loading.label"), target);
			launchAction("getGsp", target);
		    }
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("continueButton onError");
		target.add(feedbackPanel);
		target.appendJavaScript("$('#continueButton').focus();scrollToTop();");
	    }

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.GET_DEVICE || pageState == PageState.GOT_DEV_AND_GSP
			|| pageState == PageState.LOOKUP_GSP;
	    }

	};
	continueButton.setMarkupId("continueButton");
	continueButton.setOutputMarkupId(true);
	continueButton.setOutputMarkupPlaceholderTag(true);
	form.add(continueButton);
	form.setDefaultButton(continueButton);

	submitButton = new AjaxButton("submitButton", new ResourceModel("triageIssues.submitButton.label"), form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("submitButton onSubmit");
		IssuesPanel issPanel = (IssuesPanel) issuesPanel;
		if (issPanel.getSelectedTriageIssue() != null){
		    logger.debug("selectedTriageIssue=" + issPanel.getSelectedTriageIssue());
		    session.setSelectedTriageIssue((TriageIssue) Util.clone(issPanel.getSelectedTriageIssue()));
		    if (issPanel.getSelectedTriageIssue().getIssueDesc() != null
			    && issPanel.getSelectedTriageIssue().getIssueDesc().equals(
				    getString("triageIssues.crackedScreenIssueDescription"))){
			// Evaluate if should get screen repair devices
			List<Product> productList = null;
			if (session.getSelectedLine().getDevice() != null
				&& session.getSelectedLine().getDevice().getProtectionPlan() != null){
			    productList = session.getSelectedLine().getDevice().getProtectionPlan()
				    .getCoveredProductList();
			}
			if ((productList != null && !productList.isEmpty())
				|| device.getProtectionPlan().getPlanNumber() == null){
			    openLoadingModal(getString("triageIssues.getScreenRepairDevices.loading.label"), target);
			    launchAction("getScreenRepairDevices", target);
			    return;
			}
		    }
		    setResponsePage(DashboardPage.class);
		}else{
		    error(getString("triageIssues.noIssueSelected.message.label"));
		    target.add(feedbackPanel);
		    target.appendJavaScript("$('#submitButton').focus();scrollToTop();");
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("submitButton onError");
		target.add(feedbackPanel);
		target.appendJavaScript("$('#submitButton').focus();scrollToTop();");
	    }

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.GET_ISSUE;
	    }

	};
	submitButton.setMarkupId("submitButton");
	submitButton.setOutputMarkupId(true);
	submitButton.setOutputMarkupPlaceholderTag(true);
	form.add(submitButton);

	custInfoModalPanel = new CustInfoModalPanel("custInfoModalPanel", CustInfoModalPanel.ModalState.SELECT_GSP) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("custInfoModalPanel onClose");
		feedbackPanel.setVisible(true);
		if (getSelectedProtectionPlan() != null){
		    logger.debug("selectedProtectionPlan=" + getSelectedProtectionPlan());
		    device.getProtectionPlan().setPlanNumber(getSelectedProtectionPlan().getPlanNumber());
		    device.getProtectionPlan().setPlanStatus(getSelectedProtectionPlan().getPlanStatus());
		    device.getProtectionPlan().setPlanExpiryDate(getSelectedProtectionPlan().getPlanExpiryDate());
		    session.getSelectedLine().getDevice().setProtectionPlan(getSelectedProtectionPlan());
		    pageState = PageState.GOT_DEV_AND_GSP;
		    target.add(enterDeviceContainer);
		    target.add(viewDeviceContainer);
		    target.add(findGspContainer);
		    target.add(viewGspContainer);
		    target.add(noGspContainer);
		    target.add(issuesPanel);
		    targetButtons(target);
		}else{
		    target.add(deviceGspNum);
		    target.add(findGspContainer);
		}
		target.appendJavaScript("$('#continueButton').focus();");
	    }

	    @Override
	    public String checkSelectedProtectionPlan(ProtectionPlan gspPlan) {
		if (gspPlan == null || gspPlan.getPlanStatus() == null
			|| !gspPlan.getPlanStatus().equalsIgnoreCase(ServicePlan.PLAN_STATUS_ACTIVE)){
		    return getString("triageIssues.selectedGspPlanNotActive.message.label");
		}
		return null;
	    }
	};
	add(custInfoModalPanel);

	okDialog = new OkDialogPanel("okDialog", getString("okLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("okDialog onClose");
		pageState = PageState.LOOKUP_GSP;
		target.add(enterDeviceContainer);
		target.add(viewDeviceContainer);
		target.add(findGspContainer);
		target.add(viewGspContainer);
		target.add(noGspContainer);
		target.add(issuesPanel);
		targetButtons(target);
		target.appendJavaScript("$('#continueButton').focus();");
	    }

	};
	okDialog.setOutputMarkupPlaceholderTag(true);
	add(okDialog);

	deviceModalPanel = new DeviceModalPanel("deviceModalPanel", getString("yesLabel"), getString("noLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("deviceModalPanel onClose isYesSelected=" + isYesSelected());
		if (isYesSelected()){
		    session.getSelectedLine().getDevice().setSku("1");
		}else{
		    session.getSelectedLine().getDevice().setSku("0");
		}
		logger.debug("selectedDevice=" + session.getSelectedLine().getDevice());
		setResponsePage(DashboardPage.class);
	    }

	};
	deviceModalPanel.setOutputMarkupPlaceholderTag(true);
	add(deviceModalPanel);
    }

    private void targetButtons(AjaxRequestTarget target) {
	target.add(backButton);
	target.add(submitButton);
	target.add(continueButton);
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("$('#continueButton').focus();");
	return onDomReadyJS.toString();
    }

    @Override
    protected void doAction(String action, AjaxRequestTarget target) {
	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if ("haveGspSerialNumUnchanged".equals(action)){
	    pageState = PageState.LOOKUP_GSP;
	    logger.debug("DEVICE-PHONE=" + device.getOs());
	    session.getSelectedLine().setMobileNumber(device.getOs());
	    session.getSelectedLine().getDevice().setDescription(device.getDescription());
	    logger.debug("GSP=" + session.getSelectedLine().getDevice().getProtectionPlan());

	}else if ("getDevice".equals(action)){
	    if (session.getSelectedLine().getDevice() == null){
		session.getSelectedLine().setDevice(new Device());
	    }
	    logger.debug("DEVICE-PHONE=" + device.getOs());
	    session.getSelectedLine().setMobileNumber(device.getOs());
	    session.getSelectedLine().getDevice().setDescription(device.getDescription());
	    session.getSelectedLine().getDevice().setSerialNumber(device.getSerialNumber());
	    logger.debug("looking for GSP for device " + device.getSerialNumber());
	    try{
		ProtectionPlan gspPlan = customerDataService.findProtectionPlan(device.getSerialNumber());
		if (gspPlan == null || gspPlan.getPlanNumber() == null){
		    error(getString("triageIssues.noGspPlanFound.message.label"));
		    pageState = PageState.LOOKUP_GSP;
		    device.getProtectionPlan().setPlanNumber(null);
		    device.getProtectionPlan().setPlanStatus(null);
		    device.getProtectionPlan().setPlanExpiryDate(null);

		}else if (gspPlan.getPlanStatus() == null
			|| !gspPlan.getPlanStatus().equalsIgnoreCase(ServicePlan.PLAN_STATUS_ACTIVE)){
		    error(getString("triageIssues.foundGspPlanNotActive.message.label"));
		    pageState = PageState.LOOKUP_GSP;
		    device.getProtectionPlan().setPlanNumber(null);
		    device.getProtectionPlan().setPlanStatus(null);
		    device.getProtectionPlan().setPlanExpiryDate(null);

		}else{
		    device.getProtectionPlan().setPlanNumber(gspPlan.getPlanNumber());
		    device.getProtectionPlan().setPlanStatus(gspPlan.getPlanStatus());
		    device.getProtectionPlan().setPlanExpiryDate(gspPlan.getPlanExpiryDate());
		    logger.debug("found GSP from customerDataService with ID="
			    + device.getProtectionPlan().getPlanNumber());
		    session.getSelectedLine().getDevice().setProtectionPlan(gspPlan);
		    pageState = PageState.GOT_DEV_AND_GSP;
		}
	    }catch(ServiceException se){
		logger.error("ServiceException is " + se.getFullMessage());
		error(getString("triageIssues.lookupGspPlanFailed.message.label") + " " + se.getMessage());
		pageState = PageState.LOOKUP_GSP;
	    }catch(IseBusinessException be){
		error(getString("triageIssues.lookupGspPlanFailed.message.label") + " " + be.getMessage());
		pageState = PageState.LOOKUP_GSP;
	    }finally{
		this.closeLoadingModal(target);
	    }

	}else if ("getIssues".equals(action)){
	    logger.debug("GSP planNumber=" + device.getProtectionPlan().getPlanNumber());
	    try{
		List<TriageIssue> issueList = triageService.getIssueList();
		if (issueList == null || issueList.isEmpty()){
		    error(getString("triageIssues.noIssues.message.label"));
		    pageState = PageState.GOT_DEV_AND_GSP;
		}else{
		    issuesPanel = new IssuesPanel("issuesPanel", issueList, issuePopupDialog) {

			private static final long serialVersionUID = 1L;

			@Override
			public boolean isVisible() {
			    return pageState == PageState.GET_ISSUE;
			}
		    };
		    issuesPanel.setOutputMarkupPlaceholderTag(true);
		    form.replace(issuesPanel);
		    pageState = PageState.GET_ISSUE;
		    form.setDefaultButton(submitButton);
		    target.appendJavaScript("scrollToBottom();");
		}
	    }catch(ServiceException se){
		logger.error("ServiceException is " + se.getFullMessage());
		error(getString("triageIssues.getTriageIssuesFailed.message.label") + " " + se.getMessage());
		pageState = PageState.GOT_DEV_AND_GSP;
	    }finally{
		this.closeLoadingModal(target);
	    }

	}else if ("getScreenRepairDevices".equals(action)){
	    logger.debug("getting screen repair devices");
	    try{
		List<Product> productList = null;
		if (session.getSelectedLine().getDevice() != null
			&& session.getSelectedLine().getDevice().getProtectionPlan() != null){
		    productList = session.getSelectedLine().getDevice().getProtectionPlan().getCoveredProductList();
		}
		List<Config> deviceList = triageService.getScreenRepairSKUList();
		this.closeLoadingModal(target);
		if (deviceList == null || deviceList.isEmpty()){
		    error(getString("triageIssues.noScreenRepairDevices.message.label"));

		}else{
		    boolean isCoveredProductInRepairScreenList = false;
		    if (productList != null){
			for(Product product: productList){
			    for(Config config: deviceList){
				if (config.getParamValue().equals(product.getSku())){
				    isCoveredProductInRepairScreenList = true;
				    session.getSelectedLine().getDevice().setSku(product.getSku());
				    break;
				}
			    }
			}
		    }
		    logger.debug("isCoveredProductInRepairScreenList=" + isCoveredProductInRepairScreenList);
		    if (isCoveredProductInRepairScreenList){
			logger.debug("session.selectedLine.device.sku="
				+ session.getSelectedLine().getDevice().getSku());
		    }
		    if (device.getProtectionPlan().getPlanNumber() == null){
			deviceModalPanel.setDeviceList(deviceList);
			deviceModalPanel.setMessage(getString("triageIssues.deviceModalPanel.question.label"));
			deviceModalPanel.open(target);
			target.appendJavaScript("tablePrep(deviceModalTable);");
			return;
		    }
		    setResponsePage(DashboardPage.class);
		}
	    }catch(ServiceException se){
		logger.error("ServiceException is " + se.getFullMessage());
		this.closeLoadingModal(target);
		error(getString("triageIssues.getScreenRepairDevicesFailed.message.label") + " " + se.getMessage());
		target.appendJavaScript("$('#submitButton').focus();scrollToTop();");
	    }

	}else if ("getGsp".equals(action)){
	    logger.debug("getting GSP for planNumber " + device.getProtectionPlan().getPlanNumber());
	    try{
		ProtectionPlan gspPlan = customerDataService.getProtectionPlan(device.getProtectionPlan()
			.getPlanNumber());
		if (gspPlan == null || gspPlan.getPlanNumber() == null){
		    error(getString("triageIssues.noGspPlanFound.message.label"));
		    pageState = PageState.LOOKUP_GSP;

		}else if (gspPlan.getPlanStatus() == null
			|| !gspPlan.getPlanStatus().equalsIgnoreCase(ServicePlan.PLAN_STATUS_ACTIVE)){
		    error(getString("triageIssues.foundGspPlanNotActive.message.label"));
		    pageState = PageState.LOOKUP_GSP;

		}else{
		    device.getProtectionPlan().setPlanExpiryDate(gspPlan.getPlanExpiryDate());
		    device.getProtectionPlan().setPlanStatus(gspPlan.getPlanStatus());
		    session.getSelectedLine().getDevice().setProtectionPlan(gspPlan);
		    pageState = PageState.GOT_DEV_AND_GSP;
		}
	    }catch(ServiceException se){
		logger.error("ServiceException is " + se.getFullMessage());
		error(getString("triageIssues.lookupGspPlanFailed.message.label") + " " + se.getMessage());
		pageState = PageState.LOOKUP_GSP;
	    }catch(IseBusinessException be){
		logger.error("IseBusinessException is " + be.getFullMessage());
		error(getString("triageIssues.lookupGspPlanFailed.message.label") + " " + be.getMessage());
		pageState = PageState.LOOKUP_GSP;
	    }finally{
		this.closeLoadingModal(target);
	    }
	}
	target.add(enterDeviceContainer);
	target.add(viewDeviceContainer);
	target.add(findGspContainer);
	target.add(viewGspContainer);
	target.add(noGspContainer);
	target.add(issuesPanel);
	targetButtons(target);
	if (pageState == PageState.GET_ISSUE){
	    target.appendJavaScript("$('#submitButton').focus();");
	}else{
	    target.appendJavaScript("$('#continueButton').focus();");
	}
    }

}
