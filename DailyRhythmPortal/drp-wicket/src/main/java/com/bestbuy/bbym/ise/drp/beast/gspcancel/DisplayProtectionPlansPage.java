package com.bestbuy.bbym.ise.drp.beast.gspcancel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.dashboard.GspStatus;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.GSPPlanService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatModel;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class DisplayProtectionPlansPage extends BaseBeastPage2 {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(DisplayProtectionPlansPage.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    @SpringBean(name = "gspPlanService")
    private GSPPlanService gspPlanService;

    private AbstractDefaultAjaxBehavior gspPlanRadioGroupOnClick;
    private long selectedGspPlanId = 0L;
    private String getGspPlansJS;
    private String dataSharingKey;

    private ProtectionPlansDataProvider protectionPlansDataProvider;
    private AjaxFallbackDefaultDataTable<ProtectionPlan> gspPlansTable;
    private WebMarkupContainer gspPlansContainer, noGspPlansContainer, verificationContainer;

    private boolean verificationEnabled = false;

    public DisplayProtectionPlansPage(final PageParameters parameters) {
	super(parameters);

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();
	logger.debug("passed in DSK=" + dataSharingKey);

	if (parameters != null){
	    if (WicketUtils.determineBooleanParameter(parameters, PageParameterKeys.GET_GSP_PLANS.getUrlParameterKey())
		    && getDailyRhythmPortalSession().getBestBuyCustomer() != null){
		logger.debug("need to get GSP plans");
		getDailyRhythmPortalSession().setProtectionPlanList(null);
		getDailyRhythmPortalSession().setProtectionPlanDetails(null);
		getGspPlansJS = "wicketBehavior('plans');";
	    }
	}

	if (getDailyRhythmPortalSession().getProtectionPlanList() != null){
	    logger.debug("# GSP plans=" + getDailyRhythmPortalSession().getProtectionPlanList().size());
	    long count = 0L;
	    for(ProtectionPlan pp: getDailyRhythmPortalSession().getProtectionPlanList()){
		count++;
		pp.setId(new Long(count));
	    }
	}else{
	    logger.debug("GSP plans=null");
	}
	logger.debug("selectedCustomer=" + getDailyRhythmPortalSession().getBestBuyCustomer());

	final String unk = getString("unknown.label");

	// bby customer details
	if (getDailyRhythmPortalSession().getBestBuyCustomer() == null){
	    form.add(new Label("customerNameLabel", unk));
	    form.add(new Label("customerEmailLabel", unk));
	    form.add(new Label("customerAddressLabel", unk));
	}else{
	    form.add(new Label("customerNameLabel", new FormatModel<String>(getDailyRhythmPortalSession()
		    .getBestBuyCustomer().getNameString(), unk)));
	    form.add(new Label("customerEmailLabel", new FormatModel<String>(getDailyRhythmPortalSession()
		    .getBestBuyCustomer().getEmail(), unk)));
	    form.add(new Label("customerAddressLabel", new FormatModel<String>(getDailyRhythmPortalSession()
		    .getBestBuyCustomer().getAddressString(), unk)));
	}

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	form.add(feedbackPanel);

	protectionPlansDataProvider = new ProtectionPlansDataProvider();
	protectionPlansDataProvider.setGspPlanList(getDailyRhythmPortalSession().getProtectionPlanList());

	final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
	final MapFormatter<String> statusFmt = GspStatus.generateMapFormatter(this);

	final List<IColumn<ProtectionPlan>> columns = new ArrayList<IColumn<ProtectionPlan>>();
	columns.add(new AbstractColumn<ProtectionPlan>(null, "lastName") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ProtectionPlan row = (ProtectionPlan) rowModel.getObject();
		if (row != null
			&& (ServicePlan.PLAN_STATUS_ACTIVE.equalsIgnoreCase(row.getPlanStatus()) || ServicePlan.PLAN_STATUS_ON_HOLD
				.equalsIgnoreCase(row.getPlanStatus()))){
		    cellItem.add(new DisplayProtectionPlansPage.SelectRowPanel(componentId, rowModel, row));
		}else{
		    cellItem.add(new DisplayProtectionPlansPage.DisabledSelectRowPanel(componentId, rowModel));
		}
	    }
	});
	columns.add(new FormatPropertyColumn<ProtectionPlan, String>(new ResourceModel(
		"gspCancelDisplayGspPlans.planType.column"), "planType", "planType", null, unk));
	columns.add(new FormatPropertyColumn<ProtectionPlan, String>(new ResourceModel(
		"gspCancelDisplayGspPlans.planStatus.column"), "planStatus", "planStatus", statusFmt, unk));
	columns.add(new FormatPropertyColumn<ProtectionPlan, String>(new ResourceModel(
		"gspCancelDisplayGspPlans.planDescription.column"), "description", "description", null, unk));
	columns.add(new FormatPropertyColumn<ProtectionPlan, String>(new ResourceModel(
		"gspCancelDisplayGspPlans.planNumber.column"), "planNumber", "planNumber", null, unk));
	columns.add(new FormatPropertyColumn<ProtectionPlan, Date>(new ResourceModel(
		"gspCancelDisplayGspPlans.planStartDate.column"), "planEffectiveDate", "planEffectiveDate", dateFmt,
		unk) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});
	columns.add(new FormatPropertyColumn<ProtectionPlan, Date>(new ResourceModel(
		"gspCancelDisplayGspPlans.planExpiration.column"), "planExpiryDate", "planExpiryDate", dateFmt, unk));
	columns.add(new AbstractColumn<ProtectionPlan>(new ResourceModel(
		"gspCancelDisplayGspPlans.deviceDescription.column"), "planNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ProtectionPlan row = (ProtectionPlan) rowModel.getObject();
		cellItem.add(new DisplayProtectionPlansPage.DeviceDescriptionPanel(componentId, rowModel, row, unk));
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});
	columns.add(new AbstractColumn<ProtectionPlan>(new ResourceModel(
		"gspCancelDisplayGspPlans.deviceSerialNumber.column"), "planNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ProtectionPlan row = (ProtectionPlan) rowModel.getObject();
		cellItem.add(new DisplayProtectionPlansPage.DeviceSerialNumberPanel(componentId, rowModel, row, unk));
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});

	gspPlansContainer = new WebMarkupContainer("gspPlansContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return protectionPlansDataProvider.size() > 0;
	    }
	};
	gspPlansContainer.setMarkupId("gspPlansContainer");
	gspPlansContainer.setOutputMarkupPlaceholderTag(true);
	form.add(gspPlansContainer);

	gspPlansTable = new AjaxFallbackDefaultDataTable<ProtectionPlan>("gspPlansTable", columns,
		protectionPlansDataProvider, 200);
	gspPlansTable.setOutputMarkupPlaceholderTag(true);
	gspPlansContainer.add(gspPlansTable);

	gspPlanRadioGroupOnClick = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("onClick id=" + id);
		selectedGspPlanId = Util.getLong(id, -1L);
		String nextButtonState = "false";
		if (isVerificationEnabled()){
		    nextButtonState = "true";
		}
		target.appendJavaScript("handleButtonState(" + nextButtonState + ", '#nextButton');");
	    }
	};
	add(gspPlanRadioGroupOnClick);

	// no GSP plans
	noGspPlansContainer = new WebMarkupContainer("noGspPlansContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return protectionPlansDataProvider.size() <= 0;
	    }
	};
	noGspPlansContainer.setOutputMarkupPlaceholderTag(true);
	form.add(noGspPlansContainer);

	final Label noGspPlansLabel = new Label("noGspPlansLabel", new ResourceModel(
		"gspCancelDisplayGspPlans.noGspPlansLabel.label"));
	noGspPlansLabel.setOutputMarkupPlaceholderTag(true);
	noGspPlansContainer.add(noGspPlansLabel);

	// verification check
	verificationContainer = new WebMarkupContainer("verificationContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return protectionPlansDataProvider.size() > 0;
	    }
	};
	verificationContainer.setOutputMarkupPlaceholderTag(true);
	form.add(verificationContainer);

	AjaxCheckBox verificationCheckbox;
	verificationContainer.add(verificationCheckbox = new AjaxCheckBox("verificationCheckbox",
		new PropertyModel<Boolean>(this, "verificationEnabled")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		String nextButtonState = "false";
		if (selectedGspPlanId > 0L && isVerificationEnabled()){
		    nextButtonState = "true";
		}
		target.appendJavaScript("handleButtonState(" + nextButtonState + ", '#nextButton');");
	    }
	});
	// verificationCheckbox.setOutputMarkupId(true);
	verificationCheckbox.setMarkupId("verificationCheckbox");

	String verificationLabel;
	if (getDailyRhythmPortalSession().getBestBuyCustomer() == null
		|| StringUtils.isBlank(getDailyRhythmPortalSession().getBestBuyCustomer().getNameString())){
	    verificationLabel = StringUtils.replace(getString("gspCancelDisplayGspPlansForm.verification.label"),
		    "${name}", unk);
	}else{
	    verificationLabel = StringUtils.replace(getString("gspCancelDisplayGspPlansForm.verification.label"),
		    "${name}", getDailyRhythmPortalSession().getBestBuyCustomer().getNameString());
	}
	verificationContainer.add(new Label("verificationLabel", verificationLabel));
    }

    class SelectRowPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectRowPanel(String id, final IModel<?> model, final ProtectionPlan row) {
	    super(id, model);

	    final Radio<Object> customerRadio = new Radio<Object>("gspPlanRadio") {

		private static final long serialVersionUID = 1L;

		@Override
		protected void onComponentTag(final ComponentTag tag) {
		    tag.put("onClick", "wicketAjaxGet('" + gspPlanRadioGroupOnClick.getCallbackUrl() + "&id="
			    + row.getId() + "');");
		}
	    };
	    add(customerRadio);
	}
    }

    class DisabledSelectRowPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DisabledSelectRowPanel(String id, final IModel<?> model) {
	    super(id, model);
	}
    }

    class DeviceDescriptionPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DeviceDescriptionPanel(String id, final IModel<?> model, final ProtectionPlan row,
		final String notAvailableLabel) {
	    super(id, model);

	    StringBuilder sb = new StringBuilder();
	    if (row == null){
		sb.append(notAvailableLabel);

	    }else{
		if (row.getCoveredProductList() == null || row.getCoveredProductList().isEmpty()){
		    sb.append(notAvailableLabel);
		}else{
		    int size = row.getCoveredProductList().size(), i = 0;
		    for(Product p: row.getCoveredProductList()){
			if (p.getDescription() == null){
			    sb.append(notAvailableLabel);
			}else{
			    sb.append(p.getDescription());
			}
			i++;
			if (i < size){
			    sb.append("<br/>");
			}
		    }
		}
	    }
	    Label deviceDescriptionList = new Label("deviceDescriptionList", sb.toString());
	    deviceDescriptionList.setEscapeModelStrings(false);
	    add(deviceDescriptionList);
	}
    }

    class DeviceSerialNumberPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DeviceSerialNumberPanel(String id, final IModel<?> model, final ProtectionPlan row,
		final String notAvailableLabel) {
	    super(id, model);

	    StringBuilder sb = new StringBuilder();
	    if (row == null){
		sb.append(notAvailableLabel);

	    }else{
		if (row.getCoveredProductList() == null || row.getCoveredProductList().isEmpty()){
		    sb.append(notAvailableLabel);
		}else{
		    int size = row.getCoveredProductList().size(), i = 0;
		    for(Product p: row.getCoveredProductList()){
			if (p.getSerialNumber() == null){
			    sb.append(getString("noSerialNumber.label"));
			}else{
			    sb.append(p.getSerialNumber());
			}
			i++;
			if (i < size){
			    sb.append("<br/>");
			}
		    }
		}
	    }
	    Label deviceSerialNumberList = new Label("deviceSerialNumberList", sb.toString());
	    deviceSerialNumberList.setEscapeModelStrings(false);
	    add(deviceSerialNumberList);
	}
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
		return "nextButton";
	    case 3:
		return "newSearchButton";
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
    protected IModel<String> getButtonModel(int buttonId) {
	switch (buttonId) {
	    case 1:
		return new ResourceModel("gspCancelDisplayGspPlansForm.close.button");
	    case 2:
		return new ResourceModel("gspCancelDisplayGspPlansForm.next.button");
	    case 3:
		return new ResourceModel("gspCancelDisplayGspPlansForm.newSearch.button");
	}
	return null;
    }

    @Override
    protected void onButtonSubmit(int buttonId, AjaxRequestTarget target) {
	switch (buttonId) {
	    case 1:
		logger.debug("closeButton onSubmit");
		logger.debug("getGspPlansJS=" + getGspPlansJS);
		// Refresh page if getting GSP plans upon page creation
		if (getGspPlansJS != null){
		    PageParameters pp = new PageParameters();
		    pp.add(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey(), dataSharingKey);
		    setResponsePage(new DisplayProtectionPlansPage(pp));
		    return;
		}
		if (!quitModalPanel.isOpen()){
		    quitModalPanel.setQuestion(getString("gspCancelCustomerLookup.quitModal.question.label"));
		    quitModalPanel.open(target);
		}
		break;
	    case 2:
		logger.debug("nextButton onSubmit");
		if (getDailyRhythmPortalSession().getProtectionPlanDetails() != null){
		    PageParameters pp = new PageParameters();
		    pp.add(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey(), dataSharingKey);
		    setResponsePage(new PlanDetailsPage(pp));
		}else{
		    doNext(target);
		}
		break;
	    case 3:
		logger.debug("newSearchButton onSubmit");
		logger.debug("DSK=" + dataSharingKey);
		getDailyRhythmPortalSession().setProtectionPlanList(null);
		PageParameters pp = new PageParameters();
		pp.add(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey(), dataSharingKey);
		pp.add(PageParameterKeys.NEW_CUSTOMER_SEARCH.getUrlParameterKey(), "true");
		setResponsePage(new CustomerLookupPage(pp));
		break;
	}
    }

    @Override
    protected void quitModalPanelOnClose(AjaxRequestTarget target) {
	if (quitModalPanel.isOk()){
	    getDailyRhythmPortalSession().setBestBuyCustomer(null);
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
	    PageParameters pp = new PageParameters();
	    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
	    setResponsePage(new StatusPage(pp));
	}
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
	logger.debug("in wicketBehavior id=" + id);
	if ("next".equals(id)){
	    doNext(target);
	}else if ("message".equals(id)){
	    doMessage(target);
	}else if ("plans".equals(id)){
	    doPlans(target);
	}
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("handleButtonState(false, '#nextButton');");
	if (getGspPlansJS != null){
	    onDomReadyJS.append(getGspPlansJS);
	}else{
	    onDomReadyJS.append("setupGspPlansTable();");
	}
	return onDomReadyJS.toString();
    }

    private void doMessage(AjaxRequestTarget target) {
	if (!messageModalPanel.isOpen()){
	    messageModalPanel.open(target);
	}
    }

    private void doNext(AjaxRequestTarget target) {
	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("gspCancelDisplayGspPlans.planDetailsRetrieve.loading.label"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("wicketBehavior('next');");
	    return;
	}

	ProtectionPlan servicePlan = null;
	for(ProtectionPlan pp: getDailyRhythmPortalSession().getProtectionPlanList()){
	    if (pp.getId() != null && pp.getId().longValue() == selectedGspPlanId){
		servicePlan = pp;
		break;
	    }
	}
	if (servicePlan == null){
	    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
	    loadingModalPanel.close(target);
	    return;
	}

	logger.debug("getting GSP plans details");
	try{
	    getDailyRhythmPortalSession().setProtectionPlanDetails(
		    (ProtectionPlan) customerDataService.getServicePlanDetails(servicePlan));
	    if (getDailyRhythmPortalSession().getProtectionPlanDetails() == null){
		throw new IseBusinessException("ProtectionPlan object is null");
	    }
	    ServicePlanTransaction saleTrans = findSaleTransaction(getDailyRhythmPortalSession()
		    .getProtectionPlanDetails().getServicePlanTransactions());
	    List<ServicePlanTransaction> saleTransList = new ArrayList<ServicePlanTransaction>();
	    saleTransList.add(saleTrans);
	    getDailyRhythmPortalSession().getProtectionPlanDetails().setServicePlanTransactions(saleTransList);
	}catch(ServiceException se){
	    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get GSP plan details";
	    logger.error(message, se);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}catch(IseBusinessException be){
	    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get GSP plan details";
	    logger.error(message);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}catch(ClassCastException cce){
	    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get GSP plan details";
	    logger.error(message, cce);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}
	loadingModalPanel.close(target);
	target.appendJavaScript("clickButton('#nextButton');");
    }

    private void doPlans(AjaxRequestTarget target) {
	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("gspCancelCustomerLookup.gspPlansRetrieve.loading.label"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("wicketBehavior('plans');");
	    return;
	}

	Set<String> cancelGspPlanIdSet = new HashSet<String>();
	logger.debug("getting cancel GSP plans for DSK=" + dataSharingKey);
	try{
	    List<GSPPlan> list = gspPlanService.getGSPPlansMarkedForCancel(dataSharingKey);
	    if (list != null){
		for(GSPPlan gp: list){
		    cancelGspPlanIdSet.add(gp.getProtectionPlanId());
		}
	    }
	}catch(ServiceException se){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get cancel GSP plans for DSK="
		    + dataSharingKey;
	    logger.error(message, se);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}

	logger.debug("getting GSP plans for customer for DSK=" + dataSharingKey);
	getDailyRhythmPortalSession().setProtectionPlanList(new ArrayList<ProtectionPlan>());
	try{
	    List<ServicePlan> list = customerDataService.getAllServicePlans(getDailyRhythmPortalSession()
		    .getBestBuyCustomer());
	    if (list != null){
		for(ServicePlan sp: list){
		    if (sp instanceof ProtectionPlan){
			if (CustomerLookupPage.servicePlanDateGood(sp)){
			    if (cancelGspPlanIdSet.contains(sp.getPlanNumber())){
				sp.setPlanStatus(ServicePlan.PLAN_STATUS_MARKEDFORCANCEL);
			    }
			    getDailyRhythmPortalSession().getProtectionPlanList().add((ProtectionPlan) sp);
			}
		    }
		}
	    }
	}catch(ServiceException se){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get service plans";
	    logger.error(message, se);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}catch(IseBusinessException be){
	    getDailyRhythmPortalSession().setProtectionPlanList(null);
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to get service plans";
	    logger.error(message);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}
	loadingModalPanel.close(target);
	target.appendJavaScript("clickButton('#closeButton');");
    }

    public boolean isVerificationEnabled() {
	return verificationEnabled;
    }

    public void setVerificationEnabled(boolean verificationEnabled) {
	this.verificationEnabled = verificationEnabled;
    }

    public static ServicePlanTransaction findSaleTransaction(List<ServicePlanTransaction> transactionList) {
	if (transactionList == null || transactionList.isEmpty()){
	    return new ServicePlanTransaction();
	}

	for(ServicePlanTransaction t: transactionList){
	    if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equals(t.getGspTransType()))
		return t;
	}

	// if no 'sale' types exist, sort and use purchaseDate.
	Collections.sort(transactionList, new Comparator<ServicePlanTransaction>() {

	    @Override
	    public int compare(ServicePlanTransaction o1, ServicePlanTransaction o2) {
		Date d1 = o1.getPurchaseDate();
		Date d2 = o2.getPurchaseDate();
		return(SortUtil.sortDate(d1, d2, true));
	    }
	});

	// first item should be earliest purchase date.
	return transactionList.get(0);
    }

}
