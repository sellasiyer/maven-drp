/**
 *
 */
package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;

/**
 * @author a186288
 * 
 */
public class Store implements Serializable {

    private static final long serialVersionUID = 4641017744372615827L;

    private String id;
    private int optInCount;
    private int upgradeCheckCount;
    private String storePhoneNumber;
    private String storeName;
    private String district;
    private String region;
    private ScoreboardStoreType storeType;

    public void setOptInCount(int optInCount) {
	this.optInCount = optInCount;
    }

    public int getOptInCount() {
	return optInCount;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getId() {
	return id;
    }

    public void setUpgradeCheckCount(int upgradeCheckCount) {
	this.upgradeCheckCount = upgradeCheckCount;
    }

    public int getUpgradeCheckCount() {
	return upgradeCheckCount;
    }

    public String getStoreName() {
	return storeName;
    }

    public void setStoreName(String storeName) {
	this.storeName = storeName;
    }

    public ScoreboardStoreType getStoreType() {
	return storeType;
    }

    public void setStoreType(ScoreboardStoreType storeType) {
	this.storeType = storeType;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public String getStorePhoneNumber() {
	return storePhoneNumber;
    }

    public void setStorePhoneNumber(String storePhoneNumber) {
	this.storePhoneNumber = storePhoneNumber;
    }

    public String getDistrict() {
	return district;
    }

    public void setDistrict(String district) {
	this.district = district;
    }

    public String getRegion() {
	return region;
    }

    public void setRegion(String region) {
	this.region = region;
    }
}
