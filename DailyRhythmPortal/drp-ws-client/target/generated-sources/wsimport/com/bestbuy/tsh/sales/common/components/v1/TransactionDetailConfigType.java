
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TransactionDetailConfigType
 * 
 * <p>Java class for TransactionDetailConfigType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionDetailConfigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrievePayments" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveContracts" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveLineItemDetails" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveAllChildTransactions" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveAllParentTransactions" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveAllTransactionKeys" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveRelatedTransactionLine" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveLineTaxInfo" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveMasterItemDetails" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}MaskDetails" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}InquiryLevel"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionDetailConfigType", propOrder = {
    "retrievePayments",
    "retrieveContracts",
    "retrieveLineItemDetails",
    "retrieveAllChildTransactions",
    "retrieveAllParentTransactions",
    "retrieveAllTransactionKeys",
    "retrieveRelatedTransactionLine",
    "retrieveLineTaxInfo",
    "retrieveMasterItemDetails",
    "maskDetails",
    "inquiryLevel"
})
public class TransactionDetailConfigType {

    @XmlElement(name = "RetrievePayments", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrievePayments;
    @XmlElement(name = "RetrieveContracts", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveContracts;
    @XmlElement(name = "RetrieveLineItemDetails", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveLineItemDetails;
    @XmlElement(name = "RetrieveAllChildTransactions", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveAllChildTransactions;
    @XmlElement(name = "RetrieveAllParentTransactions", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveAllParentTransactions;
    @XmlElement(name = "RetrieveAllTransactionKeys", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveAllTransactionKeys;
    @XmlElement(name = "RetrieveRelatedTransactionLine", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveRelatedTransactionLine;
    @XmlElement(name = "RetrieveLineTaxInfo", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveLineTaxInfo;
    @XmlElement(name = "RetrieveMasterItemDetails", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveMasterItemDetails;
    @XmlElement(name = "MaskDetails", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean maskDetails;
    @XmlElement(name = "InquiryLevel", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", required = true)
    protected TextType inquiryLevel;

    /**
     * This element holds RetrievePayments
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrievePayments() {
        return retrievePayments;
    }

    /**
     * Sets the value of the retrievePayments property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrievePayments(Boolean value) {
        this.retrievePayments = value;
    }

    /**
     * This element holds RetrieveContracts
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveContracts() {
        return retrieveContracts;
    }

    /**
     * Sets the value of the retrieveContracts property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveContracts(Boolean value) {
        this.retrieveContracts = value;
    }

    /**
     * This element holds RetrieveLineItemDetails
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveLineItemDetails() {
        return retrieveLineItemDetails;
    }

    /**
     * Sets the value of the retrieveLineItemDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveLineItemDetails(Boolean value) {
        this.retrieveLineItemDetails = value;
    }

    /**
     * Gets the value of the retrieveAllChildTransactions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveAllChildTransactions() {
        return retrieveAllChildTransactions;
    }

    /**
     * Sets the value of the retrieveAllChildTransactions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveAllChildTransactions(Boolean value) {
        this.retrieveAllChildTransactions = value;
    }

    /**
     * This element holds RetrieveAllParentTransactions
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveAllParentTransactions() {
        return retrieveAllParentTransactions;
    }

    /**
     * Sets the value of the retrieveAllParentTransactions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveAllParentTransactions(Boolean value) {
        this.retrieveAllParentTransactions = value;
    }

    /**
     * This element holds RetrieveAllTransactionKeys
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveAllTransactionKeys() {
        return retrieveAllTransactionKeys;
    }

    /**
     * Sets the value of the retrieveAllTransactionKeys property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveAllTransactionKeys(Boolean value) {
        this.retrieveAllTransactionKeys = value;
    }

    /**
     * This element holds  RetrieveRelatedTransactionLine
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveRelatedTransactionLine() {
        return retrieveRelatedTransactionLine;
    }

    /**
     * Sets the value of the retrieveRelatedTransactionLine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveRelatedTransactionLine(Boolean value) {
        this.retrieveRelatedTransactionLine = value;
    }

    /**
     * This element holds RetrieveLineTaxInfo
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveLineTaxInfo() {
        return retrieveLineTaxInfo;
    }

    /**
     * Sets the value of the retrieveLineTaxInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveLineTaxInfo(Boolean value) {
        this.retrieveLineTaxInfo = value;
    }

    /**
     * This element holds RetrieveMasterItemDetails
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveMasterItemDetails() {
        return retrieveMasterItemDetails;
    }

    /**
     * Sets the value of the retrieveMasterItemDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveMasterItemDetails(Boolean value) {
        this.retrieveMasterItemDetails = value;
    }

    /**
     * Gets the value of the maskDetails property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMaskDetails() {
        return maskDetails;
    }

    /**
     * Sets the value of the maskDetails property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMaskDetails(Boolean value) {
        this.maskDetails = value;
    }

    /**
     * Gets the value of the inquiryLevel property.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getInquiryLevel() {
        return inquiryLevel;
    }

    /**
     * Sets the value of the inquiryLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setInquiryLevel(TextType value) {
        this.inquiryLevel = value;
    }

}
