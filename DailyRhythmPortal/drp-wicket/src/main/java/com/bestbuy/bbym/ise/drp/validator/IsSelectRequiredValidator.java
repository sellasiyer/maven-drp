package com.bestbuy.bbym.ise.drp.validator;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;

import com.bestbuy.bbym.ise.drp.util.SelectItem;

public class IsSelectRequiredValidator extends AbstractValidator<SelectItem<String>> {

    private static final long serialVersionUID = 1L;

    private final String labelName;

    @Override
    protected void onValidate(IValidatable<SelectItem<String>> validatable) {

	if (validatable.getValue() == null || StringUtils.isEmpty(validatable.getValue().getKey())){
	    validatable.error(new ValidationError().setMessage(labelName + " is required."));
	}
    }

    @Override
    public boolean validateOnNullValue() {
	return true;
    }

    public IsSelectRequiredValidator(final String labelName) {
	this.labelName = labelName;
    }

}
