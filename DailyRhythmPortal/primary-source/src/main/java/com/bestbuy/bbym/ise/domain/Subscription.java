package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author a904002 reviewed and updated by a218635
 */
public class Subscription implements Serializable {

    private static final long serialVersionUID = 5190861377541197567L;

    private String carrierPin;
    private Carrier carrier;
    private Integer numberOfLinesAvailable;
    private List<Line> lines;
    private String accountNumber;
    private String primAcctFirstName;
    private String primAcctLastName;
    private String primAcctEmailId;

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }

    public void setCarrierPin(String carrierPin) {
	this.carrierPin = carrierPin;
    }

    public String getCarrierPin() {
	return this.carrierPin;
    }

    public void setNumberOfLinesAvailable(Integer numberOfLinesAvailable) {
	this.numberOfLinesAvailable = numberOfLinesAvailable;
    }

    public Integer getNumberOfLinesAvailable() {
	return this.numberOfLinesAvailable;
    }

    public void setLines(List<Line> lines) {
	this.lines = lines;
    }

    public List<Line> getLines() {
	if (lines == null){
	    lines = new ArrayList<Line>();
	}
	return lines;
    }

    public String getAccountNumber() {
	return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
	this.accountNumber = accountNumber;
    }

    public String getPrimAcctFirstName() {
	return primAcctFirstName;
    }

    public void setPrimAcctFirstName(String primAcctFirstName) {
	this.primAcctFirstName = primAcctFirstName;
    }

    public String getPrimAcctLastName() {
	return primAcctLastName;
    }

    public void setPrimAcctLastName(String primAcctLastName) {
	this.primAcctLastName = primAcctLastName;
    }

    public String getPrimAcctEmailId() {
	return primAcctEmailId;
    }

    public void setPrimAcctEmailId(String primAcctEmailId) {
	this.primAcctEmailId = primAcctEmailId;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
