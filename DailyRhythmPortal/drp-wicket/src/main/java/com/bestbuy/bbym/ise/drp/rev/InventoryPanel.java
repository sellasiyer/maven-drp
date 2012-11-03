package com.bestbuy.bbym.ise.drp.rev;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.util.panel.SpanPanel;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.SHP_USER, DrpConstants.SHP_MANAGER })
public class InventoryPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(InventoryPanel.class);

    @SpringBean(name = "shippingService")
    private ShippingService shippingService;

    // Model Data
    private SelectItem<String> statusFilterSelected;
    private InventoryDataProvider inventoryDataProvider = new InventoryDataProvider();
    private ManifestSearchCriteria inventorySearchCriteria;

    // State Data
    private ManifestSearchCriteria.SearchType searchType = ManifestSearchCriteria.SearchType.STATUS;
    private boolean showLoading = true;
    private String loadingError;
    private ShippingParameters shippingParams;

    // Wicket Elements
    private FeedbackPanel feedbackPanel;
    private AjaxFallbackDefaultDataTable<ManifestLine> inventoryTable;
    private WebMarkupContainer shortIndicatorDesc, noInventoryData;
    private AjaxButton inventoryStatusFilterButton, inventorySerialNumFilterButton, inventoryItemTagFilterButton;
    private ConfirmShrinkModalPanel confirmShrinkModalPanel;
    private final String na = getString("notApplicable.label");

    public InventoryPanel(String id, final FeedbackPanel feedbackPanel, final ShippingParameters shippingParams,
	    final ConfirmShrinkModalPanel confirmShrinkModalPanel) {
	super(id);
	this.feedbackPanel = feedbackPanel;
	this.shippingParams = shippingParams;
	this.confirmShrinkModalPanel = confirmShrinkModalPanel;

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	inventorySearchCriteria = session.getInventorySearchCriteria();
	logger.debug("create inventory panel selectedFilter=" + inventorySearchCriteria.getSearchType() + " status="
		+ inventorySearchCriteria.getManifestStatus());

	final List<IColumn<ManifestLine>> columns = new ArrayList<IColumn<ManifestLine>>();
	columns.add(new FormatPropertyColumn<ManifestLine, String>(new ResourceModel("inventoryPanel.itemTag.column"),
		"itemTag", "itemTag", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "itemTag_col";
	    }
	});
	columns.add(new AbstractColumn<ManifestLine>(new ResourceModel("inventoryPanel.serialNum.column"), "imeiesn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<ManifestLine>> cellItem, String componentId,
		    IModel<ManifestLine> rowModel) {
		final ManifestLine row = (ManifestLine) rowModel.getObject();
		cellItem.add(new SpanPanel<ManifestLine>(componentId, rowModel) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String generateLabelString() {
			StringBuilder sb = new StringBuilder();
			sb.append("<span>");
			if (row.getImeiesn() == null){
			    sb.append(na);
			}else{
			    sb.append(row.getImeiesn());
			}
			if (row.getSku() != null){
			    sb.append("<br/>");
			    sb.append(row.getSku());
			}
			sb.append("</span>");
			return sb.toString();
		    }
		});
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "serialNum_col";
	    }
	});
	columns.add(new FormatPropertyColumn<ManifestLine, String>(new ResourceModel(
		"inventoryPanel.productDescription.column"), "productDescription", "productDescription", null, na));
	columns.add(new FormatPropertyColumn<ManifestLine, String>(
		new ResourceModel("inventoryPanel.returnType.column"), "returnType", "returnType", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "returnType_col";
	    }
	});
	columns.add(new AbstractColumn<ManifestLine>(new ResourceModel("inventoryPanel.deviceStatus.column"),
		"deviceStatus") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<ManifestLine>> cellItem, String componentId,
		    IModel<ManifestLine> rowModel) {
		final ManifestLine row = (ManifestLine) rowModel.getObject();
		cellItem.add(new InventoryPanel.DeviceStatusPanel(componentId, rowModel, row));
	    }

	    @Override
	    public String getCssClass() {
		return "deviceStatus_col";
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});

	inventoryTable = new AjaxFallbackDefaultDataTable<ManifestLine>("inventoryTable", columns,
		inventoryDataProvider, 500) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		return inventoryDataProvider.size() > 0;
	    }
	};
	inventoryTable.setMarkupId("inventoryTable");
	inventoryTable.setOutputMarkupId(true);
	inventoryTable.setOutputMarkupPlaceholderTag(true);
	add(inventoryTable);

	final Form<Object> form = new Form<Object>("inventoryForm");
	add(form);

	noInventoryData = new WebMarkupContainer("noInventoryData") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		return (!form.hasError() || loadingError != null) && inventoryDataProvider.size() == 0;
	    }
	};
	noInventoryData.setOutputMarkupPlaceholderTag(true);
	add(noInventoryData);

	Label noInventoryDataLabel = new Label("noInventoryDataLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (loadingError == null){
		    return getString("inventoryTable.noData.label");
		}
		return getString("inventoryTable.dataError.label");
	    }
	});
	noInventoryDataLabel.setOutputMarkupPlaceholderTag(true);
	noInventoryData.add(noInventoryDataLabel);

	shortIndicatorDesc = new WebMarkupContainer("shortIndicatorDesc") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		return inventoryDataProvider.size() > 0;
	    }
	};
	shortIndicatorDesc.setMarkupId("shortIndicatorDesc");
	shortIndicatorDesc.setOutputMarkupPlaceholderTag(true);
	add(shortIndicatorDesc);

	// Status Filter

	List<SelectItem<String>> statusFilterSelections = shippingParams.getInventoryStatusFilters();

	if (inventorySearchCriteria != null && inventorySearchCriteria.getManifestStatus() != null){
	    for(SelectItem<String> selected: statusFilterSelections){
		if (inventorySearchCriteria.getManifestStatus().equals(selected.getKey())){
		    statusFilterSelected = selected;
		    break;
		}
	    }
	}
	if (statusFilterSelected == null && !statusFilterSelections.isEmpty()){
	    statusFilterSelected = statusFilterSelections.get(0);
	}

	ChoiceRenderer<SelectItem<String>> choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	DropDownChoice<SelectItem<String>> statusFilterDropDown = new DropDownChoice<SelectItem<String>>(
		"statusFilterSelect", new PropertyModel<SelectItem<String>>(this, "statusFilterSelected"),
		statusFilterSelections, choiceRenderer);
	statusFilterDropDown.setMarkupId("statusFilterSelect");
	statusFilterDropDown.setOutputMarkupId(true);
	statusFilterDropDown.setOutputMarkupPlaceholderTag(true);
	form.add(statusFilterDropDown);

	inventoryStatusFilterButton = new AjaxButton("inventoryStatusFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("statusFilterSelected=" + statusFilterSelected.getKey());
		logger.debug("inventoryStatusFilterButton onSubmit status="
			+ inventorySearchCriteria.getManifestStatus());
		inventorySearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.STATUS);
		loadingError = null;
		showLoading = true;
		target.add(inventoryTable);
		target.add(shortIndicatorDesc);
		target.add(noInventoryData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadInventory", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsRecentFilterButton onError");
		loadingError = null;
		inventoryDataProvider.setInventoryList(null);
		inventoryTable.modelChanged();
		target.add(inventoryTable);
		target.add(shortIndicatorDesc);
		target.add(noInventoryData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	inventoryStatusFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(inventoryStatusFilterButton);

	final AjaxLink<Object> inventoryStatusFilterLink = new AjaxLink<Object>("inventoryStatusFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.STATUS;
		logger.debug("searchType=" + searchType);
	    }

	};
	inventoryStatusFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(inventoryStatusFilterLink);

	// IMEI/ESN Filter

	final TextField<String> inventorySerialNumFilter = new TextField<String>("inventorySerialNumFilter",
		new PropertyModel<String>(this, "inventorySearchCriteria.imeiesn")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchType == ManifestSearchCriteria.SearchType.SERIAL_NUM){
		    logger.debug("checking validity of imei/esn");
		    super.validate();
		}
	    }
	};
	inventorySerialNumFilter.setOutputMarkupPlaceholderTag(true);
	inventorySerialNumFilter.setRequired(true);
	form.add(inventorySerialNumFilter);

	inventorySerialNumFilterButton = new AjaxButton("inventorySerialNumFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("inventorySerialNumFilterButton onSubmit serialNum="
			+ inventorySearchCriteria.getImeiesn());
		inventorySearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.SERIAL_NUM);
		loadingError = null;
		showLoading = true;
		target.add(inventoryTable);
		target.add(shortIndicatorDesc);
		target.add(noInventoryData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadInventory", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("inventorySerialNumFilterButton onError");
		loadingError = null;
		inventoryDataProvider.setInventoryList(null);
		target.add(inventoryTable);
		target.add(shortIndicatorDesc);
		target.add(noInventoryData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	inventorySerialNumFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(inventorySerialNumFilterButton);

	AjaxLink<Object> inventorySerialNumFilterLink = new AjaxLink<Object>("inventorySerialNumFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.SERIAL_NUM;
		logger.debug("searchType=" + searchType);
	    }
	};
	inventorySerialNumFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(inventorySerialNumFilterLink);

	// Item Tag ID Filter

	final TextField<String> inventoryItemTagFilter = new TextField<String>("inventoryItemTagFilter",
		new PropertyModel<String>(this, "inventorySearchCriteria.itemTag")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchType == ManifestSearchCriteria.SearchType.ITEM_TAG_ID){
		    logger.debug("checking validity of item tag ID");
		    super.validate();
		}
	    }
	};
	inventoryItemTagFilter.setOutputMarkupPlaceholderTag(true);
	inventoryItemTagFilter.setRequired(true);
	form.add(inventoryItemTagFilter);

	inventoryItemTagFilterButton = new AjaxButton("inventoryItemTagFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("inventoryItemTagFilterButton onSubmit itemTag=" + inventorySearchCriteria.getItemTag());
		inventorySearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.ITEM_TAG_ID);
		loadingError = null;
		showLoading = true;
		target.add(inventoryTable);
		target.add(shortIndicatorDesc);
		target.add(noInventoryData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadInventory", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsItemTagFilterButton onError");
		loadingError = null;
		inventoryDataProvider.setInventoryList(null);
		target.add(inventoryTable);
		target.add(shortIndicatorDesc);
		target.add(noInventoryData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	inventoryItemTagFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(inventoryItemTagFilterButton);

	AjaxLink<Object> inventoryItemTagFilterLink = new AjaxLink<Object>("inventoryItemTagFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.ITEM_TAG_ID;
	    }
	};
	inventoryItemTagFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(inventoryItemTagFilterLink);
    }

    // returns null if successful or returns error string
    private String loadData() {
	// Build criteria
	ManifestSearchCriteria msc = new ManifestSearchCriteria();
	switch (inventorySearchCriteria.getSearchType()) {
	    case STATUS:
		if (statusFilterSelected != null && statusFilterSelected.getValue() != null
			&& !statusFilterSelected.getKey().equals("ALL")){
		    msc.setManifestStatus(statusFilterSelected.getKey());
		    inventorySearchCriteria.setManifestStatus(statusFilterSelected.getKey());
		}
		break;
	    case SERIAL_NUM:
		msc.setImeiesn(inventorySearchCriteria.getImeiesn());
		break;
	    case ITEM_TAG_ID:
		msc.setItemTag(inventorySearchCriteria.getItemTag());
		break;
	}
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (session.getDrpUser() != null){
	    msc.setStoreID(new BigInteger(session.getDrpUser().getStoreId()));
	}
	logger.debug("storeID=" + msc.getStoreID());

	try{
	    inventoryDataProvider.setInventoryList(shippingService.searchManifest(msc, session.getDrpUser()));
	    logger.debug("# of inventory items yielded in search " + inventoryDataProvider.getInventoryList().size());
	    return null;
	}catch(ServiceException se){
	    logger.error("Failed to search inventory using ShippingService", se);
	    inventoryDataProvider.setInventoryList(null);
	    return se.getMessage();
	}catch(IseBusinessException be){
	    logger.error("Failed to search inventory using ShippingService");
	    inventoryDataProvider.setInventoryList(null);
	    return be.getMessage();
	}
    }

    public ManifestSearchCriteria getInventorySearchCriteria() {
	return inventorySearchCriteria;
    }

    class DeviceStatusPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DeviceStatusPanel(String id, final IModel<?> model, final ManifestLine row) {
	    super(id, model);
	    final WebMarkupContainer shortIndicator = new WebMarkupContainer("shortIndicator") {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isVisible() {
		    return row.isShort();
		}
	    };
	    shortIndicator.setOutputMarkupPlaceholderTag(true);
	    add(shortIndicator);

	    logger.debug("itemTag=" + row.getItemTag() + " shrinkable=" + row.isShrinkable());

	    final Label deviceStatus = new Label("deviceStatus", new FormatPropertyModel<ManifestLine, String>(
		    new PropertyModel<ManifestLine>(row, "deviceStatus"), null, na));
	    deviceStatus.setOutputMarkupPlaceholderTag(true);
	    add(deviceStatus);

	    final Label transferNumber = new Label("transferNumber", new FormatPropertyModel<ManifestLine, String>(
		    new PropertyModel<ManifestLine>(row, "transferNumber"), null, na));
	    transferNumber.setOutputMarkupPlaceholderTag(true);
	    add(transferNumber);

	    final AjaxLink<Object> shrinkLink = new AjaxLink<Object>("shrinkLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("shrinkLink onClick");
		    confirmShrinkModalPanel.setConfirmShrinkQuestion(shippingParams.getConfirmShrinkQuestion(row
			    .getReturnType(), getString("inventoryPanel.confirmShrinkDefaultQuestion.label")));
		    confirmShrinkModalPanel.setMainfestLine(row);
		    confirmShrinkModalPanel.open(target);
		}

		@Override
		public boolean isVisible() {
		    return getDailyRhythmPortalSession().getDrpUser().hasRole(DrpConstants.SHP_MANAGER)
			    && row.isShrinkable();
		}
	    };
	    add(shrinkLink);
	}
    }

    @Override
    public String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("selectInventoryNavFilter('");
	onDomReadyJS.append(inventorySearchCriteria.getSearchType().name());
	onDomReadyJS.append("');");
	onDomReadyJS.append("setupSearchNav();");
	onDomReadyJS.append("setupFilterCopy();");
	return onDomReadyJS.toString();
    }

    @Override
    public String getLaunchAction() {
	return "loadInventory";
    }

    @Override
    public boolean canHandleAction(String action) {
	if ("loadInventory".equals(action)){
	    return true;
	}
	return false;
    }

    @Override
    public void doAction(String action, AjaxRequestTarget target) {
	if ("loadInventory".equals(action)){
	    if (!isLoadingModalOpen()){
		openLoadingModal(getString("inventoryTable.loading.label"), target);
		launchAction("loadInventory", target);
		return;
	    }
	    loadingError = loadData();
	    if (loadingError != null){
		error(loadingError);
		target.add(feedbackPanel);
	    }
	    closeLoadingModal(target);
	    showLoading = false;
	    inventoryTable.modelChanged();
	    target.add(inventoryTable);
	    target.add(shortIndicatorDesc);
	    target.add(noInventoryData);
	    target.add(inventoryStatusFilterButton);
	    target.add(inventorySerialNumFilterButton);
	    target.add(inventoryItemTagFilterButton);
	    if (inventoryDataProvider.size() > 0){
		target.appendJavaScript("setupInventoryTable();");
		target.appendJavaScript("setupTableSorting('#inventoryTable', 'setupInventoryTable();');");
	    }
	}
    }
}
