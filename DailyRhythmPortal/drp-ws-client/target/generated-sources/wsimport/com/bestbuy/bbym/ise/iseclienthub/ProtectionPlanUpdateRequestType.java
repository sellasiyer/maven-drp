
package com.bestbuy.bbym.ise.iseclienthub;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProtectionPlanUpdateRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ProtectionPlanUpdateRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="MetaData" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4}RequestMetadataType" minOccurs="0"/>
 *         &lt;element name="ProtectionPlan" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Scheme/V4}ProtectionPlanDetailType" minOccurs="0"/>
 *         &lt;element name="TargetOwnerCustomerID" type="{http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Common/V4}CustomerIDType" minOccurs="0"/>
 *         &lt;element name="StateCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="InServiceDays" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ProtectionPlanUpdateRequestType", namespace = "http://webservices.bestbuy.com/ProtectionHub/ProtectionPlan/Service/V4", propOrder = {
    "metaData",
    "protectionPlan",
    "targetOwnerCustomerID",
    "stateCode",
    "inServiceDays"
})
public class ProtectionPlanUpdateRequestType {

    @XmlElement(name = "MetaData")
    protected RequestMetadataType metaData;
    @XmlElement(name = "ProtectionPlan")
    protected ProtectionPlanDetailType protectionPlan;
    @XmlElement(name = "TargetOwnerCustomerID")
    protected CustomerIDType targetOwnerCustomerID;
    @XmlElement(name = "StateCode")
    protected String stateCode;
    @XmlElement(name = "InServiceDays")
    protected Integer inServiceDays;

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
     * Gets the value of the protectionPlan property.
     * 
     * @return
     *     possible object is
     *     {@link ProtectionPlanDetailType }
     *     
     */
    public ProtectionPlanDetailType getProtectionPlan() {
        return protectionPlan;
    }

    /**
     * Sets the value of the protectionPlan property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProtectionPlanDetailType }
     *     
     */
    public void setProtectionPlan(ProtectionPlanDetailType value) {
        this.protectionPlan = value;
    }

    /**
     * Gets the value of the targetOwnerCustomerID property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerIDType }
     *     
     */
    public CustomerIDType getTargetOwnerCustomerID() {
        return targetOwnerCustomerID;
    }

    /**
     * Sets the value of the targetOwnerCustomerID property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerIDType }
     *     
     */
    public void setTargetOwnerCustomerID(CustomerIDType value) {
        this.targetOwnerCustomerID = value;
    }

    /**
     * Gets the value of the stateCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStateCode() {
        return stateCode;
    }

    /**
     * Sets the value of the stateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStateCode(String value) {
        this.stateCode = value;
    }

    /**
     * Gets the value of the inServiceDays property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getInServiceDays() {
        return inServiceDays;
    }

    /**
     * Sets the value of the inServiceDays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setInServiceDays(Integer value) {
        this.inServiceDays = value;
    }

}
