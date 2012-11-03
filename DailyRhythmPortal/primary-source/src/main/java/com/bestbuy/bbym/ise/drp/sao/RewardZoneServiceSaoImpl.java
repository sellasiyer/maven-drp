package com.bestbuy.bbym.ise.drp.sao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.domain.Address;
import com.bestbuy.bbym.ise.drp.domain.RewardZone;
import com.bestbuy.bbym.ise.drp.domain.RwzMemberCert;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientrwzcerts.MemberCertificateType;
import com.bestbuy.bbym.ise.iseclientrwzcerts.MemberRequestType;
import com.bestbuy.bbym.ise.iseclientrwzcerts.MemberResponseType;
import com.bestbuy.bbym.ise.iseclientrwzcerts.RZGetCertsByMemberRequestType;
import com.bestbuy.bbym.ise.iseclientrwzcerts.RZGetCertsByMemberResponseType;
import com.bestbuy.bbym.ise.iseclientrwzmember.CustomerType;
import com.bestbuy.bbym.ise.iseclientrwzmember.InquireRewardZoneMbrResponseType;
import com.bestbuy.bbym.ise.iseclientrwzmember.MembershipListResponseType;
import com.bestbuy.bbym.ise.iseclientrwzmember.MembershipResponseType;
import com.bestbuy.bbym.ise.iseclientrwzmember.PhoneType;
import com.bestbuy.bbym.ise.iseclientrwzmember.RZMemberInquiryRequestType;
import com.bestbuy.bbym.ise.iseclientrwzmember.RZMemberInquiryResponseType;
import com.bestbuy.bbym.ise.iseclientrwzmember.RequestMetaType;
import com.bestbuy.bbym.ise.iseclientrwzmember.Security;
import com.bestbuy.bbym.ise.iseclientrwzpoints.RZValidateMemberRequestType;
import com.bestbuy.bbym.ise.iseclientrwzpoints.RZValidateMemberResponseType;
import com.bestbuy.bbym.ise.iseclientrwzpoints.RewardZoneAccountType;
import com.bestbuy.bbym.ise.util.Util;

@Service("rewardZoneServiceSao")
public class RewardZoneServiceSaoImpl extends AbstractSao implements RewardZoneServiceSao {

    private static Logger logger = LoggerFactory.getLogger(RewardZoneServiceSaoImpl.class);
    private static final String SERVICE = "REWARDZONE SERVICE ";

    @Override
    public String getRewardZoneMemberNumber(String customerFirstName, String customerLastName, String phoneNumber,
	    String email) throws ServiceException, IseBusinessException {

	logger.info("In the getRewardZoneMemberNumber Method");

	RZMemberInquiryRequestType rzMemberInquiryRequestType = prepareRZMemberInquiryRequestType(customerFirstName,
		customerLastName, phoneNumber, email);

	Security security = new Security();
	security.getAny().add(getSamlSecurityHeader());

	try{
	    RZMemberInquiryResponseType rzMemberInquiryResponseType = getRewardZoneMemberLookupService().inquireMember(
		    rzMemberInquiryRequestType, security);
	    return retrieveRewardZoneMember(rzMemberInquiryResponseType, customerFirstName, customerLastName,
		    phoneNumber, email);
	}catch(Throwable t){
	    handleException(SERVICE + " - get reward zone member number", t);
	}

	return null;
    }

    private String retrieveRewardZoneMember(RZMemberInquiryResponseType rzMemberInquiryResponseType,
	    String customerFirstName, String customerLastName, String phoneNumber, String email) {
	List<InquireRewardZoneMbrResponseType> inquireRwzMbrResponseTypeList = rzMemberInquiryResponseType
		.getRewardZoneMember();
	return findCustomerMatchScoreAndReturnMemType(inquireRwzMbrResponseTypeList, customerFirstName,
		customerLastName, phoneNumber, email);
    }

