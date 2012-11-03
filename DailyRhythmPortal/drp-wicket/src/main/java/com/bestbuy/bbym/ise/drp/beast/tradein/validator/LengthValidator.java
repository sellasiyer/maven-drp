package com.bestbuy.bbym.ise.drp.beast.tradein.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;

public class LengthValidator extends AbstractValidator<String> {

    final private int minLength;
    final private int maxLength;
    final private String controlLabel;

    public LengthValidator(final int minLength, final int maxLength, final String controlLabel) {
	this.minLength = minLength;
	this.maxLength = maxLength;
	this.controlLabel = controlLabel;
    }

    @Override
    protected void onValidate(IValidatable<String> validatable) {
	if (StringUtils.isNotEmpty(validatable.getValue())){
	    if (validatable.getValue().length() < minLength){
		validatable.error(new ValidationError().setMessage(controlLabel + " must have length  more then "
			+ minLength + " characters."));
		return;
	    }

	    if (validatable.getValue().length() > maxLength){
		validatable.error(new ValidationError().setMessage(controlLabel + " must have length  less then "
			+ maxLength + " characters."));
		return;
	    }

	}

    }

    public int getMinLength() {
	return minLength;
    }

    public int getMaxLength() {
	return maxLength;
    }

    public String getControlLabel() {
	return controlLabel;
    }

}
