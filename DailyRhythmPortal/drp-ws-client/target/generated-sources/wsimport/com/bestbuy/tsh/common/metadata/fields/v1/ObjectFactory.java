
package com.bestbuy.tsh.common.metadata.fields.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.common.metadata.fields.v1 package. 
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

    private final static QName _SourceID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "SourceID");
    private final static QName _ResponseTimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "ResponseTimeStamp");
    private final static QName _Brand_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Brand");
    private final static QName _Channel_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Channel");
    private final static QName _Version_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Version");
    private final static QName _ProgramID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "ProgramID");
    private final static QName _TradingArea_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "TradingArea");
    private final static QName _MessageID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "MessageID");
    private final static QName _UserID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "UserID");
    private final static QName _Environment_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Environment");
    private final static QName _RequestTimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "RequestTimeStamp");
    private final static QName _Company_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Company");
    private final static QName _BusinessUnit_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "BusinessUnit");
    private final static QName _Enterprise_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Enterprise");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.common.metadata.fields.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "SourceID")
    public JAXBElement<String> createSourceID(String value) {
        return new JAXBElement<String>(_SourceID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "ResponseTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createResponseTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ResponseTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Brand")
    public JAXBElement<String> createBrand(String value) {
        return new JAXBElement<String>(_Brand_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Channel")
    public JAXBElement<String> createChannel(String value) {
        return new JAXBElement<String>(_Channel_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Version")
    public JAXBElement<Integer> createVersion(Integer value) {
        return new JAXBElement<Integer>(_Version_QNAME, Integer.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "ProgramID")
    public JAXBElement<String> createProgramID(String value) {
        return new JAXBElement<String>(_ProgramID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "TradingArea")
    public JAXBElement<String> createTradingArea(String value) {
        return new JAXBElement<String>(_TradingArea_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "MessageID")
    public JAXBElement<String> createMessageID(String value) {
        return new JAXBElement<String>(_MessageID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "UserID")
    public JAXBElement<String> createUserID(String value) {
        return new JAXBElement<String>(_UserID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Environment")
    public JAXBElement<String> createEnvironment(String value) {
        return new JAXBElement<String>(_Environment_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "RequestTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createRequestTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RequestTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Company")
    public JAXBElement<String> createCompany(String value) {
        return new JAXBElement<String>(_Company_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "BusinessUnit")
    public JAXBElement<String> createBusinessUnit(String value) {
        return new JAXBElement<String>(_BusinessUnit_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Enterprise")
    public JAXBElement<String> createEnterprise(String value) {
        return new JAXBElement<String>(_Enterprise_QNAME, String.class, null, value);
    }

}
