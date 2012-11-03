package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @deprecated rather use actual objects like Appointment, etc. 
 */
@Deprecated
public class SchedulerRequest extends SchedulerBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean sendEmail;
    private Date startDate;
    private Date endDate;
    private String appointmentStatusId;

    public boolean isSendEmail() {
	return sendEmail;
    }

    public void setSendEmail(boolean sendEmail) {
	this.sendEmail = sendEmail;
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

    public String getAppointmentStatusId() {
	return appointmentStatusId;
    }

    public void setAppointmentStatusId(String appointmentStatusId) {
	this.appointmentStatusId = appointmentStatusId;
    }

    @Override
    public String toString() {
	return "SchedulerRequest [sendEmail=" + sendEmail + ", startDate=" + startDate + ", endDate=" + endDate
		+ ", appointmentStatusId=" + appointmentStatusId + "]";
    }

}
