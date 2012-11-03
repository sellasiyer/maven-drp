package com.bestbuy.bbym.ise.drp.scoreboard;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;
import com.bestbuy.bbym.ise.drp.util.FormatPropertyModel;
import com.bestbuy.bbym.ise.drp.util.MoneyFormatter;

public class SbdStoreDetailsViewColumnPanel extends ScoreboardModalColumnPanel {

    private static final long serialVersionUID = 1L;

    public SbdStoreDetailsViewColumnPanel(String id, List<ScoreboardCategory> categoryList) {
	super(id, categoryList);

	ListView<ScoreboardCategory> categoryListView = new ListView<ScoreboardCategory>("categoryListView",
		categoryList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardCategory> categoryListItem) {
		ScoreboardCategory category = categoryListItem.getModelObject();

		// add header label.
		categoryListItem.add(new Label("listHeaderLabel", Model.of(category.getName())));

		// add interior listview of data items.
		ListView<ScoreboardDataItem> dataItemList = makeDataItemList(category);
		dataItemList.setReuseItems(true);
		categoryListItem.add(dataItemList);

	    } // end of populate

	};

	categoryListView.setReuseItems(true);
	add(categoryListView);
    } // end of constructor.

    // constructs listview, moved it out of loop to clean up code.
    private ListView<ScoreboardDataItem> makeDataItemList(final ScoreboardCategory category) {
	ListView<ScoreboardDataItem> dataItemList = new ListView<ScoreboardDataItem>("dataItemListview", category
		.getDataItems()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardDataItem> listviewItem) {

		ScoreboardDataItem dataItem = listviewItem.getModelObject();

		listviewItem.add(new Label("itemNameLabel", PropertyModel.of(dataItem, "name")));

		// TODO: md Adjust data field property names.

		Label returnQtyLabel = new Label("returnQtyLabel", PropertyModel.of(dataItem, "returnQuantity"));
		listviewItem.add(returnQtyLabel);

		Label returnTotalLabel = new Label("returnTotalLabel", new FormatPropertyModel<Number, Number>(
			new PropertyModel<Number>(dataItem, "returnTotal"), new MoneyFormatter<Number>(), ""));
		listviewItem.add(returnTotalLabel);

		Label saleQtyLabel = new Label("saleQtyLabel", PropertyModel.of(dataItem, "salesQuantity"));
		listviewItem.add(saleQtyLabel);

		Label saleTotalLabel = new Label("saleTotalLabel", new FormatPropertyModel<Number, Number>(
			new PropertyModel<Number>(dataItem, "salesTotal"), new MoneyFormatter<Number>(), ""));
		listviewItem.add(saleTotalLabel);

		// disables some labels for notional margin category
		if ("notional margin".equals(category.getName().toLowerCase())){
		    returnQtyLabel.setDefaultModel(Model.of("-"));
		    saleQtyLabel.setDefaultModel(Model.of("-"));
		}

	    } // end of inner populate

	}; // end of inner listview definition.

	return dataItemList;

    }
}
