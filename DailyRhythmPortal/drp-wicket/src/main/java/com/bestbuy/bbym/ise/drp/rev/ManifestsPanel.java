package com.bestbuy.bbym.ise.drp.rev;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
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
import org.springframework.beans.BeanUtils;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class ManifestsPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ManifestsPanel.class);

    private static final String UPS_TRACKING_LINK = "http://wwwapps.ups.com/WebTracking/track?track=yes&trackNums=";

    @SpringBean(name = "shippingService")
    private ShippingService shippingService;

    // Model Data
    private ManifestsDataProvider manifestsDataProvider = new ManifestsDataProvider();
    private ManifestSearchCriteria manifestSearchCriteria;

    // State Data
    private ShippingParameters shippingParams;
    private ManifestSearchCriteria.SearchType searchType = ManifestSearchCriteria.SearchType.RECENT;
    private boolean showLoading = true;
    private String loadingError;
    private final String na = getString("notApplicable.label");

    // Wicket Elements
    private FeedbackPanel feedbackPanel;
    private AjaxFallbackDefaultDataTable<Manifest> manifestsTable;
    private WebMarkupContainer noManifestsData;
    private AjaxButton manifestsRecentFilterButton, manifestsDateRangeFilterButton, manifestsSerialNumFilterButton;
    private AjaxButton manifestsItemTagFilterButton, manifestsUpsTrackingNumFilterButton;

    public ManifestsPanel(String id, final FeedbackPanel feedbackPanel, final ShippingParameters shippingParams) {
	super(id);
	this.feedbackPanel = feedbackPanel;
	setShippingParams(shippingParams);

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	manifestSearchCriteria = session.getManifestSearchCriteria();
	logger.debug("create manifests panel selectedFilter=" + manifestSearchCriteria.getSearchType());

	final List<IColumn<Manifest>> columns = new ArrayList<IColumn<Manifest>>();
	columns.add(new FormatPropertyColumn<Manifest, BigInteger>(
		new ResourceModel("manifestsPanel.manifestId.column"), "manifestID", "manifestID", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "manifestId_col";
	    }
	});
	columns.add(new FormatPropertyColumn<Manifest, Date>(new ResourceModel("manifestsPanel.createdDate.column"),
		"dateTimeCreated", "dateTimeCreated", new DateFormatter<Date>("MM/dd/yy"), na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "dateTimeCreated_col";
	    }
	});
	columns.add(new FormatPropertyColumn<Manifest, String>(new ResourceModel("manifestsPanel.status.column"),
		"status", "status", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClass() {
		return "status_col";
	    }
	});
	columns
		.add(new AbstractColumn<Manifest>(new ResourceModel("manifestsPanel.deviceCount.column"), "deviceCount") {

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
			Manifest row = (Manifest) rowModel.getObject();
			cellItem.add(new ManifestsPanel.DeviceCountPanel(componentId, rowModel, row));
		    }

		    @Override
		    public String getCssClass() {
			return "deviceCount_col";
		    }
		});
	columns.add(new FormatPropertyColumn<Manifest, Date>(new ResourceModel("manifestsPanel.upsTrackingNum.column"),
		"trackingIdentifier", "trackingIdentifier", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void populateItem(Item<ICellPopulator<Manifest>> cellItem, String componentId,
		    IModel<Manifest> rowModel) {
		Manifest row = (Manifest) rowModel.getObject();
		cellItem.add(new UPSTrackingLinkPanel(componentId, rowModel, row, feedbackPanel));
	    }

	    @Override
	    public String getCssClass() {
		return "checkout_col";
	    }

	});
	columns.add(new AbstractColumn<Manifest>(null, "manifestID") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Manifest row = (Manifest) rowModel.getObject();
		if (row.getTrackingIdentifier() == null){
		    // Open manifest
		    cellItem.add(new ManifestsPanel.OpenPanel(componentId, rowModel, row));
		}else{
		    cellItem.add(new ManifestsPanel.DetailsPanel(componentId, rowModel, row));
		}
	    }

	    @Override
	    public String getCssClass() {
		return "details_col";
	    }
	});

	manifestsTable = new AjaxFallbackDefaultDataTable<Manifest>("manifestsTable", columns, manifestsDataProvider,
		500) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		return manifestsDataProvider.size() > 0;
	    }
	};
	manifestsTable.setMarkupId("manifestsTable");
	manifestsTable.setOutputMarkupId(true);
	manifestsTable.setOutputMarkupPlaceholderTag(true);
	add(manifestsTable);

	final Form<Object> form = new Form<Object>("manifestsForm");
	add(form);

	noManifestsData = new WebMarkupContainer("noManifestsData") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		if (showLoading){
		    return false;
		}
		return (!form.hasError() || loadingError != null) && manifestsDataProvider.size() == 0;
	    }
	};
	noManifestsData.setOutputMarkupPlaceholderTag(true);
	add(noManifestsData);

	Label noManifestsDataLabel = new Label("noManifestsDataLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (loadingError == null){
		    return getString("manifestsTable.noData.label");
		}
		return getString("manifestsTable.dataError.label");
	    }
	});
	noManifestsDataLabel.setOutputMarkupPlaceholderTag(true);
	noManifestsData.add(noManifestsDataLabel);

	// Recent Filter

	manifestsRecentFilterButton = new AjaxButton("manifestsRecentFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsRecentFilterButton onSubmit numMostRecentManifests="
			+ shippingParams.getNumMostRecentManifests());
		manifestSearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.RECENT);
		loadingError = null;
		showLoading = true;
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadManifests", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsRecentFilterButton onError");
		loadingError = null;
		manifestsDataProvider.setManifestList(null);
		manifestsTable.modelChanged();
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	manifestsRecentFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsRecentFilterButton);

	AjaxLink<Object> manifestsRecentFilterLink = new AjaxLink<Object>("manifestsRecentFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.RECENT;
		logger.debug("searchType=" + searchType);
	    }

	};
	manifestsRecentFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsRecentFilterLink);

	// Date Range Filter

	final DateTextField manifestsStartDateFilter = new DateTextField("manifestsStartDateFilter",
		new PropertyModel<Date>(this, "manifestSearchCriteria.startDate"), "MM/dd/yy") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchType == ManifestSearchCriteria.SearchType.DATE_RANGE){
		    logger.debug("checking validity of start date");
		    super.validate();
		}
	    }
	};
	manifestsStartDateFilter.setOutputMarkupPlaceholderTag(true);
	manifestsStartDateFilter.add(new DatePicker());
	manifestsStartDateFilter.setRequired(true);
	form.add(manifestsStartDateFilter);

	final DateTextField manifestsEndDateFilter = new DateTextField("manifestsEndDateFilter",
		new PropertyModel<Date>(this, "manifestSearchCriteria.endDate"), "MM/dd/yy") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchType == ManifestSearchCriteria.SearchType.DATE_RANGE){
		    logger.debug("checking validity of end date");
		    super.validate();
		}
	    }
	};
	manifestsEndDateFilter.setOutputMarkupPlaceholderTag(true);
	manifestsEndDateFilter.add(new DatePicker());
	manifestsEndDateFilter.setRequired(true);
	form.add(manifestsEndDateFilter);

	manifestsDateRangeFilterButton = new AjaxButton("manifestsDateRangeFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsDateRangeFilterButton onSubmit startDate="
			+ Util.toString(manifestSearchCriteria.getStartDate(), null) + " endDate="
			+ Util.toString(manifestSearchCriteria.getEndDate(), null));
		manifestSearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.DATE_RANGE);
		loadingError = null;
		showLoading = true;
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadManifests", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsDateRangeFilterButton onError");
		loadingError = null;
		manifestsDataProvider.setManifestList(null);
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	manifestsDateRangeFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsDateRangeFilterButton);

	AjaxLink<Object> manifestsDateRangeFilterLink = new AjaxLink<Object>("manifestsDateRangeFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.DATE_RANGE;
		logger.debug("searchType=" + searchType);
	    }
	};
	manifestsDateRangeFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsDateRangeFilterLink);

	// IMEI/ESN Filter

	final TextField<String> manifestsSerialNumFilter = new TextField<String>("manifestsSerialNumFilter",
		new PropertyModel<String>(this, "manifestSearchCriteria.imeiesn")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchType == ManifestSearchCriteria.SearchType.SERIAL_NUM){
		    logger.debug("checking validity of imei/esn");
		    super.validate();
		}
	    }
	};
	manifestsSerialNumFilter.setOutputMarkupPlaceholderTag(true);
	manifestsSerialNumFilter.setRequired(true);
	form.add(manifestsSerialNumFilter);

	manifestsSerialNumFilterButton = new AjaxButton("manifestsSerialNumFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger
			.debug("manifestsSerialNumFilterButton onSubmit serialNum="
				+ manifestSearchCriteria.getImeiesn());
		manifestSearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.SERIAL_NUM);
		loadingError = null;
		showLoading = true;
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadManifests", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsSerialNumFilterButton onError");
		loadingError = null;
		manifestsDataProvider.setManifestList(null);
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	manifestsSerialNumFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsSerialNumFilterButton);

	AjaxLink<Object> manifestsSerialNumFilterLink = new AjaxLink<Object>("manifestsSerialNumFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.SERIAL_NUM;
		logger.debug("searchType=" + searchType);
	    }
	};
	manifestsSerialNumFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsSerialNumFilterLink);

	// Item Tag ID Filter

	final TextField<String> manifestsItemTagFilter = new TextField<String>("manifestsItemTagFilter",
		new PropertyModel<String>(this, "manifestSearchCriteria.itemTag")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchType == ManifestSearchCriteria.SearchType.ITEM_TAG_ID){
		    logger.debug("checking validity of item tag ID");
		    super.validate();
		}
	    }
	};
	manifestsItemTagFilter.setOutputMarkupPlaceholderTag(true);
	manifestsItemTagFilter.setRequired(true);
	form.add(manifestsItemTagFilter);

	manifestsItemTagFilterButton = new AjaxButton("manifestsItemTagFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsItemTagFilterButton onSubmit itemTag=" + manifestSearchCriteria.getItemTag());
		manifestSearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.ITEM_TAG_ID);
		loadingError = null;
		showLoading = true;
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadManifests", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsItemTagFilterButton onError");
		loadingError = null;
		manifestsDataProvider.setManifestList(null);
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	manifestsItemTagFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsItemTagFilterButton);

	AjaxLink<Object> manifestsItemTagFilterLink = new AjaxLink<Object>("manifestsItemTagFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.ITEM_TAG_ID;
	    }
	};
	manifestsItemTagFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsItemTagFilterLink);

	// UPS Tracking # Filter

	final TextField<String> manifestsUpsTrackingNumFilter = new TextField<String>("manifestsUpsTrackingNumFilter",
		new PropertyModel<String>(this, "manifestSearchCriteria.trackingIdentifier")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void validate() {
		if (searchType == ManifestSearchCriteria.SearchType.UPS_TRACKING_NUM){
		    logger.debug("checking validity of ups tracking #");
		    super.validate();
		}
	    }
	};
	manifestsUpsTrackingNumFilter.setOutputMarkupPlaceholderTag(true);
	manifestsUpsTrackingNumFilter.setRequired(true);
	form.add(manifestsUpsTrackingNumFilter);

	manifestsUpsTrackingNumFilterButton = new AjaxButton("manifestsUpsTrackingNumFilterButton", new ResourceModel(
		"manifestsFilter.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsUpsTrackingNumFilterButton onSubmit upsTrackingNum="
			+ manifestSearchCriteria.getTrackingIdentifier());
		manifestSearchCriteria.setSearchType(ManifestSearchCriteria.SearchType.UPS_TRACKING_NUM);
		loadingError = null;
		showLoading = true;
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
		launchAction("loadManifests", target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("manifestsUpsTrackingNumFilterButton onError");
		loadingError = null;
		manifestsDataProvider.setManifestList(null);
		target.add(manifestsTable, noManifestsData);
		target.add(feedbackPanel);
		target.add(this);
	    }
	};
	manifestsUpsTrackingNumFilterButton.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsUpsTrackingNumFilterButton);

	AjaxLink<Object> manifestsUpsTrackingNumFilterLink = new AjaxLink<Object>("manifestsUpsTrackingNumFilterLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		searchType = ManifestSearchCriteria.SearchType.UPS_TRACKING_NUM;
		logger.debug("searchType=" + searchType);
	    }
	};
	manifestsUpsTrackingNumFilterLink.setOutputMarkupPlaceholderTag(true);
	form.add(manifestsUpsTrackingNumFilterLink);
    }

    class DeviceCountPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DeviceCountPanel(String id, final IModel<?> model, final Manifest row) {
	    super(id, model);
	    int deviceCount = 0;
	    if (row != null && row.getManifestLine() != null){
		deviceCount = row.getManifestLine().size();
	    }
	    add(new Label("deviceCount", String.valueOf(deviceCount)));
	}
    }

    class OpenPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public OpenPanel(String id, final IModel<?> model, final Manifest row) {
	    super(id, model);
	    AjaxLink<Object> resumeLink = new AjaxLink<Object>("resumeLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("resumeLink onClick");
		    setResponsePage(new BuildManifestPage(ShippingPage.cloneManifest(row), shippingParams));
		}
	    };
	    add(resumeLink);
	}
    }

    class UPSTrackingLinkPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public UPSTrackingLinkPanel(String id, final IModel<Manifest> model, final Manifest row,
		final FeedbackPanel feedbackPanel) {
	    super(id, model);

	    AjaxLink<Manifest> upsTrackingLink = new AjaxLink<Manifest>("upsTrackingLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    if (row != null && row.getTrackingIdentifier() == na){
			// do nothing
		    }else{
			Manifest manifest = new Manifest();
			BeanUtils.copyProperties(row, manifest);
			String externalLink = UPS_TRACKING_LINK + row.getTrackingIdentifier();
			target.appendJavaScript("window.open('" + externalLink + "')");
		    }
		}
	    };
	    Label upsTrackingLabel = new Label("upsTrackingLabel", row.getTrackingIdentifier());
	    Label noUpsLabel = new Label("noUpsLabel", na);

	    upsTrackingLabel.setEscapeModelStrings(false);
	    if (row.getTrackingIdentifier() != null){
		upsTrackingLabel.setVisible(true);
		noUpsLabel.setVisible(false);
	    }else{
		upsTrackingLabel.setVisible(false);
		noUpsLabel.setVisible(true);
	    }
	    upsTrackingLink.add(upsTrackingLabel);
	    add(upsTrackingLink);
	    add(noUpsLabel);
	}
    }

    class ImeiEsnSkuPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ImeiEsnSkuPanel(String id, final IModel<?> model, final ManifestLine row) {
	    super(id, model);

	    logger.trace("manifestLine=" + row);
	    Label stdEligibilityDate = new Label("stdEligibilityDate", row.getImeiesn());
	    stdEligibilityDate.setEscapeModelStrings(false);
	    add(stdEligibilityDate);

	    Label earlyEligibilityDate = new Label("earlyEligibilityDate", row.getSku());
	    earlyEligibilityDate.setEscapeModelStrings(false);
	    add(earlyEligibilityDate);
	}
    }

    class DetailsPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public DetailsPanel(String id, final IModel<?> model, final Manifest row) {
	    super(id, model);
	    AjaxLink<Object> detailsLink = new AjaxLink<Object>("detailsLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.debug("detailsLink onClick");
		    setResponsePage(new ManifestSummaryPage(ShippingPage.cloneManifest(row)));
		}
	    };
	    add(detailsLink);
	}
    }

    // returns null if successful or returns error string
    private String loadData(final ShippingParameters shippingParams) {
	// Build criteria
	ManifestSearchCriteria msc = new ManifestSearchCriteria();
	switch (manifestSearchCriteria.getSearchType()) {
	    case RECENT:
		msc.setMostRecentNumber(new Integer(shippingParams.getNumMostRecentManifests()));
		break;
	    case DATE_RANGE:
		msc.setStartDate(manifestSearchCriteria.getStartDate());
		msc.setEndDate(manifestSearchCriteria.getEndDate());
		break;
	    case SERIAL_NUM:
		msc.setImeiesn(manifestSearchCriteria.getImeiesn());
		break;
	    case ITEM_TAG_ID:
		msc.setItemTag(manifestSearchCriteria.getItemTag());
		break;
	    case UPS_TRACKING_NUM:
		msc.setTrackingIdentifier(manifestSearchCriteria.getTrackingIdentifier());
		break;

	}
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (session.getDrpUser() != null){
	    msc.setStoreID(new BigInteger(session.getDrpUser().getStoreId()));
	}
	logger.debug("storeID=" + msc.getStoreID());

	try{
	    manifestsDataProvider.setManifestList(shippingService.searchManifests(msc, session.getDrpUser()));
	    logger.debug("# of manifests yielded in search " + manifestsDataProvider.getManifestList().size());
	    for(Manifest mf: manifestsDataProvider.getManifestList()){
		if (mf.getManifestLine() == null){
		    logger.debug("# of lines in manifest " + mf.getManifestID() + " is null");
		}else{
		    logger.debug("# of lines in manifest " + mf.getManifestID() + " is " + mf.getManifestLine().size());
		}
	    }
	    return null;
	}catch(ServiceException se){
	    logger.error("Failed to search manifests using ShippingService", se);
	    manifestsDataProvider.setManifestList(null);
	    return se.getMessage();
	}catch(IseBusinessException be){
	    logger.error("Failed to search manifests using ShippingService");
	    manifestsDataProvider.setManifestList(null);
	    return be.getMessage();
	}
    }

    public ManifestSearchCriteria getManifestSearchCriteria() {
	return manifestSearchCriteria;
    }

    @Override
    public String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("selectManifestsNavFilter('");
	onDomReadyJS.append(manifestSearchCriteria.getSearchType().name());
	onDomReadyJS.append("');");
	onDomReadyJS.append("setupSearchNav();");
	return onDomReadyJS.toString();
    }

    @Override
    public String getLaunchAction() {
	return "loadManifests";
    }

    @Override
    public boolean canHandleAction(String action) {
	if ("loadManifests".equals(action)){
	    return true;
	}
	return false;
    }

    @Override
    public void doAction(String action, AjaxRequestTarget target) {
	if ("loadManifests".equals(action)){
	    if (!isLoadingModalOpen()){
		openLoadingModal(getString("manifestsTable.loading.label"), target);
		launchAction("loadManifests", target);
		return;
	    }
	    loadingError = loadData(shippingParams);
	    if (loadingError != null){
		error(loadingError);
		target.add(feedbackPanel);
	    }
	    closeLoadingModal(target);
	    showLoading = false;
	    manifestsTable.modelChanged();
	    target.add(manifestsTable, noManifestsData);
	    target.add(manifestsRecentFilterButton);
	    target.add(manifestsDateRangeFilterButton);
	    target.add(manifestsSerialNumFilterButton);
	    target.add(manifestsItemTagFilterButton);
	    target.add(manifestsUpsTrackingNumFilterButton);
	    if (manifestsDataProvider.size() > 0){
		target.appendJavaScript("setupManifestsTable();");
		target.appendJavaScript("setupTableSorting('#manifestsTable', 'setupManifestsTable();');");
	    }
	}
    }

    public ShippingParameters getShippingParams() {
	return shippingParams;
    }

    public void setShippingParams(ShippingParameters shippingParams) {
	this.shippingParams = shippingParams;
    }

}
