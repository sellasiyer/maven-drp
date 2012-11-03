package com.bestbuy.bbym.ise.drp.sao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.Unmarshaller;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Carrier;
import com.bestbuy.bbym.ise.domain.CarrierOption;
import com.bestbuy.bbym.ise.domain.CarrierPlan;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Line;
import com.bestbuy.bbym.ise.domain.Subscription;
import com.bestbuy.bbym.ise.drp.domain.CustomersDashboardCarrierData;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBaseExceptionTypeEnum;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCategoryEnum;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclient.cap.agent.request.AgentCodeLookUpRequest;
import com.bestbuy.bbym.ise.iseclient.cap.agent.request.VendorListType;
import com.bestbuy.bbym.ise.iseclient.cap.agent.request.VendorType;
import com.bestbuy.bbym.ise.iseclient.cap.agent.response.AgentCodeListType;
import com.bestbuy.bbym.ise.iseclient.cap.agent.response.AgentCodeLookUpResponse;
import com.bestbuy.bbym.ise.iseclient.cap.rsa.request.Body;
import com.bestbuy.bbym.ise.iseclient.cap.rsa.request.PostpaidRsaTokenRequest;
import com.bestbuy.bbym.ise.iseclient.cap.rsa.response.PostpaidRsaTokenResponse;
import com.bestbuy.bbym.ise.iseclientcap.GetAgentCodes;
import com.bestbuy.bbym.ise.iseclientcap.GetAgentCodesResponse;
import com.bestbuy.bbym.ise.iseclientforcapcommon.AddressType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.DestinationType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.DeviceNameType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.InactiveLinesType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.IntlBusinessHierarchyType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.Name;
import com.bestbuy.bbym.ise.iseclientforcapcommon.OptionType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.OrderType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.PricePlanLocalType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.SubscriberType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.UpgradeQualificationType;
import com.bestbuy.bbym.ise.iseclientforcapcommon.UpgradeType;
import com.bestbuy.bbym.ise.iseclientforcaprequest.Header;
import com.bestbuy.bbym.ise.iseclientforcaprequest.MessageTypes;
import com.bestbuy.bbym.ise.iseclientforcaprequest.PostpaidAccountValidationRequest;
import com.bestbuy.bbym.ise.iseclientforcapresponse.ErrorInfoType;
import com.bestbuy.bbym.ise.iseclientforcapresponse.PostpaidAccountValidationResponse;
import com.bestbuy.bbym.ise.util.Util;
import com.sun.xml.bind.marshaller.NamespacePrefixMapper;

/**
 * @author a218635
 */
@Service("capSao")
public class CapSaoImpl extends AbstractSao implements CapSao {

    private static Logger logger = LoggerFactory.getLogger(CapSaoImpl.class);
    @Autowired
    private Marshaller requestMarshaller;
    @Autowired
    private Marshaller agentRequestMarshaller;
    @Autowired
    private Marshaller tokenCodeRequestMarshaller;
    @Autowired
    private Unmarshaller tokenCodeResponseUnMarshaller;
    @Autowired
    private Unmarshaller responseUnMarshaller;
    @Autowired
    private Unmarshaller agentResponseUnMarshaller;
    private Map<Long, IseExceptionCodeEnum> capResultcodeMap = new HashMap<Long, IseExceptionCodeEnum>();
    private static final String SERVICE = "CAP ";

