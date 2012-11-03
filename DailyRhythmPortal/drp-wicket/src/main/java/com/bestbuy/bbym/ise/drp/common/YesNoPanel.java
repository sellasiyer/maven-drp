package com.bestbuy.bbym.ise.drp.common;

import java.io.Serializable;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.basic.MultiLineLabel;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * Use this as a simple confirmation dialog. 
 * Re-used code from Tomasz Dziurko @ http://www.mysticcoders.com/blog/wicket-ajax-confirmation-modal-window/
 * @author a885527
 */
public class YesNoPanel extends Panel {

    private static final long serialVersionUID = 1L;
    private final DialogResult dialogResult = new DialogResult();

    private AjaxButton yesButton;
    private AjaxButton noButton;

    public static class DialogResult implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean result = false;

	public void setResult(boolean newResult) {
	    this.result = newResult;
	}

	public boolean isTrue() {
	    return this.result;
	}
    }

    @SuppressWarnings("rawtypes")
    public YesNoPanel(String id, String message, final ModalWindow modalWindow) {
	super(id);

	Form yesNoForm = new Form("yesNoForm");

	MultiLineLabel messageLabel = new MultiLineLabel("message", message);
	add(messageLabel);

	modalWindow.setTitle("Please confirm");
	modalWindow.setInitialHeight(200);
	modalWindow.setInitialWidth(350);

	yesButton = new AjaxButton("yesButton", yesNoForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form form) {
		if (target != null){
		    dialogResult.setResult(true);
		    modalWindow.close(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};

	noButton = new AjaxButton("noButton", yesNoForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form form) {
		if (target != null){
		    dialogResult.setResult(false);
		    modalWindow.close(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};

	yesNoForm.add(yesButton);
	yesNoForm.add(noButton);

	add(yesNoForm);

    }

    public void setYesButtonText(String newText) {
	yesButton.setModel(new Model<String>(newText));
    }

    public void setNoButtonText(String newText) {
	noButton.setModel(new Model<String>(newText));
    }

    public boolean getDialogResult() {
	return dialogResult.isTrue();
    }
}
