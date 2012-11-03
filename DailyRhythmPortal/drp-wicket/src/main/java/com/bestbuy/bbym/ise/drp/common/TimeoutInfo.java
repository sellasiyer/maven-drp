package com.bestbuy.bbym.ise.drp.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.wicket.Page;
import org.apache.wicket.protocol.http.servlet.ServletWebRequest;
import org.apache.wicket.request.Request;

public class TimeoutInfo {

    private static TimeoutInfo instance = null;

    private Integer timeout = null;
    private Integer show_warning_at = 30;

    private TimeoutInfo() {
    }

    public static TimeoutInfo getInstance() {
	if (instance == null)
	    instance = new TimeoutInfo();
	return instance;
    }

    /**
     * Gets the session timeout value in seconds.
     * 
     * @param page
     *            needed to get the request to get the timeout value. That's
     *            all.
     * @return the time (in seconds) before a session times out. Default: 14
     *         minutes.
     */
    public int getTimeout(Page page) {
	Request request = page.getRequest();
	if (timeout == null){

	    ServletWebRequest servletWebRequest = (ServletWebRequest) request;
	    HttpServletRequest httpRequest = servletWebRequest.getContainerRequest();
	    HttpSession session = httpRequest.getSession();

	    if (session != null){
		timeout = session.getMaxInactiveInterval();
	    }else{
		timeout = 14 * 60; // 14 minutes
	    }
	}
	return timeout;
    }

    /**
     * Gets the number of seconds before the session timeout warning dialog is
     * displayed.
     */
    public int getWarningTime() {

	if (show_warning_at == null){
	    show_warning_at = 600; // 10 minutes.
	}
	return show_warning_at;

    }

    public int getPollingInterval() {
	return 29 * 1000;
	// intervals are in ms.
    }

    public void setWarningTime(int warningTimeInSeconds) {
	this.show_warning_at = warningTimeInSeconds;
    }

}
