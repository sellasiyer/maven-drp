package com.bestbuy.bbym.ise.drp.msw;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.bestbuy.bbym.ise.drp.domain.Recommendation;

// FIXME need wicket 1.5+
// import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;

public class SortableRecSheetDataProvider extends SortableDataProvider<Recommendation> implements
	ISortableDataProvider<Recommendation> {

    private static final long serialVersionUID = 8104922549743312786L;
    private List<Recommendation> recommendations;

    public void setRecommendations(List<Recommendation> recs) {
	this.recommendations = recs == null?new ArrayList<Recommendation>():recs;
    }

    public SortableRecSheetDataProvider(List<Recommendation> recs) {
	super();
	setRecommendations(recs);
	// FIXME will work when wicket 1.5.x is used
	// setSort("",SortOrder.ASCENDING); need wicket 1.5+
    }

    @Override
    public Iterator<? extends Recommendation> iterator(int first, int count) {
	return recommendations.subList(first, first + count).iterator();
    }

    @Override
    public IModel<Recommendation> model(Recommendation recSheet) {
	return new Model<Recommendation>(recSheet);
    }

    @Override
    public int size() {
	return recommendations.size();
    }
}
