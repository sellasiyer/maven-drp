
package com.bestbuy.bbym.ise.iseclientacdsparam;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParamRequestType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ParamRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RequestMetaData" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}RequestMetaDataType" minOccurs="0"/>
 *         &lt;element name="InternationBussinessHirerchy" type="{http://www.tsh.bestbuy.com/common/metadata/components/v2}InternationalBusinessHierarchyType"/>
 *         &lt;element ref="{http://bestbuy.com/bbym/logistics/param/fields/v1}GroupRequest" minOccurs="0"/>
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
@XmlType(name = "ParamRequestType", namespace = "http://bestbuy.com/bbym/logistics/param/service/v1", propOrder = {
    "requestMetaData",
    "internationBussinessHirerchy",
    "groupRequest",
    "userArea"
})
public class ParamRequestType {

    @XmlElement(name = "RequestMetaData")
    protected RequestMetaDataType requestMetaData;
    @XmlElement(name = "InternationBussinessHirerchy", required = true)
    protected InternationalBusinessHierarchyType internationBussinessHirerchy;
    @XmlElement(name = "GroupRequest", namespace = "http://bestbuy.com/bbym/logistics/param/fields/v1")
    protected ParamGroupRequest groupRequest;
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
     * Gets the value of the internationBussinessHirerchy property.
     * 
     * @return
     *     possible object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public InternationalBusinessHierarchyType getInternationBussinessHirerchy() {
        return internationBussinessHirerchy;
    }

    /**
     * Sets the value of the internationBussinessHirerchy property.
     * 
     * @param value
     *     allowed object is
     *     {@link InternationalBusinessHierarchyType }
     *     
     */
    public void setInternationBussinessHirerchy(InternationalBusinessHierarchyType value) {
        this.internationBussinessHirerchy = value;
    }

    /**
     * Gets the value of the groupRequest property.
     * 
     * @return
     *     possible object is
     *     {@link ParamGroupRequest }
     *     
     */
    public ParamGroupRequest getGroupRequest() {
        return groupRequest;
    }

    /**
     * Sets the value of the groupRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link ParamGroupRequest }
     *     
     */
    public void setGroupRequest(ParamGroupRequest value) {
        this.groupRequest = value;
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
