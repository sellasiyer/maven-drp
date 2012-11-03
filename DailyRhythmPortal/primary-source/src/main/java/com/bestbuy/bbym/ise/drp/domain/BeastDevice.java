package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlTransient;

import com.bestbuy.bbym.ise.domain.BaseObject;

@XmlAccessorType(XmlAccessType.PROPERTY)
public class BeastDevice extends BaseObject {

    private String id;
    private String dataSharingKey;
    private String beastTransactionId;
    private String handsetIdentifier;
    private String fourSymbolHandsetIdentifier;
    private String phoneNumber;
    private String deviceDesc;
    private String carrier;
    private String handsetIdentifierType;
    private String source;
    private boolean selectedFlag;

    public BeastDevice(String id, String dataSharingKey, String beastTransactionId, String handsetIdentifier,
	    String fourSymbolHandsetIdentifier, String phoneNumber, String deviceDesc, String carrier,
	    String handsetIdentifierType, String source, boolean selectedFlag) {
	super();
	this.id = id;
	this.dataSharingKey = dataSharingKey;
	this.beastTransactionId = beastTransactionId;
	this.handsetIdentifier = handsetIdentifier;
	this.fourSymbolHandsetIdentifier = fourSymbolHandsetIdentifier;
	this.phoneNumber = phoneNumber;
	this.deviceDesc = deviceDesc;
	this.carrier = carrier;
	this.handsetIdentifierType = handsetIdentifierType;
	this.source = source;
	this.selectedFlag = selectedFlag;
    }

    public BeastDevice() {
	super();
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getDataSharingKey() {
	return dataSharingKey;
    }

    public void setDataSharingKey(String dataSharingKey) {
	this.dataSharingKey = dataSharingKey;
    }

    public String getHandsetIdentifier() {
	return handsetIdentifier;
    }

    public void setHandsetIdentifier(String handsetIdentifier) {
	this.handsetIdentifier = handsetIdentifier;
    }

    public String getPhoneNumber() {
	return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
	this.phoneNumber = phoneNumber;
    }

    public String getDeviceDesc() {
	return deviceDesc;
    }

    public void setDeviceDesc(String deviceDesc) {
	this.deviceDesc = deviceDesc;
    }

    public String getCarrier() {
	return carrier;
    }

    public void setCarrier(String carrier) {
	this.carrier = carrier;
    }

    public String getHandsetIdentifierType() {
	return handsetIdentifierType;
    }

    public void setHandsetIdentifierType(String handsetIdentifierType) {
	this.handsetIdentifierType = handsetIdentifierType;
    }

    public String getSource() {
	return source;
    }

    public void setSource(String source) {
	this.source = source;
    }

    public boolean isSelectedFlag() {
	return selectedFlag;
    }

    public void setSelectedFlag(boolean selectedFlag) {
	this.selectedFlag = selectedFlag;
    }

    @Override
    @XmlTransient
    public Date getCreatedOn() {
	return super.getCreatedOn();
    }

    @Override
    @XmlTransient
    public Date getModifiedOn() {
	return super.getModifiedOn();
    }

    @Override
    @XmlTransient
    public String getCreatedBy() {
	return super.getCreatedBy();
    }

    @Override
    @XmlTransient
    public String getModifiedBy() {
	return super.getModifiedBy();
    }

    public String getFourSymbolHandsetIdentifier() {
	return fourSymbolHandsetIdentifier;
    }

    public void setFourSymbolHandsetIdentifier(String fourSymbolHandsetIdentifier) {
	this.fourSymbolHandsetIdentifier = fourSymbolHandsetIdentifier;
    }

    public String getBeastTransactionId() {
	return beastTransactionId;
    }

    public void setBeastTransactionId(String beastTransactionId) {
	this.beastTransactionId = beastTransactionId;
    }

    @Override
    public String toString() {
	return "BeastDevice [id=" + id + ", dataSharingKey=" + dataSharingKey + ", beastTransactionId="
		+ beastTransactionId + ", handsetIdentifier=" + handsetIdentifier + ", fourSymbolHandsetIdentifier="
		+ fourSymbolHandsetIdentifier + ", phoneNumber=" + phoneNumber + ", deviceDesc=" + deviceDesc
		+ ", carrier=" + carrier + ", handsetIdentifierType=" + handsetIdentifierType + ", source=" + source
		+ ", selectedFlag=" + selectedFlag + "]";
    }

}
