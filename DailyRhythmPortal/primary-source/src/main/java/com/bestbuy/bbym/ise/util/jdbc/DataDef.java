package com.bestbuy.bbym.ise.util.jdbc;

import java.util.Date;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

import com.bestbuy.bbym.ise.exception.DataAccessException;

public abstract class DataDef {

    public enum DataType {
	LONG, INTEGER, STRING, DATE_TIMESTAMP, DATE_DATE
    }

    private Object dataObject;

    public abstract int getMaxFieldId();

    public abstract String getTable();

    public abstract String getSequenceName();

    public abstract String getFieldName(int fieldId) throws DataAccessException;

    public abstract Object getFieldValue(int fieldId, Object dataObject) throws DataAccessException;

    public abstract void setFieldValue(int fieldId, Object dataObject, Object dataValue) throws DataAccessException;

    public abstract DataType getFieldDataType(int fieldId) throws DataAccessException;

    public abstract int getPrimaryFieldId();

    @SuppressWarnings("rawtypes")
    public abstract Class getDataObjectClass();

    public void setDataObject(Object dataObject) {
	this.dataObject = dataObject;
    }

    public Object getDataObject() {
	return dataObject;
    }

    public void validateFieldId(int fieldId) throws DataAccessException {
	if (fieldId <= 0 || fieldId > getMaxFieldId()){
	    throw new DataAccessException("Bad field ID value: " + fieldId);
	}
    }

    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }

    public static long getLong(Object dataValue) {
	if (dataValue == null || !(dataValue instanceof Long)){
	    return 0L;
	}
	Long value = (Long) dataValue;
	return value.longValue();
    }

    public static int getInteger(Object dataValue) {
	if (dataValue == null || !(dataValue instanceof Integer)){
	    return 0;
	}
	Integer value = (Integer) dataValue;
	return value.intValue();
    }

    public static String getString(Object dataValue) {
	if (dataValue == null || !(dataValue instanceof String)){
	    return null;
	}
	return (String) dataValue;
    }

    public static Date getDate(Object dataValue) {
	if (dataValue == null || !(dataValue instanceof Date)){
	    return null;
	}
	return (Date) dataValue;
    }

    public static String convertBooleanToString(Boolean value) {
	if (value != null){
	    return value.booleanValue()?"Y":"N";
	}
	return null;
    }

    public static Boolean convertStringToBoolean(String value) {
	if (value != null){
	    if ("Y".equalsIgnoreCase(value)){
		return Boolean.TRUE;
	    }
	    return Boolean.FALSE;
	}
	return null;
    }
}
