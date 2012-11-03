/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.domain.scoreboard;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

/**
 * Domain class for storing score board item values.
 * 
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 25
 */
public class ScoreboardDataItem extends BaseObject {

    private static final long serialVersionUID = 7101609519797326772L;

    private Long id;
    private String groupName;
    private String name;
    private Integer salesQuantity;
    private BigDecimal salesTotal;
    private Integer returnQuantity;
    private BigDecimal returnTotal;
    private Integer editQuantity;
    private BigDecimal editUnitPrice;
    private Long ntlMarginValueId;
    private BigDecimal ntlMarginValue;
    private boolean isUnitPriceRequired;
    private Date transactionDate;
    private boolean isDeleted;

    public boolean isDeleted() {
	return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
	this.isDeleted = isDeleted;
    }

    public String getGroupName() {
	return groupName;
    }

    public void setGroupName(String groupName) {
	this.groupName = groupName;
    }

    public boolean isUnitPriceRequired() {
	return isUnitPriceRequired;
    }

    public void setUnitPriceRequired(boolean isUnitPriceRequired) {
	this.isUnitPriceRequired = isUnitPriceRequired;
    }

    public BigDecimal getSalesTotal() {
	return salesTotal;
    }

    public void setSalesTotal(BigDecimal salesTotal) {
	this.salesTotal = salesTotal;
    }

    public BigDecimal getReturnTotal() {
	return returnTotal;
    }

    public void setReturnTotal(BigDecimal returnTotal) {
	this.returnTotal = returnTotal;
    }

    public Integer getEditQuantity() {
	return editQuantity;
    }

    public void setEditQuantity(Integer editQuantity) {
	this.editQuantity = editQuantity;
    }

    public BigDecimal getEditUnitPrice() {
	return editUnitPrice;
    }

    public void setEditUnitPrice(BigDecimal editUnitPrice) {
	this.editUnitPrice = editUnitPrice;
    }

    public ScoreboardDataItem() {
	super();
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Integer getSalesQuantity() {
	return salesQuantity;
    }

    public void setSalesQuantity(Integer salesQuantity) {
	this.salesQuantity = salesQuantity;
    }

    public Integer getReturnQuantity() {
	return returnQuantity;
    }

    public void setReturnQuantity(Integer returnQuantity) {
	this.returnQuantity = returnQuantity;
    }

    public Long getNtlMarginValueId() {
	return ntlMarginValueId;
    }

    public void setNtlMarginValueId(Long ntlMarginValueId) {
	this.ntlMarginValueId = ntlMarginValueId;
    }

    public BigDecimal getNtlMarginValue() {
	return ntlMarginValue;
    }

    public void setNtlMarginValue(BigDecimal ntlMarginValue) {
	this.ntlMarginValue = ntlMarginValue;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public Date getTransactionDate() {
	return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
	this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
