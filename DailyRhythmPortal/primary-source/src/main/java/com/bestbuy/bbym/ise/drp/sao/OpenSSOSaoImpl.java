package com.bestbuy.bbym.ise.drp.sao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientopensso.Attribute;
import com.bestbuy.bbym.ise.iseclientopensso.GeneralFailure_Exception;
import com.bestbuy.bbym.ise.iseclientopensso.InvalidCredentials_Exception;
import com.bestbuy.bbym.ise.iseclientopensso.InvalidPassword_Exception;
import com.bestbuy.bbym.ise.iseclientopensso.NeedMoreCredentials_Exception;
import com.bestbuy.bbym.ise.iseclientopensso.Token;
import com.bestbuy.bbym.ise.iseclientopensso.TokenExpired_Exception;
import com.bestbuy.bbym.ise.iseclientopensso.UserDetails;
import com.bestbuy.bbym.ise.iseclientopensso.UserNotFound_Exception;

@Service("openSSOSao")
public class OpenSSOSaoImpl extends AbstractSao implements OpenSSOSao {

    private static Logger logger = LoggerFactory.getLogger(OpenSSOSaoImpl.class);

    private static final String SERVICE = "OPENSSO ";

    private static final String ATTRIBUTE_USERID = "uid";
    private static final String ATTRIBUTE_SURNAME = "sn";
    private static final String ATTRIBUTE_GIVEN_NAME = "givenName";
    private static final String ATTRIBUTE_MANAGER = "manager";
    private static final String ATTRIBUTE_TELEPHONE_NUMBER = "telephonenumber";
    private static final String ATTRIBUTE_LOCATION_ID = "bboraclelocid";
    private static final String ATTRIBUTE_LOCATION_TYPE = "bboraclehrtype";

    private static final List<String> ATTRIBUTES;

    static{
	List<String> attributes = new ArrayList<String>();
	attributes.addAll(Arrays.asList(new String[] {ATTRIBUTE_USERID, ATTRIBUTE_SURNAME, ATTRIBUTE_GIVEN_NAME,
		ATTRIBUTE_MANAGER, ATTRIBUTE_TELEPHONE_NUMBER, ATTRIBUTE_LOCATION_ID, ATTRIBUTE_LOCATION_TYPE }));
	ATTRIBUTES = Collections.unmodifiableList(attributes);
    }

