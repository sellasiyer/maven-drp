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

@Service("returnExchangeService1")
public class DummyReturnExchangeService implements ReturnExchangeService {

    @Override
    public RetExchDevcEntitlsModel getMobileDeviceDetails(String handsetId, String phoneNumber) throws ServiceException {
	// TODO Implement me!
	return null;
    }

    @Override
    public String createDeviceEntitlement(RetExchDevcEntitlsModel retExchDevcEntitlsModel,
	    DataTransferSummary DataTransferSummary, String dataSharingKey) throws ServiceException,
	    IseBusinessException {
	// TODO Implement me!
	return null;
    }

    @Override
    public MobileEntitlement getMobileEntitlement(MobileEntitlementRequest mobileEntitlementRequest, DrpUser drpUser,
	    String iseTransactionId) throws ServiceException, IseBusinessException {
	// TODO Implement me!
	return null;
    }

    @Override
    public Product validateTransactionKeySkuCombo(String transactionKey, DrpUser drpUser, String sku)
	    throws ServiceException, IseBusinessException {
	Product product = new Product();
	product.setSerialNumber("12345");
	product.setModelNumber("98765");
	return product;
    }

}
