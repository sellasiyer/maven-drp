package com.bestbuy.bbym.ise.drp.util;

import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * Recommended for use when you have Strings that you don't want to print "null" values.
 * Advantage over formatPropertyModel is basically less code/boilerplate to setup.
 * @author a885527 (miked)
 */
public class StringModel extends Model<String> {

    private static final long serialVersionUID = 1L;

    private String nullValue = "";

    public StringModel(Object obj, String propertyExpression, String nullValue) {
	super(new PropertyModel<String>(obj, propertyExpression).getObject());
	this.nullValue = nullValue;

    }

    public StringModel(String object, String nullValue) {
	super(object);
	this.nullValue = nullValue;
    }

    @Override
    public String getObject() {
	String str = super.getObject();
	if (str == null)
	    return nullValue;

	return str;
    }

}
