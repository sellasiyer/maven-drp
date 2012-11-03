package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

import com.bestbuy.bbym.ise.domain.BaseObject;

// TODO Figure out why we have two Address objects

/**
 *
 * @author a175157
 */
public class Address extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;

    private String addressId;
    private String zip;
    private String addressline1;
    private String addressline2;
    private String city;
    private String state;
    private String countryCode;
    private String urbanizationCode;

    public Address() {
    }

    public Address(String addressId, String zip, String addressline1, String addressline2, String city, String state,
	    String createdBy, String modifiedBy) {
	super(createdBy, modifiedBy);
	this.addressId = addressId;
	this.zip = zip;
	this.addressline1 = addressline1;
	this.addressline2 = addressline2;
	this.city = city;
	this.state = state;
    }

    public String getAddressId() {
	return addressId;
    }

    public void setAddressId(String addressId) {
	this.addressId = addressId;
    }

    public String getZip() {
	return zip;
    }

    public void setZip(String zip) {
	this.zip = zip;
    }

    public String getAddressline1() {
	return addressline1;
    }

    public void setAddressline1(String addressline1) {
	this.addressline1 = addressline1;
    }

    public String getAddressline2() {
	return addressline2;
    }

    public void setAddressline2(String addressline2) {
	this.addressline2 = addressline2;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getState() {
	return state;
    }

    public void setState(String state) {
	this.state = state;
    }

    @Override
    public String getCreatedBy() {
	return super.getCreatedBy();
    }

    @Override
    public Date getCreatedOn() {
	return super.getCreatedOn();
    }

    @Override
    public String getModifiedBy() {
	return super.getModifiedBy();
    }

    @Override
    public Date getModifiedOn() {
	return super.getModifiedOn();
    }

    public String getUrbanizationCode() {
	return urbanizationCode;
    }

    public void setUrbanizationCode(String urbanizationCode) {
	this.urbanizationCode = urbanizationCode;
    }

    public String getCountryCode() {
	return countryCode;
    }

    public void setCountryCode(String countryCode) {
	this.countryCode = countryCode;
    }

    @Override
    public int hashCode() {
	int hash = 0;
	hash += (addressId != null?addressId.hashCode():0);
	return hash;
    }

    @Override
    public boolean equals(Object object) {
	// TODO: Warning - this method won't work in the case the id fields are
	// not set
	if (!(object instanceof Address)){
	    return false;
	}
	Address other = (Address) object;
	if ((this.addressId == null && other.addressId != null)
		|| (this.addressId != null && !this.addressId.equals(other.addressId))){
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	return "Address [id=" + addressId + ", zip=" + zip + ", addressline1=" + addressline1 + ", addressline2="
		+ addressline2 + ", city=" + city + ", state=" + state + "]";
    }
}
