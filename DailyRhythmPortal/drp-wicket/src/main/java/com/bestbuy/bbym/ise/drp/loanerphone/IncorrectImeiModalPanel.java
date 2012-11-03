package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;

public class IncorrectImeiModalPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(IncorrectImeiModalPanel.class);

    public IncorrectImeiModalPanel(String id, PageParameters parameters, final PageReference modalWindowPage,
	    final CustomModalWindow window, String message) {
	super(id);

	logger.info("in the incorrect imei modal page...");

	Form<Object> incorrectImeiModalForm = new Form<Object>("incorrectImeiModalForm");
	incorrectImeiModalForm.setOutputMarkupPlaceholderTag(true);
	add(incorrectImeiModalForm);

	final Label messageLabel = new Label("messageLabel", new Model<String>(message));
	messageLabel.setOutputMarkupPlaceholderTag(true);
	messageLabel.setEscapeModelStrings(false);
	incorrectImeiModalForm.add(messageLabel);

	AjaxButton okButton = new AjaxButton("okButton", incorrectImeiModalForm) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		if (target != null){
		    window.close(target);
		}
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	okButton.setMarkupId("okButton");
	okButton.setOutputMarkupPlaceholderTag(true);
	add(okButton);

    }

}
