package com.bestbuy.bbym.ise.drp.service;

import java.io.IOException;
import java.io.StringWriter;

import javax.ws.rs.HttpMethod;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bestbuy.bbym.ise.drp.domain.ui.UIData;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIElement;
import com.bestbuy.bbym.ise.drp.domain.ui.UIImage;
import com.bestbuy.bbym.ise.drp.domain.ui.UILabel;
import com.bestbuy.bbym.ise.drp.domain.ui.UILink;
import com.bestbuy.bbym.ise.drp.domain.ui.UIList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIListRow;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.drp.sao.TitanSao;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * UIServiceImpl Responsible for generating the UIReply/UIResponse based on the
 * 
 * TODO: Phase 2 UIReply/UIResponse Implement database driven Xpath query(To
 * generate UIReply/UIRequest) Implement database driven metadata
 * 
 * @author a904002
 */
@Service("uiService")
public class TitanUIServiceImpl implements UIService {

    @Autowired
    private TitanSao titanSao;
    private static Logger logger = LoggerFactory.getLogger(TitanUIServiceImpl.class);

    @Override
    public UIReply processUIRequest(UIRequest uiRequest) throws ServiceException, IseBusinessException {
	String requestName = uiRequest.getName();
	logger.debug(">> Processing UI Request () for " + requestName);
	logger.debug(">> uirequest " + uiRequest);
	UIReply uiReply = null;
	if ("CatalogSearch".equals(requestName)){
	    uiReply = getSearchResultsUIReply(uiRequest);
	}else if ("ProductDetails".equals(uiRequest.getName())){
	    uiReply = getProductDetailsUIReply(uiRequest);
	}else if ("PricingQA".equals(uiRequest.getName())){
	    uiReply = getPricingQAUIReply(uiRequest);
	}else if ("CreateCart".equals(uiRequest.getName())){
	    uiReply = getCreatCartUIReply(uiRequest);
	}else if ("EditProductAnswers".equals(requestName)){
	    uiReply = getEditProductAnswersUIReply(uiRequest);
	}else if ("EditCustomerInfo".equals(requestName)){
	    uiReply = getEditCustomerInfoUIReply(uiRequest);
	}else if ("EditCustomerAnswers".equals(requestName)){
	    uiReply = getEditCustomerAnswersUIReply(uiRequest);
	}else if ("LoadGiftCard".equals(requestName)){
	    uiReply = getLoadGiftCardUIReply(uiRequest);
	}else if ("SubmitGiftCard".equals(requestName)){
	    uiReply = getSubmitGiftCardUIReply(uiRequest);
	}else{
	    throw new ServiceException(IseExceptionCodeEnum.BusinessException, "Cannot handle UIRequest named "
		    + requestName);
	}
	logger.info(">> Returning uiReply() for " + requestName);
	logger.info(">> uireply " + uiReply);
	return uiReply;
    }

    private UIReply getSearchResultsUIReply(UIRequest uiRequest) throws ServiceException, DOMException,
	    IseBusinessException {
	UIDataItem searchParams = null;
	UIElement uiElement = uiRequest.get("searchParams");
	if (uiElement != null && uiElement.isData()){
	    UIData uiData = (UIData) uiElement;
	    if (uiData.getType() == UIData.Type.ITEM){
		searchParams = (UIDataItem) uiData;
	    }
	}
	if (searchParams == null){
	    throw new ServiceException(IseExceptionCodeEnum.InvalidState,
		    "Missing or invalid UIDataItem named 'searchParams'");
	}
	String userId = searchParams.getStringProp("userId", null);
	Document doc = titanSao.getDomDocument(searchParams.getStringProp("storeID", null), searchParams.getStringProp(
		"searchFilter", null), userId);
	logger.info("Return from titanSao >>> : " + getXMLString(doc).toString());
	Element topElem = doc.getDocumentElement();
	UIReply uiReply = new UIReply();
	uiReply.setName(topElem.getNodeName());

	UIList catalogList = new UIList();
	catalogList.setName("catalogList");
	uiReply.put(catalogList.getName(), catalogList);

	NodeList itemsNodeList = doc.getElementsByTagName("Items");
	if (itemsNodeList == null){
	    return uiReply;
	}
	Node itemsNode = itemsNodeList.item(0);
	if (itemsNode == null){
	    return uiReply;
	}
	XPath xPath = XPathFactory.newInstance().newXPath();
	NodeList itemResourceNodeList = null;
	try{
	    itemResourceNodeList = (NodeList) xPath.evaluate("//CatalogItemResource", doc, XPathConstants.NODESET);
	}catch(XPathExpressionException e){
	    logger.error(e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.NOTFOUND, "Unable to retrieve the device list.");
	}
	if (itemResourceNodeList == null){
	    return uiReply;
	}

	long rowCount = 0L;
	for(int i = 0; i < itemResourceNodeList.getLength(); i++){
	    UIListRow catalogRow = generateCatalogListRow(itemResourceNodeList.item(i));
	    if (catalogRow != null){
		rowCount++;
		catalogRow.setId(new Long(rowCount));
		catalogList.addRow(catalogRow);
	    }
	}
	return uiReply;
    }

