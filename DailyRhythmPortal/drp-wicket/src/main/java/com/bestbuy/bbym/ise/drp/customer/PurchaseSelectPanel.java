package com.bestbuy.bbym.ise.drp.customer;

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
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
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
import com.bestbuy.bbym.ise.drp.dashboard.PurchaseHistoryDataProvider;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.drp.util.panel.SpanPanel;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class PurchaseSelectPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PurchaseSelectPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private CustInfoModalPanel parentModalPanel;
    private boolean showLoading = true;

    private PurchaseHistoryDataProvider purchasesDataProvider = new PurchaseHistoryDataProvider();
    private Date startDate, endDate;

    private final String na = getString("notApplicable.label");

    public PurchaseSelectPanel(final String id, final CustInfoModalPanel parentModalPanel) {
	super(id);
	this.parentModalPanel = parentModalPanel;

	changeModalState(parentModalPanel.getModalState());

	final Form<Object> purchaseHistoryForm = new Form<Object>("purchaseHistoryForm");
	purchaseHistoryForm.setOutputMarkupPlaceholderTag(true);
	add(purchaseHistoryForm);

	final DateTextField startDateFilter = new DateTextField("startDateFilter", new PropertyModel<Date>(this,
		"startDate"), "MM/dd/yy");
	startDateFilter.setOutputMarkupPlaceholderTag(true);
	startDateFilter.add(new DatePicker());
	startDateFilter.setRequired(true);
	purchaseHistoryForm.add(startDateFilter);

	final DateTextField endDateFilter = new DateTextField("endDateFilter",
		new PropertyModel<Date>(this, "endDate"), "MM/dd/yy");
	endDateFilter.setOutputMarkupPlaceholderTag(true);
	endDateFilter.add(new DatePicker());
	endDateFilter.setRequired(true);
	purchaseHistoryForm.add(endDateFilter);

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
	purchaseHistoryColumns.add(new AbstractColumn<Product>(new ResourceModel(
		"purchaseHistoryTable.fourPartTransKey.column"), "transactionId") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Product>> cellItem, String componentId,
		    IModel<Product> rowModel) {
		final Product row = (Product) rowModel.getObject();
		cellItem.add(new SpanPanel<Product>(componentId, rowModel) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String generateLabelString() {
			return WicketUtils.generateFourPartKeyHTML(row, na);
		    }
		});
	    }

	});
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, String>(new ResourceModel(
		"purchaseHistoryTable.sku.column"), "sku", "sku", null, na));
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, String>(new ResourceModel(
		"purchaseHistoryTable.description.column"), "description", "description", null, na));
	purchaseHistoryColumns.add(new FormatPropertyColumn<Product, String>(new ResourceModel(
		"purchaseHistoryTable.serialNumber.column"), "serialNumber", "serialNumber", null,
		getString("noSerialNumber.label")));
	purchaseHistoryColumns.add(new AbstractColumn<Product>(null, "serialNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Product row = (Product) rowModel.getObject();
		cellItem.add(new PurchaseSelectPanel.SelectLinkPanel(componentId, rowModel, row,
			getString("custInfo.selectLink.label")));
	    }
	});

	final AjaxFallbackDefaultDataTable<Product> purchasesTable = new AjaxFallbackDefaultDataTable<Product>(
		"purchasesTable", purchaseHistoryColumns, purchasesDataProvider, 200) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		return purchasesDataProvider.size() > 0;
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
		return purchasesDataProvider.size() == 0;
	    }
	};
	noPurchasesData.setOutputMarkupPlaceholderTag(true);
	add(noPurchasesData);

	Label noPurchasesDataLabel = new Label("noPurchasesDataLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (parentModalPanel.getFeedbackPanel().anyErrorMessage()){
		    return getString("custInfo.purchasesTable.dataError.label");
		}
		return getString("custInfo.purchasesTable.noData.label");
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

	final AjaxButton searchPurchasesButton = new AjaxButton("searchPurchasesButton", new ResourceModel(
		"purchaseHistoryForm.searchPurchases.button"), purchaseHistoryForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("searchPurchasesButton onSubmit");
		showLoading = true;
		target.add(purchasesTable);
		target.add(noPurchasesData);
		target.add(purchasesDataLoading);
		target.add(this);
		parentModalPanel.refresh(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("searchPurchasesButton onError");
		purchasesDataProvider.setPurchaseHistoryList(null);
		purchasesTable.modelChanged();
		target.add(parentModalPanel.getFeedbackPanel());
		target.add(purchasesTable);
		target.add(noPurchasesData);
		target.add(purchasesDataLoading);
		target.add(this);
		parentModalPanel.refresh(target);
	    }

	    @Override
	    public boolean isEnabled() {
		return !showLoading;
	    }
	};
	searchPurchasesButton.setOutputMarkupPlaceholderTag(true);
	purchaseHistoryForm.add(searchPurchasesButton);

	// means by which JS can callback into Java
	final AbstractDefaultAjaxBehavior wicketBehaviorPSP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorPSP id=" + id);
		if ("load".equals(id)){
		    String error = loadData();
		    if (error != null){
			parentModalPanel.getFeedbackPanel().error(error);
			target.add(parentModalPanel.getFeedbackPanel());
		    }
		    showLoading = false;
		    purchasesTable.modelChanged();
		    target.add(purchasesTable);
		    target.add(noPurchasesData);
		    target.add(purchasesDataLoading);
		    parentModalPanel.refresh(target);
		}
	    }
	};
	add(wicketBehaviorPSP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehaviorPSP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorPSP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    class SelectLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectLinkPanel(String id, final IModel<?> model, final Product row, final String label) {
	    super(id, model);

	    AjaxLink<Object> link = new AjaxLink<Object>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("selectLinkPanel onClick");
		    parentModalPanel.setSelectedProduct((Product) Util.clone(row));
		    parentModalPanel.close(target);
		}
	    };
	    link.setOutputMarkupPlaceholderTag(true);
	    link.add(new Label("label", label));
	    add(link);
	}
    }

    // returns null if successful or returns error string
    private String loadData() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	try{
	    if (parentModalPanel.getModalState() == CustInfoModalPanel.ModalState.SELECT_ANY_PURCHASE){
		logger.debug("all purchases search");
		logger.debug("start date=" + Util.toString(startDate, null));
		logger.debug("end date=" + Util.toString(endDate, null));
		purchasesDataProvider.setPurchaseHistoryList(customerDataService.getAllPurchaseHistory(parentModalPanel
			.getSelectedCustomer(), session.getDrpUser(), startDate, endDate));
		logger.debug("# all purchase items=" + purchasesDataProvider.size());
	    }else if (parentModalPanel.getModalState() == CustInfoModalPanel.ModalState.SELECT_MOBILE_PURCHASE){
		logger.debug("mobile purchases search");
		logger.debug("start date=" + Util.toString(startDate, null));
		logger.debug("end date=" + Util.toString(endDate, null));
		purchasesDataProvider.setPurchaseHistoryList(customerDataService.getMobilePurchaseHistory(
			parentModalPanel.getSelectedCustomer(), session.getDrpUser(), startDate, endDate));
		logger.debug("# mobile purchase items=" + purchasesDataProvider.size());
	    }
	    return null;
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException is " + be.getFullMessage());
	    purchasesDataProvider.setPurchaseHistoryList(null);
	    return be.getFullMessage();
	}catch(ServiceException se){
	    logger.error("ServiceException is " + se.getFullMessage());
	    purchasesDataProvider.setPurchaseHistoryList(null);
	    return se.getFullMessage();
	}
    }

    public String getOpenPanelJS() {
	if (!isVisible()){
	    return "";
	}
	StringBuilder openPanelJS = new StringBuilder();
	if (showLoading){
	    openPanelJS.append("doWicketBehavior('wicketBehaviorPSP(\"load\")');");
	}else if (purchasesDataProvider.size() > 0){
	    openPanelJS.append("tablePrep(custInfoModalPurchaseSelectTable);");
	}
	return openPanelJS.toString();
    }

    public String getRefreshPanelJS() {
	return getOpenPanelJS();
    }

    @Override
    public boolean isVisible() {
	return (parentModalPanel.getModalState() == CustInfoModalPanel.ModalState.SELECT_ANY_PURCHASE || parentModalPanel
		.getModalState() == CustInfoModalPanel.ModalState.SELECT_MOBILE_PURCHASE)
		&& parentModalPanel.getSelectedCustomer().getId() != null;
    }

    public void reloadOnRefresh() {
	purchasesDataProvider.setPurchaseHistoryList(null);
	showLoading = true;
    }

    public void changeModalState(CustInfoModalPanel.ModalState modalState) {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (modalState == CustInfoModalPanel.ModalState.SELECT_ANY_PURCHASE){
	    if (session.getPurchaseHistoryStartDate() == null){
		Calendar monthAgo = Calendar.getInstance();
		monthAgo.add(Calendar.DATE, -30);
		startDate = monthAgo.getTime();
	    }else{
		startDate = new Date(session.getPurchaseHistoryStartDate().getTime());
	    }
	}else if (modalState == CustInfoModalPanel.ModalState.SELECT_MOBILE_PURCHASE){
	    if (session.getMobilePurchaseHistoryStartDate() == null){
		Calendar monthAgo = Calendar.getInstance();
		monthAgo.add(Calendar.DATE, -30);
		startDate = monthAgo.getTime();
	    }else{
		startDate = new Date(session.getMobilePurchaseHistoryStartDate().getTime());
	    }
	}

	if (modalState == CustInfoModalPanel.ModalState.SELECT_ANY_PURCHASE){
	    if (session.getPurchaseHistoryEndDate() == null){
		Calendar today = Calendar.getInstance();
		endDate = today.getTime();
	    }else{
		endDate = new Date(session.getPurchaseHistoryEndDate().getTime());
	    }
	}else if (modalState == CustInfoModalPanel.ModalState.SELECT_MOBILE_PURCHASE){
	    if (session.getMobilePurchaseHistoryEndDate() == null){
		Calendar today = Calendar.getInstance();
		endDate = today.getTime();
	    }else{
		endDate = new Date(session.getMobilePurchaseHistoryEndDate().getTime());
	    }
	}
    }
}
