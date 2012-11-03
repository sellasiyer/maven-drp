package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

@XmlTransient
public class BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private Date createdOn;
    private Date modifiedOn;
    private String createdBy;
    private String modifiedBy;

    public BaseObject() {
	super();
    }

    public BaseObject(String createdBy, String modifiedBy) {
	super();
	this.createdBy = createdBy;
	this.modifiedBy = modifiedBy;
    }

    public BaseObject(Date createdOn, Date modifiedOn, String createdBy, String modifiedBy) {
	super();
	this.createdOn = createdOn;
	this.modifiedOn = modifiedOn;
	this.createdBy = createdBy;
	this.modifiedBy = modifiedBy;
    }

    public Date getCreatedOn() {
	return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
	this.createdOn = createdOn;
    }

    public Date getModifiedOn() {
	return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
	this.modifiedOn = modifiedOn;
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public String getModifiedBy() {
	return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
