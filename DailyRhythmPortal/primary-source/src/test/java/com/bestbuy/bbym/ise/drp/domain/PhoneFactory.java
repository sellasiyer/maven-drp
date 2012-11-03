package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link Phone} objects for testing.
 */
public abstract class PhoneFactory {

    private PhoneFactory() {
	// This class is not meant to be extended or instantiated
    }

    public static Phone createPhone() {
	Phone phone = new Phone();
	phone.setCarrier(new Carrier());
	phone.getCarrier().setId(1L);
	phone.setOperatingSystem(new OperatingSystem());
	phone.getOperatingSystem().setId(2L);
	phone.setStoreId("3");
	phone.setCreatedBy("createdBy");
	// The H2 date type appears to have no time component, unlike the Oracle
	// date type.
	phone.setCreatedOn(new java.sql.Date(new java.util.Date("1/1/2012").getTime()));
	phone.setSerialNumber("serialNumber");
	phone.setActive(true);
	phone.setCondition(LoanerPhoneCondition.GOOD);
	phone.setConditionComment("conditionComment");
	phone.setDeletedReason(LoanerPhoneDeletedReason.DAMAGED);
	phone.setLastActionUserId("lastActionUserId");
	phone.setMake("make");
	phone.setModelNumber("modelNumber");
	// phone.setModifiedBy("modifiedBy");
	// The H2 date type appears to have no time component, unlike the Oracle
	// date type.
	// phone.setModifiedOn(new java.sql.Date(new
	// java.util.Date("1/1/2012").getTime()));
	phone.setSku("sku");

	// There are a bunch of properties of the Phone class that do not map to
	// columns on the table loanr_ph.
	// I'm ignoring them to make the tests simpler.
	// phone.setOs("os");
	// phone.setPurchaseDate(new java.util.Date(1000));
	// phone.setPurchasePrice(BigDecimal.valueOf(345.34));
	// phone.setRetailPrice(BigDecimal.valueOf(123.56));
	// phone.setDepartmentNum(234234);
	// phone.setDescription("description");
	// phone.setBlackTieProtection(true);
	// phone.getOperatingSystem().setOs("BlackBerry");
	// phone.getCarrier().setCarrier("AT&T");
	// phone.getCarrier().setCarrierLoanerSku("1111");
	// phone.setTradeInValue(BigDecimal.valueOf(345.21));
	// phone.setTransactionId("transactionId");
	// phone.setTransactionType("transactionType");

	return phone;
    }

}
