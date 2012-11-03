package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link Hotlink} objects for testing.
 */
public abstract class HotlinkFactory {

    private HotlinkFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Builds mock Hotlink for testing.
     */
    public static Hotlink getHotLink() {
	Hotlink hotlink = new Hotlink();
	hotlink.setId(new Long(1L));
	hotlink.setUrlAlias("Google");
	hotlink.setUrl("www.google.com");
	hotlink.setUserId("eric");
	hotlink.setDisplayOrder(2);
	return hotlink;
    }
}
