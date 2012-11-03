package com.bestbuy.bbym.ise.drp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.validator.AbstractValidator;

public class HttpUrlValidator extends AbstractValidator<String> {

    private static final long serialVersionUID = 1L;

    @Override
    protected void onValidate(IValidatable<String> validatable) {

	String url = validatable.getValue();
	Pattern ip = Pattern
		.compile("(https?|http)://(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})+[-/]*[-A-Za-z0-9&@#/%=;:+]*$");
	Matcher m = ip.matcher(url);

	Pattern sp = Pattern.compile("[<>\'\"&]$");
	Matcher msp = sp.matcher(url);

	if (url != null
		&& (!(url.startsWith("http") || url.startsWith("https"))
			|| (url.startsWith("http://localhost") || url.startsWith("https://localhost")) || m.find() || msp
			.find())){
	    error(validatable);
	}
    }

}
