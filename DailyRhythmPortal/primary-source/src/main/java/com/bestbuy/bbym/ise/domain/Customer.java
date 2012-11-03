package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.util.Util;

/**
 * Customer domain object - central object to hold the information about
 * customer information from Carrier and BBY Mobile
 * 
 * reviewed and updated by a218635
 */
public class Customer extends BaseObject implements Serializable {

    private static final long serialVersionUID = 4251719847353073030L;

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private String last4ssn;
    private String acctNumber;
    private String bbyCustomerId;
    private String rewardZoneId;
    private String contactTime;
    private String preferredContactMethod;
    private String contactPhone;
    private Subscription subscription;
    private Address address;
    private List<Device> deviceList;
    private List<Item> purchaseHistory;
    private List<ServicePlan> servicePlanList;
    private RewardZone rewardZone;

    private boolean simpleUpgradeFlag;

    private String capTransactionId;
    private boolean transferFlag;
    private String dataSharingKey;
    private Carrier carrier;

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return this.id;
    }

    public void setEmail(String email) {
	this.email = email;
    }

    public String getEmail() {
	return this.email;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getFirstName() {
	return this.firstName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getLastName() {
	return this.lastName;
    }

    public String getNameString() {
	return Util.determineNameString(firstName, lastName);
    }

    public void setLast4ssn(String last4ssn) {
	this.last4ssn = last4ssn;
    }

    public String getLast4ssn() {
	return this.last4ssn;
    }

    public void setAcctNumber(String acctNumber) {
	this.acctNumber = acctNumber;
    }

    public String getAcctNumber() {
	return this.acctNumber;
    }

    public void setBbyCustomerId(String bbyCustomerId) {
	this.bbyCustomerId = bbyCustomerId;
    }

    public String getBbyCustomerId() {
	return this.bbyCustomerId;
    }

    public void setRewardZoneId(String rewardZoneId) {
	this.rewardZoneId = rewardZoneId;
    }

    public String getRewardZoneId() {
	return this.rewardZoneId;
    }

    public void setContactTime(String contactTime) {
	this.contactTime = contactTime;
    }

    public String getContactTime() {
	return this.contactTime;
    }

    public void setPreferredContactMethod(String preferredContactMethod) {
	this.preferredContactMethod = preferredContactMethod;
    }

    public String getPreferredContactMethod() {
	return this.preferredContactMethod;
    }

    public void setContactPhone(String contactPhone) {
	this.contactPhone = contactPhone;
    }

    public String getContactPhone() {
	return this.contactPhone;
    }

    public Subscription getSubscription() {
	return subscription;
    }

    public void setSubscription(Subscription subscription) {
	this.subscription = subscription;
    }

    public void setAddress(Address address) {
	this.address = address;
    }

    public Address getAddress() {
	return address;
    }

    public String getCapTransactionId() {
	return capTransactionId;
    }

    public void setCapTransactionId(String capTransactionId) {
	this.capTransactionId = capTransactionId;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public boolean isTransferFlag() {
	return transferFlag;
    }

    public void setTransferFlag(boolean transferFlag) {
	this.transferFlag = transferFlag;
    }

    public boolean isSimpleUpgradeFlag() {
	return simpleUpgradeFlag;
    }

    public void setSimpleUpgradeFlag(boolean simpleUpgradeFlag) {
	this.simpleUpgradeFlag = simpleUpgradeFlag;
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

    /**
     * Specific to BBYCustomer Object. Carrier don't need to access this method.
     */
    public void setDeviceList(List<Device> deviceList) {
	this.deviceList = deviceList;
    }

    /**
     * Specific to BBYCustomer Object. Carrier don't need to access this method.
     * 
     * @return List of devices associated to the BBY Customer profile
     */
    public List<Device> getDeviceList() {
	return deviceList;
    }

    public void setAllPurchaseItems(List<Item> itemList) {
	this.purchaseHistory = itemList;
    }

    public List<Item> getAllPurchaseItems() {
	return purchaseHistory;
    }

    public List<ServicePlan> getServicePlanList() {
	return servicePlanList;
    }

    public void setServicePlanList(List<ServicePlan> servicePlanList) {
	this.servicePlanList = servicePlanList;
    }

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }

    public RewardZone getRewardZone() {
	return rewardZone;
    }

    public void setRewardZone(RewardZone rewardZone) {
	this.rewardZone = rewardZone;
    }

}
