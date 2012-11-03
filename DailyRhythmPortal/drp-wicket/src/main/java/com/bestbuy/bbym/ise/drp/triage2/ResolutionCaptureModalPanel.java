/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.triage2;

import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.Radio;
import org.apache.wicket.markup.html.form.RadioGroup;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.ModalPanel;
import com.bestbuy.bbym.ise.drp.dashboard.CustomerDashboardPage;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class ResolutionCaptureModalPanel extends ModalPanel {

    private static final String NOT_AVAILABLE = "N/A";
    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ResolutionCaptureModalPanel.class);

    @SpringBean(name = "triageService")
    private TriageService triageService;

    private Model<TriageResolution> selectedTriageRsln;
    private List<TriageResolution> triageRslnList;

    public ResolutionCaptureModalPanel(String id) {
	super(id);

	try{
	    triageRslnList = triageService.getResolutionList();
	}catch(ServiceException e){
	    String message = "An unexpected exception occured while attempting to load a triage resolution list.";
	    logger.error(message, e);
	    processException(message, e, getPage().getPageParameters());
	}

	AjaxLink<Object> closeLink = new AjaxLink<Object>("close") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		close(target);
	    }
	};
	add(closeLink);

	final Form<Object> form = new Form<Object>("form");
	add(form);

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	selectedTriageRsln = new Model<TriageResolution>();
	RadioGroup<TriageResolution> rslnRadioGroup = new RadioGroup<TriageResolution>("rslnRadioGroup",
		selectedTriageRsln);
	// rslnRadioGroup.setRequired(true).setLabel(new
	// Model<String>("Resolution type"));
	form.add(rslnRadioGroup);
	rslnRadioGroup.add(new ListView<TriageResolution>("rslnRadioChoice", new PropertyModel<List<TriageResolution>>(
		this, "triageRslnList")) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<TriageResolution> item) {
		item.add(new Radio<TriageResolution>("radio", item.getModel()));
		item.add(new Label("label", item.getModelObject().getResolutionDesc()));
	    }
	});

	final TextArea<String> rslnDetails = new TextArea<String>("rslnDetails", Model.of(new String("")));
	rslnDetails.setOutputMarkupPlaceholderTag(true);
	rslnDetails.add(StringValidator.maximumLength(500));
	form.add(rslnDetails);

	AjaxButton submitButton = new AjaxButton("submitButton", new ResourceModel(
		"triageResolutionCaptureModalPanel.submitButton.label"), form) {

	    private static final long serialVersionUID = 7632158538404618577L;

	    @Override
	    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
		logger.debug("cancelButton onSubmit");

		try{

		    if (selectedTriageRsln.getObject() == null){
			logger.debug("selectedTriageRsln=null");
			error(getString("triageResolutionCaptureModalPanel.resolutionRequired.message.label"));
			target.add(feedbackPanel);
			target.appendJavaScript("scrollToTop();");
			return;
		    }

		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    Device device = session.getSelectedLine().getDevice();

		    if (session.getTriageEvent() == null){
			TriageEvent triageEvent = new TriageEvent();
			triageEvent.setDeviceSerialNo(device.getSerialNumber());
			triageEvent.setIssueComment(session.getSelectedTriageIssue().getIssueComment());
			if (device.getProtectionPlan() != null){
			    triageEvent.setProtectionPlanId(device.getProtectionPlan().getPlanNumber());
			}
			triageEvent.setResolutionComment(rslnDetails.getModelObject());

			if (session.getSelectedTriageIssue().getTechCheckerIssue() == null){
			    triageEvent.setTechCheckerIssues(NOT_AVAILABLE);
			}else{
			    triageEvent.setTechCheckerIssues(session.getSelectedTriageIssue().getTechCheckerIssue());
			}

			triageEvent.setTriageRecommendation(session.getSelectedTriageIssue()
				.getSelectedRecommendation());
			triageEvent.setTriageResolution(selectedTriageRsln.getObject());

			triageService.addTriageEvent(triageEvent, session.getDrpUser());
		    }else{
			TriageEvent triageEvent = session.getTriageEvent();
			triageEvent.setResolutionComment(rslnDetails.getModelObject());
			triageEvent.setTriageResolution(selectedTriageRsln.getObject());
			triageEvent.setCustomerBenefits(null);
			triageService.updateTriageHistoryCustomerBenefit(triageEvent);
			session.setTriageEvent(null);
		    }

		}catch(ServiceException e){
		    String message = "An unexpected exception occured while attempting to create/update a triage history.";
		    logger.error(message, e);
		    processException(message, e, getPage().getPageParameters());
		}
		setResponsePage(CustomerDashboardPage.class);
	    }

	    @Override
	    protected void onError(AjaxRequestTarget target, Form<?> form) {
		logger.debug("cancelButton onError");
		target.add(feedbackPanel);
	    }
	};

	submitButton.setOutputMarkupPlaceholderTag(true);
	form.add(submitButton);

    }

    @Override
    public void onClose(AjaxRequestTarget target) {
	getPage().get("feedback").setVisible(true);
    }

    public TriageService getTriageService() {
	return triageService;
    }

    public void setTriageService(TriageService triageService) {
	this.triageService = triageService;
    }

    public List<TriageResolution> getTriageRslnList() {
	return triageRslnList;
    }

    public void setTriageRslnList(List<TriageResolution> triageRslnList) {
	this.triageRslnList = triageRslnList;
    }

    @Override
    public void update(AjaxRequestTarget target) {
    }

    @Override
    public String getModalSelector() {
	return "#" + getId() + " .new-modal";
    }
}
