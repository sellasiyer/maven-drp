package com.bestbuy.bbym.ise.drp.rest.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.bestbuy.bbym.beast.portal.customerinfo.response.Customer;
import com.bestbuy.bbym.beast.portal.datasummary.response.DataTransferSummaryList;
import com.bestbuy.bbym.beast.portal.gspcancel.write.response.DskResponse;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.request.GetMobileDeviceRequest;
import com.bestbuy.bbym.beast.portal.retexch.getmobdev.response.GetMobileDeviceResponse;
import com.bestbuy.bbym.beast.portal.tradein.reqres.TradeIn;
import com.bestbuy.bbym.beast.portal.tradeindevicedetails.request.TradeInDeviceDetails;
import com.bestbuy.bbym.ise.drp.dao.DataTransferDao;
import com.bestbuy.bbym.ise.drp.domain.DataTransferSummary;
import com.bestbuy.bbym.ise.drp.domain.RetExchDevcEntitlsModel;
import com.bestbuy.bbym.ise.drp.domain.TitanDevice;
import com.bestbuy.bbym.ise.drp.rest.util.ExceptionHelper;
import com.bestbuy.bbym.ise.drp.rest.util.JAXBDomainMapper;
import com.bestbuy.bbym.ise.drp.rest.util.TradeInJAXBDomainMapper;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.service.ReturnExchangeService;
import com.bestbuy.bbym.ise.drp.service.TradeInService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Responsible for gather list of available customer objects.
 * 
 * @author a904002
 */
@Path("/v1/data")
@Component
@Scope("request")
public class DSLServiceImpl implements DSLService {

    private static final Logger log = LoggerFactory.getLogger(DSLServiceImpl.class);
    @Autowired
    private DataTransferDao dataTransferDao;
    @Autowired
    private DrpPropertyService drpPropertyService;
    @Autowired
    private ExceptionHelper exceptionHelper;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private TradeInService tradeInService;
    @Autowired
    private ReturnExchangeService returnExchangeService;

    @Path("/{storeid}")
    @GET
    @Produces("application/xml,application/json, text/plain")
    @Consumes("application/xml, application/json")
    @Override
    public DataTransferSummaryList getDataTransferSummaryList(@PathParam("storeid") String storeId)
	    throws ServiceException, WebApplicationException {
	DataTransferSummaryList summaryList = null;
	try{

	    log.info("getSummaryList - Begin, storeId:" + storeId);
	    List<DataTransferSummary> dtList = dataTransferDao.getDataTransferSummaryList(storeId);

	    if (dtList != null && !dtList.isEmpty()){
		summaryList = new DataTransferSummaryList();
		String base_url = drpPropertyService.getProperty("BEAST_TRANSFER_BASE_URL");
		Set<String> dataTransferKeysSet = new HashSet<String>();
		for(DataTransferSummary dataTransferSummary: dtList){
		    if (dataTransferKeysSet.contains(dataTransferSummary.getDataSharingKey()))
			continue;
		    dataTransferSummary.setDetailsUrl(base_url + "/beast/v1/data/" + storeId + "/"
			    + dataTransferSummary.getDataSharingKey());
		    summaryList.getDataTransferSummary().add(JAXBDomainMapper.getDataTransferType(dataTransferSummary));
		    dataTransferKeysSet.add(dataTransferSummary.getDataSharingKey());
		}
		log.debug("Returning Data summary List of:" + dtList.size());

	    }else{
		log.info("No customers available for transfer.");
		throw exceptionHelper.createException("No customers available for transfer. Store ID: " + storeId,
			Status.NOT_FOUND);
	    }

	}catch(EmptyResultDataAccessException e){
	    log.info("Error getting data transfer summary list", e.getMessage());
	    throw exceptionHelper.createException("Store ID : " + storeId + ", Error in fetching list ",
		    Status.NOT_FOUND);
	}catch(WebApplicationException ex){
	    log.info("No customers available for transfer.");
	    throw ex;
	}catch(Exception e){
	    log.error("Error getting data transfer summary list", e.getMessage());
	    throw exceptionHelper.createException("Store ID : " + storeId + ", Error in fetching list ",
		    Status.INTERNAL_SERVER_ERROR);
	}
	log.info("getSummaryList - End, storeId:" + storeId);
	return summaryList;
    }

