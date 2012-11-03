
package com.bestbuy.bbym.ise.iseclientacdsshipping;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines fields for get Get Shipping Label response
 * 
 * <p>Java class for LabelResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LabelResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TrackingID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LabelImage" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LabelResponseType", propOrder = {
    "trackingID",
    "labelImage"
})
public class LabelResponseType {

    @XmlElement(name = "TrackingID", required = true)
    protected String trackingID;
    @XmlElement(name = "LabelImage", required = true)
    protected byte[] labelImage;

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

}
