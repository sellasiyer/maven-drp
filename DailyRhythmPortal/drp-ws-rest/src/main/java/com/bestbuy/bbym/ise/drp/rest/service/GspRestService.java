package com.bestbuy.bbym.ise.drp.rest.service;

import com.bestbuy.bbym.beast.portal.cancelgsp.response.GspPlanCancelResponse;
import com.bestbuy.bbym.beast.portal.gspcancel.write.request.CustomerRequest;
import com.bestbuy.bbym.beast.portal.gspcancel.write.response.DskResponse;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
public interface GspRestService {

    public DskResponse addCustomer(CustomerRequest account, String storeId, String userId) throws ServiceException;

    public GspPlanCancelResponse cancelGSPPlans(String dataSharingKey) throws ServiceException;
}
