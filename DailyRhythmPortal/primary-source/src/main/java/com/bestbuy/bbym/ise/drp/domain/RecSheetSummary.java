package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * RecSheetSummary holds the information for plan, device, gspPlan and buyBack
 * plan.
 * 
 * @author a904002
 */
@XmlAccessorType(XmlAccessType.PROPERTY)
public class RecSheetSummary extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String planInfo;
    private String deviceInfo;
    private String gspPlanInfo;
    private String buyBackPlanInfo;
    private String dataSharingKey;

    public RecSheetSummary() {
    }

    public RecSheetSummary(String planInfo, String deviceInfo, String gspPlanInfo, String buyBackPlanInfo,
	    String dataSharingKey) {
	this.planInfo = planInfo;
	this.deviceInfo = deviceInfo;
	this.gspPlanInfo = gspPlanInfo;
	this.buyBackPlanInfo = buyBackPlanInfo;
	this.dataSharingKey = dataSharingKey;
    }

    public RecSheetSummary(String planInfo, String deviceInfo, String gspPlanInfo, String buyBackPlanInfo,
	    String dataSharingKey, String createdBy, String modifiedBy) {
	super(createdBy, modifiedBy);
	this.planInfo = planInfo;
	this.deviceInfo = deviceInfo;
	this.gspPlanInfo = gspPlanInfo;
	this.buyBackPlanInfo = buyBackPlanInfo;
	this.dataSharingKey = dataSharingKey;
    }

    public RecSheetSummary(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    @XmlElement(name = "buybackInfo")
    public String getBuyBackPlanInfo() {
	return buyBackPlanInfo;
    }

    public void setBuyBackPlanInfo(String buyBackPlanInfo) {
	this.buyBackPlanInfo = buyBackPlanInfo;
    }

    @XmlTransient
    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public String getDeviceInfo() {
	return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
	this.deviceInfo = deviceInfo;
    }

    @XmlElement(name = "gspInfo")
    public String getGspPlanInfo() {
	return gspPlanInfo;
    }

    public void setGspPlanInfo(String gspPlanInfo) {
	this.gspPlanInfo = gspPlanInfo;
    }

    public String getPlanInfo() {
	return planInfo;
    }

    public void setPlanInfo(String planInfo) {
	this.planInfo = planInfo;
    }

    @Override
    @XmlTransient
    public String getCreatedBy() {
	return super.getCreatedBy();
    }

    @Override
    @XmlTransient
    public Date getCreatedOn() {
	return super.getCreatedOn();
    }

    @Override
    @XmlTransient
    public String getModifiedBy() {
	return super.getModifiedBy();
    }

    @Override
    @XmlTransient
    public Date getModifiedOn() {
	return super.getModifiedOn();
    }
}
