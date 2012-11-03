package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

public class CheckOutCheckInExtra extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String name;
    private boolean checkOutStatus = false;
    private boolean checkInStatus = false;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public boolean isCheckOutStatus() {
	return checkOutStatus;
    }

    public void setCheckOutStatus(boolean checkOutStatus) {
	this.checkOutStatus = checkOutStatus;
    }

    public boolean isCheckInStatus() {
	return checkInStatus;
    }

    public void setCheckInStatus(boolean checkInStatus) {
	this.checkInStatus = checkInStatus;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
