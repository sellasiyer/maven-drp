package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author a218635
 */
public class ServicePlanTransaction implements Serializable {

    private static final long serialVersionUID = -435312366786789798L;

    public static final String GSP_TRANS_TYPE_SALE = "S";

    private String storeNum;
    private String registerNum;
    private String transactionNum;
    private Date purchaseDate;
    private BigDecimal purchasePrice;
    private String gspTransType;
    private BigDecimal monthlyPayment;

    public String getStoreNum() {
	return storeNum;
    }

    public void setStoreNum(String storeNum) {
	this.storeNum = storeNum;
    }

    public String getRegisterNum() {
	return registerNum;
    }

    public void setRegisterNum(String registerNum) {
	this.registerNum = registerNum;
    }

    public String getTransactionNum() {
	return transactionNum;
    }

    public void setTransactionNum(String transactionNum) {
	this.transactionNum = transactionNum;
    }

    public Date getPurchaseDate() {
	return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
	this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPurchasePrice() {
	return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
	this.purchasePrice = purchasePrice;
    }

    public String getGspTransType() {
	return gspTransType;
    }

    public void setGspTransType(String gspTransType) {
	this.gspTransType = gspTransType;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public BigDecimal getMonthlyPayment() {
	return monthlyPayment;
    }

    public void setMonthlyPayment(BigDecimal monthlyPayment) {
	this.monthlyPayment = monthlyPayment;
    }

}
