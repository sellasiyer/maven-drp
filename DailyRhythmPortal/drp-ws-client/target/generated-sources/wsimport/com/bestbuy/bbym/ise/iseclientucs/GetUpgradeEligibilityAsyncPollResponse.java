
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUpgradeEligibilityAsyncPollResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUpgradeEligibilityAsyncPollResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeEligibilityResponseAsyncPoll" type="{http://bestbuy.com/bbym/ucs}UpgradeEligibilityResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUpgradeEligibilityAsyncPollResponse", propOrder = {
    "upgradeEligibilityResponseAsyncPoll"
})
public class GetUpgradeEligibilityAsyncPollResponse {

    protected UpgradeEligibilityResponse upgradeEligibilityResponseAsyncPoll;

    /**
     * Gets the value of the upgradeEligibilityResponseAsyncPoll property.
     * 
     * @return
     *     possible object is
     *     {@link UpgradeEligibilityResponse }
     *     
     */
    public UpgradeEligibilityResponse getUpgradeEligibilityResponseAsyncPoll() {
        return upgradeEligibilityResponseAsyncPoll;
    }

    /**
     * Sets the value of the upgradeEligibilityResponseAsyncPoll property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpgradeEligibilityResponse }
     *     
     */
    public void setUpgradeEligibilityResponseAsyncPoll(UpgradeEligibilityResponse value) {
        this.upgradeEligibilityResponseAsyncPoll = value;
    }

}
