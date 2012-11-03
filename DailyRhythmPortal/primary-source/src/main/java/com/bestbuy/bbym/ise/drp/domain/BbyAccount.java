package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

import com.bestbuy.bbym.ise.domain.BaseObject;
import java.util.Date;

/**
 *
 * @author a175157
 */

public class BbyAccount extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String dataSharingKey;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String email;
    private String eccaId;
    private Address address;
    private String rewardZoneNo;

    public BbyAccount() {
    }

    public BbyAccount(String dataSharingKey) {
	super();
	this.dataSharingKey = dataSharingKey;
    }

    public BbyAccount(String dataSharingKey, String firstName, String lastName, String phoneNumber, String email,
	    String eccaId, Address address) {
	super();
	this.dataSharingKey = dataSharingKey;
	this.firstName = firstName;
	this.lastName = lastName;
	this.phoneNumber = phoneNumber;
	this.email = email;
	this.eccaId = eccaId;
	this.address = address;
    }

    public BbyAccount(String dataSharingKey, String firstName, String lastName, String phoneNumber, String email,
	    String eccaId, Address address, String createdBy, String modifiedBy) {
	super(createdBy, modifiedBy);
	this.dataSharingKey = dataSharingKey;
	this.firstName = firstName;
	this.lastName = lastName;
	this.phoneNumber = phoneNumber;
	this.email = email;
	this.eccaId = eccaId;
	this.address = address;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
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

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getEccaId() {
	return eccaId;
    }

    public void setEccaId(String eccaId) {
	this.eccaId = eccaId;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    @Override
    public String getCreatedBy() {
	return super.getCreatedBy();
    }

    @Override
    public Date getCreatedOn() {
	return super.getCreatedOn();
    }

    @Override
    public String getModifiedBy() {
	return super.getModifiedBy();
    }

    @Override
    public Date getModifiedOn() {
	return super.getModifiedOn();
    }

    @Override
    public String toString() {
	return "BbyAccount [dataSharingKey=" + dataSharingKey + ", firstName=" + firstName + ", lastName=" + lastName
		+ ", phoneNumber=" + phoneNumber + ", email=" + email + ", eccaId=" + eccaId + ", address=" + address
		+ ", rewardZoneNo=" + rewardZoneNo + "]";
    }

    public String getRewardZoneNo() {
	return rewardZoneNo;
    }

    public void setRewardZoneNo(String rewardZoneNo) {
	this.rewardZoneNo = rewardZoneNo;
    }
}
