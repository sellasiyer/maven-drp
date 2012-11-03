package com.bestbuy.bbym.ise.drp.domain;

import java.util.UUID;

/**
 * Factory used to create dummy and mock {@link BeastDevice} objects for
 * testing.
 */
public class BeastDeviceFactory {

    private BeastDeviceFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static BeastDevice getBeastDevice() {
	BeastDevice mockDevice = new BeastDevice();

	mockDevice.setId(UUID.randomUUID().toString());
	mockDevice.setDataSharingKey("36d70c7d-fc98-4c97-af32-314370eeaggg");
	mockDevice.setBeastTransactionId("0281000067800006789");
	mockDevice.setHandsetIdentifier("1234567890");
	mockDevice.setFourSymbolHandsetIdentifier("7890");
	mockDevice.setHandsetIdentifierType("IMEI");
	mockDevice.setCarrier("AT&T");
	mockDevice.setCreatedBy("a194869");
	mockDevice.setDeviceDesc("TEST");
	mockDevice.setPhoneNumber("6122270966");

	return mockDevice;
    }
}
