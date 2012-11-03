package com.bestbuy.bbym.ise.drp.sao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.drools.util.codec.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.bestbuy.bbym.ise.drp.domain.YGIBDevice;
import com.bestbuy.bbym.ise.drp.domain.YGIBResponse;
import com.bestbuy.bbym.ise.drp.domain.YGIBTestResult;
import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.drp.utils.DrpConstants;
import com.bestbuy.bbym.ise.drp.utils.SecurityUtils;
import com.bestbuy.bbym.ise.exception.IseBusinessException;
import com.bestbuy.bbym.ise.exception.IseExceptionCodeEnum;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * YGIBServiceImpl Responsible for Tech Checker Service
 * 
 * @author a194869
 */
@Service("ygibSao")
public class YGIBSaoImpl extends RestSao implements YGIBSao {

    private static Logger logger = LoggerFactory.getLogger(YGIBSaoImpl.class);

    @Autowired
    @Qualifier("drpPropertyService")
    private DrpPropertyService drpPropertiesService;

    private static final String SUCCESS = "0";

    private final String YGIB_SERVICE_URL = "YGIB_SERVICE_URL";
    private final String YGIB_SIGNATURE = "YGIB_SIGNATURE";
    private final String YGIB_API_KEY = "YGIB_API_KEY";

