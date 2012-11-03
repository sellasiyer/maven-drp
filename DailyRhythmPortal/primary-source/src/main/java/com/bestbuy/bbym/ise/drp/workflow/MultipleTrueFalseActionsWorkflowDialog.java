/**
 * 
 */
package com.bestbuy.bbym.ise.drp.workflow;

import java.util.List;

import com.bestbuy.bbym.ise.drp.workflow.triage.TrueFalseAction;
import com.bestbuy.bbym.ise.workflow.BaseWorkflowTaskDialog;

/**
 * @author JAM0314
 * 
 */
public class MultipleTrueFalseActionsWorkflowDialog extends BaseWorkflowTaskDialog {

    private static final long serialVersionUID = -424154234967273067L;
    private List<TrueFalseAction> describedActions;
    private TrueFalseAction finalAction;
    private TrueFalseAction actionTaken;

    public MultipleTrueFalseActionsWorkflowDialog(List<TrueFalseAction> actions, TrueFalseAction finalAction) {
	describedActions = actions;
	this.finalAction = finalAction;
    }

    /**
     * @return the level1Question
     */
    public List<TrueFalseAction> getDescribedActions() {
	return describedActions;
    }

    /**
     * @return the finalQuestion
     */
    public TrueFalseAction getFinalAction() {
	return finalAction;
    }

    /**
     * @return the actionTaken
     */
    public TrueFalseAction getActionTaken() {
	return actionTaken;
    }

    /**
     * @param actionTaken
     *            the actionTaken to set
     */
    public void setActionTaken(TrueFalseAction actionTaken) {
	this.actionTaken = actionTaken;
    }

}
