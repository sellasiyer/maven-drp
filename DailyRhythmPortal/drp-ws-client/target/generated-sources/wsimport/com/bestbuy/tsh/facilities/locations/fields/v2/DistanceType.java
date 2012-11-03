
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for DistanceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DistanceType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>double">
 *       &lt;attribute name="UOM" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}DistanceUOM" default="Kilometer" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DistanceType", propOrder = {
    "value"
})
public class DistanceType {

    @XmlValue
    protected double value;
    @XmlAttribute(name = "UOM")
    protected DistanceUOM uom;

    /**
     * Gets the value of the value property.
     * 
     */
    public double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Gets the value of the uom property.
     * 
     * @return
     *     possible object is
     *     {@link DistanceUOM }
     *     
     */
    public DistanceUOM getUOM() {
        if (uom == null) {
            return DistanceUOM.KILOMETER;
        } else {
            return uom;
        }
    }

    /**
     * Sets the value of the uom property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistanceUOM }
     *     
     */
    public void setUOM(DistanceUOM value) {
        this.uom = value;
    }

}
