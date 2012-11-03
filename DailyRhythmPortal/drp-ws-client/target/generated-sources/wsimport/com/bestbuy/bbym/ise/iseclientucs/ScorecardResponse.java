
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ScorecardResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ScorecardResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="locationId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="upgradeCheckCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="optInCount" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ScorecardResponse", propOrder = {
    "locationId",
    "upgradeCheckCount",
    "optInCount"
})
public class ScorecardResponse {

    protected int locationId;
    protected int upgradeCheckCount;
    protected int optInCount;

    /**
     * Gets the value of the locationId property.
     * 
     */
    public int getLocationId() {
        return locationId;
    }

    /**
     * Sets the value of the locationId property.
     * 
     */
    public void setLocationId(int value) {
        this.locationId = value;
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

}
