package com.bestbuy.bbym.ise.drp.triage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.AbstractSingleSelectChoice;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RadioChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.domain.TitleAndDescription;
import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWithExplanationWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWorkflowDialog;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowService;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

public class SingleChoiceTriagePanel extends BaseTriagePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SingleChoiceTriagePanel.class);

    public SingleChoiceTriagePanel(String id, final WorkflowService workflowService,
	    final SingleChoiceWorkflowDialog workflowDialog, final Long workflowId) {
	super(id, workflowService, workflowDialog, workflowId);
	final FeedbackPanel feedbackPanel = this.feedbackPanel;

	InstructionsPanel instructionsPanel = new InstructionsPanel("instructionsPanel", workflowDialog
		.getInstruction());
	triageForm.add(instructionsPanel);

	// TODO text should come from properties
	AjaxButton submit = new AjaxButton("submit", new Model<String>("Submit")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		final PageParameters parameters = new PageParameters();
		WorkflowTaskDialog responseDialog = null;
		try{
		    responseDialog = workflowService.execute(workflowId, workflowDialog, session.getDrpUser());
		}catch(ServiceException e){
		    String message = "An unexpected exception occured while attempting to execute the workflow";
		    processException(message, e, workflowId, "Triage", parameters);
		}

		setResponsePage(new TriagePage(workflowId, responseDialog));
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		target.add(feedbackPanel);
	    }
	};
	submit.setOutputMarkupPlaceholderTag(true);
	triageForm.add(submit);

	// options label
	Label reasonOptionsLabel = new Label("reasonOptionsLabel",
		new PropertyModel<SingleChoiceWithExplanationWorkflowDialog>(workflowDialog, "question.title"));
	reasonOptionsLabel.setEscapeModelStrings(false);
	triageForm.add(reasonOptionsLabel);

	// determine whether to render with radio buttons or with drop down
	ChoiceRenderer<TitleAndDescription> renderer = new ChoiceRenderer<TitleAndDescription>("title", "code");
	AbstractSingleSelectChoice<TitleAndDescription> choice;
	AbstractSingleSelectChoice<TitleAndDescription> dummyChoice;

	for(TitleAndDescription option: workflowDialog.getOptions()){
	    logger.debug("OPTION=" + option);
	}

	if (workflowDialog.isRenderRadio()){
	    choice = new RadioChoice<TitleAndDescription>("optionsRadio", new PropertyModel<TitleAndDescription>(
		    workflowDialog, "selectedOption"), workflowDialog.getOptions(), renderer);
	    dummyChoice = new DropDownChoice<TitleAndDescription>("optionsDropDown");
	    dummyChoice.setVisible(false);
	}else{
	    choice = new DropDownChoice<TitleAndDescription>("optionsDropDown", new PropertyModel<TitleAndDescription>(
		    workflowDialog, "selectedOption"), workflowDialog.getOptions(), renderer);
	    dummyChoice = new DropDownChoice<TitleAndDescription>("optionsRadio");
	    dummyChoice.setVisible(false);
	}
	choice.setEscapeModelStrings(false);
	choice.setRequired(true);
	triageForm.add(choice);
	triageForm.add(dummyChoice);
    }
}
