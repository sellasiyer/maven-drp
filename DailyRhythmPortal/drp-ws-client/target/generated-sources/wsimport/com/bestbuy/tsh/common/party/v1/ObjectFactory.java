
package com.bestbuy.tsh.common.party.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.common.party.v1 package. 
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

    private final static QName _AccountID_QNAME = new QName("http://www.tsh.bestbuy.com/common/party/v1", "AccountID");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.common.party.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PartyType }
     * 
     */
    public PartyType createPartyType() {
        return new PartyType();
    }

    /**
     * Create an instance of {@link PartyIDs }
     * 
     */
    public PartyIDs createPartyIDs() {
        return new PartyIDs();
    }

    /**
     * Create an instance of {@link Contact }
     * 
     */
    public Contact createContact() {
        return new Contact();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/party/v1", name = "AccountID")
    public JAXBElement<IdentifierType> createAccountID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_AccountID_QNAME, IdentifierType.class, null, value);
    }

}
