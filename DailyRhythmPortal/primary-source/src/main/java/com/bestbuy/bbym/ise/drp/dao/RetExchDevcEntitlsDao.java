package com.bestbuy.bbym.ise.drp.dao;

import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.RetExchDevcEntitlsModel;
import com.bestbuy.bbym.ise.exception.DataAccessException;

public interface RetExchDevcEntitlsDao {

    public String createDeviceEntitlement(RetExchDevcEntitlsModel retExchDevcEntitlsModel,
	    DataTransferSummary dataTransferSummary, String existingDataSharingKey);

    public RetExchDevcEntitlsModel getDeviceEntitlement(String handsetId, String phoneNumber)
	    throws DataAccessException, RuntimeException;

    //delete to prevent duplicate records
    public void deleteDeviceEntitlement(String existingDataSharingKey);

}
