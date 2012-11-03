package com.bestbuy.bbym.ise.drp.loanerphone;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.ServicePlanTransaction;
import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.OkCancelDialogPanel;
import com.bestbuy.bbym.ise.drp.common.OkDialogPanel;
import com.bestbuy.bbym.ise.drp.dashboard.PrintReceiptResource;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInExtra;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneFulFillmentType;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.loanerphone.CustomModalWindow.CustomWindowClosedCallback;
import com.bestbuy.bbym.ise.drp.service.CustomerDataService;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.drp.util.DateFormatter;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class LoanerPhoneCheckInPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(LoanerPhoneCheckInPage.class);
    private static final long serialVersionUID = 1L;

    private String imeiSearch;
    private String condition;
    private String conditionComment;
    private CheckOutCheckInHistory cocih;
    private Phone checkInPhone;
    private boolean checkOutChargerStatus = false;
    private boolean checkOutBatteryStatus = false;
    private boolean checkOutAdapterStatus = false;
    private AjaxButton checkInButton;

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    @SpringBean(name = "customerDataService")
    private CustomerDataService customerDataService;

    private OkCancelDialogPanel printReceiptDialog;
    private OkDialogPanel errorDialog;
    private AbstractDefaultAjaxBehavior wicketBehavior;

    public LoanerPhoneCheckInPage(Phone phone, final Page returnPage, final Loadable returnLoadable) {
	super(null);

	final LoanerPhoneCheckInPage thisPage = this;

	checkInPhone = phone;
	logger.trace("checkInPhone=" + checkInPhone);

	if (checkInPhone == null){
	    checkInPhone = new Phone();
	    cocih = new CheckOutCheckInHistory();
	}else if (checkInPhone.getLatestCheckOutCheckInHistory() == null){
	    cocih = new CheckOutCheckInHistory();
	}else{
	    cocih = checkInPhone.getLatestCheckOutCheckInHistory();
	}

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final Form<Object> loanerPhoneCheckInForm = new Form<Object>("loanerPhoneCheckInForm");
	loanerPhoneCheckInForm.setOutputMarkupPlaceholderTag(true);
	add(loanerPhoneCheckInForm);

	final Label carrierLabel = new Label("carrierLabel", new PropertyModel<String>(checkInPhone, "carrier.carrier"));
	carrierLabel.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckInForm.add(carrierLabel);

	final Label osLabel = new Label("osLabel", new PropertyModel<String>(checkInPhone, "operatingSystem.os"));
	loanerPhoneCheckInForm.add(osLabel);

	final Label makeLabel = new Label("makeLabel", new PropertyModel<String>(checkInPhone, "make"));
	loanerPhoneCheckInForm.add(makeLabel);

	final Label modelLabel = new Label("modelLabel", new PropertyModel<String>(checkInPhone, "modelNumber"));
	loanerPhoneCheckInForm.add(modelLabel);

	final Label checkedOutLabel = new Label("checkedOutLabel",
		new FormatPropertyModel<CheckOutCheckInHistory, Date>(new PropertyModel<CheckOutCheckInHistory>(cocih,
			"checkOutTime"), new DateFormatter<Date>(), "N/A"));
	loanerPhoneCheckInForm.add(checkedOutLabel);

	final Label checkedOutByLabel = new Label("checkedOutByLabel", new PropertyModel<String>(cocih,
		"checkOutEmployee"));
	loanerPhoneCheckInForm.add(checkedOutByLabel);

	final WebMarkupContainer imeiSearchContainer = new WebMarkupContainer("imeiSearchContainer");
	imeiSearchContainer.setVisible(true);
	imeiSearchContainer.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckInForm.add(imeiSearchContainer);

	final TextField<String> imeiSearchField = new TextField<String>("imeiSearch", new PropertyModel<String>(this,
		"imeiSearch"));
	imeiSearchField.add(new PatternValidator("[0-9A-Za-z]+"));
	imeiSearchField.setOutputMarkupPlaceholderTag(true);
	imeiSearchContainer.add(imeiSearchField);
	imeiSearchField.setRequired(true);
	imeiSearchField.setMarkupId("imeiSearch");
	imeiSearchField.setOutputMarkupId(true);
	imeiSearchField.add(new DefaultFocusBehavior());

	final WebMarkupContainer imeiContainer = new WebMarkupContainer("imeiContainer");
	imeiContainer.setVisible(false);
	imeiContainer.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckInForm.add(imeiContainer);

	final Label imeiLabel = new Label("imeiLabel", new PropertyModel<String>(checkInPhone, "serialNumber"));
	imeiLabel.setOutputMarkupPlaceholderTag(true);
	imeiContainer.add(imeiLabel);

	final WebMarkupContainer customerContainer = new WebMarkupContainer("customerContainer");
	customerContainer.setOutputMarkupPlaceholderTag(true);
	customerContainer.setVisible(false);
	loanerPhoneCheckInForm.add(customerContainer);

	final Label firstName = new Label("firstName", new PropertyModel<String>(cocih, "firstName"));
	customerContainer.add(firstName);

	final Label lastName = new Label("lastName", new PropertyModel<String>(cocih, "lastName"));
	customerContainer.add(lastName);

	final Label contactNo = new Label("contactNo", new PropertyModel<String>(cocih, "contactPhone"));
	customerContainer.add(contactNo);

	final Label email = new Label("email", new PropertyModel<String>(cocih, "contactEmail"));
	customerContainer.add(email);

	final Label serviceOrderNo = new Label("serviceOrderNo", new PropertyModel<String>(cocih, "serviceOrderNo"));
	customerContainer.add(serviceOrderNo);

	final Label gspNo = new Label("gspNo", new PropertyModel<String>(cocih, "gspNo"));
	customerContainer.add(gspNo);

	final Label fulfillmentType = new Label("fulfillmentType", new PropertyModel<LoanerPhoneFulFillmentType>(cocih,
		"fulfillmentType"));
	customerContainer.add(fulfillmentType);

	final WebMarkupContainer continueButtonContainer = new WebMarkupContainer("continueButtonContainer");
	continueButtonContainer.setOutputMarkupPlaceholderTag(true);
	continueButtonContainer.setVisible(false);
	loanerPhoneCheckInForm.add(continueButtonContainer);

	final WebMarkupContainer checkInButtonContainer = new WebMarkupContainer("checkInButtonContainer");
	checkInButtonContainer.setOutputMarkupPlaceholderTag(true);
	checkInButtonContainer.setVisible(false);
	loanerPhoneCheckInForm.add(checkInButtonContainer);

	final WebMarkupContainer cancelButtonContainer = new WebMarkupContainer("cancelButtonContainer");
	cancelButtonContainer.setOutputMarkupPlaceholderTag(true);
	cancelButtonContainer.setVisible(true);
	loanerPhoneCheckInForm.add(cancelButtonContainer);

	final WebMarkupContainer closeButtonContainer = new WebMarkupContainer("closeButtonContainer");
	closeButtonContainer.setOutputMarkupPlaceholderTag(true);
	closeButtonContainer.setVisible(false);
	loanerPhoneCheckInForm.add(closeButtonContainer);

	final CustomModalWindow incorrectImeiModal = new CustomModalWindow("incorrectImeiModal");
	add(incorrectImeiModal);
	incorrectImeiModal.setContent(new IncorrectImeiModalPanel(incorrectImeiModal.getContentId(), null,
		LoanerPhoneCheckInPage.this.getPageReference(), incorrectImeiModal,
		getString("incorrectImeiModalForm.incorrectImeiCheckIn.label")));

	// GO button
	final AjaxButton goButton = new AjaxButton("goButton", new ResourceModel("loanerPhoneCheckedOutForm.go.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.info("in the go button");

		if (imeiSearch != null){
		    logger.debug("imeiSearch  is ---->>" + imeiSearch);
		    if (checkInPhone != null && checkInPhone.getSerialNumber() != null){
			if ((imeiSearch.length() == 4 && checkInPhone.getSerialNumber().endsWith(imeiSearch))
				|| checkInPhone.getSerialNumber().equalsIgnoreCase(imeiSearch)){
			    continueButtonContainer.setVisible(true);
			    customerContainer.setVisible(true);
			    imeiContainer.setVisible(true);
			    imeiSearchContainer.setVisible(false);
			}else{
			    incorrectImeiModal.show(target);
			}
		    }
		}

		target.add(customerContainer);
		target.add(continueButtonContainer);
		target.add(imeiSearchContainer);
		target.add(imeiContainer);
		loanerPhoneCheckInForm.setDefaultButton(null);
		target.add(loanerPhoneCheckInForm);
		target.appendJavaScript("scrollToBottom();setModalPanelFocus('#continueButton');");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("in go button onError...");
		target.add(feedbackPanel);
	    }

	};
	goButton.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckInForm.setDefaultButton(goButton);
	imeiSearchContainer.add(goButton);

	incorrectImeiModal.setWindowClosedCallback(new CustomWindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		loanerPhoneCheckInForm.setDefaultButton(goButton);
		target.add(loanerPhoneCheckInForm);
	    }

	});

	// lostButton
	final AjaxButton lostButton = new AjaxButton("lostButton", new ResourceModel(
		"loanerPhoneCheckInForm.lost.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("in the lost button");

		// set the condition to lost
		cocih.setCheckInCondition(LoanerPhoneCondition.LOST);

		continueButtonContainer.setVisible(true);
		customerContainer.setVisible(true);
		imeiContainer.setVisible(true);
		imeiSearchContainer.setVisible(false);

		target.add(customerContainer);
		target.add(continueButtonContainer);
		target.add(imeiSearchContainer);
		target.add(imeiContainer);
		target.appendJavaScript("scrollToBottom();");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	lostButton.setOutputMarkupPlaceholderTag(true);
	lostButton.setDefaultFormProcessing(false);
	imeiSearchContainer.add(lostButton);

	final WebMarkupContainer checkListContainer = new WebMarkupContainer("checkListContainer");
	checkListContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.setVisible(false);
	loanerPhoneCheckInForm.add(checkListContainer);

	final WebMarkupContainer phoneDataContainer = new WebMarkupContainer("phoneDataContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.LOST != cocih.getCheckInCondition();
	    }
	};
	phoneDataContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(phoneDataContainer);

	final CheckBox phoneDataCbx = new CheckBox("phoneData", new Model<Boolean>(false));
	phoneDataCbx.setRequired(true);
	phoneDataContainer.add(phoneDataCbx);
	phoneDataCbx.setMarkupId("phoneData");

	// Instructions popup
	final CustomModalWindow instructionsModal = new CustomModalWindow("instructionsModal");
	add(instructionsModal);

	// instructions link
	AjaxLink<Phone> instructionsLink = new AjaxLink<Phone>("instructionsLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.info("in instructionsLink on click....");
		String instructions = "";
		if (checkInPhone != null && checkInPhone.getOperatingSystem() != null){
		    instructions = checkInPhone.getOperatingSystem().getInstruction();
		}
		instructionsModal.setContent(new InstructionsModalPanel(instructionsModal.getContentId(), null,
			LoanerPhoneCheckInPage.this.getPageReference(), instructionsModal, instructions));
		instructionsModal.show(target);
	    }
	};
	instructionsLink.setMarkupId("instructionsLink");
	phoneDataContainer.add(instructionsLink);

	instructionsModal.setWindowClosedCallback(new CustomWindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		target.appendJavaScript("setModalPanelFocus('#instructionsLink')");
	    }

	});

	final RadioGroup<LoanerPhoneCondition> conditionRadioGroup = new RadioGroup<LoanerPhoneCondition>(
		"conditionRadioGroup", new PropertyModel<LoanerPhoneCondition>(cocih, "checkInCondition")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.LOST != cocih.getCheckInCondition();
	    }
	};
	Radio<LoanerPhoneCondition> conditionGoodRadio = new Radio<LoanerPhoneCondition>("conditionGoodRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.GOOD));
	Radio<LoanerPhoneCondition> conditionFairRadio = new Radio<LoanerPhoneCondition>("conditionFairRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.FAIR));
	Radio<LoanerPhoneCondition> conditionPoorRadio = new Radio<LoanerPhoneCondition>("conditionPoorRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.POOR));
	Radio<LoanerPhoneCondition> conditionDamagedRadio = new Radio<LoanerPhoneCondition>("conditionDamagedRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.DAMAGED));
	checkListContainer.add(conditionRadioGroup);
	conditionRadioGroup.add(conditionGoodRadio);
	conditionRadioGroup.add(conditionFairRadio);
	conditionRadioGroup.add(conditionPoorRadio);
	conditionRadioGroup.add(conditionDamagedRadio);
	conditionRadioGroup.setRequired(true);

	final WebMarkupContainer conditionCommentContainer = new WebMarkupContainer("conditionCommentContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.LOST != cocih.getCheckInCondition();
	    }
	};
	conditionCommentContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(conditionCommentContainer);

	final WebMarkupContainer conditionContainer = new WebMarkupContainer("conditionContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.LOST == cocih.getCheckInCondition();
	    }
	};
	conditionContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(conditionContainer);

	final Label conditionLabel = new Label("conditionLabel", new PropertyModel<String>(cocih, "checkInCondition"));
	conditionContainer.add(conditionLabel);

	TextArea<String> conditionCommentTextArea = new TextArea<String>("conditionCommentTextArea",
		new PropertyModel<String>(cocih, "checkInConditionComment"));
	conditionCommentTextArea.add(new MaximumLengthValidator(500));
	conditionCommentContainer.add(conditionCommentTextArea);

	final CheckBox chargerCbx = new CheckBox("charger", new Model<Boolean>(false));
	checkListContainer.add(chargerCbx);

	final CheckBox batteryCbx = new CheckBox("battery", new Model<Boolean>(false));
	checkListContainer.add(batteryCbx);

	final CheckBox adapterCbx = new CheckBox("adapter", new Model<Boolean>(false));
	checkListContainer.add(adapterCbx);

	final TextField<String> deposit = new RequiredTextField<String>("deposit", new PropertyModel<String>(cocih,
		"checkInDeposit"));
	checkListContainer.add(deposit);

	WebMarkupContainer checkOutChargerContainer = new WebMarkupContainer("checkOutChargerContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return checkOutChargerStatus;
	    }
	};
	checkOutChargerContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(checkOutChargerContainer);

	WebMarkupContainer checkOutBatteryContainer = new WebMarkupContainer("checkOutBatteryContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return checkOutBatteryStatus;
	    }
	};
	checkOutBatteryContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(checkOutBatteryContainer);

	WebMarkupContainer checkOutAdapterContainer = new WebMarkupContainer("checkOutAdapterContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return checkOutAdapterStatus;
	    }
	};
	checkOutAdapterContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(checkOutAdapterContainer);

	WebMarkupContainer displayNoneChargerContainer = new WebMarkupContainer("displayNoneChargerContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return !checkOutChargerStatus;
	    }
	};
	displayNoneChargerContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(displayNoneChargerContainer);

	WebMarkupContainer displayNoneBatteryContainer = new WebMarkupContainer("displayNoneBatteryContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return !checkOutBatteryStatus;
	    }
	};
	displayNoneBatteryContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(displayNoneBatteryContainer);

	WebMarkupContainer displayNoneAdapterContainer = new WebMarkupContainer("displayNoneAdapterContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return !checkOutAdapterStatus;
	    }
	};
	displayNoneAdapterContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(displayNoneAdapterContainer);

	final TextField<String> checkOutDeposit = new TextField<String>("checkOutDeposit",
		new FormatPropertyModel<String, BigDecimal>(new PropertyModel<String>(cocih, "checkOutDeposit"),
			new MoneyFormatter<BigDecimal>(false), "N/A"));

	checkOutDeposit.setEnabled(false);
	checkListContainer.add(checkOutDeposit);

	WebMarkupContainer damagePhoneWarning = new WebMarkupContainer("damagePhoneWarning") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.LOST != cocih.getCheckInCondition();
	    }
	};
	damagePhoneWarning.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(damagePhoneWarning);

	WebMarkupContainer lostPhoneWarning = new WebMarkupContainer("lostPhoneWarning") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return LoanerPhoneCondition.LOST == cocih.getCheckInCondition();
	    }
	};
	lostPhoneWarning.setOutputMarkupPlaceholderTag(true);
	checkListContainer.add(lostPhoneWarning);

	final WebMarkupContainer additionalTaskContainer = new WebMarkupContainer("additionalTaskContainer");
	additionalTaskContainer.setOutputMarkupPlaceholderTag(true);
	additionalTaskContainer.setVisible(false);
	loanerPhoneCheckInForm.add(additionalTaskContainer);

	//POS 4-Part Key additions.
	final WebMarkupContainer posContainer = new WebMarkupContainer("posContainer");
	posContainer.setOutputMarkupPlaceholderTag(true);
	posContainer.setOutputMarkupId(true);
	additionalTaskContainer.add(posContainer);
	Label posStoreLabel = new Label("posStoreLabel", new PropertyModel<String>(cocih, "posStoreId"));
	posContainer.add(posStoreLabel);

	Label posRegisterLabel = new Label("posRegisterLabel", new PropertyModel<Integer>(cocih, "registerId"));
	posContainer.add(posRegisterLabel);

	Label posTransNumberLabel = new Label("posTransNumberLabel", new PropertyModel<Integer>(cocih,
		"transactionNumber"));
	posContainer.add(posTransNumberLabel);

	@SuppressWarnings( {"unchecked", "rawtypes" })
	Label posTransDateLabel = new Label("posTransDateLabel", new FormatPropertyModel(new PropertyModel<Date>(cocih,
		"transactionDate"), new DateFormatter(), ""));
	posContainer.add(posTransDateLabel);

	if (check4PartKey(cocih))
	    posContainer.setVisible(true);
	else
	    posContainer.setVisible(false);
	//PRINT RECEIPT
	final PrintReceiptResource printReceiptResource = new PrintReceiptResource(this, customerDataService,
		PrintReceiptResource.BaseObjectType.SERVICE_PLAN_TRANSACTION);
	final ServicePlanTransaction servPlanTrans = new ServicePlanTransaction();
	servPlanTrans.setStoreNum(cocih.getPosStoreId());
	servPlanTrans.setRegisterNum(toString(cocih.getRegisterId()));
	servPlanTrans.setTransactionNum(toString(cocih.getTransactionNumber()));
	servPlanTrans.setPurchaseDate(cocih.getTransactionDate());
	printReceiptResource.setTransaction(servPlanTrans);
	printReceiptResource.setDrpUser(getDailyRhythmPortalSession().getDrpUser());

	printReceiptDialog = new OkCancelDialogPanel("printReceiptDialog", getString("yesLabel"), getString("noLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		if (isOk()){
		    logger.debug("transaction=" + servPlanTrans);
		    thisPage.openLoadingModal(getString("printTransactionReceipt.loading.label"), target);
		    target.appendJavaScript("doWicketBehavior('wicketBehavior()');");
		}

	    }

	};
	printReceiptDialog.setQuestion(getString("printTransactionReceipt.printReceipt.message.label"));
	printReceiptDialog.setOutputMarkupPlaceholderTag(true);
	add(printReceiptDialog);

	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehavior = function() { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("'); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String error = printReceiptResource.fetchData();
		thisPage.closeLoadingModal(target);
		if (error != null){
		    errorDialog.setMessage(error);
		    errorDialog.open(target);
		    return;
		}
		String uuidString = UUID.randomUUID().toString().replaceAll("-", "");
		ResourceReference ref = new ResourceReference(uuidString) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public IResource getResource() {
			return printReceiptResource;
		    }
		};
		if (ref.canBeRegistered()){
		    getApplication().getResourceReferenceRegistry().registerResourceReference(ref);
		    target.appendJavaScript("window.open('" + urlFor(ref, null) + "')");
		}

	    }
	};
	add(wicketBehavior);

	AjaxLink<Void> printReceiptButton = new AjaxLink<Void>("printReceiptButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		printReceiptDialog.open(target);
	    }
	};
	posContainer.add(printReceiptButton);

	// continueButton
	final AjaxButton continueButton = new AjaxButton("continueButton", new ResourceModel(
		"loanerPhoneCheckedOutForm.continue.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("in the continue button");
		logger.debug("continue button:cocih fulfillment type...." + cocih.getFulfillmentType());
		logger.debug("continue button:check out condition ...." + cocih.getCheckOutCondition());
		logger.debug("continue button:check in condition  ...." + cocih.getCheckInCondition());
		// set the check in condition, comment to check out condition,
		// comment by default
		if (LoanerPhoneCondition.LOST != cocih.getCheckInCondition()){
		    cocih.setCheckInCondition(cocih.getCheckOutCondition());
		}
		cocih.setCheckInConditionComment(cocih.getCheckOutConditionComment());

		List<CheckOutCheckInExtra> checkOutPhoneExtrasList = cocih.getPhoneExtraList();

		logger.debug("checkOutPhoneExtrasList ......" + checkOutPhoneExtrasList);

		if (checkOutPhoneExtrasList != null){
		    for(CheckOutCheckInExtra checkOutExtra: checkOutPhoneExtrasList){
			if (checkOutExtra != null
				&& getString("loanerPhoneCheckedOutForm.checkList.accessories.charger.label")
					.equalsIgnoreCase(checkOutExtra.getName())){
			    checkOutChargerStatus = checkOutExtra.isCheckOutStatus();
			}
			if (checkOutExtra != null
				&& getString("loanerPhoneCheckedOutForm.checkList.accessories.battery.label")
					.equalsIgnoreCase(checkOutExtra.getName())){
			    checkOutBatteryStatus = checkOutExtra.isCheckOutStatus();
			}
			if (checkOutExtra != null
				&& getString("loanerPhoneCheckedOutForm.checkList.accessories.adapter.label")
					.equalsIgnoreCase(checkOutExtra.getName())){
			    checkOutAdapterStatus = checkOutExtra.isCheckOutStatus();
			}

		    }
		}

		checkListContainer.setVisible(true);
		checkInButtonContainer.setVisible(true);
		continueButtonContainer.setVisible(false);
		phoneDataCbx.add(new DefaultFocusBehavior());
		target.add(checkListContainer);
		target.add(checkInButtonContainer);
		target.add(continueButtonContainer);
		loanerPhoneCheckInForm.setDefaultButton(checkInButton);
		target.add(loanerPhoneCheckInForm);
		target.appendJavaScript("scrollToBottom();");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("in continue button onError...");
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
	    }

	};
	continueButton.setOutputMarkupPlaceholderTag(true);
	continueButtonContainer.add(continueButton);
	continueButton.setMarkupId("continueButton");

	// checkOutButton
	checkInButton = new AjaxButton("checkInButton", new ResourceModel("loanerPhoneCheckInForm.checkIn.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("in the check in  button");
		logger.debug("condition...." + cocih.getCheckInCondition());
		logger.debug("condition comment...." + cocih.getCheckInConditionComment());
		logger.debug("phone data checkbox value..." + phoneDataCbx.getModelObject());
		logger.debug("charger...." + chargerCbx.getModelObject());
		logger.debug("battery....." + batteryCbx.getModelObject());
		logger.debug("adapter......." + adapterCbx.getModelObject());
		logger.debug("deposit......." + cocih.getCheckInDeposit());

		List<CheckOutCheckInExtra> phoneExtraList = cocih.getPhoneExtraList();
		DrpUser user = getDailyRhythmPortalSession().getDrpUser();

		if (phoneExtraList != null){
		    for(CheckOutCheckInExtra checkOutCheckInExtra: phoneExtraList){
			if (checkOutCheckInExtra != null
				&& getString("loanerPhoneCheckedOutForm.checkList.dataWipedClean.label")
					.equalsIgnoreCase(checkOutCheckInExtra.getName())){
			    checkOutCheckInExtra.setCheckInStatus(phoneDataCbx.getModelObject());
			    checkOutCheckInExtra.setModifiedBy(user.getUserId());
			    checkOutCheckInExtra.setModifiedOn(new Date());
			}
			if (checkOutCheckInExtra != null
				&& getString("loanerPhoneCheckedOutForm.checkList.accessories.charger.label")
					.equalsIgnoreCase(checkOutCheckInExtra.getName())){
			    checkOutCheckInExtra.setCheckInStatus(chargerCbx.getModelObject());
			    checkOutCheckInExtra.setModifiedBy(user.getUserId());
			    checkOutCheckInExtra.setModifiedOn(new Date());
			}
			if (checkOutCheckInExtra != null
				&& getString("loanerPhoneCheckedOutForm.checkList.accessories.battery.label")
					.equalsIgnoreCase(checkOutCheckInExtra.getName())){
			    checkOutCheckInExtra.setCheckInStatus(batteryCbx.getModelObject());
			    checkOutCheckInExtra.setModifiedBy(user.getUserId());
			    checkOutCheckInExtra.setModifiedOn(new Date());
			}
			if (checkOutCheckInExtra != null
				&& getString("loanerPhoneCheckedOutForm.checkList.accessories.adapter.label")
					.equalsIgnoreCase(checkOutCheckInExtra.getName())){
			    checkOutCheckInExtra.setCheckInStatus(adapterCbx.getModelObject());
			    checkOutCheckInExtra.setModifiedBy(user.getUserId());
			    checkOutCheckInExtra.setModifiedOn(new Date());
			}
		    }
		}

		checkInPhone.setCondition(cocih.getCheckInCondition());
		checkInPhone.setConditionComment(cocih.getCheckInConditionComment());
		checkInPhone.setLastActionUserId(user.getUserId());
		checkInPhone.setLastName(user.getLastName());
		checkInPhone.setFirstName(user.getFirstName());
		checkInPhone.setModifiedBy(user.getUserId());
		checkInPhone.setModifiedOn(new Date());

		cocih.setCheckedOut(false);
		cocih.setCheckInTime(new Date());
		cocih.setCheckInEmployee(user.getLastName());
		cocih.setPhoneExtraList(phoneExtraList);
		cocih.setPhone(checkInPhone);
		cocih.setModifiedBy(user.getUserId());
		cocih.setModifiedOn(new Date());

		logger.debug("check out record to be inserted in the checkincheckouthistory table...." + cocih);
		logger.debug("check out phone to be inserted........." + checkInPhone);
		// invoking the service
		try{
		    loanerPhoneService.checkInPhone(cocih);
		}catch(ServiceException se){
		    String message = "An unexpected exception occured while attempting to checking in the phone";
		    logger.error(message, se);
		    loanerPhoneCheckInForm.error(se.getFullMessage());
		    target.add(feedbackPanel);
		    return;
		}

		additionalTaskContainer.setVisible(true);
		checkInButtonContainer.setVisible(false);
		closeButtonContainer.setVisible(true);
		cancelButtonContainer.setVisible(false);

		target.add(additionalTaskContainer);
		target.add(checkInButtonContainer);
		target.add(closeButtonContainer);
		target.add(cancelButtonContainer);
		target.appendJavaScript("scrollToBottom();setModalPanelFocus('#closeButton');");

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("in checkin button onError...");
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
	    }

	};
	checkInButton.setOutputMarkupPlaceholderTag(true);
	checkInButtonContainer.add(checkInButton);

	// Cancel button
	AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel(
		"loanerPhoneCheckedOutForm.cancel.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("cancelButton onSubmit");
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(LoanerPhoneLandingPage.class);
		}
		if (returnLoadable != null){
		    returnLoadable.loadData();
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	cancelButton.setOutputMarkupPlaceholderTag(true);
	cancelButton.setDefaultFormProcessing(false);
	cancelButtonContainer.add(cancelButton);

	// Close button
	AjaxButton closeButton = new AjaxButton("closeButton", new ResourceModel(
		"loanerPhoneCheckedOutForm.close.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("closeButton onSubmit");
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(LoanerPhoneLandingPage.class);
		}
		if (returnLoadable != null){
		    returnLoadable.loadData();
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
	    }
	};
	closeButton.setMarkupId("closeButton");
	closeButton.setOutputMarkupId(true);
	closeButton.setOutputMarkupPlaceholderTag(true);
	closeButton.setDefaultFormProcessing(false);
	closeButtonContainer.add(closeButton);

	errorDialog = new OkDialogPanel("errorDialog", getString("okLabel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		//do nothing.
	    }
	};
	errorDialog.setOutputMarkupPlaceholderTag(true);
	add(errorDialog);
    }

    public String getImeiSearch() {
	return imeiSearch;
    }

    public void setImeiSearch(String imeiSearch) {
	this.imeiSearch = imeiSearch;
    }

    public String getCondition() {
	return condition;
    }

    public void setCondition(String condition) {
	this.condition = condition;
    }

    public String getConditionComment() {
	return conditionComment;
    }

    public void setConditionComment(String conditionComment) {
	this.conditionComment = conditionComment;
    }

    private static String toString(Object o) {
	if (o == null)
	    return null;

	return o.toString();
    }

    /**
     * @param cocih - The History object to grab key info from.
     * @return True if the key is all not null, false if any are null.
     */
    private static boolean check4PartKey(CheckOutCheckInHistory cocih) {
	Object[] vals = {cocih.getPosStoreId(), cocih.getRegisterId(), cocih.getTransactionDate(),
		cocih.getTransactionDate() };
	for(Object o: vals){
	    if (o == null)
		return false;
	}
	return true;
    }
}
