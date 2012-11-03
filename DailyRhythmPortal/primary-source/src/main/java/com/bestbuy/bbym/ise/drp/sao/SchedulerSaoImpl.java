package com.bestbuy.bbym.ise.drp.sao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.domain.AppointmentCloseStatus;
import com.bestbuy.bbym.ise.drp.domain.AppointmentSlots;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.domain.TimeSlot;
import com.bestbuy.bbym.ise.drp.utils.SecurityUtils;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * SchedulerSaoImpl Responsible for Appointment Scheduler Service
 * 
 * @author a194869
 */
@Service("schedulerSao")
public class SchedulerSaoImpl extends AbstractSao implements SchedulerSao {

    private static Logger logger = LoggerFactory.getLogger(SchedulerSaoImpl.class);
    private static final String SERVICE = "SCHEDULER ";

    @Autowired
    private RestOperations restOperations;

    private final String SCHEDULER_URL = "SCHEDULER_URL";
    private final String SCHEDULER_SECERET_KEY = "SCHEDULER_SECERET_KEY";
    private final String SCHEDULER_API_KEY = "SCHEDULER_API_KEY";
    private final String mediaType = "application/x-www-form-urlencoded";
    private final String SUCCESS = "1";
    private final String SCHEDULER_DATE_FORMAT = "MM/dd/yyyy";
    private final String EXISTING_STATUS = "ExistingStatus";
    private final int DEFAULT_APPOINTMENT_LENGTH = 0;

    /**
     * @param map
     *            - POST request parameters as Query map.
     * @return Request parameters as query String.
     */
    private String getStringBodyFromMap(Map<String, Object> map) {
	StringBuilder stringBuilder = new StringBuilder();
	for(String key: map.keySet()){
	    if (stringBuilder.length() > 0){
		stringBuilder.append("&");
	    }
	    Object value = map.get(key);
	    if (value instanceof String){
		if ("apiKey".equals(key)){
		    stringBuilder.append("API-KEY");

		}else if ("signature".equals(key)){
		    stringBuilder.append("SIGNATURE");
		}else{
		    stringBuilder.append((key != null?key:""));
		}
		stringBuilder.append("=");
		stringBuilder.append(value != null?value:"");
	    }

	}
	return stringBuilder.toString();

    }

    @Override
    public Map<String, String> getDepartments(String storeId) throws ServiceException, IseBusinessException {

	logger.info("getDepartments - Begin. storeId=" + storeId);

	String url = drpPropertiesService.getProperty(SCHEDULER_URL)
		+ "/GetDepartments/API-KEY/{apiKey}/SIGNATURE/{signature}/StoreId/{storeId}";
	logger.info("url:" + url);

	Map<String, Object> uriVariables = getCommonUriVariables();
	uriVariables.put("storeId", storeId);

	try{
	    ResponseEntity<String> responseEntity = restOperations.exchange(url, HttpMethod.GET, getHttpEntity(),
		    String.class, uriVariables);
	    String response = responseEntity.getBody();
	    logger.info("Response XML for getDepartments : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){

		logger.info("GetDepartments Succedded");
		Map<String, String> responseMap = new HashMap<String, String>();
		NodeList departmentNodeList = (NodeList) xPath.evaluate("//Departments/Department", doc,
			XPathConstants.NODESET);
		int length = departmentNodeList.getLength();
		for(int i = 0; i < length; i++){

		    String id = xPath.evaluate("//Department[" + (i + 1) + "]/DepartmentId", doc);
		    String name = xPath.evaluate("//Department[" + (i + 1) + "]/DepartmentName", doc);
		    responseMap.put(id, name);
		}

		logger.info("GetDepartments Succedded");

		return responseMap;
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.error("GetDepartments failed for reason " + errMsg);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errMsg);
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler get Departments service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler get Departments service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get Departments", t);
	    return null;
	}finally{
	    logger.info("GetDepartments - End.");
	}
    }

