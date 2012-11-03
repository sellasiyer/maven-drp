package com.bestbuy.bbym.ise.drp.dashboard;

import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.OkCancelDialogPanel;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;

public class CustomerDashboardPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CustomerDashboardPage.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private AbstractDefaultAjaxBehavior wicketBehavior;
    private OkCancelDialogPanel printReceiptDialog;
    private Product printReceiptProduct;

    public CustomerDashboardPage(final PageParameters parameters) {
	super(parameters);
	final CustomerDashboardPage thisPage = this;

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	Customer customer = session.getCustomer();
	boolean fromEtk = getRequest().getRequestParameters().getParameterValue(
		PageParameterKeys.ETK.getUrlParameterKey()).toBoolean(false);
	if (fromEtk){
	    logger.info("Performing ETK Best Buy Lookup Only");
	}else if (session.getSessionPropertyBoolean("needCustomer", false)
		&& (customer == null || customer.getSubscription() == null || customer.getSubscription().getLines() == null)){
	    logger.info("No customer info, redirecting to search");
	    throw new RestartResponseException(CustomerDashboardSearchPage.class);
	}
	logger.debug("clear workflow data");
	session.setSelectedLine(null);
	session.setWorkflowAction(null);

	final DashboardQuickActionsPanel quickActionsPanel = new DashboardQuickActionsPanel("quickActionsPanel");
	quickActionsPanel.setOutputMarkupPlaceholderTag(true);
	add(quickActionsPanel);

	final CarrierInfoPanel carrierInfoPanel = new CarrierInfoPanel("carrierInfoPanel");
	carrierInfoPanel.setOutputMarkupPlaceholderTag(true);
	add(carrierInfoPanel);

	final SearchCustomerPanel searchCustomerPanel = new SearchCustomerPanel("searchCustomerPanel");
	searchCustomerPanel.setOutputMarkupPlaceholderTag(true);
	add(searchCustomerPanel);

	final PurchaseHistoryPanel purchaseHistoryPanel = new PurchaseHistoryPanel("purchaseHistoryPanel");
	purchaseHistoryPanel.setOutputMarkupPlaceholderTag(true);
	add(purchaseHistoryPanel);

	final PrintReceiptResource printReceiptResource = new PrintReceiptResource(this, customerDataService,
		PrintReceiptResource.BaseObjectType.PRODUCT);

	printReceiptDialog = new OkCancelDialogPanel("printReceiptDialog", getString("yesLabel"), getString("noLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("printReceiptDialog onClose okSelected=" + isOk());
		if (isOk()){
		    logger.debug("product=" + printReceiptProduct);
		    thisPage.openLoadingModal(getString("printTransactionReceipt.loading.label"), target);
		    target.appendJavaScript("doWicketBehavior('wicketBehavior(\"printReceipt\")');");
		}
	    }

	};
	printReceiptDialog.setOutputMarkupPlaceholderTag(true);
	add(printReceiptDialog);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehavior id=" + id);
		if ("printReceipt".equals(id)){
		    printReceiptResource.setDrpUser(getDailyRhythmPortalSession().getDrpUser());
		    printReceiptResource.setProduct(printReceiptProduct);
		    String error = printReceiptResource.fetchData();
		    thisPage.closeLoadingModal(target);
		    if (error != null){
			purchaseHistoryPanel.getFeedbackPanel().error(error);
			target.add(purchaseHistoryPanel, purchaseHistoryPanel.getFeedbackPanel());
			return;
		    }
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
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    public void openPrintReceiptDialog(AjaxRequestTarget target, Product product) {
	printReceiptProduct = product;
	printReceiptDialog.setQuestion(getString("printTransactionReceipt.printReceipt.message.label"));
	printReceiptDialog.open(target);
    }
}
