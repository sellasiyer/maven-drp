package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import java.math.BigInteger;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacdsdevice.DeviceType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.ManageDevicePortType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.SearchForDevicesRequestType;
import com.bestbuy.bbym.ise.iseclientacdsdevice.SearchForDevicesResponseType;

/**
 * JUnit test for {@link SearchForDevicesSaoImpl}.
 * 
 * @author a909237
 */
public class SearchForDevicesSaoImplTest extends BaseSaoTest {

    private SearchForDevicesSaoImpl searchForDevicesSaoImpl;
    private ManageDevicePortType mockManageDevicePortType;

    @Override
    public void setUp() {
	super.setUp();

	mockManageDevicePortType = EasyMock.createMock(ManageDevicePortType.class);
	searchForDevicesSaoImpl = new SearchForDevicesSaoImpl() {

	    @Override
	    protected ManageDevicePortType getACDSDeviceSearchService() throws ServiceException {
		return mockManageDevicePortType;
	    }
	};

	expectIbhPropertiesCalls(true);

	searchForDevicesSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientacdsdevice.InternationalBusinessHierarchyType.class);
    }

    /**
     * Check that the returned values were populated from a successful sample
     * response.
     */
    @Test
    public void testSearchManifest_Success() throws Exception {

	// Prepare to record request sent in service call
	Capture<SearchForDevicesRequestType> capture = new Capture<SearchForDevicesRequestType>();

	// Load a canned service response
	SearchForDevicesResponseType searchForDevicesResponseType = loadResponse("ACDS_SearchForDevicesResponse.xml",
		SearchForDevicesResponseType.class);

	EasyMock.expect(mockManageDevicePortType.searchDevices(capture(capture))).andReturn(
		searchForDevicesResponseType);
	EasyMock.replay(mockManageDevicePortType);

	final ManifestSearchCriteria searchCriteria = new ManifestSearchCriteria();
	searchCriteria.setManifestStatus("OPEN");
	final DrpUser drpUser = DrpUserFactory.getDrpUser();

	List<ManifestLine> manifestLines = searchForDevicesSaoImpl.searchManifest(searchCriteria, drpUser);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarchy());
	assertEquals("Incorrect storeId passed in service call", new BigInteger(drpUser.getStoreId()), capture
		.getValue().getSearchCriteria().getStoreID());
	assertEquals("Incorrect deviceStatus passed in service call", searchCriteria.getManifestStatus(), capture
		.getValue().getSearchCriteria().getDeviceStatus());

	// Check that correct values were returned from SAO
	assertNotNull("No manifest lines", manifestLines);
	assertEquals("Incorrect number of manifest lines",
		searchForDevicesResponseType.getDevices().getDevice().size(), manifestLines.size());
	for(int i = 0; i < manifestLines.size(); i++){
	    ManifestLine manifestLine = manifestLines.get(i);
	    DeviceType deviceType = searchForDevicesResponseType.getDevices().getDevice().get(i);
	    assertSame("Incorrect itemTag", deviceType.getItemTag(), manifestLine.getItemTag());
	    assertSame("Incorrect imeiesn", deviceType.getIMEIESN(), manifestLine.getImeiesn());
	    assertSame("Incorrect productDescription", deviceType.getProductDescription(), manifestLine
		    .getProductDescription());
	    assertSame("Incorrect make", deviceType.getMake(), manifestLine.getMake());
	    assertSame("Incorrect model", deviceType.getModel(), manifestLine.getModel());
	    assertSame("Incorrect returnType", deviceType.getReturnType(), manifestLine.getReturnType());
	    assertSame("Incorrect deviceStatus", deviceType.getDeviceStatus(), manifestLine.getDeviceStatus());
	    if (deviceType.isShortIndicator() == null){
		assertEquals("Incorrect short", false, manifestLine.isShort());
	    }else{
		assertEquals("Incorrect short", deviceType.isShortIndicator(), manifestLine.isShort());
	    }
	}
    }

}
