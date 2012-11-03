
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * Transaction
 * 
 * <p>Java class for SearchConfigType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchConfigType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}MaxRows" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SearchTimeLimitMS" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SearchTypeToPerform" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}DiscardResult" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}PerformDrillDown" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SearchOnlyAuditedtransaction" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveCustomerInfo" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveAllTransactionKeys" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetrieveMasterItemDetails" minOccurs="0"/>
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
@XmlType(name = "SearchConfigType", propOrder = {
    "maxRows",
    "searchTimeLimitMS",
    "searchTypeToPerform",
    "discardResult",
    "performDrillDown",
    "searchOnlyAuditedtransaction",
    "retrieveCustomerInfo",
    "retrieveAllTransactionKeys",
    "retrieveMasterItemDetails",
    "inquiryLevel"
})
public class SearchConfigType {

    @XmlElement(name = "MaxRows", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger maxRows;
    @XmlElement(name = "SearchTimeLimitMS", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger searchTimeLimitMS;
    @XmlElement(name = "SearchTypeToPerform", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType searchTypeToPerform;
    @XmlElement(name = "DiscardResult", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean discardResult;
    @XmlElement(name = "PerformDrillDown", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean performDrillDown;
    @XmlElement(name = "SearchOnlyAuditedtransaction", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean searchOnlyAuditedtransaction;
    @XmlElement(name = "RetrieveCustomerInfo", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveCustomerInfo;
    @XmlElement(name = "RetrieveAllTransactionKeys", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveAllTransactionKeys;
    @XmlElement(name = "RetrieveMasterItemDetails", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean retrieveMasterItemDetails;
    @XmlElement(name = "InquiryLevel", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1", required = true)
    protected TextType inquiryLevel;

    /**
     * This element holds MaxRows
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxRows() {
        return maxRows;
    }

    /**
     * Sets the value of the maxRows property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxRows(BigInteger value) {
        this.maxRows = value;
    }

    /**
     * This element holds SearchTimeLimitMS
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getSearchTimeLimitMS() {
        return searchTimeLimitMS;
    }

    /**
     * Sets the value of the searchTimeLimitMS property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setSearchTimeLimitMS(BigInteger value) {
        this.searchTimeLimitMS = value;
    }

    /**
     * This element holds SearchTypeToPerform
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSearchTypeToPerform() {
        return searchTypeToPerform;
    }

    /**
     * Sets the value of the searchTypeToPerform property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSearchTypeToPerform(TextType value) {
        this.searchTypeToPerform = value;
    }

    /**
     * This element holds DiscardResult
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isDiscardResult() {
        return discardResult;
    }

    /**
     * Sets the value of the discardResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setDiscardResult(Boolean value) {
        this.discardResult = value;
    }

    /**
     * This element holds PerformDrillDown
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isPerformDrillDown() {
        return performDrillDown;
    }

    /**
     * Sets the value of the performDrillDown property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setPerformDrillDown(Boolean value) {
        this.performDrillDown = value;
    }

    /**
     * This element holds SearchOnlyAuditedtransaction
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSearchOnlyAuditedtransaction() {
        return searchOnlyAuditedtransaction;
    }

    /**
     * Sets the value of the searchOnlyAuditedtransaction property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSearchOnlyAuditedtransaction(Boolean value) {
        this.searchOnlyAuditedtransaction = value;
    }

    /**
     * This element holds RetrieveCustomerInfo
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRetrieveCustomerInfo() {
        return retrieveCustomerInfo;
    }

    /**
     * Sets the value of the retrieveCustomerInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRetrieveCustomerInfo(Boolean value) {
        this.retrieveCustomerInfo = value;
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
