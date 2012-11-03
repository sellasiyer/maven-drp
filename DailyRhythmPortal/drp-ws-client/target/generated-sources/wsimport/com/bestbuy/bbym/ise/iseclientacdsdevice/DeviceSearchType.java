
package com.bestbuy.bbym.ise.iseclientacdsdevice;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Defines criteria for device search requests
 * Criteria objects define an intersection for the search results (e.g. an AND condition).
 * For example, Status=OPEN with StoreID=298 represents all devices in OPEN status for store ID 298.
 * 
 * <p>Java class for DeviceSearchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceSearchType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StoreID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType" minOccurs="0"/>
 *         &lt;element name="DeviceStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IMEI_ESN" type="{http://bestbuy.com/bbym/logistics/device/fields/v1}IMEI_ESN_Type" minOccurs="0"/>
 *         &lt;element name="ItemTag" type="{http://bestbuy.com/bbym/logistics/device/fields/v1}ItemTagType" minOccurs="0"/>
 *         &lt;element name="SKU" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceSearchType", namespace = "http://bestbuy.com/bbym/logistics/device/fields/v1", propOrder = {
    "storeID",
    "deviceStatus",
    "imeiesn",
    "itemTag",
    "sku"
})
public class DeviceSearchType {

    @XmlElement(name = "StoreID")
    protected BigInteger storeID;
    @XmlElement(name = "DeviceStatus")
    protected String deviceStatus;
    @XmlElement(name = "IMEI_ESN")
    protected String imeiesn;
    @XmlElement(name = "ItemTag")
    protected String itemTag;
    @XmlElement(name = "SKU")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sku;

    /**
     * Gets the value of the storeID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getStoreID() {
        return storeID;
    }

    /**
     * Sets the value of the storeID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setStoreID(BigInteger value) {
        this.storeID = value;
    }

    /**
     * Gets the value of the deviceStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDeviceStatus() {
        return deviceStatus;
    }

    /**
     * Sets the value of the deviceStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDeviceStatus(String value) {
        this.deviceStatus = value;
    }

    /**
     * Gets the value of the imeiesn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIMEIESN() {
        return imeiesn;
    }

    /**
     * Sets the value of the imeiesn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIMEIESN(String value) {
        this.imeiesn = value;
    }

    /**
     * Gets the value of the itemTag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getItemTag() {
        return itemTag;
    }

    /**
     * Sets the value of the itemTag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setItemTag(String value) {
        this.itemTag = value;
    }

    /**
     * Gets the value of the sku property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSKU() {
        return sku;
    }

    /**
     * Sets the value of the sku property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSKU(String value) {
        this.sku = value;
    }

}