    private UIListRow generateCatalogListRow(Node itemResourceNode) {
	if (itemResourceNode == null){
	    return null;
	}
	NodeList itemResourceChildNodeList = itemResourceNode.getChildNodes();

	UILabel nameLabel = null;
	UIImage image = null;
	UILink link = null;

	for(int i = 0; i < itemResourceChildNodeList.getLength(); i++){
	    Node itemResourceChildNode = itemResourceChildNodeList.item(i);
	    if ("Name".equalsIgnoreCase(itemResourceChildNode.getNodeName())){
		nameLabel = new UILabel();
		nameLabel.setName("name");
		nameLabel.setValue(itemResourceChildNode.getTextContent());

	    }else if (itemResourceChildNode.getNodeName().endsWith(":link")){
		String itemName = null;
		NamedNodeMap attrMap = itemResourceChildNode.getAttributes();
		for(int j = 0; j < attrMap.getLength(); j++){
		    Node attr = attrMap.item(j);
		    if ("rel".equalsIgnoreCase(attr.getNodeName()) && "image".equalsIgnoreCase(attr.getNodeValue())){
			itemName = "image";
			break;
		    }else if ("rel".equalsIgnoreCase(attr.getNodeName())
			    && "self".equalsIgnoreCase(attr.getNodeValue())){
			itemName = "link";
			break;
		    }
		}
		if ("image".equals(itemName)){
		    image = new UIImage();
		    image.setName("image");
		    for(int j = 0; j < attrMap.getLength(); j++){
			Node attr = attrMap.item(j);
			if ("title".equalsIgnoreCase(attr.getNodeName())){
			    image.setTitle(attr.getNodeValue());
			}else if ("href".equalsIgnoreCase(attr.getNodeName())){
			    image.setUrl(attr.getNodeValue());
			}
		    }

		}else if ("link".equals(itemName)){
		    link = new UILink();
		    link.setName("link");
		    for(int j = 0; j < attrMap.getLength(); j++){
			Node attr = attrMap.item(j);
			if ("href".equalsIgnoreCase(attr.getNodeName())){
			    UIRequest uiRequest = new UIRequest();
			    uiRequest.setName("ProductDetails");
			    UIDataItem dataItem = new UIDataItem();
			    dataItem.setName("productDetailsLink");
			    dataItem.setStringProp("url", attr.getNodeValue());
			    uiRequest.put(dataItem.getName(), dataItem);
			    link.setUiRequest(uiRequest);
			}
		    }
		}
	    }
	}
	if (nameLabel == null || link == null){
	    return null;
	}
	UIListRow catalogRow = new UIListRow();
	catalogRow.addColumn(link);
	catalogRow.addColumn(image);
	catalogRow.addColumn(nameLabel);
	return catalogRow;
    }

    private UIReply getProductDetailsUIReply(UIRequest uiRequest) throws ServiceException, DOMException,
	    IseBusinessException {
	UIDataItem productDetailsLink = null;
	UIElement uiElement = uiRequest.get("productDetailsLink");
	if (uiElement != null && uiElement.isData()){
	    UIData uiData = (UIData) uiElement;
	    if (uiData.getType() == UIData.Type.ITEM){
		productDetailsLink = (UIDataItem) uiData;
	    }
	}
	if (productDetailsLink == null){
	    throw new ServiceException(IseExceptionCodeEnum.InvalidState,
		    "Missing or invalid UIDataItem named 'productDetailsLink'");
	}
	String userId = productDetailsLink.getStringProp("userId", null);

	Document doc = titanSao.getDomDocument(productDetailsLink.getStringProp("url", null), userId);

	Element topElem = doc.getDocumentElement();
	UIReply uiReply = new UIReply();
	uiReply.setName(topElem.getNodeName());
	uiReply.setGotoPageClass("PricingQA");

	XPath xPath = XPathFactory.newInstance().newXPath();

	try{
	    String s = xPath.evaluate("//title/text()", doc);
	    UIDataItem item = new UIDataItem();
	    item.setName("Title");
	    item.setStringProp("title", s);
	    uiReply.put(item.getName(), item);

	    NodeList linkNodeList = (NodeList) xPath.evaluate("//ProductDetailsResource/link", doc,
		    XPathConstants.NODESET);
	    UIRequest nextUIRequest = new UIRequest();

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("new-cart-item")){

		    Node newCartNode = linkNodeList.item(i);
		    item = new UIDataItem();
		    item.setName("newCartItem");
		    item
			    .setStringProp("pricingQAHref", newCartNode.getAttributes().getNamedItem("href")
				    .getNodeValue());

		    nextUIRequest.setName("PricingQA");
		    nextUIRequest.put("pricingQALink", item);
		    uiReply.put(item.getName(), item);
		    uiReply.put(nextUIRequest.getName(), nextUIRequest);

		}
	    }

	    s = xPath.evaluate("//link[@rel='image']/@href", doc);
	    item = new UIDataItem();
	    item.setName("image");
	    item.setStringProp("href", s);
	    uiReply.put(item.getName(), item);

	    s = xPath.evaluate("//link[@rel='large-image']/@href", doc);
	    item = new UIDataItem();
	    item.setName("largeImage");
	    item.setStringProp("href", s);
	    uiReply.put(item.getName(), item);

	    s = xPath.evaluate("/ProductDetailsResource/Name/text()", doc);
	    item = new UIDataItem();
	    item.setName("productName");
	    item.setStringProp("Value", s);
	    uiReply.put(item.getName(), item);

	    s = xPath.evaluate("/ProductDetailsResource/Sku/text()", doc);
	    item = new UIDataItem();
	    item.setName("productSku");
	    item.setStringProp("Value", s);
	    uiReply.put(item.getName(), item);

	    s = xPath.evaluate("/ProductDetailsResource/ModelNumber/text()", doc);
	    item = new UIDataItem();
	    item.setName("modelNumber");
	    item.setStringProp("Value", s);
	    uiReply.put(item.getName(), item);

	    s = xPath.evaluate("/ProductDetailsResource/Description/text()", doc);
	    item = new UIDataItem();
	    item.setName("productDescription");
	    item.setStringProp("Value", s);
	    uiReply.put(item.getName(), item);

