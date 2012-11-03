package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;
import java.util.Map;

public class StringMapFormatter implements Formatter<String>, Serializable {

    private static final long serialVersionUID = 1L;

    private Map<String, String> stringMap;

    public StringMapFormatter(Map<String, String> stringMap) {
	this.stringMap = stringMap;
    }

    @Override
    public String format(String value) {
	if (value == null){
	    return null;
	}
	return stringMap.get(value);
    }
}
