package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class AppointmentSlots implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Map<Date, List<TimeSlot>> availableSlots;
    private int appointmentLength;

    public int getAppointmentLength() {
	return appointmentLength;
    }

    public void setAppointmentLength(int appointmentLength) {
	this.appointmentLength = appointmentLength;
    }

    public Map<Date, List<TimeSlot>> getAvailableSlots() {
	return availableSlots;
    }

    public void setAvailableSlots(Map<Date, List<TimeSlot>> availableSlots) {
	this.availableSlots = availableSlots;
    }

    @Override
    public String toString() {
	return "AppointmentSlots [availableSlots=" + availableSlots + ", appointmentLength=" + appointmentLength
		+ ", toString()=" + super.toString() + "]";
    }

}
