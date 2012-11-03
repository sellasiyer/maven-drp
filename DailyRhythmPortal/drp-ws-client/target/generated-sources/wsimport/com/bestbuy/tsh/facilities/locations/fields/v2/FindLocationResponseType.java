
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.metadata.components.v2.ResponseMetaDataType;
import com.bestbuy.tsh.common.v1.StatusType;


/**
 * Provides multiple location information
 * 
 * <p>Java class for FindLocationResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FindLocationResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}ResponseMetaDataType"/>
 *         &lt;element name="Status" type="{http://www.tsh.bestbuy.com/common/v1}StatusType"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}Locations" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FindLocationResponseType", propOrder = {
    "metaData",
    "status",
    "locations"
})
public class FindLocationResponseType {

    @XmlElement(name = "MetaData", required = true)
    protected ResponseMetaDataType metaData;
    @XmlElement(name = "Status", required = true)
    protected StatusType status;
    @XmlElement(name = "Locations")
    protected LocationListType locations;

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link ResponseMetaDataType }
     *     
     */
    public ResponseMetaDataType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseMetaDataType }
     *     
     */
    public void setMetaData(ResponseMetaDataType value) {
        this.metaData = value;
    }

    /**
     * Gets the value of the status property.
     * 
     * @return
     *     possible object is
     *     {@link StatusType }
     *     
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatusType }
     *     
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Contains multiple Location
     * 
     * @return
     *     possible object is
     *     {@link LocationListType }
     *     
     */
    public LocationListType getLocations() {
        return locations;
    }

    /**
     * Sets the value of the locations property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationListType }
     *     
     */
    public void setLocations(LocationListType value) {
        this.locations = value;
    }

}
