
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bestbuy.bbym.logistics.device.status.component.v1 package. 
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

    private final static QName _Status_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "Status");
    private final static QName _ReturnStatusUpdate_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "ReturnStatusUpdate");
    private final static QName _UpdateTimeStamp_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "UpdateTimeStamp");
    private final static QName _Disposition_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "Disposition");
    private final static QName _ReturnStatusUpdateMessage_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "ReturnStatusUpdateMessage");
    private final static QName _ReturnStatus_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "ReturnStatus");
    private final static QName _ReturnID_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "ReturnID");
    private final static QName _StatusUpdate_QNAME = new QName("http://bestbuy.com/bbym/logistics/device/status/component/v1", "StatusUpdate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bestbuy.bbym.logistics.device.status.component.v1
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ReturnStatusUpdateMessageType }
     * 
     */
    public ReturnStatusUpdateMessageType createReturnStatusUpdateMessageType() {
        return new ReturnStatusUpdateMessageType();
    }

    /**
     * Create an instance of {@link ReturnStatusUpdateType }
     * 
     */
    public ReturnStatusUpdateType createReturnStatusUpdateType() {
        return new ReturnStatusUpdateType();
    }

    /**
     * Create an instance of {@link ChangeDataType }
     * 
     */
    public ChangeDataType createChangeDataType() {
        return new ChangeDataType();
    }

    /**
     * Create an instance of {@link GetRMAMessageType }
     * 
     */
    public GetRMAMessageType createGetRMAMessageType() {
        return new GetRMAMessageType();
    }

    /**
     * Create an instance of {@link StatusUpdateType }
     * 
     */
    public StatusUpdateType createStatusUpdateType() {
        return new StatusUpdateType();
    }

    /**
     * Create an instance of {@link ReturnStatusType }
     * 
     */
    public ReturnStatusType createReturnStatusType() {
        return new ReturnStatusType();
    }

    /**
     * Create an instance of {@link RMASearchType }
     * 
     */
    public RMASearchType createRMASearchType() {
        return new RMASearchType();
    }

    /**
     * Create an instance of {@link RMASearchListType }
     * 
     */
    public RMASearchListType createRMASearchListType() {
        return new RMASearchListType();
    }

    /**
     * Create an instance of {@link StatusType }
     * 
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link QuestionAnswerType }
     * 
     */
    public QuestionAnswerType createQuestionAnswerType() {
        return new QuestionAnswerType();
    }

    /**
     * Create an instance of {@link RepairType }
     * 
     */
    public RepairType createRepairType() {
        return new RepairType();
    }

    /**
     * Create an instance of {@link FGIType }
     * 
     */
    public FGIType createFGIType() {
        return new FGIType();
    }

    /**
     * Create an instance of {@link QuestionAnswerListType }
     * 
     */
    public QuestionAnswerListType createQuestionAnswerListType() {
        return new QuestionAnswerListType();
    }

    /**
     * Create an instance of {@link ReturnStatusMessageType }
     * 
     */
    public ReturnStatusMessageType createReturnStatusMessageType() {
        return new ReturnStatusMessageType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "Status")
    public JAXBElement<StatusType> createStatus(StatusType value) {
        return new JAXBElement<StatusType>(_Status_QNAME, StatusType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnStatusUpdateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "ReturnStatusUpdate")
    public JAXBElement<ReturnStatusUpdateType> createReturnStatusUpdate(ReturnStatusUpdateType value) {
        return new JAXBElement<ReturnStatusUpdateType>(_ReturnStatusUpdate_QNAME, ReturnStatusUpdateType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link XMLGregorianCalendar }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "UpdateTimeStamp")
    public JAXBElement<XMLGregorianCalendar> createUpdateTimeStamp(XMLGregorianCalendar value) {
        return new JAXBElement<XMLGregorianCalendar>(_UpdateTimeStamp_QNAME, XMLGregorianCalendar.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "Disposition")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createDisposition(String value) {
        return new JAXBElement<String>(_Disposition_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReturnStatusUpdateMessageType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "ReturnStatusUpdateMessage")
    public JAXBElement<ReturnStatusUpdateMessageType> createReturnStatusUpdateMessage(ReturnStatusUpdateMessageType value) {
        return new JAXBElement<ReturnStatusUpdateMessageType>(_ReturnStatusUpdateMessage_QNAME, ReturnStatusUpdateMessageType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "ReturnStatus")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    public JAXBElement<String> createReturnStatus(String value) {
        return new JAXBElement<String>(_ReturnStatus_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IdentifierType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "ReturnID")
    public JAXBElement<IdentifierType> createReturnID(IdentifierType value) {
        return new JAXBElement<IdentifierType>(_ReturnID_QNAME, IdentifierType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StatusUpdateType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", name = "StatusUpdate")
    public JAXBElement<StatusUpdateType> createStatusUpdate(StatusUpdateType value) {
        return new JAXBElement<StatusUpdateType>(_StatusUpdate_QNAME, StatusUpdateType.class, null, value);
    }

}
