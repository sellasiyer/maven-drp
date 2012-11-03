package com.bestbuy.bbym.ise.drp.sao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.domain.Subscription;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.OptInResponse;
import com.bestbuy.bbym.ise.drp.domain.UpgradeCheckerHistoryRow;
import com.bestbuy.bbym.ise.drp.helpers.UpgCheckerResponseData;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientucs.AccountLockedException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.AccountNotFoundException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.AuthenticationFailureException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.BusinessCustomerException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.CAPException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.CachedUpgradeEligibilityRequest;
import com.bestbuy.bbym.ise.iseclientucs.Carrier;
import com.bestbuy.bbym.ise.iseclientucs.InternationalBusinessHierarchy;
import com.bestbuy.bbym.ise.iseclientucs.InvalidAccountPasswordException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.InvalidArgumentException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.NotificationStatus;
import com.bestbuy.bbym.ise.iseclientucs.NotificationStatusGetRequest;
import com.bestbuy.bbym.ise.iseclientucs.NotificationStatusGetResponse;
import com.bestbuy.bbym.ise.iseclientucs.NotificationStatusMultiPutRequest;
import com.bestbuy.bbym.ise.iseclientucs.NotificationStatusMultiPutResponse;
import com.bestbuy.bbym.ise.iseclientucs.ScorecardRequest;
import com.bestbuy.bbym.ise.iseclientucs.ScorecardResponse;
import com.bestbuy.bbym.ise.iseclientucs.Subscriber;
import com.bestbuy.bbym.ise.iseclientucs.SubscriberNotificationStatus;
import com.bestbuy.bbym.ise.iseclientucs.UnknownFailureException_Exception;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeCheck;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeCheckHistoryRequest;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeCheckHistoryResponse;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeEligibilityRequest;
import com.bestbuy.bbym.ise.iseclientucs.UpgradeEligibilityResponse;
import com.bestbuy.bbym.ise.util.Util;

/**
 * @author a218635
 */
@Service("upgradeCheckerSao")
public class UpgradeCheckerSaoImpl extends AbstractSao implements UpgradeCheckerSao {

    private static Logger logger = LoggerFactory.getLogger(UpgradeCheckerSaoImpl.class);
    private static final String SERVICE = "UCS ";

