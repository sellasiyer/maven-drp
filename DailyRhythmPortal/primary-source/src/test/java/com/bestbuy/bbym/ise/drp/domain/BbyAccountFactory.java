package com.bestbuy.bbym.ise.drp.domain;

import java.util.UUID;

/**
 * Factory used to create dummy and mock {@link BbyAccount} objects for testing.
 */
public abstract class BbyAccountFactory {

    private BbyAccountFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static BbyAccount getBbyAccount() {

	String dataSharingKey = UUID.randomUUID().toString();
	String bbymAccountAddressId = UUID.randomUUID().toString();
	Address address1 = new Address(bbymAccountAddressId, "55435", "7081 Penn Ave S", "Suite 110", "Richfield",
		"MN", "a175157", "a175157");
	BbyAccount bbymAccount1 = new BbyAccount(dataSharingKey, "Sri", "Sid", "4102221212", "aa@a.com", "1234567890",
		address1, "a175157", "a175157");

	return bbymAccount1;
    }
}
