
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contains information finished goods inventory updates
 * 
 * <p>Java class for FGIType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FGIType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransferOrderNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ShipToID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OutboundCourierID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OutboundCourierTrackingID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FGIType", propOrder = {
    "transferOrderNumber",
    "shipToID",
    "outboundCourierID",
    "outboundCourierTrackingID"
})
public class FGIType {

    @XmlElement(name = "TransferOrderNumber", required = true)
    protected String transferOrderNumber;
    @XmlElement(name = "ShipToID", required = true)
    protected String shipToID;
    @XmlElement(name = "OutboundCourierID", required = true)
    protected String outboundCourierID;
    @XmlElement(name = "OutboundCourierTrackingID", required = true)
    protected String outboundCourierTrackingID;

    /**
     * Gets the value of the transferOrderNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransferOrderNumber() {
        return transferOrderNumber;
    }

    /**
     * Sets the value of the transferOrderNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransferOrderNumber(String value) {
        this.transferOrderNumber = value;
    }

    /**
     * Gets the value of the shipToID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShipToID() {
        return shipToID;
    }

    /**
     * Sets the value of the shipToID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShipToID(String value) {
        this.shipToID = value;
    }

    /**
     * Gets the value of the outboundCourierID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutboundCourierID() {
        return outboundCourierID;
    }

    /**
     * Sets the value of the outboundCourierID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutboundCourierID(String value) {
        this.outboundCourierID = value;
    }

    /**
     * Gets the value of the outboundCourierTrackingID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutboundCourierTrackingID() {
        return outboundCourierTrackingID;
    }

    /**
     * Sets the value of the outboundCourierTrackingID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutboundCourierTrackingID(String value) {
        this.outboundCourierTrackingID = value;
    }

}
