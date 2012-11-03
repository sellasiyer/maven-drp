package com.bestbuy.bbym.ise.workflow.rules;

import java.util.Map;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.workflow.WorkflowBuilder;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.Workflow;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;

public interface WorkflowDao {

    public void setSchemaName(String schemaName);

    public Workflow getWorkflow(long workflowId) throws DataAccessException;

    // returns workflowId
    public long addWorkflow(WorkflowBuilder<?> workflowBuilder, User employee) throws DataAccessException;

    // returns workflowId
    public long addWorkflow(WorkflowBuilder<?> workflowBuilder, User employee, String status)
	    throws DataAccessException;

    public void persistWorkflow(Workflow workflow, User employee) throws DataAccessException;

    public WorkflowStep getLastWorkflowStep(long workflowId) throws DataAccessException;

    public void removeLastWorkflowStep(long workflowId) throws DataAccessException;

    public Dialog getLastDialog(long workflowId) throws DataAccessException;

    public Dialog getDialog(long dialogId) throws DataAccessException;

    // returns dialogId
    public long addDialog(long workflowId, Dialog dialog, User employee) throws DataAccessException;

    public void persistDialog(Dialog dialog, User employee) throws DataAccessException;

    public Map<String, WorkflowAttribute> getWorkflowAttributeMap(long workflowId) throws DataAccessException;

    public void persistWorkflowAttributeMap(Map<String, WorkflowAttribute> newWorkflowAttributeMap, long workflowId,
	    User employee) throws DataAccessException;
}
