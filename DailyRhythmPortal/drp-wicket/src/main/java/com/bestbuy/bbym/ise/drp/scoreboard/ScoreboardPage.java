package com.bestbuy.bbym.ise.drp.scoreboard;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.OkCancelDialogPanel;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardData;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardEmployeeShift;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardNotionalMargin;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardOperationType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardTransactionType;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardWidget;
import com.bestbuy.bbym.ise.drp.service.ScoreboardService;
import com.bestbuy.bbym.ise.drp.util.panel.ProgressMetersPanel;
import com.bestbuy.bbym.ise.drp.util.panel.StoreProgressMeterPanel;
import com.bestbuy.bbym.ise.drp.util.panel.TachDialModalsPanel;
import com.bestbuy.bbym.ise.drp.util.panel.TachDialsPanel;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class ScoreboardPage extends NewBaseNavPage {

    private static final long serialVersionUID = -6927267579533524007L;
    private static Logger logger = LoggerFactory.getLogger(ScoreboardPage.class);

    @SpringBean(name = "scoreboardService")
    private ScoreboardService scoreboardService;

    public enum PageState {
	SWAS_MOBILE, SWAS_COMPUTING, SWAS_CBG, SAS_MOBILE
    }

    // State Data
    private PageState pageState = PageState.SWAS_MOBILE;

    // Model Data
    private List<ScoreboardCategory> postSalesCategoryList, postReturnsCategoryList;

    // Wicket Elements
    private FeedbackPanel feedbackPanel;
    private WebMarkupContainer scoreboardContainer, topPane;
    private WebMarkupContainer mobileStoreColumn, mobileStore, computingStoreColumn, computingStore;
    private Label mobileStoreNumber, mobileStoreName, computingStoreNumber, computingStoreName;
    private AjaxLink<Object> mobileStoreLink, computingStoreLink;
    private AjaxLink<Object> mobileSaleLink, mobileReturnLink, computingSaleLink, computingReturnLink;
    private WebMarkupContainer mobileEmployeeColumn, computingEmployeeColumn, mobileNoEmployeesSection,
	    computingNoEmployeesSection;
    private StoreProgressMeterPanel cbgStoreMeter, mobileStoreMeter, computingStoreMeter;
    private TachDialModalsPanel mobileTachDialModals, computingTachDialModals;
    private TachDialsPanel mobileTachDials, computingTachDials;
    private ProgressMetersPanel mobileEmployeeProgessMeters, computingEmployeeProgessMeters;

    private PostSalesModalPanel postSalesModalPanel;
    private PostReturnsModalPanel postReturnsModalPanel;
    private EmployeeDetailsModalPanel employeeDetailsModalPanel;
    private OkCancelDialogPanel storeTypeQueryModalPanel;
    private SbdStoreDetailsViewModal storeDetailsModalPanel;

    // SPLUNK LOG FORMAT
    private static final String STORE_INFO_LOG_FORMAT = "SPLUNK FILTER Store data : USER_ID={0}, STORE_ID={1},STORE_NAME={2},DISTRICT=(3),REGION={4}";

    public ScoreboardPage(final PageParameters parameters) {
	super(parameters);

	final String na = getString("notApplicable.label");

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	String pageStateString = session.getSessionProperty("scoreboard.pageState");
	if (pageStateString == null){
	    setSessionPageState(session);
	}else{
	    try{
		pageState = PageState.valueOf(pageStateString);
	    }catch(IllegalArgumentException iae){
		setSessionPageState(session);
	    }
	}
	// Uncomment code to force SAS Mobile Page State
	// pageState = PageState.SAS_MOBILE;
	// session.setSessionProperty("scoreboard.pageState", pageState.name());

	final List<ScoreboardWidget> mobileTachDialList = new ArrayList<ScoreboardWidget>();
	for(int i = 0; i < 8; i++){
	    ScoreboardWidget sbw = new ScoreboardWidget();
	    mobileTachDialList.add(sbw);
	}

	final List<ScoreboardWidget> computingTachDialList = new ArrayList<ScoreboardWidget>();
	for(int i = 0; i < 8; i++){
	    ScoreboardWidget sbw = new ScoreboardWidget();
	    computingTachDialList.add(sbw);
	}

	storeTypeQueryModalPanel = new OkCancelDialogPanel("storeTypeQueryModalPanel", "SWAS", "SAS") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("storeTypeQueryModalPanel onClose");
		if (isOk()){
		    session.getDrpUser().getStore().setStoreType(ScoreboardStoreType.SWAS);
		}else{
		    session.getDrpUser().getStore().setStoreType(ScoreboardStoreType.SAS);
		}
		setSessionPageState(session);
		target.appendJavaScript("refreshPage();");
	    }
	};
	storeTypeQueryModalPanel.setQuestion(getString("scoreboardPage.storeTypeQueryModal.question"));
	add(storeTypeQueryModalPanel);

	postSalesModalPanel = new PostSalesModalPanel("postSalesModalPanel", null,
		new ArrayList<ScoreboardEmployeeShift>()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("postSalesModalPanel onClose refresh=" + isOk());
		if (isOk()){
		    launchAction("load", target);
		}
	    }
	};
	add(postSalesModalPanel);

	postReturnsModalPanel = new PostReturnsModalPanel("postReturnsModalPanel", null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("postReturnsModalPanel onClose refresh=" + isOk());
		if (isOk()){
		    launchAction("load", target);
		}
	    }
	};
	add(postReturnsModalPanel);

	employeeDetailsModalPanel = new EmployeeDetailsModalPanel("employeeDetailsModalPanel", null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("employeeDetailsModalPanel onClose");
		feedbackPanel.setVisible(true);
		if (getNumItemsDeleted() > 0){
		    launchAction("load", target);
		}
	    }
	};
	add(employeeDetailsModalPanel);

	storeDetailsModalPanel = new SbdStoreDetailsViewModal("storeDetailsModalPanel", null) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("storeDetailsModalPanel onClose");
		feedbackPanel.setVisible(true);
		if (isOk()){
		    launchAction("load", target);
		}
	    }
	};
	add(storeDetailsModalPanel);

	// Settings Links

	final WebMarkupContainer sasSettings = new WebMarkupContainer("sasSettings") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SAS_MOBILE;
	    }
	};
	sasSettings.setOutputMarkupPlaceholderTag(true);
	add(sasSettings);

	final AjaxLink<Object> sasSettingsLink = new AjaxLink<Object>("link") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("sasSettingsLink onClick");
		PageParameters pp = new PageParameters();
		pp.add(PageParameterKeys.SCOREBOARD_OPERATION.getUrlParameterKey(), ScoreboardOperationType.MOBILE
			.name());
		pp.add(PageParameterKeys.STORE_TYPE.getUrlParameterKey(), ScoreboardStoreType.SAS.name());
		setResponsePage(new SettingsPage(pp));
	    }
	};
	sasSettingsLink.setOutputMarkupPlaceholderTag(true);
	sasSettings.add(sasSettingsLink);

	final WebMarkupContainer swasSettings = new WebMarkupContainer("swasSettings") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState != PageState.SAS_MOBILE;
	    }
	};
	swasSettings.setOutputMarkupPlaceholderTag(true);
	add(swasSettings);

	final AjaxLink<Object> swasSettingsMobileLink = new AjaxLink<Object>("mobileLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("swasSettingsMobileLink onClick");
		PageParameters pp = new PageParameters();
		pp.add(PageParameterKeys.SCOREBOARD_OPERATION.getUrlParameterKey(), ScoreboardOperationType.MOBILE
			.name());
		pp.add(PageParameterKeys.STORE_TYPE.getUrlParameterKey(), ScoreboardStoreType.SWAS.name());
		setResponsePage(new SettingsPage(pp));
	    }
	};
	swasSettingsMobileLink.setOutputMarkupPlaceholderTag(true);
	swasSettings.add(swasSettingsMobileLink);

	final AjaxLink<Object> swasSettingsComputingLink = new AjaxLink<Object>("computingLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("swasSettingsComputingLink onClick");
		PageParameters pp = new PageParameters();
		pp.add(PageParameterKeys.SCOREBOARD_OPERATION.getUrlParameterKey(), ScoreboardOperationType.COMPUTING
			.name());
		pp.add(PageParameterKeys.STORE_TYPE.getUrlParameterKey(), ScoreboardStoreType.SWAS.name());
		setResponsePage(new SettingsPage(pp));
	    }
	};
	swasSettingsComputingLink.setOutputMarkupPlaceholderTag(true);
	swasSettings.add(swasSettingsComputingLink);

	// SWAS State (Mobile, Computing, CBG) Selectors

	final WebMarkupContainer stateSelector = new WebMarkupContainer("stateSelector") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState != PageState.SAS_MOBILE;
	    }
	};
	stateSelector.setOutputMarkupPlaceholderTag(true);
	add(stateSelector);

	final WebMarkupContainer mobileSelection = new WebMarkupContainer("mobileSelection");
	mobileSelection.setOutputMarkupPlaceholderTag(true);
	mobileSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (pageState == PageState.SWAS_MOBILE){
		    return "selected";
		}
		return "";
	    }
	}));
	stateSelector.add(mobileSelection);

	final AjaxLink<Object> mobileSelectionLink = new AjaxLink<Object>("mobileSelectionLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("mobileSelectionLink onClick");
		if (pageState != PageState.SWAS_MOBILE){
		    session.setSessionProperty("scoreboard.pageState", PageState.SWAS_MOBILE.name());
		    setResponsePage(ScoreboardPage.class);
		}
	    }
	};
	mobileSelectionLink.setOutputMarkupPlaceholderTag(true);
	mobileSelection.add(mobileSelectionLink);

	final WebMarkupContainer computingSelection = new WebMarkupContainer("computingSelection");
	computingSelection.setOutputMarkupPlaceholderTag(true);
	computingSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (pageState == PageState.SWAS_COMPUTING){
		    return "selected";
		}
		return "";
	    }
	}));
	stateSelector.add(computingSelection);

	final AjaxLink<Object> computingSelectionLink = new AjaxLink<Object>("computingSelectionLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("computingSelectionLink onClick");
		if (pageState != PageState.SWAS_COMPUTING){
		    session.setSessionProperty("scoreboard.pageState", PageState.SWAS_COMPUTING.name());
		    setResponsePage(ScoreboardPage.class);
		}
	    }
	};
	computingSelectionLink.setOutputMarkupPlaceholderTag(true);
	computingSelection.add(computingSelectionLink);

	final WebMarkupContainer cbgSelection = new WebMarkupContainer("cbgSelection");
	cbgSelection.setOutputMarkupPlaceholderTag(true);
	cbgSelection.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (pageState == PageState.SWAS_CBG){
		    return "selected";
		}
		return "";
	    }
	}));
	stateSelector.add(cbgSelection);

	final AjaxLink<Object> cbgSelectionLink = new AjaxLink<Object>("cbgSelectionLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("cbgSelectionLink onClick");
		if (pageState != PageState.SWAS_CBG){
		    session.setSessionProperty("scoreboard.pageState", PageState.SWAS_CBG.name());
		    setResponsePage(ScoreboardPage.class);
		}
	    }
	};
	cbgSelectionLink.setOutputMarkupPlaceholderTag(true);
	cbgSelection.add(cbgSelectionLink);

	// Overall Container

	scoreboardContainer = new WebMarkupContainer("scoreboard-container");
	scoreboardContainer.setMarkupId("scoreboard-container");
	scoreboardContainer.setOutputMarkupId(true);
	scoreboardContainer.setOutputMarkupPlaceholderTag(true);
	add(scoreboardContainer);

	scoreboardContainer.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (pageState == PageState.SWAS_CBG){
		    return "cbg";
		}
		return "";
	    }
	}));

	// CBG Store Panel

	topPane = new WebMarkupContainer("topPane") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SWAS_CBG;
	    }
	};
	topPane.setOutputMarkupPlaceholderTag(true);
	scoreboardContainer.add(topPane);

	cbgStoreMeter = new StoreProgressMeterPanel("cbgStoreMeter", getString("scoreboardPage.storeMeter.cbg.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClasses() {
		return "store-bar large";
	    }
	};
	cbgStoreMeter.setOutputMarkupPlaceholderTag(true);
	topPane.add(cbgStoreMeter);

	// Mobile Store Panels

	mobileStoreColumn = new WebMarkupContainer("mobileStoreColumn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SAS_MOBILE || pageState == PageState.SWAS_MOBILE
			|| pageState == PageState.SWAS_CBG;
	    }
	};
	mobileStoreColumn.setOutputMarkupPlaceholderTag(true);
	scoreboardContainer.add(mobileStoreColumn);

	mobileStore = new WebMarkupContainer("mobileStore") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SAS_MOBILE || pageState == PageState.SWAS_MOBILE;
	    }
	};
	mobileStore.setOutputMarkupPlaceholderTag(true);
	mobileStoreColumn.add(mobileStore);

	mobileStoreNumber = new Label("mobileStoreNumber", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (session.getDrpUser().getStore() == null || session.getDrpUser().getStore().getId() == null){
		    return "Store " + na;
		}

		return "Store " + session.getDrpUser().getStore().getId().replaceAll("^0*", "");
	    }
	});
	mobileStoreNumber.setOutputMarkupPlaceholderTag(true);
	mobileStore.add(mobileStoreNumber);

	mobileStoreName = new Label("mobileStoreName", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (session.getDrpUser().getStore() == null || session.getDrpUser().getStore().getStoreName() == null){
		    return na;
		}
		return session.getDrpUser().getStore().getStoreName();
	    }
	});
	mobileStoreName.setOutputMarkupPlaceholderTag(true);
	mobileStore.add(mobileStoreName);

	mobileStoreLink = new AjaxLink<Object>("mobileStoreLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("mobileStoreLink onClick");
		openLoadingModal(getString("scoreboardPage.storeDetails.loading.label"), target);
		launchAction("loadMobileStoreDetails", target);
	    }
	};
	mobileStoreLink.setOutputMarkupPlaceholderTag(true);
	mobileStore.add(mobileStoreLink);

	mobileStoreMeter = new StoreProgressMeterPanel("mobileStoreMeter",
		getString("scoreboardPage.storeMeter.mobile.label"),
		getString("scoreboardPage.storeMeter.productivity.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClasses() {
		return "store-bar";
	    }
	};
	mobileStoreColumn.add(mobileStoreMeter);

	mobileTachDialModals = new TachDialModalsPanel("mobileTachDialModals", mobileTachDialList);
	add(mobileTachDialModals);
	mobileTachDials = new TachDialsPanel("mobileTachDials", mobileTachDialList, "mobileTachDialModals");
	mobileStoreColumn.add(mobileTachDials);

	// Computing Store Panels

	computingStoreColumn = new WebMarkupContainer("computingStoreColumn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SWAS_COMPUTING || pageState == PageState.SWAS_CBG;
	    }
	};
	computingStoreColumn.setOutputMarkupPlaceholderTag(true);
	scoreboardContainer.add(computingStoreColumn);

	computingStoreColumn.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (pageState == PageState.SWAS_COMPUTING){
		    return "left-pane";
		}
		return "right-pane";
	    }
	}));

	computingStore = new WebMarkupContainer("computingStore") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SWAS_COMPUTING;
	    }
	};
	computingStore.setOutputMarkupPlaceholderTag(true);
	computingStoreColumn.add(computingStore);

	computingStoreNumber = new Label("computingStoreNumber", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (session.getDrpUser().getStore() == null || session.getDrpUser().getStore().getId() == null){
		    return "Store " + na;
		}
		return "Store " + session.getDrpUser().getStore().getId().replaceAll("^0*", "");

	    }
	});
	computingStoreNumber.setOutputMarkupPlaceholderTag(true);
	computingStore.add(computingStoreNumber);

	computingStoreName = new Label("computingStoreName", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (session.getDrpUser().getStore() == null || session.getDrpUser().getStore().getStoreName() == null){
		    return na;
		}
		return session.getDrpUser().getStore().getStoreName();
	    }
	});
	computingStoreName.setOutputMarkupPlaceholderTag(true);
	computingStore.add(computingStoreName);

	computingStoreLink = new AjaxLink<Object>("computingStoreLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("computingStoreLink onClick");
		openLoadingModal(getString("scoreboardPage.storeDetails.loading.label"), target);
		launchAction("loadComputingStoreDetails", target);
	    }
	};
	computingStoreLink.setOutputMarkupPlaceholderTag(true);
	computingStore.add(computingStoreLink);

	computingStoreMeter = new StoreProgressMeterPanel("computingStoreMeter",
		getString("scoreboardPage.storeMeter.computing.label"),
		getString("scoreboardPage.storeMeter.productivity.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getCssClasses() {
		return "store-bar";
	    }
	};
	computingStoreColumn.add(computingStoreMeter);

	computingTachDialModals = new TachDialModalsPanel("computingTachDialModals", computingTachDialList);
	add(computingTachDialModals);
	computingTachDials = new TachDialsPanel("computingTachDials", computingTachDialList, "computingTachDialModals");
	computingStoreColumn.add(computingTachDials);

	// Mobile Employee Panels

	mobileEmployeeColumn = new WebMarkupContainer("mobileEmployeeColumn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SAS_MOBILE || pageState == PageState.SWAS_MOBILE;
	    }
	};
	mobileEmployeeColumn.setOutputMarkupPlaceholderTag(true);
	scoreboardContainer.add(mobileEmployeeColumn);

	mobileSaleLink = new AjaxLink<Object>("mobileSaleLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("mobileSaleLink onClick");
		postSalesModalPanel.setClientTimeZoneOffsetMinutes(getClientTimeZoneOffsetMinutes());
		postSalesModalPanel.open(target);
	    }

	    @Override
	    public boolean isEnabled() {
		return postSalesModalPanel.getNumEmployees() > 0;
	    }
	};
	mobileSaleLink.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (postSalesModalPanel.getNumEmployees() > 0){
		    return "";
		}
		return "disabled";
	    }
	}));
	mobileSaleLink.setOutputMarkupPlaceholderTag(true);
	mobileEmployeeColumn.add(mobileSaleLink);

	mobileReturnLink = new AjaxLink<Object>("mobileReturnLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("mobileReturnLink onClick");
		postReturnsModalPanel.setClientTimeZoneOffsetMinutes(getClientTimeZoneOffsetMinutes());
		postReturnsModalPanel.open(target);
	    }

	    @Override
	    public boolean isEnabled() {
		return postSalesModalPanel.getNumEmployees() > 0;
	    }
	};
	mobileReturnLink.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (postSalesModalPanel.getNumEmployees() > 0){
		    return "";
		}
		return "disabled";
	    }
	}));
	mobileReturnLink.setOutputMarkupPlaceholderTag(true);
	mobileEmployeeColumn.add(mobileReturnLink);

	mobileNoEmployeesSection = new WebMarkupContainer("mobileNoEmployeesSection") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return postSalesModalPanel.getNumEmployees() == 0;
	    }
	};
	mobileNoEmployeesSection.setOutputMarkupPlaceholderTag(true);
	mobileEmployeeColumn.add(mobileNoEmployeesSection);

	mobileEmployeeProgessMeters = new ProgressMetersPanel("mobileEmployeeProgessMeters",
		getString("scoreboardPage.employeeMeter.title.label"),
		getString("scoreboardPage.employeeMeter.productivity.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClickExpandLink(AjaxRequestTarget target, final ScoreboardEmployeeNotionalMargin employeeData) {
		if (employeeData == null || employeeData.getEmployeeShift() == null){
		    logger.warn("ScoreboardEmployeeShift object is null");
		    return;
		}
		logger.info("onClick mobile employee details for " + employeeData.getEmployeeShift().getEmpFullName());
		employeeDetailsModalPanel.setEmployeeNotionalMargin(employeeData);
		openLoadingModal(getString("scoreboardPage.employeeDetails.loading.label"), target);
		launchAction("loadMobileEmplDetails", target);
	    }

	    @Override
	    public boolean isVisible() {
		return postSalesModalPanel.getNumEmployees() > 0;
	    }
	};
	mobileEmployeeProgessMeters.setOutputMarkupPlaceholderTag(true);
	mobileEmployeeColumn.add(mobileEmployeeProgessMeters);

	// Computing Employee Panels

	computingEmployeeColumn = new WebMarkupContainer("computingEmployeeColumn") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return pageState == PageState.SWAS_COMPUTING;
	    }
	};
	computingEmployeeColumn.setOutputMarkupPlaceholderTag(true);
	scoreboardContainer.add(computingEmployeeColumn);

	computingSaleLink = new AjaxLink<Object>("computingSaleLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("computingSaleLink onClick");
		postSalesModalPanel.setClientTimeZoneOffsetMinutes(getClientTimeZoneOffsetMinutes());
		postSalesModalPanel.open(target);
	    }

	    @Override
	    public boolean isEnabled() {
		return postSalesModalPanel.getNumEmployees() > 0;
	    }
	};
	computingSaleLink.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (postSalesModalPanel.getNumEmployees() > 0){
		    return "";
		}
		return "disabled";
	    }
	}));
	computingSaleLink.setOutputMarkupPlaceholderTag(true);
	computingEmployeeColumn.add(computingSaleLink);

	computingReturnLink = new AjaxLink<Object>("computingReturnLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("computingReturnLink onClick");
		postReturnsModalPanel.setClientTimeZoneOffsetMinutes(getClientTimeZoneOffsetMinutes());
		postReturnsModalPanel.open(target);
	    }

	    @Override
	    public boolean isEnabled() {
		return postSalesModalPanel.getNumEmployees() > 0;
	    }
	};
	computingReturnLink.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (postSalesModalPanel.getNumEmployees() > 0){
		    return "";
		}
		return "disabled";
	    }
	}));
	computingReturnLink.setOutputMarkupPlaceholderTag(true);
	computingEmployeeColumn.add(computingReturnLink);

	computingNoEmployeesSection = new WebMarkupContainer("computingNoEmployeesSection") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return postSalesModalPanel.getNumEmployees() == 0;
	    }
	};
	computingNoEmployeesSection.setOutputMarkupPlaceholderTag(true);
	computingEmployeeColumn.add(computingNoEmployeesSection);

	computingEmployeeProgessMeters = new ProgressMetersPanel("computingEmployeeProgessMeters",
		getString("scoreboardPage.employeeMeter.title.label"),
		getString("scoreboardPage.employeeMeter.productivity.label")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClickExpandLink(AjaxRequestTarget target, final ScoreboardEmployeeNotionalMargin employeeData) {
		if (employeeData == null || employeeData.getEmployeeShift() == null){
		    logger.warn("ScoreboardEmployeeShift object is null");
		    return;
		}
		logger.info("onClick computing employee details for "
			+ employeeData.getEmployeeShift().getEmpFullName());
		employeeDetailsModalPanel.setEmployeeNotionalMargin(employeeData);
		openLoadingModal(getString("scoreboardPage.employeeDetails.loading.label"), target);
		launchAction("loadComputingEmplDetails", target);
	    }

	    @Override
	    public boolean isVisible() {
		return postSalesModalPanel.getNumEmployees() > 0;
	    }
	};
	computingEmployeeProgessMeters.setOutputMarkupPlaceholderTag(true);
	computingEmployeeColumn.add(computingEmployeeProgessMeters);

	determineClientTime();
	launchAction("load");
    }

    private boolean refreshCbgMeter(AjaxRequestTarget target, ScoreboardData mobileScoreboardData,
	    ScoreboardData computingScoreboardData, StoreProgressMeterPanel storeMeter) {
	if (mobileScoreboardData != null && mobileScoreboardData.getScoreboardNotionalMargin() != null
		&& computingScoreboardData != null && computingScoreboardData.getScoreboardNotionalMargin() != null){
	    ScoreboardNotionalMargin combined = new ScoreboardNotionalMargin();
	    combined.setNextHour(mobileScoreboardData.getScoreboardNotionalMargin().getNextHour());
	    combined.setNextHourPercentage(mobileScoreboardData.getScoreboardNotionalMargin().getNextHourPercentage());
	    combined.setCurrentValue(mobileScoreboardData.getScoreboardNotionalMargin().getCurrentValue()
		    + computingScoreboardData.getScoreboardNotionalMargin().getCurrentValue());
	    combined.setTargetValue(mobileScoreboardData.getScoreboardNotionalMargin().getTargetValue()
		    + computingScoreboardData.getScoreboardNotionalMargin().getTargetValue());
	    storeMeter.refresh(target, combined);
	    return true;
	}
	return false;
    }

    private boolean refreshStoreMeter(AjaxRequestTarget target, ScoreboardData scoreboardData,
	    StoreProgressMeterPanel storeMeter) {
	if (scoreboardData != null && scoreboardData.getScoreboardNotionalMargin() != null){
	    storeMeter.refresh(target, scoreboardData.getScoreboardNotionalMargin());
	    return true;
	}
	return false;
    }

    private boolean refreshTachDials(AjaxRequestTarget target, ScoreboardData scoreboardData, TachDialsPanel tachDials,
	    TachDialModalsPanel tachDialModals) {
	if (scoreboardData != null && scoreboardData.getWidgetList() != null){
	    tachDialModals.refresh(target, scoreboardData.getWidgetList());
	    tachDials.refresh(target, scoreboardData.getWidgetList());
	    return true;
	}
	return false;
    }

    private boolean refreshEmployeeProgressMeters(AjaxRequestTarget target, ScoreboardData scoreboardData,
	    ProgressMetersPanel employeeProgessMeters) {
	if (scoreboardData != null && scoreboardData.getEmployeeNotionalMarginList() != null){
	    ScoreboardEmployeeNotionalMargin topEmployeeData = null;
	    int topProductivity = 0;
	    for(ScoreboardEmployeeNotionalMargin sbe: scoreboardData.getEmployeeNotionalMarginList()){
		if (sbe.getProductivity() > topProductivity){
		    topProductivity = sbe.getProductivity();
		    topEmployeeData = sbe;
		}
	    }
	    employeeProgessMeters.refresh(target, scoreboardData.getEmployeeNotionalMarginList(), topEmployeeData);
	    return true;
	}
	return false;
    }

    private void setSessionPageState(DailyRhythmPortalSession session) {
	pageState = PageState.SWAS_MOBILE;
	if (session.getDrpUser() != null && session.getDrpUser().getStore() != null
		&& ScoreboardStoreType.SAS.equals(session.getDrpUser().getStore().getStoreType())){
	    pageState = PageState.SAS_MOBILE;
	}
	session.setSessionProperty("scoreboard.pageState", pageState.name());
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append(cbgStoreMeter.getOnDomReadyJS());
	onDomReadyJS.append(mobileStoreMeter.getOnDomReadyJS());
	onDomReadyJS.append(computingStoreMeter.getOnDomReadyJS());
	return onDomReadyJS.toString();
    }

    @Override
    protected void doAction(String action, AjaxRequestTarget target) {
	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (employeeDetailsModalPanel != null && employeeDetailsModalPanel.canHandleAction(action)){
	    employeeDetailsModalPanel.doAction(action, target);

	}else if ("load".equals(action)){
	    // Get store info (if needed)
	    if (session.getDrpUser() != null && session.getDrpUser().getStore() != null
		    && session.getDrpUser().getStore().getId() != null
		    && session.getDrpUser().getStore().getStoreType() == null){
		if (!isLoadingModalOpen()){
		    openLoadingModal(getString("scoreboardPage.getStoreInfo.loading.label"), target);
		    launchAction("load", target);
		    return;
		}
		try{
		    logger.debug("Before call to get store info");
		    Store store = scoreboardService.getStoreInfo(session.getDrpUser().getStore().getId());
		    logger.debug("After call to get store info");
		    if (store == null){
			logger.warn("No store found by scoreboardService for storeId="
				+ session.getDrpUser().getStore().getId());
		    }else{
			session.getDrpUser().setStore(store);
			// SPLUNK FILTER Store data : USER_ID={0},
			// STORE_ID={1},STORE_NAME={2},DISTRICT={3},REGION={4}
			logger.info(MessageFormat.format(STORE_INFO_LOG_FORMAT, session.getDrpUser().getUserId(),
				session.getDrpUser().getStoreId(), store.getStoreName(), store.getDistrict(), store
					.getRegion()));
			logger.debug("store=" + store);
		    }
		}catch(ServiceException se){
		    logger.warn("ServiceException when calling service to get store info", se);
		}catch(IseBusinessException be){
		    logger.warn("IseBusinessException when calling service to get store info", be);
		}
		if (session.getDrpUser().getStore().getStoreType() == null){
		    closeLoadingModal(target);
		    storeTypeQueryModalPanel.open(target);
		    return;
		}
		setSessionPageState(session);
		closeLoadingModal(target);
		target.appendJavaScript("refreshPage();");
		return;
	    }

	    if (!isLoadingModalOpen()){
		openLoadingModal(getString("scoreboardPage.loading.label"), target);
		launchAction("load", target);
		return;
	    }

	    ScoreboardOperationType operationType = null;
	    String serviceError = null;
	    ScoreboardData sdM = null, sdC = null;

	    // Get data for mobile (if needed)
	    if (serviceError == null && pageState == PageState.SWAS_MOBILE || pageState == PageState.SAS_MOBILE
		    || pageState == PageState.SWAS_CBG){
		operationType = ScoreboardOperationType.MOBILE;
		try{
		    logger.debug("Before call to get mobile data");
		    sdM = scoreboardService.getScoreBoardData(session.getDrpUser().getStoreId(),
			    ScoreboardOperationType.MOBILE, session.getDrpUser().getStore().getStoreType(),
			    getClientTime(), session.getDrpUser().getUserId());
		    logger.debug("After call to get mobile data");
		}catch(ServiceException se){
		    logger.error("Failed to get mobile scoreboard data: ServiceException is " + se.getFullMessage());
		    serviceError = getString("scoreboardPage.getScoreboardDataFailed.message.label") + " "
			    + se.getMessage();
		}
	    }

	    // Get data for computing (if needed)
	    if (serviceError == null && pageState == PageState.SWAS_COMPUTING || pageState == PageState.SWAS_CBG){
		operationType = ScoreboardOperationType.COMPUTING;
		try{
		    logger.debug("Before call to get computing data");
		    sdC = scoreboardService.getScoreBoardData(session.getDrpUser().getStoreId(),
			    ScoreboardOperationType.COMPUTING, session.getDrpUser().getStore().getStoreType(),
			    getClientTime(), session.getDrpUser().getUserId());
		    logger.debug("After call to get computing data");
		}catch(ServiceException se){
		    logger.error("Failed to get computing scoreboard data: ServiceException is " + se.getFullMessage());
		    serviceError = getString("scoreboardPage.getScoreboardDataFailed.message.label") + " "
			    + se.getMessage();
		}
		if (sdM != null && sdM.getScoreboardNotionalMargin() != null
			&& sdM.getScoreboardNotionalMargin().getNextHour() != null && sdC != null
			&& sdC.getScoreboardNotionalMargin() != null){
		    sdC.getScoreboardNotionalMargin().setNextHour(sdM.getScoreboardNotionalMargin().getNextHour());
		    sdC.getScoreboardNotionalMargin().setNextHourPercentage(
			    sdM.getScoreboardNotionalMargin().getNextHourPercentage());
		}
	    }

	    // Get category data for post sales (if needed)
	    if (serviceError == null && pageState != PageState.SWAS_CBG && operationType != null){
		try{
		    logger.debug("Before call to get post sales data");
		    postSalesCategoryList = scoreboardService.getScoreboardItems(session.getDrpUser().getStoreId(),
			    null, session.getDrpUser().getStore().getStoreType(), operationType,
			    ScoreboardTransactionType.EMP_SALES_POST);
		    if (postSalesCategoryList == null || postSalesCategoryList.isEmpty()){
			throw new ServiceException(IseExceptionCodeEnum.BusinessException, "No data returned");
		    }
		    logger.debug("After call to get post sales data, postSalesCategoryList.size="
			    + postSalesCategoryList.size());
		}catch(ServiceException se){
		    logger.error("Failed to get post sale scoreboard data: ServiceException is " + se.getFullMessage());
		    serviceError = getString("scoreboardPage.getScoreboardDataFailed.message.label") + " "
			    + se.getMessage();
		}
	    }

	    // Get category data for post returns (if needed)
	    if (serviceError == null && pageState != PageState.SWAS_CBG && operationType != null){
		try{
		    logger.debug("Before call to get post returns data");
		    postReturnsCategoryList = scoreboardService.getScoreboardItems(session.getDrpUser().getStoreId(),
			    null, session.getDrpUser().getStore().getStoreType(), operationType,
			    ScoreboardTransactionType.STORE_RETURNS_POST);
		    if (postReturnsCategoryList == null || postReturnsCategoryList.isEmpty()){
			throw new ServiceException(IseExceptionCodeEnum.BusinessException, "No data returned");
		    }
		    logger.debug("After call to get post returns data, postReturnsCategoryList.size="
			    + postReturnsCategoryList.size());
		}catch(ServiceException se){
		    logger.error("Failed to get post returns scoreboard data: ServiceException is "
			    + se.getFullMessage());
		    serviceError = getString("scoreboardPage.getScoreboardDataFailed.message.label") + " "
			    + se.getMessage();
		}
	    }

	    boolean showError = false;

	    switch (pageState) {
		case SAS_MOBILE:
		case SWAS_MOBILE:
		    showError = !refreshStoreMeter(target, sdM, mobileStoreMeter);
		    showError = !refreshTachDials(target, sdM, mobileTachDials, mobileTachDialModals);
		    showError = !refreshEmployeeProgressMeters(target, sdM, mobileEmployeeProgessMeters);
		    break;

		case SWAS_COMPUTING:
		    showError = !refreshStoreMeter(target, sdC, computingStoreMeter);
		    showError = !refreshTachDials(target, sdC, computingTachDials, computingTachDialModals);
		    showError = !refreshEmployeeProgressMeters(target, sdC, computingEmployeeProgessMeters);
		    break;

		case SWAS_CBG:
		    showError = !refreshCbgMeter(target, sdM, sdC, cbgStoreMeter);
		    showError = !refreshStoreMeter(target, sdM, mobileStoreMeter);
		    showError = !refreshTachDials(target, sdM, mobileTachDials, mobileTachDialModals);
		    showError = !refreshStoreMeter(target, sdC, computingStoreMeter);
		    showError = !refreshTachDials(target, sdC, computingTachDials, computingTachDialModals);
		    break;
	    }

	    if (serviceError != null){
		error(serviceError);
		target.add(feedbackPanel);
	    }else if (showError){
		error(getString("scoreboardPage.noScoreboardDataReturned.message.label"));
		target.add(feedbackPanel);
	    }else{
		List<ScoreboardEmployeeShift> employeeShiftList;
		switch (pageState) {
		    case SAS_MOBILE:
		    case SWAS_MOBILE:
			postSalesModalPanel.setOperationType(operationType);
			postSalesModalPanel.setNumberOfCategoriesOnLeftSide(3);
			postSalesModalPanel.refreshCategoryData(postSalesCategoryList);
			employeeShiftList = new ArrayList<ScoreboardEmployeeShift>();
			if (sdM.getEmployeeNotionalMarginList() != null){
			    for(ScoreboardEmployeeNotionalMargin nm: sdM.getEmployeeNotionalMarginList()){
				employeeShiftList.add(nm.getEmployeeShift());
			    }
			}
			postSalesModalPanel.refreshEmployeeShiftData(employeeShiftList);
			postReturnsModalPanel.setOperationType(operationType);
			postReturnsModalPanel.setNumberOfCategoriesOnLeftSide(3);
			postReturnsModalPanel.refreshCategoryData(postReturnsCategoryList);
			storeDetailsModalPanel.setNotionalMargin(sdM.getScoreboardNotionalMargin());
			break;

		    case SWAS_COMPUTING:
			postSalesModalPanel.setOperationType(operationType);
			postSalesModalPanel.setNumberOfCategoriesOnLeftSide(4);
			postSalesModalPanel.refreshCategoryData(postSalesCategoryList);
			employeeShiftList = new ArrayList<ScoreboardEmployeeShift>();
			if (sdC.getEmployeeNotionalMarginList() != null){
			    for(ScoreboardEmployeeNotionalMargin nm: sdC.getEmployeeNotionalMarginList()){
				employeeShiftList.add(nm.getEmployeeShift());
			    }
			}
			postSalesModalPanel.refreshEmployeeShiftData(employeeShiftList);
			postReturnsModalPanel.setOperationType(operationType);
			postReturnsModalPanel.setNumberOfCategoriesOnLeftSide(4);
			postReturnsModalPanel.refreshCategoryData(postReturnsCategoryList);
			storeDetailsModalPanel.setNotionalMargin(sdC.getScoreboardNotionalMargin());
			break;
		}
	    }
	    closeLoadingModal(target);

	    target.add(scoreboardContainer, topPane, cbgStoreMeter);
	    target.add(mobileStoreColumn, mobileStore, mobileStoreNumber, mobileStoreName, mobileStoreLink);
	    target.add(mobileStoreMeter, mobileTachDialModals, mobileEmployeeColumn);
	    target.add(computingStoreColumn, computingStore, computingStoreNumber, computingStoreName,
		    computingStoreLink);
	    target.add(computingStoreMeter, computingTachDialModals, computingEmployeeColumn);
	    target.add(mobileNoEmployeesSection, computingNoEmployeesSection);

	}else if ("loadMobileEmplDetails".equals(action) || "loadComputingEmplDetails".equals(action)){
	    ScoreboardOperationType operationType = ScoreboardOperationType.MOBILE;
	    employeeDetailsModalPanel.setNumberOfCategoriesOnLeftSide(3);
	    if ("loadComputingEmplDetails".equals(action)){
		operationType = ScoreboardOperationType.COMPUTING;
		employeeDetailsModalPanel.setNumberOfCategoriesOnLeftSide(4);
	    }
	    List<ScoreboardCategory> employeeDetailsCategoryList = null;
	    List<ScoreboardDataItem> employeeSalesList = null;
	    try{
		logger.debug("Before call to get employee details data");
		employeeDetailsCategoryList = scoreboardService
			.getScoreboardItems(session.getDrpUser().getStoreId(), employeeDetailsModalPanel
				.getEmployeeNotionalMargin().getEmployeeShift().getEmpDailyNtlMrgnId(), session
				.getDrpUser().getStore().getStoreType(), operationType,
				ScoreboardTransactionType.EMP_SALES_DETAILS);
		if (employeeDetailsCategoryList == null || employeeDetailsCategoryList.isEmpty()){
		    throw new ServiceException(IseExceptionCodeEnum.BusinessException, "No data returned");
		}
		addNotionalMarginToCategoryList(employeeDetailsModalPanel.getEmployeeNotionalMargin(),
			employeeDetailsCategoryList, this);

		employeeSalesList = scoreboardService.getEmployeeSalesTransactions(session.getDrpUser().getStoreId(),
			session.getDrpUser().getStore().getStoreType(), operationType, employeeDetailsModalPanel
				.getEmployeeNotionalMargin().getEmployeeShift());
		logger.debug("After call to get employee details data");
	    }catch(ServiceException se){
		logger.error("Failed to get employee details scoreboard data: ServiceException is "
			+ se.getFullMessage());
		error(getString("scoreboardPage.getScoreboardDataFailed.message.label") + " " + se.getMessage());
		target.add(feedbackPanel);
		return;
	    }finally{
		closeLoadingModal(target);
	    }
	    employeeDetailsModalPanel.setClientTimeZoneOffsetMinutes(getClientTimeZoneOffsetMinutes());
	    employeeDetailsModalPanel.setOperationType(operationType);
	    employeeDetailsModalPanel.refreshCategoryData(employeeDetailsCategoryList);
	    employeeDetailsModalPanel.setEmployeeSales(employeeSalesList);
	    employeeDetailsModalPanel.reset();
	    feedbackPanel.setVisible(false);
	    employeeDetailsModalPanel.open(target);

	}else if ("loadMobileStoreDetails".equals(action) || "loadComputingStoreDetails".equals(action)){
	    ScoreboardOperationType operationType = ScoreboardOperationType.MOBILE;
	    storeDetailsModalPanel.setNumberOfCategoriesOnLeftSide(3);
	    if ("loadComputingStoreDetails".equals(action)){
		operationType = ScoreboardOperationType.COMPUTING;
		storeDetailsModalPanel.setNumberOfCategoriesOnLeftSide(4);
	    }
	    List<ScoreboardCategory> storeDetailsCategoryList = null;
	    try{
		logger.debug("Before call to get store details data");
		storeDetailsCategoryList = scoreboardService.getScoreboardItems(session.getDrpUser().getStoreId(),
			null, session.getDrpUser().getStore().getStoreType(), operationType,
			ScoreboardTransactionType.STORE_DETAILS);
		if (storeDetailsCategoryList == null || storeDetailsCategoryList.isEmpty()){
		    throw new ServiceException(IseExceptionCodeEnum.BusinessException, "No data returned");
		}
		addNotionalMarginToCategoryList(storeDetailsModalPanel.getNotionalMargin(), storeDetailsCategoryList,
			this);
		logger.debug("After call to get store details data");
	    }catch(ServiceException se){
		logger.error("Failed to get store details scoreboard data: ServiceException is " + se.getFullMessage());
		error(getString("scoreboardPage.getScoreboardDataFailed.message.label") + " " + se.getMessage());
		target.add(feedbackPanel);
		return;
	    }finally{
		closeLoadingModal(target);
	    }
	    storeDetailsModalPanel.setClientTimeZoneOffsetMinutes(getClientTimeZoneOffsetMinutes());
	    storeDetailsModalPanel.setOperationType(operationType);
	    storeDetailsModalPanel.refreshCategoryData(storeDetailsCategoryList);
	    feedbackPanel.setVisible(false);
	    storeDetailsModalPanel.open(target);
	}
    }

    public static void addNotionalMarginToCategoryList(ScoreboardNotionalMargin notionalMargin,
	    List<ScoreboardCategory> detailsCategoryList, Component component) {
	ScoreboardCategory nmCat = new ScoreboardCategory();
	nmCat.setName(component.getString("scoreboardModal.notionalMargin.category.title"));
	List<ScoreboardDataItem> dataItems = new ArrayList<ScoreboardDataItem>();
	ScoreboardDataItem dataItem;
	dataItem = new ScoreboardDataItem();
	dataItem.setName(component.getString("scoreboardModal.notionalMargin.actual.title"));
	dataItem.setSalesTotal(new BigDecimal(notionalMargin.getCurrentValue()));
	dataItems.add(dataItem);
	dataItem = new ScoreboardDataItem();
	dataItem.setName(component.getString("scoreboardModal.notionalMargin.goal.title"));
	dataItem.setSalesTotal(new BigDecimal(notionalMargin.getTargetValue()));
	dataItems.add(dataItem);
	nmCat.setDataItems(dataItems);
	detailsCategoryList.add(0, nmCat);
    }
}
