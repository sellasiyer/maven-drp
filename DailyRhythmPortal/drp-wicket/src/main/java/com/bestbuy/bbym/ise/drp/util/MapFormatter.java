package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MapFormatter<P> implements Formatter<P>, Serializable {

    private static final long serialVersionUID = 1L;

    private Map<P, String> map = new HashMap<P, String>();

    public MapFormatter() {
    }

    @Override
    public String format(P value) {
	if (value == null){
	    return null;
	}
	if (value instanceof String){
	    String str = (String) value;
	    return map.get(StringUtils.upperCase(str));

	}
	throw new IllegalArgumentException("Class not handled: " + value.getClass().getName());
    }

    public void addMapping(P dataValue, String displayValue) {
	map.put(dataValue, displayValue);
    }

}
