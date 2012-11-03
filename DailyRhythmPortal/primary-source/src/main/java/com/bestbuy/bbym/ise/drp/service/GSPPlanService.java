package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * brief description of the class including any specific usage instructions
 * @author Sridhar Savaram
 * @version
 * @since 
 */
public interface GSPPlanService {

    public List<GSPPlan> cancelGSPPlans(String dataSharingKey) throws ServiceException;

    public List<GSPPlan> getGSPPlansMarkedForCancel(String dataSharingKey) throws ServiceException;

    public void addGSPPlan(GSPPlan gspPlan) throws ServiceException;

}
