/**
 * 
 */
package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;
import com.bestbuy.bbym.ise.domain.BaseObject;
import com.bestbuy.bbym.ise.domain.Carrier;

/**
 * @author a175157
 *
 */
public class MobileEntitlementRequest extends BaseObject {

    private static final long serialVersionUID = 1L;
    private String manufacturerSerialNumber;
    private String sku;
    private Carrier carrier;
    private String handsetId;
    private Date purchaseDate;
    private String planType;
    private String protectionPlanId;
    private String rewardZoneTier;
    private String transactionSourceSystem;
    private String transactionKeyType;
    private String sourceTransactionKey;
    private boolean defective;
    private boolean upgradeEligible;
    private boolean continueWithoutSerial;

    public String getManufacturerSerialNumber() {
	return manufacturerSerialNumber;
    }

    public void setManufacturerSerialNumber(String manufacturerSerialNumber) {
	this.manufacturerSerialNumber = manufacturerSerialNumber;
    }

    public String getSku() {
	return sku;
    }

    public void setSku(String sku) {
	this.sku = sku;
    }

    public Carrier getCarrier() {
	return carrier;
    }

    public void setCarrier(Carrier carrier) {
	this.carrier = carrier;
    }

    public String getHandsetId() {
	return handsetId;
    }

    public void setHandsetId(String handsetId) {
	this.handsetId = handsetId;
    }

    public Date getPurchaseDate() {
	return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
	this.purchaseDate = purchaseDate;
    }

    public String getPlanType() {
	return planType;
    }

    public void setPlanType(String planType) {
	this.planType = planType;
    }

    public String getProtectionPlanId() {
	return protectionPlanId;
    }

    public void setProtectionPlanId(String protectionPlanId) {
	this.protectionPlanId = protectionPlanId;
    }

    public String getRewardZoneTier() {
	return rewardZoneTier;
    }

    public void setRewardZoneTier(String rewardZoneTier) {
	this.rewardZoneTier = rewardZoneTier;
    }

    public String getTransactionSourceSystem() {
	return transactionSourceSystem;
    }

    public void setTransactionSourceSystem(String transactionSourceSystem) {
	this.transactionSourceSystem = transactionSourceSystem;
    }

    public String getTransactionKeyType() {
	return transactionKeyType;
    }

    public void setTransactionKeyType(String transactionKeyType) {
	this.transactionKeyType = transactionKeyType;
    }

    public String getSourceTransactionKey() {
	return sourceTransactionKey;
    }

    public void setSourceTransactionKey(String sourceTransactionKey) {
	this.sourceTransactionKey = sourceTransactionKey;
    }

    public boolean isDefective() {
	return defective;
    }

    public void setDefective(boolean defective) {
	this.defective = defective;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public void setUpgradeEligible(boolean upgradeEligible) {
	this.upgradeEligible = upgradeEligible;
    }

    public boolean isUpgradeEligible() {
	return upgradeEligible;
    }

    public boolean isContinueWithoutSerial() {
	return continueWithoutSerial;
    }

    public void setContinueWithoutSerial(boolean continueWithoutSerial) {
	this.continueWithoutSerial = continueWithoutSerial;
    }

}
