package com.bestbuy.bbym.ise.drp.triage2;

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

import com.bestbuy.bbym.ise.drp.domain.Config;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class DeviceDataProvider extends SortableDataProvider<Config> {

    private static final long serialVersionUID = 1L;

    private List<Config> deviceList = new ArrayList<Config>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public DeviceDataProvider() {
	setSort("description", SortOrder.ASCENDING);
    }

    public void setDeviceList(List<Config> deviceList) {
	if (deviceList == null){
	    this.deviceList.clear();
	    return;
	}
	this.deviceList = deviceList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<Config>, Serializable {

	public int compare(final Config o1, final Config o2) {
	    String sortProperty = getSort().getProperty();
	    PropertyModel<Object> model1, model2;
	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);
	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends Config> iterator(int first, int count) {
	List<Config> newList = new ArrayList<Config>(deviceList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return deviceList.size();
    }

    @Override
    public IModel<Config> model(final Config object) {
	return new AbstractReadOnlyModel<Config>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Config getObject() {
		return (Config) object;
	    }
	};
    }
}