    @Override
    public Map<String, String> getAppointmentTypes(String storeId, String departmentId) throws ServiceException,
	    IseBusinessException {

	logger.info("getAppointmentTypes - Begin. storeId=" + storeId + " and departmentId " + departmentId);

	String url = drpPropertiesService.getProperty(SCHEDULER_URL)
		+ "/GetTypes/API-KEY/{apiKey}/SIGNATURE/{signature}/StoreId/{storeId}/DepartmentId/{departmentId}";
	logger.info("url:" + url);

	Map<String, Object> uriVariables = getCommonUriVariables();
	uriVariables.put("storeId", storeId);
	uriVariables.put("departmentId", departmentId);

	try{
	    ResponseEntity<String> responseEntity = restOperations.exchange(url, HttpMethod.GET, getHttpEntity(),
		    String.class, uriVariables);
	    String response = responseEntity.getBody();
	    logger.info("Response XML for get Appointment Types : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){

		logger.info("getAppointmentTypes Succedded");
		Map<String, String> responseMap = new HashMap<String, String>();
		NodeList appointmentTypeNodeList = (NodeList) xPath.evaluate("//Types/Type", doc,
			XPathConstants.NODESET);
		int length = appointmentTypeNodeList.getLength();
		for(int i = 0; i < length; i++){

		    String id = xPath.evaluate("//Type[" + (i + 1) + "]/TypeId", doc);
		    String name = xPath.evaluate("//Type[" + (i + 1) + "]/TypeName", doc);
		    responseMap.put(id, name);
		}

		logger.info("getAppointmentTypes Succedded");

		return responseMap;
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.error("getAppointmentTypes failed for reason " + errMsg);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errMsg);
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler get Appointment Types service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler Appointment Types service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - Appointment Types", t);
	    return null;
	}finally{
	    logger.info("getAppointmentTypes - End.");
	}
    }

    @Override
    public Map<String, String> getReasons(String appointmentTypeId) throws ServiceException, IseBusinessException {

	logger.info("getReasons - Begin. appointmentTypeId=" + appointmentTypeId);

	String url = drpPropertiesService.getProperty(SCHEDULER_URL)
		+ "/GetReasons/API-KEY/{apiKey}/SIGNATURE/{signature}/TypeId/{appointmentTypeId}";
	logger.info("url:" + url);

	Map<String, Object> uriVariables = getCommonUriVariables();
	uriVariables.put("appointmentTypeId", appointmentTypeId);

	try{
	    ResponseEntity<String> responseEntity = restOperations.exchange(url, HttpMethod.GET, getHttpEntity(),
		    String.class, uriVariables);
	    String response = responseEntity.getBody();
	    logger.info("Response XML for get Reasons : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){

		logger.info("GetReasons Succedded");
		Map<String, String> responseMap = new HashMap<String, String>();
		NodeList departmentNodeList = (NodeList) xPath
			.evaluate("//Reasons/Reason", doc, XPathConstants.NODESET);
		int length = departmentNodeList.getLength();
		for(int i = 0; i < length; i++){

		    String id = xPath.evaluate("//Reason[" + (i + 1) + "]/ReasonId", doc);
		    String value = xPath.evaluate("//Reason[" + (i + 1) + "]/Reason", doc);
		    responseMap.put(id, value);
		}
		return responseMap;
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.error("GetReasons failed for reason " + errMsg);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errMsg);
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler get Reasons service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler get Reasons service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get Reasons", t);
	    return null;
	}finally{
	    logger.info("GetReasons - End.");
	}
    }

