package com.bestbuy.bbym.ise.drp.entitlement;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.customer.CustInfoModalPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardHeaderPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;
import com.bestbuy.bbym.ise.drp.domain.EntitlementPurchaseType;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlementRequest;
import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.DailyRhythmPortalService;
import com.bestbuy.bbym.ise.drp.service.ReturnExchangeService;
import com.bestbuy.bbym.ise.drp.service.RewardZoneService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatModel;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.PhoneFormatter;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class EntitlementLookupPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(EntitlementLookupPage.class);

    private boolean bbyCustomerInfo = false;

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    @SpringBean(name = "rewardZoneService")
    private RewardZoneService rewardZoneService;

    @SpringBean
    private DailyRhythmPortalService dailyRhythmPortalService;

    @SpringBean(name = "returnExchangeService")
    private ReturnExchangeService returnExchangeService;

    private final DateFormatter<Date> dateFmt = new DateFormatter<Date>();
    private final String na = getString("notApplicable.label");

    private PurchaseViewPanel purchaseViewPanel;
    private MobileDeviceViewPanel mobileDeviceViewPanel;
    private GSPViewPanel gspViewPanel;
    private RZViewPanel rzViewPanel;
    private PageSubmitButton pageSubmitButton;
    private Form<Object> entitlementForm;
    private PageContinueButton pageContinueButton;
    private PageBackButton pageBackButton;

    private CustInfoModalPanel custInfoModalPanel, rewardZoneLookupModal;

    public EntitlementLookupPage() {
	super(null);
    }

    private enum PageForwardState {
	PURCHASE_VIEW, MOBILE_VIEW, GSP_VIEW, RZ_VIEW, SUBMIT_VIEW
    }

    private enum PageBackState {
	PURCHASE_VIEW, MOBILE_VIEW, GSP_VIEW, RZ_VIEW, SUBMIT_VIEW
    }

    private enum PageDisplayModes {
	NO_DATA, VIEW_MODE, SEARCH_MODE
    }

    private PageForwardState pageForwardState = PageForwardState.GSP_VIEW;
    private PageBackState pageBackState = PageBackState.PURCHASE_VIEW;

    private boolean foundCustomerGSPData = false;
    private boolean foundCustomerRzData = false;

    private PageDisplayModes gspPageDisplayMode = PageDisplayModes.NO_DATA;
    private PageDisplayModes rzPageDisplayMode = PageDisplayModes.NO_DATA;

    private Customer selectedCustomer;
    private EntitlementLookup entitlement = new EntitlementLookup();
    private InvalidSerialModalPanel invalidSerialModalPanel;

    public EntitlementLookupPage(final EntitlementLookup entitlementLkup, Customer customer, final Page returnPage) {
	super(null);

	entitlement = (EntitlementLookup) Util.clone(entitlementLkup);
	this.selectedCustomer = customer;

	final FeedbackPanel feedbackPanel = new FeedbackPanel("entitlementLookupfeedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	Line line = entitlement.getLine();

	if (entitlement != null && line != null)
	    logger.debug("line.getMobileNumber()........." + line.getMobileNumber());
	entitlement.setDevicePhoneNumber(line.getMobileNumber());

	if (entitlement != null && line != null && line.getDevice() != null)
	    entitlement.setSerialNumber(line.getDevice().getSerialNumber());

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (session.getCustomer() != null && session.getCustomer().getSubscription() != null
		&& session.getCustomer().getSubscription().getCarrier() != null){
	    entitlement.setCarrier(session.getCustomer().getSubscription().getCarrier());
	}

	if (selectedCustomer == null && session.getBestBuyCustomer() != null){
	    bbyCustomerInfo = true;
	    entitlement.setCustomerFullName(session.getBestBuyCustomer().getFirstName() + " "
		    + session.getBestBuyCustomer().getLastName());
	}else if (selectedCustomer != null && selectedCustomer.getFirstName() != null
		&& selectedCustomer.getLastName() != null){
	    bbyCustomerInfo = true;
	    entitlement.setCustomerFullName(selectedCustomer.getFirstName() + " " + selectedCustomer.getLastName());
	}

	/** Customer Dashboard Header Panel to display the Header on the Page */
	CustomerDashboardHeaderPanel customerDashboardHeaderPanel = new CustomerDashboardHeaderPanel(
		"customerDashboardHeaderPanel", selectedCustomer) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return bbyCustomerInfo;
	    }

	};
	customerDashboardHeaderPanel.setOutputMarkupPlaceholderTag(true);
	add(customerDashboardHeaderPanel);

	entitlementForm = new Form<Object>("entitlementPurchaseViewForm");
	entitlementForm.setOutputMarkupPlaceholderTag(true);

	/** Purchase View Panel **/
	purchaseViewPanel = new PurchaseViewPanel("purchaseViewPanel", entitlement);
	purchaseViewPanel.setOutputMarkupPlaceholderTag(true);
	purchaseViewPanel.setVisible(true);

	/** Mobile Device View Panel **/
	mobileDeviceViewPanel = new MobileDeviceViewPanel("mobileDeviceViewPanel", entitlement);
	mobileDeviceViewPanel.setOutputMarkupPlaceholderTag(true);
	mobileDeviceViewPanel.setVisible(true);

	/** GSP Search View Panel **/
	gspViewPanel = new GSPViewPanel("gspViewPanel", entitlement);
	gspViewPanel.setOutputMarkupPlaceholderTag(true);
	gspViewPanel.setVisible(false);

	/** RZ Search View Panel **/

	rzViewPanel = new RZViewPanel("rzViewPanel", entitlement);
	rzViewPanel.setOutputMarkupPlaceholderTag(true);
	rzViewPanel.setVisible(false);

	/** Page Continue button.. **/
	pageContinueButton = new PageContinueButton(feedbackPanel);
	entitlementForm.add(pageContinueButton);

	/** Page Submit Button... **/
	pageSubmitButton = new PageSubmitButton(entitlement, feedbackPanel);
	entitlementForm.add(pageSubmitButton);
	pageSubmitButton.setVisible(false);

	/** Page Back Button **/
	pageBackButton = new PageBackButton(entitlement, feedbackPanel, returnPage);
	entitlementForm.add(pageBackButton);

	/** Page Canel Button... **/
	final PageCancelButton pageCancelButton = new PageCancelButton(entitlement, feedbackPanel);
	entitlementForm.add(pageCancelButton);

	entitlementForm.add(purchaseViewPanel);
	entitlementForm.add(mobileDeviceViewPanel);
	entitlementForm.add(gspViewPanel);
	entitlementForm.add(rzViewPanel);
	add(entitlementForm);

	custInfoModalPanel = new CustInfoModalPanel("custInfoModalPanel", CustInfoModalPanel.ModalState.SELECT_GSP) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("custInfoModalPanel onClose");
		feedbackPanel.setVisible(true);

		if (getSelectedProtectionPlan() != null && getSelectedProtectionPlan().getPlanNumber() != null){
		    logger.debug("selectedProtectionPlan=" + getSelectedProtectionPlan());
		    populateGSPDetailsToViewMode(getSelectedProtectionPlan());
		}

		target.add(feedbackPanel);
		target.add(entitlementForm);
	    }

	    @Override
	    public String checkSelectedProtectionPlan(ProtectionPlan servicePlan) {

		if (servicePlan != null){
		    if (ServicePlan.PLAN_STATUS_ACTIVE.equalsIgnoreCase(servicePlan.getPlanStatus())
			    || ServicePlan.PLAN_STATUS_INACTIVE.equalsIgnoreCase(servicePlan.getPlanStatus())){

			if ((servicePlan.getPlanExpiryDate() != null && servicePlan.getPlanExpiryDate().compareTo(
				new Date()) > 0)){

			    if (!findGspSkuMatch(entitlement.getProductSku(), servicePlan)){
				return getString("entitlement.gspview.unmatchedProdSku");
			    }else{
				return null;
			    }

			}else if ((servicePlan.getPlanExpiryDate() != null && servicePlan.getPlanExpiryDate()
				.compareTo(new Date()) < 0)){

			    return getString("entitlement.gspview.inactiveGSP");
			}

		    }else{
			return getString("entitlement.gspview.inactiveGSP");
		    }
		}else{
		    return getString("entitlement.gspview.inactiveGSP");
		}

		return null;
	    }

	};
	custInfoModalPanel.setVisible(false);
	custInfoModalPanel.setOutputMarkupPlaceholderTag(true);
	add(custInfoModalPanel);

	invalidSerialModalPanel = new InvalidSerialModalPanel("invalidSerialModalPanel", entitlement) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		feedbackPanel.setVisible(true);

		if (!isCancelCalled()){
		    entitlement.setManufacturerSerialNumber(getSerialNumber());
		}

		if (isContinueWithoutManufacturerSerial()){
		    entitlement.setContinueWithoutSerial(true);
		    entitlement.setManufacturerSerialNumber(null);
		}

		target.add(feedbackPanel);
		target.add(entitlementForm);

		if (!isCancelCalled()){
		    target.appendJavaScript("submitButtonClick();");
		}

	    }
	};
	invalidSerialModalPanel.setVisible(false);
	invalidSerialModalPanel.setOutputMarkupPlaceholderTag(true);
	add(invalidSerialModalPanel);

	rewardZoneLookupModal = new CustInfoModalPanel("rewardZoneLookupModal",
		CustInfoModalPanel.ModalState.SELECT_REWARD_ZONE) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("custInfoModalPanel onClose");
		feedbackPanel.setVisible(true);

		if (getSelectedRewardZone() != null){
		    populateRZDetailsToViewMode(getSelectedRewardZone());

		}

		target.add(feedbackPanel);
		target.add(entitlementForm);
	    }

	    @Override
	    public String checkRewardZone(RewardZone rewardZone) {
		if (rewardZone == null){
		    return getString("custInfo.rewardZone.invalidRzMember");
		}else if (RewardZone.ACCOUNT_STATUS_A.equalsIgnoreCase(rewardZone.getAccountStatus())){
		    return null;
		}else if (RewardZone.ACCOUNT_STATUS_P.equalsIgnoreCase(rewardZone.getAccountStatus())){
		    return getString("custInfo.rewardZone.pendingMember") + rewardZone.getMemberNumber();
		}
		return getString("custInfo.rewardZone.invalidRzMember");
	    }
	};
	add(rewardZoneLookupModal);

    }

    class PurchaseViewPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public PurchaseViewPanel(String id, final EntitlementLookup entitlement) {
	    super(id);

	    /******* Purchase Transaction View Page **************************/

	    final WebMarkupContainer purchaseTransViewContainer = new WebMarkupContainer("purchaseTransViewContainer");
	    purchaseTransViewContainer.setOutputMarkupPlaceholderTag(true);

	    final WebMarkupContainer orderNumBlock = new WebMarkupContainer("orderNumBlock");
	    orderNumBlock.setOutputMarkupPlaceholderTag(true);

	    final Label orderNumberLabel = new Label("orderNumberLabel", new PropertyModel<String>(entitlement,
		    "onlineOrderNumber"));
	    orderNumBlock.add(orderNumberLabel);

	    purchaseTransViewContainer.add(orderNumBlock);

	    final WebMarkupContainer fourParyKeyBlock = new WebMarkupContainer("fourParyKeyBlock");
	    fourParyKeyBlock.setOutputMarkupPlaceholderTag(true);
	    final Label storeLabel = new Label("storeLabel", new PropertyModel<String>(entitlement,
		    "fourpartkey.storeId"));

	    fourParyKeyBlock.add(storeLabel);

	    final Label registerLabel = new Label("registerLabel", new PropertyModel<String>(entitlement,
		    "fourpartkey.workStationId"));
	    fourParyKeyBlock.add(registerLabel);

	    final Label transLabel = new Label("transLabel", new PropertyModel<String>(entitlement,
		    "fourpartkey.registerTransactionNum"));
	    fourParyKeyBlock.add(transLabel);

	    final Label transDateLabel = new Label("transDateLabel", new FormatModel<Date>(
		    entitlement.getFourpartkey() != null?entitlement.getFourpartkey().getBusinessDate():null, dateFmt,
		    na));

	    fourParyKeyBlock.add(transDateLabel);

	    final Label skuLabel = new Label("skuLabel", new PropertyModel<String>(entitlement, "productSku"));
	    purchaseTransViewContainer.add(skuLabel);

	    final Label prodDescLabel = new Label("prodDescLabel", new PropertyModel<String>(entitlement,
		    "item.description"));
	    purchaseTransViewContainer.add(prodDescLabel);

	    purchaseTransViewContainer.add(fourParyKeyBlock);

	    if (entitlement.getEntitlementPurchaseType() == EntitlementPurchaseType.STORE){
		fourParyKeyBlock.setVisible(true);
		orderNumBlock.setVisible(false);
	    }else{
		orderNumBlock.setVisible(true);
		fourParyKeyBlock.setVisible(false);
	    }

	    this.add(purchaseTransViewContainer);

	    /******* Purchase Transaction View Page **************************/

	}
    }

    class MobileDeviceViewPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public MobileDeviceViewPanel(String id, final EntitlementLookup entitlement) {
	    super(id);

	    final WebMarkupContainer mobileDeviceSearchViewContainer = new WebMarkupContainer(
		    "mobileDeviceSearchViewContainer");
	    mobileDeviceSearchViewContainer.setOutputMarkupPlaceholderTag(true);

	    final WebMarkupContainer mobileSearchContainer = new WebMarkupContainer("mobileSearchContainer");
	    mobileSearchContainer.setOutputMarkupPlaceholderTag(true);

	    final WebMarkupContainer mobileViewContainer = new WebMarkupContainer("mobileViewContainer");
	    mobileViewContainer.setOutputMarkupPlaceholderTag(true);
	    mobileViewContainer.setVisible(false);

	    /** Begin Mobile Device Search Container **/
	    List<Carrier> carrLst = dailyRhythmPortalService.getSupportedCarriers();

	    PropertyModel<Carrier> carrierSelectModel = new PropertyModel<Carrier>(entitlement, "carrier");
	    ChoiceRenderer<Carrier> carrRenderer = new ChoiceRenderer<Carrier>();
	    DropDownChoice<Carrier> carriersDrpDwn = new DropDownChoice<Carrier>("carrierSelect", carrierSelectModel,
		    carrLst, carrRenderer);
	    carriersDrpDwn.setRequired(true);

	    PropertyModel<String> serialNumberModel = new PropertyModel<String>(entitlement, "serialNumber");
	    PropertyModel<String> manufacturerSerialNumberModel = new PropertyModel<String>(entitlement,
		    "manufacturerSerialNumber");
	    PropertyModel<EntitlementLookup.EntitlementDeviceStatusType> defectiveDeviceModel = new PropertyModel<EntitlementLookup.EntitlementDeviceStatusType>(
		    entitlement, "defectiveDevice");
	    ChoiceRenderer<EntitlementLookup.EntitlementDeviceStatusType> renderer = new ChoiceRenderer<EntitlementLookup.EntitlementDeviceStatusType>();
	    final DropDownChoice<EntitlementLookup.EntitlementDeviceStatusType> deviceStatusTypeBx = new DropDownChoice<EntitlementLookup.EntitlementDeviceStatusType>(
		    "deviceStatusType", defectiveDeviceModel, Arrays
			    .asList(EntitlementLookup.EntitlementDeviceStatusType.values()), renderer);
	    deviceStatusTypeBx.setRequired(true);

	    String devicePhoneNumberModel = "setup";
	    if (entitlement.getDevicePhoneNumber() != null){
		PhoneFormatter<String> formatter = new PhoneFormatter<String>();
		devicePhoneNumberModel = formatter.format(entitlement.getDevicePhoneNumber());
	    }

	    mobileSearchContainer.add(new Label("deviceReturnedLabel", devicePhoneNumberModel));
	    mobileSearchContainer.add(carriersDrpDwn);

	    TextField<String> imeiesnTxt = new TextField<String>("imeiesnTxt", serialNumberModel);
	    imeiesnTxt.setRequired(true);
	    mobileSearchContainer.add(imeiesnTxt);

	    final WebMarkupContainer serialNumContainer = new WebMarkupContainer("serialNumContainer");
	    serialNumContainer.setOutputMarkupPlaceholderTag(true);

	    TextField<String> manufacturerImeiTxt = new TextField<String>("manufacturerImeiTxt",
		    manufacturerSerialNumberModel);
	    serialNumContainer.add(manufacturerImeiTxt);
	    mobileSearchContainer.add(serialNumContainer);

	    if ("apple".equalsIgnoreCase(entitlement.getManufacturer())){
		/** Making the field mandatory based on the manufacturer type **/
		serialNumContainer.add(new Behavior() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public void onComponentTag(Component component, ComponentTag tag) {
			tag.put("class", "block required");
		    }
		});
		manufacturerImeiTxt.setRequired(true);

		manufacturerImeiTxt.add(new AjaxFormComponentUpdatingBehavior("onchange") {

		    private static final long serialVersionUID = 1L;

		    @Override
		    protected void onUpdate(AjaxRequestTarget arg0) {
			entitlement.setContinueWithoutSerial(false);

		    }
		});

	    }
	    mobileSearchContainer.add(deviceStatusTypeBx);

	    /** Begin Mobile Device View Container **/
	    mobileViewContainer.add(new Label("deviceReturnedViewLabel", devicePhoneNumberModel));
	    mobileViewContainer.add(new Label("carrierSelectedLabel", carrierSelectModel));
	    mobileViewContainer.add(new Label("imeiESNLabel", serialNumberModel));
	    mobileViewContainer.add(new Label("manufacturerSerialNumLabel", manufacturerSerialNumberModel));
	    mobileViewContainer.add(new Label("defectiveDeviceLabel", defectiveDeviceModel));

	    mobileDeviceSearchViewContainer.add(mobileSearchContainer);
	    mobileDeviceSearchViewContainer.add(mobileViewContainer);
	    this.add(mobileDeviceSearchViewContainer);

	}
    }

    class GSPViewPanel extends Panel {

	final String na = getString("notApplicable.label");

	private static final long serialVersionUID = 1L;
	final String unknown = getString("unknown.label");

	public GSPViewPanel(String id, final EntitlementLookup entitlement) {
	    super(id);

	    final WebMarkupContainer gspSearchViewContainer = new WebMarkupContainer("gspSearchViewContainer");
	    gspSearchViewContainer.setOutputMarkupPlaceholderTag(true);

	    final WebMarkupContainer gspSearchContainer = new WebMarkupContainer("gspSearchContainer");
	    gspSearchContainer.setOutputMarkupPlaceholderTag(true);

	    final TextField<String> gspNumTxt = new TextField<String>("gspNumTxt", new PropertyModel<String>(
		    entitlement, "gspNumber"));
	    gspNumTxt.setMarkupId("gspNumTxt");
	    gspNumTxt.setOutputMarkupId(true);
	    gspNumTxt.setOutputMarkupPlaceholderTag(true);
	    gspSearchContainer.add(gspNumTxt);
	    gspSearchViewContainer.add(gspSearchContainer);

	    final AjaxLink<Object> gspSearchLink = new AjaxLink<Object>("gspSearchLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    custInfoModalPanel.setVisible(true);
		    logger.debug("gspSearchLink onClick");

		    custInfoModalPanel.open(target);

		}
	    };
	    gspSearchLink.setMarkupId("purchaseTrasactionLink");
	    gspSearchLink.setOutputMarkupId(true);
	    gspSearchLink.setOutputMarkupPlaceholderTag(true);
	    gspSearchContainer.add(gspSearchLink);

	    final WebMarkupContainer gspViewContainer = new WebMarkupContainer("gspViewContainer");
	    gspViewContainer.setOutputMarkupPlaceholderTag(true);
	    gspViewContainer.setVisible(false);

	    final WebMarkupContainer gspViewDataContainer = new WebMarkupContainer("gspViewDataContainer");
	    gspViewDataContainer.setOutputMarkupPlaceholderTag(true);
	    gspViewDataContainer.setVisible(false);

	    gspViewContainer.add(gspViewDataContainer);

	    final WebMarkupContainer gspViewNoDataContainer = new WebMarkupContainer("gspViewNoDataContainer");
	    gspViewNoDataContainer.setOutputMarkupPlaceholderTag(true);
	    gspViewNoDataContainer.setVisible(false);
	    gspViewContainer.add(gspViewNoDataContainer);

	    gspViewNoDataContainer.add(new Label("gspNoDataLabel", new ResourceModel("entitlement.gspview.noGSPData")));

	    PropertyModel<String> gspNumberModel = new PropertyModel<String>(entitlement, "gspNumber");
	    PropertyModel<String> gspOwnerModel = new PropertyModel<String>(entitlement, "gspOwner");
	    PropertyModel<String> gspStatusModel = new PropertyModel<String>(entitlement, "gspStatus");

	    FormatPropertyModel<EntitlementLookup, Date> gspExpirationDateModel = new FormatPropertyModel<EntitlementLookup, Date>(
		    new PropertyModel<EntitlementLookup>(entitlement, "gspPlanExpiryDate"), new DateFormatter<Date>(),
		    na);

	    /*** Setting values to labels in View Mode ***/
	    final Label gspNumberViewLabel = new Label("gspNumberView", gspNumberModel);
	    gspViewDataContainer.add(gspNumberViewLabel);

	    final Label gspDescriptionViewLabel = new Label("gspDescriptionView", new Model<String>() {

		private static final long serialVersionUID = 1L;

		@Override
		public String getObject() {
		    if (entitlement != null && entitlement.getGspDescription() != null){
			return entitlement.getGspDescription();
		    }
		    return unknown;
		}

	    });

	    gspViewDataContainer.add(gspDescriptionViewLabel);

	    final Label gspOwnerViewLabel = new Label("gspOwnerView", gspOwnerModel);
	    gspViewDataContainer.add(gspOwnerViewLabel);

	    gspViewDataContainer.add(new Label("gspStatusView", gspStatusModel));
	    gspViewDataContainer.add(new Label("gspExpirationDateView", gspExpirationDateModel));

	    gspSearchViewContainer.add(gspViewContainer);

	    gspNumTxt.add(new AjaxFormComponentUpdatingBehavior("onchange") {

		private static final long serialVersionUID = 1L;

		@Override
		protected void onUpdate(AjaxRequestTarget target) {
		    String gspNum = gspNumTxt.getDefaultModelObjectAsString();
		    entitlement.setGspNumber(gspNum);
		    foundCustomerGSPData = false;
		}
	    });

	    this.add(gspSearchViewContainer);

	}
    }

    class RZViewPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public RZViewPanel(String id, final EntitlementLookup entitlement) {
	    super(id);

	    final WebMarkupContainer rzSearchViewContainer = new WebMarkupContainer("rzSearchViewContainer");
	    rzSearchViewContainer.setOutputMarkupPlaceholderTag(true);

	    final WebMarkupContainer rzSearchContainer = new WebMarkupContainer("rzSearchContainer");
	    rzSearchContainer.setOutputMarkupPlaceholderTag(true);
	    rzSearchContainer.setVisible(true);
	    rzSearchViewContainer.add(rzSearchContainer);

	    final WebMarkupContainer rzViewContainer = new WebMarkupContainer("rzViewContainer");
	    rzViewContainer.setOutputMarkupPlaceholderTag(true);
	    rzViewContainer.setVisible(false);
	    rzSearchViewContainer.add(rzViewContainer);

	    final WebMarkupContainer rzDataViewContainer = new WebMarkupContainer("rzDataViewContainer");
	    rzDataViewContainer.setOutputMarkupPlaceholderTag(true);
	    rzDataViewContainer.setVisible(false);
	    rzViewContainer.add(rzDataViewContainer);

	    final WebMarkupContainer rzNoDataViewContainer = new WebMarkupContainer("rzNoDataViewContainer");
	    rzNoDataViewContainer.setOutputMarkupPlaceholderTag(true);
	    rzNoDataViewContainer.setVisible(false);
	    rzViewContainer.add(rzNoDataViewContainer);

	    rzNoDataViewContainer.add(new Label("rzNoDataLabel", new ResourceModel(
		    "entitlement.rewardzone.noRZSelectedLabel")));

	    PropertyModel<String> rzNumModel = new PropertyModel<String>(entitlement, "rzNumber");
	    PropertyModel<String> rzTierModel = new PropertyModel<String>(entitlement, "rzTier");
	    PropertyModel<String> rzPointsModel = new PropertyModel<String>(entitlement, "rzPoints");
	    PropertyModel<String> rzFirstNameModel = new PropertyModel<String>(entitlement, "rzCustomerFirstName");
	    PropertyModel<String> rzLastNameModel = new PropertyModel<String>(entitlement, "rzCustomerLastName");

	    final Label rewardZoneTierLabel = new Label("rewardZoneTier", rzTierModel);
	    rzDataViewContainer.add(rewardZoneTierLabel);

	    /** Fields for View Mode **/
	    rzDataViewContainer.add(new Label("rzNumberLabel", rzNumModel));
	    rzDataViewContainer.add(new Label("rewardZonePoints", rzPointsModel));
	    rzDataViewContainer.add(new Label("rzFirstName", rzFirstNameModel));
	    rzDataViewContainer.add(new Label("rzLastName", rzLastNameModel));

	    final TextField<String> rzNumTxt = new TextField<String>("rzNumTxt", rzNumModel);
	    rzNumTxt.setMarkupId("rzNumTxt");
	    rzNumTxt.setOutputMarkupId(true);
	    rzNumTxt.setOutputMarkupPlaceholderTag(true);

	    rzNumTxt.add(new AjaxFormComponentUpdatingBehavior("onchange") {

		private static final long serialVersionUID = 1L;

		@Override
		protected void onUpdate(AjaxRequestTarget target) {
		    String rzNum = rzNumTxt.getDefaultModelObjectAsString();
		    entitlement.setRzNumber(rzNum);
		    foundCustomerRzData = false;
		}
	    });

	    rzSearchContainer.add(rzNumTxt);

	    this.add(rzSearchViewContainer);

	    final AjaxLink<Object> rzLookupLink = new AjaxLink<Object>("rzLookupLink") {

		private static final long serialVersionUID = 1L;

		@Override
		public void onClick(AjaxRequestTarget target) {
		    logger.info("rzLookupLink onClick");

		    rewardZoneLookupModal.open(target);

		}
	    };
	    rzLookupLink.setMarkupId("rzLookupLink");
	    rzLookupLink.setOutputMarkupId(true);
	    rzLookupLink.setOutputMarkupPlaceholderTag(true);
	    rzSearchContainer.add(rzLookupLink);
	}

    }

    private class PageContinueButton extends AjaxButton {

	private static final long serialVersionUID = 1L;
	private FeedbackPanel feedbackPanel;
	private boolean gspServiceLookupStatus = true;

	public PageContinueButton(FeedbackPanel feedbackPanel) {
	    super("purchaseViewContinueButton");
	    this.feedbackPanel = feedbackPanel;
	    setMarkupId("purchaseViewContinueButton");
	    setOutputMarkupPlaceholderTag(true);
	    setOutputMarkupId(true);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

	    logger.debug("currentForwardState............." + pageForwardState.name() + ".........back........."
		    + pageBackState.name());

	    if (pageForwardState == PageForwardState.GSP_VIEW){

		WebMarkupContainer mobileSearchViewContainer = (WebMarkupContainer) mobileDeviceViewPanel.get(0);
		mobileSearchViewContainer.get(0).setVisible(false);
		mobileSearchViewContainer.get(1).setVisible(true);

		gspViewPanel.setVisible(true);
		rzViewPanel.setVisible(false);

		pageForwardState = PageForwardState.RZ_VIEW;
		pageBackState = PageBackState.MOBILE_VIEW;

		/** Check to find if customer already has the GSP associated **/
		foundCustomerGSPData = valdiateCustomerGSPData(entitlement);

		logger.debug("foundCustomerGSPData.............." + foundCustomerGSPData);

		if (foundCustomerGSPData){

		    WebMarkupContainer gspSearchViewContainer = (WebMarkupContainer) gspViewPanel.get(0);
		    WebMarkupContainer gspViewContainer = (WebMarkupContainer) gspSearchViewContainer.get(1);
		    gspSearchViewContainer.get(0).setVisible(false);
		    gspSearchViewContainer.get(1).setVisible(true);
		    gspViewContainer.get(0).setVisible(true);
		    gspViewContainer.get(1).setVisible(false);
		    gspPageDisplayMode = PageDisplayModes.VIEW_MODE;
		    if (!entitlement.isLockGSPMode()){
			pageBackState = PageBackState.GSP_VIEW;
		    }else{
			pageBackState = PageBackState.MOBILE_VIEW;
		    }
		}else{
		    gspPageDisplayMode = PageDisplayModes.SEARCH_MODE;
		    WebMarkupContainer gspSearchViewContainer = (WebMarkupContainer) gspViewPanel.get(0);
		    gspSearchViewContainer.get(0).setVisible(true);
		    gspSearchViewContainer.get(1).setVisible(false);
		}

		target.appendJavaScript("$(\"#gspNumTxt\").focus();");

	    }else if (pageForwardState == PageForwardState.RZ_VIEW){

		if (!foundCustomerGSPData){
		    WebMarkupContainer gspSearchViewContainer = (WebMarkupContainer) gspViewPanel.get(0);
		    WebMarkupContainer gspViewContainer = (WebMarkupContainer) gspSearchViewContainer.get(1);
		    gspSearchViewContainer.get(0).setVisible(false);
		    gspSearchViewContainer.get(1).setVisible(true);
		    String gspNum = entitlement.getGspNumber();

		    gspPageDisplayMode = PageDisplayModes.SEARCH_MODE;
		    // Valdiate if GSP already present then populate the GSP
		    // Section in View Mode...

		    if (StringUtils.isEmpty(gspNum)){
			gspPageDisplayMode = PageDisplayModes.NO_DATA;
			entitlement.resetAllGSPData();
		    }else if (gspNum.length() < 4){
			error("Please enter a valid GSP Number");
			gspPageDisplayMode = PageDisplayModes.SEARCH_MODE;
		    }else{
			logger.debug("service call ..........." + gspNum);
			gspServiceLookupStatus = retrieveGSPServiceInfo(entitlement, true);

			if (gspServiceLookupStatus){
			    gspPageDisplayMode = PageDisplayModes.VIEW_MODE;
			}else{

			    target.add(feedbackPanel);
			}
		    }
		    switch (gspPageDisplayMode) {
			case NO_DATA:
			    gspViewContainer.get(0).setVisible(false);
			    gspViewContainer.get(1).setVisible(true);
			    break;
			case SEARCH_MODE:
			    gspSearchViewContainer.get(0).setVisible(true);
			    gspSearchViewContainer.get(1).setVisible(false);
			    break;
			case VIEW_MODE:
			    gspViewContainer.get(0).setVisible(true);
			    gspViewContainer.get(1).setVisible(false);
			    break;
		    }

		    target.appendJavaScript("$(\"#rzNumTxt\").focus();");
		    target.add(gspSearchViewContainer);

		}
		if (gspPageDisplayMode == PageDisplayModes.NO_DATA || gspPageDisplayMode == PageDisplayModes.VIEW_MODE){
		    rzViewPanel.setVisible(true);

		    pageForwardState = PageForwardState.SUBMIT_VIEW;
		    pageBackState = PageBackState.GSP_VIEW;
		}

		/**
		 * Check if the session has the RZ data and populate RZ in View
		 * MODE
		 **/
		foundCustomerRzData = populateRZCustomerData(entitlement);
		if (foundCustomerRzData){
		    WebMarkupContainer rzSearchViewContainer = (WebMarkupContainer) rzViewPanel.get(0);
		    WebMarkupContainer rzViewContainer = (WebMarkupContainer) rzSearchViewContainer.get(1);
		    rzSearchViewContainer.get(0).setVisible(false);
		    rzSearchViewContainer.get(1).setVisible(true);
		    rzViewContainer.get(0).setVisible(true);
		    rzViewContainer.get(1).setVisible(false);
		    rzPageDisplayMode = PageDisplayModes.VIEW_MODE;
		    // Enable submit button only if
		    logger.debug("gspServiceLookupStatus......." + gspServiceLookupStatus);
		    if (gspServiceLookupStatus || gspPageDisplayMode == PageDisplayModes.NO_DATA){
			this.setVisible(false);
			pageSubmitButton.setVisible(true);
			// Going Back to RZ Search Mode.......
			pageBackState = PageBackState.RZ_VIEW;
			pageForwardState = PageForwardState.SUBMIT_VIEW;
		    }
		}else{
		    WebMarkupContainer rzSearchViewContainer = (WebMarkupContainer) rzViewPanel.get(0);
		    rzSearchViewContainer.get(0).setVisible(true);
		    rzSearchViewContainer.get(1).setVisible(false);
		    rzPageDisplayMode = PageDisplayModes.SEARCH_MODE;
		}

	    }else if (pageForwardState == PageForwardState.SUBMIT_VIEW){

		// Make Reward Zone service call and handle the state....
		if (!foundCustomerRzData){
		    WebMarkupContainer rzSearchViewContainer = (WebMarkupContainer) rzViewPanel.get(0);
		    WebMarkupContainer rzViewContainer = (WebMarkupContainer) rzSearchViewContainer.get(1);
		    rzSearchViewContainer.get(0).setVisible(false);
		    rzSearchViewContainer.get(1).setVisible(true);

		    String rzNum = entitlement.getRzNumber();
		    rzPageDisplayMode = PageDisplayModes.SEARCH_MODE;

		    if (StringUtils.isEmpty(rzNum)){
			rzPageDisplayMode = PageDisplayModes.NO_DATA;
			entitlement.resetAllRzData();
		    }else{
			if (retrieveRewardZoneInfo(entitlement, true)){
			    rzPageDisplayMode = PageDisplayModes.VIEW_MODE;
			}else{
			    target.add(feedbackPanel);
			}
		    }
		    switch (rzPageDisplayMode) {
			case NO_DATA:
			    rzViewContainer.get(0).setVisible(false);
			    rzViewContainer.get(1).setVisible(true);
			    break;
			case SEARCH_MODE:
			    rzSearchViewContainer.get(0).setVisible(true);
			    rzSearchViewContainer.get(1).setVisible(false);
			    break;
			case VIEW_MODE:
			    rzViewContainer.get(0).setVisible(true);
			    rzViewContainer.get(1).setVisible(false);
			    break;
		    }
		    target.add(rzSearchViewContainer);

		}

		if (rzPageDisplayMode == PageDisplayModes.NO_DATA || rzPageDisplayMode == PageDisplayModes.VIEW_MODE){
		    this.setVisible(false);
		    pageSubmitButton.setVisible(true);
		    pageBackState = PageBackState.RZ_VIEW;

		}

	    }
	    target.add(feedbackPanel);
	    target.add(entitlementForm);
	    target.appendJavaScript("scrollTopOnFeedback();");

	}

	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator() {
	    return new IAjaxCallDecorator() {

		private static final long serialVersionUID = 1L;

		public CharSequence decorateScript(Component comp, CharSequence script) {
		    return "showButtonLoading(true, '#purchaseViewContinueButton');" + script;
		}

		public CharSequence decorateOnFailureScript(Component comp, CharSequence script) {
		    return "showButtonLoading(false, '#purchaseViewContinueButton');" + script;
		}

		public CharSequence decorateOnSuccessScript(Component comp, CharSequence script) {
		    return "showButtonLoading(false, '#purchaseViewContinueButton');" + script;
		}
	    };
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.error("continueButton..onError");
	    feedbackPanel.setVisible(true);
	    target.add(feedbackPanel);
	    target.add(entitlementForm);
	    target.appendJavaScript("scrollToTop();");

	}

    };

    private class PageBackButton extends AjaxButton {

	private static final long serialVersionUID = 1L;

	Page returnPage;
	FeedbackPanel feedbackPanel;

	public PageBackButton(EntitlementLookup entitlement, FeedbackPanel feedbackPanel, Page returnPage) {
	    super("backButton", new ResourceModel("entitlement.purchaseview.backButton"));
	    this.returnPage = returnPage;
	    this.feedbackPanel = feedbackPanel;
	    setOutputMarkupPlaceholderTag(true);
	    setDefaultFormProcessing(false);
	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("cancelButton onSubmit");

	    logger.debug("pageBackState........" + pageBackState.name());

	    // Page Submit should not be enabled till completion..
	    pageSubmitButton.setVisible(false);
	    pageContinueButton.setVisible(true);

	    if (pageBackState == PageBackState.MOBILE_VIEW){

		WebMarkupContainer mobileSearchViewContainer = (WebMarkupContainer) mobileDeviceViewPanel.get(0);
		mobileSearchViewContainer.get(0).setVisible(true);
		mobileSearchViewContainer.get(1).setVisible(false);

		gspViewPanel.setVisible(false);
		rzViewPanel.setVisible(false);

		pageForwardState = PageForwardState.GSP_VIEW;
		pageBackState = PageBackState.PURCHASE_VIEW;

	    }else if (pageBackState == PageBackState.GSP_VIEW){
		WebMarkupContainer gspSearchViewContainer = (WebMarkupContainer) gspViewPanel.get(0);

		if (entitlement.isLockGSPMode()){
		    gspSearchViewContainer.get(0).setVisible(false);
		    gspSearchViewContainer.get(1).setVisible(true);

		}else{
		    gspSearchViewContainer.get(0).setVisible(true);
		    gspSearchViewContainer.get(1).setVisible(false);
		    target.appendJavaScript("$(\"#gspNumTxt\").focus();");
		}

		gspViewPanel.setVisible(true);
		rzViewPanel.setVisible(false);

		foundCustomerGSPData = false;

		pageForwardState = PageForwardState.RZ_VIEW;
		pageBackState = PageBackState.MOBILE_VIEW;

	    }else if (pageBackState == PageBackState.RZ_VIEW){
		WebMarkupContainer rzSearchViewContainer = (WebMarkupContainer) rzViewPanel.get(0);
		rzSearchViewContainer.get(0).setVisible(true);
		rzSearchViewContainer.get(1).setVisible(false);
		// Focus the Text Fields.
		target.appendJavaScript("$(\"#rzNumTxt\").focus();");

		pageBackState = PageBackState.GSP_VIEW;
		pageForwardState = PageForwardState.SUBMIT_VIEW;

		foundCustomerRzData = false;

	    }else if (pageBackState == PageBackState.PURCHASE_VIEW){
		setResponsePage(returnPage);
	    }
	    target.add(entitlementForm);
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.error("Page Back onError");
	    target.add(feedbackPanel);
	    target.add(entitlementForm);
	}

    }

    /** Page Submit Button class... **/
    private class PageSubmitButton extends AjaxButton {

	private static final long serialVersionUID = 1L;

	private FeedbackPanel feedbackPanel;
	private EntitlementLookup entitlement;

	public PageSubmitButton(EntitlementLookup entitlement, FeedbackPanel feedbackPanel) {
	    super("entitlementLookupSubmitButton");
	    this.feedbackPanel = feedbackPanel;
	    this.entitlement = entitlement;
	    setOutputMarkupPlaceholderTag(true);
	    setMarkupId("entitlementLookupSubmitButton");
	    setOutputMarkupId(true);

	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

	    MobileEntitlementRequest mobileEntitlementRequest = new MobileEntitlementRequest();
	    mobileEntitlementRequest.setCarrier(entitlement.getCarrier());
	    mobileEntitlementRequest.setDefective(Boolean.parseBoolean(entitlement.getDefectiveDevice().name()
		    .toLowerCase()));
	    mobileEntitlementRequest.setHandsetId(entitlement.getSerialNumber());
	    mobileEntitlementRequest.setManufacturerSerialNumber(entitlement.getManufacturerSerialNumber());
	    if (StringUtils.isNotEmpty(entitlement.getGspNumber())){
		mobileEntitlementRequest.setPlanType(entitlement.getGspPlanType());
		mobileEntitlementRequest.setProtectionPlanId(entitlement.getGspNumber());
	    }
	    mobileEntitlementRequest.setRewardZoneTier(entitlement.getRzMemberTierCode());
	    mobileEntitlementRequest.setSku(entitlement.getProductSku());
	    mobileEntitlementRequest.setUpgradeEligible(entitlement.getLine().isStandardEligible());

	    if (entitlement.getItem() != null){
		mobileEntitlementRequest.setPurchaseDate(entitlement.getItem().getPurchaseDate());
		mobileEntitlementRequest.setSourceTransactionKey(entitlement.getItem().getSourceTransactionKey());
		mobileEntitlementRequest.setTransactionKeyType(entitlement.getItem().getTransactionKeyType());
		mobileEntitlementRequest.setTransactionSourceSystem(entitlement.getItem().getTransactionSourceSystem());
	    }
	    if (entitlement.isContinueWithoutSerial()){

		if (entitlement.getManufacturerSerialNumber() != null){
		    entitlement.setContinueWithoutSerial(false);
		}else{
		    entitlement.setContinueWithoutSerial(true);
		}

	    }
	    mobileEntitlementRequest.setContinueWithoutSerial(entitlement.isContinueWithoutSerial());
	    boolean submitNextPage = false;

	    try{
		MobileEntitlement mobileEntitlement = returnExchangeService.getMobileEntitlement(
			mobileEntitlementRequest, getDailyRhythmPortalSession().getDrpUser(), Util
				.generateTransactionId());

		if (mobileEntitlement != null && mobileEntitlement.getVendorWarranty() == null){
		    submitNextPage = true;
		}else if (mobileEntitlement.getVendorWarranty() != null
			&& mobileEntitlement.getVendorWarranty().isValidSerialNumber()
			|| entitlement.isContinueWithoutSerial()){
		    submitNextPage = true;
		}

		if (submitNextPage){
		    setResponsePage(new EntitlementSelectionPage(entitlement, mobileEntitlement, selectedCustomer,
			    getPage()));

		}else{
		    invalidSerialModalPanel.open(target);
		}

	    }catch(ServiceException se){
		error(se.getFullMessage());
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
	    }catch(IseBusinessException be){
		error(be.getFullMessage());
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
	    }catch(Exception e){
		error(e.getMessage());
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
	    }

	    target.add(entitlementForm);
	    target.appendJavaScript("scrollTopOnFeedback();");

	}

	@Override
	protected IAjaxCallDecorator getAjaxCallDecorator() {
	    return new IAjaxCallDecorator() {

		private static final long serialVersionUID = 1L;

		public CharSequence decorateScript(Component comp, CharSequence script) {
		    return "showButtonLoading(true, '#entitlementLookupSubmitButton');" + script;
		}

		public CharSequence decorateOnFailureScript(Component comp, CharSequence script) {
		    return "showButtonLoading(false, '#entitlementLookupSubmitButton');" + script;
		}

		public CharSequence decorateOnSuccessScript(Component comp, CharSequence script) {
		    return "showButtonLoading(false, '#entitlementLookupSubmitButton');" + script;
		}
	    };
	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.error("Page submit onError");
	    target.add(feedbackPanel);
	    target.appendJavaScript("scrollToTop();");

	}

    };

    private class PageCancelButton extends AjaxButton {

	private static final long serialVersionUID = 1L;

	FeedbackPanel feedbackPanel;

	public PageCancelButton(EntitlementLookup entitlement, FeedbackPanel feedbackPanel) {
	    super("cancelButton", new ResourceModel("entitlement.purchaseview.cancelButton"));
	    this.feedbackPanel = feedbackPanel;
	    setOutputMarkupPlaceholderTag(true);
	    setDefaultFormProcessing(false);

	}

	@Override
	protected void onError(AjaxRequestTarget target, Form<?> form) {
	    logger.error("Page Cancel onError");
	    target.add(feedbackPanel);
	    target.appendJavaScript("scrollToTop();");

	}

	@Override
	protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
	    logger.debug("cancelButton onSubmit");
	    setResponsePage(CustomerDashboardPage.class);
	}
    }

    private boolean findGspSkuMatch(String selectedSku, ProtectionPlan servicePlan) {
	boolean matchFound = false;
	if (servicePlan != null){
	    List<Product> gspProdList = servicePlan.getCoveredProductList();
	    for(Product product: gspProdList){
		if (product != null && product.getSku() != null && product.getSku().equalsIgnoreCase(selectedSku)){
		    matchFound = true;
		    break;
		}
	    }
	}
	return matchFound;
    }

    /**
     * @param searchByGspNumber
     *            {@code true} to search by GSP number; {@code false} to search
     *            by device serial number
     */
    private boolean retrieveGSPServiceInfo(EntitlementLookup entitlement, boolean searchByGspNumber) {
	boolean serviceCallSuccess = false;
	boolean showErrorMessages = false;
	entitlement.setLockGSPMode(false);

	try{

	    ProtectionPlan servicePlan = null;
	    if (searchByGspNumber){
		showErrorMessages = true;
		servicePlan = customerDataService.getProtectionPlan(entitlement.getGspNumber());
	    }else{
		servicePlan = customerDataService.findProtectionPlan(entitlement.getSerialNumber());
	    }

	    if (servicePlan != null){
		if (ServicePlan.PLAN_STATUS_ACTIVE.equalsIgnoreCase(servicePlan.getPlanStatus())
			|| ServicePlan.PLAN_STATUS_INACTIVE.equalsIgnoreCase(servicePlan.getPlanStatus())){

		    // Condition to check if active GSP expiration date is >
		    // current date
		    if ((servicePlan.getPlanExpiryDate() != null && servicePlan.getPlanExpiryDate().compareTo(
			    new Date()) > 0)){
			// Condition to check if the Product SKU matches the SKU
			// entered...
			if (findGspSkuMatch(entitlement.getProductSku(), servicePlan)){

			    serviceCallSuccess = true;
			    // one more conddition to add to check for matching
			    // sku and display description...

			    populateEntitlementWithGSPDetails(entitlement, servicePlan);

			}else{
			    error(getString("entitlement.gspview.unmatchedProdSku"));
			    entitlement.resetGSPValues();
			}
		    }else if ((servicePlan.getPlanExpiryDate() != null && servicePlan.getPlanExpiryDate().compareTo(
			    new Date()) < 0)){
			error(getString("entitlement.gspview.inactiveGSP"));
			entitlement.resetGSPValues();
		    }
		}else{
		    error(getString("entitlement.gspview.inactiveGSP"));
		    entitlement.resetGSPValues();
		}
	    }else{
		entitlement.resetGSPValues();
		throw new IseBusinessException(getString("entitlement.gspview.noSearchResult"));
	    }
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get GSP details for the customer";
	    logger.error(message, se);
	    entitlement.resetGSPValues();
	    if (showErrorMessages)
		error(se.getFullMessage());
	}catch(IseBusinessException be){
	    logger.error("IseBusinessException while getting the GSP details for the customer");
	    entitlement.resetGSPValues();
	    if (showErrorMessages)
		error(be.getFullMessage());
	}catch(Exception ex){
	    logger.error("Exception while getting the promotions for the customer", ex);
	    entitlement.resetGSPValues();
	    if (showErrorMessages)
		error(ex.getMessage());
	}

	return serviceCallSuccess;
    }

    private boolean retrieveRewardZoneInfo(EntitlementLookup entitlement, boolean showError) {
	boolean serviceCallSuccess = false;

	try{
	    RewardZone rewardZone = rewardZoneService.validateRewardZoneMember(entitlement.getRzNumber());

	    if (rewardZone != null){

		if (RewardZone.ACCOUNT_STATUS_A.equalsIgnoreCase(rewardZone.getAccountStatus())){
		    serviceCallSuccess = true;
		    entitlement.setRzTier(rewardZone.getMemberTier());
		    entitlement.setRzPoints(rewardZone.getPointsBalance());
		    entitlement.setRzCustomerFirstName(rewardZone.getFirstName());
		    entitlement.setRzCustomerLastName(rewardZone.getLastName());
		    entitlement.setRzMemberTierCode(rewardZone.getMemberTierCode());
		}else{
		    if (showError)
			error(getString("entitlement.rewardzone.invalidRzMember") + rewardZone.getMemberNumber());
		    entitlement.resetRzValues();
		    return false;
		}
	    }

	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get Reward Zone details for the customer";
	    logger.error(message, se);
	    entitlement.resetRzValues();
	    if (showError)
		error(se.getFullMessage());

	}catch(IseBusinessException be){
	    logger.error("IseBusinessException while getting the Reward Zone details for the customer");
	    entitlement.resetRzValues();
	    if (showError)
		error(be.getFullMessage());
	}catch(Exception ee){
	    logger.error("IseBusinessException while getting the Reward Zone details for the customer", ee);
	    entitlement.resetRzValues();
	    if (showError)
		error(ee.getMessage());
	}

	return serviceCallSuccess;

    }

    private boolean valdiateCustomerGSPData(EntitlementLookup entitlement) {
	Line line = entitlement.getLine();
	Device device = null;
	if (line != null){
	    device = entitlement.getLine().getDevice();
	}

	if (device != null){
	    if (entitlement.getSerialNumber() != null
		    && entitlement.getSerialNumber().equalsIgnoreCase(device.getSerialNumber())
		    && device.getProtectionPlan() != null && device.getProtectionPlan().getPlanNumber() != null){
		// Populate GSP with Existing info.....
		if (entitlement.getGspNumber() != null
			&& !entitlement.getGspNumber().equalsIgnoreCase(device.getProtectionPlan().getPlanNumber())){
		    return false;
		}else{
		    if (device.getProtectionPlan() != null
			    && ProtectionPlan.PLAN_STATUS_ACTIVE.equalsIgnoreCase(device.getProtectionPlan()
				    .getPlanStatus())){
			populateEntitlementWithGSPDetails(entitlement, device.getProtectionPlan());
			entitlement.setLockGSPMode(true);
			return true;
		    }else{
			return false;
		    }

		}
	    }else if (entitlement.getSerialNumber() != null
		    && !entitlement.getSerialNumber().equalsIgnoreCase(device.getSerialNumber())){
		// retrieve GSP if the IMEI is changes in the Enitlement Lookup
		// Screen...
		if (retrieveGSPServiceInfo(entitlement, false)){
		    entitlement.setLockGSPMode(true);
		    return true;
		}
	    }
	}
	return false;
    }

    private void populateGSPDetailsToViewMode(ProtectionPlan servicePlan) {

	gspViewPanel.setVisible(true);
	rzViewPanel.setVisible(false);
	pageForwardState = PageForwardState.RZ_VIEW;
	pageBackState = PageBackState.GSP_VIEW;

	/** Customer GSP has been captured in the GSP lookup so enabling to true **/
	foundCustomerGSPData = true;
	/** Populate EntitlementObject **/
	populateEntitlementWithGSPDetails(entitlement, servicePlan);

	if (foundCustomerGSPData){

	    WebMarkupContainer gspSearchViewContainer = (WebMarkupContainer) gspViewPanel.get(0);
	    WebMarkupContainer gspViewContainer = (WebMarkupContainer) gspSearchViewContainer.get(1);
	    gspSearchViewContainer.get(0).setVisible(false);
	    gspSearchViewContainer.get(1).setVisible(true);
	    gspViewContainer.get(0).setVisible(true);
	    gspViewContainer.get(1).setVisible(false);
	    gspPageDisplayMode = PageDisplayModes.VIEW_MODE;
	}

    }

    private void populateRZDetailsToViewMode(RewardZone selectedRzCustomer) {

	rzViewPanel.setVisible(true);
	pageForwardState = PageForwardState.SUBMIT_VIEW;
	pageBackState = PageBackState.RZ_VIEW;

	foundCustomerRzData = true;
	/** Populate EntitlementObject **/

	if (selectedRzCustomer != null){

	    entitlement.setRzNumber(selectedRzCustomer.getMemberNumber());
	    entitlement.setRzPoints((int) (selectedRzCustomer.getPointsBalance()));
	    entitlement.setRzTier(selectedRzCustomer.getMemberTier());
	    entitlement.setRzCustomerFirstName(selectedRzCustomer.getFirstName());
	    entitlement.setRzCustomerLastName(selectedRzCustomer.getLastName());

	    WebMarkupContainer rzSearchViewContainer = (WebMarkupContainer) rzViewPanel.get(0);
	    WebMarkupContainer rzViewContainer = (WebMarkupContainer) rzSearchViewContainer.get(1);
	    rzSearchViewContainer.get(0).setVisible(false);
	    rzSearchViewContainer.get(1).setVisible(true);
	    rzViewContainer.get(0).setVisible(true);
	    rzViewContainer.get(1).setVisible(false);
	    rzPageDisplayMode = PageDisplayModes.VIEW_MODE;

	    // Disable continue and Submit Buttons....
	    pageContinueButton.setVisible(false);
	    pageSubmitButton.setVisible(true);

	}

    }

    private boolean populateRZCustomerData(EntitlementLookup entitlement) {
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	if (selectedCustomer == null && session.getBestBuyCustomer() != null
		&& StringUtils.isNotEmpty(session.getBestBuyCustomer().getRewardZoneId())){
	    entitlement.setRzNumber(session.getBestBuyCustomer().getRewardZoneId());
	    return retrieveRewardZoneInfo(entitlement, false);

	}else if (selectedCustomer != null && selectedCustomer.getRewardZoneId() != null
		&& !selectedCustomer.getRewardZoneId().trim().isEmpty()){
	    entitlement.setRzNumber(selectedCustomer.getRewardZoneId());
	    return retrieveRewardZoneInfo(entitlement, false);

	}
	return false;
    }

    private void populateEntitlementWithGSPDetails(EntitlementLookup entitlement, ProtectionPlan servicePlan) {
	entitlement.setGspNumber(servicePlan.getPlanNumber());
	entitlement.setGspDescription(servicePlan.getDescription());
	entitlement.setGspOwner(servicePlan.getPlanOwnerName());
	entitlement.setGspPlanType(servicePlan.getPlanType());
	entitlement.setGspStatus(servicePlan.getPlanStatus());
	entitlement.setGspPlanExpiryDate(servicePlan.getPlanExpiryDate());
    }

}
