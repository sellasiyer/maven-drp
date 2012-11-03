package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.drp.sao.BbyOpenSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a175157
 */
@Service("skuService")
public class SKUServiceImpl implements SKUService {

    private static Logger logger = LoggerFactory.getLogger(SKUServiceImpl.class);

    @Autowired
    private BbyOpenSao bbyOpenSao;

    @Override
    public Device getSkuDetails(String skuUpc) throws ServiceException, IseBusinessException {
	return bbyOpenSao.getSKUDetails(skuUpc);
    }

    @Override
    public List<Sku> getSkuAndAccessories(String... skus) throws ServiceException, IseBusinessException {
	return bbyOpenSao.getSKUAndAccessories(skus);
    }

    @Override
    public List<Sku> getAccessories(String storeId, String... skus) throws ServiceException, IseBusinessException {
	return bbyOpenSao.getAccessories(storeId, skus);
    }

}
