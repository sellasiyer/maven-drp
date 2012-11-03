package com.bestbuy.bbym.ise.drp.service;

import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Product;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlement;
import com.bestbuy.bbym.ise.drp.domain.MobileEntitlementRequest;
import com.bestbuy.bbym.ise.drp.domain.RetExchDevcEntitlsModel;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service
public interface ReturnExchangeService {

    /**
     * Fetches a record from data sharing layer using either handsetId or phone
     * number.
     */
    public RetExchDevcEntitlsModel getMobileDeviceDetails(String handsetId, String phoneNumber) throws ServiceException;

    /**
     * Inserts a record into data sharing layer.
     */
    // TODO change the name of the method as createMobileDeviceDetails
    public String createDeviceEntitlement(RetExchDevcEntitlsModel retExchDevcEntitlsModel,
	    DataTransferSummary DataTransferSummary, String dataSharingKey) throws ServiceException,
	    IseBusinessException;

    /**
     * Calls underlying service MobileEntitlementSao. The response from service
     * is augmented with some extra information retrieved from database.
     */
    public MobileEntitlement getMobileEntitlement(MobileEntitlementRequest mobileEntitlementRequest, DrpUser drpUser,
	    String iseTransactionId) throws ServiceException, IseBusinessException;

    /**
     * Fetches transaction from EC and validates if the given SKU is part of
     * transaction
     */
    public Product validateTransactionKeySkuCombo(String transactionKey, DrpUser drpUser, String sku)
	    throws ServiceException, IseBusinessException;

}
