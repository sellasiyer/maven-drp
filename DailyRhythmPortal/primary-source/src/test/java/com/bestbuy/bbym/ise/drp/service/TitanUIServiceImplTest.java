package com.bestbuy.bbym.ise.drp.service;

import com.bestbuy.bbym.ise.drp.dao.TestUtil;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.sao.TitanSao;
import java.io.InputStream;
import javax.xml.parsers.DocumentBuilderFactory;
import org.easymock.EasyMock;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.w3c.dom.Document;
import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JUnit test for {@link UIServiceImpl}.
 * 
 * @author a904002
 */
@Ignore
public class TitanUIServiceImplTest {

    private static Logger logger = LoggerFactory.getLogger(TitanUIServiceImplTest.class);
    private TitanSao mockTitanSao;
    private TitanUIServiceImpl titanUIService;
    private UIRequest uiRequest;
    private UIReply uiReply;

    /**
     * Test method for testing CatalogSearch Results. Test of processUIRequest
     * method, of class UIServiceImpl.
     */
    @Test
    public void testProcessUIRequest_CatalogSearch() throws Exception {
	titanUIService = new TitanUIServiceImpl();
	initCatalogSearch();

	logger.info("UIServiceImplTest >> processUIRequest : CatalogSearch");

	UIReply uiReply = titanUIService.processUIRequest(uiRequest);
	UIList uiList = (UIList) uiReply.get("catalogList");
	assertTrue(uiList.getRowList().size() == 25);

    }

    private void initCatalogSearch() throws Exception {
	setMockTitanService();
	uiRequest = new UIRequest();
	uiRequest.setName("CatalogSearch");
	UIDataItem uiDataItem = new UIDataItem();
	uiDataItem.setName("searchParams");
	uiDataItem.setStringProp("storeID", "271");
	uiDataItem.setStringProp("searchFilter", "Apple iPhone 4s");
	uiRequest.put("searchParams", uiDataItem);;
	InputStream inputStream = TestUtil.readFileToInputStream(this.getClass(), "Titan_GetSearchResults.xml");
	Document mockDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
	expect(mockTitanSao.getDomDocument("271", "Apple iPhone 4s")).andReturn(mockDocument);
	replay(mockTitanSao);

    }

    /**
     * Test method for testing ProductDetails result.
     *
     */
    @Test
    public void testProcessUIRequest_ProductDetails() throws Exception {

	titanUIService = new TitanUIServiceImpl();
	initProductDetails();

	logger.info("UIServiceImplTest >> processUIRequest : ProductDetails");

	UIReply uiReply = titanUIService.processUIRequest(uiRequest);
	UIDataItem uiData = (UIDataItem) uiReply.get("productSku");
	String resultTitle = uiData.getStringProp("Value", null);
	String expTitle = "3487784";
	assertEquals(expTitle, resultTitle);

    }

    private void initProductDetails() throws Exception {
	setMockTitanService();
	uiRequest = new UIRequest();
	uiRequest.setName("ProductDetails");
	UIDataItem uiDataItem = new UIDataItem();
	uiDataItem.setName("productDetailsLink");
	uiDataItem.setStringProp("url", "http://hydra-pt:8080/store-271/catalog/products/3487784");

	uiRequest.put("productDetailsLink", uiDataItem);;
	InputStream inputStream = TestUtil.readFileToInputStream(this.getClass(), "ProductDetails_3487784.xml");
	Document mockDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
	expect(mockTitanSao.getDomDocument("http://hydra-pt:8080/store-271/catalog/products/3487784", "a904002"))
		.andReturn(mockDocument);
	replay(mockTitanSao);

    }

    /**
     * Test method for testing ProductDetails result.
     *
     */
    @Test
    public void testProcessUIRequest_PricingQA() throws Exception {
	titanUIService = new TitanUIServiceImpl();

	logger.info("UIServiceImplTest >> processUIRequest : PricingQA");

	//      BBI1309086 

	//Check Question 1
	String qaUrl = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784";
	initPricingQA(qaUrl, 1);
	UIReply uiReply = titanUIService.processUIRequest(uiRequest);
	UIDataItem uiData = (UIDataItem) uiReply.get("Name");
	String resultTitle = uiData.getStringProp("Name", null);
	String expTitle = "cellphone-condition1";
	assertEquals(expTitle, resultTitle);

	//Check Question 2
	qaUrl = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect";
	initPricingQA(qaUrl, 2);
	uiReply = titanUIService.processUIRequest(uiRequest);
	uiData = (UIDataItem) uiReply.get("Name");
	resultTitle = uiData.getStringProp("Name", null);
	expTitle = "cell-phones-7";
	assertEquals(expTitle, resultTitle);

	//Check Question 3
	qaUrl = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No";
	initPricingQA(qaUrl, 3);
	uiReply = titanUIService.processUIRequest(uiRequest);
	uiData = (UIDataItem) uiReply.get("Name");
	resultTitle = uiData.getStringProp("Name", null);
	expTitle = "cell-phones-8";
	assertEquals(expTitle, resultTitle);

	//Check Question 4
	qaUrl = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No&cell-phones-8=Yes";
	initPricingQA(qaUrl, 4);
	uiReply = titanUIService.processUIRequest(uiRequest);
	uiData = (UIDataItem) uiReply.get("Name");
	resultTitle = uiData.getStringProp("Name", null);
	expTitle = "cell-phones-9";
	assertEquals(expTitle, resultTitle);

	//Check Question 5
	qaUrl = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No&cell-phones-8=Yes&cell-phones-9=Yes";
	initPricingQA(qaUrl, 5);
	uiReply = titanUIService.processUIRequest(uiRequest);
	uiData = (UIDataItem) uiReply.get("Name");
	resultTitle = uiData.getStringProp("Name", null);
	expTitle = "cell-phones-10";
	assertEquals(expTitle, resultTitle);

	//http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No&cell-phones-8=Yes&cell-phones-9=Yes&cell-phones-10=Yes

    }

