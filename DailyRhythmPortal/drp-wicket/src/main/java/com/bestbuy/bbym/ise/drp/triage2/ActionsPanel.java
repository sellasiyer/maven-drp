package com.bestbuy.bbym.ise.drp.triage2;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.ServiceBasePanel;
import com.bestbuy.bbym.ise.drp.domain.TriageAction;
import com.bestbuy.bbym.ise.drp.domain.TriageEvent;
import com.bestbuy.bbym.ise.drp.domain.TriageIssue;
import com.bestbuy.bbym.ise.drp.domain.TriageResolution;
import com.bestbuy.bbym.ise.drp.entitlement.EntitlementSearchPage;
import com.bestbuy.bbym.ise.drp.service.TriageService;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class ActionsPanel extends ServiceBasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(ActionsPanel.class);
    private static final String NOT_AVAILABLE = "N/A";
    private static final Long TRIAGE_RSLN_ENTL_ID = 0L;

    @SpringBean(name = "triageService")
    private TriageService triageService;

    private List<TriageAction> actionList = new ArrayList<TriageAction>();
    private TriageIssue issue;
    private boolean checkOptionsLinkEnabled = false;

    private AjaxLink<Void> checkOptionsLink;
    private WebMarkupContainer actionsListViewContainer;

    public ActionsPanel(String id, final ResolutionCaptureModalPanel rslnCaptureModalPanel) {
	super(id);

	final String na = getString("notApplicable.label");

	DailyRhythmPortalSession session = getDailyRhythmPortalSession();

	if (session.getSelectedTriageIssue() != null){
	    issue = session.getSelectedTriageIssue();
	}else{
	    issue = new TriageIssue();
	}
	logger.debug("selectedTriageIssue=" + issue);

	final Label issueLabel = new Label("issueLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (issue.getIssueDesc() == null){
		    return na;
		}
		return issue.getIssueDesc();
	    }

	});
	issueLabel.setRenderBodyOnly(true);
	issueLabel.setOutputMarkupId(true);
	add(issueLabel);

	Label suggestionLabel = new Label("suggestionLabel", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (issue.getSelectedRecommendation() == null
			|| issue.getSelectedRecommendation().getRecommendation() == null){
		    return na;
		}
		return issue.getSelectedRecommendation().getRecommendation();
	    }

	});
	suggestionLabel.setOutputMarkupId(true);
	add(suggestionLabel);

	actionsListViewContainer = new WebMarkupContainer("actionsListViewContainer");
	actionsListViewContainer.setOutputMarkupId(true);
	add(actionsListViewContainer);

	final ListView<TriageAction> actionsListView = new ListView<TriageAction>("actionsListView", actionList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<TriageAction> item) {
		TriageAction action = (TriageAction) item.getModelObject();
		Label label = new Label("label", action.getAction());
		label.setEscapeModelStrings(false);
		item.add(label);
	    }
	};
	actionsListView.setOutputMarkupId(true);
	actionsListViewContainer.add(actionsListView);

	final AjaxLink<Void> resolvedLink = new AjaxLink<Void>("resolvedLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		getPage().get("feedback").setVisible(false);
		rslnCaptureModalPanel.open(target);
	    }

	};
	add(resolvedLink);

	checkOptionsLink = new AjaxLink<Void>("checkOptionsLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		try{
		    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
		    Device device = session.getSelectedLine().getDevice();

		    TriageEvent triageEvent = new TriageEvent();
		    triageEvent.setDeviceSerialNo(device.getSerialNumber());
		    triageEvent.setIssueComment(session.getSelectedTriageIssue().getIssueComment());
		    if (device.getProtectionPlan() != null){
			triageEvent.setProtectionPlanId(device.getProtectionPlan().getPlanNumber());
		    }
		    triageEvent.setResolutionComment(null);

		    if (session.getSelectedTriageIssue().getTechCheckerIssue() == null){
			triageEvent.setTechCheckerIssues(NOT_AVAILABLE);
		    }else{
			triageEvent.setTechCheckerIssues(session.getSelectedTriageIssue().getTechCheckerIssue());
		    }

		    triageEvent.setTriageRecommendation(session.getSelectedTriageIssue().getSelectedRecommendation());
		    TriageResolution triageRsln = new TriageResolution();
		    triageRsln.setId(TRIAGE_RSLN_ENTL_ID);
		    triageEvent.setTriageResolution(triageRsln);

		    triageEvent = triageService.addTriageEvent(triageEvent, session.getDrpUser());

		    session.setTriageEvent(triageEvent);

		}catch(ServiceException e){
		    String message = "An unexpected exception occured while attempting to create/update a triage history.";
		    logger.error(message, e);
		    processException(message, e, getPage().getPageParameters());
		}

		setResponsePage(EntitlementSearchPage.class);
	    }

	    @Override
	    public boolean isEnabled() {
		return checkOptionsLinkEnabled;
	    }
	};
	checkOptionsLink.add(AttributeModifier.append("class", new Model<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public String getObject() {
		if (checkOptionsLinkEnabled){
		    return "";
		}
		return "disabled";
	    }
	}));
	add(checkOptionsLink);
    }

    @Override
    public String doInitialServiceCalls() {
	checkOptionsLinkEnabled = getBooleanProperty("TRIAGE_CHECK_OPTIONS_BUTTON_ENABLED", true);
	actionList.clear();
	issue.setSelectedRecommendation(null);
	String error = getString("triageActionsPanel.getTriageActionsFailed.message.label");
	try{
	    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	    Device device = session.getSelectedLine().getDevice();
	    issue.setSelectedRecommendation(triageService.getRecommendation(issue, device.getSku()));
	}catch(ServiceException se){
	    logger.error("ServiceException getting triage recommendation", se);
	    return error + " " + se.getMessage();
	}
	try{
	    List<TriageAction> triageActions = triageService.getActions(issue.getSelectedRecommendation());
	    if (triageActions != null && !triageActions.isEmpty()){
		for(TriageAction ta: triageActions){
		    actionList.add(ta);
		}
	    }
	}catch(ServiceException se){
	    logger.error("ServiceException getting triage actions", se);
	    return error + " " + se.getMessage();
	}
	return null;
    }

    @Override
    public void updatePanel(AjaxRequestTarget target) {
	target.add(checkOptionsLink);
	target.add(actionsListViewContainer);
	target.add(this);
    }

}
