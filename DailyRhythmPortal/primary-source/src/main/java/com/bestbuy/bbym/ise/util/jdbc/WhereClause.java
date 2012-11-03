package com.bestbuy.bbym.ise.util.jdbc;

public class WhereClause {

    public enum ParamType {
	EXPRESSION, OBJECT_VALUE
    }

    public enum OperatorType {
	EQUAL, GREATER_THAN
    }

    private OperatorType operatorType;
    private FieldInfo fieldInfo1;
    private FieldInfo fieldInfo2;
    private boolean showAlias = true;

    public void setOperatorType(OperatorType operatorType) {
	this.operatorType = operatorType;
    }

    public OperatorType getOperatorType() {
	return operatorType;
    }

    public FieldInfo getFieldInfo1() {
	return fieldInfo1;
    }

    public void setFieldInfo1(FieldInfo fieldInfo1) {
	this.fieldInfo1 = fieldInfo1;
    }

    public FieldInfo getFieldInfo2() {
	return fieldInfo2;
    }

    public void setFieldInfo2(FieldInfo fieldInfo2) {
	this.fieldInfo2 = fieldInfo2;
    }

    public void setShowAlias(boolean showAlias) {
	this.showAlias = showAlias;
    }

    public boolean isShowAlias() {
	return showAlias;
    }

    public String getOperatorSql() {
	return determineOperatorSql(operatorType);
    }

    public static String determineOperatorSql(OperatorType operatorType) {
	switch (operatorType) {
	    case EQUAL:
		return "=";
	    case GREATER_THAN:
		return ">";
	}
	throw new IllegalArgumentException("OperatorType not handled: " + operatorType);
    }
}
