package com.bestbuy.bbym.ise.drp.scoreboard;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardDataItem;

public class ScoreboardItemDataProvider extends SortableDataProvider<ScoreboardDataItem> {

    private static final long serialVersionUID = 1L;

    private List<ScoreboardDataItem> dataItemList = new ArrayList<ScoreboardDataItem>();

    public ScoreboardItemDataProvider() {
    }

    public void setScoreboardDataItemList(List<ScoreboardDataItem> dataItemList) {
	if (dataItemList == null){
	    this.dataItemList.clear();
	    return;
	}
	this.dataItemList = dataItemList;
    }

    @Override
    public Iterator<? extends ScoreboardDataItem> iterator(int first, int count) {
	List<ScoreboardDataItem> newList = new ArrayList<ScoreboardDataItem>(dataItemList);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return dataItemList.size();
    }

    @Override
    public IModel<ScoreboardDataItem> model(final ScoreboardDataItem object) {
	return new AbstractReadOnlyModel<ScoreboardDataItem>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public ScoreboardDataItem getObject() {
		return (ScoreboardDataItem) object;
	    }
	};
    }
}
