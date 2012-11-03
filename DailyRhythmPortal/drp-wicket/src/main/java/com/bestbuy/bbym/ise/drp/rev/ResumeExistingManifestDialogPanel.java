package com.bestbuy.bbym.ise.drp.rev;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

public abstract class ResumeExistingManifestDialogPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    // State Data
    private boolean yesPressed = true;

    public ResumeExistingManifestDialogPanel(String id) {
	super(id);

	AjaxLink<Void> yesLink = new AjaxLink<Void>("yesLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		yesPressed = true;
		close(target);
	    }
	};
	yesLink.setMarkupId("yesLink");
	yesLink.setOutputMarkupId(true);
	yesLink.setOutputMarkupPlaceholderTag(true);
	add(yesLink);

	AjaxLink<Void> noLink = new AjaxLink<Void>("noLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		yesPressed = false;
		close(target);
	    }
	};
	noLink.setMarkupId("noLink");
	noLink.setOutputMarkupId(true);
	noLink.setOutputMarkupPlaceholderTag(true);
	add(noLink);
    }

    @Override
    public String getOpenModalJS() {
	return "setModalPanelFocus('#yesLink');";
    }

    public boolean isYesPressed() {
	return yesPressed;
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
