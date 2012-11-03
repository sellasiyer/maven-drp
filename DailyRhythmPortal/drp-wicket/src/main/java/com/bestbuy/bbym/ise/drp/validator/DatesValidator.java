package com.bestbuy.bbym.ise.drp.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatesValidator extends AbstractFormValidator {

    private static final int MAX_DAYS_PERIOD = 31;

    private static Logger logger = LoggerFactory.getLogger(DatesValidator.class);

    private static final long serialVersionUID = 1L;

    /** form components to be checked. */
    private final FormComponent[] components;

    /**
     * Construct.
     * 
     * @param formComponent1
     *            a form component
     * @param formComponent2
     *            a form component
     */
    public DatesValidator(FormComponent formComponent1, FormComponent formComponent2) {
	if (formComponent1 == null){
	    throw new IllegalArgumentException("argument formComponent1 cannot be null");
	}
	if (formComponent2 == null){
	    throw new IllegalArgumentException("argument formComponent2 cannot be null");
	}
	components = new FormComponent[] {formComponent1, formComponent2 };
    }

    /**
     * @see wicket.markup.html.form.validation.IFormValidator#getDependentFormComponents()
     */
    public FormComponent[] getDependentFormComponents() {
	return components;
    }

    /**
     * @see wicket.markup.html.form.validation.IFormValidator#validate(wicket.markup.html.form.Form)
     */
    public void validate(Form form) {
	// we have a choice to validate the type converted values or the raw
	// input values, we validate the raw input
	final FormComponent formComponent1 = components[0];
	final FormComponent formComponent2 = components[1];

	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

	Date fromDate = null;
	Date toDate = null;

	try{
	    fromDate = formatter.parse(formComponent1.getRawInput());
	    checkDateValue(formComponent1.getRawInput());
	}catch(ParseException e){
	    logger.debug(e.getMessage());
	    formComponent1.error("Enter a valid TO and FROM date");
	    return;
	}catch(NumberFormatException exc){
	    logger.debug(exc.getMessage());
	    formComponent2.error("Enter a valid TO and FROM date");
	    return;
	}

	try{
	    toDate = formatter.parse(formComponent2.getRawInput());
	    checkDateValue(formComponent2.getRawInput());
	}catch(ParseException e){
	    logger.debug(e.getMessage());
	    formComponent2.error("Enter a valid TO and FROM date");
	    return;
	}catch(NumberFormatException exc){
	    logger.debug(exc.getMessage());
	    formComponent2.error("Enter a valid TO and FROM date");
	    return;
	}

	if (fromDate.compareTo(toDate) > 0){
	    formComponent1.error("Invalid Date Range: The FROM date must fall before the TO date.");
	    return;
	}

	Days days = Days.daysBetween(new DateTime(fromDate), new DateTime(toDate));

	if (days.getDays() > MAX_DAYS_PERIOD){
	    formComponent1.error("Please select date range less than or equal to 30 days");
	    return;
	}

    }

    private void checkDateValue(String input) throws ParseException {
	if (StringUtils.isEmpty(input)){
	    throw new ParseException("invalid date entered", 0);
	}

	String[] vals = input.split("/");

	if (Integer.parseInt(vals[0]) > 12){
	    throw new ParseException("invalid date entered", 0);
	}

	if (Integer.parseInt(vals[1]) > 31){
	    throw new ParseException("invalid date entered", 0);
	}

	if (NumberUtils.createInteger(vals[2]) < 1900 && Integer.parseInt(vals[2]) > 3000){
	    throw new ParseException("invalid date entered", 0);
	}
    }

}
