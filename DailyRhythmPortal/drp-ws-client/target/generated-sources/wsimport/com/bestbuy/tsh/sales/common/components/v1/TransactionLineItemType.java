
package com.bestbuy.tsh.sales.common.components.v1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TransactionLineItemType
 * 
 * <p>Java class for TransactionLineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionLineItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionLineNumber" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionLineType" minOccurs="0"/>
 *         &lt;element name="Item" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}ItemType" minOccurs="0"/>
 *         &lt;element name="ProductCategory" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}ProductCategoryType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}UnitQuantity" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}EligibleFlag" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}AverageCost" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}RetailPrice" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SalePrice" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SerialNumber" minOccurs="0"/>
 *         &lt;element name="LineItemsDetails" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}LineItemDetailsType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="LineItemTransactionSource" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}LineItemSourceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}ReshippedItem" minOccurs="0"/>
 *         &lt;element name="RelatedLineItem" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}RelatedLineItemType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TaxAttributes" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TaxAttributesType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}PlannedDeliveryDate" minOccurs="0"/>
 *         &lt;element name="LineItemOrderSource" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}LineItemSourceType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}MasterItemID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}MarketPlaceIndicator" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionLineItemType", propOrder = {
    "transactionLineNumber",
    "transactionLineType",
    "item",
    "productCategory",
    "unitQuantity",
    "eligibleFlag",
    "averageCost",
    "retailPrice",
    "salePrice",
    "serialNumber",
    "lineItemsDetails",
    "lineItemTransactionSource",
    "reshippedItem",
    "relatedLineItem",
    "taxAttributes",
    "plannedDeliveryDate",
    "lineItemOrderSource",
    "masterItemID",
    "marketPlaceIndicator"
})
public class TransactionLineItemType {

