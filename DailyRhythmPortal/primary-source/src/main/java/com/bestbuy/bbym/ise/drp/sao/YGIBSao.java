/**
 * 
 */
package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a218635
 * 
 */
public interface YGIBSao {

    public YGIBResponse postDeviceTest(YGIBDevice device) throws ServiceException, IseBusinessException;
}
