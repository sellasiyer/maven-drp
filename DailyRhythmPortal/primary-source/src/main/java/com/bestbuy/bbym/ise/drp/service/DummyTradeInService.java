package com.bestbuy.bbym.ise.drp.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.BeastDevice;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

public class DummyTradeInService implements TradeInService {

    @Override
    public boolean isTradable(BeastDevice tradeInDevice, DrpUser drpUser) throws ServiceException, IseBusinessException {
	return true;
    }

    @Override
    public String writeDeviceDetails(List<BeastDevice> tradeInDetails, String storeId, String userId,
	    String sourceSystem) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public void addDeviceDetails(BeastDevice tradeInDetails) throws ServiceException {
    }

    @Override
    public List<BeastDevice> getDeviceDetails(String datasharingKey) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public TitanDevice getTradeInRecord(String dataSharingKey) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public TitanDevice getTradeInDocs(String dataSharingKey) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public void updateSelectedFlag(String datasharingKey, String deviceDetailsId, String modifiedBy)
	    throws ServiceException {
    }

    @Override
    public String writeTradeInRecord(TitanDevice titanDevice) throws ServiceException {
	return null;
    }

    @Override
    public BeastDevice getTradeableDevice(String dataSharingKey, String selLastFour, DrpUser drpUser)
	    throws ServiceException {
	BeastDevice beastDevice = new BeastDevice();
	beastDevice.setDataSharingKey(dataSharingKey);
	beastDevice.setId(UUID.randomUUID().toString());
	beastDevice.setHandsetIdentifier("12345678");
	beastDevice.setDeviceDesc("Some device description");
	beastDevice.setSource("BEAST_PORTAL");
	beastDevice.setCreatedBy(drpUser.getUserId());
	beastDevice.setSelectedFlag(true);
	return beastDevice;
    }

    @Override
    public String updateTradeInDevice(TitanDevice titanDevice) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public void updateTradeInDocs(TitanDevice titanDevice) throws ServiceException {
    }

    @Override
    public TitanDevice getTradeInCarrierRecord(String dataSharingKey) throws ServiceException {
	// TODO Implement me!
	return null;
    }

}
