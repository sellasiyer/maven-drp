
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCarrierAndUpgradeCheckerStatusResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCarrierAndUpgradeCheckerStatusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carrierAndUpgradeCheckerStatusResponse" type="{http://bestbuy.com/bbym/ucs}CarrierAndUpgradeCheckerStatusResponse" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCarrierAndUpgradeCheckerStatusResponse", propOrder = {
    "carrierAndUpgradeCheckerStatusResponse"
})
public class GetCarrierAndUpgradeCheckerStatusResponse {

    protected CarrierAndUpgradeCheckerStatusResponse carrierAndUpgradeCheckerStatusResponse;

    /**
     * Gets the value of the carrierAndUpgradeCheckerStatusResponse property.
     * 
     * @return
     *     possible object is
     *     {@link CarrierAndUpgradeCheckerStatusResponse }
     *     
     */
    public CarrierAndUpgradeCheckerStatusResponse getCarrierAndUpgradeCheckerStatusResponse() {
        return carrierAndUpgradeCheckerStatusResponse;
    }

    /**
     * Sets the value of the carrierAndUpgradeCheckerStatusResponse property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarrierAndUpgradeCheckerStatusResponse }
     *     
     */
    public void setCarrierAndUpgradeCheckerStatusResponse(CarrierAndUpgradeCheckerStatusResponse value) {
        this.carrierAndUpgradeCheckerStatusResponse = value;
    }

}
