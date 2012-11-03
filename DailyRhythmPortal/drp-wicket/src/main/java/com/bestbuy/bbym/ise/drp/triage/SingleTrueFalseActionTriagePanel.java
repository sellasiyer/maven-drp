package com.bestbuy.bbym.ise.drp.triage;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.drp.workflow.SingleTrueFalseActionWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.triage.TrueFalseAction;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowService;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

public class SingleTrueFalseActionTriagePanel extends BaseTriagePanel {

    private static final long serialVersionUID = 4254554191711704352L;

    public SingleTrueFalseActionTriagePanel(String id, final WorkflowService workflowService,
	    final SingleTrueFalseActionWorkflowDialog workflowDialog, final Long workflowId) {
	super(id, workflowService, workflowDialog, workflowId);

	Instruction instruction = null;
	if (workflowDialog.getActionDescribed() != null){
	    instruction = workflowDialog.getActionDescribed().getInstruction();
	}
	InstructionsPanel instructionsPanel = new InstructionsPanel("instructionsPanel", instruction);
	triageForm.add(instructionsPanel);

	// question
	Label question = new Label("triageQuestion", new PropertyModel<SingleTrueFalseActionWorkflowDialog>(
		workflowDialog, "actionDescribed.question.title"));
	question.setEscapeModelStrings(false);
	triageForm.add(question);

	// TODO text should come from properties
	AjaxButton yes = new AjaxButton("yes", new Model<String>("Yes")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		submit(true, workflowService, workflowDialog, workflowId);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	yes.setOutputMarkupPlaceholderTag(true);
	triageForm.add(yes);

	// TODO text should come from properties
	AjaxButton no = new AjaxButton("no", new Model<String>("No")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		submit(false, workflowService, workflowDialog, workflowId);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	no.setOutputMarkupPlaceholderTag(true);
	triageForm.add(no);
    }

    private void submit(boolean answer, final WorkflowService workflowService,
	    final SingleTrueFalseActionWorkflowDialog workflowDialog, final Long workflowId) {
	TrueFalseAction action = workflowDialog.getActionDescribed();
	action.getQuestion().setAnswer(answer);
	workflowDialog.setActionTaken(action);
	final PageParameters parameters = new PageParameters();
	DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	WorkflowTaskDialog responseDialog = null;
	try{
	    responseDialog = workflowService.execute(workflowId, workflowDialog, session.getDrpUser());
	}catch(ServiceException e){
	    String message = "An unexpected exception occured while attempting to execute the workflow";
	    processException(message, e, workflowId, "Triage", parameters);
	}
	setResponsePage(new TriagePage(workflowId, responseDialog));
    }
}
