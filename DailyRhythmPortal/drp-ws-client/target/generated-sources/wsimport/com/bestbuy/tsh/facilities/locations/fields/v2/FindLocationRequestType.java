
package com.bestbuy.tsh.facilities.locations.fields.v2;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.location.v1.MailingAddressType;
import com.bestbuy.tsh.common.metadata.components.v2.RequestMetaDataType;


/**
 * Input Parameter-LocationID/Address in combination of MaxStoreReponse , Radius , LocationTypes
 * 
 * <p>Java class for FindLocationRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindLocationRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}RequestMetaDataType"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationPoint" minOccurs="0"/>
 *         &lt;element name="Address" type="{http://www.tsh.bestbuy.com/common/location/v1}MailingAddressType" minOccurs="0"/>
 *         &lt;element name="FilterProperties" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}SearchPropertiesType" minOccurs="0"/>
 *         &lt;element name="MaxLocationResponse" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="Radius" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}DistanceType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindLocationRequestType", propOrder = {
    "metaData",
    "locationID",
    "locationPoint",
    "address",
    "filterProperties",
    "maxLocationResponse",
    "radius"
})
public class FindLocationRequestType {

    @XmlElement(name = "MetaData", required = true)
    protected RequestMetaDataType metaData;
    @XmlElement(name = "LocationID")
    protected BigInteger locationID;
    @XmlElement(name = "LocationPoint")
    protected GeoLocationPointType locationPoint;
    @XmlElement(name = "Address")
    protected MailingAddressType address;
    @XmlElement(name = "FilterProperties")
    protected SearchPropertiesType filterProperties;
    @XmlElement(name = "MaxLocationResponse", required = true, defaultValue = "30")
    protected BigInteger maxLocationResponse;
    @XmlElement(name = "Radius", required = true, defaultValue = "1.0E2")
    protected DistanceType radius;

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetaDataType }
     *     
     */
    public RequestMetaDataType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetaDataType }
     *     
     */
    public void setMetaData(RequestMetaDataType value) {
        this.metaData = value;
    }

    /**
     * Location Identifier
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLocationID() {
        return locationID;
    }

    /**
     * Sets the value of the locationID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLocationID(BigInteger value) {
        this.locationID = value;
    }

    /**
     * Geographical information (latitude , longitude) of the location.	
     * 
     * @return
     *     possible object is
     *     {@link GeoLocationPointType }
     *     
     */
    public GeoLocationPointType getLocationPoint() {
        return locationPoint;
    }

    /**
     * Sets the value of the locationPoint property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeoLocationPointType }
     *     
     */
    public void setLocationPoint(GeoLocationPointType value) {
        this.locationPoint = value;
    }

    /**
     * Gets the value of the address property.
     * 
     * @return
     *     possible object is
     *     {@link MailingAddressType }
     *     
     */
    public MailingAddressType getAddress() {
        return address;
    }

    /**
     * Sets the value of the address property.
     * 
     * @param value
     *     allowed object is
     *     {@link MailingAddressType }
     *     
     */
    public void setAddress(MailingAddressType value) {
        this.address = value;
    }

    /**
     * Gets the value of the filterProperties property.
     * 
     * @return
     *     possible object is
     *     {@link SearchPropertiesType }
     *     
     */
    public SearchPropertiesType getFilterProperties() {
        return filterProperties;
    }

    /**
     * Sets the value of the filterProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchPropertiesType }
     *     
     */
    public void setFilterProperties(SearchPropertiesType value) {
        this.filterProperties = value;
    }

    /**
     * Gets the value of the maxLocationResponse property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getMaxLocationResponse() {
        return maxLocationResponse;
    }

    /**
     * Sets the value of the maxLocationResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setMaxLocationResponse(BigInteger value) {
        this.maxLocationResponse = value;
    }

    /**
     * Gets the value of the radius property.
     * 
     * @return
     *     possible object is
     *     {@link DistanceType }
     *     
     */
    public DistanceType getRadius() {
        return radius;
    }

    /**
     * Sets the value of the radius property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistanceType }
     *     
     */
    public void setRadius(DistanceType value) {
        this.radius = value;
    }

}
