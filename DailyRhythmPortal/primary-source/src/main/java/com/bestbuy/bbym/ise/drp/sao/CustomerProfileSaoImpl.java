package com.bestbuy.bbym.ise.drp.sao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.SOAPElement;
import javax.xml.ws.soap.SOAPFaultException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.domain.CustomerSearchCriteria;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientca.AddressType;
import com.bestbuy.bbym.ise.iseclientca.AllSearchPlansType;
import com.bestbuy.bbym.ise.iseclientca.CustomerSearchConfigType;
import com.bestbuy.bbym.ise.iseclientca.EmailType;
import com.bestbuy.bbym.ise.iseclientca.NameType;
import com.bestbuy.bbym.ise.iseclientca.PhoneType;
import com.bestbuy.bbym.ise.iseclientca.SearchCustomerRequestType;
import com.bestbuy.bbym.ise.iseclientca.SearchCustomerRequestType.SearchParameters;
import com.bestbuy.bbym.ise.iseclientca.SearchCustomerResponseType;
import com.bestbuy.bbym.ise.iseclientca.SearchCustomerResponseType.Customer.Account;
import com.bestbuy.bbym.ise.iseclientca.Security;
import com.bestbuy.bbym.ise.util.Util;

/**
 * @author a218635  
 */
@Service("customerProfileSao")
public class CustomerProfileSaoImpl extends AbstractSao implements CustomerProfileSao {

    private static Logger logger = LoggerFactory.getLogger(CustomerProfileSaoImpl.class);
    private static final String SERVICE = "CA ";

