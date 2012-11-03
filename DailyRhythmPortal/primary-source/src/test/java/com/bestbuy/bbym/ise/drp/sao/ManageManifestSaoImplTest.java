package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientacds.AddToManifestRequestType;
import com.bestbuy.bbym.ise.iseclientacds.AddToManifestResponseType;
import com.bestbuy.bbym.ise.iseclientacds.GenerateManifestDocumentRequestType;
import com.bestbuy.bbym.ise.iseclientacds.GenerateManifestDocumentResponseType;
import com.bestbuy.bbym.ise.iseclientacds.ManageManifestPortType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestHeaderType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestLineItemType;
import com.bestbuy.bbym.ise.iseclientacds.ManifestRemoveLineType;
import com.bestbuy.bbym.ise.iseclientacds.RemoveFromManifestRequestType;
import com.bestbuy.bbym.ise.iseclientacds.RemoveFromManifestResponseType;
import com.bestbuy.bbym.ise.iseclientacds.SearchForManifestsRequestType;
import com.bestbuy.bbym.ise.iseclientacds.SearchForManifestsResponseType;
import com.bestbuy.bbym.ise.util.Util;

/**
 * JUnit test for {@link ManageManifestSaoImpl}.
 * 
 * @author a909237
 */
public class ManageManifestSaoImplTest extends BaseSaoTest {

    private ManageManifestSaoImpl manageManifestSaoImpl;
    private ManageManifestPortType mockManageManifestPortType;

