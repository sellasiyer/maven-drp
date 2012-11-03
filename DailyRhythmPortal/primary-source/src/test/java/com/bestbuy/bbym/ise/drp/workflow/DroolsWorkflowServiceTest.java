package com.bestbuy.bbym.ise.drp.workflow;

import static org.easymock.EasyMock.cmp;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.reset;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.easymock.LogicalOperator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.TitleAndDescription;
import com.bestbuy.bbym.ise.drp.workflow.triage.TriageWorkflowState;
import com.bestbuy.bbym.ise.drp.workflow.triage.TrueFalseAction;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.DialogAnswer;
import com.bestbuy.bbym.ise.workflow.domain.DialogQuestion;
import com.bestbuy.bbym.ise.workflow.domain.QueryQuestion;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;
import com.bestbuy.bbym.ise.workflow.rules.WorkflowDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:TestDroolsWorkflowService-context.xml" })
@Ignore
public class DroolsWorkflowServiceTest {

    @Resource
    private DroolsWorkflowService droolsWorkflowService;

    @Resource
    private WorkflowDao workflowDao;

    private User testUser = new User();

    @Before
    public void setUp() {
	testUser.setUserId("a777");
    }

    @Test
    public void testGetWorkflowState() throws ServiceException, DataAccessException {
	long workflowId = 123L;
	WorkflowStep lastStep = new WorkflowStep();
	lastStep.setStepId(456);
	Map<String, WorkflowAttribute> attributeMap = new HashMap<String, WorkflowAttribute>();
	WorkflowAttribute wa = new WorkflowAttribute();
	wa.setName(TriageWorkflowState.Attribute.PROBLEM_TYPE.name());
	wa.setValue(TriageWorkflowState.PROBLEM_TYPE.BATTERY_ISSUE.name());
	attributeMap.put(wa.getName(), wa);
	//
	wa = new WorkflowAttribute();
	wa.setName(TriageWorkflowState.Attribute.DEV_OS.name());
	wa.setValue("ios");
	attributeMap.put(wa.getName(), wa);
	//
	wa = new WorkflowAttribute();
	wa.setName(TriageWorkflowState.Attribute.DEV_HAS_BLACK_TIE.name());
	wa.setValue("Y");
	attributeMap.put(wa.getName(), wa);

	resetAll();

	expect(workflowDao.getLastWorkflowStep(workflowId)).andReturn(lastStep);
	expect(workflowDao.getWorkflowAttributeMap(workflowId)).andReturn(attributeMap);

	replayAll();

	TriageWorkflowState tws = droolsWorkflowService.getWorkflowState(workflowId);
	assertNotNull(tws);
	assertEquals(lastStep.getStepId(), tws.getStep());
	assertNotNull(tws.getDevice());
	assertNotNull("ios", tws.getDevice().getOs());
	assertNotNull(tws.getDevice().getBlackTieProtection());
	assertTrue(tws.getDevice().getBlackTieProtection().booleanValue());
	assertNotNull(tws.getIssue());
	assertEquals(TriageWorkflowState.PROBLEM_TYPE.BATTERY_ISSUE.name(), tws.getIssue().name());
    }

    @Test
    public void testPersistWorkflowState() throws ServiceException, DataAccessException {
	long workflowId = 123L;
	TriageWorkflowState tws = new TriageWorkflowState();
	tws.setWorkflowId(workflowId);
	tws.setIssue(TriageWorkflowState.PROBLEM_TYPE.BATTERY_ISSUE);
	tws.setDevice(new Device());
	tws.getDevice().setOs("android");
	tws.getDevice().setBlackTieProtection(Boolean.FALSE);
	//
	Map<String, WorkflowAttribute> attributeMap = new HashMap<String, WorkflowAttribute>();
	WorkflowAttribute wa = new WorkflowAttribute();
	wa.setName(TriageWorkflowState.Attribute.PROBLEM_TYPE.name());
	wa.setValue(TriageWorkflowState.PROBLEM_TYPE.BATTERY_ISSUE.name());
	attributeMap.put(wa.getName(), wa);
	//
	wa = new WorkflowAttribute();
	wa.setName(TriageWorkflowState.Attribute.DEV_OS.name());
	wa.setValue("android");
	attributeMap.put(wa.getName(), wa);
	//
	wa = new WorkflowAttribute();
	wa.setName(TriageWorkflowState.Attribute.DEV_HAS_BLACK_TIE.name());
	wa.setValue("N");
	attributeMap.put(wa.getName(), wa);

	resetAll();

	workflowDao.persistWorkflowAttributeMap(cmp(attributeMap, new AttributeMapComparator(), LogicalOperator.EQUAL),
		eq(workflowId), eq(testUser));

	replayAll();

	droolsWorkflowService.persistWorkflowState(tws, testUser);
    }

