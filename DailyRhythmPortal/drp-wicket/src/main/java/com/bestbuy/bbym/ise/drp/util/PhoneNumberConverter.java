/**
 * Best Buy Inc (c) 2011
 */
package com.bestbuy.bbym.ise.drp.util;

import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;

import com.bestbuy.bbym.ise.drp.common.PhoneNumber;

/**
 * Converts strings to and from US phone numbers
 * 
 * @author Animesh Banerjee
 * @version $Revision: 1.4 $
 */

public class PhoneNumberConverter implements IConverter {

    private static final long serialVersionUID = -947519990485065723L;

    /**
     * Construct.
     */
    public PhoneNumberConverter() {
    }

    /**
     * A valid phone number will be attempted to be parsed out of any input
     * string. All non digit characters are first stripped out. A null input
     * string will return null.
     * 
     * @throws ConversionException
     *             if an empty string is left after stripping out non-digits
     * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String
     *      , java.util.Locale)
     * 
     * @return a {@code PhoneNumber} representing the phone number
     */
    @Override
    public Object convertToObject(String value, Locale locale) {
	// this should never be the case as no conversion is invoked for null
	// values by Wicket
	if (value == null){
	    return null;
	}

	// remove all non digit characters
	String s = value.replaceAll("\\D", "");
	if (s.length() != 10){
	    error(value, "invalid");
	}

	return new PhoneNumber(s);
    }

    /**
     * This method will only properly format a 10 digit US Phone number in to
     * the format (xxx)xxx-xxxx. No effort is made to format any number of
     * length other than 10 digits exactly.
     * 
     * @see org.apache.wicket.util.convert.IConverter#convertToString(java.lang.Object,
     *      java.util.Locale)
     */
    @Override
    public String convertToString(Object value, Locale locale) {
	if (value == null){
	    return null;
	}
	return value.toString().replaceAll("(\\d{3})(\\d{3})(\\d{4})", "($1)$2-$3");
    }

    private void error(String value, String errorKey) {
	ConversionException e = new ConversionException("Error converting value: '" + value
		+ "' is not a valid phone number");
	e.setSourceValue(value);
	e.setResourceKey(getClass().getSimpleName() + "." + errorKey);
	throw e;
    }
}
