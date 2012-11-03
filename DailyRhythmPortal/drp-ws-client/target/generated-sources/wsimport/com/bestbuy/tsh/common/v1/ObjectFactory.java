
package com.bestbuy.tsh.common.v1;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.common.datatype.v1.CodeType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.NameType;
import com.bestbuy.tsh.common.datatype.v1.TextType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.tsh.common.v1 package. 
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

    private final static QName _GenderCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "GenderCode");
    private final static QName _ID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "ID");
    private final static QName _TrackingInfo_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TrackingInfo");
    private final static QName _EntityDate_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EntityDate");
    private final static QName _SystemID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "SystemID");
    private final static QName _NickName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "NickName");
    private final static QName _ReasonCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "ReasonCode");
    private final static QName _TrackID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TrackID");
    private final static QName _LastName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "LastName");
    private final static QName _TaxID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TaxID");
    private final static QName _SalutationPreferredName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "SalutationPreferredName");
    private final static QName _UserArea_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "UserArea");
    private final static QName _BirthDateTime_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "BirthDateTime");
    private final static QName _DocumentID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "DocumentID");
    private final static QName _MaidenName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "MaidenName");
    private final static QName _Responsibility_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Responsibility");
    private final static QName _ChannelCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "ChannelCode");
    private final static QName _AccountID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "AccountID");
    private final static QName _DeathDateTime_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "DeathDateTime");
    private final static QName _Suffix_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Suffix");
    private final static QName _Number_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Number");
    private final static QName _EffectiveDateTime_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EffectiveDateTime");
    private final static QName _Prefix_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Prefix");
    private final static QName _PartyIDs_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "PartyIDs");
    private final static QName _Nationality_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Nationality");
    private final static QName _SubLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "SubLineNumber");
    private final static QName _FirstName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "FirstName");
    private final static QName _Reason_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Reason");
    private final static QName _DepartmentName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "DepartmentName");
    private final static QName _MaritalStatusCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "MaritalStatusCode");
    private final static QName _Code_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Code");
    private final static QName _Status_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Status");
    private final static QName _Name_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Name");
    private final static QName _MiddleName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "MiddleName");
    private final static QName _TrackURL_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TrackURL");
    private final static QName _EntityDateList_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EntityDateList");
    private final static QName _EthnicOrigin_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EthnicOrigin");
    private final static QName _PrimeLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "PrimeLineNumber");
    private final static QName _JobTitle_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "JobTitle");
    private final static QName _Description_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Description");
    private final static QName _AlternateDocumentID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "AlternateDocumentID");
    private final static QName _Title_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Title");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.tsh.common.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TrackingStatusType }
     * 
     */
    public TrackingStatusType createTrackingStatusType() {
        return new TrackingStatusType();
    }

    /**
     * Create an instance of {@link TransactionType }
     * 
     */
    public TransactionType createTransactionType() {
        return new TransactionType();
    }

    /**
     * Create an instance of {@link EntityDateType }
     * 
     */
    public EntityDateType createEntityDateType() {
        return new EntityDateType();
    }

    /**
     * Create an instance of {@link DocumentIDType }
     * 
     */
    public DocumentIDType createDocumentIDType() {
        return new DocumentIDType();
    }

    /**
     * Create an instance of {@link TrackingInfoType }
     * 
     */
    public TrackingInfoType createTrackingInfoType() {
        return new TrackingInfoType();
    }

    /**
     * Create an instance of {@link EntityDateListType }
     * 
     */
    public EntityDateListType createEntityDateListType() {
        return new EntityDateListType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link DescriptionType }
     * 
     */
    public DescriptionType createDescriptionType() {
        return new DescriptionType();
    }

    /**
     * Create an instance of {@link AmountType }
     * 
     */
    public AmountType createAmountType() {
        return new AmountType();
    }

    /**
     * Create an instance of {@link PartyIDsType }
     * 
     */
    public PartyIDsType createPartyIDsType() {
        return new PartyIDsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "GenderCode")
    public JAXBElement<CodeType> createGenderCode(CodeType value) {
        return new JAXBElement<CodeType>(_GenderCode_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "ID")
    public JAXBElement<IdentifierType> createID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_ID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TrackingInfoType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "TrackingInfo")
    public JAXBElement<TrackingInfoType> createTrackingInfo(TrackingInfoType value) {
        return new JAXBElement<TrackingInfoType>(_TrackingInfo_QNAME, TrackingInfoType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntityDateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "EntityDate")
    public JAXBElement<EntityDateType> createEntityDate(EntityDateType value) {
        return new JAXBElement<EntityDateType>(_EntityDate_QNAME, EntityDateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "SystemID")
    public JAXBElement<IdentifierType> createSystemID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_SystemID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "NickName")
    public JAXBElement<NameType> createNickName(NameType value) {
        return new JAXBElement<NameType>(_NickName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "ReasonCode")
    public JAXBElement<CodeType> createReasonCode(CodeType value) {
        return new JAXBElement<CodeType>(_ReasonCode_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "TrackID")
    public JAXBElement<IdentifierType> createTrackID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_TrackID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "LastName")
    public JAXBElement<NameType> createLastName(NameType value) {
        return new JAXBElement<NameType>(_LastName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "TaxID")
    public JAXBElement<IdentifierType> createTaxID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_TaxID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "SalutationPreferredName")
    public JAXBElement<NameType> createSalutationPreferredName(NameType value) {
        return new JAXBElement<NameType>(_SalutationPreferredName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserAreaType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "UserArea")
    public JAXBElement<UserAreaType> createUserArea(UserAreaType value) {
        return new JAXBElement<UserAreaType>(_UserArea_QNAME, UserAreaType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "BirthDateTime")
    public JAXBElement<XMLGregorianCalendar> createBirthDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_BirthDateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentIDType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "DocumentID")
    public JAXBElement<DocumentIDType> createDocumentID(DocumentIDType value) {
        return new JAXBElement<DocumentIDType>(_DocumentID_QNAME, DocumentIDType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "MaidenName")
    public JAXBElement<NameType> createMaidenName(NameType value) {
        return new JAXBElement<NameType>(_MaidenName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Responsibility")
    public JAXBElement<TextType> createResponsibility(TextType value) {
        return new JAXBElement<TextType>(_Responsibility_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "ChannelCode")
    public JAXBElement<CodeType> createChannelCode(CodeType value) {
        return new JAXBElement<CodeType>(_ChannelCode_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "AccountID")
    public JAXBElement<IdentifierType> createAccountID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_AccountID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "DeathDateTime")
    public JAXBElement<XMLGregorianCalendar> createDeathDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_DeathDateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Suffix")
    public JAXBElement<NameType> createSuffix(NameType value) {
        return new JAXBElement<NameType>(_Suffix_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Number")
    public JAXBElement<BigInteger> createNumber(BigInteger value) {
        return new JAXBElement<BigInteger>(_Number_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "EffectiveDateTime")
    public JAXBElement<XMLGregorianCalendar> createEffectiveDateTime(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_EffectiveDateTime_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Prefix")
    public JAXBElement<NameType> createPrefix(NameType value) {
        return new JAXBElement<NameType>(_Prefix_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PartyIDsType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "PartyIDs")
    public JAXBElement<PartyIDsType> createPartyIDs(PartyIDsType value) {
        return new JAXBElement<PartyIDsType>(_PartyIDs_QNAME, PartyIDsType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Nationality")
    public JAXBElement<TextType> createNationality(TextType value) {
        return new JAXBElement<TextType>(_Nationality_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "SubLineNumber")
    public JAXBElement<IdentifierType> createSubLineNumber(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_SubLineNumber_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "FirstName")
    public JAXBElement<NameType> createFirstName(NameType value) {
        return new JAXBElement<NameType>(_FirstName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Reason")
    public JAXBElement<TextType> createReason(TextType value) {
        return new JAXBElement<TextType>(_Reason_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "DepartmentName")
    public JAXBElement<NameType> createDepartmentName(NameType value) {
        return new JAXBElement<NameType>(_DepartmentName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "MaritalStatusCode")
    public JAXBElement<CodeType> createMaritalStatusCode(CodeType value) {
        return new JAXBElement<CodeType>(_MaritalStatusCode_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Code")
    public JAXBElement<CodeType> createCode(CodeType value) {
        return new JAXBElement<CodeType>(_Code_QNAME, CodeType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Status")
    public JAXBElement<StatusType> createStatus(StatusType value) {
        return new JAXBElement<StatusType>(_Status_QNAME, StatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Name")
    public JAXBElement<NameType> createName(NameType value) {
        return new JAXBElement<NameType>(_Name_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "MiddleName")
    public JAXBElement<NameType> createMiddleName(NameType value) {
        return new JAXBElement<NameType>(_MiddleName_QNAME, NameType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "TrackURL")
    public JAXBElement<TextType> createTrackURL(TextType value) {
        return new JAXBElement<TextType>(_TrackURL_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntityDateListType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "EntityDateList")
    public JAXBElement<EntityDateListType> createEntityDateList(EntityDateListType value) {
        return new JAXBElement<EntityDateListType>(_EntityDateList_QNAME, EntityDateListType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "EthnicOrigin")
    public JAXBElement<TextType> createEthnicOrigin(TextType value) {
        return new JAXBElement<TextType>(_EthnicOrigin_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "PrimeLineNumber")
    public JAXBElement<IdentifierType> createPrimeLineNumber(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_PrimeLineNumber_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "JobTitle")
    public JAXBElement<TextType> createJobTitle(TextType value) {
        return new JAXBElement<TextType>(_JobTitle_QNAME, TextType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Description")
    public JAXBElement<String> createDescription(String value) {
        return new JAXBElement<String>(_Description_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentIDType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "AlternateDocumentID")
    public JAXBElement<DocumentIDType> createAlternateDocumentID(DocumentIDType value) {
        return new JAXBElement<DocumentIDType>(_AlternateDocumentID_QNAME, DocumentIDType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Title")
    public JAXBElement<TextType> createTitle(TextType value) {
        return new JAXBElement<TextType>(_Title_QNAME, TextType.class, null, value);
    }

}
