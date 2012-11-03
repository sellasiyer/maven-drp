
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * TaxAreaType
 * 
 * <p>Java class for TaxAreaType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxAreaType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TaxAreaID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}TaxAreaState" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxAreaType", propOrder = {
    "taxAreaID",
    "taxAreaState"
})
public class TaxAreaType {

    @XmlElement(name = "TaxAreaID", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType taxAreaID;
    @XmlElement(name = "TaxAreaState", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType taxAreaState;

    /**
     * This element holds TaxAreaID
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTaxAreaID() {
        return taxAreaID;
    }

    /**
     * Sets the value of the taxAreaID property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTaxAreaID(TextType value) {
        this.taxAreaID = value;
    }

    /**
     * This element holds TaxAreaState
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTaxAreaState() {
        return taxAreaState;
    }

    /**
     * Sets the value of the taxAreaState property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTaxAreaState(TextType value) {
        this.taxAreaState = value;
    }

}
