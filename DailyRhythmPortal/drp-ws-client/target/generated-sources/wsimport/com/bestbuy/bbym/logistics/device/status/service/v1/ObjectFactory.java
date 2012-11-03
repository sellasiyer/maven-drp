
package com.bestbuy.bbym.logistics.device.status.service.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.logistics.device.status.service.v1 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateDeviceStatusRequest_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/service/v1", "UpdateDeviceStatusRequest");
    private final static QName _UpdateDeviceStatusResponse_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/service/v1", "UpdateDeviceStatusResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.logistics.device.status.service.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateDeviceStatusResponseType }
     * 
     */
    public UpdateDeviceStatusResponseType createUpdateDeviceStatusResponseType() {
        return new UpdateDeviceStatusResponseType();
    }

    /**
     * Create an instance of {@link UpdateDeviceStatusRequestType }
     * 
     */
    public UpdateDeviceStatusRequestType createUpdateDeviceStatusRequestType() {
        return new UpdateDeviceStatusRequestType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDeviceStatusRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/service/v1", name = "UpdateDeviceStatusRequest")
    public JAXBElement<UpdateDeviceStatusRequestType> createUpdateDeviceStatusRequest(UpdateDeviceStatusRequestType value) {
        return new JAXBElement<UpdateDeviceStatusRequestType>(_UpdateDeviceStatusRequest_QNAME, UpdateDeviceStatusRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDeviceStatusResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/service/v1", name = "UpdateDeviceStatusResponse")
    public JAXBElement<UpdateDeviceStatusResponseType> createUpdateDeviceStatusResponse(UpdateDeviceStatusResponseType value) {
        return new JAXBElement<UpdateDeviceStatusResponseType>(_UpdateDeviceStatusResponse_QNAME, UpdateDeviceStatusResponseType.class, null, value);
    }

}
