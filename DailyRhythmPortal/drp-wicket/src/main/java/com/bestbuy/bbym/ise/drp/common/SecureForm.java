/**
 * 
 */
package com.bestbuy.bbym.ise.drp.common;

import java.util.UUID;

import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;

import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;

/**
 * @author a186288
 * 
 *         This class will add a hidden token each form. It will check to make
 *         sure that the same token is submitted back When the form is posted.
 * 
 *         This method is used to prevent Cross Site Request Forgery.
 */
public class SecureForm<T> extends Form<T> {

    private static final long serialVersionUID = 4645252816729279788L;

    private static final String TOKEN_NAME = "SECURE_FORM_TOKEN";
    private String token;

    public SecureForm(String id) {
	super(id);
    }

    @Override
    protected void onBeforeRender() {
	super.onBeforeRender();
	this.token = UUID.randomUUID().toString();
    }

    @Override
    public void onComponentTagBody(final MarkupStream markupStream, final ComponentTag openTag) {
	super.onComponentTagBody(markupStream, openTag);
	// append the hidden id field to the markup that is generated for the
	// form
	getResponse().write("<input type='hidden' name='" + SecureForm.TOKEN_NAME + "' value='" + this.token + "'/>");
    }

    @Override
    public void process(IFormSubmitter submittingComponent) {
	{
	    if (!this.token.equals(getRequest().getRequestParameters().getParameterValue(SecureForm.TOKEN_NAME))){
		//if the token does not match the expected token then throw an exception.
		ExceptionPageHandler.processException("Token submitted on form does not match stored token",
			IseExceptionCodeEnum.InvalidState, null, this.getSession());
	    }
	    super.process(submittingComponent);
	}
    }
}
