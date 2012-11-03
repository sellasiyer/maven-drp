package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.ResourceModel;

import com.bestbuy.bbym.ise.drp.common.ModalPanel;

public class OptinNotAllowedModalPanel extends ModalPanel {

    private static final long serialVersionUID = 1L;

    public OptinNotAllowedModalPanel(String id) {
	super(id);

	add(new Label("messageLabel", new ResourceModel("optinNotAllowedModalPanel.message.label")));

	final Form<Object> form = new Form<Object>("form");
	add(form);

	AjaxButton okButton = new AjaxButton("okButton", new ResourceModel("okLabel"), form) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		close(target);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	okButton.setMarkupId("okButton");
	okButton.setOutputMarkupId(true);
	okButton.setOutputMarkupPlaceholderTag(true);
	form.add(okButton);
    }

    @Override
    public String getOpenModalJS() {
	return "setModalPanelFocus('#okButton');";
    }

    @Override
    public void onClose(AjaxRequestTarget target) {
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }
}
