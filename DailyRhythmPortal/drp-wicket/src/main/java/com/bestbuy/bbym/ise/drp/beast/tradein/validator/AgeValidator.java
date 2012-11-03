package com.bestbuy.bbym.ise.drp.beast.tradein.validator;

import java.util.Calendar;
import java.util.Date;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;

/**
 * Checks that the age is at least 18 years old
 */
// TODO Move to common validator package 
public class AgeValidator extends AbstractValidator<Date> {

    private static final long serialVersionUID = 1L;

    private static final int REQUIRED_AGE = 18;

    @Override
    protected void onValidate(IValidatable<Date> validatable) {

	if (validatable.getValue() != null){
	    Calendar calendar = Calendar.getInstance();
	    calendar.add(Calendar.YEAR, -1 * REQUIRED_AGE);

	    if (validatable.getValue().getTime() > calendar.getTimeInMillis()){
		validatable.error(new ValidationError().setMessage("Customer must be at least " + REQUIRED_AGE
			+ " years old."));
		return;
	    }
	}

    }
}
