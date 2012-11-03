
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCachedUpgradeEligibilityResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCachedUpgradeEligibilityResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeEligibilityResponse" type="{http://bestbuy.com/bbym/ucs}UpgradeEligibilityResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCachedUpgradeEligibilityResponse", propOrder = {
    "upgradeEligibilityResponse"
})
public class GetCachedUpgradeEligibilityResponse {

    protected UpgradeEligibilityResponse upgradeEligibilityResponse;

    /**
     * Gets the value of the upgradeEligibilityResponse property.
     * 
     * @return
     *     possible object is
     *     {@link UpgradeEligibilityResponse }
     *     
     */
    public UpgradeEligibilityResponse getUpgradeEligibilityResponse() {
        return upgradeEligibilityResponse;
    }

    /**
     * Sets the value of the upgradeEligibilityResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpgradeEligibilityResponse }
     *     
     */
    public void setUpgradeEligibilityResponse(UpgradeEligibilityResponse value) {
        this.upgradeEligibilityResponse = value;
    }

}
