package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.math.BigInteger;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * Domain Class for ManifestLine for Shipping Portal of ACDS
 * 
 * @author a909237
 */
public class ManifestLine implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigInteger manifestID;
    private BigInteger manifestLineID;
    private String itemTag;
    private String imeiesn;
    private String deviceMake;
    private String model;
    private String returnType;
    private String deviceStatus;
    private String productDescription;
    private String sku;
    private String transferNumber;
    private boolean isShort;
    private boolean isShrinkable;

    public String getSku() {
	return sku;
    }

    public void setSku(String sku) {
	this.sku = sku;
    }

    public String getTransferNumber() {
	return transferNumber;
    }

    public void setTransferNumber(String transferNumber) {
	this.transferNumber = transferNumber;
    }

    public String getProductDescription() {
	return productDescription;
    }

    public void setProductDescription(String productDescription) {
	this.productDescription = productDescription;
    }

    public String getDeviceMake() {
	return deviceMake;
    }

    public void setDeviceMake(String deviceMake) {
	this.deviceMake = deviceMake;
    }

    public String getDeviceStatus() {
	return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
	this.deviceStatus = deviceStatus;
    }

    public String getReturnType() {
	return returnType;
    }

    public void setReturnType(String return_type) {
	this.returnType = return_type;
    }

    public BigInteger getManifestID() {
	return manifestID;
    }

    public void setManifestID(BigInteger manifestID) {
	this.manifestID = manifestID;
    }

    public BigInteger getManifestLineID() {
	return manifestLineID;
    }

    public void setManifestLineID(BigInteger manifestLineID) {
	this.manifestLineID = manifestLineID;
    }

    public String getItemTag() {
	return itemTag;
    }

    public void setItemTag(String itemTag) {
	this.itemTag = itemTag;
    }

    public String getImeiesn() {
	return imeiesn;
    }

    public void setImeiesn(String imeiesn) {
	this.imeiesn = imeiesn;
    }

    public String getMake() {
	return deviceMake;
    }

    public void setMake(String make) {
	this.deviceMake = make;
    }

    public String getModel() {
	return model;
    }

    public void setModel(String model) {
	this.model = model;
    }

    public boolean isShort() {
	return isShort;
    }

    public void setShort(boolean isShort) {
	this.isShort = isShort;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public boolean isShrinkable() {
	return isShrinkable;
    }

    public void setShrinkable(boolean isShrinkable) {
	this.isShrinkable = isShrinkable;
    }

}
