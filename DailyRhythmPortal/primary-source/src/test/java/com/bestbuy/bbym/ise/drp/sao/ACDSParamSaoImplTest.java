package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacdsparam.ACDSParamPortType;
import com.bestbuy.bbym.ise.iseclientacdsparam.ParamGroupRequest;
import com.bestbuy.bbym.ise.iseclientacdsparam.ParamRequestType;
import com.bestbuy.bbym.ise.iseclientacdsparam.ParamResponseType;

/**
 * JUnit test for {@link ACDSParamSaoImpl}.
 * 
 * @author a909237
 */
public class ACDSParamSaoImplTest extends BaseSaoTest {

    private ACDSParamSaoImpl acdsParamSaoImpl = new ACDSParamSaoImpl();
    private ACDSParamPortType mockAcdsParamPortType;

    @Override
    public void setUp() {
	super.setUp();

	mockAcdsParamPortType = EasyMock.createMock(ACDSParamPortType.class);
	acdsParamSaoImpl = new ACDSParamSaoImpl() {

	    @Override
	    protected ACDSParamPortType getACDSParamService() throws ServiceException {
		return mockAcdsParamPortType;
	    }
	};

	expectIbhPropertiesCalls(true);

	acdsParamSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientacdsparam.InternationalBusinessHierarchyType.class);
    }

    @Test
    public void testGetACDSParameters() throws Exception {

	// Prepare to record request sent in service call
	Capture<ParamRequestType> capture = new Capture<ParamRequestType>();

	// Load a canned service response
	ParamResponseType paramResponseType = loadResponse("ACDS_ParamResponseType.xml", ParamResponseType.class);

	EasyMock.expect(mockAcdsParamPortType.acdsParam(capture(capture))).andReturn(paramResponseType);
	EasyMock.replay(mockAcdsParamPortType);

	final String groupName = "someGroupName";

	List<ACDSParameters> acdsParameters = acdsParamSaoImpl.getACDSParameters(groupName);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBussinessHirerchy());
	ParamGroupRequest sentParamGroupRequest = capture.getValue().getGroupRequest();
	assertEquals("Incorrect number of groups passed in service call", 1, sentParamGroupRequest.getGroup().size());
	assertEquals("Incorrect group passed in service call", groupName, sentParamGroupRequest.getGroup().get(0));

	// Check that correct values were returned from SAO
	assertNotNull("No parameters returned from service call", acdsParameters);
	assertEquals("Incorrect number of parameters returned from service call", 9, acdsParameters.size());
	ACDSParameters acdsParameter = acdsParameters.get(0);
	assertEquals("Incorrect groupName returned from service call", "$SHIPMENT_PORTAL$", acdsParameter
		.getGroupName());
	// TODO Add more assertions
    }

}
