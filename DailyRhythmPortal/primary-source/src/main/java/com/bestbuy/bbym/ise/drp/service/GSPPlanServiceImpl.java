package com.bestbuy.bbym.ise.drp.service;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bestbuy.bbym.ise.drp.dao.DataTransferDaoImpl;
import com.bestbuy.bbym.ise.drp.dao.GSPPlanDao;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.sao.HubProtectionPlanSao;
import com.bestbuy.bbym.ise.exception.DataAccessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * GSP Plan service class to add, retrieve and cancel operations.
 * 
 * @author Sridhar Savaram
 * @version
 * @since
 */
@Transactional
@Service("gspPlanService")
public class GSPPlanServiceImpl implements GSPPlanService {

    private static Logger logger = LoggerFactory.getLogger(DataTransferDaoImpl.class);

    @Autowired
    private GSPPlanDao gspPlanDao;
    @Autowired
    private HubProtectionPlanSao hubProtectionPlanSao;

    /**
     * Retrieves all GSP Plans associated to dataSharingKey. If GSP Plan is a
     * Monthly plan then makes Hub Service call to cancel it.
     * 
     * @return consolidated GSP Plans summary list along with Status of above
     *         service call.
     */
    @Override
    @Transactional(readOnly = true)
    public List<GSPPlan> cancelGSPPlans(String dataSharingKey) throws ServiceException {
	List<GSPPlan> gspPlanList = null;
	try{
	    gspPlanList = gspPlanDao.getGSPPlansMarkedForCancel(dataSharingKey);
	    for(GSPPlan gspPlan: gspPlanList){
		if (gspPlan.getBillingType().equalsIgnoreCase(GSPPlan.BILLING_TYPE_MONTHLY)){
		    gspPlan.setExpirationDate(new Date());
		    gspPlan.setCancel(makeCancelGSpServiceCall(gspPlan));
		}else{
		    // For One-Time Plans
		    gspPlan.setCancel(false);
		}
	    }
	    // TODO Add Cancel GSP Plans Code here
	}catch(EmptyResultDataAccessException e){
	    logger.error("GSP CANCEL: No GSP Plan records found, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("GSP CANCEL: Error in retrieving GSP Plan records, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR, "Error in retrieving GSP Plan records");
	}catch(Exception e){
	    logger.error("GSP CANCEL: Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return gspPlanList;
    }

    /**
     * Makes Service call to HUB to cancel GSP If there is a failure for first
     * time it tries for second time.
     */
    private boolean makeCancelGSpServiceCall(GSPPlan gspPlan) {
	boolean status = false;
	int counter = 0;
	while(counter++ < 2){
	    try{
		logger.debug("Start Hub Service Update call for GSP Plan Id:" + gspPlan.getProtectionPlanId());
		status = hubProtectionPlanSao.updateProtectionPlan(gspPlan);
		logger.debug("End Hub Service Update call for GSP Plan Id:" + gspPlan.getProtectionPlanId()
			+ ", status:" + status);
	    }catch(Exception e){
		if (counter == 1)
		    logger.warn("Error in make HUB Service Update call: Trying one more time", e);
		status = false;
	    }
	}
	return status;
    }

    /**
     * Returns list of GSP Plans associated with dataSharingKey
     */
    @Override
    @Transactional(readOnly = true)
    public List<GSPPlan> getGSPPlansMarkedForCancel(String dataSharingKey) throws ServiceException {
	List<GSPPlan> gspPlanList = null;
	try{
	    gspPlanList = gspPlanDao.getGSPPlansMarkedForCancel(dataSharingKey);
	}catch(EmptyResultDataAccessException e){
	    logger.error("GET GSP RECORDS: No GSP Plan records found, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, e.getMessage());
	}catch(DataAccessException e){
	    logger.error("GET GSP RECORDS: Error in retrieving GSP Plan records, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(Exception e){
	    logger.error("GET GSP RECORDS: Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
	return gspPlanList;
    }

    /**
     * Stores GSP Plan record to database
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void addGSPPlan(GSPPlan gspPlan) throws ServiceException {
	String dataSharingKey = gspPlan.getDataSharingKey();
	try{
	    logger.debug("Add GSP Plan:" + gspPlan);
	    gspPlanDao.persistGSPPlan(gspPlan);
	}catch(DataAccessException e){
	    logger.error("GSP ADD: Error in Storing GSP Plan record, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}catch(RuntimeException e){
	    logger.error("GET ADD: Unexpected Error, dataSharingKey:" + dataSharingKey, e);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, e.getMessage());
	}
    }

}
