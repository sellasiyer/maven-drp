package com.bestbuy.bbym.ise.drp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.PatternValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PhoneNumberField extends TextField<String> {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(PhoneNumberField.class);

    private final static String formattedPatternString = "[(]\\d{3}[)] \\d{3}[-]\\d{4}";
    private final static String bothPatternString = "([(]\\d{3}[)] \\d{3}[-]\\d{4}|\\d{10})";

    private final static Pattern unformattedPattern = Pattern.compile("\\d{10}");
    private final static Pattern formattedPattern = Pattern.compile(formattedPatternString);
    private final static Pattern bothPattern = Pattern.compile(bothPatternString);

    public PhoneNumberField(final String id, final IModel<String> model) {
	super(id, new IModel<String>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public void detach() {
	    }

	    @Override
	    public String getObject() {
		logger.trace("GET-DATE==========" + model.getObject());
		if (model.getObject() == null){
		    return model.getObject();
		}
		Matcher m1 = unformattedPattern.matcher(model.getObject());
		if (m1.matches()){
		    return "(" + model.getObject().substring(0, 3) + ") " + model.getObject().substring(3, 6) + "-"
			    + model.getObject().substring(6);
		}
		Matcher m2 = formattedPattern.matcher(model.getObject());
		if (m2.matches()){
		    model.setObject(unformatValue(model.getObject()));
		}
		return model.getObject();
	    }

	    @Override
	    public void setObject(String object) {
		logger.trace("SET-DATE==========" + object);
		if (object == null){
		    model.setObject(object);
		    return;
		}
		Matcher m2 = formattedPattern.matcher(object);
		if (m2.matches()){
		    model.setObject(unformatValue(object));
		    return;
		}
		model.setObject(object);
	    }

	});
	setOutputMarkupPlaceholderTag(true);
	setRequired(true);
	setMarkupId(id);
	setOutputMarkupId(true);
	add(new AttributeAppender("onblur", new Model<String>("handlePhoneOnBlur('#" + id + "');"), ";"));
	add(new AttributeAppender("onfocus", new Model<String>("handlePhoneOnFocus('#" + id + "');"), ";"));
	add(new PatternValidator(bothPattern));
    }

    private static String unformatValue(String value) {
	return value.substring(1, 4) + value.substring(6, 9) + value.substring(10);
    }
}
