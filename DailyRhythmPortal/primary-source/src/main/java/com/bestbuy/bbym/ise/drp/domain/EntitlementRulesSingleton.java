package com.bestbuy.bbym.ise.drp.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository("entitlementRulesSingleton")
public class EntitlementRulesSingleton {

    private static Logger logger = LoggerFactory.getLogger(EntitlementRulesSingleton.class);

    // Inside map : <ERA entitlement, ERA Covg vehicle>
    private Map<Map<String, String>, EntitlementRulesModel> entitlementRulesMap = new HashMap<Map<String, String>, EntitlementRulesModel>();
    private String NONE_ENTITLEMENTS = "NONE";

    public Map<Map<String, String>, EntitlementRulesModel> getEntitlementRulesMap() {
	return entitlementRulesMap;
    }

    public void setEntitlementRulesMap(Map<Map<String, String>, EntitlementRulesModel> entitlementRulesMap) {
	this.entitlementRulesMap = entitlementRulesMap;
    }

    public List<Entitlement> supplyEntitlementDetailsAndActions(List<Entitlement> deviceEntitlementlist,
	    boolean isTradeable, boolean isUpgradable) {

	List<Entitlement> renderedEntitlementList = new ArrayList<Entitlement>();
	try{
	    Map<String, String> eraKeyMap = new HashMap<String, String>();

	    for(Entitlement entitlement: deviceEntitlementlist){
		eraKeyMap.put(entitlement.getCoverageBenefit().toUpperCase(), entitlement.getCoverageVehicle()
			.toUpperCase());
		if (entitlementRulesMap.containsKey(eraKeyMap)){
		    if (entitlementRulesMap.get(eraKeyMap).isDisplayFlag()){
			if (entitlement.getCoverageBenefit().equalsIgnoreCase(NONE_ENTITLEMENTS)
				&& entitlement.getCoverageVehicle().equalsIgnoreCase(NONE_ENTITLEMENTS)){
			    entitlement.setCoverageBenefit(NONE_ENTITLEMENTS);
			    entitlement.setCoverageVehicle(NONE_ENTITLEMENTS);
			    entitlement.setDetails(entitlementRulesMap.get(eraKeyMap).getEntitlementDetails());
			    entitlement.setActions(entitlementRulesMap.get(eraKeyMap).getActions());
			    entitlement.setDisplayText(entitlementRulesMap.get(eraKeyMap).getDisplayText());
			    renderedEntitlementList.add(entitlement);
			}else{
			    entitlement.setDetails(entitlementRulesMap.get(eraKeyMap).getEntitlementDetails());
			    entitlement.setActions(entitlementRulesMap.get(eraKeyMap).getActions());
			    entitlement.setDisplayText(entitlementRulesMap.get(eraKeyMap).getDisplayText());
			    renderedEntitlementList.add(entitlement);
			}
		    }
		}
		eraKeyMap.clear();

	    }
	    if (isTradeable){
		eraKeyMap.put("TRADEIN", "TRADEIN");
		if (entitlementRulesMap.containsKey(eraKeyMap))
		    renderedEntitlementList.add(entitlementRulesMap.get(eraKeyMap).mapEntitlementsFromDaoToDomain());
		else{// exception - tradein is not available in db
		}
	    }
	    eraKeyMap.clear();

	    if (isUpgradable){
		eraKeyMap.put("UPGRADE", "UPGRADE");
		if (entitlementRulesMap.containsKey(eraKeyMap))
		    renderedEntitlementList.add(entitlementRulesMap.get(eraKeyMap).mapEntitlementsFromDaoToDomain());
		else{// exception -  Upgrade is not available in db
		}
	    }
	    eraKeyMap.clear();

	    if (renderedEntitlementList.size() == 0){
		logger.info("handling empty entitlement list case");
		eraKeyMap.put("NONE", "NONE");
		renderedEntitlementList.add(entitlementRulesMap.get(eraKeyMap).mapEntitlementsFromDaoToDomain());
	    }
	    eraKeyMap.clear();
	    if (renderedEntitlementList.size() > 1){
		for(Entitlement noneEntitlement: renderedEntitlementList){
		    if (noneEntitlement.getCoverageBenefit().equalsIgnoreCase(NONE_ENTITLEMENTS)
			    && noneEntitlement.getCoverageVehicle().equalsIgnoreCase(NONE_ENTITLEMENTS)){
			renderedEntitlementList.remove(noneEntitlement);
			break;
		    }
		}
	    }
	}catch(Exception e){
	    logger.debug("supplyEntitlementDetailsAndActions(): entitlementList is either empty or null");
	}
	return renderedEntitlementList;
    }

}
