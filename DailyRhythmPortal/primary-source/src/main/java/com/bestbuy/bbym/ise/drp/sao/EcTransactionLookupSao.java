/**
 * 
 */
package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author a218635
 *
 */
public interface EcTransactionLookupSao {

    public abstract List<Product> getAllTransactions(Customer bbyCustomer, DrpUser drpUser, Date startDate,
	    Date endDate, boolean mobileOnly) throws ServiceException, IseBusinessException;

    public abstract BigDecimal getRetailPriceByFourPartKey(String fourPartKey, String sku, DrpUser drpUser)
	    throws ServiceException;

    public boolean validateFourPartKeySkuCombo(String transactionKey, DrpUser drpUser, boolean mobileOnly, String sku)
	    throws ServiceException;

    public List<Product> getAllTransactionsByFourPartKey(String transactionKey, DrpUser drpUser, boolean mobileOnly)
	    throws ServiceException;

}
