package com.bestbuy.bbym.ise.workflow.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Represents a specific, distinct step in the workflow process. It is
 * referenced by the planID and the stepId. It contains a reference to the task
 * used to execute the step and houses the result of the execution.
 */
public class WorkflowStep extends BaseDataObject {

    private static final long serialVersionUID = -7849633417472923567L;

    private long id;
    private int taskId;
    private String name;
    private String comment;
    private Date startTime;
    private Date endTime;
    private int result;
    private int sequenceNum;
    private long workflowId;
    private int stepId;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public int getTaskId() {
	return taskId;
    }

    public void setTaskId(int taskId) {
	this.taskId = taskId;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public void setComment(String comment) {
	this.comment = comment;
    }

    public String getComment() {
	return comment;
    }

    public Date getStartTime() {
	return startTime;
    }

    public void setStartTime(Date startTime) {
	this.startTime = startTime;
    }

    public Date getEndTime() {
	return endTime;
    }

    public void setEndTime(Date endTime) {
	this.endTime = endTime;
    }

    public int getResult() {
	return result;
    }

    public void setResult(int result) {
	this.result = result;
    }

    public void setSequenceNum(int sequenceNum) {
	this.sequenceNum = sequenceNum;
    }

    public int getSequenceNum() {
	return sequenceNum;
    }

    public void setWorkflowId(long workflowId) {
	this.workflowId = workflowId;
    }

    public long getWorkflowId() {
	return workflowId;
    }

    public void setStepId(int stepId) {
	this.stepId = stepId;
    }

    public int getStepId() {
	return stepId;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