    @XmlElement(name = "TransactionLineNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionLineNumber;
    @XmlElement(name = "TransactionLineType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionLineType;
    @XmlElement(name = "Item")
    protected ItemType item;
    @XmlElement(name = "ProductCategory")
    protected ProductCategoryType productCategory;
    @XmlElement(name = "UnitQuantity", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected BigInteger unitQuantity;
    @XmlElement(name = "EligibleFlag", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType eligibleFlag;
    @XmlElement(name = "AverageCost", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Double averageCost;
    @XmlElement(name = "RetailPrice", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Double retailPrice;
    @XmlElement(name = "SalePrice", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Double salePrice;
    @XmlElement(name = "SerialNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType serialNumber;
    @XmlElement(name = "LineItemsDetails")
    protected List<LineItemDetailsType> lineItemsDetails;
    @XmlElement(name = "LineItemTransactionSource")
    protected List<LineItemSourceType> lineItemTransactionSource;
    @XmlElement(name = "ReshippedItem", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean reshippedItem;
    @XmlElement(name = "RelatedLineItem")
    protected List<RelatedLineItemType> relatedLineItem;
    @XmlElement(name = "TaxAttributes")
    protected TaxAttributesType taxAttributes;
    @XmlElement(name = "PlannedDeliveryDate", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected XMLGregorianCalendar plannedDeliveryDate;
    @XmlElement(name = "LineItemOrderSource")
    protected List<LineItemSourceType> lineItemOrderSource;
    @XmlElement(name = "MasterItemID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType masterItemID;
    @XmlElement(name = "MarketPlaceIndicator", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected Boolean marketPlaceIndicator;

    /**
     * TransactionLineNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionLineNumber() {
        return transactionLineNumber;
    }

    /**
     * Sets the value of the transactionLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionLineNumber(TextType value) {
        this.transactionLineNumber = value;
    }

    /**
     * TransactionLineType
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTransactionLineType() {
        return transactionLineType;
    }

    /**
     * Sets the value of the transactionLineType property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTransactionLineType(TextType value) {
        this.transactionLineType = value;
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
     * Gets the value of the productCategory property.
     * 
     * @return
     *     possible object is
     *     {@link ProductCategoryType }
     *     
     */
    public ProductCategoryType getProductCategory() {
        return productCategory;
    }

    /**
     * Sets the value of the productCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProductCategoryType }
     *     
     */
    public void setProductCategory(ProductCategoryType value) {
        this.productCategory = value;
    }

    /**
     * UnitQuantity
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getUnitQuantity() {
        return unitQuantity;
    }

    /**
     * Sets the value of the unitQuantity property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setUnitQuantity(BigInteger value) {
        this.unitQuantity = value;
    }

    /**
     * EligibleFlag
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getEligibleFlag() {
        return eligibleFlag;
    }

    /**
     * Sets the value of the eligibleFlag property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setEligibleFlag(TextType value) {
        this.eligibleFlag = value;
    }

    /**
     * AverageCost
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getAverageCost() {
        return averageCost;
    }

    /**
     * Sets the value of the averageCost property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setAverageCost(Double value) {
        this.averageCost = value;
    }

    /**
     * RetailPrice
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getRetailPrice() {
        return retailPrice;
    }

    /**
     * Sets the value of the retailPrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setRetailPrice(Double value) {
        this.retailPrice = value;
    }

    /**
     * SalePrice
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSalePrice() {
        return salePrice;
    }

    /**
     * Sets the value of the salePrice property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSalePrice(Double value) {
        this.salePrice = value;
    }

    /**
     * SerialNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSerialNumber(TextType value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the lineItemsDetails property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemsDetails property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemsDetails().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineItemDetailsType }
     * 
     * 
     */
    public List<LineItemDetailsType> getLineItemsDetails() {
        if (lineItemsDetails == null) {
            lineItemsDetails = new ArrayList<LineItemDetailsType>();
        }
        return this.lineItemsDetails;
    }

    /**
     * Gets the value of the lineItemTransactionSource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemTransactionSource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemTransactionSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineItemSourceType }
     * 
     * 
     */
    public List<LineItemSourceType> getLineItemTransactionSource() {
        if (lineItemTransactionSource == null) {
            lineItemTransactionSource = new ArrayList<LineItemSourceType>();
        }
        return this.lineItemTransactionSource;
    }

    /**
     * ReshippedItem
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isReshippedItem() {
        return reshippedItem;
    }

    /**
     * Sets the value of the reshippedItem property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setReshippedItem(Boolean value) {
        this.reshippedItem = value;
    }

    /**
     * Gets the value of the relatedLineItem property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the relatedLineItem property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRelatedLineItem().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RelatedLineItemType }
     * 
     * 
     */
    public List<RelatedLineItemType> getRelatedLineItem() {
        if (relatedLineItem == null) {
            relatedLineItem = new ArrayList<RelatedLineItemType>();
        }
        return this.relatedLineItem;
    }

    /**
     * Gets the value of the taxAttributes property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAttributesType }
     *     
     */
    public TaxAttributesType getTaxAttributes() {
        return taxAttributes;
    }

    /**
     * Sets the value of the taxAttributes property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAttributesType }
     *     
     */
    public void setTaxAttributes(TaxAttributesType value) {
        this.taxAttributes = value;
    }

    /**
     * PlannedDeliveryDate
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getPlannedDeliveryDate() {
        return plannedDeliveryDate;
    }

    /**
     * Sets the value of the plannedDeliveryDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setPlannedDeliveryDate(XMLGregorianCalendar value) {
        this.plannedDeliveryDate = value;
    }

    /**
     * Gets the value of the lineItemOrderSource property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineItemOrderSource property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLineItemOrderSource().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LineItemSourceType }
     * 
     * 
     */
    public List<LineItemSourceType> getLineItemOrderSource() {
        if (lineItemOrderSource == null) {
            lineItemOrderSource = new ArrayList<LineItemSourceType>();
        }
        return this.lineItemOrderSource;
    }

    /**
     * MasterItemId
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getMasterItemID() {
        return masterItemID;
    }

    /**
     * Sets the value of the masterItemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setMasterItemID(TextType value) {
        this.masterItemID = value;
    }

    /**
     * Gets the value of the marketPlaceIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMarketPlaceIndicator() {
        return marketPlaceIndicator;
    }

    /**
     * Sets the value of the marketPlaceIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setMarketPlaceIndicator(Boolean value) {
        this.marketPlaceIndicator = value;
    }

}
