package com.bestbuy.bbym.ise.drp.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.model.Model;

public class UpperCaseBehavior extends AttributeAppender {

    private static final long serialVersionUID = 1L;

    public UpperCaseBehavior() {
	super("style", new Model<String>("text-transform: uppercase"), ";");
    }

    @Override
    public void bind(Component component) {
	super.bind(component);
	component
		.add(new AttributeAppender("onkeyup", new Model<String>("this.value = this.value.toUpperCase()"), ";"));
    }
}
