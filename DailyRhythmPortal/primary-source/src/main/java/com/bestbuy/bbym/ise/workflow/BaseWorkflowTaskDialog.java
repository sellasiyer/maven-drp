/**
 * 
 */
package com.bestbuy.bbym.ise.workflow;

/**
 * @author a186288
 * 
 */
public abstract class BaseWorkflowTaskDialog implements WorkflowTaskDialog {

    private static final long serialVersionUID = -4337092731992932165L;

    private long id;
    private int stepSequence;
    private String imageUrl;

    @Override
    public long getId() {
	return id;
    }

    @Override
    public void setId(long id) {
	this.id = id;
    }

    @Override
    public int getStepSequence() {
	return stepSequence;
    }

    @Override
    public void setStepSequence(int stepSequence) {
	this.stepSequence = stepSequence;
    }

    public void setImageUrl(String imageUrl) {
	this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
	return imageUrl;
    }

}
