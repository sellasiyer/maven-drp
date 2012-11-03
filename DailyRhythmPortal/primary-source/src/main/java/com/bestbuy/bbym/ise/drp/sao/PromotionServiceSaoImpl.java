package com.bestbuy.bbym.ise.drp.sao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.Promotion;
import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientpromo.DuplicatePhoneNumberException_Exception;
import com.bestbuy.bbym.ise.iseclientpromo.GetRelatedPromotionsRequestType;
import com.bestbuy.bbym.ise.iseclientpromo.GetRelatedPromotionsResponseType;
import com.bestbuy.bbym.ise.iseclientpromo.InternationalBusinessHierarchy;
import com.bestbuy.bbym.ise.iseclientpromo.InvalidArgumentException_Exception;
import com.bestbuy.bbym.ise.iseclientpromo.RegisterRequestType;
import com.bestbuy.bbym.ise.iseclientpromo.SearchParameter;
import com.bestbuy.bbym.ise.iseclientpromo.SearchParameterType;
import com.bestbuy.bbym.ise.iseclientpromo.UnknownFailureException_Exception;
import com.bestbuy.bbym.ise.util.Util;

@Service("promotionServiceSao")
public class PromotionServiceSaoImpl extends AbstractSao implements PromotionServiceSao {

    private static Logger logger = LoggerFactory.getLogger(PromotionServiceSaoImpl.class);
    private static final String SERVICE = "PROMOTION SERVICE ";

    @Override
    public List<RelatedPromotion> getRelatedPromotions(
	    HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap, int locationId)
	    throws ServiceException, IseBusinessException {

	logger.info("Invoking the Promotion Service");

	List<RelatedPromotion> relatedPromotions = new ArrayList<RelatedPromotion>();
	GetRelatedPromotionsRequestType requestType = preparePromoServiceRequestType(searchParamMap, locationId);

	try{

	    GetRelatedPromotionsResponseType promotionResponse = getPromotionService()
		    .getRelatedPromotions(requestType);
	    relatedPromotions = getPromotionDataList(promotionResponse);

	}catch(InvalidArgumentException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Promotion Service exception - getRelatedPromotions: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Promotion Service - getRelatedPromotions: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - getRelatedPromotions", t);
	}

	return relatedPromotions;
    }

    private List<RelatedPromotion> getPromotionDataList(GetRelatedPromotionsResponseType promotionResponse) {

	List<com.bestbuy.bbym.ise.iseclientpromo.RelatedPromotion> promoList = promotionResponse.getRelatedPromotions();
	List<RelatedPromotion> promotionResult = new ArrayList<RelatedPromotion>();

	for(com.bestbuy.bbym.ise.iseclientpromo.RelatedPromotion relatedPromotion: promoList){
	    RelatedPromotion relatedPromotions = new RelatedPromotion();
	    Promotion promotion = new Promotion();
	    if (relatedPromotion != null){
		if (relatedPromotion.isPromotionAvailable()){
		    relatedPromotions
			    .setPromotionAvailabilityType((RelatedPromotion.PromotionAvailabilityType.AVAILABLE));
		}else{
		    relatedPromotions
			    .setPromotionAvailabilityType((RelatedPromotion.PromotionAvailabilityType.UNAVAILABLE));
		}
		relatedPromotions.setReason(relatedPromotion.getStatus());
		if (relatedPromotion.getPromotion() != null){
		    relatedPromotions.setPromotion(populatePromotion(relatedPromotion.getPromotion()));
		    promotion.setPromotionBeginDate(Util.toUtilDate(relatedPromotion.getPromotion()
			    .getPromotionBeginDate()));
		    promotion.setPromotionEndDate(Util
			    .toUtilDate(relatedPromotion.getPromotion().getPromotionEndDate()));
		}

	    }
	    promotionResult.add(relatedPromotions);
	}
	return promotionResult;
    }

