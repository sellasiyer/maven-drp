package com.bestbuy.bbym.ise.drp.rev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class ManifestSummaryDataProvider extends SortableDataProvider<ManifestLine> {

    private static final long serialVersionUID = 1L;

    private List<ManifestLine> manifestSummaryList = new ArrayList<ManifestLine>();

    public ManifestSummaryDataProvider() {
	setSort("itemTag", SortOrder.DESCENDING);
    }

    public void setManifestLineList(List<ManifestLine> ManifestLineList) {
	if (ManifestLineList == null){
	    this.manifestSummaryList.clear();
	    return;
	}
	this.manifestSummaryList = ManifestLineList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<ManifestLine>, Serializable {

	public int compare(final ManifestLine o1, final ManifestLine o2) {
	    String sortProperty = getSort().getProperty();
	    PropertyModel<Object> model1, model2;
	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends ManifestLine> iterator(int first, int count) {
	List<ManifestLine> newList = new ArrayList<ManifestLine>(manifestSummaryList);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return manifestSummaryList.size();
    }

    @Override
    public IModel<ManifestLine> model(final ManifestLine object) {
	return new AbstractReadOnlyModel<ManifestLine>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public ManifestLine getObject() {
		return (ManifestLine) object;
	    }
	};
    }
}
