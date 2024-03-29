
package com.bestbuy.bbym.ise.iseclientacdsshipping;

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
@WebServiceClient(name = "Shipping_v01.serviceagent", targetNamespace = "http://www.tsh.bestbuy.com/BusinessServices/Proxy/Shipping", wsdlLocation = "file:/C:/EclipseVirtualImage/Juno/workspace/DailyRhythmPortal/drp-ws-client/src/main/resources/wsdl/ACDS_Shipping/Shipping_v01.serviceagent.wsdl")
public class ShippingV01Serviceagent
    extends Service
{

    private final static URL SHIPPINGV01SERVICEAGENT_WSDL_LOCATION;
    private final static Logger logger = Logger.getLogger(com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingV01Serviceagent.class.getName());

    static {
        URL url = null;
        try {
            URL baseUrl;
            baseUrl = com.bestbuy.bbym.ise.iseclientacdsshipping.ShippingV01Serviceagent.class.getResource(".");
            url = new URL(baseUrl, "file:/C:/EclipseVirtualImage/Juno/workspace/DailyRhythmPortal/drp-ws-client/src/main/resources/wsdl/ACDS_Shipping/Shipping_v01.serviceagent.wsdl");
        } catch (MalformedURLException e) {
            logger.warning("Failed to create URL for the wsdl Location: 'file:/C:/EclipseVirtualImage/Juno/workspace/DailyRhythmPortal/drp-ws-client/src/main/resources/wsdl/ACDS_Shipping/Shipping_v01.serviceagent.wsdl', retrying as a local file");
            logger.warning(e.getMessage());
        }
        SHIPPINGV01SERVICEAGENT_WSDL_LOCATION = url;
    }

    public ShippingV01Serviceagent(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ShippingV01Serviceagent() {
        super(SHIPPINGV01SERVICEAGENT_WSDL_LOCATION, new QName("http://www.tsh.bestbuy.com/BusinessServices/Proxy/Shipping", "Shipping_v01.serviceagent"));
    }

    /**
     * 
     * @return
     *     returns ShippingPortType
     */
    @WebEndpoint(name = "ShippingPortType")
    public ShippingPortType getShippingPortType() {
        return super.getPort(new QName("http://www.tsh.bestbuy.com/BusinessServices/Proxy/Shipping", "ShippingPortType"), ShippingPortType.class);
    }

}
