package com.bestbuy.bbym.ise.util.jdbc;

public class OrderByInfo {

    private int fieldId;
    private String tableAlias;
    private boolean ascending;

    public int getFieldId() {
	return fieldId;
    }

    public void setFieldId(int fieldId) {
	this.fieldId = fieldId;
    }

    public String getTableAlias() {
	return tableAlias;
    }

    public void setTableAlias(String tableAlias) {
	this.tableAlias = tableAlias;
    }

    public boolean isAscending() {
	return ascending;
    }

    public void setAscending(boolean ascending) {
	this.ascending = ascending;
    }

}
