package com.bestbuy.bbym.ise.drp.beast.scheduler;

import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.string.StringValueConversionException;
import org.apache.wicket.util.string.Strings;

public abstract class CustomAjaxCheckBox extends AjaxCheckBox {

    private static final long serialVersionUID = 1L;

    public CustomAjaxCheckBox(final String id) {
	this(id, null);
    }

    public CustomAjaxCheckBox(final String id, IModel model) {
	super(id, model);
    }

    public boolean isChecked() {
	final String value = getValue();
	if (value != null){
	    try{
		return Strings.isTrue(value);
	    }catch(StringValueConversionException e){
		return false;
	    }
	}
	return false;
    }
}
