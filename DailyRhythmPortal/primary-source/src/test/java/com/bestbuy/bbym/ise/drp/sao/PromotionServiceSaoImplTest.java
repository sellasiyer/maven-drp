package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Ignore;
import org.junit.Test;

import com.bestbuy.bbym.ise.drp.domain.RelatedPromotion;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.iseclientpromo.GetRelatedPromotionsRequestType;
import com.bestbuy.bbym.ise.iseclientpromo.GetRelatedPromotionsResponseType;
import com.bestbuy.bbym.ise.iseclientpromo.PromotionService;

/**
 * JUnit test for {@link PromotionServiceSaoImpl}.
 */
public class PromotionServiceSaoImplTest extends BaseSaoTest {

    private PromotionServiceSaoImpl promotionServiceSaoImpl;
    private PromotionService mockPromotionService;

    @Override
    public void setUp() {
	super.setUp();

	mockPromotionService = EasyMock.createMock(PromotionService.class);
	promotionServiceSaoImpl = new PromotionServiceSaoImpl() {

	    @Override
	    protected PromotionService getPromotionService() throws ServiceException {
		return mockPromotionService;
	    }
	};

	expectIbhPropertiesCalls(true);

	promotionServiceSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	assertIbhValuesPopulatedCorrectly(com.bestbuy.bbym.ise.iseclientpromo.InternationalBusinessHierarchy.class);
    }

    /**
     * Test {@link PromotionServiceSaoImpl#getRelatedPromotions(List, int)}.
     */
    @Ignore
    @Test
    public void testGetACDSParameters() throws Exception {

	// Prepare to record request sent in service call
	Capture<GetRelatedPromotionsRequestType> capture = new Capture<GetRelatedPromotionsRequestType>();

	// Load a canned service response
	GetRelatedPromotionsResponseType getRelatedPromotionsResponseType = loadResponse(
		"PROMO_GetRelatedPromotionsResponse.xml", GetRelatedPromotionsResponseType.class);

	EasyMock.expect(mockPromotionService.getRelatedPromotions(capture(capture))).andReturn(
		getRelatedPromotionsResponseType);
	EasyMock.replay(mockPromotionService);

	HashMap<RelatedPromotion.SearchParameterType, String> searchParamMap = new HashMap<RelatedPromotion.SearchParameterType, String>();
	searchParamMap.put(RelatedPromotion.SearchParameterType.ACTIVATION_PHONE_NUMBER, "test");

	final int locationId = 999;

	List<RelatedPromotion> relatedPromotions = promotionServiceSaoImpl.getRelatedPromotions(searchParamMap,
		locationId);

	// Check that correct values were sent in service call
	assertNotNull("No IBH passed in service call", capture.getValue().getInternationalBusinessHierarchy());
	assertEquals("Incorrect searchStrings passed in service call", searchParamMap, capture.getValue()
		.getSearchStrings());
	assertEquals("Incorrect locationId passed in service call", locationId, capture.getValue().getLocationId());

	// Check that correct values were returned from SAO
	assertNotNull("No promotions returned from service call", relatedPromotions);
	assertEquals("Incorrect number of promotions returned from service call", 9, relatedPromotions.size());
	// TODO Add more assertions
    }

    /**
     * Test
     * {@link PromotionServiceSaoImpl#register(String, String, String, String, boolean, String)}
     * .
     */
    @Ignore
    @Test
    public void testRegister() throws Exception {
	// TODO Implement me!
    }
}
