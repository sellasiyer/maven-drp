
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.datatype.v1.IdentifierType;
import com.bestbuy.tsh.common.datatype.v1.TextType;


/**
 * <p>Java class for ItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SKU" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SkuDescription" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/sales/common/fields/v1}SkuPLUText" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ItemType", propOrder = {
    "sku",
    "skuDescription",
    "skuPLUText"
})
public class ItemType {

    @XmlElement(name = "SKU", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected IdentifierType sku;
    @XmlElement(name = "SkuDescription", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType skuDescription;
    @XmlElement(name = "SkuPLUText", namespace = "http://www.tsh.bestbuy.com/sales/common/fields/v1")
    protected TextType skuPLUText;

    /**
     * SKU
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getSKU() {
        return sku;
    }

    /**
     * Sets the value of the sku property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setSKU(IdentifierType value) {
        this.sku = value;
    }

    /**
     * SkuDescription
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSkuDescription() {
        return skuDescription;
    }

    /**
     * Sets the value of the skuDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSkuDescription(TextType value) {
        this.skuDescription = value;
    }

    /**
     * SkuPLUText
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getSkuPLUText() {
        return skuPLUText;
    }

    /**
     * Sets the value of the skuPLUText property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setSkuPLUText(TextType value) {
        this.skuPLUText = value;
    }

}
