package com.bestbuy.bbym.ise.drp.workflow.triage;

import java.util.Map;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;
import com.bestbuy.bbym.ise.workflow.rules.RulesWorkflowState;
import com.bestbuy.bbym.ise.workflow.rules.WorkflowDialog;

public class TriageWorkflowState implements RulesWorkflowState {

    private int step = 0;
    private WorkflowDialog dialog;
    private Device device;
    private long workflowId;

    public enum Attribute {
	DEV_OS, DEV_SKU, DEV_HAS_BLACK_TIE, DEV_BLACK_TIE_NUM, PROBLEM_TYPE
    }

    public enum PROBLEM_TYPE {
	PHYSICAL_DAMAGE, BATTERY_ISSUE, SOFTWARE_ISSUE, HARDWARE_ISSUE
    };

    private PROBLEM_TYPE issue;

    public PROBLEM_TYPE getIssue() {
	return issue;
    }

    public void setIssue(PROBLEM_TYPE issue) {
	this.issue = issue;
    }

    public void setIssue(String issue) {
	if (issue == null){
	    this.issue = null;
	}else{
	    this.issue = PROBLEM_TYPE.valueOf(issue.toUpperCase());
	}
    }

    @Override
    public WorkflowDialog getDialog() throws ServiceException {
	return dialog;
    }

    @Override
    public void setDialog(WorkflowDialog dialog) throws ServiceException {
	this.dialog = dialog;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public int getStep() {
	return step;
    }

    @Override
    public void setStep(int stepId) {
	step = stepId;
    }

    @Override
    public WorkflowDialog replaceWorkflowDialog(Class<? extends WorkflowTaskDialog> dialogType) throws ServiceException {
	Dialog newDialog = new Dialog();
	newDialog.setName(dialogType.getSimpleName());
	dialog = newDialog;
	return dialog;
    }

    public void setDevice(Device device) {
	this.device = device;
    }

    public Device getDevice() {
	return device;
    }

    public void setWorkflowId(long workflowId) {
	this.workflowId = workflowId;
    }

    public long getWorkflowId() {
	return workflowId;
    }

    public static String getAttributeValue(Attribute attribute, Map<String, WorkflowAttribute> attributeMap) {
	if (attribute == null || attributeMap == null){
	    return null;
	}
	WorkflowAttribute wa = attributeMap.get(attribute.name());
	if (wa == null){
	    return null;
	}
	return wa.getValue();
    }

    public static void setAttributeValue(Attribute attribute, String value, Map<String, WorkflowAttribute> attributeMap) {
	if (attribute == null || attributeMap == null){
	    return;
	}
	WorkflowAttribute wa = attributeMap.get(attribute.name());
	if (wa == null){
	    if (value != null){
		wa = new WorkflowAttribute();
		wa.setName(attribute.name());
		wa.setValue(value);
		attributeMap.put(attribute.name(), wa);
	    }
	    return;
	}
	if (wa.getValue() == null && value == null){
	    // both values null, do nothing
	}else if (wa.getValue() != null && wa.getValue().equals(value)){
	    // both values same, do nothing
	}else{
	    wa.setValue(value);
	    attributeMap.put(attribute.name(), wa);
	}
    }
}
