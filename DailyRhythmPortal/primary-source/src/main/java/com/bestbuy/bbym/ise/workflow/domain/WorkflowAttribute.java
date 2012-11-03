package com.bestbuy.bbym.ise.workflow.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class WorkflowAttribute extends BaseDataObject {

    private static final long serialVersionUID = -8105912375095051485L;

    private long id;
    private String name;
    private String value;
    private long stepId;

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

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public void setStepId(long stepId) {
	this.stepId = stepId;
    }

    public long getStepId() {
	return stepId;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public WorkflowAttribute copy() {
	WorkflowAttribute wa = new WorkflowAttribute();
	wa.setId(getId());
	wa.setName(getName());
	wa.setValue(getValue());
	wa.setStepId(getStepId());
	return wa;
    }
}
