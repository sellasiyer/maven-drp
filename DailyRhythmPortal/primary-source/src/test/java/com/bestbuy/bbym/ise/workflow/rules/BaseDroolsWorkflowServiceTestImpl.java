package com.bestbuy.bbym.ise.workflow.rules;

import org.drools.KnowledgeBase;
import org.junit.Ignore;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;

@Ignore
public class BaseDroolsWorkflowServiceTestImpl extends BaseDroolsWorkflowService<TestRulesWorkflowState> {

    private static final long serialVersionUID = 3856920006488339245L;

    public BaseDroolsWorkflowServiceTestImpl(KnowledgeBase kbase, WorkflowDao workflowDao) {
	super(kbase, workflowDao);
    }

    public BaseDroolsWorkflowServiceTestImpl(String rulesFile, WorkflowDao workflowDao) {
	super(rulesFile, workflowDao);
    }

    @Override
    public TestRulesWorkflowState getWorkflowState(long workflowId) throws ServiceException {
	TestRulesWorkflowState wfState = new TestRulesWorkflowState();
	wfState.setWorkflowId(workflowId);
	return wfState;
    }

    @Override
    public void persistWorkflowState(TestRulesWorkflowState rulesWorkflowState, User user) throws ServiceException {
    }

    @Override
    public WorkflowTaskDialog convertToWorkflowTaskDialog(Dialog queryDialog) throws ServiceException {
	TestWorkflowTaskDialog taskDialog = new TestWorkflowTaskDialog();
	taskDialog.setId(queryDialog.getId());
	taskDialog.setName(queryDialog.getName());
	if (queryDialog.getStep() != null){
	    taskDialog.setStepSequence(queryDialog.getStep().getSequenceNum());
	}
	return taskDialog;
    }

    @Override
    public Dialog convertToDialog(WorkflowTaskDialog workflowTaskDialog) throws ServiceException {
	Dialog dialog = new Dialog();
	dialog.setId(workflowTaskDialog.getId());
	dialog.setName(workflowTaskDialog.getClass().getSimpleName());
	return dialog;
    }

    @Override
    public String getRulesProblemStatus() {
	return "BAD_STEP";
    }

}
