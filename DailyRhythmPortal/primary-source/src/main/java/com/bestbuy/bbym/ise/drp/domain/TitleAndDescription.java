/**
 * 
 */
package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author JAM0314
 * 
 */
public class TitleAndDescription implements Serializable {

    private static final long serialVersionUID = -7914987085632961727L;

    public TitleAndDescription(String title, String description, String code, long instanceId) {
	this(title, description, code);
	this.instanceId = instanceId;
    }

    public TitleAndDescription(String title, String description, String code) {
	this.title = title;
	this.description = description;
	this.code = code;
    }

    private String title;
    private String description;
    private String code;
    private long instanceId;

    public String getTitle() {
	return title;
    }

    public String getDescription() {
	return description;
    }

    public String getCode() {
	return code;
    }

    public long getInstanceId() {
	return instanceId;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
