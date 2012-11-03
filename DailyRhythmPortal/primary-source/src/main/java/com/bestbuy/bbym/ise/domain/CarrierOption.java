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
public class CarrierOption implements Serializable {

    private static final long serialVersionUID = -6021446369842977770L;

    private String optionCode;
    private String optionName;
    private String optionCategory;
    private BigDecimal optionPrice;

    public String getOptionCode() {
	return optionCode;
    }

    public void setOptionCode(String optionCode) {
	this.optionCode = optionCode;
    }

    public String getOptionName() {
	return optionName;
    }

    public void setOptionName(String optionName) {
	this.optionName = optionName;
    }

    public String getOptionCategory() {
	return optionCategory;
    }

    public void setOptionCategory(String optionCategory) {
	this.optionCategory = optionCategory;
    }

    public BigDecimal getOptionPrice() {
	return optionPrice;
    }

    public void setOptionPrice(BigDecimal optionPrice) {
	this.optionPrice = optionPrice;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

}
