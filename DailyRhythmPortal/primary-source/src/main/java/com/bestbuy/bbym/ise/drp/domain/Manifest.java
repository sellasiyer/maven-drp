package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Manifest implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigInteger manifestID;
    private String status;
    private BigInteger deviceCount;
    private String trackingIdentifier;
    private String dayCreated;
    private Date dateTimeCreated;
    private Date dateCompleted;
    private String createdByUser;
    private List<ManifestLine> manifestLine;
    private String details;
    byte[] labelImage;

    public Date getDateCompleted() {
	return dateCompleted;
    }

    public void setDateCompleted(Date dateCompleted) {
	this.dateCompleted = dateCompleted;
    }

    public byte[] getLabelImage() {
	return labelImage;
    }

    public void setLabelImage(byte[] labelImage) {
	this.labelImage = labelImage;
    }

    public String getDetails() {
	return details;
    }

    public void setDetails(String details) {
	this.details = details;
    }

    public BigInteger getManifestID() {
	return manifestID;
    }

    public void setManifestLine(List<ManifestLine> manifestLine) {
	this.manifestLine = manifestLine;
    }

    public void setManifestID(BigInteger manifestID) {
	this.manifestID = manifestID;
    }

    public String getStatus() {
	return status;
    }

    public void setStatus(String status) {
	this.status = status;
    }

    public BigInteger getDeviceCount() {
	return deviceCount;
    }

    public void setDeviceCount(BigInteger deviceCount) {
	this.deviceCount = deviceCount;
    }

    public String getTrackingIdentifier() {
	return trackingIdentifier;
    }

    public void setTrackingIdentifier(String trackingIdentifier) {
	this.trackingIdentifier = trackingIdentifier;
    }

    public String getDayCreated() {
	return dayCreated;
    }

    public void setDayCreated(String dayCreated) {
	this.dayCreated = dayCreated;
    }

    public Date getDateTimeCreated() {
	return dateTimeCreated;
    }

    public void setDateTimeCreated(Date dateTimeCreated) {
	this.dateTimeCreated = dateTimeCreated;
    }

    public String getCreatedByUser() {
	return createdByUser;
    }

    public void setCreatedByUser(String createdByUser) {
	this.createdByUser = createdByUser;
    }

    public List<ManifestLine> getManifestLine() {
	return manifestLine;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
