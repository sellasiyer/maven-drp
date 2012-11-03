package com.bestbuy.bbym.ise.drp.error;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.bestbuy.bbym.ise.drp.common.PageParameterKeys;

/**
 * @author a186288
 */
public class DRPWorkflowErrorPage extends DRPErrorPage {

    private static final long serialVersionUID = -6320649441191278109L;

    public DRPWorkflowErrorPage(final PageParameters parameters) {
	super(parameters);

	String workflowType = parameters.get(PageParameterKeys.WORKFLOW_NAME.name()).toString(
		getString("notApplicable.label"));
	Long workflowId = parameters.get(PageParameterKeys.WORKFLOW_ID.name()).toOptionalLong();

	add(new Label("workflowTypeLabel", new ResourceModel("workflowType.label")));
	add(new Label("workflowType", workflowType));

	add(new Label("workflowIdLabel", new ResourceModel("workflowId.label")));
	add(new Label("workflowId", workflowId != null?workflowId.toString():getString("notApplicable.label")));
    }
}
