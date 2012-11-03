/**
 * 
 */
package com.bestbuy.bbym.ise.drp.triage;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWithExplanationWorkflowDialog;
import com.bestbuy.bbym.ise.workflow.WorkflowService;

/**
 * @author JAM0314
 * 
 */
public class SingleChoiceWithExplanationTriagePanel extends SingleChoiceTriagePanel {

    private static final long serialVersionUID = 5331477525490043573L;

    public SingleChoiceWithExplanationTriagePanel(String id, final WorkflowService workflow,
	    final SingleChoiceWithExplanationWorkflowDialog workflowDialog, final Long workflowId) {
	super(id, workflow, workflowDialog, workflowId);

	triageForm.add(new TextArea<SingleChoiceWithExplanationWorkflowDialog>("explanation",
		new PropertyModel<SingleChoiceWithExplanationWorkflowDialog>(workflowDialog, "explanation")));
    }

}
