package com.bestbuy.bbym.ise.drp.validator;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;

public class IsDateRequiredValidator extends AbstractValidator<Date> {

    /**
     * @see org.apache.wicket.validation.validator.PatternValidator#onValidate(org.apache.wicket.validation.IValidatable)
     */
    @Override
    protected void onValidate(IValidatable<Date> validatable) {

	if (validatable.getValue() == null || StringUtils.isEmpty(validatable.getValue().toString())){
	    validatable.error(new ValidationError().setMessage(labelName + " is required."));
	}
    }

    /**
     * @see org.apache.wicket.validation.validator.AbstractValidator#validateOnNullValue()
     */
    @Override
    public boolean validateOnNullValue() {
	return true;
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    //public static IsRequiredValidator INSTANCE = new IsRequiredValidator(ValidatorPatterns.notEmpty);

    private final String labelName;

    /**
     * @param labelName
     */
    public IsDateRequiredValidator(final String labelName) {
	this.labelName = labelName;
    }

}
