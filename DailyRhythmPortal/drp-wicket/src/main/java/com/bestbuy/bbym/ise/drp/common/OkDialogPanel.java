package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;

public abstract class OkDialogPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    public OkDialogPanel(String id, String okLabel) {
	super(id);

	Label messageLabel = new Label("messageLabel", new String(""));
	messageLabel.setVisible(false);
	add(messageLabel);

	AjaxLink<Void> okLink = new AjaxLink<Void>("okLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		close(target);
	    }
	};
	okLink.add(new Label("okLinkLabel", okLabel));
	okLink.setMarkupId("okLink");
	okLink.setOutputMarkupId(true);
	okLink.setOutputMarkupPlaceholderTag(true);
	add(okLink);
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
	return "setModalPanelFocus('#okLink');";
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
