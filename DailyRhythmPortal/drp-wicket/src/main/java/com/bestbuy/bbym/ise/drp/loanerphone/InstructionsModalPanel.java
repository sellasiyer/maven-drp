package com.bestbuy.bbym.ise.drp.loanerphone;

import org.apache.wicket.Component;
import org.apache.wicket.PageReference;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BasePanel;

public class InstructionsModalPanel extends BasePanel {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(InstructionsModalPanel.class);

    public InstructionsModalPanel(String id, PageParameters parameters, final PageReference modalWindowPage,
	    final CustomModalWindow window, String instructions) {

	super(id);

	logger.info("in the Instructions modal page...");

	logger.info("instructions in instruction modal page...." + instructions);

	Form<Object> instructionsModalForm = new Form<Object>("instructionsModalForm");
	instructionsModalForm.setOutputMarkupPlaceholderTag(true);
	add(instructionsModalForm);

	final Label instructionLabel = new Label("instructionLabel", new Model<String>(instructions));
	instructionLabel.setOutputMarkupPlaceholderTag(true);
	instructionLabel.setEscapeModelStrings(false);
	instructionsModalForm.add(instructionLabel);

	AjaxButton okButton = new AjaxButton("okButton", instructionsModalForm) {

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

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("setModalPanelFocus('#okButton');");
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});

    }

}
