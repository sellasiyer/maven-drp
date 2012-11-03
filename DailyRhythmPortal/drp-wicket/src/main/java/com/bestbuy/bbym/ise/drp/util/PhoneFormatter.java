package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;

public class PhoneFormatter<P> implements Formatter<P>, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public String format(P value) {
	if (value == null){
	    return null;
	}
	if (value instanceof String){
	    String str = (String) value;
	    if (str.length() != 10){
		return str;
	    }
	    StringBuilder sb = new StringBuilder();
	    sb.append("(");
	    sb.append(str.substring(0, 3));
	    sb.append(") ");
	    sb.append(str.substring(3, 6));
	    sb.append("-");
	    sb.append(str.substring(6));
	    return sb.toString();
	}
	throw new IllegalArgumentException("Class not handled: " + value.getClass().getName());
    }
}
