/**
 * 
 */
package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @author a218635
 * 
 */
public class Product extends Item implements Serializable {

    private static final long serialVersionUID = -6834580137778721901L;

    private String serialNumber;
    private String modelNumber;

    public String getSerialNumber() {
	return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
	this.serialNumber = serialNumber;
    }

    public String getModelNumber() {
	return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
	this.modelNumber = modelNumber;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
