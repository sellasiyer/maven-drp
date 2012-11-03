package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.oasis_open.docs.wss._2004._01.oasis_200401_wss_wssecurity_secext_1_0.SecurityType;

import com.bestbuy.bbym.ise.domain.Store;
import com.bestbuy.bbym.ise.drp.domain.scoreboard.ScoreboardStoreType;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.tsh.common.metadata.components.v2.RequestMetaDataType;
import com.bestbuy.tsh.facilities.location.retrievelocationsservice.v2.RetrieveLocationsPortType;
import com.bestbuy.tsh.facilities.locations.fields.v2.RetrieveLocationsRequestType;
import com.bestbuy.tsh.facilities.locations.fields.v2.RetrieveLocationsResponseType;

/**
 * JUnit test for {@link StoreInformationSaoImpl}.
 */
public class StoreInformationSaoImplTest extends BaseSaoTest {

    private StoreInformationSaoImpl storeInformationSaoImpl;
    private RetrieveLocationsPortType mockRetrieveLocationsPortType;

    @Override
    public void setUp() {
	super.setUp();

	mockRetrieveLocationsPortType = EasyMock.createMock(RetrieveLocationsPortType.class);
	storeInformationSaoImpl = new StoreInformationSaoImpl() {

	    @Override
	    protected RetrieveLocationsPortType getRetrieveLocationService() throws ServiceException {
		return mockRetrieveLocationsPortType;
	    }

	};

	storeInformationSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// // TODO Why don't we have an IBH for store information service?
    }

    /**
     * Test for {@link StoreInformationSaoImpl#getStoreDetails(String)}.
     */
    @Test
    public void testGetStoreDetails() throws Exception {
	// Prepare to record request sent in service call
	Capture<SecurityType> captureSecurityType = new Capture<SecurityType>();
	Capture<RetrieveLocationsRequestType> captureRetrieveLocationsRequestType = new Capture<RetrieveLocationsRequestType>();

	// Load a canned service response
	RetrieveLocationsResponseType retrieveLocationsResponseType = loadResponse("TSH_RetrieveLocationsResponse.xml",
		RetrieveLocationsResponseType.class);

	EasyMock.expect(
		mockRetrieveLocationsPortType.retrieveLocations(capture(captureSecurityType),
			capture(captureRetrieveLocationsRequestType))).andReturn(retrieveLocationsResponseType);
	EasyMock.replay(mockRetrieveLocationsPortType);

	final String sourceId = "someSourceId";
	final String version = "7878787";

	EasyMock.expect(drpPropertyService.getProperty("APPLICATION_NAME")).andReturn(sourceId);
	EasyMock.expect(drpPropertyService.getProperty("TSH_LOCATION_WS_VERSION", "2")).andReturn(version);
	EasyMock.replay(drpPropertyService);

	final String storeId = "999999";

	Store store = storeInformationSaoImpl.getStoreDetails(storeId);

	// Check that correct values were sent in service call
	SecurityType sentSecurityType = captureSecurityType.getValue();
	assertNotNull("No security type passed in service call", sentSecurityType);
	assertNotNull("No security type base 64 assertion passed in service call", sentSecurityType
		.getBase64Assertion());
	assertEquals("Incorrect security type base 64 assertion passed in service call", "", sentSecurityType
		.getBase64Assertion().getValue());
	RetrieveLocationsRequestType sentRetrieveLocationsRequestType = captureRetrieveLocationsRequestType.getValue();
	assertNotNull("No retrieve locations request type passed in service call", sentRetrieveLocationsRequestType);
	assertNotNull("No location Id list passed in service call", sentRetrieveLocationsRequestType
		.getLocationIDList());
	assertNotNull("No location Id passed in service call", sentRetrieveLocationsRequestType.getLocationIDList()
		.getLocationID());
	assertEquals("Incorrent number of location Ids passed in service call", 1, sentRetrieveLocationsRequestType
		.getLocationIDList().getLocationID().size());
	assertEquals("Incorrent number of location Ids passed in service call", Long.valueOf(storeId),
		sentRetrieveLocationsRequestType.getLocationIDList().getLocationID().get(0));
	RequestMetaDataType sentMetaData = sentRetrieveLocationsRequestType.getMetaData();
	assertNotNull("No meta data passed in service call", sentMetaData);
	assertEquals("Incorrect meta data source Id passed in service call", sourceId, sentMetaData.getSourceID());
	assertEquals("Incorrect meta data version passed in service call", Integer.valueOf(version), sentMetaData
		.getVersion());

	// Check that correct values were returned from SAO
	assertNotNull("No store", store);
	assertEquals("Incorrect store Id returned from service call", storeId, store.getId());
	assertEquals("Incorrect store name returned from service call", "APPLICATION TEST SER", store.getStoreName());
	assertEquals("Incorrect store type returned from service call", ScoreboardStoreType.SWAS, store.getStoreType());
	assertEquals("Incorrect store phone number returned from service call", "8009472000", store
		.getStorePhoneNumber());
    }

    /**
     * Test failed call for {@link StoreInformationSaoImpl#getStoreDetails(String)}.
     */
    @Test
    public void testGetStoreDetailsFailure() throws Exception {

	// TODO Implement me!

	// Load a canned service response
	// loadResponse("TSH_RetrieveLocationsResponse1.xml")
    }
}
