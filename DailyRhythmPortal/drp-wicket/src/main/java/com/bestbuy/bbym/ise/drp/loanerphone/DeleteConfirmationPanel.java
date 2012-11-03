package com.bestbuy.bbym.ise.drp.loanerphone;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.model.Model;

import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneCondition;
import com.bestbuy.bbym.ise.drp.domain.LoanerPhoneDeletedReason;
import com.bestbuy.bbym.ise.drp.domain.Phone;

public class DeleteConfirmationPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private final Observer observer;

    public interface Observer extends Serializable {

	public void delete(AjaxRequestTarget target, LoanerPhoneDeletedReason deletedReason);

	public void cancel(AjaxRequestTarget target);
    }

    public DeleteConfirmationPanel(String id, Phone phone, Observer observer) {
	super(id);
	this.observer = observer;

	Form confirmationForm = new Form("confirmationForm");
	add(confirmationForm);

	final RadioGroup<LoanerPhoneDeletedReason> deletedReasonRadioGroup = new RadioGroup<LoanerPhoneDeletedReason>(
		"deletedReasonRadioGroup", new Model<LoanerPhoneDeletedReason>());
	Radio<LoanerPhoneDeletedReason> deletedReasonLostRadio = new Radio<LoanerPhoneDeletedReason>(
		"deletedReasonLostRadio", new Model<LoanerPhoneDeletedReason>(LoanerPhoneDeletedReason.LOST));
	Radio<LoanerPhoneDeletedReason> deletedReasonDamagedRadio = new Radio<LoanerPhoneDeletedReason>(
		"deletedReasonDamagedRadio", new Model<LoanerPhoneDeletedReason>(LoanerPhoneDeletedReason.DAMAGED));
	Radio<LoanerPhoneDeletedReason> deletedReasonOtherRadio = new Radio<LoanerPhoneDeletedReason>(
		"deletedReasonOtherRadio", new Model<LoanerPhoneDeletedReason>(LoanerPhoneDeletedReason.OTHER));
	deletedReasonRadioGroup.setRequired(true);
	if (phone.getCondition() == LoanerPhoneCondition.DAMAGED){
	    deletedReasonRadioGroup.setDefaultModelObject(LoanerPhoneDeletedReason.DAMAGED);
	}else if (phone.getCondition() == LoanerPhoneCondition.LOST){
	    deletedReasonRadioGroup.setDefaultModelObject(LoanerPhoneDeletedReason.LOST);
	}else{
	    deletedReasonRadioGroup.setDefaultModelObject(LoanerPhoneDeletedReason.OTHER);
	}
	confirmationForm.add(deletedReasonRadioGroup);
	deletedReasonRadioGroup.add(deletedReasonLostRadio);
	deletedReasonRadioGroup.add(deletedReasonDamagedRadio);
	deletedReasonRadioGroup.add(deletedReasonOtherRadio);

	AjaxButton deleteButton = new AjaxButton("deleteButton", confirmationForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form form) {
		DeleteConfirmationPanel.this.observer.delete(target, (LoanerPhoneDeletedReason) deletedReasonRadioGroup
			.getDefaultModelObject());
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	confirmationForm.add(deleteButton);

	AjaxButton cancelButton = new AjaxButton("cancelButton", confirmationForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form form) {
		DeleteConfirmationPanel.this.observer.cancel(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	cancelButton.setDefaultFormProcessing(false);
	confirmationForm.add(cancelButton);
    }
}
