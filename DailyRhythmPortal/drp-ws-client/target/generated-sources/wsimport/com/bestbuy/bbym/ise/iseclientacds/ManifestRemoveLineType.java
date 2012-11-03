
package com.bestbuy.bbym.ise.iseclientacds;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Defines common fields for manifest remove line request
 * 
 * <p>Java class for ManifestRemoveLineType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ManifestRemoveLineType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ManifestID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="ManifestLineID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *         &lt;element name="StoreID" type="{http://www.tsh.bestbuy.com/common/qualifiled/datatype/v1}IntegerNumericType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ManifestRemoveLineType", namespace = "http://bestbuy.com/bbym/logistics/manifest/fields/v1", propOrder = {
    "manifestID",
    "manifestLineID",
    "storeID"
})
public class ManifestRemoveLineType {

    @XmlElement(name = "ManifestID", required = true)
    protected BigInteger manifestID;
    @XmlElement(name = "ManifestLineID", required = true)
    protected BigInteger manifestLineID;
    @XmlElement(name = "StoreID", required = true)
    protected BigInteger storeID;

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

}
