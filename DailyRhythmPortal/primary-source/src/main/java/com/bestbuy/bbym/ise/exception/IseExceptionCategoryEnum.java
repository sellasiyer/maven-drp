package com.bestbuy.bbym.ise.exception;

/**
 * Category enum for holding different types of category.
 * 
 * @author a904002
 */
public enum IseExceptionCategoryEnum {

    /**
     * Internal Business Exception category
     */
    Internal("IBE"),
    /**
     * External Business Exception category
     */
    External("XBE"),
    /**
     * Data access layer Exception category
     */
    Dao("DAO"),
    /**
     * Service layer Exception category
     */
    Service("SRV"),
    /**
     * Unknown Exception category
     */
    Unknown("UNK");

    private String category;

    /**
     * Gets the category.
     */
    public String getCategory() {
	return category;
    }

    /**
     * Sets the category.
     */
    public void setCategory(String category) {
	this.category = category;
    }

    private IseExceptionCategoryEnum(String category) {
	this.category = category;
    }

}
