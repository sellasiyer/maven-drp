package com.bestbuy.bbym.ise.drp.service;

import java.util.List;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * All SKU related interface.
 * 
 * @author a175157
 */
public interface SKUService {

    public Device getSkuDetails(String skuUpc) throws ServiceException, IseBusinessException;

    public List<Sku> getSkuAndAccessories(String... skus) throws ServiceException, IseBusinessException;

    public List<Sku> getAccessories(String storeId, String... skus) throws ServiceException, IseBusinessException;

}
