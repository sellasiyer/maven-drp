package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

/**
 *
 * @author a904002
 */
public class RssItem implements Serializable {

    private static final long serialVersionUID = 718643016132686928L;
    //Mapped to rss item -->Full text
    private String fullText;

    public RssItem(String fullText) {
	this.fullText = fullText;
    }

    public String getFullText() {
	return fullText;
    }

    public void setFullText(String fullText) {
	this.fullText = fullText;
    }
}
