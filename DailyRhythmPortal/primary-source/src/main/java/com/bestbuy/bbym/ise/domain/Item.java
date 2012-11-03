package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author a904002 reviewed and updated by a218635
 */
public class Item extends BaseObject implements Serializable {

    private static final long serialVersionUID = -8348580609095904188L;

    public static final String TRANSACTION_KEY_TYPE_4_PART = "FourPartKey";
    public static final String TRANSACTION_KEY_TYPE_ORDER = "Order";
    public static final String TRANSACTION_KEY_TYPE_FULFILLMENT = "OrderFulfillment";

    private Long id;
    private String sku;
    private String description;
    private String transactionId;
    private String transactionType;
    private BigDecimal purchasePrice;
    private BigDecimal retailPrice;
    private BigDecimal regularPrice;
    private Integer departmentNum;
    private Date purchaseDate;
    private String transactionSourceSystem;
    private String transactionKeyType;
    private String sourceTransactionKey;
    private String upc;
    private String manufacturer;

    public void setId(Long id) {
	this.id = id;
    }

    public Long getId() {
	return id;
    }

    public void setSku(String sku) {
	this.sku = sku;
    }

    public String getSku() {
	return this.sku;
    }

    public void setDescription(String description) {
	this.description = description;
    }

    public String getDescription() {
	return this.description;
    }

    public void setTransactionId(String transactionId) {
	this.transactionId = transactionId;
    }

    public String getTransactionId() {
	return this.transactionId;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
	this.purchasePrice = purchasePrice;
    }

    public BigDecimal getPurchasePrice() {
	return this.purchasePrice;
    }

    public String getTransactionType() {
	return transactionType;
    }

    public void setTransactionType(String transactionType) {
	this.transactionType = transactionType;
    }

    public BigDecimal getRetailPrice() {
	return retailPrice;
    }

    public void setRetailPrice(BigDecimal retailPrice) {
	this.retailPrice = retailPrice;
    }

    public BigDecimal getRegularPrice() {
	return regularPrice;
    }

    public void setRegularPrice(BigDecimal regularPrice) {
	this.regularPrice = regularPrice;
    }

    public Integer getDepartmentNum() {
	return departmentNum;
    }

    public void setDepartmentNum(Integer departmentNum) {
	this.departmentNum = departmentNum;
    }

    public Date getPurchaseDate() {
	return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
	this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
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

    public String getUpc() {
	return upc;
    }

    public void setUpc(String upc) {
	this.upc = upc;
    }

    public String getManufacturer() {
	return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
	this.manufacturer = manufacturer;
    }

}
