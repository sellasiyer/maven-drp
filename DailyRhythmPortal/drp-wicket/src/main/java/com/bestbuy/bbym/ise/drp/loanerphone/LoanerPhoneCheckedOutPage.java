package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
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
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.common.PdfByteArrayResource;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInExtra;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneFulFillmentType;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.helpers.PhoneSearchCriteria;
import com.bestbuy.bbym.ise.drp.loanerphone.CustomModalWindow.CustomWindowClosedCallback;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.drp.util.WicketUtils;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class LoanerPhoneCheckedOutPage extends NewBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(LoanerPhoneCheckedOutPage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    private AbstractDefaultAjaxBehavior wicketBehavior;
    private String imeiSearch;
    private String condition;
    private String conditionComment;
    private CheckOutCheckInHistory cocih = new CheckOutCheckInHistory();
    private Phone checkOutPhone;
    private AjaxButton goButton;
    private AjaxButton continueButton;
    private AjaxButton checkOutButton;

    // FIXME Hack for getting buttons to work properly
    private boolean refreshPage = true;

    public LoanerPhoneCheckedOutPage(Phone phone, final PhoneModel phoneModel, final Page returnPage,
	    final Loadable returnLoadable) {
	super(null);

	checkOutPhone = phone;

	if (checkOutPhone == null){
	    checkOutPhone = new Phone();
	}

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	final Form<Object> loanerPhoneCheckedOutForm = new Form<Object>("loanerPhoneCheckedOutForm");
	loanerPhoneCheckedOutForm.setOutputMarkupPlaceholderTag(true);
	add(loanerPhoneCheckedOutForm);

	final Label carrierLabel = new Label("carrierLabel", new PropertyModel<String>(phoneModel, "carrier.carrier"));
	carrierLabel.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckedOutForm.add(carrierLabel);

	final Label osLabel = new Label("osLabel", new PropertyModel<String>(phoneModel, "os.os"));
	osLabel.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckedOutForm.add(osLabel);

	final Label makeLabel = new Label("makeLabel", new PropertyModel<String>(phoneModel, "make"));
	makeLabel.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckedOutForm.add(makeLabel);

	final Label modelLabel = new Label("modelLabel", new PropertyModel<String>(phoneModel, "model"));
	modelLabel.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckedOutForm.add(modelLabel);

	final WebMarkupContainer imeiSearchContainer = new WebMarkupContainer("imeiSearchContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return(checkOutPhone.getId() == null);
	    }
	};
	imeiSearchContainer.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckedOutForm.add(imeiSearchContainer);

	final TextField<String> imeiSearchField = new RequiredTextField<String>("imeiSearch",
		new PropertyModel<String>(this, "imeiSearch"));
	imeiSearchField.add(new PatternValidator("[0-9A-Za-z]+"));
	imeiSearchContainer.add(imeiSearchField);
	imeiSearchField.setRequired(true);
	imeiSearchField.setMarkupId("imeiSearch");
	imeiSearchField.add(new DefaultFocusBehavior());
	imeiSearchField.setOutputMarkupId(true);

	final WebMarkupContainer imeiContainer = new WebMarkupContainer("imeiContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return(checkOutPhone.getId() != null);
	    }
	};
	imeiContainer.setOutputMarkupPlaceholderTag(true);
	loanerPhoneCheckedOutForm.add(imeiContainer);

	final Label imeiLabel = new Label("imeiLabel", new PropertyModel<String>(this, "imeiSearch"));
	imeiLabel.setOutputMarkupPlaceholderTag(true);
	imeiContainer.add(imeiLabel);

	final WebMarkupContainer customerContainer = new WebMarkupContainer("customerContainer");
	customerContainer.setOutputMarkupPlaceholderTag(true);
	customerContainer.setVisible(false);
	loanerPhoneCheckedOutForm.add(customerContainer);

	final TextField<String> firstName = new TextField<String>("firstName", new PropertyModel<String>(cocih,
		"firstName"));
	firstName.add(new PatternValidator("[A-Za-z -]{1,30}"));
	customerContainer.add(firstName);
	firstName.setMarkupId("firstName");

	final TextField<String> lastName = new RequiredTextField<String>("lastName", new PropertyModel<String>(cocih,
		"lastName"));
	lastName.add(new PatternValidator("[A-Za-z -]{1,30}"));
	customerContainer.add(lastName);

	final TextField<String> contactNo = new RequiredTextField<String>("contactNo", new PropertyModel<String>(cocih,
		"contactPhone"));
	contactNo.add(new PatternValidator("[0-9]+"));
	customerContainer.add(contactNo);

	final TextField<String> email = new TextField<String>("email", new PropertyModel<String>(cocih, "contactEmail"));
	email.add(new PatternValidator("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+[.][A-Za-z]{2,4}"));
	customerContainer.add(email);

	ChoiceRenderer<LoanerPhoneFulFillmentType> renderer = new ChoiceRenderer<LoanerPhoneFulFillmentType>();
	final DropDownChoice<LoanerPhoneFulFillmentType> fulfillmentType = new DropDownChoice<LoanerPhoneFulFillmentType>(
		"fulfillmentType", new PropertyModel<LoanerPhoneFulFillmentType>(cocih, "fulfillmentType"), Arrays
			.asList(LoanerPhoneFulFillmentType.values()), renderer);

	final TextField<String> gspNo = new TextField<String>("gspNo", new PropertyModel<String>(cocih, "gspNo")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isRequired() {
		if (fulfillmentType != null
			&& fulfillmentType.getModelObject() != null
			&& !(LoanerPhoneFulFillmentType.FACTORY_WARRANTY_REPAIR
				.equals(fulfillmentType.getModelObject()) || LoanerPhoneFulFillmentType.LOANER_PHONE_ONLY
				.equals(fulfillmentType.getModelObject()))){
		    logger.debug("GSP # needs to be entered for GSP repair and rapid exchange");
		    return true;
		}
		return false;
	    }

	};
	gspNo.setOutputMarkupPlaceholderTag(true);
	customerContainer.add(gspNo);

	final TextField<String> serviceOrderNo = new TextField<String>("serviceOrderNo", new PropertyModel<String>(
		cocih, "serviceOrderNo")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isRequired() {
		if (fulfillmentType != null && fulfillmentType.getModelObject() != null
			&& !LoanerPhoneFulFillmentType.LOANER_PHONE_ONLY.equals(fulfillmentType.getModelObject())){
		    logger
			    .debug("service order # needs to be entered for GSP repair and factory warranty and rapid exchange");
		    return true;
		}
		return false;
	    }

	};
	serviceOrderNo.setOutputMarkupPlaceholderTag(true);
	serviceOrderNo.add(new PatternValidator("[0-9]+"));
	customerContainer.add(serviceOrderNo);

	fulfillmentType.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		gspNo.add(new AttributeModifier("style", new Model<String>() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String getObject() {
			if (fulfillmentType != null
				&& fulfillmentType.getModelObject() != null
				&& (LoanerPhoneFulFillmentType.GSP_REPAIR.equals(fulfillmentType.getModelObject()) || LoanerPhoneFulFillmentType.RAPID_EXCHANGE_GSP
					.equals(fulfillmentType.getModelObject()))){
			    return "background-color:#fcfcbe;";
			}
			return "background-color:white;";
		    }
		}));
		serviceOrderNo.add(new AttributeModifier("style", new Model<String>() {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public String getObject() {
			if (fulfillmentType != null
				&& fulfillmentType.getModelObject() != null
				&& (LoanerPhoneFulFillmentType.GSP_REPAIR.equals(fulfillmentType.getModelObject())
					|| LoanerPhoneFulFillmentType.RAPID_EXCHANGE_GSP.equals(fulfillmentType
						.getModelObject()) || LoanerPhoneFulFillmentType.FACTORY_WARRANTY_REPAIR
					.equals(fulfillmentType.getModelObject()))){
			    return "background-color:#fcfcbe;";
			}
			return "background-color:white;";
		    }
		}));
		target.add(gspNo);
		target.add(serviceOrderNo);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, RuntimeException e) {
		logger.error("fulfillmentType onError", e);
	    }

	});
	fulfillmentType.setRequired(true);
	customerContainer.add(fulfillmentType);

	final WebMarkupContainer continueButtonContainer = new WebMarkupContainer("continueButtonContainer");
	continueButtonContainer.setOutputMarkupPlaceholderTag(true);
	continueButtonContainer.setVisible(false);
	loanerPhoneCheckedOutForm.add(continueButtonContainer);

	final WebMarkupContainer checkOutButtonContainer = new WebMarkupContainer("checkOutButtonContainer");
	checkOutButtonContainer.setOutputMarkupPlaceholderTag(true);
	checkOutButtonContainer.setVisible(false);
	loanerPhoneCheckedOutForm.add(checkOutButtonContainer);

	final WebMarkupContainer reprintButtonContainer = new WebMarkupContainer("reprintButtonContainer");
	reprintButtonContainer.setOutputMarkupPlaceholderTag(true);
	reprintButtonContainer.setVisible(false);
	loanerPhoneCheckedOutForm.add(reprintButtonContainer);

	final WebMarkupContainer cancelButtonContainer = new WebMarkupContainer("cancelButtonContainer");
	cancelButtonContainer.setOutputMarkupPlaceholderTag(true);
	cancelButtonContainer.setVisible(true);
	loanerPhoneCheckedOutForm.add(cancelButtonContainer);

	final WebMarkupContainer closeButtonContainer = new WebMarkupContainer("closeButtonContainer");
	closeButtonContainer.setOutputMarkupPlaceholderTag(true);
	closeButtonContainer.setVisible(false);
	loanerPhoneCheckedOutForm.add(closeButtonContainer);

	final PhoneDataProvider phoneDataProvider = new PhoneDataProvider(null);

	final CustomModalWindow checkedOutModal = new CustomModalWindow("checkedOutModal");
	checkedOutModal.setOutputMarkupPlaceholderTag(true);
	checkedOutModal.setContent(new CheckedOutModalPanel(checkedOutModal.getContentId(), null,
		LoanerPhoneCheckedOutPage.this.getPageReference(), checkedOutModal, phoneDataProvider, phoneModel,
		checkOutPhone));

	checkedOutModal.setWindowClosedCallback(new CustomWindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("checkedOutModal onClose");
		logger.debug("checkOutPhone=" + checkOutPhone);
		setImeiSearch(checkOutPhone.getSerialNumber());
		cocih.setCheckOutCondition(checkOutPhone.getCondition());
		cocih.setCheckOutConditionComment(checkOutPhone.getConditionComment());
		feedbackPanel.setVisible(true);
		target.add(feedbackPanel);
		target.add(imeiSearchContainer);
		target.add(imeiContainer);
		target.appendJavaScript("scrollToBottom();");
		loanerPhoneCheckedOutForm.setDefaultButton(goButton);
		target.add(loanerPhoneCheckedOutForm);
		if (checkOutPhone.getId() != null){
		    customerContainer.setVisible(true);
		    continueButtonContainer.setVisible(true);
		    firstName.add(new DefaultFocusBehavior());
		    target.add(customerContainer);
		    target.add(continueButtonContainer);
		    target.add(carrierLabel);
		    target.add(osLabel);
		    target.add(makeLabel);
		    target.add(modelLabel);
		    loanerPhoneCheckedOutForm.setDefaultButton(continueButton);
		    target.add(loanerPhoneCheckedOutForm);
		}
	    }
	});
	add(checkedOutModal);

	final CustomModalWindow incorrectImeiModal = new CustomModalWindow("incorrectImeiModal");
	add(incorrectImeiModal);
	incorrectImeiModal.setContent(new IncorrectImeiModalPanel(incorrectImeiModal.getContentId(), null,
		LoanerPhoneCheckedOutPage.this.getPageReference(), incorrectImeiModal,
		getString("incorrectImeiModalForm.incorrectImei.label")));

	incorrectImeiModal.setWindowClosedCallback(new CustomWindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		logger.debug("incorrectImeiModal onClose");
		loanerPhoneCheckedOutForm.setDefaultButton(goButton);
		target.add(loanerPhoneCheckedOutForm);
	    }
	});

	final DrpUser user = getDailyRhythmPortalSession().getDrpUser();

	goButton = new AjaxButton("goButton", new ResourceModel("loanerPhoneCheckedOutForm.go.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("goButton onSubmit");
		PhoneSearchCriteria criteria = new PhoneSearchCriteria();
		criteria.setCheckedOut(false);
		try{
		    if (imeiSearch != null){
			logger.debug("imeiSearch=" + imeiSearch);
			criteria.setLast4SerialNo(imeiSearch);
		    }
		    if (isDummyStoreEnabled()){
			phoneDataProvider.setPhoneList(loanerPhoneService.getPhones(getDummyStoreNum(), criteria));
			logger.debug("phoneList.size=" + phoneDataProvider.getPhoneList().size());
		    }else{
			phoneDataProvider.setPhoneList(loanerPhoneService.getPhones(user.getStoreId(), criteria));
		    }

		}catch(ServiceException se){
		    String message = "An unexpected exception occured while attempting to get the phone Models with the filter criteria";
		    logger.error(message, se);
		    processException(message, se);
		    return;
		}

		// unique match
		if (phoneDataProvider.getPhoneList() != null && phoneDataProvider.getPhoneList().size() == 1){
		    checkOutPhone = phoneDataProvider.getPhoneList().get(0);
		    logger.debug("checkOutPhone=" + checkOutPhone);
		    logger.debug("phoneModel=" + phoneModel);
		    setImeiSearch(checkOutPhone.getSerialNumber());
		    cocih.setCheckOutCondition(checkOutPhone.getCondition());
		    cocih.setCheckOutConditionComment(checkOutPhone.getConditionComment());
		    checkedOutModal.setContent(new CheckedOutModalPanel(checkedOutModal.getContentId(), null,
			    LoanerPhoneCheckedOutPage.this.getPageReference(), checkedOutModal, phoneDataProvider,
			    phoneModel, checkOutPhone));

		    if (checkOutPhone != null
			    && phoneModel != null
			    && checkOutPhone.getCarrier() != null
			    && phoneModel.getCarrier() != null
			    && checkOutPhone.getOperatingSystem() != null
			    && phoneModel.getOs() != null
			    && (!checkOutPhone.getCarrier().getCarrier().equalsIgnoreCase(
				    phoneModel.getCarrier().getCarrier())
				    || !checkOutPhone.getOperatingSystem().getOs().equalsIgnoreCase(
					    phoneModel.getOs().getOs())
				    || !checkOutPhone.getMake().equalsIgnoreCase(phoneModel.getMake()) || !checkOutPhone
				    .getModelNumber().equalsIgnoreCase(phoneModel.getModel()))){
			logger.debug("different available phone for the imei entered");
			logger.debug("checkOutPhone=" + checkOutPhone);
			feedbackPanel.setVisible(false);
			checkedOutModal.show(target);
			// make the customer container visible
		    }else{
			if (checkOutPhone != null && checkOutPhone.getCondition() != null
				&& LoanerPhoneCondition.DAMAGED == checkOutPhone.getCondition()
				|| LoanerPhoneCondition.LOST == checkOutPhone.getCondition()){
			    error(StringUtils.replace(getString("loanerPhoneCheckedOutForm.checkOut.error.label"),
				    "${condition}", checkOutPhone.getCondition().toString().toLowerCase()));
			    checkOutPhone.setId(null);
			    imeiSearch = "";
			    return;
			}
			continueButtonContainer.setVisible(true);
			customerContainer.setVisible(true);
		    }

		}else if (phoneDataProvider.getPhoneList() != null && phoneDataProvider.getPhoneList().size() > 1){
		    logger.debug("more than one match");
		    logger.debug("checkOutPhone=" + checkOutPhone);
		    feedbackPanel.setVisible(false);
		    checkedOutModal.show(target);
		}else if (phoneDataProvider.getPhoneList() != null && phoneDataProvider.getPhoneList().size() == 0){
		    logger.debug("no match");
		    // show no records pop up window
		    incorrectImeiModal.show(target);
		}

		firstName.add(new DefaultFocusBehavior());
		target.add(feedbackPanel);
		target.add(customerContainer);
		target.add(continueButtonContainer);
		target.add(imeiSearchContainer);
		target.add(imeiContainer);
		target.add(carrierLabel);
		target.add(osLabel);
		target.add(makeLabel);
		target.add(modelLabel);
		loanerPhoneCheckedOutForm.setDefaultButton(continueButton);
		target.add(loanerPhoneCheckedOutForm);
		target.appendJavaScript("scrollToBottom();");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("goButton onError");
		target.add(feedbackPanel);
	    }

	};
	goButton.setOutputMarkupPlaceholderTag(true);
	imeiSearchContainer.add(goButton);
	loanerPhoneCheckedOutForm.setDefaultButton(goButton);

	final WebMarkupContainer checkListContainer = new WebMarkupContainer("checkListContainer");
	checkListContainer.setOutputMarkupPlaceholderTag(true);
	checkListContainer.setVisible(false);
	loanerPhoneCheckedOutForm.add(checkListContainer);

	final CheckBox phoneDataCbx = new CheckBox("phoneData", new Model<Boolean>(false));
	phoneDataCbx.setRequired(true);
	checkListContainer.add(phoneDataCbx);
	phoneDataCbx.setMarkupId("phoneData");

	final CustomModalWindow instructionsModal = new CustomModalWindow("instructionsModal");
	add(instructionsModal);

	final AjaxLink<Phone> instructionsLink = new AjaxLink<Phone>("instructionsLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("instructionsLink onClick");
		String instructions = "";
		if (checkOutPhone != null && checkOutPhone.getOperatingSystem() != null){
		    instructions = checkOutPhone.getOperatingSystem().getInstruction();
		}
		instructionsModal.setContent(new InstructionsModalPanel(instructionsModal.getContentId(), null,
			LoanerPhoneCheckedOutPage.this.getPageReference(), instructionsModal, instructions));
		instructionsModal.show(target);
	    }
	};
	instructionsLink.setMarkupId("instructionsLink");
	checkListContainer.add(instructionsLink);

	instructionsModal.setWindowClosedCallback(new CustomWindowClosedCallback() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
		target.appendJavaScript("$('#instructionsLink').focus();");
	    }

	});

	final RadioGroup<LoanerPhoneCondition> conditionRadioGroup = new RadioGroup<LoanerPhoneCondition>(
		"conditionRadioGroup", new PropertyModel<LoanerPhoneCondition>(cocih, "checkOutCondition"));
	Radio<LoanerPhoneCondition> conditionGoodRadio = new Radio<LoanerPhoneCondition>("conditionGoodRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.GOOD));
	Radio<LoanerPhoneCondition> conditionFairRadio = new Radio<LoanerPhoneCondition>("conditionFairRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.FAIR));
	Radio<LoanerPhoneCondition> conditionPoorRadio = new Radio<LoanerPhoneCondition>("conditionPoorRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.POOR));
	checkListContainer.add(conditionRadioGroup);
	conditionRadioGroup.add(conditionGoodRadio);
	conditionRadioGroup.add(conditionFairRadio);
	conditionRadioGroup.add(conditionPoorRadio);
	conditionRadioGroup.setRequired(true);

	TextArea<String> conditionCommentTextArea = new TextArea<String>("conditionCommentTextArea",
		new PropertyModel<String>(cocih, "checkOutConditionComment"));
	conditionCommentTextArea.add(new MaximumLengthValidator(500));
	checkListContainer.add(conditionCommentTextArea);

	final CheckBox chargerCbx = new CheckBox("charger", new Model<Boolean>(false));
	checkListContainer.add(chargerCbx);

	final CheckBox batteryCbx = new CheckBox("battery", new Model<Boolean>(false));
	checkListContainer.add(batteryCbx);

	final CheckBox adapterCbx = new CheckBox("adapter", new Model<Boolean>(false));
	checkListContainer.add(adapterCbx);

	final TextField<String> deposit = new RequiredTextField<String>("deposit", new PropertyModel<String>(cocih,
		"checkOutDeposit"));
	checkListContainer.add(deposit);

	final WebMarkupContainer additionalTaskContainer = new WebMarkupContainer("additionalTaskContainer");
	additionalTaskContainer.setOutputMarkupPlaceholderTag(true);
	additionalTaskContainer.setVisible(false);
	loanerPhoneCheckedOutForm.add(additionalTaskContainer);

	continueButton = new AjaxButton("continueButton",
		new ResourceModel("loanerPhoneCheckedOutForm.continue.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("continueButton onSubmit");
		logger.debug("fulfillmentType=" + cocih.getFulfillmentType());
		logger.debug("checkOutCondition=" + cocih.getCheckOutCondition());
		logger.debug("checkOutConditionComment=" + cocih.getCheckOutConditionComment());
		checkListContainer.setVisible(true);
		checkOutButtonContainer.setVisible(true);
		continueButtonContainer.setVisible(false);
		loanerPhoneCheckedOutForm.setDefaultButton(checkOutButton);
		phoneDataCbx.add(new DefaultFocusBehavior());
		target.add(checkListContainer);
		target.add(checkOutButtonContainer);
		target.add(continueButtonContainer);
		target.add(feedbackPanel);
		target.add(loanerPhoneCheckedOutForm);
		target.appendJavaScript("scrollToBottom();");

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("continueButton onError");
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
	    }

	};
	continueButton.setOutputMarkupPlaceholderTag(true);
	continueButtonContainer.add(continueButton);

	final AjaxButton checkOutButton = new AjaxButton("checkOutButton", new ResourceModel(
		"loanerPhoneCheckedOutForm.checkOut.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("checkOutButton onSubmit");
		logger.debug("checkOutCondition=" + cocih.getCheckOutCondition());
		logger.debug("checkOutConditionComment=" + cocih.getCheckOutConditionComment());
		logger.debug("phoneData=" + phoneDataCbx.getModelObject());
		logger.debug("charger=" + chargerCbx.getModelObject());
		logger.debug("battery=" + batteryCbx.getModelObject());
		logger.debug("adapter=" + adapterCbx.getModelObject());
		logger.debug("deposit=" + cocih.getCheckOutDeposit());
		DrpUser user = getDailyRhythmPortalSession().getDrpUser();

		List<CheckOutCheckInExtra> phoneExtraList = new ArrayList<CheckOutCheckInExtra>();
		CheckOutCheckInExtra checkOutExtra = null;
		checkOutExtra = new CheckOutCheckInExtra();
		checkOutExtra.setName(getString("loanerPhoneCheckedOutForm.checkList.dataWipedClean.label"));
		checkOutExtra.setCheckOutStatus(phoneDataCbx.getModelObject());
		checkOutExtra.setCreatedBy(user.getUserId());
		checkOutExtra.setCreatedOn(new Date());
		phoneExtraList.add(checkOutExtra);

		checkOutExtra = new CheckOutCheckInExtra();
		checkOutExtra.setName(getString("loanerPhoneCheckedOutForm.checkList.accessories.charger.label"));
		checkOutExtra.setCheckOutStatus(chargerCbx.getModelObject());
		checkOutExtra.setCreatedBy(user.getUserId());
		checkOutExtra.setCreatedOn(new Date());
		phoneExtraList.add(checkOutExtra);

		checkOutExtra = new CheckOutCheckInExtra();
		checkOutExtra.setName(getString("loanerPhoneCheckedOutForm.checkList.accessories.battery.label"));
		checkOutExtra.setCheckOutStatus(batteryCbx.getModelObject());
		checkOutExtra.setCreatedBy(user.getUserId());
		checkOutExtra.setCreatedOn(new Date());
		phoneExtraList.add(checkOutExtra);

		checkOutExtra = new CheckOutCheckInExtra();
		checkOutExtra.setName(getString("loanerPhoneCheckedOutForm.checkList.accessories.adapter.label"));
		checkOutExtra.setCheckOutStatus(adapterCbx.getModelObject());
		checkOutExtra.setCreatedBy(user.getUserId());
		checkOutExtra.setCreatedOn(new Date());
		phoneExtraList.add(checkOutExtra);

		checkOutPhone.setCondition(cocih.getCheckOutCondition());
		checkOutPhone.setConditionComment(cocih.getCheckOutConditionComment());
		checkOutPhone.setLastActionUserId(user.getUserId());
		checkOutPhone.setLastName(user.getLastName());
		checkOutPhone.setFirstName(user.getFirstName());
		checkOutPhone.setModifiedBy(user.getUserId());
		checkOutPhone.setModifiedOn(new Date());

		cocih.setCheckedOut(true);
		cocih.setCheckOutTime(new Date());
		cocih.setCheckOutEmployee(user.getLastName());
		cocih.setPhoneExtraList(phoneExtraList);
		cocih.setPhone(checkOutPhone);
		cocih.setCreatedBy(user.getUserId());
		cocih.setCreatedOn(new Date());
		logger.trace("checkOutHistory=" + cocih);
		logger.debug("checkOutPhone=" + checkOutPhone);
		try{
		    loanerPhoneService.checkOutPhone(cocih);
		}catch(ServiceException se){
		    String message = "An unexpected exception occured while attempting to check out the phone";
		    logger.error(message, se);
		    loanerPhoneCheckedOutForm.error(se.getFullMessage());
		    target.add(feedbackPanel);
		    target.appendJavaScript("scrollToTop();");
		    return;
		}

		additionalTaskContainer.setVisible(true);
		reprintButtonContainer.setVisible(true);
		checkOutButtonContainer.setVisible(false);
		closeButtonContainer.setVisible(true);
		cancelButtonContainer.setVisible(false);
		target.add(additionalTaskContainer);
		target.add(reprintButtonContainer);
		target.add(checkOutButtonContainer);
		target.add(closeButtonContainer);
		target.add(cancelButtonContainer);
		target.add(checkListContainer);
		target.appendJavaScript("scrollToBottom();doWicketBehavior('wicketBehavior(\"print\")');");
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.error("checkOutButton onError");
		target.add(feedbackPanel);
		target.appendJavaScript("scrollToTop();");
	    }

	};
	checkOutButton.setOutputMarkupPlaceholderTag(true);
	checkOutButtonContainer.add(checkOutButton);

	final AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel(
		"loanerPhoneCheckedOutForm.cancel.button")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("cancelButton onSubmit");
		if (returnLoadable != null){
		    returnLoadable.loadData();
		}
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(LoanerPhoneLandingPage.class);
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

	final AjaxLink<Object> reprintLink = new AjaxLink<Object>("reprintLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("reprintLink onClick");
		target.appendJavaScript("doWicketBehavior('wicketBehavior(\"reprint\")');");
	    }
	};
	reprintLink.setMarkupId("reprintLink");
	reprintLink.setOutputMarkupId(true);
	reprintLink.setOutputMarkupPlaceholderTag(true);
	reprintButtonContainer.add(reprintLink);

	final AjaxLink<Object> closeLink = new AjaxLink<Object>("closeLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("closeLink onClick");
		if (returnLoadable != null){
		    returnLoadable.loadData();
		}
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(LoanerPhoneLandingPage.class);
		}
	    }
	};
	closeLink.setMarkupId("closeLink");
	closeLink.setOutputMarkupId(true);
	closeLink.setOutputMarkupPlaceholderTag(true);
	closeButtonContainer.add(closeLink);

	// PDF printing behavior.
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehavior id=" + id);

		String html = WicketUtils.renderHTMLFromPage(new PrintAgreementPage(cocih));
		final PdfByteArrayResource pdfResource = WicketUtils.renderPDFFromHtml(html);
		pdfResource.fetchData();

		String uidString = UUID.randomUUID().toString().replaceAll("-", "");
		ResourceReference rr = new ResourceReference(uidString) {

		    private static final long serialVersionUID = 1L;

		    @Override
		    public IResource getResource() {
			return pdfResource;

		    }
		};

		if (rr.canBeRegistered()){
		    getApplication().getResourceReferenceRegistry().registerResourceReference(rr);
		    target.appendJavaScript("window.open('" + urlFor(rr, null) + "')");
		}
	    }
	};
	add(wicketBehavior);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		if (refreshPage){
		    logger.debug("CALLING refreshPage()");
		    refreshPage = false;
		    response.renderOnDomReadyJavaScript("refreshPage();");
		    return;
		}
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
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
}