    @Test
    public void testSingleChoiceWorkflowDialogConverters() {
	Dialog dialog = new Dialog();
	dialog.setId(33L);
	dialog.setName(SingleChoiceWorkflowDialog.class.getSimpleName());
	WorkflowStep step = new WorkflowStep();
	step.setSequenceNum(22);
	dialog.setStep(step);

	List<DialogQuestion> dlgQuestions = new ArrayList<DialogQuestion>();

	DialogQuestion dlgQuestion = new DialogQuestion();
	dlgQuestion.setId(44L);
	QueryQuestion question = new QueryQuestion();
	question.setName("issue_type");
	question.setQuestion("Question question");
	question.setInstruction("Question instruction");
	dlgQuestion.setQuestion(question);

	DialogAnswer batt = new DialogAnswer();
	batt.setName("battery_issue");
	batt.setAnswer("Battery Issue");

	DialogAnswer phys = new DialogAnswer();
	phys.setName("physical_damage");
	phys.setAnswer("Physical Damage");

	DialogAnswer soft = new DialogAnswer();
	soft.setName("software_issue");
	soft.setAnswer("Software Issue");

	dlgQuestion.setChoices(Arrays.asList(batt, phys, soft));

	dlgQuestions.add(dlgQuestion);
	dialog.setQuestionList(dlgQuestions);

	SingleChoiceWorkflowDialog workflowTaskDialog = null;
	try{
	    workflowTaskDialog = (SingleChoiceWorkflowDialog) droolsWorkflowService.convertToWorkflowTaskDialog(dialog);
	}catch(ServiceException e){
	    fail();
	}

	assertNotNull(workflowTaskDialog);
	assertEquals(33L, workflowTaskDialog.getId());
	assertNotNull(workflowTaskDialog.getQuestion());
	assertEquals("issue_type", workflowTaskDialog.getQuestion().getCode());
	assertEquals(44L, workflowTaskDialog.getQuestion().getInstanceId());
	assertEquals("Question question", workflowTaskDialog.getQuestion().getTitle());
	assertNotNull(workflowTaskDialog.getInstruction());
	assertEquals("Question instruction", workflowTaskDialog.getInstruction().getTitle());
	List<TitleAndDescription> options = workflowTaskDialog.getOptions();
	assertNotNull(options);
	assertEquals(3, options.size());
	assertEquals("battery_issue", options.get(0).getCode());
	assertEquals("Battery Issue", options.get(0).getTitle());
	assertEquals("physical_damage", options.get(1).getCode());
	assertEquals("Physical Damage", options.get(1).getTitle());
	assertEquals("software_issue", options.get(2).getCode());
	assertEquals("Software Issue", options.get(2).getTitle());

	// convert back to internal service dialog
	// fail due to no selected option
	dialog = null;
	try{
	    dialog = droolsWorkflowService.convertToDialog(workflowTaskDialog);
	}catch(ServiceException e){
	    assertNotNull(e);
	}

	// convert back to internal service dialog
	workflowTaskDialog.setSelectedOption(new TitleAndDescription("Software Issue", null, "software_issue"));
	try{
	    dialog = droolsWorkflowService.convertToDialog(workflowTaskDialog);
	}catch(ServiceException e){
	    fail();
	}

	assertNotNull(dialog);
	assertEquals(dialog.getName(), SingleChoiceWorkflowDialog.class.getSimpleName());
	List<DialogQuestion> questionList = dialog.getQuestionList();
	assertNotNull(questionList);
	assertEquals(1, questionList.size());
	assertNotNull(questionList.get(0));
	assertEquals("software_issue", questionList.get(0).getAnswerText());
	assertEquals(22, workflowTaskDialog.getStepSequence());
    }

