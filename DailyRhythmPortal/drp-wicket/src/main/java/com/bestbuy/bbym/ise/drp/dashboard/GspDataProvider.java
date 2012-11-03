package com.bestbuy.bbym.ise.drp.dashboard;

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

import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class GspDataProvider extends SortableDataProvider<ServicePlan> {

    private static final long serialVersionUID = 1L;

    private List<ServicePlan> servicePlanList = new ArrayList<ServicePlan>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public GspDataProvider() {
	setSort("planType", SortOrder.ASCENDING);
    }

    public void setServicePlanList(List<ServicePlan> servicePlanList) {
	if (servicePlanList == null){
	    this.servicePlanList.clear();
	    return;
	}
	this.servicePlanList = servicePlanList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<ServicePlan>, Serializable {

	public int compare(final ServicePlan sp1, final ServicePlan sp2) {
	    // Always sorting in just one manner
	    return SortUtil.sortDate(sp1.getPlanEffectiveDate(), sp2.getPlanEffectiveDate(), false);
	}
    }

    @Override
    public Iterator<? extends ServicePlan> iterator(int first, int count) {
	List<ServicePlan> newList = new ArrayList<ServicePlan>(servicePlanList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return servicePlanList.size();
    }

    @Override
    public IModel<ServicePlan> model(final ServicePlan object) {
	return new AbstractReadOnlyModel<ServicePlan>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public ServicePlan getObject() {
		return (ServicePlan) object;
	    }
	};
    }
}
