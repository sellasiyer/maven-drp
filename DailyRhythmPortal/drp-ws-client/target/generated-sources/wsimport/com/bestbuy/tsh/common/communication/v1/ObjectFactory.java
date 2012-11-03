
package com.bestbuy.tsh.common.communication.v1;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.common.communication.v1 package. 
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

    private final static QName _Phone_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "Phone");
    private final static QName _DialNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "DialNumber");
    private final static QName _EmailID_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "EmailID");
    private final static QName _CoutryDialingCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "CoutryDialingCode");
    private final static QName _Extension_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "Extension");
    private final static QName _CountryDialing_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "CountryDialing");
    private final static QName _AreaDialing_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "AreaDialing");
    private final static QName _EMail_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "EMail");
    private final static QName _Communication_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "Communication");
    private final static QName _AccessCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "AccessCode");
    private final static QName _AreaCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/communication/v1", "AreaCode");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.common.communication.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PhoneType }
     * 
     */
    public PhoneType createPhoneType() {
        return new PhoneType();
    }

    /**
     * Create an instance of {@link EmailType }
     * 
     */
    public EmailType createEmailType() {
        return new EmailType();
    }

    /**
     * Create an instance of {@link CommunicationBaseType }
     * 
     */
    public CommunicationBaseType createCommunicationBaseType() {
        return new CommunicationBaseType();
    }

    /**
     * Create an instance of {@link EMailBaseType }
     * 
     */
    public EMailBaseType createEMailBaseType() {
        return new EMailBaseType();
    }

    /**
     * Create an instance of {@link PhoneBaseType }
     * 
     */
    public PhoneBaseType createPhoneBaseType() {
        return new PhoneBaseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PhoneBaseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "Phone")
    public JAXBElement<PhoneBaseType> createPhone(PhoneBaseType value) {
        return new JAXBElement<PhoneBaseType>(_Phone_QNAME, PhoneBaseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "DialNumber")
    public JAXBElement<TextType> createDialNumber(TextType value) {
        return new JAXBElement<TextType>(_DialNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "EmailID")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createEmailID(String value) {
        return new JAXBElement<String>(_EmailID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "CoutryDialingCode")
    public JAXBElement<BigInteger> createCoutryDialingCode(BigInteger value) {
        return new JAXBElement<BigInteger>(_CoutryDialingCode_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "Extension")
    public JAXBElement<TextType> createExtension(TextType value) {
        return new JAXBElement<TextType>(_Extension_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "CountryDialing")
    public JAXBElement<TextType> createCountryDialing(TextType value) {
        return new JAXBElement<TextType>(_CountryDialing_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "AreaDialing")
    public JAXBElement<TextType> createAreaDialing(TextType value) {
        return new JAXBElement<TextType>(_AreaDialing_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EMailBaseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "EMail")
    public JAXBElement<EMailBaseType> createEMail(EMailBaseType value) {
        return new JAXBElement<EMailBaseType>(_EMail_QNAME, EMailBaseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommunicationBaseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "Communication")
    public JAXBElement<CommunicationBaseType> createCommunication(CommunicationBaseType value) {
        return new JAXBElement<CommunicationBaseType>(_Communication_QNAME, CommunicationBaseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "AccessCode")
    public JAXBElement<TextType> createAccessCode(TextType value) {
        return new JAXBElement<TextType>(_AccessCode_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/communication/v1", name = "AreaCode")
    public JAXBElement<BigInteger> createAreaCode(BigInteger value) {
        return new JAXBElement<BigInteger>(_AreaCode_QNAME, BigInteger.class, null, value);
    }

}
