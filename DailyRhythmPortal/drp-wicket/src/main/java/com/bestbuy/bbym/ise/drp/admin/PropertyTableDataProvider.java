package com.bestbuy.bbym.ise.drp.admin;

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

import com.bestbuy.bbym.ise.drp.domain.DrpProperty;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class PropertyTableDataProvider extends SortableDataProvider<DrpProperty> {

    private static final long serialVersionUID = 1L;

    private List<DrpProperty> propertyList;
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public PropertyTableDataProvider(List<DrpProperty> propertyList) {
	this.propertyList = propertyList;
	setSort("name", SortOrder.ASCENDING);
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<DrpProperty>, Serializable {

	public int compare(final DrpProperty o1, final DrpProperty o2) {
	    String sortProperty = getSort().getProperty();
	    PropertyModel<Object> model1 = new PropertyModel<Object>(o1, sortProperty);
	    PropertyModel<Object> model2 = new PropertyModel<Object>(o2, sortProperty);

	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends DrpProperty> iterator(int first, int count) {
	List<DrpProperty> newPropertyList = new ArrayList<DrpProperty>(propertyList);

	Collections.sort(newPropertyList, comparator);

	return newPropertyList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return propertyList.size();
    }

    @Override
    public IModel<DrpProperty> model(final DrpProperty object) {
	return new AbstractReadOnlyModel<DrpProperty>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public DrpProperty getObject() {
		return (DrpProperty) object;
	    }
	};
    }
}
