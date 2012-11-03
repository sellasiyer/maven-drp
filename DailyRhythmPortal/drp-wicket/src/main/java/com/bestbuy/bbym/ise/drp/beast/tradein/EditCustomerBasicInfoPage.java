package com.bestbuy.bbym.ise.drp.beast.tradein;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.beast.tradein.mapper.CustomerQABean;
import com.bestbuy.bbym.ise.drp.domain.CarrierAccount;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.service.UIService;
import com.bestbuy.bbym.ise.drp.util.PhoneNumberConverter;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.drp.util.behavior.DefaultFocusBehavior;

/**
 * @author a915726
 */
public class EditCustomerBasicInfoPage extends BeastPage {

    private static final long serialVersionUID = 1L;
    private static Logger LOGGER = LoggerFactory.getLogger(EditCustomerBasicInfoPage.class);

    private CustomerQABean customerQABean;

    private SelectItem<String> stateSelectionSelected;
    @SpringBean(name = "uiService")
    private UIService uiService;
    private UIReply uiReply;
    private UIRequest uiRequest;

    private TitanDevice titanDevice;

    private final CarrierAccount ca;

    final TextField<String> zipTextField;
    final TextField<String> cityTextField;
    final TextField<String> addressLine1TextField;
    final TextField<String> firstNameTextField;
    final TextField<String> phoneNumberTextField;
    final TextField<String> emailAddressTextField;
    final TextField<String> addressLine2TextField;
    final TextField<String> lastNameTextField;

    public EditCustomerBasicInfoPage(final PageParameters parameters, UIReply uiReply) {
	super(parameters);
	this.backButton.setVisible(false);

	this.uiReply = uiReply;
	this.populatePageTitle(this.uiReply, "EditCustomerAnswers", "CustomerInfo");

	titanDevice = getDailyRhythmPortalSession().getTitanDevice();
	ca = titanDevice.getCarrierAccount();
	if (customerQABean == null){
	    customerQABean = new CustomerQABean();

	}

	stateSelectionSelected = new SelectItem<String>(customerQABean.getState(), "");

	// firstName
	firstNameTextField = new TextField<String>("firstName", new PropertyModel<String>(ca, "firstName"));

	firstNameTextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.firstName.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.firstName.max.length"))));
	firstNameTextField.setRequired(true);
	firstNameTextField.setMarkupId("firstName");
	firstNameTextField.setOutputMarkupId(true);
	firstNameTextField.setOutputMarkupPlaceholderTag(true);
	firstNameTextField.add(new DefaultFocusBehavior());
	add(firstNameTextField);

	// lastName
	lastNameTextField = new TextField<String>("lastName", new PropertyModel<String>(ca, "lastName"));

	lastNameTextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.lastName.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.lastName.max.length"))));
	lastNameTextField.setRequired(true);
	lastNameTextField.setMarkupId("lastName");
	lastNameTextField.setOutputMarkupId(true);
	lastNameTextField.setOutputMarkupPlaceholderTag(true);
	add(lastNameTextField);

	// phoneNumber
	phoneNumberTextField = new TextField<String>("phoneNumber", new PropertyModel<String>(ca, "phoneNumber"));
	phoneNumberTextField.add(new AjaxFormComponentUpdatingBehavior("onBlur") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onUpdate(AjaxRequestTarget target) {
		PhoneNumberConverter converter = new PhoneNumberConverter();
		phoneNumberTextField.setDefaultModelObject(converter.convertToString(phoneNumberTextField
			.getDefaultModelObject(), null));
		target.add(phoneNumberTextField);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, RuntimeException e) {
		EditCustomerBasicInfoPage.this.getBorder().getNotificationPanel().refresh(target);
	    }

	});
	phoneNumberTextField.add(new PatternValidator("\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})"));
	phoneNumberTextField.setRequired(true);
	phoneNumberTextField.setMarkupId("phoneNumber");
	phoneNumberTextField.setOutputMarkupId(true);
	phoneNumberTextField.setOutputMarkupPlaceholderTag(true);
	add(phoneNumberTextField);

	// email
	emailAddressTextField = new TextField<String>("email", new PropertyModel<String>(ca, "email"));

	emailAddressTextField.add(StringValidator.maximumLength(75));
	emailAddressTextField.add(EmailAddressValidator.getInstance());
	emailAddressTextField.setRequired(true);
	emailAddressTextField.setMarkupId("email");
	emailAddressTextField.setOutputMarkupId(true);
	emailAddressTextField.setOutputMarkupPlaceholderTag(true);
	add(emailAddressTextField);

	// addr1
	addressLine1TextField = new TextField<String>("addr1", new PropertyModel<String>(ca.getAddress(),
		"addressline1"));
	addressLine1TextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.addr1.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.addr1.max.length"))));
	addressLine1TextField.setRequired(true);
	addressLine1TextField.setMarkupId("addr1");
	addressLine1TextField.setOutputMarkupId(true);
	addressLine1TextField.setOutputMarkupPlaceholderTag(true);
	add(addressLine1TextField);

	// addr2
	addressLine2TextField = new TextField<String>("addr2", new PropertyModel<String>(ca.getAddress(),
		"addressline2"));
	addressLine2TextField.add(StringValidator.lengthBetween(Integer
		.parseInt(getString("tradeInCustomerQAPage.addr2.min.length")), Integer
		.parseInt(getString("tradeInCustomerQAPage.addr2.max.length"))));
	addressLine2TextField.setMarkupId("addr2");
	addressLine2TextField.setOutputMarkupId(true);
	addressLine2TextField.setOutputMarkupPlaceholderTag(true);
	add(addressLine2TextField);

