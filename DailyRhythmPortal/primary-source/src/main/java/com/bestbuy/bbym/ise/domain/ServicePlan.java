package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author a218635
 */
public abstract class ServicePlan extends Item implements Serializable {

    public static final String PLAN_STATUS_ACTIVE = "ACTIVE";
    public static final String PLAN_STATUS_CANCELLED = "CANCELLED";
    public static final String PLAN_STATUS_INACTIVE = "INACTIVE";
    public static final String PLAN_STATUS_MARKEDFORCANCEL = "MARKEDFORCANCEL";
    public static final String PLAN_STATUS_ON_HOLD = "ONHOLD";
    public static final String PLAN_STATUS_UNKNOWN = "UNKNOWN";

    public static final String PLAN_TYPE_BUY_BACK = "Buy Back";
    public static final String PLAN_TYPE_PSP = "PSP";
    public static final String PLAN_TYPE_PRP = "PRP";

    private static final long serialVersionUID = 5495051876205683383L;

    private String planNumber;
    private String planStatus;
    private Date planEffectiveDate;
    private Date planExpiryDate;
    private String planOwnerName;
    private List<ServicePlanTransaction> servicePlanTransactions;

    public abstract String getPlanType();

    public abstract BigDecimal getPlanPrice();

    public String getPlanNumber() {
	return planNumber;
    }

    public void setPlanNumber(String planNumber) {
	this.planNumber = planNumber;
    }

    public String getPlanStatus() {
	return planStatus;
    }

    public void setPlanStatus(String planStatus) {
	this.planStatus = planStatus;
    }

    public Date getPlanEffectiveDate() {
	return planEffectiveDate;
    }

    public void setPlanEffectiveDate(Date planEffectiveDate) {
	this.planEffectiveDate = planEffectiveDate;
    }

    public Date getPlanExpiryDate() {
	return planExpiryDate;
    }

    public void setPlanExpiryDate(Date planExpiryDate) {
	this.planExpiryDate = planExpiryDate;
    }

    public String getPlanOwnerName() {
	return planOwnerName;
    }

    public void setPlanOwnerName(String planOwnerName) {
	this.planOwnerName = planOwnerName;
    }

    public List<ServicePlanTransaction> getServicePlanTransactions() {
	return servicePlanTransactions;
    }

    public void setServicePlanTransactions(List<ServicePlanTransaction> servicePlanTransactions) {
	this.servicePlanTransactions = servicePlanTransactions;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
