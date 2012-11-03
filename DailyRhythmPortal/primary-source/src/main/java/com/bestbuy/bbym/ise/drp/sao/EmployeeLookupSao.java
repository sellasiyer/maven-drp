/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 27
 */
public interface EmployeeLookupSao {

    /**
     * Returns user details by user ID.
     * 
     * @param userId
     *            BBY userId like a1003810
     * @return returns user details.
     * @throws ServiceException 
     * @throws IseBusinessException 
     */
    User getEmployeeDetails(String userId) throws ServiceException, IseBusinessException;
}
