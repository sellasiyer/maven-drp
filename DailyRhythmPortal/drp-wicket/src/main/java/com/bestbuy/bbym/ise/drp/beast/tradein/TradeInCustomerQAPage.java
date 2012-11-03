package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.CustomerQABean;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;
import com.bestbuy.bbym.ise.drp.util.SelectItem;

/**
 * @author a915726
 */
public class TradeInCustomerQAPage extends BeastPage {

    private static Logger LOGGER = LoggerFactory.getLogger(TradeInCustomerQAPage.class);
    private static final long serialVersionUID = 1L;

    private CustomerQABean customerQABean;

    private String dataSharingKey;
    private String dateOfBirth;
    private SelectItem<String> stateSelectionSelected;
    private SelectItem<String> idIssuerSelectionSelected;
    private String idNumber;
    private String idDateIssued;
    private String idExpiration;

    public TradeInCustomerQAPage(PageParameters parameters) {
	super(parameters);

	getBorder().setPageTitle(getString("tradeInCustomerQAPage.customerInformation.label"));

	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yy");

	dataSharingKey = parameters.get(PageParameterKeys.DATA_SHARING_KEY.getUrlParameterKey()).toString();

	UIDataItem titanDataItem = new UIDataItem();
	titanDataItem.setStringProp("dataSharingKey", dataSharingKey);
	getDailyRhythmPortalSession().setTitanDataItem(titanDataItem);

	customerQABean = getDailyRhythmPortalSession().getCustomerQABean();
	if (customerQABean == null){
	    customerQABean = new CustomerQABean();

	}else{
	    if (customerQABean.getDateOfBirth() != null)
		dateOfBirth = sdf.format(customerQABean.getDateOfBirth());

	    idNumber = getLast4ID(customerQABean.getIdNumber());
	    if (customerQABean.getIdDateIssued() != null)
		idDateIssued = sdf.format(customerQABean.getIdDateIssued());
	    if (customerQABean.getIdExpiration() != null)
		idExpiration = sdf.format(customerQABean.getIdExpiration());

	}

	stateSelectionSelected = new SelectItem<String>(customerQABean.getState(), "");
	idIssuerSelectionSelected = new SelectItem<String>(customerQABean.getIdIssuer(), "");

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	// firstName
	final TextField<String> firstNameTextField = new TextField<String>("firstName", new PropertyModel<String>(
		customerQABean, "firstName"));

	firstNameTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});
	firstNameTextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.firstName.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.firstName.max.length"))));
	firstNameTextField.setRequired(true);
	firstNameTextField.setMarkupId("firstName");
	firstNameTextField.setOutputMarkupId(true);
	firstNameTextField.setOutputMarkupPlaceholderTag(true);
	add(firstNameTextField);

	// lastName
	TextField<String> lastNameTextField = new TextField<String>("lastName", new PropertyModel<String>(
		customerQABean, "lastName"));

	lastNameTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});
	lastNameTextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.lastName.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.lastName.max.length"))));
	lastNameTextField.setRequired(true);
	lastNameTextField.setMarkupId("lastName");
	lastNameTextField.setOutputMarkupId(true);
	lastNameTextField.setOutputMarkupPlaceholderTag(true);
	add(lastNameTextField);

	// dateOfBirth
	final TextField<String> dateOfBirthTextField = new TextField<String>("dateOfBirth", new PropertyModel<String>(
		this, "dateOfBirth"));
	dateOfBirthTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		dateOfBirth = addDateSlash(dateOfBirth);
		Date date = dateValidate(dateOfBirthTextField);
		customerQABean.setDateOfBirth(date);
		target.add(dateOfBirthTextField);
	    }
	});
	dateOfBirthTextField.setRequired(true);
	dateOfBirthTextField.setMarkupId("dateOfBirth");
	dateOfBirthTextField.setOutputMarkupId(true);
	dateOfBirthTextField.setOutputMarkupPlaceholderTag(true);
	add(dateOfBirthTextField);

	// phoneNumber
	final TextField<String> phoneNumberTextField = new TextField<String>("phoneNumber", new PropertyModel<String>(
		customerQABean, "phoneNumber"));
	phoneNumberTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		PhoneNumberConverter converter = new PhoneNumberConverter();
		phoneNumberTextField.setDefaultModelObject(converter.convertToString(phoneNumberTextField
			.getDefaultModelObject(), null));
		target.add(phoneNumberTextField);
	    }
	});
	phoneNumberTextField.add(new PatternValidator("\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})"));
	phoneNumberTextField.setRequired(true);
	phoneNumberTextField.setMarkupId("phoneNumber");
	phoneNumberTextField.setOutputMarkupId(true);
	phoneNumberTextField.setOutputMarkupPlaceholderTag(true);
	add(phoneNumberTextField);

	// email
	final TextField<String> emailAddressTextField = new TextField<String>("email", new PropertyModel<String>(
		customerQABean, "email"));
	emailAddressTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});
	emailAddressTextField.add(StringValidator.maximumLength(75));
	emailAddressTextField.add(EmailAddressValidator.getInstance());
	emailAddressTextField.setRequired(true);
	emailAddressTextField.setMarkupId("email");
	emailAddressTextField.setOutputMarkupId(true);
	emailAddressTextField.setOutputMarkupPlaceholderTag(true);
	add(emailAddressTextField);

	// addr1
	final TextField<String> addressLine1TextField = new TextField<String>("addr1", new PropertyModel<String>(
		customerQABean, "addr1"));
	addressLine1TextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});
	addressLine1TextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.addr1.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.addr1.max.length"))));
	addressLine1TextField.setRequired(true);
	addressLine1TextField.setMarkupId("addr1");
	addressLine1TextField.setOutputMarkupId(true);
	addressLine1TextField.setOutputMarkupPlaceholderTag(true);
	add(addressLine1TextField);

	// addr2
	final TextField<String> addressLine2TextField = new TextField<String>("addr2", new PropertyModel<String>(
		customerQABean, "addr2"));
	addressLine2TextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});
	addressLine2TextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.addr2.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.addr2.max.length"))));
	addressLine2TextField.setMarkupId("addr2");
	addressLine2TextField.setOutputMarkupId(true);
	addressLine2TextField.setOutputMarkupPlaceholderTag(true);
	add(addressLine2TextField);

	// city
	final TextField<String> cityTextField = new TextField<String>("city", new PropertyModel<String>(customerQABean,
		"city"));
	cityTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});
	cityTextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.city.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.city.max.length"))));
	cityTextField.setRequired(true);
	cityTextField.setMarkupId("city");
	cityTextField.setOutputMarkupId(true);
	cityTextField.setOutputMarkupPlaceholderTag(true);
	add(cityTextField);

	// State
	List<SelectItem<String>> stateSelections = new ArrayList<SelectItem<String>>();
	for(CustomerQABean.State state: CustomerQABean.State.values()){
	    stateSelections.add(new SelectItem<String>(state.getStateCode(), state.getStateName()));
	}
	ChoiceRenderer<SelectItem<String>> choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	DropDownChoice<SelectItem<String>> stateDropDown = new DropDownChoice<SelectItem<String>>("state",
		new PropertyModel<SelectItem<String>>(this, "stateSelectionSelected"), stateSelections, choiceRenderer);
	stateDropDown.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		customerQABean.setState(stateSelectionSelected.getKey());
	    }
	});

	stateDropDown.setRequired(true);
	stateDropDown.setMarkupId("state");
	stateDropDown.setOutputMarkupPlaceholderTag(true);
	add(stateDropDown);

	// zip
	final TextField<String> zipTextField = new TextField<String>("zip", new PropertyModel<String>(customerQABean,
		"zip"));
	zipTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
	    }
	});
	zipTextField.add(StringValidator.exactLength(5));
	zipTextField.add(new PatternValidator("([0-9]{5})"));
	zipTextField.setRequired(true);
	zipTextField.setMarkupId("zip");
	zipTextField.setOutputMarkupId(true);
	zipTextField.setOutputMarkupPlaceholderTag(true);
	add(zipTextField);

	// ID Type
	DropDownChoice<String> idTypeDropDown = new DropDownChoice<String>("idType", new PropertyModel<String>(
		customerQABean, "idType"), customerQABean.getIdTypeOptions());
	idTypeDropDown.setRequired(true);
	idTypeDropDown.setMarkupId("idType");
	idTypeDropDown.setOutputMarkupPlaceholderTag(true);
	add(idTypeDropDown);

	// ID Issuer
	List<SelectItem<String>> idIssuerSelections = new ArrayList<SelectItem<String>>();
	for(CustomerQABean.State state: CustomerQABean.State.values()){
	    idIssuerSelections.add(new SelectItem<String>(state.getStateCode(), state.getStateName()));
	}
	idIssuerSelections.add(new SelectItem<String>("AA", "Armed Forces - AA"));
	idIssuerSelections.add(new SelectItem<String>("AE", "Armed Forces - AE"));
	idIssuerSelections.add(new SelectItem<String>("AP", "Armed Forces - AP"));

	choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	DropDownChoice<SelectItem<String>> idIssuerDropDown = new DropDownChoice<SelectItem<String>>("idIssuer",
		new PropertyModel<SelectItem<String>>(this, "idIssuerSelectionSelected"), idIssuerSelections,
		choiceRenderer);
	idIssuerDropDown.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		customerQABean.setIdIssuer(idIssuerSelectionSelected.getKey());
	    }
	});
	idIssuerDropDown.setRequired(true);
	idIssuerDropDown.setMarkupId("idIssuer");
	idIssuerDropDown.setOutputMarkupPlaceholderTag(true);
	add(idIssuerDropDown);

	// idNumber
	final TextField<String> idNumberTextField = new TextField<String>("idNumber", new PropertyModel<String>(this,
		"idNumber"));
	idNumberTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		if (idNumber != null && idNumber.contains("*") == false){
		    customerQABean.setIdNumber(idNumber);
		}
		idNumber = getLast4ID(idNumber);
		//LOGGER.debug(idNumber);
		target.add(idNumberTextField);
	    }
	});
	idNumberTextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.idNumber.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.idNumber.max.length"))));
	idNumberTextField.setRequired(true);
	idNumberTextField.add(new PatternValidator("[A-Za-z0-9\\*]+"));
	idNumberTextField.setMarkupId("idNumber");
	idNumberTextField.setOutputMarkupId(true);
	idNumberTextField.setOutputMarkupPlaceholderTag(true);
	add(idNumberTextField);

	// idDateIssued
	final TextField<String> idDateIssuedTextField = new TextField<String>("idDateIssued",
		new PropertyModel<String>(this, "idDateIssued"));
	idDateIssuedTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		idDateIssued = addDateSlash(idDateIssued);
		Date date = dateValidate(idDateIssuedTextField);
		customerQABean.setIdDateIssued(date);
		target.add(idDateIssuedTextField);
	    }
	});
	idDateIssuedTextField.setRequired(true);
	idDateIssuedTextField.setMarkupId("idDateIssued");
	idDateIssuedTextField.setOutputMarkupId(true);
	idDateIssuedTextField.setOutputMarkupPlaceholderTag(true);
	add(idDateIssuedTextField);

	// idExpiration
	final TextField<String> idExpirationTextField = new TextField<String>("idExpiration",
		new PropertyModel<String>(this, "idExpiration"));
	idExpirationTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		idExpiration = addDateSlash(idExpiration);
		Date date = dateValidate(idExpirationTextField);
		customerQABean.setIdExpiration(date);
		target.add(idExpirationTextField);
	    }
	});
	idExpirationTextField.setRequired(true);
	idExpirationTextField.setMarkupId("idExpiration");
	idExpirationTextField.setOutputMarkupId(true);
	idExpirationTextField.setOutputMarkupPlaceholderTag(true);
	add(idExpirationTextField);

    }

    public SelectItem<String> getStateSelectionSelected() {
	return stateSelectionSelected;
    }

    public void setStateSelectionSelected(SelectItem<String> stateSelectionSelected) {
	this.stateSelectionSelected = stateSelectionSelected;
    }

    public SelectItem<String> getIdIssuerSelectionSelected() {
	return idIssuerSelectionSelected;
    }

    public void setIdIssuerSelectionSelected(SelectItem<String> idIssuerSelectionSelected) {
	this.idIssuerSelectionSelected = idIssuerSelectionSelected;
    }

    // prefix mask
    private String getLast4ID(String id) {
	String tmpId = id;
	if (id != null && id.length() > 4){
	    tmpId = "*****" + id.substring(id.length() - 4);
	}
	return tmpId;
    }

    private String addDateSlash(String value) {
	if (value != null && value.contains("/") == false){
	    if (value.length() == 6){
		if (Integer.parseInt(value.substring(4, 6)) > 30){
		    value = value.substring(0, 2) + "/" + value.substring(2, 4) + "/19" + value.substring(4, 6);
		}else{
		    value = value.substring(0, 2) + "/" + value.substring(2, 4) + "/20" + value.substring(4, 6);
		}
	    }else if (value.length() == 8){
		value = value.substring(0, 2) + "/" + value.substring(2, 4) + "/" + value.substring(4, 8);
	    }
	}
	return value;
    }

    // TODO Refactor to use new AgeValidator
    private boolean checkBirthDate(AjaxRequestTarget target, Date birthdate) {
	boolean status = true;
	int year = Calendar.getInstance().get(Calendar.YEAR);
	final Calendar bday = Calendar.getInstance();
	int bYear = 0;
	if (birthdate == null){
	    return false;
	}else{
	    bday.setTime(birthdate);
	    bYear = bday.get(Calendar.YEAR);
	}

	if ((year - bYear) < 18){
	    displayError("Customer must be at least 18 years old.", target);
	    status = false;
	}
	return status;
    }

    private boolean checkIdExpiration(AjaxRequestTarget target, Date idExpiration) {
	boolean status = true;
	if (idExpiration == null)
	    return false;
	if (idExpiration.compareTo(new Date()) < 0){
	    displayError("Expiration date must be after today's date.", target);
	    status = false;
	}
	return status;
    }

    @SuppressWarnings("rawtypes")
    private Date dateValidate(FormComponent formComponent) {
	String labelName = formComponent.getDefaultLabel();
	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

	Date day = null;
	Date today = new Date();

	try{
	    String rawInput = formComponent.getDefaultModelObjectAsString();
	    LOGGER.debug("rawInput:" + rawInput);
	    day = formatter.parse(rawInput);
	    checkDateValue(rawInput, labelName);
	}catch(ParseException e){
	    LOGGER.debug(e.getMessage());
	    formComponent.error(e.getMessage());
	}catch(NumberFormatException exc){
	    LOGGER.debug(exc.getMessage());
	    formComponent.error(exc.getMessage());
	}

	if ("ID Expiration".equals(labelName) && day != null && day.compareTo(today) < 0){
	    formComponent.error("Invalid Date Range: The " + labelName + " must fall after today.");
	}
	if ("Date of Birth".equals(labelName) && day != null && day.compareTo(today) > 0){
	    formComponent.error("Invalid Date Range: The " + labelName + " must fall before today.");
	}

	return day;
    }

    private void checkDateValue(String input, String labelName) throws ParseException {
	if (StringUtils.isEmpty(input)){
	    throw new ParseException(labelName + " is invalid date.", 0);
	}

	String[] vals = input.split("/");
	int month = Integer.parseInt(vals[0]);
	int day = Integer.parseInt(vals[1]);
	int year = Integer.parseInt(vals[2]);

	if (month > 12 || day > 31){
	    throw new ParseException(labelName + " is invalid date.", 0);
	}

	if (NumberUtils.createInteger(vals[2]) < 1900 && Integer.parseInt(vals[2]) > 3000){
	    throw new ParseException(labelName + " is invalid date.", 0);
	}

	if ((month % 2 == 0 && day > 30) || (month % 2 == 1 && day > 31)){
	    throw new ParseException(labelName + " is invalid date.", 0);
	}

	if (month == 2){
	    if (year % 4 != 0 && day > 28){
		throw new ParseException(labelName + " is invalid date.", 0);
	    }else if (year % 4 == 0 && year % 100 != 0 && day > 29){
		throw new ParseException(labelName + " is invalid date.", 0);
	    }else if (year % 4 == 0 && year % 100 == 0 && year % 400 != 0 && day > 28){
		throw new ParseException(labelName + " is invalid date.", 0);
	    }else if (year % 4 == 0 && year % 100 == 0 && year % 400 == 0 && day > 29){
		throw new ParseException(labelName + " is invalid date.", 0);
	    }
	}

    }

    @Override
    void clearForm(AjaxRequestTarget target) {
	this.getDailyRhythmPortalSession().setCustomerQABean(null);
	this.setResponsePage(TradeInCustomerQAPage.class);
    }

    @Override
    void goBack(AjaxRequestTarget target) {
	this.setResponsePage(EditProductAnswersPage.class);
    }

    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	if (checkBirthDate(target, customerQABean.getDateOfBirth()) == false)
	    return;

	if (checkIdExpiration(target, customerQABean.getIdExpiration()) == false)
	    return;

	this.getDailyRhythmPortalSession().setCustomerQABean(customerQABean);

	this.setResponsePage(LoadGiftCardPage.class);
    }
}
