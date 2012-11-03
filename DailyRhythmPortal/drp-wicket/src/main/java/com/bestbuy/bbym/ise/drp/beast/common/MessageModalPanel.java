package com.bestbuy.bbym.ise.drp.beast.common;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

/**
 * @author a885527
 */
public abstract class MessageModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    private boolean okSelected = false;
    private String returnFocusId = "barcode";
    private Page sendRedirectToPage;

    public Page getSendRedirectToPage() {
	return sendRedirectToPage;
    }

    public void setSendRedirectToPage(Page sendRedirectToPage) {
	this.sendRedirectToPage = sendRedirectToPage;
    }

    public String getReturnFocusId() {
	return returnFocusId;
    }

    public void setReturnFocusId(String returnFocusId) {
	this.returnFocusId = returnFocusId;
    }

    public MessageModalPanel(String id, final String okLabel) {
	super(id);

	Label messageLabel = new Label("messageLabel", new String(""));
	add(messageLabel);
	messageLabel.setEscapeModelStrings(false);

	AjaxLink<Void> okLink = new AjaxLink<Void>("okLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		okSelected = true;
		close(target);
	    }
	};
	okLink.add(new Label("okLinkLabel", okLabel));
	add(okLink);
    }

    public boolean isOk() {
	return okSelected;
    }

    public void setMessage(String message) {
	addOrReplace(new Label("messageLabel", message));
    }

    public void setMulitLineMessage(String message) {
	addOrReplace(new MultiLineLabel("messageLabel", message));
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
