
package com.bestbuy.bbym.sales.transactioncheck.fields.v1;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.sales.transactioncheck.fields.v1 package. 
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

    private final static QName _ActivationMethod_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "ActivationMethod");
    private final static QName _ActivationType_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "ActivationType");
    private final static QName _UpgradeEligibleCode_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "UpgradeEligibleCode");
    private final static QName _AccountNumber_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "AccountNumber");
    private final static QName _Value_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Value");
    private final static QName _ManagerID_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "ManagerID");
    private final static QName _PortFlag_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "PortFlag");
    private final static QName _Device_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Device");
    private final static QName _LineSequenceNumber_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "LineSequenceNumber");
    private final static QName _Type_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Type");
    private final static QName _OrderType_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "OrderType");
    private final static QName _RecurringCharge_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "RecurringCharge");
    private final static QName _RepID_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "RepID");
    private final static QName _Transaction_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Transaction");
    private final static QName _DepositMode_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "DepositMode");
    private final static QName _Rule_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Rule");
    private final static QName _TransactionList_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "TransactionList");
    private final static QName _Status_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Status");
    private final static QName _Category_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Category");
    private final static QName _OriginalTransactionID_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "OriginalTransactionID");
    private final static QName _Qualification_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Qualification");
    private final static QName _ContractType_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "ContractType");
    private final static QName _OriginalLineSequenceNumber_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "OriginalLineSequenceNumber");
    private final static QName _Communication_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Communication");
    private final static QName _RuleSetName_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "RuleSetName");
    private final static QName _ConfirmationNumber_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "ConfirmationNumber");
    private final static QName _Score_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Score");
    private final static QName _SubscriberNumber_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "SubscriberNumber");
    private final static QName _Term_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Term");
    private final static QName _SubAccountNumber_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "SubAccountNumber");
    private final static QName _AgentCode_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "AgentCode");
    private final static QName _Plan_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Plan");
    private final static QName _UpgradeTier_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "UpgradeTier");
    private final static QName _LinesRequested_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "LinesRequested");
    private final static QName _LocationID_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "LocationID");
    private final static QName _ActivationDate_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "ActivationDate");
    private final static QName _SubscriberID_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "SubscriberID");
    private final static QName _Carrier_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "Carrier");
    private final static QName _ActivationRequestDate_QNAME = new QName("http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", "ActivationRequestDate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.sales.transactioncheck.fields.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DeviceType }
     * 
     */
    public DeviceType createDeviceType() {
        return new DeviceType();
    }

    /**
     * Create an instance of {@link TransactionListType }
     * 
     */
    public TransactionListType createTransactionListType() {
        return new TransactionListType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link ChannelType }
     * 
     */
    public ChannelType createChannelType() {
        return new ChannelType();
    }

    /**
     * Create an instance of {@link CarrierBaseType }
     * 
     */
    public CarrierBaseType createCarrierBaseType() {
        return new CarrierBaseType();
    }

    /**
     * Create an instance of {@link CommunicationType }
     * 
     */
    public CommunicationType createCommunicationType() {
        return new CommunicationType();
    }

    /**
     * Create an instance of {@link TransactionBaseType }
     * 
     */
    public TransactionBaseType createTransactionBaseType() {
        return new TransactionBaseType();
    }

    /**
     * Create an instance of {@link ContractLineItemType }
     * 
     */
    public ContractLineItemType createContractLineItemType() {
        return new ContractLineItemType();
    }

    /**
     * Create an instance of {@link PlanType }
     * 
     */
    public PlanType createPlanType() {
        return new PlanType();
    }

    /**
     * Create an instance of {@link DeviceListType }
     * 
     */
    public DeviceListType createDeviceListType() {
        return new DeviceListType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "ActivationMethod")
    public JAXBElement<String> createActivationMethod(String value) {
        return new JAXBElement<String>(_ActivationMethod_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "ActivationType")
    public JAXBElement<String> createActivationType(String value) {
        return new JAXBElement<String>(_ActivationType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "UpgradeEligibleCode")
    public JAXBElement<String> createUpgradeEligibleCode(String value) {
        return new JAXBElement<String>(_UpgradeEligibleCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "AccountNumber")
    public JAXBElement<String> createAccountNumber(String value) {
        return new JAXBElement<String>(_AccountNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Value")
    public JAXBElement<String> createValue(String value) {
        return new JAXBElement<String>(_Value_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "ManagerID")
    public JAXBElement<String> createManagerID(String value) {
        return new JAXBElement<String>(_ManagerID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "PortFlag")
    public JAXBElement<String> createPortFlag(String value) {
        return new JAXBElement<String>(_PortFlag_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeviceType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Device")
    public JAXBElement<DeviceType> createDevice(DeviceType value) {
        return new JAXBElement<DeviceType>(_Device_QNAME, DeviceType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "LineSequenceNumber")
    public JAXBElement<String> createLineSequenceNumber(String value) {
        return new JAXBElement<String>(_LineSequenceNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Type")
    public JAXBElement<String> createType(String value) {
        return new JAXBElement<String>(_Type_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "OrderType")
    public JAXBElement<String> createOrderType(String value) {
        return new JAXBElement<String>(_OrderType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Double }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "RecurringCharge")
    public JAXBElement<Double> createRecurringCharge(Double value) {
        return new JAXBElement<Double>(_RecurringCharge_QNAME, Double.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "RepID")
    public JAXBElement<String> createRepID(String value) {
        return new JAXBElement<String>(_RepID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionBaseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Transaction")
    public JAXBElement<TransactionBaseType> createTransaction(TransactionBaseType value) {
        return new JAXBElement<TransactionBaseType>(_Transaction_QNAME, TransactionBaseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "DepositMode")
    public JAXBElement<String> createDepositMode(String value) {
        return new JAXBElement<String>(_DepositMode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Rule")
    public JAXBElement<String> createRule(String value) {
        return new JAXBElement<String>(_Rule_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "TransactionList")
    public JAXBElement<TransactionListType> createTransactionList(TransactionListType value) {
        return new JAXBElement<TransactionListType>(_TransactionList_QNAME, TransactionListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Status")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createStatus(String value) {
        return new JAXBElement<String>(_Status_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Category")
    public JAXBElement<String> createCategory(String value) {
        return new JAXBElement<String>(_Category_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "OriginalTransactionID")
    public JAXBElement<String> createOriginalTransactionID(String value) {
        return new JAXBElement<String>(_OriginalTransactionID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Qualification")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createQualification(String value) {
        return new JAXBElement<String>(_Qualification_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "ContractType")
    public JAXBElement<String> createContractType(String value) {
        return new JAXBElement<String>(_ContractType_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "OriginalLineSequenceNumber")
    public JAXBElement<String> createOriginalLineSequenceNumber(String value) {
        return new JAXBElement<String>(_OriginalLineSequenceNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CommunicationType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Communication")
    public JAXBElement<CommunicationType> createCommunication(CommunicationType value) {
        return new JAXBElement<CommunicationType>(_Communication_QNAME, CommunicationType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "RuleSetName")
    public JAXBElement<String> createRuleSetName(String value) {
        return new JAXBElement<String>(_RuleSetName_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "ConfirmationNumber")
    public JAXBElement<String> createConfirmationNumber(String value) {
        return new JAXBElement<String>(_ConfirmationNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Score")
    public JAXBElement<BigInteger> createScore(BigInteger value) {
        return new JAXBElement<BigInteger>(_Score_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "SubscriberNumber")
    public JAXBElement<String> createSubscriberNumber(String value) {
        return new JAXBElement<String>(_SubscriberNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Term")
    public JAXBElement<BigInteger> createTerm(BigInteger value) {
        return new JAXBElement<BigInteger>(_Term_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "SubAccountNumber")
    public JAXBElement<String> createSubAccountNumber(String value) {
        return new JAXBElement<String>(_SubAccountNumber_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "AgentCode")
    public JAXBElement<String> createAgentCode(String value) {
        return new JAXBElement<String>(_AgentCode_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PlanType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Plan")
    public JAXBElement<PlanType> createPlan(PlanType value) {
        return new JAXBElement<PlanType>(_Plan_QNAME, PlanType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "UpgradeTier")
    public JAXBElement<String> createUpgradeTier(String value) {
        return new JAXBElement<String>(_UpgradeTier_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "LinesRequested")
    public JAXBElement<BigInteger> createLinesRequested(BigInteger value) {
        return new JAXBElement<BigInteger>(_LinesRequested_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "LocationID")
    public JAXBElement<String> createLocationID(String value) {
        return new JAXBElement<String>(_LocationID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "ActivationDate")
    public JAXBElement<XMLGregorianCalendar> createActivationDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivationDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "SubscriberID")
    public JAXBElement<String> createSubscriberID(String value) {
        return new JAXBElement<String>(_SubscriberID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CarrierBaseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "Carrier")
    public JAXBElement<CarrierBaseType> createCarrier(CarrierBaseType value) {
        return new JAXBElement<CarrierBaseType>(_Carrier_QNAME, CarrierBaseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1", name = "ActivationRequestDate")
    public JAXBElement<XMLGregorianCalendar> createActivationRequestDate(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ActivationRequestDate_QNAME, XMLGregorianCalendar.class, null, value);
    }

}
