package com.bestbuy.bbym.ise.drp.common;

import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

public class RowIndexColumn<T> extends AbstractColumn<T> {

    private static final long serialVersionUID = 1L;

    private int rowIndex = 1;

    public RowIndexColumn(IModel<String> displayModel, String sortProperty) {
	super(displayModel, sortProperty);
    }

    public void resetRowIndex() {
	rowIndex = 1;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void populateItem(Item cellItem, String componentId, IModel rowModel) {
	cellItem.add(new RowIndexPanel(componentId, rowIndex++));
    }

}
