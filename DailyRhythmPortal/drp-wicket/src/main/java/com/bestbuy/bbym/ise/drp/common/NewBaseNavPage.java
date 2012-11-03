package com.bestbuy.bbym.ise.drp.common;

import java.util.Date;

import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.util.Util;

public abstract class NewBaseNavPage extends BaseNavPage {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(NewBaseNavPage.class);

    private String actionToLaunch;
    private boolean getClientTime = false;
    private Date clientTime;
    private int clientTimeZoneOffsetMinutes;

    protected LoadingModalPanel loadingModalPanel;
    private boolean changeLoadingModalMessage = false;
    private AbstractDefaultAjaxBehavior wicketBaseBehavior;

    public NewBaseNavPage(PageParameters parameters) {
	super(parameters);

	loadingModalPanel = new LoadingModalPanel("loadingModalPanel") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClose(AjaxRequestTarget target) {
	    }
	};
	add(loadingModalPanel);
	loadingModalPanel.setOutputMarkupPlaceholderTag(true);

	// means by which JS can callback into Java
	wicketBaseBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		String id = RequestCycle.get().getRequest().getRequestParameters().getParameterValue("id").toString();
		logger.debug("wicketBaseBehavior id=" + id);
		if (id != null && id.startsWith("clientTime")){
		    clientTime = Util.convertClientTime(id.substring("clientTime".length()), new Date());
		    logger.info("clientTime=" + Util.toStringTime(clientTime));
		    clientTimeZoneOffsetMinutes = Util.getClientTimeZoneOffset(id.substring("clientTime".length()));
		    if (actionToLaunch != null){
			launchAction(actionToLaunch, target);
		    }
		}else{
		    doAction(id, target);
		}
	    }
	};
	add(wicketBaseBehavior);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	StringBuilder onDomReadyJS = new StringBuilder();
	onDomReadyJS.append("wicketBaseBehavior = function(id) { ");
	onDomReadyJS.append("wicketAjaxGet('");
	onDomReadyJS.append(wicketBaseBehavior.getCallbackUrl());
	onDomReadyJS.append("&id='+id); };");
	if (getClientTime){
	    onDomReadyJS.append("setClientTime();");
	}else if (actionToLaunch != null){
	    onDomReadyJS.append("wicketBaseBehavior('" + actionToLaunch + "');");
	    // onDomReadyJS.append("doWicketBehavior('wicketBaseBehavior(\"" +
	    // actionToLaunch + "\")');");
	}
	onDomReadyJS.append(getOnDomReadyJS());
	logger.debug("onDomReadyJS=" + onDomReadyJS);
	response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
    }

    public boolean isLoadingModalOpen() {
	return loadingModalPanel.isOpen();
    }

    public void changeLoadingModalMessage(final String message, AjaxRequestTarget target) {
	loadingModalPanel.setMessage(message);
	target.add(loadingModalPanel);
    }

    public void openLoadingModal(final String message, AjaxRequestTarget target) {
	loadingModalPanel.setMessage(message);
	loadingModalPanel.open(target);
    }

    public void closeLoadingModal(AjaxRequestTarget target) {
	loadingModalPanel.close(target);
    }

    public boolean isChangeLoadingModalMessage() {
	return changeLoadingModalMessage;
    }

    public void setChangeLoadingModalMessage(boolean changeLoadingModalMessage) {
	this.changeLoadingModalMessage = changeLoadingModalMessage;
    }

    public Date getClientTime() {
	return clientTime;
    }

    public int getClientTimeZoneOffsetMinutes() {
	return clientTimeZoneOffsetMinutes;
    }

    protected void doAction(String action, AjaxRequestTarget target) {
	logger.warn("Action not handled: " + action);
    }

    protected final void determineClientTime() {
	getClientTime = true;
    }

    protected final void launchAction(String action) {
	launchAction(action, null);
    }

    protected final void launchAction(String action, AjaxRequestTarget target) {
	if (target != null){
	    if (action != null){
		target.appendJavaScript("wicketBaseBehavior('" + action + "');");
		// target.appendJavaScript("doWicketBehavior('wicketBaseBehavior(\""
		// + action + "\")');");
	    }
	    return;
	}
	actionToLaunch = action;
    }

    protected String getOnDomReadyJS() {
	return "";
    }
}
