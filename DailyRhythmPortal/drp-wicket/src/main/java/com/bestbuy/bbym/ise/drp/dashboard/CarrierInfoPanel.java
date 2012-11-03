package com.bestbuy.bbym.ise.drp.dashboard;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.TransparentWebMarkupContainer;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.domain.Subscription;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.entitlement.EntitlementSearchPage;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.triage2.IssuesPage;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FeedbackMessageFilter;
import com.bestbuy.bbym.ise.drp.util.FormatModel;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyColumn;
import com.bestbuy.bbym.ise.drp.util.MapFormatter;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class CarrierInfoPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(CarrierInfoPanel.class);

    @SpringBean(name = "customerService")
    private CustomerService customerService;

    private AbstractDefaultAjaxBehavior wicketBehaviorCIP;
    private OptinNotAllowedModalPanel optinNotAllowedModalPanel;
    private String numlinesAvailable;

    private List<SelectItem<String>> actionSelections, actionSelectionsNoOptIn;
    private MapFormatter<String> statusFmt = GspStatus.generateMapFormatter(this);
    private DateFormatter<Date> dateFmt = new DateFormatter<Date>("MM/dd/yy");

    public CarrierInfoPanel(String id) {
	super(id);

	final String na = getString("notApplicable.label");
	final Date now = new Date();

	final MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>();
	final PhoneFormatter<String> phoneFmt = new PhoneFormatter<String>();

	final DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	final CarrierInfoDataProvider provider = new CarrierInfoDataProvider();

	if (session.getCustomer() != null && session.getCustomer().getSubscription() != null){
	    provider.setLineDataList(session.getCustomer().getSubscription().getLines());
	    if (session.getCustomer().getSubscription().getNumberOfLinesAvailable() != null
		    && session.getCustomer().getSubscription().getNumberOfLinesAvailable().intValue() > 0){
		numlinesAvailable = session.getCustomer().getSubscription().getNumberOfLinesAvailable().toString();
	    }
	}
	logger.debug("numlinesAvailable=" + numlinesAvailable);
	if (session.getCustomer() != null){
	    provider.setSearchPhoneNum(session.getCustomer().getContactPhone());
	}

	optinNotAllowedModalPanel = new OptinNotAllowedModalPanel("optinNotAllowedModalPanel") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
	    }
	};
	optinNotAllowedModalPanel.setOutputMarkupPlaceholderTag(true);
	add(optinNotAllowedModalPanel);

	final boolean showTriage = WicketUtils.getBooleanProperty("triage.enabled", true, this);
	final boolean showReturn = WicketUtils.getBooleanProperty("returnsExchanges.enabled", true, this);

	actionSelections = new ArrayList<SelectItem<String>>();
	actionSelections.add(new SelectItem<String>("A", "Actions"));
	if (showTriage){
	    actionSelections.add(new SelectItem<String>("T", "Triage"));
	}
	if (showReturn){
	    actionSelections.add(new SelectItem<String>("R", "Return"));
	}
	actionSelections.add(new SelectItem<String>("O", "Opt-In"));

	actionSelectionsNoOptIn = new ArrayList<SelectItem<String>>();
	actionSelectionsNoOptIn.add(new SelectItem<String>("A", "Actions"));
	if (showTriage){
	    actionSelectionsNoOptIn.add(new SelectItem<String>("T", "Triage"));
	}
	if (showReturn){
	    actionSelectionsNoOptIn.add(new SelectItem<String>("R", "Return"));
	}

	// opt-in form
	final Form<Object> carrierInfoOptinForm = new Form<Object>("carrierInfoOptinForm");
	carrierInfoOptinForm.setOutputMarkupPlaceholderTag(true);

	final List<IColumn<Line>> columns = new ArrayList<IColumn<Line>>();
	columns.add(new AbstractColumn<Line>(new ResourceModel("carrierPanel.phone.column"), "mobileNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Line row = (Line) rowModel.getObject();
		cellItem.add(new CarrierInfoPanel.PhoneNumberPanel(componentId, rowModel, row, phoneFmt, na));
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "phone_col";
	    }
	});
	columns.add(new AbstractColumn<Line>(new ResourceModel("carrierPanel.hardwareDescriptionSerialNum.column"),
		"device.description") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Line row = (Line) rowModel.getObject();
		cellItem.add(new CarrierInfoPanel.HardwareDescriptionSerialNumPanel(componentId, rowModel, row, na));
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }
	});
	columns
		.add(new AbstractColumn<Line>(new ResourceModel("carrierPanel.eligibility.column"),
			"stdEligibilityDate") {

		    private static final long serialVersionUID = 1L;

		    @SuppressWarnings("rawtypes")
		    @Override
		    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
			Line row = (Line) rowModel.getObject();
			cellItem.add(new CarrierInfoPanel.EligibilityPanel(componentId, rowModel, row, "Eligible",
				"Not Eligible", now));
		    }

		    @Override
		    public Component getHeader(String componentId) {
			Label label = new Label(componentId, getDisplayModel());
			label.setEscapeModelStrings(false);
			return label;
		    }

		    @Override
		    public String getCssClass() {
			return "eligibility_col";
		    }
		});
	columns.add(new FormatPropertyColumn<Line, Date>(new ResourceModel("carrierPanel.contractEndDate.column"),
		"contractEndDate", "contractEndDate", dateFmt, "Unknown") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "contractEndDate_col";
	    }
	});
	columns.add(new FormatPropertyColumn<Line, String>(new ResourceModel("carrierPanel.optIn.column"), "optin",
		"optin", null, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "optIn_col";
	    }
	});
	columns.add(new FormatPropertyColumn<Line, BigDecimal>(new ResourceModel("carrierPanel.tradeInValue.column"),
		"device.tradeInValue", "device.tradeInValue", moneyFmt, na) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "tradeInVal_col";
	    }
	});
	columns.add(new AbstractColumn<Line>(new ResourceModel("carrierPanel.gspStatus.column"), "mobileNumber") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Line row = (Line) rowModel.getObject();
		cellItem.add(new CarrierInfoPanel.GspStatusPanel(componentId, rowModel, row,
			getString("carrierPanel.gspStatus.noGsp.name.label"),
			getString("carrierPanel.gspStatus.noGsp.message.label"), na));
	    }

	    @Override
	    public Component getHeader(String componentId) {
		Label label = new Label(componentId, getDisplayModel());
		label.setEscapeModelStrings(false);
		return label;
	    }

	    @Override
	    public String getCssClass() {
		return "gspStatus_col";
	    }
	});
	columns.add(new AbstractColumn<Line>(null, "actions") {

	    private static final long serialVersionUID = 1L;

	    @SuppressWarnings("rawtypes")
	    @Override
	    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
		Line row = (Line) rowModel.getObject();
		cellItem.add(new CarrierInfoPanel.ActionsPanel(componentId, rowModel, row));
	    }

	    @Override
	    public String getCssClass() {
		return "actions_col";
	    }
	});
	// cannot use Ajax table cause need refresh to make tool tips work
	// properly
	final DefaultDataTable<Line> carrierDataTable = new DefaultDataTable<Line>("carrierDataTable", columns,
		provider, 20);
	carrierDataTable.setMarkupId("carrierDataTable");
	carrierDataTable.setOutputMarkupId(true);
	carrierInfoOptinForm.add(carrierDataTable);

	final WebMarkupContainer linesAvailable = new WebMarkupContainer("linesAvailable") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return numlinesAvailable != null;
	    }

	};
	linesAvailable.setOutputMarkupPlaceholderTag(true);
	carrierInfoOptinForm.add(linesAvailable);

	final Label linesAvailableLabel = new Label("linesAvailableLabel", new Model<String>(numlinesAvailable));
	linesAvailableLabel.setOutputMarkupPlaceholderTag(true);
	carrierInfoOptinForm.add(linesAvailableLabel);
	linesAvailable.add(linesAvailableLabel);

	final Label upgradeCheckerStoreCountLabel = new Label("upgradeCheckerStoreCount", new PropertyModel<Integer>(
		session, "carrierStore.upgradeCheckCount"));
	upgradeCheckerStoreCountLabel.setOutputMarkupPlaceholderTag(true);
	add(upgradeCheckerStoreCountLabel);

	final Label optInStoreCountLabel = new Label("optInStoreCount", new PropertyModel<Integer>(session,
		"carrierStore.optInCount"));
	optInStoreCountLabel.setOutputMarkupPlaceholderTag(true);
	add(optInStoreCountLabel);

	final FeedbackMessageFilter filter = new FeedbackMessageFilter("CarrierInfo");
	filter.setAcceptAllUnspecifiedComponents(false);
	filter.addFilterInComponent(carrierInfoOptinForm);

	final FeedbackPanel carrierInfoFeedbackPanel = new FeedbackPanel("carrierInfoFeedback", filter);
	carrierInfoFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(carrierInfoFeedbackPanel);

	add(carrierInfoOptinForm);

	BookmarkablePageLink<Void> featuresLink = new BookmarkablePageLink<Void>("featuresLink",
		PlanInformationAndFeaturesPage.class);
	carrierInfoOptinForm.add(featuresLink);

	// Hide the features button if there is no data. This is true when doing
	// a UCS-only search.
	if (!customerHasPlanOrFeatureData(getDailyRhythmPortalSession().getCustomer())){
	    featuresLink.setVisible(false);
	}

	// carrier panel header
	StringBuilder carrierStyleClass = new StringBuilder();
	carrierStyleClass.append("carrier-logo");
	if (session.getCustomer() != null && session.getCustomer().getSubscription() != null
		&& session.getCustomer().getSubscription().getCarrier() != null){
	    Carrier carrier = session.getCustomer().getSubscription().getCarrier();
	    carrierStyleClass.append(" ");
	    carrierStyleClass.append(carrier.getStyleClass());
	}
	final String carrierCssClass = carrierStyleClass.toString();
	TransparentWebMarkupContainer carrierLogo = new TransparentWebMarkupContainer("carrierLogo");
	carrierLogo.add(new Behavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onComponentTag(Component component, ComponentTag tag) {
		tag.put("class", carrierCssClass);
	    }
	});
	carrierInfoOptinForm.add(carrierLogo);

	carrierInfoOptinForm.add(new Label("customerNameLabel", new FormatModel<String>(session.getCustomer()
		.getNameString(), na)));
	Subscription subscr = session.getCustomer().getSubscription();

	String email = null, acct = null;
	if (subscr != null){
	    email = subscr.getPrimAcctEmailId();
	    acct = subscr.getAccountNumber();
	    session.getCustomer().setCarrier(subscr.getCarrier());
	    session.getCustomer().setAcctNumber(acct);
	    session.getCustomer().setEmail(email);
	}

	session.getCustomer().setTransferFlag(false);

	// FIXME
	try{
	    if (!session.getCustomer().isSimpleUpgradeFlag() && subscr != null){
		customerService.createCustomer(session.getCustomer(), session.getDrpUser());
		logger.debug("Transfer customer DSK: " + session.getCustomer().getDataSharingKey());
		logger.debug("Transfer customer Transaction ID: " + session.getCustomer().getCapTransactionId());
		session.getSearchCustomer().setDataSharingKey(session.getCustomer().getDataSharingKey());
		logger.debug("Transfer customer : " + session.getCustomer().getDataSharingKey());
		session.getCustomer().setTransferFlag(true);
	    }else{
		session.getCustomer().setTransferFlag(false);
		logger.debug("Carrier account : >> Skipped autosaving function for DSL");
	    }
	}catch(ServiceException ex){
	    session.getCustomer().setTransferFlag(false);
	    logger.error("Error while Transferring Customer", ex);
	}

	final WebMarkupContainer header = new WebMarkupContainer("header");
	header.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (session.getCustomer().isTransferFlag()){
		    return "";
		}
		return "notransfer";
	    }
	}));
	add(header);

	header.add(new Image("transfer-to-beast", new ContextRelativeResource("/css/img/transfer-to-beast.png")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onConfigure() {
		super.onConfigure();
		setVisibilityAllowed(session.getCustomer().isTransferFlag());
	    }
	});

	carrierInfoOptinForm.add(new Label("customerPhoneNumberLabel", new FormatModel<String>(session.getCustomer()
		.getContactPhone(), new PhoneFormatter<String>(), na)));
	carrierInfoOptinForm.add(new Label("customerAcctNumberLabel", new FormatModel<String>(acct, na)));

	// means by which JS can callback into Java
	wicketBehaviorCIP = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id")
			.toOptionalString();
		logger.debug("wicketBehaviorCIP id=" + id);

		if (id != null && id.startsWith("actionsSelect")){
		    String lineAction = id.substring("actionsSelect".length());
		    int idx = lineAction.indexOf("-");
		    if (idx < 0){
			logger.warn("cannot determine line ID or action from " + id);
			return;
		    }
		    String action = lineAction.substring(idx + 1);
		    if (action == null){
			logger.warn("cannot determine line ID or action from " + id);
			return;
		    }
		    long selectedLineId = Util.getLong(lineAction.substring(0, idx), -1L);
		    if (selectedLineId < 0L){
			logger.warn("cannot determine line ID or action from " + id);
			return;
		    }
		    Line selectedLine = null;
		    for(Line line: provider.getLineDataList()){
			if (line.getId() != null && line.getId().longValue() == selectedLineId){
			    selectedLine = line;
			    break;
			}
		    }
		    if (selectedLine == null){
			logger.warn("cannot find selected line from " + id);
			return;
		    }
		    session.setSelectedLine((Line) Util.clone(selectedLine));

		    // Opt-In Action
		    if ("O".equals(action)){
			logger.debug("doing Opt-In");
			session.setWorkflowAction(DailyRhythmPortalSession.WorkflowAction.OptIn);
			setResponsePage(OptInSelectionPage.class);

			// Triage Action
		    }else if ("T".equals(action)){
			logger.debug("doing Triage");
			if (session.getSelectedLine().getDevice() != null
				&& session.getSelectedLine().getDevice().getProtectionPlan() != null
				&& (session.getSelectedLine().getDevice().getProtectionPlan().getPlanStatus() == null || session
					.getSelectedLine().getDevice().getProtectionPlan().getPlanStatus()
					.equalsIgnoreCase(ServicePlan.PLAN_STATUS_UNKNOWN))){
			    session.getSelectedLine().getDevice().setProtectionPlan(null);
			}
			session.setWorkflowAction(DailyRhythmPortalSession.WorkflowAction.Triage);
			setResponsePage(IssuesPage.class);

			// Return Action
		    }else if ("R".equals(action)){
			logger.debug("doing Return");
			session.setWorkflowAction(DailyRhythmPortalSession.WorkflowAction.Return);
			setResponsePage(EntitlementSearchPage.class);

		    }else{
			logger.warn("action not handled: " + action);
		    }
		}
	    }
	};
	add(wicketBehaviorCIP);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("setupCarrierTable();");
		onDomReadyJS.append("setupCarrierPanel();");
		onDomReadyJS.append("wicketBehaviorCIP = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehaviorCIP.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

    @Override
    public boolean isVisible() {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	return session.getCustomer() != null && session.getCustomer().getSubscription() != null
		&& session.getCustomer().getSubscription().getLines() != null
		&& !session.getCustomer().getSubscription().getLines().isEmpty();
    }

    class PhoneNumberPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public PhoneNumberPanel(String id, final IModel<?> model, final Line row,
		final PhoneFormatter<String> phoneFmt, final String notAvailableLabel) {
	    super(id, model);
	    StringBuilder sb = new StringBuilder();
	    if (row.getMobileNumber() != null){
		sb.append(phoneFmt.format(row.getMobileNumber()));
	    }else{
		sb.append(notAvailableLabel);
	    }
	    sb.append("<br/>");
	    if (row.getCarrierPlans() != null && !row.getCarrierPlans().isEmpty()
		    && row.getCarrierPlans().get(0) != null && row.getCarrierPlans().get(0).getPlanType() != null){
		sb.append(CarrierPlan.returnPlanTypeDisplayValue(row.getCarrierPlans().get(0).getPlanType()));
	    }else{
		sb.append(notAvailableLabel);
	    }
	    Label phoneNumber = new Label("phoneNumber", sb.toString());
	    phoneNumber.setEscapeModelStrings(false);
	    add(phoneNumber);
	}
    }

    class EligibilityPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public EligibilityPanel(String id, final IModel<?> model, final Line row, final String eligibleLabel,
		final String notEligibleLabel, final Date now) {
	    super(id, model);

	    logger.trace("line=" + row);
	    // Standard Date
	    Date date = null;
	    boolean eligible = false;
	    if (row != null){
		date = row.getStdEligibilityDate();
		eligible = row.isStandardEligible();
	    }
	    Label stdEligibilityDate = new Label("stdEligibilityDate", generateEligibilityDateHtml(date, eligibleLabel,
		    notEligibleLabel, now, eligible, false));
	    stdEligibilityDate.setEscapeModelStrings(false);
	    add(stdEligibilityDate);
	    // Early Date
	    date = null;
	    eligible = false;
	    if (row != null){
		date = row.getEarlyEligibilityDate();
		eligible = row.isEarlyEligible();
	    }
	    Label earlyEligibilityDate = new Label("earlyEligibilityDate", generateEligibilityDateHtml(date,
		    eligibleLabel, notEligibleLabel, now, eligible, true));
	    earlyEligibilityDate.setEscapeModelStrings(false);
	    add(earlyEligibilityDate);
	}
    }

    class HardwareDescriptionSerialNumPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public HardwareDescriptionSerialNumPanel(String id, final IModel<?> model, final Line row,
		final String notAvailableLabel) {
	    super(id, model);

	    StringBuilder sb = new StringBuilder();
	    if (row.getDevice() != null && row.getDevice().getDescription() != null){
		sb.append(row.getDevice().getDescription());
	    }else{
		sb.append(notAvailableLabel);
	    }
	    sb.append("<br/>");
	    if (row.getDevice() != null && row.getDevice().getSerialNumber() != null){
		sb.append(row.getDevice().getSerialNumber());
	    }else{
		sb.append(notAvailableLabel);
	    }
	    Label hardwareDescriptionSerialNum = new Label("hardwareDescriptionSerialNum", sb.toString());
	    hardwareDescriptionSerialNum.setEscapeModelStrings(false);
	    add(hardwareDescriptionSerialNum);
	}
    }

    class GspStatusPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public GspStatusPanel(String id, final IModel<?> model, final Line row, final String noGspName,
		final String noGspMessage, final String notAvailableLabel) {
	    super(id, model);

	    boolean useNoGspMessage = true;
	    String styleClass = "black", planStatusValue = noGspName;
	    StringBuilder sb = new StringBuilder();

	    if (row.getDevice() == null || row.getDevice().getProtectionPlan() == null){
		// use default values
	    }else if (ServicePlan.PLAN_STATUS_ACTIVE.equalsIgnoreCase(row.getDevice().getProtectionPlan()
		    .getPlanStatus())){
		styleClass = "green";
		planStatusValue = statusFmt.format(row.getDevice().getProtectionPlan().getPlanStatus());
		useNoGspMessage = false;
	    }else if (ServicePlan.PLAN_STATUS_INACTIVE.equalsIgnoreCase(row.getDevice().getProtectionPlan()
		    .getPlanStatus())){
		styleClass = "red";
		planStatusValue = statusFmt.format(row.getDevice().getProtectionPlan().getPlanStatus());
		useNoGspMessage = false;
	    }else if (ServicePlan.PLAN_STATUS_ON_HOLD.equalsIgnoreCase(row.getDevice().getProtectionPlan()
		    .getPlanStatus())){
		styleClass = "orange";
		planStatusValue = statusFmt.format(row.getDevice().getProtectionPlan().getPlanStatus());
		useNoGspMessage = false;
	    }else if (ServicePlan.PLAN_STATUS_CANCELLED.equalsIgnoreCase(row.getDevice().getProtectionPlan()
		    .getPlanStatus())){
		styleClass = "red";
		planStatusValue = statusFmt.format(row.getDevice().getProtectionPlan().getPlanStatus());
		useNoGspMessage = false;
	    }
	    StringBuilder tt = new StringBuilder();
	    if (useNoGspMessage){
		tt.append(noGspMessage);
	    }else{
		tt.append("Plan #: ");
		if (row.getDevice().getProtectionPlan().getPlanNumber() == null){
		    tt.append(notAvailableLabel);
		}else{
		    tt.append(row.getDevice().getProtectionPlan().getPlanNumber());
		}
		tt.append("<br/>");
		tt.append("Expires: ");
		if (row.getDevice().getProtectionPlan().getPlanExpiryDate() == null){
		    tt.append(notAvailableLabel);
		}else{
		    tt.append(dateFmt.format(row.getDevice().getProtectionPlan().getPlanExpiryDate()));
		}
	    }
	    sb.append("<a class=\"toolTip\" title=\"");
	    sb.append(tt.toString());
	    sb.append("\">");
	    sb.append("<span class=");
	    sb.append(styleClass);
	    sb.append(">");
	    sb.append(planStatusValue);
	    sb.append("</span>");
	    sb.append("</a>");
	    Label gspStatus = new Label("gspStatus", sb.toString());
	    gspStatus.setEscapeModelStrings(false);
	    add(gspStatus);
	}
    }

    class ActionsPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ActionsPanel(String id, final IModel<?> model, final Line row) {
	    super(id, model);

	    List<SelectItem<String>> actions = actionSelectionsNoOptIn;
	    if (row.getOptin() != null && row.getOptinAllowed() != null && row.getOptinAllowed().booleanValue()){
		actions = actionSelections;
	    }
	    SelectItem<String> si = new SelectItem<String>("A", "Actions");
	    ChoiceRenderer<SelectItem<String>> choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	    DropDownChoice<SelectItem<String>> actionsDropDown = new DropDownChoice<SelectItem<String>>(
		    "actionsSelect", new Model<SelectItem<String>>(si), actions, choiceRenderer);
	    actionsDropDown.setMarkupId("actionsSelect" + row.getId());
	    actionsDropDown.setOutputMarkupPlaceholderTag(true);
	    add(actionsDropDown);
	}
    }

    private String generateEligibilityDateHtml(Date eligibilityDate, final String eligibleLabel,
	    final String notEligibleLabel, final Date currentDate, final boolean eligible, final boolean early) {
	String earlyLabel = "";
	if (early){
	    earlyLabel = " (early)";
	}
	StringBuilder sb = new StringBuilder();
	if (eligibilityDate == null){
	    if (eligible){
		sb.append("<span class=green>");
		sb.append(eligibleLabel);
		sb.append(earlyLabel);
		sb.append("</span>");
	    }else{
		sb.append(notEligibleLabel);
		sb.append(earlyLabel);
	    }
	}else{
	    String date = Util.toString(eligibilityDate, "MM/dd/yy");
	    String day = Util.toString(eligibilityDate, "EEE");
	    int daysBeforeEligible = Util.daysBetween(currentDate, eligibilityDate);

	    if (!eligible && (daysBeforeEligible > 0 && daysBeforeEligible <= 30)){
		sb.append("<a class=\"toolTip\" title=\"");
		sb.append("Eligible in <span class=tipnumdays>");
		sb.append(daysBeforeEligible);
		sb.append(" days</span> <br/> <span class=tipday>");
		sb.append(day);
		sb.append(", ");
		sb.append(date);
		sb.append("</span>");
		sb.append("\">");
		sb.append(date);
		sb.append(earlyLabel);
		sb.append("</a>");

	    }else if (!eligible){
		sb.append(date);
		sb.append(earlyLabel);
	    }else{
		sb.append("<span class=green>");
		sb.append(date);
		sb.append(earlyLabel);
		sb.append("</span>");
	    }
	}
	return sb.toString();
    }

    private static boolean customerHasPlanOrFeatureData(Customer customer) {
	if (customer == null || customer.getSubscription() == null || customer.getSubscription().getLines() == null){
	    return false;
	}
	for(Line line: customer.getSubscription().getLines()){
	    if ((line.getCarrierPlans() != null && line.getCarrierPlans().size() != 0)
		    || (line.getCarrierOptions() != null && line.getCarrierOptions().size() != 0)){
		return true;
	    }
	}
	return false;
    }

}
