
package com.bestbuy.bbym.logistics.device.status.service.v1;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import com.bestbuy.bbym.logistics.device.status.component.v1.ReturnStatusUpdateMessageType;
import com.bestbuy.tsh.common.datatype.v1.UserAreaType;
import com.bestbuy.tsh.common.metadata.components.v2.InternationalBusinessHierarchyType;
import com.bestbuy.tsh.common.metadata.components.v2.RequestMetaDataType;


/**
 * Schema type definition for Update Device Status
 * 
 * <p>Java class for UpdateDeviceStatusRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="UpdateDeviceStatusRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestMetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}RequestMetaDataType" minOccurs="0"/>
 *         &lt;element name="InternationalBusinessHierarchy" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}InternationalBusinessHierarchyType" minOccurs="0"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/logistics/device/status/component/v1}ReturnStatusUpdateMessage"/>
 *         &lt;element ref="{http://www.tsh.bestbuy.com/common/v1}UserArea" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "UpdateDeviceStatusRequestType", propOrder = {
    "requestMetaData",
    "internationalBusinessHierarchy",
    "returnStatusUpdateMessage",
    "userArea"
})
public class UpdateDeviceStatusRequestType {

    @XmlElement(name = "RequestMetaData")
    protected RequestMetaDataType requestMetaData;
    @XmlElement(name = "InternationalBusinessHierarchy")
    protected InternationalBusinessHierarchyType internationalBusinessHierarchy;
    @XmlElement(name = "ReturnStatusUpdateMessage", namespace = "http://bestbuy.com/bbym/logistics/device/status/component/v1", required = true)
    protected ReturnStatusUpdateMessageType returnStatusUpdateMessage;
    @XmlElement(name = "UserArea", namespace = "http://www.tsh.bestbuy.com/common/v1")
    protected UserAreaType userArea;

    /**
     * Gets the value of the requestMetaData property.
     * 
     * @return
     *     possible object is
     *     {@link RequestMetaDataType }
     *     
     */
    public RequestMetaDataType getRequestMetaData() {
        return requestMetaData;
    }

    /**
     * Sets the value of the requestMetaData property.
     * 
     * @param value
     *     allowed object is
     *     {@link RequestMetaDataType }
     *     
     */
    public void setRequestMetaData(RequestMetaDataType value) {
        this.requestMetaData = value;
    }

    /**
     * Gets the value of the internationalBusinessHierarchy property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public InternationalBusinessHierarchyType getInternationalBusinessHierarchy() {
        return internationalBusinessHierarchy;
    }

    /**
     * Sets the value of the internationalBusinessHierarchy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public void setInternationalBusinessHierarchy(InternationalBusinessHierarchyType value) {
        this.internationalBusinessHierarchy = value;
    }

    /**
     * Gets the value of the returnStatusUpdateMessage property.
     * 
     * @return
     *     possible object is
     *     {@link ReturnStatusUpdateMessageType }
     *     
     */
    public ReturnStatusUpdateMessageType getReturnStatusUpdateMessage() {
        return returnStatusUpdateMessage;
    }

    /**
     * Sets the value of the returnStatusUpdateMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReturnStatusUpdateMessageType }
     *     
     */
    public void setReturnStatusUpdateMessage(ReturnStatusUpdateMessageType value) {
        this.returnStatusUpdateMessage = value;
    }

    /**
     * Allows the Tag Super Highway schema data model to extend the specification in order to provide additional information that is not captured in Canonical Schema.
     * 
     * @return
     *     possible object is
     *     {@link UserAreaType }
     *     
     */
    public UserAreaType getUserArea() {
        return userArea;
    }

    /**
     * Sets the value of the userArea property.
     * 
     * @param value
     *     allowed object is
     *     {@link UserAreaType }
     *     
     */
    public void setUserArea(UserAreaType value) {
        this.userArea = value;
    }

}
