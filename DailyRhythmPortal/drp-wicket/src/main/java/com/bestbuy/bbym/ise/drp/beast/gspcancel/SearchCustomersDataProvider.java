package com.bestbuy.bbym.ise.drp.beast.gspcancel;

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

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class SearchCustomersDataProvider extends SortableDataProvider<Customer> {

    private static final long serialVersionUID = 1L;

    private List<Customer> customerList = new ArrayList<Customer>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public SearchCustomersDataProvider() {
	setSort("firstName", SortOrder.ASCENDING);
    }

    public void setCustomerList(List<Customer> customerList) {
	if (customerList == null){
	    this.customerList.clear();
	    return;
	}
	this.customerList = customerList;
    }

    public List<Customer> getCustomerList() {
	return customerList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<Customer>, Serializable {

	public int compare(final Customer o1, final Customer o2) {
	    String sortProperty = getSort().getProperty();

	    PropertyModel<Object> model1, model2;

	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends Customer> iterator(int first, int count) {
	List<Customer> newList = new ArrayList<Customer>(customerList);

	Collections.sort(newList, comparator);

	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return customerList.size();
    }

    @Override
    public IModel<Customer> model(final Customer object) {
	return new AbstractReadOnlyModel<Customer>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Customer getObject() {
		return (Customer) object;
	    }
	};
    }
}