	// city
	cityTextField = new TextField<String>("city", new PropertyModel<String>(ca.getAddress(), "city"));
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

	    if (StringUtils.isNotBlank(ca.getAddress().getState())
		    && ca.getAddress().getState().equalsIgnoreCase(state.getStateCode().toUpperCase())){
		stateSelectionSelected = new SelectItem<String>(state.getStateCode(), state.getStateName());
	    }
	}
	ChoiceRenderer<SelectItem<String>> choiceRenderer = new ChoiceRenderer<SelectItem<String>>("value", "key");
	DropDownChoice<SelectItem<String>> stateDropDown = new DropDownChoice<SelectItem<String>>("state",
		new PropertyModel<SelectItem<String>>(this, "stateSelectionSelected"), stateSelections, choiceRenderer);

	stateDropDown.setRequired(true);
	stateDropDown.setMarkupId("state");
	stateDropDown.setOutputMarkupId(true);
	stateDropDown.setOutputMarkupPlaceholderTag(true);
	add(stateDropDown);

	// zip
	zipTextField = new TextField<String>("zip", new PropertyModel<String>(ca.getAddress(), "zip"));
	zipTextField.add(StringValidator.exactLength(5));
	zipTextField.add(new PatternValidator("([0-9]{5})"));
	zipTextField.setRequired(true);
	zipTextField.setMarkupId("zip");
	zipTextField.setOutputMarkupId(true);
	zipTextField.setOutputMarkupPlaceholderTag(true);
	add(zipTextField);

    }

    public SelectItem<String> getStateSelectionSelected() {
	return stateSelectionSelected;
    }

    public void setStateSelectionSelected(SelectItem<String> stateSelectionSelected) {
	this.stateSelectionSelected = stateSelectionSelected;
    }

    /**
     * @see com.bestbuy.bbym.ise.drp.beast.tradein.BeastPage#clearForm(org.apache.wicket.ajax.AjaxRequestTarget)
     */
    @Override
    void clearForm(AjaxRequestTarget target) {
	try{
	    this.stateSelectionSelected = new SelectItem<String>("", "");

	    zipTextField.setDefaultModel(new Model<String>(""));
	    cityTextField.setDefaultModel(new Model<String>(""));
	    addressLine1TextField.setDefaultModel(new Model<String>(""));
	    addressLine2TextField.setDefaultModel(new Model<String>(""));
	    firstNameTextField.setDefaultModel(new Model<String>(""));
	    lastNameTextField.setDefaultModel(new Model<String>(""));
	    emailAddressTextField.setDefaultModel(new Model<String>(""));
	    phoneNumberTextField.setDefaultModel(new Model<String>(""));

	    target.add(this.getBorder().getForm());

	    target.appendJavaScript("bindHotKeys();");
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage());
	    this.displayError(this.getString("EditCustomerBasicInfoPage.service.down.message"), target);
	    return;

	}
    }

    @Override
    void goBack(AjaxRequestTarget target) {
    }

    @Override
    void nextPage(AjaxRequestTarget target, Form<?> form) {
	removeBackButtonParameter();
	try{
	    this.getDailyRhythmPortalSession().saveUIReply(this.getClass().getName(), this.uiReply);

	    uiRequest = (UIRequest) this.uiReply.get("EditCustomerAnswers");
	    UIDataItem dataItem = (UIDataItem) uiRequest.get("CustomerInfo");
	    dataItem.setStringProp("FirstName", ca.getFirstName());
	    dataItem.setStringProp("MiddleName", null);
	    dataItem.setStringProp("LastName", ca.getLastName());
	    dataItem.setStringProp("NameSuffix", null);
	    dataItem.setStringProp("EmailAddress", ca.getEmail());
	    dataItem.setStringProp("PhoneNumber", ca.getPhoneNumber());
	    dataItem.setStringProp("AddressLine1", ca.getAddress().getAddressline1());
	    dataItem.setStringProp("AddressLine2", ca.getAddress().getAddressline2());
	    dataItem.setStringProp("City", ca.getAddress().getCity());
	    dataItem.setStringProp("State", stateSelectionSelected.getKey());
	    dataItem.setStringProp("PostalCode", ca.getAddress().getZip());
	    dataItem.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());

	    this.getDailyRhythmPortalSession().setCustomerQABean(customerQABean);

	    UIReply dynamicQAUIReply = uiService.processUIRequest(uiRequest);
	    UIDataList qList = (UIDataList) dynamicQAUIReply.get("Questions");
	    if (qList == null || qList.isEmpty() || qList.size() == 0){
		UIDataItem requestDataItem = (UIDataItem) ((UIRequest) dynamicQAUIReply.get("LoadGiftCard"))
			.get("EditCustomerAnswers");
		requestDataItem.setStringProp("userId", getDailyRhythmPortalSession().getDrpUser().getUserId());

		dynamicQAUIReply = uiService.processUIRequest((UIRequest) dynamicQAUIReply.get("LoadGiftCard"));
		this.setResponsePage(new LoadGiftCardPage(this.getPageParameters(), dynamicQAUIReply));
	    }else{
		this.setResponsePage(new EditCustomerIDInfoPage(this.getPageParameters(), dynamicQAUIReply));
	    }
	}catch(Exception exc){
	    LOGGER.error(exc.getMessage());
	    this.displayError(this.getString("EditCustomerBasicInfoPage.service.down.message"), target);
	    return;
	}
    }
}
