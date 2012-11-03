package com.bestbuy.bbym.ise.drp.sao;

import java.util.List;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface BbyOpenSao {

    public Device getSKUDetails(String skuUpc) throws ServiceException, IseBusinessException;

    public List<Sku> getSKUAndAccessories(String... skus) throws ServiceException, IseBusinessException;

    public List<Sku> getAccessories(String storeId, String... skus) throws ServiceException, IseBusinessException;
}
