
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
 * SearchParameterType
 * 
 * <p>Java class for SearchParameterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchParameterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Customer" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}CustomerType" minOccurs="0"/>
 *         &lt;element name="Account" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}AccountType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ECTransactionIDRange" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}ECTransactionIDRangeType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ECTransactionID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}QuickOrderNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}StartDate" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}EndDate" minOccurs="0"/>
 *         &lt;element name="Item" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}ItemType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ExcludeRelatedSku" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionKey" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionSourceSystem" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionKeyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchParameterType", propOrder = {
    "customer",
    "account",
    "ecTransactionIDRange",
    "ecTransactionID",
    "quickOrderNumber",
    "startDate",
    "endDate",
    "item",
    "excludeRelatedSku",
    "transactionKey",
    "transactionSourceSystem",
    "transactionKeyType"
})
public class SearchParameterType {

    @XmlElement(name = "Customer")
    protected CustomerType customer;
    @XmlElement(name = "Account")
    protected List<AccountType> account;
    @XmlElement(name = "ECTransactionIDRange")
    protected ECTransactionIDRangeType ecTransactionIDRange;
    @XmlElement(name = "ECTransactionID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType ecTransactionID;
    @XmlElement(name = "QuickOrderNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType quickOrderNumber;
    @XmlElement(name = "StartDate", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "EndDate", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar endDate;
    @XmlElement(name = "Item")
    protected ItemType item;
    @XmlElement(name = "ExcludeRelatedSku", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType excludeRelatedSku;
    @XmlElement(name = "TransactionKey", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionKey;
    @XmlElement(name = "TransactionSourceSystem", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionSourceSystem;
    @XmlElement(name = "TransactionKeyType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionKeyType;

    /**
     * Gets the value of the customer property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerType }
     *     
     */
    public CustomerType getCustomer() {
        return customer;
    }

    /**
     * Sets the value of the customer property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerType }
     *     
     */
    public void setCustomer(CustomerType value) {
        this.customer = value;
    }

    /**
     * Gets the value of the account property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the account property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccountType }
     * 
     * 
     */
    public List<AccountType> getAccount() {
        if (account == null) {
            account = new ArrayList<AccountType>();
        }
        return this.account;
    }

    /**
     * Gets the value of the ecTransactionIDRange property.
     * 
     * @return
     *     possible object is
     *     {@link ECTransactionIDRangeType }
     *     
     */
    public ECTransactionIDRangeType getECTransactionIDRange() {
        return ecTransactionIDRange;
    }

    /**
     * Sets the value of the ecTransactionIDRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link ECTransactionIDRangeType }
     *     
     */
    public void setECTransactionIDRange(ECTransactionIDRangeType value) {
        this.ecTransactionIDRange = value;
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
     * This element holds QuickOrderNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getQuickOrderNumber() {
        return quickOrderNumber;
    }

    /**
     * Sets the value of the quickOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setQuickOrderNumber(TextType value) {
        this.quickOrderNumber = value;
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
     * Gets the value of the item property.
     * 
     * @return
     *     possible object is
     *     {@link ItemType }
     *     
     */
    public ItemType getItem() {
        return item;
    }

    /**
     * Sets the value of the item property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemType }
     *     
     */
    public void setItem(ItemType value) {
        this.item = value;
    }

    /**
     * This element holds ExcludeRelatedSku
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getExcludeRelatedSku() {
        return excludeRelatedSku;
    }

    /**
     * Sets the value of the excludeRelatedSku property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setExcludeRelatedSku(TextType value) {
        this.excludeRelatedSku = value;
    }

    /**
     * Transaction Key
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionKey() {
        return transactionKey;
    }

    /**
     * Sets the value of the transactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionKey(TextType value) {
        this.transactionKey = value;
    }

    /**
     * This element holds TransactionSourceSystem
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionSourceSystem() {
        return transactionSourceSystem;
    }

    /**
     * Sets the value of the transactionSourceSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionSourceSystem(TextType value) {
        this.transactionSourceSystem = value;
    }

    /**
     * This element holds TransactionKeyType
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionKeyType() {
        return transactionKeyType;
    }

    /**
     * Sets the value of the transactionKeyType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionKeyType(TextType value) {
        this.transactionKeyType = value;
    }

}
