package com.bestbuy.bbym.ise.workflow.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class DialogAnswer extends BaseDataObject {

    private static final long serialVersionUID = 3180638971116727569L;

    private long id;
    private String name;
    private String answer;
    private int sequenceNum;
    private long dialogQuestionId;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAnswer() {
	return answer;
    }

    public void setAnswer(String answer) {
	this.answer = answer;
    }

    public void setSequenceNum(int sequenceNum) {
	this.sequenceNum = sequenceNum;
    }

    public int getSequenceNum() {
	return sequenceNum;
    }

    public long getDialogQuestionId() {
	return dialogQuestionId;
    }

    public void setDialogQuestionId(long dialogQuestionId) {
	this.dialogQuestionId = dialogQuestionId;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
