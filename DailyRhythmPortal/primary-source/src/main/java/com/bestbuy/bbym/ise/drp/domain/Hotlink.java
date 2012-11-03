package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Hotlink entity.
 *
 * @author MyEclipse Persistence Tools
 */
public class Hotlink implements java.io.Serializable {

    // Fields
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long id;
    private String urlAlias;
    private String url;

    private String modifiedBy;
    private Date modifiedDate;
    private String createdBy;
    private Date createdDate;
    private String userId;
    private int displayOrder;

    private final String BASE = "http://";

    public int getDisplayOrder() {
	return displayOrder;
    }

    public void setDisplayOrder(int displayOrder) {
	this.displayOrder = displayOrder;
    }

    // Constructors
    /**
     * default constructor
     */
    public Hotlink() {
	this.url = BASE;
    }

    /**
     * minimal constructor
     */
    public Hotlink(String urlAlias, String url, String userId) {
	this.urlAlias = urlAlias;
	this.url = url;
	this.userId = userId;
    }

    // Property accessors
    public Long getId() {
	return this.id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUrlAlias() {
	return this.urlAlias;
    }

    public void setUrlAlias(String urlAlias) {
	this.urlAlias = urlAlias;
    }

    public String getUrl() {
	return this.url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getModifiedBy() {
	return this.modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
	this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDate() {
	return this.modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
	this.modifiedDate = modifiedDate;
    }

    public String getCreatedBy() {
	return this.createdBy;
    }

    public void setCreatedBy(String createdBy) {
	this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
	return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
	this.createdDate = createdDate;
    }

    public String getUserId() {
	return this.userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

    public void copy(Hotlink hotlink) {
	if (null != hotlink){
	    setCreatedBy(hotlink.getCreatedBy());
	    setCreatedDate(hotlink.getCreatedDate());
	    setId(hotlink.getId());
	    setModifiedBy(hotlink.getModifiedBy());
	    setModifiedDate(hotlink.getModifiedDate());
	    setUrl(hotlink.getUrl());
	    setUrlAlias(hotlink.getUrlAlias());
	    setUserId(hotlink.getUserId());
	    setDisplayOrder(hotlink.getDisplayOrder());
	}
    }

    public void clear() {
	setCreatedBy(null);
	setCreatedDate(null);

	setId(null);
	setModifiedBy(null);
	setModifiedDate(null);
	setUrl(BASE);
	setUrlAlias(null);
	setUserId(null);
	setDisplayOrder(0);

    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
