package com.bestbuy.bbym.ise.drp.domain;

import java.util.Date;
import java.util.UUID;

/**
 * Factory used to create dummy and mock {@link Customer} objects for testing.
 */
public abstract class CustomerFactory {

    private CustomerFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static Customer getCustomer() {

	String dataSharingKey = UUID.randomUUID().toString();
	String bbymAccountAddressId = UUID.randomUUID().toString();
	Address address1 = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Sri", "Sid", "4102221212", "aa@a.com", "1234567890",
		address1, "a175157", "a175157");

	String carrierAccountAddressId = UUID.randomUUID().toString();
	Address address2 = new Address(carrierAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	CarrierAccount carrierAccount1 = new CarrierAccount("1010101", "55435", "Sri", "Sid", "4102221212",
		dataSharingKey, address2, "aa@a.com", "a175157", "a175157");

	RecSheetSummary recSheetSummary1 = new RecSheetSummary("Plan Info", "Device Info", "GSP Plan Info",
		"BuyBack Info", dataSharingKey, "a175157", "a175157");

	Customer customer1 = new Customer(dataSharingKey, "0699", false, "BEAST", "a175157", "a175157", new Date(),
		recSheetSummary1, bbymAccount1, carrierAccount1, "390710101010010");

	return customer1;
    }
}
