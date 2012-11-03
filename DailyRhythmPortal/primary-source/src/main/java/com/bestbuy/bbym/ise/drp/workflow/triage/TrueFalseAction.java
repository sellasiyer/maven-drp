/**
 * 
 */
package com.bestbuy.bbym.ise.drp.workflow.triage;

import java.io.Serializable;

import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.drp.domain.TrueFalseQuestion;

/**
 * @author a186288
 * 
 */
public class TrueFalseAction implements Serializable {

    private static final long serialVersionUID = 3896699792960213522L;

    private TrueFalseQuestion question;
    private Instruction instruction;

    /**
     * @return the question
     */
    public TrueFalseQuestion getQuestion() {
	return question;
    }

    /**
     * @param question
     *            the question to set
     */
    public void setQuestion(TrueFalseQuestion question) {
	this.question = question;
    }

    /**
     * @return the instruction
     */
    public Instruction getInstruction() {
	return instruction;
    }

    /**
     * @param instruction
     *            the instruction to set
     */
    public void setInstruction(Instruction instruction) {
	this.instruction = instruction;
    }
}