    @Override
    public AppointmentSlots getAppointmentSlots(String userId, String storeId, String departmentId, Date startDate,
	    Date endDate, String reasonTypeId) throws ServiceException, IseBusinessException {

	logger.info("getAppointmentSlots - Begin. userId=" + userId + " storeId=" + storeId + " departmentId="
		+ departmentId + " startDate=" + startDate.toString() + " endDate=" + endDate + " reasonTypeId="
		+ reasonTypeId);

	String url = drpPropertiesService.getProperty(SCHEDULER_URL)
		+ "/GetSlots/API-KEY/{apiKey}/SIGNATURE/{signature}/AID/{userId}/StoreId/{storeId}/DepartmentId/{departmentId}/StartDate/{startDate}/EndDate/{endDate}/TypeId/{reasonTypeId}";
	logger.info("url:" + url);

	Map<String, Object> uriVariables = getCommonUriVariables();
	uriVariables.put("userId", userId);
	uriVariables.put("storeId", storeId);
	uriVariables.put("departmentId", departmentId);
	uriVariables.put("startDate", getDateAsMMddyyyy(startDate));
	uriVariables.put("endDate", getDateAsMMddyyyy(endDate));
	uriVariables.put("reasonTypeId", reasonTypeId);

	try{
	    ResponseEntity<String> responseEntity = restOperations.exchange(url, HttpMethod.GET, getHttpEntity(),
		    String.class, uriVariables);
	    String response = responseEntity.getBody();
	    logger.info("GetSlots response XML : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    AppointmentSlots responseMessage = new AppointmentSlots();
	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){

		logger.info("GetSlots Succeded");
		String appLength = (String) xPath.evaluate("//AppointmentLength/text()", doc);
		responseMessage.setAppointmentLength(appLength != null?Integer.parseInt(appLength)
			:DEFAULT_APPOINTMENT_LENGTH);
		NodeList appointmentSlots = (NodeList) xPath.evaluate("//AppointmentSlots/AppointmentSlot", doc,
			XPathConstants.NODESET);
		Map<Date, List<TimeSlot>> availableSlots = new HashMap<Date, List<TimeSlot>>();
		for(int i = 0; i < appointmentSlots.getLength(); i++){
		    String strDate = xPath
			    .evaluate("//AppointmentSlot[" + (i + 1) + "]/Date", appointmentSlots.item(i));
		    if (strDate != null){
			SimpleDateFormat formatter = new SimpleDateFormat(SCHEDULER_DATE_FORMAT);
			Date date = formatter.parse(strDate);
			NodeList timeSlots = (NodeList) xPath.evaluate("//AppointmentSlot[" + (i + 1) + "]/Slots/Slot",
				doc, XPathConstants.NODESET);
			List<TimeSlot> timeSlotList = new ArrayList<TimeSlot>();
			int timeSlotListLength = timeSlots.getLength();
			for(int j = 0; j < timeSlotListLength; j++){
			    TimeSlot slot = new TimeSlot();
			    slot.setTime(xPath.evaluate("//AppointmentSlot[" + (i + 1) + "]/Slots/Slot[" + (j + 1)
				    + "]/Time", doc));
			    String availability = xPath.evaluate("//AppointmentSlot[" + (i + 1) + "]/Slots/Slot["
				    + (j + 1) + "]/Availability", doc);
			    if (availability != null){
				slot.setAvailability(Integer.parseInt(availability));
			    }
			    timeSlotList.add(slot);
			}
			availableSlots.put(date, timeSlotList);
		    }
		}
		responseMessage.setAvailableSlots(availableSlots);
		logger.info("Get Slots object " + responseMessage.toString());
		return responseMessage;
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.error("GetSlots failed for reason " + errMsg);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errMsg);
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler get Slots service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler get Slots service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get Slots", t);
	    return null;
	}finally{
	    logger.info("GetSlots - End.");
	}
    }

