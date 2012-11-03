package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * StoreInformationSao
 * @author a194869
 */
public interface StoreInformationSao {

    public Store getStoreDetails(String storeId) throws ServiceException, IseBusinessException;
}
