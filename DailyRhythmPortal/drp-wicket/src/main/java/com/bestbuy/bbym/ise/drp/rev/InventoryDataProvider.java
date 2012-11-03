package com.bestbuy.bbym.ise.drp.rev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

public class InventoryDataProvider extends SortableDataProvider<ManifestLine> {

    private static final long serialVersionUID = 1L;

    private List<ManifestLine> inventoryList = new ArrayList<ManifestLine>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public InventoryDataProvider() {
	setSort("itemTag", SortOrder.DESCENDING);
    }

    public void setInventoryList(List<ManifestLine> inventoryList) {
	if (inventoryList == null){
	    this.inventoryList.clear();
	    return;
	}
	this.inventoryList = inventoryList;
    }

    public List<ManifestLine> getInventoryList() {
	List<ManifestLine> newList = new ArrayList<ManifestLine>(inventoryList);
	Collections.sort(newList, new SortableDataProviderComparator());
	return newList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<ManifestLine>, Serializable {

	public int compare(final ManifestLine o1, final ManifestLine o2) {
	    String sortProperty = getSort().getProperty();
	    PropertyModel<Object> model1 = new PropertyModel<Object>(o1, sortProperty);
	    PropertyModel<Object> model2 = new PropertyModel<Object>(o2, sortProperty);

	    if ("deviceStatus".equals(sortProperty)){
		if (o1 != null && o2 != null){
		    if (o1.isShort() && !o2.isShort()){
			if (getSort().isAscending()){
			    return -1;
			}else{
			    return 1;
			}
		    }else if (!o1.isShort() && o2.isShort()){
			if (getSort().isAscending()){
			    return 1;
			}else{
			    return -1;
			}
		    }
		}
	    }

	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends ManifestLine> iterator(int first, int count) {
	List<ManifestLine> newList = new ArrayList<ManifestLine>(inventoryList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return inventoryList.size();
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
