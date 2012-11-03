package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.eq;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import org.easymock.Capture;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestOperations;

import com.bestbuy.bbym.ise.domain.User;
import com.bestbuy.bbym.ise.drp.dao.TestUtil;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * JUnit test for {@link EmployeeLookupSaoImpl}.
 */
public class EmployeeLookupSaoImplTest extends BaseSaoTest {

    private EmployeeLookupSaoImpl sao;
    private RestOperations restOperations;

    @Before
    public void setUp() {
	super.setUp();

	restOperations = createMock(RestOperations.class);
	sao = new EmployeeLookupSaoImpl();

	sao.setDrpPropertiesService(drpPropertyService);
	ReflectionTestUtils.setField(sao, "restOperations", restOperations);
    }

    @Override
    public void testTimeout() {
	try{
	    expect(drpPropertyService.getProperty("TSH_EMPLOYEE_LOOKUP_SERVICE_URL")).andReturn(
		    "TSH_EMPLOYEE_LOOKUP_SERVICE_URL");
	}catch(ServiceException e){
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	replay(drpPropertyService);

	Capture<String> url = new Capture<String>();
	Capture<String> params = new Capture<String>();
	String response = TestUtil.readFileToString(getClass(), "EmployeeLookupValidResponse.xml");
	expect(restOperations.postForObject(capture(url), capture(params), eq(String.class))).andThrow(
		new org.springframework.web.client.ResourceAccessException("Time out..."));
	replay(restOperations);

	try{
	    sao.getEmployeeDetails("a860196");

	}catch(ServiceException e){

	}catch(IseBusinessException e){

	}
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH for employee lookup service?
    }

    @Test
    public void testGetEmployeeDetails() throws Exception {

	expect(drpPropertyService.getProperty("TSH_EMPLOYEE_LOOKUP_SERVICE_URL")).andReturn(
		"TSH_EMPLOYEE_LOOKUP_SERVICE_URL");
	replay(drpPropertyService);

	Capture<String> url = new Capture<String>();
	Capture<String> params = new Capture<String>();
	String response = TestUtil.readFileToString(getClass(), "EmployeeLookupValidResponse.xml");
	expect(restOperations.postForObject(capture(url), capture(params), eq(String.class))).andReturn(response);
	replay(restOperations);

	User user = sao.getEmployeeDetails("a860196");

	assertEquals("TSH_EMPLOYEE_LOOKUP_SERVICE_URL", url.getValue());
	assertEquals(
		"<v1:EmployeeLookupRequest v11:languageCode=\"?\" xmlns:v1=\"http://www.tsh.bestbuy.com/parties/employee/lookup/service/v1\" xmlns:v11=\"http://www.tsh.bestbuy.com/common/v1\" xmlns:v12=\"http://www.tsh.bestbuy.com/parties/employee/lookup/components/v1\" xmlns:v13=\"http://www.tsh.bestbuy.com/common/metadata/fields/v1\" xmlns:v14=\"http://www.tsh.bestbuy.com/common/communication/v1\" xmlns:v15=\"http://www.tsh.bestbuy.com/parties/employee/lookup/fields/v1\"><v12:EmployeeRequestDetails><v12:EmployeeInformation><v15:AID>a860196</v15:AID></v12:EmployeeInformation></v12:EmployeeRequestDetails></v1:EmployeeLookupRequest>",
		params.getValue());
	assertEquals("Scott", user.getFirstName());
	assertEquals("Kimber", user.getLastName());
    }

    @Test
    public void testGetEmployeeDetailsNoUser() throws Exception {

	expect(drpPropertyService.getProperty("TSH_EMPLOYEE_LOOKUP_SERVICE_URL")).andReturn(
		"TSH_EMPLOYEE_LOOKUP_SERVICE_URL");
	replay(drpPropertyService);

	Capture<String> url = new Capture<String>();
	Capture<String> params = new Capture<String>();
	String response = TestUtil.readFileToString(getClass(), "EmployeeLookupValidResponseWithNoUser.xml");
	expect(restOperations.postForObject(capture(url), capture(params), eq(String.class))).andReturn(response);
	replay(restOperations);

	User user = sao.getEmployeeDetails("a860196");

	assertEquals("TSH_EMPLOYEE_LOOKUP_SERVICE_URL", url.getValue());
	assertEquals(
		"<v1:EmployeeLookupRequest v11:languageCode=\"?\" xmlns:v1=\"http://www.tsh.bestbuy.com/parties/employee/lookup/service/v1\" xmlns:v11=\"http://www.tsh.bestbuy.com/common/v1\" xmlns:v12=\"http://www.tsh.bestbuy.com/parties/employee/lookup/components/v1\" xmlns:v13=\"http://www.tsh.bestbuy.com/common/metadata/fields/v1\" xmlns:v14=\"http://www.tsh.bestbuy.com/common/communication/v1\" xmlns:v15=\"http://www.tsh.bestbuy.com/parties/employee/lookup/fields/v1\"><v12:EmployeeRequestDetails><v12:EmployeeInformation><v15:AID>a860196</v15:AID></v12:EmployeeInformation></v12:EmployeeRequestDetails></v1:EmployeeLookupRequest>",
		params.getValue());
	assertEquals(null, user);
    }

}
