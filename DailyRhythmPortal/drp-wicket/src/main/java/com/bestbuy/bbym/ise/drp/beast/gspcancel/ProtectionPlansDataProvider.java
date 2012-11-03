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

import com.bestbuy.bbym.ise.domain.ProtectionPlan;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class ProtectionPlansDataProvider extends SortableDataProvider<ProtectionPlan> {

    private static final long serialVersionUID = 1L;

    private List<ProtectionPlan> gspPlanList = new ArrayList<ProtectionPlan>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public ProtectionPlansDataProvider() {
	setSort("planEffectiveDate", SortOrder.DESCENDING);
    }

    public void setGspPlanList(List<ProtectionPlan> gspPlanList) {
	if (gspPlanList == null){
	    this.gspPlanList.clear();
	    return;
	}
	this.gspPlanList = gspPlanList;
    }

    public List<ProtectionPlan> getGspPlanList() {
	return gspPlanList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<ProtectionPlan>, Serializable {

	public int compare(final ProtectionPlan o1, final ProtectionPlan o2) {
	    String st1 = o1.getPlanStatus();
	    String st2 = o2.getPlanStatus();
	    if (st1 == null && st2 == null){
		return 0;
	    }else if (st1 == null){
		return 1;
	    }else if (st2 == null){
		return -1;
	    }else if (ServicePlan.PLAN_STATUS_ACTIVE.equals(st1) && !ServicePlan.PLAN_STATUS_ACTIVE.equals(st2)){
		return -1;
	    }else if (!ServicePlan.PLAN_STATUS_ACTIVE.equals(st1) && ServicePlan.PLAN_STATUS_ACTIVE.equals(st2)){
		return 1;
	    }
	    return SortUtil.sortDate(o1.getPlanEffectiveDate(), o2.getPlanEffectiveDate(), false);
	}
    }

    @Override
    public Iterator<? extends ProtectionPlan> iterator(int first, int count) {
	List<ProtectionPlan> newList = new ArrayList<ProtectionPlan>(gspPlanList);

	Collections.sort(newList, comparator);

	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return gspPlanList.size();
    }

    @Override
    public IModel<ProtectionPlan> model(final ProtectionPlan object) {
	return new AbstractReadOnlyModel<ProtectionPlan>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public ProtectionPlan getObject() {
		return (ProtectionPlan) object;
	    }
	};
    }
}
