package com.bestbuy.bbym.ise.workflow.rules;

import java.util.Date;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.runtime.StatefulKnowledgeSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowBuilder;
import com.bestbuy.bbym.ise.workflow.WorkflowService;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.Workflow;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;

public abstract class BaseDroolsWorkflowService<S extends RulesWorkflowState> implements WorkflowService {

    private static final long serialVersionUID = -7188773296281619311L;
    private static Logger logger = LoggerFactory.getLogger(BaseDroolsWorkflowService.class);

    public BaseDroolsWorkflowService(KnowledgeBase kbase, WorkflowDao workflowDao) {
	this.kbase = kbase;
	this.workflowDao = workflowDao;
    }

    public BaseDroolsWorkflowService(String rulesFile, WorkflowDao workflowDao) {
	this.rulesFile = rulesFile;
	this.workflowDao = workflowDao;
	try{
	    initialize();
	}catch(ServiceException e){
	    logger.error("BaseDroolsWorkflowService failed to initialize", e);
	}
    }

    protected WorkflowDao workflowDao;

    private String rulesFile;

    private KnowledgeBase kbase;

    private void initialize() throws ServiceException {
	logger.debug("calling initalize on base drools");
	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
	kbuilder.add(ResourceFactory.newClassPathResource(getRulesFile()), ResourceType.DRL);
	KnowledgeBuilderErrors errors = kbuilder.getErrors();
	if (errors.size() > 0){
	    for(KnowledgeBuilderError error: errors){
		logger.error("Failed to build KnowledgeBuilder: " + error.getMessage());
	    }
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem);
	}
	logger.debug("built knowledge builder");
	kbase = KnowledgeBaseFactory.newKnowledgeBase();
	logger.info("built kbase");
	kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
	logger.info("added packages");
    }

    @Override
    public long create(WorkflowBuilder<?> workflowBuilder, User user) throws ServiceException {
	String type = workflowBuilder.getWorkflowType();
	try{
	    return workflowDao.addWorkflow(workflowBuilder, user);
	}catch(DataAccessException e){
	    logger.error("Failed to add workflow of type " + type, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public WorkflowTaskDialog backup(long workflowId, User user) throws ServiceException {
	try{
	    workflowDao.removeLastWorkflowStep(workflowId);
	}catch(DataAccessException e){
	    logger.error("Failed to remove last step for workflow with ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	try{
	    Dialog lastDialog = workflowDao.getLastDialog(workflowId);
	    if (lastDialog == null){
		return null;
	    }else{
		return convertToWorkflowTaskDialog(lastDialog);
	    }
	}catch(DataAccessException e){
	    logger.error("Failed to get last dialog for workflow with ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public WorkflowTaskDialog execute(long workflowId, User user) throws ServiceException {
	return execute(workflowId, null, user);
    }

    @Override
    public WorkflowTaskDialog execute(long workflowId, WorkflowTaskDialog dialog, User user) throws ServiceException {
	Workflow workflow = null;
	Dialog dlg;
	try{
	    workflow = workflowDao.getWorkflow(workflowId);
	}catch(DataAccessException e){
	    logger.error("Failed to get workflow with ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	StatefulKnowledgeSession session = kbase.newStatefulKnowledgeSession();
	S rulesWorkflowState = getWorkflowState(workflowId);
	int stepId = rulesWorkflowState.getStep();

	if (dialog == null){
	    // Restart of workflow, get last dialog and return it
	    if (workflow.getStartTime() != null){
		try{
		    Dialog lastDialog = workflowDao.getLastDialog(workflowId);
		    if (lastDialog == null){
			return null;
		    }else{
			return convertToWorkflowTaskDialog(lastDialog);
		    }
		}catch(DataAccessException e){
		    logger.error("Failed to get last dialog for workflow with ID " + workflowId, e);
		    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
		}
		// Start of workflow
	    }else{
		rulesWorkflowState.setDialog(null);
		workflow.setStartTime(new Date());
		try{
		    workflowDao.persistWorkflow(workflow, user);
		}catch(DataAccessException e){
		    logger.error("Failed to persist workflow with ID " + workflowId, e);
		    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
		}
	    }
	    // Continuation of workflow
	}else{
	    dlg = convertToDialog(dialog);
	    rulesWorkflowState.setDialog(dlg);
	    try{
		workflowDao.persistDialog(dlg, user);
	    }catch(DataAccessException e){
		logger.error("Failed to persist dialog with ID " + dlg.getId(), e);
		throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	    }
	}

	session.insert(rulesWorkflowState);
	session.fireAllRules();
	session.dispose();
	WorkflowDialog newDialog = rulesWorkflowState.getDialog();

	String status = "COMPLETE";
	if (rulesWorkflowState.getStep() == stepId){
	    status = getRulesProblemStatus();
	    logger.info("Rules did not return new dialog, ending workflow ID " + workflowId);
	    newDialog = null;
	}

	if (newDialog == null){
	    workflow.setEndTime(new Date());
	    workflow.setStatus(status);
	    try{
		workflowDao.persistWorkflow(workflow, user);
	    }catch(DataAccessException e){
		logger.error("Failed to persist workflow with ID " + workflowId, e);
		throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	    }
	    persistWorkflowState(rulesWorkflowState, user);
	    return null;
	}

	try{
	    dlg = (Dialog) newDialog;
	    WorkflowStep ws = new WorkflowStep();
	    ws.setStepId(rulesWorkflowState.getStep());
	    dlg.setStep(ws);
	    long dialogId = workflowDao.addDialog(workflowId, dlg, user);
	    persistWorkflowState(rulesWorkflowState, user);
	    return convertToWorkflowTaskDialog(workflowDao.getDialog(dialogId));
	}catch(DataAccessException e){
	    logger.error("Failed to add dialog with workflow ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}catch(ClassCastException e){
	    logger.error("Failed to add dialog with workflow ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public void finish(long workflowId, String status, User user) throws ServiceException {
	try{
	    Workflow workflow = workflowDao.getWorkflow(workflowId);
	    workflow.setStatus(status);
	    workflow.setEndTime(new Date());
	    workflowDao.persistWorkflow(workflow, user);
	}catch(DataAccessException e){
	    logger.error("Failed to finish the workflow with ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    public abstract S getWorkflowState(long workflowId) throws ServiceException;

    public abstract void persistWorkflowState(S rulesWorkflowState, User user) throws ServiceException;

    // populate question and answer text
    public abstract WorkflowTaskDialog convertToWorkflowTaskDialog(Dialog queryDialog) throws ServiceException;

    // Must store answers to questions
    public abstract Dialog convertToDialog(WorkflowTaskDialog workflowTaskDialog) throws ServiceException;

    public abstract String getRulesProblemStatus();

    public String getRulesFile() {
	return rulesFile;
    }

    public void setRulesFile(String rulesFile) {
	this.rulesFile = rulesFile;
    }

    public void setWorkflowDao(WorkflowDao workflowDao) {
	this.workflowDao = workflowDao;
    }

    public void setKbase(KnowledgeBase kbase) {
	this.kbase = kbase;
    }
}
