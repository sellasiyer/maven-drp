package com.bestbuy.bbym.ise.drp.util;

import java.io.Serializable;

import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

public class FormatModel<T extends Serializable> extends Model<String> {

    private static final long serialVersionUID = 1L;

    private IModel<T> model;
    private Formatter<T> formatter;
    private String nullValue;

    public FormatModel(IModel<T> model, Formatter<T> formatter) {
	this.model = model;
	this.formatter = formatter;
    }

    public FormatModel(T value, String nullValue) {
	model = new Model<T>(value);
	this.nullValue = nullValue;
    }

    public FormatModel(T value, Formatter<T> formatter, String nullValue) {
	model = new Model<T>(value);
	this.formatter = formatter;
	this.nullValue = nullValue;
    }

    @Override
    public String getObject() {
	if (model == null){
	    return null;
	}
	T value = model.getObject();
	if (value == null){
	    return nullValue;
	}
	if (formatter == null){
	    return value.toString();
	}
	return formatter.format(value);
    }
}
