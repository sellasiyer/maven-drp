
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * ECTransactionIDRangeType
 * 
 * <p>Java class for ECTransactionIDRangeType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ECTransactionIDRangeType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}StartECTransID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}EndECTransID" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ECTransactionIDRangeType", propOrder = {
    "startECTransID",
    "endECTransID"
})
public class ECTransactionIDRangeType {

    @XmlElement(name = "StartECTransID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType startECTransID;
    @XmlElement(name = "EndECTransID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType endECTransID;

    /**
     * This element holds StartECTransID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getStartECTransID() {
        return startECTransID;
    }

    /**
     * Sets the value of the startECTransID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setStartECTransID(TextType value) {
        this.startECTransID = value;
    }

    /**
     * This element holds EndECTransID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getEndECTransID() {
        return endECTransID;
    }

    /**
     * Sets the value of the endECTransID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setEndECTransID(TextType value) {
        this.endECTransID = value;
    }

}
