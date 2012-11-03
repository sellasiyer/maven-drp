package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestOperations;

import com.bestbuy.bbym.ise.domain.Customer;
import com.bestbuy.bbym.ise.drp.dao.TestUtil;
import com.bestbuy.bbym.ise.drp.domain.Appointment;
import com.bestbuy.bbym.ise.drp.domain.AppointmentCloseStatus;
import com.bestbuy.bbym.ise.drp.domain.AppointmentSlots;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.SchedulerRequest;
import com.bestbuy.bbym.ise.drp.domain.TimeSlot;
import com.bestbuy.bbym.ise.drp.utils.SecurityUtils;

/**
 * JUnit test for {@link SchedulerSaoImpl}.
 */
public class SchedulerSaoImplTest extends BaseSaoTest {

    private SchedulerSaoImpl schedulerSaoImpl;
    private RestOperations mockRestOperations;

    @Override
    public void setUp() {
	super.setUp();

	mockRestOperations = EasyMock.createMock(RestOperations.class);
	schedulerSaoImpl = new SchedulerSaoImpl();
	ReflectionTestUtils.setField(schedulerSaoImpl, "restOperations", mockRestOperations);

	schedulerSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH for BBYOpen service?
    }

    /**
     * Test for {@link SchedulerSaoImpl#getStatuses(String)}.
     */
    @Test
    public void testGetStatuses() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_GetStatuses.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getStatusesSuffix = "/GetStatuses/API-KEY/{apiKey}/SIGNATURE/{signature}/StoreId/{storeId}";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";

	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.exchange(capture(captureUrl), capture(captureMethod), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(responseEntity);
	EasyMock.replay(mockRestOperations);

	final String storeId = "someStoreId";

	Map<String, String> statusesResponse = schedulerSaoImpl.getStatuses(storeId);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getStatusesSuffix, captureUrl.getValue());
	assertEquals("Incorrect method call passed in service call", HttpMethod.GET, captureMethod.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect uriVariable (signature) passed in service call", signature, captureUriVariables
		.getValue().get("signature"));
	assertEquals("Incorrect uriVariable (storeId) passed in service call", storeId, captureUriVariables.getValue()
		.get("storeId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", statusesResponse);
	assertEquals("Incorrect number of statuses returned from service call", 4, statusesResponse.size());
	assertEquals("Incorrect status (1) returned from service call", "Cancelled", statusesResponse.get("1"));
	assertEquals("Incorrect status (2) returned from service call", "Complete w/out Purchase", statusesResponse
		.get("2"));
	assertEquals("Incorrect status returned from service call", "Complete w/ Purchase", statusesResponse.get("3"));
	assertEquals("Incorrect status returned from service call", "No Show", statusesResponse.get("4"));
    }

    /**
     * Test for {@link SchedulerSaoImpl#getDepartments(String)}.
     */
    @Test
    public void testGetDepartments() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_GetDepartments.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getDepartmentsSuffix = "/GetDepartments/API-KEY/{apiKey}/SIGNATURE/{signature}/StoreId/{storeId}";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";

	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.exchange(capture(captureUrl), capture(captureMethod), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(responseEntity);
	EasyMock.replay(mockRestOperations);

	final String storeId = "someStoreId";

	Map<String, String> departmentsResponse = schedulerSaoImpl.getDepartments(storeId);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getDepartmentsSuffix, captureUrl.getValue());
	assertEquals("Incorrect method call passed in service call", HttpMethod.GET, captureMethod.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect uriVariable (signature) passed in service call", signature, captureUriVariables
		.getValue().get("signature"));
	assertEquals("Incorrect uriVariable (storeId) passed in service call", storeId, captureUriVariables.getValue()
		.get("storeId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", departmentsResponse);
	assertEquals("Incorrect number of departments returned from service call", 3, departmentsResponse.size());
	assertEquals("Incorrect department (1) returned from service call", "Mobile", departmentsResponse.get("1"));
	assertEquals("Incorrect department (2) returned from service call", "Electronics", departmentsResponse.get("2"));
	assertEquals("Incorrect department (3) returned from service call", "Computing", departmentsResponse.get("3"));
    }

