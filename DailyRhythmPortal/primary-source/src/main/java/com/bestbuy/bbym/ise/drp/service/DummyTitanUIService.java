package com.bestbuy.bbym.ise.drp.service;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bestbuy.bbym.ise.drp.domain.ui.UIDataItem;
import com.bestbuy.bbym.ise.drp.domain.ui.UIDataList;
import com.bestbuy.bbym.ise.drp.domain.ui.UIReply;
import com.bestbuy.bbym.ise.drp.domain.ui.UIRequest;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

@Service("uiService1")
public class DummyTitanUIService extends TitanUIServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(DummyTitanUIService.class);

    @Override
    public UIReply processUIRequest(UIRequest uiRequest) throws ServiceException, IseBusinessException {
	String requestName = uiRequest.getName();
	logger.debug(">> Processing UI Request () for " + requestName);
	logger.debug(">> uirequest " + uiRequest.toString());
	UIReply uiReply = null;
	if ("CatalogSearch".equals(requestName)){

	    uiReply = super.processUIRequest(uiRequest);
	}else if ("ProductDetails".equals(uiRequest.getName())){
	    uiReply = super.processUIRequest(uiRequest);
	}else if ("PricingQA".equals(uiRequest.getName())){
	    uiReply = super.processUIRequest(uiRequest);
	}else if ("CreateCart".equals(uiRequest.getName())){
	    uiReply = super.processUIRequest(uiRequest);
	}else if ("EditProductInfo".equals(requestName)){
	    uiReply = getEditProductInfoUIReply(uiRequest);
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

	logger.debug(">> Returning uiReply() for " + requestName);
	logger.debug(">> uireply " + uiReply.toString());
	return uiReply;
    }

    private UIReply getEditProductInfoUIReply(UIRequest uiRequest) throws ServiceException {
	UIReply uiReply = new UIReply();
	try{
	    uiReply.setGotoPageClass("EditProductAnswersPage");

	    UIDataItem requestDataItem = null;
	    requestDataItem = (UIDataItem) uiRequest.get("nextUrl");
	    String href = requestDataItem.getStringProp("href", null);
	    String promoCode = requestDataItem.getStringProp("promoCode", null);

	    Document t = readFileToDocument(this.getClass(), "Titan_CartResource.xml");

	    String s = "//PromotionItem/Source/Id[(text()='mobile')]/../PromotionCode[text()='" + promoCode
		    + "']/../../link/@href";

	    XPath xPath = XPathFactory.newInstance().newXPath();
	    s = xPath.evaluate(s, t);

	    if (StringUtils.isNotBlank(s)){
		t = readFileToDocument(this.getClass(), "Titan_SelectPromotionResource.xml");

		NodeList n = (NodeList) xPath.evaluate("//link", t, XPathConstants.NODESET);

		for(int i = 0; i < n.getLength(); i++){
		    Node nd = n.item(i);
		    if (nd.getAttributes().getNamedItem("rel").getNodeValue().endsWith("apply-selection")){
			s = nd.getAttributes().getNamedItem("href").getNodeValue();
			break;
		    }
		}

		t = readFileToDocument(this.getClass(), "Titan_SelectPromotionResource.xml");

		xPath = XPathFactory.newInstance().newXPath();
		s = xPath.evaluate("//link[@rel='next']/@href", t);

		t = readFileToDocument(this.getClass(), "Titan_EditProductInfo.xml");

	    }else{
		xPath = XPathFactory.newInstance().newXPath();
		s = xPath.evaluate("//link[@rel='next']/@href", t);
		t = readFileToDocument(this.getClass(), "Titan_EditProductInfo.xml");
	    }

	    Document doc = t;

	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("ProductInfo");

	    xPath = XPathFactory.newInstance().newXPath();
	    String modelNumber = xPath.evaluate("//EditProductForm/ModelNumber/text()", doc);
	    String manufacturerName = xPath.evaluate("//EditProductForm/Manufacturer/text()", doc);
	    String title = xPath.evaluate("//EditProductForm/title/text()", doc);

	    dataItem.setStringProp("modelNumber", modelNumber);
	    dataItem.setStringProp("manufacturerName", manufacturerName);
	    dataItem.setStringProp("title", title);
	    boolean isReceiptRequired = true;
	    dataItem.setStringProp("receipt", Boolean.toString(isReceiptRequired));
	    uiReply.put(dataItem.getName(), dataItem);

	    String nextUrl = null;
	    NodeList linkNodeList = (NodeList) xPath.evaluate("//EditProductForm/link", doc, XPathConstants.NODESET);

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("update")){

		    nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		}

	    }

	    UIRequest nextUIRequest = new UIRequest();
	    nextUIRequest.setName("EditProductAnswers");
	    dataItem = new UIDataItem();
	    dataItem.setName("EditProductAnswers");
	    dataItem.setStringProp("modelNumber", modelNumber);
	    dataItem.setStringProp("manufacturerName", manufacturerName);
	    dataItem.setStringProp("receipt", Boolean.toString(true));
	    dataItem.setStringProp("title", title);
	    dataItem.setStringProp("href", nextUrl);

	    dataItem.setObjProp("domDoc", doc);
	    nextUIRequest.put(dataItem.getName(), dataItem);
	    uiReply.put(nextUIRequest.getName(), nextUIRequest);
	    System.err.println("EditProductInfoReply : " + uiReply.toString());

	}catch(XPathExpressionException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;
    }

    private UIReply getEditProductAnswersUIReply(UIRequest uiRequest) throws ServiceException {
	UIReply uiReply = new UIReply();
	uiReply.setGotoPageClass("CustomerEditPage");

	UIRequest nextUIRequest = new UIRequest();
	nextUIRequest.setName("EditCustomerInfo");
	try{

	    UIDataItem requestDataItem = null;
	    requestDataItem = (UIDataItem) uiRequest.get("EditProductAnswers");
	    String href = requestDataItem.getStringProp("href", null);
	    String modelNumber = requestDataItem.getStringProp("modelNumber", null);
	    String manufacturerName = requestDataItem.getStringProp("manufacturerName", null);
	    String receiptNumber = requestDataItem.getStringProp("receiptNumber", null);
	    boolean isReceiptRequired = Boolean.parseBoolean(requestDataItem.getStringProp("receipt", "false"));
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    Document doc = (Document) requestDataItem.getObjProp("domDoc", null);
	    Node modelNumberNode = (Node) xPath.evaluate("//EditProductForm/ModelNumber", doc, XPathConstants.NODE);
	    modelNumberNode.setTextContent(modelNumber);

	    Node manufacturerNameNode = (Node) xPath.evaluate("//EditProductForm/Manufacturer", doc,
		    XPathConstants.NODE);
	    manufacturerNameNode.setTextContent(manufacturerName);

	    Node receiptNumberNode = (Node) xPath.evaluate("//EditProductForm/ReceiptNumber", doc, XPathConstants.NODE);

	    receiptNumberNode.setTextContent(receiptNumber);

	    //			doc = titanSao.getDomDocument(href, doc, false, HttpMethod.PUT,
	    //					null);

	    xPath = XPathFactory.newInstance().newXPath();
	    String nextUrl = xPath.evaluate("//link[@rel='next']/@href", doc);

	    doc = readFileToDocument(this.getClass(), "Titan_EditProductAnswers.xml");

	    UIDataList uiQuestionsList = new UIDataList();
	    uiQuestionsList.setName("Questions");
	    UIDataItem dataItem;
	    NodeList questionsNodeList = (NodeList) xPath.evaluate(
		    "//EditProductAnswersForm/Questions/ProductQuestion", doc, XPathConstants.NODESET);
	    for(int i = 0; i < questionsNodeList.getLength(); i++){

		dataItem = new UIDataItem();
		Node qNode = questionsNodeList.item(i);
		String qName = xPath.evaluate("Name/text()", qNode);
		String qText = xPath.evaluate("Text/text()", qNode);
		dataItem.setName(qName);
		dataItem.setStringProp("Name", qName);
		dataItem.setStringProp("Text", qText);

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

	    nextUrl = null;
	    NodeList linkNodeList = (NodeList) xPath.evaluate("//EditProductAnswersForm/link", doc,
		    XPathConstants.NODESET);

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("update")){

		    nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		}

	    }

	    dataItem = new UIDataItem();
	    dataItem.setName("EditProductAnswers");
	    dataItem.setStringProp("href", nextUrl);
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

	    UIDataItem requestDataItem = null;
	    requestDataItem = (UIDataItem) uiRequest.get("EditProductAnswers");
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

	    //			Document doc = titanSao.getDomDocument(href, t, false,
	    //					HttpMethod.PUT, userId);
	    //			xPath = XPathFactory.newInstance().newXPath();
	    //			String nextUrl = xPath.evaluate("//link[@rel='next']/@href", doc);
	    //
	    //		Document	doc = readFileToDocument(this.getClass(), "Titan_EditProductAnswers.xml");
	    //
	    //			nextUrl = null;
	    //			NodeList linkNodeList = (NodeList) xPath.evaluate(
	    //					"//CustomerSearchFormResource/link", doc,
	    //					XPathConstants.NODESET);
	    //
	    //			for (int i = 0; i < linkNodeList.getLength(); i++) {
	    //
	    //				if (linkNodeList.item(i).getAttributes().getNamedItem("rel")
	    //						.getNodeValue().endsWith("/related-edit")) {
	    //
	    //					nextUrl = linkNodeList.item(i).getAttributes()
	    //							.getNamedItem("href").getNodeValue();
	    //				}
	    //
	    //			}

	    Document doc = readFileToDocument(this.getClass(), "Titan_EditCustomerInfo.xml");

	    String nextUrl = null;
	    NodeList linkNodeList = (NodeList) xPath.evaluate("//EditCustomerForm/link", doc, XPathConstants.NODESET);

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("/update")){

		    nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		}

	    }

	    uiRequest = new UIRequest();
	    uiRequest.setName("EditCustomerAnswers");

	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("CustomerInfo");
	    dataItem.setStringProp("href", nextUrl);
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

	}catch(XPathExpressionException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;
    }

    private UIReply getEditCustomerAnswersUIReply(UIRequest uiRequest) throws ServiceException, DOMException,
	    IseBusinessException {
	UIReply uiReply = new UIReply();
	try{
	    uiReply.setGotoPageClass("EditCustomerAnswersPage");

	    UIDataItem requestDataItem = null;
	    requestDataItem = (UIDataItem) uiRequest.get("CustomerInfo");
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

	    //			doc = titanSao.getDomDocument(href, doc, true, HttpMethod.PUT,
	    //					userId);
	    xPath = XPathFactory.newInstance().newXPath();
	    //			String nextUrl = xPath.evaluate("//link[@rel='next']/@href", doc);

	    doc = readFileToDocument(this.getClass(), "Titan_EditCustomerAnswersForm.xml");
	    UIRequest nextUIRequest = new UIRequest();
	    nextUIRequest.setName("LoadGiftCard");
	    UIDataList uiQuestionsList = new UIDataList();
	    uiQuestionsList.setName("Questions");
	    UIDataItem dataItem;
	    NodeList questionsNodeList = (NodeList) xPath.evaluate(
		    "//EditCustomerAnswersForm/Questions/CustomerQuestion", doc, XPathConstants.NODESET);
	    for(int i = 0; i < questionsNodeList.getLength(); i++){

		dataItem = new UIDataItem();
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

	    String nextUrl = null;
	    NodeList linkNodeList = (NodeList) xPath.evaluate("//EditCustomerAnswersForm/link", doc,
		    XPathConstants.NODESET);

	    for(int i = 0; i < linkNodeList.getLength(); i++){

		if (linkNodeList.item(i).getAttributes().getNamedItem("rel").getNodeValue().endsWith("update")){

		    nextUrl = linkNodeList.item(i).getAttributes().getNamedItem("href").getNodeValue();
		}

	    }

	    dataItem = new UIDataItem();
	    dataItem.setName("EditCustomerAnswers");
	    dataItem.setStringProp("href", nextUrl);
	    dataItem.setObjProp("domDoc", doc);
	    nextUIRequest.put(dataItem.getName(), dataItem);
	    uiReply.put(nextUIRequest.getName(), nextUIRequest);

	}catch(XPathExpressionException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;

    }

    private UIReply getLoadGiftCardUIReply(UIRequest uiRequest) throws ServiceException {
	UIReply uiReply = new UIReply();
	try{
	    uiReply.setGotoPageClass("Success");

	    UIDataItem requestDataItem = null;
	    requestDataItem = (UIDataItem) uiRequest.get("EditCustomerAnswers");
	    String href = requestDataItem.getStringProp("href", null);

	    String userId = requestDataItem.getStringProp("userId", null);

	    Document t = (Document) requestDataItem.getObjProp("domDoc", null);

	    t = getDocFromString(getXMLString(t).toString());
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    UIDataList uiDataList = (UIDataList) uiRequest.get("Questions");
	    for(int i = 0; i < uiDataList.size(); i++){
		xPath = XPathFactory.newInstance().newXPath();
		UIDataItem qItem = (UIDataItem) uiDataList.get(i);
		String pth = "/EditCustomerAnswersForm/Questions/CustomerQuestion/Name[text()='" + qItem.getName()
			+ "']/../Value";

		Node qNode = (Node) xPath.evaluate(pth, t, XPathConstants.NODE);
		qNode.setTextContent(qItem.getStringProp("Value", null));
	    }
	    //
	    //			Document doc = titanSao.getDomDocument(href, t, false,
	    //					HttpMethod.PUT, userId);
	    xPath = XPathFactory.newInstance().newXPath();
	    String nextUrl;//xPath.evaluate("//link[@rel='next']/@href", doc);

	    Document doc = readFileToDocument(this.getClass(), "Titan_NewTradeInTransactionResource.xml");

	    nextUrl = getNextUrl_EndsWith("//NewTradeInTransactionResource/link", "/create", doc);

	    uiRequest = new UIRequest();
	    uiRequest.setName("SubmitGiftCard");

	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("GiftCardInfo");
	    dataItem.setStringProp("href", nextUrl);

	    dataItem.setObjProp("domDoc", doc);

	    uiReply.put("GiftCardInfo", dataItem);

	    uiRequest.put("GiftCardInfo", dataItem);

	    uiReply.put("SubmitGiftCard", uiRequest);

	}catch(XPathExpressionException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;
    }

    private UIReply getSubmitGiftCardUIReply(UIRequest uiRequest) throws ServiceException {
	UIReply uiReply = new UIReply();
	try{
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
	    Document doc = readFileToDocument(this.getClass(), "Titan_TransactionResource.xml");

	    xPath = XPathFactory.newInstance().newXPath();
	    String tradeInDocUrl = xPath.evaluate("//link[@rel='self']/@href", doc);
	    UIDataItem dataItem = new UIDataItem();
	    dataItem.setName("TradeInInfo");
	    dataItem.setStringProp("tradeInDocUrl", tradeInDocUrl);
	    dataItem.setStringProp("giftCardNumber", giftCardNumber);
	    // TODO Implement Item Tag
	    dataItem.setStringProp("itemTag", null);

	}catch(XPathExpressionException ex){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());

	}
	return uiReply;
    }

    /**
     * Reads the contents of the file.
     * <p>
     * Fails if the file could not be read from the test resources folder.
     * </p>
     * 
     * @param testClass
     *            the test class
     * @param fileName
     *            the name of the file to be read
     * @return an input stream containing the file contents
     * @throws ServiceException 
     */
    public static InputStream readFileToInputStream(Class<?> testClass, String fileName) throws ServiceException {
	String packageName = testClass.getPackage().getName();
	packageName = packageName.replace('.', '/');
	InputStream inputStream = DummyTitanUIService.class.getClassLoader().getResourceAsStream(
		packageName + '/' + fileName);
	if (inputStream == null){
	    logger.error("Unable to create Cart for the device");
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Unable to get the file contents");
	}
	return inputStream;
    }

    /**
     * Reads the contents of the file and return it as DOM Document.
     * <p>
     * Fails if the file could not be read from the test resources folder, parser configuration or Sax Exception.
     * </p>
     *  Used in Titan services - for mocking purposes.
     * @param testClass
     *            the test class
     * @param fileName
     *            the name of the file to be read
     * @return DOM representation of the document
     * @throws ServiceException 
     */
    public static Document readFileToDocument(Class<?> testClass, String fileName) throws ServiceException {
	Document document = null;

	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
	try{
	    DocumentBuilder db = dbf.newDocumentBuilder();

	    document = db.parse(readFileToInputStream(testClass, fileName));
	}catch(ParserConfigurationException e){
	    logger.error("Unable to get the file contents. " + e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Unable to get the file contents");
	}catch(IOException e){
	    logger.error("Unable to get the file contents. " + e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Unable to get the file contents");
	}catch(SAXException e){

	    logger.error("Unable to get the file contents. " + e.getMessage());
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, "Unable to get the file contents");
	}

	return document;

    }

}
