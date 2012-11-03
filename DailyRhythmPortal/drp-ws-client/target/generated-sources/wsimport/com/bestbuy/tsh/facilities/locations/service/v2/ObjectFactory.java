
package com.bestbuy.tsh.facilities.locations.service.v2;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.facilities.locations.fields.v2.FindLocationRequestType;
import com.bestbuy.tsh.facilities.locations.fields.v2.FindLocationResponseType;
import com.bestbuy.tsh.facilities.locations.fields.v2.RetrieveLocationsRequestType;
import com.bestbuy.tsh.facilities.locations.fields.v2.RetrieveLocationsResponseType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.facilities.locations.service.v2 package. 
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

    private final static QName _FindLocationResponse_QNAME = new QName("http://www.tsh.bestbuy.com/facilities/locations/service/v2", "FindLocationResponse");
    private final static QName _RetrieveLocationsRequest_QNAME = new QName("http://www.tsh.bestbuy.com/facilities/locations/service/v2", "RetrieveLocationsRequest");
    private final static QName _FindLocationRequest_QNAME = new QName("http://www.tsh.bestbuy.com/facilities/locations/service/v2", "FindLocationRequest");
    private final static QName _RetrieveLocationsResponse_QNAME = new QName("http://www.tsh.bestbuy.com/facilities/locations/service/v2", "RetrieveLocationsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.facilities.locations.service.v2
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindLocationResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/facilities/locations/service/v2", name = "FindLocationResponse")
    public JAXBElement<FindLocationResponseType> createFindLocationResponse(FindLocationResponseType value) {
        return new JAXBElement<FindLocationResponseType>(_FindLocationResponse_QNAME, FindLocationResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveLocationsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/facilities/locations/service/v2", name = "RetrieveLocationsRequest")
    public JAXBElement<RetrieveLocationsRequestType> createRetrieveLocationsRequest(RetrieveLocationsRequestType value) {
        return new JAXBElement<RetrieveLocationsRequestType>(_RetrieveLocationsRequest_QNAME, RetrieveLocationsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindLocationRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/facilities/locations/service/v2", name = "FindLocationRequest")
    public JAXBElement<FindLocationRequestType> createFindLocationRequest(FindLocationRequestType value) {
        return new JAXBElement<FindLocationRequestType>(_FindLocationRequest_QNAME, FindLocationRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RetrieveLocationsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/facilities/locations/service/v2", name = "RetrieveLocationsResponse")
    public JAXBElement<RetrieveLocationsResponseType> createRetrieveLocationsResponse(RetrieveLocationsResponseType value) {
        return new JAXBElement<RetrieveLocationsResponseType>(_RetrieveLocationsResponse_QNAME, RetrieveLocationsResponseType.class, null, value);
    }

}
