package com.bestbuy.bbym.ise.drp.admin.hotlinks;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.sort.SortOrder;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import com.bestbuy.bbym.ise.drp.domain.Hotlink;

public class HotlinkTableDataProvider extends SortableDataProvider<Hotlink> {

    private static final long serialVersionUID = 1L;

    private List<Hotlink> hotlinkList;

    public HotlinkTableDataProvider(List<Hotlink> hotlinkList) {
	this.hotlinkList = hotlinkList;
	setSort("url", SortOrder.ASCENDING);
    }

    @Override
    public Iterator<? extends Hotlink> iterator(int first, int count) {
	List<Hotlink> newHotlinkList = new ArrayList<Hotlink>(hotlinkList);
	return newHotlinkList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return hotlinkList.size();
    }

    @Override
    public IModel<Hotlink> model(final Hotlink object) {
	return new AbstractReadOnlyModel<Hotlink>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Hotlink getObject() {
		return (Hotlink) object;
	    }
	};
    }

    public void removeRow(Hotlink hotlink) {
	hotlinkList.remove(hotlink);
    }

}
