
package com.bestbuy.bbym.logistics.device.status.component.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Contains information about repair performed on a device
 * 
 * <p>Java class for RepairType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RepairType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RepairLevel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RepairStatus" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RepairType", propOrder = {
    "repairLevel",
    "repairStatus"
})
public class RepairType {

    @XmlElement(name = "RepairLevel", required = true)
    protected String repairLevel;
    @XmlElement(name = "RepairStatus", required = true)
    protected String repairStatus;

    /**
     * Gets the value of the repairLevel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepairLevel() {
        return repairLevel;
    }

    /**
     * Sets the value of the repairLevel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepairLevel(String value) {
        this.repairLevel = value;
    }

    /**
     * Gets the value of the repairStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRepairStatus() {
        return repairStatus;
    }

    /**
     * Sets the value of the repairStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRepairStatus(String value) {
        this.repairStatus = value;
    }

}
