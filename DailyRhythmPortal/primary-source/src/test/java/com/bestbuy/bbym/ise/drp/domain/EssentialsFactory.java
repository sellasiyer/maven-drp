package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link Essentials} objects for testing.
 */
public abstract class EssentialsFactory {

    private EssentialsFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock Essentials for testing
     */
    public static Essentials getEssentials() {
	Essentials essentials = new Essentials();
	essentials.setAccessories("test_accessories");
	essentials.setBuyback("test_buyback");
	essentials.setChargers("test_chargers");
	essentials.setFinancing("Test_financing");
	essentials.setGsbtp("test_btp");
	essentials.setHandsfree("test_handsfreee");
	essentials.setMemory("test_memory");
	essentials.setShields("test_shields");
	return essentials;
    }
}
