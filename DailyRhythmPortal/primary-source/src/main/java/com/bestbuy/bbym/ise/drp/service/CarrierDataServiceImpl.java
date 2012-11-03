package com.bestbuy.bbym.ise.drp.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.OptInResponse;
import com.bestbuy.bbym.ise.drp.helpers.UpgCheckerResponseData;
import com.bestbuy.bbym.ise.drp.sao.CapSao;
import com.bestbuy.bbym.ise.drp.sao.UpgradeCheckerSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

/**
 * getSubscribersCarrierInfo method is used to get the subscribers information
 * from the CAP and the UCS System
 * 
 * Step 1.a : Call the CAP's Account Validation service Step 1.b : If the Call
 * fails, halt the orchestration.
 * 
 * Step 2 : For each subscriber obtained from CAP response, make a call to UCS
 * cached upgrade service to get trade In info. This cached upgrade service,
 * retrieves data from CAP DB based on the previous Account validation call.
 * Step 2.a : If there is no tradeIn info for the subscriber, then continue
 * processing other subscribers by setting empty value. Step 2.b : In case of
 * any service error, continue to optIn status lookup.
 * 
 * Step 3 : For each subscriber obtained from CAP response, make a call to UCS
 * Notification plus service to get OptIn info. Step 3.a : If the subscriber
 * optIn info is not found or in case of service error, then continue to step 4.
 * 
 * Step 4 : Fill all the SubscribersLineData for all subscribers and return.
 */
@Service("carrierDataService")
public class CarrierDataServiceImpl implements CarrierDataService {

    private static Logger logger = LoggerFactory.getLogger(CarrierDataServiceImpl.class);
    @Autowired
    private CapSao capSao;
    @Autowired
    private UpgradeCheckerSao upgradeCheckerSao;
    @Autowired
    private CustomerDataService customerDataService;

    public void setCapSao(CapSao capSao) {
	this.capSao = capSao;
    }

    public void setUpgradeCheckerSao(UpgradeCheckerSao upgradeCheckerSao) {
	this.upgradeCheckerSao = upgradeCheckerSao;
    }

    @Override
    public CustomersDashboardCarrierData getSubscribersCarrierInfo(Customer customer, DrpUser drpUser, boolean invokeUcs)
	    throws ServiceException, IseBusinessException {

	String iseTransactionId = Util.generateTransactionId();
	logger.info("invokeUcs Service : " + invokeUcs);

	CustomersDashboardCarrierData custDashboardCarrierData = null;
	if (invokeUcs){
	    custDashboardCarrierData = upgradeCheckerSao.getSubcribersUpgradeInfo(customer, drpUser);
	    logger.info("Obtained the Real UCS Response successfully for Orchestration");
	    UpgCheckerResponseData upgCheckerRespData = new UpgCheckerResponseData();
	    if (custDashboardCarrierData.getSubscriptionInfo() != null){
		List<Line> linesInfoFromUcsResp = custDashboardCarrierData.getSubscriptionInfo().getLines();
		List<String> phoneNumsFromUcsResp = new ArrayList<String>();
		for(Line lineInfo: linesInfoFromUcsResp){
		    phoneNumsFromUcsResp.add(lineInfo.getMobileNumber());
		}
		upgCheckerRespData.setOptInStatusesMap(upgradeCheckerSao.getOptInStatus(phoneNumsFromUcsResp, customer
			.getAddress().getZipcode(), drpUser));
		updateWithOptInData(upgCheckerRespData, custDashboardCarrierData);
		updateWithOptInAllowedData(upgCheckerRespData, custDashboardCarrierData);
	    }

	}else{
	    // Add CapTransactionId
	    customer.setCapTransactionId(iseTransactionId);
	    custDashboardCarrierData = capSao.getSubsAccInfoFromCarrier(customer, iseTransactionId, drpUser);

	    logger.info("Obtained the CAP Response successfully for Orchestration");

	    List<Line> linesInfoFromCapResp = custDashboardCarrierData.getSubscriptionInfo().getLines();
	    List<String> activePhoneNumsFromCapResp = new ArrayList<String>();

	    logger.info("Preparing the ACTIVE subscribers phoneNumbers list from the CAP Response");
	    for(Line lineInfo: linesInfoFromCapResp){
		if (Line.LINE_STATUS_ACTIVE.equalsIgnoreCase(lineInfo.getLineStatus())){
		    activePhoneNumsFromCapResp.add(lineInfo.getMobileNumber());
		    addProtectionPlan(lineInfo);
		}
	    }
	    logger.info("Added the ACTIVE phone numbers from CAP Response : " + activePhoneNumsFromCapResp.toString());

	    getUcsData(activePhoneNumsFromCapResp, customer, iseTransactionId, drpUser, custDashboardCarrierData);

	}
	// set unique ID values on lines
	if (custDashboardCarrierData != null && custDashboardCarrierData.getSubscriptionInfo() != null
		&& custDashboardCarrierData.getSubscriptionInfo().getLines() != null){
	    long lineId = 0L;
	    for(Line line: custDashboardCarrierData.getSubscriptionInfo().getLines()){
		line.setId(new Long(lineId));
		lineId++;
	    }
	}
	return custDashboardCarrierData;

    }

