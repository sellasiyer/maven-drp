package com.bestbuy.bbym.ise.drp.rest.message;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author a904002
 */
@XmlRootElement
public class Recommendation {

    private String dataSharingKey;

    private String planInfo;
    private String gspInfo;
    private String buybackInfo;
    private String deviceInfo;

    public String getDeviceInfo() {
	return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
	this.deviceInfo = deviceInfo;
    }

    public String getGspInfo() {
	return gspInfo;
    }

    public void setGspInfo(String gspInfo) {
	this.gspInfo = gspInfo;
    }

    public String getPlanInfo() {
	return planInfo;
    }

    public void setPlanInfo(String planInfo) {
	this.planInfo = planInfo;
    }

    public String getBuybackInfo() {
	return buybackInfo;
    }

    public void setBuybackInfo(String buybackInfo) {
	this.buybackInfo = buybackInfo;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

}
