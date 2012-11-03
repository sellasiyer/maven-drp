package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import com.bestbuy.bbym.ise.domain.Customer;

public class RetExchRZLookupCustomer implements Serializable {

    private static final long serialVersionUID = -6632933362198297938L;
    private Customer customer;
    private String rzNumber;
    private String rzTier;
    private long rzPoints;
    private String rzMemberTierCode;
    private String rzMemberDetails;

    public RetExchRZLookupCustomer(RetExchRZLookupCustomer retExchRZLookupCustomer) {

	this.customer = retExchRZLookupCustomer.getCustomer();
	this.rzNumber = retExchRZLookupCustomer.getRzNumber();
	this.rzTier = retExchRZLookupCustomer.getRzTier();
	this.rzPoints = retExchRZLookupCustomer.getRzPoints();

    }

    public RetExchRZLookupCustomer() {
	// TODO Auto-generated constructor stub
    }

    public String getRzMemberDetails() {
	rzMemberDetails = getCustomer().getLastName() + "," + getCustomer().getFirstName() + "\n"
		+ getCustomer().getAddressString() + getCustomer().getContactPhone();
	return rzMemberDetails;
    }

    public void setRzMemberDetails(String customerDetails) {
	this.rzMemberDetails = customerDetails;
    }

    public Customer getCustomer() {
	return customer;
    }

    public void setCustomer(Customer customer) {
	this.customer = customer;
    }

    public String getRzNumber() {
	return rzNumber;
    }

    public void setRzNumber(String rzNumber) {
	this.rzNumber = rzNumber;
    }

    public String getRzTier() {
	return rzTier;
    }

    public void setRzTier(String rzTier) {
	this.rzTier = rzTier;
    }

    public long getRzPoints() {
	return rzPoints;
    }

    public void setRzPoints(long rzPoints) {
	this.rzPoints = rzPoints;
    }

    public void resetRzValues() {
	this.setRzNumber("");
	this.setRzPoints(0);
	this.setRzTier("");
    }

    public String toString() {

	return "RZ LOOKUP CUSTOMER =" + this.getRzMemberDetails() + this.getRzNumber() + this.getRzPoints()
		+ this.getRzTier() + this.getCustomer();

    }

    public void setRzValues(RetExchRZLookupCustomer row) {

	this.rzTier = row.rzTier;
	this.rzPoints = row.rzPoints;
	this.rzNumber = row.rzNumber;

    }

    public String getRzMemberTierCode() {
	return rzMemberTierCode;
    }

    public void setRzMemberTierCode(String rzMemberTierCode) {
	this.rzMemberTierCode = rzMemberTierCode;
    }

}
