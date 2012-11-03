package com.bestbuy.bbym.ise.drp.entitlement;

import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.domain.EntitlementLookup;

public abstract class InvalidSerialModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(InvalidSerialModalPanel.class);

    InvalidSerialModalPanel parentModalPanel;
    TextField<String> serialNumberText;
    Form<Object> invalidSerialNumForm;

    private String serialNumber;
    private boolean continueWithoutManufacturerSerial;
    private boolean cancelCalled;

    public InvalidSerialModalPanel(String id, EntitlementLookup entitlementLookup) {
	super(id);

	invalidSerialNumForm = new Form<Object>("invalidSerialNumForm");
	invalidSerialNumForm.setOutputMarkupPlaceholderTag(true);

	serialNumberText = new TextField<String>("serialNumber", new PropertyModel<String>(entitlementLookup,
		"manufacturerSerialNumber"));
	serialNumberText.setOutputMarkupId(true);
	serialNumberText.setMarkupId("serialNumber");
	serialNumberText.setOutputMarkupPlaceholderTag(true);

	invalidSerialNumForm.add(serialNumberText);

	final CheckBox noSerialCheck = new CheckBox("noSerialCheck", new Model<Boolean>(false));
	noSerialCheck.setOutputMarkupId(true);
	noSerialCheck.setMarkupId("noSerialCheck");
	noSerialCheck.setOutputMarkupPlaceholderTag(true);

	noSerialCheck.add(new AjaxEventBehavior("onclick") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onEvent(AjaxRequestTarget target) {
		target.appendJavaScript("handleFormFields();");

	    }

	});

	invalidSerialNumForm.add(noSerialCheck);

	final AjaxButton cancelButton = new AjaxButton("cancelButton", new ResourceModel(
		"entitlement.purchaseview.cancelButton")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		setCancelCalled(true);
		close(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
	    }
	};
	cancelButton.setOutputMarkupPlaceholderTag(true);
	cancelButton.setDefaultFormProcessing(false);
	invalidSerialNumForm.add(cancelButton);

	/* Page Submit Button... 
	pageSubmitButton = new PageSubmitButton(entitlementLookup, feedbackPanel);
	invalidSerialNumForm.add(pageSubmitButton);
	
	 *
	 */

	final AjaxButton submitButton = new AjaxButton("submitButton") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

		setCancelCalled(false);

		if (noSerialCheck != null && noSerialCheck.getModelObject()){
		    setContinueWithoutManufacturerSerial(true);
		}else{
		    setSerialNumber(serialNumberText.getDefaultModelObjectAsString());
		    setContinueWithoutManufacturerSerial(false);
		}
		close(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("Error in Invalid Serial Modal Panel");
	    }

	};
	submitButton.setOutputMarkupId(true);
	submitButton.setOutputMarkupPlaceholderTag(true);
	invalidSerialNumForm.add(submitButton);

	add(invalidSerialNumForm);

    }

    @Override
    public void update(AjaxRequestTarget target) {
	target.add(invalidSerialNumForm);

    }

    public String getSerialNumber() {
	return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
    }

    public boolean isContinueWithoutManufacturerSerial() {
	return continueWithoutManufacturerSerial;
    }

    public void setContinueWithoutManufacturerSerial(boolean continueWithoutManufacturerSerial) {
	this.continueWithoutManufacturerSerial = continueWithoutManufacturerSerial;
    }

    public boolean isCancelCalled() {
	return cancelCalled;
    }

    public void setCancelCalled(boolean cancelCalled) {
	this.cancelCalled = cancelCalled;
    }

    @Override
    public String getModalSelector() {
	return "#" + getId() + " .new-modal";
    }
}
