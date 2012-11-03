package com.bestbuy.bbym.ise.drp.util.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

public class DisableEnterKeyBehavior extends Behavior {

    private static final long serialVersionUID = 1L;

    public static final String JS_SUPPRESS_ENTER = "if(event.keyCode==13 || window.event.keyCode==13){return false;}else{return true;}";

    @Override
    public final void onComponentTag(Component component, ComponentTag tag) {
	tag.put("onkeydown", JS_SUPPRESS_ENTER);
	tag.put("onkeypress", JS_SUPPRESS_ENTER);
    }
}