    /**
     * Gets the list of matching Best Buy customer profile information based on
     * the search Criteria supplied by the ISE user.
     */
    @Override
    public List<Customer> searchBbyCustomer(Customer bbyCustomer, CustomerSearchCriteria searchCriteria, DrpUser drpUser)
	    throws ServiceException, IseBusinessException {

	logger.info("In the searchBbyCustomer Method");
	List<Customer> custProfileInfoList = new ArrayList<Customer>();
	SearchCustomerRequestType searchCustReq = prepareCARequest(bbyCustomer, searchCriteria, drpUser);

	try{
	    SearchCustomerResponseType searchCustResp = getCustomerAccountServices().searchCustomer(searchCustReq,
		    getSecurityToken());
	    custProfileInfoList = getAllCustomerProfileDetails(searchCustResp);
	}catch(SOAPFaultException sfe){
	    logger.info("In the CA's SOAPFaultException : " + sfe.getFault().getFaultCode() + " : "
		    + sfe.getFault().getFaultString());
	    SOAPElement soapElement = (SOAPElement) sfe.getFault().getDetail().getChildElements().next();
	    Iterator itParams = soapElement.getChildElements();
	    String errorCode = "";
	    String errorMessage = "";
	    if (itParams.hasNext())
		errorCode = ((SOAPElement) itParams.next()).getValue();
	    if (itParams.hasNext())
		errorMessage = ((SOAPElement) itParams.next()).getValue();
	    logger.info("CA errorCode : " + errorCode + ", CA errorMessage : " + errorMessage);
	    if (errorCode.equalsIgnoreCase("4134"))
		return custProfileInfoList;
	    throw new IseBusinessException(errorMessage);
	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " ServiceException - searchBbyCustomer : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - search by customer", t);
	}
	logger.info("Returning the List of Customer Profiles from the Service to the UI");
	return custProfileInfoList;
    }

    /**
     * Sets all the request parameters based on the search criteria supplied by
     * the ISE user.
     */
    private SearchCustomerRequestType prepareCARequest(Customer bbyCustomer, CustomerSearchCriteria searchCriteria,
	    DrpUser drpUser) {

	SearchCustomerRequestType searchCustReq = new SearchCustomerRequestType();
	String iseTransactionId = Util.generateTransactionId();

	SearchParameters searchParams = getQueryParams(bbyCustomer, searchCriteria);
	searchCustReq.setSearchParameters(searchParams);
	try{
	    searchCustReq.setSourceId(getDrpPropertiesService().getProperty("APPLICATION_NAME"));
	    searchCustReq.setUserId(drpUser.getUserId());
	    searchCustReq.setProgramId(getDrpPropertiesService().getProperty("SOURCE_SYSTEM"));
	}catch(ServiceException e){
	    logger.error("Error setting search parameters", e);
	}
	searchCustReq.setRequestId(iseTransactionId);

	CustomerSearchConfigType configParams = new CustomerSearchConfigType();
	configParams.setMaxRows(100);
	configParams.setSearchTimeLimitMS(10000);
	if (CustomerSearchCriteria.PHONE_NUMBER == searchCriteria)
	    configParams.setSearchTypeToPerform(AllSearchPlansType.SEARCH_PLAN_4);
	else if (CustomerSearchCriteria.EMAIL == searchCriteria)
	    configParams.setSearchTypeToPerform(AllSearchPlansType.EC_BY_EMAIL);
	else if (CustomerSearchCriteria.FN_LN_ZIP == searchCriteria)
	    configParams.setSearchTypeToPerform(AllSearchPlansType.SEARCH_PLAN_7);
	configParams.setDiscardResult(false);
	configParams.setPerformDrillDown(true);
	configParams.setRetrieveForOrgID(100);
	searchCustReq.setConfiguration(configParams);

	return searchCustReq;
    }

    /**
     * Sets the query parameters based on the search criteria supplied by the
     * ISE user.
     */
    private SearchParameters getQueryParams(Customer customer, CustomerSearchCriteria searchCriteria) {

	logger.info("In the getQueryParams Method : About to set the search Criteria based on : "
		+ searchCriteria.toString());
	com.bestbuy.bbym.ise.iseclientca.SearchCustomerRequestType.SearchParameters.Customer caCustomer = new com.bestbuy.bbym.ise.iseclientca.SearchCustomerRequestType.SearchParameters.Customer();

	if (CustomerSearchCriteria.PHONE_NUMBER == searchCriteria){
	    PhoneType phoneNum = new PhoneType();
	    if (StringUtils.isNotBlank(customer.getContactPhone())){
		phoneNum.setAreaCode(customer.getContactPhone().substring(0, 3));
		phoneNum.setNumber(customer.getContactPhone().substring(3, 10));
		if (phoneNum != null)
		    caCustomer.setPhone(phoneNum);
	    }
	}else if (CustomerSearchCriteria.EMAIL == searchCriteria){
	    EmailType emailAddType = new EmailType();
	    emailAddType.setEmailAddress(customer.getEmail());
	    if (emailAddType != null)
		caCustomer.setEmailAddress(emailAddType);
	}else if (CustomerSearchCriteria.FN_LN_ZIP == searchCriteria){
	    NameType custName = new NameType();
	    com.bestbuy.bbym.ise.iseclientca.AddressType postalAddress = new com.bestbuy.bbym.ise.iseclientca.AddressType();
	    com.bestbuy.bbym.ise.iseclientca.AddressType.Zip zip = new com.bestbuy.bbym.ise.iseclientca.AddressType.Zip();
	    if (StringUtils.isNotBlank(customer.getFirstName()))
		custName.setFirstName(customer.getFirstName());
	    if (StringUtils.isNotBlank(customer.getLastName()))
		custName.setLastName(customer.getLastName());

	    if (custName != null)
		caCustomer.setName(custName);

	    zip.setZip(customer.getAddress().getZipcode());
	    postalAddress.setZip(zip);
	    caCustomer.setPostalAddress(postalAddress);
	}

	SearchParameters searchValues = new SearchParameters();
	searchValues.setCustomer(caCustomer);
	logger.info("Set the search Criteria successfully");
	return searchValues;

    }

    /**
     * Sets the Header security Token.
     */
    private Security getSecurityToken() {
	logger.info("In the getSecurityToken method : Setting up the request header Token");
	Security secToken = new Security();
	secToken.getAny().add(getSamlSecurityHeader());
	return secToken;
    }

    /**
     * Retrieves the List of Customer Profiles that was returned from Profile
     * Search.
     */
    private List<Customer> getAllCustomerProfileDetails(SearchCustomerResponseType searchCustResp) {

	logger
		.info("In the getAllCustomerProfileDetails method : About to interpret the customer Profiles returned from Profile Search");
	List<com.bestbuy.bbym.ise.iseclientca.SearchCustomerResponseType.Customer> custAccProfileList = searchCustResp
		.getCustomer();
	List<Customer> custProfileInfoList = new ArrayList<Customer>();

	logger.info("In the getAllCustomerProfileDetails method : Number of profile Retrieved : "
		+ custAccProfileList.size());

	for(com.bestbuy.bbym.ise.iseclientca.SearchCustomerResponseType.Customer custProfile: custAccProfileList){

	    Customer customer = new Customer();
	    if (StringUtils.isNotBlank(custProfile.getCustomerId()))
		customer.setBbyCustomerId(custProfile.getCustomerId());

	    if (custProfile.getName() != null){
		if (StringUtils.isNotBlank(custProfile.getName().getFirstName()))
		    customer.setFirstName(custProfile.getName().getFirstName());

		if (StringUtils.isNotBlank(custProfile.getName().getLastName()))
		    customer.setLastName(custProfile.getName().getLastName());
	    }
	    if (custProfile.getPhoneAddress() != null && !custProfile.getPhoneAddress().isEmpty()){
		com.bestbuy.bbym.ise.iseclientca.PhoneType phoneNum = new com.bestbuy.bbym.ise.iseclientca.PhoneType();
		phoneNum = custProfile.getPhoneAddress().get(0);
		customer.setContactPhone(phoneNum.getAreaCode() + phoneNum.getNumber());
	    }

	    if (custProfile.getPostalAddress() != null)
		customer.setAddress(getCustAddressDetails(custProfile.getPostalAddress()));

	    if (custProfile.getEmailAddress() != null
		    && StringUtils.isNotBlank(custProfile.getEmailAddress().getEmailAddress())){
		customer.setEmail(custProfile.getEmailAddress().getEmailAddress());
	    }

	    if (custProfile.getAccount() != null)
		customer.setRewardZoneId(getCustRewardZoneDetails(custProfile.getAccount(), "RWZ_NUM"));
	    // customer.setRewardZoneStatus(getCustRewardZoneDetails(custProfile.getAccount(),"RWZ_STATUS"));

	    custProfileInfoList.add(customer);
	}

	logger.info("In the getAllCustomerProfileDetails method : About to Return the customer Profiles");
	return custProfileInfoList;
    }

    /**
     * Filters and retrieves customer's Reward zone Info from the customer's
     * SubAccount.
     */
    private String getCustRewardZoneDetails(List<Account> accList, String field) {

	// logger.info("In the getCustRewardZoneDetails method : checking whether it is a reward zone customer");
	String rewardZonefield = "";
	for(Account allCustomerSubAcc: accList){

	    if (StringUtils.isNotBlank(allCustomerSubAcc.getAccountCategory())
		    && allCustomerSubAcc.getAccountCategory().equalsIgnoreCase("RWZ")){
		logger.info("In the getCustRewardZoneDetails method : It is a reward zone customer");
		if (field.equalsIgnoreCase("RWZ_NUM"))
		    rewardZonefield = allCustomerSubAcc.getAccountNumber();
		else if (field.equalsIgnoreCase("RWZ_STATUS"))
		    rewardZonefield = allCustomerSubAcc.getStatus();
	    }
	}
	return rewardZonefield;
    }

    /**
     * Retrieves the BBY customer's Profile address.
     */
    private Address getCustAddressDetails(AddressType custAccRespAddress) {
	// logger.info("In the getCustAddressDetails method : About to map the customer information");

	Address custAddr = new Address();

	if (StringUtils.isNotBlank(custAccRespAddress.getType()))
	    custAddr.setAddressType(custAccRespAddress.getType());

	if (StringUtils.isNotBlank(custAccRespAddress.getAddressLine1()))
	    custAddr.setAddressLine1(custAccRespAddress.getAddressLine1());

	if (StringUtils.isNotBlank(custAccRespAddress.getAddressLine2()))
	    custAddr.setAddressLine2(custAccRespAddress.getAddressLine2());

	if (StringUtils.isNotBlank(custAccRespAddress.getCity()))
	    custAddr.setCity(custAccRespAddress.getCity());

	if (StringUtils.isNotBlank(custAccRespAddress.getState()))
	    custAddr.setState(custAccRespAddress.getState());

	if (custAccRespAddress.getZip() != null){
	    if (StringUtils.isNotBlank(custAccRespAddress.getZip().getZip()))
		custAddr.setZipcode(custAccRespAddress.getZip().getZip());
	    if (StringUtils.isNotBlank(custAccRespAddress.getZip().getZip4()))
		custAddr.setZipcode4(custAccRespAddress.getZip().getZip4());
	}

	if (StringUtils.isNotBlank(custAccRespAddress.getCountry()))
	    custAddr.setCountry(custAccRespAddress.getCountry());
	else
	    custAddr.setCountry("USA");

	return custAddr;

    }

}