    public CapSaoImpl() {

	capResultcodeMap.put(Long.parseLong("17226"), IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(Long.parseLong("17259"), IseExceptionCodeEnum.BusinessCustomerException);
	// ATT
	capResultcodeMap.put(30000109001L, IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(30000212002L, IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(30001259002L, IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(Long.parseLong("120107"), IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(30001417004L, IseExceptionCodeEnum.AuthenticationFailureException);
	// SPRINT
	capResultcodeMap.put(Long.parseLong("120120"), IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(Long.parseLong("246"), IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(Long.parseLong("120046"), IseExceptionCodeEnum.AuthenticationFailureException);
	capResultcodeMap.put(Long.parseLong("329"), IseExceptionCodeEnum.AuthenticationFailureException);
	// T-MOBILE
	capResultcodeMap.put(Long.parseLong("18002"), IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(Long.parseLong("18018"), IseExceptionCodeEnum.InvalidAccountPasswordException);
	capResultcodeMap.put(Long.parseLong("17260"), IseExceptionCodeEnum.InvalidAccountPasswordException);
	capResultcodeMap.put(Long.parseLong("120031"), IseExceptionCodeEnum.InvalidAccountPasswordException);
	capResultcodeMap.put(Long.parseLong("17290"), IseExceptionCodeEnum.BusinessCustomerException);
	capResultcodeMap.put(Long.parseLong("17277"), IseExceptionCodeEnum.BusinessCustomerException);
	capResultcodeMap.put(Long.parseLong("1026"), IseExceptionCodeEnum.AuthenticationFailureException);
	capResultcodeMap.put(Long.parseLong("120047"), IseExceptionCodeEnum.AuthenticationFailureException);
	// VERIZON
	capResultcodeMap.put(Long.parseLong("120058"), IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(Long.parseLong("608"), IseExceptionCodeEnum.AccountNotFoundException);
	capResultcodeMap.put(Long.parseLong("120036"), IseExceptionCodeEnum.BusinessCustomerException);
	capResultcodeMap.put(Long.parseLong("120088"), IseExceptionCodeEnum.AuthenticationFailureException);
	capResultcodeMap.put(Long.parseLong("606"), IseExceptionCodeEnum.BusinessCustomerException);
	capResultcodeMap.put(Long.parseLong("120086"), IseExceptionCodeEnum.InvalidAccountPasswordException);
	capResultcodeMap.put(Long.parseLong("2603"), IseExceptionCodeEnum.InvalidAccountPasswordException);
	capResultcodeMap.put(Long.parseLong("2601"), IseExceptionCodeEnum.AuthenticationFailureException);
	capResultcodeMap.put(Long.parseLong("602"), IseExceptionCodeEnum.InvalidAccountPasswordException);
    }

    @Override
    public CustomersDashboardCarrierData getSubsAccInfoFromCarrier(Customer customer, String iseTransactionId,
	    DrpUser drpUser) throws ServiceException, IseBusinessException {

	CustomersDashboardCarrierData customerAccountData = null;

	try{

	    logger.info("In the getSubsAccInfoFromCarrier method with  iseTransactionId : " + iseTransactionId
		    + " : phoneNumber : " + customer.getContactPhone());
	    PostpaidAccountValidationRequest capRequest = new PostpaidAccountValidationRequest();
	    com.bestbuy.bbym.ise.iseclientforcaprequest.Body body = new com.bestbuy.bbym.ise.iseclientforcaprequest.Body();

	    Header header = createCapRequestHeader(iseTransactionId, drpUser, customer.getSubscription().getCarrier(),
		    MessageTypes.POSTPAID_ACCOUNT_VALIDATION_REQUEST);

	    body.setAccountPhoneNumber(customer.getContactPhone());
	    body.setZipCode(customer.getAddress().getZipcode());
	    if (StringUtils.isNotBlank(customer.getLast4ssn())){
		body.setSsn(customer.getLast4ssn());
	    }
	    if (customer.getSubscription() != null && customer.getSubscription().getCarrierPin() != null){
		body.setSecPin(customer.getSubscription().getCarrierPin());
	    }
	    capRequest.setHeader(header);
	    capRequest.setBody(body);

	    PostpaidAccountValidationResponse accountValidationResponse = (PostpaidAccountValidationResponse) processRequest(
		    capRequest, "http://bestbuy.com/bbym/beast/cap/request/postpaid/accountValidation");
	    logger.info("Executed the getSubsAccInfoFromCarrier method successfully : accountValidationResponse");
	    customerAccountData = interpretResponse(customer, iseTransactionId, accountValidationResponse);

	}catch(IOException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error in CAP servcie call:", ex);
	    throw new ServiceException(IseExceptionCodeEnum.CapException, ex.getMessage());
	}catch(JAXBException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error in CAP servcie call:", ex);
	    throw new ServiceException(IseExceptionCodeEnum.CapException, ex.getMessage());
	}catch(DatatypeConfigurationException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error in CAP servcie call:", ex);
	    throw new ServiceException(IseExceptionCodeEnum.CapException, ex.getMessage());
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error in CAP servcie call:", se);
	    throw(se);
	}catch(Throwable t){
	    handleException(SERVICE + " - get subscriber account info", t);
	}

	return customerAccountData;
    }

    protected Object processTokenCodeRequest(Object request, String namespace) throws JAXBException, ServiceException,
	    IOException {

	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	tokenCodeRequestMarshaller.marshal(request, new StreamResult(outputStream));
	String requestMessage = outputStream.toString();
	String displayServiceXmls = getDrpPropertiesService().getProperty("DISPLAY_SERVICE_XMLS");
	if ("TRUE".equalsIgnoreCase(displayServiceXmls)){
	    logger.info("CAP Request Message: " + requestMessage);
	}
	String responseMessage = getCapWsController().processRequest(requestMessage);
	if ("TRUE".equalsIgnoreCase(displayServiceXmls)){
	    logger.info("CAP call successful : responseMessage: " + responseMessage);
	}

	return tokenCodeResponseUnMarshaller.unmarshal(new StreamSource(new StringReader(responseMessage)));
    }

    protected Object processRequest(Object request, String namespace) throws JAXBException, ServiceException,
	    IOException {

	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	requestMarshaller.marshal(request, new StreamResult(outputStream));
	String requestMessage = outputStream.toString();
	String displayServiceXmls = getDrpPropertiesService().getProperty("DISPLAY_SERVICE_XMLS");
	if ("TRUE".equalsIgnoreCase(displayServiceXmls)){
	    logger.info("CAP Request Message: " + requestMessage);
	}
	String responseMessage = getCapWsController().processRequest(requestMessage);

	if ("TRUE".equalsIgnoreCase(displayServiceXmls)){
	    logger.info("CAP call successful : responseMessage: " + responseMessage);
	}

	return responseUnMarshaller.unmarshal(new StreamSource(new StringReader(responseMessage)));
    }

    protected Object processAgentCodeRequest(Object request) throws JAXBException, ServiceException, IOException {

	ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
	agentRequestMarshaller.marshal(request, new StreamResult(outputStream));
	String requestMessage = outputStream.toString();
	String displayServiceXmls = getDrpPropertiesService().getProperty("DISPLAY_SERVICE_XMLS");
	if ("TRUE".equalsIgnoreCase(displayServiceXmls)){
	    logger.info("CAP Request Message: " + requestMessage);
	}
	requestMessage = requestMessage.replaceAll("xmlns=\"http://bestbuy.com/bbym/beast/cap/request\"", "");
	String responseMessage = getCapWsController().getAgentCodes(requestMessage);

	if ("TRUE".equalsIgnoreCase(displayServiceXmls)){
	    logger.info("CAP call successful : responseMessage: " + responseMessage);
	}
	return agentResponseUnMarshaller.unmarshal(new StreamSource(new StringReader(responseMessage)));
    }

    @Override
    public Map<String, String> getRsaTokenByCarrier(String transactionId, Carrier carrier, String agentCode,
	    DrpUser drpUser) throws ServiceException, IseBusinessException {
	try{

	    PostpaidRsaTokenRequest tokenRequest = new PostpaidRsaTokenRequest();
	    Header header = createCapRequestHeader(transactionId, drpUser, carrier,
		    MessageTypes.POSTPAID_RSA_TOKEN_REQUEST);
	    header.setAgentCode(agentCode);
	    header.setOrderType(OrderType.QUERY);
	    tokenRequest.setHeader(header);
	    com.bestbuy.bbym.ise.iseclient.cap.rsa.request.Body requestBody = new Body();
	    requestBody.setReasonCode("ACTIVATION");
	    tokenRequest.setBody(requestBody);
	    PostpaidRsaTokenResponse tokenResponse = (PostpaidRsaTokenResponse) processTokenCodeRequest(tokenRequest,
		    "http://bestbuy.com/bbym/beast/cap/request/postpaid/rsaToken");
	    if (tokenResponse != null && tokenResponse.getBody() != null){
		Map<String, String> returnMap = new LinkedHashMap<String, String>();
		returnMap.put("Store ID", drpUser.getStoreId());
		returnMap.put("Dealer Code", agentCode);
		switch (carrier) {
		    case ATT:
			returnMap.put("Serial Number", tokenResponse.getBody().getTokenUserId());
			returnMap.put("Token", tokenResponse.getBody().getTokenPassCode());
			break;
		    case SPRINT:
			returnMap.put("Sales Agent ID", tokenResponse.getBody().getTokenUserId());
			returnMap.put("NSS Passcode", tokenResponse.getBody().getTokenPassCode());
			break;
		    case TMOBILE:
			returnMap.put("Token Code", tokenResponse.getBody().getTokenUserId());
			returnMap.put("Passcode", tokenResponse.getBody().getTokenPassCode());
			break;
		    case VERIZON:
			returnMap.put("Sales Agent ID", tokenResponse.getBody().getTokenUserId());
			returnMap.put("NSS Passcode", tokenResponse.getBody().getTokenPassCode());
			break;
		    default:
			returnMap.put("Agent ID", tokenResponse.getBody().getTokenUserId());
			returnMap.put("Passcode", tokenResponse.getBody().getTokenPassCode());
			break;

		}

		return returnMap;
	    }else if (tokenResponse != null && tokenResponse.getResultInfo() != null){
		logger.error("Got error in getting RSA token code from CAP service"
			+ tokenResponse.getResultInfo().getResultDetails());
		throw new IseBusinessException(IseExceptionCodeEnum.CapException, tokenResponse.getResultInfo()
			.getResultDetails());
	    }

	}catch(Exception ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error in CAP RSA servcie call:", ex);
	    throw new IseBusinessException(IseExceptionCodeEnum.CapException, ex.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - Get RSA Token code", t);
	    return null;
	}

	return null;
    }

    @Override
    public String getAgentCode(Carrier carrier, String storeId) throws ServiceException, IseBusinessException {
	try{

	    AgentCodeLookUpRequest agentCodeRequest = new AgentCodeLookUpRequest();
	    com.bestbuy.bbym.ise.iseclient.cap.agent.request.Header agentCodeHeader = new com.bestbuy.bbym.ise.iseclient.cap.agent.request.Header();
	    DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
	    GregorianCalendar calendar = new GregorianCalendar();
	    calendar.setTime(new Date());
	    agentCodeHeader.setTimestamp(datatypeFactory.newXMLGregorianCalendar(calendar));
	    agentCodeHeader.setDestinationID(getDrpPropertiesService().getProperty("APPLICATION_NAME"));
	    com.bestbuy.bbym.ise.iseclient.cap.agent.request.Body body = new com.bestbuy.bbym.ise.iseclient.cap.agent.request.Body();
	    VendorType capVendorCode = new VendorType();
	    capVendorCode.setStoreId(storeId);
	    body.setCAPVendorCode(capVendorCode);
	    agentCodeRequest.setHeader(agentCodeHeader);
	    agentCodeRequest.setBody(body);
	    AgentCodeLookUpResponse agentCodeResponse = (AgentCodeLookUpResponse) processAgentCodeRequest(agentCodeRequest);
	    DestinationType destination;
	    switch (carrier) {
		case ATT:
		    destination = DestinationType.ATT;
		    break;
		case SPRINT:
		    destination = DestinationType.SPR;
		    break;
		case TMOBILE:
		    destination = DestinationType.TMO;
		    break;
		case VERIZON:
		    destination = DestinationType.VEZ;
		    break;
		default:
		    IseBusinessException se = new IseBusinessException("Unexpected value for carrier: " + carrier);
		    se.setCategory(IseExceptionCategoryEnum.Internal);
		    se.setExceptionType(IseBaseExceptionTypeEnum.Recoverable);
		    se.setErrorCode(IseExceptionCodeEnum.CapException);
		    throw se;
	    }
	    if (agentCodeResponse != null && agentCodeResponse.getBody() != null){
		List<AgentCodeListType> agentCodeTypes = agentCodeResponse.getBody().getAgentCodes();
		for(AgentCodeListType agentCodeType: agentCodeTypes){
		    if (agentCodeType.getCarrier() != null && agentCodeType.getCarrier().equals(destination)){
			return agentCodeType.getAgentCode();
		    }
		}
	    }else if (agentCodeResponse != null && agentCodeResponse.getResultInfo() != null){
		logger.error("Got error While getting agent code from CAP service"
			+ agentCodeResponse.getResultInfo().getResultDetails());
		throw new IseBusinessException(IseExceptionCodeEnum.CapException, agentCodeResponse.getResultInfo()
			.getResultDetails());
	    }
	}catch(Exception ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error in CAP RSA servcie call:", ex);
	    throw new IseBusinessException(IseExceptionCodeEnum.CapException, ex.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - Get Agent code", t);
	    return null;
	}
	return null;
    }

    // http://blogs.sun.com/enterprisetechtips/entry/customizing_jaxb
    static class MyNamespacePrefixMapper extends NamespacePrefixMapper {

	private String namespace;

	public MyNamespacePrefixMapper(String namespace) {
	    this.namespace = namespace;
	}

	public String getPreferredPrefix(String namespaceUri, String suggestion, boolean requirePrefix) {
	    if (namespace.equals(namespaceUri)){
		return "cap";
	    }else{
		return suggestion;
	    }
	}
    }

    private Header createCapRequestHeader(String capTransactionId, DrpUser drpUser, Carrier carrier,
	    MessageTypes messageType) throws DatatypeConfigurationException, ServiceException, IseBusinessException {

	DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
	Header header = new Header();
	header.setMessageType(messageType);
	switch (carrier) {
	    case ATT:
		header.setDestinationId(DestinationType.ATT);
		break;
	    case SPRINT:
		header.setDestinationId(DestinationType.SPR);
		break;
	    case TMOBILE:
		header.setDestinationId(DestinationType.TMO);
		break;
	    case VERIZON:
		header.setDestinationId(DestinationType.VEZ);
		break;
	    default:

		IseBusinessException se = new IseBusinessException("Unexpected value for carrier: " + carrier);
		se.setCategory(IseExceptionCategoryEnum.Internal);
		se.setExceptionType(IseBaseExceptionTypeEnum.Recoverable);
		se.setErrorCode(IseExceptionCodeEnum.CapException);

		throw se;
	}

	GregorianCalendar calendar = new GregorianCalendar();
	calendar.setTime(new Date());
	header.setTimestamp(datatypeFactory.newXMLGregorianCalendar(calendar));
	if (isDummyStoreEnabled()){
	    header.setChannelId(getDummyStoreNum()); // In CAP QA env, only 0699
	    // is configured
	}else{
	    header.setChannelId(drpUser.getStoreId());
	}
	header.setRepId(drpUser.getUserId());
	header
		.setInternationalBusinessHierarchy(getPopulatedInternationalBusinessHierarchy(new IntlBusinessHierarchyType()));
	header.setSourceSystem(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	header.setOrderType(OrderType.QUERY);

	String trainingMode = getDrpPropertiesService().getProperty("TRAINING_MODE");
	if (StringUtils.isNotBlank(trainingMode)){
	    header.setIsTrainingMode(Boolean.parseBoolean(trainingMode));
	}else{
	    header.setIsTrainingMode(false);
	}

	header.setTransactionId(capTransactionId);

	return header;
    }

    private CustomersDashboardCarrierData interpretResponse(Customer customer, String capTransactionId,
	    PostpaidAccountValidationResponse capResponse) throws ServiceException, IseBusinessException {

	logger.info("Interpreting the CAP Response");
	CustomersDashboardCarrierData dashboardCarrierData = new CustomersDashboardCarrierData();
	List<Line> allSubscribersLineInfo = new ArrayList<Line>();

	// Translate CAP error codes.
	if (capResponse.getResultInfo() != null){
	    logger.info("Translating CAP error codes");
	    throw interpretCapResultInfo(capResponse.getResultInfo(), capTransactionId);
	}

	if (capResponse.getBody() != null && capResponse.getBody().getAccountInfo() != null
		&& capResponse.getBody().getAccountInfo().getSubscriber() != null){

	    logger.info("Retrieving the information from CAP response and populating into subscribersAccountInfo");
	    Subscription carrierSubscription = new Subscription();
	    carrierSubscription.setAccountNumber(capResponse.getBody().getAccountInfo().getAccountNumber());
	    if (StringUtils.isNotBlank(capResponse.getBody().getCustomerInfo().getName().getFirstName()))
		carrierSubscription.setPrimAcctFirstName(capResponse.getBody().getCustomerInfo().getName()
			.getFirstName());
	    carrierSubscription.setPrimAcctLastName(capResponse.getBody().getCustomerInfo().getName().getLastName());
	    if (StringUtils.isNotBlank(capResponse.getBody().getCustomerInfo().getEmailAddress()))
		carrierSubscription.setPrimAcctEmailId(capResponse.getBody().getCustomerInfo().getEmailAddress());

	    if (capResponse.getBody().getAccountInfo().getNumberOfLinesAvailable() != null){
		carrierSubscription.setNumberOfLinesAvailable(capResponse.getBody().getAccountInfo()
			.getNumberOfLinesAvailable().intValue());
	    }
	    if (capResponse.getHeader().getDestinationId() != null){
		carrierSubscription.setCarrier(getCarrier(capResponse.getHeader().getDestinationId()));
	    }

	    if (capResponse.getBody().getCustomerInfo().getBillingAddress() != null){
		AddressType billingAddressType = capResponse.getBody().getCustomerInfo().getBillingAddress();

		Address address = new com.bestbuy.bbym.ise.domain.Address();
		address.setCity(billingAddressType.getCity());
		address.setAddressLine1(billingAddressType.getStreetAddress1());
		address.setAddressLine2(billingAddressType.getStreetAddress2());
		address.setCountry(billingAddressType.getCountryCode());
		address.setState(billingAddressType.getStateCode());
		address.setZipcode(billingAddressType.getZipCode());
		address.setZipcode4(billingAddressType.getZipCode4());

		customer.setAddress(address);
	    }

	    logger.info("Number of Subscribers returned from carrier: "
		    + capResponse.getBody().getAccountInfo().getSubscriber().size());

	    // Only Active Lines Processing
	    for(int i = 0; i < capResponse.getBody().getAccountInfo().getSubscriber().size(); ++i){

		boolean isEarly = false;
		boolean isIphone = false;
		boolean eligibleForEarly = false;
		boolean eligibleForStandard = false;
		boolean earlyIPhone = false;

		logger.info("Processing the Subscriber Number : " + i);
		Line subscribersLineInfo = new Line();
		SubscriberType capSubscriber = capResponse.getBody().getAccountInfo().getSubscriber().get(i);
		subscribersLineInfo.setMobileNumber(capSubscriber.getPhoneNumber());
		if (capSubscriber.getLineStatus() != null
			&& StringUtils.isNotBlank(capSubscriber.getLineStatus().toString())){
		    subscribersLineInfo.setLineStatus(capSubscriber.getLineStatus().toString());
		}
		if (capSubscriber.getContractInfo() != null){
		    subscribersLineInfo.setContractEndDate(Util
			    .toUtilDate(capSubscriber.getContractInfo().getEndDate()));
		}

		if (capSubscriber.getHardwareDetails() != null){
		    Device deviceInfo = new Device();
		    if (StringUtils.isNotBlank(capSubscriber.getHardwareDetails().getIMEI())){
			deviceInfo.setSerialNumber(capSubscriber.getHardwareDetails().getIMEI());
		    }
		    if (StringUtils.isNotBlank(capSubscriber.getHardwareDetails().getESN())){
			deviceInfo.setSerialNumber(capSubscriber.getHardwareDetails().getESN());
		    }
		    if (StringUtils.isNotBlank(capSubscriber.getPhoneName())){
			deviceInfo.setDescription(capSubscriber.getPhoneName());
		    }
		    subscribersLineInfo.setDevice(deviceInfo);
		}

		if (capSubscriber.getPricePlan() != null){
		    // getPricePlan() method was earlier returning list and CAP
		    // now changed in I18 to return just PricePlanLocalType
		    // For time being though returning carrierplan as list, it
		    // will always be having only 1 entry
		    List<CarrierPlan> carrierplanList = new ArrayList<CarrierPlan>();
		    PricePlanLocalType capPriceplan = capSubscriber.getPricePlan();
		    CarrierPlan plan = new CarrierPlan();
		    if (StringUtils.isNotBlank(capPriceplan.getPlanCode()))
			plan.setPlanCode(capPriceplan.getPlanCode());

		    if (StringUtils.isNotBlank(capPriceplan.getPlanName()))
			plan.setPlanName(capPriceplan.getPlanName());
		    else if (StringUtils.isNotBlank(capPriceplan.getPlanDescription()))
			plan.setPlanName(capPriceplan.getPlanDescription());

		    if (capPriceplan.getPlanType() != null)
			plan.setPlanType(mapPlan(capPriceplan.getPlanType().toString()));

		    if (capPriceplan.getPlanMRC() != null)
			plan.setPlanMRC(capPriceplan.getPlanMRC());

		    if (capPriceplan.getAccountMRC() != null)
			plan.setAccountMRC(capPriceplan.getAccountMRC());

		    carrierplanList.add(plan);
		    subscribersLineInfo.setCarrierPlans(carrierplanList);
		}

		if (capSubscriber.getOptions() != null){
		    List<CarrierOption> carrierOptionList = new ArrayList<CarrierOption>();
		    for(OptionType capOption: capSubscriber.getOptions()){
			CarrierOption option = new CarrierOption();
			if (StringUtils.isNotBlank(capOption.getCode()))
			    option.setOptionCode(capOption.getCode());

			if (StringUtils.isNotBlank(capOption.getName()))
			    option.setOptionName(capOption.getName());
			else if (StringUtils.isNotBlank(capOption.getDescription()))
			    option.setOptionName(capOption.getDescription());

			if (capOption.getCategory() != null)
			    option.setOptionCategory(capOption.getCategory());

			if (capOption.getPrice() != null)
			    option.setOptionPrice(capOption.getPrice());

			carrierOptionList.add(option);
		    }
		    subscribersLineInfo.setCarrierOptions(carrierOptionList);
		}

		UpgradeType upgrade = capSubscriber.getUpgrade();
		if (upgrade != null){

		    if (upgrade.getUpgradeQualificationDetails().size() == 1){
			UpgradeQualificationType qualification = upgrade.getUpgradeQualificationDetails().get(0);
			subscribersLineInfo.setStdEligibilityDate(Util.toUtilDate(qualification.getEligibilityDate()));
		    }else{
			// Sort the details by eligibility date.
			java.util.Collections.sort(upgrade.getUpgradeQualificationDetails(),
				new java.util.Comparator<UpgradeQualificationType>() {

				    public int compare(UpgradeQualificationType qualification1,
					    UpgradeQualificationType qualification2) {
					if (qualification1.getEligibilityDate() != null
						&& qualification2.getEligibilityDate() != null){
					    return qualification1.getEligibilityDate().toGregorianCalendar().compareTo(
						    qualification2.getEligibilityDate().toGregorianCalendar());
					}else if (qualification1.getEligibilityDate() == qualification2
						.getEligibilityDate()){
					    return 0;
					}else if (qualification1.getEligibilityDate() == null){
					    return -1;
					}
					return 1;
				    }

				    public boolean equals(Object object) {
					return this == object;
				    }
				});
		    }

		    for(UpgradeQualificationType qualification: upgrade.getUpgradeQualificationDetails()){

			if (qualification.getQualificationDescription() == null){
			    continue;
			}

			if (qualification.isEligible()){
			    if (qualification.getQualificationDescription().equals("EARLY_UPGRADE")){
				eligibleForEarly = true;
				if (qualification.getEquipmentRestrictions() != null
					&& qualification.getEquipmentRestrictions().getInclusions() != null
					&& qualification.getEquipmentRestrictions().getInclusions().getDeviceName()
						.size() == 1
					&& qualification.getEquipmentRestrictions().getInclusions().getDeviceName()
						.get(0) == DeviceNameType.I_PHONE){
				    earlyIPhone = true;
				}
			    }else if (qualification.getQualificationDescription().equals("STANDARD_UPGRADE")){
				eligibleForStandard = true;
			    }
			}

			if (qualification.getQualificationDescription().equals("STANDARD_UPGRADE")){
			    subscribersLineInfo.setStdEligibilityDate(Util.toUtilDate(qualification
				    .getEligibilityDate()));
			}else if (qualification.getQualificationDescription().equals("EARLY_UPGRADE")){
			    subscribersLineInfo.setEarlyEligibilityDate(Util.toUtilDate(qualification
				    .getEligibilityDate()));
			}
		    }

		    if (eligibleForEarly && !eligibleForStandard){
			isEarly = true;
			if (earlyIPhone){
			    isIphone = true;
			}
		    }

		    if (eligibleForStandard && !isEarly && !isIphone){
			subscribersLineInfo.setStandardEligible(true);
			subscribersLineInfo.setEligibilityType("STANDARD_UPGRADE");
		    }else{
			subscribersLineInfo.setStandardEligible(false);
		    }

		    if (isEarly && isIphone){
			subscribersLineInfo.setEarlyEligible(true);
			subscribersLineInfo.setEligibilityType("EARLY_IPHONE_UPGRADE");
		    }else if (isEarly && !isIphone){
			subscribersLineInfo.setEarlyEligible(true);
			subscribersLineInfo.setEligibilityType("EARLY_UPGRADE");
		    }else{
			subscribersLineInfo.setEarlyEligible(false);
		    }

		}
		allSubscribersLineInfo.add(subscribersLineInfo);
	    }

	    // InActive Lines Processing only
	    if (!capResponse.getBody().getAccountInfo().getInactiveLines().isEmpty()){
		logger.info("Processing the inactive Subscriber Lines");
		for(int i = 0; i < capResponse.getBody().getAccountInfo().getInactiveLines().size(); ++i){
		    Line subsInactiveLineInfo = new Line();
		    InactiveLinesType inactiveLinesType = capResponse.getBody().getAccountInfo().getInactiveLines()
			    .get(i);

		    subsInactiveLineInfo.setMobileNumber(inactiveLinesType.getInactiveNumber());
		    if (StringUtils.isNotBlank(inactiveLinesType.getInactiveStatus())){
			subsInactiveLineInfo.setLineStatus(inactiveLinesType.getInactiveStatus());
		    }

		    allSubscribersLineInfo.add(subsInactiveLineInfo);
		}
	    }
	    carrierSubscription.setLines(allSubscribersLineInfo);
	    dashboardCarrierData.setSubscriptionInfo(carrierSubscription);
	}

	return dashboardCarrierData;
    }

    private Carrier getCarrier(DestinationType destinationId) {
	switch (destinationId) {
	    case ATT:
		return Carrier.ATT;
	    case VEZ:
		return Carrier.VERIZON;
	    case TMO:
		return Carrier.TMOBILE;
	    case SPR:
		return Carrier.SPRINT;
	}
	return null;
    }

    private IseBusinessException interpretCapResultInfo(ErrorInfoType resultInfo, String capTransactionId) {

	IseBusinessException businessException;

	if (resultInfo.getResultCode() == null){
	    businessException = new IseBusinessException(IseExceptionCodeEnum.CapException,
		    "CAP result code is null for Transaction : " + capTransactionId);
	}else{
	    long capResultCode = resultInfo.getResultCode().longValue();
	    logger.error("CAP ERROR CODE : " + capResultCode);
	    logger.error("CAP ERROR TYPE : " + resultInfo.getResultType());
	    logger.error("CAP ERROR - CATEGORY CODE : " + resultInfo.getCategoryCode());
	    logger.error("CAP ERROR DETAIL : " + resultInfo.getResultDetails());

	    if (capResultcodeMap.containsKey(capResultCode)){
		businessException = new IseBusinessException(capResultcodeMap.get(capResultCode), resultInfo
			.getResultDetails());
	    }else{
		businessException = new IseBusinessException(IseExceptionCodeEnum.CapException,
			"Unknown CAP result code for Transaction : " + capTransactionId + " : "
				+ resultInfo.getResultDetails());
	    }

	}

	return businessException;
    }

    private String mapPlan(String plan) {
	if (!StringUtils.isEmpty(plan)){
	    if (plan.equalsIgnoreCase("INDIVIDUAL")){
		plan = "Ind.";
		return plan;
	    }else if (plan.equalsIgnoreCase("FAMILY_PRIMARY")){
		plan = "Fam Pri.";
		return plan;
	    }else if (plan.equalsIgnoreCase("FAMILY_SECONDARY")){
		plan = "Fam Sec.";
		return plan;
	    }else if (plan.equalsIgnoreCase("ACCOUNT_LEVEL_PLAN")){
		plan = "Share";
		return plan;
	    }else if (plan.equalsIgnoreCase("ACCOUNT_LEVEL_V")){
		plan = "Share";
		return plan;
	    }else if (plan.equalsIgnoreCase("ACCOUNT_LEVEL_VM")){
		plan = "Share";
		return plan;
	    }else if (plan.equalsIgnoreCase("ACCOUNT_LEVEL_D1")){
		plan = "Share";
		return plan;
	    }else if (plan.equalsIgnoreCase("ACCOUNT_LEVEL_VD")){
		plan = "Share";
		return plan;
	    }
	    return plan;
	}else{
	    return null;
	}
    }

    public void setRequestMarshaller(Marshaller requestMarshaller) {
	this.requestMarshaller = requestMarshaller;
    }

    public void setResponseUnMarshaller(Unmarshaller responseUnMarshaller) {
	this.responseUnMarshaller = responseUnMarshaller;
    }
}
