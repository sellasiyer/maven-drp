package com.bestbuy.bbym.ise.drp.triage;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.domain.Instruction;
import com.bestbuy.bbym.ise.drp.domain.TitleAndDescription;
import com.bestbuy.bbym.ise.drp.domain.TrueFalseQuestion;
import com.bestbuy.bbym.ise.drp.workflow.MultipleTrueFalseActionsWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWithExplanationWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SingleTrueFalseActionWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SuccessfulCompletionWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.triage.TrueFalseAction;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowBuilder;
import com.bestbuy.bbym.ise.workflow.WorkflowService;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

/**
 * @author a186288
 * 
 */
public class MockWorkflowService implements WorkflowService {

    private static final long serialVersionUID = -6207522752121573249L;

    /* private static final long serialVersionUID = -7829660929809407894L; */

    @Override
    public long create(WorkflowBuilder<?> workflowBuilder, User user) throws ServiceException {

	return 1l;
	/* throw new ServiceException(IseExceptionCodeEnum.UnexpectedNull); */
    }

    @Override
    public WorkflowTaskDialog execute(long workflowId, User user) throws ServiceException {
	// TODO Auto-generated method stub

	TrueFalseAction level1Action = new TrueFalseAction();
	TrueFalseQuestion question = new TrueFalseQuestion("Did this action resolve the customer's issue?",
		"optional description or additional detail abbout the question", "1", 1l);
	level1Action.setQuestion(question);
	Instruction instruction = new
	/* Instruction("Pull the battery and check for water damage",
	 "If the battery is removable then pull out the <b>battery</b> and look for the <a href=\"http://google.com\">google</a>indicator strip that will show whether the phone has been exposed to moisture"
	 , "battery.jpg","1"); */
	Instruction("Pull the battery and check for water damage", null, "battery.jpg", "1", 1l);
	level1Action.setInstruction(instruction);
	SingleTrueFalseActionWorkflowDialog triageWorkflowTaskDialog = new SingleTrueFalseActionWorkflowDialog(
		level1Action);

	return triageWorkflowTaskDialog;

    }

    @Override
    public WorkflowTaskDialog execute(long workflowId, WorkflowTaskDialog oldWorkflowDialog, User user)
	    throws ServiceException {

	if (oldWorkflowDialog instanceof SingleTrueFalseActionWorkflowDialog){
	    SingleTrueFalseActionWorkflowDialog dialog = (SingleTrueFalseActionWorkflowDialog) oldWorkflowDialog;
	    if (dialog.getActionTaken().getQuestion().getAnswer()){
		return returnComplete();
	    }else{
		return returnMultiple();
	    }
	}else if (oldWorkflowDialog instanceof MultipleTrueFalseActionsWorkflowDialog){
	    // if yes return complete
	    TrueFalseAction actionTaken = ((MultipleTrueFalseActionsWorkflowDialog) oldWorkflowDialog).getActionTaken();

	    if (actionTaken.getQuestion().getAnswer()){
		return returnComplete();
	    }else{
		return returnOptions();
	    }
	}else if (oldWorkflowDialog instanceof SingleChoiceWithExplanationWorkflowDialog){
	    return new SuccessfulCompletionWorkflowDialog();
	}
	return null;
	// return new UnsuccessfulCompletionWorkflowDialog();

    }

    private WorkflowTaskDialog returnComplete() {
	List<TitleAndDescription> options = new ArrayList<TitleAndDescription>();

	options.add(new TitleAndDescription("Pulled Battery", null, "1"));
	options.add(new TitleAndDescription("Found Water Damage", null, "2"));
	options.add(new TitleAndDescription("Updated OS", null, "3"));

	Instruction instruction = new Instruction("identify the customer's issue", "check it out", null, "1", 1l);

	TitleAndDescription question = new TitleAndDescription("please choose the correct issue type", null,
		"issue_type", 22l);

	SingleChoiceWithExplanationWorkflowDialog dialog = new SingleChoiceWithExplanationWorkflowDialog(options,
		question, null);

	dialog.setSelectedOption(new TitleAndDescription("Pulled Battery", null, "1"));

	return dialog;
    }

