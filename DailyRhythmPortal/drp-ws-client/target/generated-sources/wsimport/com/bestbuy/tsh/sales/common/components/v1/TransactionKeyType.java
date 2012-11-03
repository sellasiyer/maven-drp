
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TransactionKeyType
 * 
 * <p>Java class for TransactionKeyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TransactionKeyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionSourceSystem" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TransactionKeyType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SourceTransactionKey" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionKeyType", propOrder = {
    "transactionSourceSystem",
    "transactionKeyType",
    "sourceTransactionKey"
})
public class TransactionKeyType {

    @XmlElement(name = "TransactionSourceSystem", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionSourceSystem;
    @XmlElement(name = "TransactionKeyType", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType transactionKeyType;
    @XmlElement(name = "SourceTransactionKey", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType sourceTransactionKey;

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

    /**
     * This element holds SourceTransactionKey
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSourceTransactionKey() {
        return sourceTransactionKey;
    }

    /**
     * Sets the value of the sourceTransactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSourceTransactionKey(TextType value) {
        this.sourceTransactionKey = value;
    }

}
