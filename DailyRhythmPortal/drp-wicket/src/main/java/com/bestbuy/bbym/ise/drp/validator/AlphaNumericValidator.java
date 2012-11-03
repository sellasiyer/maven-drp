package com.bestbuy.bbym.ise.drp.validator;

import org.apache.wicket.validation.validator.PatternValidator;

public class AlphaNumericValidator extends PatternValidator {

    private static final long serialVersionUID = 1L;

    public static AlphaNumericValidator INSTANCE = new AlphaNumericValidator(ValidatorPatterns.alphaNumeric);

    private AlphaNumericValidator(String pattern) {
	super(pattern);
    }
}
