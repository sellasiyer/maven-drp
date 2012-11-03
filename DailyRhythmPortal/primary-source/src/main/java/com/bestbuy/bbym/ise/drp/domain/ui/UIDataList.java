package com.bestbuy.bbym.ise.drp.domain.ui;

import java.util.ArrayList;

import com.bestbuy.bbym.ise.util.Util;

public class UIDataList extends ArrayList<UIData> implements UIData {

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
	return UIData.Type.LIST;
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
	sb.append("UIDataList[name=");
	sb.append(name);
	sb.append(" [");
	sb.append(Util.toStringList(this));
	sb.append("]]");
	return sb.toString();
    }
}
