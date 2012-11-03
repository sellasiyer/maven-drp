package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;
    private String appointmentId;
    private String storeId;
    private String orderNo;
    private Map<String, String> reasonType;
    private Date appointmentDateTime;
    private String reason;
    private String department;
    private String firstName;
    private String lastName;
    private String phoneNo;
    private String email;
    /**
     * Current Status ID
     */
    private Integer currentStatus;
    /**
     * Key may be one of (Completed,Canceled,NoShow)
     * Value will be list of status ids
     */
    private Map<AppointmentCloseStatus, List<String>> allowedStatus;

    public String getAppointmentId() {
	return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
	this.appointmentId = appointmentId;
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public String getOrderNo() {
	return orderNo;
    }

    public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
    }

    public Map<String, String> getReasonType() {
	return reasonType;
    }

    public void setReasonType(Map<String, String> reasonType) {
	this.reasonType = reasonType;
    }

    public Date getAppointmentDateTime() {
	return appointmentDateTime;
    }

    public void setAppointmentDateTime(Date appointmentDateTime) {
	this.appointmentDateTime = appointmentDateTime;
    }

    public String getReason() {
	return reason;
    }

    public void setReason(String reason) {
	this.reason = reason;
    }

    public String getDepartment() {
	return department;
    }

    public void setDepartment(String department) {
	this.department = department;
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

    public String getPhoneNo() {
	return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
	this.phoneNo = phoneNo;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Integer getCurrentStatus() {
	return currentStatus;
    }

    public void setCurrentStatus(Integer currentStatus) {
	this.currentStatus = currentStatus;
    }

    @Override
    public String toString() {
	return "Appointment [appointmentId=" + appointmentId + ", storeId=" + storeId + ", orderNo=" + orderNo
		+ ", reasonType=" + reasonType + ", appointmentDateTime=" + appointmentDateTime + ", reason=" + reason
		+ ", department=" + department + ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNo="
		+ phoneNo + ", email=" + email + ", currentStatus=" + currentStatus + ", allowedStatus="
		+ allowedStatus + ", toString()=" + super.toString() + "]";
    }

    public Map<AppointmentCloseStatus, List<String>> getAllowedStatus() {
	return allowedStatus;
    }

    public void setAllowedStatus(Map<AppointmentCloseStatus, List<String>> allowedStatus) {
	this.allowedStatus = allowedStatus;
    }

}
