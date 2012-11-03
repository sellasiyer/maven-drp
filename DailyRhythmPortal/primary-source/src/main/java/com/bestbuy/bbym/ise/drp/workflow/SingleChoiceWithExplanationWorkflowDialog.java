/**
 * 
 */
package com.bestbuy.bbym.ise.drp.workflow;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.drp.domain.TitleAndDescription;

/**
 * @author JAM0314
 * 
 */
public class SingleChoiceWithExplanationWorkflowDialog extends SingleChoiceWorkflowDialog {

    private static final long serialVersionUID = -5069523536860510044L;

    private String explanation;

    public SingleChoiceWithExplanationWorkflowDialog(List<TitleAndDescription> options, TitleAndDescription question,
	    Instruction instruction) {
	super(options, question, instruction);
    }

    public void setExplanation(String explanation) {
	this.explanation = explanation;
    }

    public String getExplanation() {
	return explanation;
    }

}
