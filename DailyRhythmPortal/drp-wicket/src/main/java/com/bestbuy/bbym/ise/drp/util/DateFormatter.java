package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;
import java.util.Date;

import com.bestbuy.bbym.ise.util.Util;

public class DateFormatter<P> implements Formatter<P>, Serializable {

    private static final long serialVersionUID = 1L;

    private String dateFormat = "MM/dd/yy";

    public DateFormatter() {
    }

    public DateFormatter(String dateFormat) {
	this.dateFormat = dateFormat;
    }

    @Override
    public String format(P value) {
	if (value == null){
	    return null;
	}
	if (value instanceof String){
	    String str = (String) value;
	    String[] buf = str.split("-");
	    if (buf.length != 3){
		return str;
	    }
	    StringBuilder sb = new StringBuilder();
	    sb.append(buf[1]);
	    sb.append("/");
	    sb.append(buf[2]);
	    sb.append("/");
	    sb.append(buf[0]);
	    return sb.toString();

	}else if (value instanceof Date){
	    Date date = (Date) value;
	    return Util.toString(date, dateFormat);
	}
	throw new IllegalArgumentException("Class not handled: " + value.getClass().getName());
    }
}
