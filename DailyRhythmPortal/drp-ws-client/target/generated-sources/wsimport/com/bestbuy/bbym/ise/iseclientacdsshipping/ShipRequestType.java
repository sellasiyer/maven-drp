
package com.bestbuy.bbym.ise.iseclientacdsshipping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines common fields for shipping service requests
 * 
 * <p>Java class for ShipRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Package" type="{http://bestbuy.com/bbym/logistics/shipping/fields/v1}PackageType" minOccurs="0"/>
 *         &lt;element name="ShipFromLocation" type="{http://bestbuy.com/bbym/logistics/shipping/fields/v1}ShipLocationType"/>
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
@XmlType(name = "ShipRequestType", propOrder = {
    "_package",
    "shipFromLocation",
    "shipManifest"
})
public class ShipRequestType {

    @XmlElement(name = "Package")
    protected PackageType _package;
    @XmlElement(name = "ShipFromLocation", required = true)
    protected ShipLocationType shipFromLocation;
    @XmlElement(name = "ShipManifest", required = true)
    protected ShipManifestType shipManifest;

    /**
     * Gets the value of the package property.
     * 
     * @return
     *     possible object is
     *     {@link PackageType }
     *     
     */
    public PackageType getPackage() {
        return _package;
    }

    /**
     * Sets the value of the package property.
     * 
     * @param value
     *     allowed object is
     *     {@link PackageType }
     *     
     */
    public void setPackage(PackageType value) {
        this._package = value;
    }

    /**
     * Gets the value of the shipFromLocation property.
     * 
     * @return
     *     possible object is
     *     {@link ShipLocationType }
     *     
     */
    public ShipLocationType getShipFromLocation() {
        return shipFromLocation;
    }

    /**
     * Sets the value of the shipFromLocation property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipLocationType }
     *     
     */
    public void setShipFromLocation(ShipLocationType value) {
        this.shipFromLocation = value;
    }

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
