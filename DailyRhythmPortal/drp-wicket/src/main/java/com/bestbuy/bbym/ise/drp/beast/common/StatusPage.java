package com.bestbuy.bbym.ise.drp.beast.common;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.common.BaseWebPage;
import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;

@AuthorizeInstantiation(DrpConstants.DRP_BEAST)
public class StatusPage extends BaseWebPage {

    private static Logger logger = LoggerFactory.getLogger(StatusPage.class);

    private String exitStatus;

    public StatusPage(final PageParameters parameters) {
	super(parameters);

	if (parameters != null){
	    exitStatus = parameters.get(PageParameterKeys.EXIT_STATUS.getUrlParameterKey()).toString();
	}

	add(new AbstractDefaultAjaxBehavior() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void respond(AjaxRequestTarget target) {
	    }

	    @Override
	    public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		StringBuilder onDomReadyJS = new StringBuilder();
		if (exitStatus != null){
		    onDomReadyJS.append("window.status='");
		    onDomReadyJS.append(exitStatus);
		    onDomReadyJS.append("';");
		}
		logger.debug("onDomReadyJS=" + onDomReadyJS);
		response.renderOnDomReadyJavaScript(onDomReadyJS.toString());
		//Kill the session
		if (exitStatus != null){
		    logger.debug("GSP Cancel Flow - Killing Session");
		    getDailyRhythmPortalSession().signOut();
		}
	    }
	});
    }

}
