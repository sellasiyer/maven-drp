package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;
import java.text.NumberFormat;

public class IntegerFormatter<T> implements Formatter<T>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String format(T value) {
	if (value == null){
	    return null;
	}

	if (value instanceof Integer){
	    return NumberFormat.getIntegerInstance().format(value);
	}

	throw new IllegalArgumentException("Class not handled: " + value.getClass().getName());
    }

}
