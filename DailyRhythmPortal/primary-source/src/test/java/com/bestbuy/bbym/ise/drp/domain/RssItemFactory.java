package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link RssItem} objects for testing.
 */
public abstract class RssItemFactory {

    private RssItemFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Creates a {@link RssItem} with the given text.
     */
    public static final RssItem create(String fullText) {
	RssItem rssItem = new RssItem(fullText);
	return rssItem;
    }

}
