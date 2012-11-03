package com.bestbuy.bbym.ise.drp.beast.scheduler;

import java.io.Serializable;
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

import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class ManageAppointmentsDataProvider extends SortableDataProvider<Appointment> {

    private static final long serialVersionUID = 1L;

    private List<Appointment> schedulerList = new ArrayList<Appointment>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public ManageAppointmentsDataProvider() {
	setSort("appointmentId", SortOrder.ASCENDING);
    }

    public void setSchedulerDataList(List<Appointment> schedulerList) {
	if (schedulerList == null){
	    this.schedulerList.clear();
	    return;
	}
	this.schedulerList = schedulerList;
    }

    class SortableDataProviderComparator implements Comparator<Appointment>, Serializable {

	private static final long serialVersionUID = 1L;

	public int compare(final Appointment o1, final Appointment o2) {

	    String sortProperty = getSort().getProperty();

	    PropertyModel<Object> model1, model2;

	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    if (sortProperty != null && sortProperty.equals("appointmentDateTime")){
		return SortUtil.sortDate((Date) model1.getObject(), (Date) model2.getObject(), getSort().isAscending());
	    }else if (sortProperty != null && sortProperty.equals("currentStatus")){
		return SortUtil.sortInteger((Integer) model1.getObject(), (Integer) model2.getObject(), getSort()
			.isAscending());
	    }else if (sortProperty != null && sortProperty.equals("reasonType")){
		return 0;
	    }else{
		return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
			.isAscending());
	    }
	}
    }

    @Override
    public Iterator<? extends Appointment> iterator(int first, int count) {
	List<Appointment> newList = new ArrayList<Appointment>(schedulerList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return schedulerList.size();
    }

    @Override
    public IModel<Appointment> model(final Appointment object) {
	return new AbstractReadOnlyModel<Appointment>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Appointment getObject() {
		return (Appointment) object;
	    }
	};
    }
}
