package com.bestbuy.bbym.ise.drp.service;

import java.util.HashMap;
import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface PromotionService {

    public List<RelatedPromotion> getRelatedPromotions(
	    HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap, int locationId)
	    throws ServiceException, IseBusinessException;

    public void register(String promotionCode, String phoneNumber, String emailAddress, String fullName,
	    boolean allowContact) throws ServiceException, IseBusinessException;

}
