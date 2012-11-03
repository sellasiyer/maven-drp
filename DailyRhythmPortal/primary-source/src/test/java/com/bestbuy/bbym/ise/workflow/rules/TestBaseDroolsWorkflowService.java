package com.bestbuy.bbym.ise.workflow.rules;

import static org.easymock.EasyMock.cmp;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.isA;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.annotation.Resource;

import org.drools.KnowledgeBase;
import org.easymock.LogicalOperator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.Workflow;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:TestBaseRulesWorkflowService-context.xml" })
public class TestBaseDroolsWorkflowService {

    private Logger logger = LoggerFactory.getLogger(TestBaseDroolsWorkflowService.class);

    @Resource
    private KnowledgeBase kbase;

    @Resource
    private WorkflowDao workflowDao;

    @Resource
    private BaseDroolsWorkflowServiceTestImpl service;

    private User testUser = new User();

    @Before
    public void setUp() {
	testUser.setUserId("a777");
    }

    @Test
    public void testCreate() throws ServiceException, DataAccessException {
	TestWorkflowBuilder wfBuilder = new TestWorkflowBuilder(TestWorkflowType.TEST1);

	resetAll();

	expect(workflowDao.addWorkflow(eq(wfBuilder), eq(testUser))).andReturn(123L);

	replayAll();

	long workflowId = service.create(wfBuilder, testUser);
	assertEquals(123L, workflowId);
    }

    @Test
    public void testBackup() throws ServiceException, DataAccessException {
	long workflowId = 123L;
	Dialog lastDialog = new Dialog();
	lastDialog.setId(234L);
	lastDialog.setName("TestWorkflowTaskDialog");

	resetAll();

	workflowDao.removeLastWorkflowStep(eq(workflowId));
	expect(workflowDao.getLastDialog(workflowId)).andReturn(lastDialog);

	replayAll();

	WorkflowTaskDialog taskDialog = service.backup(workflowId, testUser);
	assertNotNull(taskDialog);
	assertEquals(234L, taskDialog.getId());
	assertEquals("TestWorkflowTaskDialog", taskDialog.getClass().getSimpleName());
    }

    @Test
    public void testExecuteStartWorkflow() throws ServiceException, DataAccessException {
	long workflowId = 1L;
	long dialogId = 123L;
	Workflow wf = new Workflow();
	wf.setId(workflowId);
	TestSession session = new TestSession();
	Dialog newDialog = new Dialog();
	newDialog.setId(dialogId);
	WorkflowStep step = new WorkflowStep();
	step.setSequenceNum(1);
	newDialog.setStep(step);

	resetAll();

	expect(workflowDao.getWorkflow(workflowId)).andReturn(wf);
	expect(kbase.newStatefulKnowledgeSession()).andReturn(session);
	workflowDao.persistWorkflow(eq(wf), eq(testUser));
	expect(workflowDao.addDialog(eq(workflowId), isA(Dialog.class), eq(testUser))).andReturn(dialogId);
	expect(workflowDao.getDialog(eq(dialogId))).andReturn(newDialog);

	replayAll();

	WorkflowTaskDialog taskDialog = service.execute(workflowId, testUser);
	assertNotNull(taskDialog);
	assertEquals(123L, taskDialog.getId());
	assertEquals("TestWorkflowTaskDialog", taskDialog.getClass().getSimpleName());
	assertEquals(1, taskDialog.getStepSequence());
    }

    @Test
    public void testExecuteContinueWorkflow() throws ServiceException, DataAccessException {
	long workflowId = 2L;
	long oldDdialogId = 123L, newDialogId = 155L;
	Workflow wf = new Workflow();
	wf.setId(workflowId);
	TestSession session = new TestSession();
	TestWorkflowTaskDialog oldDialog = new TestWorkflowTaskDialog();
	oldDialog.setId(oldDdialogId);
	//
	Dialog newDialog = new Dialog();
	newDialog.setId(newDialogId);
	WorkflowStep step = new WorkflowStep();
	step.setSequenceNum(2);
	newDialog.setStep(step);

	resetAll();

	expect(workflowDao.getWorkflow(workflowId)).andReturn(wf);
	expect(kbase.newStatefulKnowledgeSession()).andReturn(session);
	workflowDao.persistDialog(isA(Dialog.class), eq(testUser));
	expect(workflowDao.addDialog(eq(workflowId), isA(Dialog.class), eq(testUser))).andReturn(newDialogId);
	expect(workflowDao.getDialog(eq(newDialogId))).andReturn(newDialog);

	replayAll();

	WorkflowTaskDialog taskDialog = service.execute(workflowId, oldDialog, testUser);
	assertNotNull(taskDialog);
	assertEquals(155L, taskDialog.getId());
	assertEquals("TestWorkflowTaskDialog", taskDialog.getClass().getSimpleName());
	assertEquals(2, taskDialog.getStepSequence());
    }

    @Test
    public void testExecuteEndWorkflow() throws ServiceException, DataAccessException {
	long workflowId = 3L;
	long oldDdialogId = 123L;
	Workflow wf = new Workflow();
	wf.setId(workflowId);
	TestSession session = new TestSession();
	TestWorkflowTaskDialog oldDialog = new TestWorkflowTaskDialog();
	oldDialog.setId(oldDdialogId);

	resetAll();

	expect(workflowDao.getWorkflow(workflowId)).andReturn(wf);
	expect(kbase.newStatefulKnowledgeSession()).andReturn(session);
	workflowDao.persistDialog(isA(Dialog.class), eq(testUser));
	workflowDao.persistWorkflow(eq(wf), eq(testUser));

	replayAll();

	WorkflowTaskDialog taskDialog = service.execute(workflowId, oldDialog, testUser);
	assertNull(taskDialog);
    }

    @Test
    public void testFinish() throws ServiceException, DataAccessException {
	long workflowId = 123L;
	String status = "GOOD";
	Workflow wf = new Workflow(), wf2 = new Workflow();
	wf.setId(workflowId);
	wf2.setId(workflowId);
	wf2.setStatus(status);

	resetAll();

	expect(workflowDao.getWorkflow(workflowId)).andReturn(wf);
	workflowDao.persistWorkflow(cmp(wf2, new TestWorkflowComp(), LogicalOperator.EQUAL), eq(testUser));

	replayAll();

	service.finish(workflowId, status, testUser);
    }

    private void resetAll() {
	reset(kbase);
	reset(workflowDao);
    }

    private void replayAll() {
	replay(kbase);
	replay(workflowDao);
    }
}
