package com.bestbuy.bbym.ise.drp.domain;

import java.math.BigDecimal;

/**
 * Factory used to create dummy and mock {@link CheckOutCheckInHistory} objects
 * for testing.
 */
public abstract class CheckOutCheckInHistoryFactory {

    private CheckOutCheckInHistoryFactory() {
	// This class is not meant to be extended or instantiated
    }

    public static CheckOutCheckInHistory createCheckOutCheckInHistory() {
	CheckOutCheckInHistory history = new CheckOutCheckInHistory();
	history.setCheckedOut(true);
	history.setCheckInCondition(LoanerPhoneCondition.DAMAGED);
	history.setCheckInConditionComment("checkInConditionComment");
	history.setCheckInDeposit(new BigDecimal("234.45"));
	history.setCheckInEmployee("checkInEmployee");
	history.setCheckInTime(new java.sql.Timestamp(new java.util.Date("1/1/2001 01:01:01 PM").getTime()));
	history.setCheckOutCondition(LoanerPhoneCondition.FAIR);
	history.setCheckOutConditionComment("checkOutConditionComment");
	history.setCheckOutDeposit(new BigDecimal("123.67"));
	history.setCheckOutEmployee("checkOutEmployee");
	history.setCheckOutTime(new java.sql.Timestamp(new java.util.Date("2/2/2002 02:02:02 PM").getTime()));
	history.setContactEmail("contactEmail");
	history.setContactPhone("contactPhone");
	history.setCreatedBy("createdBy");
	history.setCreatedOn(new java.sql.Timestamp(new java.util.Date("3/3/2003 03:03:03 PM").getTime()));
	history.setFirstName("firstName");
	history.setFulfillmentType(LoanerPhoneFulFillmentType.FACTORY_WARRANTY_REPAIR);
	history.setGspNo("" + 987654321909876543L);
	history.setLastName("lastName");
	history.setModifiedBy("modifiedBy");
	history.setModifiedOn(new java.sql.Timestamp(new java.util.Date("4/4/2004 04:04:04 PM").getTime()));
	// history.setNoOfDaysOut(5); // Not sure why this field exists
	history.setPhone(new Phone());
	history.getPhone().setId(1L);
	history.setServiceOrderNo("" + 1234567890123456789L);

	history.setRegisterId(1234);
	history.setTransactionDate(new java.sql.Timestamp(new java.util.Date("4/4/2004 04:04:04 PM").getTime()));
	history.setTransactionNumber(456);
	history
		.setNotes("this is a note it is a very long string of text that will not fit in most data fields but it fits in this one because we specified the note field should be 2000 characters long");
	history.setPosStoreId("0010");
	return history;
    }
}
