
package com.bestbuy.bbym.ise.iseclientacds;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Defines manifest header fields
 * 
 * <p>Java class for ManifestHeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManifestHeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManifestID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="Status" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceCount" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="TrackingIdentifier" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}PackageTrackingIdType" minOccurs="0"/>
 *         &lt;element name="DayCreated" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DateTimeCreated" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="ShipScheduledDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="CreatedByUser" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ManifestLine" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}ManifestLineItemType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestHeaderType", namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", propOrder = {
    "manifestID",
    "status",
    "deviceCount",
    "trackingIdentifier",
    "dayCreated",
    "dateTimeCreated",
    "shipScheduledDateTime",
    "createdByUser",
    "manifestLine"
})
public class ManifestHeaderType {

    @XmlElement(name = "ManifestID", required = true)
    protected BigInteger manifestID;
    @XmlElement(name = "Status", required = true)
    protected String status;
    @XmlElement(name = "DeviceCount", required = true)
    protected BigInteger deviceCount;
    @XmlElement(name = "TrackingIdentifier")
    protected String trackingIdentifier;
    @XmlElement(name = "DayCreated", required = true)
    protected String dayCreated;
    @XmlElement(name = "DateTimeCreated", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateTimeCreated;
    @XmlElement(name = "ShipScheduledDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar shipScheduledDateTime;
    @XmlElement(name = "CreatedByUser", required = true)
    protected String createdByUser;
    @XmlElement(name = "ManifestLine", required = true)
    protected List<ManifestLineItemType> manifestLine;

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
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

    /**
     * Gets the value of the deviceCount property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDeviceCount() {
        return deviceCount;
    }

    /**
     * Sets the value of the deviceCount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDeviceCount(BigInteger value) {
        this.deviceCount = value;
    }

    /**
     * Gets the value of the trackingIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingIdentifier() {
        return trackingIdentifier;
    }

    /**
     * Sets the value of the trackingIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingIdentifier(String value) {
        this.trackingIdentifier = value;
    }

    /**
     * Gets the value of the dayCreated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDayCreated() {
        return dayCreated;
    }

    /**
     * Sets the value of the dayCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDayCreated(String value) {
        this.dayCreated = value;
    }

    /**
     * Gets the value of the dateTimeCreated property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateTimeCreated() {
        return dateTimeCreated;
    }

    /**
     * Sets the value of the dateTimeCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateTimeCreated(XMLGregorianCalendar value) {
        this.dateTimeCreated = value;
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
     * Gets the value of the createdByUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreatedByUser() {
        return createdByUser;
    }

    /**
     * Sets the value of the createdByUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreatedByUser(String value) {
        this.createdByUser = value;
    }

    /**
     * Gets the value of the manifestLine property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the manifestLine property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getManifestLine().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ManifestLineItemType }
     * 
     * 
     */
    public List<ManifestLineItemType> getManifestLine() {
        if (manifestLine == null) {
            manifestLine = new ArrayList<ManifestLineItemType>();
        }
        return this.manifestLine;
    }

}