    private void addProtectionPlan(Line lineInfo) {
	if (lineInfo.getDevice() != null){
	    String deviceSerialNumber = lineInfo.getDevice().getSerialNumber();
	    if (!StringUtils.isEmpty(deviceSerialNumber)){
		try{
		    lineInfo.getDevice().setProtectionPlan(customerDataService.findProtectionPlan(deviceSerialNumber));
		}catch(Exception ignore){
		    logger.warn("Error in finding protection plan using deviceSerialNumber" + deviceSerialNumber);
		}
	    }
	}
    }

    /**
     * Updates UCS data for each subscribers in the custDashboardCarrierData
     */
    private void getUcsData(List<String> activePhoneNumsFromCapResp, Customer customer, String iseTransactionId,
	    DrpUser drpUser, CustomersDashboardCarrierData custDashboardCarrierData) throws IseBusinessException,
	    ServiceException {

	UpgCheckerResponseData upgCheckerRespData = new UpgCheckerResponseData();

	try{
	    upgCheckerRespData = upgradeCheckerSao.getCachedSubcribersUpgradeInfo(customer, iseTransactionId, drpUser);
	    updateWithTradeInData(upgCheckerRespData, custDashboardCarrierData);
	    updateWithOptInAllowedData(upgCheckerRespData, custDashboardCarrierData);
	}catch(Exception e){
	    logger.error("caught exception while calling getCachedSubcribersUpgradeInfo with customer: " + customer
		    + " and transaction id: " + iseTransactionId);

	    try{
		logger.info("Continuing to invoke the optin Service though the TradeInfo service failed");
		upgCheckerRespData.setOptInStatusesMap(upgradeCheckerSao.getOptInStatus(activePhoneNumsFromCapResp,
			customer.getAddress().getZipcode(), drpUser));
		updateWithOptInData(upgCheckerRespData, custDashboardCarrierData);
	    }catch(Exception ex){
		logger.error("from Catch section: caught exception while calling getOptInStatus with phone: "
			+ activePhoneNumsFromCapResp + " and zip: " + customer.getAddress().getZipcode());
	    }
	    return;
	}
	logger.info("phoneDescTradeInInfoMap from UCS Response is obtained");

	try{
	    upgCheckerRespData.setOptInStatusesMap(upgradeCheckerSao.getOptInStatus(activePhoneNumsFromCapResp,
		    customer.getAddress().getZipcode(), drpUser));
	    updateWithOptInData(upgCheckerRespData, custDashboardCarrierData);
	}catch(Exception e){
	    logger.error("caught exception while calling getOptInStatus with phone: " + activePhoneNumsFromCapResp
		    + " and zip: " + customer.getAddress().getZipcode());
	}
	logger.info("optInInfoMap from UCS Response is obtained");

    }

    /**
     * Updates the TradeIn info for each subscribers in the
     * custDashboardCarrierData.
     */
    private void updateWithTradeInData(UpgCheckerResponseData upgCheckerRespData,
	    CustomersDashboardCarrierData custDashboardCarrierData) {

	if (upgCheckerRespData != null){
	    List<Line> updatedlinesInfo = new ArrayList<Line>();

	    Store storeInfo = new Store();
	    storeInfo.setUpgradeCheckCount(upgCheckerRespData.getUpgradeCount());
	    storeInfo.setOptInCount(upgCheckerRespData.getOptInCount());
	    custDashboardCarrierData.setStoreInfo(storeInfo);

	    for(Line lineInfo: custDashboardCarrierData.getSubscriptionInfo().getLines()){

		String phoneNumKey = lineInfo.getMobileNumber();

		if (upgCheckerRespData.getDeviceTradeInInfoMap() != null
			&& !upgCheckerRespData.getDeviceTradeInInfoMap().isEmpty()){
		    Device deviceTradeInInfoMap = upgCheckerRespData.getDeviceTradeInInfoMap().get(phoneNumKey);
		    if (deviceTradeInInfoMap != null){
			if (StringUtils.isNotBlank(deviceTradeInInfoMap.getDescription()))
			    lineInfo.getDevice().setDescription(deviceTradeInInfoMap.getDescription());
			if (deviceTradeInInfoMap.getTradeInValue() != null)
			    lineInfo.getDevice().setTradeInValue(deviceTradeInInfoMap.getTradeInValue());
		    }
		}
		updatedlinesInfo.add(lineInfo);
	    }
	    custDashboardCarrierData.getSubscriptionInfo().setLines(updatedlinesInfo);
	}

    }

