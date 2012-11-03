package com.bestbuy.bbym.ise.drp.common.modal;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

public abstract class MessageDialogPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    public MessageDialogPanel(final String id, final String modalTitle) {
	super(id);

	add(new Label("modalTitle", modalTitle));

	Label messageLabel = new Label("messageLabel", new String(""));
	messageLabel.setVisible(false);
	add(messageLabel);

	final AjaxLink<Object> closeLink = new AjaxLink<Object>("closeLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		close(target);
	    }
	};
	closeLink.setMarkupId("closeLink");
	closeLink.setOutputMarkupId(true);
	closeLink.setOutputMarkupPlaceholderTag(true);
	add(closeLink);
    }

    public void setMessage(String message) {
	setMessage(message, true);
    }

    public void setMessage(String message, boolean escapeModelStrings) {
	Label messageLabel = new Label("messageLabel", message);
	messageLabel.setEscapeModelStrings(escapeModelStrings);
	addOrReplace(messageLabel);
    }

    @Override
    public String getOpenModalJS() {
	return "setModalPanelFocus('#closeLink');";
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }

    @Override
    public String getModalSelector() {
	return "#" + getId() + " .new-modal";
    }
}
