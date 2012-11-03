
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.sales.transactioncheck.component.v1 package. 
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

    private final static QName _RuleSet_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "RuleSet");
    private final static QName _NameValuePair_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "NameValuePair");
    private final static QName _Customer_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "Customer");
    private final static QName _ItemizedRuleResult_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "ItemizedRuleResult");
    private final static QName _RuleList_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "RuleList");
    private final static QName _ItemizedRuleResultList_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "ItemizedRuleResultList");
    private final static QName _TransactionCheckResultHeader_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "TransactionCheckResultHeader");
    private final static QName _TransationSourceDetail_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "TransationSourceDetail");
    private final static QName _NameValuePairList_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/component/v1", "NameValuePairList");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.sales.transactioncheck.component.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CommunicationType }
     * 
     */
    public CommunicationType createCommunicationType() {
        return new CommunicationType();
    }

    /**
     * Create an instance of {@link RuleListType }
     * 
     */
    public RuleListType createRuleListType() {
        return new RuleListType();
    }

    /**
     * Create an instance of {@link NameValuePairType }
     * 
     */
    public NameValuePairType createNameValuePairType() {
        return new NameValuePairType();
    }

    /**
     * Create an instance of {@link RuleSetType }
     * 
     */
    public RuleSetType createRuleSetType() {
        return new RuleSetType();
    }

    /**
     * Create an instance of {@link TransationSourceDetailType }
     * 
     */
    public TransationSourceDetailType createTransationSourceDetailType() {
        return new TransationSourceDetailType();
    }

    /**
     * Create an instance of {@link HeaderType }
     * 
     */
    public HeaderType createHeaderType() {
        return new HeaderType();
    }

    /**
     * Create an instance of {@link TransactionCheckResponseType }
     * 
     */
    public TransactionCheckResponseType createTransactionCheckResponseType() {
        return new TransactionCheckResponseType();
    }

    /**
     * Create an instance of {@link ItemizedRuleResultType }
     * 
     */
    public ItemizedRuleResultType createItemizedRuleResultType() {
        return new ItemizedRuleResultType();
    }

    /**
     * Create an instance of {@link CustomerType }
     * 
     */
    public CustomerType createCustomerType() {
        return new CustomerType();
    }

    /**
     * Create an instance of {@link NameValuePairListType }
     * 
     */
    public NameValuePairListType createNameValuePairListType() {
        return new NameValuePairListType();
    }

    /**
     * Create an instance of {@link TransactionCheckResultType }
     * 
     */
    public TransactionCheckResultType createTransactionCheckResultType() {
        return new TransactionCheckResultType();
    }

    /**
     * Create an instance of {@link TransactionCheckType }
     * 
     */
    public TransactionCheckType createTransactionCheckType() {
        return new TransactionCheckType();
    }

    /**
     * Create an instance of {@link LineItemListType }
     * 
     */
    public LineItemListType createLineItemListType() {
        return new LineItemListType();
    }

    /**
     * Create an instance of {@link TransactionBaseType }
     * 
     */
    public TransactionBaseType createTransactionBaseType() {
        return new TransactionBaseType();
    }

    /**
     * Create an instance of {@link TransactionCheckResultHeaderType }
     * 
     */
    public TransactionCheckResultHeaderType createTransactionCheckResultHeaderType() {
        return new TransactionCheckResultHeaderType();
    }

    /**
     * Create an instance of {@link TransactionCheckRequestType }
     * 
     */
    public TransactionCheckRequestType createTransactionCheckRequestType() {
        return new TransactionCheckRequestType();
    }

    /**
     * Create an instance of {@link ItemizedRuleResultListType }
     * 
     */
    public ItemizedRuleResultListType createItemizedRuleResultListType() {
        return new ItemizedRuleResultListType();
    }

    /**
     * Create an instance of {@link LineItemType }
     * 
     */
    public LineItemType createLineItemType() {
        return new LineItemType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RuleSetType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "RuleSet")
    public JAXBElement<RuleSetType> createRuleSet(RuleSetType value) {
        return new JAXBElement<RuleSetType>(_RuleSet_QNAME, RuleSetType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameValuePairType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "NameValuePair")
    public JAXBElement<NameValuePairType> createNameValuePair(NameValuePairType value) {
        return new JAXBElement<NameValuePairType>(_NameValuePair_QNAME, NameValuePairType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CustomerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "Customer")
    public JAXBElement<CustomerType> createCustomer(CustomerType value) {
        return new JAXBElement<CustomerType>(_Customer_QNAME, CustomerType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemizedRuleResultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "ItemizedRuleResult")
    public JAXBElement<ItemizedRuleResultType> createItemizedRuleResult(ItemizedRuleResultType value) {
        return new JAXBElement<ItemizedRuleResultType>(_ItemizedRuleResult_QNAME, ItemizedRuleResultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RuleListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "RuleList")
    public JAXBElement<RuleListType> createRuleList(RuleListType value) {
        return new JAXBElement<RuleListType>(_RuleList_QNAME, RuleListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ItemizedRuleResultListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "ItemizedRuleResultList")
    public JAXBElement<ItemizedRuleResultListType> createItemizedRuleResultList(ItemizedRuleResultListType value) {
        return new JAXBElement<ItemizedRuleResultListType>(_ItemizedRuleResultList_QNAME, ItemizedRuleResultListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionCheckResultHeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "TransactionCheckResultHeader")
    public JAXBElement<TransactionCheckResultHeaderType> createTransactionCheckResultHeader(TransactionCheckResultHeaderType value) {
        return new JAXBElement<TransactionCheckResultHeaderType>(_TransactionCheckResultHeader_QNAME, TransactionCheckResultHeaderType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransationSourceDetailType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "TransationSourceDetail")
    public JAXBElement<TransationSourceDetailType> createTransationSourceDetail(TransationSourceDetailType value) {
        return new JAXBElement<TransationSourceDetailType>(_TransationSourceDetail_QNAME, TransationSourceDetailType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameValuePairListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/component/v1", name = "NameValuePairList")
    public JAXBElement<NameValuePairListType> createNameValuePairList(NameValuePairListType value) {
        return new JAXBElement<NameValuePairListType>(_NameValuePairList_QNAME, NameValuePairListType.class, null, value);
    }

}
