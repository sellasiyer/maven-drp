package com.bestbuy.bbym.ise.workflow.rules;

import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

public interface RulesWorkflowState {

    // represents where we are in the workflow (state machine)
    public int getStep();

    public void setStep(int step);

    public WorkflowDialog getDialog() throws ServiceException;

    public void setDialog(WorkflowDialog dialog) throws ServiceException;

    public WorkflowDialog replaceWorkflowDialog(Class<? extends WorkflowTaskDialog> dialogType) throws ServiceException;

    public long getWorkflowId();

    public void setWorkflowId(long workflowId);
}
