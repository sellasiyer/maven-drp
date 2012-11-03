package com.bestbuy.bbym.ise.drp.service;

import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * UIService responsible for delegating the UIReply and UIRequest from Wicket.
 * 
 * @author a904002
 */
public interface YGIBService {

    public final static int SUCCESS_RESPONSE_CODE = 0;

    public YGIBResponse processRequest(YGIBDevice device) throws ServiceException, IseBusinessException;

}
