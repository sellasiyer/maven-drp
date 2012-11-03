package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.RestartResponseException;
import org.apache.wicket.Session;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.error.DRPErrorPage;
import com.bestbuy.bbym.ise.drp.error.DRPWorkflowErrorPage;
import com.bestbuy.bbym.ise.exception.IseBaseException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;

/**
 * @author a186288
 * 
 */
public class ExceptionPageHandler {

    public static void processException(String message, IseBaseException e, final PageParameters parameters,
	    Session session) {
	processException(message, e.getErrorCode(), parameters, session);
    }

    public static void processException(String message, IseBaseException e, Long workflowId, String workflowName,
	    final PageParameters parameters, Session session) {
	processException(message, e.getErrorCode(), workflowId, workflowName, parameters, session);
    }

    public static void processException(String message, IseExceptionCodeEnum iseEnum, PageParameters parameters,
	    Session session) {
	session.error(message);
	if (parameters == null){
	    parameters = new PageParameters();
	}
	parameters.add(PageParameterKeys.EXCEPTION_CODE.name(), iseEnum.name());
	throw new RestartResponseException(DRPErrorPage.class, parameters);
    }

    public static void processException(String message, IseExceptionCodeEnum iseEnum, Long workflowId,
	    String workflowName, PageParameters parameters, Session session) {
	session.error(message);
	if (parameters == null){
	    parameters = new PageParameters();
	}
	parameters.add(PageParameterKeys.WORKFLOW_NAME.name(), "Triage");
	parameters.add(PageParameterKeys.WORKFLOW_ID.name(), workflowId);
	parameters.add(PageParameterKeys.EXCEPTION_CODE.name(), iseEnum.name());
	throw new RestartResponseException(DRPWorkflowErrorPage.class, parameters);
    }
}
