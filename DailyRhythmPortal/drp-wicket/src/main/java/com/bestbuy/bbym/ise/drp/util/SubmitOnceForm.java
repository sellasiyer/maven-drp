/**
 * Best Buy Inc (c) 2011
 */
package com.bestbuy.bbym.ise.drp.util;

import java.util.ArrayList;

import org.apache.wicket.MetaDataKey;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IFormSubmitter;
import org.apache.wicket.model.IModel;

/**
 * This form can be used when it is not appropriate to use the browser back
 * functionality and resubmit a previously submitted form. The
 * <code>onResubmit</code> method is provided to handle multiple submissions
 * 
 * @author Animesh Banerjee
 */
public class SubmitOnceForm<T> extends Form<T> {

    private static final long serialVersionUID = 1L;
    private static MetaDataKey<ArrayList<FormToken>> PROCESSED = new MetaDataKey<ArrayList<FormToken>>() {

	private static final long serialVersionUID = 1L;
    };

    public SubmitOnceForm(String id) {
	super(id);
    }

    public SubmitOnceForm(final String id, IModel<T> model) {
	super(id, model);
    }

    protected void onRepeatSubmit() {
	error(getString("alreadySubmitted"));
    }

    @Override
    public void process(IFormSubmitter submittingComponent) {
	if (isAlreadyProcessed()){
	    onRepeatSubmit();
	}else{
	    super.process(submittingComponent);
	    updateProcessedForms();
	}
    }

    private FormToken getToken() {
	return new FormToken(getPage().getPageReference(), getPageRelativePath());
    }

    private synchronized boolean isAlreadyProcessed() {
	ArrayList<FormToken> tokens = getSession().getMetaData(PROCESSED);

	if (tokens != null){
	    FormToken token = getToken();
	    if (tokens.contains(token)){
		return true;
	    }
	}
	return false;
    }

    private synchronized void updateProcessedForms() {
	if (hasError()){
	    return;
	}

	ArrayList<FormToken> tokens = getSession().getMetaData(PROCESSED);
	if (tokens == null){
	    tokens = new ArrayList<FormToken>();
	}

	FormToken token = getToken();

	if (!tokens.contains(token)){
	    tokens.add(token);
	    while(tokens.size() > 20){
		tokens.remove(0);
	    }
	    getSession().setMetaData(PROCESSED, tokens);
	}
    }

}