    @Override
    @Path("/{storeid}/{dataSharingKey}")
    @GET
    @Produces("application/xml,application/json, text/plain")
    @Consumes("application/xml, application/json")
    public Customer getCustomerDetail(@PathParam("dataSharingKey") String dataSharingKey,
	    @PathParam("storeid") String storeId) throws ServiceException {
	log.info("getCustomerDetail - Begin, dataSharingKey:" + dataSharingKey);
	Customer customer = null;
	try{

	    customer = JAXBDomainMapper.getCustomer(customerService.getCustomer(storeId, dataSharingKey));
	    if (customer == null){
		throw exceptionHelper.createException("Unable to find the customer ", Response.Status.NOT_FOUND);
	    }
	    if (!drpPropertyService.getProperty(DrpConstants.SEND_CAP_TRANS_ID, "TRUE").equalsIgnoreCase("TRUE"))
		customer.setCapTransactionId(null);
	}catch(IseBusinessException ex){
	    log.error("Error getting customer detail");
	    throw exceptionHelper.createException("Error in retireving Customer details for Data Sharing Key:"
		    + dataSharingKey, ex.getErrorCode());
	}catch(ServiceException ex){
	    log.error("Error getting customer detail", ex);
	    throw exceptionHelper.createException("Error in retireving Customer details for Data Sharing Key:"
		    + dataSharingKey, Response.Status.INTERNAL_SERVER_ERROR);
	}catch(Exception ex){
	    log.error("Error getting customer detail", ex);
	    throw exceptionHelper.createException("Error in retireving Customer details for Data Sharing Key:"
		    + dataSharingKey, Response.Status.INTERNAL_SERVER_ERROR);
	}
	log.info("getCustomerDetail - End, dataSharingKey:" + dataSharingKey);
	return customer;
    }

    @Path("/writedevicedetails")
    @PUT
    @Produces("application/xml,application/json")
    @Consumes("application/xml,application/json")
    @Override
    public DskResponse writeDeviceDetails(TradeInDeviceDetails tradeInDeviceDetails) throws WebApplicationException {
	log.info("writeDeviceDetails - Begin, userId:" + tradeInDeviceDetails.getCreatedBy() + " ,storeId:"
		+ tradeInDeviceDetails.getStoreId());
	String dataSharingKey = null;
	DskResponse response = null;
	try{
	    dataSharingKey = tradeInService.writeDeviceDetails(TradeInJAXBDomainMapper
		    .getTradeInDeviceDetails(tradeInDeviceDetails), tradeInDeviceDetails.getStoreId(),
		    tradeInDeviceDetails.getCreatedBy(), tradeInDeviceDetails.getSource());
	    response = new DskResponse();
	    log.debug("Returning datasharingKey : " + dataSharingKey);
	    response.setDataSharingkey(dataSharingKey);
	}catch(ServiceException ex){
	    log.error("Error writing device details", ex);
	    throw exceptionHelper.createException("Error in Writing Device Details to Data Transfer Layer for userId:"
		    + tradeInDeviceDetails.getCreatedBy() + " ,storeId:" + tradeInDeviceDetails.getStoreId(),
		    Status.INTERNAL_SERVER_ERROR);
	}catch(Exception ex){
	    log.error("Error writing device details", ex);
	    throw exceptionHelper.createException("Error in Writing Device Details to Data Transfer Layer for userId:"
		    + tradeInDeviceDetails.getCreatedBy() + " ,storeId:" + tradeInDeviceDetails.getStoreId(),
		    Status.INTERNAL_SERVER_ERROR);
	}
	log.info("addCustomer - End, userId:" + tradeInDeviceDetails.getCreatedBy() + " ,storeId:"
		+ tradeInDeviceDetails.getStoreId());
	return response;
    }

    @Path("/gettradeinrecord/{datasharingkey}")
    @GET
    @Produces("application/xml,application/json")
    @Consumes("application/xml,application/json")
    @Override
    public TradeIn getTradeInRecord(@PathParam("datasharingkey") String dataSharingKey) throws WebApplicationException {
	log.info("getTradeInRecord - Begin, dataSharingKey:" + dataSharingKey);
	TradeIn tradeInResponse = new TradeIn();
	try{
	    TitanDevice titanDevice = tradeInService.getTradeInRecord(dataSharingKey);
	    tradeInResponse = TradeInJAXBDomainMapper.getTradeIn(titanDevice);
	}catch(ServiceException ex){
	    log.error("Error getting trade in record", ex);
	    throw exceptionHelper.createException("Error in Retrieving TradeIn Record for  Data Sharing Key:"
		    + dataSharingKey, Status.INTERNAL_SERVER_ERROR);
	}catch(Exception e){
	    log.error("Error getting trade in record", e);
	    throw exceptionHelper.createException("Error in Retrieving TradeIn Record for  Data Sharing Key:"
		    + dataSharingKey, Status.INTERNAL_SERVER_ERROR);
	}
	log.info("getTradeInRecord - End, dataSharingKey:" + dataSharingKey);
	return tradeInResponse;
    }

