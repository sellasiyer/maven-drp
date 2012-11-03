package com.bestbuy.bbym.ise.drp.helpers;

import java.util.Map;

import com.bestbuy.bbym.ise.domain.Device;

/**
 * @author a218635
 */
public class UpgCheckerResponseData {

    private Map<String, Device> deviceTradeInInfoMap;
    private Map<String, String> optInStatusesMap;
    private Map<String, Boolean> optInAllowedMap;
    private int optInCount;
    private int upgradeCount;

    public Map<String, Device> getDeviceTradeInInfoMap() {
	return deviceTradeInInfoMap;
    }

    public void setDeviceTradeInInfoMap(Map<String, Device> deviceTradeInInfoMap) {
	this.deviceTradeInInfoMap = deviceTradeInInfoMap;
    }

    public Map<String, String> getOptInStatusesMap() {
	return optInStatusesMap;
    }

    public void setOptInStatusesMap(Map<String, String> optInStatusesMap) {
	this.optInStatusesMap = optInStatusesMap;
    }

    public int getOptInCount() {
	return optInCount;
    }

    public void setOptInCount(int optInCount) {
	this.optInCount = optInCount;
    }

    public int getUpgradeCount() {
	return upgradeCount;
    }

    public void setUpgradeCount(int upgradeCount) {
	this.upgradeCount = upgradeCount;
    }

    public Map<String, Boolean> getOptInAllowedMap() {
	return optInAllowedMap;
    }

    public void setOptInAllowedMap(Map<String, Boolean> optInAllowedMap) {
	this.optInAllowedMap = optInAllowedMap;
    }
}
