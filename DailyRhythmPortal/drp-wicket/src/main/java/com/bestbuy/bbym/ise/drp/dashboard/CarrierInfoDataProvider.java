package com.bestbuy.bbym.ise.drp.dashboard;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;

import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class CarrierInfoDataProvider extends SortableDataProvider<Line> {

    private static final long serialVersionUID = 1L;

    private List<Line> lineDataList = new ArrayList<Line>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();
    private String searchPhoneNum;

    public CarrierInfoDataProvider() {
	setSort("firstTimeSort", SortOrder.ASCENDING);
    }

    public void setLineDataList(List<Line> lineDataList) {
	if (lineDataList == null){
	    this.lineDataList.clear();
	    return;
	}
	this.lineDataList = lineDataList;
    }

    public List<Line> getLineDataList() {
	List<Line> newList = new ArrayList<Line>(lineDataList);
	Collections.sort(newList, new SortableDataProviderComparator());
	return newList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<Line>, Serializable {

	public int compare(final Line o1, final Line o2) {
	    String sortProperty = getSort().getProperty();

	    PropertyModel<Object> model1, model2;
	    if ("firstTimeSort".equals(sortProperty)){
		if (searchPhoneNum != null){
		    model1 = new PropertyModel<Object>(o1, "mobileNumber");
		    model2 = new PropertyModel<Object>(o2, "mobileNumber");
		    return SortUtil.sortStringSpecifiedValueAlwaysFirst((String) model1.getObject(), (String) model2
			    .getObject(), searchPhoneNum);
		}else{
		    setSort("mobileNumber", SortOrder.ASCENDING);
		    sortProperty = getSort().getProperty();
		}
	    }

	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    if ("device.tradeInValue".equals(sortProperty)){
		return SortUtil.sortBigDecimal((BigDecimal) model1.getObject(), (BigDecimal) model2.getObject(),
			getSort().isAscending());

	    }else if (sortProperty != null && sortProperty.endsWith("Date")){
		return SortUtil.sortDate((Date) model1.getObject(), (Date) model2.getObject(), getSort().isAscending());

		// Sort in reverse
	    }else if ("eligible".equals(sortProperty)){
		return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), !getSort()
			.isAscending());
	    }
	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends Line> iterator(int first, int count) {
	List<Line> newList = new ArrayList<Line>(lineDataList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return lineDataList.size();
    }

    @Override
    public IModel<Line> model(final Line object) {
	return new AbstractReadOnlyModel<Line>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Line getObject() {
		return (Line) object;
	    }
	};
    }

    public void setSearchPhoneNum(String searchPhoneNum) {
	this.searchPhoneNum = searchPhoneNum;
    }

    public String getSearchPhoneNum() {
	return searchPhoneNum;
    }
}
