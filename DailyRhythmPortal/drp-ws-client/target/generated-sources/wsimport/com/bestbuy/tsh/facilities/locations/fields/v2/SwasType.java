
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information about Store with in a Store (Facility / Service)
 * 
 * <p>Java class for SwasType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SwasType">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}CustomCodeType">
 *       &lt;sequence>
 *         &lt;element name="Types" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}SwasPropertyType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SwasType", propOrder = {
    "types"
})
public class SwasType
    extends CustomCodeType
{

    @XmlElement(name = "Types")
    protected SwasPropertyType types;

    /**
     * Gets the value of the types property.
     * 
     * @return
     *     possible object is
     *     {@link SwasPropertyType }
     *     
     */
    public SwasPropertyType getTypes() {
        return types;
    }

    /**
     * Sets the value of the types property.
     * 
     * @param value
     *     allowed object is
     *     {@link SwasPropertyType }
     *     
     */
    public void setTypes(SwasPropertyType value) {
        this.types = value;
    }

}
