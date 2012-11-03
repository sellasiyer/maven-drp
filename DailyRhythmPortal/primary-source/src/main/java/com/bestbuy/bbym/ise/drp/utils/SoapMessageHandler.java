package com.bestbuy.bbym.ise.drp.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bestbuy.bbym.ise.drp.service.DrpPropertyService;
import com.bestbuy.bbym.ise.exception.ServiceException;

/**
 * Logs web service requests and results.
 * 
 * @author a904002
 */
@Service
public class SoapMessageHandler implements SOAPHandler<SOAPMessageContext> {

    private static Logger logger = LoggerFactory.getLogger(SoapMessageHandler.class);

    @Autowired
    @Qualifier("drpPropertyService")
    private DrpPropertyService drpPropertiesService;

    @Override
    public boolean handleMessage(SOAPMessageContext messageContext) {
	writeToLog(messageContext);
	return true;
    }

    public Set<QName> getHeaders() {
	return Collections.emptySet();
    }

    @Override
    public boolean handleFault(SOAPMessageContext messageContext) {
	writeToLog(messageContext);
	return true;
    }

    @Override
    public void close(MessageContext context) {
    }

    private void writeToLog(SOAPMessageContext messageContext) {

	try{
	    if (!"TRUE".equalsIgnoreCase(drpPropertiesService.getProperty("DISPLAY_SERVICE_XMLS"))){
		return;
	    }
	}catch(ServiceException e){
	    logger.error("Could not determine whether to log web service call details", e);
	    return;
	}

	String elementName = "unknown";
	try{
	    SOAPMessage msg = messageContext.getMessage();
	    SOAPBody sb = msg.getSOAPBody();
	    Iterator<SOAPElement> em = sb.getChildElements();

	    while(em.hasNext()){
		SOAPElement e = em.next();
		if (e.getLocalName() != null){
		    elementName = e.getLocalName().toUpperCase();
		}
		if (e.getElementQName().getNamespaceURI().equals(
			"http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4")){
		    e.setElementQName(new QName(e.getElementName().getLocalName()));
		}

	    }

	    ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
	    msg.writeTo(byteOutput);

	    logger.info(elementName + " : " + byteOutput);

	}catch(SOAPException e){
	    logger.error(elementName + " : " + e.getMessage());
	}catch(IOException e){
	    logger.error(e.getMessage());
	}
    }
}
