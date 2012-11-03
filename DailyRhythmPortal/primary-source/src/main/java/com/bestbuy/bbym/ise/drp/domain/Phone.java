package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.Device;

public class Phone extends Device {

    private static final long serialVersionUID = 1L;
    private Long id;
    private OperatingSystem operatingSystem;
    private String storeId;
    private Carrier carrier;
    private String make;
    private LoanerPhoneCondition condition;
    private String conditionComment;
    private String lastActionUserId;
    private LoanerPhoneDeletedReason deletedReason;
    private boolean active;
    private CheckOutCheckInHistory latestCheckOutCheckInHistory;
    private String firstName; // last action user first name
    private String lastName; // last action user last name

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public OperatingSystem getOperatingSystem() {
	return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
	this.operatingSystem = operatingSystem;
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }

    public String getMake() {
	return make;
    }

    public void setMake(String make) {
	this.make = make;
    }

    public LoanerPhoneCondition getCondition() {
	return condition;
    }

    public void setCondition(LoanerPhoneCondition condition) {
	this.condition = condition;
    }

    public String getConditionComment() {
	return conditionComment;
    }

    public void setConditionComment(String conditionComment) {
	this.conditionComment = conditionComment;
    }

    public String getLastActionUserId() {
	return lastActionUserId;
    }

    public void setLastActionUserId(String lastActionUserId) {
	this.lastActionUserId = lastActionUserId;
    }

    public LoanerPhoneDeletedReason getDeletedReason() {
	return deletedReason;
    }

    public void setDeletedReason(LoanerPhoneDeletedReason deletedReason) {
	this.deletedReason = deletedReason;
    }

    public boolean isActive() {
	return active;
    }

    public void setActive(boolean active) {
	this.active = active;
    }

    public CheckOutCheckInHistory getLatestCheckOutCheckInHistory() {
	return latestCheckOutCheckInHistory;
    }

    public void setLatestCheckOutCheckInHistory(CheckOutCheckInHistory latestCheckOutCheckInHistory) {
	this.latestCheckOutCheckInHistory = latestCheckOutCheckInHistory;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
