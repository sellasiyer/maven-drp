
package com.bestbuy.bbym.ise.iseclientacds;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.ise.iseclientacds package. 
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
    private final static QName _GenerateManifestDocResponse_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/fields/v1", "GenerateManifestDocResponse");
    private final static QName _SourceID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "SourceID");
    private final static QName _RemoveFromManifestRequest_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "RemoveFromManifestRequest");
    private final static QName _ResponseTimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "ResponseTimeStamp");
    private final static QName _Brand_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Brand");
    private final static QName _TrackingInfo_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TrackingInfo");
    private final static QName _ID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "ID");
    private final static QName _Channel_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Channel");
    private final static QName _SearchForManifestsResponse_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "SearchForManifestsResponse");
    private final static QName _AddToManifestRequest_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "AddToManifestRequest");
    private final static QName _EntityDate_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EntityDate");
    private final static QName _Version_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Version");
    private final static QName _SystemID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "SystemID");
    private final static QName _NickName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "NickName");
    private final static QName _ReasonCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "ReasonCode");
    private final static QName _TrackID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TrackID");
    private final static QName _GenerateManifestDocumentRequest_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "GenerateManifestDocumentRequest");
    private final static QName _LastName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "LastName");
    private final static QName _TaxID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TaxID");
    private final static QName _ProgramID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "ProgramID");
    private final static QName _SalutationPreferredName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "SalutationPreferredName");
    private final static QName _UserArea_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "UserArea");
    private final static QName _DocumentID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "DocumentID");
    private final static QName _BirthDateTime_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "BirthDateTime");
    private final static QName _MaidenName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "MaidenName");
    private final static QName _AccountID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "AccountID");
    private final static QName _ChannelCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "ChannelCode");
    private final static QName _Responsibility_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Responsibility");
    private final static QName _TradingArea_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "TradingArea");
    private final static QName _DeathDateTime_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "DeathDateTime");
    private final static QName _Number_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Number");
    private final static QName _Suffix_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Suffix");
    private final static QName _EffectiveDateTime_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EffectiveDateTime");
    private final static QName _Prefix_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Prefix");
    private final static QName _PartyIDs_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "PartyIDs");
    private final static QName _Nationality_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Nationality");
    private final static QName _MessageID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "MessageID");
    private final static QName _UserID_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "UserID");
    private final static QName _SubLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "SubLineNumber");
    private final static QName _GenerateManifestDocumentResponse_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "GenerateManifestDocumentResponse");
    private final static QName _FirstName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "FirstName");
    private final static QName _Environment_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Environment");
    private final static QName _Reason_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Reason");
    private final static QName _RequestTimeStamp_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "RequestTimeStamp");
    private final static QName _DepartmentName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "DepartmentName");
    private final static QName _MaritalStatusCode_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "MaritalStatusCode");
    private final static QName _Company_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Company");
    private final static QName _ENTERNAMEOFROOTELEMENTHERE_QNAME = new QName("http://www.tsh.bestbuy.com/common/datatype/v1", "ENTER_NAME_OF_ROOT_ELEMENT_HERE");
    private final static QName _BusinessUnit_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "BusinessUnit");
    private final static QName _Code_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Code");
    private final static QName _Status_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Status");
    private final static QName _GenerateManifestDocRequest_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/fields/v1", "GenerateManifestDocRequest");
    private final static QName _Name_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Name");
    private final static QName _MiddleName_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "MiddleName");
    private final static QName _TrackURL_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "TrackURL");
    private final static QName _SearchForManifestsRequest_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "SearchForManifestsRequest");
    private final static QName _Manifest_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/fields/v1", "Manifest");
    private final static QName _EntityDateList_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EntityDateList");
    private final static QName _EthnicOrigin_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "EthnicOrigin");
    private final static QName _JobTitle_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "JobTitle");
    private final static QName _PrimeLineNumber_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "PrimeLineNumber");
    private final static QName _AddToManifestResponse_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "AddToManifestResponse");
    private final static QName _TSHFault_QNAME = new QName("http://tsh.bestbuy.com/tsh/tshfault", "TSHFault");
    private final static QName _RemoveFromManifestResponse_QNAME = new QName("http://bestbuy.com/bbym/logistics/manifest/service/v1", "RemoveFromManifestResponse");
    private final static QName _Description_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Description");
    private final static QName _AlternateDocumentID_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "AlternateDocumentID");
    private final static QName _Enterprise_QNAME = new QName("http://www.tsh.bestbuy.com/common/metadata/fields/v1", "Enterprise");
    private final static QName _Title_QNAME = new QName("http://www.tsh.bestbuy.com/common/v1", "Title");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.ise.iseclientacds
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link TrackingInfoType }
     * 
     */
    public TrackingInfoType createTrackingInfoType() {
        return new TrackingInfoType();
    }

    /**
     * Create an instance of {@link ManifestAddRequestType }
     * 
     */
    public ManifestAddRequestType createManifestAddRequestType() {
        return new ManifestAddRequestType();
    }

    /**
     * Create an instance of {@link GenerateManifestDocumentRequestType }
     * 
     */
    public GenerateManifestDocumentRequestType createGenerateManifestDocumentRequestType() {
        return new GenerateManifestDocumentRequestType();
    }

    /**
     * Create an instance of {@link TSHFaultType }
     * 
     */
    public TSHFaultType createTSHFaultType() {
        return new TSHFaultType();
    }

    /**
     * Create an instance of {@link SequencedNameType }
     * 
     */
    public SequencedNameType createSequencedNameType() {
        return new SequencedNameType();
    }

    /**
     * Create an instance of {@link SearchForManifestsResponseType }
     * 
     */
    public SearchForManifestsResponseType createSearchForManifestsResponseType() {
        return new SearchForManifestsResponseType();
    }

    /**
     * Create an instance of {@link TransactionType }
     * 
     */
    public TransactionType createTransactionType() {
        return new TransactionType();
    }

    /**
     * Create an instance of {@link DocumentIDType }
     * 
     */
    public DocumentIDType createDocumentIDType() {
        return new DocumentIDType();
    }

    /**
     * Create an instance of {@link DescriptionType }
     * 
     */
    public DescriptionType createDescriptionType() {
        return new DescriptionType();
    }

    /**
     * Create an instance of {@link UserAreaType }
     * 
     */
    public UserAreaType createUserAreaType() {
        return new UserAreaType();
    }

    /**
     * Create an instance of {@link ManifestHeaderType }
     * 
     */
    public ManifestHeaderType createManifestHeaderType() {
        return new ManifestHeaderType();
    }

    /**
     * Create an instance of {@link RequestMetaDataType }
     * 
     */
    public RequestMetaDataType createRequestMetaDataType() {
        return new RequestMetaDataType();
    }

    /**
     * Create an instance of {@link TextType }
     * 
     */
    public TextType createTextType() {
        return new TextType();
    }

    /**
     * Create an instance of {@link ManifestLineItemType }
     * 
     */
    public ManifestLineItemType createManifestLineItemType() {
        return new ManifestLineItemType();
    }

    /**
     * Create an instance of {@link ManifestRemoveType }
     * 
     */
    public ManifestRemoveType createManifestRemoveType() {
        return new ManifestRemoveType();
    }

    /**
     * Create an instance of {@link FlagType }
     * 
     */
    public FlagType createFlagType() {
        return new FlagType();
    }

    /**
     * Create an instance of {@link EntityDateListType }
     * 
     */
    public EntityDateListType createEntityDateListType() {
        return new EntityDateListType();
    }

    /**
     * Create an instance of {@link ManifestSearchType }
     * 
     */
    public ManifestSearchType createManifestSearchType() {
        return new ManifestSearchType();
    }

    /**
     * Create an instance of {@link NameType }
     * 
     */
    public NameType createNameType() {
        return new NameType();
    }

    /**
     * Create an instance of {@link GenerateManifestDocRequestType }
     * 
     */
    public GenerateManifestDocRequestType createGenerateManifestDocRequestType() {
        return new GenerateManifestDocRequestType();
    }

    /**
     * Create an instance of {@link RemoveFromManifestRequestType }
     * 
     */
    public RemoveFromManifestRequestType createRemoveFromManifestRequestType() {
        return new RemoveFromManifestRequestType();
    }

    /**
     * Create an instance of {@link CodeType }
     * 
     */
    public CodeType createCodeType() {
        return new CodeType();
    }

    /**
     * Create an instance of {@link DateRange }
     * 
     */
    public DateRange createDateRange() {
        return new DateRange();
    }

    /**
     * Create an instance of {@link EntityDateType }
     * 
     */
    public EntityDateType createEntityDateType() {
        return new EntityDateType();
    }

    /**
     * Create an instance of {@link MeasureType }
     * 
     */
    public MeasureType createMeasureType() {
        return new MeasureType();
    }

    /**
     * Create an instance of {@link InternationalBusinessHierarchyType }
     * 
     */
    public InternationalBusinessHierarchyType createInternationalBusinessHierarchyType() {
        return new InternationalBusinessHierarchyType();
    }

    /**
     * Create an instance of {@link SearchForManifestsRequestType }
     * 
     */
    public SearchForManifestsRequestType createSearchForManifestsRequestType() {
        return new SearchForManifestsRequestType();
    }

    /**
     * Create an instance of {@link ServiceDetailsType }
     * 
     */
    public ServiceDetailsType createServiceDetailsType() {
        return new ServiceDetailsType();
    }

    /**
     * Create an instance of {@link NameValueType }
     * 
     */
    public NameValueType createNameValueType() {
        return new NameValueType();
    }

    /**
     * Create an instance of {@link ManifestRemoveLineType }
     * 
     */
    public ManifestRemoveLineType createManifestRemoveLineType() {
        return new ManifestRemoveLineType();
    }

    /**
     * Create an instance of {@link IdentifierType }
     * 
     */
    public IdentifierType createIdentifierType() {
        return new IdentifierType();
    }

    /**
     * Create an instance of {@link RemoveFromManifestResponseType }
     * 
     */
    public RemoveFromManifestResponseType createRemoveFromManifestResponseType() {
        return new RemoveFromManifestResponseType();
    }

    /**
     * Create an instance of {@link AddToManifestResponseType }
     * 
     */
    public AddToManifestResponseType createAddToManifestResponseType() {
        return new AddToManifestResponseType();
    }

    /**
     * Create an instance of {@link GenerateManifestDocumentResponseType }
     * 
     */
    public GenerateManifestDocumentResponseType createGenerateManifestDocumentResponseType() {
        return new GenerateManifestDocumentResponseType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link GenerateManifestDocResponseType }
     * 
     */
    public GenerateManifestDocResponseType createGenerateManifestDocResponseType() {
        return new GenerateManifestDocResponseType();
    }

    /**
     * Create an instance of {@link ResponseMetaDataType }
     * 
     */
    public ResponseMetaDataType createResponseMetaDataType() {
        return new ResponseMetaDataType();
    }

    /**
     * Create an instance of {@link PartyIDsType }
     * 
     */
    public PartyIDsType createPartyIDsType() {
        return new PartyIDsType();
    }

    /**
     * Create an instance of {@link AddToManifestRequestType }
     * 
     */
    public AddToManifestRequestType createAddToManifestRequestType() {
        return new AddToManifestRequestType();
    }

    /**
     * Create an instance of {@link AmountType }
     * 
     */
    public AmountType createAmountType() {
        return new AmountType();
    }

    /**
     * Create an instance of {@link ManifestListType }
     * 
     */
    public ManifestListType createManifestListType() {
        return new ManifestListType();
    }

    /**
     * Create an instance of {@link KeyType }
     * 
     */
    public KeyType createKeyType() {
        return new KeyType();
    }

    /**
     * Create an instance of {@link SequencedTextType }
     * 
     */
    public SequencedTextType createSequencedTextType() {
        return new SequencedTextType();
    }

    /**
     * Create an instance of {@link TrackingStatusType }
     * 
     */
    public TrackingStatusType createTrackingStatusType() {
        return new TrackingStatusType();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateManifestDocResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", name = "GenerateManifestDocResponse")
    public JAXBElement<GenerateManifestDocResponseType> createGenerateManifestDocResponse(GenerateManifestDocResponseType value) {
        return new JAXBElement<GenerateManifestDocResponseType>(_GenerateManifestDocResponse_QNAME, GenerateManifestDocResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "SourceID")
    public JAXBElement<String> createSourceID(String value) {
        return new JAXBElement<String>(_SourceID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveFromManifestRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "RemoveFromManifestRequest")
    public JAXBElement<RemoveFromManifestRequestType> createRemoveFromManifestRequest(RemoveFromManifestRequestType value) {
        return new JAXBElement<RemoveFromManifestRequestType>(_RemoveFromManifestRequest_QNAME, RemoveFromManifestRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "ResponseTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createResponseTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_ResponseTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Brand")
    public JAXBElement<String> createBrand(String value) {
        return new JAXBElement<String>(_Brand_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "ID")
    public JAXBElement<IdentifierType> createID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_ID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Channel")
    public JAXBElement<String> createChannel(String value) {
        return new JAXBElement<String>(_Channel_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchForManifestsResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "SearchForManifestsResponse")
    public JAXBElement<SearchForManifestsResponseType> createSearchForManifestsResponse(SearchForManifestsResponseType value) {
        return new JAXBElement<SearchForManifestsResponseType>(_SearchForManifestsResponse_QNAME, SearchForManifestsResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToManifestRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "AddToManifestRequest")
    public JAXBElement<AddToManifestRequestType> createAddToManifestRequest(AddToManifestRequestType value) {
        return new JAXBElement<AddToManifestRequestType>(_AddToManifestRequest_QNAME, AddToManifestRequestType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link Integer }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Version")
    public JAXBElement<Integer> createVersion(Integer value) {
        return new JAXBElement<Integer>(_Version_QNAME, Integer.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateManifestDocumentRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "GenerateManifestDocumentRequest")
    public JAXBElement<GenerateManifestDocumentRequestType> createGenerateManifestDocumentRequest(GenerateManifestDocumentRequestType value) {
        return new JAXBElement<GenerateManifestDocumentRequestType>(_GenerateManifestDocumentRequest_QNAME, GenerateManifestDocumentRequestType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "ProgramID")
    public JAXBElement<String> createProgramID(String value) {
        return new JAXBElement<String>(_ProgramID_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DocumentIDType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "DocumentID")
    public JAXBElement<DocumentIDType> createDocumentID(DocumentIDType value) {
        return new JAXBElement<DocumentIDType>(_DocumentID_QNAME, DocumentIDType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link NameType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "MaidenName")
    public JAXBElement<NameType> createMaidenName(NameType value) {
        return new JAXBElement<NameType>(_MaidenName_QNAME, NameType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link CodeType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "ChannelCode")
    public JAXBElement<CodeType> createChannelCode(CodeType value) {
        return new JAXBElement<CodeType>(_ChannelCode_QNAME, CodeType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "TradingArea")
    public JAXBElement<String> createTradingArea(String value) {
        return new JAXBElement<String>(_TradingArea_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "Number")
    public JAXBElement<BigInteger> createNumber(BigInteger value) {
        return new JAXBElement<BigInteger>(_Number_QNAME, BigInteger.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "MessageID")
    public JAXBElement<String> createMessageID(String value) {
        return new JAXBElement<String>(_MessageID_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "UserID")
    public JAXBElement<String> createUserID(String value) {
        return new JAXBElement<String>(_UserID_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateManifestDocumentResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "GenerateManifestDocumentResponse")
    public JAXBElement<GenerateManifestDocumentResponseType> createGenerateManifestDocumentResponse(GenerateManifestDocumentResponseType value) {
        return new JAXBElement<GenerateManifestDocumentResponseType>(_GenerateManifestDocumentResponse_QNAME, GenerateManifestDocumentResponseType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Environment")
    public JAXBElement<String> createEnvironment(String value) {
        return new JAXBElement<String>(_Environment_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "RequestTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createRequestTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_RequestTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Company")
    public JAXBElement<String> createCompany(String value) {
        return new JAXBElement<String>(_Company_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Object }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/datatype/v1", name = "ENTER_NAME_OF_ROOT_ELEMENT_HERE")
    public JAXBElement<Object> createENTERNAMEOFROOTELEMENTHERE(Object value) {
        return new JAXBElement<Object>(_ENTERNAMEOFROOTELEMENTHERE_QNAME, Object.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "BusinessUnit")
    public JAXBElement<String> createBusinessUnit(String value) {
        return new JAXBElement<String>(_BusinessUnit_QNAME, String.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link GenerateManifestDocRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", name = "GenerateManifestDocRequest")
    public JAXBElement<GenerateManifestDocRequestType> createGenerateManifestDocRequest(GenerateManifestDocRequestType value) {
        return new JAXBElement<GenerateManifestDocRequestType>(_GenerateManifestDocRequest_QNAME, GenerateManifestDocRequestType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchForManifestsRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "SearchForManifestsRequest")
    public JAXBElement<SearchForManifestsRequestType> createSearchForManifestsRequest(SearchForManifestsRequestType value) {
        return new JAXBElement<SearchForManifestsRequestType>(_SearchForManifestsRequest_QNAME, SearchForManifestsRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManifestHeaderType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", name = "Manifest")
    public JAXBElement<ManifestHeaderType> createManifest(ManifestHeaderType value) {
        return new JAXBElement<ManifestHeaderType>(_Manifest_QNAME, ManifestHeaderType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link TextType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/v1", name = "JobTitle")
    public JAXBElement<TextType> createJobTitle(TextType value) {
        return new JAXBElement<TextType>(_JobTitle_QNAME, TextType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToManifestResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "AddToManifestResponse")
    public JAXBElement<AddToManifestResponseType> createAddToManifestResponse(AddToManifestResponseType value) {
        return new JAXBElement<AddToManifestResponseType>(_AddToManifestResponse_QNAME, AddToManifestResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TSHFaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://tsh.bestbuy.com/tsh/tshfault", name = "TSHFault")
    public JAXBElement<TSHFaultType> createTSHFault(TSHFaultType value) {
        return new JAXBElement<TSHFaultType>(_TSHFault_QNAME, TSHFaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveFromManifestResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/manifest/service/v1", name = "RemoveFromManifestResponse")
    public JAXBElement<RemoveFromManifestResponseType> createRemoveFromManifestResponse(RemoveFromManifestResponseType value) {
        return new JAXBElement<RemoveFromManifestResponseType>(_RemoveFromManifestResponse_QNAME, RemoveFromManifestResponseType.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1", name = "Enterprise")
    public JAXBElement<String> createEnterprise(String value) {
        return new JAXBElement<String>(_Enterprise_QNAME, String.class, null, value);
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
