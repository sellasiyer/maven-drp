package com.bestbuy.bbym.ise.drp.domain;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Factory used to create dummy and mock {@link Manifest} objects for testing.
 */
public abstract class ManifestFactory {

    private ManifestFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing.
     */
    public static Manifest getManifest() {
	Manifest manifest = new Manifest();

	List<ManifestLine> manifestLines = new ArrayList<ManifestLine>();
	manifestLines.add(ManifestLineFactory.getManifestLine());
	manifestLines.add(ManifestLineFactory.getManifestLine());

	manifest.setCreatedByUser("Mock Created By User");
	manifest.setDateCompleted(new Date());
	manifest.setDateTimeCreated(new Date());
	manifest.setDayCreated("Mock Day Created");
	manifest.setDetails("Mock Details");
	manifest.setDeviceCount(new BigInteger("12345678901234567890"));
	manifest.setManifestID(new BigInteger("12345678901234567890"));
	manifest.setManifestLine(manifestLines);
	manifest.setStatus("Mock Status");
	manifest.setTrackingIdentifier("Mock Tracking Identifer");

	return manifest;
    }
}
