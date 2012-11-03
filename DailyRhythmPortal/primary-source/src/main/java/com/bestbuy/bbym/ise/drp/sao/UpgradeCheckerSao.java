/**
 * 
 */
package com.bestbuy.bbym.ise.drp.sao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.OptInResponse;
import com.bestbuy.bbym.ise.drp.domain.UpgradeCheckerHistoryRow;
import com.bestbuy.bbym.ise.drp.helpers.UpgCheckerResponseData;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a218635
 *
 */
public interface UpgradeCheckerSao {

    public abstract CustomersDashboardCarrierData getSubcribersUpgradeInfo(Customer customer, DrpUser drpUser)
	    throws ServiceException, IseBusinessException;

    public abstract UpgCheckerResponseData getCachedSubcribersUpgradeInfo(Customer customer, String iseTransactionId,
	    DrpUser drpUser) throws ServiceException, IseBusinessException;

    public abstract Map<String, String> getOptInStatus(List<String> phoneNumbers, String zipCode, DrpUser drpUser)
	    throws ServiceException, IseBusinessException;

    public OptInResponse setOptInStatus(HashMap<String, String> phoneOptInStatPair, String zipCode, DrpUser drpUser)
	    throws ServiceException, IseBusinessException;

    public List<UpgradeCheckerHistoryRow> getUpgradeCheckerHistory(String phoneNumber) throws ServiceException,
	    IseBusinessException;

    public Store getUCSStoreCount(DrpUser drpUser) throws ServiceException, IseBusinessException;

}
