package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.Promotion;
import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("promotionService2")
public class DummyPromotionService implements PromotionService {

    @Override
    public List<RelatedPromotion> getRelatedPromotions(
	    HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap, int locationId)
	    throws ServiceException, IseBusinessException {
	List<RelatedPromotion> list = new ArrayList<RelatedPromotion>();

	for(int i = 0; i < 10; i++){
	    RelatedPromotion rp = new RelatedPromotion();
	    rp.setPromotion(this.makeDummyPromotion());
	    list.add(rp);
	}
	return list;
    }

    private Promotion makeDummyPromotion() {
	Promotion p = new Promotion();
	p.setPromotionCode(DummyData.getPromoCode());
	p.setPromotionDescription(DummyData.getPromoDesc());
	p.setPromotionValue(DummyData.getPromoValue());
	p.setPromotionContentURL("http://www.google.com");
	p.setPromotionEndDate(DummyData.getDate());
	return p;
    }

    @Override
    public void register(String promotionCode, String phoneNumber, String emailAddress, String fullName,
	    boolean allowContact) throws ServiceException, IseBusinessException {
	// TODO Implement me!
    }

}