    @Path("/gettradeindoc/{datasharingkey}")
    @GET
    @Produces("application/xml,application/json")
    @Consumes("application/xml,application/json")
    @Override
    public TradeIn getTradeInDocs(@PathParam("datasharingkey") String dataSharingKey) throws WebApplicationException {
	log.info("getTradeInDocs - Begin, dataSharingKey:" + dataSharingKey);
	TradeIn tradeInResponse = new TradeIn();
	try{
	    TitanDevice titanDevice = tradeInService.getTradeInDocs(dataSharingKey);
	    tradeInResponse = TradeInJAXBDomainMapper.getTradeIn(titanDevice);
	}catch(ServiceException ex){
	    log.error("Error getting trade in record", ex);
	    throw exceptionHelper.createException("Error in Retrieving TradeIn Record for  Data Sharing Key:"
		    + dataSharingKey, Status.INTERNAL_SERVER_ERROR);
	}catch(Exception e){
	    log.error("Error getting trade in record", e);
	    throw exceptionHelper.createException("Error in Retrieving TradeIn Record for  Data Sharing Key:"
		    + dataSharingKey, Status.INTERNAL_SERVER_ERROR);
	}
	log.info("getTradeInDocs - End, dataSharingKey:" + dataSharingKey);
	return tradeInResponse;
    }

    @Path("/writetradeinrecord")
    @POST
    @Produces("application/xml,application/json")
    @Consumes("application/xml,application/json")
    @Override
    public DskResponse writeTradeInRecord(TradeIn tradeIn) throws ServiceException {
	log.info("writeTradeInRecord - Begin, userId:" + tradeIn.getCreatedBy() + " ,dataSharingKey:"
		+ tradeIn.getDataSharingKey());
	TitanDevice titanDevice = TradeInJAXBDomainMapper.createTitanDevice(tradeIn);
	String dataSharingKey = tradeInService.updateTradeInDevice(titanDevice);
	DskResponse response = null;

	try{

	    response = new DskResponse();
	    log.debug("Returning datasharingKey : " + dataSharingKey);
	    response.setDataSharingkey(dataSharingKey);
	    /*}catch(ServiceException ex){
	        log.error(ex);
	        throw exceptionHelper.createException("Error in Writing Device Details to Data Transfer Layer for userId:"
	    	    + tradeIn.getCreatedBy() + " ,dataSharingKey:" + tradeIn.getDataSharingKey(),
	    	    Status.INTERNAL_SERVER_ERROR);
	     */}catch(Exception ex){
	    log.error("Error writing trade in record", ex);
	    throw exceptionHelper.createException("Error in Writing Device Details to Data Transfer Layer for userId:"
		    + tradeIn.getCreatedBy() + " ,dataSharingKey:" + tradeIn.getDataSharingKey(),
		    Status.INTERNAL_SERVER_ERROR);
	}
	log.info("writeTradeInRecord - End, userId:" + tradeIn.getCreatedBy() + " ,dataSharingKey:"
		+ tradeIn.getDataSharingKey());
	return response;
    }

    @Path("/retexch/getmobdev")
    @POST
    @Produces("application/xml,application/json")
    @Consumes("application/xml,application/json")
    @Override
    public GetMobileDeviceResponse getMobileDeviceData(GetMobileDeviceRequest getMobileDeviceRequest)
	    throws WebApplicationException {
	log.info("getMobileDeviceData - Begin, IMEI/ESN:" + getMobileDeviceRequest.getHandsetIdentifier()
		+ " ,MobileNumber:" + getMobileDeviceRequest.getMobileNumber());
	GetMobileDeviceResponse getMobileDeviceResponse = null;
	try{
	    log.debug("Calling returnExchangeService");
	    RetExchDevcEntitlsModel retExchDevcEntitlsModel = returnExchangeService.getMobileDeviceDetails(
		    getMobileDeviceRequest.getHandsetIdentifier(), getMobileDeviceRequest.getMobileNumber());
	    if (retExchDevcEntitlsModel == null)
		throw exceptionHelper.createException("No Data Found", Status.NOT_FOUND);
	    getMobileDeviceResponse = JAXBDomainMapper.getGetMobileDeviceResponse(retExchDevcEntitlsModel);
	}catch(WebApplicationException ex){
	    log.error("No record found using IMEI/ESN:" + getMobileDeviceRequest.getHandsetIdentifier()
		    + " or Mobile Number:" + getMobileDeviceRequest.getMobileNumber(), ex);
	    throw ex;
	}catch(Exception ex){
	    log.error("Error in getting Mobile Device Data record", ex);
	    throw exceptionHelper.createException(
		    "Error in retrieving Device Details from Data Sharing Layer using IMEI/ESN:"
			    + getMobileDeviceRequest.getHandsetIdentifier() + " or Mobile Number:"
			    + getMobileDeviceRequest.getMobileNumber(), Status.INTERNAL_SERVER_ERROR);
	}
	log.info("getMobileDeviceData - End, IMEI/ESN:" + getMobileDeviceRequest.getHandsetIdentifier()
		+ " ,MobileNumber:" + getMobileDeviceRequest.getMobileNumber());
	return getMobileDeviceResponse;
    }
}
