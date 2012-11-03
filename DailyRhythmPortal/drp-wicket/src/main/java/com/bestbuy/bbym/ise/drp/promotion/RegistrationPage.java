package com.bestbuy.bbym.ise.drp.promotion;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxCallDecorator;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.ExternalLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;
import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.drp.service.PromotionService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class RegistrationPage extends NewBaseNavPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(RegistrationPage.class);

    private String phoneNumber;
    private String email;
    private Boolean remindAlertsOptin;
    private Boolean disclaimerOption;
    private RelatedPromotion relatedPromo;
    private String promoCode;

    @SpringBean(name = "promotionService")
    private PromotionService promotionService;

    // TODO Hack for getting save button to work properly
    private boolean refreshPage = true;
    WebMarkupContainer eSignOptionContainer;

    public RegistrationPage(RelatedPromotion relatedPromotion, final Page returnPage) {
	super(null);

	relatedPromo = relatedPromotion;
	logger.trace("passed in relatedPromo=" + relatedPromo);

	if (relatedPromo != null && relatedPromo.getPromotion() != null){
	    promoCode = relatedPromo.getPromotion().getPromotionCode();
	}

	logger.debug("promo code ...." + promoCode);

	Form<Object> registrationForm = new Form<Object>("registrationForm");
	registrationForm.setOutputMarkupPlaceholderTag(true);
	add(registrationForm);

	final FeedbackPanel registrationFeedbackPanel = new FeedbackPanel("registrationFeedback");
	registrationFeedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(registrationFeedbackPanel);

	TextField<String> phoneNumberField = new RequiredTextField<String>("phoneNumber", new PropertyModel<String>(
		this, "phoneNumber"));
	phoneNumberField.setOutputMarkupPlaceholderTag(true);
	phoneNumberField.add(new PatternValidator("[0-9]+"));
	registrationForm.add(phoneNumberField);

	TextField<String> emailField = new RequiredTextField<String>("email", new PropertyModel<String>(this, "email"));
	emailField.setOutputMarkupPlaceholderTag(true);
	emailField.add(EmailAddressValidator.getInstance());
	registrationForm.add(emailField);

	eSignOptionContainer = new WebMarkupContainer("eSignOptionContainer");
	eSignOptionContainer.setVisible(false);
	registrationForm.add(eSignOptionContainer);
	eSignOptionContainer.setOutputMarkupPlaceholderTag(true);

	ExternalLink hereLink = new ExternalLink("hereLink", getString("registrationForm.termsConditions.externalLink"));
	eSignOptionContainer.add(hereLink);

	AjaxCheckBox remindAlertsOptinCbx = new AjaxCheckBox("remindAlertsOptinCbx", new PropertyModel<Boolean>(this,
		"remindAlertsOptin")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (this.getModelObject()){
		    eSignOptionContainer.setVisible(true);
		    target.appendJavaScript("enableSaveButton(false);");
		}else{
		    eSignOptionContainer.setVisible(false);
		    target.appendJavaScript("enableSaveButton(true);");
		}
		target.add(eSignOptionContainer);
	    }
	};
	remindAlertsOptinCbx.setMarkupId("remindAlertsOptinCbx");
	remindAlertsOptinCbx.setOutputMarkupPlaceholderTag(true);
	registrationForm.add(remindAlertsOptinCbx);

	AjaxCheckBox eSignCheckBox = new AjaxCheckBox("eSignCheckBox", new PropertyModel<Boolean>(this,
		"disclaimerOption")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (this.getModelObject()){
		    target.appendJavaScript("enableSaveButton(true);");
		}else{
		    target.appendJavaScript("enableSaveButton(false);");
		}
	    }
	};
	eSignCheckBox.setMarkupId("eSignCheckBox");
	eSignCheckBox.setOutputMarkupPlaceholderTag(true);
	eSignOptionContainer.add(eSignCheckBox);

	AjaxButton submitButton = new AjaxButton("submitButton", new ResourceModel(
		"registrationForm.submitButton.label"), registrationForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("on submit...");
		logger.debug("phonenumber....." + phoneNumber);
		logger.debug("email....." + email);
		logger.debug("remindAlertsOptin...." + remindAlertsOptin.booleanValue());

		try{
		    promotionService.register(promoCode, phoneNumber, email, null, remindAlertsOptin.booleanValue());
		}catch(ServiceException se){
		    String message = "An unexpected exception occured while attempting to register the customer";
		    logger.error(message, se);
		    // processException(message, se);
		    error("Unable to Complete Registration, please try again.");
		    target.add(registrationFeedbackPanel);
		    return;
		}catch(IseBusinessException be){
		    logger.error("IseBusinessException while registering the customer");
		    error(be.getMessage());
		    target.add(registrationFeedbackPanel);
		    return;
		}

		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(PromotionsLookupPage.class);
		}

	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("on error...");
		target.add(registrationFeedbackPanel);
	    }

	    @Override
	    protected IAjaxCallDecorator getAjaxCallDecorator() {
		return new IAjaxCallDecorator() {

		    private static final long serialVersionUID = 1L;

		    public CharSequence decorateScript(Component comp, CharSequence script) {
			return "showButtonLoading(true, '#submitButton');" + script;
		    }

		    public CharSequence decorateOnFailureScript(Component comp, CharSequence script) {
			return "showButtonLoading(false, '#submitButton');" + script;
		    }

		    public CharSequence decorateOnSuccessScript(Component comp, CharSequence script) {
			return "showButtonLoading(false, '#submitButton');" + script;
		    }
		};
	    }
	};
	submitButton.setMarkupId("submitButton");
	submitButton.setOutputMarkupPlaceholderTag(true);
	registrationForm.add(submitButton);

	registrationForm.setDefaultButton(submitButton);

	AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel(
		"registrationForm.cancelButton.label"), registrationForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("on cancel...");
		if (returnPage != null){
		    setResponsePage(returnPage);
		}else{
		    setResponsePage(PromotionsLookupPage.class);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	cancelButton.setMarkupId("cancelButton");
	cancelButton.setOutputMarkupPlaceholderTag(true);
	cancelButton.setDefaultFormProcessing(false);
	registrationForm.add(cancelButton);

	ExternalLink termsConditionsLink = new ExternalLink("termsConditionsLink",
		getString("registrationForm.termsConditions.externalLink"));
	registrationForm.add(termsConditionsLink);

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
	    }
	});
    }

    private String getCustomerIPAddress1() {
	InetAddress localHost = null;
	String customerIPAddress = "";
	try{
	    localHost = InetAddress.getLocalHost();
	}catch(UnknownHostException e){
	    logger.error("UnknownHostException while getting the local host of the customer " + e.getMessage());
	}

	if (localHost != null){
	    customerIPAddress = localHost.getHostAddress();
	}
	logger.debug("IP of customer system is := " + customerIPAddress);

	return customerIPAddress;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Boolean getRemindAlertsOptin() {
	return remindAlertsOptin;
    }

    public void setRemindAlertsOptin(Boolean remindAlertsOptin) {
	this.remindAlertsOptin = remindAlertsOptin;
    }

    public Boolean getDisclaimerOption() {
	return disclaimerOption;
    }

    public void setDisclaimerOption(Boolean disclaimerOption) {
	this.disclaimerOption = disclaimerOption;
    }

}
