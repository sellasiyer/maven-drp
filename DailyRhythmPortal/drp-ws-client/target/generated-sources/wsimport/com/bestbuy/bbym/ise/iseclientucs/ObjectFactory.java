
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.ise.iseclientucs package. 
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

    private final static QName _AuthenticationFailureException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "AuthenticationFailureException");
    private final static QName _PutNotificationStatus_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatus");
    private final static QName _PutNotificationStatusMultiResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusMultiResponse");
    private final static QName _PutNotificationStatusMulti_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusMulti");
    private final static QName _GetNotificationStatusResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getNotificationStatusResponse");
    private final static QName _AccountLockedException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "AccountLockedException");
    private final static QName _GetUpgradeEligibilityAsyncPollResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeEligibilityAsyncPollResponse");
    private final static QName _CAPException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "CAPException");
    private final static QName _InvalidArgumentException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "InvalidArgumentException");
    private final static QName _GetScorecardResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getScorecardResponse");
    private final static QName _AccountNotFoundException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "AccountNotFoundException");
    private final static QName _BusinessCustomerException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "BusinessCustomerException");
    private final static QName _GetNotificationStatusPlus_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getNotificationStatusPlus");
    private final static QName _PutNotificationStatusPlusMulti_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusPlusMulti");
    private final static QName _GetUpgradeEligibilityResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeEligibilityResponse");
    private final static QName _GetCachedUpgradeEligibility_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getCachedUpgradeEligibility");
    private final static QName _PutNotificationStatusWithCachedUpgradeCheck_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusWithCachedUpgradeCheck");
    private final static QName _GetUpgradeCheckHistory_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeCheckHistory");
    private final static QName _GetCarrierAndUpgradeCheckerStatusResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getCarrierAndUpgradeCheckerStatusResponse");
    private final static QName _GetUpgradeEligibilityAsyncResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeEligibilityAsyncResponse");
    private final static QName _PutNotificationStatusPlusResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusPlusResponse");
    private final static QName _GetNotificationStatusPlusResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getNotificationStatusPlusResponse");
    private final static QName _PutNotificationStatusWithCachedUpgradeCheckResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusWithCachedUpgradeCheckResponse");
    private final static QName _InvalidAccountPasswordException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "InvalidAccountPasswordException");
    private final static QName _PutNotificationStatusPlus_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusPlus");
    private final static QName _PutNotificationStatusResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusResponse");
    private final static QName _GetCarrierAndUpgradeCheckerStatus_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getCarrierAndUpgradeCheckerStatus");
    private final static QName _GetUpgradeEligibilityAsync_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeEligibilityAsync");
    private final static QName _GetCachedUpgradeEligibilityResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getCachedUpgradeEligibilityResponse");
    private final static QName _GetScorecard_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getScorecard");
    private final static QName _UnknownFailureException_QNAME = new QName("http://bestbuy.com/bbym/ucs", "UnknownFailureException");
    private final static QName _GetUpgradeEligibilityAsyncPoll_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeEligibilityAsyncPoll");
    private final static QName _GetUpgradeCheckHistoryResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeCheckHistoryResponse");
    private final static QName _GetNotificationStatus_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getNotificationStatus");
    private final static QName _GetUpgradeEligibility_QNAME = new QName("http://bestbuy.com/bbym/ucs", "getUpgradeEligibility");
    private final static QName _PutNotificationStatusPlusMultiResponse_QNAME = new QName("http://bestbuy.com/bbym/ucs", "putNotificationStatusPlusMultiResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.ise.iseclientucs
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetUpgradeEligibilityAsync }
     * 
     */
    public GetUpgradeEligibilityAsync createGetUpgradeEligibilityAsync() {
        return new GetUpgradeEligibilityAsync();
    }

    /**
     * Create an instance of {@link NotificationStatusMultiPutRequest }
     * 
     */
    public NotificationStatusMultiPutRequest createNotificationStatusMultiPutRequest() {
        return new NotificationStatusMultiPutRequest();
    }

    /**
     * Create an instance of {@link BusinessCustomerException }
     * 
     */
    public BusinessCustomerException createBusinessCustomerException() {
        return new BusinessCustomerException();
    }

    /**
     * Create an instance of {@link GetNotificationStatusPlus }
     * 
     */
    public GetNotificationStatusPlus createGetNotificationStatusPlus() {
        return new GetNotificationStatusPlus();
    }

    /**
     * Create an instance of {@link PutNotificationStatusMultiResponse }
     * 
     */
    public PutNotificationStatusMultiResponse createPutNotificationStatusMultiResponse() {
        return new PutNotificationStatusMultiResponse();
    }

    /**
     * Create an instance of {@link AuthenticationFailureException }
     * 
     */
    public AuthenticationFailureException createAuthenticationFailureException() {
        return new AuthenticationFailureException();
    }

    /**
     * Create an instance of {@link NotificationStatusGetResponse }
     * 
     */
    public NotificationStatusGetResponse createNotificationStatusGetResponse() {
        return new NotificationStatusGetResponse();
    }

    /**
     * Create an instance of {@link InvalidArgumentException }
     * 
     */
    public InvalidArgumentException createInvalidArgumentException() {
        return new InvalidArgumentException();
    }

    /**
     * Create an instance of {@link ScorecardRequest }
     * 
     */
    public ScorecardRequest createScorecardRequest() {
        return new ScorecardRequest();
    }

    /**
     * Create an instance of {@link GetUpgradeEligibility }
     * 
     */
    public GetUpgradeEligibility createGetUpgradeEligibility() {
        return new GetUpgradeEligibility();
    }

    /**
     * Create an instance of {@link PutNotificationStatusPlus }
     * 
     */
    public PutNotificationStatusPlus createPutNotificationStatusPlus() {
        return new PutNotificationStatusPlus();
    }

    /**
     * Create an instance of {@link ScorecardResponse }
     * 
     */
    public ScorecardResponse createScorecardResponse() {
        return new ScorecardResponse();
    }

    /**
     * Create an instance of {@link NotificationStatusWithCachedUpgradeCheckPutResponse }
     * 
     */
    public NotificationStatusWithCachedUpgradeCheckPutResponse createNotificationStatusWithCachedUpgradeCheckPutResponse() {
        return new NotificationStatusWithCachedUpgradeCheckPutResponse();
    }

    /**
     * Create an instance of {@link InvalidAccountPasswordException }
     * 
     */
    public InvalidAccountPasswordException createInvalidAccountPasswordException() {
        return new InvalidAccountPasswordException();
    }

    /**
     * Create an instance of {@link UpgradeCheck }
     * 
     */
    public UpgradeCheck createUpgradeCheck() {
        return new UpgradeCheck();
    }

    /**
     * Create an instance of {@link NotificationStatusWithCachedUpgradeCheckPutRequest }
     * 
     */
    public NotificationStatusWithCachedUpgradeCheckPutRequest createNotificationStatusWithCachedUpgradeCheckPutRequest() {
        return new NotificationStatusWithCachedUpgradeCheckPutRequest();
    }

    /**
     * Create an instance of {@link GetCarrierAndUpgradeCheckerStatus }
     * 
     */
    public GetCarrierAndUpgradeCheckerStatus createGetCarrierAndUpgradeCheckerStatus() {
        return new GetCarrierAndUpgradeCheckerStatus();
    }

    /**
     * Create an instance of {@link NotificationStatusPutResponse }
     * 
     */
    public NotificationStatusPutResponse createNotificationStatusPutResponse() {
        return new NotificationStatusPutResponse();
    }

    /**
     * Create an instance of {@link UpgradeEligibilityRequest }
     * 
     */
    public UpgradeEligibilityRequest createUpgradeEligibilityRequest() {
        return new UpgradeEligibilityRequest();
    }

    /**
     * Create an instance of {@link GetUpgradeEligibilityResponse }
     * 
     */
    public GetUpgradeEligibilityResponse createGetUpgradeEligibilityResponse() {
        return new GetUpgradeEligibilityResponse();
    }

    /**
     * Create an instance of {@link GetNotificationStatusResponse }
     * 
     */
    public GetNotificationStatusResponse createGetNotificationStatusResponse() {
        return new GetNotificationStatusResponse();
    }

    /**
     * Create an instance of {@link GetNotificationStatus }
     * 
     */
    public GetNotificationStatus createGetNotificationStatus() {
        return new GetNotificationStatus();
    }

    /**
     * Create an instance of {@link PutNotificationStatusResponse }
     * 
     */
    public PutNotificationStatusResponse createPutNotificationStatusResponse() {
        return new PutNotificationStatusResponse();
    }

    /**
     * Create an instance of {@link GetCachedUpgradeEligibility }
     * 
     */
    public GetCachedUpgradeEligibility createGetCachedUpgradeEligibility() {
        return new GetCachedUpgradeEligibility();
    }

    /**
     * Create an instance of {@link GetUpgradeCheckHistory }
     * 
     */
    public GetUpgradeCheckHistory createGetUpgradeCheckHistory() {
        return new GetUpgradeCheckHistory();
    }

    /**
     * Create an instance of {@link GetNotificationStatusPlusResponse }
     * 
     */
    public GetNotificationStatusPlusResponse createGetNotificationStatusPlusResponse() {
        return new GetNotificationStatusPlusResponse();
    }

    /**
     * Create an instance of {@link Subscriber }
     * 
     */
    public Subscriber createSubscriber() {
        return new Subscriber();
    }

    /**
     * Create an instance of {@link GetCarrierAndUpgradeCheckerStatusResponse }
     * 
     */
    public GetCarrierAndUpgradeCheckerStatusResponse createGetCarrierAndUpgradeCheckerStatusResponse() {
        return new GetCarrierAndUpgradeCheckerStatusResponse();
    }

    /**
     * Create an instance of {@link UpgradeCheckHistoryResponse }
     * 
     */
    public UpgradeCheckHistoryResponse createUpgradeCheckHistoryResponse() {
        return new UpgradeCheckHistoryResponse();
    }

    /**
     * Create an instance of {@link PutNotificationStatusPlusMulti }
     * 
     */
    public PutNotificationStatusPlusMulti createPutNotificationStatusPlusMulti() {
        return new PutNotificationStatusPlusMulti();
    }

    /**
     * Create an instance of {@link GetUpgradeEligibilityAsyncPollResponse }
     * 
     */
    public GetUpgradeEligibilityAsyncPollResponse createGetUpgradeEligibilityAsyncPollResponse() {
        return new GetUpgradeEligibilityAsyncPollResponse();
    }

    /**
     * Create an instance of {@link NotificationStatusPutRequest }
     * 
     */
    public NotificationStatusPutRequest createNotificationStatusPutRequest() {
        return new NotificationStatusPutRequest();
    }

    /**
     * Create an instance of {@link UpgradeCheckHistoryRequest }
     * 
     */
    public UpgradeCheckHistoryRequest createUpgradeCheckHistoryRequest() {
        return new UpgradeCheckHistoryRequest();
    }

    /**
     * Create an instance of {@link PutNotificationStatusPlusMultiResponse }
     * 
     */
    public PutNotificationStatusPlusMultiResponse createPutNotificationStatusPlusMultiResponse() {
        return new PutNotificationStatusPlusMultiResponse();
    }

    /**
     * Create an instance of {@link GetUpgradeCheckHistoryResponse }
     * 
     */
    public GetUpgradeCheckHistoryResponse createGetUpgradeCheckHistoryResponse() {
        return new GetUpgradeCheckHistoryResponse();
    }

    /**
     * Create an instance of {@link CarrierAndUpgradeCheckerStatus }
     * 
     */
    public CarrierAndUpgradeCheckerStatus createCarrierAndUpgradeCheckerStatus() {
        return new CarrierAndUpgradeCheckerStatus();
    }

    /**
     * Create an instance of {@link GetUpgradeEligibilityAsyncResponse }
     * 
     */
    public GetUpgradeEligibilityAsyncResponse createGetUpgradeEligibilityAsyncResponse() {
        return new GetUpgradeEligibilityAsyncResponse();
    }

    /**
     * Create an instance of {@link InternationalBusinessHierarchy }
     * 
     */
    public InternationalBusinessHierarchy createInternationalBusinessHierarchy() {
        return new InternationalBusinessHierarchy();
    }

    /**
     * Create an instance of {@link UpgradeEligibilityResponse }
     * 
     */
    public UpgradeEligibilityResponse createUpgradeEligibilityResponse() {
        return new UpgradeEligibilityResponse();
    }

    /**
     * Create an instance of {@link PutNotificationStatusPlusResponse }
     * 
     */
    public PutNotificationStatusPlusResponse createPutNotificationStatusPlusResponse() {
        return new PutNotificationStatusPlusResponse();
    }

    /**
     * Create an instance of {@link PutNotificationStatusWithCachedUpgradeCheck }
     * 
     */
    public PutNotificationStatusWithCachedUpgradeCheck createPutNotificationStatusWithCachedUpgradeCheck() {
        return new PutNotificationStatusWithCachedUpgradeCheck();
    }

    /**
     * Create an instance of {@link GetUpgradeEligibilityAsyncPoll }
     * 
     */
    public GetUpgradeEligibilityAsyncPoll createGetUpgradeEligibilityAsyncPoll() {
        return new GetUpgradeEligibilityAsyncPoll();
    }

    /**
     * Create an instance of {@link CarrierAndUpgradeCheckerStatusResponse }
     * 
     */
    public CarrierAndUpgradeCheckerStatusResponse createCarrierAndUpgradeCheckerStatusResponse() {
        return new CarrierAndUpgradeCheckerStatusResponse();
    }

    /**
     * Create an instance of {@link PutNotificationStatusMulti }
     * 
     */
    public PutNotificationStatusMulti createPutNotificationStatusMulti() {
        return new PutNotificationStatusMulti();
    }

    /**
     * Create an instance of {@link CarrierAndUpgradeCheckerStatusRequest }
     * 
     */
    public CarrierAndUpgradeCheckerStatusRequest createCarrierAndUpgradeCheckerStatusRequest() {
        return new CarrierAndUpgradeCheckerStatusRequest();
    }

    /**
     * Create an instance of {@link AccountNotFoundException }
     * 
     */
    public AccountNotFoundException createAccountNotFoundException() {
        return new AccountNotFoundException();
    }

    /**
     * Create an instance of {@link PutNotificationStatusWithCachedUpgradeCheckResponse }
     * 
     */
    public PutNotificationStatusWithCachedUpgradeCheckResponse createPutNotificationStatusWithCachedUpgradeCheckResponse() {
        return new PutNotificationStatusWithCachedUpgradeCheckResponse();
    }

    /**
     * Create an instance of {@link PutNotificationStatus }
     * 
     */
    public PutNotificationStatus createPutNotificationStatus() {
        return new PutNotificationStatus();
    }

    /**
     * Create an instance of {@link UnknownFailureException }
     * 
     */
    public UnknownFailureException createUnknownFailureException() {
        return new UnknownFailureException();
    }

    /**
     * Create an instance of {@link GetScorecardResponse }
     * 
     */
    public GetScorecardResponse createGetScorecardResponse() {
        return new GetScorecardResponse();
    }

    /**
     * Create an instance of {@link SubscriberNotificationStatus }
     * 
     */
    public SubscriberNotificationStatus createSubscriberNotificationStatus() {
        return new SubscriberNotificationStatus();
    }

    /**
     * Create an instance of {@link GetCachedUpgradeEligibilityResponse }
     * 
     */
    public GetCachedUpgradeEligibilityResponse createGetCachedUpgradeEligibilityResponse() {
        return new GetCachedUpgradeEligibilityResponse();
    }

    /**
     * Create an instance of {@link NotificationStatusMultiPutResponse }
     * 
     */
    public NotificationStatusMultiPutResponse createNotificationStatusMultiPutResponse() {
        return new NotificationStatusMultiPutResponse();
    }

    /**
     * Create an instance of {@link CachedUpgradeEligibilityRequest }
     * 
     */
    public CachedUpgradeEligibilityRequest createCachedUpgradeEligibilityRequest() {
        return new CachedUpgradeEligibilityRequest();
    }

    /**
     * Create an instance of {@link GetScorecard }
     * 
     */
    public GetScorecard createGetScorecard() {
        return new GetScorecard();
    }

    /**
     * Create an instance of {@link AccountLockedException }
     * 
     */
    public AccountLockedException createAccountLockedException() {
        return new AccountLockedException();
    }

    /**
     * Create an instance of {@link CAPException }
     * 
     */
    public CAPException createCAPException() {
        return new CAPException();
    }

    /**
     * Create an instance of {@link NotificationStatusGetRequest }
     * 
     */
    public NotificationStatusGetRequest createNotificationStatusGetRequest() {
        return new NotificationStatusGetRequest();
    }

    /**
     * Create an instance of {@link NotificationStatusChange }
     * 
     */
    public NotificationStatusChange createNotificationStatusChange() {
        return new NotificationStatusChange();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AuthenticationFailureException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "AuthenticationFailureException")
    public JAXBElement<AuthenticationFailureException> createAuthenticationFailureException(AuthenticationFailureException value) {
        return new JAXBElement<AuthenticationFailureException>(_AuthenticationFailureException_QNAME, AuthenticationFailureException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatus")
    public JAXBElement<PutNotificationStatus> createPutNotificationStatus(PutNotificationStatus value) {
        return new JAXBElement<PutNotificationStatus>(_PutNotificationStatus_QNAME, PutNotificationStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusMultiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusMultiResponse")
    public JAXBElement<PutNotificationStatusMultiResponse> createPutNotificationStatusMultiResponse(PutNotificationStatusMultiResponse value) {
        return new JAXBElement<PutNotificationStatusMultiResponse>(_PutNotificationStatusMultiResponse_QNAME, PutNotificationStatusMultiResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusMulti }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusMulti")
    public JAXBElement<PutNotificationStatusMulti> createPutNotificationStatusMulti(PutNotificationStatusMulti value) {
        return new JAXBElement<PutNotificationStatusMulti>(_PutNotificationStatusMulti_QNAME, PutNotificationStatusMulti.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNotificationStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getNotificationStatusResponse")
    public JAXBElement<GetNotificationStatusResponse> createGetNotificationStatusResponse(GetNotificationStatusResponse value) {
        return new JAXBElement<GetNotificationStatusResponse>(_GetNotificationStatusResponse_QNAME, GetNotificationStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountLockedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "AccountLockedException")
    public JAXBElement<AccountLockedException> createAccountLockedException(AccountLockedException value) {
        return new JAXBElement<AccountLockedException>(_AccountLockedException_QNAME, AccountLockedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeEligibilityAsyncPollResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeEligibilityAsyncPollResponse")
    public JAXBElement<GetUpgradeEligibilityAsyncPollResponse> createGetUpgradeEligibilityAsyncPollResponse(GetUpgradeEligibilityAsyncPollResponse value) {
        return new JAXBElement<GetUpgradeEligibilityAsyncPollResponse>(_GetUpgradeEligibilityAsyncPollResponse_QNAME, GetUpgradeEligibilityAsyncPollResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CAPException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "CAPException")
    public JAXBElement<CAPException> createCAPException(CAPException value) {
        return new JAXBElement<CAPException>(_CAPException_QNAME, CAPException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidArgumentException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "InvalidArgumentException")
    public JAXBElement<InvalidArgumentException> createInvalidArgumentException(InvalidArgumentException value) {
        return new JAXBElement<InvalidArgumentException>(_InvalidArgumentException_QNAME, InvalidArgumentException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScorecardResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getScorecardResponse")
    public JAXBElement<GetScorecardResponse> createGetScorecardResponse(GetScorecardResponse value) {
        return new JAXBElement<GetScorecardResponse>(_GetScorecardResponse_QNAME, GetScorecardResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccountNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "AccountNotFoundException")
    public JAXBElement<AccountNotFoundException> createAccountNotFoundException(AccountNotFoundException value) {
        return new JAXBElement<AccountNotFoundException>(_AccountNotFoundException_QNAME, AccountNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessCustomerException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "BusinessCustomerException")
    public JAXBElement<BusinessCustomerException> createBusinessCustomerException(BusinessCustomerException value) {
        return new JAXBElement<BusinessCustomerException>(_BusinessCustomerException_QNAME, BusinessCustomerException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNotificationStatusPlus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getNotificationStatusPlus")
    public JAXBElement<GetNotificationStatusPlus> createGetNotificationStatusPlus(GetNotificationStatusPlus value) {
        return new JAXBElement<GetNotificationStatusPlus>(_GetNotificationStatusPlus_QNAME, GetNotificationStatusPlus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusPlusMulti }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusPlusMulti")
    public JAXBElement<PutNotificationStatusPlusMulti> createPutNotificationStatusPlusMulti(PutNotificationStatusPlusMulti value) {
        return new JAXBElement<PutNotificationStatusPlusMulti>(_PutNotificationStatusPlusMulti_QNAME, PutNotificationStatusPlusMulti.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeEligibilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeEligibilityResponse")
    public JAXBElement<GetUpgradeEligibilityResponse> createGetUpgradeEligibilityResponse(GetUpgradeEligibilityResponse value) {
        return new JAXBElement<GetUpgradeEligibilityResponse>(_GetUpgradeEligibilityResponse_QNAME, GetUpgradeEligibilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCachedUpgradeEligibility }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getCachedUpgradeEligibility")
    public JAXBElement<GetCachedUpgradeEligibility> createGetCachedUpgradeEligibility(GetCachedUpgradeEligibility value) {
        return new JAXBElement<GetCachedUpgradeEligibility>(_GetCachedUpgradeEligibility_QNAME, GetCachedUpgradeEligibility.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusWithCachedUpgradeCheck }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusWithCachedUpgradeCheck")
    public JAXBElement<PutNotificationStatusWithCachedUpgradeCheck> createPutNotificationStatusWithCachedUpgradeCheck(PutNotificationStatusWithCachedUpgradeCheck value) {
        return new JAXBElement<PutNotificationStatusWithCachedUpgradeCheck>(_PutNotificationStatusWithCachedUpgradeCheck_QNAME, PutNotificationStatusWithCachedUpgradeCheck.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeCheckHistory }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeCheckHistory")
    public JAXBElement<GetUpgradeCheckHistory> createGetUpgradeCheckHistory(GetUpgradeCheckHistory value) {
        return new JAXBElement<GetUpgradeCheckHistory>(_GetUpgradeCheckHistory_QNAME, GetUpgradeCheckHistory.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCarrierAndUpgradeCheckerStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getCarrierAndUpgradeCheckerStatusResponse")
    public JAXBElement<GetCarrierAndUpgradeCheckerStatusResponse> createGetCarrierAndUpgradeCheckerStatusResponse(GetCarrierAndUpgradeCheckerStatusResponse value) {
        return new JAXBElement<GetCarrierAndUpgradeCheckerStatusResponse>(_GetCarrierAndUpgradeCheckerStatusResponse_QNAME, GetCarrierAndUpgradeCheckerStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeEligibilityAsyncResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeEligibilityAsyncResponse")
    public JAXBElement<GetUpgradeEligibilityAsyncResponse> createGetUpgradeEligibilityAsyncResponse(GetUpgradeEligibilityAsyncResponse value) {
        return new JAXBElement<GetUpgradeEligibilityAsyncResponse>(_GetUpgradeEligibilityAsyncResponse_QNAME, GetUpgradeEligibilityAsyncResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusPlusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusPlusResponse")
    public JAXBElement<PutNotificationStatusPlusResponse> createPutNotificationStatusPlusResponse(PutNotificationStatusPlusResponse value) {
        return new JAXBElement<PutNotificationStatusPlusResponse>(_PutNotificationStatusPlusResponse_QNAME, PutNotificationStatusPlusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNotificationStatusPlusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getNotificationStatusPlusResponse")
    public JAXBElement<GetNotificationStatusPlusResponse> createGetNotificationStatusPlusResponse(GetNotificationStatusPlusResponse value) {
        return new JAXBElement<GetNotificationStatusPlusResponse>(_GetNotificationStatusPlusResponse_QNAME, GetNotificationStatusPlusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusWithCachedUpgradeCheckResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusWithCachedUpgradeCheckResponse")
    public JAXBElement<PutNotificationStatusWithCachedUpgradeCheckResponse> createPutNotificationStatusWithCachedUpgradeCheckResponse(PutNotificationStatusWithCachedUpgradeCheckResponse value) {
        return new JAXBElement<PutNotificationStatusWithCachedUpgradeCheckResponse>(_PutNotificationStatusWithCachedUpgradeCheckResponse_QNAME, PutNotificationStatusWithCachedUpgradeCheckResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidAccountPasswordException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "InvalidAccountPasswordException")
    public JAXBElement<InvalidAccountPasswordException> createInvalidAccountPasswordException(InvalidAccountPasswordException value) {
        return new JAXBElement<InvalidAccountPasswordException>(_InvalidAccountPasswordException_QNAME, InvalidAccountPasswordException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusPlus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusPlus")
    public JAXBElement<PutNotificationStatusPlus> createPutNotificationStatusPlus(PutNotificationStatusPlus value) {
        return new JAXBElement<PutNotificationStatusPlus>(_PutNotificationStatusPlus_QNAME, PutNotificationStatusPlus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusResponse")
    public JAXBElement<PutNotificationStatusResponse> createPutNotificationStatusResponse(PutNotificationStatusResponse value) {
        return new JAXBElement<PutNotificationStatusResponse>(_PutNotificationStatusResponse_QNAME, PutNotificationStatusResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCarrierAndUpgradeCheckerStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getCarrierAndUpgradeCheckerStatus")
    public JAXBElement<GetCarrierAndUpgradeCheckerStatus> createGetCarrierAndUpgradeCheckerStatus(GetCarrierAndUpgradeCheckerStatus value) {
        return new JAXBElement<GetCarrierAndUpgradeCheckerStatus>(_GetCarrierAndUpgradeCheckerStatus_QNAME, GetCarrierAndUpgradeCheckerStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeEligibilityAsync }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeEligibilityAsync")
    public JAXBElement<GetUpgradeEligibilityAsync> createGetUpgradeEligibilityAsync(GetUpgradeEligibilityAsync value) {
        return new JAXBElement<GetUpgradeEligibilityAsync>(_GetUpgradeEligibilityAsync_QNAME, GetUpgradeEligibilityAsync.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCachedUpgradeEligibilityResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getCachedUpgradeEligibilityResponse")
    public JAXBElement<GetCachedUpgradeEligibilityResponse> createGetCachedUpgradeEligibilityResponse(GetCachedUpgradeEligibilityResponse value) {
        return new JAXBElement<GetCachedUpgradeEligibilityResponse>(_GetCachedUpgradeEligibilityResponse_QNAME, GetCachedUpgradeEligibilityResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetScorecard }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getScorecard")
    public JAXBElement<GetScorecard> createGetScorecard(GetScorecard value) {
        return new JAXBElement<GetScorecard>(_GetScorecard_QNAME, GetScorecard.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnknownFailureException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "UnknownFailureException")
    public JAXBElement<UnknownFailureException> createUnknownFailureException(UnknownFailureException value) {
        return new JAXBElement<UnknownFailureException>(_UnknownFailureException_QNAME, UnknownFailureException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeEligibilityAsyncPoll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeEligibilityAsyncPoll")
    public JAXBElement<GetUpgradeEligibilityAsyncPoll> createGetUpgradeEligibilityAsyncPoll(GetUpgradeEligibilityAsyncPoll value) {
        return new JAXBElement<GetUpgradeEligibilityAsyncPoll>(_GetUpgradeEligibilityAsyncPoll_QNAME, GetUpgradeEligibilityAsyncPoll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeCheckHistoryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeCheckHistoryResponse")
    public JAXBElement<GetUpgradeCheckHistoryResponse> createGetUpgradeCheckHistoryResponse(GetUpgradeCheckHistoryResponse value) {
        return new JAXBElement<GetUpgradeCheckHistoryResponse>(_GetUpgradeCheckHistoryResponse_QNAME, GetUpgradeCheckHistoryResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetNotificationStatus }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getNotificationStatus")
    public JAXBElement<GetNotificationStatus> createGetNotificationStatus(GetNotificationStatus value) {
        return new JAXBElement<GetNotificationStatus>(_GetNotificationStatus_QNAME, GetNotificationStatus.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUpgradeEligibility }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "getUpgradeEligibility")
    public JAXBElement<GetUpgradeEligibility> createGetUpgradeEligibility(GetUpgradeEligibility value) {
        return new JAXBElement<GetUpgradeEligibility>(_GetUpgradeEligibility_QNAME, GetUpgradeEligibility.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PutNotificationStatusPlusMultiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/ucs", name = "putNotificationStatusPlusMultiResponse")
    public JAXBElement<PutNotificationStatusPlusMultiResponse> createPutNotificationStatusPlusMultiResponse(PutNotificationStatusPlusMultiResponse value) {
        return new JAXBElement<PutNotificationStatusPlusMultiResponse>(_PutNotificationStatusPlusMultiResponse_QNAME, PutNotificationStatusPlusMultiResponse.class, null, value);
    }

}
