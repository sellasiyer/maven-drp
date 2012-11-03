package com.bestbuy.bbym.ise.drp.recsheet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.extensions.yui.calendar.DatePicker;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator.MinimumLengthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.States;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;
import com.bestbuy.bbym.ise.drp.util.SelectItem;

/**
 * 
 * @author a909782
 * 
 */

public class CTYouSectionPage extends AbstractRecSheetPage {

    private static Logger logger = LoggerFactory.getLogger(CTYouSectionPage.class);

    private static final long serialVersionUID = 1L;

    private SelectItem<String> selectedState;

    private AjaxCheckBox upgradeTextCbx, upgradeReminderCallCbx;

    private TextField<String> firstNameTextField, lastNameTextField, addressTextField, cityTextField, zipTextField,
	    emailTextField;

    private final TextField<String> phoneNumberTextField;

    private DropDownChoice<SelectItem<String>> stateDropDown;

    private DateTextField upgradeEligibilityDateField;

    public CTYouSectionPage(PageParameters parameters) {
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

	addressTextField = new TextField<String>("address", new PropertyModel<String>(recommendation, "addr"));
	addressTextField.setMarkupId("address");
	addressTextField.setOutputMarkupPlaceholderTag(true);
	form.add(addressTextField);

	cityTextField = new TextField<String>("city", new PropertyModel<String>(recommendation, "city"));
	cityTextField.setMarkupId("city");
	cityTextField.setOutputMarkupPlaceholderTag(true);
	form.add(cityTextField);

	List<SelectItem<String>> stateSelections = new ArrayList<SelectItem<String>>();

	for(States state: States.values()){
	    stateSelections.add(new SelectItem<String>(state.getShortLabel(), state.getLabel()));

	}

	ChoiceRenderer<SelectItem<String>> dropDownRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");

	stateDropDown = new DropDownChoice<SelectItem<String>>("state", new PropertyModel<SelectItem<String>>(this,
		"selectedState"), stateSelections, dropDownRenderer);
	stateDropDown.setMarkupId("state");
	stateDropDown.setOutputMarkupPlaceholderTag(true);
	form.add(stateDropDown);

	zipTextField = new TextField<String>("zip", new PropertyModel<String>(recommendation, "zipcode"));
	zipTextField.setMarkupId("zipcode");
	zipTextField.setOutputMarkupPlaceholderTag(true);
	form.add(zipTextField);

	phoneNumberTextField = new RequiredTextField<String>("phoneNumber", new PropertyModel<String>(recommendation,
		"mobileNo")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public <C> IConverter<C> getConverter(Class<C> type) {
		return new PhoneNumberConverter();
	    }
	};
	phoneNumberTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		target.add(phoneNumberTextField);
	    }
	});
	phoneNumberTextField.setMarkupId("phoneNumber");
	phoneNumberTextField.setOutputMarkupPlaceholderTag(true);
	form.add(phoneNumberTextField);

	emailTextField = new TextField<String>("email", new PropertyModel<String>(recommendation, "email"));
	emailTextField.setMarkupId("email");
	emailTextField.setOutputMarkupPlaceholderTag(true);
	emailTextField.add(EmailAddressValidator.getInstance());
	form.add(emailTextField);

	upgradeEligibilityDateField = new DateTextField("upgradeEligibilityDate", new PropertyModel<Date>(
		recommendation, "upgradeEligibilityDate"), "MM/dd/yy");
	upgradeEligibilityDateField.add(new DatePicker());
	upgradeEligibilityDateField.setMarkupId("upgradeEligibilityDate");
	upgradeEligibilityDateField.setOutputMarkupPlaceholderTag(true);
	form.add(upgradeEligibilityDateField);

	upgradeTextCbx = new AjaxCheckBox("upgradetext", new PropertyModel<Boolean>(recommendation,
		"upgradeReminderText")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (this.getModelObject()){
		    if (upgradeReminderCallCbx != null){
			upgradeReminderCallCbx.setModelObject(Boolean.FALSE);
			target.add(upgradeReminderCallCbx);
		    }
		}

	    }
	};
	upgradeTextCbx.setMarkupId("upgradeTextCbx");
	upgradeTextCbx.setOutputMarkupPlaceholderTag(true);
	form.add(upgradeTextCbx);

	upgradeReminderCallCbx = new AjaxCheckBox("upgradecall", new PropertyModel<Boolean>(recommendation,
		"upgradeReminderText")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (this.getModelObject()){
		    if (upgradeTextCbx != null){
			upgradeTextCbx.setModelObject(Boolean.FALSE);
			target.add(upgradeTextCbx);
		    }
		}

	    }
	};
	upgradeReminderCallCbx.setMarkupId("upgradeReminderCallCbx");
	upgradeReminderCallCbx.setOutputMarkupPlaceholderTag(true);
	form.add(upgradeReminderCallCbx);

	loadSavedFields();

    }

    private void loadSavedFields() {
	if (recommendation.getState() != null){
	    States state = States.fromLabel(recommendation.getState());
	    if (state != null){
		selectedState = new SelectItem<String>(state.getShortLabel(), state.getLabel());
	    }
	}
    }

    void saveScreenValues(AjaxRequestTarget target) {
	if (selectedState != null){
	    recommendation.setState(selectedState.getValue());
	}
	logger.debug("in the save screen values..." + recommendation);
	super.saveScreenValues(target);
    }

    @Override
    void clearSection(AjaxRequestTarget target) {
	selectedState = null;
	recommendation.setFirstName(null);
	recommendation.setLastName(null);
	recommendation.setMobileNo(null);
	recommendation.setAddr(null);
	recommendation.setCity(null);
	recommendation.setState(null);
	recommendation.setZipcode(null);
	recommendation.setEmail(null);
	recommendation.setUpgradeEligibilityDate(null);
	recommendation.setUpgradeReminderCall(Boolean.FALSE);
	recommendation.setUpgradeReminderText(Boolean.FALSE);
	target.add(form);
	target.add(phoneNumberTextField);

    }
}
