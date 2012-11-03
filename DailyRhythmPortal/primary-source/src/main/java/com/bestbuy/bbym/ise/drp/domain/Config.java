/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for general configuration item.
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 24
 */
public class Config extends BaseObject {

    private static final long serialVersionUID = -24233265331566107L;

    private Long configId;
    private String configType;
    private String paramName;
    private String paramValue;
    private String description;

    public Config() {
	super();
    }

    public Config(Date createdOn, Date modifiedOn, String createdBy, String modifiedBy, Long configId,
	    String configType, String paramName, String paramValue, String description) {
	super(createdOn, modifiedOn, createdBy, modifiedBy);
	this.configId = configId;
	this.configType = configType;
	this.paramName = paramName;
	this.paramValue = paramValue;
	this.description = description;
    }

    public Long getConfigId() {
	return configId;
    }

    public void setConfigId(Long configId) {
	this.configId = configId;
    }

    public String getConfigType() {
	return configType;
    }

    public void setConfigType(String configType) {
	this.configType = configType;
    }

    public String getParamName() {
	return paramName;
    }

    public void setParamName(String paramName) {
	this.paramName = paramName;
    }

    public String getParamValue() {
	return paramValue;
    }

    public void setParamValue(String paramValue) {
	this.paramValue = paramValue;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
