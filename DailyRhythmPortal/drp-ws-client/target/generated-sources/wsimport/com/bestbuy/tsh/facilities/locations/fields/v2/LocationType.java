
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides information about the store			
 * 
 * <p>Java class for LocationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}Header" minOccurs="0"/>
 *         &lt;element name="Communication" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}CommunicationType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationHours" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationPoint" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationServices" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationFacilities" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}Traits" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}Employees" minOccurs="0"/>
 *         &lt;element name="Distance" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}DistanceType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationType", propOrder = {
    "header",
    "communication",
    "locationHours",
    "locationPoint",
    "locationServices",
    "locationFacilities",
    "traits",
    "employees",
    "distance"
})
public class LocationType {

    @XmlElement(name = "Header")
    protected HeaderType header;
    @XmlElement(name = "Communication")
    protected CommunicationType communication;
    @XmlElement(name = "LocationHours")
    protected LocationHoursType locationHours;
    @XmlElement(name = "LocationPoint")
    protected GeoLocationPointType locationPoint;
    @XmlElement(name = "LocationServices")
    protected LocationServicesType locationServices;
    @XmlElement(name = "LocationFacilities")
    protected LocationFacilitiesType locationFacilities;
    @XmlElement(name = "Traits")
    protected TraitsType traits;
    @XmlElement(name = "Employees")
    protected EmployeesType employees;
    @XmlElement(name = "Distance")
    protected DistanceType distance;

    /**
     * This element provides all Primary information of BBY Location
     * 
     * @return
     *     possible object is
     *     {@link HeaderType }
     *     
     */
    public HeaderType getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType }
     *     
     */
    public void setHeader(HeaderType value) {
        this.header = value;
    }

    /**
     * Gets the value of the communication property.
     * 
     * @return
     *     possible object is
     *     {@link CommunicationType }
     *     
     */
    public CommunicationType getCommunication() {
        return communication;
    }

    /**
     * Sets the value of the communication property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommunicationType }
     *     
     */
    public void setCommunication(CommunicationType value) {
        this.communication = value;
    }

    /**
     * 
     * 			Hours of operation for each Location [i.e opening and closing timing for each individual days] 
     * 			
     * 
     * @return
     *     possible object is
     *     {@link LocationHoursType }
     *     
     */
    public LocationHoursType getLocationHours() {
        return locationHours;
    }

    /**
     * Sets the value of the locationHours property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationHoursType }
     *     
     */
    public void setLocationHours(LocationHoursType value) {
        this.locationHours = value;
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
     * 
     * 				Contains multipl ServiceType.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link LocationServicesType }
     *     
     */
    public LocationServicesType getLocationServices() {
        return locationServices;
    }

    /**
     * Sets the value of the locationServices property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationServicesType }
     *     
     */
    public void setLocationServices(LocationServicesType value) {
        this.locationServices = value;
    }

    /**
     * 
     * 				Contains multipl ServiceType.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link LocationFacilitiesType }
     *     
     */
    public LocationFacilitiesType getLocationFacilities() {
        return locationFacilities;
    }

    /**
     * Sets the value of the locationFacilities property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationFacilitiesType }
     *     
     */
    public void setLocationFacilities(LocationFacilitiesType value) {
        this.locationFacilities = value;
    }

    /**
     * This element provides all additional information of Location
     * 
     * @return
     *     possible object is
     *     {@link TraitsType }
     *     
     */
    public TraitsType getTraits() {
        return traits;
    }

    /**
     * Sets the value of the traits property.
     * 
     * @param value
     *     allowed object is
     *     {@link TraitsType }
     *     
     */
    public void setTraits(TraitsType value) {
        this.traits = value;
    }

    /**
     * This element provides the details of Employees in BestBuy Location.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link EmployeesType }
     *     
     */
    public EmployeesType getEmployees() {
        return employees;
    }

    /**
     * Sets the value of the employees property.
     * 
     * @param value
     *     allowed object is
     *     {@link EmployeesType }
     *     
     */
    public void setEmployees(EmployeesType value) {
        this.employees = value;
    }

    /**
     * Gets the value of the distance property.
     * 
     * @return
     *     possible object is
     *     {@link DistanceType }
     *     
     */
    public DistanceType getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     * @param value
     *     allowed object is
     *     {@link DistanceType }
     *     
     */
    public void setDistance(DistanceType value) {
        this.distance = value;
    }

}
