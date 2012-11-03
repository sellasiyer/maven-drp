
package com.bestbuy.bbym.ise.iseclientucs;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CarrierAndUpgradeCheckerStatusResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CarrierAndUpgradeCheckerStatusResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="carrierAndUpgradeCheckerStatuses" type="{http://bestbuy.com/bbym/ucs}CarrierAndUpgradeCheckerStatus" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CarrierAndUpgradeCheckerStatusResponse", propOrder = {
    "carrierAndUpgradeCheckerStatuses"
})
public class CarrierAndUpgradeCheckerStatusResponse {

    @XmlElement(required = true)
    protected List<CarrierAndUpgradeCheckerStatus> carrierAndUpgradeCheckerStatuses;

    /**
     * Gets the value of the carrierAndUpgradeCheckerStatuses property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the carrierAndUpgradeCheckerStatuses property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCarrierAndUpgradeCheckerStatuses().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CarrierAndUpgradeCheckerStatus }
     * 
     * 
     */
    public List<CarrierAndUpgradeCheckerStatus> getCarrierAndUpgradeCheckerStatuses() {
        if (carrierAndUpgradeCheckerStatuses == null) {
            carrierAndUpgradeCheckerStatuses = new ArrayList<CarrierAndUpgradeCheckerStatus>();
        }
        return this.carrierAndUpgradeCheckerStatuses;
    }

}
