package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;

/**
 * Factory used to create dummy and mock {@link DrpProperty} objects for
 * testing.
 */
public abstract class DrpPropertyFactory {

    private DrpPropertyFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static DrpProperty getDrpProperty(String propertyName, String propertyValue) {
	DrpProperty drpProperty = new DrpProperty(propertyName, propertyValue);
	drpProperty.setCreatedBy("a904002");
	drpProperty.setCreatedDate(new Date());
	return drpProperty;
    }
}
