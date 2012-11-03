
package com.example.xmlns._1299800422009;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.1.7-b01-
 * Generated source version: 2.0
 * 
 */
@WebServiceClient(name = "SearchTransactionService", targetNamespace = "http://xmlns.example.com/1299800422009", wsdlLocation = "file:/C:/EclipseVirtualImage/Juno/workspace/DailyRhythmPortal/drp-ws-client/src/main/resources/wsdl/EC/SearchTransactionServiceSoap.wsdl")
public class SearchTransactionService
    extends Service
{

    private final static URL SEARCHTRANSACTIONSERVICE_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.example.xmlns._1299800422009.SearchTransactionService.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.example.xmlns._1299800422009.SearchTransactionService.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/EclipseVirtualImage/Juno/workspace/DailyRhythmPortal/drp-ws-client/src/main/resources/wsdl/EC/SearchTransactionServiceSoap.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/EclipseVirtualImage/Juno/workspace/DailyRhythmPortal/drp-ws-client/src/main/resources/wsdl/EC/SearchTransactionServiceSoap.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        SEARCHTRANSACTIONSERVICE_WSDL_LOCATION = url;
    }

    public SearchTransactionService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public SearchTransactionService() {
        super(SEARCHTRANSACTIONSERVICE_WSDL_LOCATION, new QName("http://xmlns.example.com/1299800422009", "SearchTransactionService"));
    }

    /**
     * 
     * @return
     *     returns PortType
     */
    @WebEndpoint(name = "SearchTransactionSOAPHTTP")
    public PortType getSearchTransactionSOAPHTTP() {
        return super.getPort(new QName("http://xmlns.example.com/1299800422009", "SearchTransactionSOAPHTTP"), PortType.class);
    }

}