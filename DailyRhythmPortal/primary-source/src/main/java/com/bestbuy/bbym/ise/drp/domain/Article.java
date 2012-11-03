package com.bestbuy.bbym.ise.drp.domain;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

/**
 * An article retrieved from the Geek Squad armory used to troubleshoot phone
 * issues.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    private String title;
    private String link;
    private String articleAbstract;

    public String getTitle() {
	return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
	this.title = title;
    }

    public String getLink() {
	return link;
    }

    @JsonProperty("url")
    public void setLink(String link) {
	this.link = link;
    }

    public String getArticleAbstract() {
	return articleAbstract;
    }

    @JsonProperty("abstract")
    public void setArticleAbstract(String articleAbstract) {
	this.articleAbstract = articleAbstract;
    }

    @Override
    public String toString() {
	return "Article [title=" + title + ", link=" + link + ", articleAbstract=" + articleAbstract + "]";
    }
}
