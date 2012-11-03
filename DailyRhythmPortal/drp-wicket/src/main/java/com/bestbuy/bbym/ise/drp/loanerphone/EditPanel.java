package com.bestbuy.bbym.ise.drp.loanerphone;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.service.LoanerPhoneService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class EditPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(EditPanel.class);

    @SpringBean(name = "loanerPhoneService")
    private LoanerPhoneService loanerPhoneService;

    private Phone phone;

    public EditPanel(String id) {
	super(id);
	this.phone = new Phone();
	this.phone.setCarrier(new Carrier());
	this.phone.setCondition(LoanerPhoneCondition.GOOD);
	construct();
    }

    public EditPanel(String id, Phone phone) {
	super(id);
	this.phone = phone;
	construct();
    }

    private void construct() {
	List<Carrier> carrierList = null;
	List<OperatingSystem> osList = null;
	try{
	    carrierList = loanerPhoneService.getCarriers();
	    osList = loanerPhoneService.getOperatingSystems();
	}catch(ServiceException se){
	    String message = "An unexpected exception occured while attempting to get the carriers and operating systems";
	    logger.error(message, se);
	    processException(message, se, getPage().getPageParameters());
	    return;
	}

	final TextField<String> carrierLoanerSkuTextField = new TextField<String>("carrierLoanerSkuTextField",
		new PropertyModel<String>(phone.getCarrier(), "carrierLoanerSku"));
	carrierLoanerSkuTextField.setEnabled(false);
	carrierLoanerSkuTextField.setOutputMarkupId(true);
	add(carrierLoanerSkuTextField);

	final DropDownChoice<Carrier> carrierDropDownChoice = new DropDownChoice<Carrier>("carrierDropDownChoice",
		new PropertyModel<Carrier>(phone, "carrier"), carrierList, new ChoiceRenderer<Carrier>("carrier", "id"));

	// Trigger the loaner SKU field to change when a carrier is
	// selected. This technique requires a round trip to the server each
	// time the user selects a carrier and thus might not provide a good
	// user
	// experience on a slow network.
	carrierDropDownChoice.add(new AjaxFormComponentUpdatingBehavior("onchange") {

	    private static final long serialVersionUID = 1L;

	    protected void onUpdate(AjaxRequestTarget target) {
		Carrier carrier = (Carrier) carrierDropDownChoice.getDefaultModelObject();
		if (carrier != null){
		    carrierLoanerSkuTextField.setDefaultModelObject(carrier.getCarrierLoanerSku());
		}else{
		    carrierLoanerSkuTextField.setDefaultModelObject(null);
		}
		target.add(carrierLoanerSkuTextField);
	    }
	});
	carrierDropDownChoice.setRequired(true);
	add(carrierDropDownChoice);

	DropDownChoice<OperatingSystem> operatingSystemDropDownChoice = new DropDownChoice<OperatingSystem>(
		"operatingSystemDropDownChoice", new PropertyModel<OperatingSystem>(phone, "operatingSystem"), osList,
		new ChoiceRenderer<OperatingSystem>("os", "id"));
	operatingSystemDropDownChoice.setRequired(true);
	add(operatingSystemDropDownChoice);

	TextField<String> makeTextField = new TextField<String>("makeTextField", new PropertyModel<String>(phone,
		"make"));
	makeTextField.setRequired(true);
	makeTextField.add(new MaximumLengthValidator(100));
	makeTextField.setOutputMarkupId(true);
	add(makeTextField);
	add(new AbstractAutocompleteAjaxBehavior(makeTextField) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String[] getData() {
		try{
		    return loanerPhoneService.getDistinctMakes(EditPanel.this.getStoreID());
		}catch(ServiceException ex){
		    logger.error(ex.getMessage(), ex);
		    return null;
		}
	    }
	});

	TextField<String> modelTextField = new TextField<String>("modelNumberTextField", new PropertyModel<String>(
		phone, "modelNumber"));
	modelTextField.setRequired(true);
	modelTextField.add(new MaximumLengthValidator(100));
	modelTextField.setOutputMarkupId(true);
	add(modelTextField);
	add(new AbstractAutocompleteAjaxBehavior(modelTextField) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String[] getData() {
		try{
		    return loanerPhoneService.getDistinctModels(EditPanel.this.getStoreID());
		}catch(ServiceException ex){
		    logger.error(ex.getMessage(), ex);
		    return null;
		}
	    }
	});

	TextField<String> serialNumberTextField = new TextField<String>("serialNumberTextField",
		new PropertyModel<String>(phone, "serialNumber"));
	serialNumberTextField.setRequired(true);
	serialNumberTextField.add(new MaximumLengthValidator(20));
	serialNumberTextField.add(new PatternValidator("[a-zA-Z0-9]+"));
	add(serialNumberTextField);

	final RadioGroup<LoanerPhoneCondition> conditionRadioGroup = new RadioGroup<LoanerPhoneCondition>(
		"conditionRadioGroup", new PropertyModel<LoanerPhoneCondition>(phone, "condition"));
	Radio<LoanerPhoneCondition> conditionGoodRadio = new Radio<LoanerPhoneCondition>("conditionGoodRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.GOOD));
	Radio<LoanerPhoneCondition> conditionFairRadio = new Radio<LoanerPhoneCondition>("conditionFairRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.FAIR));
	Radio<LoanerPhoneCondition> conditionPoorRadio = new Radio<LoanerPhoneCondition>("conditionPoorRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.POOR));
	WebMarkupContainer damagedLostRadioContainer = new WebMarkupContainer("damagedLostRadioContainer") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public boolean isVisible() {
		return phone.getId() != null;
	    }

	};
	add(damagedLostRadioContainer);
	Radio<LoanerPhoneCondition> conditionLostRadio = new Radio<LoanerPhoneCondition>("conditionLostRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.LOST));
	damagedLostRadioContainer.add(conditionLostRadio);

	Radio<LoanerPhoneCondition> conditionDamagedRadio = new Radio<LoanerPhoneCondition>("conditionDamagedRadio",
		new Model<LoanerPhoneCondition>(LoanerPhoneCondition.DAMAGED));
	damagedLostRadioContainer.add(conditionDamagedRadio);
	add(conditionRadioGroup);

	conditionRadioGroup.add(conditionGoodRadio);
	conditionRadioGroup.add(conditionFairRadio);
	conditionRadioGroup.add(conditionPoorRadio);
	conditionRadioGroup.add(damagedLostRadioContainer);

	TextArea<String> conditionCommentTextArea = new TextArea<String>("conditionCommentTextArea",
		new PropertyModel<String>(phone, "conditionComment"));
	conditionCommentTextArea.add(new MaximumLengthValidator(500));
	add(conditionCommentTextArea);

	FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);
    }

    public Phone getPhone() {
	return phone;
    }
}
