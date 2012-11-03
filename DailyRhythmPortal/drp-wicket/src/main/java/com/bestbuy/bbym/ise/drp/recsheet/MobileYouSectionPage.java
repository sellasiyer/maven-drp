package com.bestbuy.bbym.ise.drp.recsheet;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.DateValidator;
import org.apache.wicket.validation.validator.MinimumValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.apache.wicket.validation.validator.StringValidator.MinimumLengthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.util.MoneyConverter;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;
import com.bestbuy.bbym.ise.drp.validator.SingleDateValidator;
import com.bestbuy.bbym.ise.util.Util;

/**
 * 
 * @author a909782
 */
public class MobileYouSectionPage extends AbstractRecSheetPage {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(MobileYouSectionPage.class);

    private Integer characterCount = 200;

    private TextField<String> firstNameTextField, lastNameTextField, phoneNumberTextField, tradeInValueField;

    private DateTextField upgradeEligibilityDateField;

    private TextArea<String> subscriptionInfoTextArea;

    private CheckBox upgradeTextCbx, upgradeCallCbx;

    private final Label characterCountLabel;

    private MoneyConverter mc = new MoneyConverter();

    public MobileYouSectionPage(PageParameters parameters) {
	super(parameters);

	Label title = new Label("sectionTitle", getString("mobileYouSectionForm.title.label"));
	title.setOutputMarkupPlaceholderTag(true);
	form.add(title);

	firstNameTextField = new RequiredTextField<String>("firstName", new PropertyModel<String>(recommendation,
		"firstName"));
	firstNameTextField.setMarkupId("firstName");
	firstNameTextField.add(new PatternValidator("[A-Za-z -]{1,15}"));
	firstNameTextField.add(new MinimumLengthValidator(2));
	firstNameTextField.setOutputMarkupPlaceholderTag(true);
	form.add(firstNameTextField);

	lastNameTextField = new RequiredTextField<String>("lastName", new PropertyModel<String>(recommendation,
		"lastName"));
	lastNameTextField.setMarkupId("lastName");
	lastNameTextField.add(new PatternValidator("[A-Za-z -]{1,25}"));
	lastNameTextField.add(new MinimumLengthValidator(2));
	lastNameTextField.setOutputMarkupPlaceholderTag(true);
	form.add(lastNameTextField);

	phoneNumberTextField = new RequiredTextField<String>("phoneNumber", new PropertyModel<String>(recommendation,
		"mobileNo"));
	phoneNumberTextField.add(new PatternValidator("\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})"));

	phoneNumberTextField.setMarkupId("phoneNumber");
	phoneNumberTextField.setOutputMarkupPlaceholderTag(true);
	form.add(phoneNumberTextField);

	upgradeEligibilityDateField = new DateTextField("upgradeEligibilityDate", new PropertyModel<Date>(
		recommendation, "upgradeEligibilityDate"), "MM/dd/yy");
	upgradeEligibilityDateField.add(new DatePicker());
	upgradeEligibilityDateField.setMarkupId("upgradeEligibilityDate");
	upgradeEligibilityDateField.setOutputMarkupPlaceholderTag(true);
	upgradeEligibilityDateField.add(DateValidator.minimum(Util.getTodayDate()));
	form.add(upgradeEligibilityDateField);

	subscriptionInfoTextArea = new TextArea<String>("subscriptionInfo", new PropertyModel<String>(recommendation,
		"subscriptionInfo"));
	subscriptionInfoTextArea.setMarkupId("subscriptionInfo");
	subscriptionInfoTextArea.setOutputMarkupPlaceholderTag(true);
	subscriptionInfoTextArea.add(new MaximumLengthValidator(200));
	form.add(subscriptionInfoTextArea);

	characterCountLabel = new Label("characterCountLabel", new PropertyModel<Integer>(this, "characterCount"));
	characterCountLabel.setMarkupId("characterCountLabel");
	characterCountLabel.setOutputMarkupPlaceholderTag(true);
	form.add(characterCountLabel);

	subscriptionInfoTextArea.add(new AjaxFormComponentUpdatingBehavior("onkeyup") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		logger.debug("subscription info..." + recommendation.getSubscriptionInfo());
		if (recommendation != null && recommendation.getSubscriptionInfo() != null){
		    characterCount = 200 - recommendation.getSubscriptionInfo().length();
		    target.add(characterCountLabel);
		}else{
		    characterCount = 200;
		    target.add(characterCountLabel);
		}

	    }
	});

	upgradeTextCbx = new CheckBox("upgradetext", new PropertyModel<Boolean>(recommendation, "upgradeReminderText"));
	upgradeTextCbx.setMarkupId("upgradeTextCbx");
	upgradeTextCbx.setOutputMarkupPlaceholderTag(true);
	form.add(upgradeTextCbx);

	upgradeCallCbx = new CheckBox("upgradecall", new PropertyModel<Boolean>(recommendation, "upgradeReminderCall"));
	upgradeCallCbx.setMarkupId("upgradeCallCbx");
	upgradeCallCbx.setOutputMarkupPlaceholderTag(true);
	form.add(upgradeCallCbx);

	tradeInValueField = new TextField<String>("tradeInValue", new PropertyModel<String>(recommendation,
		"tradeInValue")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected String getModelValue() {
		if (this.getModelObject() != null){
		    return mc.convertToString(this.getModelObject(), Locale.US).replace("$", "");
		}
		return super.getModelValue();
	    }

	};

	tradeInValueField.add(new MinimumValidator<BigDecimal>(new BigDecimal(0)));
	tradeInValueField.setMarkupId("tradeInValue");
	tradeInValueField.setOutputMarkupPlaceholderTag(true);
	form.add(tradeInValueField);

	loadSavedFields();

	form.add(new SingleDateValidator(upgradeEligibilityDateField));

	setLinkVisible(this.clearSectionButton, RecSheetsSections.YOU_MOBILE.dataWasEntered(recommendation));
    }

    @Override
    void saveScreenValues(AjaxRequestTarget target) {
	if (phoneNumberTextField.getModelObject() != null){
	    String mobileNo = phoneNumberTextField.getModelObject().replace("(", "").replace(")", "").replace("-", "");
	    logger.debug("mobile no removing format.." + mobileNo);
	    recommendation.setMobileNo(mobileNo);
	}
	target.add(tradeInValueField);
	super.saveScreenValues(target);
	setLinkVisible(this.clearSectionButton, RecSheetsSections.YOU_MOBILE.dataWasEntered(recommendation));
    }

    private void loadSavedFields() {
	if (!StringUtils.isBlank(recommendation.getSubscriptionInfo())){
	    characterCount = characterCount - recommendation.getSubscriptionInfo().length();
	}
	if (recommendation.getMobileNo() != null){
	    PhoneNumberConverter converter = new PhoneNumberConverter();
	    phoneNumberTextField.setDefaultModelObject(converter.convertToString(recommendation.getMobileNo(), null));
	}

    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	recommendation.setFirstName(null);
	recommendation.setLastName(null);
	recommendation.setMobileNo(null);
	recommendation.setSubscriptionInfo(null);
	recommendation.setUpgradeEligibilityDate(null);
	recommendation.setUpgradeReminderCall(Boolean.FALSE);
	recommendation.setUpgradeReminderText(Boolean.FALSE);
	recommendation.setTradeInValue(null);
	characterCount = 200;
	form.clearInput();
	target.add(form);
	target.add(phoneNumberTextField);
	target.add(subscriptionInfoTextArea);
	markSections();
	setLinkVisible(this.clearSectionButton, RecSheetsSections.YOU_MOBILE.dataWasEntered(recommendation));
    }

}
