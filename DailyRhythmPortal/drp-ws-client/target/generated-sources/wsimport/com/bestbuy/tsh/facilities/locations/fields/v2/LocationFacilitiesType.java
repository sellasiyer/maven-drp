
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
 * <p>Java class for LocationFacilitiesType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="LocationFacilitiesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationFacility" maxOccurs="25" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LocationFacilitiesType", propOrder = {
    "locationFacility"
})
public class LocationFacilitiesType {

    @XmlElement(name = "LocationFacility")
    protected List<SwasType> locationFacility;

    /**
     * 
     * 				Different types of services are carried out in a location.
     * 			Gets the value of the locationFacility property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationFacility property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationFacility().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SwasType }
     * 
     * 
     */
    public List<SwasType> getLocationFacility() {
        if (locationFacility == null) {
            locationFacility = new ArrayList<SwasType>();
        }
        return this.locationFacility;
    }

}
