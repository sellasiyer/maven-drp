package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link DataUsageFactors} objects for
 * testing.
 */
public abstract class DataUsageFactorsFactory {

    private DataUsageFactorsFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock data used for testing
     */
    public static DataUsageFactors getDataUsageFactors() {

	DataUsageFactors dataUsageFactors = new DataUsageFactors();

	dataUsageFactors.setAudioFactorInGB(1);
	dataUsageFactors.setEmailFactorInGB(2);
	dataUsageFactors.setHighResVideoFactorInGB(3);
	dataUsageFactors.setLoResVideoFactorInGB(4);
	dataUsageFactors.setOnlineGamingFactorInGB(5);
	dataUsageFactors.setPhotoFactorInGB(6);
	dataUsageFactors.setVoipAndVideoFactorInGB(7);
	dataUsageFactors.setVoipFactorInGB(8);
	dataUsageFactors.setWebPageFactorInGB(9);

	return dataUsageFactors;
    }
}
