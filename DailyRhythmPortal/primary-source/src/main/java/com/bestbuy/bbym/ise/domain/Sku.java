package com.bestbuy.bbym.ise.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class Sku extends Item implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String longDescription;
    private int subClassId;
    private String Image;
    private List<Sku> skuList;

    public void setName(String name) {
	this.name = name;
    }

    public String getName() {
	return this.name;
    }

    public void setLongDescription(String longDescription) {
	this.longDescription = longDescription;
    }

    public String getLongDescription() {
	return this.longDescription;
    }

    public void setSubClassId(int subClassId) {
	this.subClassId = subClassId;
    }

    public int getSubClassId() {
	return this.subClassId;
    }

    public String getImage() {
	return Image;
    }

    public void setImage(String image) {
	Image = image;
    }

    /**
     * Gets the accessory SKUs that correspond to this device SKU.
     */
    public List<Sku> getSkuList() {
	return skuList;
    }

    /**
     * Sets the accessory SKUs that correspond to this device SKU.
     */
    public void setSkuList(List<Sku> skuList) {
	this.skuList = skuList;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public void clear() {

	this.name = "";
	this.longDescription = "";
	this.Image = "";
	this.setRegularPrice(new BigDecimal(0));
    }

}
