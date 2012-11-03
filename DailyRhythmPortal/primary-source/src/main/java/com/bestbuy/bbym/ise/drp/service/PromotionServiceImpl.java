package com.bestbuy.bbym.ise.drp.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.drp.sao.PromotionServiceSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("promotionService")
public class PromotionServiceImpl implements PromotionService {

    @Autowired
    private PromotionServiceSao promotionServiceSao;

    @Override
    public List<RelatedPromotion> getRelatedPromotions(
	    HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap, int locationId)
	    throws ServiceException, IseBusinessException {
	return promotionServiceSao.getRelatedPromotions(searchParamMap, locationId);
    }

    @Override
    public void register(String promotionCode, String phoneNumber, String emailAddress, String fullName,
	    boolean allowContact) throws ServiceException, IseBusinessException {
	promotionServiceSao.register(promotionCode, phoneNumber, emailAddress, fullName, allowContact);
    }

    public void setSao(PromotionServiceSao promotionServiceSao) {
	this.promotionServiceSao = promotionServiceSao;
    }

}
