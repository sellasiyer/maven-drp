package com.bestbuy.bbym.ise.drp.beast.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.Model;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

/**
 * Modal panel that display the service error details.
 */
// TODO Handle modal dialogs in a consistent manner 
public abstract class ServiceErrorMessageModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    public ServiceErrorMessageModalPanel(String id, final String okLabel) {
	super(id);
	setVisible(false);

	add(new Label("messageLabel", new String("")));

	add(new TextArea<String>("errorMessageText"));

	AjaxLink<Void> okLink = new AjaxLink<Void>("okLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		close(target);
	    }
	};
	okLink.add(new Label("okLinkLabel", okLabel));
	add(okLink);

    }

    public void setMessageLabel(String message) {
	addOrReplace(new Label("messageLabel", message));
    }

    public void setErrorMessage(String errorMsg) {
	addOrReplace(new TextArea<String>("errorMessageText", new Model<String>(errorMsg)));
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
