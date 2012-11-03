package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;

public abstract class ServiceBasePanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    public ServiceBasePanel(String id) {
	super(id);
    }

    public ServiceBasePanel(String id, IModel<?> model) {
	super(id, model);
    }

    // Return error or null if no error
    public abstract String doInitialServiceCalls();

    public abstract void updatePanel(AjaxRequestTarget target);
}
