package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.ACDSParameters;
import com.bestbuy.bbym.ise.drp.domain.DrpUser;
import com.bestbuy.bbym.ise.drp.domain.DrpUserFactory;
import com.bestbuy.bbym.ise.drp.domain.Manifest;
import com.bestbuy.bbym.ise.drp.domain.ManifestFactory;
import com.bestbuy.bbym.ise.drp.domain.ManifestLine;
import com.bestbuy.bbym.ise.drp.domain.ManifestLineFactory;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteria;
import com.bestbuy.bbym.ise.drp.domain.ManifestSearchCriteriaFactory;
import com.bestbuy.bbym.ise.drp.sao.ACDSParamSao;
import com.bestbuy.bbym.ise.drp.sao.ACDSShippingSao;
import com.bestbuy.bbym.ise.drp.sao.ManageManifestSao;
import com.bestbuy.bbym.ise.drp.sao.SearchForDevicesSao;

/**
 * JUnit test for {@link ShippingServiceImpl}.
 */
public class ShippingServiceImplTest {

    private ShippingServiceImpl service;
    private ACDSParamSao mockAcdsParamSao;
    private ACDSShippingSao mockACDSShippingSao;
    private ManageManifestSao mockSao;
    private SearchForDevicesSao mockSearchForDevicesSao;

    @Before
    public void setUp() {
	service = new ShippingServiceImpl();
	mockAcdsParamSao = createStrictMock(ACDSParamSao.class);
	mockACDSShippingSao = createStrictMock(ACDSShippingSao.class);
	mockSao = createStrictMock(ManageManifestSao.class);
	mockSearchForDevicesSao = createStrictMock(SearchForDevicesSao.class);
	service.setACDSParamSao(mockAcdsParamSao);
	service.setACDSShippingSao(mockACDSShippingSao);
	service.setManageManifestSao(mockSao);
	service.setSearchForDevicesSao(mockSearchForDevicesSao);
    }

    /**
     * Test {@link ShippingService#getACDSParameters(String)}.
     */
    @Test
    public void testGetACDSParameters() throws Exception {
	List<ACDSParameters> list = new ArrayList<ACDSParameters>();
	String groupName = "GroupName";
	expect(mockAcdsParamSao.getACDSParameters(groupName)).andReturn(list);
	EasyMock.replay(mockAcdsParamSao);

	List<ACDSParameters> expectResult = service.getACDSParameters(groupName);
	assertEquals(list, expectResult);

    }

    /**
     * Test {@link ShippingService#getShipManifestInfo(BigInteger, DrpUser)}.
     */
    @Test
    public void testGetShipManifestInfo() throws Exception {
	Manifest manifest = new Manifest();
	BigInteger manifestID = new BigInteger("100");
	DrpUser drpUser = DrpUserFactory.getDrpUser();
	expect(mockACDSShippingSao.getShipManifestInfo(manifestID, drpUser)).andReturn(manifest);
	EasyMock.replay(mockACDSShippingSao);

	Manifest expectResult = service.getShipManifestInfo(manifestID, drpUser);
	assertEquals(manifest, expectResult);
    }

    /**
     * Test {@link ShippingService#getShippingImage(BigInteger)}.
     */
    @Test
    public void testGetShippingImage() throws Exception {
	byte[] shippingImage = null;
	BigInteger manifestID = new BigInteger("100");
	expect(mockACDSShippingSao.getShippingImage(manifestID)).andReturn(shippingImage);
	EasyMock.replay(mockACDSShippingSao);

	byte[] expectResult = service.getShippingImage(manifestID);
	assertEquals(shippingImage, expectResult);
    }

    /**
     * Test {@link ShippingService#addManifestLine(ManifestLine, DrpUser)}.
     */
    @Test
    public void testAddManifestLine() throws Exception {
	ManifestLine mockManifestLine = ManifestLineFactory.getManifestLine();
	DrpUser mockUser = DrpUserFactory.getBeastUser();

	ManifestLine mockResult = ManifestLineFactory.getManifestLine();

	expect(mockSao.addManifestLine(mockManifestLine, mockUser)).andReturn(mockResult);
	replay(mockSao);

	assertEquals(service.addManifestLine(mockManifestLine, mockUser), mockResult);
	verify(mockSao);

    }

    /**
     * Test {@link ShippingService#removeManifestLine(List, DrpUser).
     */
    @Test
    public void testRemoveManifestLine() throws Exception {
	List<ManifestLine> mockManifestLines = new ArrayList<ManifestLine>();
	mockManifestLines.add(ManifestLineFactory.getManifestLine());
	mockManifestLines.add(ManifestLineFactory.getManifestLine());
	DrpUser mockUser = DrpUserFactory.getBeastUser();

	expect(mockSao.removeManifestLine(mockManifestLines, mockUser)).andReturn(true);
	replay(mockSao);

	assertTrue(service.removeManifestLine(mockManifestLines, mockUser));
	verify(mockSao);
    }

    /**
     * Test
     * {@link ShippingService#searchManifests(ManifestSearchCriteria, DrpUser)}.
     */
    @Test
    public void testSearchManifests() throws Exception {
	ManifestSearchCriteria mockSearchCriteria = ManifestSearchCriteriaFactory.getManifestSearchCriteria();
	DrpUser mockUser = DrpUserFactory.getBeastUser();
	List<Manifest> mockResults = new ArrayList<Manifest>();
	mockResults.add(ManifestFactory.getManifest());
	mockResults.add(ManifestFactory.getManifest());

	expect(mockSao.searchManifest(mockSearchCriteria, mockUser)).andReturn(mockResults);
	replay(mockSao);

	assertEquals(mockResults, service.searchManifests(mockSearchCriteria, mockUser));
	verify(mockSao);
    }

    /**
     * Test {@link ShippingService#generateManifestDoc(BigInteger, DrpUser)}.
     */
    @Test
    public void testGenerateManifestDoc() throws Exception {
	BigInteger mockManifestID = new BigInteger("12345678901234567890");;
	DrpUser mockUser = DrpUserFactory.getBeastUser();
	byte[] mockByte = new byte[] {7, 25, 12 };

	expect(mockSao.generateManifestDoc(mockManifestID, mockUser)).andReturn(mockByte);
	replay(mockSao);

	assertEquals(mockByte, service.generateManifestDoc(mockManifestID, mockUser));
	verify(mockSao);
    }

}
