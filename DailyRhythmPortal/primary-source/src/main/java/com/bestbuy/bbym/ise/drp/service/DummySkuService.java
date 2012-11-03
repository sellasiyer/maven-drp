package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("skuService1")
public class DummySkuService implements SKUService {

    @Override
    public Device getSkuDetails(String skuupc) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public List<Sku> getSkuAndAccessories(String... skus) throws ServiceException, IseBusinessException {
	// TODO Implement me!
	return null;
    }

    @Override
    public List<Sku> getAccessories(String storeId, String... skus) throws ServiceException, IseBusinessException {
	// TODO Implement me!
	return null;
    }

}
