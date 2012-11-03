/**
 * 
 */
package com.bestbuy.bbym.ise.workflow.rules;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.util.jdbc.DataDefMap;
import com.bestbuy.bbym.ise.util.jdbc.InputDataMap;
import com.bestbuy.bbym.ise.util.jdbc.TemplateComplexDelete;
import com.bestbuy.bbym.ise.util.jdbc.TemplateDelete;
import com.bestbuy.bbym.ise.util.jdbc.TemplateInsert;
import com.bestbuy.bbym.ise.util.jdbc.TemplateSelect;
import com.bestbuy.bbym.ise.util.jdbc.TemplateUpdate;
import com.bestbuy.bbym.ise.util.jdbc.WhereClause;
import com.bestbuy.bbym.ise.workflow.WorkflowBuilder;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.DialogAnswer;
import com.bestbuy.bbym.ise.workflow.domain.DialogQuestion;
import com.bestbuy.bbym.ise.workflow.domain.QueryAnswer;
import com.bestbuy.bbym.ise.workflow.domain.QueryQuestion;
import com.bestbuy.bbym.ise.workflow.domain.Workflow;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;
import com.bestbuy.bbym.ise.workflow.domain.datadef.DialogAnswerDataDef;
import com.bestbuy.bbym.ise.workflow.domain.datadef.DialogDataDef;
import com.bestbuy.bbym.ise.workflow.domain.datadef.DialogQuestionDataDef;
import com.bestbuy.bbym.ise.workflow.domain.datadef.QueryAnswerDataDef;
import com.bestbuy.bbym.ise.workflow.domain.datadef.QueryQuestionDataDef;
import com.bestbuy.bbym.ise.workflow.domain.datadef.WorkflowAttributeDataDef;
import com.bestbuy.bbym.ise.workflow.domain.datadef.WorkflowDataDef;
import com.bestbuy.bbym.ise.workflow.domain.datadef.WorkflowDataDefMap;
import com.bestbuy.bbym.ise.workflow.domain.datadef.WorkflowStepDataDef;

/**
 * @author EJT0522
 * 
 */
@Repository("workflowDao")
public class WorkflowDaoImpl implements WorkflowDao {

    private static final long serialVersionUID = -929171040100559918L;
    private static Logger logger = LoggerFactory.getLogger(WorkflowDaoImpl.class);

    private TemplateInsert.InsertIndexMethod insertIndexMethod = TemplateInsert.InsertIndexMethod.INSERT_SEQUENCE_NEXTVAL;
    private JdbcTemplate jdbcTemplate;
    private String schemaName;

    private final static DataDefMap wfDataDefMap = new WorkflowDataDefMap();

    public void setSchemaName(String schemaName) {
	this.schemaName = schemaName;
    }

