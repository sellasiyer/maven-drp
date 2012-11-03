package com.bestbuy.bbym.ise.drp.rev;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.drp.service.ShippingService;
import com.bestbuy.bbym.ise.drp.util.SelectItem;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

public class ShippingParameters implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(ShippingParameters.class);

    private static final int DEFAULT_NUM_MOST_RECENT_MANIFESTS = 15;
    private static final int DEFAULT_MAX_NUM_ITEMS_PER_MANIFEST = 10;

    private static final List<String> INVENTORY_STATUS_FILTER_SYS_CODES;
    static{
	List<String> tempStatusFilterList = new ArrayList<String>();
	tempStatusFilterList.add("$DEVICE_ELIGIBLE_STAT_CODE$");
	tempStatusFilterList.add("$DEVICE_HOLD_STAT_CODE$");
	tempStatusFilterList.add("$DEVICE_SHIPPED_STAT_CODE$");
	tempStatusFilterList.add("$DEVICE_RECEIVED_STAT_CODE$");
	INVENTORY_STATUS_FILTER_SYS_CODES = Collections.unmodifiableList(tempStatusFilterList);
    }

    private int numMostRecentManifests = DEFAULT_NUM_MOST_RECENT_MANIFESTS;
    private int maxNumItemsPerManifest = DEFAULT_MAX_NUM_ITEMS_PER_MANIFEST;
    private String shrinkDispositionType;
    private String storeShrinkStatus;
    private List<SelectItem<String>> inventoryStatusFilters = new ArrayList<SelectItem<String>>();
    private Map<String, String> confirmShrinkQuestions = new HashMap<String, String>();

    public ShippingParameters() {
    }

    public void retrieveParameters(final ShippingService shippingService) throws IseBusinessException, ServiceException {
	inventoryStatusFilters.clear();
	if (shippingService == null){
	    logger.error("shippingService is null");
	    return;
	}

	List<ACDSParameters> acdsParameters = shippingService.getACDSParameters("$SHIPMENT_PORTAL$");
	if (acdsParameters != null && acdsParameters.size() != 0){

	    Map<String, SelectItem<String>> unsortedInventoryStatusFilters = new HashMap<String, SelectItem<String>>();

	    logger.debug("acdsParameters.size=" + acdsParameters.size());
	    for(ACDSParameters acdsParam: acdsParameters){
		logger.debug("acdsParam.sysCode=" + acdsParam.getSysCode());
		if (acdsParam.getSysCode() == null || acdsParam.getActivationFlag() == null
			|| !acdsParam.getActivationFlag().equalsIgnoreCase("1")){
		    continue;
		}
		if (acdsParam.getSysCode().equalsIgnoreCase("$RECENT_MANIFEST_NO$")){
		    numMostRecentManifests = Util.getInt(acdsParam.getValue(), DEFAULT_NUM_MOST_RECENT_MANIFESTS);
		    logger.debug("numMostRecentManifests=" + numMostRecentManifests);
		}
		if (acdsParam.getSysCode().equalsIgnoreCase("$MAX_ITEM_PER_MANIFEST$")){
		    maxNumItemsPerManifest = Util.getInt(acdsParam.getValue(), DEFAULT_MAX_NUM_ITEMS_PER_MANIFEST);
		    logger.debug("maxNumItemsPerManifest=" + maxNumItemsPerManifest);
		}
		if (acdsParam.getSysCode().equalsIgnoreCase("$SHK_DISPT_TYPE$")){
		    shrinkDispositionType = acdsParam.getValue();
		    logger.debug("shrinkDispositionType=" + shrinkDispositionType);
		}
		if (acdsParam.getSysCode().equalsIgnoreCase("$SHK_ITEM_STATUS$")){
		    storeShrinkStatus = acdsParam.getValue();
		    logger.debug("storeShrinkStatus=" + storeShrinkStatus);
		}
		if (INVENTORY_STATUS_FILTER_SYS_CODES.contains(acdsParam.getSysCode())){
		    logger.debug("filterStatus.value=" + acdsParam.getValue());
		    logger.debug("filterStatus.description=" + acdsParam.getDescription());
		    unsortedInventoryStatusFilters.put(acdsParam.getSysCode(), new SelectItem<String>(acdsParam
			    .getValue(), acdsParam.getDescription()));
		}
		if (acdsParam.getSysCode().equalsIgnoreCase("$ISE_SHK_WARN_MSG$")){
		    logger.debug("shrinkWarnMsg.value=" + acdsParam.getValue());
		    logger.debug("shrinkWarnMsg.description=" + acdsParam.getDescription());
		    confirmShrinkQuestions.put(acdsParam.getValue(), acdsParam.getDescription());
		}
	    }
	    logger.debug("unsortedInventoryStatusFilters.size=" + unsortedInventoryStatusFilters.size());
	    SelectItem<String> statusFilter;
	    for(String statusFilterCode: INVENTORY_STATUS_FILTER_SYS_CODES){
		statusFilter = unsortedInventoryStatusFilters.get(statusFilterCode);
		if (statusFilter != null){
		    inventoryStatusFilters.add(statusFilter);
		}
	    }
	    logger.debug("inventoryStatusFilters.size=" + inventoryStatusFilters.size());
	}else{
	    logger.error("acdsParameters is null or zero");
	    throw new IseBusinessException("No ACDS parameters returned");
	}
    }

    public int getNumMostRecentManifests() {
	return numMostRecentManifests;
    }

    public int getMaxNumItemsPerManifest() {
	return maxNumItemsPerManifest;
    }

    public String getShrinkDispositionType() {
	return shrinkDispositionType;
    }

    public String getStoreShrinkStatus() {
	return storeShrinkStatus;
    }

    public List<SelectItem<String>> getInventoryStatusFilters() {
	return inventoryStatusFilters;
    }

    public String getConfirmShrinkQuestion(String returnType, String defaultQuestion) {
	String question = confirmShrinkQuestions.get(returnType);
	if (question == null){
	    return defaultQuestion;
	}
	return question;
    }
}
