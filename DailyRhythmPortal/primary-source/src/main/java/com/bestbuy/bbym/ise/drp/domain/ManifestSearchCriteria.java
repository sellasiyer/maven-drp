package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class ManifestSearchCriteria implements Serializable {

    public enum SearchType {
	RECENT, DATE_RANGE, SERIAL_NUM, ITEM_TAG_ID, UPS_TRACKING_NUM, STATUS
    }

    private static final long serialVersionUID = 1L;

    private SearchType searchType = SearchType.RECENT;
    private BigInteger storeID;
    private BigInteger manifestID;
    private String manifestStatus;
    private Integer mostRecentNumber;
    private Date startDate;
    private Date endDate;
    private String imeiesn;
    private String itemTag;
    private String trackingIdentifier;

    public BigInteger getManifestID() {
	return manifestID;
    }

    public void setManifestID(BigInteger manifestID) {
	this.manifestID = manifestID;
    }

    public SearchType getSearchType() {
	return searchType;
    }

    public void setSearchType(SearchType searchType) {
	this.searchType = searchType;
    }

    public BigInteger getStoreID() {
	return storeID;
    }

    public void setStoreID(BigInteger storeID) {
	this.storeID = storeID;
    }

    public String getManifestStatus() {
	return manifestStatus;
    }

    public void setManifestStatus(String manifestStatus) {
	this.manifestStatus = manifestStatus;
    }

    public Integer getMostRecentNumber() {
	return mostRecentNumber;
    }

    public void setMostRecentNumber(Integer mostRecentNumber) {
	this.mostRecentNumber = mostRecentNumber;
    }

    public Date getStartDate() {
	return startDate;
    }

    public void setStartDate(Date startDate) {
	this.startDate = startDate;
    }

    public Date getEndDate() {
	return endDate;
    }

    public void setEndDate(Date endDate) {
	this.endDate = endDate;
    }

    public String getImeiesn() {
	return imeiesn;
    }

    public void setImeiesn(String imeiesn) {
	this.imeiesn = imeiesn;
    }

    public String getItemTag() {
	return itemTag;
    }

    public void setItemTag(String itemTag) {
	this.itemTag = itemTag;
    }

    public String getTrackingIdentifier() {
	return trackingIdentifier;
    }

    public void setTrackingIdentifier(String trackingIdentifier) {
	this.trackingIdentifier = trackingIdentifier;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
