package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;

public class TruncateFormatter<P> implements Formatter<P>, Serializable {

    private static final long serialVersionUID = 1L;

    private int maxSize = 20;
    private final static String BREAK_STRING = "...";

    public TruncateFormatter(int maxSize) {
	this.maxSize = maxSize;
    }

    @Override
    public String format(P value) {
	if (value == null){
	    return null;
	}
	if (value instanceof String){
	    String str = (String) value;
	    int leng = str.length();
	    if (leng <= maxSize){
		return str;
	    }
	    int diff = leng - maxSize;
	    int breakIdx = (leng - diff - BREAK_STRING.length()) / 2;
	    StringBuilder sb = new StringBuilder();
	    sb.append(str.substring(0, breakIdx));
	    sb.append(BREAK_STRING);
	    breakIdx = leng - (maxSize - sb.length());
	    sb.append(str.substring(breakIdx));
	    return sb.toString();
	}
	throw new IllegalArgumentException("Class not handled: " + value.getClass().getName());
    }
}
