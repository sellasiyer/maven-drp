/**
 * 
 */
package com.bestbuy.bbym.ise.drp.workflow;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.drp.domain.TitleAndDescription;
import com.bestbuy.bbym.ise.workflow.BaseWorkflowTaskDialog;

/**
 * @author a186288
 * 
 */
public class SingleChoiceWorkflowDialog extends BaseWorkflowTaskDialog {

    private static final long serialVersionUID = 1535003579593511159L;

    private List<TitleAndDescription> options = new ArrayList<TitleAndDescription>();
    private TitleAndDescription selectedOption;
    private TitleAndDescription question;
    private Instruction instruction;
    private boolean renderRadio = false;

    public SingleChoiceWorkflowDialog(List<TitleAndDescription> options, TitleAndDescription question,
	    Instruction instruction, boolean renderRadio) {
	this.options = options;
	this.question = question;
	this.instruction = instruction;
	this.renderRadio = renderRadio;
    }

    public SingleChoiceWorkflowDialog(List<TitleAndDescription> options, TitleAndDescription question,
	    Instruction instruction) {
	this.options = options;
	this.question = question;
	this.instruction = instruction;
    }

    /**
     * @return the selectedOption
     */
    public TitleAndDescription getSelectedOption() {
	return selectedOption;
    }

    /**
     * @param selectedOption
     *            the selectedOption to set
     */
    public void setSelectedOption(TitleAndDescription selectedOption) {
	this.selectedOption = selectedOption;
    }

    /**
     * @return the options
     */
    public List<TitleAndDescription> getOptions() {
	return options;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion(TitleAndDescription question) {
	this.question = question;
    }

    /**
     * @return the question
     */
    public TitleAndDescription getQuestion() {
	return question;
    }

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

    /**
     * @return the renderRadio
     */
    public boolean isRenderRadio() {
	return renderRadio;
    }

    public void setRenderRadio(boolean renderRadio) {
	this.renderRadio = renderRadio;
    }
}
