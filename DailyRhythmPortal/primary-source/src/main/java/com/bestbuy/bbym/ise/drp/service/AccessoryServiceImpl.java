package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("accessoryService")
public class AccessoryServiceImpl implements AccessoryService {

    private static final String CASE_SUBCLASS_IDS = "ACCESSORY_CASE_SUBCLASS_IDS";

    private static final String CHARGER_SUBCLASS_IDS = "ACCESSORY_CHARGER_SUBCLASS_IDS";

    private static final String SHIELD_SUBCLASS_IDS = "ACCESSORY_SHIELD_SUBCLASS_IDS";

    private static final String CASE_MAX_NOS = "ACCESSORY_CASE_MAX_NOS";

    private static final String CHARGER_MAX_NOS = "ACCESSORY_CHARGER_MAX_NOS";

    private static final String SHIELD_MAX_NOS = "ACCESSORY_SHIELD_MAX_NOS";

    private static Logger logger = LoggerFactory.getLogger(AccessoryServiceImpl.class);

    @Autowired
    private SKUService skuService;

    @Autowired
    @Qualifier("drpPropertyService")
    protected DrpPropertyService drpPropertiesService;

    @Override
    public Map<String, Sku> getAccessories(String storeId, Map<String, Sku> phoneNumberSkuMap) throws ServiceException,
	    IseBusinessException {

	try{
	    // Get the product SKU information
	    Set<String> distinctProductSkus = new HashSet<String>();
	    for(Sku productSku: phoneNumberSkuMap.values()){
		distinctProductSkus.add(productSku.getSku());
	    }
	    List<Sku> skuAndAccessories = skuService.getSkuAndAccessories(distinctProductSkus
		    .toArray(new String[distinctProductSkus.size()]));

	    // Get the accessory SKU information
	    Set<String> distinctAccessorySkus = new HashSet<String>();
	    for(Sku sku: skuAndAccessories){
		if (sku.getSkuList() != null){
		    for(Sku accessorySku: sku.getSkuList()){
			distinctAccessorySkus.add(accessorySku.getSku());
		    }
		}
	    }
	    List<Sku> accessoryList = skuService.getAccessories(storeId, distinctAccessorySkus
		    .toArray(new String[distinctAccessorySkus.size()]));
	    for(String phoneNumber: phoneNumberSkuMap.keySet()){
		Sku product = phoneNumberSkuMap.get(phoneNumber);
		Sku resultProduct = getSkuFromList(skuAndAccessories, product.getSku());
		List<Sku> acessorySkuList = new ArrayList<Sku>();
		if (resultProduct != null && resultProduct.getSkuList() != null){
		    for(Sku accessorySku: resultProduct.getSkuList()){
			Sku resultAccessorySku = getSkuFromList(accessoryList, accessorySku.getSku());
			if (resultAccessorySku != null){
			    acessorySkuList.add(resultAccessorySku);
			}
		    }
		    List<Sku> sortedAccessorySkuList = sortAccessoryList(acessorySkuList);
		    resultProduct.setSkuList(sortedAccessorySkuList.size() > 0?sortedAccessorySkuList:null);
		    phoneNumberSkuMap.put(phoneNumber, resultProduct);
		}
	    }

	    return phoneNumberSkuMap;

	}catch(Exception e){
	    logger.error("Error in SKU Service", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "No accessories could be found for this device.");
	}
    }

    private List<Sku> sortAccessoryList(List<Sku> accessoryCompleteList) {
	List<Sku> returnList = new ArrayList<Sku>();
	String strCaseIds = drpPropertiesService.getProperty(CASE_SUBCLASS_IDS, "753,804");
	String strChargerIds = drpPropertiesService.getProperty(CHARGER_SUBCLASS_IDS, "405");
	String strShieldIds = drpPropertiesService.getProperty(SHIELD_SUBCLASS_IDS, "752");
	int caseMaxNos = Integer.parseInt(drpPropertiesService.getProperty(CASE_MAX_NOS, "3"));
	int chargerMaxNos = Integer.parseInt(drpPropertiesService.getProperty(CHARGER_MAX_NOS, "2"));
	int shieldMaxNos = Integer.parseInt(drpPropertiesService.getProperty(SHIELD_MAX_NOS, "2"));
	int caseCount = 0;
	int chargerCount = 0;
	int shieldCount = 0;
	int totalCount = 0;
	List<Sku> caseList = new ArrayList<Sku>();
	List<Sku> chargerList = new ArrayList<Sku>();
	List<Sku> shieldList = new ArrayList<Sku>();
	for(Sku sku: accessoryCompleteList){
	    if ((getIds(strCaseIds) != null && getIds(strCaseIds).contains(sku.getSubClassId()))
		    && caseCount < caseMaxNos){
		caseList.add(sku);
		caseCount++;
		totalCount++;

	    }else if ((getIds(strChargerIds) != null && getIds(strChargerIds).contains(sku.getSubClassId()))
		    && chargerCount < chargerMaxNos){
		chargerList.add(sku);
		chargerCount++;
		totalCount++;

	    }else if ((getIds(strShieldIds) != null && getIds(strShieldIds).contains(sku.getSubClassId()))
		    && shieldCount < shieldMaxNos){
		shieldList.add(sku);
		shieldCount++;
		totalCount++;
	    }
	    if (totalCount == (caseMaxNos + chargerMaxNos + shieldCount)){
		break;
	    }
	}
	returnList.addAll(caseList);
	returnList.addAll(shieldList);
	returnList.addAll(chargerList);
	return returnList;
    }

    private List getIds(String strIds) {
	StringTokenizer st = new StringTokenizer(strIds, ",");
	List ids = new ArrayList();
	while(st.hasMoreElements()){
	    int id = Integer.parseInt((String) st.nextElement());
	    ids.add(id);
	}
	return ids;
    }

    private Sku getSkuFromList(List<Sku> skuList, String skuString) {
	for(Sku sku: skuList){
	    if (sku.getSku().equals(skuString)){
		return sku;
	    }
	}
	return null;
    }
}
