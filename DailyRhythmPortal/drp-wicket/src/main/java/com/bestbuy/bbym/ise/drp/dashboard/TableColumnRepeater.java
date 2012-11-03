package com.bestbuy.bbym.ise.drp.dashboard;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

public class TableColumnRepeater extends Panel {

    private static final long serialVersionUID = 1L;

    public interface ColumnFactory {

	public Component newColumn(int index, String id);

	public int size();
    }

    public TableColumnRepeater(String id, ColumnFactory factory) {
	super(id);
	RepeatingView columnRepeater = new RepeatingView("columnRepeater");
	for(int i = 0; i < factory.size(); ++i){
	    columnRepeater.add(factory.newColumn(i, columnRepeater.newChildId()));
	}
	add(columnRepeater);
    }
}
