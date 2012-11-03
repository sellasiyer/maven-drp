
package com.bestbuy.bbym.ise.iseclientca;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.ise.iseclientca package. 
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

    private final static QName _ManageSubAccountAttributesResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "ManageSubAccountAttributesResponse");
    private final static QName _MatchPartyResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "MatchPartyResponse");
    private final static QName _CleanseCustomerDataRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "CleanseCustomerDataRequest");
    private final static QName _TransferAccountResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "TransferAccountResponse");
    private final static QName _Security_QNAME = new QName("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "Security");
    private final static QName _ConsolidatePartyRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "ConsolidatePartyRequest");
    private final static QName _GetSubAccountAttributesResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "GetSubAccountAttributesResponse");
    private final static QName _MatchPartyRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "MatchPartyRequest");
    private final static QName _SearchCustomerRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "SearchCustomerRequest");
    private final static QName _ConsolidatePartyResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "ConsolidatePartyResponse");
    private final static QName _SearchCustomerResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "SearchCustomerResponse");
    private final static QName _GetSubAccountAttributesRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "GetSubAccountAttributesRequest");
    private final static QName _CleanseCustomerDataResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "CleanseCustomerDataResponse");
    private final static QName _ManageAccountResponse_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "ManageAccountResponse");
    private final static QName _ManageAccountRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "ManageAccountRequest");
    private final static QName _TransferAccountRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "TransferAccountRequest");
    private final static QName _FaultElement_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "faultElement");
    private final static QName _ManageSubAccountAttributesRequest_QNAME = new QName("http://webservices.bestbuy.com/ca/services/entity/v2", "ManageSubAccountAttributesRequest");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.ise.iseclientca
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IdentityType.Credentials }
     * 
     */
    public IdentityType.Credentials createIdentityTypeCredentials() {
        return new IdentityType.Credentials();
    }

    /**
     * Create an instance of {@link BaseResponseType }
     * 
     */
    public BaseResponseType createBaseResponseType() {
        return new BaseResponseType();
    }

    /**
     * Create an instance of {@link CommunicationPreferenceType.CommunicationAddress.PostalAddress }
     * 
     */
    public CommunicationPreferenceType.CommunicationAddress.PostalAddress createCommunicationPreferenceTypeCommunicationAddressPostalAddress() {
        return new CommunicationPreferenceType.CommunicationAddress.PostalAddress();
    }

    /**
     * Create an instance of {@link SearchCustomerRequestType.SearchParameters.Account }
     * 
     */
    public SearchCustomerRequestType.SearchParameters.Account createSearchCustomerRequestTypeSearchParametersAccount() {
        return new SearchCustomerRequestType.SearchParameters.Account();
    }

    /**
     * Create an instance of {@link AccountCategoryType }
     * 
     */
    public AccountCategoryType createAccountCategoryType() {
        return new AccountCategoryType();
    }

    /**
     * Create an instance of {@link TransferAccountRequestType }
     * 
     */
    public TransferAccountRequestType createTransferAccountRequestType() {
        return new TransferAccountRequestType();
    }

    /**
     * Create an instance of {@link IdentityType }
     * 
     */
    public IdentityType createIdentityType() {
        return new IdentityType();
    }

    /**
     * Create an instance of {@link PaymentAccountType }
     * 
     */
    public PaymentAccountType createPaymentAccountType() {
        return new PaymentAccountType();
    }

    /**
     * Create an instance of {@link PartyType.PaymentAccountList }
     * 
     */
    public PartyType.PaymentAccountList createPartyTypePaymentAccountList() {
        return new PartyType.PaymentAccountList();
    }

    /**
     * Create an instance of {@link ThinAccountType }
     * 
     */
    public ThinAccountType createThinAccountType() {
        return new ThinAccountType();
    }

    /**
     * Create an instance of {@link SearchCustomerRequestType.SearchParameters.Customer }
     * 
     */
    public SearchCustomerRequestType.SearchParameters.Customer createSearchCustomerRequestTypeSearchParametersCustomer() {
        return new SearchCustomerRequestType.SearchParameters.Customer();
    }

    /**
     * Create an instance of {@link PaymentPreferenceType }
     * 
     */
    public PaymentPreferenceType createPaymentPreferenceType() {
        return new PaymentPreferenceType();
    }

    /**
     * Create an instance of {@link ManageSubAccountAttributesResponseType }
     * 
     */
    public ManageSubAccountAttributesResponseType createManageSubAccountAttributesResponseType() {
        return new ManageSubAccountAttributesResponseType();
    }

    /**
     * Create an instance of {@link GetSubAccountAttributesRequestType }
     * 
     */
    public GetSubAccountAttributesRequestType createGetSubAccountAttributesRequestType() {
        return new GetSubAccountAttributesRequestType();
    }

    /**
     * Create an instance of {@link NameType }
     * 
     */
    public NameType createNameType() {
        return new NameType();
    }

    /**
     * Create an instance of {@link RegistrationType }
     * 
     */
    public RegistrationType createRegistrationType() {
        return new RegistrationType();
    }

    /**
     * Create an instance of {@link PartyAccountAttributeDetailsType }
     * 
     */
    public PartyAccountAttributeDetailsType createPartyAccountAttributeDetailsType() {
        return new PartyAccountAttributeDetailsType();
    }

    /**
     * Create an instance of {@link DateOfBirthType }
     * 
     */
    public DateOfBirthType createDateOfBirthType() {
        return new DateOfBirthType();
    }

    /**
     * Create an instance of {@link SearchCustomerRequestType.SearchParameters }
     * 
     */
    public SearchCustomerRequestType.SearchParameters createSearchCustomerRequestTypeSearchParameters() {
        return new SearchCustomerRequestType.SearchParameters();
    }

    /**
     * Create an instance of {@link PhoneType }
     * 
     */
    public PhoneType createPhoneType() {
        return new PhoneType();
    }

    /**
     * Create an instance of {@link SearchCustomerRequestType.SearchParameters.Customer.Alias }
     * 
     */
    public SearchCustomerRequestType.SearchParameters.Customer.Alias createSearchCustomerRequestTypeSearchParametersCustomerAlias() {
        return new SearchCustomerRequestType.SearchParameters.Customer.Alias();
    }

    /**
     * Create an instance of {@link PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList.SubAccount }
     * 
     */
    public PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList.SubAccount createPartyAccountAttributeDetailsTypePartySubAccountWithAttributesListSubAccount() {
        return new PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList.SubAccount();
    }

    /**
     * Create an instance of {@link PartyType.SubAccountList }
     * 
     */
    public PartyType.SubAccountList createPartyTypeSubAccountList() {
        return new PartyType.SubAccountList();
    }

    /**
     * Create an instance of {@link MatchPartyRequestType }
     * 
     */
    public MatchPartyRequestType createMatchPartyRequestType() {
        return new MatchPartyRequestType();
    }

    /**
     * Create an instance of {@link ThinAccountListType }
     * 
     */
    public ThinAccountListType createThinAccountListType() {
        return new ThinAccountListType();
    }

    /**
     * Create an instance of {@link MatchPartyRequestType.Party.PartyDetail }
     * 
     */
    public MatchPartyRequestType.Party.PartyDetail createMatchPartyRequestTypePartyPartyDetail() {
        return new MatchPartyRequestType.Party.PartyDetail();
    }

    /**
     * Create an instance of {@link SubAccountAttributeListType }
     * 
     */
    public SubAccountAttributeListType createSubAccountAttributeListType() {
        return new SubAccountAttributeListType();
    }

    /**
     * Create an instance of {@link ConsolidatePartyResponseType }
     * 
     */
    public ConsolidatePartyResponseType createConsolidatePartyResponseType() {
        return new ConsolidatePartyResponseType();
    }

    /**
     * Create an instance of {@link CustomerSearchTransactionType }
     * 
     */
    public CustomerSearchTransactionType createCustomerSearchTransactionType() {
        return new CustomerSearchTransactionType();
    }

    /**
     * Create an instance of {@link PartyAccountAttributeDetailsType.Party }
     * 
     */
    public PartyAccountAttributeDetailsType.Party createPartyAccountAttributeDetailsTypeParty() {
        return new PartyAccountAttributeDetailsType.Party();
    }

    /**
     * Create an instance of {@link FaultType }
     * 
     */
    public FaultType createFaultType() {
        return new FaultType();
    }

    /**
     * Create an instance of {@link Security }
     * 
     */
    public Security createSecurity() {
        return new Security();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link ManageAccountResponseType }
     * 
     */
    public ManageAccountResponseType createManageAccountResponseType() {
        return new ManageAccountResponseType();
    }

    /**
     * Create an instance of {@link SearchCustomerResponseType.Customer.Alias }
     * 
     */
    public SearchCustomerResponseType.Customer.Alias createSearchCustomerResponseTypeCustomerAlias() {
        return new SearchCustomerResponseType.Customer.Alias();
    }

    /**
     * Create an instance of {@link GetSubAccountAttributesResponseType }
     * 
     */
    public GetSubAccountAttributesResponseType createGetSubAccountAttributesResponseType() {
        return new GetSubAccountAttributesResponseType();
    }

    /**
     * Create an instance of {@link CommunicationPreferenceType.CommunicationAddress.PhoneAddress }
     * 
     */
    public CommunicationPreferenceType.CommunicationAddress.PhoneAddress createCommunicationPreferenceTypeCommunicationAddressPhoneAddress() {
        return new CommunicationPreferenceType.CommunicationAddress.PhoneAddress();
    }

    /**
     * Create an instance of {@link PhoneIdListType }
     * 
     */
    public PhoneIdListType createPhoneIdListType() {
        return new PhoneIdListType();
    }

    /**
     * Create an instance of {@link ManageAccountResponseType.Party }
     * 
     */
    public ManageAccountResponseType.Party createManageAccountResponseTypeParty() {
        return new ManageAccountResponseType.Party();
    }

    /**
     * Create an instance of {@link ManageAccountResponseType.Party.Account }
     * 
     */
    public ManageAccountResponseType.Party.Account createManageAccountResponseTypePartyAccount() {
        return new ManageAccountResponseType.Party.Account();
    }

    /**
     * Create an instance of {@link RoleType }
     * 
     */
    public RoleType createRoleType() {
        return new RoleType();
    }

    /**
     * Create an instance of {@link BaseRequestType }
     * 
     */
    public BaseRequestType createBaseRequestType() {
        return new BaseRequestType();
    }

    /**
     * Create an instance of {@link CleanseCustomerDataRequestType }
     * 
     */
    public CleanseCustomerDataRequestType createCleanseCustomerDataRequestType() {
        return new CleanseCustomerDataRequestType();
    }

    /**
     * Create an instance of {@link ThinPartyListType }
     * 
     */
    public ThinPartyListType createThinPartyListType() {
        return new ThinPartyListType();
    }

    /**
     * Create an instance of {@link EmailType }
     * 
     */
    public EmailType createEmailType() {
        return new EmailType();
    }

    /**
     * Create an instance of {@link CommunicationPreferenceType.CommunicationAddress.EmailAddress }
     * 
     */
    public CommunicationPreferenceType.CommunicationAddress.EmailAddress createCommunicationPreferenceTypeCommunicationAddressEmailAddress() {
        return new CommunicationPreferenceType.CommunicationAddress.EmailAddress();
    }

    /**
     * Create an instance of {@link TimePeriodType }
     * 
     */
    public TimePeriodType createTimePeriodType() {
        return new TimePeriodType();
    }

    /**
     * Create an instance of {@link EmailAddressIDListType }
     * 
     */
    public EmailAddressIDListType createEmailAddressIDListType() {
        return new EmailAddressIDListType();
    }

    /**
     * Create an instance of {@link AccountType }
     * 
     */
    public AccountType createAccountType() {
        return new AccountType();
    }

    /**
     * Create an instance of {@link PartyType }
     * 
     */
    public PartyType createPartyType() {
        return new PartyType();
    }

    /**
     * Create an instance of {@link CustomerSearchConfigType }
     * 
     */
    public CustomerSearchConfigType createCustomerSearchConfigType() {
        return new CustomerSearchConfigType();
    }

    /**
     * Create an instance of {@link SearchCustomerResponseType.Customer }
     * 
     */
    public SearchCustomerResponseType.Customer createSearchCustomerResponseTypeCustomer() {
        return new SearchCustomerResponseType.Customer();
    }

    /**
     * Create an instance of {@link ManageSubAccountAttributesRequestType }
     * 
     */
    public ManageSubAccountAttributesRequestType createManageSubAccountAttributesRequestType() {
        return new ManageSubAccountAttributesRequestType();
    }

    /**
     * Create an instance of {@link AddressType.Zip }
     * 
     */
    public AddressType.Zip createAddressTypeZip() {
        return new AddressType.Zip();
    }

    /**
     * Create an instance of {@link RoleTypeList }
     * 
     */
    public RoleTypeList createRoleTypeList() {
        return new RoleTypeList();
    }

    /**
     * Create an instance of {@link CommunicationPreferenceType }
     * 
     */
    public CommunicationPreferenceType createCommunicationPreferenceType() {
        return new CommunicationPreferenceType();
    }

    /**
     * Create an instance of {@link SubAccountAttributesType }
     * 
     */
    public SubAccountAttributesType createSubAccountAttributesType() {
        return new SubAccountAttributesType();
    }

    /**
     * Create an instance of {@link MatchPartyResponseType.Party }
     * 
     */
    public MatchPartyResponseType.Party createMatchPartyResponseTypeParty() {
        return new MatchPartyResponseType.Party();
    }

    /**
     * Create an instance of {@link PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList }
     * 
     */
    public PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList createPartyAccountAttributeDetailsTypePartySubAccountWithAttributesList() {
        return new PartyAccountAttributeDetailsType.Party.SubAccountWithAttributesList();
    }

    /**
     * Create an instance of {@link MatchPartyRequestType.Configuration }
     * 
     */
    public MatchPartyRequestType.Configuration createMatchPartyRequestTypeConfiguration() {
        return new MatchPartyRequestType.Configuration();
    }

    /**
     * Create an instance of {@link ThinPartyType }
     * 
     */
    public ThinPartyType createThinPartyType() {
        return new ThinPartyType();
    }

    /**
     * Create an instance of {@link CleanseCustomerDataResponseType }
     * 
     */
    public CleanseCustomerDataResponseType createCleanseCustomerDataResponseType() {
        return new CleanseCustomerDataResponseType();
    }

    /**
     * Create an instance of {@link MatchPartyRequestType.Party.PartyDetail.CommunicationAddress }
     * 
     */
    public MatchPartyRequestType.Party.PartyDetail.CommunicationAddress createMatchPartyRequestTypePartyPartyDetailCommunicationAddress() {
        return new MatchPartyRequestType.Party.PartyDetail.CommunicationAddress();
    }

    /**
     * Create an instance of {@link PartyType.AliasList }
     * 
     */
    public PartyType.AliasList createPartyTypeAliasList() {
        return new PartyType.AliasList();
    }

    /**
     * Create an instance of {@link PostalAddressIdListType }
     * 
     */
    public PostalAddressIdListType createPostalAddressIdListType() {
        return new PostalAddressIdListType();
    }

    /**
     * Create an instance of {@link MatchPartyRequestType.Party }
     * 
     */
    public MatchPartyRequestType.Party createMatchPartyRequestTypeParty() {
        return new MatchPartyRequestType.Party();
    }

    /**
     * Create an instance of {@link CommunicationPreferenceType.CommunicationAddress }
     * 
     */
    public CommunicationPreferenceType.CommunicationAddress createCommunicationPreferenceTypeCommunicationAddress() {
        return new CommunicationPreferenceType.CommunicationAddress();
    }

    /**
     * Create an instance of {@link ConsolidatePartyRequestType }
     * 
     */
    public ConsolidatePartyRequestType createConsolidatePartyRequestType() {
        return new ConsolidatePartyRequestType();
    }

    /**
     * Create an instance of {@link TransferAccountResponseType }
     * 
     */
    public TransferAccountResponseType createTransferAccountResponseType() {
        return new TransferAccountResponseType();
    }

    /**
     * Create an instance of {@link SearchCustomerResponseType.Customer.Account }
     * 
     */
    public SearchCustomerResponseType.Customer.Account createSearchCustomerResponseTypeCustomerAccount() {
        return new SearchCustomerResponseType.Customer.Account();
    }

    /**
     * Create an instance of {@link MatchPartyResponseType }
     * 
     */
    public MatchPartyResponseType createMatchPartyResponseType() {
        return new MatchPartyResponseType();
    }

    /**
     * Create an instance of {@link ManageAccountRequestType }
     * 
     */
    public ManageAccountRequestType createManageAccountRequestType() {
        return new ManageAccountRequestType();
    }

    /**
     * Create an instance of {@link PaymentPreferenceListType }
     * 
     */
    public PaymentPreferenceListType createPaymentPreferenceListType() {
        return new PaymentPreferenceListType();
    }

    /**
     * Create an instance of {@link SearchCustomerResponseType }
     * 
     */
    public SearchCustomerResponseType createSearchCustomerResponseType() {
        return new SearchCustomerResponseType();
    }

    /**
     * Create an instance of {@link SearchCustomerRequestType }
     * 
     */
    public SearchCustomerRequestType createSearchCustomerRequestType() {
        return new SearchCustomerRequestType();
    }

    /**
     * Create an instance of {@link AliasType }
     * 
     */
    public AliasType createAliasType() {
        return new AliasType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManageSubAccountAttributesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "ManageSubAccountAttributesResponse")
    public JAXBElement<ManageSubAccountAttributesResponseType> createManageSubAccountAttributesResponse(ManageSubAccountAttributesResponseType value) {
        return new JAXBElement<ManageSubAccountAttributesResponseType>(_ManageSubAccountAttributesResponse_QNAME, ManageSubAccountAttributesResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchPartyResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "MatchPartyResponse")
    public JAXBElement<MatchPartyResponseType> createMatchPartyResponse(MatchPartyResponseType value) {
        return new JAXBElement<MatchPartyResponseType>(_MatchPartyResponse_QNAME, MatchPartyResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CleanseCustomerDataRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "CleanseCustomerDataRequest")
    public JAXBElement<CleanseCustomerDataRequestType> createCleanseCustomerDataRequest(CleanseCustomerDataRequestType value) {
        return new JAXBElement<CleanseCustomerDataRequestType>(_CleanseCustomerDataRequest_QNAME, CleanseCustomerDataRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferAccountResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "TransferAccountResponse")
    public JAXBElement<TransferAccountResponseType> createTransferAccountResponse(TransferAccountResponseType value) {
        return new JAXBElement<TransferAccountResponseType>(_TransferAccountResponse_QNAME, TransferAccountResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Security }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", name = "Security")
    public JAXBElement<Security> createSecurity(Security value) {
        return new JAXBElement<Security>(_Security_QNAME, Security.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsolidatePartyRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "ConsolidatePartyRequest")
    public JAXBElement<ConsolidatePartyRequestType> createConsolidatePartyRequest(ConsolidatePartyRequestType value) {
        return new JAXBElement<ConsolidatePartyRequestType>(_ConsolidatePartyRequest_QNAME, ConsolidatePartyRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubAccountAttributesResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "GetSubAccountAttributesResponse")
    public JAXBElement<GetSubAccountAttributesResponseType> createGetSubAccountAttributesResponse(GetSubAccountAttributesResponseType value) {
        return new JAXBElement<GetSubAccountAttributesResponseType>(_GetSubAccountAttributesResponse_QNAME, GetSubAccountAttributesResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MatchPartyRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "MatchPartyRequest")
    public JAXBElement<MatchPartyRequestType> createMatchPartyRequest(MatchPartyRequestType value) {
        return new JAXBElement<MatchPartyRequestType>(_MatchPartyRequest_QNAME, MatchPartyRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchCustomerRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "SearchCustomerRequest")
    public JAXBElement<SearchCustomerRequestType> createSearchCustomerRequest(SearchCustomerRequestType value) {
        return new JAXBElement<SearchCustomerRequestType>(_SearchCustomerRequest_QNAME, SearchCustomerRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConsolidatePartyResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "ConsolidatePartyResponse")
    public JAXBElement<ConsolidatePartyResponseType> createConsolidatePartyResponse(ConsolidatePartyResponseType value) {
        return new JAXBElement<ConsolidatePartyResponseType>(_ConsolidatePartyResponse_QNAME, ConsolidatePartyResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SearchCustomerResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "SearchCustomerResponse")
    public JAXBElement<SearchCustomerResponseType> createSearchCustomerResponse(SearchCustomerResponseType value) {
        return new JAXBElement<SearchCustomerResponseType>(_SearchCustomerResponse_QNAME, SearchCustomerResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSubAccountAttributesRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "GetSubAccountAttributesRequest")
    public JAXBElement<GetSubAccountAttributesRequestType> createGetSubAccountAttributesRequest(GetSubAccountAttributesRequestType value) {
        return new JAXBElement<GetSubAccountAttributesRequestType>(_GetSubAccountAttributesRequest_QNAME, GetSubAccountAttributesRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CleanseCustomerDataResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "CleanseCustomerDataResponse")
    public JAXBElement<CleanseCustomerDataResponseType> createCleanseCustomerDataResponse(CleanseCustomerDataResponseType value) {
        return new JAXBElement<CleanseCustomerDataResponseType>(_CleanseCustomerDataResponse_QNAME, CleanseCustomerDataResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManageAccountResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "ManageAccountResponse")
    public JAXBElement<ManageAccountResponseType> createManageAccountResponse(ManageAccountResponseType value) {
        return new JAXBElement<ManageAccountResponseType>(_ManageAccountResponse_QNAME, ManageAccountResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManageAccountRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "ManageAccountRequest")
    public JAXBElement<ManageAccountRequestType> createManageAccountRequest(ManageAccountRequestType value) {
        return new JAXBElement<ManageAccountRequestType>(_ManageAccountRequest_QNAME, ManageAccountRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransferAccountRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "TransferAccountRequest")
    public JAXBElement<TransferAccountRequestType> createTransferAccountRequest(TransferAccountRequestType value) {
        return new JAXBElement<TransferAccountRequestType>(_TransferAccountRequest_QNAME, TransferAccountRequestType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FaultType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "faultElement")
    public JAXBElement<FaultType> createFaultElement(FaultType value) {
        return new JAXBElement<FaultType>(_FaultElement_QNAME, FaultType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManageSubAccountAttributesRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://webservices.bestbuy.com/ca/services/entity/v2", name = "ManageSubAccountAttributesRequest")
    public JAXBElement<ManageSubAccountAttributesRequestType> createManageSubAccountAttributesRequest(ManageSubAccountAttributesRequestType value) {
        return new JAXBElement<ManageSubAccountAttributesRequestType>(_ManageSubAccountAttributesRequest_QNAME, ManageSubAccountAttributesRequestType.class, null, value);
    }

}
