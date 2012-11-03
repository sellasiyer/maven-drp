package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link RecSheetCountByDay} objects for
 * testing.
 */
public abstract class RecSheetCountByDayFactory {

    private RecSheetCountByDayFactory() {
	// This class is not meant to be extended or instantiated
    }

    public static RecSheetCountByDay getRecSheetCountByDay(String aId, String firstName, String lastName) {
	RecSheetCountByDay result = new RecSheetCountByDay();

	result.setFirstName(firstName);
	result.setLastName(lastName);
	result.setChangeDate("20120202");
	result.setStoreId("0699");
	result.setCountByDay("3");
	result.setAid(aId);

	return result;
    }
}
