package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.bestbuy.bbym.ise.domain.BaseObject;
import com.bestbuy.bbym.ise.util.WorkFlowTypeEnum;

/**
 * 
 * @author a175157
 */
@XmlRootElement
public class DataTransferSummary extends BaseObject implements Serializable {

    private static final long serialVersionUID = 1L;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String dataSharingKey;
    private String detailsUrl;
    private boolean transferFlag;
    private String capTransId;
    private String storeId;
    private String srcSys;
    private WorkFlowTypeEnum workFlowType;

    public DataTransferSummary() {
	super();
    }

    public DataTransferSummary(String firstName, String lastName, String phoneNumber, String dataSharingKey,
	    String detailsUrl, boolean transferFlag, String capTransId, String storeId, String srcSys,
	    WorkFlowTypeEnum workFlowType) {
	super();
	this.firstName = firstName;
	this.lastName = lastName;
	this.phoneNumber = phoneNumber;
	this.dataSharingKey = dataSharingKey;
	this.detailsUrl = detailsUrl;
	this.transferFlag = transferFlag;
	this.capTransId = capTransId;
	this.storeId = storeId;
	this.srcSys = srcSys;
	this.workFlowType = workFlowType;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
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

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getDetailsUrl() {
	return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
	this.detailsUrl = detailsUrl;
    }

    public boolean isTransferFlag() {
	return transferFlag;
    }

    public void setTransferFlag(boolean transferFlag) {
	this.transferFlag = transferFlag;
    }

    public String getCapTransId() {
	return capTransId;
    }

    public void setCapTransId(String capTransId) {
	this.capTransId = capTransId;
    }

    public String getStoreId() {
	return storeId;
    }

    public void setStoreId(String storeId) {
	this.storeId = storeId;
    }

    public String getSrcSys() {
	return srcSys;
    }

    public void setSrcSys(String srcSys) {
	this.srcSys = srcSys;
    }

    @Override
    public String toString() {
	return "DataTransferSummary [firstName=" + firstName + ", lastName=" + lastName + ", phoneNumber="
		+ phoneNumber + ", dataSharingKey=" + dataSharingKey + ", transferFlag=" + transferFlag + "]";
    }

    public Date getCreatedOn() {
	return super.getCreatedOn();
    }

    public void setCreatedOn(Date createdOn) {
	super.setCreatedOn(createdOn);
    }

    public Date getModifiedOn() {
	return super.getModifiedOn();
    }

    public void setModifiedOn(Date modifiedOn) {
	super.setModifiedOn(modifiedOn);
    }

    public String getCreatedBy() {
	return super.getCreatedBy();
    }

    public void setCreatedBy(String createdBy) {
	super.setCreatedBy(createdBy);
    }

    public String getModifiedBy() {
	return super.getModifiedBy();
    }

    public void setModifiedBy(String modifiedBy) {
	super.setModifiedBy(modifiedBy);
    }

    public WorkFlowTypeEnum getWorkFlowType() {
	return workFlowType;
    }

    public void setWorkFlowType(WorkFlowTypeEnum workFlowType) {
	this.workFlowType = workFlowType;
    }
}
