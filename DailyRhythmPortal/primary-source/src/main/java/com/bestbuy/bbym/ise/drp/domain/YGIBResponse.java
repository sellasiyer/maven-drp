package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.List;

public class YGIBResponse implements Serializable {

    private static final long serialVersionUID = 1L;
    private String responseDesc;
    private int responseCode;
    private String name;
    private String manufacturer;
    private String model;
    private String ram;
    private String os;
    private String firmware;
    private String displayDensity;
    private boolean rooted;
    private String sku;
    private String planId;

    private List<YGIBTestResult> testResults;

    public String getResponseDesc() {
	return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
	this.responseDesc = responseDesc;
    }

    public List<YGIBTestResult> getTestResults() {
	return testResults;
    }

    public void setTestResults(List<YGIBTestResult> testResults) {
	this.testResults = testResults;
    }

    public int getResponseCode() {
	return responseCode;
    }

    public void setResponseCode(int responseCode) {
	this.responseCode = responseCode;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public String getRam() {
	return ram;
    }

    public void setRam(String ram) {
	this.ram = ram;
    }

    public String getOs() {
	return os;
    }

    public void setOs(String os) {
	this.os = os;
    }

    public String getFirmware() {
	return firmware;
    }

    public void setFirmware(String firmware) {
	this.firmware = firmware;
    }

    public String getDisplayDensity() {
	return displayDensity;
    }

    public void setDisplayDensity(String displayDensity) {
	this.displayDensity = displayDensity;
    }

    public boolean isRooted() {
	return rooted;
    }

    public void setRooted(boolean rooted) {
	this.rooted = rooted;
    }

    public String getSku() {
	return sku;
    }

    public void setSku(String sku) {
	this.sku = sku;
    }

    public String getPlanId() {
	return planId;
    }

    public void setPlanId(String planId) {
	this.planId = planId;
    }

    @Override
    public String toString() {
	return "YGIBResponse [responseDesc=" + responseDesc + ", responseCode=" + responseCode + ", name=" + name
		+ ", manufacturer=" + manufacturer + ", model=" + model + ", ram=" + ram + ", os=" + os + ", firmware="
		+ firmware + ", displayDensity=" + displayDensity + ", rooted=" + rooted + ", sku=" + sku + ", planId="
		+ planId + ", testResults=" + testResults + "]";
    }
}
