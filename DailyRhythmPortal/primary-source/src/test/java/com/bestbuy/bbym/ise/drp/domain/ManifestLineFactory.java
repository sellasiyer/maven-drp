package com.bestbuy.bbym.ise.drp.domain;

import java.math.BigInteger;

/**
 * Factory used to create dummy and mock {@link ManifestLine} objects for
 * testing.
 */
public abstract class ManifestLineFactory {

    private ManifestLineFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing.
     */
    public static ManifestLine getManifestLine() {
	ManifestLine manifestLine = new ManifestLine();

	manifestLine.setManifestID(new BigInteger("1234567890123456890"));
	manifestLine.setManifestLineID(new BigInteger("12345678901234567890"));
	manifestLine.setItemTag("Mock Item Tag");
	manifestLine.setImeiesn("Mock imeiesn");
	manifestLine.setDeviceMake("Mock Device Make");
	manifestLine.setModel("Mock Model");
	manifestLine.setReturnType("Mock Return Type");
	manifestLine.setDeviceStatus("Mock Device Status");
	manifestLine.setProductDescription("Mock Product Description");
	manifestLine.setShort(true);

	return manifestLine;
    }
}
