package com.bestbuy.bbym.ise.drp.beast.tradein.mapper;

import java.io.Serializable;

/**
 * Represents <BuyPriceQuestion> node in XML.
 * 
 * @author a915722
 */
public class BuyPriceQuestionOption implements Serializable {

    private final String value;
    private final String description;
    private final String adjustmentPrice;

    /**
     * Constructs a new instance.
     */
    public BuyPriceQuestionOption(final String value, final String description, final String adjustmentPrice) {
	this.value = value;
	this.description = description;
	this.adjustmentPrice = adjustmentPrice;
    }

    /**
     * Returns option value.
     */
    public String getValue() {
	return value;
    }

    /**
     * Returns option description.
     */
    public String getDescription() {
	return description;
    }

    /**
     * Returns adjustment price.
     */
    public String getAdjustmentPrice() {
	return adjustmentPrice;
    }
}
