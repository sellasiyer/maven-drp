package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Promotion implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long promotionID;
    private String promotionCode;
    private String promotionDescription;
    private Date promotionBeginDate;
    private Date promotionEndDate;
    private long promotionSKU;
    private String promotionValue;
    private String promotionApprover;
    private String promotionAffiliate;
    private String promotionCostCenter;
    private long promotionCirculationQuantity;
    private long promotionExpense;
    private String promotionDeviceLogicRule;
    private String promotionCardinality;
    private boolean promotionStackable;
    private String fulfillmentType;
    private String promotionContentFilename;
    private String promotionContentType;
    private String promotionContentURL;
    private List<Device> devices;
    private List<String> validOrderTypes;

    public Long getPromotionID() {
	return promotionID;
    }

    public void setPromotionID(Long promotionID) {
	this.promotionID = promotionID;
    }

    public String getPromotionCode() {
	return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
	this.promotionCode = promotionCode;
    }

    public String getPromotionDescription() {
	return promotionDescription;
    }

    public void setPromotionDescription(String promotionDescription) {
	this.promotionDescription = promotionDescription;
    }

    public Date getPromotionBeginDate() {
	return promotionBeginDate;
    }

    public void setPromotionBeginDate(Date promotionBeginDate) {
	this.promotionBeginDate = promotionBeginDate;
    }

    public Date getPromotionEndDate() {
	return promotionEndDate;
    }

    public void setPromotionEndDate(Date promotionEndDate) {
	this.promotionEndDate = promotionEndDate;
    }

    public long getPromotionSKU() {
	return promotionSKU;
    }

    public void setPromotionSKU(long promotionSKU) {
	this.promotionSKU = promotionSKU;
    }

    public String getPromotionValue() {
	return promotionValue;
    }

    public void setPromotionValue(String promotionValue) {
	this.promotionValue = promotionValue;
    }

    public String getPromotionApprover() {
	return promotionApprover;
    }

    public void setPromotionApprover(String promotionApprover) {
	this.promotionApprover = promotionApprover;
    }

    public String getPromotionAffiliate() {
	return promotionAffiliate;
    }

    public void setPromotionAffiliate(String promotionAffiliate) {
	this.promotionAffiliate = promotionAffiliate;
    }

    public String getPromotionCostCenter() {
	return promotionCostCenter;
    }

    public void setPromotionCostCenter(String promotionCostCenter) {
	this.promotionCostCenter = promotionCostCenter;
    }

    public long getPromotionCirculationQuantity() {
	return promotionCirculationQuantity;
    }

    public void setPromotionCirculationQuantity(long promotionCirculationQuantity) {
	this.promotionCirculationQuantity = promotionCirculationQuantity;
    }

    public long getPromotionExpense() {
	return promotionExpense;
    }

    public void setPromotionExpense(long promotionExpense) {
	this.promotionExpense = promotionExpense;
    }

    public String getPromotionDeviceLogicRule() {
	return promotionDeviceLogicRule;
    }

    public void setPromotionDeviceLogicRule(String promotionDeviceLogicRule) {
	this.promotionDeviceLogicRule = promotionDeviceLogicRule;
    }

    public String getPromotionCardinality() {
	return promotionCardinality;
    }

    public void setPromotionCardinality(String promotionCardinality) {
	this.promotionCardinality = promotionCardinality;
    }

    public boolean isPromotionStackable() {
	return promotionStackable;
    }

    public void setPromotionStackable(boolean promotionStackable) {
	this.promotionStackable = promotionStackable;
    }

    public String getFulfillmentType() {
	return fulfillmentType;
    }

    public void setFulfillmentType(String fulfillmentType) {
	this.fulfillmentType = fulfillmentType;
    }

    public String getPromotionContentFilename() {
	return promotionContentFilename;
    }

    public void setPromotionContentFilename(String promotionContentFilename) {
	this.promotionContentFilename = promotionContentFilename;
    }

    public String getPromotionContentType() {
	return promotionContentType;
    }

    public void setPromotionContentType(String promotionContentType) {
	this.promotionContentType = promotionContentType;
    }

    public String getPromotionContentURL() {
	return promotionContentURL;
    }

    public void setPromotionContentURL(String promotionContentURL) {
	this.promotionContentURL = promotionContentURL;
    }

    public List<Device> getDevices() {
	return devices;
    }

    public void setDevices(List<Device> devices) {
	this.devices = devices;
    }

    public List<String> getValidOrderTypes() {
	return validOrderTypes;
    }

    public void setValidOrderTypes(List<String> validOrderTypes) {
	this.validOrderTypes = validOrderTypes;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
