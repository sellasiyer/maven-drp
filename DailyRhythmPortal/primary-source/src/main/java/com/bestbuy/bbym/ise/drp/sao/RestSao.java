package com.bestbuy.bbym.ise.drp.sao;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

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

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;
import org.xml.sax.SAXException;

import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.xml.ws.util.xml.XmlUtil;

/**
 * Simple class for providing useful methods to interact with restful services.
 * TODO: Add security, if needed.
 *
 * @author a904002
 * @deprecated rather use {@link RestTemplate} directly.
 */
@Deprecated
public abstract class RestSao {

    private static Logger logger = LoggerFactory.getLogger(RestSao.class);

    @Autowired
    private RestOperations restTemplate;

    /**
     * Post the restful request and returns the response.
     *
     * @param baseUri - Base uri
     * @param <T> responseType - ResponseType
     * @param mediaType - MediaType of the target restful service/method
     * @param pathUri - Path Uri
     * @param requestEntity - RequestEntity object
     * @return <T> responseType - Returns the response as responseType object
     * @throws UniformInterfaceException
     * @throws ServiceException 
     */
    public <T> T postRestfulClientRequest(String baseUri, Class<T> responseType, String mediaType, String pathUri,
	    Object requestEntity) throws ServiceException {

	if (StringUtils.isNotBlank(pathUri)){
	    baseUri = baseUri + "/" + pathUri;
	}

	HttpHeaders httpHeader = new HttpHeaders();
	httpHeader.add("Accept", mediaType);
	httpHeader.add("Content-Type", mediaType);
	HttpEntity<T> reqEntity = new HttpEntity<T>((T) requestEntity, httpHeader);

	logger.debug("BASE_URI : " + baseUri);
	ResponseEntity<T> rt = null;
	try{

	    rt = restTemplate.exchange(baseUri, HttpMethod.POST, reqEntity, responseType);

	}catch(RestClientException e){
	    logger.error("Rest Client exception", e);
	    throw new ServiceException(IseExceptionCodeEnum.INTERNALSERVERERROR, e.getMessage());
	}
	return rt.getBody();

    }

    public <T> T getResponseWithSecurityHeaders(String url, String method, HttpEntity requestEntity,
	    Map<String, String> uriVariables) throws ServiceException {

	ResponseEntity<?> rt = null;

	if (HttpMethod.GET.toString().equals(method)){
	    rt = getResponse(url, HttpMethod.GET, requestEntity, String.class, uriVariables);

	}else if (HttpMethod.PUT.toString().equals(method)){
	    rt = getResponse(url, HttpMethod.PUT, requestEntity, String.class, uriVariables);

	}else if (HttpMethod.POST.toString().equals(method)){
	    rt = getResponse(url, HttpMethod.POST, requestEntity, String.class, uriVariables);

	}
	//TODO ADD HttpMethod.Delete if required in future.
	else{
	    String errMsg = "Unknown HttpMethod : " + method;
	    logger.error(errMsg);
	    throw new ServiceException(IseExceptionCodeEnum.METHODNOTALLOWED, errMsg);

	}

	return (T) rt.getBody();
    }

    private ResponseEntity<?> getResponse(String url, HttpMethod httpMethod, HttpEntity<?> reqEntity,
	    Class<String> class1, Map<String, String> uriVariables) {

	if (uriVariables != null && !uriVariables.isEmpty())
	    return restTemplate.exchange(url, httpMethod, reqEntity, String.class, uriVariables);
	else
	    return restTemplate.exchange(url, httpMethod, reqEntity, String.class);
    }

    public Document getDocFromString(String response) throws ServiceException {
	Document doc = null;
	try{

	    DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();

	    doc = db.parse(XmlUtil.getUTF8Stream(response));

	    logger.debug("RESPONSE XML >> : " + getXMLString(doc));
	}catch(ParserConfigurationException ex){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(SAXException ex){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}catch(IOException ex){
	    throw new ServiceException(IseExceptionCodeEnum.GeneralSystem, ex.getMessage());
	}
	return doc;

    }

    public String getStringFromDoc(Document doc) {
	DOMImplementationLS domImplementation = (DOMImplementationLS) doc.getImplementation();
	LSSerializer lsSerializer = domImplementation.createLSSerializer();
	return lsSerializer.writeToString(doc);
    }

    public String getXMLString(Document result) throws TransformerFactoryConfigurationError {
	StringWriter stringWriter = new StringWriter();
	Result sResult = new StreamResult(stringWriter);
	try{
	    Source source = new DOMSource(result);

	    TransformerFactory factory = TransformerFactory.newInstance();
	    Transformer transformer = factory.newTransformer();
	    transformer.transform(source, sResult);

	}catch(TransformerConfigurationException e){
	    logger.error("Error getting XML String from document", e);
	}catch(TransformerException e){
	    logger.error("Error getting XML String from document", e);
	}

	return stringWriter.toString();
    }

}
