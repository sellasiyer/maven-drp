package com.bestbuy.bbym.ise.drp.triage;

import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.NoBackBaseNavPage;
import com.bestbuy.bbym.ise.drp.workflow.DRPWorkflowBuilder;
import com.bestbuy.bbym.ise.drp.workflow.DRPWorkflowEnum;
import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWithExplanationWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SingleChoiceWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SingleTrueFalseActionWorkflowDialog;
import com.bestbuy.bbym.ise.drp.workflow.SuccessfulCompletionWorkflowDialog;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.WorkflowService;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

/**
 * @author JAM0314
 */
public class TriagePage extends NoBackBaseNavPage {

    private static Logger logger = LoggerFactory.getLogger(TriagePage.class);
    private static final long serialVersionUID = 1L;

    @SpringBean(name = "workflowService")
    protected WorkflowService workflowService;

    public TriagePage(Long workflowId, WorkflowTaskDialog triageWorkflowTaskDialog) {
	super(null);

	boolean newWorkflow = false;

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	if (workflowId == null){
	    // create a specific instance of the service
	    newWorkflow = true;
	    try{
		Customer customer = session.getCustomer();
		DRPWorkflowBuilder builder = new DRPWorkflowBuilder(DRPWorkflowEnum.TRIAGE);
		if (customer != null){
		    builder.setWorkflowAttribute("CUSTOMER", customer);
		}
		workflowId = workflowService.create(builder, session.getDrpUser());
	    }catch(ServiceException se){
		String message = "An unexpected exception occured while attempting to create the workflow";
		logger.error(message, se);
		processException(message, se, workflowId, "Triage");
	    }
	}

	if (triageWorkflowTaskDialog == null){
	    if (newWorkflow){
		// start the workflow
		try{
		    triageWorkflowTaskDialog = workflowService.execute(workflowId, session.getDrpUser());
		}catch(ServiceException se){
		    String message = "An unexpected exception occured while attempting to execute the workflow";
		    logger.error(message, se);
		    processException(message, se, workflowId, "Triage");
		}
	    }else{
		String message = "A workflow dialog was unexpectedly null while attempting to execute the workflow";
		logger.error(message);
		processException(message, IseExceptionCodeEnum.UnsuccessfulWorkflowCompletion, workflowId, "Triage");
	    }
	}

	logger.info("Dialog is " + triageWorkflowTaskDialog.getClass().getSimpleName());

	if (triageWorkflowTaskDialog instanceof SingleTrueFalseActionWorkflowDialog){
	    SingleTrueFalseActionWorkflowDialog dlg = (SingleTrueFalseActionWorkflowDialog) triageWorkflowTaskDialog;
	    SingleTrueFalseActionTriagePanel level1TriagePanel = new SingleTrueFalseActionTriagePanel("triagePanel",
		    workflowService, dlg, workflowId);
	    add(level1TriagePanel.setVisible(true));

	}else if (triageWorkflowTaskDialog instanceof SingleChoiceWithExplanationWorkflowDialog){
	    SingleChoiceWithExplanationWorkflowDialog dlg = (SingleChoiceWithExplanationWorkflowDialog) triageWorkflowTaskDialog;
	    SingleChoiceWithExplanationTriagePanel singleChoiceWithExplanationTriagePanel = new SingleChoiceWithExplanationTriagePanel(
		    "triagePanel", workflowService, dlg, workflowId);
	    add(singleChoiceWithExplanationTriagePanel.setVisible(true));

	}else if (triageWorkflowTaskDialog instanceof SingleChoiceWorkflowDialog){
	    SingleChoiceWorkflowDialog dlg = (SingleChoiceWorkflowDialog) triageWorkflowTaskDialog;
	    SingleChoiceTriagePanel singleOptionsPanel = new SingleChoiceTriagePanel("triagePanel", workflowService,
		    dlg, workflowId);
	    add(singleOptionsPanel);

	}else if (triageWorkflowTaskDialog instanceof SuccessfulCompletionWorkflowDialog){
	    SuccessfulCompletionWorkflowDialog dlg = (SuccessfulCompletionWorkflowDialog) triageWorkflowTaskDialog;
	    SuccessfulCompletionTriagePanel successfulCompletionTriagePanel = new SuccessfulCompletionTriagePanel(
		    "triagePanel", workflowService, dlg, workflowId);
	    add(successfulCompletionTriagePanel);

	}else{
	    String message = "The workflow completed unsuccessfully";
	    processException(message, IseExceptionCodeEnum.UnsuccessfulWorkflowCompletion, workflowId, "Triage");
	}
    }
}
