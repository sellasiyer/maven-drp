package com.bestbuy.bbym.ise.drp.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleDateValidator extends AbstractFormValidator {

    private static Logger logger = LoggerFactory.getLogger(SingleDateValidator.class);

    private static final long serialVersionUID = 1L;

    /** form components to be checked. */
    private final FormComponent[] components;

    /**
     * Construct.
     * 
     * @param formComponent1
     *            a form component
     */
    public SingleDateValidator(FormComponent formComponent) {
	if (formComponent == null){
	    throw new IllegalArgumentException("argument formComponent1 cannot be null");
	}
	components = new FormComponent[] {formComponent };
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
	final FormComponent formComponent = components[0];
	if (!StringUtils.isEmpty(formComponent.getRawInput())){

	    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");

	    Date fromDate = null;

	    try{
		fromDate = formatter.parse(formComponent.getRawInput());
		checkDateValue(formComponent.getRawInput());
	    }catch(ParseException e){
		logger.debug(e.getMessage());
		formComponent.error("Enter a valid  date");
		return;
	    }catch(NumberFormatException exc){
		logger.debug(exc.getMessage());
		formComponent.error("Enter a valid  date");
		return;
	    }
	}

    }

    private void checkDateValue(String input) throws ParseException {
	if (StringUtils.isEmpty(input)){
	    throw new ParseException("invalid date entered", 0);
	}

	String[] vals = input.split("/");

	if (Integer.parseInt(vals[0]) > 12 || Integer.parseInt(vals[0]) < 1){
	    throw new ParseException("invalid date entered", 0);
	}

	if (Integer.parseInt(vals[1]) > 31 || Integer.parseInt(vals[1]) < 1){
	    throw new ParseException("invalid date entered", 0);
	}

	if (NumberUtils.createInteger(vals[2]) < 1900 && Integer.parseInt(vals[2]) > 3000){
	    throw new ParseException("invalid date entered", 0);
	}
    }

}
