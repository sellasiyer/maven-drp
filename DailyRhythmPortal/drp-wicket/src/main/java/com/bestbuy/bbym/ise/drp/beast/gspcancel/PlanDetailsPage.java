package com.bestbuy.bbym.ise.drp.beast.gspcancel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
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
import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.dashboard.GspStatus;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.helpers.BeastObjectMapper;
import com.bestbuy.bbym.ise.drp.service.GSPPlanService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.StaticFormatters;
import com.bestbuy.bbym.ise.drp.util.StringMapFormatter;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class PlanDetailsPage extends BaseBeastPage {

    private static Logger logger = LoggerFactory.getLogger(PlanDetailsPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "gspPlanService")
    private GSPPlanService gspPlanService;

    private boolean formVerificationCheck = false;
    private List<Product> deviceList;
    private GSPPlan gspPlan;

    private AbstractDefaultAjaxBehavior wicketBehavior;
    private String dataSharingKey;

    public PlanDetailsPage(final PageParameters parameters) {
	super(parameters);

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();

	if (getDailyRhythmPortalSession().getProtectionPlanDetails() == null){
	    // TODO handle if details null
	}

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupId(true);
	add(feedbackPanel);

	final MapFormatter<String> statusFmt = GspStatus.generateMapFormatter(this);
	final StringMapFormatter gspTransTypeFmt = StaticFormatters.GSP_TRANSACTION_TYPE;
	final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
	final MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>();
	final String unk = getString("unknown.label");
	final String noSerial = getString("noSerialNumber.label");

	// customer info
	add(new Label("planOwnerLabel", new FormatPropertyModel<ProtectionPlan, String>(
		new PropertyModel<ProtectionPlan>(getDailyRhythmPortalSession().getProtectionPlanDetails(),
			"planOwnerName"), null, unk)));
	add(new Label("planNumberLabel", new FormatPropertyModel<ProtectionPlan, String>(
		new PropertyModel<ProtectionPlan>(getDailyRhythmPortalSession().getProtectionPlanDetails(),
			"planNumber"), null, unk)));
	add(new Label("expiresLabel", new FormatPropertyModel<ProtectionPlan, Date>(new PropertyModel<ProtectionPlan>(
		getDailyRhythmPortalSession().getProtectionPlanDetails(), "planExpiryDate"), dateFmt, unk)));
	add(new Label("descriptionLabel", new FormatPropertyModel<ProtectionPlan, String>(
		new PropertyModel<ProtectionPlan>(getDailyRhythmPortalSession().getProtectionPlanDetails(),
			"description"), null, unk)));
	add(new Label("skuLabel", new FormatPropertyModel<ProtectionPlan, String>(new PropertyModel<ProtectionPlan>(
		getDailyRhythmPortalSession().getProtectionPlanDetails(), "sku"), null, unk)));
	add(new Label("planTypeLabel",
		new FormatPropertyModel<ProtectionPlan, String>(new PropertyModel<ProtectionPlan>(
			getDailyRhythmPortalSession().getProtectionPlanDetails(), "planType"), null, unk)));
	add(new Label("statusLabel", new FormatPropertyModel<ProtectionPlan, String>(new PropertyModel<ProtectionPlan>(
		getDailyRhythmPortalSession().getProtectionPlanDetails(), "planStatus"), statusFmt, unk)));

	// GSP transaction info
	ServicePlanTransaction transaction = getDailyRhythmPortalSession().getProtectionPlanDetails()
		.getServicePlanTransactions().get(0);
	add(new Label("trans.typeLabel", new FormatPropertyModel<ServicePlanTransaction, String>(
		new PropertyModel<ServicePlanTransaction>(transaction, "gspTransType"), gspTransTypeFmt, unk)));
	add(new Label("trans.storeNumberLabel", new FormatPropertyModel<ServicePlanTransaction, String>(
		new PropertyModel<ServicePlanTransaction>(transaction, "storeNum"), null, unk)));
	add(new Label("trans.registerNumberLabel", new FormatPropertyModel<ServicePlanTransaction, String>(
		new PropertyModel<ServicePlanTransaction>(transaction, "registerNum"), null, unk)));
	add(new Label("trans.transactionNumberLabel", new FormatPropertyModel<ServicePlanTransaction, String>(
		new PropertyModel<ServicePlanTransaction>(transaction, "transactionNum"), null, unk)));
	add(new Label("trans.purchaseDateLabel", new FormatPropertyModel<ServicePlanTransaction, Date>(
		new PropertyModel<ServicePlanTransaction>(transaction, "purchaseDate"), dateFmt, unk)));
	add(new Label("trans.purchasePriceLabel", new FormatPropertyModel<ServicePlan, BigDecimal>(
		new PropertyModel<ServicePlan>(transaction, "purchasePrice"), moneyFmt, unk)));

	if (getDailyRhythmPortalSession().getProtectionPlanDetails().getCoveredProductList() == null){
	    setDeviceList(new ArrayList<Product>());
	}else{
	    setDeviceList(getDailyRhythmPortalSession().getProtectionPlanDetails().getCoveredProductList());
	}

	final ListView<Product> devicesListView = new ListView<Product>("productListView",
		new PropertyModel<List<Product>>(this, "deviceList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<Product> item) {
		Product p = item.getModelObject();
		item.add(new Label("devices.phoneLabel", new PropertyModel<String>(p, "description")));
		item.add(new Label("devices.skuLabel", new PropertyModel<String>(p, "sku")));
		item.add(new Label("devices.modelNumberLabel", new FormatPropertyModel<Product, String>(
			new PropertyModel<Product>(p, "modelNumber"), null, unk)));
		item.add(new Label("devices.serialNumberLabel", new FormatPropertyModel<Product, String>(
			new PropertyModel<Product>(p, "serialNumber"), null, noSerial)));
	    }
	};
	add(devicesListView);

	Form<Object> planDetailsForm = new Form<Object>("gspPlanDetailsForm");
	add(planDetailsForm);
	AjaxCheckBox verifyCheckbox;
	planDetailsForm.add(verifyCheckbox = new AjaxCheckBox("verifyCheckbox", new PropertyModel<Boolean>(this,
		"formVerificationCheck")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		String nextButtonState = "false";
		if (isFormVerificationCheck()){
		    nextButtonState = "true";
		}
		target.appendJavaScript("handlePlanDetailsSubmitButtonState(" + nextButtonState + ");");
	    }
	});
	verifyCheckbox.setMarkupId("verifyCheckbox");

	AjaxButton closeButton = new AjaxButton("closeButton", new ResourceModel("gspPlanDetailsForm.close.button"),
		planDetailsForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("in closeButton onSubmit");
		if (!quitModalPanel.isOpen()){
		    quitModalPanel.setQuestion(getString("gspCancelCustomerLookup.quitModal.question.label"));
		    quitModalPanel.open(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	closeButton.setMarkupId("closeButton");
	closeButton.setOutputMarkupPlaceholderTag(true);
	planDetailsForm.add(closeButton);

	AjaxButton backButton = new AjaxButton("backButton", new ResourceModel("gspPlanDetailsForm.back.button"),
		planDetailsForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		// force user to pick a new GSP plan
		getDailyRhythmPortalSession().setProtectionPlanDetails(null);
		PageParameters pp = new PageParameters();
		pp.add(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey(), dataSharingKey);
		setResponsePage(new DisplayProtectionPlansPage(pp));
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	backButton.setMarkupId("backButton");
	backButton.setOutputMarkupPlaceholderTag(true);
	planDetailsForm.add(backButton);

	AjaxButton submitButton = new AjaxButton("submitButton", new ResourceModel("gspPlanDetailsForm.submit.button"),
		planDetailsForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if (gspPlan != null){
		    getDailyRhythmPortalSession().setBestBuyCustomer(null);
		    getDailyRhythmPortalSession().setProtectionPlanList(null);
		    getDailyRhythmPortalSession().setProtectionPlanDetails(null);
		    PageParameters pp = new PageParameters();
		    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "success");
		    setResponsePage(new StatusPage(pp));
		}else{
		    doSubmit(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	submitButton.setMarkupId("submitButton");
	submitButton.setOutputMarkupPlaceholderTag(true);
	planDetailsForm.add(submitButton);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("in wicketBehavior id=" + id);
		if ("submit".equals(id)){
		    doSubmit(target);
		}else if ("message".equals(id)){
		    doMessage(target);
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
		onDomReadyJS.append("handlePlanDetailsSubmitButtonState(false);");
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		onDomReadyJS.append("bindHotKeys();");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    private void doMessage(AjaxRequestTarget target) {
	if (!messageModalPanel.isOpen()){
	    messageModalPanel.open(target);
	}
    }

    private void doSubmit(AjaxRequestTarget target) {
	if (!loadingModalPanel.isOpen()){
	    loadingModalPanel.setMessage(getString("gspPlanDetails.submitPlanForCancel.loading.label"));
	    loadingModalPanel.open(target);
	    target.appendJavaScript("wicketBehavior('submit');");
	    return;
	}

	logger.debug("submitting GSP plan for cancel for DSK=" + dataSharingKey);
	try{
	    gspPlan = BeastObjectMapper.convertToGSPPlan(getDailyRhythmPortalSession().getProtectionPlanDetails());
	    gspPlan.setDataSharingKey(dataSharingKey);
	    gspPlan.setCreatedBy(getDailyRhythmPortalSession().getDrpUser().getUserId());
	    gspPlan.setCancel(true);
	    gspPlanService.addGSPPlan(gspPlan);
	}catch(ServiceException se){
	    gspPlan = null;
	    loadingModalPanel.close(target);
	    String message = "An unexpected exception occured while attempting to add GSP plan for cancel for DSK="
		    + dataSharingKey;
	    logger.error(message, se);
	    messageModalPanel.setMessage(getString("gspCancel.generic.error"));
	    target.appendJavaScript("wicketBehavior('message');");
	    return;
	}
	loadingModalPanel.close(target);
	target.appendJavaScript("clickPlanDetailsSubmitButton();");
    }

    public List<Product> getDeviceList() {
	return deviceList;
    }

    public void setDeviceList(List<Product> deviceList) {
	this.deviceList = deviceList;
    }

    public boolean isFormVerificationCheck() {
	return formVerificationCheck;
    }

    public void setFormVerificationCheck(boolean formVerificationCheck) {
	this.formVerificationCheck = formVerificationCheck;
    }

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
}
