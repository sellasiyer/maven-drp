package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author a904002 reviewed and updated by a218635
 */
public class ProtectionPlan extends ServicePlan implements Serializable {

    private static final long serialVersionUID = 2859686295740920517L;

    private String planType;
    private List<Product> coveredProductList;

    @Override
    public String getPlanType() {
	return planType;
    }

    public void setPlanType(String planType) {
	this.planType = planType;
    }

    public void setCoveredProductList(List<Product> coveredProductList) {
	this.coveredProductList = coveredProductList;
    }

    public List<Product> getCoveredProductList() {
	return coveredProductList;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public BigDecimal getPlanPrice() {
	return null;
    }
}
