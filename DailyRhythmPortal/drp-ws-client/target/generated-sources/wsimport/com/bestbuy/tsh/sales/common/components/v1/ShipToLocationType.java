
package com.bestbuy.tsh.sales.common.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * ShipToLocationType
 * 
 * <p>Java class for ShipToLocationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipToLocationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TaxArea" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}TaxAreaType" minOccurs="0"/>
 *         &lt;element name="ShipToAddress" type="{http://www.tsh.bestbuy.com/sales/common/components/v1}AddressType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipToLocationType", propOrder = {
    "taxArea",
    "shipToAddress"
})
public class ShipToLocationType {

    @XmlElement(name = "TaxArea")
    protected TaxAreaType taxArea;
    @XmlElement(name = "ShipToAddress")
    protected AddressType shipToAddress;

    /**
     * Gets the value of the taxArea property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAreaType }
     *     
     */
    public TaxAreaType getTaxArea() {
        return taxArea;
    }

    /**
     * Sets the value of the taxArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAreaType }
     *     
     */
    public void setTaxArea(TaxAreaType value) {
        this.taxArea = value;
    }

    /**
     * Gets the value of the shipToAddress property.
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getShipToAddress() {
        return shipToAddress;
    }

    /**
     * Sets the value of the shipToAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setShipToAddress(AddressType value) {
        this.shipToAddress = value;
    }

}
