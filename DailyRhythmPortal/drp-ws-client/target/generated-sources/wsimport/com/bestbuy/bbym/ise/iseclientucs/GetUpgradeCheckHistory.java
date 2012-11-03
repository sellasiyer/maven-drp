
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUpgradeCheckHistory complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUpgradeCheckHistory">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeCheckHistoryRequest" type="{http://bestbuy.com/bbym/ucs}UpgradeCheckHistoryRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUpgradeCheckHistory", propOrder = {
    "upgradeCheckHistoryRequest"
})
public class GetUpgradeCheckHistory {

    protected UpgradeCheckHistoryRequest upgradeCheckHistoryRequest;

    /**
     * Gets the value of the upgradeCheckHistoryRequest property.
     * 
     * @return
     *     possible object is
     *     {@link UpgradeCheckHistoryRequest }
     *     
     */
    public UpgradeCheckHistoryRequest getUpgradeCheckHistoryRequest() {
        return upgradeCheckHistoryRequest;
    }

    /**
     * Sets the value of the upgradeCheckHistoryRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpgradeCheckHistoryRequest }
     *     
     */
    public void setUpgradeCheckHistoryRequest(UpgradeCheckHistoryRequest value) {
        this.upgradeCheckHistoryRequest = value;
    }

}
