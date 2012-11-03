package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Line implements Serializable {

    private static final long serialVersionUID = 6873401750805222127L;

    public static final String OPT_IN_TEXT = "TEXT";
    public static final String OPT_IN_VOICE = "VOICE";
    public static final String OPT_IN_PLUS = "PLUS";
    public static final String OPT_IN_NONE = "NONE";

    public static final String LINE_STATUS_ACTIVE = "ACTIVE";
    public static final String LINE_STATUS_TENTATIVE = "TENTATIVE";
    public static final String LINE_STATUS_CANCELLED = "CANCELLED";
    public static final String LINE_STATUS_SUSPENDED = "SUSPENDED";
    public static final String LINE_STATUS_CLOSED = "CLOSED";
    public static final String LINE_STATUS_UNKNOWN = "UNKNOWN";

    private Long id;
    private String optin;
    private boolean standardEligible;
    private boolean earlyEligible;
    private String lineStatus;
    private String mobileNumber;
    private Date earlyEligibilityDate;
    private Date stdEligibilityDate;
    private String eligibilityType;
    private Boolean optinVoice = Boolean.FALSE;
    private Boolean optinText = Boolean.FALSE;
    private Boolean optinAllowed = Boolean.FALSE;
    private Device device;
    private Date contractEndDate;
    private List<CarrierPlan> carrierPlans;
    private List<CarrierOption> carrierOptions;
    private boolean esignVerified;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public void setOptin(String optin) {
	this.optin = optin;

	if (OPT_IN_VOICE.equalsIgnoreCase(optin)){
	    this.setOptinVoice(Boolean.TRUE);
	    this.setOptinText(Boolean.FALSE);
	}else if (OPT_IN_TEXT.equalsIgnoreCase(optin)){
	    this.setOptinText(Boolean.TRUE);
	    this.setOptinVoice(Boolean.FALSE);
	}else{
	    this.setOptinVoice(Boolean.FALSE);
	    this.setOptinText(Boolean.FALSE);
	}
    }

    public String getOptin() {
	return this.optin;
    }

    public boolean isStandardEligible() {
	return standardEligible;
    }

    public void setStandardEligible(boolean standardEligible) {
	this.standardEligible = standardEligible;
    }

    public boolean isEarlyEligible() {
	return earlyEligible;
    }

    public void setEarlyEligible(boolean earlyEligible) {
	this.earlyEligible = earlyEligible;
    }

    public void setLineStatus(String lineStatus) {
	this.lineStatus = lineStatus;
    }

    public String getLineStatus() {
	return this.lineStatus;
    }

    public void setMobileNumber(String mobileNumber) {
	this.mobileNumber = mobileNumber;
    }

    public String getMobileNumber() {
	return this.mobileNumber;
    }

    public void setEarlyEligibilityDate(Date earlyEligibilityDate) {
	this.earlyEligibilityDate = earlyEligibilityDate;
    }

    public Date getEarlyEligibilityDate() {
	return this.earlyEligibilityDate;
    }

    public void setStdEligibilityDate(Date stdEligibilityDate) {
	this.stdEligibilityDate = stdEligibilityDate;
    }

    public Date getStdEligibilityDate() {
	return this.stdEligibilityDate;
    }

    public void setEligibilityType(String eligibilityType) {
	this.eligibilityType = eligibilityType;
    }

    public String getEligibilityType() {
	return this.eligibilityType;
    }

    public void setOptinVoice(Boolean optinVoice) {
	this.optinVoice = optinVoice;
    }

    public Boolean getOptinVoice() {
	return this.optinVoice;
    }

    public void setOptinText(Boolean optinText) {
	this.optinText = optinText;
    }

    public Boolean getOptinText() {
	return this.optinText;
    }

    public void setOptinAllowed(Boolean optinAllowed) {
	this.optinAllowed = optinAllowed;
    }

    public Boolean getOptinAllowed() {
	return this.optinAllowed;
    }

    public void setDevice(Device device) {
	this.device = device;
    }

    public Device getDevice() {
	return device;
    }

    public void setContractEndDate(Date contractEndDate) {
	this.contractEndDate = contractEndDate;
    }

    public Date getContractEndDate() {
	return this.contractEndDate;
    }

    public List<CarrierPlan> getCarrierPlans() {
	return carrierPlans;
    }

    public void setCarrierPlans(List<CarrierPlan> carrierPlans) {
	this.carrierPlans = carrierPlans;
    }

    public List<CarrierOption> getCarrierOptions() {
	return carrierOptions;
    }

    public void setCarrierOptions(List<CarrierOption> carrierOptions) {
	this.carrierOptions = carrierOptions;
    }

    public boolean isEsignVerified() {
	return esignVerified;
    }

    public void setEsignVerified(boolean esignVerified) {
	this.esignVerified = esignVerified;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
