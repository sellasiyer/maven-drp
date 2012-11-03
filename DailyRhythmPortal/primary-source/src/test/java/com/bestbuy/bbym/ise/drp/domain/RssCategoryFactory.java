package com.bestbuy.bbym.ise.drp.domain;

/**
 * Factory used to create dummy and mock {@link RssCategory} objects for
 * testing.
 */
public abstract class RssCategoryFactory {

    private RssCategoryFactory() {
	// This class is not meant to be extended or instantiated
    }

    /**
     * Creates a {@link RssCategory} with a title and last updated date.
     */
    public static final RssCategory create() {
	RssCategory resCategory = new RssCategory();
	resCategory.setTitle("Displayed category");
	resCategory.setLastUpdatedDate("date");
	return resCategory;
    }

    /**
     * Creates a {@link RssCategory} with a {@code null} title.
     */
    public static final RssCategory createWithNullTitle() {
	RssCategory resCategory = new RssCategory();
	resCategory.setTitle(null);
	return resCategory;
    }
}
