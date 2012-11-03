package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link RecSheetSummary} objects for
 * testing.
 */
public abstract class RecSheetSummaryFactory {

    private RecSheetSummaryFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static RecSheetSummary getRecSheetSummary(String dataSharingKey) {

	RecSheetSummary recSheetSummary1 = new RecSheetSummary("Plan Info", "Device Info", "GSP Plan Info",
		"BuyBack Info", dataSharingKey, "a175157", "a175157");

	return recSheetSummary1;
    }
}