    private WorkflowTaskDialog returnOptions() {

	List<TitleAndDescription> options = new ArrayList<TitleAndDescription>();

	options.add(new TitleAndDescription("Rapid Exchange", null, "1"));
	options.add(new TitleAndDescription("Return", null, "2"));
	options.add(new TitleAndDescription("Upgrade Check", null, "3"));

	Instruction instruction = new Instruction("identify the next step to take", "look for this", null, "1", 1l);

	TitleAndDescription question = new TitleAndDescription("What would the customer like to do next?", null,
		"next_Step", 22l);

	SingleChoiceWorkflowDialog dialog = new SingleChoiceWorkflowDialog(options, question, instruction);
	return dialog;
    }

    private SingleChoiceWorkflowDialog returnChoices() {

	List<TitleAndDescription> options = new ArrayList<TitleAndDescription>();

	options.add(new TitleAndDescription("Hardware Issue", null, "1"));
	options.add(new TitleAndDescription("Software Issue", null, "1"));
	options.add(new TitleAndDescription("Physical/Water Damage", null, "1"));
	options.add(new TitleAndDescription("Battery Issue", null, "1"));

	Instruction instruction = new Instruction("identify the issue type", null, null, "1", 1l);

	TitleAndDescription question = new TitleAndDescription("what type of Issue is the customer having?", null,
		"issue_type", 22l);

	SingleChoiceWorkflowDialog dialog = new SingleChoiceWorkflowDialog(options, question, instruction);

	return dialog;
    }

    private MultipleTrueFalseActionsWorkflowDialog returnMultiple() {
	List<TrueFalseAction> actions = new ArrayList<TrueFalseAction>();

	TrueFalseAction level2Action = new TrueFalseAction();
	TrueFalseQuestion question = new TrueFalseQuestion("Did this action resolve the customers issue?",
		"optional description or additional detail abbout the question", "1", 1l);
	level2Action.setQuestion(question);

	Instruction instruction = new Instruction(
		"Check for an OS update",
		"Look up the phones OS version and compare it to the latest published version of the OS.  Update the version accordingly.  "
			+ "An out of synch OS can cause apps to exit abruptly or freeze altogether", "battery.jpg",
		"1", 1l);
	level2Action.setInstruction(instruction);

	actions.add(level2Action);

	TrueFalseAction level2Action2 = new TrueFalseAction();
	TrueFalseQuestion question2 = new TrueFalseQuestion("Did this action resolve the customers issue?",
		"optional description or additional detail abbout the question", "1", 1l);
	level2Action2.setQuestion(question2);

	Instruction instruction2 = new Instruction(
		"Check for an OS update",
		"Look up the phones OS version and compare it to the latest published version of the OS.  Update the version accordingly.  "
			+ "An out of synch OS can cause apps to exit abruptly or freeze altogether", "battery.jpg",
		"1", 1l);
	level2Action2.setInstruction(instruction2);

	actions.add(level2Action2);

	TrueFalseAction finalAction = new TrueFalseAction();
	TrueFalseQuestion finalQuestion = new TrueFalseQuestion(
		"Click continue to view options for fixing the customer phone",
		"optional description or additional detail abbout the question", "1", 1l);
	finalAction.setQuestion(finalQuestion);

	Instruction FinalInstruction = new Instruction(
		"Check for an OS update",
		"Look up the phones OS version and compare it to the latest published version of the OS.  Update the version accordingly.  "
			+ "An out of synch OS can cause apps to exit abruptly or freeze altogether", "battery.jpg",
		"1", 1l);
	finalAction.setInstruction(FinalInstruction);

	MultipleTrueFalseActionsWorkflowDialog workflowTaskDialog = new MultipleTrueFalseActionsWorkflowDialog(actions,
		finalAction);

	return workflowTaskDialog;
    }

    @Override
    public WorkflowTaskDialog backup(long workflowId, User user) throws ServiceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public void finish(long workflowId, String status, User user) throws ServiceException {
	// TODO Auto-generated method stub

    }
}
