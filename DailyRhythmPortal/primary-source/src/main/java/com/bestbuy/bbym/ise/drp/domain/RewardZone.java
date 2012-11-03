package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.List;

import com.bestbuy.bbym.ise.domain.Address;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RewardZone implements Serializable {

    public static final String ACCOUNT_STATUS_A = "A";
    public static final String ACCOUNT_STATUS_P = "P";

    public static final String MEMBER_TIER_STANDARD = "Standard";
    public static final String MEMBER_TIER_SILVER = "Silver";
    public static final String MEMBER_TIER_PREMIUM = "Premier Silver";

    public static final String BESTBUY_MASTER_CARD = "RZMC";
    public static final String BESTBUY_COMPANY_CARD = "RZCC";

    private static final long serialVersionUID = 1L;

    private String memberNumber;
    private String memberTier;
    private String memberTierCode;
    private String accountStatus;
    private int pointsBalance;
    private String ccAccountType;
    private boolean memberCertsAvailable;
    private int convertedPointsDollarAmt;
    private String firstName;
    private String lastName;
    private Address address;
    private String contactPhone;
    private List<RwzMemberCert> rwzMemberCertList;

    public String getMemberNumber() {
	return memberNumber;
    }

    public void setMemberNumber(String memberNumber) {
	this.memberNumber = memberNumber;
    }

    public String getMemberTier() {
	return memberTier;
    }

    public void setMemberTier(String memberTier) {
	this.memberTier = memberTier;
    }

    public String getMemberTierCode() {
	return memberTierCode;
    }

    public void setMemberTierCode(String memberTierCode) {
	this.memberTierCode = memberTierCode;
    }

    public String getAccountStatus() {
	return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
	this.accountStatus = accountStatus;
    }

    public int getPointsBalance() {
	return pointsBalance;
    }

    public void setPointsBalance(int pointsBalance) {
	this.pointsBalance = pointsBalance;
    }

    public String getCcAccountType() {
	return ccAccountType;
    }

    public void setCcAccountType(String ccAccountType) {
	this.ccAccountType = ccAccountType;
    }

    public boolean isMemberCertsAvailable() {
	return memberCertsAvailable;
    }

    public void setMemberCertsAvailable(boolean memberCertsAvailable) {
	this.memberCertsAvailable = memberCertsAvailable;
    }

    public int getConvertedPointsDollarAmt() {
	return convertedPointsDollarAmt;
    }

    public void setConvertedPointsDollarAmt(int convertedPointsDollarAmt) {
	this.convertedPointsDollarAmt = convertedPointsDollarAmt;
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

    public List<RwzMemberCert> getRwzMemberCertList() {
	return rwzMemberCertList;
    }

    public void setRwzMemberCertList(List<RwzMemberCert> rwzMemberCertList) {
	this.rwzMemberCertList = rwzMemberCertList;
    }

    public Address getAddress() {
	return address;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public String getContactPhone() {
	return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
	this.contactPhone = contactPhone;
    }

    public String getAddressString() {
	if (address != null && StringUtils.isNotBlank(address.getAddressLine1())
		&& StringUtils.isNotBlank(address.getCity()) && StringUtils.isNotBlank(address.getState())
		&& StringUtils.isNotBlank(address.getZipcode())){
	    return address.getAddressLine1() + " " + address.getCity() + " " + address.getState() + " "
		    + address.getZipcode();
	}
	return null;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
