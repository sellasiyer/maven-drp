/**
 * Best Buy Inc (c) 2011
 */

package com.bestbuy.bbym.ise.drp.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.drp.domain.TitleAndDescription;
import com.bestbuy.bbym.ise.drp.domain.TrueFalseQuestion;
import com.bestbuy.bbym.ise.drp.workflow.triage.TriageWorkflowState;
import com.bestbuy.bbym.ise.drp.workflow.triage.TrueFalseAction;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.jdbc.DataDef;
import com.bestbuy.bbym.ise.workflow.WorkflowBuilder;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;
import com.bestbuy.bbym.ise.workflow.domain.Dialog;
import com.bestbuy.bbym.ise.workflow.domain.DialogAnswer;
import com.bestbuy.bbym.ise.workflow.domain.DialogQuestion;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowAttribute;
import com.bestbuy.bbym.ise.workflow.domain.WorkflowStep;
import com.bestbuy.bbym.ise.workflow.rules.BaseDroolsWorkflowService;
import com.bestbuy.bbym.ise.workflow.rules.WorkflowDao;

public class DroolsWorkflowService extends BaseDroolsWorkflowService<TriageWorkflowState> {

    private static final long serialVersionUID = -2260548450123330901L;
    private static Logger logger = LoggerFactory.getLogger(DroolsWorkflowService.class);

    /**
     * @param rulesFile
     * @param workflowDao
     */
    public DroolsWorkflowService(String rulesFile, WorkflowDao workflowDao) {
	super(rulesFile, workflowDao);
    }

