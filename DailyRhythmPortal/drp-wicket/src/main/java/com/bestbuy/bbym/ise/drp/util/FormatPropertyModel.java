package com.bestbuy.bbym.ise.drp.util;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

public class FormatPropertyModel<T, P> extends Model<String> {

    private static final long serialVersionUID = 1L;

    private PropertyModel<T> pm;
    private Formatter<P> formatter;
    private String nullValue;

    public FormatPropertyModel(PropertyModel<T> pm, Formatter<P> formatter, String nullValue) {
	this.pm = pm;
	this.formatter = formatter;
	this.nullValue = nullValue;
    }

    @Override
    @SuppressWarnings("unchecked")
    public String getObject() {
	if (pm == null){
	    return null;
	}
	T value = pm.getObject();
	if (value == null || StringUtils.isBlank(value.toString())){
	    return nullValue;
	}
	if (formatter == null){
	    return value.toString();
	}
	return formatter.format((P) value);
    }
}
