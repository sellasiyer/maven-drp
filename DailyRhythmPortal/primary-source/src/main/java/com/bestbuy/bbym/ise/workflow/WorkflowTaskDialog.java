package com.bestbuy.bbym.ise.workflow;

import java.io.Serializable;

/**
 * The data that is transmitted to and from the service client. It is a subset
 * of the WorkflowData that is specific to the client interaction and the step
 * that is currently being executed.
 */
public interface WorkflowTaskDialog extends Serializable {

    public long getId();

    public void setId(long id);

    public int getStepSequence();

    public void setStepSequence(int stepSequence);
}