    @Override
    public YGIBResponse postDeviceTest(YGIBDevice device) throws ServiceException, IseBusinessException {
	YGIBResponse testResult;
	try{
	    String serviceURL = drpPropertiesService.getProperty(YGIB_SERVICE_URL,
		    "https://bbytest.yougetitback.com/bby/services/rest/download/getDevice/API-KEY/");
	    String signature = drpPropertiesService.getProperty(YGIB_SIGNATURE,
		    "/5lk6DzCEH1NPqhWclEewjmXJzSRRB0TqjyBiXTrsZNWQu3+PJEmRJwwmrD6112k1NM7358BW0nNFTakuOPY3w==");
	    String apiKey = drpPropertiesService.getProperty(YGIB_API_KEY, "1bc1bb4be38b98ba09218e9360f9059aa");
	    Document deviceDom = getDeviceDom(device);
	    String httpRef = serviceURL + apiKey + "/SIGNATURE/"
		    + SecurityUtils.getHmacMD5(signature, getStringFromDoc(deviceDom));
	    logger.info("TRIAGE : YGIB URL : " + httpRef);
	    logger.info("TRIAGE : Request to YGIB : " + getStringFromDoc(deviceDom));

	    String responseString = postRestfulClientRequest(httpRef, String.class, "application/xml", "",
		    getStringFromDoc(deviceDom));
	    // Temp response XML still connection issue resolved.
	    // String responseString =
	    // "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><get-device-response><model>SPH-D710</model><ram>233738240</ram><name>SPH-D710</name><manufacturer>samsung</manufacturer><display-density>1.5</display-density><rooted>No</rooted><os-version>IMM76I.FF18</os-version><firmware>4.0.4</firmware><response-code>0</response-code><response-desc>success</response-desc><test fail=\"2\" other=\"0\" pass=\"6\" id=\"TOUCH SCREEN\"><desc>touch screen test</desc><status>2</status><subjective>no</subjective><timestamp>2012-09-04T15:09:18-05:00</timestamp></test><test fail=\"0\" other=\"0\" pass=\"6\" id=\"MULTI TOUCH SCREEN\"><desc>multi touch screen test</desc><status>0</status><subjective>no</subjective><timestamp>2012-09-04T15:09:28-05:00</timestamp></test><test fail=\"0\" other=\"2\" pass=\"2\" id=\"CAMERA FLASH\"><desc>camera flash test</desc><status>0</status><subjective>yes</subjective><timestamp>2012-08-31T14:14:53-05:00</timestamp></test><test fail=\"1\" other=\"2\" pass=\"2\" id=\"PROXIMITY SENSOR\"><desc>proximity sensor test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-17T14:27:17-05:00</timestamp></test><test fail=\"0\" other=\"1\" pass=\"0\" id=\"HEADSET\"><desc>headset test</desc><status>1</status><subjective>yes</subjective><timestamp>2012-08-15T14:42:57-05:00</timestamp></test><test fail=\"0\" other=\"1\" pass=\"0\" id=\"HEADSET MIC\"><desc>headset mic test</desc><status>1</status><subjective>no</subjective><timestamp>2012-08-15T14:42:57-05:00</timestamp></test><test fail=\"0\" other=\"1\" pass=\"1\" id=\"SPEAKER\"><desc>speaker test</desc><status>0</status><subjective>yes</subjective><timestamp>2012-08-15T15:08:45-05:00</timestamp></test><test fail=\"0\" other=\"1\" pass=\"2\" id=\"MICROPHONE\"><desc>microphone test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-23T14:21:55-05:00</timestamp></test><test fail=\"0\" other=\"2\" pass=\"4\" id=\"GPS\"><desc>gps test</desc><status>0</status><subjective>no</subjective><timestamp>2012-09-04T14:09:16-05:00</timestamp></test><test fail=\"0\" other=\"1\" pass=\"2\" id=\"FRONT CAMERA\"><desc>front camera test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-28T11:27:06-05:00</timestamp></test><test fail=\"0\" other=\"1\" pass=\"3\" id=\"BLUETOOTH\"><desc>bluetooth test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-17T14:26:21-05:00</timestamp></test><test fail=\"10\" other=\"1\" pass=\"2\" id=\"WIFI\"><desc>wifi test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-28T11:24:53-05:00</timestamp></test><test fail=\"2\" other=\"2\" pass=\"10\" id=\"ACCELEROMETER SENSOR\"><desc>accelerometer sensor test</desc><status>0</status><subjective>no</subjective><timestamp>2012-09-04T15:13:10-05:00</timestamp></test><test fail=\"0\" other=\"4\" pass=\"2\" id=\"MAGNETIC FIELD SENSOR\"><desc>magnetic field sensor test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-17T14:27:24-05:00</timestamp></test><test fail=\"1\" other=\"2\" pass=\"2\" id=\"BACK CAMERA\"><desc>back camera test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-28T11:26:17-05:00</timestamp></test><test fail=\"1\" other=\"2\" pass=\"2\" id=\"VIBRATION\"><desc>vibration test</desc><status>0</status><subjective>yes</subjective><timestamp>2012-09-04T15:08:10-05:00</timestamp></test><test fail=\"1\" other=\"2\" pass=\"2\" id=\"TELEPHONY\"><desc>telephony test</desc><status>0</status><subjective>yes</subjective><timestamp>2012-08-17T14:28:03-05:00</timestamp></test><test fail=\"0\" other=\"3\" pass=\"2\" id=\"GYROSCOPE SENSOR\"><desc>gyroscope sensor test</desc><status>0</status><subjective>no</subjective><timestamp>2012-08-17T14:27:38-05:00</timestamp></test></get-device-response>";
	    testResult = getYGIBResponse(responseString);

	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + "TRIAGE : YGIB Tech Checker Service Exception", e);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException,
		    "Unable to retrieve test results at this time. Please try again later.");
	}
	return testResult;

    }

    private YGIBResponse getYGIBResponse(String responseString) throws ServiceException {
	YGIBResponse testResult = new YGIBResponse();
	List<YGIBTestResult> testResultList = new ArrayList<YGIBTestResult>();
	responseString = new String(StringUtils.getBytesUtf8(responseString));
	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

	try{
	    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	    Document response = documentBuilder.parse(IOUtils.toInputStream(responseString));
	    logger.info("TRIAGE : Response from YGIB : " + getStringFromDoc(response));
	    XPath xPath = XPathFactory.newInstance().newXPath();
	    testResult.setResponseDesc(xPath.evaluate("//response-desc/text()", response));
	    if (xPath.evaluate("//response-code/text()", response) != null){
		testResult.setResponseCode(Integer.valueOf(xPath.evaluate("//response-code/text()", response)));
	    }
	    if (xPath.evaluate("//response-code/text()", response).equals(SUCCESS)){
		testResult.setName(xPath.evaluate("//name/text()", response));
		testResult.setManufacturer(xPath.evaluate("//manufacturer/text()", response));
		testResult.setModel(xPath.evaluate("//model/text()", response));
		testResult.setRam(xPath.evaluate("//ram/text()", response));
		testResult.setOs(xPath.evaluate("//os-version/text()", response));
		testResult.setFirmware(xPath.evaluate("//firmware/text()", response));
		testResult.setDisplayDensity(xPath.evaluate("//display-density/text()", response));
		testResult.setSku(xPath.evaluate("//sku/text()", response));
		testResult.setPlanId(xPath.evaluate("//plan-id/text()", response));
		testResult.setRooted("yes".equalsIgnoreCase(xPath.evaluate("//rooted/text()", response)));
		NodeList testNodeList = (NodeList) xPath.evaluate("//test", response, XPathConstants.NODESET);
		for(int i = 0; i < testNodeList.getLength(); i++){
		    if (testNodeList.item(i) != null){
			String id = testNodeList.item(i).getAttributes().getNamedItem("id").getNodeValue();
			String pass = testNodeList.item(i).getAttributes().getNamedItem("pass").getNodeValue();
			String fail = testNodeList.item(i).getAttributes().getNamedItem("fail").getNodeValue();
			String other = testNodeList.item(i).getAttributes().getNamedItem("other").getNodeValue();
			NodeList childNodes = testNodeList.item(i).getChildNodes();
			String desc = "";
			String timestamp = "";
			String status = "";
			String subjective = "";
			for(int j = 0; j < childNodes.getLength(); j++){
			    if (childNodes.item(j).getNodeType() == Node.ELEMENT_NODE){
				if (childNodes.item(j).getNodeName().equalsIgnoreCase("desc")){
				    desc = childNodes.item(j).getTextContent();
				}else if (childNodes.item(j).getNodeName().equalsIgnoreCase("timestamp")){
				    timestamp = childNodes.item(j).getTextContent();
				}else if (childNodes.item(j).getNodeName().equalsIgnoreCase("status")){
				    status = childNodes.item(j).getTextContent();
				}else if (childNodes.item(j).getNodeName().equalsIgnoreCase("subjective")){
				    subjective = childNodes.item(j).getTextContent();
				}

			    }
			}
			YGIBTestResult returnYGIBDeviceTest = new YGIBTestResult();
			returnYGIBDeviceTest.setDesc(desc);
			returnYGIBDeviceTest.setFail(Integer.parseInt(fail));
			returnYGIBDeviceTest.setOther(Integer.parseInt(other));
			returnYGIBDeviceTest.setPass(Integer.parseInt(pass));
			returnYGIBDeviceTest.setStatus(Integer.parseInt(status));
			returnYGIBDeviceTest.setSubjective(subjective);
			returnYGIBDeviceTest.setTestId(id);
			if (!org.apache.commons.lang.StringUtils.isBlank(timestamp)){

			    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HHmmssZ");
			    try{
				Date date = formatter.parse(timestamp.replaceAll(":", ""));
				returnYGIBDeviceTest.setTimestamp(date);
			    }catch(ParseException e){
				logger.error("Error in date parse - " + timestamp, e);
				logger.info("Unable to convert time stamp = " + timestamp
					+ " to date so setting it as null");
				returnYGIBDeviceTest.setTimestamp(null);
			    }
			}
			testResultList.add(returnYGIBDeviceTest);
		    }
		}
		testResult.setTestResults(testResultList);
	    }
	}catch(Exception e){
	    logger.error(DrpConstants.EXTERNAL_SERVICE_CALL_ERROR + "TRIAGE : YGIB Tech Checker Service Exception", e);
	    throw new ServiceException(IseExceptionCodeEnum.ExternalServiceException, e.getMessage());
	}
	logger.info("YGIB Response Object : " + testResult.toString());
	return testResult;
    }

    private Document getDeviceDom(YGIBDevice device) throws ParserConfigurationException {
	logger.info("Creating Request Dom object");
	DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
	DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	Document document = documentBuilder.newDocument();
	Element rootElement = (Element) document.createElement("get-device");
	if (device.getPhoneNo() != null){
	    rootElement.setAttribute("phone", device.getPhoneNo());
	}
	document.appendChild(rootElement);
	return document;

    }

}