    @Override
    public boolean reserveSlot(String userId, String storeId, String departmentId, String reasonTypeId,
	    Date appointmentDateTime) throws ServiceException, IseBusinessException {

	logger.info("reserveSlot - Begin. userId=" + userId + " storeId=" + storeId + " departmentId=" + departmentId
		+ " appointmentDateTime=" + appointmentDateTime + " reasonTypeId=" + reasonTypeId);

	String url = drpPropertiesService.getProperty(SCHEDULER_URL)
		+ "/ReserveSlot/API-KEY/{apiKey}/SIGNATURE/{signature}/AID/{userId}/StoreId/{storeId}/DepartmentId/{departmentId}/TypeId/{reasonTypeId}/Date/{appointmentDate}/Time/{appointmentTime}";
	logger.info("url:" + url);

	Map<String, Object> uriVariables = getCommonUriVariables();
	uriVariables.put("userId", userId);
	uriVariables.put("storeId", storeId);
	uriVariables.put("departmentId", departmentId);
	uriVariables.put("reasonTypeId", reasonTypeId);
	uriVariables.put("appointmentDate", getDateAsMMddyyyy(appointmentDateTime));
	uriVariables.put("appointmentTime", getTimeAshmma(appointmentDateTime));

	try{
	    ResponseEntity<String> responseEntity = restOperations.exchange(url, HttpMethod.GET, getHttpEntity(),
		    String.class, uriVariables);
	    String response = responseEntity.getBody();
	    logger.info("Response XML for reserveSlot : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){

		logger.info("reserveSlot Succedded");
		return true;
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.error("reserveSlot failed for reason " + errMsg);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errMsg);
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler Reserve Slot service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler Reserve Slot  service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - Reserve Slot ", t);
	    return false;
	}finally{
	    logger.info("Reserve Slot  - End.");
	}
    }

    @Override
    public Map<String, String> getStatuses(String storeId) throws ServiceException, IseBusinessException {

	logger.info("getStatuses - Begin. storeId=" + storeId);

	String url = drpPropertiesService.getProperty(SCHEDULER_URL)
		+ "/GetStatuses/API-KEY/{apiKey}/SIGNATURE/{signature}/StoreId/{storeId}";
	logger.info("url:" + url);

	Map<String, Object> uriVariables = getCommonUriVariables();
	uriVariables.put("storeId", storeId);

	try{
	    ResponseEntity<String> responseEntity = restOperations.exchange(url, HttpMethod.GET, getHttpEntity(),
		    String.class, uriVariables);
	    String response = responseEntity.getBody();
	    logger.info("Response XML for get Statuses : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){
		logger.info("GetStatuses Succedded");
		Map<String, String> responseMap = new HashMap<String, String>();

		NodeList statusesNodeList = (NodeList) xPath.evaluate("//Statuses/Status", doc, XPathConstants.NODESET);
		int length = statusesNodeList.getLength();
		for(int i = 0; i < length; i++){
		    String id = xPath.evaluate("//Statuses/Status[" + (i + 1) + "]/StatusId", doc);
		    String name = xPath.evaluate("//Statuses/Status[" + (i + 1) + "]/StatusName", doc);
		    responseMap.put(id, name);
		}

		return responseMap;
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.error("GetStatuses failed for reason " + errMsg);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errMsg);
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler get statuses service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler get statuses service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - get statuses", t);
	    return null;
	}finally{
	    logger.info("getStatuses - End.");
	}

    }

    @Override
    public Appointment searchAppointmentById(String appointmentId, String storeId) throws ServiceException,
	    IseBusinessException {
	logger.info("SearchAppointments - Begin. appointmentId=" + appointmentId + " storeId=" + storeId);
	Map<String, Object> queryMap = getCommonUriVariables();
	if (storeId != null){
	    queryMap.put("StoreId", storeId);
	}
	if (appointmentId != null){
	    queryMap.put("AppointmentId", appointmentId);
	}
	String request = getStringBodyFromMap(queryMap);
	List<Appointment> appointmentList = searchAppointment(getHttpEntityWithRequest(request));
	return (appointmentList != null && appointmentList.size() > 0)?appointmentList.get(0):null;
    }

