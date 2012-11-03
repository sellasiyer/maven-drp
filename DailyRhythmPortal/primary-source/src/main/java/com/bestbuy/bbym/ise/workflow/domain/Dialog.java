package com.bestbuy.bbym.ise.workflow.domain;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.workflow.rules.DialogItem;
import com.bestbuy.bbym.ise.workflow.rules.DialogSelectItem;
import com.bestbuy.bbym.ise.workflow.rules.WorkflowDialog;

public class Dialog extends BaseDataObject implements WorkflowDialog {

    private static final long serialVersionUID = 1520849639541239682L;

    private long id;
    private String name;
    private WorkflowStep step;
    private List<DialogQuestion> questionList;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    @Override
    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public WorkflowStep getStep() {
	return step;
    }

    public void setStep(WorkflowStep step) {
	this.step = step;
    }

    public List<DialogQuestion> getQuestionList() {
	return questionList;
    }

    public void setQuestionList(List<DialogQuestion> questionList) {
	this.questionList = questionList;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public List<DialogItem> getDialogItems() {
	if (questionList == null){
	    return null;
	}
	List<DialogItem> dialogItemList = new ArrayList<DialogItem>();
	for(DialogQuestion qa: questionList){
	    dialogItemList.add(qa);
	}
	return dialogItemList;
    }

    @Override
    public DialogSelectItem addDialogSelectItem(String name) {
	DialogQuestion qa = new DialogQuestion();
	qa.setType(DialogQuestion.AnswerType.SELECT);
	qa.setName(name);
	if (questionList == null){
	    questionList = new ArrayList<DialogQuestion>();
	}
	questionList.add(qa);
	return qa;
    }

    @Override
    public DialogItem addDialogItem(String name) {
	DialogQuestion qa = new DialogQuestion();
	qa.setType(DialogQuestion.AnswerType.YES_NO);
	qa.setName(name);
	if (questionList == null){
	    questionList = new ArrayList<DialogQuestion>();
	}
	questionList.add(qa);
	return qa;
    }
}
