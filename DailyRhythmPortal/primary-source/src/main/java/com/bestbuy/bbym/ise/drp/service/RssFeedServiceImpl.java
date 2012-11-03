package com.bestbuy.bbym.ise.drp.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.bestbuy.bbym.ise.drp.domain.RssCategory;
import com.bestbuy.bbym.ise.drp.domain.RssItem;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

/**
 * Service to retrieve the RSS feed for "BEAST TICKER" and "BEAST NEWS"
 *
 * @author a904002
 */
//TODO Services should use SAOs to talk to external systems
@Service("rssFeedService")
public class RssFeedServiceImpl implements RSSFeedService {

    private static Logger logger = LoggerFactory.getLogger(RssFeedServiceImpl.class);

    private static final String RSS_BEAST_NEWS = "RSS_BEAST_NEWS";
    private static final String RSS_NEWS = "RSS_NEWS";
    private static final String RSS_BEAST_TICKER = "RSS_BEAST_TICKER";
    private static final String SERVICE = "RSS ";

    @Autowired
    @Qualifier("drpPropertyService")
    private DrpPropertyService drpPropertyService;
    private Map<String, String> rssCategoryMap = new HashMap<String, String>();

    public void setDrpPropertyService(DrpPropertyService drpPropertyService) {
	this.drpPropertyService = drpPropertyService;
    }

    private DocumentBuilderFactory domFactory;

    public RssFeedServiceImpl() {

	domFactory = DocumentBuilderFactory.newInstance();
	domFactory.setNamespaceAware(true);

	rssCategoryMap.put("749", "Post Paid");
	rssCategoryMap.put("750", "No Contract");
	rssCategoryMap.put("751", "Accessory");
	rssCategoryMap.put("752", "Mobile Broadband");
	rssCategoryMap.put("753", "Important Callouts this Week");
	rssCategoryMap.put("590", "Post Paid - Test");
	rssCategoryMap.put("589", "No Contract - Test");

    }

    @Override
    public List<RssCategory> getBeastNewsRssFeed() throws ServiceException {
	List<RssCategory> rssCategoryList = null;
	try{

	    rssCategoryList = covertNewsRssFeed(new URL(drpPropertyService.getProperty(RSS_BEAST_NEWS)));

	}catch(MalformedURLException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error getting BEAST new feed", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
	return rssCategoryList;
    }

    @Override
    public List<RssCategory> getBeastTickerRssFeed() throws ServiceException {
	List<RssCategory> rssCategoryList = null;
	try{

	    rssCategoryList = covertTickerRssFeed(new URL(drpPropertyService.getProperty(RSS_BEAST_TICKER)));

	}catch(MalformedURLException ex){
	    logger
		    .error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error getting BEAST ticker RSS feed",
			    ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
	return rssCategoryList;

    }

    private List<RssCategory> covertTickerRssFeed(URL url) throws ServiceException {
	List<RssCategory> categoryList = new ArrayList<RssCategory>();

	try{
	    DocumentBuilder parser = domFactory.newDocumentBuilder();
	    Document XMLDoc = parser.parse(getURLConnection(url).getInputStream());
	    NodeList categoryNodeList = XMLDoc.getElementsByTagName("item");
	    logger.info("Number of Tickers : " + categoryNodeList.getLength());
	    for(int i = 0; i < categoryNodeList.getLength(); i++){
		RssCategory rssCategory = new RssCategory();
		Node categoryNode = categoryNodeList.item(i);
		NodeList childNodeList = categoryNode.getChildNodes();
		String pubDate = "";
		for(int j = 0; j < childNodeList.getLength(); j++){
		    Node childNode = childNodeList.item(j);
		    if (childNode.getNodeName().equals("pubDate")){
			pubDate = childNode.getTextContent();
		    }

		    if (childNode.getNodeName().equals("title")){
			if (!StringUtils.isBlank(pubDate)){
			    rssCategory.setShortDescription(childNode.getTextContent() + " (" + pubDate + ") ");
			    pubDate = "";
			}else{
			    rssCategory.setShortDescription(childNode.getTextContent() + " ");
			}

		    }

		}
		categoryList.add(rssCategory);
	    }

	}catch(SAXException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error converting ticker RSS feed", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(ParserConfigurationException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error converting ticker RSS feed", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(IOException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error converting ticker RSS feed", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
	return categoryList;

    }

    private List<RssCategory> covertNewsRssFeed(URL url) throws ServiceException {
	List<RssCategory> categoryList = new ArrayList<RssCategory>();

	try{
	    DocumentBuilder parser = domFactory.newDocumentBuilder();
	    Document XMLDoc = parser.parse(getURLConnection(url).getInputStream());
	    NodeList categoryNodeList = XMLDoc.getElementsByTagName("item");
	    logger.info("Number of Categories : " + categoryNodeList.getLength());
	    for(int i = 0; i < categoryNodeList.getLength(); i++){
		RssCategory rssCategory = new RssCategory();
		Node categoryNode = categoryNodeList.item(i);
		NodeList childNodeList = categoryNode.getChildNodes();

		for(int j = 0; j < childNodeList.getLength(); j++){
		    Node childNode = childNodeList.item(j);

		    if (childNode.getNodeName().equals("categoryID")){
			rssCategory.setTitle(rssCategoryMap.get(childNode.getTextContent()));
		    }

		    if (childNode.getNodeName().equals("pubDate")){
			rssCategory.setLastUpdatedDate(childNode.getTextContent());
		    }
		    if (childNode.getNodeName().equals("newsID")){
			rssCategory.setRssItemList(getRssItemList(childNode.getTextContent()));
		    }

		}
		categoryList.add(rssCategory);
	    }

	}catch(SAXException ex){
	    logger.error("Error converting news RSS feed", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(ParserConfigurationException ex){
	    logger.error("Error converting news RSS feed", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(IOException ex){
	    logger.error("Error converting news RSS feed", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}
	return categoryList;

    }

    private List<RssItem> getRssItemList(String newsId) throws ServiceException {
	List<RssItem> rssItemList = new ArrayList<RssItem>();

	try{
	    URL url = new URL(drpPropertyService.getProperty(RSS_NEWS).concat(newsId));
	    DocumentBuilder parser = domFactory.newDocumentBuilder();
	    Document XMLDoc = parser.parse(getURLConnection(url).getInputStream());
	    NodeList categoryNodeList = XMLDoc.getElementsByTagName("item");
	    for(int i = 0; i < categoryNodeList.getLength(); i++){
		Node categoryNode = categoryNodeList.item(i);
		NodeList childNodeList = categoryNode.getChildNodes();
		for(int j = 0; j < childNodeList.getLength(); j++){
		    Node childNode = childNodeList.item(j);
		    if (childNode.getNodeName().equals("fullText")){
			rssItemList.add(new RssItem(childNode.getTextContent()));
			break;
		    }

		}
	    }
	}catch(SAXException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error getting RSS item list", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(ParserConfigurationException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error getting RSS item list", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}catch(IOException ex){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + SERVICE + "Error getting RSS item list", ex);
	    throw new ServiceException(IseExceptionCodeEnum.DataAccess, ex.getMessage());
	}

	return rssItemList;
    }

    private URLConnection getURLConnection(URL url) throws IOException {
	URLConnection urlConn = url.openConnection();
	//Default to 10 secs if property not set
	int timeout = Util.getInt(drpPropertyService.getProperty("RSS_CONNECTION_TIMEOUT", "10"), 10) * 1000;
	urlConn.setConnectTimeout(timeout);
	urlConn.setReadTimeout(timeout);
	return urlConn;
    }
}
