package com.bestbuy.bbym.ise.drp.entitlement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import com.bestbuy.bbym.ise.drp.domain.Entitlement;

public class ReturnExchangeDataProvider extends SortableDataProvider<Entitlement> {

    private static final long serialVersionUID = 1L;
    private List<Entitlement> returnExchangeDataList = new ArrayList<Entitlement>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public ReturnExchangeDataProvider(List<Entitlement> returnExchangeDataList) {
	super();
	setSort("displayText", SortOrder.ASCENDING); //mapped to PropertyColumn definition. What is 'firstTimeSort' used elsewhere?
	setReturnExchangeDataList(returnExchangeDataList);
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<Entitlement>, Serializable {

	public int compare(final Entitlement o1, final Entitlement o2) {
	    /*
	     * String sortProperty = getSort().getProperty();
	     * 
	     * PropertyModel<Object> model1, model2; 
	     * if
	     * ("firstTimeSort".equals(sortProperty)){ if (searchPhoneNum !=
	     * null){ model1 = new PropertyModel<Object>(o1, "mobileNumber");
	     * model2 = new PropertyModel<Object>(o2, "mobileNumber"); return
	     * SortUtil.sortStringSpecifiedValueAlwaysFirst((String)
	     * model1.getObject(), (String) model2 .getObject(),
	     * searchPhoneNum); }else{ setSort("mobileNumber", true);
	     * sortProperty = getSort().getProperty(); } }
	     * 
	     * model1 = new PropertyModel<Object>(o1, sortProperty); model2 =
	     * new PropertyModel<Object>(o2, sortProperty);
	     * 
	     * if ("device.tradeInValue".equals(sortProperty)){ return
	     * SortUtil.sortBigDecimal((BigDecimal) model1.getObject(),
	     * (BigDecimal) model2.getObject(), getSort().isAscending());
	     * 
	     * }else if (sortProperty != null && sortProperty.endsWith("Date")){
	     * return SortUtil.sortDate((Date) model1.getObject(), (Date)
	     * model2.getObject(), getSort().isAscending());
	     * 
	     * // Sort in reverse }else if ("eligible".equals(sortProperty)){
	     * return SortUtil.sortString((String) model1.getObject(), (String)
	     * model2.getObject(), !getSort() .isAscending()); } return
	     * SortUtil.sortString((String) model1.getObject(), (String)
	     * model2.getObject(), getSort() .isAscending());
	     */
	    return 0; // do later
	}
    }

    @Override
    public Iterator<? extends Entitlement> iterator(int first, int count) {
	List<Entitlement> newList = new ArrayList<Entitlement>(returnExchangeDataList);
	/* Collections.sort(newList, comparator); */
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return returnExchangeDataList.size();
    }

    @Override
    public IModel<Entitlement> model(final Entitlement object) {
	return new AbstractReadOnlyModel<Entitlement>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Entitlement getObject() {
		return (Entitlement) object;
	    }
	};
    }

    public List<Entitlement> getReturnExchangeDataList() {
	return returnExchangeDataList;
    }

    public void setReturnExchangeDataList(List<Entitlement> returnExchangeDataList) {
	this.returnExchangeDataList = returnExchangeDataList;
    }

}
