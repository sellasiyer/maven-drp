
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.bestbuy.bbym.sales.transactioncheck.fields.v1.DeviceType;
import com.bestbuy.bbym.sales.transactioncheck.fields.v1.PlanType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;


/**
 * <p>Java class for LineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}ID" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}LineSequenceNumber" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}OriginalTransactionID" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}OriginalLineSequenceNumber" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}Device" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}Plan" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}ActivationDate" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}UpgradeTier" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}SubscriberNumber" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}DepositMode" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}ConfirmationNumber" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/fields/v1}PortFlag" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineItemType", propOrder = {
    "id",
    "lineSequenceNumber",
    "originalTransactionID",
    "originalLineSequenceNumber",
    "device",
    "plan",
    "activationDate",
    "upgradeTier",
    "subscriberNumber",
    "depositMode",
    "confirmationNumber",
    "portFlag",
    "userArea"
})
public class LineItemType {

    @XmlElement(name = "ID", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected IdentifierType id;
    @XmlElement(name = "LineSequenceNumber", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String lineSequenceNumber;
    @XmlElement(name = "OriginalTransactionID", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String originalTransactionID;
    @XmlElement(name = "OriginalLineSequenceNumber", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String originalLineSequenceNumber;
    @XmlElement(name = "Device", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected DeviceType device;
    @XmlElement(name = "Plan", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected PlanType plan;
    @XmlElement(name = "ActivationDate", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar activationDate;
    @XmlElement(name = "UpgradeTier", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String upgradeTier;
    @XmlElement(name = "SubscriberNumber", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String subscriberNumber;
    @XmlElement(name = "DepositMode", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String depositMode;
    @XmlElement(name = "ConfirmationNumber", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String confirmationNumber;
    @XmlElement(name = "PortFlag", namespace = "http://bestbuy.com/bbym/sales/transactioncheck/fields/v1")
    protected String portFlag;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;

    /**
     * Line ID or order ID
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getID() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setID(IdentifierType value) {
        this.id = value;
    }

    /**
     * Order Header Identifier from the source system
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLineSequenceNumber() {
        return lineSequenceNumber;
    }

    /**
     * Sets the value of the lineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLineSequenceNumber(String value) {
        this.lineSequenceNumber = value;
    }

    /**
     * Original SALE Order Header for RETURNS from the source system
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalTransactionID() {
        return originalTransactionID;
    }

    /**
     * Sets the value of the originalTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalTransactionID(String value) {
        this.originalTransactionID = value;
    }

    /**
     * Original SALE Order Line Identifier for RETURNS from the source system for every order header
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOriginalLineSequenceNumber() {
        return originalLineSequenceNumber;
    }

    /**
     * Sets the value of the originalLineSequenceNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOriginalLineSequenceNumber(String value) {
        this.originalLineSequenceNumber = value;
    }

    /**
     * Complex type used to capture the device(Hardware) details.
     * 
     * @return
     *     possible object is
     *     {@link DeviceType }
     *     
     */
    public DeviceType getDevice() {
        return device;
    }

    /**
     * Sets the value of the device property.
     * 
     * @param value
     *     allowed object is
     *     {@link DeviceType }
     *     
     */
    public void setDevice(DeviceType value) {
        this.device = value;
    }

    /**
     * Indicate if the Activation is NOCONTRACT or POSTPAID
     * 
     * @return
     *     possible object is
     *     {@link PlanType }
     *     
     */
    public PlanType getPlan() {
        return plan;
    }

    /**
     * Sets the value of the plan property.
     * 
     * @param value
     *     allowed object is
     *     {@link PlanType }
     *     
     */
    public void setPlan(PlanType value) {
        this.plan = value;
    }

    /**
     * Date of the activation by Carrier. This maps to the Contract Start Date which may be in the future
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getActivationDate() {
        return activationDate;
    }

    /**
     * Sets the value of the activationDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setActivationDate(XMLGregorianCalendar value) {
        this.activationDate = value;
    }

    /**
     * Upgrade tier assigned to customer by carrier when an upgrade request is carried out.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpgradeTier() {
        return upgradeTier;
    }

    /**
     * Sets the value of the upgradeTier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpgradeTier(String value) {
        this.upgradeTier = value;
    }

    /**
     * Phone Number of the Line as Activated
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    /**
     * Sets the value of the subscriberNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubscriberNumber(String value) {
        this.subscriberNumber = value;
    }

    /**
     * Store the deposit payment mode. Payment done through Cash or credit card.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDepositMode() {
        return depositMode;
    }

    /**
     * Sets the value of the depositMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDepositMode(String value) {
        this.depositMode = value;
    }

    /**
     * Confirmation number when the payment done through credit card
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConfirmationNumber() {
        return confirmationNumber;
    }

    /**
     * Sets the value of the confirmationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConfirmationNumber(String value) {
        this.confirmationNumber = value;
    }

    /**
     * To indicate the port in activation
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPortFlag() {
        return portFlag;
    }

    /**
     * Sets the value of the portFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPortFlag(String value) {
        this.portFlag = value;
    }

    /**
     * Allows the Tag Super Highway schema data model to extend the specification in order to provide additional information that is not captured in Canonical Schema.
     * 
     * @return
     *     possible object is
     *     {@link UserAreaType }
     *     
     */
    public UserAreaType getUserArea() {
        return userArea;
    }

    /**
     * Sets the value of the userArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAreaType }
     *     
     */
    public void setUserArea(UserAreaType value) {
        this.userArea = value;
    }

}
