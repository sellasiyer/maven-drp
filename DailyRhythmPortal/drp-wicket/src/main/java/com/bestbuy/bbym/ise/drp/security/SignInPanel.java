package com.bestbuy.bbym.ise.drp.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.authroles.authentication.AuthenticatedWebSession;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.StatelessForm;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.http.WebResponse;
import org.apache.wicket.util.value.ValueMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.DailyRhythmPortalSession;
import com.bestbuy.bbym.ise.drp.common.BasePanel;
import com.bestbuy.bbym.ise.drp.sao.OpenSSOSao;

public class SignInPanel extends BasePanel {

    private static final long serialVersionUID = 1L;
    private static Logger logger = LoggerFactory.getLogger(SignInPanel.class);

    /** Field for password. */
    private PasswordTextField password;

    /** Field for user name. */
    private TextField<String> username;

    public final class SignInForm extends StatelessForm<Void> {

	private static final long serialVersionUID = 1L;

	private final ValueMap properties = new ValueMap();

	/**
	 * Constructor.
	 * 
	 * @param id
	 *            id of the form component
	 */
	public SignInForm(final String id) {
	    super(id);

	    // Attach textfield components that edit properties map
	    // in lieu of a formal beans model
	    add(username = new TextField<String>("username", new PropertyModel<String>(properties, "username")));
	    add(password = new PasswordTextField("password", new PropertyModel<String>(properties, "password")));
	    username.setType(String.class);
	    password.setType(String.class);

	    // Do this so can throw single error in onSubmit
	    password.setRequired(false);

	    username.add(AttributeModifier.replace("maxlength", getString("loginMaxLength.constraint")));
	    password.add(AttributeModifier.replace("maxlength", getString("passwordMaxLength.constraint")));
	}

	/**
	 * @see org.apache.wicket.markup.html.form.Form#onSubmit()
	 */
	@Override
	public final void onSubmit() {
	    boolean usernameBlank = StringUtils.isBlank(getUsername());
	    boolean passwordBlank = StringUtils.isBlank(getPassword());
	    if (usernameBlank && passwordBlank){
		error(getString("userIdPasswordRequired.error"));
		return;
	    }else if (usernameBlank){
		error(getString("userIdRequired.error"));
		return;
	    }else if (passwordBlank){
		error(getString("passwordRequired.error"));
		return;
	    }

	    if (signIn(getUsername(), getPassword())){
		DailyRhythmPortalSession session = (DailyRhythmPortalSession) getSession();
		if (session != null){
		    String oldSessionId = session.getId();
		    session.replaceSession();
		    logger.debug("Replaced session " + oldSessionId + " with " + session.getId());
		}else{
		    logger.debug("session is null");
		}
		onSignInSucceeded();
	    }else{
		onSignInFailed();
	    }
	}
    }

    public SignInPanel(final String id) {
	super(id);

	// Create feedback panel and add to page
	final FeedbackPanel feedback = new FeedbackPanel("feedback");
	add(feedback);

	// Add sign-in form to page, passing feedback panel as
	// validation error handler
	add(new SignInForm("signInForm"));
    }

    /**
     * Convenience method to access the password.
     * 
     * @return The password
     */
    public String getPassword() {
	return password.getInput();
    }

    /**
     * Convenience method to access the username.
     * 
     * @return The user name
     */
    public String getUsername() {
	return username.getDefaultModelObjectAsString();
    }

    /**
     * Sign in user if possible.
     * 
     * @param username
     *            The username
     * @param password
     *            The password
     * @return True if signin was successful
     */
    public boolean signIn(String username, String password) {
	boolean signedIn = AuthenticatedWebSession.get().signIn(username, password);
	if (signedIn){
	    logger.info("Adding OpenSSO cookie");
	    WebResponse webResponse = (WebResponse) getResponse();
	    DailyRhythmPortalSession session = getDailyRhythmPortalSession();
	    Cookie cookie = new Cookie(OpenSSOSao.COOKIE_NAME_OPENSSO, session.getDrpUser().getOpenSsoTokenId());
	    HttpServletResponse servletResponse = (HttpServletResponse) webResponse.getContainerResponse();
	    servletResponse.addCookie(cookie);
	}
	return signedIn;
    }

    private void onSignInFailed() {
	// Try the component based localizer first. If not found try the
	// application localizer. Else use the default
	logger.warn("*** Failed login attempt by userName " + getUsername());
	error(getString("signInFailed.error"));
    }

    private void onSignInSucceeded() {
	setResponsePage(getApplication().getHomePage());
    }

}
