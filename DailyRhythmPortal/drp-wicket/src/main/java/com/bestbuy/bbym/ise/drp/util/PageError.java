package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;

public class PageError implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;
    private Class<?> panelClass;

    public PageError(String message) {
	setMessage(message);
    }

    public PageError(String message, Class<?> panelClass) {
	this(message);
	setPanelClass(panelClass);
    }

    public String getMessage() {
	return message;
    }

    public void setMessage(String message) {
	this.message = message;
    }

    public Class<?> getPanelClass() {
	return panelClass;
    }

    public void setPanelClass(Class<?> panelClass) {
	this.panelClass = panelClass;
    }

    public boolean doesApply(Class<?> panelClass) {
	if (this.panelClass == null){
	    return true;
	}
	if (panelClass == null){
	    return false;
	}
	return this.panelClass.getName().equals(panelClass.getName());
    }
}
