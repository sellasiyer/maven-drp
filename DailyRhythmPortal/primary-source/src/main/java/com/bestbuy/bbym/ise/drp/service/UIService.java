package com.bestbuy.bbym.ise.drp.service;

import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * UIService responsible for delegating the UIReply and UIRequest from Wicket.
 * 
 * @author a904002
 */
public interface UIService {

    public UIReply processUIRequest(UIRequest uiRequest) throws ServiceException, IseBusinessException;

}
