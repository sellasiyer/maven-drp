package com.bestbuy.bbym.ise.drp.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.BuybackPlan;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.util.CreditCardUrlFormatter;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class GspTabPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(GspTabPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private FeedbackPanel feedbackPanel;
    private Form<Object> form;
    private AbstractDefaultAjaxBehavior wicketBehaviorGTP;
    private boolean showLoading = false;
    private GspDetailsModalPanel gspDetailsModalPanel;
    private MapFormatter<String> statusFmt = GspStatus.generateMapFormatter(this);
    private AjaxFallbackDefaultDataTable<ServicePlan> gspTable;

    public GspTabPanel(String id, final FeedbackPanel feedbackPanel, final Form<Object> form,
	    final FeedbackMessageFilter filter) {
	super(id);
	this.feedbackPanel = feedbackPanel;
	this.form = form;

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	final String na = getString("notApplicable.label");
	final MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>();
	final DateFormatter<Date> dateFmt = new DateFormatter<Date>("MM/dd/yy");

	if (session.getPageError() != null && session.getPageError().doesApply(GspTabPanel.class)){
	    form.error(session.getPageError().getMessage());
	    session.setPageError(null);
	}else if (session.getServicePlanList() == null){
	    showLoading = true;
	}

	final List<IColumn<ServicePlan>> gspColumns = new ArrayList<IColumn<ServicePlan>>();
	gspColumns.add(new FormatPropertyColumn<ServicePlan, String>(new ResourceModel("gspTable.planType.column"),
		"planType", "planType", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});
	gspColumns.add(new AbstractColumn<ServicePlan>(new ResourceModel("gspTable.planStatus.column"), "planStatus") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ServicePlan row = (ServicePlan) rowModel.getObject();
		cellItem.add(new GspTabPanel.GspUpdateCreditCardPanel(componentId, rowModel, row, na));
	    }
	});
	gspColumns.add(new AbstractColumn<ServicePlan>(new ResourceModel("gspTable.planDescriptionNumber.column"),
		"planNumber") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ServicePlan row = (ServicePlan) rowModel.getObject();
		cellItem.add(new GspTabPanel.GspPlanDescriptionNumberPanel(componentId, rowModel, row, na));
	    }
	});
	gspColumns.add(new FormatPropertyColumn<ServicePlan, Date>(new ResourceModel("gspTable.planExpiration.column"),
		"planExpiryDate", "planExpiryDate", dateFmt, na));
	gspColumns.add(new AbstractColumn<ServicePlan>(new ResourceModel("gspTable.planPrice.column"), "planNumber") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ServicePlan row = (ServicePlan) rowModel.getObject();
		GspTabPanel.GspPlanPricePanel planPricePanel = new GspTabPanel.GspPlanPricePanel(componentId, rowModel,
			row, getString("gspTable.clickLink.label"), na, moneyFmt,
			getString("gspTable.cantGetBuyBackPrice.label"));
		cellItem.add(planPricePanel);
	    }
	});
	gspColumns.add(new AbstractColumn<ServicePlan>(new ResourceModel("gspTable.deviceDescription.column"),
		"planNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ServicePlan row = (ServicePlan) rowModel.getObject();
		cellItem.add(new GspTabPanel.GspDeviceDescriptionPanel(componentId, rowModel, row, na));
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});
	gspColumns.add(new AbstractColumn<ServicePlan>(null, "planNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ServicePlan row = (ServicePlan) rowModel.getObject();
		cellItem.add(new GspTabPanel.GspDetailsLinkPanel(componentId, rowModel, row,
			getString("gspTable.detailsLink.label")));
	    }
	});

	final GspDataProvider gspDataProvider = new GspDataProvider();
	gspDataProvider.setServicePlanList(session.getServicePlanList());

	gspTable = new AjaxFallbackDefaultDataTable<ServicePlan>("gspTable", gspColumns, gspDataProvider, 200) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		return session.getServicePlanList() != null && !session.getServicePlanList().isEmpty();
	    }
	};
	gspTable.setMarkupId("gspTable");
	gspTable.setOutputMarkupId(true);
	gspTable.setOutputMarkupPlaceholderTag(true);
	add(gspTable);

	final WebMarkupContainer noGspData = new WebMarkupContainer("noGspData") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		return session.getServicePlanList() == null || session.getServicePlanList().isEmpty();
	    }
	};
	noGspData.setOutputMarkupPlaceholderTag(true);
	add(noGspData);

	Label noGspDataLabel = new Label("noGspDataLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		if (session.getServicePlanList() == null){
		    return getString("gspTable.dataError.label");
		}
		return getString("gspTable.noData.label");
	    }
	});
	noGspDataLabel.setOutputMarkupPlaceholderTag(true);
	noGspData.add(noGspDataLabel);

	final WebMarkupContainer gspDataLoading = new WebMarkupContainer("gspDataLoading") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return showLoading;
	    }
	};
	gspDataLoading.setOutputMarkupPlaceholderTag(true);
	add(gspDataLoading);

	session.setPlanDetails(new ProtectionPlan());
	gspDetailsModalPanel = new GspDetailsModalPanel("gspDetailsModalPanel");
	gspDetailsModalPanel.setOutputMarkupPlaceholderTag(true);
	add(gspDetailsModalPanel);

	// means by which JS can callback into Java
	wicketBehaviorGTP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorGTP id=" + id);
		if ("load".equals(id)){
		    String error = loadData();
		    if (error != null){
			form.error(error);
			target.add(feedbackPanel);
		    }
		    showLoading = false;
		    gspTable.modelChanged();
		    gspDataProvider.setServicePlanList(session.getServicePlanList());
		    target.add(gspTable);
		    target.add(noGspData);
		    target.add(gspDataLoading);
		    if (session.getServicePlanList() != null && !session.getServicePlanList().isEmpty()){
			target.appendJavaScript("setupGspTable();");
		    }
		}
	    }
	};
	add(wicketBehaviorGTP);

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
		if (session.getServicePlanList() != null && !session.getServicePlanList().isEmpty()){
		    onDomReadyJS.append("setupGspTable();");
		}
		onDomReadyJS.append("wicketBehaviorGTP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorGTP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		if (showLoading){
		    onDomReadyJS.append("doWicketBehavior('wicketBehaviorGTP(\"load\")');");
		}
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    class GspDetailsLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public GspDetailsLinkPanel(String id, final IModel<?> model, final ServicePlan row, final String label) {
	    super(id, model);

	    setOutputMarkupPlaceholderTag(true);

	    AjaxLink<Void> link = new AjaxLink<Void>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    try{
			session.setPlanDetails(customerDataService.getServicePlanDetails(row));
			target.appendJavaScript("clearGspCellLoading();setupGspTable();");
			target.add(gspTable);
			logger.trace("PlanDetails=" + session.getPlanDetails());
			if (session.getPlanDetails() != null
				&& session.getPlanDetails().getServicePlanTransactions() != null){
			    logger.debug("# of transactions="
				    + session.getPlanDetails().getServicePlanTransactions().size());
			}
		    }catch(ServiceException se){
			target.appendJavaScript("clearGspCellLoading();setupGspTable();");
			target.add(gspTable);
			form.error(se.getFullMessage());
			target.add(feedbackPanel);
			return;
		    }catch(IseBusinessException be){
			target.appendJavaScript("clearGspCellLoading();setupGspTable();");
			target.add(gspTable);
			form.error(be.getFullMessage());
			target.add(feedbackPanel);
			return;
		    }
		    gspDetailsModalPanel.refresh();
		    logger.debug("gspDetailsModalPanel open");
		    gspDetailsModalPanel.open(target);
		}
	    };
	    link.setOutputMarkupPlaceholderTag(true);
	    link.add(new Label("label", label));
	    add(link);
	}
    }

    class GspUpdateCreditCardPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public GspUpdateCreditCardPanel(String id, final IModel<?> model, final ServicePlan row,
		final String notAvailableLabel) {
	    super(id, model);

	    boolean showAsLink = (row.getPlanType() != null)
		    && !row.getPlanType().equalsIgnoreCase(ServicePlan.PLAN_TYPE_BUY_BACK)
		    && (row.getPlanStatus() != null)
		    && !(row.getPlanStatus().equalsIgnoreCase(ServicePlan.PLAN_STATUS_INACTIVE) || row.getPlanStatus()
			    .equalsIgnoreCase(ServicePlan.PLAN_STATUS_CANCELLED));

	    // No Link
	    if (!showAsLink){
		Link<Void> link = new Link<Void>("updateCCLink") {

		    private static final long serialVersionUID = 1L;

		    public void onClick() {
		    };
		};
		link.setVisible(false);
		add(link);
		Label statusLabel = new Label("statusLabel");
		link.add(statusLabel);
		link.setVisible(false);

		Label noLinkStatusLabel = new Label("noLinkStatusLabel", determineStatusLabel(notAvailableLabel, row));
		noLinkStatusLabel.setEscapeModelStrings(false);
		add(noLinkStatusLabel);

		// Has Link
	    }else{
		Label statusLabel = new Label("statusLabel", determineStatusLabel(notAvailableLabel, row)
			+ "<span class=\"textalert\">***</span>");
		statusLabel.setEscapeModelStrings(false);

		AjaxLink<Void> link = new AjaxLink<Void>("updateCCLink") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onClick(AjaxRequestTarget target) {
			DailyRhythmPortalSession session = getDailyRhythmPortalSession();
			String firstName = null;
			String lastName = null;
			if (session.getBestBuyCustomer() != null){
			    firstName = session.getBestBuyCustomer().getFirstName();
			    lastName = session.getBestBuyCustomer().getLastName();
			}
			String psp = row.getPlanNumber();
			final String url = CreditCardUrlFormatter.formatURL(firstName, lastName, psp);
			logger.debug("url=" + url);
			target.appendJavaScript("window.open('" + url + "')");
		    }
		};
		link.add(statusLabel);
		add(link);

		Label noLinkStatusLabel = new Label("noLinkStatusLabel");
		add(noLinkStatusLabel);
		noLinkStatusLabel.setVisible(false);
	    }
	}
    }

    class GspPlanDescriptionNumberPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public GspPlanDescriptionNumberPanel(String id, final IModel<?> model, final ServicePlan row,
		final String notAvailableLabel) {
	    super(id, model);

	    StringBuilder sb = new StringBuilder();
	    if (row == null || (row.getDescription() == null && row.getPlanNumber() == null)){
		sb.append(notAvailableLabel);
	    }else if (row.getDescription() != null && row.getPlanNumber() != null){
		sb.append(row.getDescription());
		sb.append("<br/>");
		sb.append(row.getPlanNumber());
	    }else if (row.getDescription() == null){
		sb.append(notAvailableLabel);
		sb.append("<br/>");
		sb.append(row.getPlanNumber());
	    }else if (row.getPlanNumber() == null){
		sb.append(row.getDescription());
		sb.append("<br/>");
		sb.append(notAvailableLabel);
	    }
	    Label planDescriptionNumber = new Label("planDescriptionNumber", sb.toString());
	    planDescriptionNumber.setEscapeModelStrings(false);
	    add(planDescriptionNumber);
	}
    }

    class GspPlanPricePanel extends Panel {

	private static final long serialVersionUID = 1L;

	public GspPlanPricePanel(String id, final IModel<?> model, final ServicePlan row, final String clickLabel,
		final String notAvailableLabel, final MoneyFormatter<BigDecimal> moneyFmt,
		final String failedGetBuyBackPriceLabel) {
	    super(id, model);

	    final Label priceLabel = new Label("priceLabel", new Model<String>() {

		private static final long serialVersionUID = 1L;

		@Override
		public String getObject() {
		    // BuybackPlan
		    if (row instanceof BuybackPlan){
			BuybackPlan bbp = (BuybackPlan) row;
			return moneyFmt.format(bbp.getBuyBackPrice());
			// ProtectionPlan
		    }else{
			return notAvailableLabel;
		    }
		}
	    }) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    if (row instanceof BuybackPlan){
			BuybackPlan bbp = (BuybackPlan) row;
			if (bbp.getBuyBackPrice() != null && bbp.getBuyBackPrice().doubleValue() >= 0.0){
			    return true;
			}else{
			    return false;
			}
		    }else{
			return true;
		    }
		}
	    };
	    priceLabel.setOutputMarkupPlaceholderTag(true);
	    priceLabel.setEscapeModelStrings(false);
	    add(priceLabel);

	    final Label cantGetBuyBackPriceLabel = new Label("cantGetBuyBackPriceLabel", new Model<String>() {

		private static final long serialVersionUID = 1L;

		@Override
		public String getObject() {
		    // BuybackPlan
		    if (row instanceof BuybackPlan){
			return failedGetBuyBackPriceLabel;
			// ProtectionPlan
		    }else{
			return notAvailableLabel;
		    }
		}
	    }) {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    // BuybackPlan
		    if (row instanceof BuybackPlan){
			BuybackPlan bbp = (BuybackPlan) row;
			if (bbp.getBuyBackPrice() != null && bbp.getBuyBackPrice().doubleValue() < 0.0){
			    return true;
			}else{
			    return false;
			}
			// ProtectionPlan
		    }else{
			return false;
		    }
		}
	    };
	    cantGetBuyBackPriceLabel.setOutputMarkupPlaceholderTag(true);
	    cantGetBuyBackPriceLabel.setEscapeModelStrings(false);
	    add(cantGetBuyBackPriceLabel);

	    AjaxLink<Void> clickLink = new AjaxLink<Void>("clickLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    BuybackPlan bbp = (BuybackPlan) row;
		    try{
			logger.debug("buy back purchase date is "
				+ Util.toString(bbp.getProduct().getPurchaseDate(), null));
			bbp.setBuyBackPrice(customerDataService.getBuyBackValue(bbp, bbp.getProduct().getSku(), bbp
				.getProduct().getPurchaseDate(), session.getDrpUser()));
			logger.debug("buy back purchase price is " + bbp.getBuyBackPrice());
			if (bbp.getBuyBackPrice() == null){
			    bbp.setBuyBackPrice(new BigDecimal(-1));
			}
		    }catch(ServiceException se){
			logger.warn("onClick clickLink failed to get buy back purchase price", se);
			bbp.setBuyBackPrice(new BigDecimal(-1));
		    }catch(IseBusinessException be){
			logger.warn("onClick clickLink failed to get buy back purchase price");
			bbp.setBuyBackPrice(new BigDecimal(-1));
		    }
		    target.add(priceLabel);
		    target.add(cantGetBuyBackPriceLabel);
		    target.add(this);
		    target.appendJavaScript("clearGspCellLoading();");
		}

		@Override
		public boolean isVisible() {
		    if (row instanceof BuybackPlan){
			BuybackPlan bbp = (BuybackPlan) row;
			if (bbp.getBuyBackPrice() == null){
			    return true;
			}else{
			    return false;
			}
		    }else{
			return false;
		    }
		}
	    };
	    clickLink.setOutputMarkupPlaceholderTag(true);
	    clickLink.add((new Label("clickLabel", clickLabel)).setEscapeModelStrings(false));
	    add(clickLink);
	}
    }

    class GspDeviceDescriptionPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public GspDeviceDescriptionPanel(String id, final IModel<?> model, final ServicePlan row,
		final String notAvailableLabel) {
	    super(id, model);

	    StringBuilder sb = new StringBuilder();
	    if (row == null){
		sb.append(notAvailableLabel);

	    }else if (row instanceof ProtectionPlan){
		ProtectionPlan prPlan = (ProtectionPlan) row;
		if (prPlan.getCoveredProductList() == null || prPlan.getCoveredProductList().isEmpty()){
		    sb.append(notAvailableLabel);
		}else{
		    int size = prPlan.getCoveredProductList().size(), i = 0;
		    for(Product p: prPlan.getCoveredProductList()){
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

	    }else if (row instanceof BuybackPlan){
		BuybackPlan bbPlan = (BuybackPlan) row;
		if (bbPlan.getProduct() == null || bbPlan.getProduct().getDescription() == null){
		    sb.append(notAvailableLabel);
		}else{
		    sb.append(bbPlan.getProduct().getDescription());
		}
	    }
	    Label deviceDescriptionList = new Label("deviceDescriptionList", sb.toString());
	    deviceDescriptionList.setEscapeModelStrings(false);
	    add(deviceDescriptionList);
	}
    }

    class GspDeviceSerialNumberPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public GspDeviceSerialNumberPanel(String id, final IModel<?> model, final ServicePlan row,
		final String notAvailableLabel) {
	    super(id, model);

	    StringBuilder sb = new StringBuilder();
	    if (row == null){
		sb.append(notAvailableLabel);

	    }else if (row instanceof ProtectionPlan){
		ProtectionPlan prPlan = (ProtectionPlan) row;
		if (prPlan.getCoveredProductList() == null || prPlan.getCoveredProductList().isEmpty()){
		    sb.append(notAvailableLabel);
		}else{
		    int size = prPlan.getCoveredProductList().size(), i = 0;
		    for(Product p: prPlan.getCoveredProductList()){
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

	    }else if (row instanceof BuybackPlan){
		BuybackPlan bbPlan = (BuybackPlan) row;
		if (bbPlan.getProduct() == null || bbPlan.getProduct().getSerialNumber() == null){
		    sb.append(getString("noSerialNumber.label"));
		}else{
		    sb.append(bbPlan.getProduct().getSerialNumber());
		}
	    }
	    Label deviceSerialNumberList = new Label("deviceSerialNumberList", sb.toString());
	    deviceSerialNumberList.setEscapeModelStrings(false);
	    add(deviceSerialNumberList);
	}
    }

    private String determineStatusLabel(String notAvailableLabel, final ServicePlan row) {
	String styleClass = "", value = notAvailableLabel;
	if (ServicePlan.PLAN_STATUS_ACTIVE.equalsIgnoreCase(row.getPlanStatus())){
	    styleClass = "green";
	    value = statusFmt.format(row.getPlanStatus());
	}else if (ServicePlan.PLAN_STATUS_INACTIVE.equalsIgnoreCase(row.getPlanStatus())){
	    styleClass = "red";
	    value = statusFmt.format(row.getPlanStatus());
	}else if (ServicePlan.PLAN_STATUS_ON_HOLD.equalsIgnoreCase(row.getPlanStatus())){
	    styleClass = "orange";
	    value = statusFmt.format(row.getPlanStatus());
	}else if (ServicePlan.PLAN_STATUS_CANCELLED.equalsIgnoreCase(row.getPlanStatus())){
	    styleClass = "red";
	    value = statusFmt.format(row.getPlanStatus());
	}else if (ServicePlan.PLAN_STATUS_UNKNOWN.equalsIgnoreCase(row.getPlanStatus())){
	    value = statusFmt.format(row.getPlanStatus());
	}
	StringBuilder sb = new StringBuilder();
	sb.append("<span class=\"");
	sb.append(styleClass);
	sb.append("\">");
	sb.append(value);
	sb.append("</span>");
	return sb.toString();
    }

    // returns null if successful or returns error string
    public String loadData() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	try{
	    session.setServicePlanList(customerDataService.getAllServicePlans(session.getBestBuyCustomer()));
	    if (session.getServicePlanList() == null){
		session.setServicePlanList(new ArrayList<ServicePlan>());
	    }
	    logger.debug("# servicePlan items=" + session.getServicePlanList().size());
	    return null;
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException is " + be.getFullMessage());
	    session.setServicePlanList(null);
	    return be.getFullMessage();
	}catch(ServiceException se){
	    logger.error("ServiceException is " + se.getFullMessage());
	    session.setServicePlanList(null);
	    return se.getFullMessage();
	}
    }
}
