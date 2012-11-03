
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getScorecardResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getScorecardResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeEligibilityResponse" type="{http://bestbuy.com/bbym/ucs}ScorecardResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getScorecardResponse", propOrder = {
    "upgradeEligibilityResponse"
})
public class GetScorecardResponse {

    protected ScorecardResponse upgradeEligibilityResponse;

    /**
     * Gets the value of the upgradeEligibilityResponse property.
     * 
     * @return
     *     possible object is
     *     {@link ScorecardResponse }
     *     
     */
    public ScorecardResponse getUpgradeEligibilityResponse() {
        return upgradeEligibilityResponse;
    }

    /**
     * Sets the value of the upgradeEligibilityResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScorecardResponse }
     *     
     */
    public void setUpgradeEligibilityResponse(ScorecardResponse value) {
        this.upgradeEligibilityResponse = value;
    }

}
