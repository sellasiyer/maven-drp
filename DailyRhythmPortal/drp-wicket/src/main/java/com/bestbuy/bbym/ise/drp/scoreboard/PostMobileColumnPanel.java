package com.bestbuy.bbym.ise.drp.scoreboard;

import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.MinimumValidator;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardCategory;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;

public class PostMobileColumnPanel extends ScoreboardModalColumnPanel {

    private static final long serialVersionUID = 1L;

    private PostMode postMode = PostMode.SALE;

    public PostMobileColumnPanel(String id, List<ScoreboardCategory> categoryList) {
	super(id, categoryList);

	ListView<ScoreboardCategory> categoryListView = new ListView<ScoreboardCategory>("categoryListView",
		categoryList) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardCategory> categoryListItem) {
		ScoreboardCategory category = categoryListItem.getModelObject();
		categoryListItem.add(new Label("listHeaderLabel", Model.of(category.getName())));
		ListView<ScoreboardDataItem> dataItemList = makeDataItemList(category);
		categoryListItem.add(dataItemList);
	    }

	};
	add(categoryListView);
    }

    private ListView<ScoreboardDataItem> makeDataItemList(ScoreboardCategory category) {
	ListView<ScoreboardDataItem> dataItemList = new ListView<ScoreboardDataItem>("dataItemListview", category
		.getDataItems()) {

	    private static final long serialVersionUID = 1L;

	    @Override
	    protected void populateItem(ListItem<ScoreboardDataItem> listviewItem) {
		ScoreboardDataItem dataItem = listviewItem.getModelObject();

		listviewItem.add(new Label("itemNameLabel", PropertyModel.of(dataItem, "name")));

		TextField<Number> itemQtyField = new TextField<Number>("itemQtyField", new PropertyModel<Number>(
			dataItem, postMode.toString()));
		itemQtyField.setOutputMarkupPlaceholderTag(true);
		itemQtyField.add(new MinimumValidator<Integer>(0));
		listviewItem.add(itemQtyField);
	    }
	};
	return dataItemList;
    }

}
