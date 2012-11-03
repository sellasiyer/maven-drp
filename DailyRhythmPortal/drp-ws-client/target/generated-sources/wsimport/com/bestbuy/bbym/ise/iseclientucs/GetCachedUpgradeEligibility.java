
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCachedUpgradeEligibility complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCachedUpgradeEligibility">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeEligibilityWithCachedUpgradeCheckRequest" type="{http://bestbuy.com/bbym/ucs}CachedUpgradeEligibilityRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCachedUpgradeEligibility", propOrder = {
    "upgradeEligibilityWithCachedUpgradeCheckRequest"
})
public class GetCachedUpgradeEligibility {

    protected CachedUpgradeEligibilityRequest upgradeEligibilityWithCachedUpgradeCheckRequest;

    /**
     * Gets the value of the upgradeEligibilityWithCachedUpgradeCheckRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CachedUpgradeEligibilityRequest }
     *     
     */
    public CachedUpgradeEligibilityRequest getUpgradeEligibilityWithCachedUpgradeCheckRequest() {
        return upgradeEligibilityWithCachedUpgradeCheckRequest;
    }

    /**
     * Sets the value of the upgradeEligibilityWithCachedUpgradeCheckRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CachedUpgradeEligibilityRequest }
     *     
     */
    public void setUpgradeEligibilityWithCachedUpgradeCheckRequest(CachedUpgradeEligibilityRequest value) {
        this.upgradeEligibilityWithCachedUpgradeCheckRequest = value;
    }

}
