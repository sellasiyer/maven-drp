
package com.bestbuy.tsh.facilities.locations.fields.v2;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Contains primary information of the Location
 * 
 * <p>Java class for HeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationName" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationTypeID" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationType" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationCategory" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationTypeDescription" minOccurs="0"/>
 *         &lt;element name="DefaultWarehouseId" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType" minOccurs="0"/>
 *         &lt;element name="TaxPercentage" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}DoubleNumericType" minOccurs="0"/>
 *         &lt;group ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationIndicatorGroup" minOccurs="0"/>
 *         &lt;group ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationDateGroup" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/facilities/locations/fields/v2}LocationHierarchy" maxOccurs="10" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType", propOrder = {
    "locationID",
    "locationName",
    "locationTypeID",
    "locationType",
    "locationCategory",
    "locationTypeDescription",
    "defaultWarehouseId",
    "taxPercentage",
    "expressSASIndicator",
    "mobileSASIndicator",
    "storeDeliveryIndicator",
    "physicalWarehouseIndicator",
    "crossDockIndicator",
    "openDate",
    "closeDate",
    "locationHierarchy"
})
public class HeaderType {

    @XmlElement(name = "LocationID")
    protected BigInteger locationID;
    @XmlElement(name = "LocationName")
    protected String locationName;
    @XmlElement(name = "LocationTypeID")
    protected BigInteger locationTypeID;
    @XmlElement(name = "LocationType")
    protected String locationType;
    @XmlElement(name = "LocationCategory")
    protected String locationCategory;
    @XmlElement(name = "LocationTypeDescription")
    protected String locationTypeDescription;
    @XmlElement(name = "DefaultWarehouseId")
    protected BigInteger defaultWarehouseId;
    @XmlElement(name = "TaxPercentage")
    protected Double taxPercentage;
    @XmlElement(name = "ExpressSASIndicator")
    protected String expressSASIndicator;
    @XmlElement(name = "MobileSASIndicator")
    protected String mobileSASIndicator;
    @XmlElement(name = "StoreDeliveryIndicator")
    protected String storeDeliveryIndicator;
    @XmlElement(name = "PhysicalWarehouseIndicator")
    protected String physicalWarehouseIndicator;
    @XmlElement(name = "CrossDockIndicator")
    protected String crossDockIndicator;
    @XmlElement(name = "OpenDate")
    protected XMLGregorianCalendar openDate;
    @XmlElement(name = "CloseDate")
    protected XMLGregorianCalendar closeDate;
    @XmlElement(name = "LocationHierarchy")
    protected List<LocationHierarchyType> locationHierarchy;

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
     * 
     * 				Name of the Location
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationName() {
        return locationName;
    }

    /**
     * Sets the value of the locationName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationName(String value) {
        this.locationName = value;
    }

    /**
     * Type of Location as Per Source System
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getLocationTypeID() {
        return locationTypeID;
    }

    /**
     * Sets the value of the locationTypeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setLocationTypeID(BigInteger value) {
        this.locationTypeID = value;
    }

    /**
     * Abbreviated LocationType Name 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationType() {
        return locationType;
    }

    /**
     * Sets the value of the locationType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationType(String value) {
        this.locationType = value;
    }

    /**
     * Possible values = "Store" | "Warehouse"
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationCategory() {
        return locationCategory;
    }

    /**
     * Sets the value of the locationCategory property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationCategory(String value) {
        this.locationCategory = value;
    }

    /**
     * Description for Location Type
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLocationTypeDescription() {
        return locationTypeDescription;
    }

    /**
     * Sets the value of the locationTypeDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLocationTypeDescription(String value) {
        this.locationTypeDescription = value;
    }

    /**
     * Gets the value of the defaultWarehouseId property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDefaultWarehouseId() {
        return defaultWarehouseId;
    }

    /**
     * Sets the value of the defaultWarehouseId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDefaultWarehouseId(BigInteger value) {
        this.defaultWarehouseId = value;
    }

    /**
     * Gets the value of the taxPercentage property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getTaxPercentage() {
        return taxPercentage;
    }

    /**
     * Sets the value of the taxPercentage property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setTaxPercentage(Double value) {
        this.taxPercentage = value;
    }

    /**
     * Gets the value of the expressSASIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExpressSASIndicator() {
        return expressSASIndicator;
    }

    /**
     * Sets the value of the expressSASIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExpressSASIndicator(String value) {
        this.expressSASIndicator = value;
    }

    /**
     * Gets the value of the mobileSASIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobileSASIndicator() {
        return mobileSASIndicator;
    }

    /**
     * Sets the value of the mobileSASIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMobileSASIndicator(String value) {
        this.mobileSASIndicator = value;
    }

    /**
     * Gets the value of the storeDeliveryIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreDeliveryIndicator() {
        return storeDeliveryIndicator;
    }

    /**
     * Sets the value of the storeDeliveryIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreDeliveryIndicator(String value) {
        this.storeDeliveryIndicator = value;
    }

    /**
     * Gets the value of the physicalWarehouseIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhysicalWarehouseIndicator() {
        return physicalWarehouseIndicator;
    }

    /**
     * Sets the value of the physicalWarehouseIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhysicalWarehouseIndicator(String value) {
        this.physicalWarehouseIndicator = value;
    }

    /**
     * Gets the value of the crossDockIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCrossDockIndicator() {
        return crossDockIndicator;
    }

    /**
     * Sets the value of the crossDockIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCrossDockIndicator(String value) {
        this.crossDockIndicator = value;
    }

    /**
     * Gets the value of the openDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getOpenDate() {
        return openDate;
    }

    /**
     * Sets the value of the openDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setOpenDate(XMLGregorianCalendar value) {
        this.openDate = value;
    }

    /**
     * Gets the value of the closeDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCloseDate() {
        return closeDate;
    }

    /**
     * Sets the value of the closeDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCloseDate(XMLGregorianCalendar value) {
        this.closeDate = value;
    }

    /**
     * Gets the value of the locationHierarchy property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the locationHierarchy property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getLocationHierarchy().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LocationHierarchyType }
     * 
     * 
     */
    public List<LocationHierarchyType> getLocationHierarchy() {
        if (locationHierarchy == null) {
            locationHierarchy = new ArrayList<LocationHierarchyType>();
        }
        return this.locationHierarchy;
    }

}
