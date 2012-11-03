
package com.bestbuy.bbym.sales.transactioncheck.service.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckRequestType;
import com.bestbuy.bbym.sales.transactioncheck.component.v1.TransactionCheckResponseType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.sales.transactioncheck.service.v1 package. 
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

    private final static QName _TransactionCheckRequest_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/service/v1", "TransactionCheckRequest");
    private final static QName _TransactionCheckResponse_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/service/v1", "TransactionCheckResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.sales.transactioncheck.service.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionCheckRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/service/v1", name = "TransactionCheckRequest")
    public JAXBElement<TransactionCheckRequestType> createTransactionCheckRequest(TransactionCheckRequestType value) {
        return new JAXBElement<TransactionCheckRequestType>(_TransactionCheckRequest_QNAME, TransactionCheckRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionCheckResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/service/v1", name = "TransactionCheckResponse")
    public JAXBElement<TransactionCheckResponseType> createTransactionCheckResponse(TransactionCheckResponseType value) {
        return new JAXBElement<TransactionCheckResponseType>(_TransactionCheckResponse_QNAME, TransactionCheckResponseType.class, null, value);
    }

}
