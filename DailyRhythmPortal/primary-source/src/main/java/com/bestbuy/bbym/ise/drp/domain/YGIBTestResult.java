package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

public class YGIBTestResult implements Serializable {

    private static final long serialVersionUID = 1L;
    private String testId;
    private int pass;
    private int fail;
    private int other;
    private String desc;
    private Date timestamp;
    private int status;
    private String subjective;

    public String getTestId() {
	return testId;
    }

    public void setTestId(String testId) {
	this.testId = testId;
    }

    public int getPass() {
	return pass;
    }

    public void setPass(int pass) {
	this.pass = pass;
    }

    public int getFail() {
	return fail;
    }

    public void setFail(int fail) {
	this.fail = fail;
    }

    public int getOther() {
	return other;
    }

    public void setOther(int other) {
	this.other = other;
    }

    public String getDesc() {
	return desc;
    }

    public void setDesc(String desc) {
	this.desc = desc;
    }

    public Date getTimestamp() {
	return timestamp;
    }

    public void setTimestamp(Date timestamp) {
	this.timestamp = timestamp;
    }

    public int getStatus() {
	return status;
    }

    public void setStatus(int status) {
	this.status = status;
    }

    public String getSubjective() {
	return subjective;
    }

    public void setSubjective(String subjective) {
	this.subjective = subjective;
    }

    @Override
    public String toString() {
	return "YGIBTestResult [testId=" + testId + ", pass=" + pass + ", fail=" + fail + ", other=" + other
		+ ", desc=" + desc + ", timestamp=" + timestamp + ", status=" + status + ", subjective=" + subjective
		+ "]";
    }

}
