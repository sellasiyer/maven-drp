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
import com.bestbuy.bbym.ise.drp.domain.OperatingSystem;
import com.bestbuy.bbym.ise.drp.domain.PhoneModel;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class PhoneModelDataProvider extends SortableDataProvider<PhoneModel> implements
	ISortableDataProvider<PhoneModel> {

    private static final long serialVersionUID = 1L;

    private List<PhoneModel> phones;

    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public PhoneModelDataProvider(List<PhoneModel> phones) {
	super();
	setSort("firstTimeSort", SortOrder.ASCENDING);
	setPhoneList(phones);
    }

    @Override
    public Iterator<? extends PhoneModel> iterator(int first, int count) {
	List<PhoneModel> newList = new ArrayList<PhoneModel>(phones);
	Collections.sort(newList, comparator);

	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return phones.size();
    }

    @Override
    public IModel<PhoneModel> model(PhoneModel object) {
	return new Model<PhoneModel>(object);
    }

    public void setPhoneList(List<PhoneModel> phones) {
	this.phones = phones;
    }

    class SortableDataProviderComparator implements Comparator<PhoneModel>, Serializable {

	private static final long serialVersionUID = 1L;

	@Override
	public int compare(PhoneModel o1, PhoneModel o2) {
	    String str1 = null;
	    String str2 = null;
	    String sortProperty = getSort().getProperty();
	    if ("firstTimeSort".equals(sortProperty)){
		return 0; // initial sort is done by db.
	    }

	    PropertyModel<Object> model1, model2;

	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    if (sortProperty.equals("carrier")){
		str1 = ((Carrier) model1.getObject()).getCarrier().toUpperCase();
		str2 = ((Carrier) model2.getObject()).getCarrier().toUpperCase();
	    }else if (sortProperty.equals("os")){
		str1 = ((OperatingSystem) model1.getObject()).getOs();
		str2 = ((OperatingSystem) model2.getObject()).getOs();
	    }else if (model1.getObject().getClass().equals(Integer.class)){
		Integer int1 = (Integer) model1.getObject();
		Integer int2 = (Integer) model2.getObject();
		return SortUtil.sortInteger(int1, int2, getSort().isAscending());
	    }else{
		str1 = ((String) model1.getObject()).toUpperCase();
		str2 = ((String) model2.getObject()).toUpperCase();
	    }

	    return SortUtil.sortString(str1, str2, getSort().isAscending());
	}

    }

}
