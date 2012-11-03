
package com.bestbuy.bbym.ise.iseclientacdsshipping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Defines common fields for shipping service response
 * 
 * <p>Java class for ShipResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ShipResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TrackingID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LabelImage" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="ManifestStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShipScheduledDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ShipResponseType", propOrder = {
    "trackingID",
    "labelImage",
    "manifestStatus",
    "shipScheduledDateTime"
})
public class ShipResponseType {

    @XmlElement(name = "TrackingID", required = true)
    protected String trackingID;
    @XmlElement(name = "LabelImage", required = true)
    protected byte[] labelImage;
    @XmlElement(name = "ManifestStatus", required = true)
    protected String manifestStatus;
    @XmlElement(name = "ShipScheduledDateTime", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar shipScheduledDateTime;

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
     * Gets the value of the labelImage property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getLabelImage() {
        return labelImage;
    }

    /**
     * Sets the value of the labelImage property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setLabelImage(byte[] value) {
        this.labelImage = ((byte[]) value);
    }

    /**
     * Gets the value of the manifestStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManifestStatus() {
        return manifestStatus;
    }

    /**
     * Sets the value of the manifestStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManifestStatus(String value) {
        this.manifestStatus = value;
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

}
