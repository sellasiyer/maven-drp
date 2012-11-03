package com.bestbuy.bbym.ise.drp.security;

import org.apache.wicket.markup.html.WebPage;

/**
 * Renders the sign in page.
 * <p>
 * This will typically be skipped in production when the user navigates to BEAST
 * Portal from the ETK widget. They will already have been authenticated when
 * logging into ETK so we'll be utilizing single sign-on and the user will
 * automatically log in to the BEAST Portal.
 * </p>
 * 
 * @author jam
 */
public class SignInPage extends WebPage {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs the page.
     */
    public SignInPage() {
	add(new SignInPanel("signInPanel"));
    }

}
