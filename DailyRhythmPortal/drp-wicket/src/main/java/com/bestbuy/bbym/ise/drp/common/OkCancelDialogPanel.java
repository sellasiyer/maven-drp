package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

public abstract class OkCancelDialogPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    private boolean okSelected = false;

    public OkCancelDialogPanel(String id, final String okLabel, final String cancelLabel) {
	super(id);

	Label messageLabel = new Label("messageLabel", new String(""));
	messageLabel.setVisible(false);
	add(messageLabel);

	Label questionLabel = new Label("questionLabel", new String(""));
	questionLabel.setVisible(false);
	add(questionLabel);

	AjaxLink<Void> okLink = new AjaxLink<Void>("okLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		okSelected = true;
		close(target);
	    }
	};
	okLink.add(new Label("okLinkLabel", okLabel));
	okLink.setMarkupId("okLink");
	okLink.setOutputMarkupId(true);
	okLink.setOutputMarkupPlaceholderTag(true);
	add(okLink);

	AjaxLink<Void> cancelLink = new AjaxLink<Void>("cancelLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		okSelected = false;
		close(target);
	    }
	};
	cancelLink.add(new Label("cancelLinkLabel", cancelLabel));
	cancelLink.setMarkupId("cancelLink");
	cancelLink.setOutputMarkupId(true);
	cancelLink.setOutputMarkupPlaceholderTag(true);
	add(cancelLink);
    }

    public boolean isOk() {
	return okSelected;
    }

    public void setMessage(String message) {
	addOrReplace(new Label("messageLabel", message));
    }

    public void setQuestion(String question) {
	addOrReplace(new Label("questionLabel", question));
    }

    @Override
    public String getOpenModalJS() {
	return "setModalPanelFocus('#okLink');";
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
