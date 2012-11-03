package com.bestbuy.bbym.ise.workflow.rules;

import org.junit.Ignore;

import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

@Ignore
public class TestRulesWorkflowState implements RulesWorkflowState {

    private int step;
    private WorkflowDialog dialog;
    private long workflowId;

    @Override
    public int getStep() {
	return step;
    }

    @Override
    public void setStep(int step) {
	this.step = step;
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
    public WorkflowDialog replaceWorkflowDialog(Class<? extends WorkflowTaskDialog> dialogType) throws ServiceException {
	return null;
    }

    @Override
    public long getWorkflowId() {
	return workflowId;
    }

    @Override
    public void setWorkflowId(long workflowId) {
	this.workflowId = workflowId;
    }

}