    @Override
    public List<Appointment> searchAppointmentByCriteria(SchedulerRequest shedulerReq) throws ServiceException,
	    IseBusinessException {

	logger.info("SearchAppointments - Begin. shedulerReq=" + shedulerReq.toString());
	String request = getRequest(shedulerReq, "SearchAppointments");
	return searchAppointment(getHttpEntityWithRequest(request));
    }

    private List<Appointment> searchAppointment(HttpEntity<String> requestEntity) throws ServiceException,
	    IseBusinessException {
	String url = drpPropertiesService.getProperty(SCHEDULER_URL) + "/SearchAppointments";
	logger.info("url:" + url);
	Map<String, Object> uriVariables = new HashMap<String, Object>();
	List<Appointment> responseList = new ArrayList<Appointment>();

	try{
	    String response = restOperations.postForObject(url, requestEntity, String.class, uriVariables);
	    logger.info("Response XML for SearchAppointments : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){
		logger.info("Search Appointment Succedded");
		NodeList appointments = (NodeList) xPath.evaluate("//Appointments/Appointment", doc,
			XPathConstants.NODESET);
		int appointmentsLength = appointments.getLength();
		for(int i = 0; i < appointmentsLength; i++){
		    Appointment appointment = new Appointment();
		    appointment.setAppointmentId(xPath.evaluate("//Appointment[" + (i + 1) + "]/AppointmentId", doc));
		    appointment.setStoreId(xPath.evaluate("//Appointment[" + (i + 1) + "]/StoreId", doc));
		    appointment.setOrderNo(xPath.evaluate("//Appointment[" + (i + 1) + "]/OrderNo", doc));
		    appointment.setDepartment(xPath.evaluate("//Appointment[" + (i + 1) + "]/DepartmentName", doc));
		    String reasonTypeId = xPath.evaluate("//Appointments/Appointment[" + (i + 1) + "]/TypeId",
			    appointments.item(i));
		    String reasonTypeValue = xPath.evaluate("//Appointments/Appointment[" + (i + 1) + "]/TypeName",
			    appointments.item(i));
		    Map<String, String> resaonType = new HashMap<String, String>();
		    resaonType.put(reasonTypeId, reasonTypeValue);
		    appointment.setReasonType(resaonType);
		    appointment.setReason(xPath.evaluate("//Appointment[" + (i + 1) + "]/Reason", doc));
		    String strAppointmentDate = xPath.evaluate("//Appointment[" + (i + 1) + "]/Date", doc);
		    String strAppointmentTime = xPath.evaluate("//Appointment[" + (i + 1) + "]/Time", doc);

		    SimpleDateFormat dateTimeFormat = new SimpleDateFormat(SCHEDULER_DATE_FORMAT + "hh:mma");
		    Date appointmentTime = dateTimeFormat.parse(strAppointmentDate + strAppointmentTime);
		    appointment.setAppointmentDateTime(appointmentTime);
		    appointment.setFirstName(xPath.evaluate("//Appointment[" + (i + 1) + "]/FirstName", doc));
		    appointment.setLastName(xPath.evaluate("//Appointment[" + (i + 1) + "]/LastName", doc));
		    appointment.setPhoneNo(xPath.evaluate("//Appointment[" + (i + 1) + "]/Phone", doc));
		    appointment.setEmail(xPath.evaluate("//Appointment[" + (i + 1) + "]/Email", doc));
		    Map<AppointmentCloseStatus, List<String>> status = new HashMap<AppointmentCloseStatus, List<String>>();
		    NodeList statusNodeList = (NodeList) xPath.evaluate("//Appointment[" + (i + 1) + "]/Status/*", doc,
			    XPathConstants.NODESET);
		    int alloweStatusLength = statusNodeList.getLength();
		    for(int j = 0; j < alloweStatusLength; j++){
			String statusListKey = xPath.evaluate("name(//Appointment[" + (i + 1) + "]/Status/*[" + (j + 1)
				+ "])", doc);

			if (EXISTING_STATUS.equals(statusListKey)){
			    String existingStatusId = xPath.evaluate("//Appointment[" + (i + 1)
				    + "]/Status/ExistingStatus", doc);
			    if (existingStatusId != null){
				appointment.setCurrentStatus(new Integer(existingStatusId));
			    }
			}else{
			    NodeList individualStatusList = (NodeList) xPath.evaluate("//Appointment[" + (i + 1)
				    + "]/Status/" + statusListKey + "/item", doc, XPathConstants.NODESET);
			    List<String> subStatusList = new ArrayList<String>();
			    for(int k = 0; k < individualStatusList.getLength(); k++){
				subStatusList.add(xPath.evaluate("//Appointment[" + (i + 1) + "]/Status/"
					+ statusListKey + "/item[" + (k + 1) + "]/StatusId", doc));
			    }
			    status.put(AppointmentCloseStatus.fromServiceValue(statusListKey), subStatusList);
			}

		    }
		    appointment.setAllowedStatus(status);
		    responseList.add(appointment);
		}
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", response, XPathConstants.STRING);
		logger.info("Search Appointments failed for reason " + errMsg);
		throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, errMsg);
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Search Appointments service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler Search Appointments service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - Search Appointments ", t);
	}finally{
	    logger.info("Search Appointments  - End.");
	}

	return responseList;
    }

