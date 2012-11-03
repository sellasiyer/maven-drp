
package com.bestbuy.tsh.common.datatype.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.common.datatype.v1 package. 
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

    private final static QName _ENTERNAMEOFROOTELEMENTHERE_QNAME = new QName("http://www.tsh.bestbuy.com/common/datatype/v1", "ENTER_NAME_OF_ROOT_ELEMENT_HERE");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.common.datatype.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FlagType }
     * 
     */
    public FlagType createFlagType() {
        return new FlagType();
    }

    /**
     * Create an instance of {@link TextType }
     * 
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link CodeType }
     * 
     */
    public CodeType createCodeType() {
        return new CodeType();
    }

    /**
     * Create an instance of {@link UserAreaType }
     * 
     */
    public UserAreaType createUserAreaType() {
        return new UserAreaType();
    }

    /**
     * Create an instance of {@link KeyType }
     * 
     */
    public KeyType createKeyType() {
        return new KeyType();
    }

    /**
     * Create an instance of {@link NameValueType }
     * 
     */
    public NameValueType createNameValueType() {
        return new NameValueType();
    }

    /**
     * Create an instance of {@link IdentifierType }
     * 
     */
    public IdentifierType createIdentifierType() {
        return new IdentifierType();
    }

    /**
     * Create an instance of {@link MeasureType }
     * 
     */
    public MeasureType createMeasureType() {
        return new MeasureType();
    }

    /**
     * Create an instance of {@link NameType }
     * 
     */
    public NameType createNameType() {
        return new NameType();
    }

    /**
     * Create an instance of {@link SequencedTextType }
     * 
     */
    public SequencedTextType createSequencedTextType() {
        return new SequencedTextType();
    }

    /**
     * Create an instance of {@link SequencedNameType }
     * 
     */
    public SequencedNameType createSequencedNameType() {
        return new SequencedNameType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/datatype/v1", name = "ENTER_NAME_OF_ROOT_ELEMENT_HERE")
    public JAXBElement<Object> createENTERNAMEOFROOTELEMENTHERE(Object value) {
        return new JAXBElement<Object>(_ENTERNAMEOFROOTELEMENTHERE_QNAME, Object.class, null, value);
    }

}
