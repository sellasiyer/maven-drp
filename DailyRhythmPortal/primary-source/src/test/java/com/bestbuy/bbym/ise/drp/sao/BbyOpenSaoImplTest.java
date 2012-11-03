package com.bestbuy.bbym.ise.drp.sao;

import static org.easymock.EasyMock.capture;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestOperations;

import com.bestbuy.bbym.ise.domain.Device;
import com.bestbuy.bbym.ise.domain.Sku;
import com.bestbuy.bbym.ise.drp.dao.TestUtil;

/**
 * JUnit test for {@link BbyOpenSaoImpl}.
 */
public class BbyOpenSaoImplTest extends BaseSaoTest {

    private BbyOpenSaoImpl bbyOpenSaoImpl;
    private RestOperations mockRestOperations;

    @Override
    public void setUp() {
	super.setUp();

	mockRestOperations = EasyMock.createMock(RestOperations.class);
	bbyOpenSaoImpl = new BbyOpenSaoImpl();
	ReflectionTestUtils.setField(bbyOpenSaoImpl, "restOperations", mockRestOperations);

	bbyOpenSaoImpl.setDrpPropertiesService(drpPropertyService);
    }

    @Override
    public void testTimeout() {
	// TODO Implement me!
    }

    @Override
    public void assertIbhValuesPopulatedCorrectly() throws Exception {
	// TODO Why don't we have an IBH for BBYOpen service?
    }

