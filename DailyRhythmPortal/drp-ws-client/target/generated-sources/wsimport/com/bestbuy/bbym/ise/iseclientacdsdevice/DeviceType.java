
package com.bestbuy.bbym.ise.iseclientacdsdevice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Defines fields for each device in a search result 
 * 
 * <p>Java class for DeviceType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DeviceType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ItemTag" type="{http://bestbuy.com/bbym/logistics/device/fields/v1}ItemTagType"/>
 *         &lt;element name="IMEI_ESN" type="{http://bestbuy.com/bbym/logistics/device/fields/v1}IMEI_ESN_Type"/>
 *         &lt;element name="SKU" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ProductDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Make" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Model" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TransferNumber" type="{http://www.w3.org/2001/XMLSchema}normalizedString" minOccurs="0"/>
 *         &lt;element name="ReturnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DeviceStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StatusDateTime" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="ShortIndicator" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="Shrinkable" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DeviceType", namespace = "http://bestbuy.com/bbym/logistics/device/fields/v1", propOrder = {
    "itemTag",
    "imeiesn",
    "sku",
    "productDescription",
    "make",
    "model",
    "transferNumber",
    "returnType",
    "deviceStatus",
    "statusDateTime",
    "shortIndicator",
    "shrinkable"
})
public class DeviceType {

    @XmlElement(name = "ItemTag", required = true)
    protected String itemTag;
    @XmlElement(name = "IMEI_ESN", required = true)
    protected String imeiesn;
    @XmlElement(name = "SKU")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String sku;
    @XmlElement(name = "ProductDescription", required = true)
    protected String productDescription;
    @XmlElement(name = "Make", required = true)
    protected String make;
    @XmlElement(name = "Model", required = true)
    protected String model;
    @XmlElement(name = "TransferNumber")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String transferNumber;
    @XmlElement(name = "ReturnType", required = true)
    protected String returnType;
    @XmlElement(name = "DeviceStatus", required = true)
    protected String deviceStatus;
    @XmlElement(name = "StatusDateTime")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar statusDateTime;
    @XmlElement(name = "ShortIndicator")
    protected Boolean shortIndicator;
    @XmlElement(name = "Shrinkable")
    protected Boolean shrinkable;

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

    /**
     * Gets the value of the productDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductDescription() {
        return productDescription;
    }

    /**
     * Sets the value of the productDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductDescription(String value) {
        this.productDescription = value;
    }

    /**
     * Gets the value of the make property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMake() {
        return make;
    }

    /**
     * Sets the value of the make property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMake(String value) {
        this.make = value;
    }

    /**
     * Gets the value of the model property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the value of the model property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModel(String value) {
        this.model = value;
    }

    /**
     * Gets the value of the transferNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTransferNumber() {
        return transferNumber;
    }

    /**
     * Sets the value of the transferNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTransferNumber(String value) {
        this.transferNumber = value;
    }

    /**
     * Gets the value of the returnType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReturnType() {
        return returnType;
    }

    /**
     * Sets the value of the returnType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReturnType(String value) {
        this.returnType = value;
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
     * Gets the value of the statusDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getStatusDateTime() {
        return statusDateTime;
    }

    /**
     * Sets the value of the statusDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setStatusDateTime(XMLGregorianCalendar value) {
        this.statusDateTime = value;
    }

    /**
     * Gets the value of the shortIndicator property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShortIndicator() {
        return shortIndicator;
    }

    /**
     * Sets the value of the shortIndicator property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShortIndicator(Boolean value) {
        this.shortIndicator = value;
    }

    /**
     * Gets the value of the shrinkable property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShrinkable() {
        return shrinkable;
    }

    /**
     * Sets the value of the shrinkable property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShrinkable(Boolean value) {
        this.shrinkable = value;
    }

}
