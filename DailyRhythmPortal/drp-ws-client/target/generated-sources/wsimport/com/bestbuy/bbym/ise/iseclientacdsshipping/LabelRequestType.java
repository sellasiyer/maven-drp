
package com.bestbuy.bbym.ise.iseclientacdsshipping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines fields for getting shipment label
 * 
 * <p>Java class for LabelRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LabelRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ShipManifest" type="{http://bestbuy.com/bbym/logistics/shipping/fields/v1}ShipManifestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabelRequestType", propOrder = {
    "shipManifest"
})
public class LabelRequestType {

    @XmlElement(name = "ShipManifest", required = true)
    protected ShipManifestType shipManifest;

    /**
     * Gets the value of the shipManifest property.
     * 
     * @return
     *     possible object is
     *     {@link ShipManifestType }
     *     
     */
    public ShipManifestType getShipManifest() {
        return shipManifest;
    }

    /**
     * Sets the value of the shipManifest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipManifestType }
     *     
     */
    public void setShipManifest(ShipManifestType value) {
        this.shipManifest = value;
    }

}
