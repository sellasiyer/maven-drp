package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

public class RowIndexPanel extends Panel {

    private static final long serialVersionUID = 1L;

    public RowIndexPanel(String id, int index) {
	super(id);
	add(new Label("indexLabel", String.valueOf(index)));
    }
}
