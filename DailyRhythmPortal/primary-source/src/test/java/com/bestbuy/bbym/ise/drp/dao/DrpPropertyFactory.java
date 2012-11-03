package com.bestbuy.bbym.ise.drp.dao;

import java.util.Date;

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;

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
    public static DrpProperty getDrpProperty() {
	DrpProperty drpProperty = new DrpProperty();
	drpProperty.setCreatedBy("a123");
	drpProperty.setCreatedDate(new Date());
	drpProperty.setModifiedBy("a777");
	drpProperty.setModifiedDate(new Date());
	drpProperty.setName("Gmail");
	drpProperty.setValue("www.gmail.com");
	drpProperty.setDescription("TestDescription");
	drpProperty.setWicketProperty(true);
	return drpProperty;
    }
}
