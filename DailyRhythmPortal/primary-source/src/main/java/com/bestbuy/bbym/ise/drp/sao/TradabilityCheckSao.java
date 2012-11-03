package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * TradabilityCheckSao
 * @author a904002
 */
public interface TradabilityCheckSao {

    public boolean checkForTradability(BeastDevice tradeInDevice, DrpUser drpUser, String storeId)
	    throws ServiceException, IseBusinessException;

}
