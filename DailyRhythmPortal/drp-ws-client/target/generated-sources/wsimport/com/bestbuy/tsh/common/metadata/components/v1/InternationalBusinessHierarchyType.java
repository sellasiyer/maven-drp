
package com.bestbuy.tsh.common.metadata.components.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * International Business Hierarchy structure
 * 
 * <p>Java class for InternationalBusinessHierarchyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InternationalBusinessHierarchyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}Enterprise" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}TradingArea" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}Company" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}Brand" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}BusinessUnit" minOccurs="0"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/metadata/fields/v1}Channel" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InternationalBusinessHierarchyType", propOrder = {
    "enterprise",
    "tradingArea",
    "company",
    "brand",
    "businessUnit",
    "channel"
})
public class InternationalBusinessHierarchyType {

    @XmlElement(name = "Enterprise", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String enterprise;
    @XmlElement(name = "TradingArea", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String tradingArea;
    @XmlElement(name = "Company", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String company;
    @XmlElement(name = "Brand", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String brand;
    @XmlElement(name = "BusinessUnit", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String businessUnit;
    @XmlElement(name = "Channel", namespace = "http://www.tsh.bestbuy.com/common/metadata/fields/v1")
    protected String channel;

    /**
     * 
     * 				The overall Best Buy. INC Corporation
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnterprise() {
        return enterprise;
    }

    /**
     * Sets the value of the enterprise property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnterprise(String value) {
        this.enterprise = value;
    }

    /**
     * 
     * 				A distinct Geographic region comprising of one
     * 				or more countries: US, Canada, China, UK,
     * 				Mexico. This will be implemented as the ISO
     * 				Standard 3166: 2 Character Country code.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradingArea() {
        return tradingArea;
    }

    /**
     * Sets the value of the tradingArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradingArea(String value) {
        this.tradingArea = value;
    }

    /**
     * 
     * 				One or more legal entities which reside /
     * 				operate in the particular countries under a
     * 				given Trading Area.
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCompany() {
        return company;
    }

    /**
     * Sets the value of the company property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCompany(String value) {
        this.company = value;
    }

    /**
     * 
     * 				A distinct banner under which business is
     * 				operated (usually visible to the customer)
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrand() {
        return brand;
    }

    /**
     * Sets the value of the brand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrand(String value) {
        this.brand = value;
    }

    /**
     * 
     * 				An organizational sub unit which may hold
     * 				P&L responsibility
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessUnit() {
        return businessUnit;
    }

    /**
     * Sets the value of the businessUnit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessUnit(String value) {
        this.businessUnit = value;
    }

    /**
     * 
     * 				The medium under which business is operated
     * 			
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

}
