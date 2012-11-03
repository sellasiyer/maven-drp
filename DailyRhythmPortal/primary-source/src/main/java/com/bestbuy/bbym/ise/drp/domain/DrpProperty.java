package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author a904002
 */
public class DrpProperty implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private String value;
    private String createdBy;
    private Date createdDate;
    private String modifiedBy;
    private Date modifiedDate;
    private String description;
    private boolean wicketProperty;

    public DrpProperty() {
    }

    public DrpProperty(String name, String value) {
	this.name = name;
	this.value = value;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getCreatedBy() {
	return createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
	return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public String getModifiedBy() {
	return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
	return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (name != null?name.hashCode():0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof DrpProperty)){
	    return false;
	}
	DrpProperty other = (DrpProperty) object;
	if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))){
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void copy(DrpProperty drpProperty) {
	if (drpProperty != null){
	    setId(drpProperty.getId());
	    setName(drpProperty.getName());
	    setValue(drpProperty.getValue());
	    setDescription(drpProperty.getDescription());
	    setCreatedBy(drpProperty.getCreatedBy());
	    setCreatedDate(drpProperty.getCreatedDate());
	    setModifiedBy(drpProperty.getModifiedBy());
	    setModifiedDate(drpProperty.getModifiedDate());
	    setWicketProperty(drpProperty.isWicketProperty());
	}
    }

    public void clear() {
	setId(null);
	setName(null);
	setValue(null);
	setDescription(null);
	setCreatedBy(null);
	setCreatedDate(null);
	setModifiedBy(null);
	setModifiedDate(null);
	setWicketProperty(false);
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return description;
    }

    /**
     * Determines whether this property is used to override a value in a Wicket
     * properties file.
     */
    public boolean isWicketProperty() {
	return wicketProperty;
    }

    /**
     * Sets whether this property is used to override a value in a Wicket
     * properties file.
     */
    public void setWicketProperty(boolean wicketProperty) {
	this.wicketProperty = wicketProperty;
    }

}
