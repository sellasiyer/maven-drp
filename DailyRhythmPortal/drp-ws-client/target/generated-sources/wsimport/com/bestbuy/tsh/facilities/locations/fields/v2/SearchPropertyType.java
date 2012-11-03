
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contains name , values pair for multiple search properties
 * 
 * <p>Java class for SearchPropertyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SearchPropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}StringType" minOccurs="0"/>
 *         &lt;element name="Values" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}SearchPropertyValuesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SearchPropertyType", propOrder = {
    "name",
    "values"
})
public class SearchPropertyType {

    @XmlElement(name = "Name")
    protected String name;
    @XmlElement(name = "Values")
    protected SearchPropertyValuesType values;

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * Gets the value of the values property.
     * 
     * @return
     *     possible object is
     *     {@link SearchPropertyValuesType }
     *     
     */
    public SearchPropertyValuesType getValues() {
        return values;
    }

    /**
     * Sets the value of the values property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchPropertyValuesType }
     *     
     */
    public void setValues(SearchPropertyValuesType value) {
        this.values = value;
    }

}
