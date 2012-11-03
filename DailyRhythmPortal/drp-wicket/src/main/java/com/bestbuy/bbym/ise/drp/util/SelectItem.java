package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

public class SelectItem<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    private String key;
    private T value;

    public SelectItem(String key, T value) {
	setKey(key);
	setValue(value);
    }

    public String getKey() {
	return key;
    }

    public void setKey(String key) {
	this.key = key;
    }

    public T getValue() {
	return value;
    }

    public void setValue(T value) {
	this.value = value;
    }

    @Override
    public String toString() {
	return ReflectionToStringBuilder.toString(this);
    }
}
