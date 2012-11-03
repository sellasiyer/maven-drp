/**
 * Best Buy Inc (c) 2012
 */
package com.bestbuy.bbym.ise.drp.sao;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.w3c.dom.Document;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * @author Yernar Khuangan (a1003810)
 * @version 1.0
 * @since iteration 27
 */
@Service("employeeLookupSao")
public class EmployeeLookupSaoImpl extends AbstractSao implements EmployeeLookupSao {

    private static final String XPATH_EMPLOYEE_LAST_NAME = "/EmployeeLookupResponse/EmployeeResponseDetails/Location/Department/EmployeeInformation/LastName";
    private static final String XPATH_EMPLOYEE_FIRST_NAME = "/EmployeeLookupResponse/EmployeeResponseDetails/Location/Department/EmployeeInformation/FirstName";
    private static final String XPATH_STATUS_CODE = "/EmployeeLookupResponse/Status/Code";
    private static Logger logger = LoggerFactory.getLogger(EmployeeLookupSaoImpl.class);
    private final String TSH_EMPLOYEE_LOOKUP_SERVICE_URL = "TSH_EMPLOYEE_LOOKUP_SERVICE_URL";
    private final String REQUEST_BY_USER_ID = "<v1:EmployeeLookupRequest v11:languageCode=\"?\" "
	    + "xmlns:v1=\"http://www.tsh.bestbuy.com/parties/employee/lookup/service/v1\" "
	    + "xmlns:v11=\"http://www.tsh.bestbuy.com/common/v1\" "
	    + "xmlns:v12=\"http://www.tsh.bestbuy.com/parties/employee/lookup/components/v1\" "
	    + "xmlns:v13=\"http://www.tsh.bestbuy.com/common/metadata/fields/v1\" "
	    + "xmlns:v14=\"http://www.tsh.bestbuy.com/common/communication/v1\" "
	    + "xmlns:v15=\"http://www.tsh.bestbuy.com/parties/employee/lookup/fields/v1\">"
	    + "<v12:EmployeeRequestDetails><v12:EmployeeInformation><v15:AID>%s</v15:AID>"
	    + "</v12:EmployeeInformation></v12:EmployeeRequestDetails></v1:EmployeeLookupRequest>";

    @Autowired
    private RestOperations restOperations;

    @Override
    public User getEmployeeDetails(String userId) throws ServiceException, IseBusinessException {

	logger.info("getEmployeeDetails - Begin. userId:" + userId);

	if (StringUtils.isEmpty(userId)){
	    return null;
	}

	String url = drpPropertiesService.getProperty(TSH_EMPLOYEE_LOOKUP_SERVICE_URL);

	logger.info("url:" + url);

	try{
	    String params = String.format(REQUEST_BY_USER_ID, userId);
	    String response = restOperations.postForObject(url, params, String.class);
	    logger.debug("response:" + response);

	    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	    dbf.setNamespaceAware(false);
	    DocumentBuilder db = dbf.newDocumentBuilder();
	    Document doc = db.parse(new java.io.ByteArrayInputStream(response.getBytes()));
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    String returnCode = xPath.evaluate(XPATH_STATUS_CODE, doc);
	    String firstName = xPath.evaluate(XPATH_EMPLOYEE_FIRST_NAME, doc);
	    String lastName = xPath.evaluate(XPATH_EMPLOYEE_LAST_NAME, doc);

	    if (!"TSH-PXY-000000".equalsIgnoreCase(returnCode)){
		return null;
	    }

	    if (StringUtils.isEmpty(firstName) && StringUtils.isEmpty(lastName)){
		return null;
	    }
	    User user = new User();
	    user.setFirstName(firstName);
	    user.setLastName(lastName);
	    return user;
	}catch(RestClientException e){
	    logger.error("Error calling TSH Employee Lookup Service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling TSH Employee Lookup Service.");
	}catch(Throwable t){
	    handleException("TSH Employee Lookup Service - get employee details", t);
	    return null;
	}finally{
	    logger.info("getEmployeeDetails - End.");
	}
    }

}
