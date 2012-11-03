package com.bestbuy.bbym.ise.drp.entitlement;

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

import com.bestbuy.bbym.ise.drp.domain.RetExchRZLookupCustomer;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class RewardZoneLookupDataProvider extends SortableDataProvider<RetExchRZLookupCustomer> {

    private static final long serialVersionUID = 1L;

    private List<RetExchRZLookupCustomer> customerList = new ArrayList<RetExchRZLookupCustomer>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public RewardZoneLookupDataProvider() {
	setSort("firstName", SortOrder.ASCENDING);
    }

    public void setCustomerList(List<RetExchRZLookupCustomer> customerList) {
	if (customerList == null){
	    this.customerList.clear();
	    return;
	}
	this.customerList = customerList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<RetExchRZLookupCustomer>, Serializable {

	public int compare(final RetExchRZLookupCustomer o1, final RetExchRZLookupCustomer o2) {
	    String sortProperty = getSort().getProperty();

	    PropertyModel<Object> model1, model2;

	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends RetExchRZLookupCustomer> iterator(int first, int count) {
	List<RetExchRZLookupCustomer> newList = new ArrayList<RetExchRZLookupCustomer>(customerList);

	Collections.sort(newList, comparator);

	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return customerList.size();
    }

    @Override
    public IModel<RetExchRZLookupCustomer> model(final RetExchRZLookupCustomer object) {
	return new AbstractReadOnlyModel<RetExchRZLookupCustomer>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public RetExchRZLookupCustomer getObject() {
		return (RetExchRZLookupCustomer) object;
	    }
	};
    }

}
