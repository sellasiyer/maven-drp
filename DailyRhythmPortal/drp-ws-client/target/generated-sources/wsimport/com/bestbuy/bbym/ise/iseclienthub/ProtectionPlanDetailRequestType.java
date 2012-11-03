
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanDetailRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanDetailRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ProtectionPlanID" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanIDType" minOccurs="0"/>
 *         &lt;element name="ActivityLogLimit" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="MetaData" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4}RequestMetadataType" minOccurs="0"/>
 *         &lt;element name="ShowAuthorizeUser" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanDetailRequestType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", propOrder = {
    "protectionPlanID",
    "activityLogLimit",
    "metaData",
    "showAuthorizeUser"
})
public class ProtectionPlanDetailRequestType {

    @XmlElement(name = "ProtectionPlanID")
    protected ProtectionPlanIDType protectionPlanID;
    @XmlElement(name = "ActivityLogLimit")
    protected Integer activityLogLimit;
    @XmlElement(name = "MetaData")
    protected RequestMetadataType metaData;
    @XmlElement(name = "ShowAuthorizeUser")
    protected Boolean showAuthorizeUser;

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

    /**
     * Gets the value of the activityLogLimit property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getActivityLogLimit() {
        return activityLogLimit;
    }

    /**
     * Sets the value of the activityLogLimit property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setActivityLogLimit(Integer value) {
        this.activityLogLimit = value;
    }

    /**
     * Gets the value of the metaData property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetadataType }
     *     
     */
    public RequestMetadataType getMetaData() {
        return metaData;
    }

    /**
     * Sets the value of the metaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetadataType }
     *     
     */
    public void setMetaData(RequestMetadataType value) {
        this.metaData = value;
    }

    /**
     * Gets the value of the showAuthorizeUser property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isShowAuthorizeUser() {
        return showAuthorizeUser;
    }

    /**
     * Sets the value of the showAuthorizeUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setShowAuthorizeUser(Boolean value) {
        this.showAuthorizeUser = value;
    }

}
