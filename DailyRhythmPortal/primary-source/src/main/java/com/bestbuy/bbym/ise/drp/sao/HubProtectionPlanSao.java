package com.bestbuy.bbym.ise.drp.sao;

import java.util.List;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.ServicePlan;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a218635
 */
public interface HubProtectionPlanSao {

    /**
     * Search and return the list of available service plans for the given Best
     * Buy customer profile.
     */
    public abstract List<ServicePlan> searchServicePlan(Customer bbyCustomer) throws ServiceException,
	    IseBusinessException;

    /**
     * Retrieves the full details about particular service plan.
     */
    public abstract ServicePlan getServicePlanDetails(ServicePlan servicePlanInfo) throws ServiceException,
	    IseBusinessException;

    /**
     * Updates the given GSP Plan(Protection Plan).
     * 
     * @return {@code true} if successfully updated
     */
    public boolean updateProtectionPlan(GSPPlan gspPlan) throws ServiceException, IseBusinessException;

    public List<ServicePlan> searchServicePlanByPlanId(String protectionPlanId) throws ServiceException,
	    IseBusinessException;

    public List<ServicePlan> searchServicePlanByIMEI(String deviceSerialNum) throws ServiceException,
	    IseBusinessException;

}
