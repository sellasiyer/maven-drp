
package com.bestbuy.tsh.common.location.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import com.bestbuy.tsh.common.datatype.v1.MeasureType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;


/**
 * Geographic location of the city/state
 * 			
 * 
 * <p>Java class for GeographyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GeographyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Latitude" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Longitude" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Elevation" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Altitude" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Sectioncode" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Bsacode" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Matchcode" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Faultcode" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Faultmessage" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/location/v1}Statuscode" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute ref="{http://www.tsh.bestbuy.com/common/v1}type"/>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GeographyType", propOrder = {
    "latitude",
    "longitude",
    "elevation",
    "altitude",
    "userArea",
    "sectioncode",
    "bsacode",
    "matchcode",
    "faultcode",
    "faultmessage",
    "statuscode"
})
public class GeographyType {

    @XmlElement(name = "Latitude")
    protected GeoCoordinateMeasureType latitude;
    @XmlElement(name = "Longitude")
    protected GeoCoordinateMeasureType longitude;
    @XmlElement(name = "Elevation")
    protected MeasureType elevation;
    @XmlElement(name = "Altitude")
    protected GeoCoordinateMeasureType altitude;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;
    @XmlElement(name = "Sectioncode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String sectioncode;
    @XmlElement(name = "Bsacode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String bsacode;
    @XmlElement(name = "Matchcode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String matchcode;
    @XmlElement(name = "Faultcode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String faultcode;
    @XmlElement(name = "Faultmessage")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String faultmessage;
    @XmlElement(name = "Statuscode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String statuscode;
    @XmlAttribute(namespace = "http://www.tsh.bestbuy.com/common/v1")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String type;

    /**
     * the angular distance between an imaginary line around a heavenly body parallel to its equator and the equator itself 
     * 				It is usually expressed in degrees, minutes	and seconds.
     * 				
     * 						An alternative representation uses degrees and minutes, where parts of a minute are expressed in decimal notation 
     * 						with a fraction, thus: 23? 27.500? E. Degrees may also be expressed as a decimal fraction: 23.45833? E. 
     * 						For calculations, the angular measure may be converted to radians, so longitude may also be expressed in this 
     * 						manner as a signed fraction of ? (pi), or an unsigned fraction of 2?.
     * 
     * @return
     *     possible object is
     *     {@link GeoCoordinateMeasureType }
     *     
     */
    public GeoCoordinateMeasureType getLatitude() {
        return latitude;
    }

    /**
     * Sets the value of the latitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeoCoordinateMeasureType }
     *     
     */
    public void setLatitude(GeoCoordinateMeasureType value) {
        this.latitude = value;
    }

    /**
     * Longitude is the angular distance of a point's meridian from the Prime
     * 				(Greenwich) Meridian. It is usually expressed in degrees, minutes and seconds.
     * 				
     * 						An alternative representation uses degrees and minutes, where parts of a minute are expressed in decimal notation 
     * 						with a fraction, thus: 23? 27.500? E. Degrees may also be expressed as a decimal fraction: 23.45833? E. 
     * 						For calculations, the angular measure may be converted to radians, so longitude may also be expressed in this 
     * 						manner as a signed fraction of ? (pi), or an unsigned fraction of 2?.
     * 
     * @return
     *     possible object is
     *     {@link GeoCoordinateMeasureType }
     *     
     */
    public GeoCoordinateMeasureType getLongitude() {
        return longitude;
    }

    /**
     * Sets the value of the longitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeoCoordinateMeasureType }
     *     
     */
    public void setLongitude(GeoCoordinateMeasureType value) {
        this.longitude = value;
    }

    /**
     * Elevation, or geometric height, is mainly used
     * 				when referring to points on the Earth's surface
     * 
     * @return
     *     possible object is
     *     {@link MeasureType }
     *     
     */
    public MeasureType getElevation() {
        return elevation;
    }

    /**
     * Sets the value of the elevation property.
     * 
     * @param value
     *     allowed object is
     *     {@link MeasureType }
     *     
     */
    public void setElevation(MeasureType value) {
        this.elevation = value;
    }

    /**
     * Is the value and unit of measure in the z-axis in a thrre dimensional system.
     * 				Examples of this maybe the Altitude in the case of a map of the world, or the height or shelf in 
     * 				a rack system in the case of a manufacturing or warehouse facility.
     * 
     * @return
     *     possible object is
     *     {@link GeoCoordinateMeasureType }
     *     
     */
    public GeoCoordinateMeasureType getAltitude() {
        return altitude;
    }

    /**
     * Sets the value of the altitude property.
     * 
     * @param value
     *     allowed object is
     *     {@link GeoCoordinateMeasureType }
     *     
     */
    public void setAltitude(GeoCoordinateMeasureType value) {
        this.altitude = value;
    }

    /**
     * Allows the Tag Super Highway schema data model to extend the specification in order to provide additional information that is not captured in Canonical Schema.
     * 
     * @return
     *     possible object is
     *     {@link UserAreaType }
     *     
     */
    public UserAreaType getUserArea() {
        return userArea;
    }

    /**
     * Sets the value of the userArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAreaType }
     *     
     */
    public void setUserArea(UserAreaType value) {
        this.userArea = value;
    }

    /**
     * Elevation, or geometric height, is mainly used
     * 				when referring to points on the Earth's surface
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSectioncode() {
        return sectioncode;
    }

    /**
     * Sets the value of the sectioncode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSectioncode(String value) {
        this.sectioncode = value;
    }

    /**
     * Gets the value of the bsacode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBsacode() {
        return bsacode;
    }

    /**
     * Sets the value of the bsacode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBsacode(String value) {
        this.bsacode = value;
    }

    /**
     * Gets the value of the matchcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatchcode() {
        return matchcode;
    }

    /**
     * Sets the value of the matchcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatchcode(String value) {
        this.matchcode = value;
    }

    /**
     * Gets the value of the faultcode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultcode() {
        return faultcode;
    }

    /**
     * Sets the value of the faultcode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultcode(String value) {
        this.faultcode = value;
    }

    /**
     * Gets the value of the faultmessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaultmessage() {
        return faultmessage;
    }

    /**
     * Sets the value of the faultmessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFaultmessage(String value) {
        this.faultmessage = value;
    }

    /**
     * Gets the value of the statuscode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatuscode() {
        return statuscode;
    }

    /**
     * Sets the value of the statuscode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatuscode(String value) {
        this.statuscode = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

}
