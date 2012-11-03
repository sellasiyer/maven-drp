package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;

/**
 * An {@link AjaxLink} that displays a loading modal dialog with a spinning
 * indicator while the ajax request is in progress.
 */
public abstract class LoadingModalAjaxLink extends AjaxLink<Object> implements IAjaxIndicatorAware {

    private static final long serialVersionUID = 1L;

    private DrpAjaxIndicatorAppender drpAjaxIndicatorAppender;

    /**
     * Creates a link that displays a modal dialog while the ajax request is in
     * progress.
     * 
     * @param id
     *            the component id
     * @param linkTextModel
     *            a model containing the text to be displayed on the link
     * @param messageModel
     *            the message displayed in the modal dialog
     */
    public LoadingModalAjaxLink(final String id, final IModel<Object> linkTextModel, final IModel<String> messageModel) {
	super(id, linkTextModel);
	drpAjaxIndicatorAppender = new DrpAjaxIndicatorAppender(messageModel);
	add(drpAjaxIndicatorAppender);
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
	return drpAjaxIndicatorAppender.getMarkupId();
    }
}
