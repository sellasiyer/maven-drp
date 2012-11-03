package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class PhoneModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Carrier carrier;
    private OperatingSystem os;
    private String make;
    private String model;
    private int noOfPhones;
    private int noOfDamaged;
    private String storeId;
    private LoanerPhoneCondition condition;

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }

    public OperatingSystem getOs() {
	return os;
    }

    public void setOs(OperatingSystem os) {
	this.os = os;
    }

    public String getMake() {
	return make;
    }

    public void setMake(String make) {
	this.make = make;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public int getNoOfPhones() {
	return noOfPhones;
    }

    public void setNoOfPhones(int noOfPhones) {
	this.noOfPhones = noOfPhones;
    }

    public int getNoOfDamaged() {
	return noOfDamaged;
    }

    public void setNoOfDamaged(int noOfDamaged) {
	this.noOfDamaged = noOfDamaged;
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public LoanerPhoneCondition getCondition() {
	return condition;
    }

    public void setCondition(LoanerPhoneCondition condition) {
	this.condition = condition;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
