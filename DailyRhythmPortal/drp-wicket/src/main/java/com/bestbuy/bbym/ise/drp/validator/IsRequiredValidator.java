package com.bestbuy.bbym.ise.drp.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.PatternValidator;

public class IsRequiredValidator extends PatternValidator {

    private static final long serialVersionUID = 1L;

    private final String labelName;

    @Override
    protected void onValidate(IValidatable<String> validatable) {

	if (StringUtils.isEmpty(validatable.getValue())){
	    validatable.error(new ValidationError().setMessage(labelName + " is required."));
	}
    }

    @Override
    public boolean validateOnNullValue() {
	return true;
    }

    public IsRequiredValidator(final String labelName) {
	super(ValidatorPatterns.notEmpty);
	this.labelName = labelName;
    }

}
