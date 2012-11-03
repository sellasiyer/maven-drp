package com.bestbuy.bbym.ise.drp.rest.service;

import javax.ws.rs.WebApplicationException;

import com.bestbuy.bbym.beast.portal.customerinfo.response.Customer;
import com.bestbuy.bbym.beast.portal.datasummary.response.DataTransferSummaryList;
import com.bestbuy.bbym.beast.portal.gspcancel.write.response.DskResponse;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.request.GetMobileDeviceRequest;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.response.GetMobileDeviceResponse;
import com.bestbuy.bbym.beast.portal.tradein.reqres.TradeIn;
import com.bestbuy.bbym.beast.portal.tradeindevicedetails.request.TradeInDeviceDetails;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Services called by BEAST during the GSP Cancel process.
 * 
 * @author a904002
 */
public interface DSLService {

    /**
     * Accesses the list of customers that are available for transfer for the
     * given store Id. Returns customers that have been transferred within an
     * hour. Each item in the list will contain the customer first name,
     * customer last name, customer phone number, data sharing key, and a URL
     * that can be used to access the detailed data.
     */
    public DataTransferSummaryList getDataTransferSummaryList(String storeId) throws ServiceException;

    /**
     * Accesses all of the data that was transferred from BEAST Portal. Calling
     * this service will effectively <i>transfer</i> the data to BEAST. It will
     * return carrier customer data, BBY customer data, and recommendation sheet
     * data.
     */
    public Customer getCustomerDetail(String storeId, String dataSharingKey) throws ServiceException;

    public DskResponse writeDeviceDetails(TradeInDeviceDetails tradeInDeviceDetails) throws WebApplicationException;

    public TradeIn getTradeInRecord(String dataSharingKey) throws WebApplicationException;

    public DskResponse writeTradeInRecord(TradeIn tradeIn) throws ServiceException;

    public GetMobileDeviceResponse getMobileDeviceData(GetMobileDeviceRequest getMobileDeviceRequest)
	    throws WebApplicationException;

    public TradeIn getTradeInDocs(String dataSharingKey) throws WebApplicationException;
}
