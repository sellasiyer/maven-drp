package com.bestbuy.bbym.ise.drp.customer;

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

import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class SearchRewardZoneDataProvider extends SortableDataProvider<RewardZone> {

    private static final long serialVersionUID = 1L;

    private List<RewardZone> rzList = new ArrayList<RewardZone>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public SearchRewardZoneDataProvider() {
	setSort("memberNumber", SortOrder.ASCENDING);
    }

    public void setRewardZoneList(List<RewardZone> rzList) {
	if (rzList == null){
	    this.rzList.clear();
	    return;
	}
	this.rzList = rzList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<RewardZone>, Serializable {

	public int compare(final RewardZone o1, final RewardZone o2) {
	    String sortProperty = getSort().getProperty();

	    PropertyModel<Object> model1, model2;

	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    if ("pointsBalance".equals(sortProperty)){
		return SortUtil.sortInteger((Integer) model1.getObject(), (Integer) model2.getObject(), getSort()
			.isAscending());
	    }else{
		return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
			.isAscending());
	    }

	}
    }

    @Override
    public Iterator<? extends RewardZone> iterator(int first, int count) {
	List<RewardZone> newList = new ArrayList<RewardZone>(rzList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return rzList.size();
    }

    @Override
    public IModel<RewardZone> model(final RewardZone object) {
	return new AbstractReadOnlyModel<RewardZone>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public RewardZone getObject() {
		return (RewardZone) object;
	    }
	};
    }
}