    /**
     * Test for {@link BbyOpenSaoImpl#getSKUDetails(String)}.
     */
    @Test
    public void testGetSKUDetails() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "BBYOPEN_SkuDetailsResponse.xml");

	final String url = "someUrl";
	final String skuDetailsSuffix = "/v1/products((upc={upc}|sku={sku})&active=*)?show={show}&apiKey={apiKey}";
	final String apiKey = "someApiKey";

	EasyMock.expect(drpPropertyService.getProperty("BBYOPEN_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("BBYOPEN_API_KEY")).andReturn(apiKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.getForObject(capture(captureUrl), capture(captureResponseType),
			capture(captureUriVariables))).andReturn(response);
	EasyMock.replay(mockRestOperations);

	final String skuUpc = "3815245";

	Device deviceResponse = bbyOpenSaoImpl.getSKUDetails(skuUpc);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + skuDetailsSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (upc) passed in service call", skuUpc, captureUriVariables.getValue().get(
		"upc"));
	assertEquals("Incorrect uriVariable (sku) passed in service call", skuUpc, captureUriVariables.getValue().get(
		"sku"));
	assertEquals("Incorrect uriVariable (show) passed in service call", "name,upc,sku,deviceManufacturer",
		captureUriVariables.getValue().get("show"));
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));

	// Check that correct values were returned from SAO
	assertNotNull("No device returned from service call", deviceResponse);
	assertEquals("Incorrect device SKU returned from service call", skuUpc, deviceResponse.getSku());
	assertEquals("Incorrect device description returned from service call",
		"Apple® - iPhone 4 with 8GB Memory - Black (Verizon Wireless)", deviceResponse.getDescription());
	assertEquals("Incorrect device UPC returned from service call", "885909543267", deviceResponse.getUpc());
	assertEquals("Incorrect device manufacturer returned from service call", "Apple", deviceResponse
		.getManufacturer());
    }

    /**
     * Test for {@link BbyOpenSaoImpl#getSKUAndAccessories(String...)}.
     */
    @Test
    public void testGetSKUAndAccessories() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "BBYOPEN_SkuAndAccessoriesResponse.xml");

	final String url = "someUrl";
	final String skuAndAccessoriesSuffix = "/v1/products(sku in({sku})&active=*)?show={show}&apiKey={apiKey}";
	final String apiKey = "someApiKey";

	EasyMock.expect(drpPropertyService.getProperty("BBYOPEN_URL")).andReturn(url);
	EasyMock.expect(drpPropertyService.getProperty("BBYOPEN_API_KEY")).andReturn(apiKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.getForObject(capture(captureUrl), capture(captureResponseType),
			capture(captureUriVariables))).andReturn(response);
	EasyMock.replay(mockRestOperations);

	final String[] skus = {"6699308" };

	List<Sku> skuResponse = bbyOpenSaoImpl.getSKUAndAccessories(skus);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", url + skuAndAccessoriesSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (sku) passed in service call", skus[0], captureUriVariables.getValue().get(
		"sku"));
	assertEquals("Incorrect uriVariable (show) passed in service call", "name,sku,image,accessories",
		captureUriVariables.getValue().get("show"));
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", apiKey, captureUriVariables.getValue()
		.get("apiKey"));

	// Check that correct values were returned from SAO
	assertNotNull("No skus returned from service call", skuResponse);
	assertEquals("Incorrect number of skus returned from service call", 1, skuResponse.size());
	Sku sku = skuResponse.get(0);
	assertEquals("Incorrect sku sku", skus[0], sku.getSku());
	assertEquals("Incorrect sku name", "Apple® - iPhone® 5 with 16GB Memory Mobile Phone - Black & Slate (AT&T)",
		sku.getName());
	assertEquals("Incorrect number of accessory skus returned from service call", 5, sku.getSkuList().size());
	String[] expectedAccessorySkus = {"8948847", "2908168", "1074217", "1074244", "5619255" };
	List<String> foundAccessorySkus = new ArrayList<String>();
	for(Sku tmpSku: sku.getSkuList()){
	    foundAccessorySkus.add(tmpSku.getSku());
	}
	assertTrue("Missing accessory sku returned from service call", Arrays.asList(expectedAccessorySkus)
		.containsAll(foundAccessorySkus));
    }

    /**
     * Test for {@link BbyOpenSaoImpl#getAccessories(String, String...)}.
     */
    @Test
    public void testGetAccessories() throws Exception {

	// Prepare to record request sent in service call
	Capture<String> captureUrl = new Capture<String>();
	Capture<Class<String>> captureResponseType = new Capture<Class<String>>();
	Capture<Map<String, ?>> captureUriVariables = new Capture<Map<String, ?>>();

	// Load a canned service response
	String response = TestUtil.readFileToString(getClass(), "BBYOPEN_AccessoriesResponse.xml");

	final String bbyOpenUrl = "someUrl";
	final String accessoriesSuffix = "/v1/stores(storeId={storeId})+products(sku in({sku}))?show={show}&apiKey={apiKey}";
	final String bbyOpenApiKey = "someApiKey";

	EasyMock.expect(drpPropertyService.getProperty("BBYOPEN_URL")).andReturn(bbyOpenUrl);
	EasyMock.expect(drpPropertyService.getProperty("BBYOPEN_API_KEY")).andReturn(bbyOpenApiKey);
	EasyMock.replay(drpPropertyService);

	EasyMock.expect(
		mockRestOperations.getForObject(capture(captureUrl), capture(captureResponseType),
			capture(captureUriVariables))).andReturn(response);
	EasyMock.replay(mockRestOperations);

	final String storeId = "someStoreId";
	final String[] skus = {"8948847", "2908168", "1074217", "1074244", "5619255" };

	List<Sku> skuResponse = bbyOpenSaoImpl.getAccessories(storeId, skus);

	// Check that correct values were sent in service call
	assertEquals("Incorrect URL passed in service call", bbyOpenUrl + accessoriesSuffix, captureUrl.getValue());
	assertEquals("Incorrect responseType passed in service call", String.class, captureResponseType.getValue());
	assertEquals("Incorrect uriVariable (sku) passed in service call", StringUtils.join(skus, ','),
		captureUriVariables.getValue().get("sku"));
	assertEquals(
		"Incorrect uriVariable (show) passed in service call",
		"products.sku,products.name,products.regularPrice,products.salePrice,products.thumbnailImage,products.shortDescription,products.longDescription,products.subclassId",
		captureUriVariables.getValue().get("show"));
	assertEquals("Incorrect uriVariable (apiKey) passed in service call", bbyOpenApiKey, captureUriVariables
		.getValue().get("apiKey"));

	// Check that correct values were returned from SAO
	assertNotNull("No skus returned from service call", skuResponse);
	assertEquals("Incorrect number of skus returned from service call", 5, skuResponse.size());
	// Check first accessory
	Sku accessorySku = skuResponse.get(0);
	assertEquals("Incorrect sku returned from service for item " + 1, "8948847", accessorySku.getSku());
	assertEquals("Incorrect name returned from service for item " + 1,
		"Eddie Bauer - Case for Most Mobile Phones - Black", accessorySku.getName());
	assertEquals("Incorrect regularPrice returned from service for item " + 1, new BigDecimal("27.99"),
		accessorySku.getRegularPrice());
	//	  accessory.setImage(image);
	assertEquals("Incorrect description returned from service for item " + 1,
		"Compatible with most iPhones, mobile phones and Blackberrys; vinyl material; expandable sides",
		accessorySku.getDescription());
	assertEquals(
		"Incorrect longDescription returned from service for item " + 1,
		"Keep your mobile phone safe from bumps and scratches with this stylish case. The belt clip on the back provides easy portability.",
		accessorySku.getLongDescription());
	// TODO Add assertions for second accessory
	// TODO Add assertions for third accessory
	// TODO Add assertions for fourth accessory
	// TODO Add assertions for fifth accessory
    }
}
