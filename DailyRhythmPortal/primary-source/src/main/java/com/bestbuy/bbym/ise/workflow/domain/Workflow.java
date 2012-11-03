package com.bestbuy.bbym.ise.workflow.domain;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.Customer;

public class Workflow extends BaseDataObject {

    private static final long serialVersionUID = 1647319790681875261L;

    private long id;
    private int planId;
    private Customer customer;
    private String type;
    private Date startTime;
    private Date endTime;
    private String comment;
    private String status;
    private List<WorkflowAttribute> attributeList;

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public void setPlanId(int planId) {
	this.planId = planId;
    }

    public int getPlanId() {
	return planId;
    }

    public Customer getCustomer() {
	return customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
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

    public void setComment(String comment) {
	this.comment = comment;
    }

    public String getComment() {
	return comment;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public void setAttributeList(List<WorkflowAttribute> attributeList) {
	this.attributeList = attributeList;
    }

    public List<WorkflowAttribute> getAttributeList() {
	return attributeList;
    }

    public boolean isCompleted() {
	return endTime != null;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
