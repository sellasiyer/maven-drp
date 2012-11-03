package com.bestbuy.bbym.ise.drp.util.panel;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

public abstract class SpanPanel<T> extends Panel {

    private static final long serialVersionUID = 1L;

    public SpanPanel(final String id, IModel<T> model) {
	super(id, model);
	Label label = new Label("label", generateLabelString());
	label.setEscapeModelStrings(false);
	label.setRenderBodyOnly(true);
	add(label);
    }

    public abstract String generateLabelString();
}
