package com.bestbuy.bbym.ise.drp.domain;

import com.bestbuy.bbym.ise.domain.Line;

/**
 * Factory used to create dummy and mock {@link EntitlementLookup} objects for
 * testing.
 */
public class EntitlementLookupFactory {

    private EntitlementLookupFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock user.
     */
    public static EntitlementLookup getEntitlementLookup() {

	EntitlementLookup entitlementLookup = new EntitlementLookup();
	FourPartKey fourpartkey = new FourPartKey();
	entitlementLookup.setFourpartkey(fourpartkey);

	Line line = new Line();
	entitlementLookup.setLine(line);

	return entitlementLookup;
    }

}
