package com.bestbuy.bbym.ise.drp.domain;

public enum AppointmentCloseStatus {

    /**
     * COMPLETE.
     */
    COMPLETE("Completed"),
    /**
     * CANCEL.
     */
    CANCEL("Cancelled"),
    /**
     * NO SHOW.
     */
    NO_SHOW("NoShow");

    private final String serviceValue;

    private AppointmentCloseStatus(String serviceValue) {
	this.serviceValue = serviceValue;
    }

    public String getServiceValue() {
	return serviceValue;
    }

    public static AppointmentCloseStatus fromServiceValue(String serviceValue) {
	for(AppointmentCloseStatus status: values()){
	    if (status.serviceValue.equals(serviceValue)){
		return status;
	    }
	}
	return null;
    }

}
