package com.bestbuy.bbym.ise.drp.domain.ui;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.util.Util;

public class UIList extends UIBaseComponent {

    private static final long serialVersionUID = 1L;

    private List<UIListRow> rowList;

    public void addRow(UIListRow row) {
	if (row == null){
	    return;
	}
	if (rowList == null){
	    rowList = new ArrayList<UIListRow>();
	}
	rowList.add(row);
    }

    public List<UIListRow> getRowList() {
	return rowList;
    }

    @Override
    public Type getType() {
	return UIComponent.Type.LIST;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UIList[rowList=");
	sb.append(Util.toStringList(rowList));
	sb.append(" ");
	sb.append(super.toString());
	sb.append("]");
	return sb.toString();
    }
}
