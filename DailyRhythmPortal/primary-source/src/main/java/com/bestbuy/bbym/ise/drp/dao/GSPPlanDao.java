package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * interface for GSPPlan DB object
 * 
 * @author Sridhar Savaram
 * @version
 * @since
 */
public interface GSPPlanDao {

    public void persistGSPPlan(GSPPlan gspPlan) throws DataAccessException, RuntimeException;

    public List<GSPPlan> getGSPPlansMarkedForCancel(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public GSPPlan getGSPPlan(String id) throws DataAccessException, EmptyResultDataAccessException, RuntimeException;
}
