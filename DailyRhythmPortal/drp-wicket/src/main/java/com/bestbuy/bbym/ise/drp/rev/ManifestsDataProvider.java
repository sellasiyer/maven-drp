package com.bestbuy.bbym.ise.drp.rev;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class ManifestsDataProvider extends SortableDataProvider<Manifest> {

    private static final long serialVersionUID = 1L;

    private List<Manifest> manifestList = new ArrayList<Manifest>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public ManifestsDataProvider() {
	setSort("manifestID", SortOrder.DESCENDING);
    }

    public void setManifestList(List<Manifest> manifestList) {
	if (manifestList == null){
	    this.manifestList.clear();
	    return;
	}
	this.manifestList = manifestList;
    }

    public List<Manifest> getManifestList() {
	List<Manifest> newList = new ArrayList<Manifest>(manifestList);
	Collections.sort(newList, new SortableDataProviderComparator());
	return newList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<Manifest>, Serializable {

	public int compare(final Manifest o1, final Manifest o2) {
	    String sortProperty = getSort().getProperty();
	    PropertyModel<Object> model1 = new PropertyModel<Object>(o1, sortProperty);
	    PropertyModel<Object> model2 = new PropertyModel<Object>(o2, sortProperty);

	    if ("manifestID".equals(sortProperty)){
		return SortUtil.sortBigInteger((BigInteger) model1.getObject(), (BigInteger) model2.getObject(),
			getSort().isAscending());

	    }else if ("deviceCount".equals(sortProperty)){
		List<ManifestLine> list1 = null;
		if (o1 != null){
		    list1 = o1.getManifestLine();
		}
		List<ManifestLine> list2 = null;
		if (o2 != null){
		    list2 = o2.getManifestLine();
		}
		return SortUtil.sortCollectionSize(list1, list2, getSort().isAscending());

	    }else if ("trackingIdentifier".equals(sortProperty)){
		String status1 = null, status2 = null;
		PropertyModel<Object> st1 = new PropertyModel<Object>(o1, "status");
		PropertyModel<Object> st2 = new PropertyModel<Object>(o2, "status");
		if (st1 != null){
		    status1 = (String) st1.getObject();
		}
		if (st2 != null){
		    status2 = (String) st2.getObject();
		}
		if (StringUtils.equalsIgnoreCase(status1, "Open") && !StringUtils.equalsIgnoreCase(status2, "Open")){
		    return -1;
		}else if (!StringUtils.equalsIgnoreCase(status1, "Open")
			&& StringUtils.equalsIgnoreCase(status2, "Open")){
		    return 1;
		}

	    }else if ("dateTimeCreated".equals(sortProperty)){
		return SortUtil.sortDate((Date) model1.getObject(), (Date) model2.getObject(), getSort().isAscending());
	    }
	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends Manifest> iterator(int first, int count) {
	List<Manifest> newList = new ArrayList<Manifest>(manifestList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return manifestList.size();
    }

    @Override
    public IModel<Manifest> model(final Manifest object) {
	return new AbstractReadOnlyModel<Manifest>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Manifest getObject() {
		return (Manifest) object;
	    }
	};
    }
}
