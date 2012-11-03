
package com.bestbuy.bbym.ise.iseclientacds;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines fields within a manifest line item
 * 
 * <p>Java class for ManifestLineItemType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManifestLineItemType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManifestID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="ManifestLineID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="ItemTag" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}ItemTagType"/>
 *         &lt;element name="DeviceStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IMEI_ESN" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}IMEI_ESN_Type"/>
 *         &lt;element name="Make" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Model" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SKU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TransferNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ReturnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProductDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestLineItemType", namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", propOrder = {
    "manifestID",
    "manifestLineID",
    "itemTag",
    "deviceStatus",
    "imeiesn",
    "make",
    "model",
    "sku",
    "transferNumber",
    "returnType",
    "productDescription"
})
public class ManifestLineItemType {

    @XmlElement(name = "ManifestID", required = true)
    protected BigInteger manifestID;
    @XmlElement(name = "ManifestLineID", required = true)
    protected BigInteger manifestLineID;
    @XmlElement(name = "ItemTag", required = true)
    protected String itemTag;
    @XmlElement(name = "DeviceStatus", required = true)
    protected String deviceStatus;
    @XmlElement(name = "IMEI_ESN", required = true)
    protected String imeiesn;
    @XmlElement(name = "Make", required = true)
    protected String make;
    @XmlElement(name = "Model", required = true)
    protected String model;
    @XmlElement(name = "SKU")
    protected String sku;
    @XmlElement(name = "TransferNumber")
    protected String transferNumber;
    @XmlElement(name = "ReturnType", required = true)
    protected String returnType;
    @XmlElement(name = "ProductDescription", required = true)
    protected String productDescription;

    /**
     * Gets the value of the manifestID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getManifestID() {
        return manifestID;
    }

    /**
     * Sets the value of the manifestID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setManifestID(BigInteger value) {
        this.manifestID = value;
    }

    /**
     * Gets the value of the manifestLineID property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getManifestLineID() {
        return manifestLineID;
    }

    /**
     * Sets the value of the manifestLineID property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setManifestLineID(BigInteger value) {
        this.manifestLineID = value;
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

}
