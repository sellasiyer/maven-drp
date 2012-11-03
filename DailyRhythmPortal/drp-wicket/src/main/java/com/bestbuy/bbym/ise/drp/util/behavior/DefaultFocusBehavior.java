package com.bestbuy.bbym.ise.drp.util.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.html.IHeaderResponse;

public class DefaultFocusBehavior extends Behavior {

    private static final long serialVersionUID = 1L;

    @Override
    public void renderHead(Component component, IHeaderResponse response) {
	response.renderOnLoadJavaScript("$('#" + component.getMarkupId() + "').focus();");
    }

}
