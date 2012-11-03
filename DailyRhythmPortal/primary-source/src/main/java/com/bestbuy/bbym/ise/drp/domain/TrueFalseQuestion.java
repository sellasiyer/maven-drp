package com.bestbuy.bbym.ise.drp.domain;

/**
 * @author JAM0314
 */
public class TrueFalseQuestion extends TitleAndDescription {

    private static final long serialVersionUID = -2516027008080758189L;

    public TrueFalseQuestion(String title, String description, String questionCode, long questionInstanceId) {
	super(title, description, questionCode, questionInstanceId);
    }

    Boolean answer = null;

    public Boolean getAnswer() {
	return answer;
    }

    public void setAnswer(Boolean answer) {
	this.answer = answer;
    }
}
