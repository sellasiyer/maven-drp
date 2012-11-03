package com.bestbuy.bbym.ise.drp.customer;

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
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.dashboard.GspDataProvider;
import com.bestbuy.bbym.ise.drp.dashboard.GspStatus;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class GspSelectPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(GspSelectPanel.class);

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private CustInfoModalPanel parentModalPanel;
    private boolean showLoading = true;
    private GspDataProvider gspDataProvider = new GspDataProvider();

    private final String na = getString("notApplicable.label");
    private final MapFormatter<String> statusFmt = GspStatus.generateMapFormatter(this);
    private final DateFormatter<Date> dateFmt = new DateFormatter<Date>("MM/dd/yy");

    public GspSelectPanel(final String id, final CustInfoModalPanel parentModalPanel) {
	super(id);
	this.parentModalPanel = parentModalPanel;

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
	gspColumns.add(new FormatPropertyColumn<ServicePlan, String>(new ResourceModel("gspTable.planStatus.column"),
		"planStatus", "planStatus", statusFmt, na));
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
		cellItem.add(new GspSelectPanel.GspPlanDescriptionNumberPanel(componentId, rowModel, row, na));
	    }
	});
	gspColumns.add(new FormatPropertyColumn<ServicePlan, Date>(new ResourceModel("gspTable.planExpiration.column"),
		"planExpiryDate", "planExpiryDate", dateFmt, na));
	gspColumns.add(new AbstractColumn<ServicePlan>(new ResourceModel("gspTable.deviceDescription.column"),
		"planNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		ServicePlan row = (ServicePlan) rowModel.getObject();
		cellItem.add(new GspSelectPanel.GspDeviceDescriptionPanel(componentId, rowModel, row, na));
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
		cellItem.add(new GspSelectPanel.SelectLinkPanel(componentId, rowModel, row,
			getString("custInfo.selectLink.label")));
	    }
	});

	final AjaxFallbackDefaultDataTable<ServicePlan> gspTable = new AjaxFallbackDefaultDataTable<ServicePlan>(
		"gspTable", gspColumns, gspDataProvider, 200) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		return gspDataProvider.size() > 0;
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
		return gspDataProvider.size() == 0;
	    }
	};
	noGspData.setOutputMarkupPlaceholderTag(true);
	add(noGspData);

	Label noGspDataLabel = new Label("noGspDataLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (parentModalPanel.getFeedbackPanel().anyErrorMessage()){
		    return getString("custInfo.gspTable.dataError.label");
		}
		return getString("custInfo.gspTable.noData.label");
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

	// means by which JS can callback into Java
	final AbstractDefaultAjaxBehavior wicketBehaviorGSP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehaviorGSP id=" + id);
		if ("load".equals(id)){
		    String error = loadData();
		    if (error != null){
			parentModalPanel.getFeedbackPanel().error(error);
			target.add(parentModalPanel.getFeedbackPanel());
		    }
		    showLoading = false;
		    gspTable.modelChanged();
		    target.add(gspTable);
		    target.add(noGspData);
		    target.add(gspDataLoading);
		    parentModalPanel.refresh(target);
		}
	    }
	};
	add(wicketBehaviorGSP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehaviorGSP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorGSP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
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

    class SelectLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public SelectLinkPanel(String id, final IModel<?> model, final ServicePlan row, final String label) {
	    super(id, model);

	    AjaxLink<Object> link = new AjaxLink<Object>("link") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("selectLinkPanel onClick");
		    String error = parentModalPanel.checkSelectedProtectionPlan((ProtectionPlan) row);
		    if (error != null){
			error(error);
			target.add(parentModalPanel.getFeedbackPanel());
			return;
		    }
		    parentModalPanel.setSelectedProtectionPlan((ProtectionPlan) Util.clone(row));
		    parentModalPanel.close(target);
		}
	    };
	    link.setOutputMarkupPlaceholderTag(true);
	    link.add(new Label("label", label));
	    add(link);
	}
    }

    // returns null if successful or returns error string
    public String loadData() {
	try{
	    List<ServicePlan> gspPlans = new ArrayList<ServicePlan>();
	    List<ServicePlan> allPlans = customerDataService.getAllServicePlans(parentModalPanel.getSelectedCustomer());
	    if (allPlans != null){
		for(ServicePlan sp: allPlans){
		    if (sp instanceof ProtectionPlan){
			gspPlans.add(sp);
		    }
		}
	    }
	    gspDataProvider.setServicePlanList(gspPlans);
	    logger.debug("# gsp plans=" + gspDataProvider.size());
	    return null;
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException is " + be.getFullMessage());
	    gspDataProvider.setServicePlanList(null);
	    return be.getFullMessage();
	}catch(ServiceException se){
	    logger.error("ServiceException is " + se.getFullMessage());
	    gspDataProvider.setServicePlanList(null);
	    return se.getFullMessage();
	}
    }

    public String getOpenPanelJS() {
	if (!isVisible()){
	    return "";
	}
	StringBuilder openPanelJS = new StringBuilder();
	if (showLoading){
	    openPanelJS.append("doWicketBehavior('wicketBehaviorGSP(\"load\")');");
	}else if (gspDataProvider.size() > 0){
	    openPanelJS.append("tablePrep(custInfoModalGspSelectTable);");
	}
	return openPanelJS.toString();
    }

    public String getRefreshPanelJS() {
	return getOpenPanelJS();
    }

    @Override
    public boolean isVisible() {
	return parentModalPanel.getModalState() == CustInfoModalPanel.ModalState.SELECT_GSP
		&& parentModalPanel.getSelectedCustomer().getId() != null;
    }

    public void reloadOnRefresh() {
	gspDataProvider.setServicePlanList(null);
	showLoading = true;
    }
}
