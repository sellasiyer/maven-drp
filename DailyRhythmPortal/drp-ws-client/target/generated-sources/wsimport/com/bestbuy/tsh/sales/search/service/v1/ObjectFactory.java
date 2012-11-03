
package com.bestbuy.tsh.sales.search.service.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.sales.common.components.v1.SearchTransactionsRequestType;
import com.bestbuy.tsh.sales.common.components.v1.SearchTransactionsResponseType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.sales.search.service.v1 package. 
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

    private final static QName _SearchTransactionsRequest_QNAME = new QName("http://www.tsh.bestbuy.com/sales/search/service/v1", "SearchTransactionsRequest");
    private final static QName _SearchTransactionsResponse_QNAME = new QName("http://www.tsh.bestbuy.com/sales/search/service/v1", "SearchTransactionsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.sales.search.service.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchTransactionsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/search/service/v1", name = "SearchTransactionsRequest")
    public JAXBElement<SearchTransactionsRequestType> createSearchTransactionsRequest(SearchTransactionsRequestType value) {
        return new JAXBElement<SearchTransactionsRequestType>(_SearchTransactionsRequest_QNAME, SearchTransactionsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchTransactionsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/search/service/v1", name = "SearchTransactionsResponse")
    public JAXBElement<SearchTransactionsResponseType> createSearchTransactionsResponse(SearchTransactionsResponseType value) {
        return new JAXBElement<SearchTransactionsResponseType>(_SearchTransactionsResponse_QNAME, SearchTransactionsResponseType.class, null, value);
    }

}
