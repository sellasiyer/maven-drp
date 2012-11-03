/**
 * 
 */
package com.bestbuy.bbym.ise.drp.workflow;

import com.bestbuy.bbym.ise.drp.workflow.triage.TrueFalseAction;
import com.bestbuy.bbym.ise.workflow.BaseWorkflowTaskDialog;

/**
 * @author JAM0314
 * 
 */
public class SingleTrueFalseActionWorkflowDialog extends BaseWorkflowTaskDialog {

    private static final long serialVersionUID = 3896699792960213522L;

    private TrueFalseAction actionDescribed;
    private TrueFalseAction actionTaken;

    public SingleTrueFalseActionWorkflowDialog(TrueFalseAction trueFalseTriageAction) {
	this.actionDescribed = trueFalseTriageAction;
    }

    /**
     * @return the level1Action
     */
    public TrueFalseAction getActionDescribed() {
	return actionDescribed;
    }

    public void setActionTaken(TrueFalseAction actionTaken) {
	this.actionTaken = actionTaken;
    }

    public TrueFalseAction getActionTaken() {
	return actionTaken;
    }
}