    @Override
    public TriageWorkflowState getWorkflowState(long workflowId) throws ServiceException {
	WorkflowStep lastStep = null;
	try{
	    lastStep = workflowDao.getLastWorkflowStep(workflowId);
	    logger.debug("Last Step=" + lastStep);
	}catch(DataAccessException e){
	    logger.error("Failed to get last step for workflow with ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	Map<String, WorkflowAttribute> attributeMap = null;
	try{
	    attributeMap = workflowDao.getWorkflowAttributeMap(workflowId);
	}catch(DataAccessException e){
	    logger.error("Failed to get attribute list for workflow with ID " + workflowId, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
	TriageWorkflowState tws = constructTriageWorkflowState(attributeMap);
	tws.setWorkflowId(workflowId);
	if (lastStep != null){
	    tws.setStep(lastStep.getStepId());
	}
	return tws;
    }

    public static TriageWorkflowState constructTriageWorkflowState(Map<String, WorkflowAttribute> attributeMap) {
	WorkflowAttribute wa;
	TriageWorkflowState tws = new TriageWorkflowState();
	if (attributeMap.containsKey(TriageWorkflowState.Attribute.PROBLEM_TYPE.name())){
	    wa = attributeMap.get(TriageWorkflowState.Attribute.PROBLEM_TYPE.name());
	    if (wa != null){
		tws.setIssue(wa.getValue());
	    }else{
		tws.setIssue((String) null);
	    }
	}
	tws.setDevice(constructTriageWorkflowDevice(attributeMap));
	return tws;
    }

    public static Device constructTriageWorkflowDevice(Map<String, WorkflowAttribute> attributeMap) {
	WorkflowAttribute wa;
	Device device = new Device();
	if (attributeMap.containsKey(TriageWorkflowState.Attribute.DEV_OS.name())){
	    device.setOs(TriageWorkflowState.getAttributeValue(TriageWorkflowState.Attribute.DEV_OS, attributeMap));
	}
	if (attributeMap.containsKey(TriageWorkflowState.Attribute.DEV_SKU.name())){
	    device.setSku(TriageWorkflowState.getAttributeValue(TriageWorkflowState.Attribute.DEV_SKU, attributeMap));
	}
	if (attributeMap.containsKey(TriageWorkflowState.Attribute.DEV_HAS_BLACK_TIE.name())){
	    wa = attributeMap.get(TriageWorkflowState.Attribute.DEV_HAS_BLACK_TIE.name());
	    if (wa != null){
		device.setBlackTieProtection(DataDef.convertStringToBoolean(wa.getValue()));
	    }else{
		device.setBlackTieProtection(null);
	    }
	}
	if (attributeMap.containsKey(TriageWorkflowState.Attribute.DEV_BLACK_TIE_NUM.name())){
	    if (device.getProtectionPlan() == null){
		device.setProtectionPlan(new ProtectionPlan());
	    }
	    device.getProtectionPlan().setPlanNumber(
		    TriageWorkflowState
			    .getAttributeValue(TriageWorkflowState.Attribute.DEV_BLACK_TIE_NUM, attributeMap));
	}
	return device;
    }

    public static void augmentAttributeMapFromTriageWorkflowState(Map<String, WorkflowAttribute> attributeMap,
	    TriageWorkflowState triageWorkflowState) {
	if (triageWorkflowState == null){
	    return;
	}
	String issue = null;
	if (triageWorkflowState.getIssue() != null){
	    issue = triageWorkflowState.getIssue().name();
	}
	TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.PROBLEM_TYPE, issue, attributeMap);
	augmentAttributeMapFromTriageWorkflowDevice(attributeMap, triageWorkflowState.getDevice());
    }

    public static void augmentAttributeMapFromTriageWorkflowDevice(Map<String, WorkflowAttribute> attributeMap,
	    Device device) {
	if (device == null){
	    TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_OS, null, attributeMap);
	    TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_SKU, null, attributeMap);
	    TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_HAS_BLACK_TIE, null, attributeMap);
	    TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_BLACK_TIE_NUM, null, attributeMap);
	    return;
	}
	TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_OS, device.getOs(), attributeMap);
	TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_SKU, device.getSku(), attributeMap);
	TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_HAS_BLACK_TIE, DataDef
		.convertBooleanToString(device.getBlackTieProtection()), attributeMap);
	String protectionPlanNumber = null;
	if (device.getProtectionPlan() != null){
	    protectionPlanNumber = device.getProtectionPlan().getPlanNumber();
	}
	TriageWorkflowState.setAttributeValue(TriageWorkflowState.Attribute.DEV_BLACK_TIE_NUM, protectionPlanNumber,
		attributeMap);
    }

    @Override
    public long create(WorkflowBuilder<?> workflowBuilder, User user) throws ServiceException {
	String type = workflowBuilder.getWorkflowType();
	try{
	    return workflowDao.addWorkflow(workflowBuilder, user, DRPWorkflowEndStateEnum.INPROGRESS.name());
	}catch(DataAccessException e){
	    logger.error("Failed to add workflow of type " + type, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public void persistWorkflowState(TriageWorkflowState rulesWorkflowState, User user) throws ServiceException {
	try{
	    Map<String, WorkflowAttribute> attributeMap = new HashMap<String, WorkflowAttribute>();
	    augmentAttributeMapFromTriageWorkflowState(attributeMap, rulesWorkflowState);
	    workflowDao.persistWorkflowAttributeMap(attributeMap, rulesWorkflowState.getWorkflowId(), user);
	}catch(DataAccessException e){
	    logger.error("Failed to persist attribute map for workflow with ID " + rulesWorkflowState.getWorkflowId(),
		    e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}
    }

    @Override
    public Dialog convertToDialog(WorkflowTaskDialog dialog) throws ServiceException {
	if (dialog != null){
	    if (dialog instanceof SingleChoiceWithExplanationWorkflowDialog){
		return singleChoiceWithExplanationConverter((SingleChoiceWithExplanationWorkflowDialog) dialog);
	    }else if (dialog instanceof SingleChoiceWorkflowDialog){
		return singleChoiceConverter((SingleChoiceWorkflowDialog) dialog, SingleChoiceWorkflowDialog.class);
	    }else if (dialog instanceof SingleTrueFalseActionWorkflowDialog){
		return singleTrueFalseActionConverter((SingleTrueFalseActionWorkflowDialog) dialog);
	    }else if (dialog instanceof MultipleTrueFalseActionsWorkflowDialog){
		return multipleTrueFalseActionConverter((MultipleTrueFalseActionsWorkflowDialog) dialog);
	    }else if (dialog instanceof SuccessfulCompletionWorkflowDialog){
		return successfulCompletionConverter((SuccessfulCompletionWorkflowDialog) dialog);
	    }else{
		logger.error("Dialog name not handled in convertToDialog: " + dialog.getClass().getSimpleName());
		throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	    }
	}else{
	    logger.error("Input dialog invalid for convertToDialog");
	    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	}

    }

    @Override
    public WorkflowTaskDialog convertToWorkflowTaskDialog(Dialog dialog) throws ServiceException {
	if (dialog != null && dialog.getName() != null){
	    String name = dialog.getName();
	    if (name.equals(SingleChoiceWorkflowDialog.class.getSimpleName())){
		return singleChoiceConverter(dialog);
	    }else if (name.equals(SingleTrueFalseActionWorkflowDialog.class.getSimpleName())){
		return singleTrueFalseActionConverter(dialog);
	    }else if (name.equals(MultipleTrueFalseActionsWorkflowDialog.class.getSimpleName())){
		return multipleTrueFalseActionConverter(dialog);
	    }else if (name.equals(SingleChoiceWithExplanationWorkflowDialog.class.getSimpleName())){
		return singleChoiceWithExplanationConverter(dialog);
	    }else if (name.equals(SuccessfulCompletionWorkflowDialog.class.getSimpleName())){
		return successfulCompletionConverter(dialog);
	    }else{
		logger.error("Dialog name not handled in convertToWorkflowTaskDialog: "
			+ dialog.getClass().getSimpleName());
		throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	    }
	}else{
	    logger.error("Input dialog invalid for convertToWorkflowTaskDialog");
	    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	}
    }

    @Override
    public WorkflowTaskDialog execute(long workflowId, WorkflowTaskDialog dialog, User user) throws ServiceException {
	WorkflowTaskDialog wtd = super.execute(workflowId, dialog, user);
	if (wtd == null){
	    return wtd;
	}
	if (wtd instanceof SuccessfulCompletionWorkflowDialog){
	    finish(workflowId, DRPWorkflowEndStateEnum.SUCCESS.name(), user);
	}
	return wtd;
    }

    private SingleChoiceWorkflowDialog singleChoiceConverter(Dialog dialog) throws ServiceException {
	return genericSingleChoiceWithExplanationConverter(dialog, SingleChoiceWorkflowDialog.class);
    }

    private Dialog singleChoiceConverter(SingleChoiceWorkflowDialog inboundDialog,
	    Class<? extends WorkflowTaskDialog> classType) throws ServiceException {
	Dialog outboundDialog = new Dialog();
	outboundDialog.setId(inboundDialog.getId());
	outboundDialog.setName(classType.getSimpleName());

	if (inboundDialog != null && inboundDialog.getQuestion() != null
		&& inboundDialog.getQuestion().getCode() != null && inboundDialog.getSelectedOption() != null
		&& inboundDialog.getSelectedOption().getCode() != null){
	    DialogQuestion question = new DialogQuestion();
	    question.setType(DialogQuestion.AnswerType.SELECT);
	    question.setAnswer(inboundDialog.getSelectedOption().getCode());
	    question.setId(inboundDialog.getQuestion().getInstanceId());
	    question.setName(inboundDialog.getQuestion().getCode());
	    List<DialogQuestion> questionList = new ArrayList<DialogQuestion>();
	    questionList.add(question);
	    outboundDialog.setQuestionList(questionList);
	    return outboundDialog;
	}else{
	    logger.error("Invalid inboundDialog for singleChoiceConverter");
	    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	}
    }

    private SingleChoiceWithExplanationWorkflowDialog singleChoiceWithExplanationConverter(Dialog dialog)
	    throws ServiceException {
	return (SingleChoiceWithExplanationWorkflowDialog) genericSingleChoiceWithExplanationConverter(dialog,
		SingleChoiceWithExplanationWorkflowDialog.class);
    }

    private Dialog singleChoiceWithExplanationConverter(SingleChoiceWithExplanationWorkflowDialog dialog)
	    throws ServiceException {
	Dialog outboundDialog = singleChoiceConverter(dialog, SingleChoiceWithExplanationWorkflowDialog.class);
	// TODO where do we add the response field
	return outboundDialog;
    }

    private SingleChoiceWorkflowDialog genericSingleChoiceWithExplanationConverter(Dialog dialog,
	    Class<? extends WorkflowTaskDialog> classType) throws ServiceException {
	List<DialogQuestion> questionList = dialog.getQuestionList();
	if (questionList.size() == 1){
	    DialogQuestion question = questionList.get(0);
	    if (question != null){
		List<TitleAndDescription> options = new ArrayList<TitleAndDescription>();
		if (question.getChoices() != null && !question.getChoices().isEmpty()){
		    for(DialogAnswer option: question.getChoices()){
			String optionTitle = option.getAnswer();
			String optionKey = option.getName();
			options.add(new TitleAndDescription(optionTitle, null, optionKey));
		    }
		    if (question.getQuestion() == null){
			logger
				.error("Input dialog contained invalid question question for genericSingleChoiceWithExplanationConverter");
			throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
		    }

		    TitleAndDescription questionInfo = new TitleAndDescription(question.getQuestion().getQuestion(),
			    null, question.getQuestion().getName(), question.getId());
		    Instruction instructionInfo = new Instruction(question.getQuestion().getInstruction(), question
			    .getQuestion().getDetail(), null, question.getQuestion().getName(), question.getId());

		    SingleChoiceWorkflowDialog singleChoiceWorkflowDialog = null;
		    if (classType == null || classType.equals(SingleChoiceWorkflowDialog.class)){
			singleChoiceWorkflowDialog = new SingleChoiceWorkflowDialog(options, questionInfo,
				instructionInfo);
		    }else if (classType.equals(SingleChoiceWithExplanationWorkflowDialog.class)){
			singleChoiceWorkflowDialog = new SingleChoiceWithExplanationWorkflowDialog(options,
				questionInfo, instructionInfo);
		    }
		    if (dialog.getStep() != null){
			singleChoiceWorkflowDialog.setStepSequence(dialog.getStep().getSequenceNum());
		    }
		    singleChoiceWorkflowDialog.setId(dialog.getId());
		    singleChoiceWorkflowDialog.setImageUrl(question.getQuestion().getImageUrl());
		    if ("R".equals(question.getQuestion().getDisplayType())){
			singleChoiceWorkflowDialog.setRenderRadio(true);
		    }
		    return singleChoiceWorkflowDialog;
		}else{
		    logger
			    .error("Input dialog contained invalid question answer choices for genericSingleChoiceWithExplanationConverter");
		    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
		}
	    }else{
		logger.error("Input dialog contained invalid question for genericSingleChoiceWithExplanationConverter");
		throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	    }
	}else{
	    logger.error("Input dialog contained " + questionList.size()
		    + " questions, expected just one for genericSingleChoiceWithExplanationConverter");
	    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	}
    }

    private SingleTrueFalseActionWorkflowDialog singleTrueFalseActionConverter(Dialog dialog) throws ServiceException {
	List<DialogQuestion> questionList = dialog.getQuestionList();
	if (questionList.size() == 1){
	    DialogQuestion question = questionList.get(0);
	    if (question != null){
		if (question.getQuestion() == null){
		    logger.error("Input dialog contained invalid question question for singleTrueFalseActionConverter");
		    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
		}

		TrueFalseQuestion questionInfo = new TrueFalseQuestion(question.getQuestion().getQuestion(), null,
			question.getQuestion().getName(), question.getId());
		Instruction instructionInfo = new Instruction(question.getQuestion().getInstruction(), question
			.getQuestion().getDetail(), null, question.getQuestion().getName(), question.getId());

		TrueFalseAction action = new TrueFalseAction();
		action.setQuestion(questionInfo);
		action.setInstruction(instructionInfo);

		SingleTrueFalseActionWorkflowDialog singleTrueFalseActionWorkflowDialog = new SingleTrueFalseActionWorkflowDialog(
			action);
		if (dialog.getStep() != null){
		    singleTrueFalseActionWorkflowDialog.setStepSequence(dialog.getStep().getSequenceNum());
		}
		singleTrueFalseActionWorkflowDialog.setImageUrl(question.getQuestion().getImageUrl());
		singleTrueFalseActionWorkflowDialog.setId(dialog.getId());
		return singleTrueFalseActionWorkflowDialog;
	    }else{
		logger.error("Input dialog contained invalid question for singleTrueFalseActionConverter");
		throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	    }
	}else{
	    logger.error("Input dialog contained " + questionList.size()
		    + " questions, expected just one for singleTrueFalseActionConverter");
	    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	}
    }

    private Dialog singleTrueFalseActionConverter(SingleTrueFalseActionWorkflowDialog inboundDialog)
	    throws ServiceException {
	Dialog outboundDialog = new Dialog();
	outboundDialog.setName(SingleTrueFalseActionWorkflowDialog.class.getSimpleName());
	outboundDialog.setId(inboundDialog.getId());

	if (inboundDialog != null && inboundDialog.getActionTaken() != null
		&& inboundDialog.getActionTaken().getQuestion() != null
		&& inboundDialog.getActionTaken().getQuestion().getAnswer() != null
		&& inboundDialog.getActionDescribed().getQuestion() != null){
	    Boolean inboundAnswer = inboundDialog.getActionTaken().getQuestion().getAnswer();
	    DialogQuestion question = new DialogQuestion();
	    question.setType(DialogQuestion.AnswerType.YES_NO);
	    question.setAnswerText(inboundAnswer?"Y":"N");
	    question.setId(inboundDialog.getActionDescribed().getQuestion().getInstanceId());
	    question.setName(inboundDialog.getActionDescribed().getQuestion().getCode());
	    List<DialogQuestion> questionList = new ArrayList<DialogQuestion>();
	    questionList.add(question);
	    outboundDialog.setQuestionList(questionList);
	    return outboundDialog;
	}else{
	    logger.error("Input inboundDialog invalid for singleTrueFalseActionConverter");
	    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	}
    }

    private SuccessfulCompletionWorkflowDialog successfulCompletionConverter(Dialog dialog) throws ServiceException {
	List<DialogQuestion> questionList = dialog.getQuestionList();
	if (questionList.size() == 1){
	    DialogQuestion question = questionList.get(0);
	    if (question != null){
		if (question.getQuestion() == null){
		    logger.error("Input dialog contained invalid question question for successfulCompletionConverter");
		    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
		}

		Instruction instructionInfo = new Instruction(question.getQuestion().getInstruction(), question
			.getQuestion().getDetail(), null, question.getQuestion().getName(), question.getId());

		SuccessfulCompletionWorkflowDialog successfulCompletionWorkflowDialog = new SuccessfulCompletionWorkflowDialog();
		successfulCompletionWorkflowDialog.setInstruction(instructionInfo);
		successfulCompletionWorkflowDialog.setId(dialog.getId());
		if (dialog.getStep() != null){
		    successfulCompletionWorkflowDialog.setStepSequence(dialog.getStep().getSequenceNum());
		}
		successfulCompletionWorkflowDialog.setImageUrl(question.getQuestion().getImageUrl());
		return successfulCompletionWorkflowDialog;
	    }else{
		logger.error("Input dialog contained invalid question for successfulCompletionConverter");
		throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	    }
	}else{
	    logger.error("Input dialog contained " + questionList.size()
		    + " questions, expected just one for successfulCompletionConverter");
	    throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
	}
    }

    private Dialog successfulCompletionConverter(SuccessfulCompletionWorkflowDialog inboundDialog)
	    throws ServiceException {
	logger.error("Method successfulCompletionConverter (returns Dialog) not implemented");
	throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
    }

    private MultipleTrueFalseActionsWorkflowDialog multipleTrueFalseActionConverter(Dialog dialog)
	    throws ServiceException {
	logger
		.error("Method multipleTrueFalseActionConverter (returns MultipleTrueFalseActionsWorkflowDialog) not implemented");
	throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
    }

    private Dialog multipleTrueFalseActionConverter(MultipleTrueFalseActionsWorkflowDialog dialog)
	    throws ServiceException {
	logger.error("Method multipleTrueFalseActionConverter (returns Dialog) not implemented");
	throw new ServiceException(IseExceptionCodeEnum.DataConversionError);
    }

    public static void log(String format, Object... objects) {
	logger.debug(format, objects);
    }

    @Override
    public String getRulesProblemStatus() {
	return DRPWorkflowEndStateEnum.BAD_STEP.name();
    }
}
