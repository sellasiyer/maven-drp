package com.bestbuy.bbym.ise.drp.dao;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * @author a194869
 */
public interface TradeInDao {

    public void persistDevice(TitanDevice titanDevice) throws DataAccessException, RuntimeException;

    public void persistTradeIn(TitanDevice titanDevice) throws DataAccessException, RuntimeException;

    public TitanDevice getTradeInRecord(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public void insertTradeInDevice(TitanDevice titanDevice) throws DataAccessException, RuntimeException;

    public void updateTradeInDevice(TitanDevice titanDevice) throws DataAccessException, RuntimeException;

    public TitanDevice getTradeInDocs(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public void updateTradeInDocs(TitanDevice titanDevice) throws DataAccessException, RuntimeException;

    public TitanDevice getTradeInCarrierRecord(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;
}
