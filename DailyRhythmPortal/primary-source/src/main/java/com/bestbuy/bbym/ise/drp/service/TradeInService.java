package com.bestbuy.bbym.ise.drp.service;

import java.util.List;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
public interface TradeInService {

    public boolean isTradable(BeastDevice tradeInDevice, DrpUser drpUser) throws ServiceException, IseBusinessException;

    public String writeDeviceDetails(List<BeastDevice> tradeInDetails, String storeId, String userId,
	    String sourceSystem) throws ServiceException;

    public void addDeviceDetails(BeastDevice tradeInDetails) throws ServiceException;

    public List<BeastDevice> getDeviceDetails(String datasharingKey) throws ServiceException;

    public TitanDevice getTradeInRecord(String dataSharingKey) throws ServiceException;

    public TitanDevice getTradeInDocs(String dataSharingKey) throws ServiceException;

    public void updateSelectedFlag(String datasharingKey, String deviceDetailsId, String modifiedBy)
	    throws ServiceException;

    public String writeTradeInRecord(TitanDevice titanDevice) throws ServiceException;

    public BeastDevice getTradeableDevice(String datasharingKey, String selLastFour, DrpUser drpUser)
	    throws ServiceException;

    public String updateTradeInDevice(TitanDevice titanDevice) throws ServiceException;

    public void updateTradeInDocs(TitanDevice titanDevice) throws ServiceException;

    public TitanDevice getTradeInCarrierRecord(String dataSharingKey) throws ServiceException;

}
