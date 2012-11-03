package com.bestbuy.bbym.ise.drp.rev;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class BuildManifestDataProvider extends SortableDataProvider<ManifestLine> {

    private static final long serialVersionUID = 1L;

    private List<ManifestLine> manifestLineList = new ArrayList<ManifestLine>();

    public BuildManifestDataProvider() {
	setSort("manifestLineID", SortOrder.ASCENDING);
    }

    public void setManifestLineList(List<ManifestLine> manifestLineList) {
	if (manifestLineList == null){
	    this.manifestLineList.clear();
	    return;
	}
	this.manifestLineList = manifestLineList;
    }

    public void addManifestLine(ManifestLine manifestLine) {
	manifestLineList.add(manifestLine);
    }

    public void removeManifestLine(BigInteger manifestLineID) {
	if (manifestLineID != null){
	    int removeIdx = -1;
	    ManifestLine mfl;
	    for(int i = 0; i < manifestLineList.size(); i++){
		mfl = manifestLineList.get(i);
		if (ObjectUtils.equals(manifestLineID, mfl.getManifestLineID())){
		    removeIdx = i;
		    break;
		}
	    }
	    if (removeIdx >= 0){
		manifestLineList.remove(removeIdx);
	    }
	}
    }

    class SortableDataProviderComparator implements Comparator<ManifestLine>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(ManifestLine o1, ManifestLine o2) {
	    String sortProperty = getSort().getProperty();
	    PropertyModel<Object> model1, model2;
	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<ManifestLine> iterator(int first, int count) {
	List<ManifestLine> newList = new ArrayList<ManifestLine>(manifestLineList);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return manifestLineList.size();
    }

    @Override
    public IModel<ManifestLine> model(final ManifestLine object) {

	return new AbstractReadOnlyModel<ManifestLine>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public ManifestLine getObject() {
		return object;
	    }
	};
    }
}
