package com.bestbuy.bbym.ise.drp.loanerphone;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.data.table.ISortableDataProvider;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.drp.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CheckOutCheckInHistory;
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.Phone;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class PhoneDataProvider extends SortableDataProvider<Phone> implements ISortableDataProvider<Phone> {

    private static final long serialVersionUID = 1L;
    protected List<Phone> phoneList;

    protected SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public PhoneDataProvider(List<Phone> phoneList) {
	super();
	setSort("firstTimeSort", SortOrder.ASCENDING);
	setPhoneList(phoneList);
    }

    @Override
    public Iterator<? extends Phone> iterator(int first, int count) {
	List<Phone> newList = new ArrayList<Phone>(phoneList);
	Collections.sort(newList, comparator);

	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return phoneList.size();
    }

    @Override
    public IModel<Phone> model(Phone object) {
	return new Model<Phone>(object);
    }

    public void setPhoneList(List<Phone> phones) {
	this.phoneList = phones;
    }

    public List<Phone> getPhoneList() {
	return this.phoneList;
    }

    class SortableDataProviderComparator implements Comparator<Phone>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(final Phone phn1, final Phone phn2) {
	    String str1;
	    String str2;
	    String sortProperty = getSort().getProperty();
	    if ("firstTimeSort".equals(sortProperty)){
		return 0; // initial sort is done by db.
	    }else if ("latestCheckOutCheckInHistory.lastName".equals(sortProperty)){
		//this is a special case, so for now just grab history and send off to extraction.
		sortProperty = "latestCheckOutCheckInHistory";
	    }

	    // setup objects and check for nulls.
	    Object ob1 = new PropertyModel<Object>(phn1, sortProperty).getObject();
	    Object ob2 = new PropertyModel<Object>(phn2, sortProperty).getObject();

	    if (ob1 == null && ob2 == null){
		return 0;
	    }else if (ob1 == null){
		return 1;
	    }else if (ob2 == null){
		return -1;
	    }

	    if (Number.class.isInstance(ob1)){
		Double d1 = ((Number) ob1).doubleValue();
		Double d2 = ((Number) ob2).doubleValue();
		return SortUtil.sortDouble(d1, d2, getSort().isAscending());
	    }

	    str1 = this.extractString(ob1, getSort().getProperty());
	    str2 = this.extractString(ob2, getSort().getProperty());

	    return SortUtil.sortString(str1, str2, getSort().isAscending());
	}

	/**
	 * Objects have vastly different ways of sorting; This function extracts
	 * a string from an object for easier sorting.
	 * 
	 * @param obj
	 *            - The object to convert into a string.
	 * @param sortProperty
	 *            - Used to identify what column is being asked for.
	 * @return The extracted String
	 */
	protected String extractString(Object obj, String sortProperty) {
	    String str = null;

	    if (sortProperty.equals("carrier")){
		str = ((Carrier) obj).getCarrier().toUpperCase();

	    }else if (sortProperty.equals("operatingSystem")){
		str = ((OperatingSystem) obj).getOs();
	    }else if ("latestCheckOutCheckInHistory.lastName".equals(sortProperty)){

		//I alter comparator to send the check out history here instead of lastname if you were confused.
		//I am writing the logic to treat a customer w/ non-checked out phones as blank string,
		// ... as we do not display cust name for non-checked out phones.
		CheckOutCheckInHistory chist = (CheckOutCheckInHistory) obj;
		str = chist.getLastName();
		if (chist == null || (chist != null && !chist.isCheckedOut())){
		    str = "zzzzzzz"; //hack to make it go to the bottom. string compare puts blank at the top.
		}
	    }else{
		str = ((String) obj).toUpperCase();
	    }

	    return str.toUpperCase();
	}

    } // end of comparator
}
