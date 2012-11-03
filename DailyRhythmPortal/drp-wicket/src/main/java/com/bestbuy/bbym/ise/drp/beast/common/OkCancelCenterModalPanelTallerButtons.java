package com.bestbuy.bbym.ise.drp.beast.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.basic.MultiLineLabel;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

public abstract class OkCancelCenterModalPanelTallerButtons extends ModalPanel {

    private static final long serialVersionUID = 1L;

    private boolean okSelected = false;

    public OkCancelCenterModalPanelTallerButtons(String id, final String okLabel, final String cancelLabel) {
	super(id);
	setVisible(false);

	add(new Label("questionLabel", new String("")));

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

	AjaxLink<Void> cancelLink = new AjaxLink<Void>("cancelLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		okSelected = false;
		close(target);
	    }
	};
	cancelLink.add(new Label("cancelLinkLabel", cancelLabel));
	add(cancelLink);
    }

    public boolean isOk() {
	return okSelected;
    }

    public void setOkSelected(boolean okSelected) {
	this.okSelected = okSelected;
    }

    public void setQuestion(String question) {
	addOrReplace(new Label("questionLabel", question));
    }

    public void setMultiLineQuestion(String question) {
	addOrReplace(new MultiLineLabel("questionLabel", question));
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
