package com.bestbuy.bbym.ise.drp.beast.tradein.validator;

import java.util.Date;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;

/**
 * Checks whether the field's date is before or after the current date.
 */
// TODO Move to common validator package
public class TodayDateValidator extends AbstractValidator<Date> {

    private static final long serialVersionUID = 1L;

    private final String label;
    private final boolean after;

    public TodayDateValidator(final String label, boolean after) {
	this.label = label;
	this.after = after;
    }

    @Override
    protected void onValidate(IValidatable<Date> validatable) {
	if (validatable.getValue() != null){
	    if (after){
		if (System.currentTimeMillis() > validatable.getValue().getTime()){
		    validatable.error(new ValidationError().setMessage(label + " date must be after today's date."));
		    return;
		}
	    }else{
		if (System.currentTimeMillis() < validatable.getValue().getTime()){
		    validatable.error(new ValidationError().setMessage(label + " date must be before today's date."));
		    return;
		}

	    }
	}
    }
}
