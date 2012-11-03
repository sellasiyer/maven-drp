/**
 * 
 */
package com.bestbuy.bbym.ise.workflow;

import java.util.HashMap;
import java.util.Map;

/**
 * @author a186288
 * 
 */
@SuppressWarnings("rawtypes")
public abstract class WorkflowBuilder<T extends Enum> {

    private Map<String, Object> workflowAttributes = new HashMap<String, Object>();
    private T workflowType;

    public WorkflowBuilder(T workflowType) {
	this.workflowType = workflowType;
    }

    /**
     * @return the workflowAttributes
     */
    public Object getWorkflowAttribute(String key) {
	return workflowAttributes.get(key);
    }

    /**
     * @param workflowAttributes
     *            the workflowAttributes to set
     */
    public void setWorkflowAttribute(String key, Object attribute) {
	workflowAttributes.put(key, attribute);
    }

    public String getWorkflowType() {
	if (workflowType == null){
	    return null;
	}
	return workflowType.name();
    }
}
