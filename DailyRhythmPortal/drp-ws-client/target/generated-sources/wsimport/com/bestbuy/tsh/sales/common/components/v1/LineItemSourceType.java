
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * LineItemSourceType
 * 
 * <p>Java class for LineItemSourceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LineItemSourceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SourceSystemID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SourceSystemTransactionKey" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SourceSystemTransactionLineNumber" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LineItemSourceType", propOrder = {
    "sourceSystemID",
    "sourceSystemTransactionKey",
    "sourceSystemTransactionLineNumber"
})
public class LineItemSourceType {

    @XmlElement(name = "SourceSystemID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType sourceSystemID;
    @XmlElement(name = "SourceSystemTransactionKey", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType sourceSystemTransactionKey;
    @XmlElement(name = "SourceSystemTransactionLineNumber", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType sourceSystemTransactionLineNumber;

    /**
     * This element holds SourceSystemID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSourceSystemID() {
        return sourceSystemID;
    }

    /**
     * Sets the value of the sourceSystemID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSourceSystemID(TextType value) {
        this.sourceSystemID = value;
    }

    /**
     * This element holds SourceSystemTransactionKey
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSourceSystemTransactionKey() {
        return sourceSystemTransactionKey;
    }

    /**
     * Sets the value of the sourceSystemTransactionKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSourceSystemTransactionKey(TextType value) {
        this.sourceSystemTransactionKey = value;
    }

    /**
     * This element holds SourceSystemTransactionLineNumber
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSourceSystemTransactionLineNumber() {
        return sourceSystemTransactionLineNumber;
    }

    /**
     * Sets the value of the sourceSystemTransactionLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSourceSystemTransactionLineNumber(TextType value) {
        this.sourceSystemTransactionLineNumber = value;
    }

}
