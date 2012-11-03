/**
 * 
 */
package com.bestbuy.bbym.ise.drp.sao;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import java.util.Map;

/**
 * @author a218635
 *
 */
public interface CapSao {

    public abstract CustomersDashboardCarrierData getSubsAccInfoFromCarrier(Customer customer, String iseTransactionId,
	    DrpUser drpUser) throws ServiceException, IseBusinessException;

    public Map<String, String> getRsaTokenByCarrier(String transactionId, Carrier carrier, String agentCode,
	    DrpUser drpUser) throws ServiceException, IseBusinessException;

    public String getAgentCode(Carrier carrier, String storeId) throws ServiceException, IseBusinessException;

}