    /**
     * Updates the Optin info for for each subscribers in the
     * custDashboardCarrierData.
     */
    private void updateWithOptInData(UpgCheckerResponseData upgCheckerRespData,
	    CustomersDashboardCarrierData custDashboardCarrierData) {

	if (upgCheckerRespData != null){
	    List<Line> updatedlinesInfo = new ArrayList<Line>();
	    String optInValue = "";

	    for(Line lineInfo: custDashboardCarrierData.getSubscriptionInfo().getLines()){

		String phoneNumKey = lineInfo.getMobileNumber();

		if (upgCheckerRespData.getOptInStatusesMap() != null
			&& StringUtils.isNotBlank(upgCheckerRespData.getOptInStatusesMap().get(phoneNumKey))){
		    optInValue = upgCheckerRespData.getOptInStatusesMap().get(phoneNumKey);
		    lineInfo.setOptin(optInValue);
		}
		updatedlinesInfo.add(lineInfo);
	    }
	    custDashboardCarrierData.getSubscriptionInfo().setLines(updatedlinesInfo);
	}

    }

    private void updateWithOptInAllowedData(UpgCheckerResponseData upgCheckerRespData,
	    CustomersDashboardCarrierData custDashboardCarrierData) {

	if (upgCheckerRespData != null){
	    List<Line> updatedlinesInfo = new ArrayList<Line>();

	    for(Line lineInfo: custDashboardCarrierData.getSubscriptionInfo().getLines()){

		String phoneNumKey = lineInfo.getMobileNumber();

		if (upgCheckerRespData.getOptInAllowedMap() != null){
		    lineInfo.setOptinAllowed(upgCheckerRespData.getOptInAllowedMap().get(phoneNumKey));
		}
		updatedlinesInfo.add(lineInfo);
	    }
	    custDashboardCarrierData.getSubscriptionInfo().setLines(updatedlinesInfo);
	}

    }

    /**
     * Sets each subscribers notification type in UCS System.
     */
    public OptInResponse setSubscribersOptIn(List<Line> optedSubsLineData, Customer customer, DrpUser drpUser)
	    throws ServiceException {

	OptInResponse response = null;
	try{
	    HashMap<String, String> phoneOptInStatPair = new HashMap<String, String>();
	    for(Line lineInfo: optedSubsLineData){
		phoneOptInStatPair.put(lineInfo.getMobileNumber(), lineInfo.getOptin());
	    }
	    response = upgradeCheckerSao
		    .setOptInStatus(phoneOptInStatPair, customer.getAddress().getZipcode(), drpUser);
	}catch(IseBusinessException ex){
	    logger.warn("Business exception in setSubscribersOptIn : " + ex.getMessage());
	}catch(ServiceException e){
	    logger.error("caught exception while calling setOptInStatus for customer aith account Num : "
		    + customer.getAcctNumber());
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess);
	}

	return response;
    }

    @Override
    public Map<String, String> getTokenCodes(Carrier carrier, DrpUser drpUser) throws ServiceException,
	    IseBusinessException {
	try{
	    String agentCode = capSao.getAgentCode(carrier, drpUser.getStoreId());
	    logger.info("Received Agent code for " + carrier + " ==>> " + agentCode);
	    String iseTransactionId = Util.generateTransactionId();
	    return capSao.getRsaTokenByCarrier(iseTransactionId, carrier, agentCode, drpUser);
	}catch(Exception e){
	    logger.error("Error in get Token Code Service", e);
	    throw new IseBusinessException(
		    IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "The Token Code service is currently unavailable. Please try again. If the problem persists, please call the Help Desk to report the issue.");

	}
    }
}
