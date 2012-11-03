package com.bestbuy.bbym.ise.drp.msw;

import org.apache.wicket.model.IModel;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.Recommendation;
import java.util.Date;

public class DigitalRecSheetModel implements IModel<Recommendation> {

    private static final long serialVersionUID = -4841033315203516593L;
    private Recommendation recommendation = null;
    private DrpUser user;

    public DigitalRecSheetModel(Recommendation recSheet) {
	this.recommendation = recSheet;
	if (this.recommendation == null)
	    this.recommendation = new Recommendation();
	if (null == this.recommendation.getCreatedOn()){
	    this.recommendation.setCreatedOn(new Date());
	}
    }

    public Recommendation getRecommendation() {
	return this.recommendation;
    }

    @Override
    public void detach() {
	recommendation = null;
    }

    @Override
    public Recommendation getObject() {
	return recommendation;
    }

    @Override
    public void setObject(Recommendation object) {
	recommendation = object;

    }

    public void setUser(DrpUser user) {
	this.user = user;
    }

    public DrpUser getUser() {
	return user;
    }
}
