
package com.bestbuy.bbym.ise.iseclienthub;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanSearchResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanSearchResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SystemStatus" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}SystemStatusType" minOccurs="0"/>
 *         &lt;element name="ProtectionPlan" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanSearchResponseType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", propOrder = {
    "systemStatus",
    "protectionPlan"
})
public class ProtectionPlanSearchResponseType {

    @XmlElement(name = "SystemStatus")
    protected SystemStatusType systemStatus;
    @XmlElement(name = "ProtectionPlan")
    protected List<ProtectionPlanType> protectionPlan;

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
     * Gets the value of the protectionPlan property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the protectionPlan property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProtectionPlan().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProtectionPlanType }
     * 
     * 
     */
    public List<ProtectionPlanType> getProtectionPlan() {
        if (protectionPlan == null) {
            protectionPlan = new ArrayList<ProtectionPlanType>();
        }
        return this.protectionPlan;
    }

}
