package com.bestbuy.bbym.ise.drp.common;

import java.util.Date;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalApplication;

public class SessionTimeoutPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SessionTimeoutPanel.class);

    public SessionTimeoutPanel(String id) {
	super(id);

	// KEEPALIVE Behavior for javascript calling back into wicket.
	final AbstractDefaultAjaxBehavior keepAliveBehavior = new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
		logger.trace("Keepalive: " + new Date());
		target.appendJavaScript("consoleLog(\"keepAlive\");");
		target.appendJavaScript("resetElapsedTime();");

	    }
	};
	add(keepAliveBehavior);

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		// Keep Alive Behavior setup:
		onDomReadyJS.append("keepAliveBehavior = function(id) { ");
		onDomReadyJS.append("wicketAjaxGet('");
		onDomReadyJS.append(keepAliveBehavior.getCallbackUrl());
		onDomReadyJS.append("&id='+id); };");

		TimeoutInfo timeoutInfo = TimeoutInfo.getInstance();
		onDomReadyJS.append("activityIntervalDuration=" + timeoutInfo.getPollingInterval() + ";");
		onDomReadyJS.append("warningAt_seconds=" + timeoutInfo.getWarningTime() + ";");
		onDomReadyJS.append("timeoutAt_seconds=" + timeoutInfo.getTimeout(getPage()) + ";");

		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
	    }
	});
	// end of timeout code.

	final AjaxLink<Object> refreshLink = new AjaxLink<Object>("refreshLink") {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("refreshLink onClick");
		target.appendJavaScript("resetElapsedTime(); hideWarning();");
	    }
	};
	refreshLink.setOutputMarkupPlaceholderTag(true);
	add(refreshLink);

	final AjaxLink<Object> logoutLink = new AjaxLink<Object>("logoutLink") {

	    private static final long serialVersionUID = 1L;
	    private Class<? extends Page> landingPageClass;

	    {
		landingPageClass = ((DailyRhythmPortalApplication) getApplication()).getHomePage();
	    }

	    @Override
	    public void onClick(AjaxRequestTarget target) {
		logger.debug("logoutLink onClick");

		// identical to what logout link does.
		getSession().invalidateNow();
		setResponsePage(landingPageClass);

		target.appendJavaScript(" hideWarning();");
	    }
	};
	logoutLink.setOutputMarkupPlaceholderTag(true);
	add(logoutLink);

    }
}
