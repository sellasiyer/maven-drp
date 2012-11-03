package com.bestbuy.bbym.ise.drp.beast.scheduler;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.IConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.beast.common.BaseBeastPage2;
import com.bestbuy.bbym.ise.drp.beast.common.StatusPage;
import com.bestbuy.bbym.ise.drp.behavior.UpperCaseBehavior;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.Styles;
import com.bestbuy.bbym.ise.drp.common.Validation;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;

/**
 * Scheduler create appointment screen
 * 
 * @author a909782
 */
public class CustomerInfoPage extends BaseBeastPage2 {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(CustomerInfoPage.class);

    private String firstName;
    private String lastName;
    private String orderNo;
    private String phone;
    private String email;
    private String confirmEmail;

    private TextField<String> firstNameTextField;
    private TextField<String> lastNameTextField;
    private TextField<String> orderNoTextField;
    private TextField<String> phoneTextField;
    private TextField<String> emailTextField;
    private TextField<String> confirmEmailTextField;
    private Boolean emailOptIn = Boolean.TRUE;

    private SchedulerRequest schedulerReq;
    private Customer customer;

    public CustomerInfoPage(final PageParameters parameters) {
	super(parameters);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	form.add(feedbackPanel);

	if (getDailyRhythmPortalSession() != null && getDailyRhythmPortalSession().getSchedulerReq() != null
		&& getDailyRhythmPortalSession().getSchedulerReq().getCustomer() != null){
	    Customer customer = getDailyRhythmPortalSession().getSchedulerReq().getCustomer();
	    setFirstName(customer.getFirstName());
	    setLastName(customer.getLastName());
	    setOrderNo(getDailyRhythmPortalSession().getSchedulerReq().getOrderNo());
	    setPhone(customer.getContactPhone());
	    setEmail(customer.getEmail());
	    setConfirmEmail(customer.getEmail());
	    emailOptIn = getDailyRhythmPortalSession().getSchedulerReq().isSendEmail();
	}

	firstNameTextField = new TextField<String>("firstName", new PropertyModel<String>(this, "firstName"));
	firstNameTextField.setMarkupId("firstName");
	firstNameTextField.setOutputMarkupPlaceholderTag(true);
	firstNameTextField.add(new UpperCaseBehavior());
	form.add(firstNameTextField);

	lastNameTextField = new TextField<String>("lastName", new PropertyModel<String>(this, "lastName"));
	lastNameTextField.setMarkupId("lastName");
	lastNameTextField.setOutputMarkupPlaceholderTag(true);
	lastNameTextField.add(new UpperCaseBehavior());
	form.add(lastNameTextField);

	orderNoTextField = new TextField<String>("orderNo", new PropertyModel<String>(this, "orderNo"));
	orderNoTextField.setMarkupId("orderNo");
	orderNoTextField.setOutputMarkupPlaceholderTag(true);
	form.add(orderNoTextField);

	phoneTextField = new TextField<String>("phone", new PropertyModel<String>(this, "phone")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public <C> IConverter<C> getConverter(Class<C> type) {
		return new PhoneNumberConverter();
	    }
	};
	phoneTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		target.add(phoneTextField);
	    }
	});
	phoneTextField.setMarkupId("phone");
	phoneTextField.setOutputMarkupPlaceholderTag(true);
	form.add(phoneTextField);

	emailTextField = new TextField<String>("email", new PropertyModel<String>(this, "email"));
	emailTextField.setMarkupId("email");
	emailTextField.setOutputMarkupPlaceholderTag(true);
	form.add(emailTextField);

	confirmEmailTextField = new TextField<String>("confirmEmail", new PropertyModel<String>(this, "confirmEmail"));
	confirmEmailTextField.setMarkupId("confirmEmail");
	confirmEmailTextField.setOutputMarkupPlaceholderTag(true);
	form.add(confirmEmailTextField);

	AjaxCheckBox emailOptInCbx = new AjaxCheckBox("emailOptInCbx", new PropertyModel<Boolean>(this, "emailOptIn")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		StringBuilder appendJS = new StringBuilder();
		handleEmailFields();

		appendJS.append(buildValidationJS());
		appendJS.append("doFormFieldValidation(custInfoValidation);");
		target.appendJavaScript(appendJS.toString());
		target.add(emailTextField);
		target.add(confirmEmailTextField);

	    }
	};
	emailOptInCbx.setMarkupId("emailOptInCbx");
	form.add(emailOptInCbx);

	handleEmailFields();

    }

    private void handleEmailFields() {
	if (emailOptIn != null && emailOptIn.booleanValue()){
	    emailTextField.setEnabled(true);
	    confirmEmailTextField.setEnabled(true);
	    emailTextField.add(new AttributeModifier("class", new Model<String>(Styles.SCHEDULER_REQUIRED)));
	    confirmEmailTextField.add(new AttributeModifier("class", new Model<String>(Styles.SCHEDULER_REQUIRED)));
	}else{
	    emailTextField.setEnabled(false);
	    confirmEmailTextField.setEnabled(false);
	    emailTextField.add(new AttributeModifier("class", new Model<String>(Styles.SCHEDULER_TEXTFIELD_DISABLED)));
	    confirmEmailTextField.add(new AttributeModifier("class", new Model<String>(
		    Styles.SCHEDULER_TEXTFIELD_DISABLED)));
	}
    }

    @Override
    protected String getButtonFunctionKey(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "F3";
	    case 2:
		return "F4";
	    case 3:
		return "F2";
	    case 4:
		return "F1";

	}
	return null;
    }

    @Override
    protected String getButtonName(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "closeButton";
	    case 2:
		return "clearButton";
	    case 3:
		return "continueButton";
	    case 4:
		return "backButton";

	}
	return null;
    }

    @Override
    protected String getButtonStyleClasses(int buttonId) {
	switch (buttonId) {
	    case 1:
		return "button left";
	    case 2:
		return "button left";
	    case 3:
		return "button right";
	    case 4:
		return "button right";

	}
	return null;
    }

    @Override
    protected void onButtonSubmit(int buttonId, AjaxRequestTarget target) {
	switch (buttonId) {
	    case 1:
		logger.debug("closeButton onSubmit");
		if (!quitModalPanel.isOpen()){
		    quitModalPanel.setMultiLineQuestion(getString("schedulerCustomerInfo.quitModal.question.label"));
		    quitModalPanel.open(target);
		}
		break;
	    case 2:
		logger.debug("clearButton onSubmit");
		if (!clearModalPanel.isOpen()){
		    clearModalPanel.setQuestion(getString("schedulerCustomerInfo.clearModal.question.label"));
		    clearModalPanel.open(target);
		}
		break;
	    case 3:
		logger.debug("continueButton onSubmit--" + email + "--" + confirmEmail);
		doContinue(target);
		break;
	    case 4:
		logger.debug("backButton onSubmit");
		doBack(target);
		break;
	}
    }

    @Override
    protected IModel<String> getButtonModel(int buttonId) {
	switch (buttonId) {
	    case 1:
		return new ResourceModel("schedulerCustomerInfoForm.close.button");
	    case 2:
		return new ResourceModel("schedulerCustomerInfoForm.clear.button");
	    case 3:
		return new ResourceModel("schedulerCustomerInfoForm.continue.button");
	    case 4:
		return new ResourceModel("schedulerCustomerInfoForm.back.button");
	}
	return null;
    }

    @Override
    protected boolean getDefaultFormProcessingProperty(int buttonId) {
	switch (buttonId) {
	    case 1:
		return false;
	    case 2:
		return true;
	    case 3:
		return true;
	    case 4:
		return false;
	}
	return true;
    }

    @Override
    protected void wicketBehaviorRespond(AjaxRequestTarget target) {
	String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
	logger.debug("in wicketBehavior id=" + id);
    }

    @Override
    protected String getOnDomReadyJS() {
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append(buildStaticValidationJS());
	onDomReadyJS.append(buildValidationJS());
	onDomReadyJS.append("bindHotKeys();");
	onDomReadyJS.append("handleButtonState(false, '#continueButton');");
	onDomReadyJS.append("doFormFieldValidation(custInfoValidation);disableEmailCopyPaste();");// formatPhoneNumber();
	return onDomReadyJS.toString();
    }

    private String buildValidationJS() {
	StringBuilder validateJS = new StringBuilder();

	validateJS.append("custInfoValidation.firstName.ignore=false;");
	validateJS.append("custInfoValidation.lastName.ignore=false;");
	validateJS.append("custInfoValidation.phone.ignore=false;");
	if (emailOptIn != null && emailOptIn.booleanValue()){
	    validateJS.append("custInfoValidation.email.ignore=false;");
	    validateJS.append("custInfoValidation.confirmEmail.ignore=false;");
	}else{
	    validateJS.append("custInfoValidation.email.ignore=true;");
	    validateJS.append("custInfoValidation.confirmEmail.ignore=true;");
	}

	return validateJS.toString();
    }

    private String buildStaticValidationJS() {
	final StringBuilder validationJS = new StringBuilder();
	//
	// phone settings
	validationJS.append("custInfoValidation.phone.exactLength=");
	validationJS.append(Validation.FORMAT_PHONE_NUMBER_SIZE);
	validationJS.append(";");
	//
	// first name settings
	validationJS.append("custInfoValidation.firstName.maxLength=");
	validationJS.append(Validation.FIRST_NAME_MAX_SIZE);
	validationJS.append(";");
	validationJS.append("custInfoValidation.firstName.minLength=");
	validationJS.append(Validation.FIRST_NAME_MIN_SIZE);
	validationJS.append(";");
	//
	// last name settings
	validationJS.append("custInfoValidation.lastName.maxLength=");
	validationJS.append(Validation.LAST_NAME_MAX_SIZE);
	validationJS.append(";");
	validationJS.append("custInfoValidation.lastName.minLength=");
	validationJS.append(Validation.LAST_NAME_MIN_SIZE);
	validationJS.append(";");
	//
	return validationJS.toString();
    }

    @Override
    protected void quitModalPanelOnClose(AjaxRequestTarget target) {
	if (quitModalPanel.isOk()){
	    PageParameters pp = new PageParameters();
	    pp.add(PageParameterKeys.EXIT_STATUS.getUrlParameterKey(), "cancel");
	    logger.info("setting the status to cancel...");
	    setResponsePage(new StatusPage(pp));
	}
    }

    @Override
    protected void clearModalPanelOnClose(AjaxRequestTarget target) {
	logger.debug("clear onSubmit");
	if (clearModalPanel.isOk()){
	    firstName = "";
	    lastName = "";
	    orderNo = "";
	    phone = "";
	    email = "";
	    confirmEmail = "";
	    target.add(firstNameTextField);
	    target.add(lastNameTextField);
	    target.add(orderNoTextField);
	    target.add(phoneTextField);
	    target.add(emailTextField);
	    target.add(confirmEmailTextField);
	}
    }

    private void doBack(AjaxRequestTarget target) {
	logger.debug("back onSubmit");
	setResponsePage(new ViewManageAppointmentPage(null));
    }

    private void doContinue(AjaxRequestTarget target) {
	logger.debug("continue onSubmit");
	if (emailOptIn != null && emailOptIn.booleanValue() && email != null && !email.equals(confirmEmail)){
	    error("Both emails should match");
	    return;
	}
	schedulerReq = new SchedulerRequest();
	customer = new Customer();
	customer.setFirstName(firstName);
	customer.setLastName(lastName);
	if (phone != null){
	    phone = phone.replace("(", "").replace(")", "").replace("-", "");
	    logger.debug("contact phone is...." + phone);
	}
	customer.setContactPhone(phone);
	schedulerReq.setCustomer(customer);
	schedulerReq.setOrderNo(orderNo);
	logger.debug("email opted----" + emailOptIn);
	if (emailOptIn != null && emailOptIn.booleanValue()){
	    schedulerReq.setSendEmail(emailOptIn.booleanValue());
	    customer.setEmail(email);
	}
	logger.debug("schedulerReq in customer info page...." + schedulerReq);
	this.getDailyRhythmPortalSession().setSchedulerReq(schedulerReq);
	setResponsePage(ChooseAppointmentPage.class);
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getOrderNo() {
	return orderNo;
    }

    public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
    }

    public String getPhone() {
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getConfirmEmail() {
	return confirmEmail;
    }

    public void setConfirmEmail(String confirmEmail) {
	this.confirmEmail = confirmEmail;
    }

}
