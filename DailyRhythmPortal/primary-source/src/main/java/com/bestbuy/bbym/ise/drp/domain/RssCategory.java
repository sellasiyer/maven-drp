package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author a904002
 */
public class RssCategory implements Serializable {

    private static final long serialVersionUID = 718643016132686928L;
    // Mapped to rss item -> categoryID
    private String title;
    // List of items contains the full text for each news per category
    private List<RssItem> rssItemList;
    // Mapped to rss item -->last Updated Date
    private String lastUpdatedDate;
    // Short description only used in Ticker feed
    private String shortDescription;

    public String getShortDescription() {
	return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
	this.shortDescription = shortDescription;
    }

    /**
     * Gets the last updated date.
     * 
     * @return the last updated date in the format <i>mm/dd/yyyy</i>
     */
    public String getLastUpdatedDate() {
	return lastUpdatedDate;
    }

    /**
     * Sets the last updated date.
     * 
     * @param lastUpdatedDate
     *            the last updated date in the format <i>mm/dd/yyyy</i>
     */
    public void setLastUpdatedDate(String lastUpdatedDate) {
	this.lastUpdatedDate = lastUpdatedDate;
    }

    /**
     * Gets the RSS items.
     * 
     * @return the RSS items; {@code null} if none have been set
     */
    public List<RssItem> getRssItemList() {
	return rssItemList;
    }

    public void setRssItemList(List<RssItem> rssItemList) {
	this.rssItemList = rssItemList;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }
}
