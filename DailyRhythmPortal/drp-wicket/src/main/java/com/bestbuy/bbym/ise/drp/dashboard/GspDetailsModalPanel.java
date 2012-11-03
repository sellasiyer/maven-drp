package com.bestbuy.bbym.ise.drp.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.BuybackPlan;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.StaticFormatters;
import com.bestbuy.bbym.ise.drp.util.StringMapFormatter;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.util.Util;

public class GspDetailsModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(GspDetailsModalPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private List<Product> productList;
    private List<ServicePlanTransaction> servicePlanTransList;
    private boolean showPrintReceiptContainer = false;
    private boolean showPrintReceiptLoading = false;

    private AbstractDefaultAjaxBehavior wicketBehaviorGDMP;

    public GspDetailsModalPanel(String id) {
	super(id);

	refresh();

	final FeedbackPanel feedbackPanel = new FeedbackPanel("modalFeedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final PrintReceiptResource printReceiptResource = new PrintReceiptResource(this, customerDataService,
		PrintReceiptResource.BaseObjectType.SERVICE_PLAN_TRANSACTION);
	final boolean showPrintReceiptLink = WicketUtils.getBooleanProperty("printReceiptLink.enabled", true, this);

	final StringMapFormatter gspTransTypeFmt = StaticFormatters.GSP_TRANSACTION_TYPE;
	final String na = getString("notApplicable.label");
	final String unknown = getString("unknown.label");
	final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
	final MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>();

	Label planOwnerName = new Label("planOwnerName", new FormatPropertyModel<ServicePlan, String>(
		new PropertyModel<ServicePlan>(this, "planDetails.planOwnerName"), null, na));
	planOwnerName.setOutputMarkupPlaceholderTag(true);
	add(planOwnerName);

	Label planNumber = new Label("planNumber", new FormatPropertyModel<ServicePlan, String>(
		new PropertyModel<ServicePlan>(this, "planDetails.planNumber"), null, na));
	planNumber.setOutputMarkupPlaceholderTag(true);
	add(planNumber);

	Label planType = new Label("planType", new FormatPropertyModel<ServicePlan, String>(
		new PropertyModel<ServicePlan>(this, "planDetails.planType"), null, na));
	planType.setOutputMarkupPlaceholderTag(true);
	add(planType);

	Label planExpiration = new Label("planExpiration", new FormatPropertyModel<ServicePlan, Date>(
		new PropertyModel<ServicePlan>(this, "planDetails.planExpiryDate"), new DateFormatter<Date>(), na));
	planExpiration.setOutputMarkupPlaceholderTag(true);
	add(planExpiration);

	Label planSku = new Label("planSku", new FormatPropertyModel<ServicePlan, String>(
		new PropertyModel<ServicePlan>(this, "planDetails.sku"), null, na));
	planSku.setOutputMarkupPlaceholderTag(true);
	add(planSku);

	Label planDescription = new Label("planDescription", new FormatPropertyModel<ServicePlan, String>(
		new PropertyModel<ServicePlan>(this, "planDetails.description"), null, na));
	planDescription.setOutputMarkupPlaceholderTag(true);
	add(planDescription);

	Label planStatus = new Label("planStatus", new FormatPropertyModel<ServicePlan, String>(
		new PropertyModel<ServicePlan>(this, "planDetails.planStatus"), null, na));
	planStatus.setOutputMarkupPlaceholderTag(true);
	add(planStatus);

	final String notApplicable;

	if (getPlanDetails() != null && getPlanDetails().getServicePlanTransactions() != null
		&& !getPlanDetails().getServicePlanTransactions().isEmpty()){
	    notApplicable = na;
	}else{
	    notApplicable = unknown;
	}

	final WebMarkupContainer printReceiptLoading = new WebMarkupContainer("printReceiptLoading") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return showPrintReceiptLoading;
	    }
	};
	printReceiptLoading.setOutputMarkupPlaceholderTag(true);
	add(printReceiptLoading);

	final WebMarkupContainer printReceiptContainer = new WebMarkupContainer("printReceiptContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showPrintReceiptLoading){
		    return false;
		}
		return showPrintReceiptContainer;
	    }
	};
	printReceiptContainer.setOutputMarkupPlaceholderTag(true);
	add(printReceiptContainer);

	final AjaxLink<Object> printReceiptLink = new AjaxLink<Object>("printReceiptLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("printReceiptLink onClick");
		showPrintReceiptContainer = true;
		target.add(printReceiptContainer);
		target.add(this);
		target.appendJavaScript("handleTransactionSliderState(false);");
	    }

	    @Override
	    public boolean isVisible() {
		return showPrintReceiptLink;
	    }

	    @Override
	    public boolean isEnabled() {
		return !showPrintReceiptContainer;
	    }
	};
	printReceiptLink.setOutputMarkupPlaceholderTag(true);
	add(printReceiptLink);

	final AjaxLink<Object> printReceiptYesLink = new AjaxLink<Object>("printReceiptYesLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("printReceiptYesLink onClick");
		showPrintReceiptLoading = true;
		target.add(printReceiptContainer);
		target.add(printReceiptLoading);
		target.appendJavaScript("doPrintReceiptWicketBehavior();");
	    }

	};
	printReceiptYesLink.setOutputMarkupPlaceholderTag(true);
	printReceiptContainer.add(printReceiptYesLink);

	final AjaxLink<Object> printReceiptNoLink = new AjaxLink<Object>("printReceiptNoLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("printReceiptNoLink onClick");
		showPrintReceiptContainer = false;
		target.add(printReceiptContainer);
		target.add(printReceiptLink);
		target.appendJavaScript("handleTransactionSliderState(true);");
	    }

	};
	printReceiptNoLink.setOutputMarkupPlaceholderTag(true);
	printReceiptContainer.add(printReceiptNoLink);

	final ListView<ServicePlanTransaction> transactionListView = new ListView<ServicePlanTransaction>(
		"transactionListView", new PropertyModel<List<ServicePlanTransaction>>(this, "servicePlanTransactions")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<ServicePlanTransaction> listItem) {
		final ServicePlanTransaction transaction = listItem.getModelObject();
		logger.trace("transaction=" + transaction);
		listItem.add(new Label("transactionType", new FormatPropertyModel<ServicePlanTransaction, String>(
			new PropertyModel<ServicePlanTransaction>(transaction, "gspTransType"), gspTransTypeFmt,
			notApplicable)));
		listItem.add(new Label("transactionStore", new FormatPropertyModel<ServicePlanTransaction, String>(
			new PropertyModel<ServicePlanTransaction>(transaction, "storeNum"), null, notApplicable)));
		listItem.add(new Label("transactionRegister", new FormatPropertyModel<ServicePlanTransaction, String>(
			new PropertyModel<ServicePlanTransaction>(transaction, "registerNum"), null, notApplicable)));
		listItem
			.add(new Label("transactionNumber", new FormatPropertyModel<ServicePlanTransaction, String>(
				new PropertyModel<ServicePlanTransaction>(transaction, "transactionNum"), null,
				notApplicable)));
		listItem.add(new Label("transactionPurchaseDate",
			new FormatPropertyModel<ServicePlanTransaction, Date>(
				new PropertyModel<ServicePlanTransaction>(transaction, "purchaseDate"), dateFmt,
				notApplicable)));
		listItem.add(new Label("transactionPurchasePrice",
			new FormatPropertyModel<ServicePlanTransaction, BigDecimal>(
				new PropertyModel<ServicePlanTransaction>(transaction, "purchasePrice"), moneyFmt,
				notApplicable)));
	    }

	};
	transactionListView.setOutputMarkupPlaceholderTag(true);
	add(transactionListView);

	final ListView<Product> productListView = new ListView<Product>("productListView",
		new PropertyModel<List<Product>>(this, "productList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<Product> listItem) {
		final Product product = listItem.getModelObject();
		listItem.add(new Label("productDescription", new FormatPropertyModel<Product, String>(
			new PropertyModel<Product>(product, "description"), null, na)));
		listItem.add(new Label("productSku", new FormatPropertyModel<Product, String>(
			new PropertyModel<Product>(product, "sku"), null, na)));
		listItem.add(new Label("productModel", new FormatPropertyModel<Product, String>(
			new PropertyModel<Product>(product, "modelNumber"), null, na)));
		listItem.add(new Label("productSerialNumber", new FormatPropertyModel<Product, String>(
			new PropertyModel<Product>(product, "serialNumber"), null, getString("noSerialNumber.label"))));
	    }
	};
	productListView.setOutputMarkupPlaceholderTag(true);
	add(productListView);

	final AjaxLink<Object> printLink = new AjaxLink<Object>("print") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("print onClick");
		String url = String.valueOf(getPage().urlFor(PrintGspDetailsModal.class, new PageParameters()));
		target.appendJavaScript("window.open('" + url + "')");
	    }

	};
	printLink.setOutputMarkupPlaceholderTag(true);
	add(printLink);

	AjaxLink<Object> closeLink = new AjaxLink<Object>("close") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		close(target);
	    }
	};
	closeLink.setMarkupId("close");
	closeLink.setOutputMarkupId(true);
	closeLink.setOutputMarkupPlaceholderTag(true);
	add(closeLink);

	// means by which JS can callback into Java
	wicketBehaviorGDMP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorGDMP id=" + id);
		if (id != null && id.startsWith("printReceipt")){
		    int idx = Util.getInt(id.substring("printReceipt".length()), -1) - 1;
		    if (servicePlanTransList == null || idx < 0 || idx >= servicePlanTransList.size()){
			showPrintReceiptLoading = false;
			target.add(printReceiptContainer);
			target.add(printReceiptLoading);
			feedbackPanel.error(getString("printTransactionReceipt.badTransaction.message.label"));
			target.add(feedbackPanel);
			return;
		    }
		    printReceiptResource.setDrpUser(getDailyRhythmPortalSession().getDrpUser());
		    printReceiptResource.setTransaction(servicePlanTransList.get(idx));
		    String error = printReceiptResource.fetchData();
		    showPrintReceiptLoading = false;
		    if (error != null){
			feedbackPanel.error(error);
			target.add(feedbackPanel);
			target.add(printReceiptContainer);
			target.add(printReceiptLoading);
			return;
		    }
		    showPrintReceiptContainer = false;
		    target.add(printReceiptContainer);
		    target.add(printReceiptLoading);
		    target.add(printReceiptLink);
		    target.appendJavaScript("handleTransactionSliderState(true);");
		    String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
		    ResourceReference ref = new ResourceReference(uuidString) {

			private static final long serialVersionUID = 1L;

			@Override
			public IResource getResource() {
			    return printReceiptResource;
			}
		    };
		    if (ref.canBeRegistered()){
			getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
			target.appendJavaScript("window.open('" + urlFor(ref, null) + "')");
		    }
		}
	    }
	};
	add(wicketBehaviorGDMP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehaviorGDMP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorGDMP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    public ServicePlan getPlanDetails() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	return session.getPlanDetails();
    }

    public List<ServicePlanTransaction> getServicePlanTransactions() {
	if (servicePlanTransList != null){
	    return servicePlanTransList;
	}
	servicePlanTransList = new ArrayList<ServicePlanTransaction>();
	if (getPlanDetails() != null && getPlanDetails().getServicePlanTransactions() != null
		&& !getPlanDetails().getServicePlanTransactions().isEmpty()){
	    // Make sure Sale Transactions are first
	    for(ServicePlanTransaction transaction: getPlanDetails().getServicePlanTransactions()){
		if (ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(transaction.getGspTransType())){
		    servicePlanTransList.add(transaction);
		}
	    }
	    for(ServicePlanTransaction transaction: getPlanDetails().getServicePlanTransactions()){
		if (!ServicePlanTransaction.GSP_TRANS_TYPE_SALE.equalsIgnoreCase(transaction.getGspTransType())){
		    servicePlanTransList.add(transaction);
		}
	    }
	}else{
	    servicePlanTransList.add(new ServicePlanTransaction());
	}
	return servicePlanTransList;
    }

    public List<Product> getProductList() {
	if (productList != null){
	    return productList;
	}
	productList = new ArrayList<Product>();
	if (getPlanDetails() != null){
	    if (getPlanDetails() instanceof ProtectionPlan){
		List<Product> covProdList = ((ProtectionPlan) getPlanDetails()).getCoveredProductList();
		if (covProdList != null){
		    for(Product p: covProdList){
			productList.add(p);
		    }
		}
	    }else if (getPlanDetails() instanceof BuybackPlan){
		BuybackPlan bbPlan = (BuybackPlan) getPlanDetails();
		if (bbPlan.getProduct() != null){
		    productList.add(bbPlan.getProduct());
		}
	    }
	}
	return productList;
    }

    public void refresh() {
	productList = null;
	servicePlanTransList = null;
    }

    @Override
    public String getOpenModalJS() {
	return "setupGspDetailsModalPanel();setModalPanelFocus('#close');";
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }

    @Override
    public void onClose(AjaxRequestTarget target) {
	showPrintReceiptContainer = false;
	showPrintReceiptLoading = false;
	target.appendJavaScript("handleTransactionSliderState(true);");
    }
}
