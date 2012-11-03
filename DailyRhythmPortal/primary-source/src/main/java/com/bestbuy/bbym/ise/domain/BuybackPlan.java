package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * 
 * @author a904002 reviewed and updated by a218635
 */
public class BuybackPlan extends ServicePlan implements Serializable {

    private static final long serialVersionUID = 5452389819324801771L;

    private BigDecimal buyBackPrice;
    private Product product;

    public void setBuyBackPrice(BigDecimal buyBackPrice) {
	this.buyBackPrice = buyBackPrice;
    }

    public BigDecimal getBuyBackPrice() {
	return this.buyBackPrice;
    }

    public void setProduct(Product product) {
	this.product = product;
    }

    public Product getProduct() {
	return product;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    @Override
    public String getPlanType() {
	return "Buy Back";
    }

    @Override
    public BigDecimal getPlanPrice() {
	return getBuyBackPrice();
    }
}
