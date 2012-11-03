package com.bestbuy.bbym.ise.drp.validator;

import org.apache.wicket.validation.validator.PatternValidator;

public class ValidatorPatterns {

    public static String alphaNumeric = "[A-Za-z0-9]+";
    public static String notEmpty = "^(?!\\s*$).+";

    public static PatternValidator SERIAL_NUMBER = new PatternValidator("[A-Za-z0-9]{7,22}");
}
