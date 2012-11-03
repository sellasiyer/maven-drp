/**
 * 
 */
package com.bestbuy.bbym.ise.drp.workflow;

import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.workflow.BaseWorkflowTaskDialog;

/**
 * @author a186288
 * 
 */
public class SuccessfulCompletionWorkflowDialog extends BaseWorkflowTaskDialog {

    private static final long serialVersionUID = 2494752655704306157L;

    private Instruction instruction;

    /**
     * @param instruction
     *            the instruction to set
     */
    public void setInstruction(Instruction instruction) {
	this.instruction = instruction;
    }

    /**
     * @return the instruction
     */
    public Instruction getInstruction() {
	return instruction;
    }
}
