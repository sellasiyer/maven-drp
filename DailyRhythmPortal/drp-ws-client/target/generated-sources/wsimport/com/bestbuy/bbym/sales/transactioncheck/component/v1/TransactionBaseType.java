
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionBaseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionBaseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Header" type="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}HeaderType" minOccurs="0"/>
 *         &lt;element name="LineItemList" type="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}LineItemListType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionBaseType", propOrder = {
    "header",
    "lineItemList"
})
public class TransactionBaseType {

    @XmlElement(name = "Header")
    protected HeaderType header;
    @XmlElement(name = "LineItemList")
    protected LineItemListType lineItemList;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link HeaderType }
     *     
     */
    public HeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType }
     *     
     */
    public void setHeader(HeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the lineItemList property.
     * 
     * @return
     *     possible object is
     *     {@link LineItemListType }
     *     
     */
    public LineItemListType getLineItemList() {
        return lineItemList;
    }

    /**
     * Sets the value of the lineItemList property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineItemListType }
     *     
     */
    public void setLineItemList(LineItemListType value) {
        this.lineItemList = value;
    }

}
