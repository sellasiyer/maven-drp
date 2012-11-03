package com.bestbuy.bbym.ise.drp.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class PurchaseHistoryTabPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PurchaseHistoryTabPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private Date startDate, endDate;
    private PurchaseHistoryPanel.TabId tabId;
    private AbstractDefaultAjaxBehavior wicketBehaviorPHTP;
    private boolean showLoading = false;
    private boolean showPrintReceiptLink = true;

    public PurchaseHistoryTabPanel(String id, final FeedbackPanel feedbackPanel, final Form<Object> form,
	    final FeedbackMessageFilter filter, final PurchaseHistoryPanel.TabId tabId) {
	super(id);
	this.tabId = tabId;

	final String na = getString("notApplicable.label");
	final MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>();

	showPrintReceiptLink = WicketUtils.getBooleanProperty("printReceiptLink.enabled", true, this);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	if (tabId == PurchaseHistoryPanel.TabId.ALL && session.getPurchaseHistoryStartDate() == null){
	    Calendar monthAgo = Calendar.getInstance();
	    monthAgo.add(Calendar.DATE, -30);
	    if (session.getMobilePurchaseHistoryStartDate() != null){
		startDate = session.getMobilePurchaseHistoryStartDate();
	    }else{
		startDate = monthAgo.getTime();
	    }
	    session.setPurchaseHistoryStartDate(startDate);
	}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE && session.getMobilePurchaseHistoryStartDate() == null){
	    Calendar monthAgo = Calendar.getInstance();
	    monthAgo.add(Calendar.DATE, -30);
	    if (session.getPurchaseHistoryStartDate() != null){
		startDate = session.getPurchaseHistoryStartDate();
	    }else{
		startDate = monthAgo.getTime();
	    }
	    session.setMobilePurchaseHistoryStartDate(startDate);
	}else if (tabId == PurchaseHistoryPanel.TabId.ALL){
	    startDate = session.getPurchaseHistoryStartDate();
	}else{
	    startDate = session.getMobilePurchaseHistoryStartDate();
	}

	if (tabId == PurchaseHistoryPanel.TabId.ALL && session.getPurchaseHistoryEndDate() == null){
	    Calendar today = Calendar.getInstance();
	    if (session.getMobilePurchaseHistoryEndDate() != null){
		endDate = session.getMobilePurchaseHistoryEndDate();
	    }else{
		endDate = today.getTime();
	    }
	    session.setPurchaseHistoryEndDate(endDate);
	}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE && session.getMobilePurchaseHistoryEndDate() == null){
	    Calendar today = Calendar.getInstance();
	    if (session.getPurchaseHistoryEndDate() != null){
		endDate = session.getPurchaseHistoryEndDate();
	    }else{
		endDate = today.getTime();
	    }
	    session.setMobilePurchaseHistoryEndDate(endDate);
	}else if (tabId == PurchaseHistoryPanel.TabId.ALL){
	    endDate = session.getPurchaseHistoryEndDate();
	}else{
	    endDate = session.getMobilePurchaseHistoryEndDate();
	}

	if (tabId == PurchaseHistoryPanel.TabId.ALL && session.getAllPurchaseHistory() == null){
	    showLoading = true;
	}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE && session.getMobilePurchaseHistory() == null){
	    showLoading = true;
	}

	final DateTextField startDateFilter = new DateTextField("startDateFilter", new PropertyModel<Date>(this,
		"startDate"), "MM/dd/yy");
	startDateFilter.setOutputMarkupPlaceholderTag(true);
	startDateFilter.add(new DatePicker());
	startDateFilter.setRequired(true);
	add(startDateFilter);
	filter.addFilterInComponent(startDateFilter);

	final DateTextField endDateFilter = new DateTextField("endDateFilter",
		new PropertyModel<Date>(this, "endDate"), "MM/dd/yy");
	endDateFilter.setOutputMarkupPlaceholderTag(true);
	endDateFilter.add(new DatePicker());
	endDateFilter.setRequired(true);
	add(endDateFilter);
	filter.addFilterInComponent(endDateFilter);

	final List<IColumn<Product>> purchaseHistoryColumns = new ArrayList<IColumn<Product>>();
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, String>(new ResourceModel(
		"purchaseHistoryTable.transactionType.column"), "transactionType", "transactionType", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});
	purchaseHistoryColumns.add(new AbstractColumn<Product>(new Model<String>(
		getString("purchaseHistoryTable.fourPartTransKey.column")), "transactionId") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Product row = (Product) rowModel.getObject();
		cellItem.add(new PurchaseHistoryTabPanel.FourPartKeyPanel(componentId, rowModel, row, na));
	    }
	});
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, String>(new ResourceModel(
		"purchaseHistoryTable.sku.column"), "sku", "sku", null, na));
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, String>(new ResourceModel(
		"purchaseHistoryTable.description.column"), "description", "description", null, na));
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, String>(new ResourceModel(
		"purchaseHistoryTable.serialNumber.column"), "serialNumber", "serialNumber", null,
		getString("noSerialNumber.label")));
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, BigDecimal>(new ResourceModel(
		"purchaseHistoryTable.retailPrice.column"), "retailPrice", "retailPrice", moneyFmt, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, BigDecimal>(new ResourceModel(
		"purchaseHistoryTable.purchasePrice.column"), "purchasePrice", "purchasePrice", moneyFmt, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});

	final PurchaseHistoryDataProvider purchasesDataProvider = new PurchaseHistoryDataProvider();
	if (tabId == PurchaseHistoryPanel.TabId.ALL){
	    purchasesDataProvider.setPurchaseHistoryList(session.getAllPurchaseHistory());
	}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
	    purchasesDataProvider.setPurchaseHistoryList(session.getMobilePurchaseHistory());
	}

	final AjaxFallbackDefaultDataTable<Product> purchasesTable = new AjaxFallbackDefaultDataTable<Product>(
		"purchasesTable", purchaseHistoryColumns, purchasesDataProvider, 200) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (tabId == PurchaseHistoryPanel.TabId.ALL){
		    return session.getAllPurchaseHistory() != null && !session.getAllPurchaseHistory().isEmpty();
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		    return session.getMobilePurchaseHistory() != null && !session.getMobilePurchaseHistory().isEmpty();
		}
		return false;
	    }
	};
	purchasesTable.setMarkupId("purchasesTable");
	purchasesTable.setOutputMarkupId(true);
	purchasesTable.setOutputMarkupPlaceholderTag(true);
	add(purchasesTable);

	final WebMarkupContainer noPurchasesData = new WebMarkupContainer("noPurchasesData") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (tabId == PurchaseHistoryPanel.TabId.ALL){
		    return session.getAllPurchaseHistory() == null || session.getAllPurchaseHistory().isEmpty();
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		    return session.getMobilePurchaseHistory() == null || session.getMobilePurchaseHistory().isEmpty();
		}
		return false;
	    }
	};
	noPurchasesData.setOutputMarkupPlaceholderTag(true);
	add(noPurchasesData);

	Label noPurchasesDataLabel = new Label("noPurchasesDataLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (tabId == PurchaseHistoryPanel.TabId.ALL && session.getAllPurchaseHistory() == null){
		    return getString("allPurchasesTable.dataError.label");
		}else if (tabId == PurchaseHistoryPanel.TabId.ALL){
		    return getString("allPurchasesTable.noData.label");
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE && session.getMobilePurchaseHistory() == null){
		    return getString("mobilePurchasesTable.dataError.label");
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		    return getString("mobilePurchasesTable.noData.label");
		}
		return "";
	    }
	});
	noPurchasesDataLabel.setOutputMarkupPlaceholderTag(true);
	noPurchasesData.add(noPurchasesDataLabel);

	final WebMarkupContainer purchasesDataLoading = new WebMarkupContainer("purchasesDataLoading") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return showLoading;
	    }
	};
	purchasesDataLoading.setOutputMarkupPlaceholderTag(true);
	add(purchasesDataLoading);

	Label purchasesDataLoadingLabel = new Label("purchasesDataLoadingLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (tabId == PurchaseHistoryPanel.TabId.ALL){
		    return getString("allPurchasesTable.loading.label");
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		    return getString("mobilePurchasesTable.loading.label");
		}
		return "";
	    }
	});
	purchasesDataLoadingLabel.setOutputMarkupPlaceholderTag(true);
	purchasesDataLoading.add(purchasesDataLoadingLabel);

	final AjaxButton searchPurchasesButton = new AjaxButton("searchPurchasesButton", new ResourceModel(
		"purchaseHistoryForm.searchPurchases.button"), form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("searchPurchasesButton onSubmit tabId=" + tabId);
		showLoading = true;
		target.add(purchasesTable);
		target.add(noPurchasesData);
		target.add(purchasesDataLoading);
		target.add(this);
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (tabId == PurchaseHistoryPanel.TabId.ALL){
		    session.setPurchaseHistoryStartDate(startDate);
		    session.setPurchaseHistoryEndDate(endDate);
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		    session.setMobilePurchaseHistoryStartDate(startDate);
		    session.setMobilePurchaseHistoryEndDate(endDate);
		}
		target.appendJavaScript("doWicketBehavior('wicketBehaviorPHTP(\"load\")');");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("searchPurchasesButton onError tabId=" + tabId);
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (tabId == PurchaseHistoryPanel.TabId.ALL){
		    session.setAllPurchaseHistory(null);
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		    session.setMobilePurchaseHistory(null);
		}
		purchasesDataProvider.setPurchaseHistoryList(null);
		purchasesTable.modelChanged();
		target.add(purchasesTable);
		target.add(noPurchasesData);
		target.add(purchasesDataLoading);
		target.add(this);
	    }

	    @Override
	    public boolean isEnabled() {
		return !showLoading;
	    }
	};
	searchPurchasesButton.setOutputMarkupPlaceholderTag(true);
	add(searchPurchasesButton);

	// means by which JS can callback into Java
	wicketBehaviorPHTP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorPHTP id=" + id);
		if ("load".equals(id)){
		    String error = loadData();
		    if (error != null){
			form.error(error);
			target.add(feedbackPanel);
		    }
		    showLoading = false;
		    if (tabId == PurchaseHistoryPanel.TabId.ALL){
			purchasesDataProvider.setPurchaseHistoryList(session.getAllPurchaseHistory());
		    }else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
			purchasesDataProvider.setPurchaseHistoryList(session.getMobilePurchaseHistory());
		    }
		    purchasesTable.modelChanged();
		    target.add(purchasesTable);
		    target.add(noPurchasesData);
		    target.add(purchasesDataLoading);
		    target.add(searchPurchasesButton);
		    // target.add(fourPartKeyWindow);
		    if (tabId == PurchaseHistoryPanel.TabId.ALL && session.getAllPurchaseHistory() != null
			    && !session.getAllPurchaseHistory().isEmpty()){
			target.appendJavaScript("setupPurchaseHistoryTable();");
		    }else if (tabId == PurchaseHistoryPanel.TabId.MOBILE && session.getMobilePurchaseHistory() != null
			    && !session.getMobilePurchaseHistory().isEmpty()){
			target.appendJavaScript("setupPurchaseHistoryTable();");
		    }
		}
	    }
	};
	add(wicketBehaviorPHTP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (tabId == PurchaseHistoryPanel.TabId.ALL && session.getAllPurchaseHistory() != null
			&& !session.getAllPurchaseHistory().isEmpty()){
		    onDomReadyJS.append("setupPurchaseHistoryTable();");
		}else if (tabId == PurchaseHistoryPanel.TabId.MOBILE && session.getMobilePurchaseHistory() != null
			&& !session.getMobilePurchaseHistory().isEmpty()){
		    onDomReadyJS.append("setupPurchaseHistoryTable();");
		}
		onDomReadyJS.append("wicketBehaviorPHTP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorPHTP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		if (showLoading){
		    onDomReadyJS.append("doWicketBehavior('wicketBehaviorPHTP(\"load\")');");
		}
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    class FourPartKeyPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public FourPartKeyPanel(String id, final IModel<?> model, final Product row, final String notAvailableLabel) {
	    super(id, model);

	    final String fourPartKeyValue = WicketUtils.generateFourPartKeyHTML(row, notAvailableLabel);
	    final boolean productIsInvalid = fourPartKeyValue.equals(notAvailableLabel);

	    final Label fourPartKey = new Label("fourPartKey", fourPartKeyValue);
	    fourPartKey.setEscapeModelStrings(false);
	    add(fourPartKey);

	    final AjaxLink<Object> printReceiptLink = new AjaxLink<Object>("printReceiptLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.info("printReceiptLink onClick");
		    CustomerDashboardPage basePage = (CustomerDashboardPage) getPage();
		    basePage.openPrintReceiptDialog(target, row);
		}

		@Override
		public boolean isVisible() {
		    if (productIsInvalid){
			return false;
		    }
		    return showPrintReceiptLink;
		}
	    };
	    printReceiptLink.setOutputMarkupPlaceholderTag(true);
	    add(printReceiptLink);
	}
    }

    // returns null if successful or returns error string
    private String loadData() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	try{
	    if (tabId == PurchaseHistoryPanel.TabId.ALL){
		logger.debug("all purchases search");
		logger.debug("start date=" + Util.toString(startDate, null));
		logger.debug("end date=" + Util.toString(endDate, null));
		session.setAllPurchaseHistory(customerDataService.getAllPurchaseHistory(session.getBestBuyCustomer(),
			session.getDrpUser(), startDate, endDate));
		if (session.getAllPurchaseHistory() == null){
		    session.setAllPurchaseHistory(new ArrayList<Product>());
		}
		logger.debug("# all purchase items=" + session.getAllPurchaseHistory().size());
	    }else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		logger.debug("mobile purchases search");
		logger.debug("start date=" + Util.toString(startDate, null));
		logger.debug("end date=" + Util.toString(endDate, null));
		session.setMobilePurchaseHistory(customerDataService.getMobilePurchaseHistory(session
			.getBestBuyCustomer(), session.getDrpUser(), startDate, endDate));
		if (session.getMobilePurchaseHistory() == null){
		    session.setMobilePurchaseHistory(new ArrayList<Product>());
		}
		logger.debug("# mobile purchase items=" + session.getMobilePurchaseHistory().size());
	    }
	    return null;
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException is " + be.getFullMessage());
	    if (tabId == PurchaseHistoryPanel.TabId.ALL){
		session.setAllPurchaseHistory(null);
	    }else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		session.setMobilePurchaseHistory(null);
	    }
	    return be.getFullMessage();
	}catch(ServiceException se){
	    logger.error("ServiceException is " + se.getFullMessage());
	    if (tabId == PurchaseHistoryPanel.TabId.ALL){
		session.setAllPurchaseHistory(null);
	    }else if (tabId == PurchaseHistoryPanel.TabId.MOBILE){
		session.setMobilePurchaseHistory(null);
	    }
	    return se.getFullMessage();
	}
    }

}
