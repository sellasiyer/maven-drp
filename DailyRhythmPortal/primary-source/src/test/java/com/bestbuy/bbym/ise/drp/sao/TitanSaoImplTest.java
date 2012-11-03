package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.createMockBuilder;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertNotNull;

import java.io.InputStream;

import javax.ws.rs.core.MultivaluedMap;
import javax.xml.parsers.DocumentBuilderFactory;

import org.easymock.EasyMock;
import org.easymock.IMockBuilder;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

import com.bestbuy.bbym.ise.drp.dao.TestUtil;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.sun.jersey.core.util.MultivaluedMapImpl;

/**
 * JUnit test for {@link TitanSaoImpl}.
 * 
 * @author a904002
 */
@Ignore
public class TitanSaoImplTest {

    private static Logger logger = LoggerFactory.getLogger(TitanSaoImplTest.class);
    private static final String TITAN_CATEGORY = "TITAN_CATEGORY";
    private static final String TITAN_BASE_URI = "TITAN_BASE_URI";
    private static final String TITAN_MEDIA_TYPE = "TITAN_MEDIA_TYPE";
    private static final String TITAN_RESULT_PAGE_SIZE = "TITAN_RESULT_PAGE_SIZE";
    private static final String TITAN_SEARCH_PREFIX = "Q";
    private static final String CATEGORY = "Category";
    private static final String PAGE = "Page";
    private static final String PAGE_SIZE = "PageSize";
    private static final String productDetailHref = "http://hydra-pt:8080/store-271/catalog/products/3487784";
    private static final String pricingQA1 = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784";
    private static final String pricingQA2 = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect";
    private static final String pricingQA3 = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No";
    private static final String pricingQA4 = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No&cell-phones-8=Yes";
    private static final String pricingQA5 = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No&cell-phones-8=Yes&cell-phones-9=Yes";
    private static final String pricingQASummaryHref = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No&cell-phones-8=Yes&cell-phones-9=Yes&cell-phones-10=Yes";
    private static final String mediaType = "application/vnd.bestbuy.bpe+xml";
    private static final String addToCartHref = "http://hydra-pt:8080/store-10/carts";
    private DrpPropertyService drpPropertyService;
    private TitanSaoImpl titanSao;

    @Before
    public void setUp() {
	drpPropertyService = EasyMock.createMock(DrpPropertyService.class);
    }

    /**
     * Test of getDomDocument method, of class TitanSaoImpl.
     */
    @Test
    public void testGetDomDocumentByStoreIdAndSearchDescription() throws Exception {
	// Create partial mock
	IMockBuilder<TitanSaoImpl> mockTitanSaoBuilder = createMockBuilder(TitanSaoImpl.class);
	// Add methods to be mocked.
	mockTitanSaoBuilder.addMockedMethods("getRestfulClientResponse", "getRestfulClientResponseByQueryMap",
		"getResponseWithSecurityHeaders");
	titanSao = mockTitanSaoBuilder.createMock();
	logger.debug("TitanSaoImplTest : testGetDomDocumentByStoreIdAndSearchDescription()");
	String storeId = "271";
	String searchCatalogUrl = "store-" + storeId + "/catalog";
	String searchDescription = "Apple%20iPhone";
	ReflectionTestUtils.setField(titanSao, "drpPropertyService", drpPropertyService);

	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_BASE_URI)).andReturn("http://hydra-pt:8080");
	expect(drpPropertyService.getProperty(TITAN_CATEGORY)).andReturn("mobile-phones");
	expect(drpPropertyService.getProperty(TITAN_RESULT_PAGE_SIZE)).andReturn("20");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");

	MultivaluedMap<String, String> queryMap = new MultivaluedMapImpl();
	queryMap.add(TITAN_SEARCH_PREFIX, searchDescription);
	queryMap.add(CATEGORY, "mobile-phones");
	queryMap.add(PAGE, "1");
	queryMap.add(PAGE_SIZE, "20");

	String mockResponse = TestUtil.readFileToString(getClass(), "Titan_GetSearchResults.xml");

	replay(drpPropertyService, titanSao);
	Document result = titanSao.getDomDocument(storeId, searchDescription, "a904002");
	logger.debug("Result XML Document " + getStringFromDoc(result));
	// TODO review the generated test code and remove the default call to
	// fail.

    }

    /**
     * Test of getDomDocument method, of class TitanSaoImpl.
     */
    @Test
    public void testGetDomDocument() throws Exception {

	logger.debug("TitanSaoImplTest : testGetDomDocument()");
	// Create partial mock
	IMockBuilder<TitanSaoImpl> mockTitanSaoBuilder = createMockBuilder(TitanSaoImpl.class);
	// Add methods to be mocked.
	mockTitanSaoBuilder.addMockedMethods("getRestfulClientResponse");
	titanSao = mockTitanSaoBuilder.createMock();
	ReflectionTestUtils.setField(titanSao, "drpPropertyService", drpPropertyService);

	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");

	// ProductDetails_3487784

	replay(drpPropertyService, titanSao);
	Document result = titanSao.getDomDocument(productDetailHref, "a904002");
	assertNotNull(result);

	result = titanSao.getDomDocument(pricingQA1, "a904002");
	assertNotNull(result);

	result = titanSao.getDomDocument(pricingQA2, "a904002");
	assertNotNull(result);
	result = titanSao.getDomDocument(pricingQA3, "a904002");
	assertNotNull(result);
	result = titanSao.getDomDocument(pricingQA4, "a904002");
	assertNotNull(result);
	result = titanSao.getDomDocument(pricingQA5, "a904002");
	assertNotNull(result);
	result = titanSao.getDomDocument(pricingQASummaryHref, "a904002");
	assertNotNull(result);

	// TODO review the generated test code and remove the default call to
	// fail.

    }

    /**
     * Test of createCart method, of class TitanSaoImpl.
     */
    @Ignore
    @Test
    public void testCreateCart() throws Exception {
	logger.debug("TitanSaoImplTest : testCreateCart()");

	// Create partial mock
	IMockBuilder<TitanSaoImpl> mockTitanSaoBuilder = createMockBuilder(TitanSaoImpl.class);
	// Add methods to be mocked.
	mockTitanSaoBuilder.addMockedMethods("postRestfulClientRequest");
	titanSao = mockTitanSaoBuilder.createMock();
	ReflectionTestUtils.setField(titanSao, "drpPropertyService", drpPropertyService);

	expect(drpPropertyService.getProperty(TITAN_MEDIA_TYPE)).andReturn(mediaType);
	expect(drpPropertyService.getProperty("X-Api-Key")).andReturn("DEMOKEY");

	InputStream inputStream = TestUtil.readFileToInputStream(getClass(), "Titan_PricingQA_Summary_3487784.xml");
	Document postMockDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

	String mockResponse = TestUtil.readFileToString(getClass(), "Titan_AddToCart_3487784.xml");
	expect(titanSao.postRestfulClientRequest(addToCartHref, String.class, mediaType, "", postMockDoc)).andReturn(
		mockResponse);

	replay(drpPropertyService, titanSao);

	String httpRef = "http://hydra-pt:8080/store-10/carts";
	Document createdCart = titanSao.getDomDocument(httpRef, postMockDoc, "a904002");
	assertNotNull(createdCart);

    }

    private String getStringFromDoc(Document doc) {
	DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
	LSSerializer lsSerializer = domImplementation.createLSSerializer();
	return lsSerializer.writeToString(doc);
    }
}
