package com.bestbuy.bbym.ise.workflow.rules;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DatabaseScript;
import com.bestbuy.bbym.ise.util.jdbc.TemplateInsert;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.DialogAnswer;
import com.bestbuy.bbym.ise.workflow.domain.DialogQuestion;
import com.bestbuy.bbym.ise.workflow.domain.QueryAnswer;
import com.bestbuy.bbym.ise.workflow.domain.QueryQuestion;
import com.bestbuy.bbym.ise.workflow.domain.Workflow;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;

/**
 * JUnit test for {@link WorkflowDaoImpl}.
 */
public class WorkflowDaoImplTest {

    private WorkflowDaoImpl workflowDaoImpl = new WorkflowDaoImpl();
    private User testUser = new User();
    private static EmbeddedDatabase db;
    private static EmbeddedDatabaseBuilder builder;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
	builder = new EmbeddedDatabaseBuilder();
	db = builder.setType(EmbeddedDatabaseType.H2).addScript("ise_db_scripts/create_ise_db.sql").build();
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
	DatabaseScript.execute("ise_db_scripts/drop_ise_db.sql", db);
	db.shutdown();
    }

    @Before
    public void setUp() {
	workflowDaoImpl.setInsertIndexMethod(TemplateInsert.InsertIndexMethod.INSERT_SEQUENCE_NEXTVAL);
	workflowDaoImpl.setDataSource(db);
	workflowDaoImpl.setSchemaName("BST_ISE_SCH01");
	testUser.setUserId("a777");
    }

    @After
    public void tearDown() throws Exception {
	DatabaseScript.execute("ise_db_scripts/clean_ise_db.sql", db);
    }

    @Test
    public void testGetWorkflowById() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	Workflow wf = workflowDaoImpl.getWorkflow(1L);
	assertNotNull(wf);
	assertNotNull(wf.getCustomer());
	assertEquals("John", wf.getCustomer().getFirstName());
	assertEquals("Morgan", wf.getCustomer().getLastName());
	assertEquals("TRIAGE", wf.getType());
	assertNotNull(wf.getStartTime());
	assertNull(wf.getEndTime());
	assertEquals("GOOD", wf.getStatus());
	assertEquals("Hello", wf.getComment());
    }

    @Test
    public void testAddWorkflow() throws DataAccessException {
	TestWorkflowBuilder builder = new TestWorkflowBuilder(TestWorkflowType.TEST1);
	Customer cust = new Customer();
	cust.setFirstName("Pete");
	cust.setLastName("Jones");
	builder.setWorkflowAttribute("CUSTOMER", cust);
	long wfId = workflowDaoImpl.addWorkflow(builder, testUser);
	Workflow wf = workflowDaoImpl.getWorkflow(wfId);
	assertNotNull(wf);
	assertNotNull(wf.getCustomer());
	assertEquals("Pete", wf.getCustomer().getFirstName());
	assertEquals("Jones", wf.getCustomer().getLastName());
	assertEquals("TEST1", wf.getType());
	assertNull(wf.getStartTime());
	assertNull(wf.getEndTime());
    }

    @Test
    public void testPersistWorkflow() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	Workflow wf = workflowDaoImpl.getWorkflow(1L);
	assertNotNull(wf);
	assertNotNull(wf.getCustomer());
	wf.getCustomer().setFirstName("Jojo");
	wf.getCustomer().setLastName("Smith");
	wf.setStatus("BAD");
	wf.setEndTime(new Date());
	wf.setComment("What");
	workflowDaoImpl.persistWorkflow(wf, testUser);
	wf = workflowDaoImpl.getWorkflow(1L);
	assertNotNull(wf);
	assertNotNull(wf.getCustomer());
	assertEquals("Jojo", wf.getCustomer().getFirstName());
	assertEquals("Smith", wf.getCustomer().getLastName());
	assertEquals("TRIAGE", wf.getType());
	assertNotNull(wf.getEndTime());
	assertEquals("BAD", wf.getStatus());
	assertEquals("What", wf.getComment());
	assertEquals("a777", wf.getAmendedBy());
    }

    @Test
    public void testGetLastWorkflowStep() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	WorkflowStep lastStep = workflowDaoImpl.getLastWorkflowStep(1L);
	assertNotNull(lastStep);
	assertEquals(3L, lastStep.getId());
	assertEquals(3, lastStep.getSequenceNum());
	assertEquals(103, lastStep.getStepId());
	assertEquals(1L, lastStep.getWorkflowId());
	assertEquals("Step 103", lastStep.getComment());
    }

    @Test
    public void testRemoveLastWorkflowStep() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_attribute_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_hist_data.sql", db);
	long workflowId = 1L;
	workflowDaoImpl.removeLastWorkflowStep(workflowId);
	//
	Dialog dialog = workflowDaoImpl.getLastDialog(workflowId);
	assertNotNull(dialog);
	assertEquals(2L, dialog.getId());
	//
	assertNotNull(dialog.getQuestionList());
	assertEquals(1, dialog.getQuestionList().size());
	//
	Map<String, WorkflowAttribute> map = workflowDaoImpl.getWorkflowAttributeMap(workflowId);
	assertNotNull(map);
	assertEquals(3, map.size());
	assertNotNull(map.get("NATION"));
	assertEquals("usa", map.get("NATION").getValue());
	assertNotNull(map.get("COLOR"));
	assertNull(map.get("COLOR").getValue());
	assertNotNull(map.get("NAME"));
	assertEquals("john", map.get("NAME").getValue());
	//
	workflowDaoImpl.removeLastWorkflowStep(workflowId);
	//
	dialog = workflowDaoImpl.getLastDialog(workflowId);
	assertNotNull(dialog);
	assertEquals(1L, dialog.getId());
	//
	assertNotNull(dialog.getQuestionList());
	assertEquals(2, dialog.getQuestionList().size());
	DialogQuestion dq = dialog.getQuestionList().get(0);
	assertNotNull(dq.getQuestion());
	assertEquals(1L, dq.getQuestion().getId());
	assertNotNull(dq.getChoices());
	assertEquals(0, dq.getChoices().size());
	assertEquals(1, dq.getSequenceNum());
	//
	dq = dialog.getQuestionList().get(1);
	assertNotNull(dq.getQuestion());
	assertEquals(2L, dq.getQuestion().getId());
	assertNotNull(dq.getChoices());
	assertEquals(2, dq.getChoices().size());
	assertEquals(2, dq.getSequenceNum());
	//
	DialogAnswer da = dq.getChoices().get(0);
	assertEquals(1, da.getSequenceNum());
	assertEquals("Choice 1", da.getAnswer());
	//
	da = dq.getChoices().get(1);
	assertEquals(2, da.getSequenceNum());
	assertEquals("Choice 2", da.getAnswer());
	//
	map = workflowDaoImpl.getWorkflowAttributeMap(workflowId);
	assertNotNull(map);
	assertEquals(2, map.size());
	assertNotNull(map.get("NATION"));
	assertEquals("usa", map.get("NATION").getValue());
	assertNotNull(map.get("COLOR"));
	assertNull(map.get("COLOR").getValue());
    }

    @Test
    public void testGetWorkflowStep() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	WorkflowStep step = workflowDaoImpl.getWorkflowStep(1L, 2);
	assertNotNull(step);
	assertEquals(2L, step.getId());
	assertEquals(2, step.getSequenceNum());
	assertEquals(102, step.getStepId());
	assertEquals(1L, step.getWorkflowId());
	assertEquals("Step 102", step.getComment());
    }

    @Test
    public void testAddWorkflowStep() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	WorkflowStep step = new WorkflowStep();
	step.setStartTime(new Date());
	step.setComment("Another Step");
	step.setWorkflowId(1L);
	step.setStepId(456);
	step.setSequenceNum(4);
	workflowDaoImpl.addWorkflowStep(step, testUser);
	WorkflowStep lastStep = workflowDaoImpl.getLastWorkflowStep(1L);
	assertNotNull(lastStep);
	assertEquals(4, lastStep.getSequenceNum());
	assertEquals(456, lastStep.getStepId());
	assertEquals(1L, lastStep.getWorkflowId());
	assertEquals("Another Step", lastStep.getComment());
    }

    @Test
    public void testGetQueryQuestion() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	QueryQuestion qq = new QueryQuestion();
	qq.setId(1L);
	qq = workflowDaoImpl.getQueryQuestion(qq);
	assertNotNull(qq);
	assertEquals(1L, qq.getId());
	assertEquals("question_1", qq.getName());
	assertEquals("Question 1", qq.getQuestion());
	assertEquals("Instruction 1", qq.getInstruction());
	assertEquals("Instruction Detail 1", qq.getDetail());
	assertEquals("TRG", qq.getType());
	assertNull(qq.getImageUrl());
	assertNull(qq.getDisplayType());
	//
	qq = new QueryQuestion();
	qq.setId(2L);
	qq = workflowDaoImpl.getQueryQuestion(qq);
	assertNotNull(qq);
	assertEquals(2L, qq.getId());
	assertEquals("question_2", qq.getName());
	assertEquals("Question 2", qq.getQuestion());
	assertEquals("Instruction 2", qq.getInstruction());
	assertEquals("Instruction Detail 2", qq.getDetail());
	assertEquals("TRG", qq.getType());
	assertEquals("http://www.some.com/sample2.jpg", qq.getImageUrl());
	assertEquals("R", qq.getDisplayType());
	//
	qq = new QueryQuestion();
	qq.setName("question_2");
	qq = workflowDaoImpl.getQueryQuestion(qq);
	assertNotNull(qq);
	assertEquals(2L, qq.getId());
	assertEquals("question_2", qq.getName());
	assertEquals("Question 2", qq.getQuestion());
	assertEquals("Instruction 2", qq.getInstruction());
	assertEquals("Instruction Detail 2", qq.getDetail());
	assertEquals("TRG", qq.getType());
	assertEquals("http://www.some.com/sample2.jpg", qq.getImageUrl());
	assertEquals("R", qq.getDisplayType());
    }

    @Test
    public void testGetQueryAnswer() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	QueryAnswer qa = workflowDaoImpl.getQueryAnswer("choice_2");
	assertNotNull(qa);
	assertEquals(2L, qa.getId());
	assertEquals("choice_2", qa.getName());
	assertEquals("Choice 2", qa.getAnswer());
	assertEquals("a123", qa.getCreatedBy());
    }

    @Test
    public void testGetDialogAnswerList() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_hist_data.sql", db);
	List<DialogAnswer> list = workflowDaoImpl.getDialogAnswerList(2L);
	assertNotNull(list);
	assertEquals(2, list.size());
	DialogAnswer da = list.get(0);
	assertEquals("choice_1", da.getName());
	assertEquals("Choice 1", da.getAnswer());
	//
	da = list.get(1);
	assertEquals("choice_2", da.getName());
	assertEquals("Choice 2", da.getAnswer());
    }

    @Test
    public void testGetDialogQuestionList() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_hist_data.sql", db);
	List<DialogQuestion> list = workflowDaoImpl.getDialogQuestionList(1L);
	assertNotNull(list);
	assertEquals(2, list.size());
	DialogQuestion dq = list.get(0);
	assertEquals(1, dq.getSequenceNum());
	assertEquals("Answer 1 DLG 1", dq.getAnswerText());
	//
	dq = list.get(1);
	assertEquals(2, dq.getSequenceNum());
	assertEquals("choice_2", dq.getAnswerText());
    }

    @Test
    public void testPersistWorkflowStep() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	WorkflowStep step = workflowDaoImpl.getWorkflowStep(1L, 2);
	assertNotNull(step);
	step.setComment("Step 202");
	step.setStepId(202);
	workflowDaoImpl.persistWorkflowStep(step, testUser);
	step = workflowDaoImpl.getWorkflowStep(1L, 2);
	assertEquals(2L, step.getId());
	assertEquals(2, step.getSequenceNum());
	assertEquals(202, step.getStepId());
	assertEquals(1L, step.getWorkflowId());
	assertEquals("Step 202", step.getComment());
	assertEquals("a777", step.getAmendedBy());
    }

    @Test
    public void testGetLastDialog() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_data.sql", db);
	long workflowId = 1L;
	Dialog dialog = workflowDaoImpl.getLastDialog(workflowId);
	assertNotNull(dialog);
	assertEquals(3L, dialog.getId());
    }

    @Test
    public void testGetDialog() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_hist_data.sql", db);
	Dialog dlg = workflowDaoImpl.getDialog(1L);
	assertNotNull(dlg);
	assertEquals("Dialog 101", dlg.getName());
	//
	assertNotNull(dlg.getStep());
	assertEquals(1L, dlg.getStep().getId());
	assertEquals(1L, dlg.getStep().getWorkflowId());
	assertEquals(101, dlg.getStep().getStepId());
	assertEquals(1, dlg.getStep().getSequenceNum());
	assertEquals("Step 101", dlg.getStep().getComment());
	assertNotNull(dlg.getStep().getStartTime());
	//
	assertNotNull(dlg.getQuestionList());
	assertEquals(dlg.getQuestionList().size(), 2);
	DialogQuestion dq = dlg.getQuestionList().get(0);
	assertEquals(1, dq.getSequenceNum());
	assertEquals(dq.getAnswerText(), "Answer 1 DLG 1");
	//
	dq = dlg.getQuestionList().get(1);
	assertEquals(2, dq.getSequenceNum());
	assertEquals("choice_2", dq.getAnswerText());
	//
	assertNotNull(dq.getChoices());
	assertEquals(2, dq.getChoices().size());
	DialogAnswer da = dq.getChoices().get(0);
	assertEquals(1, da.getSequenceNum());
	assertEquals("choice_1", da.getName());
	assertEquals("Choice 1", da.getAnswer());
	//
	da = dq.getChoices().get(1);
	assertEquals(2, da.getSequenceNum());
	assertEquals("choice_2", da.getName());
	assertEquals("Choice 2", da.getAnswer());
    }

    @Test
    public void testAddDialog() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_hist_data.sql", db);
	Dialog dlg = new Dialog();
	WorkflowStep step = new WorkflowStep();
	step.setStepId(104);
	dlg.setStep(step);
	dlg.setName("Dialog 104");
	long workflowId = 1L;
	//
	dlg.setQuestionList(new ArrayList<DialogQuestion>());
	DialogQuestion dq = new DialogQuestion();
	dq.setName("question_1");
	List<DialogAnswer> choices = new ArrayList<DialogAnswer>();
	DialogAnswer da = new DialogAnswer();
	da.setName("choice_1");
	da.setSequenceNum(1);
	choices.add(da);
	da = new DialogAnswer();
	da.setName("choice_2");
	da.setSequenceNum(2);
	choices.add(da);
	dq.setChoices(choices);
	dlg.getQuestionList().add(dq);
	dq = new DialogQuestion();
	dq.setName("question_2");
	dlg.getQuestionList().add(dq);
	long dialogId = workflowDaoImpl.addDialog(workflowId, dlg, testUser);
	//
	WorkflowStep previousStep = workflowDaoImpl.getWorkflowStep(workflowId, 3);
	assertNotNull(previousStep);
	assertNotNull(previousStep.getEndTime());
	//
	dlg = workflowDaoImpl.getDialog(dialogId);
	assertNotNull(dlg);
	assertEquals("Dialog 104", dlg.getName());
	//
	assertNotNull(dlg.getStep());
	assertEquals(1L, dlg.getStep().getWorkflowId());
	assertEquals(104, dlg.getStep().getStepId());
	assertEquals(4, dlg.getStep().getSequenceNum());
	assertEquals("Dialog 104", dlg.getStep().getComment());
	assertNotNull(dlg.getStep().getStartTime());
	assertNull(dlg.getStep().getEndTime());
	//
	assertNotNull(dlg.getQuestionList());
	assertEquals(dlg.getQuestionList().size(), 2);
	DialogQuestion dlgq = dlg.getQuestionList().get(0);
	assertNotNull(dlgq.getQuestion());
	assertNull(dlgq.getAnswerText());
	assertEquals(dialogId, dlgq.getDialogId());
	assertEquals("question_1", dlgq.getQuestion().getName());
	assertEquals("Question 1", dlgq.getQuestion().getQuestion());
	assertEquals("Instruction 1", dlgq.getQuestion().getInstruction());
	assertEquals("Instruction Detail 1", dlgq.getQuestion().getDetail());
	assertEquals("TRG", dlgq.getQuestion().getType());
	assertEquals(1, dlgq.getSequenceNum());
	//
	assertNotNull(dlgq.getChoices());
	assertEquals(2, dlgq.getChoices().size());
	DialogAnswer dlga = dlgq.getChoices().get(0);
	assertEquals("choice_1", dlga.getName());
	assertEquals("Choice 1", dlga.getAnswer());
	assertEquals(1, dlga.getSequenceNum());
	//
	dlga = dlgq.getChoices().get(1);
	assertEquals("choice_2", dlga.getName());
	assertEquals("Choice 2", dlga.getAnswer());
	assertEquals(2, dlga.getSequenceNum());
	//
	dlgq = dlg.getQuestionList().get(1);
	assertNotNull(dlgq.getQuestion());
	assertNull(dlgq.getAnswerText());
	assertEquals(dialogId, dlgq.getDialogId());
	assertEquals("question_2", dlgq.getQuestion().getName());
	assertEquals("Question 2", dlgq.getQuestion().getQuestion());
	assertEquals("Instruction 2", dlgq.getQuestion().getInstruction());
	assertEquals("Instruction Detail 2", dlgq.getQuestion().getDetail());
	assertEquals("TRG", dlgq.getQuestion().getType());
	assertEquals(2, dlgq.getSequenceNum());
	assertNotNull(dlgq.getChoices());
	assertEquals(0, dlgq.getChoices().size());
    }

    @Test
    public void testPersistDialog() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_dialog_quest_answ_hist_data.sql", db);
	long dialogId = 1L;
	Dialog dlg = new Dialog();
	dlg.setId(dialogId);
	dlg.setQuestionList(new ArrayList<DialogQuestion>());
	DialogQuestion dq = new DialogQuestion();
	dq.setId(1L);
	dq.setAnswerText("Hello");
	dlg.getQuestionList().add(dq);
	dq = new DialogQuestion();
	dq.setId(2L);
	dq.setAnswerText("GoodBye");
	dlg.getQuestionList().add(dq);
	workflowDaoImpl.persistDialog(dlg, testUser);
	//
	dlg = workflowDaoImpl.getDialog(dialogId);
	assertNotNull(dlg);
	assertNotNull(dlg.getQuestionList());
	assertEquals(2, dlg.getQuestionList().size());
	DialogQuestion dlgq = dlg.getQuestionList().get(0);
	assertEquals("Hello", dlgq.getAnswerText());
	assertEquals(1, dlgq.getSequenceNum());
	assertEquals(1L, dlgq.getId());
	//
	dlgq = dlg.getQuestionList().get(1);
	assertEquals("GoodBye", dlgq.getAnswerText());
	assertEquals(2, dlgq.getSequenceNum());
	assertEquals(2L, dlgq.getId());
    }

    @Test
    public void testGetWorkflowAttributeMap() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_attribute_data.sql", db);
	long workflowId = 1L;
	Map<String, WorkflowAttribute> map = workflowDaoImpl.getWorkflowAttributeMap(workflowId);
	assertNotNull(map);
	assertEquals(3, map.size());
	assertNotNull(map.get("COLOR"));
	assertEquals("red", map.get("COLOR").getValue());
	assertNotNull(map.get("NATION"));
	assertNull(map.get("NATION").getValue());
	assertNotNull(map.get("NAME"));
	assertEquals("john", map.get("NAME").getValue());
    }

    @Test
    public void testPersistWorkflowAttributeMap() throws DataAccessException {
	DatabaseScript.execute("ise_db_scripts/add_workflow_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_step_data.sql", db);
	DatabaseScript.execute("ise_db_scripts/add_workflow_attribute_data.sql", db);
	long workflowId = 1L;
	WorkflowStep step = new WorkflowStep();
	step.setStartTime(new Date());
	step.setComment("Another Step");
	step.setWorkflowId(workflowId);
	step.setStepId(456);
	step.setSequenceNum(4);
	workflowDaoImpl.addWorkflowStep(step, testUser);
	//
	WorkflowAttribute wa;
	Map<String, WorkflowAttribute> newMap = new HashMap<String, WorkflowAttribute>();
	wa = new WorkflowAttribute();
	wa.setName("SIZE");
	wa.setValue("17");
	newMap.put(wa.getName(), wa);
	//
	wa = new WorkflowAttribute();
	wa.setName("NAME");
	wa.setValue(null);
	newMap.put(wa.getName(), wa);
	//
	wa = new WorkflowAttribute();
	wa.setName("COLOR");
	wa.setValue("green");
	newMap.put(wa.getName(), wa);
	//
	workflowDaoImpl.persistWorkflowAttributeMap(newMap, workflowId, testUser);
	//
	Map<String, WorkflowAttribute> map = workflowDaoImpl.getWorkflowAttributeMap(workflowId);
	assertNotNull(map);
	assertEquals(4, map.size());
	assertNotNull(map.get("COLOR"));
	assertEquals("green", map.get("COLOR").getValue());
	// Step did change so attribute should have been added as new one
	assertTrue(1L != map.get("COLOR").getId());
	assertNotNull(map.get("NATION"));
	assertNull(map.get("NATION").getValue());
	assertNotNull(map.get("NAME"));
	assertNull(map.get("NAME").getValue());
	assertNotNull(map.get("SIZE"));
	assertEquals("17", map.get("SIZE").getValue());
	long sizeAttributeId = map.get("SIZE").getId();
	//
	newMap = new HashMap<String, WorkflowAttribute>();
	wa = new WorkflowAttribute();
	wa.setName("SIZE");
	wa.setValue("19");
	newMap.put(wa.getName(), wa);
	//
	workflowDaoImpl.persistWorkflowAttributeMap(newMap, workflowId, testUser);
	//
	map = workflowDaoImpl.getWorkflowAttributeMap(workflowId);
	assertNotNull(map);
	assertEquals(4, map.size());
	assertNotNull(map.get("SIZE"));
	assertEquals("19", map.get("SIZE").getValue());
	// Step did not change so attribute should have been changed not added
	// as new one
	assertEquals(sizeAttributeId, map.get("SIZE").getId());
    }
}
