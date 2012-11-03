package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

public class TimeSlot implements Serializable {

    private static final long serialVersionUID = 1L;

    private String time;
    private int availability;

    public String getTime() {
	return time;
    }

    public void setTime(String time) {
	this.time = time;
    }

    public int getAvailability() {
	return availability;
    }

    public void setAvailability(int availability) {
	this.availability = availability;
    }

    @Override
    public String toString() {
	return "TimeSlot [time=" + time + ", availability=" + availability + "]";
    }

}
