
package com.bestbuy.tsh.facilities.locations.fields.v2;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.tsh.common.metadata.components.v2.RequestMetaDataType;


/**
 * Input Parameter-LocationID's
 * 				1. MetaData: to capture technical context of the message exchanged between the systems involved.
 * 				2. InternationalBusinessHierarchy : to capture business context of the message exchanged between the systems involved.
 * 				3. LocationIDList: This object defines one or more Location ID's to perform the search. Its optional and if not
 * 					specified then search results will not be constrained based on Location IDs.
 * 				4. LocationTypeFilter: This object defines one or more Location Type ID's to perform the search. LocationTypeFilter is optional and if not
 * 					specified then search results will not be constrained based on location types.
 * 				5. StateProvinceFilter: This object defines State or Province to perform the search. StateFilter is optional and 
 * 					when included will specify 2 letter state/province code as recommended in ISO 3166-2.
 * 				6. ResponseCategory: Defines which content should be included in the response.
 * 					ResponseCategory is optional.  When omitted, only Location identifiers will be included in the response.
 * 				7. UserArea: This object allows to pass any element and kept here for future purpose.
 *  
 * 
 * <p>Java class for RetrieveLocationsRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RetrieveLocationsRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}RequestMetaDataType"/>
 *         &lt;choice>
 *           &lt;element name="LocationIDList" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationIDListType" minOccurs="0"/>
 *           &lt;sequence>
 *             &lt;element name="LocationTypeFilter" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationTypeIDList" minOccurs="0"/>
 *             &lt;element name="StateProvinceFilter" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}StateProvinceCodeList" minOccurs="0"/>
 *           &lt;/sequence>
 *         &lt;/choice>
 *         &lt;element name="FilterProperties" type="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}SearchPropertiesType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RetrieveLocationsRequestType", propOrder = {
    "metaData",
    "locationIDList",
    "locationTypeFilter",
    "stateProvinceFilter",
    "filterProperties"
})
public class RetrieveLocationsRequestType {

    @XmlElement(name = "MetaData", required = true)
    protected RequestMetaDataType metaData;
    @XmlElement(name = "LocationIDList")
    protected LocationIDListType locationIDList;
    @XmlElement(name = "LocationTypeFilter")
    protected LocationTypeIDList locationTypeFilter;
    @XmlElement(name = "StateProvinceFilter")
    protected StateProvinceCodeList stateProvinceFilter;
    @XmlElement(name = "FilterProperties")
    protected SearchPropertiesType filterProperties;

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
     * Gets the value of the locationIDList property.
     * 
     * @return
     *     possible object is
     *     {@link LocationIDListType }
     *     
     */
    public LocationIDListType getLocationIDList() {
        return locationIDList;
    }

    /**
     * Sets the value of the locationIDList property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationIDListType }
     *     
     */
    public void setLocationIDList(LocationIDListType value) {
        this.locationIDList = value;
    }

    /**
     * Gets the value of the locationTypeFilter property.
     * 
     * @return
     *     possible object is
     *     {@link LocationTypeIDList }
     *     
     */
    public LocationTypeIDList getLocationTypeFilter() {
        return locationTypeFilter;
    }

    /**
     * Sets the value of the locationTypeFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocationTypeIDList }
     *     
     */
    public void setLocationTypeFilter(LocationTypeIDList value) {
        this.locationTypeFilter = value;
    }

    /**
     * Gets the value of the stateProvinceFilter property.
     * 
     * @return
     *     possible object is
     *     {@link StateProvinceCodeList }
     *     
     */
    public StateProvinceCodeList getStateProvinceFilter() {
        return stateProvinceFilter;
    }

    /**
     * Sets the value of the stateProvinceFilter property.
     * 
     * @param value
     *     allowed object is
     *     {@link StateProvinceCodeList }
     *     
     */
    public void setStateProvinceFilter(StateProvinceCodeList value) {
        this.stateProvinceFilter = value;
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

}
