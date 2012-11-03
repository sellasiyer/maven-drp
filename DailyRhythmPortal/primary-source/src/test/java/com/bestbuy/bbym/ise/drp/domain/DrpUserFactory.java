package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link DrpUser} objects for testing.
 */
public abstract class DrpUserFactory {

    private DrpUserFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock user.
     */
    public static DrpUser getDrpUser() {

	DrpUser drpUser = new DrpUser();
	drpUser.setUserId("a175157");
	drpUser.setStoreId("0699");

	return drpUser;
    }

    /**
     * Builds mock BEAST user for testing.
     */
    public static DrpUser getBeastUser() {
	DrpUser drpUser = new DrpUser();
	drpUser.setBeast(true);
	return drpUser;
    }
}