    @Test
    public void testSingleTrueFalseActionWorkflowDialogConverters() {
	Dialog dialog = new Dialog();
	dialog.setId(33L);
	dialog.setName(SingleTrueFalseActionWorkflowDialog.class.getSimpleName());
	WorkflowStep step = new WorkflowStep();
	step.setSequenceNum(22);
	dialog.setStep(step);

	List<DialogQuestion> dlgQuestions = new ArrayList<DialogQuestion>();

	DialogQuestion dlgQuestion = new DialogQuestion();
	dlgQuestion.setId(55L);
	QueryQuestion question = new QueryQuestion();
	dlgQuestion.setQuestion(question);
	question.setInstruction("Pull the battery");
	question.setQuestion("Did this solve the issue?");
	question.setName("BATTERY_PULL");

	dlgQuestions.add(dlgQuestion);
	dialog.setQuestionList(dlgQuestions);

	SingleTrueFalseActionWorkflowDialog workflowTaskDialog = null;
	try{
	    workflowTaskDialog = (SingleTrueFalseActionWorkflowDialog) droolsWorkflowService
		    .convertToWorkflowTaskDialog(dialog);
	}catch(ServiceException e1){
	    fail();
	}

	assertNotNull(workflowTaskDialog);
	assertEquals(33L, workflowTaskDialog.getId());
	assertNotNull(workflowTaskDialog.getActionDescribed());
	TrueFalseAction action = workflowTaskDialog.getActionDescribed();
	assertNotNull(action.getQuestion());
	assertNotNull(action.getInstruction());
	assertEquals("Did this solve the issue?", action.getQuestion().getTitle());
	assertEquals("Pull the battery", action.getInstruction().getTitle());
	assertEquals("BATTERY_PULL", action.getQuestion().getCode());
	assertEquals(55L, action.getQuestion().getInstanceId());

	// convert back to internal service dialog
	// fail due to no provided answer
	dialog = null;
	try{
	    dialog = droolsWorkflowService.convertToDialog(workflowTaskDialog);
	}catch(ServiceException e){
	    assertNotNull(e);
	}

	// convert back to internal service dialog
	TrueFalseAction actionDescribed = workflowTaskDialog.getActionDescribed();
	actionDescribed.getQuestion().setAnswer(true);
	workflowTaskDialog.setActionTaken(actionDescribed);
	try{
	    dialog = droolsWorkflowService.convertToDialog(workflowTaskDialog);
	}catch(ServiceException e){
	    fail();
	}

	assertNotNull(dialog);
	List<DialogQuestion> questionList = dialog.getQuestionList();
	assertNotNull(questionList);
	assertEquals(1, questionList.size());
	assertNotNull(questionList.get(0));
	assertEquals("Y", questionList.get(0).getAnswerText());
	assertEquals(22, workflowTaskDialog.getStepSequence());
    }

