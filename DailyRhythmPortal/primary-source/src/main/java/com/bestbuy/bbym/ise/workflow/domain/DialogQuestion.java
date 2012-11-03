package com.bestbuy.bbym.ise.workflow.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.workflow.rules.DialogItem;
import com.bestbuy.bbym.ise.workflow.rules.DialogSelectItem;

public class DialogQuestion extends BaseDataObject implements DialogItem, DialogSelectItem {

    private static final long serialVersionUID = 5028041820508934798L;

    public enum AnswerType {
	SELECT, YES_NO
    }

    private long id;
    private QueryQuestion question;
    private String answerText;
    private long dialogId;
    private int sequenceNum;
    private List<DialogAnswer> choices;
    //
    private AnswerType type = AnswerType.YES_NO;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public QueryQuestion getQuestion() {
	return question;
    }

    public void setQuestion(QueryQuestion question) {
	this.question = question;
    }

    public void setAnswerText(String answerText) {
	this.answerText = answerText;
    }

    public String getAnswerText() {
	return answerText;
    }

    public long getDialogId() {
	return dialogId;
    }

    public void setDialogId(long dialogId) {
	this.dialogId = dialogId;
    }

    public int getSequenceNum() {
	return sequenceNum;
    }

    public void setSequenceNum(int sequenceNum) {
	this.sequenceNum = sequenceNum;
    }

    public List<DialogAnswer> getChoices() {
	return choices;
    }

    public void setChoices(List<DialogAnswer> choices) {
	this.choices = choices;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public List<String> getOptions() {
	if (getChoices() == null){
	    return null;
	}
	List<String> options = new ArrayList<String>();
	for(DialogAnswer da: getChoices()){
	    options.add(da.getName());
	}
	return options;
    }

    @Override
    public void setOptions(List<String> options) {
	if (options == null){
	    setChoices(null);
	    return;
	}
	DialogAnswer da;
	List<DialogAnswer> choices = new ArrayList<DialogAnswer>();
	for(String option: options){
	    da = new DialogAnswer();
	    da.setName(option);
	    choices.add(da);
	}
	setChoices(choices);
    }

    @Override
    public String getName() {
	if (question == null){
	    return null;
	}
	return question.getName();
    }

    @Override
    public void setName(String name) {
	if (question == null){
	    question = new QueryQuestion();
	}
	question.setName(name);
    }

    @Override
    public void setAnswer(String answer) {
	answerText = answer;
    }

    @Override
    public String getAnswer() {
	return answerText;
    }

    public void setType(AnswerType type) {
	this.type = type;
    }

    public AnswerType getType() {
	return type;
    }

}
