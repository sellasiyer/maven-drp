package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;

/**
 * An {@link AjaxSubmitLink} that displays a loading modal dialog with a spinning
 * indicator while the ajax request is in progress.
 */
public abstract class LoadingModalAjaxSubmitLink extends AjaxSubmitLink implements IAjaxIndicatorAware {

    private static final long serialVersionUID = 1L;

    private DrpAjaxIndicatorAppender drpAjaxIndicatorAppender;

    public LoadingModalAjaxSubmitLink(String id, final Form<?> form, final IModel<String> messageModel) {
	super(id, form);
	drpAjaxIndicatorAppender = new DrpAjaxIndicatorAppender(messageModel);
	add(drpAjaxIndicatorAppender);
    }

    @Override
    public String getAjaxIndicatorMarkupId() {
	return drpAjaxIndicatorAppender.getMarkupId();
    }
}