    /**
     * Authenticates the user using Open SSO.
     */
    public DrpUser authenticate(String userName, String password) throws ServiceException, IseBusinessException {
	logger.info("Authentication user '" + userName + "'");

	try{

	    Token token = getIdentityService().authenticate(userName, password, null);
	    DrpUser user = attributes(null, token.getId());
	    user.setUserId(userName.toUpperCase());
	    return user;

	}catch(ServiceException e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Error in OpenSSO authenticate service call:", e);
	    throw e;
	}catch(GeneralFailure_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "General failure authenticating user '"
		    + userName + "' : " + faultMessage);
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, faultMessage);
	}catch(InvalidCredentials_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Invalid cendentials authenticating user '" + userName + "' : " + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.LoginError, faultMessage);
	}catch(InvalidPassword_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Invalid password authenticating user '"
		    + userName + "' : " + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.LoginError, faultMessage);
	}catch(NeedMoreCredentials_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Need more credentials authenticating user '" + userName + "' : " + faultMessage);
	    throw new ServiceException(IseExceptionCodeEnum.LoginError, faultMessage);
	}catch(UserNotFound_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "User not found authenticating user '"
		    + userName + "' : " + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.LoginError, faultMessage);
	}catch(Throwable t){
	    handleException(SERVICE + " - authenticate", t);
	}
	return null;
    }

    /**
     * Gets the user's information using the OpenSSO subject Id.
     */
    public DrpUser attributes(String oblixToken, String openSsoToken) throws ServiceException, IseBusinessException {
	logger.debug("Getting attributes for oblixToken=" + oblixToken + ", openSsoToken=" + openSsoToken);

	if (oblixToken != null){
	    logger.info("Getting attributes using Oblix token");
	    String openSsoRestUrl = drpPropertiesService.getProperty("OPENSSO_REST_URL");
	    String realm = drpPropertiesService.getProperty("OPENSSO_REALM");
	    String url = openSsoRestUrl + "/UI/Login?realm=/" + realm + "&module=OblixAuth&goto=" + openSsoRestUrl
		    + "/identity/attributes";
	    PostMethod method = new PostMethod(url) {

		/**
		 * Hack to allow following redirect
		 */
		@Override
		public boolean getFollowRedirects() {
		    return true;
		}
	    };
	    method.setRequestHeader("Cookie", COOKIE_NAME_OBLIX + '=' + oblixToken);
	    method.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);

	    HttpClient httpClient = getHttpClient(getTimeout("OPENSSO_TIMEOUT"));
	    try{
		int statusCode = httpClient.executeMethod(method);
		if (statusCode != HttpStatus.SC_OK){
		    logger.error("Method failed: " + method.getStatusLine());
		    return null;
		}

		String response = method.getResponseBodyAsString();
		openSsoToken = getOpenSsoToken(response);
		logger.info("Got openSSO token: " + openSsoToken);

	    }catch(Throwable t){
		handleException(SERVICE + " - attributes", t);
	    }finally{
		method.releaseConnection();
	    }
	}

	Token token = new Token();
	token.setId(openSsoToken);
	UserDetails userDetails;
	try{
	    logger.info("Getting attributes from OpenSSO");
	    userDetails = getIdentityService().attributes(ATTRIBUTES, token);
	    logger.info("Got attributes from OpenSSO");
	}catch(ServiceException e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE
		    + "Error in OpenSSO attributes service call:", e);
	    throw e;
	}catch(GeneralFailure_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "General failure getting attributes: "
		    + faultMessage);
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, faultMessage);
	}catch(TokenExpired_Exception e){
	    String faultMessage = e.getFaultInfo().getMessage().getValue();
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Token expired: " + faultMessage);
	    throw new IseBusinessException(IseExceptionCodeEnum.LoginError, faultMessage);
	}

	return populateUserInfo(token.getId(), userDetails);
    }

    /**
     * Gets the Open SSO token from the response.
     * 
     * @param response
     *            the HTTP response
     * @return the Open SSO token
     * @throws ServiceException
     *             if the response does not contain an OpenSSO token
     */
    private String getOpenSsoToken(String response) throws ServiceException {
	final String REST_OPENSSO_TOKEN = "userdetails.token.id";
	String[] lines = response.split("[\r\n]+");
	for(String line: lines){
	    if (line.startsWith(REST_OPENSSO_TOKEN + "=")){
		return StringUtils.removeStart(line, REST_OPENSSO_TOKEN + "=");
	    }
	}
	logger.warn("Could not get OpenSSO token from response");
	logger.debug("OpenSSO response=" + response);
	throw new ServiceException(IseExceptionCodeEnum.LoginError, "");
    }

    private DrpUser populateUserInfo(String openSsoTokenId, UserDetails userDetails) {

	logger.debug("Populating user with info obtained from OpenSSO");
	DrpUser user = new DrpUser();
	user.setOpenSsoTokenId(openSsoTokenId);

	for(Attribute attribute: userDetails.getAttributes()){
	    if (ATTRIBUTE_USERID.equals(attribute.getName())){
		String value = getValue(attribute.getValues(), 0);
		user.setUserId(value);
		continue;
	    }
	    if (ATTRIBUTE_SURNAME.equals(attribute.getName())){
		String value = getValue(attribute.getValues(), 0);
		user.setLastName(value.toUpperCase());
		continue;
	    }
	    if (ATTRIBUTE_GIVEN_NAME.equals(attribute.getName())){
		String value = getValue(attribute.getValues(), 0);
		user.setFirstName(value.toUpperCase());
		continue;
	    }
	    if (ATTRIBUTE_MANAGER.equals(attribute.getName())){
		String value = getValue(attribute.getValues(), 0);
		user.setManagerId(value);
		continue;
	    }
	    if (ATTRIBUTE_TELEPHONE_NUMBER.equals(attribute.getName())){
		String value = getValue(attribute.getValues(), 0);
		user.setLocationPhoneNum(value);
		continue;
	    }
	    if (ATTRIBUTE_LOCATION_ID.equals(attribute.getName())){
		String value = getValue(attribute.getValues(), 0);
		if (StringUtils.isNotBlank(value) && value.length() == 6){
		    value = value.substring(value.length() - 4, value.length());
		}
		user.setStoreId(value);
		continue;
	    }
	    if (ATTRIBUTE_LOCATION_TYPE.equals(attribute.getName())){
		String value = getValue(attribute.getValues(), 0);
		user.setLocationType(value);
		continue;
	    }
	}

	user.setApplicationRoles(getRoles(userDetails));

	if (user.hasRole(DrpConstants.DRP_TEAM)){
	    logger.info("Using dummy store for team role");
	    try{
		user.setStoreId(getDummyStoreNum());
	    }catch(ServiceException e){
		logger.error("Failed to set dummy store number", e);
	    }
	}

	return user;
    }

    private String getValue(List<String> values, int index) {
	if (values == null || values.isEmpty()){
	    return null;
	}
	return values.get(index);
    }

    private List<String> getRoles(UserDetails userDetails) {
	Set<String> roles = new HashSet<String>();
	if (userDetails.getRoles() != null && !userDetails.getRoles().isEmpty()){
	    for(String role: userDetails.getRoles()){
		String[] tokens = StringUtils.split(role, ',');
		if (tokens != null){
		    for(IseLdapRole ldapRole: IseLdapRole.values()){
			if (ArrayUtils.contains(tokens, "id=" + ldapRole.getLdapName())
				&& ArrayUtils.contains(tokens, "ou=filteredrole")
				&& ArrayUtils.contains(tokens, "o=bestbuy.com")){
			    roles.add(ldapRole.getIseRole());
			}
		    }
		}
	    }
	}

	for(Attribute attribute: userDetails.getAttributes()){
	    if ("groups".equals(attribute.getName()) && attribute.getValues() != null){
		for(int i = 0; i < attribute.getValues().size(); i++){
		    String value = getValue(attribute.getValues(), i);
		    String[] tokens = StringUtils.split(value, ',');
		    if (tokens != null){
			for(IseLdapRole ldapRole: IseLdapRole.values()){
			    if (ArrayUtils.contains(tokens, "cn=" + ldapRole.getLdapName())
				    && ArrayUtils.contains(tokens, "ou=groups")
				    && ArrayUtils.contains(tokens, "o=bestbuy.com")){
				roles.add(ldapRole.getIseRole());
			    }
			}
		    }
		}
	    }
	}

	if (roles.isEmpty()){
	    roles.add(DrpConstants.DRP_UNAUTHORIZED);
	}
	logger.info("User has roles: " + roles);
	return new ArrayList<String>(roles);
    }

    public enum IseLdapRole {
	ADMININSTRATOR("BBYM_ISE_ADMIN", DrpConstants.DRP_ADMIN),
	MANAGER("bbym_drp_manager", DrpConstants.DRP_MANAGER), LEAD("bbym_drp_lead", DrpConstants.DRP_LEAD), USER(
		"bbym_drp_user", DrpConstants.DRP_USER), TEAM("BBYM_ISE_TEAM", DrpConstants.DRP_TEAM), SHIPPING_USER(
		"bbym_drp_shippingportaluser", DrpConstants.SHP_USER), SHIPPING_MANAGER(
		"bbym_drp_shippingportalmanager", DrpConstants.SHP_MANAGER);

	private String ldapName;
	private String iseRole;

	private IseLdapRole(String ldapName, String iseRole) {
	    this.ldapName = ldapName;
	    this.iseRole = iseRole;
	}

	public String getLdapName() {
	    return ldapName;
	}

	public String getIseRole() {
	    return iseRole;
	}

    }
}
