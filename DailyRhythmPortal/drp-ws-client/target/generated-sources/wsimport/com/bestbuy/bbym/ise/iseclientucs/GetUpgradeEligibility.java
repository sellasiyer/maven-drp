
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUpgradeEligibility complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUpgradeEligibility">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeEligibilityRequest" type="{http://bestbuy.com/bbym/ucs}UpgradeEligibilityRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUpgradeEligibility", propOrder = {
    "upgradeEligibilityRequest"
})
public class GetUpgradeEligibility {

    protected UpgradeEligibilityRequest upgradeEligibilityRequest;

    /**
     * Gets the value of the upgradeEligibilityRequest property.
     * 
     * @return
     *     possible object is
     *     {@link UpgradeEligibilityRequest }
     *     
     */
    public UpgradeEligibilityRequest getUpgradeEligibilityRequest() {
        return upgradeEligibilityRequest;
    }

    /**
     * Sets the value of the upgradeEligibilityRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpgradeEligibilityRequest }
     *     
     */
    public void setUpgradeEligibilityRequest(UpgradeEligibilityRequest value) {
        this.upgradeEligibilityRequest = value;
    }

}
