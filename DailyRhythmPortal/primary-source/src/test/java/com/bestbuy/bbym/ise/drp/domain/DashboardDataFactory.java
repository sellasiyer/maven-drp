package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link DashboardData} objects for
 * testing.
 */
public class DashboardDataFactory {

    private DashboardDataFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock dashboard data.
     */
    public static DashboardData getDashboardData() {

	DashboardData dashboardData = new DashboardData();
	dashboardData.setCarrierData(CustomersDashboardCarrierDataFactory.getCustomersDashboardCarrierData());

	return dashboardData;
    }
}
