
package com.bestbuy.bbym.ise.iseclientucs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for UpgradeEligibilityResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpgradeEligibilityResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carrierAccountNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="zip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="language" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="subscribers" type="{http://bestbuy.com/bbym/ucs}Subscriber" maxOccurs="unbounded"/>
 *         &lt;element name="upgradeCheckCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="optInCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="tradeInDisclaimer" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpgradeEligibilityResponse", propOrder = {
    "carrierAccountNumber",
    "zip",
    "language",
    "subscribers",
    "upgradeCheckCount",
    "optInCount",
    "tradeInDisclaimer"
})
public class UpgradeEligibilityResponse {

    protected String carrierAccountNumber;
    protected String zip;
    @XmlElement(required = true)
    protected String language;
    @XmlElement(required = true)
    protected List<Subscriber> subscribers;
    protected int upgradeCheckCount;
    protected int optInCount;
    @XmlElement(required = true)
    protected String tradeInDisclaimer;

    /**
     * Gets the value of the carrierAccountNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCarrierAccountNumber() {
        return carrierAccountNumber;
    }

    /**
     * Sets the value of the carrierAccountNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCarrierAccountNumber(String value) {
        this.carrierAccountNumber = value;
    }

    /**
     * Gets the value of the zip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZip() {
        return zip;
    }

    /**
     * Sets the value of the zip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZip(String value) {
        this.zip = value;
    }

    /**
     * Gets the value of the language property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLanguage() {
        return language;
    }

    /**
     * Sets the value of the language property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLanguage(String value) {
        this.language = value;
    }

    /**
     * Gets the value of the subscribers property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the subscribers property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSubscribers().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Subscriber }
     * 
     * 
     */
    public List<Subscriber> getSubscribers() {
        if (subscribers == null) {
            subscribers = new ArrayList<Subscriber>();
        }
        return this.subscribers;
    }

    /**
     * Gets the value of the upgradeCheckCount property.
     * 
     */
    public int getUpgradeCheckCount() {
        return upgradeCheckCount;
    }

    /**
     * Sets the value of the upgradeCheckCount property.
     * 
     */
    public void setUpgradeCheckCount(int value) {
        this.upgradeCheckCount = value;
    }

    /**
     * Gets the value of the optInCount property.
     * 
     */
    public int getOptInCount() {
        return optInCount;
    }

    /**
     * Sets the value of the optInCount property.
     * 
     */
    public void setOptInCount(int value) {
        this.optInCount = value;
    }

    /**
     * Gets the value of the tradeInDisclaimer property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTradeInDisclaimer() {
        return tradeInDisclaimer;
    }

    /**
     * Sets the value of the tradeInDisclaimer property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTradeInDisclaimer(String value) {
        this.tradeInDisclaimer = value;
    }

}
