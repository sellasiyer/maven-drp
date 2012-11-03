package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Device extends Product implements Serializable {

    private static final long serialVersionUID = -2394959318339252777L;

    private String os;
    private BigDecimal tradeInValue;
    private ProtectionPlan protectionPlan;
    private BuybackPlan buyBackPlan;
    // Attribute derived from protectionPlan
    private Boolean blackTieProtection;

    public void setOs(String os) {
	this.os = os;
    }

    public String getOs() {
	return this.os;
    }

    public void setTradeInValue(BigDecimal tradeInValue) {
	this.tradeInValue = tradeInValue;
    }

    public BigDecimal getTradeInValue() {
	return this.tradeInValue;
    }

    public ProtectionPlan getProtectionPlan() {
	return protectionPlan;
    }

    public void setProtectionPlan(ProtectionPlan protectionPlan) {
	if (protectionPlan == null && this.protectionPlan != null){
	    blackTieProtection = Boolean.FALSE;
	}else if (protectionPlan != null){
	    blackTieProtection = Boolean.TRUE;
	}
	this.protectionPlan = protectionPlan;

    }

    public void setBuyBackPlan(BuybackPlan buyBackPlan) {
	this.buyBackPlan = buyBackPlan;
    }

    public BuybackPlan getBuyBackPlan() {
	return buyBackPlan;
    }

    public void setBlackTieProtection(Boolean blackTieProtection) {
	this.blackTieProtection = blackTieProtection;
    }

    public Boolean getBlackTieProtection() {
	return blackTieProtection;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
