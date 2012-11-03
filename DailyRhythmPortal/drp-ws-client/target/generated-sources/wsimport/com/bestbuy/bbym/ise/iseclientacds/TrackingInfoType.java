
package com.bestbuy.bbym.ise.iseclientacds;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TrackingInfoType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TrackingInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}TrackID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}TrackURL" minOccurs="0"/>
 *         &lt;element name="TrackStatus" type="{http://www.tsh.bestbuy.com/common/v1}TrackingStatusType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TrackingInfoType", propOrder = {
    "trackID",
    "trackURL",
    "trackStatus"
})
public class TrackingInfoType {

    @XmlElement(name = "TrackID")
    protected IdentifierType trackID;
    @XmlElement(name = "TrackURL")
    protected TextType trackURL;
    @XmlElement(name = "TrackStatus")
    protected TrackingStatusType trackStatus;

    /**
     * This is the ID used for tracking the status of a job or request.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link IdentifierType }
     *     
     */
    public IdentifierType getTrackID() {
        return trackID;
    }

    /**
     * Sets the value of the trackID property.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentifierType }
     *     
     */
    public void setTrackID(IdentifierType value) {
        this.trackID = value;
    }

    /**
     * This the Tracking URL that can be used to track the current status of the request or job.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link TextType }
     *     
     */
    public TextType getTrackURL() {
        return trackURL;
    }

    /**
     * Sets the value of the trackURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link TextType }
     *     
     */
    public void setTrackURL(TextType value) {
        this.trackURL = value;
    }

    /**
     * Gets the value of the trackStatus property.
     * 
     * @return
     *     possible object is
     *     {@link TrackingStatusType }
     *     
     */
    public TrackingStatusType getTrackStatus() {
        return trackStatus;
    }

    /**
     * Sets the value of the trackStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link TrackingStatusType }
     *     
     */
    public void setTrackStatus(TrackingStatusType value) {
        this.trackStatus = value;
    }

}
