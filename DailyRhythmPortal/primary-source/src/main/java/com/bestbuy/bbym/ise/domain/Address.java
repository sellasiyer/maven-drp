package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

// TODO Figure out why we have two Address objects

/**
 * Address information for Customer - Applies to both Carrier and BBY Customer
 * Object
 */
public class Address extends BaseObject implements Serializable {

    private static final long serialVersionUID = 4138982543770676667L;

    private String addressId;
    private String addressLine1;
    private String addressLine2;
    private String addressType;
    private String city;
    private String state;
    private String zipcode;
    private String zipcode4;
    private String country;

    public String getAddressId() {
	return addressId;
    }

    public void setAddressId(String addressId) {
	this.addressId = addressId;
    }

    public void setAddressLine1(String addressLine1) {
	this.addressLine1 = addressLine1;
    }

    public String getAddressLine1() {
	return this.addressLine1;
    }

    public void setAddressLine2(String addressLine2) {
	this.addressLine2 = addressLine2;
    }

    public String getAddressLine2() {
	return this.addressLine2;
    }

    public void setAddressType(String addressType) {
	this.addressType = addressType;
    }

    public String getAddressType() {
	return this.addressType;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getCity() {
	return this.city;
    }

    public void setState(String state) {
	this.state = state;
    }

    public String getState() {
	return this.state;
    }

    public void setZipcode(String zipcode) {
	this.zipcode = zipcode;
    }

    public String getZipcode() {
	return this.zipcode;
    }

    public void setZipcode4(String zipcode4) {
	this.zipcode4 = zipcode4;
    }

    public String getZipcode4() {
	return this.zipcode4;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    public String getCountry() {
	return this.country;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
