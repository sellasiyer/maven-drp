package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.Response;

public class DrpAjaxIndicatorAppender extends Behavior {

    private static final long serialVersionUID = 1L;

    private IModel<String> messageModel;
    /**
     * Component instance this behavior is bound to
     */
    private Component component;

    /**
     * Creates an ajax indicator appender that displays a modal dialog with the
     * supplied message while an operation is in progress.
     * 
     * @param messageModel
     *            the message displayed in the modal dialog
     */
    public DrpAjaxIndicatorAppender(IModel<String> messageModel) {
	this.messageModel = messageModel;
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
	super.renderHead(component, response);

	AjaxRequestTarget target = AjaxRequestTarget.get();
	if (target != null){
	    final String javascript = "var e = Wicket.$('" + getMarkupId()
		    + "'); if (e != null && typeof(e.parentNode) != 'undefined') e.parentNode.removeChild(e);";
	    target.prependJavaScript(javascript);
	}
    }

    @Override
    public void afterRender(final Component component) {
	super.afterRender(component);

	final Response r = component.getResponse();

	r.write("<span style=\"display:none;\" class=\"");
	r.write(getSpanClass());
	r.write("\" ");
	r.write("id=\"");
	r.write(getMarkupId());
	r.write("\">");
	r.write("<div class=\"modal\">");
	r.write("<div class=\"loading-spinner-big\">");
	r.write(messageModel.getObject());
	r.write("</div>");
	r.write("</div>");
	r.write("</span>");

    }

    /**
     * @return css class name of the generated outer span
     */
    protected String getSpanClass() {
	return "fader";
    }

    /**
     * Returns the markup id attribute of the outer most span of this indicator.
     * This is the id of the span that should be hidden or show to hide or show
     * the indicator.
     * 
     * @return markup id of outer most span
     */
    public String getMarkupId() {
	return component.getMarkupId() + "--ajax-indicator";
    }

    @Override
    public final void bind(final Component component) {
	this.component = component;
    }
}
