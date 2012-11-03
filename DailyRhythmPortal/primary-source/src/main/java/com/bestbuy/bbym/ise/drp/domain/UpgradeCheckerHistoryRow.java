package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author a218635
 */
public class UpgradeCheckerHistoryRow implements Serializable {

    private static final long serialVersionUID = -4353104212576678798L;

    private String phoneNumber;
    private Date upgradeCheckDate;
    private String eligibilityType;
    private String sourceSystem;
    private Date upgradeEligibilityDate;
    private int locationId;
    private String userId;

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public Date getUpgradeCheckDate() {
	return upgradeCheckDate;
    }

    public void setUpgradeCheckDate(Date upgradeCheckDate) {
	this.upgradeCheckDate = upgradeCheckDate;
    }

    public String getEligibilityType() {
	return eligibilityType;
    }

    public void setEligibilityType(String eligibilityType) {
	this.eligibilityType = eligibilityType;
    }

    public String getSourceSystem() {
	return sourceSystem;
    }

    public void setSourceSystem(String sourceSystem) {
	this.sourceSystem = sourceSystem;
    }

    public Date getUpgradeEligibilityDate() {
	return upgradeEligibilityDate;
    }

    public void setUpgradeEligibilityDate(Date upgradeEligibilityDate) {
	this.upgradeEligibilityDate = upgradeEligibilityDate;
    }

    public int getLocationId() {
	return locationId;
    }

    public void setLocationId(int locationId) {
	this.locationId = locationId;
    }

    public String getUserId() {
	return userId;
    }

    public void setUserId(String userId) {
	this.userId = userId;
    }

}
