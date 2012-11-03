package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

import com.bestbuy.bbym.ise.domain.BaseObject;
import com.bestbuy.bbym.ise.domain.Carrier;

/**
 * 
 * @author a175157
 */
public class CarrierAccount extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String carrierAccountNumber;
    private String coverageZip;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dataSharingKey;
    private Address address;
    private String email;
    private Date dob;
    private String idType;
    private String idIssuer;
    private String idCode;
    private Date idExpirationDate;
    private Carrier carrier;

    public CarrierAccount() {
	super();
    }

    public CarrierAccount(String carrierAccountNumber, String coverageZip, String firstName, String lastName,
	    String phoneNumber, String dataSharingKey, Address address, String email, String createdBy,
	    String modifiedBy) {
	super(createdBy, modifiedBy);
	this.carrierAccountNumber = carrierAccountNumber;
	this.coverageZip = coverageZip;
	this.firstName = firstName;
	this.lastName = lastName;
	this.phoneNumber = phoneNumber;
	this.dataSharingKey = dataSharingKey;
	this.address = address;
	this.email = email;
    }

    public String getCarrierAccountNumber() {
	return carrierAccountNumber;
    }

    public void setCarrierAccountNumber(String carrierAccountNumber) {
	this.carrierAccountNumber = carrierAccountNumber;
    }

    public String getCoverageZip() {
	return coverageZip;
    }

    public void setCoverageZip(String coverageZip) {
	this.coverageZip = coverageZip;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public String getEmail() {
	return email;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public Date getDob() {
	return dob;
    }

    public void setDob(Date dob) {
	this.dob = dob;
    }

    public String getIdType() {
	return idType;
    }

    public void setIdType(String idType) {
	this.idType = idType;
    }

    public String getIdIssuer() {
	return idIssuer;
    }

    public void setIdIssuer(String idIssuer) {
	this.idIssuer = idIssuer;
    }

    public String getIdCode() {
	return idCode;
    }

    public void setIdCode(String idCode) {
	this.idCode = idCode;
    }

    public Date getIdExpirationDate() {
	return idExpirationDate;
    }

    public void setIdExpirationDate(Date idExpirationDate) {
	this.idExpirationDate = idExpirationDate;
    }

    @Override
    public String toString() {
	return "CarrierAccount [carrierAccountNumber=" + carrierAccountNumber + ", coverageZip=" + coverageZip
		+ ", firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber=" + phoneNumber
		+ ", dataSharingKey=" + dataSharingKey + ", address=" + address + ", email=" + email + ", dob=" + dob
		+ ", idType=" + idType + ", idIssuer=" + idIssuer + ", idCode=" + idCode + ", idExpirationDate="
		+ idExpirationDate + ", carrier=" + carrier + "]";
    }

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }
}