	    s = xPath.evaluate("/ProductDetailsResource/Estimate/BuyPrice/text()", doc);
	    item = new UIDataItem();
	    item.setName("estimatedBuyPrice");
	    item.setStringProp("buyPrice", s);
	    uiReply.put(item.getName(), item);

	}catch(XPathExpressionException e){
	    logger.error("Error in XPath expression", e);
	}

	NodeList s = null;
	try{
	    s = (NodeList) xPath.evaluate("//IncludedItems/IncludedItem", doc, XPathConstants.NODESET);
	}catch(XPathExpressionException e){
	    logger.error("Error in XPath expression", e);
	}

	UIDataList includedItemList = new UIDataList();
	includedItemList.setName("includedItems");

	for(int i = 0; i < s.getLength(); i++){

	    NodeList n = s.item(i).getChildNodes();
	    UIDataItem item = new UIDataItem();
	    item.setName("includedItem");
	    for(int j = 0; j < n.getLength(); j++){
		item.setStringProp("includedItem", n.item(j).getTextContent());
	    }
	    includedItemList.add(item);

	}
	uiReply.put(includedItemList.getName(), includedItemList);

	// Build next uiRequest

	return uiReply;
    }

    private UIReply getPricingQAUIReply(UIRequest uiRequest) throws ServiceException, DOMException,
	    IseBusinessException {
	UIDataItem pricingQALink = null;

	UIElement uiElement = uiRequest.get("pricingQALink");
	if (uiElement != null && uiElement.isData()){
	    UIData uiData = (UIData) uiElement;
	    if (uiData.getType() == UIData.Type.ITEM){
		pricingQALink = (UIDataItem) uiData;
	    }
	}
	if (pricingQALink == null){
	    throw new ServiceException(IseExceptionCodeEnum.InvalidState,
		    "Missing or invalid UIDataItem named 'pricingQALink'");
	}
	String userId = pricingQALink.getStringProp("userId", null);
	Document doc = titanSao.getDomDocument(pricingQALink.getStringProp("pricingQAHref", null), userId);
	UIDataItem domData = new UIDataItem();
	domData.setName("domDocument");
	domData.setObjProp("domDocument", doc);
	Element topElem = doc.getDocumentElement();
	UIReply uiReply = new UIReply();
	uiReply.setName(topElem.getNodeName());

	XPath xPath = XPathFactory.newInstance().newXPath();

	try{

	    String numberOfQuestions = xPath.evaluate("count(//BuyPriceQuestion)", doc);
	    int questionNumber = Integer.parseInt(numberOfQuestions)
		    - Integer.parseInt(xPath.evaluate("count(//BuyPriceQuestion/Value[not(text())])", doc)) + 1;
	    String stepValue = "Step " + questionNumber + " of " + numberOfQuestions;

	    NodeList questionList = (NodeList) xPath.evaluate(
		    "/NewCartItemResource/Estimate/Questions/BuyPriceQuestion", doc, XPathConstants.NODESET);

	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("TotalQuestions");
	    dataItem.setStringProp("TotalQuestions", Integer.toString(questionList.getLength()));

	    uiReply.put(dataItem.getName(), dataItem);

	    dataItem = new UIDataItem();
	    dataItem.setName("Steps");
	    dataItem.setStringProp("Steps", stepValue);

	    uiReply.put(dataItem.getName(), dataItem);

	    Node buyPrice = (Node) xPath.evaluate("/NewCartItemResource/Estimate", doc, XPathConstants.NODE);
	    dataItem = new UIDataItem();
	    dataItem.setName("BuyPrice");
	    dataItem.setStringProp("BuyPrice", xPath.evaluate("BuyPrice/text()", buyPrice));

	    uiReply.put(dataItem.getName(), dataItem);

	    Node buyPriceQuestion = (Node) xPath.evaluate(
		    "/NewCartItemResource/Estimate/Questions/BuyPriceQuestion[1]", doc, XPathConstants.NODE);

	    dataItem = new UIDataItem();
	    dataItem.setName("Name");
	    dataItem.setStringProp("Name", xPath.evaluate("Name/text()", buyPriceQuestion));

	    uiReply.put(dataItem.getName(), dataItem);

	    dataItem = new UIDataItem();
	    dataItem.setName("Text");
	    dataItem.setStringProp("Text", xPath.evaluate("Text/text()", buyPriceQuestion));
	    uiReply.put(dataItem.getName(), dataItem);

	    dataItem = new UIDataItem();
	    dataItem.setName("Justification");
	    dataItem.setStringProp("Justification", xPath.evaluate("Justification/text()", buyPriceQuestion));
	    uiReply.put(dataItem.getName(), dataItem);

	    dataItem = new UIDataItem();
	    dataItem.setName("Value");
	    dataItem.setStringProp("Value", xPath.evaluate("Value/text()", buyPriceQuestion));
	    uiReply.put(dataItem.getName(), dataItem);

	    NodeList optNodeList = (NodeList) xPath.evaluate("Options/BuyPriceQuestionOption", buyPriceQuestion,
		    XPathConstants.NODESET);
	    if (optNodeList.getLength() > 0){
		UIDataList optionsList = new UIDataList();
		optionsList.setName("options");
		for(int i = 0; i < optNodeList.getLength(); i++){
		    String optionValue = xPath.evaluate("Value/text()", optNodeList.item(i));
		    String optionDescription = xPath.evaluate("Description/text()", optNodeList.item(i));
		    String optionAdjustment = xPath.evaluate("AdjustmentDisplayText/text()", optNodeList.item(i));

		    UIDataItem optItem = new UIDataItem();
		    optItem.setName(optionValue);
		    optItem.setStringProp("Value", optionValue);
		    optItem.setStringProp("Description", optionDescription);
		    optItem.setStringProp("AdjustmentDisplayText", optionAdjustment);
		    optionsList.add(optItem);
		}
		uiReply.put(optionsList.getName(), optionsList);
	    }

	    Node value = (Node) xPath.evaluate("Value", buyPriceQuestion, XPathConstants.NODE);
	    UIRequest nextUiRequest = new UIRequest();
	    if (value.hasAttributes()){

		uiReply.setGotoPageClass("PricingQA");
		nextUiRequest.setName("PricingQA");
		UIDataItem reqItem = new UIDataItem();
		reqItem.setStringProp("pricingQAHref", ((UIDataItem) uiRequest.get("pricingQALink")).getStringProp(
			"pricingQAHref", null));
		reqItem.setName("pricingQALink");
		nextUiRequest.put("pricingQALink", reqItem);

		uiReply.put("nextUIRequest", nextUiRequest);
	    }else{
		uiReply = new UIReply();
		uiReply.setName(topElem.getNodeName());
		uiReply.setGotoPageClass("PricingQASummary");
		nextUiRequest.setName("CreateCart");
		UIDataItem reqItem = new UIDataItem();

		NodeList linkNodeList = (NodeList) xPath.evaluate("//NewCartItemResource/link", doc,
			XPathConstants.NODESET);
		Node newCartNode = null;
		logger.debug("NodeList.length=" + linkNodeList.getLength());
		for(int i = 0; i < linkNodeList.getLength(); i++){

		    if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("create")){

			newCartNode = linkNodeList.item(i);
			reqItem = new UIDataItem();
			reqItem.setName("addToCart");
			reqItem.setStringProp("href", newCartNode.getAttributes().getNamedItem("href").getNodeValue());
			// itemList.add(item);
		    }
		}

		dataItem = new UIDataItem();
		dataItem.setName("Summary");
		dataItem.setStringProp("Title", xPath.evaluate("/NewCartItemResource/Name/text()", doc));
		dataItem.setStringProp("BuyPrice", xPath.evaluate("BuyPrice/text()", buyPrice));

		uiReply.put(dataItem.getName(), dataItem);

		linkNodeList = (NodeList) xPath.evaluate("/NewCartItemResource/Estimate/Questions/BuyPriceQuestion",
			doc, XPathConstants.NODESET);
		UIDataList uiDataList = new UIDataList();
		uiDataList.setName("QASummaryList");
		for(int i = 0; i < linkNodeList.getLength(); i++){
		    buyPriceQuestion = linkNodeList.item(i);
		    dataItem = new UIDataItem();
		    dataItem.setName("Name");
		    dataItem.setStringProp("Name", xPath.evaluate("Name/text()", buyPriceQuestion));
		    dataItem.setStringProp("Text", xPath.evaluate("Text/text()", buyPriceQuestion));
		    dataItem.setStringProp("Value", xPath.evaluate("Value/text()", buyPriceQuestion));

		    uiDataList.add(dataItem);
		}
		uiReply.put(uiDataList.getName(), uiDataList);

		nextUiRequest.put(domData.getName(), domData);

		nextUiRequest.put("addToCart", reqItem);

		uiReply.put("nextUIRequest", nextUiRequest);

	    }

	}catch(XPathExpressionException ex){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}

	return uiReply;
    }

    private UIReply getCreatCartUIReply(UIRequest uiRequest) throws ServiceException, DOMException,
	    IseBusinessException {

	UIDataItem nextDataItem = new UIDataItem();
	logger.info("UIRequest Add To Cart : >> " + uiRequest.toString());
	UIDataItem addToCart = (UIDataItem) uiRequest.get("addToCart");

	UIDataItem domDataItem = (UIDataItem) uiRequest.get("domDocument");

	String createCartHttpRef = addToCart.getStringProp("href", null);
	Document doc = (Document) domDataItem.getObjProp("domDocument", null);
	UIReply uiReply = new UIReply();
	uiReply.setName("AddToCart");
	String userId = addToCart.getStringProp("userId", null);

	try{
	    Document result = titanSao.getDomDocument(createCartHttpRef, doc, HttpMethod.POST, userId, null);

	    XPath xPath = XPathFactory.newInstance().newXPath();
	    String nextUrl = xPath.evaluate("//link[@rel='self']/@href", result);
	    String cartId = xPath.evaluate("//CartId/text()", result);
	    nextDataItem.setStringProp("CartId", cartId);
	    nextDataItem.setStringProp("NextUrl", nextUrl);
	    nextDataItem.setName("nextDataItem");
	    uiReply.put("nextDataItem", nextDataItem);
	    uiReply.setGotoPageClass("Success");
	}catch(XPathExpressionException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}catch(IseBusinessException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(ServiceException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	logger.info("Add to Cart UIReply : >>> " + uiReply.toString());
	return uiReply;
    }

    private UIReply getEditProductAnswersUIReply(UIRequest uiRequest) throws ServiceException {
	UIReply uiReply = new UIReply();
	uiReply.setGotoPageClass("EditProductAnswersPage");
	uiReply.setName("EditProductAnswers");
	UIRequest nextUIRequest = new UIRequest();
	nextUIRequest.setName("EditCustomerInfo");
	try{

	    UIDataItem requestDataItem = (UIDataItem) uiRequest.get("nextUrl");
	    String href = requestDataItem.getStringProp("href", null);
	    String promoCode = requestDataItem.getStringProp("promoCode", null);
	    String userId = requestDataItem.getStringProp("userId", null);

	    Document t = titanSao.getDomDocument(href, userId);
	    String promoCodeErroMessage = null;
	    XPath xPath = XPathFactory.newInstance().newXPath();

	    String defaultSelection = "//PromotionItem/Source/Id[(text()='mobile')]/../PromotionCode[text()='"
		    + promoCode + "']/../../IsSelected/text()";
	    String s = null;
	    String appliedPromo = xPath.evaluate(defaultSelection, t);

	    if (!Boolean.parseBoolean(appliedPromo)){

		s = "//PromotionItem/Source/Id[(text()='mobile')]/../PromotionCode[text()='" + promoCode
			+ "']/../../link/@href";

		s = xPath.evaluate(s, t);
		if (StringUtils.isNotBlank(promoCode) && StringUtils.isNotBlank(s)){
		    t = titanSao.getDomDocument(s, userId);

		    NodeList n = (NodeList) xPath.evaluate("//link", t, XPathConstants.NODESET);

		    for(int i = 0; i < n.getLength(); i++){
			Node nd = n.item(i);
			if (nd.getAttributes().getNamedItem("rel").getNodeValue().endsWith("apply-selection")){
			    s = nd.getAttributes().getNamedItem("href").getNodeValue();
			    break;
			}
		    }

		    t = titanSao.getDomDocument(s, t, userId);

		    //		xPath = XPathFactory.newInstance().newXPath();
		    //		s = xPath.evaluate("//link[@rel='next']/@href", t);
		    //
		    //		t = titanSao.getDomDocument(s, userId);

		}else if (StringUtils.isNotBlank(promoCode)){
		    logger.info("Unable to apply the PROMOCODE : " + promoCode);
		    promoCodeErroMessage = "Unable to apply the PROMOCODE : " + promoCode;
		    UIDataItem promoError = new UIDataItem();
		    promoError.setName("promoError");
		    promoError.setStringProp("error", promoCodeErroMessage);
		    promoError.setStringProp("promoCode", promoCode);
		    uiReply.put(promoError.getName(), promoError);
		}
	    }

	    xPath = XPathFactory.newInstance().newXPath();
	    s = xPath.evaluate("//link[@rel='next']/@href", t);
	    t = titanSao.getDomDocument(s, userId);

	    Document doc = t;

	    UIDataList uiQuestionsList = new UIDataList();
	    uiQuestionsList.setName("Questions");
	    NodeList questionsNodeList = (NodeList) xPath.evaluate(
		    "//EditProductAnswersForm/Questions/ProductQuestion", doc, XPathConstants.NODESET);
	    for(int i = 0; i < questionsNodeList.getLength(); i++){

		UIDataItem dataItem = new UIDataItem();
		Node qNode = questionsNodeList.item(i);
		String qName = xPath.evaluate("Name/text()", qNode);
		String qText = xPath.evaluate("Text/text()", qNode);
		String qValue = xPath.evaluate("Value/text()", qNode);
		dataItem.setName(qName);
		dataItem.setStringProp("Name", qName);
		dataItem.setStringProp("Text", qText);
		dataItem.setStringProp("Value", qValue);

		NodeList optionsList = (NodeList) xPath.evaluate("Options/ProductQuestionOption", qNode,
			XPathConstants.NODESET);
		if (optionsList.getLength() > 0){

		    UIDataList uiOptionsList = new UIDataList();
		    for(int j = 0; j < optionsList.getLength(); j++){
			UIDataItem optItem = new UIDataItem();
			String desc = xPath.evaluate("Description/text()", optionsList.item(j));
			String value = xPath.evaluate("Value/text()", optionsList.item(j));
			optItem.setStringProp("Name", desc);
			optItem.setStringProp("Description", desc);
			optItem.setStringProp("Value", value);
			uiOptionsList.add(optItem);
		    }
		    dataItem.setObjProp("options", uiOptionsList);
		}

		uiQuestionsList.add(dataItem);
	    }
	    nextUIRequest.put(uiQuestionsList.getName(), uiQuestionsList);
	    uiReply.put(uiQuestionsList.getName(), uiQuestionsList);

	    String nextUrl = null;
	    NodeList linkNodeList = (NodeList) xPath.evaluate("//EditProductAnswersForm/link", doc,
		    XPathConstants.NODESET);

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("update")){

		    nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		}

	    }
	    String title = xPath.evaluate("//EditProductAnswersForm/title/text()", doc);
	    String productName = xPath.evaluate("//EditProductAnswersForm/Name/text()", doc);
	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("EditProductAnswers");
	    dataItem.setStringProp("href", nextUrl);
	    dataItem.setStringProp("title", title);
	    dataItem.setStringProp("name", productName);
	    dataItem.setObjProp("domDoc", doc);
	    nextUIRequest.put(dataItem.getName(), dataItem);
	    uiReply.put(nextUIRequest.getName(), nextUIRequest);
	}catch(XPathExpressionException ex){
	    logger.error("Unable to get the reply from titan");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}catch(Exception ex){
	    logger.error("Unable to create Cart for the device", ex);
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}

	return uiReply;
    }

    private UIReply getEditCustomerInfoUIReply(UIRequest uiRequest) throws ServiceException {
	UIReply uiReply = new UIReply();
	try{
	    uiReply.setGotoPageClass("EditCustomerAnswersPage");
	    uiReply.setName("EditCustomerInfo");
	    UIDataItem requestDataItem = (UIDataItem) uiRequest.get("EditProductAnswers");
	    String href = requestDataItem.getStringProp("href", null);

	    String userId = requestDataItem.getStringProp("userId", null);

	    Document t = (Document) requestDataItem.getObjProp("domDoc", null);

	    t = getDocFromString(getXMLString(t).toString());
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    UIDataList uiDataList = (UIDataList) uiRequest.get("Questions");
	    for(int i = 0; i < uiDataList.size(); i++){
		xPath = XPathFactory.newInstance().newXPath();
		UIDataItem qItem = (UIDataItem) uiDataList.get(i);
		String pth = "/EditProductAnswersForm/Questions/ProductQuestion/Name[text()='" + qItem.getName()
			+ "']/../Value";

		Node qNode = (Node) xPath.evaluate(pth, t, XPathConstants.NODE);
		qNode.setTextContent(qItem.getStringProp("Value", null));
	    }

	    logger.debug(getXMLString(t).toString());
	    Document doc = titanSao.getDomDocument(href, t, HttpMethod.PUT, userId, null);
	    if (doc.getDocumentElement().getNodeName().equals("BadRequestResource")){
		return handleError(doc, t);
	    }
	    xPath = XPathFactory.newInstance().newXPath();
	    String nextUrl = xPath.evaluate("//link[@rel='next']/@href", doc);
	    logger.info(">>Customer next url : " + nextUrl);
	    doc = titanSao.getDomDocument(nextUrl, null, HttpMethod.GET, userId, null);

	    NodeList linkNodeList = (NodeList) xPath.evaluate("//CustomerSearchFormResource/link", doc,
		    XPathConstants.NODESET);

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("/related-edit")){

		    nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		    logger.info(">>Customer next url -- related-edit : " + nextUrl);
		}

	    }

	    doc = titanSao.getDomDocument(nextUrl, null, HttpMethod.GET, userId, null);

	    nextUrl = null;
	    linkNodeList = (NodeList) xPath.evaluate("//EditCustomerForm/link", doc, XPathConstants.NODESET);

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("/update")){

		    nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		    logger.info(">>Customer update : " + nextUrl);
		}

	    }

	    uiRequest = new UIRequest();
	    uiRequest.setName("EditCustomerAnswers");
	    String title = xPath.evaluate("//EditCustomerForm/title/text()", doc);
	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("CustomerInfo");
	    dataItem.setStringProp("href", nextUrl);
	    dataItem.setStringProp("title", title);
	    String exp = "/EditCustomerForm/FirstName";
	    Node n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }
	    exp = "/EditCustomerForm/MiddleName";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/LastName";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/NameSuffix";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/EmailAddress";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/PhoneNumber";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/AddressLine1";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/AddressLine2";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/City";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/State";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    exp = "/EditCustomerForm/PostalCode ";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (n != null){
		dataItem.setBooleanObjProp(n.getNodeName(), Boolean.TRUE);
		dataItem.setStringProp(n.getNodeName(), null);
	    }

	    dataItem.setObjProp("domDoc", doc);

	    uiReply.put("CustomerInfo", dataItem);

	    uiRequest.put("CustomerInfo", dataItem);

	    uiReply.put("EditCustomerAnswers", uiRequest);

	}catch(IseBusinessException ex){
	    logger.error("Unable to Customer Basic QA UIReply");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(XPathExpressionException ex){
	    logger.error("Unable to Customer Basic QA UIReply");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;
    }

    private UIReply getEditCustomerAnswersUIReply(UIRequest uiRequest) throws ServiceException, DOMException,
	    IseBusinessException {
	UIReply uiReply = new UIReply();
	try{
	    uiReply.setGotoPageClass("EditCustomerAnswersPage");
	    uiReply.setName("EditCustomerAnswers");

	    UIDataItem requestDataItem = (UIDataItem) uiRequest.get("CustomerInfo");
	    String href = requestDataItem.getStringProp("href", null);

	    String userId = requestDataItem.getStringProp("userId", null);

	    Document doc = (Document) requestDataItem.getObjProp("domDoc", null);
	    doc = getDocFromString(getXMLString(doc).toString());
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    String exp = "/EditCustomerForm/FirstName";
	    Node n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }
	    exp = "/EditCustomerForm/MiddleName";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/LastName";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/NameSuffix";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/EmailAddress";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/PhoneNumber";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/AddressLine1";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/AddressLine2";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/City";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/State";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    exp = "/EditCustomerForm/PostalCode ";
	    n = (Node) xPath.evaluate(exp, doc, XPathConstants.NODE);
	    if (requestDataItem.getStringProp(n.getNodeName(), null) != null){
		n.setTextContent(requestDataItem.getStringProp(n.getNodeName(), null));
	    }

	    logger.debug(getXMLString(doc).toString());
	    Document postDoc = doc;
	    doc = titanSao.getDomDocument(href, doc, HttpMethod.PUT, userId, null);
	    if (doc.getDocumentElement().getNodeName().equals("BadRequestResource")){
		return handleError(doc, postDoc);
	    }
	    xPath = XPathFactory.newInstance().newXPath();
	    String nextUrl = xPath.evaluate("//link[@rel='next']/@href", doc);

	    doc = titanSao.getDomDocument(nextUrl, null, HttpMethod.GET, userId, null);
	    UIRequest nextUIRequest = new UIRequest();
	    nextUIRequest.setName("LoadGiftCard");
	    UIDataList uiQuestionsList = new UIDataList();
	    uiQuestionsList.setName("Questions");
	    NodeList questionsNodeList = (NodeList) xPath.evaluate(
		    "//EditCustomerAnswersForm/Questions/CustomerQuestion", doc, XPathConstants.NODESET);

	    for(int i = 0; i < questionsNodeList.getLength(); i++){

		UIDataItem dataItem = new UIDataItem();
		Node qNode = questionsNodeList.item(i);
		String qName = xPath.evaluate("Name/text()", qNode);
		String qText = xPath.evaluate("Text/text()", qNode);
		dataItem.setName(qName);
		dataItem.setStringProp("Name", qName);
		dataItem.setStringProp("Text", qText);

		NodeList optionsList = (NodeList) xPath.evaluate("Options/CustomerQuestionOption", qNode,
			XPathConstants.NODESET);
		if (optionsList.getLength() > 0){

		    UIDataList uiOptionsList = new UIDataList();
		    for(int j = 0; j < optionsList.getLength(); j++){
			UIDataItem optItem = new UIDataItem();
			String desc = xPath.evaluate("Description/text()", optionsList.item(j));
			String value = xPath.evaluate("Value/text()", optionsList.item(j));
			optItem.setStringProp("Name", desc);
			optItem.setStringProp("Description", desc);
			optItem.setStringProp("Value", value);
			uiOptionsList.add(optItem);
		    }
		    dataItem.setObjProp("options", uiOptionsList);
		}

		uiQuestionsList.add(dataItem);
	    }
	    nextUIRequest.put(uiQuestionsList.getName(), uiQuestionsList);
	    uiReply.put(uiQuestionsList.getName(), uiQuestionsList);

	    nextUrl = null;
	    NodeList linkNodeList = (NodeList) xPath.evaluate("//EditCustomerAnswersForm/link", doc,
		    XPathConstants.NODESET);

	    if (questionsNodeList.getLength() > 0){

		for(int i = 0; i < linkNodeList.getLength(); i++){

		    if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("update")){

			nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		    }
		}

	    }else
		nextUrl = xPath.evaluate("//link[@rel='next']/@href", doc);

	    String title = xPath.evaluate("//EditCustomerAnswersForm/title/text()", doc);
	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("EditCustomerAnswers");
	    dataItem.setStringProp("href", nextUrl);
	    dataItem.setStringProp("title", title);
	    dataItem.setObjProp("domDoc", doc);
	    nextUIRequest.put(dataItem.getName(), dataItem);
	    uiReply.put(nextUIRequest.getName(), nextUIRequest);

	}catch(IseBusinessException ex){
	    logger.error("Unable to get Customer Dynamic QA UIReply");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(XPathExpressionException ex){
	    logger.error("Unable to get Customer Dynamic QA UIReply");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;

    }

    private UIReply getLoadGiftCardUIReply(UIRequest uiRequest) throws ServiceException {
	UIReply uiReply = new UIReply();
	try{
	    uiReply.setGotoPageClass("Success");
	    uiReply.setName("LoadGiftCard");
	    UIDataItem requestDataItem = (UIDataItem) uiRequest.get("EditCustomerAnswers");
	    String href = requestDataItem.getStringProp("href", null);

	    String userId = requestDataItem.getStringProp("userId", null);

	    Document doc = (Document) requestDataItem.getObjProp("domDoc", null);

	    doc = getDocFromString(getXMLString(doc).toString());
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    UIDataList uiDataList = (UIDataList) uiRequest.get("Questions");
	    if (!uiDataList.isEmpty()){
		for(int i = 0; i < uiDataList.size(); i++){
		    xPath = XPathFactory.newInstance().newXPath();
		    UIDataItem qItem = (UIDataItem) uiDataList.get(i);
		    String pth = "/EditCustomerAnswersForm/Questions/CustomerQuestion/Name[text()='" + qItem.getName()
			    + "']/../Value";

		    Node qNode = (Node) xPath.evaluate(pth, doc, XPathConstants.NODE);
		    if (qNode.hasAttributes()){
			NamedNodeMap attMap = qNode.getAttributes();
			Node nilNode = attMap.getNamedItem("xs:nil");
			if (null != nilNode)
			    attMap.removeNamedItem(nilNode.getNodeName());
		    }
		    qNode.setTextContent(qItem.getStringProp("Value", null));
		}
		logger.debug(getXMLString(doc).toString());
		Document postDoc = doc;
		doc = titanSao.getDomDocument(href, doc, HttpMethod.PUT, userId, null);
		if (doc.getDocumentElement().getNodeName().equals("BadRequestResource")){
		    return handleError(doc, postDoc);
		}else if (doc.getDocumentElement().getNodeName().equals("DoNotBuyListResource")){
		    return handleDoNotBuyResponse(doc);
		}
	    }

	    xPath = XPathFactory.newInstance().newXPath();
	    String nextUrl = xPath.evaluate("//link[@rel='next']/@href", doc);

	    doc = titanSao.getDomDocument(nextUrl, null, HttpMethod.GET, userId, null);

	    nextUrl = getNextUrl_EndsWith("//NewTradeInTransactionResource/link", "/create", doc);
	    String title = xPath.evaluate("//NewTradeInTransactionResource/title/text()", doc);
	    uiRequest = new UIRequest();
	    uiRequest.setName("SubmitGiftCard");

	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("GiftCardInfo");
	    dataItem.setStringProp("href", nextUrl);
	    dataItem.setStringProp("title", title);

	    dataItem.setObjProp("domDoc", doc);

	    uiReply.put("GiftCardInfo", dataItem);

	    uiRequest.put("GiftCardInfo", dataItem);

	    uiReply.put("SubmitGiftCard", uiRequest);

	}catch(IseBusinessException ex){
	    logger.error("Unable to get LoadGiftCard UIReply");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(XPathExpressionException ex){
	    logger.error("Unable to get LoadGiftCard UIReply");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;
    }

    private UIReply getSubmitGiftCardUIReply(UIRequest uiRequest) throws ServiceException {
	logger.debug("getSubmitGiftCardUIReply");
	UIReply uiReply = new UIReply();

	try{
	    uiReply.setName("SubmitGiftCard");
	    uiReply.setGotoPageClass("Success");

	    UIDataItem requestDataItem = null;
	    requestDataItem = (UIDataItem) uiRequest.get("GiftCardInfo");
	    String href = requestDataItem.getStringProp("href", null);

	    String userId = requestDataItem.getStringProp("userId", null);
	    String giftCardNumber = requestDataItem.getStringProp("giftCardNumber", null);

	    Document t = (Document) requestDataItem.getObjProp("domDoc", null);

	    t = getDocFromString(getXMLString(t).toString());

	    XPath xPath = XPathFactory.newInstance().newXPath();
	    String exp = "/NewTradeInTransactionResource/GiftCardId";
	    Node n = (Node) xPath.evaluate(exp, t, XPathConstants.NODE);
	    n.getAttributes().removeNamedItem(n.getAttributes().item(0).getNodeName());
	    n.setTextContent(giftCardNumber);
	    logger.debug(getXMLString(t).toString());
	    Document doc = titanSao.getDomDocument(href, t, HttpMethod.POST, userId, null);
	    if (doc.getDocumentElement().getNodeName().equals("BadRequestResource")){
		return handleError(doc, null);
	    }
	    xPath = XPathFactory.newInstance().newXPath();
	    String tradeInDocUrl = xPath.evaluate("//link[@rel='self']/@href", doc);
	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("TradeInInfo");
	    dataItem.setStringProp("tradeInDocUrl", tradeInDocUrl);
	    dataItem.setStringProp("giftCardNumber", giftCardNumber);

	    String itemTag = xPath.evaluate("//DtmsTradeInItemId", doc);

	    dataItem.setStringProp("itemTag", itemTag);
	    uiReply.put("TradeInInfo", dataItem);

	}catch(IseBusinessException ex){
	    logger.error("Unable to get Gift card Trasaction resource");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(XPathExpressionException ex){
	    logger.error("Unable to get Gift card Trasaction resource");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;
    }

    protected final StringWriter getXMLString(Document result) throws TransformerFactoryConfigurationError {
	StringWriter stringWriter = new StringWriter();
	Result sResult = new StreamResult(stringWriter);
	try{
	    Source source = new DOMSource(result);

	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    transformer.transform(source, sResult);

	}catch(TransformerConfigurationException e){
	    logger.error("Error getting XML string", e);
	}catch(TransformerException e){
	    logger.error("Error getting XML string", e);
	}
	return stringWriter;
    }

    protected final Document getDocFromString(String response) throws ServiceException {
	Document doc = null;
	try{

	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    response = new String(response.getBytes(), "UTF-8");
	    doc = db.parse(IOUtils.toInputStream(response));

	}catch(SAXException ex){
	    logger.error("Unable get Document from response.");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(IOException ex){
	    logger.error("Unable get Document from response.");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(ParserConfigurationException ex){
	    logger.error("Unable get Document from response.");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}
	return doc;

    }

    protected final String getNextUrl_EndsWith(String xPathExpression, String endsWithValue, Document doc)
	    throws DOMException, XPathExpressionException {
	String nextUrl = null;
	XPath xPath = XPathFactory.newInstance().newXPath();
	NodeList linkNodeList = (NodeList) xPath.evaluate(xPathExpression, doc, XPathConstants.NODESET);
	for(int i = 0; i < linkNodeList.getLength(); i++){

	    if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith(endsWithValue)){

		nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
	    }

	}
	return nextUrl;
    }

    private UIReply handleError(Document doc, Document postDoc) {
	UIReply uiReply = new UIReply();
	uiReply.setName("TitanError");
	UIDataItem di = new UIDataItem();
	di.setName("TitanError");

	XPath xPath = XPathFactory.newInstance().newXPath();
	try{

	    String title = xPath.evaluate("//title/text()", doc);
	    String message = xPath.evaluate("//Message/text()", doc);
	    di.setStringProp("Title", title);
	    di.setStringProp("Message", message);
	    uiReply.put(di.getName(), di);

	    UIDataList dl = new UIDataList();
	    dl.setName("ErrorList");
	    xPath = XPathFactory.newInstance().newXPath();
	    NodeList errorList = (NodeList) xPath.evaluate("//Error//..", doc, XPathConstants.NODESET);

	    for(int i = 0; i < errorList.getLength(); i++){
		xPath = XPathFactory.newInstance().newXPath();

		String name = xPath.evaluate("Name/text()", errorList.item(i));
		String error = xPath.evaluate("Error/text()", errorList.item(i));

		di = new UIDataItem();
		di.setName(name);
		di.setStringProp("Error", error);
		if (StringUtils.isNotBlank(error))
		    dl.add(di);
	    }

	    uiReply.put(dl.getName(), dl);
	}catch(XPathExpressionException ex){
	    logger.error("Xpath expression error: " + ex.getMessage());
	}

	return uiReply;
    }

    private UIReply handleDoNotBuyResponse(Document doc) {
	UIReply uiReply = new UIReply();
	uiReply.setName("DoNotBuyCustomerError");
	UIDataItem di = new UIDataItem();
	di.setName("DoNotBuyCustomerError");

	XPath xPath = XPathFactory.newInstance().newXPath();
	try{

	    String message = xPath.evaluate("//Message/text()", doc);

	    di.setStringProp("Message", message);
	    uiReply.put(di.getName(), di);

	}catch(XPathExpressionException ex){
	    logger.error("Xpath expression error: " + ex.getMessage());
	}

	return uiReply;
    }
}
