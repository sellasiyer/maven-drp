
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.sales.common.components.v1 package. 
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

    private final static QName _Transaction_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/components/v1", "Transaction");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.sales.common.components.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CustomerType }
     * 
     */
    public CustomerType createCustomerType() {
        return new CustomerType();
    }

    /**
     * Create an instance of {@link TransactionPaymentType }
     * 
     */
    public TransactionPaymentType createTransactionPaymentType() {
        return new TransactionPaymentType();
    }

    /**
     * Create an instance of {@link SearchTransactionsRequestType }
     * 
     */
    public SearchTransactionsRequestType createSearchTransactionsRequestType() {
        return new SearchTransactionsRequestType();
    }

    /**
     * Create an instance of {@link AccountType }
     * 
     */
    public AccountType createAccountType() {
        return new AccountType();
    }

    /**
     * Create an instance of {@link TransactionLineItemType }
     * 
     */
    public TransactionLineItemType createTransactionLineItemType() {
        return new TransactionLineItemType();
    }

    /**
     * Create an instance of {@link LoyaltyInfoType }
     * 
     */
    public LoyaltyInfoType createLoyaltyInfoType() {
        return new LoyaltyInfoType();
    }

    /**
     * Create an instance of {@link TransactionKeyType }
     * 
     */
    public TransactionKeyType createTransactionKeyType() {
        return new TransactionKeyType();
    }

    /**
     * Create an instance of {@link RetrieveTransactionResponseType }
     * 
     */
    public RetrieveTransactionResponseType createRetrieveTransactionResponseType() {
        return new RetrieveTransactionResponseType();
    }

    /**
     * Create an instance of {@link TransactionType }
     * 
     */
    public TransactionType createTransactionType() {
        return new TransactionType();
    }

    /**
     * Create an instance of {@link TaxAreaType }
     * 
     */
    public TaxAreaType createTaxAreaType() {
        return new TaxAreaType();
    }

    /**
     * Create an instance of {@link LineItemSourceType }
     * 
     */
    public LineItemSourceType createLineItemSourceType() {
        return new LineItemSourceType();
    }

    /**
     * Create an instance of {@link ProductCategoryType }
     * 
     */
    public ProductCategoryType createProductCategoryType() {
        return new ProductCategoryType();
    }

    /**
     * Create an instance of {@link SearchConfigType }
     * 
     */
    public SearchConfigType createSearchConfigType() {
        return new SearchConfigType();
    }

    /**
     * Create an instance of {@link SearchTransactionsResponseType }
     * 
     */
    public SearchTransactionsResponseType createSearchTransactionsResponseType() {
        return new SearchTransactionsResponseType();
    }

    /**
     * Create an instance of {@link RelatedLineItemType }
     * 
     */
    public RelatedLineItemType createRelatedLineItemType() {
        return new RelatedLineItemType();
    }

    /**
     * Create an instance of {@link TransactionContractType }
     * 
     */
    public TransactionContractType createTransactionContractType() {
        return new TransactionContractType();
    }

    /**
     * Create an instance of {@link RequestMetaDataExtensionType }
     * 
     */
    public RequestMetaDataExtensionType createRequestMetaDataExtensionType() {
        return new RequestMetaDataExtensionType();
    }

    /**
     * Create an instance of {@link RetrieveTransactionRequestType }
     * 
     */
    public RetrieveTransactionRequestType createRetrieveTransactionRequestType() {
        return new RetrieveTransactionRequestType();
    }

    /**
     * Create an instance of {@link SystemStatusType }
     * 
     */
    public SystemStatusType createSystemStatusType() {
        return new SystemStatusType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link SearchParameterType }
     * 
     */
    public SearchParameterType createSearchParameterType() {
        return new SearchParameterType();
    }

    /**
     * Create an instance of {@link TransactionHeaderType }
     * 
     */
    public TransactionHeaderType createTransactionHeaderType() {
        return new TransactionHeaderType();
    }

    /**
     * Create an instance of {@link ItemType }
     * 
     */
    public ItemType createItemType() {
        return new ItemType();
    }

    /**
     * Create an instance of {@link TaxAttributesType }
     * 
     */
    public TaxAttributesType createTaxAttributesType() {
        return new TaxAttributesType();
    }

    /**
     * Create an instance of {@link TransactionDetailConfigType }
     * 
     */
    public TransactionDetailConfigType createTransactionDetailConfigType() {
        return new TransactionDetailConfigType();
    }

    /**
     * Create an instance of {@link LineItemDetailsType }
     * 
     */
    public LineItemDetailsType createLineItemDetailsType() {
        return new LineItemDetailsType();
    }

    /**
     * Create an instance of {@link ECTransactionIDRangeType }
     * 
     */
    public ECTransactionIDRangeType createECTransactionIDRangeType() {
        return new ECTransactionIDRangeType();
    }

    /**
     * Create an instance of {@link ShipToLocationType }
     * 
     */
    public ShipToLocationType createShipToLocationType() {
        return new ShipToLocationType();
    }

    /**
     * Create an instance of {@link TaxItemType }
     * 
     */
    public TaxItemType createTaxItemType() {
        return new TaxItemType();
    }

    /**
     * Create an instance of {@link CustomerInfoType }
     * 
     */
    public CustomerInfoType createCustomerInfoType() {
        return new CustomerInfoType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/components/v1", name = "Transaction")
    public JAXBElement<TransactionType> createTransaction(TransactionType value) {
        return new JAXBElement<TransactionType>(_Transaction_QNAME, TransactionType.class, null, value);
    }

}
