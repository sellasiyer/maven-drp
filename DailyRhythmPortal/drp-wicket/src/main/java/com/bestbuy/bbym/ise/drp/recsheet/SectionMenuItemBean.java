package com.bestbuy.bbym.ise.drp.recsheet;

import java.io.Serializable;

public class SectionMenuItemBean implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String label;
    private Class clazz;
    private boolean saved = false;
    private String uid;
    private RecSheetsSections recSheetsSections;

    public RecSheetsSections getRecSheetsSections() {
	return recSheetsSections;
    }

    public void setRecSheetsSections(RecSheetsSections recSheetsSections) {
	this.recSheetsSections = recSheetsSections;
    }

    public String getUid() {
	return uid;
    }

    public void setUid(String uid) {
	this.uid = uid;
    }

    private boolean addType = false;

    public boolean isAddType() {
	return addType;
    }

    public void setAddType(boolean addType) {
	this.addType = addType;
    }

    public String getLabel() {
	return label;
    }

    public void setLabel(String label) {
	this.label = label;
    }

    public Class getClazz() {
	return clazz;
    }

    public void setClazz(Class clazz) {
	this.clazz = clazz;
    }

    public boolean isSaved() {
	return saved;
    }

    public void setSaved(boolean saved) {
	this.saved = saved;
    }

}
