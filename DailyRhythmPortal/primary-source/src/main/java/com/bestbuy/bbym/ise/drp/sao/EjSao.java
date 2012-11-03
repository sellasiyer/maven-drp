package com.bestbuy.bbym.ise.drp.sao;

import java.util.Date;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Electronic journal service access object.
 */
public interface EjSao {

    /**
     * Gets the electronic journal.
     * 
     * @param storeId
     *            the store that the transaction was processed in
     * @param registerId
     *            the register that the transaction was processed on
     * @param transactionDate
     *            the date that the transaction was performed on
     * @param transactionNumber
     *            the transaction number
     * @param the
     *            user requesting the electronic journal
     * @return the electronic journal receipt; {@code null} if no receipt was
     *         found
     */
    public String getElectronicJournal(String storeId, String registerId, Date transactionDate,
	    String transactionNumber, DrpUser drpUser) throws ServiceException, IseBusinessException;

}
