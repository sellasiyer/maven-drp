
package com.bestbuy.bbym.ise.iseclientacdsshipping;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Defines common fields for shipping notification messages
 * 
 * <p>Java class for ShipNotifyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipNotifyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TrackingID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CourierID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShipScheduledDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ManifestID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="ShipItems" type="{http://bestbuy.com/bbym/logistics/shipping/fields/v1}ShipItemListType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipNotifyType", propOrder = {
    "trackingID",
    "courierID",
    "shipScheduledDateTime",
    "manifestID",
    "shipItems"
})
public class ShipNotifyType {

    @XmlElement(name = "TrackingID", required = true)
    protected String trackingID;
    @XmlElement(name = "CourierID", required = true)
    protected String courierID;
    @XmlElement(name = "ShipScheduledDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar shipScheduledDateTime;
    @XmlElement(name = "ManifestID", required = true)
    protected BigInteger manifestID;
    @XmlElement(name = "ShipItems", required = true)
    protected ShipItemListType shipItems;

    /**
     * Gets the value of the trackingID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingID() {
        return trackingID;
    }

    /**
     * Sets the value of the trackingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingID(String value) {
        this.trackingID = value;
    }

    /**
     * Gets the value of the courierID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCourierID() {
        return courierID;
    }

    /**
     * Sets the value of the courierID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCourierID(String value) {
        this.courierID = value;
    }

    /**
     * Gets the value of the shipScheduledDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getShipScheduledDateTime() {
        return shipScheduledDateTime;
    }

    /**
     * Sets the value of the shipScheduledDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setShipScheduledDateTime(XMLGregorianCalendar value) {
        this.shipScheduledDateTime = value;
    }

    /**
     * Gets the value of the manifestID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getManifestID() {
        return manifestID;
    }

    /**
     * Sets the value of the manifestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setManifestID(BigInteger value) {
        this.manifestID = value;
    }

    /**
     * Gets the value of the shipItems property.
     * 
     * @return
     *     possible object is
     *     {@link ShipItemListType }
     *     
     */
    public ShipItemListType getShipItems() {
        return shipItems;
    }

    /**
     * Sets the value of the shipItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link ShipItemListType }
     *     
     */
    public void setShipItems(ShipItemListType value) {
        this.shipItems = value;
    }

}
