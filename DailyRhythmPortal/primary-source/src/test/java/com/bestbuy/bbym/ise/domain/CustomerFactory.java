package com.bestbuy.bbym.ise.domain;

import java.util.UUID;

/**
 * Factory used to create dummy and mock {@link Customer} objects for testing.
 */
public class CustomerFactory {

    private CustomerFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock customer.
     */
    public static Customer getMockCustomer() {
	String dataSharingKey = UUID.randomUUID().toString();

	Customer customer = new Customer();
	customer.setId(1L);
	customer.setDataSharingKey(dataSharingKey);
	customer.setFirstName("Sri");
	customer.setLastName("Sid");
	customer.setContactPhone("4102221212");

	Address address1 = new Address();
	address1.setZipcode("55435");
	customer.setAddress(address1);

	return customer;
    }

    /**
     * Builds another mock customer.
     */
    public static Customer getAnotherMockCustomer() {
	String dataSharingKey = UUID.randomUUID().toString();

	Customer customer = new Customer();
	customer.setId(2L);
	customer.setDataSharingKey(dataSharingKey);
	customer.setFirstName("Fred");
	customer.setLastName("Flintstone");
	customer.setContactPhone("5552223333");

	Address address1 = new Address();
	address1.setZipcode("70210");
	customer.setAddress(address1);

	return customer;
    }
}