    @Override
    public void setUp() {
	super.setUp();

	mockManageManifestPortType = EasyMock.createMock(ManageManifestPortType.class);
	manageManifestSaoImpl = new ManageManifestSaoImpl() {

	    @Override
	    protected ManageManifestPortType getManageManifestService() throws ServiceException {
		return mockManageManifestPortType;
	    }
	};

	expectIbhPropertiesCalls(true);

	manageManifestSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientacds.InternationalBusinessHierarchyType.class);
    }

    /**
     * Test Case for Adding ManifestLine OPeration of the Service
     */
    @Ignore
    @Test
    public void testAddManifestLine() throws Exception {

	// Prepare to record request sent in service call
	Capture<AddToManifestRequestType> capture = new Capture<AddToManifestRequestType>();

	// Load a canned service response
	AddToManifestResponseType addToManifestResponseType = loadResponse("ACDS_AddToManifestResponseType.xml",
		AddToManifestResponseType.class);

	EasyMock.expect(mockManageManifestPortType.addManifestLine(capture(capture))).andReturn(
		addToManifestResponseType);
	EasyMock.replay(mockManageManifestPortType);

	final ManifestLine mfLine = new ManifestLine();
	mfLine.setItemTag("DTMS144688");
	final DrpUser drpUser = DrpUserFactory.getDrpUser();

	ManifestLine manifestLine = manageManifestSaoImpl.addManifestLine(mfLine, drpUser);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarchy());
	assertEquals("Incorrect itemTag passed in service call", mfLine.getItemTag(), capture.getValue()
		.getManifestRequest().getItemTag());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No manifest line returned from service call", manifestLine);
	// TODO Add more assertions
    }

    /**
     * Test Case for Removing ManifestLine Operation of the Service
     */
    @Test
    public void testRemoveManifestLine() throws Exception {

	// Prepare to record request sent in service call
	Capture<RemoveFromManifestRequestType> capture = new Capture<RemoveFromManifestRequestType>();

	// Load a canned service response
	RemoveFromManifestResponseType removeFromManifestResponseType = loadResponse(
		"ACDS_RemoveFromManifestResponse.xml", RemoveFromManifestResponseType.class);

	EasyMock.expect(mockManageManifestPortType.removeManifestLine(capture(capture))).andReturn(
		removeFromManifestResponseType);
	EasyMock.replay(mockManageManifestPortType);

	final List<ManifestLine> manifestlines = new ArrayList<ManifestLine>();
	ManifestLine manifestline = new ManifestLine();
	manifestline.setManifestID(new BigInteger("28"));
	manifestline.setManifestLineID(new BigInteger("70"));
	manifestlines.add(manifestline);
	final DrpUser drpUser = DrpUserFactory.getDrpUser();

	boolean removeFlag = manageManifestSaoImpl.removeManifestLine(manifestlines, drpUser);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarchy());
	List<ManifestRemoveLineType> removeLineTypes = capture.getValue().getRemoveLines().getManifestRequest();
	assertEquals("Incorrect itemTag passed in service call", manifestlines.size(), removeLineTypes.size());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertEquals("Incorrect remove flag returned from service call", removeFromManifestResponseType
		.isManifestDeleted(), removeFlag);
	// TODO Add more assertions
    }

    @Test
    public void testSearchManifest() throws Exception {

	// Prepare to record request sent in service call
	Capture<SearchForManifestsRequestType> capture = new Capture<SearchForManifestsRequestType>();

	// Load a canned service response
	SearchForManifestsResponseType searchForManifestsResponseType = loadResponse(
		"ACDS_SearchForManifestsResponse.xml", SearchForManifestsResponseType.class);

	EasyMock.expect(mockManageManifestPortType.searchManifests(capture(capture))).andReturn(
		searchForManifestsResponseType);
	EasyMock.replay(mockManageManifestPortType);

	ManifestSearchCriteria searchCriteria = new ManifestSearchCriteria();
	searchCriteria.setMostRecentNumber(15);

	List<Manifest> manifests = manageManifestSaoImpl.searchManifest(searchCriteria, DrpUserFactory.getDrpUser());

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarchy());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No manifests returned from service call", manifests);
	assertEquals("Incorrect number of manifests", searchForManifestsResponseType.getManifests().getManifest()
		.size(), manifests.size());
	for(int i = 0; i < manifests.size(); i++){
	    Manifest manifest = manifests.get(i);
	    ManifestHeaderType manifestHeaderType = searchForManifestsResponseType.getManifests().getManifest().get(i);
	    assertSame("Incorrect manifestId", manifestHeaderType.getManifestID(), manifest.getManifestID());
	    assertSame("Incorrect status", manifestHeaderType.getStatus(), manifest.getStatus());
	    assertSame("Incorrect deviceCount", manifestHeaderType.getDeviceCount(), manifest.getDeviceCount());
	    assertSame("Incorrect trackingIdentifier", manifestHeaderType.getTrackingIdentifier(), manifest
		    .getTrackingIdentifier());
	    assertSame("Incorrect dayCreated", manifestHeaderType.getDayCreated(), manifest.getDayCreated());
	    assertEquals("Incorrect dateTimeCreated", Util.toUtilDate(manifestHeaderType.getDateTimeCreated()),
		    manifest.getDateTimeCreated());
	    if (manifestHeaderType.getShipScheduledDateTime() == null){
		assertNull("Incorrect dateCompleted", manifest.getDateCompleted());
	    }else{
		assertEquals("Incorrect dateCompleted", manifestHeaderType.getShipScheduledDateTime()
			.toGregorianCalendar().getTime(), manifest.getDateCompleted());
	    }
	    assertSame("Incorrect createdByUser", manifestHeaderType.getCreatedByUser(), manifest.getCreatedByUser());
	    assertEquals("Incorrect number of manifest lines", manifestHeaderType.getManifestLine().size(), manifest
		    .getManifestLine().size());
	    for(int j = 0; j < manifest.getManifestLine().size(); j++){
		ManifestLine manifestLine = manifest.getManifestLine().get(j);
		ManifestLineItemType manifestLineItemType = manifestHeaderType.getManifestLine().get(j);
		assertSame("Incorrect manifestId", manifestLineItemType.getManifestID(), manifestLine.getManifestID());
		assertSame("Incorrect manifestLineId", manifestLineItemType.getManifestLineID(), manifestLine
			.getManifestLineID());
		assertSame("Incorrect itemTag", manifestLineItemType.getItemTag(), manifestLine.getItemTag());
		assertSame("Incorrect deviceStatus", manifestLineItemType.getDeviceStatus(), manifestLine
			.getDeviceStatus());
		assertSame("Incorrect imeiesn", manifestLineItemType.getIMEIESN(), manifestLine.getImeiesn());
		assertSame("Incorrect make", manifestLineItemType.getMake(), manifestLine.getMake());
		assertSame("Incorrect model", manifestLineItemType.getModel(), manifestLine.getModel());
		assertSame("Incorrect returnType", manifestLineItemType.getReturnType(), manifestLine.getReturnType());
		assertSame("Incorrect productDescription", manifestLineItemType.getProductDescription(), manifestLine
			.getProductDescription());
	    }

	}
    }

    /**
     * Test for
     * {@link ManageManifestSaoImpl#generateManifestDoc(BigInteger, com.bestbuy.bbym.ise.drp.domain.DrpUser)}
     * .
     */
    @Test
    public void testGenerateManifestDoc() throws Exception {

	// Prepare to record request sent in service call
	Capture<GenerateManifestDocumentRequestType> capture = new Capture<GenerateManifestDocumentRequestType>();

	// Load a canned service response
	GenerateManifestDocumentResponseType generateManifestDocumentResponseType = loadResponse(
		"ACDS_GenerateManifestDocResponse.xml", GenerateManifestDocumentResponseType.class);

	EasyMock.expect(mockManageManifestPortType.generateManifestDoc(capture(capture))).andReturn(
		generateManifestDocumentResponseType);
	EasyMock.replay(mockManageManifestPortType);

	byte[] manifestDoc = manageManifestSaoImpl.generateManifestDoc(new BigInteger("138"), DrpUserFactory
		.getDrpUser());

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationBusinessHierarchy());
	// TODO Add more assertions

	// Check that correct values were returned from SAO
	assertNotNull("No manifest doc returned from service call", manifestDoc);
	// TODO Add more assertions
    }

}
