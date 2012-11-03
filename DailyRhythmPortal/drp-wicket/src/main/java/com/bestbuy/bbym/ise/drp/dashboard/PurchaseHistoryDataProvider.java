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

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class PurchaseHistoryDataProvider extends SortableDataProvider<Product> {

    private static final long serialVersionUID = 1L;

    private List<Product> purchaseHistoryList = new ArrayList<Product>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public PurchaseHistoryDataProvider() {
	setSort("transactionType", SortOrder.ASCENDING);
    }

    public void setPurchaseHistoryList(List<Product> purchaseHistoryList) {
	if (purchaseHistoryList == null){
	    this.purchaseHistoryList.clear();
	    return;
	}
	this.purchaseHistoryList = purchaseHistoryList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<Product>, Serializable {

	public int compare(final Product p1, final Product p2) {
	    return SortUtil.sortDate(p1.getPurchaseDate(), p2.getPurchaseDate(), false);
	}
    }

    @Override
    public Iterator<? extends Product> iterator(int first, int count) {
	List<Product> newList = new ArrayList<Product>(purchaseHistoryList);
	Collections.sort(newList, comparator);
	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return purchaseHistoryList.size();
    }

    @Override
    public IModel<Product> model(final Product object) {
	return new AbstractReadOnlyModel<Product>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public Product getObject() {
		return (Product) object;
	    }
	};
    }
}
