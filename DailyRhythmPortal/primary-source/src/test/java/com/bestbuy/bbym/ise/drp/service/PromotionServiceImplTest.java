package com.bestbuy.bbym.ise.drp.service;

import static org.easymock.EasyMock.createStrictMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.datatype.DatatypeConfigurationException;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.Device;
import com.bestbuy.bbym.ise.drp.domain.Promotion;
import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.drp.sao.PromotionServiceSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;

/**
 * JUnit test for {@link PromotionServiceImpl}.
 * 
 * @author a911002
 */
public class PromotionServiceImplTest {

    private PromotionServiceImpl promotionService = new PromotionServiceImpl();
    private PromotionServiceSao mockSao;

    @Before
    public void setUp() {
	mockSao = createStrictMock(PromotionServiceSao.class);
	promotionService.setSao(mockSao);
    }

    /**
     * Tests getRelatedPromotions method from PromotionServiceImpl
     */
    @Test
    public void testGetRelatedPromotions() throws Exception, DatatypeConfigurationException {
	// Setting up the parameters for the SAO call
	HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap = new HashMap<RelatedPromotion.SearchParameterType, String>();
	searchParamMap.put(RelatedPromotion.SearchParameterType.ACTIVATION_PHONE_NUMBER, "test");

	int locationId = 12;

	// Setting up the results expected
	List<RelatedPromotion> mockResults = this.buildMockRelatedPromotions();

	expect(mockSao.getRelatedPromotions(searchParamMap, locationId)).andReturn(mockResults);
	replay(mockSao);

	assertEquals(promotionService.getRelatedPromotions(searchParamMap, locationId), mockResults);
	verify(mockSao);
    }

    /**
     * Tests getRelatedPromotions to see if IseBusinessException is thrown from PromotionServiceImpl
     */
    @Test(expected = IseBusinessException.class)
    public void testGetRelatedPromotionsIseBusinessException() throws Exception {
	// Setting up the parameters for the SAO call
	HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap = new HashMap<RelatedPromotion.SearchParameterType, String>();
	searchParamMap.put(RelatedPromotion.SearchParameterType.ACTIVATION_PHONE_NUMBER, "test");

	int locationId = 12;

	expect(mockSao.getRelatedPromotions(searchParamMap, locationId)).andThrow(new IseBusinessException());
	replay(mockSao);

	promotionService.getRelatedPromotions(searchParamMap, locationId);
	verify(mockSao);
    }

    /**
     * Tests register method from PromotionServiceImpl
     */
    @Test
    public void testRegister() throws Exception {
	String mockPromoCode = "6999";
	String mockPhoneNumber = "313-555-1212";
	String MockEmail = "mock@gmail.com";
	String mockFullName = "Bruce Wayne";
	boolean mockAllowContact = true;

	mockSao.register(mockPromoCode, mockPhoneNumber, MockEmail, mockFullName, mockAllowContact);
	EasyMock.expectLastCall();
	replay(mockSao);

	promotionService.register(mockPromoCode, mockPhoneNumber, MockEmail, mockFullName, mockAllowContact);
	verify(mockSao);
    }

    /**
     * Tests register to see if IseBusinessException is thrown from PromotionServiceImpl
     */
    @Test(expected = IseBusinessException.class)
    public void testRegisterIseBusinessException() throws Exception {
	String mockPromoCode = "6999";
	String mockPhoneNumber = "313-555-1212";
	String MockEmail = "mock@gmail.com";
	String mockFullName = "Bruce Wayne";
	boolean mockAllowContact = true;

	mockSao.register(mockPromoCode, mockPhoneNumber, MockEmail, mockFullName, mockAllowContact);
	EasyMock.expectLastCall().andThrow(new IseBusinessException());
	replay(mockSao);

	promotionService.register(mockPromoCode, mockPhoneNumber, MockEmail, mockFullName, mockAllowContact);
	verify(mockSao);
    }

    /**
     * Builds List of Mock RelatedPromotion objects used to test
     */
    private List<RelatedPromotion> buildMockRelatedPromotions() {
	List<RelatedPromotion> mockResults = new ArrayList<RelatedPromotion>();
	RelatedPromotion mockPromo = new RelatedPromotion();

	mockPromo.setPromotion(this.buildMockPromotion());
	mockPromo.setPromotionAvailable(true);
	mockResults.add(mockPromo);

	return mockResults;
    }

    /**
     * Builds mock Promotion object used for testing
     */
    private Promotion buildMockPromotion() {
	Promotion mockPromo = new Promotion();

	mockPromo.setPromotionID((long) 1234);
	mockPromo.setPromotionCode("Mock Promotion Code");
	mockPromo.setPromotionDescription("Mock description");
	mockPromo.setPromotionBeginDate(new Date());
	mockPromo.setPromotionEndDate(new Date());
	mockPromo.setPromotionSKU(1234567890);
	mockPromo.setPromotionValue("Mock Value");
	mockPromo.setPromotionApprover("Mock Approver");
	mockPromo.setPromotionAffiliate("Mock Affiliate");
	mockPromo.setPromotionCostCenter("Mock Cost Center");
	mockPromo.setPromotionCirculationQuantity(222);
	mockPromo.setPromotionExpense(444);
	mockPromo.setPromotionDeviceLogicRule("Mock Device Logic Rule");
	mockPromo.setPromotionCardinality("Mock Cardinality");
	mockPromo.setPromotionStackable(true);
	mockPromo.setFulfillmentType("Mock Fulfillment Type");
	mockPromo.setPromotionContentFilename("Mock Content Filename");
	mockPromo.setPromotionContentType("Mock Content Type");
	mockPromo.setPromotionContentURL("Mock Content URL");
	// List type for Devices and ValidOrderTypes
	List<Device> mockDevices = new ArrayList<Device>();
	mockDevices.add(this.buildMockDevices());
	mockPromo.setDevices(null);
	List<String> mockOrderTypes = new ArrayList<String>();
	mockOrderTypes.add("String1");
	mockOrderTypes.add("String2");
	mockOrderTypes.add("String3");
	mockPromo.setValidOrderTypes(mockOrderTypes);

	return mockPromo;
    }

    /**
     * Builds mock Device object used for testing
     * 
     * @return Device object
     */
    private Device buildMockDevices() {
	Device mockDevice = new Device();

	return mockDevice;
    }

}