    /**
     * Test for {@link SchedulerSaoImpl#getAppointmentTypes(String,String)}.
     */
    @Test
    public void testGetAppointmentTypes() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_GetReasonTypes.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/GetTypes/API-KEY/{apiKey}/SIGNATURE/{signature}/StoreId/{storeId}/DepartmentId/{departmentId}";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";

	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.exchange(capture(captureUrl), capture(captureMethod), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(responseEntity);
	EasyMock.replay(mockRestOperations);

	final String storeId = "someStoreId";
	final String departmentId = "someDepartmentId";

	Map<String, String> typesResponse = schedulerSaoImpl.getAppointmentTypes(storeId, departmentId);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect method call passed in service call", HttpMethod.GET, captureMethod.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect uriVariable (signature) passed in service call", signature, captureUriVariables
		.getValue().get("signature"));
	assertEquals("Incorrect uriVariable (storeId) passed in service call", storeId, captureUriVariables.getValue()
		.get("storeId"));
	assertEquals("Incorrect uriVariable (departmentId) passed in service call", departmentId, captureUriVariables
		.getValue().get("departmentId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", typesResponse);
	assertEquals("Incorrect number of Types returned from service call", 1, typesResponse.size());
	assertEquals("Incorrect Appoinment Type (2) returned from service call", "Store-Pickup", typesResponse.get("2"));
    }

    /**
     * Test for {@link SchedulerSaoImpl#getReasons(String)}.
     */
    @Test
    public void testGetReasons() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_GetReasons.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/GetReasons/API-KEY/{apiKey}/SIGNATURE/{signature}/TypeId/{appointmentTypeId}";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";

	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.exchange(capture(captureUrl), capture(captureMethod), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(responseEntity);
	EasyMock.replay(mockRestOperations);

	final String appointmentTypeId = "someAppointmentTypeId";

	Map<String, String> reasonsResponse = schedulerSaoImpl.getReasons(appointmentTypeId);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect method call passed in service call", HttpMethod.GET, captureMethod.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect uriVariable (signature) passed in service call", signature, captureUriVariables
		.getValue().get("signature"));
	assertEquals("Incorrect uriVariable (appointmentTypeId) passed in service call", appointmentTypeId,
		captureUriVariables.getValue().get("appointmentTypeId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", reasonsResponse);
	assertEquals("Incorrect number of Types returned from service call", 2, reasonsResponse.size());
	assertEquals("Incorrect Appoinment Type (2) returned from service call", "Pickup Store Pickup", reasonsResponse
		.get("2"));
	assertEquals("Incorrect Appoinment Type (7) returned from service call", "Prepare walk out working for phone",
		reasonsResponse.get("7"));
    }