    @Test
    public void testProcessUIRequest_PricingQASummary() throws Exception {
	titanUIService = new TitanUIServiceImpl();

	logger.info("UIServiceImplTest >> processUIRequest : PricingQASummary");

	//Check Question 1
	String qaUrl = "http://hydra-pt:8080/store-271/carts/new?StoreId=271&CartId=&Sku=3487784&cellphone-condition1=Perfect&cell-phones-7=No&cell-phones-8=Yes&cell-phones-9=Yes&cell-phones-10=Yes";
	initPricingQA(qaUrl, 6);
	uiReply = titanUIService.processUIRequest(uiRequest);
	UIDataItem summaryDI = (UIDataItem) uiReply.get("Summary");

	assertNotNull(summaryDI.getStringProp("Title", null));
	assertEquals("241.5", summaryDI.getStringProp("BuyPrice", null));

	UIDataList dataList = (UIDataList) uiReply.get("QASummaryList");

	UIDataItem qName = (UIDataItem) dataList.get(0);
	assertEquals("cellphone-condition1", qName.getStringProp("Name", null));
	assertEquals("Perfect", qName.getStringProp("Value", null));

	qName = (UIDataItem) dataList.get(1);
	assertEquals("cell-phones-7", qName.getStringProp("Name", null));
	assertEquals("No", qName.getStringProp("Value", null));

	qName = (UIDataItem) dataList.get(2);
	assertEquals("cell-phones-8", qName.getStringProp("Name", null));
	assertEquals("Yes", qName.getStringProp("Value", null));

	qName = (UIDataItem) dataList.get(3);
	assertEquals("cell-phones-9", qName.getStringProp("Name", null));
	assertEquals("Yes", qName.getStringProp("Value", null));

	qName = (UIDataItem) dataList.get(4);
	assertEquals("cell-phones-10", qName.getStringProp("Name", null));
	assertEquals("Yes", qName.getStringProp("Value", null));

    }

    private void initPricingQA(String href, int qId) throws Exception {
	setMockTitanService();
	uiRequest = new UIRequest();
	uiRequest.setName("PricingQA");
	UIDataItem uiDataItem = new UIDataItem();
	uiDataItem.setName("pricingQALink");
	uiDataItem.setStringProp("pricingQAHref", href);
	uiDataItem.setStringProp("userId", "a904002");

	uiRequest.put("pricingQALink", uiDataItem);;

	InputStream inputStream = null;
	if (qId < 6){
	    inputStream = TestUtil.readFileToInputStream(this.getClass(), "PricingQA_" + qId + "_3487784.xml");
	}else{
	    inputStream = TestUtil.readFileToInputStream(this.getClass(), "PricingQA_Summary_3487784.xml");
	}
	Document mockDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
	expect(mockTitanSao.getDomDocument(href, "a904002")).andReturn(mockDocument);
	replay(mockTitanSao);
    }

