/**
 * 
 */
package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author a218635
 * 
 */
public class CarrierPlan implements Serializable {

    private static final long serialVersionUID = -6021446369842966620L;

    private String planCode;
    private String planName;
    private String planType;
    private BigDecimal planMRC;
    private BigDecimal accountMRC;

    public String getPlanCode() {
	return planCode;
    }

    public void setPlanCode(String planCode) {
	this.planCode = planCode;
    }

    public String getPlanName() {
	return planName;
    }

    public void setPlanName(String planName) {
	this.planName = planName;
    }

    public String getPlanType() {
	return planType;
    }

    public void setPlanType(String planType) {
	this.planType = planType;
    }

    public BigDecimal getPlanMRC() {
	return planMRC;
    }

    public void setPlanMRC(BigDecimal planMRC) {
	this.planMRC = planMRC;
    }

    public BigDecimal getAccountMRC() {
	return accountMRC;
    }

    public void setAccountMRC(BigDecimal accountMRC) {
	this.accountMRC = accountMRC;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public static String returnPlanTypeDisplayValue(String planType) {
	if ("ACCOUNT_LEVEL_D1".equals(planType)){
	    return "Share";
	}else if ("ACCOUNT_LEVEL_VD".equals(planType)){
	    return "Share";
	}
	return planType;
    }

}
