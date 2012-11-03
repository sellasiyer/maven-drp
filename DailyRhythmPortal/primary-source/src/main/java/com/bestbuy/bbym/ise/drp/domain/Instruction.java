/**
 * 
 */
package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author a186288
 * 
 */
public class Instruction extends TitleAndDescription {

    private static final long serialVersionUID = 477900344041380621L;

    private String ImageUrl;

    public Instruction(String title, String description, String imageUrl, String instructionCode,
	    long instructionInstanceId) {
	super(title, description, instructionCode, instructionInstanceId);
	this.setImageUrl(imageUrl);
    }

    public void setImageUrl(String imageUrl) {
	ImageUrl = imageUrl;
    }

    public String getImageUrl() {
	return ImageUrl;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
