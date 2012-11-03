package com.bestbuy.bbym.ise.drp.sao;

import java.util.HashMap;
import java.util.Map;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.bestbuy.bbym.ise.util.Util;

/**
 * TitanSaoImpl
 *
 * @author a904002
 */
@Service("titanSao")
public class TitanSaoImpl extends RestSao implements TitanSao {

    private static Logger logger = LoggerFactory.getLogger(TitanSaoImpl.class);
    @Autowired
    private DrpPropertyService drpPropertyService;
    private static final String TITAN_CATEGORY = "TITAN_CATEGORY";
    private static final String TITAN_BASE_URI = "TITAN_BASE_URI";
    private static final String TITAN_MEDIA_TYPE = "TITAN_MEDIA_TYPE";
    private static final String TITAN_RESULT_PAGE_SIZE = "TITAN_RESULT_PAGE_SIZE";
    private static final String TITAN_SEARCH_PREFIX = "Q";
    private static final String CATEGORY = "Category*";
    private static final String PAGE = "Page";
    private static final String PAGE_SIZE = "PageSize";
    private static final String USER_NAME = "X-Client-UserName";
    private static final String CLIENT_IP = "X-Client-IP";

    //Added for Security
    private static final String API_KEY = "X-Api-Key";

    @Override
    public Document getDomDocument(String storeId, String searchDescription, String userId) throws ServiceException,
	    IseBusinessException {

	String searchCatalogUrl = "store-" + storeId + "/catalog";

	String baseUri = drpPropertyService.getProperty(TITAN_BASE_URI);
	logger.debug("titan BASE_URI : " + baseUri);

	try{

	    Document d = getDomDocument(baseUri + searchCatalogUrl, null, HttpMethod.GET.toString(), userId, null);
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    logger.debug(">>> ret 1 : " + getXMLString(d).toString());
	    String s = xPath.evaluate("//link[@rel='search']/@href", d);

	    d = getDomDocument(s, null, HttpMethod.GET.toString(), userId, null);
	    xPath = XPathFactory.newInstance().newXPath();

	    logger.debug(">>> ret 2 : " + getXMLString(d).toString());
	    s = xPath.evaluate("//LinkTemplate/@UriTemplate", d);

	    //For url safe values

	    Map<String, String> queryMap = new HashMap<String, String>();
	    queryMap.put(TITAN_SEARCH_PREFIX, searchDescription);
	    queryMap.put(CATEGORY, drpPropertyService.getProperty(TITAN_CATEGORY));
	    queryMap.put(PAGE, "1");
	    queryMap.put(PAGE_SIZE, drpPropertyService.getProperty(TITAN_RESULT_PAGE_SIZE));

	    d = getDomDocument(s, null, HttpMethod.GET.toString(), userId, queryMap);
	    logger.debug(">>> ret 3 : " + getXMLString(d).toString());
	    return d;

	}catch(XPathExpressionException ex){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}
    }

    @Override
    public Document getDomDocument(String httpRef, String userId) throws ServiceException, IseBusinessException {

	return getDomDocument(httpRef, null, HttpMethod.GET.toString(), userId, null);
    }

    @Override
    public Document getDomDocument(String httpRef, Document doc, String userId) throws ServiceException,
	    IseBusinessException {

	return getDomDocument(httpRef, doc, HttpMethod.POST.toString(), userId, null);

    }

    @Override
    public Document getDomDocument(String httpRef, Document doc, String httpMethod, String userId,
	    Map<String, String> urlVariables) throws ServiceException, IseBusinessException {

	String response = null;
	HttpEntity<String> reqEntity;

	if (doc != null){
	    reqEntity = new HttpEntity<String>(getXMLString(doc), createTitanHeader(userId));
	}else
	    reqEntity = new HttpEntity<String>(createTitanHeader(userId));

	response = getResponseWithSecurityHeaders(httpRef, httpMethod, reqEntity, urlVariables);
	logger.debug(response);
	doc = getDocFromString(response);
	return doc;
    }

    private HttpHeaders createTitanHeader(String userId) throws ServiceException {
	HttpHeaders header = new HttpHeaders();
	header.add(API_KEY, drpPropertyService.getProperty(API_KEY));
	header.add(USER_NAME, userId);
	header.add(CLIENT_IP, Util.getIPAddress());
	header.add("Content-Type", drpPropertyService.getProperty(TITAN_MEDIA_TYPE) + ";charset=UTF-8");
	header.add("Accept", drpPropertyService.getProperty(TITAN_MEDIA_TYPE) + ";charset=UTF-8");
	return header;
    }

}
