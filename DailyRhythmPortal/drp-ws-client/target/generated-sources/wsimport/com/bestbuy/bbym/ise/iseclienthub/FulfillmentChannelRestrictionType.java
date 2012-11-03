
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FulfillmentChannelRestrictionType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FulfillmentChannelRestrictionType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RestrictInHome" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RestrictInStore" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *         &lt;element name="RestrictRemote" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FulfillmentChannelRestrictionType", propOrder = {
    "restrictInHome",
    "restrictInStore",
    "restrictRemote"
})
public class FulfillmentChannelRestrictionType {

    @XmlElement(name = "RestrictInHome")
    protected Boolean restrictInHome;
    @XmlElement(name = "RestrictInStore")
    protected Boolean restrictInStore;
    @XmlElement(name = "RestrictRemote")
    protected Boolean restrictRemote;

    /**
     * Gets the value of the restrictInHome property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRestrictInHome() {
        return restrictInHome;
    }

    /**
     * Sets the value of the restrictInHome property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRestrictInHome(Boolean value) {
        this.restrictInHome = value;
    }

    /**
     * Gets the value of the restrictInStore property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRestrictInStore() {
        return restrictInStore;
    }

    /**
     * Sets the value of the restrictInStore property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRestrictInStore(Boolean value) {
        this.restrictInStore = value;
    }

    /**
     * Gets the value of the restrictRemote property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isRestrictRemote() {
        return restrictRemote;
    }

    /**
     * Sets the value of the restrictRemote property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setRestrictRemote(Boolean value) {
        this.restrictRemote = value;
    }

}
