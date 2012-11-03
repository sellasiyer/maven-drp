package com.bestbuy.bbym.ise.drp.domain.ui;

import java.util.ArrayList;
import java.util.List;

import com.bestbuy.bbym.ise.util.Util;

public class UIListRow extends UIBaseComponent {

    private static final long serialVersionUID = 1L;

    private List<UIComponent> columnList;

    public void addColumn(UIComponent column) {
	if (column == null){
	    return;
	}
	if (columnList == null){
	    columnList = new ArrayList<UIComponent>();
	}
	columnList.add(column);
    }

    public List<UIComponent> getColumnList() {
	return columnList;
    }

    @Override
    public Type getType() {
	return UIComponent.Type.LIST_ROW;
    }

    @Override
    public String toString() {
	StringBuilder sb = new StringBuilder();
	sb.append("UIListRow[columnList=");
	sb.append(Util.toStringList(columnList));
	sb.append(" ");
	sb.append(super.toString());
	sb.append("]");
	return sb.toString();
    }
}
