package com.bestbuy.bbym.ise.drp.sao;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.easymock.EasyMock.capture;

import java.math.BigInteger;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;

import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacdsshipping.GetLabelRequestType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.GetLabelResponseType;
import com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingPortType;

/**
 * JUnit test for {@link ACDSShippingSaoImpl}
 */
public class ACDSShippingSaoImplTest extends BaseSaoTest {

    private ACDSShippingSaoImpl acdsShippingSaoImpl = new ACDSShippingSaoImpl();
    private ShippingPortType mockShippingPortType;

    @Override
    public void setUp() {
	super.setUp();

	mockShippingPortType = EasyMock.createMock(ShippingPortType.class);
	acdsShippingSaoImpl = new ACDSShippingSaoImpl() {

	    @Override
	    protected ShippingPortType getACDSShippingService() throws ServiceException {
		return mockShippingPortType;
	    }

	};

	expectIbhPropertiesCalls(true);

	acdsShippingSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientacdsshipping.InternationalBusinessHierarchyType.class);
    }

    /**
     * Test {@link ACDSShippingSaoImpl#getShippingImage(java.math.BigInteger)}.
     */
    @Test
    public void testGetShippingImage() throws Exception {

	// Prepare to record request sent in service call
	Capture<GetLabelRequestType> capture = new Capture<GetLabelRequestType>();

	// Load a canned service response
	GetLabelResponseType getLabelResponseType = loadResponse("ACDS_GetLabelResponse.xml",
		GetLabelResponseType.class);

	EasyMock.expect(mockShippingPortType.getShippingLabel(capture(capture))).andReturn(getLabelResponseType);
	EasyMock.replay(mockShippingPortType);

	final BigInteger manifestId = new BigInteger("12345");

	byte[] shippingImage = acdsShippingSaoImpl.getShippingImage(manifestId);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarchy());
	assertEquals("Incorrect manifestId passed in service call", manifestId, capture.getValue().getLabelRequest()
		.getShipManifest().getManifestID());

	// Check that correct values were returned from SAO
	assertNotNull("No shipping image", shippingImage);
    }
}
