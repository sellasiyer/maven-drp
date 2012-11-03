
package com.bestbuy.bbym.ise.iseclientucs;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getCarrierAndUpgradeCheckerStatus complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getCarrierAndUpgradeCheckerStatus">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carrierAndUpgradeCheckerStatusRequest" type="{http://bestbuy.com/bbym/ucs}CarrierAndUpgradeCheckerStatusRequest" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getCarrierAndUpgradeCheckerStatus", propOrder = {
    "carrierAndUpgradeCheckerStatusRequest"
})
public class GetCarrierAndUpgradeCheckerStatus {

    protected CarrierAndUpgradeCheckerStatusRequest carrierAndUpgradeCheckerStatusRequest;

    /**
     * Gets the value of the carrierAndUpgradeCheckerStatusRequest property.
     * 
     * @return
     *     possible object is
     *     {@link CarrierAndUpgradeCheckerStatusRequest }
     *     
     */
    public CarrierAndUpgradeCheckerStatusRequest getCarrierAndUpgradeCheckerStatusRequest() {
        return carrierAndUpgradeCheckerStatusRequest;
    }

    /**
     * Sets the value of the carrierAndUpgradeCheckerStatusRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link CarrierAndUpgradeCheckerStatusRequest }
     *     
     */
    public void setCarrierAndUpgradeCheckerStatusRequest(CarrierAndUpgradeCheckerStatusRequest value) {
        this.carrierAndUpgradeCheckerStatusRequest = value;
    }

}
