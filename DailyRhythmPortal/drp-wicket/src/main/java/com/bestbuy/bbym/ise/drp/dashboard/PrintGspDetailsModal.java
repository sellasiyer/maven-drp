package com.bestbuy.bbym.ise.drp.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.BuybackPlan;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.StaticFormatters;
import com.bestbuy.bbym.ise.drp.util.StringMapFormatter;

public class PrintGspDetailsModal extends BaseWebPage {

    private static final long serialVersionUID = 1L;

    private List<Product> productList;
    private List<ServicePlanTransaction> servicePlanTransList;
    private static Logger logger = LoggerFactory.getLogger(PrintGspDetailsModal.class);

    public PrintGspDetailsModal(PageParameters parameters) {

	logger.debug("in the print gsp details page...");

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

	final ListView<ServicePlanTransaction> transactionListView = new ListView<ServicePlanTransaction>(
		"transactionListView", new PropertyModel<List<ServicePlanTransaction>>(this, "servicePlanTransactions")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(final ListItem<ServicePlanTransaction> listItem) {
		final ServicePlanTransaction transaction = listItem.getModelObject();
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
			new PropertyModel<Product>(product, "serialNumber"), null, na)));
	    }
	};
	productListView.setOutputMarkupPlaceholderTag(true);
	add(productListView);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		response.renderOnDomReadyJavaScript("window.print();");
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

}
