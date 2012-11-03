
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUpgradeEligibilityAsyncResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUpgradeEligibilityAsyncResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeEligibilityResponseAsync" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUpgradeEligibilityAsyncResponse", propOrder = {
    "upgradeEligibilityResponseAsync"
})
public class GetUpgradeEligibilityAsyncResponse {

    protected long upgradeEligibilityResponseAsync;

    /**
     * Gets the value of the upgradeEligibilityResponseAsync property.
     * 
     */
    public long getUpgradeEligibilityResponseAsync() {
        return upgradeEligibilityResponseAsync;
    }

    /**
     * Sets the value of the upgradeEligibilityResponseAsync property.
     * 
     */
    public void setUpgradeEligibilityResponseAsync(long value) {
        this.upgradeEligibilityResponseAsync = value;
    }

}