    private String findCustomerMatchScoreAndReturnMemType(
	    List<InquireRewardZoneMbrResponseType> inquireRwzMbrResponseTypeList, String customerFirstName,
	    String customerLastName, String phoneNumber, String email) {

	String memberNumber = null;
	HashMap<Integer, List<MembershipResponseType>> tempDataMap = new HashMap<Integer, List<MembershipResponseType>>();

	int matchScore = 0;

	if (inquireRwzMbrResponseTypeList != null){
	    for(InquireRewardZoneMbrResponseType inquireRewardZoneMbrResponseType: inquireRwzMbrResponseTypeList){
		String memberType = inquireRewardZoneMbrResponseType.getMemberType();

		if (memberType.equals("R")){
		    CustomerType custType = inquireRewardZoneMbrResponseType.getCustomer();
		    if (custType.getFirstName().equalsIgnoreCase(customerFirstName)
			    && custType.getLastName().equalsIgnoreCase(customerLastName)){
			matchScore += 1;
		    }
		    if (custType.getPhone() != null && custType.getPhone().getAreaCode() != null
			    && custType.getPhone().getNumber() != null){
			String phNumber = custType.getPhone().getAreaCode() + "" + custType.getPhone().getNumber();
			if (phNumber.equals(phoneNumber)){
			    matchScore += 1;
			}
		    }
		    if (custType.getEmail() != null && custType.getEmail().equalsIgnoreCase(email)){
			matchScore += 1;
		    }
		    if (inquireRewardZoneMbrResponseType.getMemberShipList() != null){
			if (matchScore > 0)
			    tempDataMap.put(matchScore, inquireRewardZoneMbrResponseType.getMemberShipList()
				    .getMembership());
		    }
		}
	    }
	    if (tempDataMap.size() > 0){
		int maxScoreSearch = Collections.max(tempDataMap.keySet());
		List<MembershipResponseType> memResponseType = tempDataMap.get(maxScoreSearch);

		for(MembershipResponseType membershipResponseType: memResponseType){
		    if (membershipResponseType != null && membershipResponseType.getMemberType() != null
			    && membershipResponseType.getMemberType().equals("R")){
			memberNumber = membershipResponseType.getMemberNumber();
			break;
		    }
		}
	    }
	}
	return memberNumber;

    }

    private RZMemberInquiryRequestType prepareRZMemberInquiryRequestType(String customerFirstName,
	    String customerLastName, String phoneNumber, String email) throws ServiceException {

	RZMemberInquiryRequestType rzMemberInquiryRequestType = new RZMemberInquiryRequestType();

	rzMemberInquiryRequestType.setRequestMetaData(getRequestMetaType());
	rzMemberInquiryRequestType.setCustomerFirstName(customerFirstName);
	rzMemberInquiryRequestType.setCustomerLastName(customerLastName);
	if (phoneNumber != null && phoneNumber.length() >= 10){

	    PhoneType phoneType = new PhoneType();
	    if (phoneNumber.length() == 10){
		phoneType.setAreaCode(phoneNumber.substring(0, 3));
		phoneType.setNumber(phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6, phoneNumber.length()));
	    }
	    rzMemberInquiryRequestType.setPhone(phoneType);
	}
	rzMemberInquiryRequestType.setEmail(email);

