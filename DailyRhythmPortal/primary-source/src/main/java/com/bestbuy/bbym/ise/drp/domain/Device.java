package com.bestbuy.bbym.ise.drp.domain;

public class Device {

    protected long skuID;
    protected String skuDescription;

    /**
     * Gets the value of the skuID property.
     * 
     */
    public long getSkuID() {
	return skuID;
    }

    /**
     * Sets the value of the skuID property.
     * 
     */
    public void setSkuID(long value) {
	this.skuID = value;
    }

    /**
     * Gets the value of the skuDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSkuDescription() {
	return skuDescription;
    }

    /**
     * Sets the value of the skuDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSkuDescription(String value) {
	this.skuDescription = value;
    }

}
