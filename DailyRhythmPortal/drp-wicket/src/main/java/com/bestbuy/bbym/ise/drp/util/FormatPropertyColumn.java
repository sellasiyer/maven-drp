package com.bestbuy.bbym.ise.drp.util;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

public class FormatPropertyColumn<T, P> extends PropertyColumn<T> {

    private static final long serialVersionUID = 1L;
    private Formatter<P> formatter;
    private String nullValue;

    public FormatPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression,
	    Formatter<P> formatter) {
	super(displayModel, sortProperty, propertyExpression);
	this.formatter = formatter;
    }

    public FormatPropertyColumn(IModel<String> displayModel, String sortProperty, String propertyExpression,
	    Formatter<P> formatter, String nullValue) {
	this(displayModel, sortProperty, propertyExpression, formatter);
	this.nullValue = nullValue;
    }

    protected IModel<?> createLabelModel(IModel<T> rowModel) {
	PropertyModel<T> pm = new PropertyModel<T>(rowModel, getPropertyExpression());
	return new FormatPropertyModel<T, P>(pm, formatter, nullValue);
    }
}
