package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.bestbuy.bbym.ise.domain.Customer;

/**
 * @deprecated rather use actual objects like Appointment, etc. 
 */
@Deprecated
public class SchedulerBase implements Serializable {

    private static final long serialVersionUID = 1L;
    private DrpUser user;
    private Customer customer;
    private Map<Date, TimeSlot> appointmentTime;
    private String orderNo;
    private String appointmentId;
    private Map<String, String> department;
    private Map<String, String> appointmentType;
    private Map<String, String> reason;

    public DrpUser getUser() {
	return user;
    }

    public void setUser(DrpUser user) {
	this.user = user;
    }

    public Customer getCustomer() {
	return customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public Map<Date, TimeSlot> getAppointmentTime() {
	return appointmentTime;
    }

    public void setAppointmentTime(Map<Date, TimeSlot> appointmentTime) {
	this.appointmentTime = appointmentTime;
    }

    public String getOrderNo() {
	return orderNo;
    }

    public void setOrderNo(String orderNo) {
	this.orderNo = orderNo;
    }

    public String getAppointmentId() {
	return appointmentId;
    }

    public void setAppointmentId(String appointmentId) {
	this.appointmentId = appointmentId;
    }

    public Map<String, String> getDepartment() {
	return department;
    }

    public void setDepartment(Map<String, String> department) {
	this.department = department;
    }

    public Map<String, String> getAppointmentType() {
	return appointmentType;
    }

    public void setAppointmentType(Map<String, String> appointmentType) {
	this.appointmentType = appointmentType;
    }

    public Map<String, String> getReason() {
	return reason;
    }

    public void setReason(Map<String, String> reason) {
	this.reason = reason;
    }

    @Override
    public String toString() {
	return "SchedulerBase [user=" + user + ", customer=" + customer + ", appointmentTime=" + appointmentTime
		+ ", orderNo=" + orderNo + ", appointmentId=" + appointmentId + ", department=" + department
		+ ", appointmentType=" + appointmentType + ", reason=" + reason + "]";
    }

}
