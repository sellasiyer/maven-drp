package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class RelatedPromotion implements Serializable {

    public enum SearchParameterType {
	ACTIVATION_PHONE_NUMBER, EMAIL_ADDRESS, PROMOTION_CODE, DEVICE_SKU, TRADEIN_SKU, ORDER_TYPE;
    };

    public enum PromotionAvailabilityType {

	AVAILABLE("Available"), UNAVAILABLE("Unavailable");

	private final String label;

	private PromotionAvailabilityType(String label) {
	    this.label = label;
	}

	@Override
	public String toString() {
	    return label;
	}
    }

    private static final long serialVersionUID = 1L;
    private Promotion promotion;
    private boolean promotionAvailable;
    private SearchParameterType searchParameterType;
    private String reason;
    private PromotionAvailabilityType promotionAvailabilityType;

    public SearchParameterType getSearchParameterType() {
	return searchParameterType;
    }

    public void setSearchParameterType(SearchParameterType searchParameterType) {
	this.searchParameterType = searchParameterType;
    }

    public Promotion getPromotion() {
	return promotion;
    }

    public void setPromotion(Promotion promotion) {
	this.promotion = promotion;
    }

    public boolean isPromotionAvailable() {
	return promotionAvailable;
    }

    public void setPromotionAvailable(boolean promotionAvailable) {
	this.promotionAvailable = promotionAvailable;
    }

    public String getReason() {
	return reason;
    }

    public void setReason(String reason) {
	this.reason = reason;
    }

    public PromotionAvailabilityType getPromotionAvailabilityType() {
	return promotionAvailabilityType;
    }

    public void setPromotionAvailabilityType(PromotionAvailabilityType promotionAvailabilityType) {
	this.promotionAvailabilityType = promotionAvailabilityType;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
