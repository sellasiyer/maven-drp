package com.bestbuy.bbym.ise.drp.scoreboard;

import java.math.BigDecimal;
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

public class EmployeeDetailsColumnPanel extends ScoreboardModalColumnPanel {

    private static final long serialVersionUID = 1L;

    private MoneyFormatter<BigDecimal> moneyFmt = new MoneyFormatter<BigDecimal>(true, false);
    private String na = getString("notApplicable.label");
    private String nullAmt;

    public EmployeeDetailsColumnPanel(String id, List<ScoreboardCategory> categoryList) {
	super(id, categoryList);

	nullAmt = moneyFmt.format(new BigDecimal(0));

	ListView<ScoreboardCategory> categoryListView = new ListView<ScoreboardCategory>("categoryListView",
		categoryList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardCategory> categoryListItem) {
		ScoreboardCategory category = categoryListItem.getModelObject();
		categoryListItem.add(new Label("listHeader", Model.of(category.getName())));

		ListView<ScoreboardDataItem> dataItemList = makeDataItemList(category);
		dataItemList.setReuseItems(true);
		categoryListItem.add(dataItemList);
	    }
	};
	add(categoryListView);
    }

    private ListView<ScoreboardDataItem> makeDataItemList(final ScoreboardCategory category) {
	ListView<ScoreboardDataItem> dataItemList = new ListView<ScoreboardDataItem>("dataItemListView", category
		.getDataItems()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardDataItem> listviewItem) {
		ScoreboardDataItem dataItem = listviewItem.getModelObject();
		Label itemName = new Label("itemName", new FormatPropertyModel<ScoreboardDataItem, String>(
			new PropertyModel<ScoreboardDataItem>(dataItem, "name"), null, na));
		listviewItem.add(itemName);
		Label itemQty = new Label("itemQty", new FormatPropertyModel<ScoreboardDataItem, Integer>(
			new PropertyModel<ScoreboardDataItem>(dataItem, "salesQuantity"), null, "-"));
		listviewItem.add(itemQty);
		Label itemTotal = new Label("itemTotal", new FormatPropertyModel<ScoreboardDataItem, BigDecimal>(
			new PropertyModel<ScoreboardDataItem>(dataItem, "salesTotal"), moneyFmt, nullAmt));
		listviewItem.add(itemTotal);
	    }
	};
	return dataItemList;
    }
}
