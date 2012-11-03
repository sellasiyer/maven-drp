package com.bestbuy.bbym.ise.drp.dao;

import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.exception.DataAccessException;

/**
 * @author a194869
 */
public interface TradeInDeviceDetailsDao {

    public void persistTradeInDeviceDetails(BeastDevice tradeInDeviceDetails) throws DataAccessException,
	    RuntimeException;

    public BeastDevice getSelectedBEASTDevice(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

    public void updateSelectedDevice(String dataSharingKey, String id, String modifiedBy, boolean selected)
	    throws DataAccessException, RuntimeException;

    public void resetSelectedDevice(String dataSharingKey, String modifiedBy);

    public List<BeastDevice> getBEASTDevices(String dataSharingKey) throws DataAccessException,
	    EmptyResultDataAccessException, RuntimeException;

}
