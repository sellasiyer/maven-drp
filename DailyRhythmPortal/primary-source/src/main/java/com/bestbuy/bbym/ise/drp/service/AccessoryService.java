package com.bestbuy.bbym.ise.drp.service;

import java.util.Map;

import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface AccessoryService {

    /**
     * Populates each device SKU's accessory SKUs.
     * 
     * @param storeId
     *            the store that the accessories belong to
     * @param phoneNumberSkuMap
     *            a {@code Map} whose key represents a phone number and value
     *            represents the corresponding device SKU.
     * @return a copy of the {@code phoneNumberSkuMap} with the accessory SKUs
     *         populated
     */
    public Map<String, Sku> getAccessories(String storeId, Map<String, Sku> phoneNumberSkuMap) throws ServiceException,
	    IseBusinessException;

}