    @Override
    public String modifyAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException {

	logger.info("ModifyAppointment - Begin. shedulerReq=" + shedulerReq.toString());

	String url = drpPropertiesService.getProperty(SCHEDULER_URL) + "/ModifyAppointment";
	logger.info("url:" + url);
	Map<String, Object> uriVariables = new HashMap<String, Object>();
	String request = getRequest(shedulerReq, "ModifyAppointment");

	try{
	    String response = restOperations.postForObject(url, getHttpEntityWithRequest(request), String.class,
		    uriVariables);
	    logger.info("Response XML for ModifyAppointment : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){
		return (String) xPath.evaluate("//AppointmentId/text()", doc);
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.info("ModifyAppointment failed for reason " + errMsg);
		return errMsg;
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler ModifyAppointment service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler ModifyAppointment service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - ModifyAppointment ", t);
	    return t.getMessage();
	}finally{
	    logger.info("ModifyAppointment  - End.");
	}
    }

    @Override
    public String updateStatus(String userId, String appointmentId, String statusId) throws ServiceException,
	    IseBusinessException {

	logger.info("updateStatus - Begin. userId=" + userId + " appointmentId=" + appointmentId + " statusId="
		+ statusId);

	String url = drpPropertiesService.getProperty(SCHEDULER_URL)
		+ "/UpdateStatus/API-KEY/{apiKey}/SIGNATURE/{signature}/AppointmentId/{appointmentId}/AID/{userId}/StatusId/{statusId}";
	logger.info("url:" + url);

	Map<String, Object> uriVariables = getCommonUriVariables();
	uriVariables.put("userId", userId);
	uriVariables.put("appointmentId", appointmentId);
	uriVariables.put("statusId", statusId);

	try{

	    ResponseEntity<String> responseEntity = restOperations.exchange(url, HttpMethod.GET, getHttpEntity(),
		    String.class, uriVariables);
	    String response = responseEntity.getBody();
	    logger.info("Response XML for Update status : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){
		return null;
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.info("UpdateStatus failed for reason " + errMsg);
		return errMsg;
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler Update Status service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler Update Status  service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - Update Status ", t);
	    return t.getMessage();
	}finally{
	    logger.info("Update Status  - End.");
	}
    }

    @Override
    public String createAppointment(SchedulerRequest shedulerReq) throws ServiceException, IseBusinessException {

	logger.info("createAppointment - Begin. shedulerReq=" + shedulerReq.toString());

	String url = drpPropertiesService.getProperty(SCHEDULER_URL) + "/CreateAppointment";
	logger.info("url:" + url);
	Map<String, Object> uriVariables = new HashMap<String, Object>();
	String request = getRequest(shedulerReq, "CreateAppointment");

	try{
	    String response = restOperations.postForObject(url, getHttpEntityWithRequest(request), String.class,
		    uriVariables);
	    logger.info("Response XML for createAppointment : " + response);
	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    Document doc = db.parse(IOUtils.toInputStream(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String errCd = (String) xPath.evaluate("//ErrorCode", doc);
	    if (SUCCESS.equals(errCd)){
		return (String) xPath.evaluate("//AppointmentId/text()", doc);
	    }else{
		String errMsg = (String) xPath.evaluate("//ErrorMessage", doc);
		logger.info("Create Appointment failed for reason " + errMsg);
		return errMsg;
	    }
	}catch(RestClientException e){
	    logger.error("Error calling Scheduler Create Appointment service.", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR,
		    "Error calling Scheduler Create Appointment service.");
	}catch(Throwable t){
	    handleException(SERVICE + " - Create Appointment ", t);
	    return t.getMessage();
	}finally{
	    logger.info("Create Appointment  - End.");
	}

    }

    /**
     * @param shedulerReq
     *            - Request object having the values
     * @param serviceMethod
     *            - Service method's name
     * @return - Map of request parameters.
     * @throws ServiceException
     */
    private String getRequest(SchedulerRequest shedulerReq, String serviceMethod) throws ServiceException {
	logger.info("Creating Request Dom object");
	boolean modifyAppointment = "ModifyAppointment".equals(serviceMethod);
	boolean createAppointment = "CreateAppointment".equals(serviceMethod);
	boolean searchAppointment = "SearchAppointments".equals(serviceMethod);
	Map<String, Object> queryMap = getCommonUriVariables();

	if (shedulerReq.getUser() != null){
	    if ((modifyAppointment || createAppointment) && shedulerReq.getUser().getUserId() != null){
		queryMap.put("AID", shedulerReq.getUser().getUserId());
	    }
	    if (shedulerReq.getUser().getStoreId() != null){
		queryMap.put("StoreId", shedulerReq.getUser().getStoreId());
	    }
	}
	if ((searchAppointment || modifyAppointment) && shedulerReq.getAppointmentId() != null){
	    queryMap.put("AppointmentId", shedulerReq.getAppointmentId());
	}
	if ((searchAppointment || createAppointment) && shedulerReq.getDepartment() != null){
	    queryMap.put("DepartmentId", getKey(shedulerReq.getDepartment()));
	}
	if ((searchAppointment || createAppointment) && shedulerReq.getAppointmentType() != null){
	    queryMap.put("TypeId", getKey(shedulerReq.getAppointmentType()));
	}
	if (shedulerReq.getAppointmentTime() != null){
	    if ((modifyAppointment || createAppointment)){
		queryMap.put("Date", getDateAsMMddyyyy(getDateFromSlot(shedulerReq.getAppointmentTime())));
	    }
	    queryMap.put("Time", getTimeFromSlot(shedulerReq.getAppointmentTime()));
	}
	if (shedulerReq.getCustomer() != null){
	    if ((searchAppointment || createAppointment) && shedulerReq.getCustomer().getFirstName() != null){
		queryMap.put("FirstName", shedulerReq.getCustomer().getFirstName());
	    }
	    if ((searchAppointment || createAppointment) && shedulerReq.getCustomer().getLastName() != null){
		queryMap.put("LastName", shedulerReq.getCustomer().getLastName());
	    }
	    if (createAppointment && shedulerReq.getCustomer().getContactPhone() != null){
		queryMap.put("Phone", shedulerReq.getCustomer().getContactPhone());
	    }
	    if (createAppointment && shedulerReq.getCustomer().getEmail() != null){
		queryMap.put("Email", shedulerReq.getCustomer().getEmail());
		if (shedulerReq.isSendEmail()){
		    queryMap.put("SendEmail", "Yes");
		}else{
		    queryMap.put("SendEmail", "No");
		}
	    }
	}
	if (createAppointment && shedulerReq.getReason() != null){
	    queryMap.put("ReasonId", getKey(shedulerReq.getReason()));
	}
	if ((searchAppointment || createAppointment) && shedulerReq.getOrderNo() != null){
	    queryMap.put("OrderNo", shedulerReq.getOrderNo());
	}
	if (searchAppointment && shedulerReq.getAppointmentStatusId() != null){
	    queryMap.put("StatusId", shedulerReq.getAppointmentStatusId());
	}
	if (searchAppointment && shedulerReq.getStartDate() != null){
	    queryMap.put("StartDate", getDateAsMMddyyyy((shedulerReq.getStartDate())));
	}
	if (searchAppointment && shedulerReq.getEndDate() != null){
	    queryMap.put("EndDate", getDateAsMMddyyyy((shedulerReq.getEndDate())));
	}
	logger.info("Returning document object : " + queryMap.toString());
	return getStringBodyFromMap(queryMap);

    }

    /**
     * @param startDate
     *            - Date object
     * @return - Date format as accepted by Scheduler service
     */
    private String getDateAsMMddyyyy(Date date) {
	SimpleDateFormat formatter = new SimpleDateFormat("MMddyyyy");
	return formatter.format(date);
    }

    private String getTimeAshmma(Date date) {
	SimpleDateFormat formatter = new SimpleDateFormat("h:mma");
	return formatter.format(date);
    }

    /**
     * Get ID from a HashMap having only one entry.
     * 
     * @param hMap
     * @return
     */
    private String getKey(Map<String, String> hMap) {
	String returnKey = "";
	Set st = hMap.keySet();
	Iterator itr = st.iterator();
	while(itr.hasNext())
	    returnKey = (String) itr.next();
	return returnKey;
    }

    /**
     * Get Appointment Date from Map
     * 
     * @param appointmentTime
     * @return
     */
    private Date getDateFromSlot(Map<Date, TimeSlot> appointmentTime) {
	Date returnDate = null;
	Set<Date> st = appointmentTime.keySet();
	Iterator<Date> itr = st.iterator();
	while(itr.hasNext())
	    returnDate = (Date) itr.next();
	return returnDate;
    }

    /**
     * Get Appointment Time from Map
     * 
     * @param appointmentTime
     * @return
     */
    private String getTimeFromSlot(Map<Date, TimeSlot> appointmentTime) {

	Collection<TimeSlot> c = appointmentTime.values();
	Iterator<TimeSlot> itr = c.iterator();
	TimeSlot slot = null;
	while(itr.hasNext()){
	    slot = itr.next();
	}
	return slot != null?slot.getTime():null;
    }

    /**
     * Gets URI variables common to all method calls.
     */
    private Map<String, Object> getCommonUriVariables() throws ServiceException {

	String apiKey = drpPropertiesService.getProperty(SCHEDULER_API_KEY);
	String secretKey = drpPropertiesService.getProperty(SCHEDULER_SECERET_KEY);
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);

	Map<String, Object> uriVariables = new HashMap<String, Object>();
	uriVariables.put("apiKey", apiKey);
	uriVariables.put("signature", signature);

	return uriVariables;
    }

    private HttpEntity getHttpEntity() {

	HttpHeaders httpHeaders = new HttpHeaders();
	List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
	acceptableMediaTypes.add(MediaType.APPLICATION_XML);
	httpHeaders.setAccept(acceptableMediaTypes);
	httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	httpHeaders.add("Content-Type", mediaType);
	HttpEntity httpEntity = new HttpEntity(httpHeaders);
	return httpEntity;
    }

    private HttpEntity<String> getHttpEntityWithRequest(String request) {
	return new HttpEntity<String>(request, getHttpEntity().getHeaders());
    }

}
