/**
 * 
 */
package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlementRequest;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a175157
 *
 */
public interface MobileEntitlementSao {

    public MobileEntitlement getMobileEntitlements(MobileEntitlementRequest mobEntRequest, DrpUser drpUser,
	    String iseTransactionId) throws ServiceException, IseBusinessException;
}
