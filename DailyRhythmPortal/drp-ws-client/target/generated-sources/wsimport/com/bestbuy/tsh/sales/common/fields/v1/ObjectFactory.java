
package com.bestbuy.tsh.sales.common.fields.v1;

import java.math.BigDecimal;
import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.sales.common.fields.v1 package. 
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

    private final static QName _MarketPlaceIndicator_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "MarketPlaceIndicator");
    private final static QName _TaxAreaID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TaxAreaID");
    private final static QName _JurisdictionCode_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "JurisdictionCode");
    private final static QName _PlannedDeliveryDate_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "PlannedDeliveryDate");
    private final static QName _AccountCategory_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "AccountCategory");
    private final static QName _RetrieveAllChildTransactions_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveAllChildTransactions");
    private final static QName _TECCard_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TECCard");
    private final static QName _TransactionSourceSystem_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionSourceSystem");
    private final static QName _TransactionLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionLineNumber");
    private final static QName _SettlementKey_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SettlementKey");
    private final static QName _TransactionAccountNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionAccountNumber");
    private final static QName _PurchasedEmployeeID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "PurchasedEmployeeID");
    private final static QName _RetrievePayments_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrievePayments");
    private final static QName _EndDate_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "EndDate");
    private final static QName _RPIMFlag_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RPIMFlag");
    private final static QName _SourceSystemTransactionLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SourceSystemTransactionLineNumber");
    private final static QName _PaymentAmount_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "PaymentAmount");
    private final static QName _RelationshipType_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RelationshipType");
    private final static QName _Comments_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "Comments");
    private final static QName _AccountNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "AccountNumber");
    private final static QName _ProgramID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ProgramID");
    private final static QName _SkuDescription_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SkuDescription");
    private final static QName _ProductDescription_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ProductDescription");
    private final static QName _MaskDetails_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "MaskDetails");
    private final static QName _RetrieveAllParentTransactions_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveAllParentTransactions");
    private final static QName _SubClassName_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SubClassName");
    private final static QName _SubClassID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SubClassID");
    private final static QName _BBYUpdateTimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "BBYUpdateTimeStamp");
    private final static QName _SystemWarningCode_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SystemWarningCode");
    private final static QName _RebateSuppressFlag_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RebateSuppressFlag");
    private final static QName _TimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TimeStamp");
    private final static QName _SalePrice_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SalePrice");
    private final static QName _TransactionAccountCategory_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionAccountCategory");
    private final static QName _MaxRows_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "MaxRows");
    private final static QName _LineItemDetailType_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "LineItemDetailType");
    private final static QName _SearchTypeToPerform_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SearchTypeToPerform");
    private final static QName _SKU_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SKU");
    private final static QName _TransactionVersion_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionVersion");
    private final static QName _CreditCardFinancePlanID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "CreditCardFinancePlanID");
    private final static QName _ReshippedItem_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ReshippedItem");
    private final static QName _InquiryLevel_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "InquiryLevel");
    private final static QName _QuickOrderNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "QuickOrderNumber");
    private final static QName _UserID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "UserID");
    private final static QName _AverageCost_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "AverageCost");
    private final static QName _TransactionLineType_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionLineType");
    private final static QName _ECTransactionID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ECTransactionID");
    private final static QName _ECLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ECLineNumber");
    private final static QName _PimCode_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "PimCode");
    private final static QName _RetrieveLineTaxInfo_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveLineTaxInfo");
    private final static QName _ContractType_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ContractType");
    private final static QName _DeptID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "DeptID");
    private final static QName _TransactionDate_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionDate");
    private final static QName _ServerName_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ServerName");
    private final static QName _PCardIndicator_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "PCardIndicator");
    private final static QName _Name_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "Name");
    private final static QName _ContractDescription_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ContractDescription");
    private final static QName _Quantity_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "Quantity");
    private final static QName _EligibleFlag_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "EligibleFlag");
    private final static QName _RetrieveCustomerInfo_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveCustomerInfo");
    private final static QName _ProductSku_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ProductSku");
    private final static QName _RetrieveAllTransactionKeys_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveAllTransactionKeys");
    private final static QName _TransactionKeyType_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionKeyType");
    private final static QName _RetrieveRelatedTransactionLine_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveRelatedTransactionLine");
    private final static QName _RetrieveMasterItemDetails_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveMasterItemDetails");
    private final static QName _SourceSystemTransactionKey_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SourceSystemTransactionKey");
    private final static QName _PerformDrillDown_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "PerformDrillDown");
    private final static QName _SystemWarningMsg_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SystemWarningMsg");
    private final static QName _SourceSystemID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SourceSystemID");
    private final static QName _TransactionStatus_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionStatus");
    private final static QName _SerialNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SerialNumber");
    private final static QName _SourceLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SourceLineNumber");
    private final static QName _SearchTimeLimitMS_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SearchTimeLimitMS");
    private final static QName _StartECTransID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "StartECTransID");
    private final static QName _ContractSku_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ContractSku");
    private final static QName _DiscardResult_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "DiscardResult");
    private final static QName _ClassID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ClassID");
    private final static QName _ECAuditTimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ECAuditTimeStamp");
    private final static QName _SourceLineType_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SourceLineType");
    private final static QName _UnitQuantity_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "UnitQuantity");
    private final static QName _BBYCreateTimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "BBYCreateTimeStamp");
    private final static QName _TaxAreaState_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TaxAreaState");
    private final static QName _ECParentTransactionID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ECParentTransactionID");
    private final static QName _EmployeeID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "EmployeeID");
    private final static QName _SourceTransactionKey_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SourceTransactionKey");
    private final static QName _LineItemDetailKey_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "LineItemDetailKey");
    private final static QName _RetailPrice_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetailPrice");
    private final static QName _ExcludeRelatedSku_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ExcludeRelatedSku");
    private final static QName _RetrieveLineItemDetails_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveLineItemDetails");
    private final static QName _Environment_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "Environment");
    private final static QName _TaxRate_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TaxRate");
    private final static QName _TransactionType_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionType");
    private final static QName _TransactionKey_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionKey");
    private final static QName _TransactionLocation_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TransactionLocation");
    private final static QName _DeptName_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "DeptName");
    private final static QName _LoyaltyMemberLevelDesc_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "LoyaltyMemberLevelDesc");
    private final static QName _CustomerID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "CustomerID");
    private final static QName _LoyaltyMemberNumber_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "LoyaltyMemberNumber");
    private final static QName _EndECTransID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "EndECTransID");
    private final static QName _LoyaltyMemberLevel_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "LoyaltyMemberLevel");
    private final static QName _SkuPLUText_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SkuPLUText");
    private final static QName _TaxAmount_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "TaxAmount");
    private final static QName _ApplicationID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ApplicationID");
    private final static QName _SearchOnlyAuditedtransaction_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "SearchOnlyAuditedtransaction");
    private final static QName _StartDate_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "StartDate");
    private final static QName _RetrieveContracts_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "RetrieveContracts");
    private final static QName _ClassName_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ClassName");
    private final static QName _Rows_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "Rows");
    private final static QName _ContractID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "ContractID");
    private final static QName _LineItemDetailAmount_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "LineItemDetailAmount");
    private final static QName _MasterItemID_QNAME = new QName("http://www.tsh.bestbuy.com/sales/common/fields/v1", "MasterItemID");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.sales.common.fields.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "MarketPlaceIndicator")
    public JAXBElement<Boolean> createMarketPlaceIndicator(Boolean value) {
        return new JAXBElement<Boolean>(_MarketPlaceIndicator_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TaxAreaID")
    public JAXBElement<TextType> createTaxAreaID(TextType value) {
        return new JAXBElement<TextType>(_TaxAreaID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "JurisdictionCode")
    public JAXBElement<TextType> createJurisdictionCode(TextType value) {
        return new JAXBElement<TextType>(_JurisdictionCode_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "PlannedDeliveryDate")
    public JAXBElement<XMLGregorianCalendar> createPlannedDeliveryDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_PlannedDeliveryDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "AccountCategory")
    public JAXBElement<TextType> createAccountCategory(TextType value) {
        return new JAXBElement<TextType>(_AccountCategory_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveAllChildTransactions")
    public JAXBElement<Boolean> createRetrieveAllChildTransactions(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveAllChildTransactions_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TECCard")
    public JAXBElement<TextType> createTECCard(TextType value) {
        return new JAXBElement<TextType>(_TECCard_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionSourceSystem")
    public JAXBElement<TextType> createTransactionSourceSystem(TextType value) {
        return new JAXBElement<TextType>(_TransactionSourceSystem_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionLineNumber")
    public JAXBElement<TextType> createTransactionLineNumber(TextType value) {
        return new JAXBElement<TextType>(_TransactionLineNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SettlementKey")
    public JAXBElement<TextType> createSettlementKey(TextType value) {
        return new JAXBElement<TextType>(_SettlementKey_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionAccountNumber")
    public JAXBElement<TextType> createTransactionAccountNumber(TextType value) {
        return new JAXBElement<TextType>(_TransactionAccountNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "PurchasedEmployeeID")
    public JAXBElement<TextType> createPurchasedEmployeeID(TextType value) {
        return new JAXBElement<TextType>(_PurchasedEmployeeID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrievePayments")
    public JAXBElement<Boolean> createRetrievePayments(Boolean value) {
        return new JAXBElement<Boolean>(_RetrievePayments_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "EndDate")
    public JAXBElement<XMLGregorianCalendar> createEndDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_EndDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RPIMFlag")
    public JAXBElement<Boolean> createRPIMFlag(Boolean value) {
        return new JAXBElement<Boolean>(_RPIMFlag_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SourceSystemTransactionLineNumber")
    public JAXBElement<TextType> createSourceSystemTransactionLineNumber(TextType value) {
        return new JAXBElement<TextType>(_SourceSystemTransactionLineNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "PaymentAmount")
    public JAXBElement<BigDecimal> createPaymentAmount(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_PaymentAmount_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RelationshipType")
    public JAXBElement<TextType> createRelationshipType(TextType value) {
        return new JAXBElement<TextType>(_RelationshipType_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "Comments")
    public JAXBElement<TextType> createComments(TextType value) {
        return new JAXBElement<TextType>(_Comments_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "AccountNumber")
    public JAXBElement<TextType> createAccountNumber(TextType value) {
        return new JAXBElement<TextType>(_AccountNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ProgramID")
    public JAXBElement<TextType> createProgramID(TextType value) {
        return new JAXBElement<TextType>(_ProgramID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SkuDescription")
    public JAXBElement<TextType> createSkuDescription(TextType value) {
        return new JAXBElement<TextType>(_SkuDescription_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ProductDescription")
    public JAXBElement<TextType> createProductDescription(TextType value) {
        return new JAXBElement<TextType>(_ProductDescription_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "MaskDetails")
    public JAXBElement<Boolean> createMaskDetails(Boolean value) {
        return new JAXBElement<Boolean>(_MaskDetails_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveAllParentTransactions")
    public JAXBElement<Boolean> createRetrieveAllParentTransactions(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveAllParentTransactions_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SubClassName")
    public JAXBElement<TextType> createSubClassName(TextType value) {
        return new JAXBElement<TextType>(_SubClassName_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SubClassID")
    public JAXBElement<BigInteger> createSubClassID(BigInteger value) {
        return new JAXBElement<BigInteger>(_SubClassID_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "BBYUpdateTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createBBYUpdateTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_BBYUpdateTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SystemWarningCode")
    public JAXBElement<TextType> createSystemWarningCode(TextType value) {
        return new JAXBElement<TextType>(_SystemWarningCode_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RebateSuppressFlag")
    public JAXBElement<TextType> createRebateSuppressFlag(TextType value) {
        return new JAXBElement<TextType>(_RebateSuppressFlag_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TimeStamp")
    public JAXBElement<XMLGregorianCalendar> createTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SalePrice")
    public JAXBElement<Double> createSalePrice(Double value) {
        return new JAXBElement<Double>(_SalePrice_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionAccountCategory")
    public JAXBElement<TextType> createTransactionAccountCategory(TextType value) {
        return new JAXBElement<TextType>(_TransactionAccountCategory_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "MaxRows")
    public JAXBElement<BigInteger> createMaxRows(BigInteger value) {
        return new JAXBElement<BigInteger>(_MaxRows_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "LineItemDetailType")
    public JAXBElement<TextType> createLineItemDetailType(TextType value) {
        return new JAXBElement<TextType>(_LineItemDetailType_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SearchTypeToPerform")
    public JAXBElement<TextType> createSearchTypeToPerform(TextType value) {
        return new JAXBElement<TextType>(_SearchTypeToPerform_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SKU")
    public JAXBElement<IdentifierType> createSKU(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_SKU_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionVersion")
    public JAXBElement<TextType> createTransactionVersion(TextType value) {
        return new JAXBElement<TextType>(_TransactionVersion_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "CreditCardFinancePlanID")
    public JAXBElement<BigInteger> createCreditCardFinancePlanID(BigInteger value) {
        return new JAXBElement<BigInteger>(_CreditCardFinancePlanID_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ReshippedItem")
    public JAXBElement<Boolean> createReshippedItem(Boolean value) {
        return new JAXBElement<Boolean>(_ReshippedItem_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "InquiryLevel")
    public JAXBElement<TextType> createInquiryLevel(TextType value) {
        return new JAXBElement<TextType>(_InquiryLevel_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "QuickOrderNumber")
    public JAXBElement<TextType> createQuickOrderNumber(TextType value) {
        return new JAXBElement<TextType>(_QuickOrderNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "UserID")
    public JAXBElement<TextType> createUserID(TextType value) {
        return new JAXBElement<TextType>(_UserID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "AverageCost")
    public JAXBElement<Double> createAverageCost(Double value) {
        return new JAXBElement<Double>(_AverageCost_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionLineType")
    public JAXBElement<TextType> createTransactionLineType(TextType value) {
        return new JAXBElement<TextType>(_TransactionLineType_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ECTransactionID")
    public JAXBElement<TextType> createECTransactionID(TextType value) {
        return new JAXBElement<TextType>(_ECTransactionID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ECLineNumber")
    public JAXBElement<TextType> createECLineNumber(TextType value) {
        return new JAXBElement<TextType>(_ECLineNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "PimCode")
    public JAXBElement<TextType> createPimCode(TextType value) {
        return new JAXBElement<TextType>(_PimCode_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveLineTaxInfo")
    public JAXBElement<Boolean> createRetrieveLineTaxInfo(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveLineTaxInfo_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ContractType")
    public JAXBElement<TextType> createContractType(TextType value) {
        return new JAXBElement<TextType>(_ContractType_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "DeptID")
    public JAXBElement<BigInteger> createDeptID(BigInteger value) {
        return new JAXBElement<BigInteger>(_DeptID_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionDate")
    public JAXBElement<XMLGregorianCalendar> createTransactionDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_TransactionDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ServerName")
    public JAXBElement<TextType> createServerName(TextType value) {
        return new JAXBElement<TextType>(_ServerName_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "PCardIndicator")
    public JAXBElement<Boolean> createPCardIndicator(Boolean value) {
        return new JAXBElement<Boolean>(_PCardIndicator_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "Name")
    public JAXBElement<TextType> createName(TextType value) {
        return new JAXBElement<TextType>(_Name_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ContractDescription")
    public JAXBElement<TextType> createContractDescription(TextType value) {
        return new JAXBElement<TextType>(_ContractDescription_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "Quantity")
    public JAXBElement<BigInteger> createQuantity(BigInteger value) {
        return new JAXBElement<BigInteger>(_Quantity_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "EligibleFlag")
    public JAXBElement<TextType> createEligibleFlag(TextType value) {
        return new JAXBElement<TextType>(_EligibleFlag_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveCustomerInfo")
    public JAXBElement<Boolean> createRetrieveCustomerInfo(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveCustomerInfo_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ProductSku")
    public JAXBElement<TextType> createProductSku(TextType value) {
        return new JAXBElement<TextType>(_ProductSku_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveAllTransactionKeys")
    public JAXBElement<Boolean> createRetrieveAllTransactionKeys(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveAllTransactionKeys_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionKeyType")
    public JAXBElement<TextType> createTransactionKeyType(TextType value) {
        return new JAXBElement<TextType>(_TransactionKeyType_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveRelatedTransactionLine")
    public JAXBElement<Boolean> createRetrieveRelatedTransactionLine(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveRelatedTransactionLine_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveMasterItemDetails")
    public JAXBElement<Boolean> createRetrieveMasterItemDetails(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveMasterItemDetails_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SourceSystemTransactionKey")
    public JAXBElement<TextType> createSourceSystemTransactionKey(TextType value) {
        return new JAXBElement<TextType>(_SourceSystemTransactionKey_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "PerformDrillDown")
    public JAXBElement<Boolean> createPerformDrillDown(Boolean value) {
        return new JAXBElement<Boolean>(_PerformDrillDown_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SystemWarningMsg")
    public JAXBElement<TextType> createSystemWarningMsg(TextType value) {
        return new JAXBElement<TextType>(_SystemWarningMsg_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SourceSystemID")
    public JAXBElement<TextType> createSourceSystemID(TextType value) {
        return new JAXBElement<TextType>(_SourceSystemID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionStatus")
    public JAXBElement<TextType> createTransactionStatus(TextType value) {
        return new JAXBElement<TextType>(_TransactionStatus_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SerialNumber")
    public JAXBElement<TextType> createSerialNumber(TextType value) {
        return new JAXBElement<TextType>(_SerialNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SourceLineNumber")
    public JAXBElement<BigDecimal> createSourceLineNumber(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_SourceLineNumber_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SearchTimeLimitMS")
    public JAXBElement<BigInteger> createSearchTimeLimitMS(BigInteger value) {
        return new JAXBElement<BigInteger>(_SearchTimeLimitMS_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "StartECTransID")
    public JAXBElement<TextType> createStartECTransID(TextType value) {
        return new JAXBElement<TextType>(_StartECTransID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ContractSku")
    public JAXBElement<TextType> createContractSku(TextType value) {
        return new JAXBElement<TextType>(_ContractSku_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "DiscardResult")
    public JAXBElement<Boolean> createDiscardResult(Boolean value) {
        return new JAXBElement<Boolean>(_DiscardResult_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ClassID")
    public JAXBElement<BigInteger> createClassID(BigInteger value) {
        return new JAXBElement<BigInteger>(_ClassID_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ECAuditTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createECAuditTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ECAuditTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SourceLineType")
    public JAXBElement<TextType> createSourceLineType(TextType value) {
        return new JAXBElement<TextType>(_SourceLineType_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "UnitQuantity")
    public JAXBElement<BigInteger> createUnitQuantity(BigInteger value) {
        return new JAXBElement<BigInteger>(_UnitQuantity_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "BBYCreateTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createBBYCreateTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_BBYCreateTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TaxAreaState")
    public JAXBElement<TextType> createTaxAreaState(TextType value) {
        return new JAXBElement<TextType>(_TaxAreaState_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ECParentTransactionID")
    public JAXBElement<TextType> createECParentTransactionID(TextType value) {
        return new JAXBElement<TextType>(_ECParentTransactionID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "EmployeeID")
    public JAXBElement<TextType> createEmployeeID(TextType value) {
        return new JAXBElement<TextType>(_EmployeeID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SourceTransactionKey")
    public JAXBElement<TextType> createSourceTransactionKey(TextType value) {
        return new JAXBElement<TextType>(_SourceTransactionKey_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "LineItemDetailKey")
    public JAXBElement<TextType> createLineItemDetailKey(TextType value) {
        return new JAXBElement<TextType>(_LineItemDetailKey_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetailPrice")
    public JAXBElement<Double> createRetailPrice(Double value) {
        return new JAXBElement<Double>(_RetailPrice_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ExcludeRelatedSku")
    public JAXBElement<TextType> createExcludeRelatedSku(TextType value) {
        return new JAXBElement<TextType>(_ExcludeRelatedSku_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveLineItemDetails")
    public JAXBElement<Boolean> createRetrieveLineItemDetails(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveLineItemDetails_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "Environment")
    public JAXBElement<TextType> createEnvironment(TextType value) {
        return new JAXBElement<TextType>(_Environment_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TaxRate")
    public JAXBElement<BigDecimal> createTaxRate(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_TaxRate_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionType")
    public JAXBElement<TextType> createTransactionType(TextType value) {
        return new JAXBElement<TextType>(_TransactionType_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionKey")
    public JAXBElement<TextType> createTransactionKey(TextType value) {
        return new JAXBElement<TextType>(_TransactionKey_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TransactionLocation")
    public JAXBElement<TextType> createTransactionLocation(TextType value) {
        return new JAXBElement<TextType>(_TransactionLocation_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "DeptName")
    public JAXBElement<TextType> createDeptName(TextType value) {
        return new JAXBElement<TextType>(_DeptName_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "LoyaltyMemberLevelDesc")
    public JAXBElement<TextType> createLoyaltyMemberLevelDesc(TextType value) {
        return new JAXBElement<TextType>(_LoyaltyMemberLevelDesc_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "CustomerID")
    public JAXBElement<TextType> createCustomerID(TextType value) {
        return new JAXBElement<TextType>(_CustomerID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "LoyaltyMemberNumber")
    public JAXBElement<TextType> createLoyaltyMemberNumber(TextType value) {
        return new JAXBElement<TextType>(_LoyaltyMemberNumber_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "EndECTransID")
    public JAXBElement<TextType> createEndECTransID(TextType value) {
        return new JAXBElement<TextType>(_EndECTransID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "LoyaltyMemberLevel")
    public JAXBElement<TextType> createLoyaltyMemberLevel(TextType value) {
        return new JAXBElement<TextType>(_LoyaltyMemberLevel_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SkuPLUText")
    public JAXBElement<TextType> createSkuPLUText(TextType value) {
        return new JAXBElement<TextType>(_SkuPLUText_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "TaxAmount")
    public JAXBElement<BigDecimal> createTaxAmount(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_TaxAmount_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ApplicationID")
    public JAXBElement<TextType> createApplicationID(TextType value) {
        return new JAXBElement<TextType>(_ApplicationID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "SearchOnlyAuditedtransaction")
    public JAXBElement<Boolean> createSearchOnlyAuditedtransaction(Boolean value) {
        return new JAXBElement<Boolean>(_SearchOnlyAuditedtransaction_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "StartDate")
    public JAXBElement<XMLGregorianCalendar> createStartDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_StartDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Boolean }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "RetrieveContracts")
    public JAXBElement<Boolean> createRetrieveContracts(Boolean value) {
        return new JAXBElement<Boolean>(_RetrieveContracts_QNAME, Boolean.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ClassName")
    public JAXBElement<TextType> createClassName(TextType value) {
        return new JAXBElement<TextType>(_ClassName_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "Rows")
    public JAXBElement<BigInteger> createRows(BigInteger value) {
        return new JAXBElement<BigInteger>(_Rows_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "ContractID")
    public JAXBElement<TextType> createContractID(TextType value) {
        return new JAXBElement<TextType>(_ContractID_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigDecimal }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "LineItemDetailAmount")
    public JAXBElement<BigDecimal> createLineItemDetailAmount(BigDecimal value) {
        return new JAXBElement<BigDecimal>(_LineItemDetailAmount_QNAME, BigDecimal.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", name = "MasterItemID")
    public JAXBElement<TextType> createMasterItemID(TextType value) {
        return new JAXBElement<TextType>(_MasterItemID_QNAME, TextType.class, null, value);
    }

}
