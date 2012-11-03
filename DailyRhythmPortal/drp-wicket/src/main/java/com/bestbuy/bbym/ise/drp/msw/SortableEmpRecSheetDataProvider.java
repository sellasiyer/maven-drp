package com.bestbuy.bbym.ise.drp.msw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.bestbuy.bbym.ise.drp.domain.RecSheetReportingSearchResults;

// FIXME need wicket 1.5+
// import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;

public class SortableEmpRecSheetDataProvider extends SortableDataProvider<RecSheetReportingSearchResults> implements
	ISortableDataProvider<RecSheetReportingSearchResults> {

    private static final long serialVersionUID = 8104922549743312786L;
    private List<RecSheetReportingSearchResults> recommendations;

    public void setRecommendations(List<RecSheetReportingSearchResults> recs) {
	this.recommendations = recs == null?new ArrayList<RecSheetReportingSearchResults>():recs;
    }

    public SortableEmpRecSheetDataProvider(List<RecSheetReportingSearchResults> recs) {
	super();
	setRecommendations(recs);
	// FIXME will work when wicket 1.5.x is used
	// setSort("",SortOrder.ASCENDING); need wicket 1.5+
    }

    @Override
    public Iterator<? extends RecSheetReportingSearchResults> iterator(int first, int count) {
	return recommendations.subList(first, first + count).iterator();
    }

    @Override
    public IModel<RecSheetReportingSearchResults> model(RecSheetReportingSearchResults recSheet) {
	return new Model<RecSheetReportingSearchResults>(recSheet);
    }

    @Override
    public int size() {
	return recommendations.size();
    }
}
