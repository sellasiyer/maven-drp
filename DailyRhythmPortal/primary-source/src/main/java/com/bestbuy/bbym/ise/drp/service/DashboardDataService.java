package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.domain.DashboardData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public interface DashboardDataService {

    public DashboardData getDashboardData(Customer customer, DrpUser drpUser, boolean invokeUcs)
	    throws ServiceException, IseBusinessException;

    public List<List<Object>> getPlansAndOptionsTable(Customer customer);
}
