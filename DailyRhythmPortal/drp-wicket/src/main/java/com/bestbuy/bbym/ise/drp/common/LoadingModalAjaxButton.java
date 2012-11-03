package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * An {@link AjaxButton} that displays a loading modal dialog with a spinning
 * indicator while the ajax request is in progress.
 */
public abstract class LoadingModalAjaxButton extends AjaxButton implements IAjaxIndicatorAware {

    private static final long serialVersionUID = 1L;

    private DrpAjaxIndicatorAppender drpAjaxIndicatorAppender;

    /**
     * Creates a button that displays a modal dialog while the ajax request is
     * in progress.
     * 
     * @param id
     *            the component id
     * @param buttonNameModel
     *            a model containing the text to be displayed on the button
     * @param messageModel
     *            the message displayed in the modal dialog
     */
    public LoadingModalAjaxButton(String id, IModel<String> buttonNameModel, IModel<String> messageModel) {
	this(id, null, buttonNameModel, messageModel);
    }

    public LoadingModalAjaxButton(String id, final Form<?> form, IModel<String> buttonNameModel,
	    IModel<String> messageModel) {
	super(id, buttonNameModel, form);
	drpAjaxIndicatorAppender = new DrpAjaxIndicatorAppender(messageModel);
	add(drpAjaxIndicatorAppender);
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
	return drpAjaxIndicatorAppender.getMarkupId();
    }
}
