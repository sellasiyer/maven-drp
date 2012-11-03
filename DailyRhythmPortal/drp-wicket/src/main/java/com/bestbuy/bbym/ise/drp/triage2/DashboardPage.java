/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.triage2;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.NewBaseNavPage;

/**
 * Wicket page class for triage dashboard screen.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 21
 */
public class DashboardPage extends NewBaseNavPage {

    private static final long serialVersionUID = 9049588227891993594L;
    private static Logger logger = LoggerFactory.getLogger(DashboardPage.class);

    private AbstractDefaultAjaxBehavior wicketBehavior;
    private String actionsPanelInitError, knowledgePanelInitError, techCheckerPanelInitError;

    public DashboardPage(final PageParameters parameters) {
	super(parameters);
	final DashboardPage thisPage = this;

	final FeedbackPanel feedbackPanel = new FeedbackPanel("feedback");
	feedbackPanel.setOutputMarkupPlaceholderTag(true);
	add(feedbackPanel);

	DeviceInfoPanel deviceInfoPanel = new DeviceInfoPanel("deviceInfoPanel");
	deviceInfoPanel.setOutputMarkupPlaceholderTag(true);
	add(deviceInfoPanel);

	final ResolutionCaptureModalPanel rslnCaptureModalPanel = new ResolutionCaptureModalPanel(
		"rslnCaptureModalPanel");
	rslnCaptureModalPanel.setOutputMarkupPlaceholderTag(true);
	add(rslnCaptureModalPanel);

	final ActionsPanel actionsPanel = new ActionsPanel("steps", rslnCaptureModalPanel);
	actionsPanel.setMarkupId("steps");
	actionsPanel.setOutputMarkupId(true);
	actionsPanel.setOutputMarkupPlaceholderTag(true);
	add(actionsPanel);

	final KnowledgePanel knowledgePanel = new KnowledgePanel("knowledgebase");
	knowledgePanel.setMarkupId("knowledgebase");
	knowledgePanel.setOutputMarkupId(true);
	knowledgePanel.setOutputMarkupPlaceholderTag(true);
	add(knowledgePanel);

	final TechCheckerPanel techCheckerPanel = new TechCheckerPanel("tech-checker-results");
	techCheckerPanel.setMarkupId("tech-checker-results");
	techCheckerPanel.setOutputMarkupId(true);
	techCheckerPanel.setOutputMarkupPlaceholderTag(true);
	add(techCheckerPanel);

	// means by which JS can callback into Java
	wicketBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBehavior id=" + id);
		if ("startup-actions".equals(id)){
		    if (!thisPage.isLoadingModalOpen()){
			thisPage.openLoadingModal(getString("triageActionsPanel.loading.label"), target);
			target.appendJavaScript("doWicketBehavior('wicketBehavior(\"startup-actions\")');");
			return;
		    }
		    actionsPanelInitError = actionsPanel.doInitialServiceCalls();
		    thisPage.setChangeLoadingModalMessage(true);
		    target.appendJavaScript("doWicketBehavior('wicketBehavior(\"startup-knowledge\")');");

		}else if ("startup-knowledge".equals(id)){
		    if (thisPage.isChangeLoadingModalMessage()){
			thisPage.changeLoadingModalMessage(getString("triageKnowledge.loading.label"), target);
			thisPage.setChangeLoadingModalMessage(false);
			target.appendJavaScript("doWicketBehavior('wicketBehavior(\"startup-knowledge\")');");
			return;
		    }
		    knowledgePanelInitError = knowledgePanel.doInitialServiceCalls();
		    thisPage.setChangeLoadingModalMessage(true);
		    target.appendJavaScript("doWicketBehavior('wicketBehavior(\"startup-techChecker\")');");

		}else if ("startup-techChecker".equals(id)){
		    if (thisPage.isChangeLoadingModalMessage()){
			thisPage.changeLoadingModalMessage(getString("triageTechChecker.loading.label"), target);
			thisPage.setChangeLoadingModalMessage(false);
			target.appendJavaScript("doWicketBehavior('wicketBehavior(\"startup-techChecker\")');");
			return;
		    }
		    techCheckerPanelInitError = techCheckerPanel.doInitialServiceCalls();
		    thisPage.closeLoadingModal(target);
		    actionsPanel.updatePanel(target);
		    knowledgePanel.updatePanel(target);
		    techCheckerPanel.updatePanel(target);
		    if (actionsPanelInitError != null){
			error(actionsPanelInitError);
		    }
		    if (knowledgePanelInitError != null){
			error(knowledgePanelInitError);
		    }
		    if (techCheckerPanelInitError != null){
			error(techCheckerPanelInitError);
		    }
		    target.add(feedbackPanel);
		}
	    }
	};
	add(wicketBehavior);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		onDomReadyJS.append("wicketBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(wicketBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");
		onDomReadyJS.append("doWicketBehavior('wicketBehavior(\"startup-actions\")');");
		logger.debug("onDomReadyJS=" + onDomReadyJS.toString());
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
    }

}
