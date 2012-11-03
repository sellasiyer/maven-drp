/**
 * Best Buy Inc (c) 2011
 */
package com.bestbuy.bbym.ise.drp.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.wicket.util.convert.ConversionException;
import org.apache.wicket.util.convert.IConverter;
import org.apache.wicket.util.convert.converter.BigDecimalConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converts strings to and from a decimal number representing a sum of money for
 * any locale
 * 
 * @author Animesh Banerjee
 * @version $Revision: 1.4 $
 */
public class MoneyConverter implements IConverter {

    private static Logger logger = LoggerFactory.getLogger(MoneyConverter.class);

    private static final long serialVersionUID = -947519990485065723L;

    /**
     * Construct.
     */
    public MoneyConverter() {
    }

    /**
     * A valid sum of money will be attempted to be parsed out of any input
     * string in a locale specific manner.
     * 
     * @throws ConversionException
     *             if an invalid number is input
     * @see org.apache.wicket.util.convert.IConverter#convertToObject(java.lang.String
     *      , java.util.Locale)
     * 
     * @return a {@code BigDecimal} representing the sum
     */
    @Override
    public Object convertToObject(String value, Locale locale) {
	BigDecimal bigDecimal = null;
	// this should never be the case as no conversion is invoked for null
	// values by Wicket
	if (value == null){
	    return null;
	}

	NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale);
	BigDecimalConverter converter = new BigDecimalConverter();
	try{
	    // Attempt a straight numeric conversion first e.g. -12.34
	    bigDecimal = converter.convertToObject(value, locale);
	}catch(Exception e){
	    // failing that, attempt a currency format conversion in case the
	    // input string includes non numeric characters such as the currency
	    // symbol e.g. ($12.34)
	    try{
		bigDecimal = new BigDecimal(currencyFormat.parse(value.trim()).toString());
	    }catch(ParseException e1){
		// give up now, this can't be a valid currency amount
		error(value, "invalid");
		logger.info("Invlalid currency amount", e1);
	    }
	}
	return bigDecimal;
    }

    /**
     * This method will only properly format a big decimal number into a locale
     * specific string e.x. 12.34 in USD = "$12.34"
     * 
     * @see org.apache.wicket.util.convert.IConverter#convertToString(java.lang.Object,
     *      java.util.Locale)
     */
    @Override
    public String convertToString(Object bigDecimal, Locale locale) {
	if (bigDecimal == null){
	    return "";
	}else{
	    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(locale == null?Locale.getDefault():locale);
	    currencyFormat.setMinimumFractionDigits(2);
	    return currencyFormat.format(bigDecimal);
	}
    }

    private void error(String value, String errorKey) {
	ConversionException e = new ConversionException("Error converting value: '" + value
		+ "' is not a valid money amount");
	e.setSourceValue(value);
	e.setResourceKey(getClass().getSimpleName() + "." + errorKey);
	throw e;
    }
}
