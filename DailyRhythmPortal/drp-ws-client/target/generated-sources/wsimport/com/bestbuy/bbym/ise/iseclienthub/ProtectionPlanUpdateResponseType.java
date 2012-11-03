
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanUpdateResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanUpdateResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SystemStatus" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}SystemStatusType" minOccurs="0"/>
 *         &lt;element name="ProtectionPlanID" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanIDType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanUpdateResponseType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", propOrder = {
    "systemStatus",
    "protectionPlanID"
})
public class ProtectionPlanUpdateResponseType {

    @XmlElement(name = "SystemStatus")
    protected SystemStatusType systemStatus;
    @XmlElement(name = "ProtectionPlanID")
    protected ProtectionPlanIDType protectionPlanID;

    /**
     * Gets the value of the systemStatus property.
     * 
     * @return
     *     possible object is
     *     {@link SystemStatusType }
     *     
     */
    public SystemStatusType getSystemStatus() {
        return systemStatus;
    }

    /**
     * Sets the value of the systemStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link SystemStatusType }
     *     
     */
    public void setSystemStatus(SystemStatusType value) {
        this.systemStatus = value;
    }

    /**
     * Gets the value of the protectionPlanID property.
     * 
     * @return
     *     possible object is
     *     {@link ProtectionPlanIDType }
     *     
     */
    public ProtectionPlanIDType getProtectionPlanID() {
        return protectionPlanID;
    }

    /**
     * Sets the value of the protectionPlanID property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtectionPlanIDType }
     *     
     */
    public void setProtectionPlanID(ProtectionPlanIDType value) {
        this.protectionPlanID = value;
    }

}
