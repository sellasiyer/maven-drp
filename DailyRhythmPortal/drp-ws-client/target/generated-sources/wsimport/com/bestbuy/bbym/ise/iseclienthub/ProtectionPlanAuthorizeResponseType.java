
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanAuthorizeResponseType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanAuthorizeResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SystemStatus" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}SystemStatusType" minOccurs="0"/>
 *         &lt;element name="AuthorizeActionNote" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanAuthorizeResponseType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", propOrder = {
    "systemStatus",
    "authorizeActionNote"
})
public class ProtectionPlanAuthorizeResponseType {

    @XmlElement(name = "SystemStatus")
    protected SystemStatusType systemStatus;
    @XmlElement(name = "AuthorizeActionNote")
    protected String authorizeActionNote;

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
     * Gets the value of the authorizeActionNote property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorizeActionNote() {
        return authorizeActionNote;
    }

    /**
     * Sets the value of the authorizeActionNote property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorizeActionNote(String value) {
        this.authorizeActionNote = value;
    }

}
