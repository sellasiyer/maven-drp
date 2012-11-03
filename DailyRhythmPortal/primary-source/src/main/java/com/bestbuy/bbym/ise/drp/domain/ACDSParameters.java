package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

public class ACDSParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private String groupName;
    private String sysCode;
    private String value;
    private String activationFlag;
    private String description;

    public String getGroupName() {
	return groupName;
    }

    public void setGroupName(String groupName) {
	this.groupName = groupName;
    }

    public String getSysCode() {
	return sysCode;
    }

    public void setSysCode(String sysCode) {
	this.sysCode = sysCode;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    public String getActivationFlag() {
	return activationFlag;
    }

    public void setActivationFlag(String activationFlag) {
	this.activationFlag = activationFlag;
    }

    public String getDescription() {
	return description;
    }

    public void setDescription(String description) {
	this.description = description;
    }

}