	return rzMemberInquiryRequestType;
    }

    private RequestMetaType getRequestMetaType() throws ServiceException {
	RequestMetaType requestMetaType = new RequestMetaType();
	requestMetaType.setApplicationID(DrpConstants.APPLICATION_ID);
	requestMetaType.setUserID("a123456");
	requestMetaType.setTrainingMode("No");
	requestMetaType.getProgramEnabled().add("R");
	return requestMetaType;
    }

    private Object copyBeanProperties(Object ob1, Object ob2) {
	BeanUtils.copyProperties(ob1, ob2);
	return ob2;
    }

    @Override
    public RewardZone getRewardZonePointsAndCerts(String memberNumber) throws ServiceException, IseBusinessException {

	logger.info("In the getRewardZonePointsAndCerts Method");
	RewardZone rewardZone = new RewardZone();

	try{
	    invokeRwzPointsLookupService(memberNumber, rewardZone);
	    invokeRwzCertLookupService(memberNumber, rewardZone);

	}catch(ServiceException se){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + " ServiceException - getRewardZonePointsAndCerts : " + se.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, se.getMessage());
	}catch(Throwable t){
	    handleException(SERVICE + " - getRewardZonePointsAndCerts", t);
	}
	logger.info("Returing the RewardZone Points and Certs Details");

	return rewardZone;
    }

    private void invokeRwzPointsLookupService(String memberNumber, RewardZone rewardZone) throws IseBusinessException,
	    ServiceException {
	try{
	    RZValidateMemberRequestType validateMemberRequest = new RZValidateMemberRequestType();
	    validateMemberRequest.setMemberNumber(memberNumber);
	    validateMemberRequest
		    .setRequestMetaData((com.bestbuy.bbym.ise.iseclientrwzpoints.RequestMetaType) copyBeanProperties(
			    getRequestMetaType(), new com.bestbuy.bbym.ise.iseclientrwzpoints.RequestMetaType()));

	    com.bestbuy.bbym.ise.iseclientrwzpoints.Security security = new com.bestbuy.bbym.ise.iseclientrwzpoints.Security();
	    security.getAny().add(getSamlSecurityHeader());

	    RZValidateMemberResponseType validateMemResponseType = getRewardZonePointsLookupService().validateMember(
		    validateMemberRequest, security);

	    if (validateMemResponseType.getResponseMetaData().getSystemCode() == 1006){
		populatePointsAccountDetails(validateMemResponseType, rewardZone);
		populateRzCustomerDetails(validateMemResponseType, rewardZone);

	    }else if (validateMemResponseType.getResponseMetaData().getSystemCode() == 1010){
		// throw new business exception for Member NotFound.
		throw new IseBusinessException("RewardZone member not found.");
	    }
	}catch(IseBusinessException exception){
	    throw new IseBusinessException(exception.getMessage());
	}catch(Exception exception){
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, exception.getMessage());
	}

    }

    private void populatePointsAccountDetails(RZValidateMemberResponseType validateMemResponseType,
	    RewardZone rewardZone) {
	RewardZoneAccountType rewardZoneAccountType = validateMemResponseType.getAccount();
	if (rewardZoneAccountType != null){
	    rewardZone.setMemberNumber(rewardZoneAccountType.getMemberNumber());
	    String memTier = "";
	    String memberTier = rewardZoneAccountType.getMemberTier();
	    if (memberTier.equals("10130") || memberTier.equals("10120")){
		memTier = RewardZone.MEMBER_TIER_STANDARD;
	    }else if (memberTier.equals("10025")){
		memTier = RewardZone.MEMBER_TIER_SILVER;
	    }else if (memberTier.equals("10150")){
		memTier = RewardZone.MEMBER_TIER_PREMIUM;
	    }
	    rewardZone.setMemberTierCode(rewardZoneAccountType.getMemberTier());
	    rewardZone.setMemberTier(memTier);
	    rewardZone.setAccountStatus(rewardZoneAccountType.getStatus());
	    if (rewardZoneAccountType.getPointsInfo() != null
		    && rewardZoneAccountType.getPointsInfo().getPointsBalance() != null){
		rewardZone.setPointsBalance(rewardZoneAccountType.getPointsInfo().getPointsBalance().intValue());
		rewardZone.setConvertedPointsDollarAmt(convertPointsToDollars(rewardZoneAccountType.getPointsInfo()
			.getPointsBalance().intValue()));
	    }
	    if (rewardZoneAccountType.getCreditCard() != null && rewardZoneAccountType.getCreditCard().size() > 0){
		if (StringUtils.isNotEmpty(rewardZoneAccountType.getCreditCard().get(0).getAcctType())){
		    if (rewardZoneAccountType.getCreditCard().get(0).getAcctType().equalsIgnoreCase(
			    RewardZone.BESTBUY_COMPANY_CARD)){
			rewardZone.setCcAccountType(RewardZone.BESTBUY_COMPANY_CARD);
		    }else if (rewardZoneAccountType.getCreditCard().get(0).getAcctType().equalsIgnoreCase(
			    RewardZone.BESTBUY_MASTER_CARD)){
			rewardZone.setCcAccountType(RewardZone.BESTBUY_MASTER_CARD);
		    }
		}
	    }
	}
    }

    private void populateRzCustomerDetails(RZValidateMemberResponseType validateMemResponseType, RewardZone rewardZone) {
	RewardZoneAccountType rewardZoneAccountType = validateMemResponseType.getAccount();
	if (rewardZoneAccountType != null && rewardZoneAccountType.getCustomer() != null){
	    rewardZone.setFirstName(rewardZoneAccountType.getCustomer().getFirstName());
	    rewardZone.setLastName(rewardZoneAccountType.getCustomer().getLastName());
	}
    }

    /******
     * Business Logic to convert Points to dollar Amt
     * 
     */
    private int convertPointsToDollars(int pointsBalance) {
	return (pointsBalance / 250) * 5;
    }

    private void invokeRwzCertLookupService(String memberNumber, RewardZone rewardZone) throws ServiceException {

	RZGetCertsByMemberRequestType certsRequestType = new RZGetCertsByMemberRequestType();
	MemberRequestType memberRequestType = new MemberRequestType();
	memberRequestType.setMemberNumber(memberNumber);
	memberRequestType.setCertStatus("I");
	memberRequestType.setIncludeExpired(false);

	certsRequestType
		.setRequestMetaData((com.bestbuy.bbym.ise.iseclientrwzcerts.RequestMetaType) copyBeanProperties(
			getRequestMetaType(), new com.bestbuy.bbym.ise.iseclientrwzcerts.RequestMetaType()));
	certsRequestType.getMemberRequest().add(memberRequestType);

	RZGetCertsByMemberResponseType certsByMemberResponseType = getRewardZoneCertsLookupService().getCertsByMember(
		certsRequestType);

	if (certsByMemberResponseType.getResponseMetaData().getSystemCode() != 1012){
	    rewardZone.setMemberCertsAvailable(false);
	    List<MemberResponseType> memResList = certsByMemberResponseType.getMemberResponse();
	    if (memResList != null && memResList.size() > 0 && memResList.get(0) != null)
		if (memResList.get(0).getMemberResponseCode() == 1018){
		    rewardZone.setMemberCertsAvailable(false);
		    logger.info("Member Certs Status ::" + memResList.get(0).getMemberResponseMessage());
		}else{
		    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, memResList.get(0)
			    .getMemberResponseMessage());
		}
	}else{
	    List<RwzMemberCert> rwzMemberCertList = new ArrayList<RwzMemberCert>();
	    List<MemberResponseType> memberResponseTypeList = certsByMemberResponseType.getMemberResponse();

	    for(MemberResponseType memberResponseType: memberResponseTypeList){

		List<MemberCertificateType> memberCertificateTypeList = memberResponseType.getMemberCertificates();
		if (memberCertificateTypeList != null){
		    rewardZone.setMemberCertsAvailable(true);

		    for(MemberCertificateType memberCertificateType: memberCertificateTypeList){
			RwzMemberCert cert = new RwzMemberCert();
			cert.setCertId(memberCertificateType.getCertId());
			cert.setCertStatus(memberCertificateType.getCertStatus());
			cert.setCertAmount(memberCertificateType.getCertAmount() != null?memberCertificateType
				.getCertAmount().intValue():0);
			cert.setIssuedDate(Util.toUtilDate(memberCertificateType.getIssuedDate()));
			cert.setExpiredDate(Util.toUtilDate(memberCertificateType.getExpiredDate()));
			rwzMemberCertList.add(cert);
		    }
		}
	    }
	    rewardZone.setRwzMemberCertList(rwzMemberCertList);
	}

    }

    @Override
    public RewardZone validateRewardZoneMember(String memberNumber) throws ServiceException, IseBusinessException {
	RewardZone rewardZone = new RewardZone();
	invokeRwzPointsLookupService(memberNumber, rewardZone);
	return rewardZone;
    }

    public List<RewardZone> retrieveCustomerRewardZone(String customerFirstName, String customerLastName,
	    String phoneNumber, String email) throws ServiceException, IseBusinessException {

	List<RewardZone> rewardZoneMembersList = retrieveCustomerRewardZoneMembersList(customerFirstName,
		customerLastName, phoneNumber, email);

	for(RewardZone rewardZone: rewardZoneMembersList){
	    String rzNum = rewardZone.getMemberNumber();
	    invokeRwzPointsLookupService(rzNum, rewardZone);
	}
	return rewardZoneMembersList;

    }

    private List<RewardZone> retrieveCustomerRewardZoneMembersList(String customerFirstName, String customerLastName,
	    String phoneNumber, String email) throws ServiceException, IseBusinessException {

	List<RewardZone> rewardZoneMembersList = new ArrayList<RewardZone>();

	RZMemberInquiryRequestType rzMemberInquiryRequestType = prepareRZMemberInquiryRequestType(customerFirstName,
		customerLastName, phoneNumber, email);

	Security security = new Security();
	security.getAny().add(getSamlSecurityHeader());

	try{
	    RZMemberInquiryResponseType rzMemberInquiryResponseType = getRewardZoneMemberLookupService().inquireMember(
		    rzMemberInquiryRequestType, security);

	    if (rzMemberInquiryResponseType.getResponseMetaData().getSystemCode() == 1006){

		List<InquireRewardZoneMbrResponseType> inquireRewardZoneMbrResponseTypeList = rzMemberInquiryResponseType
			.getRewardZoneMember();

		for(InquireRewardZoneMbrResponseType inquireRewardZoneMbrResponseType: inquireRewardZoneMbrResponseTypeList){

		    // Only Reward zone members  
		    if ("R".equalsIgnoreCase(inquireRewardZoneMbrResponseType.getMemberType())){

			CustomerType customerType = inquireRewardZoneMbrResponseType.getCustomer();

			MembershipListResponseType membershipListResponseType = inquireRewardZoneMbrResponseType
				.getMemberShipList();

			List<MembershipResponseType> membershipResponseTypeList = membershipListResponseType
				.getMembership();

			for(MembershipResponseType membershipResponseType: membershipResponseTypeList){
			    // Only Reward zone member ship  	  
			    if ("R".equalsIgnoreCase(membershipResponseType.getMemberType())){
				RewardZone rewardZone = new RewardZone();
				if (customerType != null){
				    populateCustomer(rewardZone, customerType);
				}
				rewardZone.setMemberNumber(membershipResponseType.getMemberNumber());
				rewardZoneMembersList.add(rewardZone);

			    }
			}
		    }
		}

	    }else if (rzMemberInquiryResponseType.getResponseMetaData().getSystemCode() == 1010){
		throw new IseBusinessException("RewardZone member not found.");
	    }

	}catch(Throwable t){
	    handleException(SERVICE + " - get reward zone details", t);
	}

	return rewardZoneMembersList;

    }

    private void populateCustomer(RewardZone rewardZone, CustomerType customerType) {
	Address address = new Address();

	rewardZone.setFirstName(customerType.getFirstName());
	rewardZone.setLastName(customerType.getLastName());
	if (customerType.getAddress() != null){
	    address.setAddressLine1(customerType.getAddress().getAddressLine1());
	    address.setAddressLine2(customerType.getAddress().getAddressLine2());
	    address.setAddressType(customerType.getAddress().getType());
	    address.setCity(customerType.getAddress().getCity());
	    address.setState(customerType.getAddress().getState());
	    address.setZipcode(customerType.getAddress().getZip());
	    address.setCountry(customerType.getAddress().getCountry());
	    rewardZone.setAddress(address);
	}

	if (customerType.getPhone() != null && customerType.getPhone().getAreaCode() != null
		&& customerType.getPhone().getNumber() != null){
	    rewardZone
		    .setContactPhone(customerType.getPhone().getAreaCode() + "" + customerType.getPhone().getNumber());
	}
    }

}
