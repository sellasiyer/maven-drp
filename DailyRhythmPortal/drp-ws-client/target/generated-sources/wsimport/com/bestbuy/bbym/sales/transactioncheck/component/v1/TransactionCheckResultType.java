
package com.bestbuy.bbym.sales.transactioncheck.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TransactionCheckResultType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionCheckResultType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}TransactionCheckResultHeader" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/sales/transactioncheck/component/v1}ItemizedRuleResultList" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionCheckResultType", propOrder = {
    "transactionCheckResultHeader",
    "itemizedRuleResultList"
})
public class TransactionCheckResultType {

    @XmlElement(name = "TransactionCheckResultHeader")
    protected TransactionCheckResultHeaderType transactionCheckResultHeader;
    @XmlElement(name = "ItemizedRuleResultList")
    protected ItemizedRuleResultListType itemizedRuleResultList;

    /**
     * Gets the value of the transactionCheckResultHeader property.
     * 
     * @return
     *     possible object is
     *     {@link TransactionCheckResultHeaderType }
     *     
     */
    public TransactionCheckResultHeaderType getTransactionCheckResultHeader() {
        return transactionCheckResultHeader;
    }

    /**
     * Sets the value of the transactionCheckResultHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionCheckResultHeaderType }
     *     
     */
    public void setTransactionCheckResultHeader(TransactionCheckResultHeaderType value) {
        this.transactionCheckResultHeader = value;
    }

    /**
     * Gets the value of the itemizedRuleResultList property.
     * 
     * @return
     *     possible object is
     *     {@link ItemizedRuleResultListType }
     *     
     */
    public ItemizedRuleResultListType getItemizedRuleResultList() {
        return itemizedRuleResultList;
    }

    /**
     * Sets the value of the itemizedRuleResultList property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemizedRuleResultListType }
     *     
     */
    public void setItemizedRuleResultList(ItemizedRuleResultListType value) {
        this.itemizedRuleResultList = value;
    }

}
