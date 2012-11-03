package com.bestbuy.bbym.ise.drp.service;

/**
 * @author a218635
 * 
 */
import java.util.List;
import java.util.Map;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.OptInResponse;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface CarrierDataService {

    public CustomersDashboardCarrierData getSubscribersCarrierInfo(Customer customer, DrpUser drpUser, boolean invokeUcs)
	    throws ServiceException, IseBusinessException;

    public OptInResponse setSubscribersOptIn(List<Line> optedSubsLineData, Customer customer, DrpUser drpUser)
	    throws ServiceException;

    /**
     * @param carrierType
     * @param drpUser - has store id & user id
     * @return Map of Label as key and display text as value
     * @throws ServiceException
     * @throws IseBusinessException
     */
    public Map<String, String> getTokenCodes(Carrier carrierType, DrpUser drpUser) throws ServiceException,
	    IseBusinessException;
}
