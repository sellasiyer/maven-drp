package com.bestbuy.bbym.ise.util.jdbc;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class FieldInfo {

    private int fieldId;
    private String tableAlias;
    private int otherFieldId;
    private WhereClause.ParamType paramType;
    private Object inputValue;
    private int index;

    public FieldInfo() {
    }

    public FieldInfo(int fieldId, String tableAlias, WhereClause.ParamType paramType) {
	setFieldId(fieldId);
	setTableAlias(tableAlias);
	setParamType(paramType);
    }

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

    public int getOtherFieldId() {
	return otherFieldId;
    }

    public void setOtherFieldId(int otherFieldId) {
	this.otherFieldId = otherFieldId;
    }

    public void setParamType(WhereClause.ParamType paramType) {
	this.paramType = paramType;
    }

    public WhereClause.ParamType getParamType() {
	return paramType;
    }

    public void setInputValue(Object inputValue) {
	this.inputValue = inputValue;
    }

    public Object getInputValue() {
	return inputValue;
    }

    public void setIndex(int index) {
	this.index = index;
    }

    public int getIndex() {
	return index;
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
