
package com.bestbuy.bbym.ise.iseclienthub;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FulfillmentInformationType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FulfillmentInformationType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ThirdPartyAdministrator" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FulfillmentChannelRestriction" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}FulfillmentChannelRestrictionType" minOccurs="0"/>
 *         &lt;element name="WaitingPeriod" type="{http://www.w3.org/2001/XMLSchema}integer" minOccurs="0"/>
 *         &lt;element name="ADHEligible" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="FeeSKU" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MaxBenefit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="RemainingBenefit" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FulfillmentInformationType", propOrder = {
    "thirdPartyAdministrator",
    "fulfillmentChannelRestriction",
    "waitingPeriod",
    "adhEligible",
    "feeSKU",
    "maxBenefit",
    "remainingBenefit"
})
public class FulfillmentInformationType {

    @XmlElement(name = "ThirdPartyAdministrator")
    protected String thirdPartyAdministrator;
    @XmlElement(name = "FulfillmentChannelRestriction")
    protected FulfillmentChannelRestrictionType fulfillmentChannelRestriction;
    @XmlElement(name = "WaitingPeriod")
    protected BigInteger waitingPeriod;
    @XmlElement(name = "ADHEligible")
    protected String adhEligible;
    @XmlElement(name = "FeeSKU")
    protected String feeSKU;
    @XmlElement(name = "MaxBenefit")
    protected String maxBenefit;
    @XmlElement(name = "RemainingBenefit")
    protected String remainingBenefit;

    /**
     * Gets the value of the thirdPartyAdministrator property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getThirdPartyAdministrator() {
        return thirdPartyAdministrator;
    }

    /**
     * Sets the value of the thirdPartyAdministrator property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setThirdPartyAdministrator(String value) {
        this.thirdPartyAdministrator = value;
    }

    /**
     * Gets the value of the fulfillmentChannelRestriction property.
     * 
     * @return
     *     possible object is
     *     {@link FulfillmentChannelRestrictionType }
     *     
     */
    public FulfillmentChannelRestrictionType getFulfillmentChannelRestriction() {
        return fulfillmentChannelRestriction;
    }

    /**
     * Sets the value of the fulfillmentChannelRestriction property.
     * 
     * @param value
     *     allowed object is
     *     {@link FulfillmentChannelRestrictionType }
     *     
     */
    public void setFulfillmentChannelRestriction(FulfillmentChannelRestrictionType value) {
        this.fulfillmentChannelRestriction = value;
    }

    /**
     * Gets the value of the waitingPeriod property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getWaitingPeriod() {
        return waitingPeriod;
    }

    /**
     * Sets the value of the waitingPeriod property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setWaitingPeriod(BigInteger value) {
        this.waitingPeriod = value;
    }

    /**
     * Gets the value of the adhEligible property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getADHEligible() {
        return adhEligible;
    }

    /**
     * Sets the value of the adhEligible property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setADHEligible(String value) {
        this.adhEligible = value;
    }

    /**
     * Gets the value of the feeSKU property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeeSKU() {
        return feeSKU;
    }

    /**
     * Sets the value of the feeSKU property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeeSKU(String value) {
        this.feeSKU = value;
    }

    /**
     * Gets the value of the maxBenefit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMaxBenefit() {
        return maxBenefit;
    }

    /**
     * Sets the value of the maxBenefit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMaxBenefit(String value) {
        this.maxBenefit = value;
    }

    /**
     * Gets the value of the remainingBenefit property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemainingBenefit() {
        return remainingBenefit;
    }

    /**
     * Sets the value of the remainingBenefit property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemainingBenefit(String value) {
        this.remainingBenefit = value;
    }

}
