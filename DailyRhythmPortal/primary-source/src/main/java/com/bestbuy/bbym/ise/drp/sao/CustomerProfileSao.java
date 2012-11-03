package com.bestbuy.bbym.ise.drp.sao;

import java.util.List;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a218635
 */
public interface CustomerProfileSao {

    public List<Customer> searchBbyCustomer(Customer bbyCustomer, CustomerSearchCriteria searchCriteria, DrpUser drpUser)
	    throws ServiceException, IseBusinessException;

}
