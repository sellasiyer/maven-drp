
package com.bestbuy.bbym.ise.iseclientacds;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines criteria for manifest search requests
 * Criteria objects define an intersection for the search results (e.g. an AND condition).
 * For example, Status=OPEN with StoreID=298 represents all OPEN manifests for store ID 298.
 * 
 * <p>Java class for ManifestSearchType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManifestSearchType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="StoreID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType" minOccurs="0"/>
 *         &lt;element name="ManifestStatus" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MostRecentNumber" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType">
 *               &lt;minInclusive value="1"/>
 *               &lt;maxInclusive value="30"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="TransactionDateRange" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}DateRange" minOccurs="0"/>
 *         &lt;choice minOccurs="0">
 *           &lt;element name="IMEI_ESN" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}IMEI_ESN_Type" minOccurs="0"/>
 *           &lt;element name="ItemTag" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}ItemTagType" minOccurs="0"/>
 *         &lt;/choice>
 *         &lt;element name="TrackingIdentifier" type="{http://bestbuy.com/bbym/logistics/manifest/fields/v1}PackageTrackingIdType" minOccurs="0"/>
 *         &lt;element name="ManifestID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestSearchType", namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", propOrder = {
    "storeID",
    "manifestStatus",
    "mostRecentNumber",
    "transactionDateRange",
    "imeiesn",
    "itemTag",
    "trackingIdentifier",
    "manifestID"
})
public class ManifestSearchType {

    @XmlElement(name = "StoreID")
    protected BigInteger storeID;
    @XmlElement(name = "ManifestStatus")
    protected String manifestStatus;
    @XmlElement(name = "MostRecentNumber")
    protected Integer mostRecentNumber;
    @XmlElement(name = "TransactionDateRange")
    protected DateRange transactionDateRange;
    @XmlElement(name = "IMEI_ESN")
    protected String imeiesn;
    @XmlElement(name = "ItemTag")
    protected String itemTag;
    @XmlElement(name = "TrackingIdentifier")
    protected String trackingIdentifier;
    @XmlElement(name = "ManifestID")
    protected BigInteger manifestID;

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
     * Gets the value of the manifestStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManifestStatus() {
        return manifestStatus;
    }

    /**
     * Sets the value of the manifestStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManifestStatus(String value) {
        this.manifestStatus = value;
    }

    /**
     * Gets the value of the mostRecentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getMostRecentNumber() {
        return mostRecentNumber;
    }

    /**
     * Sets the value of the mostRecentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setMostRecentNumber(Integer value) {
        this.mostRecentNumber = value;
    }

    /**
     * Gets the value of the transactionDateRange property.
     * 
     * @return
     *     possible object is
     *     {@link DateRange }
     *     
     */
    public DateRange getTransactionDateRange() {
        return transactionDateRange;
    }

    /**
     * Sets the value of the transactionDateRange property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateRange }
     *     
     */
    public void setTransactionDateRange(DateRange value) {
        this.transactionDateRange = value;
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
     * Gets the value of the trackingIdentifier property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrackingIdentifier() {
        return trackingIdentifier;
    }

    /**
     * Sets the value of the trackingIdentifier property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTrackingIdentifier(String value) {
        this.trackingIdentifier = value;
    }

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

}
