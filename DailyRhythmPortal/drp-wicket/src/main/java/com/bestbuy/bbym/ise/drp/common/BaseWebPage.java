package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBaseException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;

@AuthorizeInstantiation( {DrpConstants.DRP_ADMIN, DrpConstants.DRP_MANAGER, DrpConstants.DRP_LEAD,
	DrpConstants.DRP_USER, DrpConstants.DRP_TEAM })
public abstract class BaseWebPage extends WebPage {

    private String variant = null;
    private boolean variantRetrieved = false;
    private boolean tablet = false;

    public BaseWebPage() {
    }

    public BaseWebPage(PageParameters parameters) {
	super(parameters);
    }

    public DailyRhythmPortalSession getDailyRhythmPortalSession() {
	return (DailyRhythmPortalSession) getSession();
    }

    @Override
    protected void configureResponse(WebResponse webResponse) {
	super.configureResponse(webResponse);
	WebResponse response = (WebResponse) getRequestCycle().getResponse();
	response.setHeader("Cache-Control", "no-cache,max-age=0,must-revalidate,no-store");
	response.setHeader("Expires", "-1");
	response.setHeader("Pragma", "no-cache");
    }

    @Override
    public String getVariation() {
	setVariant();
	return variant;
    }

    public void setTablet(boolean tablet) {
	this.tablet = tablet;
    }

    public boolean isTablet() {
	setVariant();
	return tablet;
    }

    private void setVariant() {
	if (!variantRetrieved){
	    variantRetrieved = true;
	    variant = getDailyRhythmPortalSession().getVariant();
	    if (DrpConstants.TABLET.equals(variant)){
		setTablet(true);
	    }
	}
    }

    public void processException(String message, IseExceptionCodeEnum iseEnum) {
	ExceptionPageHandler.processException(message, iseEnum, getPageParameters(), getSession());
    }

    public void processException(String message, IseExceptionCodeEnum iseEnum, long workflowId, String workflowName) {
	ExceptionPageHandler.processException(message, iseEnum, workflowId, workflowName, getPageParameters(),
		getSession());
    }

    public void processException(String message, IseBaseException iseBaseException) {
	ExceptionPageHandler.processException(message, iseBaseException, getPageParameters(), getSession());
    }

    public void processException(String message, IseBaseException iseBaseException, long workflowId, String workflowName) {
	ExceptionPageHandler.processException(message, iseBaseException, workflowId, workflowName, getPageParameters(),
		getSession());
    }
}