    @Test
    public void testProcessUIRequest_CreateCart() throws Exception {
	titanUIService = new TitanUIServiceImpl();
	setMockTitanService();
	logger.info("UIServiceImplTest >> processUIRequest : CreateCart");
	uiRequest = new UIRequest();
	uiRequest.setName("CreateCart");
	String createCartUrl = "http://hydra-pt:8080/store-271/carts";
	UIDataItem reqItem = new UIDataItem();

	reqItem.setName("addToCart");
	reqItem.setStringProp("href", createCartUrl);
	reqItem.setStringProp("userId", "a904002");
	uiRequest.put(reqItem.getName(), reqItem);

	InputStream inputStream = TestUtil.readFileToInputStream(this.getClass(), "PricingQA_Summary_3487784.xml");
	Document summaryDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
	reqItem = new UIDataItem();
	reqItem.setName("domDocument");
	reqItem.setObjProp("domDocument", summaryDocument);
	uiRequest.put(reqItem.getName(), reqItem);

	inputStream = TestUtil.readFileToInputStream(this.getClass(), "AddToCart_3487784.xml");
	Document replyDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

	expect(mockTitanSao.getDomDocument(createCartUrl, summaryDocument, "a904002")).andReturn(replyDocument);
	replay(mockTitanSao);

	uiReply = titanUIService.processUIRequest(uiRequest);

	UIDataItem summaryDI = (UIDataItem) uiReply.get("nextDataItem");

	assertEquals("19109", summaryDI.getStringProp("CartId", null));
	assertEquals("http://hydra-pt:8080/store-10/transactions/product-info/new?CartItemId=20511&CartId=19109",
		summaryDI.getStringProp("NextUrl", null));

    }

    @Test
    public void testProcessUIRequest_EditProductInfo() throws Exception {
	titanUIService = new TitanUIServiceImpl();
	setMockTitanService();
	logger.info("UIServiceImplTest >> processUIRequest : EditProductInfo");
	uiRequest = new UIRequest();
	uiRequest.setName("EditProductInfo");
	String createCartUrl = "http://localhost:2279/store-50/carts/10/items/12/product";
	UIDataItem reqItem = new UIDataItem();

	reqItem.setName("editProductHref");
	reqItem.setStringProp("href", createCartUrl);
	reqItem.setStringProp("storeId", "50");
	reqItem.setStringProp("userId", "a904002");
	uiRequest.put(reqItem.getName(), reqItem);

	InputStream inputStream = TestUtil.readFileToInputStream(this.getClass(), "EditProductInfo.xml");
	Document replyDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

	expect(mockTitanSao.getDomDocument(createCartUrl, "a904002")).andReturn(replyDocument);
	replay(mockTitanSao);

	uiReply = titanUIService.processUIRequest(uiRequest);

	UIDataItem summaryDI = (UIDataItem) uiReply.get("ProductInfo");
	//
	//assertEquals("http://localhost:2279/store-50/carts/10/items/12/product", summaryDI.getStringProp("href", null));
	assertEquals("MC918LL/A", summaryDI.getStringProp("modelNumber", null));
	assertEquals("Apple", summaryDI.getStringProp("manufacturerName", null));
	UIRequest uiRequest = (UIRequest) uiReply.get("EditProductAnswers");

    }

    @Test
    public void testProcessUIRequest_EditProductAnswers() throws Exception {
	titanUIService = new TitanUIServiceImpl();
	setMockTitanService();
	String createCartUrl = "http://localhost:2279/store-50/carts/10/items/12/product";
	logger.info("UIServiceImplTest >> processUIRequest : CreateCart");
	uiRequest = new UIRequest();
	UIDataItem dataItem = null;

	uiRequest.setName("EditProductAnswers");
	dataItem = new UIDataItem();
	dataItem.setName("EditProductAnswers");
	dataItem.setStringProp("userId", "a904002");
	dataItem.setStringProp("modelNumber", "MC918LL/A");
	dataItem.setStringProp("manufacturerName", "Apple");
	dataItem.setStringProp("receipt", Boolean.toString(true));
	dataItem.setStringProp("receiptNumber", "11111111111");
	dataItem.setStringProp("href", "http://localhost:2279/store-50/carts/10/items/12/product");

	InputStream inputStream = TestUtil.readFileToInputStream(this.getClass(), "EditProductInfo.xml");
	Document replyDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);
	inputStream = TestUtil.readFileToInputStream(this.getClass(), "ProductQA.xml");
	Document exDoc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputStream);

	expect(mockTitanSao.getDomDocument(createCartUrl, "a904002")).andReturn(replyDocument);
	expect(mockTitanSao.getDomDocument(createCartUrl, replyDocument, "a904002")).andReturn(exDoc);
	replay(mockTitanSao);
	dataItem.setObjProp("domDoc", replyDocument);

	uiRequest.put(dataItem.getName(), dataItem);
	uiReply = titanUIService.processUIRequest(uiRequest);

	UIRequest nextUIRequest = (UIRequest) uiReply.get("nextUIRequest");
	/**
	assertEquals("http://localhost:2279/store-50/carts/10/items/12/product", summaryDI.getStringProp("href", null));
	assertEquals("MC918LL/A", summaryDI.getStringProp("modelNumber", null));
	assertEquals("Apple", summaryDI.getStringProp("manufacturerName", null));
	 */
    }

    private void setMockTitanService() {
	mockTitanSao = EasyMock.createMock(TitanSao.class);
	ReflectionTestUtils.setField(titanUIService, "titanSao", mockTitanSao);
    }
}
