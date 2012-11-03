
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TransactionContractType
 * 
 * <p>Java class for TransactionContractType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionContractType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ContractID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ContractType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}StartDate" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}EndDate" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ContractSku" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ContractDescription" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ProductSku" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ProductDescription" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}Comments" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}CustomerID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ECTransactionID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ECLineNumber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionContractType", propOrder = {
    "contractID",
    "contractType",
    "startDate",
    "endDate",
    "contractSku",
    "contractDescription",
    "productSku",
    "productDescription",
    "comments",
    "customerID",
    "ecTransactionID",
    "ecLineNumber"
})
public class TransactionContractType {

    @XmlElement(name = "ContractID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType contractID;
    @XmlElement(name = "ContractType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType contractType;
    @XmlElement(name = "StartDate", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "ContractSku", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType contractSku;
    @XmlElement(name = "ContractDescription", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType contractDescription;
    @XmlElement(name = "ProductSku", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType productSku;
    @XmlElement(name = "ProductDescription", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType productDescription;
    @XmlElement(name = "Comments", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType comments;
    @XmlElement(name = "CustomerID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType customerID;
    @XmlElement(name = "ECTransactionID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType ecTransactionID;
    @XmlElement(name = "ECLineNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType ecLineNumber;

    /**
     * ContractID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getContractID() {
        return contractID;
    }

    /**
     * Sets the value of the contractID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setContractID(TextType value) {
        this.contractID = value;
    }

    /**
     * ContractType
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getContractType() {
        return contractType;
    }

    /**
     * Sets the value of the contractType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setContractType(TextType value) {
        this.contractType = value;
    }

    /**
     * This element holds the starting date of contract.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    /**
     * Sets the value of the startDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    /**
     * This element holds the ending date of contract
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEndDate() {
        return endDate;
    }

    /**
     * Sets the value of the endDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEndDate(XMLGregorianCalendar value) {
        this.endDate = value;
    }

    /**
     * This element holds the contract sku.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getContractSku() {
        return contractSku;
    }

    /**
     * Sets the value of the contractSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setContractSku(TextType value) {
        this.contractSku = value;
    }

    /**
     * Contract SKU Description
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getContractDescription() {
        return contractDescription;
    }

    /**
     * Sets the value of the contractDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setContractDescription(TextType value) {
        this.contractDescription = value;
    }

    /**
     * This element holds the product sku
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getProductSku() {
        return productSku;
    }

    /**
     * Sets the value of the productSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setProductSku(TextType value) {
        this.productSku = value;
    }

    /**
     * This element holds the product sku description
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the value of the productDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setProductDescription(TextType value) {
        this.productDescription = value;
    }

    /**
     * Comments
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getComments() {
        return comments;
    }

    /**
     * Sets the value of the comments property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setComments(TextType value) {
        this.comments = value;
    }

    /**
     * This element holds the customer ID.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getCustomerID() {
        return customerID;
    }

    /**
     * Sets the value of the customerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setCustomerID(TextType value) {
        this.customerID = value;
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
     * This element holds ECLineNumber.
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getECLineNumber() {
        return ecLineNumber;
    }

    /**
     * Sets the value of the ecLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setECLineNumber(TextType value) {
        this.ecLineNumber = value;
    }

}