    /**
     * Calls the UpgradeEligibility, which returns the UpgradeInfo, hardware
     * description and TradeInfo of a specific subscriber from the
     * UCS-CAP-Carrier System
     */
    public CustomersDashboardCarrierData getSubcribersUpgradeInfo(Customer customer, DrpUser drpUser)
	    throws ServiceException, IseBusinessException {
	CustomersDashboardCarrierData dashboardCarrierData = new CustomersDashboardCarrierData();
	UpgradeEligibilityRequest ucsRequestData = prepareUcsRequestData(customer, drpUser);
	logger.info("Invoking the Upgrade Eligibility Service");
	try{
	    UpgradeEligibilityResponse ucsResponse = getUpgradeCheckerService().getUpgradeEligibility(ucsRequestData);
	    if (ucsResponse != null){
		Subscription carrierSubscription = new Subscription();
		List<Line> subsCarrierInfoList = new ArrayList<Line>();
		List<Subscriber> allSubscribersList = new ArrayList<Subscriber>();
		Store storeCount = new Store();
		if (StringUtils.isNotBlank(ucsResponse.getCarrierAccountNumber()))
		    carrierSubscription.setAccountNumber(ucsResponse.getCarrierAccountNumber());
		storeCount.setOptInCount(ucsResponse.getOptInCount());
		storeCount.setUpgradeCheckCount(ucsResponse.getUpgradeCheckCount());
		allSubscribersList = ucsResponse.getSubscribers();
		logger.info("UCS call successful : " + allSubscribersList);
		subsCarrierInfoList = getSubsCarrierInfoList(allSubscribersList);
		carrierSubscription.setLines(subsCarrierInfoList);
		dashboardCarrierData.setSubscriptionInfo(carrierSubscription);
		dashboardCarrierData.setStoreInfo(storeCount);
	    }
	}catch(AccountLockedException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AccountNotFoundException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AuthenticationFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(BusinessCustomerException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(CAPException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidAccountPasswordException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidArgumentException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - get subscriber upgrade info", t);
	}
	return dashboardCarrierData;
    }

    /**
     * Calls the CachedUpgradeEligibility, which returns the hardware
     * description and TradeInfo of a specific subscriber from the UCS-CAP DB
     * System, along with the upgradeCount from that store.
     */
    public UpgCheckerResponseData getCachedSubcribersUpgradeInfo(Customer customer, String iseTransactionId,
	    DrpUser drpUser) throws ServiceException, IseBusinessException {

	UpgCheckerResponseData ucsRespData = new UpgCheckerResponseData();
	CachedUpgradeEligibilityRequest ucsRequestData = prepareCachedUcsRequestData(customer, iseTransactionId,
		drpUser);

	try{
	    logger.info("Invoking the Cached Upgrade Eligibility Service");
	    UpgradeEligibilityResponse ucsResponse = getUpgradeCheckerService().getCachedUpgradeEligibility(
		    ucsRequestData);
	    List<Subscriber> allSubscribersList = ucsResponse.getSubscribers();
	    ucsRespData.setUpgradeCount(ucsResponse.getUpgradeCheckCount());
	    ucsRespData.setOptInCount(ucsResponse.getOptInCount());
	    logger.info("UCS Cached service call successful : " + allSubscribersList);
	    ucsRespData.setDeviceTradeInInfoMap(getSubsUcsInfoList(allSubscribersList));
	    ucsRespData.setOptInAllowedMap(getSubsOptinAllowedList(allSubscribersList));
	}catch(AccountLockedException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AccountNotFoundException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AuthenticationFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(BusinessCustomerException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(CAPException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidAccountPasswordException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidArgumentException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getSubcribersUpgradeInfo: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - get cached subscriber upgrade info", t);
	}
	return ucsRespData;
    }

    /**
     * Gets the optIn status of a specific subscriber from UCS System.
     */
    public Map<String, String> getOptInStatus(List<String> phoneNumbers, String zipCode, DrpUser drpUser)
	    throws ServiceException, IseBusinessException {

	NotificationStatusGetRequest notificationReqData = prepareNotificationReqData(phoneNumbers, zipCode, drpUser);
	Map<String, String> optInStatusMap = new HashMap<String, String>();

	logger.info("Invoking the NotificationStatusGetRequest Service");
	try{
	    NotificationStatusGetResponse notificationResponse = getUpgradeCheckerService().getNotificationStatusPlus(
		    notificationReqData);
	    List<SubscriberNotificationStatus> allSubscribersStatusList = notificationResponse.getSubscriberStatuses();
	    logger.info("NotificationStatus service call successful : " + allSubscribersStatusList);

	    for(SubscriberNotificationStatus optInStatus: allSubscribersStatusList){
		if (StringUtils.isNotBlank(optInStatus.getMobilePhoneNumber())){
		    String type = optInStatus.getNotificationStatusCode().toString();

		    if (!StringUtils.isEmpty(type)){
			// map plus to text for display purposes
			if (type.equalsIgnoreCase(Line.OPT_IN_PLUS)){
			    type = Line.OPT_IN_TEXT;
			}
		    }else{
			// if the status is null or blank then set it to NONE
			type = Line.OPT_IN_NONE;
		    }
		    optInStatusMap.put(optInStatus.getMobilePhoneNumber(), type);
		}
	    }
	}catch(AccountLockedException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AccountNotFoundException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AuthenticationFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(BusinessCustomerException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(CAPException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidAccountPasswordException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidArgumentException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - get opt in status", t);
	}
	logger.info("Display optInStatusMap : " + optInStatusMap);
	return optInStatusMap;
    }

    /**
     * Sets each opted subscribers notification type in UCS System.
     */
    public OptInResponse setOptInStatus(HashMap<String, String> phoneOptInStatPair, String zipCode, DrpUser drpUser)
	    throws ServiceException, IseBusinessException {

	NotificationStatusMultiPutRequest notificationStatusMultiPutRequest = prepareNotificationStatusMultiPutRequest(
		phoneOptInStatPair, zipCode, drpUser);
	int optInCount = 0;
	NotificationStatusMultiPutResponse response = null;
	try{
	    response = getUpgradeCheckerService().putNotificationStatusPlusMulti(notificationStatusMultiPutRequest);
	}catch(AccountLockedException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AccountNotFoundException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(AuthenticationFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(BusinessCustomerException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(CAPException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidAccountPasswordException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidArgumentException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - setOptInStatus: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - set opt in status", t);
	}

	if (response != null){
	    optInCount = response.getOptInCount();
	    logger.info("opt in count: " + optInCount);
	}
	return new OptInResponse(optInCount);
    }

    /**
     * Gets UpgradeChecker History for the given subscriber from the UCS System.
     */
    public List<UpgradeCheckerHistoryRow> getUpgradeCheckerHistory(String phoneNumber) throws ServiceException,
	    IseBusinessException {

	List<UpgradeCheckerHistoryRow> interpretedUcsHistoryList = new ArrayList<UpgradeCheckerHistoryRow>();
	UpgradeCheckHistoryRequest ucsHistroyRequest = new UpgradeCheckHistoryRequest();
	ucsHistroyRequest.setMobilePhoneNumber(phoneNumber);

	try{
	    logger.info("Invoking the getUpgradeCheckerHistory Service");
	    UpgradeCheckHistoryResponse ucsResponse = getUpgradeCheckerService().getUpgradeCheckHistory(
		    ucsHistroyRequest);
	    List<UpgradeCheck> upgradeCheckHistoryList = ucsResponse.getUpgradeChecks();
	    logger.info("UpgradeChecker History Service call successful");
	    interpretedUcsHistoryList = interpretUCSHistoryResponse(upgradeCheckHistoryList);
	}catch(AccountNotFoundException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getUpgradeCheckerHistory: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getUpgradeCheckerHistory: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - get upgrade checker history", t);
	}
	return interpretedUcsHistoryList;
    }

    /**
     * Gets UpgradeChecker Store count for the upgrade check and optin count.
     */
    public Store getUCSStoreCount(DrpUser drpUser) throws ServiceException, IseBusinessException {

	Store storeCount = new Store();
	ScorecardRequest scorecardRequest = new ScorecardRequest();
	scorecardRequest.setLocationId(Integer.parseInt(drpUser.getStoreId()));
	try{
	    logger.info("Invoking the getUCSStoreCount Service");
	    ScorecardResponse scorecardResponse = getUpgradeCheckerService().getScorecard(scorecardRequest);
	    storeCount.setOptInCount(scorecardResponse.getOptInCount());
	    storeCount.setUpgradeCheckCount(scorecardResponse.getUpgradeCheckCount());
	    logger.info("UpgradeChecker get UCSStore Count call successful");
	}catch(AccountNotFoundException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error("Upgrade checker service exception - getUCSStoreCount: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(InvalidArgumentException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getUCSStoreCount: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(UnknownFailureException_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Upgrade checker service exception - getUCSStoreCount: " + faultMessage);
	    throw new IseBusinessException(faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - get UCS store count", t);
	}

	return storeCount;
    }

    /**
     * Gets each subscribers upgrade Eligibility info and Trade Info, given the
     * list of all subscribers from the UCS response.
     */
    private List<Line> getSubsCarrierInfoList(List<Subscriber> allSubscribersList) {

	List<Line> allSubsCarrInfoList = new ArrayList<Line>();
	logger.info("Retrieveing the subscribers");

	for(Subscriber subs: allSubscribersList){
	    Line ucsRespLineInfo = new Line();

	    ucsRespLineInfo.setContractEndDate(Util.toUtilDate(subs.getContractEndDate()));
	    ucsRespLineInfo.setEarlyEligibilityDate(Util.toUtilDate(subs.getEarlyUpgradeEligibilityDate()));
	    ucsRespLineInfo.setStdEligibilityDate(Util.toUtilDate(subs.getUpgradeEligibilityDate()));

	    if (subs.isUpgradeEligibilityFlag()){
		if (subs.getUpgradeEligibilityType() != null
			&& subs.getUpgradeEligibilityType().toString().equalsIgnoreCase("ELIGIBLE_FULL")){
		    ucsRespLineInfo.setStandardEligible(true);
		    ucsRespLineInfo.setEligibilityType("STANDARD_UPGRADE");
		}else{
		    ucsRespLineInfo.setStandardEligible(false);
		}

		if (subs.getUpgradeEligibilityType() != null
			&& subs.getUpgradeEligibilityType().toString().equalsIgnoreCase("ELIGIBLE_EARLY")){
		    ucsRespLineInfo.setEarlyEligible(true);
		    ucsRespLineInfo.setEligibilityType("EARLY_UPGRADE");
		}else if (subs.getUpgradeEligibilityType() != null
			&& subs.getUpgradeEligibilityType().toString().equalsIgnoreCase("ELIGIBLE_EARLY_IPHONE")){
		    ucsRespLineInfo.setEarlyEligible(true);
		    ucsRespLineInfo.setEligibilityType("EARLY_IPHONE_UPGRADE");
		}else{
		    ucsRespLineInfo.setEarlyEligible(false);
		}
	    }else{
		ucsRespLineInfo.setStandardEligible(false);
		ucsRespLineInfo.setEarlyEligible(false);
	    }

	    ucsRespLineInfo.setLineStatus("");

	    if (StringUtils.isNotBlank(subs.getMobilePhoneNumber()))
		ucsRespLineInfo.setMobileNumber(subs.getMobilePhoneNumber());

	    ucsRespLineInfo.setOptinAllowed(subs.isOptInAllowed());

	    Device hwdInfo = new Device();

	    if (subs.getTradeInValue() != null)
		hwdInfo.setTradeInValue(BigDecimal.valueOf(subs.getTradeInValue()));

	    if (subs.getTradeInPhoneName() != null && StringUtils.isNotBlank(subs.getTradeInPhoneName())){
		hwdInfo.setDescription(subs.getTradeInPhoneName());
	    }

	    if (StringUtils.isNotBlank(subs.getEsn())){
		hwdInfo.setSerialNumber(subs.getEsn());
	    }
	    if (StringUtils.isNotBlank(subs.getImei())){
		hwdInfo.setSerialNumber(subs.getImei());
	    }

	    ucsRespLineInfo.setDevice(hwdInfo);
	    logger.info("Interpreted Response Line data from UCS : " + ucsRespLineInfo.toString());
	    allSubsCarrInfoList.add(ucsRespLineInfo);
	}
	logger.info("Obtained real UCS subscribers Info");
	return allSubsCarrInfoList;

    }

    /**
     * Gets each subscribers hardware and corresponding Trade Info given the
     * list of all subscribers from the UCS response.
     */
    private Map<String, Device> getSubsUcsInfoList(List<Subscriber> allSubscribersList) {

	Map<String, Device> deviceTradeInInfoMap = new HashMap<String, Device>();
	logger.info("Retrieveing the phone description and TradeInInfo");

	for(Subscriber subs: allSubscribersList){
	    Device deviceTradeInInfo = new Device();
	    if (subs.getTradeInValue() != null){
		deviceTradeInInfo.setTradeInValue(BigDecimal.valueOf(subs.getTradeInValue()));
	    }
	    if (StringUtils.isNotBlank(subs.getTradeInPhoneName())){
		deviceTradeInInfo.setDescription(subs.getTradeInPhoneName());
	    }

	    deviceTradeInInfoMap.put(subs.getMobilePhoneNumber(), deviceTradeInInfo);
	}
	logger.info("phoneDescTradeInInfoMap from UCS Response : " + deviceTradeInInfoMap);
	return deviceTradeInInfoMap;

    }

    private Map<String, Boolean> getSubsOptinAllowedList(List<Subscriber> allSubscribersList) {

	Map<String, Boolean> optinAllowedMap = new HashMap<String, Boolean>();

	for(Subscriber subs: allSubscribersList){
	    optinAllowedMap.put(subs.getMobilePhoneNumber(), subs.isOptInAllowed());
	}
	logger.info("optinAllowedMap from UCS Response : " + optinAllowedMap);
	return optinAllowedMap;

    }

    /**
     * Sets the cached request data to get the TradeIn info and hardware info
     * from UCS.
     */
    private CachedUpgradeEligibilityRequest prepareCachedUcsRequestData(Customer customer, String capTransactionId,
	    DrpUser drpUser) throws ServiceException {
	logger.info("Assigning the UCS cached request data");

	CachedUpgradeEligibilityRequest ucsCachedReqData = new CachedUpgradeEligibilityRequest();

	if (StringUtils.isNotBlank(customer.getContactPhone())){
	    ucsCachedReqData.setMobilePhoneNumber(customer.getContactPhone());
	}

	if (StringUtils.isNotBlank(customer.getAddress().getZipcode())){
	    ucsCachedReqData.setZip(customer.getAddress().getZipcode());
	}

	ucsCachedReqData
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchy()));
	ucsCachedReqData.setSourceSystem(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));

	ucsCachedReqData.setCapTransactionId(capTransactionId);
	if (isDummyStoreEnabled()){
	    ucsCachedReqData.setLocationId(Integer.parseInt(getDummyStoreNum()));
	}else{
	    ucsCachedReqData.setLocationId(Integer.parseInt(drpUser.getStoreId()));
	}

	ucsCachedReqData.setUserId(drpUser.getUserId());

	return ucsCachedReqData;
    }

    /**
     * Sets the request data to get the Upgrade Info, TradeIn info and hardware
     * info from UCS.
     */
    private UpgradeEligibilityRequest prepareUcsRequestData(Customer customer, DrpUser drpUser) throws ServiceException {
	logger.info("Assigning the UCS request data");

	UpgradeEligibilityRequest ucsReqData = new UpgradeEligibilityRequest();

	if (StringUtils.isNotBlank(customer.getContactPhone()))
	    ucsReqData.setMobilePhoneNumber(customer.getContactPhone());

	if (StringUtils.isNotBlank(customer.getLast4ssn()))
	    ucsReqData.setLast4SSN(customer.getLast4ssn());

	if (StringUtils.isNotBlank(customer.getAddress().getZipcode()))
	    ucsReqData.setZip(customer.getAddress().getZipcode());

	logger.info("customer.getCarrier().name() : " + customer.getSubscription().getCarrier().name());

	if (StringUtils.isNotBlank(customer.getSubscription().getCarrier().toString()))
	    ucsReqData.setCarrierCode(Carrier.fromValue(customer.getSubscription().getCarrier().name()));

	ucsReqData
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchy()));
	ucsReqData.setSourceSystem(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	if (isDummyStoreEnabled()){
	    ucsReqData.setLocationId(Integer.parseInt(getDummyStoreNum()));
	}else{
	    ucsReqData.setLocationId(Integer.parseInt(drpUser.getStoreId()));
	}

	ucsReqData.setUserId(drpUser.getUserId());

	if (StringUtils.isNotBlank(customer.getSubscription().getCarrierPin()))
	    ucsReqData.setPassword(customer.getSubscription().getCarrierPin());

	String trainingMode = getDrpPropertiesService().getProperty("TRAINING_MODE");
	if (StringUtils.isNotBlank(trainingMode)){
	    ucsReqData.setIsTrainingMode(Boolean.parseBoolean(trainingMode));
	}else{
	    ucsReqData.setIsTrainingMode(false);
	}

	return ucsReqData;
    }

    /**
     * Sets the request data to get the optIn info from UCS.
     */
    private NotificationStatusGetRequest prepareNotificationReqData(List<String> phoneNumbers, String zipCode,
	    DrpUser drpUser) throws ServiceException {
	NotificationStatusGetRequest notifReqData = new NotificationStatusGetRequest();
	logger.info("Preparing NotificationReqData values");

	for(String phNum: phoneNumbers){
	    if (StringUtils.isNotBlank(phNum)){
		notifReqData.getMobilePhoneNumbers().add(phNum);
	    }
	}
	notifReqData.setZip(zipCode);
	notifReqData
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchy()));
	notifReqData.setSourceSystem(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	if (isDummyStoreEnabled()){
	    notifReqData.setLocationId(Integer.parseInt(getDummyStoreNum()));
	}else{
	    notifReqData.setLocationId(Integer.parseInt(drpUser.getStoreId()));
	}
	notifReqData.setUserId(drpUser.getUserId());

	return notifReqData;
    }

    /**
     * Prepares the request data to set the notification type of all opted
     * subscribers in UCS.
     */
    private NotificationStatusMultiPutRequest prepareNotificationStatusMultiPutRequest(
	    HashMap<String, String> phoneOptInStatPair, String zipCode, DrpUser drpUser) throws ServiceException {

	NotificationStatusMultiPutRequest multiStatus = new NotificationStatusMultiPutRequest();
	if (!phoneOptInStatPair.isEmpty()){
	    Set set = phoneOptInStatPair.entrySet();
	    Iterator iter = set.iterator();
	    while(iter.hasNext()){
		SubscriberNotificationStatus subNotifStatus = new SubscriberNotificationStatus();
		Map.Entry phoneOptInStatus = (Map.Entry) iter.next();
		subNotifStatus.setMobilePhoneNumber(phoneOptInStatus.getKey().toString());
		if (phoneOptInStatus.getValue() != null
			&& StringUtils.isNotBlank(phoneOptInStatus.getValue().toString())){
		    // map text to plus for enrollment purposes
		    String rawType = phoneOptInStatus.getValue().toString();
		    if (rawType.equalsIgnoreCase(Line.OPT_IN_TEXT)){
			rawType = Line.OPT_IN_PLUS;
		    }
		    NotificationStatus type = NotificationStatus.fromValue(rawType.toUpperCase());
		    subNotifStatus.setNotificationStatusCode(type);
		}
		multiStatus.getSubscriberStatuses().add(subNotifStatus);
	    }
	    multiStatus.setZip(zipCode);
	    multiStatus
		    .setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new InternationalBusinessHierarchy()));
	    multiStatus.setSourceSystem(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	    if (isDummyStoreEnabled()){
		multiStatus.setLocationId(Integer.parseInt(getDummyStoreNum()));
	    }else{
		multiStatus.setLocationId(Integer.parseInt(drpUser.getStoreId()));
	    }

	    multiStatus.setUserId(drpUser.getUserId());
	}
	return multiStatus;
    }

    /**
     * Interprets UCS History check and map to internal UpgradeCheckerHistoryRow
     * List.
     */
    private List<UpgradeCheckerHistoryRow> interpretUCSHistoryResponse(List<UpgradeCheck> upgradeCheckHistoryList) {

	List<UpgradeCheckerHistoryRow> ucsHistoryRowList = new ArrayList<UpgradeCheckerHistoryRow>();

	for(UpgradeCheck ucsHistoryResp: upgradeCheckHistoryList){
	    UpgradeCheckerHistoryRow ucsHistoryRow = new UpgradeCheckerHistoryRow();
	    if (StringUtils.isNotBlank(ucsHistoryResp.getMobilePhoneNumber()))
		ucsHistoryRow.setPhoneNumber(ucsHistoryResp.getMobilePhoneNumber());
	    ucsHistoryRow.setUpgradeCheckDate(Util.toUtilDate(ucsHistoryResp.getUpgradeCheckDate()));
	    if (ucsHistoryResp.getUpgradeEligibilityType() != null)
		ucsHistoryRow.setEligibilityType(ucsHistoryResp.getUpgradeEligibilityType().toString());
	    if (StringUtils.isNotBlank(ucsHistoryResp.getSourceSystem()))
		ucsHistoryRow.setSourceSystem(ucsHistoryResp.getSourceSystem());
	    ucsHistoryRow.setUpgradeEligibilityDate(Util.toUtilDate(ucsHistoryResp.getUpgradeEligibilityDate()));

	    ucsHistoryRow.setLocationId(ucsHistoryResp.getLocationId());
	    if (StringUtils.isNotBlank(ucsHistoryResp.getUserId()))
		ucsHistoryRow.setUserId(ucsHistoryResp.getUserId());

	    ucsHistoryRowList.add(ucsHistoryRow);
	}

	return ucsHistoryRowList;
    }
}