    private Promotion populatePromotion(com.bestbuy.bbym.ise.iseclientpromo.Promotion servPromotion) {
	Promotion promotion = new Promotion();
	promotion.setPromotionID(servPromotion.getPromotionID());
	promotion.setPromotionBeginDate(Util.toUtilDate(servPromotion.getPromotionBeginDate()));
	promotion.setPromotionEndDate(Util.toUtilDate(servPromotion.getPromotionEndDate()));
	promotion.setPromotionCode(servPromotion.getPromotionCode());
	promotion.setPromotionDescription(servPromotion.getPromotionDescription());
	promotion.setPromotionSKU(servPromotion.getPromotionSKU());
	if (servPromotion.getPromotionValue() != null){
	    promotion.setPromotionValue(servPromotion.getPromotionValue().toString());
	}

	return promotion;
    }

    private GetRelatedPromotionsRequestType preparePromoServiceRequestType(
	    HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap, int locationId)
	    throws ServiceException {
	logger.info("Assigning the PROMO request data");

	GetRelatedPromotionsRequestType requestType = new GetRelatedPromotionsRequestType();
	requestType.getSearchStrings().addAll(convertToSearchParameterList(searchParamMap));
	requestType.setLocationId(locationId);
	requestType
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchy()));
	return requestType;
    }

    @Override
    public void register(String promotionCode, String phoneNumber, String emailAddress, String fullName,
	    boolean allowContact) throws ServiceException, IseBusinessException {

	RegisterRequestType registerRequest = new RegisterRequestType();
	registerRequest.setPhoneNumber(phoneNumber);
	registerRequest.setEmailAddress(emailAddress);
	registerRequest.setPromotionCode(promotionCode);
	registerRequest.setAllowContact(allowContact);
	registerRequest.setCustomerIPAddress(Util.getIPAddress());
	registerRequest
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchy()));

	try{
	    getPromotionService().register(registerRequest);
	}catch(DuplicatePhoneNumberException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Promotion Service exception - register : " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidArgumentException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Promotion Service exception - register : " + faultMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Promotion Service exception - register : " + faultMessage);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - register ", t);
	}
    }

    private List<SearchParameter> convertToSearchParameterList(
	    HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap) {

	List<SearchParameter> searchParameterTypeList = new ArrayList<SearchParameter>();

	final Iterator<Map.Entry<RelatedPromotion.SearchParameterType, String>> mapIterator = searchParamMap.entrySet()
		.iterator();

	while(mapIterator.hasNext()){

	    SearchParameter searchParameter = new SearchParameter();

	    Map.Entry<RelatedPromotion.SearchParameterType, String> statusType = (Map.Entry<RelatedPromotion.SearchParameterType, String>) mapIterator
		    .next();

	    if (statusType != null){
		if (statusType.getKey() != null
			&& statusType.getKey() == RelatedPromotion.SearchParameterType.ACTIVATION_PHONE_NUMBER){
		    searchParameter.setType(SearchParameterType.ACTIVATION_PHONE_NUMBER);
		    searchParameter.setValue(statusType.getValue());
		}else if (statusType.getKey() != null
			&& statusType.getKey() == RelatedPromotion.SearchParameterType.DEVICE_SKU){
		    searchParameter.setType(SearchParameterType.DEVICE_SKU);
		    searchParameter.setValue(statusType.getValue());
		}else if (statusType.getKey() != null
			&& statusType.getKey() == RelatedPromotion.SearchParameterType.EMAIL_ADDRESS){
		    searchParameter.setType(SearchParameterType.EMAIL_ADDRESS);
		    searchParameter.setValue(statusType.getValue());
		}else if (statusType.getKey() != null
			&& statusType.getKey() == RelatedPromotion.SearchParameterType.ORDER_TYPE){
		    searchParameter.setType(SearchParameterType.ORDER_TYPE);
		    searchParameter.setValue(statusType.getValue());
		}else if (statusType.getKey() != null
			&& statusType.getKey() == RelatedPromotion.SearchParameterType.PROMOTION_CODE){
		    searchParameter.setType(SearchParameterType.PROMOTION_CODE);
		    searchParameter.setValue(statusType.getValue());
		}else if (statusType.getKey() != null
			&& statusType.getKey() == RelatedPromotion.SearchParameterType.TRADEIN_SKU){
		    searchParameter.setType(SearchParameterType.TRADEIN_SKU);
		    searchParameter.setValue(statusType.getValue());
		}
	    }
	    searchParameterTypeList.add(searchParameter);
	}

	logger.debug("searchParameterTypeList ::  " + searchParameterTypeList);
	return searchParameterTypeList;

    }

}
