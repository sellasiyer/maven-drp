package com.bestbuy.bbym.ise.drp.loanerphone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.domain.Phone;

public class InventoryPhoneDataProvider extends PhoneDataProvider {

    private static final long serialVersionUID = 1L;

    public InventoryPhoneDataProvider(List<Phone> phoneList) {
	super(phoneList);
	setSort("firstTimeSort", SortOrder.ASCENDING);
	setPhoneList(phoneList);
    }

    @Override
    public Iterator<? extends Phone> iterator(int first, int count) {
	List<Phone> newList = new ArrayList<Phone>(phoneList);
	Collections.sort(newList, comparator);

	return newList.subList(first, first + count).iterator();
    }

    class SortableDataProviderComparator implements Comparator<Phone>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(final Phone phn1, final Phone phn2) {
	    String sortProperty = getSort().getProperty();
	    Object ob1 = new PropertyModel<Object>(phn1, sortProperty).getObject();
	    Object ob2 = new PropertyModel<Object>(phn2, sortProperty).getObject();

	    //checking for nulls here.
	    if (ob1 == null && ob2 == null){
		return 0;
	    }else if (ob1 == null){
		return 1;
	    }else if (ob2 == null){
		return -1;
	    }
	    return 0;
	}
    }

} //end of class
