package com.bestbuy.bbym.ise.drp.promotion;

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

import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.drp.utils.SortUtil;

public class SearchPromotionDataProvider extends SortableDataProvider<RelatedPromotion> {

    private static final long serialVersionUID = 1L;

    private List<RelatedPromotion> promotionList = new ArrayList<RelatedPromotion>();
    private SortableDataProviderComparator comparator = new SortableDataProviderComparator();

    public SearchPromotionDataProvider() {
	setSort("promotionAvailabilityType", SortOrder.ASCENDING);
    }

    public void setRelatedPromotionList(List<RelatedPromotion> promotionList) {
	if (promotionList == null){
	    this.promotionList.clear();
	    return;
	}
	this.promotionList = promotionList;
    }

    public List<RelatedPromotion> getRelatedPromotionList() {
	return promotionList;
    }

    @SuppressWarnings("serial")
    class SortableDataProviderComparator implements Comparator<RelatedPromotion>, Serializable {

	public int compare(final RelatedPromotion o1, final RelatedPromotion o2) {
	    String sortProperty = getSort().getProperty();

	    PropertyModel<Object> model1, model2;

	    model1 = new PropertyModel<Object>(o1, sortProperty);
	    model2 = new PropertyModel<Object>(o2, sortProperty);

	    if (sortProperty != null && sortProperty.endsWith("Date")){
		return SortUtil.sortDate((Date) model1.getObject(), (Date) model2.getObject(), getSort().isAscending());

		// Sort in reverse
	    }else if (sortProperty != null && sortProperty.equals("promotionAvailabilityType")){
		return SortUtil.sortString((String) ((RelatedPromotion.PromotionAvailabilityType) model1.getObject())
			.toString(), (String) ((RelatedPromotion.PromotionAvailabilityType) model2.getObject())
			.toString(), getSort().isAscending());
	    }

	    return SortUtil.sortString((String) model1.getObject(), (String) model2.getObject(), getSort()
		    .isAscending());
	}
    }

    @Override
    public Iterator<? extends RelatedPromotion> iterator(int first, int count) {
	List<RelatedPromotion> newList = new ArrayList<RelatedPromotion>(promotionList);

	Collections.sort(newList, comparator);

	return newList.subList(first, first + count).iterator();
    }

    @Override
    public int size() {
	return promotionList.size();
    }

    @Override
    public IModel<RelatedPromotion> model(final RelatedPromotion object) {
	return new AbstractReadOnlyModel<RelatedPromotion>() {

	    private static final long serialVersionUID = 1L;

	    @Override
	    public RelatedPromotion getObject() {
		return (RelatedPromotion) object;
	    }
	};
    }
}
