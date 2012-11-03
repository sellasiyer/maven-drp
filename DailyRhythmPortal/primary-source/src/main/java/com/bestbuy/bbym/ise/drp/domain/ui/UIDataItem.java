package com.bestbuy.bbym.ise.drp.domain.ui;

import com.bestbuy.bbym.ise.drp.domain.ui.PropertyHashMap;

public class UIDataItem extends PropertyHashMap implements UIData {

    private static final long serialVersionUID = 1L;

    private String name;

    @Override
    public String getName() {
	return name;
    }

    @Override
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public Type getType() {
	return UIData.Type.ITEM;
    }

    @Override
    public boolean isComponent() {
	return false;
    }

    @Override
    public boolean isData() {
	return true;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UIDataItem[name=");
	sb.append(name);
	sb.append(" [");
	sb.append(super.toString());
	sb.append("]]");
	return sb.toString();
    }

}
