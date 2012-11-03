package com.bestbuy.bbym.ise.drp.triage;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.common.Styles;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.error.DRPWorkflowErrorPage;
import com.bestbuy.bbym.ise.drp.workflow.DRPWorkflowEndStateEnum;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.workflow.BaseWorkflowTaskDialog;
import com.bestbuy.bbym.ise.workflow.WorkflowService;
import com.bestbuy.bbym.ise.workflow.WorkflowTaskDialog;

/**
 * @author a186288
 */
public abstract class BaseTriagePanel extends BasePanel {

    private static final long serialVersionUID = -284407924639342104L;
    private static Logger logger = LoggerFactory.getLogger(BaseTriagePanel.class);

    protected Form<Object> triageForm;
    protected FeedbackPanel feedbackPanel;

    public BaseTriagePanel(String id, final WorkflowService workflowService, final WorkflowTaskDialog workflowDialog,
	    final long workflowId) {
	super(id);

	if (workflowDialog == null){
	    String message = "The workflow dialog was unexpectedly null";
	    processException(message, new ServiceException(IseExceptionCodeEnum.UnexpectedNull), workflowId, "Triage",
		    new PageParameters());
	    return;
	}

	triageForm = new Form<Object>("triageForm");
	add(triageForm);

	feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	triageForm.add(feedbackPanel);

	Button cancel = new Button("cancel", new Model<String>("Cancel")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		// call finish with workflowID
		try{
		    User user = getDailyRhythmPortalSession().getDrpUser();
		    workflowService.finish(workflowId, DRPWorkflowEndStateEnum.CANCEL.name(), user);
		    throw new RestartResponseException(CustomerDashboardPage.class);
		}catch(ServiceException e){
		    String message = "An unexpected exception occured while attempting to finish the workflow";
		    processException(message, e, workflowId, "Triage", new PageParameters());
		}
	    }
	};
	cancel.setDefaultFormProcessing(false);
	triageForm.add(cancel);

	Button back = new Button("back", new Model<String>("Back")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onSubmit() {
		logger.debug("on submit of back button...");
		// call backup with workflowId
		User user = getDailyRhythmPortalSession().getDrpUser();
		WorkflowTaskDialog dialog = null;
		try{
		    dialog = workflowService.backup(workflowId, user);
		}catch(ServiceException e){
		    String message = "An unexpected exception occured while attempting to go back on the workflow";
		    processException(message, e, workflowId, "Triage", new PageParameters());
		}

		final PageParameters parameters = new PageParameters();

		if (dialog != null){
		    // submit dialog back to the triagePage
		    setResponsePage(new TriagePage(Long.valueOf(workflowId), dialog));
		}else{
		    getSession().error("Unexpected null dialog when attempting to go back");
		    parameters.add(PageParameterKeys.WORKFLOW_NAME.name(), "Triage");
		    parameters.add(PageParameterKeys.WORKFLOW_ID.name(), workflowId);
		    parameters.add(PageParameterKeys.EXCEPTION_CODE.name(),
			    IseExceptionCodeEnum.UnsuccessfulWorkflowCompletion);
		    throw new RestartResponseException(DRPWorkflowErrorPage.class, parameters);
		}
	    }

	    @Override
	    protected void onComponentTag(ComponentTag tag) {
		super.onComponentTag(tag);
		if (workflowDialog.getStepSequence() != 1){
		    tag.put("class", Styles.TRIAGE_BUTTON_ACTIVE);
		}else{
		    tag.put("class", Styles.TRIAGE_BUTTON_INACTIVE);
		}
	    }

	    @Override
	    public boolean isEnabled() {
		return workflowDialog.getStepSequence() != 1;
	    }
	};
	back.setDefaultFormProcessing(false);
	triageForm.add(back);

	String displayImageUrl = null;
	if (workflowDialog instanceof BaseWorkflowTaskDialog){
	    BaseWorkflowTaskDialog bwtd = (BaseWorkflowTaskDialog) workflowDialog;
	    if (bwtd.getImageUrl() != null){
		displayImageUrl = bwtd.getImageUrl();
	    }
	}
	if (displayImageUrl == null){
	    displayImageUrl = getString("defaultTriageImageUrl");
	}
	triageForm.add(new Image("dialog_image", new ContextRelativeResource(displayImageUrl)));

	// TODO keep this code!
	// will eventually get image URLs from database
	// logger.info("DISPLAY IMG=" + displayImageUrl);
	// Image img = new Image("dialog_image");
	// if (displayImageUrl == null){
	// img.setVisible(false);
	// }else{
	// img.add(new SimpleAttributeModifier("src",
	// getString("imageServerBaseUrl") + displayImageUrl));
	// }
	// triageForm.add(img);
    }

    public class OpenWindowOnLoadBehavior extends AbstractDefaultAjaxBehavior {

	private static final long serialVersionUID = 1L;

	@Override
	protected void respond(AjaxRequestTarget target) {
	    logger.info("in OpenWindowOnLoadBehavior respond...");
	    ModalWindow window = (ModalWindow) getComponent();
	    window.show(target);
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
	    response.renderOnLoadJavaScript(getCallbackScript().toString());
	}
    }
}