    public void setDataSource(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void setInsertIndexMethod(TemplateInsert.InsertIndexMethod insertIndexMethod) {
	this.insertIndexMethod = insertIndexMethod;
    }

    @Override
    public Workflow getWorkflow(long workflowId) throws DataAccessException {
	TemplateSelect<Workflow> workflowSelect = new TemplateSelect<Workflow>(jdbcTemplate, schemaName, wfDataDefMap,
		Workflow.class);
	int workflowIdInput = workflowSelect.addWhere(WhereClause.OperatorType.EQUAL, Workflow.class,
		WorkflowDataDef.F_ID);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(workflowIdInput, workflowId);
	return workflowSelect.selectObject(inputDataMap);
    }

    @Override
    public long addWorkflow(WorkflowBuilder<?> workflowBuilder, User employee) throws DataAccessException {
	return addWorkflow(workflowBuilder, employee, null);
    }

    @Override
    public long addWorkflow(WorkflowBuilder<?> workflowBuilder, User employee, String status)
	    throws DataAccessException {
	if (workflowBuilder == null){
	    throw new DataAccessException("WorkflowBuilder invalid");
	}
	if (workflowBuilder.getWorkflowType() == null){
	    throw new DataAccessException("WorkflowBuilder has invalid workflow type");
	}
	Workflow wf = new Workflow();
	wf.setType(workflowBuilder.getWorkflowType());
	wf.setCustomer((Customer) workflowBuilder.getWorkflowAttribute("CUSTOMER"));
	wf.setStatus(status);

	TemplateInsert<Workflow> workflowAdd = new TemplateInsert<Workflow>(jdbcTemplate, schemaName, wfDataDefMap,
		Workflow.class);
	workflowAdd.setInsertIndexMethod(insertIndexMethod);
	workflowAdd.setChanger(employee);
	workflowAdd.insert(wf);
	return wf.getId();
    }

    @Override
    public void persistWorkflow(Workflow workflow, User employee) throws DataAccessException {
	TemplateUpdate<Workflow> workflowEdit = new TemplateUpdate<Workflow>(jdbcTemplate, schemaName, wfDataDefMap,
		Workflow.class);
	workflowEdit.setChanger(employee);
	workflowEdit.addWhere(WhereClause.OperatorType.EQUAL, Workflow.class, WorkflowDataDef.F_ID);
	int rowsUpdated = workflowEdit.update(workflow);
	logger.debug("workflow rows updated=" + rowsUpdated);
    }

    @Override
    public WorkflowStep getLastWorkflowStep(long workflowId) throws DataAccessException {
	TemplateSelect<WorkflowStep> workflowStepSelect = new TemplateSelect<WorkflowStep>(jdbcTemplate, schemaName,
		wfDataDefMap, WorkflowStep.class);
	int workflowIdInput = workflowStepSelect.addWhere(WhereClause.OperatorType.EQUAL, WorkflowStep.class,
		WorkflowStepDataDef.F_WORKFLOW_ID);
	workflowStepSelect.addOrderBy(WorkflowStep.class, WorkflowStepDataDef.F_SEQUENCE_NUM, false);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(workflowIdInput, workflowId);
	List<WorkflowStep> list = workflowStepSelect.selectList(inputDataMap);
	if (list == null || list.isEmpty()){
	    return null;
	}
	return list.get(0);
    }

    public WorkflowStep getWorkflowStep(long workflowId, int sequenceNumber) throws DataAccessException {
	TemplateSelect<WorkflowStep> workflowStepSelect = new TemplateSelect<WorkflowStep>(jdbcTemplate, schemaName,
		wfDataDefMap, WorkflowStep.class);
	int workflowIdInput = workflowStepSelect.addWhere(WhereClause.OperatorType.EQUAL, WorkflowStep.class,
		WorkflowStepDataDef.F_WORKFLOW_ID);
	int sequenceNumberInput = workflowStepSelect.addWhere(WhereClause.OperatorType.EQUAL, WorkflowStep.class,
		WorkflowStepDataDef.F_SEQUENCE_NUM);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(workflowIdInput, workflowId);
	inputDataMap.put(sequenceNumberInput, sequenceNumber);
	return workflowStepSelect.selectObject(inputDataMap);
    }

    @Override
    public void removeLastWorkflowStep(long workflowId) throws DataAccessException {
	WorkflowStep lastStep = getLastWorkflowStep(workflowId);
	if (lastStep == null){
	    return;
	}
	// Remove old answers
	TemplateComplexDelete<DialogAnswer> answersDelete = new TemplateComplexDelete<DialogAnswer>(jdbcTemplate,
		schemaName, wfDataDefMap, DialogAnswer.class);
	answersDelete.addWhere(WhereClause.OperatorType.EQUAL, DialogAnswer.class,
		DialogAnswerDataDef.F_DIALOG_QUESTION_ID, DialogQuestion.class, DialogQuestionDataDef.F_ID);
	answersDelete.addWhere(WhereClause.OperatorType.EQUAL, DialogQuestion.class, DialogQuestionDataDef.F_DIALOG_ID,
		Dialog.class, DialogDataDef.F_ID);
	int workflowStepIdInput = answersDelete.addWhere(WhereClause.OperatorType.EQUAL, Dialog.class,
		DialogDataDef.F_STEP_ID);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(workflowStepIdInput, lastStep.getId());
	int rowsDeleted = answersDelete.delete(inputDataMap);
	logger.debug("dialog answer rows deleted=" + rowsDeleted);

	// Remove old questions
	TemplateComplexDelete<DialogQuestion> questionsDelete = new TemplateComplexDelete<DialogQuestion>(jdbcTemplate,
		schemaName, wfDataDefMap, DialogQuestion.class);
	questionsDelete.addWhere(WhereClause.OperatorType.EQUAL, DialogQuestion.class,
		DialogQuestionDataDef.F_DIALOG_ID, Dialog.class, DialogDataDef.F_ID);
	workflowStepIdInput = questionsDelete.addWhere(WhereClause.OperatorType.EQUAL, Dialog.class,
		DialogDataDef.F_STEP_ID);
	inputDataMap = new InputDataMap();
	inputDataMap.put(workflowStepIdInput, lastStep.getId());
	rowsDeleted = questionsDelete.delete(inputDataMap);
	logger.debug("dialog question rows deleted=" + rowsDeleted);

	// Remove old attributes
	TemplateDelete<WorkflowAttribute> attributeRemove = new TemplateDelete<WorkflowAttribute>(jdbcTemplate,
		schemaName, wfDataDefMap, WorkflowAttribute.class);
	workflowStepIdInput = attributeRemove.addWhere(WhereClause.OperatorType.EQUAL, WorkflowAttribute.class,
		WorkflowAttributeDataDef.F_STEP_ID);
	WorkflowAttribute wfAttribute = new WorkflowAttribute();
	wfAttribute.setStepId(lastStep.getId());
	rowsDeleted = attributeRemove.delete(wfAttribute);
	logger.debug("workflow attribute rows deleted=" + rowsDeleted);

	// Remove old dialogs
	TemplateDelete<Dialog> dialogRemove = new TemplateDelete<Dialog>(jdbcTemplate, schemaName, wfDataDefMap,
		Dialog.class);
	workflowStepIdInput = dialogRemove.addWhere(WhereClause.OperatorType.EQUAL, Dialog.class,
		DialogDataDef.F_STEP_ID);
	Dialog dialog = new Dialog();
	dialog.setStep(lastStep);
	rowsDeleted = dialogRemove.delete(dialog);
	logger.debug("dialog rows deleted=" + rowsDeleted);

	// Remove last step
	TemplateDelete<WorkflowStep> stepRemove = new TemplateDelete<WorkflowStep>(jdbcTemplate, schemaName,
		wfDataDefMap, WorkflowStep.class);
	workflowStepIdInput = stepRemove.addWhere(WhereClause.OperatorType.EQUAL, WorkflowStep.class,
		WorkflowStepDataDef.F_ID);
	rowsDeleted = stepRemove.delete(lastStep);
	logger.debug("workflow step rows deleted=" + rowsDeleted);
    }

    public long addWorkflowStep(WorkflowStep step, User employee) throws DataAccessException {
	TemplateInsert<WorkflowStep> stepAdd = new TemplateInsert<WorkflowStep>(jdbcTemplate, schemaName, wfDataDefMap,
		WorkflowStep.class);
	stepAdd.setInsertIndexMethod(insertIndexMethod);
	stepAdd.setChanger(employee);
	stepAdd.insert(step);
	return step.getId();
    }

    public QueryQuestion getQueryQuestion(QueryQuestion queryQuestion) throws DataAccessException {
	TemplateSelect<QueryQuestion> questionSelect = new TemplateSelect<QueryQuestion>(jdbcTemplate, schemaName,
		wfDataDefMap, QueryQuestion.class);
	InputDataMap inputDataMap = new InputDataMap();

	// select by name
	if (queryQuestion.getName() != null){
	    int nameInput = questionSelect.addWhere(WhereClause.OperatorType.EQUAL, QueryQuestion.class,
		    QueryQuestionDataDef.F_NAME);
	    inputDataMap.put(nameInput, queryQuestion.getName());

	    // select by id
	}else{
	    int queryQuestionIdInput = questionSelect.addWhere(WhereClause.OperatorType.EQUAL, QueryQuestion.class,
		    QueryQuestionDataDef.F_ID);
	    inputDataMap.put(queryQuestionIdInput, queryQuestion.getId());
	}
	return questionSelect.selectObject(inputDataMap);
    }

    public QueryAnswer getQueryAnswer(String name) throws DataAccessException {
	TemplateSelect<QueryAnswer> answerSelect = new TemplateSelect<QueryAnswer>(jdbcTemplate, schemaName,
		wfDataDefMap, QueryAnswer.class);
	int nameInput = answerSelect.addWhere(WhereClause.OperatorType.EQUAL, QueryAnswer.class,
		QueryAnswerDataDef.F_NAME);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(nameInput, name);
	return answerSelect.selectObject(inputDataMap);
    }

    public List<DialogAnswer> getDialogAnswerList(long dialogQuestionId) throws DataAccessException {
	TemplateSelect<DialogAnswer> answerSelect = new TemplateSelect<DialogAnswer>(jdbcTemplate, schemaName,
		wfDataDefMap, DialogAnswer.class);
	int dialogQuestionIdInput = answerSelect.addWhere(WhereClause.OperatorType.EQUAL, DialogAnswer.class,
		DialogAnswerDataDef.F_DIALOG_QUESTION_ID);
	answerSelect.addOrderBy(DialogAnswer.class, DialogAnswerDataDef.F_SEQUENCE_NUM, true);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(dialogQuestionIdInput, dialogQuestionId);
	return answerSelect.selectList(inputDataMap);
    }

    public List<DialogQuestion> getDialogQuestionList(long dialogId) throws DataAccessException {
	TemplateSelect<DialogQuestion> questionSelect = new TemplateSelect<DialogQuestion>(jdbcTemplate, schemaName,
		wfDataDefMap, DialogQuestion.class);
	int dialogIdInput = questionSelect.addWhere(WhereClause.OperatorType.EQUAL, DialogQuestion.class,
		DialogQuestionDataDef.F_DIALOG_ID);
	questionSelect.addOrderBy(DialogQuestion.class, DialogQuestionDataDef.F_SEQUENCE_NUM, true);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(dialogIdInput, dialogId);
	List<DialogQuestion> questionList = questionSelect.selectList(inputDataMap);
	if (questionList == null){
	    return null;
	}
	// set query questions on dialog questions
	// set dialog answers on dialog questions
	for(DialogQuestion dq: questionList){
	    dq.setQuestion(getQueryQuestion(dq.getQuestion()));
	    dq.setChoices(getDialogAnswerList(dq.getId()));
	}
	return questionList;
    }

    public void persistWorkflowStep(WorkflowStep step, User employee) throws DataAccessException {
	TemplateUpdate<WorkflowStep> stepEdit = new TemplateUpdate<WorkflowStep>(jdbcTemplate, schemaName,
		wfDataDefMap, WorkflowStep.class);
	stepEdit.setChanger(employee);
	stepEdit.addWhere(WhereClause.OperatorType.EQUAL, WorkflowStep.class, WorkflowStepDataDef.F_ID);
	int rowsUpdated = stepEdit.update(step);
	logger.debug("step rows updated=" + rowsUpdated);
    }

    @Override
    public Dialog getLastDialog(long workflowId) throws DataAccessException {
	WorkflowStep lastStep = getLastWorkflowStep(workflowId);
	if (lastStep == null){
	    return null;
	}
	TemplateSelect<Dialog> dialogSelect = new TemplateSelect<Dialog>(jdbcTemplate, schemaName, wfDataDefMap,
		Dialog.class);
	dialogSelect.addSelectOutField(Dialog.class, DialogDataDef.F_ID);
	int stepIdInput = dialogSelect.addWhere(WhereClause.OperatorType.EQUAL, Dialog.class, DialogDataDef.F_STEP_ID);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(stepIdInput, lastStep.getId());
	Dialog dialog = dialogSelect.selectObject(inputDataMap);
	if (dialog == null){
	    return null;
	}
	return getDialog(dialog.getId());
    }

    @Override
    public Dialog getDialog(long dialogId) throws DataAccessException {
	TemplateSelect<Dialog> dialogSelect = new TemplateSelect<Dialog>(jdbcTemplate, schemaName, wfDataDefMap,
		Dialog.class);
	// dialogSelect.setSelectAllPrimaryObjectFields(false);
	// dialogSelect.addSelectOutField(Dialog.class, DialogDataDef2.F_ID);
	// dialogSelect.addSelectOutField(Dialog.class, DialogDataDef2.F_NAME);
	// dialogSelect.addSelectOutField(Dialog.class,
	// DialogDataDef2.F_STEP_ID);
	int dialogIdInput = dialogSelect.addWhere(WhereClause.OperatorType.EQUAL, Dialog.class, DialogDataDef.F_ID);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(dialogIdInput, dialogId);
	Dialog dialog = dialogSelect.selectObject(inputDataMap);
	if (dialog == null){
	    return null;
	}
	if (dialog.getStep() != null){
	    dialog.setStep(getWorkflowStep(dialog.getStep().getId()));
	}
	dialog.setQuestionList(getDialogQuestionList(dialogId));
	return dialog;
    }

    public WorkflowStep getWorkflowStep(long stepId) throws DataAccessException {
	TemplateSelect<WorkflowStep> stepSelect = new TemplateSelect<WorkflowStep>(jdbcTemplate, schemaName,
		wfDataDefMap, WorkflowStep.class);
	int stepIdInput = stepSelect.addWhere(WhereClause.OperatorType.EQUAL, WorkflowStep.class,
		WorkflowStepDataDef.F_ID);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(stepIdInput, stepId);
	return stepSelect.selectObject(inputDataMap);
    }

    @Override
    public long addDialog(long workflowId, Dialog dialog, User employee) throws DataAccessException {
	if (dialog == null){
	    throw new DataAccessException("WorkflowTaskDialog invalid");
	}
	WorkflowStep lastStep = getLastWorkflowStep(workflowId), nextStep = new WorkflowStep();
	if (lastStep == null){
	    nextStep.setSequenceNum(1);
	}else{
	    nextStep.setSequenceNum(lastStep.getSequenceNum() + 1);
	    lastStep.setEndTime(new Date());
	    persistWorkflowStep(lastStep, employee);
	}
	nextStep.setStartTime(new Date());
	nextStep.setComment(dialog.getName());
	nextStep.setWorkflowId(workflowId);
	if (dialog.getStep() != null){
	    nextStep.setStepId(dialog.getStep().getStepId());
	}
	addWorkflowStep(nextStep, employee);
	dialog.setStep(nextStep);

	TemplateInsert<Dialog> dialogAdd = new TemplateInsert<Dialog>(jdbcTemplate, schemaName, wfDataDefMap,
		Dialog.class);
	dialogAdd.setInsertIndexMethod(insertIndexMethod);
	dialogAdd.setChanger(employee);
	dialogAdd.insert(dialog);

	TemplateInsert<DialogQuestion> questionAdd = new TemplateInsert<DialogQuestion>(jdbcTemplate, schemaName,
		wfDataDefMap, DialogQuestion.class);
	questionAdd.setInsertIndexMethod(insertIndexMethod);
	questionAdd.setChanger(employee);

	TemplateInsert<DialogAnswer> answerAdd = new TemplateInsert<DialogAnswer>(jdbcTemplate, schemaName,
		wfDataDefMap, DialogAnswer.class);
	answerAdd.setInsertIndexMethod(insertIndexMethod);
	answerAdd.setChanger(employee);

	QueryQuestion qq;
	QueryAnswer qa;
	List<DialogQuestion> questionList = dialog.getQuestionList();
	if (questionList == null || questionList.isEmpty()){
	    throw new DataAccessException("Dialog has invalid question list");
	}
	int seqNum = 0, choicesSeqNum = 0;
	for(DialogQuestion dq: questionList){
	    seqNum++;
	    if (dq.getQuestion() == null || dq.getQuestion().getName() == null){
		throw new DataAccessException("Dialog has invalid question");
	    }
	    qq = getQueryQuestion(dq.getQuestion());
	    if (qq == null){
		throw new DataAccessException("No data found for question with name " + dq.getQuestion().getName());
	    }
	    dq.setDialogId(dialog.getId());
	    dq.setSequenceNum(seqNum);
	    dq.setQuestion(qq);
	    questionAdd.insert(dq);

	    if (dq.getChoices() == null || dq.getChoices().isEmpty()){
		continue;
	    }
	    choicesSeqNum = 0;
	    for(DialogAnswer da: dq.getChoices()){
		choicesSeqNum++;
		qa = getQueryAnswer(da.getName());
		if (qa == null){
		    throw new DataAccessException("No data found for answer choice with name " + da.getName());
		}
		da.setAnswer(qa.getAnswer());
		da.setDialogQuestionId(dq.getId());
		da.setSequenceNum(choicesSeqNum);
		answerAdd.insert(da);
	    }
	}
	return dialog.getId();
    }

    @Override
    public void persistDialog(Dialog dialog, User employee) throws DataAccessException {
	if (dialog.getQuestionList() == null || dialog.getQuestionList().isEmpty()){
	    return;
	}
	TemplateUpdate<DialogQuestion> answerEdit = new TemplateUpdate<DialogQuestion>(jdbcTemplate, schemaName,
		wfDataDefMap, DialogQuestion.class);
	answerEdit.setChanger(employee);
	answerEdit.addSelectVarOutField(DialogQuestion.class, DialogQuestionDataDef.F_ANSWER);
	answerEdit.addWhere(WhereClause.OperatorType.EQUAL, DialogQuestion.class, DialogQuestionDataDef.F_ID);

	int rowsUpdated = 0;
	for(DialogQuestion dq: dialog.getQuestionList()){
	    rowsUpdated += answerEdit.update(dq);
	}
	if (dialog.getQuestionList().size() != rowsUpdated){
	    throw new DataAccessException("Number of query answers " + dialog.getQuestionList().size()
		    + " does not match number of rows updated " + rowsUpdated);
	}
    }

    @Override
    public Map<String, WorkflowAttribute> getWorkflowAttributeMap(long workflowId) throws DataAccessException {
	TemplateSelect<WorkflowAttribute> attributeSelect = new TemplateSelect<WorkflowAttribute>(jdbcTemplate,
		schemaName, wfDataDefMap, WorkflowAttribute.class);
	attributeSelect.addWhere(WhereClause.OperatorType.EQUAL, WorkflowAttribute.class,
		WorkflowAttributeDataDef.F_STEP_ID, WorkflowStep.class, WorkflowStepDataDef.F_ID);
	int workflowIdInput = attributeSelect.addWhere(WhereClause.OperatorType.EQUAL, WorkflowStep.class,
		WorkflowStepDataDef.F_WORKFLOW_ID);
	attributeSelect.addOrderBy(WorkflowAttribute.class, WorkflowAttributeDataDef.F_STEP_ID, false);
	InputDataMap inputDataMap = new InputDataMap();
	inputDataMap.put(workflowIdInput, workflowId);
	List<WorkflowAttribute> list = attributeSelect.selectList(inputDataMap);
	if (list == null){
	    return null;
	}
	Map<String, WorkflowAttribute> map = new HashMap<String, WorkflowAttribute>();
	for(WorkflowAttribute attr: list){
	    if (!map.containsKey(attr.getName())){
		map.put(attr.getName(), attr);
	    }
	}
	return map;
    }

    private long addWorkflowAttribute(WorkflowAttribute attribute, User employee) throws DataAccessException {
	TemplateInsert<WorkflowAttribute> attributeAdd = new TemplateInsert<WorkflowAttribute>(jdbcTemplate,
		schemaName, wfDataDefMap, WorkflowAttribute.class);
	attributeAdd.setInsertIndexMethod(insertIndexMethod);
	attributeAdd.setChanger(employee);
	attributeAdd.insert(attribute);
	return attribute.getId();
    }

    private void persistWorkflowAttribute(WorkflowAttribute wfAttribute, User employee) throws DataAccessException {
	TemplateUpdate<WorkflowAttribute> attributeEdit = new TemplateUpdate<WorkflowAttribute>(jdbcTemplate,
		schemaName, wfDataDefMap, WorkflowAttribute.class);
	attributeEdit.setChanger(employee);
	attributeEdit.addWhere(WhereClause.OperatorType.EQUAL, WorkflowAttribute.class, WorkflowAttributeDataDef.F_ID);
	int rowsUpdated = attributeEdit.update(wfAttribute);
	logger.debug("workflow attribute rows updated=" + rowsUpdated);
    }

    @Override
    public void persistWorkflowAttributeMap(Map<String, WorkflowAttribute> newWorkflowAttributeMap, long workflowId,
	    User employee) throws DataAccessException {
	Map<String, WorkflowAttribute> oldAttributeMap = getWorkflowAttributeMap(workflowId);
	if (oldAttributeMap == null){
	    oldAttributeMap = new HashMap<String, WorkflowAttribute>();
	}
	if (newWorkflowAttributeMap == null){
	    throw new DataAccessException("Invalid input newWorkflowAttributeMap");
	}
	long lastStepId = 0L;
	WorkflowStep lastStep = getLastWorkflowStep(workflowId);
	if (lastStep != null){
	    lastStepId = lastStep.getId();
	}

	WorkflowAttribute oldAttribute, newAttribute;
	for(String key: newWorkflowAttributeMap.keySet()){
	    oldAttribute = oldAttributeMap.get(key);
	    newAttribute = newWorkflowAttributeMap.get(key);
	    if (newAttribute == null){
		throw new DataAccessException("Invalid element in input newWorkflowAttributeMap with key " + key);
	    }
	    newAttribute.setStepId(lastStepId);

	    // Add new attribute
	    if (oldAttribute == null){
		addWorkflowAttribute(newAttribute, employee);

		// Both values are null so do nothing
	    }else if (oldAttribute.getValue() == null && newAttribute.getValue() == null){

		// Both values are same so do nothing
	    }else if (oldAttribute.getValue() != null && oldAttribute.getValue().equals(newAttribute.getValue())){

		// Change existing attribute
	    }else if (lastStepId == oldAttribute.getStepId()){
		newAttribute.setId(oldAttribute.getId());
		persistWorkflowAttribute(newAttribute, employee);

		// Add new attribute
	    }else{
		addWorkflowAttribute(newAttribute, employee);
	    }
	}
    }

}
