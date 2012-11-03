package com.bestbuy.bbym.ise.drp.rest.service;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.bestbuy.bbym.beast.portal.cancelgsp.response.GspPlanCancelResponse;
import com.bestbuy.bbym.beast.portal.gspcancel.write.request.CustomerRequest;
import com.bestbuy.bbym.beast.portal.gspcancel.write.response.DskResponse;
import com.bestbuy.bbym.ise.drp.domain.BbyAccount;
import com.bestbuy.bbym.ise.drp.domain.GSPPlan;
import com.bestbuy.bbym.ise.drp.rest.util.ExceptionHelper;
import com.bestbuy.bbym.ise.drp.rest.util.JAXBDomainMapper;
import com.bestbuy.bbym.ise.drp.service.CustomerService;
import com.bestbuy.bbym.ise.drp.service.GSPPlanService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * 
 * @author a904002
 */
@Path("/v1/data/gsp")
@Component
@Scope("request")
public class GspRestServiceImpl implements GspRestService {

    private static final Logger log = LoggerFactory.getLogger(GspRestServiceImpl.class);
    @Autowired
    private CustomerService customerService;
    @Autowired
    private GSPPlanService gspPlanService;
    @Autowired
    private ExceptionHelper exceptionHelper;

    @Path("/{storeid}/{userId}")
    @POST
    @Produces("application/xml,application/json")
    @Consumes("application/xml,application/json")
    @Override
    public DskResponse addCustomer(CustomerRequest account, @PathParam("storeid") String storeId,
	    @PathParam("userId") String userId) throws ServiceException {
	log.info("addCustomer - Begin, userId:" + userId + " ,storeId:" + storeId);
	String dataSharingKey = null;
	DskResponse response = null;
	try{
	    dataSharingKey = createCustomer(account, storeId, userId);
	    response = new DskResponse();
	    log.debug("Returning datasharingKey : " + dataSharingKey);
	    response.setDataSharingkey(dataSharingKey);
	}catch(ServiceException ex){
	    log.error("Error adding customer", ex);
	    throw exceptionHelper.createException("Error in Writing Customer to Data Transfer Layer for userId:"
		    + userId + " ,storeId:" + storeId, Status.INTERNAL_SERVER_ERROR);
	}catch(Exception ex){
	    log.error("Error adding customer", ex);
	    throw exceptionHelper.createException("Error in Writing Customer to Data Transfer Layer for userId:"
		    + userId + " ,storeId:" + storeId, Status.INTERNAL_SERVER_ERROR);
	}
	log.info("addCustomer - End, userId:" + userId + " ,storeId:" + storeId);
	return response;
    }

    @Path("/{datasharingkey}")
    @DELETE
    @Produces("application/xml,application/json,text/xml")
    @Consumes("application/xml, application/json")
    @Override
    public GspPlanCancelResponse cancelGSPPlans(@PathParam("datasharingkey") String dataSharingKey)
	    throws ServiceException {
	log.info("cancelGSPPlans - Begin, dataSharingKey:" + dataSharingKey);
	GspPlanCancelResponse gspPlanResponse = new GspPlanCancelResponse();
	try{
	    List<GSPPlan> gspPlanList = gspPlanService.cancelGSPPlans(dataSharingKey);
	    if (gspPlanList != null && !gspPlanList.isEmpty()){
		for(GSPPlan gspPlan: gspPlanList){
		    gspPlanResponse.getGspPlan().add(JAXBDomainMapper.getGspPlanType(gspPlan));
		}
	    }else{
		throw exceptionHelper.createException("No GSP Plans found for the customer. DataSharingKey : "
			+ dataSharingKey, Status.NOT_FOUND);
	    }
	}catch(ServiceException ex){
	    log.error("Error canceling GSP plans", ex);
	    throw exceptionHelper.createException("Error in Cancelling GSP Plans for  Data Sharing Key:"
		    + dataSharingKey, Status.INTERNAL_SERVER_ERROR);
	}catch(Exception e){
	    log.error("Error canceling GSP plans", e);
	    throw exceptionHelper.createException("Error in Cancelling GSP Plans for  Data Sharing Key:"
		    + dataSharingKey, Status.INTERNAL_SERVER_ERROR);
	}
	log.info("cancelGSPPlans - End, dataSharingKey:" + dataSharingKey);
	return gspPlanResponse;
    }

    private String createCustomer(CustomerRequest account, String storeId, String userId) throws ServiceException {
	BbyAccount bbyAccount = JAXBDomainMapper.getBbyAccount(account, storeId, userId);
	customerService.addBbyCustomer(bbyAccount);
	return bbyAccount.getDataSharingKey();
    }
}
