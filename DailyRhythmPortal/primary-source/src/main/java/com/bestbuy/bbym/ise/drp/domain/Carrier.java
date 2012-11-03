package com.bestbuy.bbym.ise.drp.domain;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.domain.BaseObject;

public class Carrier extends BaseObject {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String carrier;
    private String carrierLoanerSku;

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getCarrier() {
	return carrier;
    }

    public void setCarrier(String carrier) {
	this.carrier = carrier;
    }

    public String getCarrierLoanerSku() {
	return carrierLoanerSku;
    }

    public void setCarrierLoanerSku(String carrierLoanerSku) {
	this.carrierLoanerSku = carrierLoanerSku;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
