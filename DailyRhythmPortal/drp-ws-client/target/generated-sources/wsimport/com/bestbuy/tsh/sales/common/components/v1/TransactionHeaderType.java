
package com.bestbuy.tsh.sales.common.components.v1;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TransactionHeaderType
 * 
 * <p>Java class for TransactionHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionDate" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionType" minOccurs="0"/>
 *         &lt;element name="TransactionKey" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TransactionKeyType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ECTransactionID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionStatus" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}EmployeeID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}PurchasedEmployeeID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ECParentTransactionID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}BBYCreateTimeStamp" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}BBYUpdateTimeStamp" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ECAuditTimeStamp" minOccurs="0"/>
 *         &lt;element name="CustomerInformation" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}CustomerInfoType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionVersion" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionLocation" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}PCardIndicator" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionHeaderType", propOrder = {
    "transactionDate",
    "transactionType",
    "transactionKey",
    "ecTransactionID",
    "transactionStatus",
    "employeeID",
    "purchasedEmployeeID",
    "ecParentTransactionID",
    "bbyCreateTimeStamp",
    "bbyUpdateTimeStamp",
    "ecAuditTimeStamp",
    "customerInformation",
    "transactionVersion",
    "transactionLocation",
    "pCardIndicator"
})
public class TransactionHeaderType {

    @XmlElement(name = "TransactionDate", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar transactionDate;
    @XmlElement(name = "TransactionType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionType;
    @XmlElement(name = "TransactionKey")
    protected List<TransactionKeyType> transactionKey;
    @XmlElement(name = "ECTransactionID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType ecTransactionID;
    @XmlElement(name = "TransactionStatus", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionStatus;
    @XmlElement(name = "EmployeeID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType employeeID;
    @XmlElement(name = "PurchasedEmployeeID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType purchasedEmployeeID;
    @XmlElement(name = "ECParentTransactionID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType ecParentTransactionID;
    @XmlElement(name = "BBYCreateTimeStamp", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar bbyCreateTimeStamp;
    @XmlElement(name = "BBYUpdateTimeStamp", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar bbyUpdateTimeStamp;
    @XmlElement(name = "ECAuditTimeStamp", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar ecAuditTimeStamp;
    @XmlElement(name = "CustomerInformation")
    protected CustomerInfoType customerInformation;
    @XmlElement(name = "TransactionVersion", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionVersion;
    @XmlElement(name = "TransactionLocation", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionLocation;
    @XmlElement(name = "PCardIndicator", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean pCardIndicator;

    /**
     * This element holds the transaction date.
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
     * TransactionType
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionType() {
        return transactionType;
    }

    /**
     * Sets the value of the transactionType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionType(TextType value) {
        this.transactionType = value;
    }

    /**
     * Gets the value of the transactionKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the transactionKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTransactionKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TransactionKeyType }
     * 
     * 
     */
    public List<TransactionKeyType> getTransactionKey() {
        if (transactionKey == null) {
            transactionKey = new ArrayList<TransactionKeyType>();
        }
        return this.transactionKey;
    }

    /**
     * This element holds EC transaction ID.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getECTransactionID() {
        return ecTransactionID;
    }

    /**
     * Sets the value of the ecTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setECTransactionID(TextType value) {
        this.ecTransactionID = value;
    }

    /**
     * TransactionStatus
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionStatus() {
        return transactionStatus;
    }

    /**
     * Sets the value of the transactionStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionStatus(TextType value) {
        this.transactionStatus = value;
    }

    /**
     * EmployeeID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getEmployeeID() {
        return employeeID;
    }

    /**
     * Sets the value of the employeeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setEmployeeID(TextType value) {
        this.employeeID = value;
    }

    /**
     * PurchasedEmployeeID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getPurchasedEmployeeID() {
        return purchasedEmployeeID;
    }

    /**
     * Sets the value of the purchasedEmployeeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setPurchasedEmployeeID(TextType value) {
        this.purchasedEmployeeID = value;
    }

    /**
     * ECParentTransactionID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getECParentTransactionID() {
        return ecParentTransactionID;
    }

    /**
     * Sets the value of the ecParentTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setECParentTransactionID(TextType value) {
        this.ecParentTransactionID = value;
    }

    /**
     * BBYCreateTimeStamp
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBBYCreateTimeStamp() {
        return bbyCreateTimeStamp;
    }

    /**
     * Sets the value of the bbyCreateTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBBYCreateTimeStamp(XMLGregorianCalendar value) {
        this.bbyCreateTimeStamp = value;
    }

    /**
     * BBYUpdateTimeStamp
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBBYUpdateTimeStamp() {
        return bbyUpdateTimeStamp;
    }

    /**
     * Sets the value of the bbyUpdateTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setBBYUpdateTimeStamp(XMLGregorianCalendar value) {
        this.bbyUpdateTimeStamp = value;
    }

    /**
     * ECAuditTimeStamp
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getECAuditTimeStamp() {
        return ecAuditTimeStamp;
    }

    /**
     * Sets the value of the ecAuditTimeStamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setECAuditTimeStamp(XMLGregorianCalendar value) {
        this.ecAuditTimeStamp = value;
    }

    /**
     * Gets the value of the customerInformation property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerInfoType }
     *     
     */
    public CustomerInfoType getCustomerInformation() {
        return customerInformation;
    }

    /**
     * Sets the value of the customerInformation property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerInfoType }
     *     
     */
    public void setCustomerInformation(CustomerInfoType value) {
        this.customerInformation = value;
    }

    /**
     * TransactionVersion
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionVersion() {
        return transactionVersion;
    }

    /**
     * Sets the value of the transactionVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionVersion(TextType value) {
        this.transactionVersion = value;
    }

    /**
     * TransactionLocation
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionLocation() {
        return transactionLocation;
    }

    /**
     * Sets the value of the transactionLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionLocation(TextType value) {
        this.transactionLocation = value;
    }

    /**
     * PCardIndicator
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPCardIndicator() {
        return pCardIndicator;
    }

    /**
     * Sets the value of the pCardIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPCardIndicator(Boolean value) {
        this.pCardIndicator = value;
    }

}
