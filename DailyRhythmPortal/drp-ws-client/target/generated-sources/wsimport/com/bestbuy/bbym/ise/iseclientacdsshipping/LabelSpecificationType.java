
package com.bestbuy.bbym.ise.iseclientacdsshipping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LabelSpecificationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LabelSpecificationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="LabelImageFormat" type="{http://bestbuy.com/bbym/logistics/shipping/fields/v1}LabelImageFormatType"/>
 *         &lt;element name="LabelStockSize" type="{http://bestbuy.com/bbym/logistics/shipping/fields/v1}LabelStockSizeType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabelSpecificationType", propOrder = {
    "labelImageFormat",
    "labelStockSize"
})
public class LabelSpecificationType {

    @XmlElement(name = "LabelImageFormat", required = true)
    protected LabelImageFormatType labelImageFormat;
    @XmlElement(name = "LabelStockSize")
    protected LabelStockSizeType labelStockSize;

    /**
     * Gets the value of the labelImageFormat property.
     * 
     * @return
     *     possible object is
     *     {@link LabelImageFormatType }
     *     
     */
    public LabelImageFormatType getLabelImageFormat() {
        return labelImageFormat;
    }

    /**
     * Sets the value of the labelImageFormat property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelImageFormatType }
     *     
     */
    public void setLabelImageFormat(LabelImageFormatType value) {
        this.labelImageFormat = value;
    }

    /**
     * Gets the value of the labelStockSize property.
     * 
     * @return
     *     possible object is
     *     {@link LabelStockSizeType }
     *     
     */
    public LabelStockSizeType getLabelStockSize() {
        return labelStockSize;
    }

    /**
     * Sets the value of the labelStockSize property.
     * 
     * @param value
     *     allowed object is
     *     {@link LabelStockSizeType }
     *     
     */
    public void setLabelStockSize(LabelStockSizeType value) {
        this.labelStockSize = value;
    }

}
