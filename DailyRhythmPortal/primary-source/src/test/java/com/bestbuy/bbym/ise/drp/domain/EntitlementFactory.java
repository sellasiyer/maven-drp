package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link Entitlement} objects for
 * testing.
 */
public class EntitlementFactory {

    private EntitlementFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static Entitlement getEntitlement() {

	Entitlement entitlement = new Entitlement();

	return entitlement;
    }
}
