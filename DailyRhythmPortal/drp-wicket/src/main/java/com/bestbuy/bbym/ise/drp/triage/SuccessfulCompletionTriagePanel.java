/**
 * 
 */
package com.bestbuy.bbym.ise.drp.triage;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.workflow.SuccessfulCompletionWorkflowDialog;
import com.bestbuy.bbym.ise.workflow.WorkflowService;

/**
 * @author JAM0314
 * 
 */
public class SuccessfulCompletionTriagePanel extends BaseTriagePanel {

    //private static Logger logger = LoggerFactory.getLogger(SuccessfulCompletionTriagePanel.class);

    private static final long serialVersionUID = 4254554191711704352L;

    public SuccessfulCompletionTriagePanel(String id, final WorkflowService workflowService,
	    final SuccessfulCompletionWorkflowDialog workflowDialog, final Long workflowId) {
	super(id, workflowService, workflowDialog, workflowId);

	// do not display successful completion page,
	// go back to customer dashboard page
	if (workflowDialog.getInstruction() == null
		|| "goto_dashboard".equals(workflowDialog.getInstruction().getCode())){
	    throw new RestartResponseException(CustomerDashboardPage.class);
	}

	Label instructionLabel = new Label("instructionLabel", new PropertyModel<SuccessfulCompletionWorkflowDialog>(
		workflowDialog, "instruction.title"));
	instructionLabel.setEscapeModelStrings(false);
	triageForm.add(instructionLabel);

	Label instructionDetail = new Label("instructionDetail", new PropertyModel<SuccessfulCompletionWorkflowDialog>(
		workflowDialog, "instruction.description"));
	instructionDetail.setEscapeModelStrings(false);
	triageForm.add(instructionDetail);

	// TODO text should come from properties
	AjaxButton returnMain = new AjaxButton("returnMain", new Model<String>("Finish")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		throw new RestartResponseException(CustomerDashboardPage.class);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		// TODO Implement me!
	    }
	};
	returnMain.setOutputMarkupPlaceholderTag(true);
	triageForm.add(returnMain);
    }
}