    /**
     * Test for {@link SchedulerSaoImpl#getAppointmentSlots(String)}.
     */
    @Test
    public void testGetAppointmentSlots() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_GetAppointmentSlots.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/GetSlots/API-KEY/{apiKey}/SIGNATURE/{signature}/AID/{userId}/StoreId/{storeId}/DepartmentId/{departmentId}/StartDate/{startDate}/EndDate/{endDate}/TypeId/{reasonTypeId}";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";

	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.exchange(capture(captureUrl), capture(captureMethod), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(responseEntity);
	EasyMock.replay(mockRestOperations);

	final String userId = "someUserId";
	final String storeId = "someStoreId";
	final String departmentId = "someDepartmentId";
	final Date startDate = new Date();
	final Date endDate = new Date();
	final String reasonTypeId = "someReasonTypeId";

	AppointmentSlots appointmentSlotsResponse = schedulerSaoImpl.getAppointmentSlots(userId, storeId, departmentId,
		startDate, endDate, reasonTypeId);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect method call passed in service call", HttpMethod.GET, captureMethod.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect uriVariable (signature) passed in service call", signature, captureUriVariables
		.getValue().get("signature"));
	assertEquals("Incorrect uriVariable (storeId) passed in service call", storeId, captureUriVariables.getValue()
		.get("storeId"));
	assertEquals("Incorrect uriVariable (userId) passed in service call", userId, captureUriVariables.getValue()
		.get("userId"));
	assertEquals("Incorrect uriVariable (departmentId) passed in service call", departmentId, captureUriVariables
		.getValue().get("departmentId"));
	assertEquals("Incorrect uriVariable (startDate) passed in service call", getDateAsMMddyyyy(startDate),
		captureUriVariables.getValue().get("startDate"));
	assertEquals("Incorrect uriVariable (endDate) passed in service call", getDateAsMMddyyyy(endDate),
		captureUriVariables.getValue().get("endDate"));
	assertEquals("Incorrect uriVariable (reasonTypeId) passed in service call", reasonTypeId, captureUriVariables
		.getValue().get("reasonTypeId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", appointmentSlotsResponse);
	assertEquals("Incorrect Appointment Length returned from service call", 60, appointmentSlotsResponse
		.getAppointmentLength());
	assertEquals("Incorrect No. of Appointment slots returned from service call", 2, appointmentSlotsResponse
		.getAvailableSlots().size());
	assertEquals("Incorrect Appoinment Time returned from service call", "12:00am", appointmentSlotsResponse
		.getAvailableSlots().get(new Date("10/03/2012")).get(0).getTime());
	assertEquals("Incorrect Appointment availability flag returned from service call", 0, appointmentSlotsResponse
		.getAvailableSlots().get(new Date("10/03/2012")).get(0).getAvailability());
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
     * Test for
     * {@link SchedulerSaoImpl#reserveSlot(String , String , String , String , Date)
     * .
     */
    @Test
    public void testReserveSlot() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_ReserveSlots.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/ReserveSlot/API-KEY/{apiKey}/SIGNATURE/{signature}/AID/{userId}/StoreId/{storeId}/DepartmentId/{departmentId}/TypeId/{reasonTypeId}/Date/{appointmentDate}/Time/{appointmentTime}";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";

	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.exchange(capture(captureUrl), capture(captureMethod), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(responseEntity);
	EasyMock.replay(mockRestOperations);

	final String userId = "someUserId";
	final String storeId = "someStoreId";
	final String departmentId = "someDepartmentId";
	final Date appointmentDateTime = new Date();
	final String reasonTypeId = "someReasonTypeId";

	boolean reserveSlotResponse = schedulerSaoImpl.reserveSlot(userId, storeId, departmentId, reasonTypeId,
		appointmentDateTime);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect method call passed in service call", HttpMethod.GET, captureMethod.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect uriVariable (signature) passed in service call", signature, captureUriVariables
		.getValue().get("signature"));
	assertEquals("Incorrect uriVariable (storeId) passed in service call", storeId, captureUriVariables.getValue()
		.get("storeId"));
	assertEquals("Incorrect uriVariable (userId) passed in service call", userId, captureUriVariables.getValue()
		.get("userId"));
	assertEquals("Incorrect uriVariable (departmentId) passed in service call", departmentId, captureUriVariables
		.getValue().get("departmentId"));
	assertEquals("Incorrect uriVariable (appointmentDate) passed in service call",
		getDateAsMMddyyyy(appointmentDateTime), captureUriVariables.getValue().get("appointmentDate"));
	assertEquals("Incorrect uriVariable (appointmentTime) passed in service call",
		getTimeAshmma(appointmentDateTime), captureUriVariables.getValue().get("appointmentTime"));
	assertEquals("Incorrect uriVariable (reasonTypeId) passed in service call", reasonTypeId, captureUriVariables
		.getValue().get("reasonTypeId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", reserveSlotResponse);
	assertEquals("Incorrect success flag returned from service call", true, reserveSlotResponse);
    }

    /**
     * Test for {@link SchedulerSaoImpl#updateStatus(String , String , String )
     */
    @Test
    public void testUpdateStatus() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_UpdateAppointment.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/UpdateStatus/API-KEY/{apiKey}/SIGNATURE/{signature}/AppointmentId/{appointmentId}/AID/{userId}/StatusId/{statusId}";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";

	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.exchange(capture(captureUrl), capture(captureMethod), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(responseEntity);
	EasyMock.replay(mockRestOperations);

	final String userId = "someUserId";
	final String statusId = "someStatusId";
	final String appointmentId = "someAppointmentId";

	String updateStatusResponse = schedulerSaoImpl.updateStatus(userId, appointmentId, statusId);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect method call passed in service call", HttpMethod.GET, captureMethod.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect uriVariable (signature) passed in service call", signature, captureUriVariables
		.getValue().get("signature"));
	assertEquals("Incorrect uriVariable (userId) passed in service call", userId, captureUriVariables.getValue()
		.get("userId"));
	assertEquals("Incorrect uriVariable (statusId) passed in service call", statusId, captureUriVariables
		.getValue().get("statusId"));
	assertEquals("Incorrect uriVariable (appointmentId) passed in service call", appointmentId, captureUriVariables
		.getValue().get("appointmentId"));

	// Check that correct values were returned from SAO
	assertEquals("Incorrect success String returned from service call", null, updateStatusResponse);
    }

    /**
     * Test for {@link SchedulerSaoImpl#createAppointment(SchedulerRequest )
     */
    @Test
    public void testCreateAppointment() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_CreateAppointment.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/CreateAppointment";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";
	SchedulerRequest mockRequest = createSchedulerRequest();
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.postForObject(capture(captureUrl), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(response);
	EasyMock.replay(mockRestOperations);

	String createAppointmentResponse = schedulerSaoImpl.createAppointment(mockRequest);
	Map<String, String> requestMap = getRequestMap(captureRequestEntity.getValue().getBody().toString());

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Illegal uriVariables passed in service call", 0, captureUriVariables.getValue().entrySet().size());
	assertEquals("Incorrect apikey passed in service call", apiKey, requestMap.get("API-KEY"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect signature passed in service call", signature, requestMap.get("SIGNATURE"));
	assertEquals("Incorrect Phone passed in service call", mockRequest.getCustomer().getContactPhone(), requestMap
		.get("Phone"));
	assertEquals("Incorrect LastName passed in service call", mockRequest.getCustomer().getLastName(), requestMap
		.get("LastName"));
	assertEquals("Incorrect SendEmail passed in service call", mockRequest.isSendEmail()?"Yes":"No", requestMap
		.get("SendEmail"));
	assertEquals("Incorrect ReasonId passed in service call", mockRequest.getReason().keySet().iterator().next(),
		requestMap.get("ReasonId"));
	assertEquals("Incorrect Time passed in service call", mockRequest.getAppointmentTime().entrySet().iterator()
		.next().getValue().getTime(), requestMap.get("Time"));
	assertEquals("Incorrect TypeId passed in service call", mockRequest.getAppointmentType().keySet().iterator()
		.next(), requestMap.get("TypeId"));
	assertEquals("Incorrect Email passed in service call", mockRequest.getCustomer().getEmail(), requestMap
		.get("Email"));
	assertEquals("Incorrect OrderNo passed in service call", mockRequest.getOrderNo(), requestMap.get("OrderNo"));
	assertEquals("Incorrect StoreId passed in service call", mockRequest.getUser().getStoreId(), requestMap
		.get("StoreId"));
	assertEquals("Incorrect Date passed in service call", getDateAsMMddyyyy(mockRequest.getAppointmentTime()
		.keySet().iterator().next()), requestMap.get("Date"));
	assertEquals("Incorrect AID passed in service call", mockRequest.getUser().getUserId(), requestMap.get("AID"));
	assertEquals("Incorrect FirstName passed in service call", mockRequest.getCustomer().getFirstName(), requestMap
		.get("FirstName"));
	assertEquals("Incorrect DepartmentId passed in service call", mockRequest.getDepartment().keySet().iterator()
		.next(), requestMap.get("DepartmentId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", createAppointmentResponse);
	assertEquals("Incorrect Appointment ID returned from service call", "ISPU-90", createAppointmentResponse);
    }

    /**
     * Test for {@link SchedulerSaoImpl#modifyAppointment(SchedulerRequest )
     */
    @Test
    public void testModifyAppointment() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_ModifyAppointmentSlot.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/ModifyAppointment";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";
	SchedulerRequest mockRequest = createSchedulerRequest();
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.postForObject(capture(captureUrl), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(response);
	EasyMock.replay(mockRestOperations);

	String modifyAppointmentResponse = schedulerSaoImpl.modifyAppointment(mockRequest);
	Map<String, String> requestMap = getRequestMap(captureRequestEntity.getValue().getBody().toString());

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Illegal uriVariables passed in service call", 0, captureUriVariables.getValue().entrySet().size());
	assertEquals("Incorrect apikey passed in service call", apiKey, requestMap.get("API-KEY"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect signature passed in service call", signature, requestMap.get("SIGNATURE"));
	assertEquals("Incorrect Time passed in service call", mockRequest.getAppointmentTime().entrySet().iterator()
		.next().getValue().getTime(), requestMap.get("Time"));
	assertEquals("Incorrect StoreId passed in service call", mockRequest.getUser().getStoreId(), requestMap
		.get("StoreId"));
	assertEquals("Incorrect Date passed in service call", getDateAsMMddyyyy(mockRequest.getAppointmentTime()
		.keySet().iterator().next()), requestMap.get("Date"));
	assertEquals("Incorrect AID passed in service call", mockRequest.getUser().getUserId(), requestMap.get("AID"));
	assertEquals("Incorrect Appoinment Id passed in service call", mockRequest.getAppointmentId(), requestMap
		.get("AppointmentId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", modifyAppointmentResponse);
	assertEquals("Incorrect Appointment ID returned from service call", "ISPU-69", modifyAppointmentResponse);
    }

    /**
     * Test for
     * {@link SchedulerSaoImpl#searchAppointmentByCriteria(SchedulerRequest )
     */
    @Test
    public void testSearchAppointmentByCriteria() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_SearchAppointments.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/SearchAppointments";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";
	SchedulerRequest mockRequest = createSchedulerRequest();
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.postForObject(capture(captureUrl), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(response);
	EasyMock.replay(mockRestOperations);

	List<Appointment> searchAppointmentResponse = schedulerSaoImpl.searchAppointmentByCriteria(mockRequest);
	Map<String, String> requestMap = getRequestMap(captureRequestEntity.getValue().getBody().toString());

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Illegal uriVariables passed in service call", 0, captureUriVariables.getValue().entrySet().size());
	assertEquals("Incorrect apikey passed in service call", apiKey, requestMap.get("API-KEY"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect signature passed in service call", signature, requestMap.get("SIGNATURE"));
	assertEquals("Incorrect LastName passed in service call", mockRequest.getCustomer().getLastName(), requestMap
		.get("LastName"));
	assertEquals("Incorrect TypeId passed in service call", mockRequest.getAppointmentType().keySet().iterator()
		.next(), requestMap.get("TypeId"));
	assertEquals("Incorrect StoreId passed in service call", mockRequest.getUser().getStoreId(), requestMap
		.get("StoreId"));
	assertEquals("Incorrect FirstName passed in service call", mockRequest.getCustomer().getFirstName(), requestMap
		.get("FirstName"));
	assertEquals("Incorrect DepartmentId passed in service call", mockRequest.getDepartment().keySet().iterator()
		.next(), requestMap.get("DepartmentId"));
	assertEquals("Incorrect StartDate passed in service call", getDateAsMMddyyyy(mockRequest.getStartDate()),
		requestMap.get("StartDate"));
	assertEquals("Incorrect EndDate passed in service call", getDateAsMMddyyyy(mockRequest.getEndDate()),
		requestMap.get("EndDate"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", searchAppointmentResponse);
	assertEquals("Incorrect No. of Appointments returned from service call", 1, searchAppointmentResponse.size());
	assertEquals("Incorrect Appointment ID returned from service call", "ISPU-13", searchAppointmentResponse.get(0)
		.getAppointmentId());
	assertEquals("Incorrect Store ID returned from service call", "281", searchAppointmentResponse.get(0)
		.getStoreId());
	assertEquals("Incorrect Order No. returned from service call", "124124", searchAppointmentResponse.get(0)
		.getOrderNo());
	assertEquals("Incorrect Department returned from service call", "", searchAppointmentResponse.get(0)
		.getDepartment());
	assertEquals("Incorrect Appointment ID returned from service call", "Store-Pickup", searchAppointmentResponse
		.get(0).getReasonType().entrySet().iterator().next().getValue());
	assertEquals("Incorrect Type ID returned from service call", "2", searchAppointmentResponse.get(0)
		.getReasonType().entrySet().iterator().next().getKey());
	assertEquals("Incorrect Appointment Date returned from service call", "04192012",
		getDateAsMMddyyyy(searchAppointmentResponse.get(0).getAppointmentDateTime()));
	SimpleDateFormat format = new SimpleDateFormat("h:mma");
	Date timeDate = format.parse("2:00pm");
	assertEquals("Incorrect Appointment Time returned from service call", getTimeAshmma(timeDate),
		getTimeAshmma(searchAppointmentResponse.get(0).getAppointmentDateTime()));
	assertEquals("Incorrect First Name returned from service call", "Bryce", searchAppointmentResponse.get(0)
		.getFirstName());
	assertEquals("Incorrect Last Name returned from service call", "Levin", searchAppointmentResponse.get(0)
		.getLastName());
	assertEquals("Incorrect Phone no returned from service call", "(234) 234-2342", searchAppointmentResponse
		.get(0).getPhoneNo());
	assertEquals("Incorrect Appointment ID returned from service call", "brycelevin@gmail.com",
		searchAppointmentResponse.get(0).getEmail());
	assertEquals("Incorrect Appointment ID returned from service call", "Pickup Store Pickup",
		searchAppointmentResponse.get(0).getReason());
	assertEquals("Incorrect current status returned from service call", 0, searchAppointmentResponse.get(0)
		.getCurrentStatus().intValue());
	assertEquals("Incorrect No. of allowed status returned from service call", 3, searchAppointmentResponse.get(0)
		.getAllowedStatus().size());
	assertEquals("Incorrect No. of Cancel status returned from service call", 1, searchAppointmentResponse.get(0)
		.getAllowedStatus().get(AppointmentCloseStatus.CANCEL).size());
	assertEquals("Incorrect Cancel status (1) returned from service call", "1", searchAppointmentResponse.get(0)
		.getAllowedStatus().get(AppointmentCloseStatus.CANCEL).get(0));
	assertEquals("Incorrect No. of Complete status returned from service call", 2, searchAppointmentResponse.get(0)
		.getAllowedStatus().get(AppointmentCloseStatus.COMPLETE).size());
	assertEquals("Incorrect Complete status (2) returned from service call", "3", searchAppointmentResponse.get(0)
		.getAllowedStatus().get(AppointmentCloseStatus.COMPLETE).get(0));
	assertEquals("Incorrect Complete status (3) returned from service call", "2", searchAppointmentResponse.get(0)
		.getAllowedStatus().get(AppointmentCloseStatus.COMPLETE).get(1));
	assertEquals("Incorrect No. of No show status returned from service call", 1, searchAppointmentResponse.get(0)
		.getAllowedStatus().get(AppointmentCloseStatus.NO_SHOW).size());
	assertEquals("Incorrect No Show status (4) returned from service call", "4", searchAppointmentResponse.get(0)
		.getAllowedStatus().get(AppointmentCloseStatus.NO_SHOW).get(0));
    }

    /**
     * Test for {@link SchedulerSaoImpl#searchAppointmentById(String,String )
     */
    @Test
    public void testSearchAppointmentById() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<HttpMethod> captureMethod = new Capture<HttpMethod>();
	Capture<HttpEntity> captureRequestEntity = new Capture<HttpEntity>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "Scheduler_SearchAppointments.xml");
	ResponseEntity<String> responseEntity = new ResponseEntity<String>(response, HttpStatus.OK);

	final String url = "someUrl";
	final String getTypesSuffix = "/SearchAppointments";
	final String apiKey = "someApiKey";
	final String secretKey = "someSecretKey";
	SchedulerRequest mockRequest = createSchedulerRequest();
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_API_KEY")).andReturn(apiKey);
	EasyMock.expect(drpPropertyService.getProperty("SCHEDULER_SECERET_KEY")).andReturn(secretKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.postForObject(capture(captureUrl), capture(captureRequestEntity),
			capture(captureResponseType), capture(captureUriVariables))).andReturn(response);
	EasyMock.replay(mockRestOperations);
	final String appointmentId = "someAppointmentId";
	final String storeId = "someStoreId";
	Appointment searchAppointmentResponse = schedulerSaoImpl.searchAppointmentById(appointmentId, storeId);
	Map<String, String> requestMap = getRequestMap(captureRequestEntity.getValue().getBody().toString());

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + getTypesSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Illegal uriVariables passed in service call", 0, captureUriVariables.getValue().entrySet().size());
	assertEquals("Incorrect apikey passed in service call", apiKey, requestMap.get("API-KEY"));
	String signature = SecurityUtils.buildHmacMD5Signature(secretKey, apiKey + secretKey);
	assertEquals("Incorrect signature passed in service call", signature, requestMap.get("SIGNATURE"));
	assertEquals("Incorrect StoreId passed in service call", storeId, requestMap.get("StoreId"));
	assertEquals("Incorrect Appointment ID passed in service call", appointmentId, requestMap.get("AppointmentId"));

	// Check that correct values were returned from SAO
	assertNotNull("No statuses returned from service call", searchAppointmentResponse);
	assertEquals("Incorrect Appointment ID returned from service call", "ISPU-13", searchAppointmentResponse
		.getAppointmentId());
	assertEquals("Incorrect Store ID returned from service call", "281", searchAppointmentResponse.getStoreId());
	assertEquals("Incorrect Order No. returned from service call", "124124", searchAppointmentResponse.getOrderNo());
	assertEquals("Incorrect Department returned from service call", "", searchAppointmentResponse.getDepartment());
	assertEquals("Incorrect Appointment ID returned from service call", "Store-Pickup", searchAppointmentResponse
		.getReasonType().entrySet().iterator().next().getValue());
	assertEquals("Incorrect Type ID returned from service call", "2", searchAppointmentResponse.getReasonType()
		.entrySet().iterator().next().getKey());
	assertEquals("Incorrect Appointment Date returned from service call", "04192012",
		getDateAsMMddyyyy(searchAppointmentResponse.getAppointmentDateTime()));
	SimpleDateFormat format = new SimpleDateFormat("h:mma");
	Date timeDate = format.parse("2:00pm");
	assertEquals("Incorrect Appointment Time returned from service call", getTimeAshmma(timeDate),
		getTimeAshmma(searchAppointmentResponse.getAppointmentDateTime()));
	assertEquals("Incorrect First Name returned from service call", "Bryce", searchAppointmentResponse
		.getFirstName());
	assertEquals("Incorrect Last Name returned from service call", "Levin", searchAppointmentResponse.getLastName());
	assertEquals("Incorrect Phone no returned from service call", "(234) 234-2342", searchAppointmentResponse
		.getPhoneNo());
	assertEquals("Incorrect Appointment ID returned from service call", "brycelevin@gmail.com",
		searchAppointmentResponse.getEmail());
	assertEquals("Incorrect Appointment ID returned from service call", "Pickup Store Pickup",
		searchAppointmentResponse.getReason());
	assertEquals("Incorrect current status returned from service call", 0, searchAppointmentResponse
		.getCurrentStatus().intValue());
	assertEquals("Incorrect No. of allowed status returned from service call", 3, searchAppointmentResponse
		.getAllowedStatus().size());
	assertEquals("Incorrect No. of Cancel status returned from service call", 1, searchAppointmentResponse
		.getAllowedStatus().get(AppointmentCloseStatus.CANCEL).size());
	assertEquals("Incorrect Cancel status (1) returned from service call", "1", searchAppointmentResponse
		.getAllowedStatus().get(AppointmentCloseStatus.CANCEL).get(0));
	assertEquals("Incorrect No. of Complete status returned from service call", 2, searchAppointmentResponse
		.getAllowedStatus().get(AppointmentCloseStatus.COMPLETE).size());
	assertEquals("Incorrect Complete status (2) returned from service call", "3", searchAppointmentResponse
		.getAllowedStatus().get(AppointmentCloseStatus.COMPLETE).get(0));
	assertEquals("Incorrect Complete status (3) returned from service call", "2", searchAppointmentResponse
		.getAllowedStatus().get(AppointmentCloseStatus.COMPLETE).get(1));
	assertEquals("Incorrect No. of No show status returned from service call", 1, searchAppointmentResponse
		.getAllowedStatus().get(AppointmentCloseStatus.NO_SHOW).size());
	assertEquals("Incorrect No Show status (4) returned from service call", "4", searchAppointmentResponse
		.getAllowedStatus().get(AppointmentCloseStatus.NO_SHOW).get(0));
    }

    private Map<String, String> getRequestMap(String body) {
	Map<String, String> requestMap = new HashMap<String, String>();
	StringTokenizer strTokens = new StringTokenizer(body, "&");
	while(strTokens.hasMoreElements()){
	    String token = strTokens.nextToken();
	    requestMap.put(token.substring(0, token.indexOf('=')), token.substring(token.indexOf('=') + 1));
	}
	System.out.println("&&&&&&&&&&&&&&************&&&&&&&&&&&&&&" + requestMap.toString());
	return requestMap;
    }

    private SchedulerRequest createSchedulerRequest() {
	SchedulerRequest request = new SchedulerRequest();
	DrpUser user = DrpUserFactory.getDrpUser();
	request.setUser(user);
	Customer customer = new Customer();
	customer.setFirstName("someFirstName");
	customer.setLastName("someLastName");
	customer.setEmail("someEmail");
	customer.setContactPhone("someContactPhone");
	request.setCustomer(customer);
	Map<Date, TimeSlot> appointmenDateTime = new HashMap<Date, TimeSlot>();
	TimeSlot time = new TimeSlot();
	time.setTime("12:00am");
	appointmenDateTime.put(new Date(), time);
	request.setAppointmentTime(appointmenDateTime);
	request.setOrderNo("someOrderNo");
	request.setAppointmentId("someAppointmentId");
	Map<String, String> department = new HashMap<String, String>();
	department.put("departmentId", "departmentValue");
	request.setDepartment(department);
	Map<String, String> appointmentType = new HashMap<String, String>();
	appointmentType.put("appointmentTypeId", "appointmentTypeValue");
	request.setAppointmentType(appointmentType);
	Map<String, String> reason = new HashMap<String, String>();
	reason.put("reasonId", "reasonValue");
	request.setReason(reason);
	request.setSendEmail(true);
	request.setStartDate(new Date());
	request.setEndDate(new Date());
	request.setAppointmentStatusId("someAppointmentStatusId");
	return request;
    }

}
