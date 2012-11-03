
package com.bestbuy.tsh.tsh.tshfault;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.tsh.tshfault package. 
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

    private final static QName _TSHFault_QNAME = new QName("http://tsh.bestbuy.com/tsh/tshfault", "TSHFault");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.tsh.tshfault
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TSHFaultType }
     * 
     */
    public TSHFaultType createTSHFaultType() {
        return new TSHFaultType();
    }

    /**
     * Create an instance of {@link ServiceDetailsType }
     * 
     */
    public ServiceDetailsType createServiceDetailsType() {
        return new ServiceDetailsType();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSHFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tsh.bestbuy.com/tsh/tshfault", name = "TSHFault")
    public JAXBElement<TSHFaultType> createTSHFault(TSHFaultType value) {
        return new JAXBElement<TSHFaultType>(_TSHFault_QNAME, TSHFaultType.class, null, value);
    }

}