    @Test
    public void testSingleChoiceWithExplanationWorkflowDialogConverters() {
	Dialog dialog = new Dialog();
	dialog.setId(33L);
	dialog.setName(SingleChoiceWithExplanationWorkflowDialog.class.getSimpleName());
	WorkflowStep step = new WorkflowStep();
	step.setSequenceNum(22);
	dialog.setStep(step);

	List<DialogQuestion> dlgQuestions = new ArrayList<DialogQuestion>();

	DialogQuestion dlgQuestion = new DialogQuestion();
	dlgQuestion.setId(66L);
	QueryQuestion question = new QueryQuestion();
	question.setName("issue_type");
	question.setQuestion("Question question");
	question.setInstruction("Question instruction");
	dlgQuestion.setQuestion(question);

	DialogAnswer batt = new DialogAnswer();
	batt.setName("battery_issue");
	batt.setAnswer("Battery Issue");

	DialogAnswer phys = new DialogAnswer();
	phys.setName("physical_damage");
	phys.setAnswer("Physical Damage");

	DialogAnswer soft = new DialogAnswer();
	soft.setName("software_issue");
	soft.setAnswer("Software Issue");

	dlgQuestion.setChoices(Arrays.asList(batt, phys, soft));

	dlgQuestions.add(dlgQuestion);
	dialog.setQuestionList(dlgQuestions);

	SingleChoiceWithExplanationWorkflowDialog workflowTaskDialog = null;
	try{
	    workflowTaskDialog = (SingleChoiceWithExplanationWorkflowDialog) droolsWorkflowService
		    .convertToWorkflowTaskDialog(dialog);
	}catch(ServiceException e1){
	    fail();
	}

	assertNotNull(workflowTaskDialog);
	assertEquals(33L, workflowTaskDialog.getId());
	assertNotNull(workflowTaskDialog.getQuestion());
	assertEquals("issue_type", workflowTaskDialog.getQuestion().getCode());
	assertEquals(66L, workflowTaskDialog.getQuestion().getInstanceId());
	assertEquals("Question question", workflowTaskDialog.getQuestion().getTitle());
	assertNotNull(workflowTaskDialog.getInstruction());
	assertEquals("Question instruction", workflowTaskDialog.getInstruction().getTitle());
	List<TitleAndDescription> options = workflowTaskDialog.getOptions();
	assertNotNull(options);
	assertEquals(3, options.size());
	assertEquals("battery_issue", options.get(0).getCode());
	assertEquals("Battery Issue", options.get(0).getTitle());
	assertEquals("physical_damage", options.get(1).getCode());
	assertEquals("Physical Damage", options.get(1).getTitle());
	assertEquals("software_issue", options.get(2).getCode());
	assertEquals("Software Issue", options.get(2).getTitle());

	// convert back to internal service dialog
	// fail due to no selected option
	dialog = null;
	try{
	    dialog = droolsWorkflowService.convertToDialog(workflowTaskDialog);
	}catch(ServiceException e){
	    assertNotNull(e);
	}

	// convert back to internal service dialog
	workflowTaskDialog.setSelectedOption(new TitleAndDescription("Software Issue", null, "software_issue"));
	try{
	    dialog = droolsWorkflowService.convertToDialog(workflowTaskDialog);
	}catch(ServiceException e){
	    fail();
	}

	assertNotNull(dialog);
	assertEquals(dialog.getName(), SingleChoiceWithExplanationWorkflowDialog.class.getSimpleName());
	List<DialogQuestion> questionList = dialog.getQuestionList();
	assertNotNull(questionList);
	assertEquals(1, questionList.size());
	assertNotNull(questionList.get(0));
	assertEquals("software_issue", questionList.get(0).getAnswerText());
	assertEquals(22, workflowTaskDialog.getStepSequence());
    }

    @Test
    public void testMultipleTrueFalseActionsWorkflowDialogConverters() {
	// FIXME code testMultipleTrueFalseActionsWorkflowDialogConverters
    }

    @Test
    public void testSuccessfulCompletionWorkflowDialogConverters() {
	Dialog dialog = new Dialog();
	dialog.setId(33L);
	dialog.setName(SuccessfulCompletionWorkflowDialog.class.getSimpleName());
	WorkflowStep step = new WorkflowStep();
	step.setSequenceNum(22);
	dialog.setStep(step);

	List<DialogQuestion> dlgQuestions = new ArrayList<DialogQuestion>();

	DialogQuestion dlgQuestion = new DialogQuestion();
	dlgQuestion.setId(66L);
	QueryQuestion question = new QueryQuestion();
	question.setName("complete");
	question.setInstruction("Complete instruction");
	question.setDetail("Complete detail");
	dlgQuestion.setQuestion(question);

	dlgQuestions.add(dlgQuestion);
	dialog.setQuestionList(dlgQuestions);

	SuccessfulCompletionWorkflowDialog workflowTaskDialog = null;
	try{
	    workflowTaskDialog = (SuccessfulCompletionWorkflowDialog) droolsWorkflowService
		    .convertToWorkflowTaskDialog(dialog);
	}catch(ServiceException e){
	    fail();
	}

	assertNotNull(workflowTaskDialog);
	assertEquals(33L, workflowTaskDialog.getId());
	assertNotNull(workflowTaskDialog.getInstruction());
	assertEquals("complete", workflowTaskDialog.getInstruction().getCode());
	assertEquals(66L, workflowTaskDialog.getInstruction().getInstanceId());
	assertEquals("Complete instruction", workflowTaskDialog.getInstruction().getTitle());
	assertEquals("Complete detail", workflowTaskDialog.getInstruction().getDescription());
	assertEquals(22, workflowTaskDialog.getStepSequence());

	// no conversion back
	try{
	    dialog = droolsWorkflowService.convertToDialog(workflowTaskDialog);
	}catch(ServiceException e){
	    assertNotNull(e);
	}
    }

    private void resetAll() {
	reset(workflowDao);
    }

    private void replayAll() {
	replay(workflowDao);
    }
}
