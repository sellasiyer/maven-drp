package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * SAO Interface for update device status to Shrink
 * 
 */
public interface UpdateACDSDeviceStatusSao {

    public void updateDeviceStatus(String itemTag, String returnStatus, String disposition) throws ServiceException,
	    IseBusinessException;
}
