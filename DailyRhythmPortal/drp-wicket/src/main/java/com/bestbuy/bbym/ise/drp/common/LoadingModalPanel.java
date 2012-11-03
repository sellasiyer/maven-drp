package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.basic.Label;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

public abstract class LoadingModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    public LoadingModalPanel(String id) {
	super(id);
	add(new Label("messageLabel", new String("")));
    }

    public void setMessage(String message) {
	addOrReplace(new Label("messageLabel", message));
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
