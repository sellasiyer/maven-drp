
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getUpgradeCheckHistoryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getUpgradeCheckHistoryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="upgradeCheckHistoryResponse" type="{http://bestbuy.com/bbym/ucs}UpgradeCheckHistoryResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getUpgradeCheckHistoryResponse", propOrder = {
    "upgradeCheckHistoryResponse"
})
public class GetUpgradeCheckHistoryResponse {

    protected UpgradeCheckHistoryResponse upgradeCheckHistoryResponse;

    /**
     * Gets the value of the upgradeCheckHistoryResponse property.
     * 
     * @return
     *     possible object is
     *     {@link UpgradeCheckHistoryResponse }
     *     
     */
    public UpgradeCheckHistoryResponse getUpgradeCheckHistoryResponse() {
        return upgradeCheckHistoryResponse;
    }

    /**
     * Sets the value of the upgradeCheckHistoryResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link UpgradeCheckHistoryResponse }
     *     
     */
    public void setUpgradeCheckHistoryResponse(UpgradeCheckHistoryResponse value) {
        this.upgradeCheckHistoryResponse = value;
    }

}
