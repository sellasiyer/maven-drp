
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Contains information about the return transaction
 * 
 * <p>Java class for ReturnStatusType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ReturnStatusType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransactionID" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}TransactionIDType"/>
 *         &lt;element name="TransactionType" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}TransactionStatusType"/>
 *         &lt;element name="TransactionDate" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ReturnRoutingChannel" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}ReturnRoutingDispositionType" minOccurs="0"/>
 *         &lt;element name="LiquidationRoutingChannel" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}LiquidationDispositionType" minOccurs="0"/>
 *         &lt;element name="Changed" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}ChangeDataType" minOccurs="0"/>
 *         &lt;element name="ConditionValueResponse" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}QuestionAnswerListType" minOccurs="0"/>
 *         &lt;element name="Repair" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}RepairType" minOccurs="0"/>
 *         &lt;element name="FGI" type="{http://bestbuy.com/bbym/logistics/device/status/component/v1}FGIType" minOccurs="0"/>
 *         &lt;element name="SalesPrice" type="{http://www.w3.org/2001/XMLSchema}float" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReturnStatusType", propOrder = {
    "transactionID",
    "transactionType",
    "transactionDate",
    "returnRoutingChannel",
    "liquidationRoutingChannel",
    "changed",
    "conditionValueResponse",
    "repair",
    "fgi",
    "salesPrice"
})
public class ReturnStatusType {

    @XmlElement(name = "TransactionID", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String transactionID;
    @XmlElement(name = "TransactionType", required = true)
    protected TransactionStatusType transactionType;
    @XmlElement(name = "TransactionDate", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar transactionDate;
    @XmlElement(name = "ReturnRoutingChannel")
    protected ReturnRoutingDispositionType returnRoutingChannel;
    @XmlElement(name = "LiquidationRoutingChannel")
    protected LiquidationDispositionType liquidationRoutingChannel;
    @XmlElement(name = "Changed")
    protected ChangeDataType changed;
    @XmlElement(name = "ConditionValueResponse")
    protected QuestionAnswerListType conditionValueResponse;
    @XmlElement(name = "Repair")
    protected RepairType repair;
    @XmlElement(name = "FGI")
    protected FGIType fgi;
    @XmlElement(name = "SalesPrice")
    protected Float salesPrice;

    /**
     * Gets the value of the transactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransactionID() {
        return transactionID;
    }

    /**
     * Sets the value of the transactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransactionID(String value) {
        this.transactionID = value;
    }

    /**
     * Gets the value of the transactionType property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionStatusType }
     *     
     */
    public TransactionStatusType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionStatusType }
     *     
     */
    public void setTransactionType(TransactionStatusType value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the transactionDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the value of the transactionDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setTransactionDate(XMLGregorianCalendar value) {
        this.transactionDate = value;
    }

    /**
     * Gets the value of the returnRoutingChannel property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnRoutingDispositionType }
     *     
     */
    public ReturnRoutingDispositionType getReturnRoutingChannel() {
        return returnRoutingChannel;
    }

    /**
     * Sets the value of the returnRoutingChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnRoutingDispositionType }
     *     
     */
    public void setReturnRoutingChannel(ReturnRoutingDispositionType value) {
        this.returnRoutingChannel = value;
    }

    /**
     * Gets the value of the liquidationRoutingChannel property.
     * 
     * @return
     *     possible object is
     *     {@link LiquidationDispositionType }
     *     
     */
    public LiquidationDispositionType getLiquidationRoutingChannel() {
        return liquidationRoutingChannel;
    }

    /**
     * Sets the value of the liquidationRoutingChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link LiquidationDispositionType }
     *     
     */
    public void setLiquidationRoutingChannel(LiquidationDispositionType value) {
        this.liquidationRoutingChannel = value;
    }

    /**
     * Gets the value of the changed property.
     * 
     * @return
     *     possible object is
     *     {@link ChangeDataType }
     *     
     */
    public ChangeDataType getChanged() {
        return changed;
    }

    /**
     * Sets the value of the changed property.
     * 
     * @param value
     *     allowed object is
     *     {@link ChangeDataType }
     *     
     */
    public void setChanged(ChangeDataType value) {
        this.changed = value;
    }

    /**
     * Gets the value of the conditionValueResponse property.
     * 
     * @return
     *     possible object is
     *     {@link QuestionAnswerListType }
     *     
     */
    public QuestionAnswerListType getConditionValueResponse() {
        return conditionValueResponse;
    }

    /**
     * Sets the value of the conditionValueResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link QuestionAnswerListType }
     *     
     */
    public void setConditionValueResponse(QuestionAnswerListType value) {
        this.conditionValueResponse = value;
    }

    /**
     * Gets the value of the repair property.
     * 
     * @return
     *     possible object is
     *     {@link RepairType }
     *     
     */
    public RepairType getRepair() {
        return repair;
    }

    /**
     * Sets the value of the repair property.
     * 
     * @param value
     *     allowed object is
     *     {@link RepairType }
     *     
     */
    public void setRepair(RepairType value) {
        this.repair = value;
    }

    /**
     * Gets the value of the fgi property.
     * 
     * @return
     *     possible object is
     *     {@link FGIType }
     *     
     */
    public FGIType getFGI() {
        return fgi;
    }

    /**
     * Sets the value of the fgi property.
     * 
     * @param value
     *     allowed object is
     *     {@link FGIType }
     *     
     */
    public void setFGI(FGIType value) {
        this.fgi = value;
    }

    /**
     * Gets the value of the salesPrice property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getSalesPrice() {
        return salesPrice;
    }

    /**
     * Sets the value of the salesPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setSalesPrice(Float value) {
        this.salesPrice = value;
    }

}
