package com.bestbuy.bbym.ise.drp.domain;

import java.math.BigInteger;
import java.util.Date;

/**
 * Factory used to create dummy and mock {@link ManifestSearchCriteria} objects
 * for testing.
 */
public abstract class ManifestSearchCriteriaFactory {

    private ManifestSearchCriteriaFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing.
     */
    public static ManifestSearchCriteria getManifestSearchCriteria() {
	ManifestSearchCriteria manifestSearchCriteria = new ManifestSearchCriteria();

	manifestSearchCriteria.setSearchType(null);
	manifestSearchCriteria.setStoreID(new BigInteger("12345678901234567890"));
	manifestSearchCriteria.setManifestStatus("Mock Manifest Status");
	manifestSearchCriteria.setMostRecentNumber(6990);
	manifestSearchCriteria.setStartDate(new Date());
	manifestSearchCriteria.setEndDate(new Date());
	manifestSearchCriteria.setImeiesn("Mock Imeiesn");
	manifestSearchCriteria.setItemTag("Mock Item Tag");
	manifestSearchCriteria.setTrackingIdentifier("Mock Tracking Identifier");

	return manifestSearchCriteria;
    }
}
