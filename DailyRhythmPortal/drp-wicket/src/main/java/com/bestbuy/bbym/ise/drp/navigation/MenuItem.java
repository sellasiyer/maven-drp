package com.bestbuy.bbym.ise.drp.navigation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MenuItem implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String url;
    private String page;
    private String itemListFactory;
    private String preImage;
    private String target;
    private List<MenuItem> subItems;

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getUrl() {
	return url;
    }

    public void setUrl(String url) {
	this.url = url;
    }

    public String getPage() {
	return page;
    }

    public void setPage(String page) {
	this.page = page;
    }

    public String getItemListFactory() {
	return itemListFactory;
    }

    public void setItemListFactory(String itemListFactory) {
	this.itemListFactory = itemListFactory;
    }

    public String getPreImage() {
	return preImage;
    }

    public void setPreImage(String preImage) {
	this.preImage = preImage;
    }

    public String getTarget() {
	return target;
    }

    public void setTarget(String target) {
	this.target = target;
    }

    public List<MenuItem> getSubItems() {
	return subItems;
    }

    public void add(MenuItem subItem) {
	if (subItem == null){
	    return;
	}
	if (subItems == null){
	    subItems = new ArrayList<MenuItem>();
	}
	subItems.add(subItem);
    }

}
