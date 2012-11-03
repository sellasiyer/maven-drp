package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MoneyFormatter<P> implements Formatter<P>, Serializable {

    private static final long serialVersionUID = 1L;

    private String dollarSign = "$";
    private String fmt = "###,###,##0.00";

    public MoneyFormatter() {
	this(true, true);
    }

    public MoneyFormatter(boolean showDollarSign) {
	this(showDollarSign, true);
    }

    public MoneyFormatter(boolean showDollarSign, boolean showCents) {
	if (!showDollarSign){
	    dollarSign = "";
	}
	if (!showCents){
	    fmt = "###,###,###";
	}
    }

    @Override
    public String format(P value) {
	if (value == null){
	    return null;
	}
	if (value instanceof String){
	    String str = (String) value;
	    double d = 0.0;
	    try{
		d = Double.parseDouble(str);
	    }catch(NumberFormatException nfe){
		return str;
	    }
	    DecimalFormat df = new DecimalFormat(fmt);
	    if (d < 0.0){
		return "(" + dollarSign + df.format(-d) + ")";
	    }
	    return dollarSign + df.format(d);

	}else if (value instanceof Double){
	    Double d = (Double) value;
	    DecimalFormat df = new DecimalFormat(fmt);
	    if (d.doubleValue() < 0.0){
		return "(" + dollarSign + df.format(-d.doubleValue()) + ")";
	    }
	    return dollarSign + df.format(d.doubleValue());

	}else if (value instanceof BigDecimal){
	    BigDecimal b = (BigDecimal) value;
	    DecimalFormat df = new DecimalFormat(fmt);
	    if (b.doubleValue() < 0.0){
		return "(" + dollarSign + df.format(-b.doubleValue()) + ")";
	    }
	    return dollarSign + df.format(b.doubleValue());

	}
	throw new IllegalArgumentException("Class not handled: " + value.getClass().getName());
    }
}
