package com.bestbuy.bbym.ise.workflow;

import java.io.Serializable;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Controls the execution of a workflow. It delegates to tasks, dialogs, steps
 * and plans to accomplish its work .
 */
public interface WorkflowService extends Serializable {

    public long create(WorkflowBuilder<?> workflowBuilder, User user) throws ServiceException;

    /**
     * Backup one step in workflow and return that WorkflowDialog.
     */
    public WorkflowTaskDialog backup(long workflowId, User user) throws ServiceException;

    /**
     * Returns first WorkflowDialog encountered. If workflow has no interactive
     * tasks, the entire workflow will be run to completion. If returns
     * {@code null}, this indicates workflow completed
     */
    public WorkflowTaskDialog execute(long workflowId, User user) throws ServiceException;

    /**
     * Returns next WorkflowDialog encountered If workflow has no more
     * interactive tasks, the entire workflow will be run to completion. If
     * returns {@code null}, this indicates workflow completed
     */
    public WorkflowTaskDialog execute(long workflowId, WorkflowTaskDialog oldWorkflowDialog, User user)
	    throws ServiceException;

    /**
     * Completes the workflow.
     */
    public void finish(long workflowId, String status, User user) throws ServiceException;
}
