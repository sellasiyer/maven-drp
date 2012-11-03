
package com.bestbuy.tsh.facilities.locations.fields.v2;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contains multiple Service Type
 * 
 * <p>Java class for LocationServicesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationServicesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationService" maxOccurs="25" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationServicesType", propOrder = {
    "locationService"
})
public class LocationServicesType {

    @XmlElement(name = "LocationService")
    protected List<SwasType> locationService;

    /**
     * 
     * 				Different types of services are carried out in a location.
     * 			Gets the value of the locationService property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationService property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationService().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SwasType }
     * 
     * 
     */
    public List<SwasType> getLocationService() {
        if (locationService == null) {
            locationService = new ArrayList<SwasType>();
        }
        return this.locationService;
    }

}
