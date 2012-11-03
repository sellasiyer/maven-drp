
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanSearchRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanSearchRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProtectionPlanID" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanIDType" minOccurs="0"/>
 *         &lt;element name="CustomerID" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}CustomerIDType" minOccurs="0"/>
 *         &lt;element name="TransactionKey" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}TransactionKeyType" minOccurs="0"/>
 *         &lt;element name="CoveredSerialNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="CoveredSKU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TypeFilter" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanSearchRequestType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", propOrder = {
    "protectionPlanID",
    "customerID",
    "transactionKey",
    "coveredSerialNumber",
    "coveredSKU",
    "typeFilter"
})
public class ProtectionPlanSearchRequestType {

    @XmlElement(name = "ProtectionPlanID")
    protected ProtectionPlanIDType protectionPlanID;
    @XmlElement(name = "CustomerID")
    protected CustomerIDType customerID;
    @XmlElement(name = "TransactionKey")
    protected TransactionKeyType transactionKey;
    @XmlElement(name = "CoveredSerialNumber")
    protected String coveredSerialNumber;
    @XmlElement(name = "CoveredSKU")
    protected String coveredSKU;
    @XmlElement(name = "TypeFilter")
    protected String typeFilter;

    /**
     * Gets the value of the protectionPlanID property.
     * 
     * @return
     *     possible object is
     *     {@link ProtectionPlanIDType }
     *     
     */
    public ProtectionPlanIDType getProtectionPlanID() {
        return protectionPlanID;
    }

    /**
     * Sets the value of the protectionPlanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtectionPlanIDType }
     *     
     */
    public void setProtectionPlanID(ProtectionPlanIDType value) {
        this.protectionPlanID = value;
    }

    /**
     * Gets the value of the customerID property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerIDType }
     *     
     */
    public CustomerIDType getCustomerID() {
        return customerID;
    }

    /**
     * Sets the value of the customerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerIDType }
     *     
     */
    public void setCustomerID(CustomerIDType value) {
        this.customerID = value;
    }

    /**
     * Gets the value of the transactionKey property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionKeyType }
     *     
     */
    public TransactionKeyType getTransactionKey() {
        return transactionKey;
    }

    /**
     * Sets the value of the transactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionKeyType }
     *     
     */
    public void setTransactionKey(TransactionKeyType value) {
        this.transactionKey = value;
    }

    /**
     * Gets the value of the coveredSerialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoveredSerialNumber() {
        return coveredSerialNumber;
    }

    /**
     * Sets the value of the coveredSerialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoveredSerialNumber(String value) {
        this.coveredSerialNumber = value;
    }

    /**
     * Gets the value of the coveredSKU property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCoveredSKU() {
        return coveredSKU;
    }

    /**
     * Sets the value of the coveredSKU property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCoveredSKU(String value) {
        this.coveredSKU = value;
    }

    /**
     * Gets the value of the typeFilter property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeFilter() {
        return typeFilter;
    }

    /**
     * Sets the value of the typeFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeFilter(String value) {
        this.typeFilter = value;
    }

}
