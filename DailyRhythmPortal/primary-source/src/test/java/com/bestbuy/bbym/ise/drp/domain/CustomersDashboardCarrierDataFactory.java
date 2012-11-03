package com.bestbuy.bbym.ise.drp.domain;

import com.bestbuy.bbym.ise.domain.Subscription;

/**
 * Factory used to create dummy and mock {@link CustomersDashboardCarrierData} objects for testing.
 */
public class CustomersDashboardCarrierDataFactory {

    private CustomersDashboardCarrierDataFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock dashboard carrier data.
     */
    public static CustomersDashboardCarrierData getCustomersDashboardCarrierData() {

	CustomersDashboardCarrierData customersDashboardCarrierData = new CustomersDashboardCarrierData();
	customersDashboardCarrierData.setSubscriptionInfo(new Subscription());

	return customersDashboardCarrierData;
    }
}
