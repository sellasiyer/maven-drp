package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

import com.bestbuy.bbym.ise.domain.Item;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Carrier;

public class EntitlementLookup implements Serializable {

    public enum EntitlementDeviceStatusType {

	TRUE("Yes"), FALSE("No");

	private final String label;

	private EntitlementDeviceStatusType(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return label;
	}
    }

    private static final long serialVersionUID = 1L;

    private EntitlementPurchaseType entitlementPurchaseType;
    private String productSku;
    private String storeSku;
    private String onlineSku;
    private FourPartKey fourpartkey;
    private EntitlementDeviceStatusType defectiveDevice;
    private String gspNumber;
    private String gspDescription;
    private String gspOwner;
    private String gspStatus;
    private Date gspPlanExpiryDate;
    private String gspPlanType;
    private String rzNumber;
    private String rzTier;
    private String rzMemberTierCode;
    private int rzPoints;
    private String devicePhoneNumber;
    private String rzCustomerFirstName;
    private String rzCustomerLastName;
    private Line line;
    private Carrier carrier;
    private String serialNumber;
    private String manufacturerSerialNumber;
    private Item item;
    private String transactionId;
    private String onlineOrderNumber;
    private String manufacturer;
    private String customerFullName;
    private Date activationDate;
    private boolean lockGSPMode;
    private boolean continueWithoutSerial;

    public boolean isContinueWithoutSerial() {
	return continueWithoutSerial;
    }

    public void setContinueWithoutSerial(boolean continueWithoutSerial) {
	this.continueWithoutSerial = continueWithoutSerial;
    }

    public boolean isLockGSPMode() {
	return lockGSPMode;
    }

    public void setLockGSPMode(boolean lockGSPMode) {
	this.lockGSPMode = lockGSPMode;
    }

    public Date getActivationDate() {
	return activationDate;
    }

    public void setActivationDate(Date activationDate) {
	this.activationDate = activationDate;
    }

    public String getCustomerFullName() {
	return customerFullName;
    }

    public void setCustomerFullName(String customerFullName) {
	this.customerFullName = customerFullName;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
    }

    public String getTransactionId() {
	return transactionId;
    }

    public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
    }

    public String getSerialNumber() {
	return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
    }

    public String getManufacturerSerialNumber() {
	return manufacturerSerialNumber;
    }

    public void setManufacturerSerialNumber(String manufacturerSerialNumber) {
	this.manufacturerSerialNumber = manufacturerSerialNumber;
    }

    public Item getItem() {
	return item;
    }

    public void setItem(Item item) {
	this.item = item;
    }

    public String getDevicePhoneNumber() {
	return devicePhoneNumber;
    }

    public void setDevicePhoneNumber(String devicePhoneNumber) {
	this.devicePhoneNumber = devicePhoneNumber;
    }

    public EntitlementPurchaseType getEntitlementPurchaseType() {
	return entitlementPurchaseType;
    }

    public void setEntitlementPurchaseType(EntitlementPurchaseType entitlementPurchaseType) {
	this.entitlementPurchaseType = entitlementPurchaseType;
    }

    public String getProductSku() {
	return productSku;
    }

    public void setProductSku(String productSku) {
	this.productSku = productSku;
    }

    public String getStoreSku() {
	return storeSku;
    }

    public void setStoreSku(String storeSku) {
	this.storeSku = storeSku;
    }

    public String getOnlineSku() {
	return onlineSku;
    }

    public void setOnlineSku(String onlineSku) {
	this.onlineSku = onlineSku;
    }

    public String getOnlineOrderNumber() {
	return onlineOrderNumber;
    }

    public void setOnlineOrderNumber(String onlineOrderNumber) {
	this.onlineOrderNumber = onlineOrderNumber;
    }

    public FourPartKey getFourpartkey() {
	return fourpartkey;
    }

    public void setFourpartkey(FourPartKey fourpartkey) {
	this.fourpartkey = fourpartkey;
    }

    public EntitlementDeviceStatusType getDefectiveDevice() {
	return defectiveDevice;
    }

    public void setDefectiveDevice(EntitlementDeviceStatusType defectiveDevice) {
	this.defectiveDevice = defectiveDevice;
    }

    public String getGspNumber() {
	return gspNumber;
    }

    public void setGspNumber(String gspNumber) {
	this.gspNumber = gspNumber;
    }

    public String getGspDescription() {
	return gspDescription;
    }

    public void setGspDescription(String gspDescription) {
	this.gspDescription = gspDescription;
    }

    public String getRzMemberTierCode() {
	return rzMemberTierCode;
    }

    public void setRzMemberTierCode(String rzMemberTierCode) {
	this.rzMemberTierCode = rzMemberTierCode;
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

    public int getRzPoints() {
	return rzPoints;
    }

    public void setRzPoints(int rzPoints) {
	this.rzPoints = rzPoints;
    }

    public String getRzCustomerFirstName() {
	return rzCustomerFirstName;
    }

    public void setRzCustomerFirstName(String rzCustomerFirstName) {
	this.rzCustomerFirstName = rzCustomerFirstName;
    }

    public String getRzCustomerLastName() {
	return rzCustomerLastName;
    }

    public void setRzCustomerLastName(String rzCustomerLastName) {
	this.rzCustomerLastName = rzCustomerLastName;
    }

    public String getGspOwner() {
	return gspOwner;
    }

    public void setGspOwner(String gspOwner) {
	this.gspOwner = gspOwner;
    }

    public String getGspStatus() {
	return gspStatus;
    }

    public void setGspStatus(String gspStatus) {
	this.gspStatus = gspStatus;
    }

    public Date getGspPlanExpiryDate() {
	return gspPlanExpiryDate;
    }

    public void setGspPlanExpiryDate(Date gspPlanExpiryDate) {
	this.gspPlanExpiryDate = gspPlanExpiryDate;
    }

    public String getGspPlanType() {
	return gspPlanType;
    }

    public void setGspPlanType(String gspPlanType) {
	this.gspPlanType = gspPlanType;
    }

    public Line getLine() {
	return line;
    }

    public void setLine(Line line) {
	this.line = line;
    }

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }

    public void resetGSPValues() {
	this.gspDescription = "";
	this.gspOwner = "";
	this.gspStatus = "";
    }

    public void resetRzValues() {
	this.rzCustomerFirstName = "";
	this.rzCustomerLastName = "";
	this.rzPoints = 0;
	this.rzTier = "";
    }

    public void resetAllGSPData() {
	this.gspNumber = "";
	this.gspDescription = "";
	this.gspOwner = "";
	this.gspStatus = "";
    }

    public void resetAllRzData() {
	this.rzNumber = "";
	this.rzCustomerFirstName = "";
	this.rzCustomerLastName = "";
	this.rzPoints = 0;
	this.rzTier = "";
    }

}
