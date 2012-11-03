
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NonBBYProductType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NonBBYProductType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MarketValue" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="StoreofPurchase" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POPSource" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POPTransactionID" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="POPPath" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NonBBYProductType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4", propOrder = {
    "marketValue",
    "storeofPurchase",
    "popSource",
    "popTransactionID",
    "description",
    "popPath"
})
public class NonBBYProductType {

    @XmlElement(name = "MarketValue")
    protected BigDecimal marketValue;
    @XmlElement(name = "StoreofPurchase")
    protected String storeofPurchase;
    @XmlElement(name = "POPSource")
    protected String popSource;
    @XmlElement(name = "POPTransactionID")
    protected String popTransactionID;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "POPPath")
    protected String popPath;

    /**
     * Gets the value of the marketValue property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMarketValue() {
        return marketValue;
    }

    /**
     * Sets the value of the marketValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMarketValue(BigDecimal value) {
        this.marketValue = value;
    }

    /**
     * Gets the value of the storeofPurchase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStoreofPurchase() {
        return storeofPurchase;
    }

    /**
     * Sets the value of the storeofPurchase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStoreofPurchase(String value) {
        this.storeofPurchase = value;
    }

    /**
     * Gets the value of the popSource property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOPSource() {
        return popSource;
    }

    /**
     * Sets the value of the popSource property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOPSource(String value) {
        this.popSource = value;
    }

    /**
     * Gets the value of the popTransactionID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOPTransactionID() {
        return popTransactionID;
    }

    /**
     * Sets the value of the popTransactionID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOPTransactionID(String value) {
        this.popTransactionID = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the popPath property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPOPPath() {
        return popPath;
    }

    /**
     * Sets the value of the popPath property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPOPPath(String value) {
        this.popPath = value;
    }

}
