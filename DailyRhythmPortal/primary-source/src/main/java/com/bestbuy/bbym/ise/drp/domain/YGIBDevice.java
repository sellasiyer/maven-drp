package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

public class YGIBDevice implements Serializable {

    private static final long serialVersionUID = 1L;
    private String uid;
    private String phoneNo;

    public String getUid() {
	return uid;
    }

    public void setUid(String uid) {
	this.uid = uid;
    }

    public String getPhoneNo() {
	return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
	this.phoneNo = phoneNo;
    }
}
