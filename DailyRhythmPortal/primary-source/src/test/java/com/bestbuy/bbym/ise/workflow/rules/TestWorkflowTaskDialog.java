package com.bestbuy.bbym.ise.workflow.rules;

import org.junit.Ignore;

import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

@Ignore
public class TestWorkflowTaskDialog implements WorkflowTaskDialog {

    private static final long serialVersionUID = -8176492978327864378L;

    private long id;
    private String name;
    private int stepSequence;

    @Override
    public long getId() {
	return id;
    }

    @Override
    public void setId(long id) {
	this.id = id;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return name;
    }

    @Override
    public int getStepSequence() {
	return stepSequence;
    }

    @Override
    public void setStepSequence(int stepSequence) {
	this.stepSequence = stepSequence;
    }

}
